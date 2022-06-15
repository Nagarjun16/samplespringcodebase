package com.ngen.cosys.platform.rfid.tracker.interfaces.job;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.platform.rfid.tracker.interfaces.client.service.PostHttpUtilityService;
import com.ngen.cosys.platform.rfid.tracker.interfaces.client.service.PostHttpUtilityServiceImpl;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.ApiRequestModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AwbModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.FlightLegModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.FlightsModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.ULDModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.service.FlightBookingService;
import com.ngen.cosys.platform.rfid.tracker.interfaces.service.FlightBookingServiceImpl;
import com.ngen.cosys.platform.rfid.tracker.interfaces.service.FlightManifestedService;
import com.ngen.cosys.platform.rfid.tracker.interfaces.service.FlightManifestedServiceImpl;
import com.ngen.cosys.platform.rfid.tracker.interfaces.service.FlightsService;
import com.ngen.cosys.platform.rfid.tracker.interfaces.service.FlightsServiceImpl;
import com.ngen.cosys.platform.rfid.tracker.interfaces.service.ImportFFMService;
import com.ngen.cosys.platform.rfid.tracker.interfaces.service.ImportFFMServiceImpl;
import com.ngen.cosys.platform.rfid.tracker.interfaces.util.FormatUtils;

public class TestJsonData {

	private static final Logger LOG = LoggerFactory.getLogger(TestJsonData.class);

	static List<FlightsModel> flights = new ArrayList<FlightsModel>();
	static List<AwbModel> awb = new ArrayList<AwbModel>();
 
	static FlightsService flightsService;
	static FlightBookingService flightBookingService;
	static FlightManifestedService flightManifestedService;
	static ImportFFMService importFFMService;
	static PostHttpUtilityService postHttpUtilityService;
	 	
	

	@Value("${tracer.platform.flight-export-endpoint}")
	private static String FLT;

	@Value("${tracer.platform.flight-booking-endpoint}")
	private static String BKG;

	@Value("${tracer.platform.flight-manifested-endpoint}")
	private static String MAN;

	@Value("${tracer.platform.flight-ffm-endpoint}")
	private static String FFM;

