package com.ngen.cosys.platform.rfid.tracker.interfaces.delegate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.platform.rfid.tracker.interfaces.client.service.PostHttpUtilitySearchCriteriaService;
import com.ngen.cosys.platform.rfid.tracker.interfaces.client.service.PostHttpUtilityService;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.ApiRequestModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AwbModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.SearchFilterModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.ULDModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.service.ImportFFMService;
import com.ngen.cosys.platform.rfid.tracker.interfaces.util.FormatUtils;
import com.ngen.cosys.platform.rfid.tracker.interfaces.util.SearchFilterUtils;
import com.ngen.cosys.timezone.util.TenantZoneTime;

@Service
public class ImportFFMJobDelegate {

	private static final Logger LOG = LoggerFactory.getLogger(ImportFFMJobDelegate.class);

	@Value("${tracer.platform.flight-ffm-endpoint}")
	private String flightFFMEndPoint;
	
	@Value("${tracer.platform.search-criteria}")
	private String searchCriteriaEndPoint;

	@Autowired
	private PostHttpUtilitySearchCriteriaService postHttpUtilitySearchCriteriaService;

	@Autowired
	private ImportFFMService importFFMService;

	@Autowired
	private PostHttpUtilityService postHttpUtilityService;

	public void getImportFFM() {
		AuthModel auth = null;
		try {

			auth = postHttpUtilityService.getAuthUserPassword();

			List<AwbModel> awbs = importFFMService.getImportFFMData(auth);

			LOG.info(new Date() + "=== " + "getImportFFMData===========================After " + awbs.size());
			if (awbs != null && !awbs.isEmpty()) {

//				List<String> tempList = new ArrayList<String>();
//				List<String> dupList = new ArrayList<String>();
				List<AwbModel> awbBOs = new ArrayList<AwbModel>();
//				List<AwbModel> awbBOs2 = new ArrayList<AwbModel>();
				
				int fromIter = 0;
				int toIter = 0;
				String lastRecFlag = "N";
				LocalDateTime dateNow = LocalDateTime.now();
				
				for (int i1 = 0; i1 <= awbs.size()/100; i1++) {
					fromIter = i1 * 100;
					toIter = i1 * 100 + 99; // since zero based array 0 to 99
 					for (int i2 = fromIter; i2 <= toIter; i2++) {
 						if(i2 >= awbs.size()) {
 							break;
 						}
						AwbModel obj = (AwbModel) awbs.get(i2);
						// if i2 between fromIter and toIter
//						if(i2 >= fromIter && i2 <= toIter) {
							awbBOs.add(obj);
//						}
					}
 					if (toIter >= awbs.size()){
 						lastRecFlag = "Y";
 					} else {
 						lastRecFlag = "N";
 					}
// 					if (awbBOs.size() > 100) {
						groupDataPush(awbBOs, 100, flightFFMEndPoint, auth, dateNow, lastRecFlag);
						awbBOs.clear();
//					}
				}

//				for (int i1 = 0; i1 < awbs.size(); i1++) {
//					AwbModel obj = (AwbModel) awbs.get(i1);
//					if (!FormatUtils.isEmpty(obj.getFlightKeyDate()) && !tempList.contains((obj.getFlightKeyDate()))) {
//						tempList.add((obj.getFlightKeyDate()));
//					} else if (!FormatUtils.isEmpty(obj.getFlightKeyDate())
//							&& !dupList.contains((obj.getFlightKeyDate()))) {
//						dupList.add((obj.getFlightKeyDate()));
//					}
//				}
//
//				List<ULDModel> uldbos = new ArrayList<ULDModel>();
//				AwbModel awbBO = new AwbModel();
//
//				for (int i = 0; i < dupList.size(); i++) {
//					awbBO = new AwbModel();
//					uldbos = new ArrayList<ULDModel>();
//					for (int i1 = 0; i1 < awbs.size(); i1++) {
//						AwbModel obj = (AwbModel) awbs.get(i1);
//
//						if (!FormatUtils.isEmpty(obj.getFlightKeyDate())
//								&& dupList.get(i).contains(obj.getFlightKeyDate())) {
//							awbBOs.add(obj);
//						}
//					}
//					if ((awbBOs.size() > 90) && (awbBOs.size() < 100)) {
//						groupDataPush(awbBOs, 100, flightFFMEndPoint, auth);
//						awbBOs.clear();
//					} else if (awbBOs.size() > 100) {
//						groupDataPush(awbBOs, 100, flightFFMEndPoint, auth);
//						awbBOs.clear();
//					}
//				}
//
//				if (awbBOs.size() != 0) {
//					groupDataPush(awbBOs, 100, flightFFMEndPoint, auth);
//					awbBOs.clear();
//				}
//
//				for (AwbModel obj : awbs) {
//					if (!FormatUtils.isEmpty(obj.getFlightKeyDate()) && tempList.contains(obj.getFlightKeyDate())) {
//						if (!dupList.contains(obj.getFlightKeyDate())) {
//							awbBOs2.add(obj);
//						}
//					}
//				}
//
//				groupDataPush(awbBOs2, 100, flightFFMEndPoint, auth);

				ApiRequestModel apiRequest = new ApiRequestModel();
				apiRequest.setAwbList(awbs);
				try {
					FormatUtils.handle(apiRequest, "FFM");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				LOG.error("No Record Found for   flight Manifest");
			}

		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void groupDataPush(List<AwbModel> awbs2, int groupNumber, String endpoint, AuthModel auth,
			LocalDateTime dateNow, String lastRecFlag)
			throws CustomException {
//		int arraySize = awbs2.size();
//		int count = 0;
		// ArrayList<ArrayList> result = new ArrayList<>();
		
		List<SearchFilterModel> searchFilterModels = postHttpUtilitySearchCriteriaService
				.onPostExecute(searchCriteriaEndPoint, auth);

		SearchFilterModel searchFilterModel = SearchFilterUtils.getSearchFilter(searchFilterModels);
		
//		ArrayList<AwbModel> subResult = new ArrayList<>();
//		for (int i = 0; i < arraySize; i++) {
//			if (count == groupNumber) {
				ApiRequestModel apiRequest = new ApiRequestModel();
				apiRequest.setAwbList(awbs2);
				apiRequest.setFromDate(TenantZoneTime.getZoneDateTime(dateNow.minusMinutes(searchFilterModel.getImportDataPushMinusFrom()),searchFilterModel.getTenantId()).toString());
				apiRequest.setToDate(TenantZoneTime.getZoneDateTime(dateNow.plusMinutes(searchFilterModel.getImportDataPushPlusTo()),searchFilterModel.getTenantId()).toString());
				//if(awbs2.size() == groupNumber) {
					apiRequest.setIsLastRecord(lastRecFlag);
				//}
				apiRequest.setStation("SATS");
				apiRequest.setFlightType("I");
//				apiRequest.setFromDate(TenantZoneTime.getZoneDateTime(LocalDateTime.now().minusMinutes(searchFilterModel.getImportDataPushMinusFrom()),searchFilterModel.getTenantId()).toString());
//				apiRequest.setToDate(TenantZoneTime.getZoneDateTime(LocalDateTime.now().plusMinutes(searchFilterModel.getImportDataPushPlusTo()),searchFilterModel.getTenantId()).toString());
//				if(awbs2.size() == groupNumber) {
//					apiRequest.setIsLastRecord("Y");
//				}
//				apiRequest.setStation("SATS");
//				apiRequest.setFlightType("I");
				LOG.info("ImportFFMJob count == groupNumber ============  " + " " + "AwbType" + " "
						+ awbs2.get(0).getStage() + " " + awbs2.size());
				postHttpUtilityService.onPostExecute(apiRequest, endpoint, auth);
//				count = 0;
//				// result.add(subResult);
//				subResult = new ArrayList<>();
//			}
//			subResult.add(awbs2.get(i));
//			count++;
//			if (count == arraySize) {
//				ApiRequestModel apiRequest = new ApiRequestModel();
//				apiRequest.setAwbList(subResult);
//				apiRequest.setFromDate(TenantZoneTime.getZoneDateTime(LocalDateTime.now().minusMinutes(searchFilterModel.getImportDataPushMinusFrom()), "SIN").toString());
//				apiRequest.setToDate(TenantZoneTime.getZoneDateTime(LocalDateTime.now().plusMinutes(searchFilterModel.getImportDataPushPlusTo()), "SIN").toString());
//				apiRequest.setIsLastRecord("Y");
//				apiRequest.setStation("SATS");
//				apiRequest.setFlightType("I");
//				LOG.info("ImportFFMJob count == arraySize ============  " + " " + "AwbType" + " "
//						+ subResult.get(0).getStage() + " " + subResult.size());
//				postHttpUtilityService.onPostExecute(apiRequest, endpoint, auth);
//				count = 0;
//			}
//		}
//
//		if (count != 0) {
//			ApiRequestModel apiRequest = new ApiRequestModel();
//			apiRequest.setAwbList(subResult);
//			LOG.info("ImportFFMJob count != 0 ============  " + " " + "AwbType" + " " + subResult.get(0).getStage()
//					+ " " + subResult.size());
//			postHttpUtilityService.onPostExecute(apiRequest, endpoint, auth);
//		}
	}

}
