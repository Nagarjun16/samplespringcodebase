package com.ngen.cosys.service.volumetricscanner.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngen.cosys.esb.route.ConnectorPublisher;
import com.ngen.cosys.esb.route.ConnectorUtils;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.ESBRouterTypeUtils;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.events.esb.connector.router.ESBConnectorService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.satssg.interfaces.manifestreconcillationstatement.service.ManifestReconcillationStatementMessageService;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;
import com.ngen.cosys.service.util.api.ServiceUtil;
import com.ngen.cosys.service.volumetricscanner.dao.VolumetricScannerDAO;
import com.ngen.cosys.service.volumetricscanner.model.CancelScanVolWgtRequest;
import com.ngen.cosys.service.volumetricscanner.model.CancelScanVolWgtResponse;
import com.ngen.cosys.service.volumetricscanner.model.CargoEyeScannerImage;
import com.ngen.cosys.service.volumetricscanner.model.CargoEyeVolWeightErrorResponse;
import com.ngen.cosys.service.volumetricscanner.model.CargoEyeVolWeightImageResponse;
import com.ngen.cosys.service.volumetricscanner.model.CargoEyeVolWeightRequest;
import com.ngen.cosys.service.volumetricscanner.model.CargoEyeVolWeightResponse;
import com.ngen.cosys.service.volumetricscanner.model.ScanVolWgtRequest;
import com.ngen.cosys.service.volumetricscanner.model.ScanVolWgtResponse;
import com.ngen.cosys.service.volumetricscanner.model.ScannedImage;
import com.ngen.cosys.service.volumetricscanner.model.UpdateVolWgtRequest;
import com.ngen.cosys.service.volumetricscanner.model.UpdateVolWgtResponse;
import com.ngen.cosys.service.volumetricscanner.model.VolumetricRequest;
import com.ngen.cosys.service.volumetricscanner.model.VolumetricResponse;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class VolumetricScannerServiceImpl implements VolumetricScannerService {

	private static final char[] hexChars = "0123456789ABCDEF".toCharArray();

	private static final Logger LOGGER = LoggerFactory.getLogger(VolumetricScannerService.class);

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	VolumetricScannerDAO volumetricScannerDAO;
	@Autowired
	ConnectorPublisher publisher;
	@Autowired
	ManifestReconcillationStatementMessageService manifestReconcillationStatementMessageService;
	@Autowired
	ConnectorLoggerService logger;
	@Autowired
	private ESBConnectorService connerctorService;
	@Autowired
	ApplicationLoggerService loggerService;

	@Value("${esb.connector.hostname}")
	private String esbHost;

	@Value("${esb.connector.portnumber}")
	private String esbPort;

	@Value("${esb.connector.path-rest}")
	private String esbPathREST;

	private HashMap<String, String> cargoEyeConfigurations;

	private String ipAddress;
	
	@Override
	@Deprecated
	public ResponseEntity<ScanVolWgtRequest> scanVolWgtReq(ScanVolWgtRequest payload) throws CustomException {
		LOGGER.warn("Volumetric Scanner Service :: ScanVolWgtRequest - {}");
		volumetricScannerDAO.scanVolWgtReqLog(payload);
		sendReqtoVolumetricScanner((String) constructXMLString(ScanVolWgtRequest.class, payload), "scanvolwgt");
		return new ResponseEntity<>(payload, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<VolumetricResponse> saveVolumetricScanRequest(VolumetricRequest volumetricRequest)
			throws CustomException {
		LOGGER.warn("Volumetric Scanner Service :: ScanVolWgtRequest - {}");

		long previousMessageId = volumetricRequest.getMessageId();
		volumetricScannerDAO.saveVolumetricScanRequest(volumetricRequest);
		if ("SMARTGATE".equalsIgnoreCase(volumetricRequest.getScannerType())) {
			ScanVolWgtRequest payload = new ScanVolWgtRequest();
			payload.setMessageId(volumetricRequest.getMessageId());
			payload.setAwb(volumetricRequest.getShipmentNumber());
			payload.setHawb(volumetricRequest.getHawb());
			payload.setMeasuredPieces(volumetricRequest.getMeasuredPieces());
			payload.setDeclaredVolume(volumetricRequest.getDeclaredVolume());
			payload.setSkidHeight(volumetricRequest.getSkidHeight());

			sendReqtoVolumetricScanner((String) constructXMLString(ScanVolWgtRequest.class, payload), "scanvolwgt");
		} else if ("CARGOEYE".equalsIgnoreCase(volumetricRequest.getScannerType())) {
			CompletableFuture.runAsync(() -> {
				try {
					this.cargoEyeConfigurations = volumetricScannerDAO.getCargoEyeConfigurations();
				this.ipAddress = volumetricRequest.getScannerIP();
					sendReqtoVolumetricScanner(
							new CargoEyeVolWeightRequest(volumetricRequest.getMessageId(),
									volumetricRequest.getShipmentNumber(), volumetricRequest.getHawb(),
									volumetricRequest.getMeasuredPieces(), volumetricRequest.getSkidHeight(),
									volumetricRequest.getDeclaredVolume(),
									(("TRUE".equalsIgnoreCase(volumetricRequest.getOddSize())
											|| "Y".equalsIgnoreCase(volumetricRequest.getOddSize())) ? "Y" : "N")),
							volumetricRequest.getScannerId(), volumetricRequest.getScannerIP(), previousMessageId);
				} catch (CustomException e) {
					LOGGER.error("saveVolumetricScanRequest", e);
				} catch (CompletionException e) {
					LOGGER.error("saveVolumetricScanRequest", e);
				}
			});
		}
		return new ResponseEntity<>(new VolumetricResponse(volumetricRequest.getMessageId()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ScanVolWgtResponse> scanVolWgtRes(String payload) throws CustomException {
		ScanVolWgtResponse scanVolWgtResponse = (ScanVolWgtResponse) constructObjectFromXMLString(payload,
				ScanVolWgtResponse.class);
		// Volumetric Scanner Error Response
		if (Objects.nonNull(scanVolWgtResponse)) {
			volumetricScannerErrorResponseLog(scanVolWgtResponse);
			if (Objects.nonNull(scanVolWgtResponse.getMessageId())) {
				volumetricScannerDAO.scanVolWgtResLog(scanVolWgtResponse);
			}
		}
		return new ResponseEntity<>(scanVolWgtResponse, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CancelScanVolWgtRequest> cancelScanVolWgtReq(CancelScanVolWgtRequest payload)
			throws CustomException {
		volumetricScannerDAO.cancelScanVolWgtReq(payload);
		sendReqtoVolumetricScanner((String) constructXMLString(CancelScanVolWgtRequest.class, payload), "scancancel");
		return new ResponseEntity<>(payload, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<CancelScanVolWgtResponse> cancelScanVolWgtRes(String payload) throws CustomException {
		CancelScanVolWgtResponse cancelScanVolWgtResponse = (CancelScanVolWgtResponse) constructObjectFromXMLString(
				payload, CancelScanVolWgtResponse.class);
		// Volumetric Scanner Cancel Response
		if (Objects.nonNull(cancelScanVolWgtResponse)) {
			volumetricScannerCancelResponseLog(cancelScanVolWgtResponse);
			if (Objects.nonNull(cancelScanVolWgtResponse.getMessageId())) {
				volumetricScannerDAO.cancelScanVolWgtRes(cancelScanVolWgtResponse);
			}
		}
		return new ResponseEntity<>(cancelScanVolWgtResponse, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> updateVolWgtReqLog(String payload) throws CustomException {
		UpdateVolWgtRequest updateVolWgtRequest = (UpdateVolWgtRequest) constructObjectFromXMLString(payload,
				UpdateVolWgtRequest.class);
		// Volumetric Scanner UPDATE Response
		long messageId = 0L;
		if (Objects.nonNull(updateVolWgtRequest)) {
			volumetricScannerUpdateResponseLog(updateVolWgtRequest);
			if (Objects.nonNull(updateVolWgtRequest.getMessageId())) {
				messageId = updateVolWgtRequest.getMessageId();
				volumetricScannerDAO.updateVolWgtReqLog(updateVolWgtRequest);
			}
		}
		// Success Response
		String volResponse = updateVolumetricResponse(messageId, true);
		return new ResponseEntity<>(volResponse, HttpStatus.OK);
		// return new ResponseEntity<>(updateVolWgtRequest, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<UpdateVolWgtResponse> updateVolWgtResLog(UpdateVolWgtResponse payload)
			throws CustomException {

		volumetricScannerDAO.updateVolWgtResLog(
				(UpdateVolWgtResponse) constructObjectFromXMLString(payload, CancelScanVolWgtResponse.class));
		return new ResponseEntity<UpdateVolWgtResponse>(payload, HttpStatus.OK);
	}

	private Object constructXMLString(Class<?> clazz, Object payload) {
		// Convert object to XML string
		return JacksonUtility.convertObjectToXMLString(payload);

	}

	private Object constructObjectFromXMLString(Object payload, Class<?> clazz) {
		// Convert XML to object string
		return JacksonUtility.convertXMLStringToObject(payload, clazz);
	}

	public void sendReqtoVolumetricScanner(String payload, String operation) {
		LOGGER.warn("Volumetric Scanner Service :: ESB Connector Call - {}");
		boolean loggerEnabled = false;
		BigInteger messageId = null;
		BigInteger errorMessageId = null;
		try {
			if (!loggerEnabled) {
				messageId = logOutgoingMessage(payload);
			}
			Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled, "SMARTGATE",
					TenantContext.getTenantId());
			ResponseEntity<Object> response = publisher.sendPayloadDataToConnector(payload, operation,
					MediaType.APPLICATION_XML, payloadHeaders);
			LOGGER.warn("Volumetric Scanner Service :: SMARTGATE Response received - {}");
			if (Objects.nonNull(response)) {
				String exceptionMsg = null;
				if (Objects.equals(HttpStatus.BAD_REQUEST, response.getStatusCode())) {
					CustomException ex = null;
					if (Objects.nonNull(response.getBody()) && response.getBody() instanceof CustomException) {
						ex = (CustomException) response.getBody();
						exceptionMsg = ex.getErrorCode();
						messageId = (BigInteger) ex.getPlaceHolders()[1];
						if (Objects.nonNull(ex.getPlaceHolders()[2])) {
							errorMessageId = (BigInteger) ex.getPlaceHolders()[2];
						}
						if (!loggerEnabled) {
							// LogINTO outgoing error message table
						}

					} else {
						// Partial Success Case
						if (loggerEnabled) {
							messageId = new BigInteger(
									response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
							if (response.getHeaders().containsKey(ESBRouterTypeUtils.MESSAGE_ID.getName())
									&& Objects.nonNull(
											response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()))) {
								errorMessageId = new BigInteger(response.getHeaders()
										.get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()).get(0));
							}
						}

					}
				} else {
					// Success Case
					Object responseBody = response.getBody();
					LOGGER.warn("Volumetric Scanner Response Body - {}", responseBody);
					if (Objects.nonNull(responseBody)) {
						if (Objects.equals("scanvolwgt", operation)) {
							// ScanVolWgtResponse scanVolumetricResponse = (ScanVolWgtResponse)
							// JacksonUtility.convertXMLStringToObject(responseBody,
							// ScanVolWgtResponse.class);
							ScanVolWgtResponse scanVolumetricResponse = (ScanVolWgtResponse) volumetricScannerResponse(
									(Map<String, Object>) responseBody, operation);
							volumetricScannerErrorResponseLog(scanVolumetricResponse);
							LOGGER.warn("Volumetric Scanner Response Logged - {}");
						} else if (Objects.equals("scancancel", operation)) {
							CancelScanVolWgtResponse cancelVolumetricResponse = (CancelScanVolWgtResponse) volumetricScannerResponse(
									(Map<String, Object>) responseBody, operation);
							volumetricScannerCancelResponseLog(cancelVolumetricResponse);
						}
					}
					if (!loggerEnabled) {
						messageId = new BigInteger(
								response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
						if (response.getHeaders().containsKey(ESBRouterTypeUtils.MESSAGE_ID.getName()) && Objects
								.nonNull(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()))) {
							errorMessageId = new BigInteger(
									response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()).get(0));
						}
					}

				}
			}

			System.out.println("response :: " + response);

			logOutgoingMessage(messageId, "SENT");

		} catch (Exception e) {
			LOGGER.warn("Volumetric Scanner Service :: Exception occurred - {}", String.valueOf(e));
			// Log Outgoing message
			if (messageId != null) {
				logOutgoingMessage(messageId, "EXCEPTION");
			}
		}

	}

	private void sendReqtoVolumetricScanner(CargoEyeVolWeightRequest cargoEyeVolWeightRequest, String scannerId,
			String scannerIP, long previousMessageId) {

		HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		RestTemplate restTemplate = null;
		String url = this.cargoEyeConfigurations.get("CARGOEYE_SCANNER_API_ENDPOINT");
		String connectionTimeout = this.cargoEyeConfigurations.get("CARGOEYE_CONNECTION_TIMEOUT");
		String requestTimeout = this.cargoEyeConfigurations.get("CARGOEYE_REQUEST_TIMEOUT");
		String enableSsl = this.cargoEyeConfigurations.get("CARGOEYE_ENABLE_SSL");

		if (!StringUtils.isEmpty(url)) {
			url = url.replace("{scannerIP}", scannerIP).replace("{scannerId}", scannerId);
			// if(url.endsWith("?")) {
			// url = url.replace("?", "");
			// }
			// if(!url.endsWith("/")) {
			// url += "/";
			// }
		}

		if (enableSsl != null && ("TRUE".equalsIgnoreCase(enableSsl) || "Y".equalsIgnoreCase(enableSsl))) {
			try {
				SSLContext context = SSLContext.getInstance("TLSv1.2");
				context.init(null, null, null);
				CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLContext(context).build();
				HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
						httpClient);
				requestFactory.setConnectTimeout(Integer.valueOf(connectionTimeout) * 1000);
				requestFactory.setReadTimeout(Integer.valueOf(requestTimeout) * 1000);
				restTemplate = CosysApplicationContext.getRestTemplate(new RestTemplate(requestFactory));
			} catch (NoSuchAlgorithmException | KeyManagementException e) {
				LOGGER.error("restTemplateWithSSL", e);
			}
		} else {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
					httpClient);
			requestFactory.setConnectTimeout(Integer.valueOf(connectionTimeout) * 1000);
			requestFactory.setReadTimeout(Integer.valueOf(requestTimeout) * 1000);
			restTemplate = CosysApplicationContext.getRestTemplate(new RestTemplate(requestFactory));
		}

		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				if(response.getStatusCode().value() != 200 && response.getStatusCode().value() != 402) {
					return true;
				}
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				LOGGER.warn("CargoEye Status Code:" + response.getStatusCode().value());
				LOGGER.warn("CargoEye Status Text:" + response.getStatusText());

			}
		});

		HttpEntity<Object> requestHttpEntity = new HttpEntity<Object>(cargoEyeVolWeightRequest, headers);

		ResponseEntity<String> response = null;

		try {
			LOGGER.warn("CargoEye VolWgt URL: " + url);
			response = restTemplate.postForEntity(url, requestHttpEntity, String.class);
			LOGGER.warn("CargoEye Status Code:" + response.getStatusCode().value());			
			if (response.getStatusCodeValue() == 200) {
				CargoEyeVolWeightResponse cargoEyeVolWeightResponse = objectMapper.readValue(response.getBody(),
						CargoEyeVolWeightResponse.class);
				cargoEyeVolWeightResponse.setMessageId(cargoEyeVolWeightRequest.getMessageId());
				cargoEyeVolWeightResponse.setAwb(cargoEyeVolWeightRequest.getAwb());
				cargoEyeVolWeightResponse.setHawb(cargoEyeVolWeightRequest.getHawb());
				volumetricScannerDAO.saveVolumetricScanResponse(cargoEyeVolWeightResponse);
				CompletableFuture.runAsync(() -> {
					try {
						retrieveVolumetricScannerImages(cargoEyeVolWeightResponse, scannerId, scannerIP,
								previousMessageId);
					} catch (CustomException e) {
						LOGGER.error("sendReqtoVolumetricScanner", e);
					} catch (CompletionException e) {
						LOGGER.error("sendReqtoVolumetricScanner", e);
					}
				});

			} else if (response.getStatusCodeValue() == 402) {
				CargoEyeVolWeightErrorResponse cargoEyeVolWeightErrorResponse = objectMapper
						.readValue(response.getBody(), CargoEyeVolWeightErrorResponse.class);
				volumetricScannerDAO.saveVolumetricScanErrorResponse(cargoEyeVolWeightErrorResponse);
				// } else if (response.getStatusCodeValue() == 402) {

				// } else {

			}
		} catch (RestClientException e) {
			LOGGER.error("saveVolumetricScanRequest", e);
		} catch (CustomException e) {
			LOGGER.error("saveVolumetricScanRequest", e);
		} catch (JsonParseException e) {
			LOGGER.error("saveVolumetricScanRequest", e);
		} catch (JsonMappingException e) {
			LOGGER.error("saveVolumetricScanRequest", e);
		} catch (IOException e) {
			LOGGER.error("saveVolumetricScanRequest", e);
		}
		LOGGER.warn(response.toString());
	}

	private void retrieveVolumetricScannerImages(CargoEyeVolWeightResponse cargoEyeVolWeightResponse, String scannerId,
			String scannerIP, long previousMessageId) throws CustomException {
		HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		RestTemplate restTemplate = null;

		String url = this.cargoEyeConfigurations.get("CARGOEYE_IMAGES_API_ENDPOINT");
		String connectionTimeout = this.cargoEyeConfigurations.get("CARGOEYE_CONNECTION_TIMEOUT");
		String requestTimeout = this.cargoEyeConfigurations.get("CARGOEYE_REQUEST_TIMEOUT");
		String enableSsl = this.cargoEyeConfigurations.get("CARGOEYE_ENABLE_SSL");
		String uploadFilesUrl = this.cargoEyeConfigurations.get("CONFIG_UPLOAD_FILES_ENDPOINT");

		if (!StringUtils.isEmpty(url)) {
			if (url.endsWith("?")) {
				url = url.replace("?", "");
			}
			if (!url.endsWith("/")) {
				url += "/";
			}
		}

		if (enableSsl != null && ("TRUE".equalsIgnoreCase(enableSsl) || "Y".equalsIgnoreCase(enableSsl))) {
			try {
				SSLContext context = SSLContext.getInstance("TLSv1.2");
				context.init(null, null, null);
				CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLContext(context).build();
				HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
						httpClient);
				requestFactory.setConnectTimeout(Integer.valueOf(connectionTimeout) * 1000);
				requestFactory.setReadTimeout(Integer.valueOf(requestTimeout) * 1000);
				restTemplate = CosysApplicationContext.getRestTemplate(new RestTemplate(requestFactory));
			} catch (NoSuchAlgorithmException | KeyManagementException e) {
				LOGGER.error("restTemplateWithSSL", e);
			}
		} else {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
					httpClient);
			requestFactory.setConnectTimeout(Integer.valueOf(connectionTimeout) * 1000);
			requestFactory.setReadTimeout(Integer.valueOf(requestTimeout) * 1000);
			restTemplate = CosysApplicationContext.getRestTemplate(new RestTemplate(requestFactory));
		}

		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				if(response.getStatusCode().value() != 200) {
					return true;
				}
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				LOGGER.warn("CargoEye Status Code:" + response.getStatusCode().value());
				LOGGER.warn("CargoEye Status Text:" + response.getStatusText());

			}
		});

		if (cargoEyeVolWeightResponse.getScannedImages() != null && cargoEyeVolWeightResponse.getScannedImages().getImages()!= null
				&& cargoEyeVolWeightResponse.getScannedImages().getImages().size() > 0) {

			LOGGER.warn("CargoEye Image URLs from CargoEyeScanner: " + scannerId + " at IP: " + scannerId);

			ArrayList<String> scannedImages = new ArrayList<>();
			for (CargoEyeScannerImage scannerImage : cargoEyeVolWeightResponse.getScannedImages().getImages()) {
				try {
					String scannedImageUrl = scannerImage.getUrl();
					LOGGER.warn("CargoEye Image ID: " + String.valueOf(scannerImage.getImageId()));
					LOGGER.warn("CargoEye Image URL: " + scannerImage.getUrl());
					byte[] imageBytes = restTemplate.getForObject(URLDecoder.decode(scannedImageUrl, "UTF-8"), byte[].class); 
					LOGGER.warn("CargoEye Image Downloaded");
					scannedImages.add(Base64.getEncoder().encodeToString(imageBytes));
				} catch (RestClientException | UnsupportedEncodingException e) {
					LOGGER.error("retrieveVolumetricScannerImages", e);
				}
			}
			if (scannedImages.size() > 0) {

				String[] scannedImagesArray = new String[scannedImages.size()];
				scannedImagesArray = scannedImages.toArray(scannedImagesArray);

				CargoEyeVolWeightImageResponse cargoEyeVolWeightImageResponse = new CargoEyeVolWeightImageResponse();
				cargoEyeVolWeightImageResponse.setMessageId(cargoEyeVolWeightResponse.getMessageId());
				cargoEyeVolWeightImageResponse.setAwb(cargoEyeVolWeightResponse.getAwb());
				cargoEyeVolWeightImageResponse.setScannedImages(scannedImagesArray);
				cargoEyeVolWeightImageResponse.prepareScannedImages();
				try {
					ResponseEntity<Object> uploadResponse = ServiceUtil
							.route(cargoEyeVolWeightImageResponse.getImages(), uploadFilesUrl);
					if (uploadResponse.getStatusCode() == HttpStatus.OK) {
						volumetricScannerDAO.saveVolumetricScanImageResponse(
								(ArrayList<ScannedImage>) ((ArrayList<LinkedHashMap>) ((LinkedHashMap<String, Object>) uploadResponse
										.getBody()).get("data")).stream().map(imageMap -> {
											return ScannedImage.builder()
													.associatedTo((String) imageMap.get("associatedTo"))
													.uploadDocId(new BigInteger(
															String.valueOf((Integer) imageMap.get("uploadDocId"))))
													.build();
										}).collect(Collectors.toList()));
					}
				} catch (RestClientException e) {
					LOGGER.error("retrieveVolumetricScannerImages", e);
				} catch (CustomException e) {
					LOGGER.error("retrieveVolumetricScannerImages", e);
				}
			}
		} else {
			LOGGER.warn("No Image URLs from CargoEyeScanner: " + scannerId + " at IP: " + scannerId);
		}

		volumetricScannerDAO.deletePreviousVolumeWeightLogData(previousMessageId);

		// LOGGER.debug(response.toString());
	}

	private void logOutgoingMessage(BigInteger referenceId, String status) {
		OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
		outgoingMessage.setOutMessageId(referenceId);
		outgoingMessage.setChannelSent("HTTP");
		outgoingMessage.setInterfaceSystem("SMARTGATE");
		outgoingMessage.setSenderOriginAddress("COSYS");
		outgoingMessage.setMessageType("Vol");
		outgoingMessage.setSubMessageType("TXT");
		outgoingMessage.setCarrierCode(null);
		outgoingMessage.setFlightNumber(null);
		outgoingMessage.setFlightOriginDate(null);
		outgoingMessage.setShipmentNumber(null);
		outgoingMessage.setShipmentDate(null);
		outgoingMessage.setRequestedOn(LocalDateTime.now());
		outgoingMessage.setSentOn(LocalDateTime.now());
		outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
		outgoingMessage.setVersionNo(1);
		outgoingMessage.setSequenceNo(1);
		outgoingMessage.setMessageContentEndIndicator(null);
		outgoingMessage.setStatus(status);
		logger.logOutgoingMessage(outgoingMessage);
	}

	private BigInteger logOutgoingMessage(String payload) {
		OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
		outgoingMessage.setChannelSent("HTTP");
		outgoingMessage.setInterfaceSystem("SMARTGATE");
		outgoingMessage.setSenderOriginAddress("COSYS");
		outgoingMessage.setMessageType("VOL");
		outgoingMessage.setSubMessageType("TXT");
		outgoingMessage.setCarrierCode(null);
		outgoingMessage.setFlightNumber(null);
		outgoingMessage.setFlightOriginDate(null);
		outgoingMessage.setShipmentNumber(null);
		outgoingMessage.setShipmentDate(null);
		outgoingMessage.setRequestedOn(LocalDateTime.now());
		outgoingMessage.setSentOn(LocalDateTime.now());
		outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
		outgoingMessage.setVersionNo(1);
		outgoingMessage.setSequenceNo(1);
		outgoingMessage.setMessageContentEndIndicator(null);
		outgoingMessage.setStatus("PROCESSED");
		outgoingMessage.setMessage(payload);
		// logger.logOutgoingMessage(outgoingMessage);
		loggerService.logInterfaceOutgoingMessage(outgoingMessage);
		System.out.println("outgoing message id            :: " + outgoingMessage.getOutMessageId());
		return outgoingMessage.getOutMessageId();
	}

	public String getVolScannerConnectorURL(String endPointURL) {

		StringBuilder path = new StringBuilder();
		switch (endPointURL) {
			case "scanvolwgt":
				path = new StringBuilder(generateICSURL("scanvolwgt"));
				break;
			case "scanrequest":
				path = new StringBuilder(generateICSURL("scanrequest"));
				break;
			case "scaninfo":
				path = new StringBuilder(generateICSURL("scaninfo"));
				break;
			case "scancancel":
				path = new StringBuilder(generateICSURL("scancancel"));
				break;
			case "errorinfo":
				path = new StringBuilder(generateICSURL("errorinfo"));
				break;
			case "cancelinfo":
				path = new StringBuilder(generateICSURL("cancelinfo"));
				break;
			default:
				break;
		}
		return path.toString();
	}

	private StringBuilder generateICSURL(String endPointURL) {
		StringBuilder path = new StringBuilder();
		path.append("http://").append(esbHost).append(":").append(esbPort);
		path.append(esbPathREST).append("/");
		if (Objects.nonNull(endPointURL)) {
			path.append(endPointURL);
		}
		return path;
	}

	private Object volumetricScannerResponse(Map<String, Object> response, String operation) {
		if (StringUtils.isEmpty(operation)) {
			return null;
		}
		String messageId = null;
		String errorFlag = null;
		String errorDesc = null;
		for (Iterator<Entry<String, Object>> iterator = response.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Object> entry = iterator.next();
			if (!StringUtils.isEmpty(entry.getKey())) {
				if (Objects.equals("messageId", entry.getKey())) {
					messageId = Objects.isNull(entry.getValue()) ? null : String.valueOf(entry.getValue());
				}
				if (Objects.equals("errorFlag", entry.getKey())) {
					errorFlag = Objects.isNull(entry.getValue()) ? null : String.valueOf(entry.getValue());
				}
				if (Objects.equals("errorDescription", entry.getKey())) {
					errorDesc = Objects.isNull(entry.getValue()) ? null : String.valueOf(entry.getValue());
				}
			}
		}
		if (Objects.equals("scanvolwgt", operation)) {
			ScanVolWgtResponse scanVolumetricResponse = new ScanVolWgtResponse();
			scanVolumetricResponse.setMessageId(Long.valueOf(messageId));
			scanVolumetricResponse.setErrorFlag(errorFlag);
			scanVolumetricResponse.setErrorDescription(errorDesc);
			return scanVolumetricResponse;
		} else if (Objects.equals("scancancel", operation)) {
			CancelScanVolWgtResponse cancelVolumetricResponse = new CancelScanVolWgtResponse();
			cancelVolumetricResponse.setMessageId(Long.valueOf(messageId));
			cancelVolumetricResponse.setErrorFlag(errorFlag);
			cancelVolumetricResponse.setErrorDescription(errorDesc);
			return cancelVolumetricResponse;
		}
		//
		return null;
	}

	/**
	 * ERROR Response
	 * 
	 * @param scanVolumetricResponse
	 */
	private void volumetricScannerErrorResponseLog(ScanVolWgtResponse scanVolumetricResponse) {
		//
		LOGGER.warn("Scan Error Volumetric Response After Parsing Availability - {}",
				Objects.isNull(scanVolumetricResponse) ? String.valueOf(false) : String.valueOf(true));
		//
		if (Objects.nonNull(scanVolumetricResponse)) {
			LOGGER.warn(
					"Scan Error Voumetric Response for the Message Id - {} and Received Status - {} and Description - {}",
					String.valueOf(scanVolumetricResponse.getMessageId()), scanVolumetricResponse.getErrorFlag(),
					scanVolumetricResponse.getErrorDescription());
		}
	}

	/**
	 * @param cancelVolumetricResponse
	 */
	private void volumetricScannerCancelResponseLog(CancelScanVolWgtResponse cancelVolumetricResponse) {
		LOGGER.warn("Cancel Volumetric Response After Parsing Availability - {}",
				Objects.isNull(cancelVolumetricResponse) ? String.valueOf(false) : String.valueOf(true));
		//
		if (Objects.nonNull(cancelVolumetricResponse)) {
			LOGGER.warn(
					"Cancel Voumetric Response for the Message Id - {} and Received Status - {} and Description - {}",
					String.valueOf(cancelVolumetricResponse.getMessageId()), cancelVolumetricResponse.getErrorFlag(),
					cancelVolumetricResponse.getErrorDescription());
		}
	}

	/**
	 * @param updateVolumetricResponse
	 */
	private void volumetricScannerUpdateResponseLog(UpdateVolWgtRequest updateVolumetricRequest) {
		LOGGER.warn("Update Volumetric Request After Parsing Availability - {}",
				Objects.isNull(updateVolumetricRequest) ? String.valueOf(false) : String.valueOf(true));
		//
		if (Objects.nonNull(updateVolumetricRequest)) {
			String messageResponse = (String) com.ngen.cosys.esb.jackson.util.JacksonUtility
					.convertObjectToXMLString(updateVolumetricRequest);
			LOGGER.warn("Update Voumetric Response for the Message Id - {} and Response XML String - {}",
					String.valueOf(updateVolumetricRequest.getMessageId()), messageResponse);
		}
	}

	/**
	 * @param messageId
	 * @param success
	 * @return
	 */
	@Override
	public String updateVolumetricResponse(long messageId, boolean success) throws CustomException {
		UpdateVolWgtResponse response = new UpdateVolWgtResponse();
		if (Objects.nonNull(messageId)) {
			response.setMessageId(messageId);
		}
		if (success) {
			response.setErrorFlag("N");
		} else {
			response.setErrorFlag("Y");
			response.setErrorDescription("Unable to process request");
		}
		String xmlResponse = (String) JacksonUtility.convertObjectToXMLString(response);
		return xmlResponse;
	}

	@Override
	public BigInteger getVolumetricScannerReferenceLogId(String shipmentNumber) throws CustomException {
		return volumetricScannerDAO.getVolumetricScannerReferenceLogId(shipmentNumber);
	}

	// public static synchronized String generateUUID() {
	// // Creates a new random (version 4) UUID
	// UUID tokenId = UUID.randomUUID();
	// MessageDigest salt = null;
	// try {
	// //
	// salt = MessageDigest.getInstance("SHA-256");
	// //
	// salt.update(tokenId.toString().getBytes("UTF-8"));
	// } catch (Exception e) {
	//
	// }
	// return bytesToHex(salt.digest());
	// }
	//
	// public static String bytesToHex(byte[] bytes) {
	// char[] hash = new char[bytes.length * 2];
	// for (int j = 0; j < bytes.length; j++) {
	// int v = bytes[j] & 0xFF;
	// hash[j * 2] = hexChars[v >>> 4];
	// hash[j * 2 + 1] = hexChars[v & 0x0F];
	// }
	// return new String(hash);
	// }

}