	public static void main(String[] args) throws Exception {

		flightsService = new FlightsServiceImpl();
		flightBookingService = new FlightBookingServiceImpl();
		flightManifestedService = new FlightManifestedServiceImpl();
		importFFMService = new ImportFFMServiceImpl();
		postHttpUtilityService = new PostHttpUtilityServiceImpl();
		
		String fileName = "";
		// SearchFilterBO searchFilterBO = new SearchFilterBO();

		// searchFilterService.getFlightFrequency8Hr(searchFilterBO);

		// List<SearchFilterBO> searchFilterBO1 = new ArrayList<SearchFilterBO>();
		// searchFilterBO = (SearchFilterBO)
		// SearchFilterUtils.getSearchFilter(searchFilterBO1);
		/*
		 * searchFilterBO = SearchFilterUtils
		 * .getSearchFilter((PostHttpUtilitySearchCriteria.onPostExecute(
		 * PropertiesCache.getInstance().getProperty(GlobalConstants.SEARCH_CRITERIA),
		 * DataSourceConfiguration.getStationName())));
		 */

		AuthModel auth = null;

		auth = postHttpUtilityService.getAuthUserPassword();

		flights = flightsService.getFlightData(auth);

		// LOG.info(System.getProperty("java.version"));

		LOG.info("flights" + flights.size());
		if (flights != null && !flights.isEmpty()) {
			/*
			 * ApiRequestBO apiRequest = new ApiRequestBO();
			 * apiRequest.setFlightList(flights); fileName = "FLIGHT"; handle(apiRequest,
			 * fileName); PostHttpUtility.onPostExecute(apiRequest,
			 * PropertiesCache.getInstance().getProperty(GlobalConstants.
			 * FLIGHT_EXPORT_ENDPOINT));
			 */

			List<String> tempList = new ArrayList<String>();
			List<String> dupList = new ArrayList<String>();
			List<FlightsModel> flightBOs = new ArrayList<FlightsModel>();
			List<FlightsModel> flightBOs2 = new ArrayList<FlightsModel>();
			List<FlightsModel> flightBOs3 = new ArrayList<FlightsModel>();
			int k1 = 0;
			int k2 = 0;
			int k3 = 0;
			for (int i1 = 0; i1 < flights.size(); i1++) {
				FlightsModel obj = (FlightsModel) flights.get(i1);
				if (!tempList.contains((obj.getFlightKey() + obj.getFlightOriginDate() + obj.getFlightType()))
						&& (obj.getFlightType()).equals("E")) {
					tempList.add((obj.getFlightKey() + obj.getFlightOriginDate() + obj.getFlightType()));
					k1++;
				} else if (!dupList.contains((obj.getFlightKey() + obj.getFlightOriginDate() + obj.getFlightType()))
						&& (obj.getFlightType()).equals("I")) {
					dupList.add((obj.getFlightKey() + obj.getFlightOriginDate() + obj.getFlightType()));
					k2++;
				}
			}

			List<FlightLegModel> flightLegBOs = new ArrayList<FlightLegModel>();
			FlightsModel flightBO = new FlightsModel();
			List<FlightsModel> awbBOs4 = new ArrayList<FlightsModel>();

			for (int i = 0; i < dupList.size(); i++) {
				flightBO = new FlightsModel();
				flightLegBOs = new ArrayList<FlightLegModel>();
				for (int i1 = 0; i1 < flights.size(); i1++) {
					FlightsModel obj = (FlightsModel) flights.get(i1);

					if (dupList.get(i).contains(obj.getFlightKey() + obj.getFlightOriginDate() + obj.getFlightType())) {
						flightBOs.add(obj);
						flightBOs3.add(obj);
					}
				}

				if ((flightBOs.size() > 90) && (flightBOs.size() <= 100)) {
					if (flightBOs3 != null && !flightBOs3.isEmpty() && flightBOs3.size() == k2) {
						flightBOs.get(flightBOs.size() - 1).setLastDataPush("LAST");
					}
					groupFlightDataPush(flightBOs, 100, FLT, auth);
					flightBOs.clear();
				} else if (flightBOs.size() >= 100) {
					if (flightBOs3 != null && !flightBOs3.isEmpty() && flightBOs3.size() == k2) {
						flightBOs.get(flightBOs.size() - 1).setLastDataPush("LAST");
					}
					groupFlightDataPush(flightBOs, 100, FLT, auth);
					flightBOs.clear();
				}
			}

			if (flightBOs.size() != 0) {
				if (flightBOs3 != null && !flightBOs3.isEmpty() && flightBOs3.size() == k2) {
					flightBOs.get(flightBOs.size() - 1).setLastDataPush("LAST");
				}
				groupFlightDataPush(flightBOs, 100, FLT, auth);
				flightBOs.clear();
			}

			for (FlightsModel obj : flights) {
				if (tempList.contains(obj.getFlightKey() + obj.getFlightOriginDate() + obj.getFlightType())) {
					if (!dupList.contains(obj.getFlightKey() + obj.getFlightOriginDate() + obj.getFlightType())) {
						flightBOs2.add(obj);
						k3++;
					}
				}
			}

			if ((flightBOs2.size() < 100)) {
				if (flightBOs2 != null && !flightBOs2.isEmpty() && flightBOs2.size() == k3) {
					flightBOs2.get(flightBOs2.size() - 1).setLastDataPush("LAST");
				}
				groupExpFlightDataPush(flightBOs2, 100, FLT, auth);
				flightBOs2.clear();
			} else if (flightBOs2.size() >= 100) {
				if (flightBOs2 != null && !flightBOs2.isEmpty() && flightBOs2.size() == k3) {
					flightBOs2.get(flightBOs2.size() - 1).setLastDataPush("LAST");
				}
				groupExpFlightDataPush(flightBOs2, 100, FLT, auth);
				flightBOs2.clear();
			}

		} else {
			LOG.error("No Record Found");
		}

		awb.clear();

		awb = flightBookingService.getFlightBookingData(auth);
		LOG.info("getFlightBookingData" + awb.size());
		if (awb != null && !awb.isEmpty()) {

			/*
			 * PostHttpUtility.onPostExecute(apiRequest,
			 * PropertiesCache.getInstance().getProperty(GlobalConstants.
			 * FLIGHT_BOOKING_ENDPOINT));
			 */

			List<String> tempList = new ArrayList<String>();
			List<String> dupList = new ArrayList<String>();
			List<AwbModel> awbBOs = new ArrayList<AwbModel>();
			List<AwbModel> awbBOs2 = new ArrayList<AwbModel>();

			for (int i1 = 0; i1 < awb.size(); i1++) {
				AwbModel obj = (AwbModel) awb.get(i1);
				if (!FormatUtils.isEmpty(obj.getFlightKeyDate()) && !tempList.contains((obj.getFlightKeyDate()))) {
					tempList.add((obj.getFlightKeyDate()));
				} else if (!FormatUtils.isEmpty(obj.getFlightKeyDate())
						&& !dupList.contains((obj.getFlightKeyDate()))) {
					dupList.add((obj.getFlightKeyDate()));
				}
			}

			List<ULDModel> uldbos = new ArrayList<ULDModel>();
			AwbModel awbBO = new AwbModel();

			for (int i = 0; i < dupList.size(); i++) {
				awbBO = new AwbModel();
				uldbos = new ArrayList<ULDModel>();
				for (int i1 = 0; i1 < awb.size(); i1++) {
					AwbModel obj = (AwbModel) awb.get(i1);

					if (!FormatUtils.isEmpty(obj.getFlightKeyDate())
							&& dupList.get(i).contains(obj.getFlightKeyDate())) {
						awbBOs.add(obj);
					}
				}
				if ((awbBOs.size() > 90) && (awbBOs.size() < 100)) {
					groupDataPush(awbBOs, 100, BKG, auth);
					awbBOs.clear();
				} else if (awbBOs.size() > 100) {
					groupDataPush(awbBOs, 100, BKG, auth);
					awbBOs.clear();
				}
			}

			if (awbBOs.size() != 0) {
				groupDataPush(awbBOs, 100, BKG, auth);
				awbBOs.clear();
			}

			for (AwbModel obj : awb) {
				if (!FormatUtils.isEmpty(obj.getFlightKeyDate()) && tempList.contains(obj.getFlightKeyDate())) {
					if (!dupList.contains(obj.getFlightKeyDate())) {
						awbBOs2.add(obj);
					}
				}
			}

			groupDataPush(awbBOs2, 100, BKG, auth);

			ApiRequestModel apiRequest = new ApiRequestModel();
			apiRequest.setAwbList(awb);
			fileName = "BOOKING";
			handle(apiRequest, fileName);

		} else {
			LOG.error("No Record Found");
		}

		awb.clear();
		awb = flightManifestedService.getFlightManifestedData(auth);
		LOG.info("getFlightManifestedData" + awb.size());
		if (awb != null && !awb.isEmpty()) {
			/*
			 * ApiRequestBO apiRequest = new ApiRequestBO(); apiRequest.setAwbList(awb);
			 * fileName = "MANIFEST"; handle(apiRequest, fileName);
			 * PostHttpUtility.onPostExecute(apiRequest,
			 * PropertiesCache.getInstance().getProperty(GlobalConstants.
			 * FLIGHT_MANIFESTED_ENDPOINT));
			 */

			List<String> tempList = new ArrayList<String>();
			List<String> dupList = new ArrayList<String>();
			List<AwbModel> awbBOs = new ArrayList<AwbModel>();
			List<AwbModel> awbBOs2 = new ArrayList<AwbModel>();

			for (int i1 = 0; i1 < awb.size(); i1++) {
				AwbModel obj = (AwbModel) awb.get(i1);
				if (!FormatUtils.isEmpty(obj.getFlightKeyDate()) && !tempList.contains((obj.getFlightKeyDate()))) {
					tempList.add((obj.getFlightKeyDate()));
				} else if (!FormatUtils.isEmpty(obj.getFlightKeyDate())
						&& !dupList.contains((obj.getFlightKeyDate()))) {
					dupList.add((obj.getFlightKeyDate()));
				}
			}

			List<ULDModel> uldbos = new ArrayList<ULDModel>();
			AwbModel awbBO = new AwbModel();

			for (int i = 0; i < dupList.size(); i++) {
				awbBO = new AwbModel();
				uldbos = new ArrayList<ULDModel>();
				for (int i1 = 0; i1 < awb.size(); i1++) {
					AwbModel obj = (AwbModel) awb.get(i1);

					if (!FormatUtils.isEmpty(obj.getFlightKeyDate())
							&& dupList.get(i).contains(obj.getFlightKeyDate())) {
						awbBOs.add(obj);
					}
				}
				if ((awbBOs.size() > 90) && (awbBOs.size() < 100)) {
					groupDataPush(awbBOs, 100, MAN, auth);
					awbBOs.clear();
				} else if (awbBOs.size() > 100) {
					groupDataPush(awbBOs, 100, MAN, auth);
					awbBOs.clear();
				}
			}

			if (awbBOs.size() != 0) {
				groupDataPush(awbBOs, 100, MAN, auth);
				awbBOs.clear();
			}

			for (AwbModel obj : awb) {
				if (!FormatUtils.isEmpty(obj.getFlightKeyDate()) && tempList.contains(obj.getFlightKeyDate())) {
					if (!dupList.contains(obj.getFlightKeyDate())) {
						awbBOs2.add(obj);
					}
				}
			}

			groupDataPush(awbBOs2, 100, MAN, auth);

			ApiRequestModel apiRequest = new ApiRequestModel();
			apiRequest.setAwbList(awb);
			fileName = "MANIFEST";
			handle(apiRequest, fileName);

		} else {
			LOG.error("No Record Found");
		}

		awb.clear();
		awb = importFFMService.getImportFFMData(auth);
		LOG.info("getImportFFMData" + awb.size());
		if (awb != null && !awb.isEmpty()) {
			/*
			 * ApiRequestBO apiRequest = new ApiRequestBO(); apiRequest.setAwbList(awb);
			 * fileName = "FFM"; handle(apiRequest, fileName);
			 * PostHttpUtility.onPostExecute(apiRequest,
			 * PropertiesCache.getInstance().getProperty(GlobalConstants.FLIGHT_FFM_ENDPOINT
			 * ));
			 */

			List<String> tempList = new ArrayList<String>();
			List<String> dupList = new ArrayList<String>();
			List<AwbModel> awbBOs = new ArrayList<AwbModel>();
			List<AwbModel> awbBOs2 = new ArrayList<AwbModel>();

			for (int i1 = 0; i1 < awb.size(); i1++) {
				AwbModel obj = (AwbModel) awb.get(i1);
				if (!FormatUtils.isEmpty(obj.getFlightKeyDate()) && !tempList.contains((obj.getFlightKeyDate()))) {
					tempList.add((obj.getFlightKeyDate()));
				} else if (!FormatUtils.isEmpty(obj.getFlightKeyDate())
						&& !dupList.contains((obj.getFlightKeyDate()))) {
					dupList.add((obj.getFlightKeyDate()));
				}
			}

			List<ULDModel> uldbos = new ArrayList<ULDModel>();
			AwbModel awbBO = new AwbModel();

			for (int i = 0; i < dupList.size(); i++) {
				awbBO = new AwbModel();
				uldbos = new ArrayList<ULDModel>();
				for (int i1 = 0; i1 < awb.size(); i1++) {
					AwbModel obj = (AwbModel) awb.get(i1);

					if (!FormatUtils.isEmpty(obj.getFlightKeyDate())
							&& dupList.get(i).contains(obj.getFlightKeyDate())) {
						awbBOs.add(obj);
					}
				}
				if ((awbBOs.size() > 90) && (awbBOs.size() < 100)) {
					groupDataPush(awbBOs, 100, FFM, auth);
					awbBOs.clear();
				} else if (awbBOs.size() > 100) {
					groupDataPush(awbBOs, 100, FFM, auth);
					awbBOs.clear();
				}
			}

			if (awbBOs.size() != 0) {
				groupDataPush(awbBOs, 100, FFM, auth);
				awbBOs.clear();
			}

			for (AwbModel obj : awb) {
				if (!FormatUtils.isEmpty(obj.getFlightKeyDate()) && tempList.contains(obj.getFlightKeyDate())) {
					if (!dupList.contains(obj.getFlightKeyDate())) {
						awbBOs2.add(obj);
					}
				}
			}

			groupDataPush(awbBOs2, 100, FFM, auth);

			ApiRequestModel apiRequest = new ApiRequestModel();
			apiRequest.setAwbList(awb);
			fileName = "FFM";
			handle(apiRequest, fileName);

		} else {
			LOG.error("No Record Found");
		}

	}

