package com.ngen.cosys.platform.rfid.tracker.interfaces.delegate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.platform.rfid.tracker.interfaces.client.service.PostHttpUtilityService;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.ApiRequestModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.FlightLegModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.FlightsModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.service.FlightsService;
import com.ngen.cosys.platform.rfid.tracker.interfaces.util.FormatUtils;

@Service
public class FlightDelegate {

	private static final Logger LOG = LoggerFactory.getLogger(FlightDelegate.class);

	@Value("${tracer.platform.flight-export-endpoint}")
	private String flightEndPoint;

	@Autowired
	private FlightsService flightsService;

	@Autowired
	private PostHttpUtilityService postHttpUtilityService;

	public void getFlights() {
		AuthModel auth = null;
		try {

			auth = postHttpUtilityService.getAuthUserPassword();

			// getting import flight data
			List<FlightsModel> flights = flightsService.getFlightData(auth);

			LOG.info(new Date() + "=== " + "flightsService===========================After  " + flights.size());

			if (flights != null && !flights.isEmpty()) {

				List<String> tempList = new ArrayList<String>();
				List<String> dupList = new ArrayList<String>();
				List<FlightsModel> flightBOs = new ArrayList<FlightsModel>();
				List<FlightsModel> flightBOs2 = new ArrayList<FlightsModel>();
				List<FlightsModel> flightBOs3 = new ArrayList<FlightsModel>();
//				int k1 = 0;
				int k2 = 0;
				int k3 = 0;
				for (int i1 = 0; i1 < flights.size(); i1++) {
					FlightsModel obj = (FlightsModel) flights.get(i1);
					if (!tempList.contains((obj.getFlightKey() + obj.getFlightOriginDate() + obj.getFlightType()))
							&& (obj.getFlightType()).equals("E")) {
						tempList.add((obj.getFlightKey() + obj.getFlightOriginDate() + obj.getFlightType()));
//						k1++;
					} else if (!dupList.contains((obj.getFlightKey() + obj.getFlightOriginDate() + obj.getFlightType()))
							&& (obj.getFlightType()).equals("I")) {
						dupList.add((obj.getFlightKey() + obj.getFlightOriginDate() + obj.getFlightType()));
						k2++;
					}
				}

//				List<FlightLegModel> flightLegBOs = new ArrayList<FlightLegModel>();
//				FlightsModel flightBO = new FlightsModel();

				for (int i = 0; i < dupList.size(); i++) {
//					flightBO = new FlightsModel();
//					flightLegBOs = new ArrayList<FlightLegModel>();
					for (int i1 = 0; i1 < flights.size(); i1++) {
						FlightsModel obj = (FlightsModel) flights.get(i1);

						if (dupList.get(i)
								.contains(obj.getFlightKey() + obj.getFlightOriginDate() + obj.getFlightType())) {
							flightBOs.add(obj);
							flightBOs3.add(obj);
						}
					}

					if ((flightBOs.size() > 90) && (flightBOs.size() <= 100)) {
						if (flightBOs3 != null && !flightBOs3.isEmpty() && flightBOs3.size() == k2) {
							flightBOs.get(flightBOs.size() - 1).setLastDataPush("LAST");
						}
						groupFlightDataPush2(flightBOs, 100, flightEndPoint, auth);
						flightBOs.clear();
					} else if (flightBOs.size() >= 100) {
						if (flightBOs3 != null && !flightBOs3.isEmpty() && flightBOs3.size() == k2) {
							flightBOs.get(flightBOs.size() - 1).setLastDataPush("LAST");
						}
						groupFlightDataPush2(flightBOs, 100, flightEndPoint, auth);
						flightBOs.clear();
					}
				}

				if (flightBOs.size() != 0) {
					if (flightBOs3 != null && !flightBOs3.isEmpty() && flightBOs3.size() == k2) {
						flightBOs.get(flightBOs.size() - 1).setLastDataPush("LAST");
					}
					groupFlightDataPush2(flightBOs, 100, flightEndPoint, auth);
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
					groupFlightDataPush2(flightBOs2, 100, flightEndPoint, auth);
					flightBOs2.clear();
				} else if (flightBOs2.size() >= 100) {
					if (flightBOs2 != null && !flightBOs2.isEmpty() && flightBOs2.size() == k3) {
						flightBOs2.get(flightBOs2.size() - 1).setLastDataPush("LAST");
					}
					groupFlightDataPush2(flightBOs2, 100, flightEndPoint, auth);
					flightBOs2.clear();
				}

				ApiRequestModel apiRequest = new ApiRequestModel();
				apiRequest.setFlightList(flights);
				apiRequest.setFromDate(flights.get(0).getFromDate());
				apiRequest.setToDate(flights.get(0).getToDate());
				apiRequest.setStation(flights.get(0).getStation());
				apiRequest.setFlightType(flights.get(0).getFlightType());
				try {
					FormatUtils.handle(apiRequest, "FLIGHT");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				LOG.error("No Record Found for import/Export flight");
			}

		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void groupFlightDataPush2(List<FlightsModel> flightBOs, int groupNumber, String endpoint, AuthModel auth)
			throws CustomException {
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
				if (!FormatUtils.isEmpty(flightBOs.get(i).getLastDataPush())) {
					apiRequest.setIsLastRecord("N");
				}
				apiRequest.setStation(flightBOs.get(0).getStation());
				apiRequest.setFlightType(flightBOs.get(0).getFlightType());
				LOG.info("FlightJob count == groupNumber ============  " + " " + "IsLastRecord" + " "
						+ apiRequest.getIsLastRecord() + " " + "flightType" + " " + apiRequest.getFlightType() + " "
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
				if (!FormatUtils.isEmpty(flightBOs.get(i).getLastDataPush())) {
					apiRequest.setIsLastRecord("N");
				}
				apiRequest.setStation(flightBOs.get(0).getStation());
				apiRequest.setFlightType(flightBOs.get(0).getFlightType());
				LOG.info("FlightJob count == arraySize ============  " + " " + "IsLastRecord" + " "
						+ apiRequest.getIsLastRecord() + " " + "flightType" + " " + apiRequest.getFlightType() + " "
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
			for (int i = 0; i < subResult.size(); i++) {
				if (!FormatUtils.isEmpty(subResult.get(i).getLastDataPush())) {
					apiRequest.setIsLastRecord("Y");
				}
			}
			apiRequest.setStation(flightBOs.get(0).getStation());
			apiRequest.setFlightType(flightBOs.get(0).getFlightType());
			LOG.info("FlightJob count != 0 ============  " + " " + "IsLastRecord" + " " + apiRequest.getIsLastRecord()
					+ " " + "flightType" + " " + apiRequest.getFlightType() + " " + subResult.size());
			postHttpUtilityService.onPostExecute(apiRequest, endpoint, auth);
		}

	}

}
