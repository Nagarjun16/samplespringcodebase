package com.ngen.cosys.platform.rfid.tracker.feeder.task;

import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.platform.rfid.tracker.feeder.dao.FeederRepository;
import com.ngen.cosys.platform.rfid.tracker.feeder.interceptor.RequestResponseInterceptor;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.AWB;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.DeleteTag;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.JobStatus;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.PrintTag;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.PrintTagInfo;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.ScanFeed;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.Tag;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.TagForPrintAWB;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.TagInfo;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.TagRequest;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.TrackerFeedResponse;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.UpdateTag;
import com.ngen.cosys.platform.rfid.tracker.feeder.service.FeederService;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;

@Component
public class FeederTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(FeederTask.class);

	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	private static final DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private CloseableHttpClient httpClient = null;
	private SSLContext context = null;
	private RestTemplate restTemplate;

	@Autowired
	private FeederService feederService;
	
	@Autowired
	FeederRepository feederReporitory;

	private AuthModel auth = null;
	List<ClientHttpRequestInterceptor> interceptors = null;

	boolean postExecute = false;

	/**
	 * Push all Tag events to Tracker platform, by scanning the events history.
	 */
	public void pushTrackingFeeds() {
		try {
			auth = feederService.getAuthUserPassword();
			JobStatus jobStatus = feederService.getJobStatus();
			List<ScanFeed> scanFeeds = null;
			if ((jobStatus != null) && (jobStatus.isJobEnabled())) {
				Integer stationId = feederReporitory.getRFIDStationId();
				scanFeeds = feederService.getScanFeeds(jobStatus);
				if ((scanFeeds != null) && (scanFeeds.size() > 0)) {
					for (ScanFeed scanFeed : scanFeeds) {
						postExecute = false;
						try {
							scanFeed.setStationId(stationId);
							pushScanFeed(scanFeed, jobStatus);
						} catch (ConnectException e) {
							throw e;
						} catch (Exception e) {
							LOGGER.error("Error", e);
						}
					}
					// If all the tags an successful in update the run date-time for exclusion in next run
					if (postExecute) {
						jobStatus.setLastJobRun(jobStatus.getCurrentJobRun());
						feederService.updateJobStatusLastJobRun(jobStatus);
					}
				}
			}
			jobStatus = null;
			scanFeeds = null;
		} catch (Exception e) {
			LOGGER.error("Error", e);
		} finally {
			auth = null;
			context = null;
			httpClient = null;
			restTemplate = null;
			interceptors = null;
		}
	}

	/**
	 * Process each Tag Log to be pushed to the platform
	 * 
	 * @param scanFeed
	 * @param jobStatus
	 * @param buffer
	 * @throws Exception
	 */
	@Transactional
	private void pushScanFeed(ScanFeed scanFeed, JobStatus jobStatus) throws Exception {
		try {
//			LOGGER.debug(scanFeed.toString());
			if (ScanFeed.Stage.PRINT.toString().equalsIgnoreCase(scanFeed.getStageName())) {
				if ("ULD".equals(scanFeed.getFlagUldAwb())) {
					pushPrintUldTagFeed(scanFeed, jobStatus);
				} else {
					pushPrintAwbTagFeed(scanFeed, jobStatus);
				}
			} 
			else if (ScanFeed.Stage.CANCEL.toString().equalsIgnoreCase(scanFeed.getStageName())) {
				pushCancelScanFeed(scanFeed, jobStatus);
			} 
			else if (ScanFeed.FlowType.EXPORT.toString().equalsIgnoreCase(scanFeed.getFlowType())) {
				pushExportScanFeed(scanFeed, jobStatus);
			} 
			else if (ScanFeed.FlowType.IMPORT.toString().equalsIgnoreCase(scanFeed.getFlowType())) {
				pushImportScanFeed(scanFeed, jobStatus);
			}

			// On successful push; update the last sequence of push to job status
			if (postExecute) {
				jobStatus.setLastSeqNo(scanFeed.getMsgSeqNo());
				feederService.updateJobStatusLastSequenceNumber(jobStatus);
			}
		} catch (Exception e) {
			LOGGER.error("Error", e);
			feederService.copyScanFeedToBuffer(scanFeed);
			throw e;
		}
	}

	/**
	 * Send create ULDTag (NOTE this pushes the AWB associations automatically)
	 */
	private void pushPrintUldTagFeed(ScanFeed scanFeed, JobStatus jobStatus) throws Exception {
		PrintTag request = new PrintTag();
		// request.setDeviceId("COSYS-TRACKER-FEEDER");
		request.setStationId(scanFeed.getStationId());
		PrintTagInfo traceableItems = new PrintTagInfo();
		traceableItems.setTagType("uld");
		traceableItems.setUldNo(scanFeed.getUldBtNumber());
		traceableItems.setFlightKey(scanFeed.getFlightKey());
		traceableItems.setFlightDate(scanFeed.getFlightOriginDate() != null ? scanFeed.getFlightOriginDate().format(date) : null);
		traceableItems.setTotalPcs(scanFeed.getTotalPieces());
		traceableItems.setTotalTagPcs(scanFeed.getTotalTagPieces());
		traceableItems.setTotalTags(scanFeed.getTotalTags());
		traceableItems.setEpcCode(scanFeed.getUldRfidTag());
//		Integer totalTags = feederService.getULDTotalTags(scanFeed);
//		if (totalTags > 0) {
			// For BUP case
//			List<ScanFeed> buildUpScanFeeds = feederService.getBuilUpScanFeeds(scanFeed);
//			List<AWB> awbList = new ArrayList<AWB>();
//			if (buildUpScanFeeds != null && buildUpScanFeeds.size() > 0) {
//				for (ScanFeed buildUpScanFeed : buildUpScanFeeds) {
//					if (buildUpScanFeed.getAwbNumber() == null) {
//						continue;
//					}
//					
//					AWB awb = new AWB();
//					awb.setAwbNo(buildUpScanFeed.getAwbNumber());
//					awb.setTotalPcs(buildUpScanFeed.getTotalPieces());
//					awb.setTotalTagPcs(buildUpScanFeed.getTotalPieces());
//					awbList.add(awb);
//				}
//			}
//			traceableItems.setAwbList(awbList);
			
			request.setTraceableItems(Arrays.asList(traceableItems));

			if (request != null) {
				postEventData(jobStatus.getPrintTagService(), request);
			}
//		}
		
//		UpdateTag request = null;
//		request = new UpdateTag();
//		request.setDeviceId("COSYS-TRACKER-FEEDER");
//		request.setFlowType("EXPORT");
//		request.setStageName("BUILDUP");
//
//		request.setStationId(1);
//		request.setUserName(scanFeed.getUserId());
//		request.setStorageLocation(scanFeed.getLocation());
//		if (scanFeed.getFlightKey() != null) {
//			request.setFlightKey(scanFeed.getFlightKey());
//		}
//		if (scanFeed.getFlightOriginDate() != null) {
//			request.setFlightDate(formatter.format(scanFeed.getFlightOriginDate()));
//		}
//		request.setUldNo(scanFeed.getUldBtNumber());
//		request.setEpcCode(scanFeed.getRfidTag());
//
//		request.setTagInfoList(new ArrayList<TagInfo>());
//
//		// ------ Get All AWBs of the ULD
//		List<ScanFeed> buildUpScanFeeds = feederService.getBuilUpScanFeeds(scanFeed);
//		if ((buildUpScanFeeds != null) && (buildUpScanFeeds.size() > 0)) {
//
//			for (ScanFeed buildUpScanFeed : buildUpScanFeeds) {
//
//				if (buildUpScanFeed.getAwbNumber() == null) {
//					continue;
//				}
//
//				TagInfo tagInfo = null;
//				// new AWB or already in list?.. create new taginfo object for every AWB
//				for (TagInfo tagInfoX : request.getTagInfoList()) {
//					if (tagInfoX.getAwbNo().equalsIgnoreCase(buildUpScanFeed.getAwbNumber())) {
//						tagInfo = tagInfoX;
//						break;
//					}
//				}
//				if (tagInfo == null) {
//					tagInfo = new TagInfo();
//					tagInfo.setListOfTags(new ArrayList<TagForUpdate>());
//					request.getTagInfoList().add(tagInfo);
//				}
//
//				tagInfo.setAwbNo(buildUpScanFeed.getAwbNumber());
//
//				if (buildUpScanFeed.getUldBtNumber() != null && buildUpScanFeed.getUldBtNumber().length() <= 6) {
//					tagInfo.setBtNo(buildUpScanFeed.getUldBtNumber());
//				} else {
//					tagInfo.setUldNo(buildUpScanFeed.getUldBtNumber());
//				}
//
//				tagInfo.setTotalPcs(tagInfo.getTotalPcs() + buildUpScanFeed.getTagPieces());
//				// NOT VALID VALUE, doesnt give awb pieces. Used??
//
//				tagInfo.setTotalScannedPcs(tagInfo.getTotalScannedPcs() + buildUpScanFeed.getTagPieces());
//
//				TagForUpdate tag = new TagForUpdate();
//				tag.setIsManualVerified("N");
//				tag.setTagNo(buildUpScanFeed.getTagNo());
//				tag.setEpcCode(buildUpScanFeed.getRfidTag());
//				tag.setTimeOfScan(formatter.format(buildUpScanFeed.getScanDate()));
//
//				if (tagInfo.getListOfTags() != null) {
//					tagInfo.getListOfTags().add(tag);
//				}
//			}
//		}
//
//		postEventData(jobStatus.getUpdateTagService(), request);
	}

	/**
	 * Print AWB tag, record one tag per scanFeed
	 */
	private void pushPrintAwbTagFeed(ScanFeed scanFeed, JobStatus jobStatus) throws Exception {
		PrintTag request = new PrintTag();
		// request.setDeviceId("COSYS-TRACKER-FEEDER");
		request.setStationId(scanFeed.getStationId());
		PrintTagInfo traceableItems = new PrintTagInfo();
		traceableItems.setTagType("awb");
		traceableItems.setAwbNo(scanFeed.getAwbNumber() != null ? scanFeed.getAwbNumber() : null);
		traceableItems.setTotalPcs(scanFeed.getTotalPieces());
		traceableItems.setTotalTagPcs(scanFeed.getTotalTagPieces()); 
		traceableItems.setTotalTags(scanFeed.getTotalTags());
//		Integer totalTags = feederService.getAWBTotalTags(scanFeed);
//		if (totalTags > 0) {
			// Add tag info
			traceableItems.setTagList(new ArrayList<TagForPrintAWB>());
			TagForPrintAWB tag = new TagForPrintAWB();
			tag.setTagNo(scanFeed.getTagNo());
			tag.setEpcCode(scanFeed.getRfidTag());
			tag.setPcs(scanFeed.getTagPieces());
			traceableItems.getTagList().add(tag);

			request.setTraceableItems(Arrays.asList(traceableItems));

			if (request != null) {
				postEventData(jobStatus.getPrintTagService(), request);
			}
//		}
	}

	private void pushCancelScanFeed(ScanFeed scanFeed, JobStatus jobStatus) throws Exception {
		DeleteTag request = new DeleteTag();
		request.setStationId(scanFeed.getStationId()); 
		Tag tag = new Tag();
		tag.setEpcCode(scanFeed.getRfidTag());
		request.setEpcCodeList(Arrays.asList(tag));
		if (request != null) {
			postEventData(jobStatus.getDeleteTagService(), request);
		}
	}

	private void pushExportScanFeed(ScanFeed scanFeed, JobStatus jobStatus) throws Exception {
		UpdateTag request = new UpdateTag();
		// request.setDeviceId("COSYS-TRACKER-FEEDER");
		request.setStationId(scanFeed.getStationId());

		TagInfo tagInfo = new TagInfo();
		tagInfo.setEventDateTime(formatter.format(new Date()));
		tagInfo.setFlowType(ScanFeed.FlowType.EXPORT.toString());

		if (ScanFeed.Stage.RECEIVED.toString().equalsIgnoreCase(scanFeed.getStageName()) 
				|| ScanFeed.Stage.ACCEPTANCE.toString().equalsIgnoreCase(scanFeed.getStageName())) {
			tagInfo.setStageName(ScanFeed.Stage.RECEIVED.toString());
		} 
		else if (ScanFeed.Stage.STORAGE.toString().equalsIgnoreCase(scanFeed.getStageName())) {
			tagInfo.setStageName(ScanFeed.Stage.STORAGE.toString());
		} 
		else if (ScanFeed.Stage.BUILDUP.toString().equalsIgnoreCase(scanFeed.getStageName())) {
			tagInfo.setStageName(ScanFeed.Stage.BUILDUP.toString());
			// 1. If ULD Tag, push update for all AWBs
			// Hopefully nothing to do...

			// 2. if AWB Tag, check if any ULD is associated with this AWB and if so set in
			// the request (UpdateTag) with ULD info
//			if (scanFeed.getContainerTagid() > 0) {
//				tagInfo.setEpcCode(scanFeed.getUldRfidTag());
//			}
		} 
		else if (ScanFeed.Stage.HANDOVER.toString().equalsIgnoreCase(scanFeed.getStageName())) {
			tagInfo.setStageName(ScanFeed.Stage.HANDOVER.toString());
		} 
		else if (ScanFeed.Stage.BAYDROPOFF.toString().equalsIgnoreCase(scanFeed.getStageName())) {
			tagInfo.setStageName(ScanFeed.Stage.BAYDROPOFF.toString());
		} 
		else if (ScanFeed.Stage.FLIGHTLOAD.toString().equalsIgnoreCase(scanFeed.getStageName())) {
			tagInfo.setStageName(ScanFeed.Stage.FLIGHTLOAD.toString());
		} 
		else if (ScanFeed.Stage.OFFLOAD.toString().equalsIgnoreCase(scanFeed.getStageName())) {
			tagInfo.setStageName(ScanFeed.Stage.OFFLOAD.toString());
		}
		
		tagInfo.setAwbNo(scanFeed.getAwbNumber() != null ? scanFeed.getAwbNumber() : null);
		if (scanFeed.getUldBtNumber() != null && scanFeed.getUldBtNumber().length() <= 6) {
			tagInfo.setBtNo(scanFeed.getUldBtNumber());
		} else {
			tagInfo.setUldNo(scanFeed.getUldBtNumber());
		}
		tagInfo.setFlightKey(scanFeed.getFlightKey() != null ? scanFeed.getFlightKey() : null);
		tagInfo.setFlightDate(scanFeed.getFlightOriginDate() != null ? scanFeed.getFlightOriginDate().format(date) : null);
		tagInfo.setStorageLocation(scanFeed.getLocation());
		tagInfo.setEpcCode(scanFeed.getRfidTag());
		request.setScanEvents(Arrays.asList(tagInfo));

		if (request != null) {
			postEventData(jobStatus.getUpdateTagService(), request);
		}
	}

	private void pushImportScanFeed(ScanFeed scanFeed, JobStatus jobStatus) throws Exception {
		UpdateTag request = new UpdateTag();
		// request.setDeviceId("COSYS-TRACKER-FEEDER");
		request.setStationId(scanFeed.getStationId());

		TagInfo tagInfo = new TagInfo();
		tagInfo.setEventDateTime(formatter.format(new Date()));
		tagInfo.setFlowType(ScanFeed.FlowType.IMPORT.toString());

		if (ScanFeed.Stage.DELIVERY.toString().equalsIgnoreCase(scanFeed.getStageName())) {
			tagInfo.setStageName(ScanFeed.Stage.DELIVERY.toString());
		} 
		else if (ScanFeed.Stage.STORAGE.toString().equalsIgnoreCase(scanFeed.getStageName())) {
			tagInfo.setStageName(ScanFeed.Stage.STORAGE.toString());
		} 
		else if (ScanFeed.Stage.BREAKDOWN.toString().equalsIgnoreCase(scanFeed.getStageName())) {
			tagInfo.setStageName(ScanFeed.Stage.BREAKDOWN.toString());
		} 
		else if (ScanFeed.Stage.HANDOVER.toString().equalsIgnoreCase(scanFeed.getStageName())) {
			tagInfo.setStageName(ScanFeed.Stage.HANDOVER.toString());
		} 
		else if (ScanFeed.Stage.BAYPICKUP.toString().equalsIgnoreCase(scanFeed.getStageName())) {
			tagInfo.setStageName(ScanFeed.Stage.BAYPICKUP.toString());
		} 
		else if (ScanFeed.Stage.UNLOAD.toString().equalsIgnoreCase(scanFeed.getStageName())) {
			tagInfo.setStageName(ScanFeed.Stage.UNLOAD.toString());
		}

		tagInfo.setAwbNo(scanFeed.getAwbNumber() != null ? scanFeed.getAwbNumber() : null);
		if (scanFeed.getUldBtNumber() != null && scanFeed.getUldBtNumber().length() <= 6) {
			tagInfo.setBtNo(scanFeed.getUldBtNumber());
		} else {
			tagInfo.setUldNo(scanFeed.getUldBtNumber());
		}
		tagInfo.setFlightKey(scanFeed.getFlightKey() != null ? scanFeed.getFlightKey() : null);
		tagInfo.setFlightDate(
				scanFeed.getFlightOriginDate() != null ? scanFeed.getFlightOriginDate().format(date) : null);
		tagInfo.setStorageLocation(scanFeed.getLocation());
		tagInfo.setEpcCode(scanFeed.getRfidTag());
		request.setScanEvents(Arrays.asList(tagInfo));

		if (request != null) {
			postEventData(jobStatus.getUpdateTagService(), request);
		}
	}

	/**
	 * Push to the Platform Service.
	 * 
	 * @param url
	 * @param request
	 * @throws Exception
	 */

	private void postEventData(String url, TagRequest request) throws Exception {
		List<Header> defaultHeaders = new ArrayList<Header>();
		defaultHeaders.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"));
		defaultHeaders.add(new BasicHeader(HttpHeaders.ACCEPT, "application/json"));
		if (auth != null) {
			defaultHeaders.add(new BasicHeader(HttpHeaders.AUTHORIZATION,
					"Basic " + Base64Utils.encodeToString((auth.getUsername() + ":" + auth.getPassword()).getBytes())));
		}
		if (url != null && url.startsWith("https://")) {
			// For HTTPS TLSv1.2 Enabling
			context = SSLContext.getInstance("TLSv1.2");
			context.init(null, null, null);
			httpClient = HttpClientBuilder.create().setDefaultHeaders(defaultHeaders).setSSLContext(context).build();
		} else {
			httpClient = HttpClientBuilder.create().setDefaultHeaders(defaultHeaders).build();
			httpClient = HttpClients.custom().setDefaultHeaders(defaultHeaders).build();

		}
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		requestFactory.setConnectTimeout(10000);
		requestFactory.setReadTimeout(15000);

		restTemplate = CosysApplicationContext.getRestTemplate(new RestTemplate(requestFactory));

		interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new RequestResponseInterceptor());
		// restTemplate.setInterceptors(interceptors);

		try {
			LOGGER.debug("Calling " + url);
			LOGGER.debug(request.toString());

			// ---- This failed to work... although default headers are set
			// ResponseEntity<TrackerFeedResponse> response =
			// restTemplate.postForEntity(url, request, TrackerFeedResponse.class);
			//
			// LOGGER.debug(response.toString());
			// ----- hence the update below

			HttpHeaders headers = new org.springframework.http.HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<Object> requestHttpEntity = new HttpEntity<Object>(request, headers);

			ObjectMapper mapper = new ObjectMapper();
			LOGGER.error("===========================request begin================================================");
			LOGGER.error("URI         : {}" + url);
			LOGGER.error("Header      : {}" + mapper.writeValueAsString(headers));
			LOGGER.error("Request body: {}" + mapper.writeValueAsString(request));
			LOGGER.error("==========================request end================================================");

			ResponseEntity<TrackerFeedResponse> response = restTemplate.postForEntity(url, requestHttpEntity,
					TrackerFeedResponse.class);

			LOGGER.error("============================response begin==========================================");
			LOGGER.error("Status code  : {}" + response.getStatusCode().value());
			LOGGER.error("Headers      : {}" + response.getHeaders());
			LOGGER.error("Response body: {}" + mapper.writeValueAsString(response.getBody()));
			LOGGER.error("=======================response end=================================================");

			LOGGER.debug(response.toString());

			if (response != null && response.getBody().getData() != null) {
				if (response.getStatusCode().value() == 200) {
					postExecute = true;
					// Good to go
					return;
				}
				if (response.getStatusCode().value() >= 400) {
					postExecute = false;
					// Bad response... Abort this and future processing
					LOGGER.debug(" Response Error: HTTP " + response.getStatusCode().value() + "  "
							+ response.getBody().getData());
					throw new ConnectException(new Long(response.getStatusCode().value()).toString());
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			this.context = null;
			this.httpClient = null;
			this.restTemplate = null;
			this.interceptors = null;
		}
	}

}