	public static void groupFlightDataPush(List<FlightsModel> flightBOs, int groupNumber, String endpoint, AuthModel auth)
			throws IOException, CustomException {
		int arraySize = flightBOs.size();
		int count = 0;
		// ArrayList<ArrayList> result = new ArrayList<>();
		ArrayList<FlightsModel> subResult = new ArrayList<>();
		for (int i = 0; i < arraySize; i++) {
			if (count == groupNumber) {
				ApiRequestModel apiRequest = new ApiRequestModel();
				apiRequest.setFlightList(subResult);
				apiRequest.setFromDate(flightBOs.get(0).getFromDate());
				apiRequest.setToDate(flightBOs.get(0).getToDate());
				apiRequest.setStation(flightBOs.get(0).getStation());
				apiRequest.setFlightType(flightBOs.get(0).getFlightType());
				if (!FormatUtils.isEmpty(flightBOs.get(i).getLastDataPush())) {
					apiRequest.setIsLastRecord("Y");
					handle(apiRequest, "IMPFLIGHT");
				}
				LOG.info("FlightJob count == groupNumber ============  " + " " + "flightType" + " "
						+ apiRequest.getFlightType() + "IsLastRecord" + " " + apiRequest.getIsLastRecord() + " "
						+ subResult.size());
				postHttpUtilityService.onPostExecute(apiRequest, endpoint, auth);

				count = 0;
				// result.add(subResult);
				subResult = new ArrayList<>();
			}
			subResult.add(flightBOs.get(i));
			count++;
			if (count == arraySize) {
				ApiRequestModel apiRequest = new ApiRequestModel();
				apiRequest.setFlightList(subResult);
				apiRequest.setFromDate(flightBOs.get(0).getFromDate());
				apiRequest.setToDate(flightBOs.get(0).getToDate());

				apiRequest.setStation(flightBOs.get(0).getStation());
				apiRequest.setFlightType(flightBOs.get(0).getFlightType());
				if (!FormatUtils.isEmpty(flightBOs.get(i).getLastDataPush())) {
					apiRequest.setIsLastRecord("Y");
					handle(apiRequest, "IMPFLIGHT");
				}
				LOG.info("FlightJob count == arraySize ============  " + " " + "flightType" + " "
						+ apiRequest.getFlightType() + "IsLastRecord" + " " + apiRequest.getIsLastRecord() + " "
						+ subResult.size());
				postHttpUtilityService.onPostExecute(apiRequest, endpoint, auth);
				count = 0;
			}
		}

		if (count != 0) {
			ApiRequestModel apiRequest = new ApiRequestModel();
			apiRequest.setFlightList(subResult);
			apiRequest.setFromDate(flightBOs.get(0).getFromDate());
			apiRequest.setToDate(flightBOs.get(0).getToDate());

			apiRequest.setStation(flightBOs.get(0).getStation());
			apiRequest.setFlightType(flightBOs.get(0).getFlightType());

			for (int i = 0; i < subResult.size(); i++) {
				if (!FormatUtils.isEmpty(subResult.get(i).getLastDataPush())) {
					apiRequest.setIsLastRecord("Y");
					handle(apiRequest, "IMPFLIGHT");
				}
			}
			LOG.info("FlightJob count != 0 ============  " + " " + "flightType" + " " + apiRequest.getFlightType()
					+ "IsLastRecord" + " " + apiRequest.getIsLastRecord() + " " + subResult.size());
			postHttpUtilityService.onPostExecute(apiRequest, endpoint, auth);
		}
	}

	public static void groupExpFlightDataPush(List<FlightsModel> flightBOs, int groupNumber, String endpoint, AuthModel auth)
			throws IOException, CustomException {
		int arraySize = flightBOs.size();
		int count = 0;
		// ArrayList<ArrayList> result = new ArrayList<>();
		ArrayList<FlightsModel> subResult = new ArrayList<>();
		for (int i = 0; i < arraySize; i++) {
			if (count == groupNumber) {
				ApiRequestModel apiRequest = new ApiRequestModel();
				apiRequest.setFlightList(subResult);
				apiRequest.setFromDate(flightBOs.get(0).getFromDate());
				apiRequest.setToDate(flightBOs.get(0).getToDate());
				apiRequest.setStation(flightBOs.get(0).getStation());
				apiRequest.setFlightType(flightBOs.get(0).getFlightType());
				if (!FormatUtils.isEmpty(flightBOs.get(i).getLastDataPush())) {
					apiRequest.setIsLastRecord("Y");
					handle(apiRequest, "EXPFLIGHT");
				}
				LOG.info("FlightJob count == groupNumber ============  " + " " + "flightType" + " "
						+ apiRequest.getFlightType() + "IsLastRecord" + " " + apiRequest.getIsLastRecord() + " "
						+ subResult.size());
				postHttpUtilityService.onPostExecute(apiRequest, endpoint, auth);

				count = 0;
				// result.add(subResult);
				subResult = new ArrayList<>();
			}
			subResult.add(flightBOs.get(i));
			count++;
			if (count == arraySize) {
				ApiRequestModel apiRequest = new ApiRequestModel();
				apiRequest.setFlightList(subResult);
				apiRequest.setFromDate(flightBOs.get(0).getFromDate());
				apiRequest.setToDate(flightBOs.get(0).getToDate());

				apiRequest.setStation(flightBOs.get(0).getStation());
				apiRequest.setFlightType(flightBOs.get(0).getFlightType());
				if (!FormatUtils.isEmpty(flightBOs.get(i).getLastDataPush())) {
					apiRequest.setIsLastRecord("Y");
					handle(apiRequest, "EXPFLIGHT");
				}
				LOG.info("FlightJob count == arraySize ============  " + " " + "flightType" + " "
						+ apiRequest.getFlightType() + "IsLastRecord" + " " + apiRequest.getIsLastRecord() + " "
						+ subResult.size());
				postHttpUtilityService.onPostExecute(apiRequest, endpoint, auth);
				count = 0;
			}
		}

		if (count != 0) {
			ApiRequestModel apiRequest = new ApiRequestModel();
			apiRequest.setFlightList(subResult);
			apiRequest.setFromDate(flightBOs.get(0).getFromDate());
			apiRequest.setToDate(flightBOs.get(0).getToDate());

			apiRequest.setStation(flightBOs.get(0).getStation());
			apiRequest.setFlightType(flightBOs.get(0).getFlightType());

			for (int i = 0; i < subResult.size(); i++) {
				if (!FormatUtils.isEmpty(subResult.get(i).getLastDataPush())) {
					apiRequest.setIsLastRecord("Y");
					handle(apiRequest, "EXPFLIGHT");
				}
			}
			LOG.info("FlightJob count != 0 ============  " + " " + "flightType" + " " + apiRequest.getFlightType()
					+ "IsLastRecord" + " " + apiRequest.getIsLastRecord() + " " + subResult.size());
			postHttpUtilityService.onPostExecute(apiRequest, endpoint, auth);
		}
	}

	public static void groupDataPush(List<AwbModel> awbs2, int groupNumber, String endpoint, AuthModel auth)
			throws IOException, CustomException {
		int arraySize = awbs2.size();
		int count = 0;
		// ArrayList<ArrayList> result = new ArrayList<>();
		ArrayList<AwbModel> subResult = new ArrayList<>();
		for (int i = 0; i < arraySize; i++) {
			if (count == groupNumber) {
				ApiRequestModel apiRequest = new ApiRequestModel();
				apiRequest.setAwbList(subResult);
				LOG.info("count == groupNumber ============  " + " " + "AwbType" + " " + subResult.get(0).getStage()
						+ " " + subResult.size());
				postHttpUtilityService.onPostExecute(apiRequest, endpoint, auth);
				count = 0;
				// result.add(subResult);
				subResult = new ArrayList<>();
			}
			subResult.add(awbs2.get(i));
			count++;
			if (count == arraySize) {
				ApiRequestModel apiRequest = new ApiRequestModel();
				apiRequest.setAwbList(subResult);
				LOG.info("count == arraySize ============  " + " " + "AwbType" + " " + subResult.get(0).getStage() + " "
						+ subResult.size());
				postHttpUtilityService.onPostExecute(apiRequest, endpoint, auth);
				count = 0;
			}
		}

		if (count != 0) {
			ApiRequestModel apiRequest = new ApiRequestModel();
			apiRequest.setAwbList(subResult);
			LOG.info("count != 0 ============  " + " " + "AwbType" + " " + subResult.get(0).getStage() + " "
					+ subResult.size());
			postHttpUtilityService.onPostExecute(apiRequest, endpoint, auth);
		}
	}

	public static String handle(ApiRequestModel apiRequest, String fileName) throws IOException {
		String jsonOutput = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			jsonOutput = objectMapper.writeValueAsString(apiRequest);
			objectMapper.writeValue(new File("D:\\Prithvi\\JSON\\" + fileName + ".json"), apiRequest);
			// objectMapper.writeValue(new File("C:\\" + fileName + ".json"), apiRequest);
			LOG.debug("JSON response for outbound request", jsonOutput);
		} catch (JsonProcessingException jsonProcessingException) {
			LOG.error("JsonProcessingException while writing response object to json format", jsonProcessingException);
		}
		return jsonOutput;
	}

}
