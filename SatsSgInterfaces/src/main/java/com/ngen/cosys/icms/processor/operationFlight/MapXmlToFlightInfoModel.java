package com.ngen.cosys.icms.processor.operationFlight;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ngen.cosys.icms.exception.MessageParseException;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightSegmentInfo;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightInfo;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightLegInfo;
import com.ngen.cosys.icms.schema.operationFlight.AirCraftdetails;
import com.ngen.cosys.icms.schema.operationFlight.FlightLegDetails;
import com.ngen.cosys.icms.schema.operationFlight.FlightOperation;
import com.ngen.cosys.icms.schema.operationFlight.FlightSegmentDetails;
import com.ngen.cosys.icms.schema.operationFlight.OperationalFlightPublish;
import com.ngen.cosys.icms.util.CommonUtil;
import com.ngen.cosys.icms.util.ValidationConstant;

/**
 * This class used for map required fields from xml to Flight Model
 */

@Component
public class MapXmlToFlightInfoModel {

	@Autowired
	CommonUtil commonUtil;

	private static final Logger LOGGER = LoggerFactory.getLogger(OperationalFlightProcessor.class);

	public OperationalFlightInfo createOperationalFlightInfoModel(OperationalFlightPublish operationalFlightPublish)
			throws ParseException {
		LOGGER.info("start mapXmlValuesToFlightModel method ::");
		OperationalFlightInfo flightInfo = new OperationalFlightInfo();
		String fromFormat = ValidationConstant.XML_DATE_FORMAT;
		String toFormat = ValidationConstant.DATE_FORMAT;
		FlightOperation flightOperation = operationalFlightPublish.getObjEntity().getPublishData().getFlightoperation();
		int segmentOrder = 0;
		// map flightDetails
		flightInfo.setCarrier(flightOperation.getCarrierCode().trim());
		flightInfo.setFlightNo(commonUtil.validateFlightNumber(flightOperation.getFlightNumber()));
		flightInfo.setFlightKey(flightOperation.getCarrierCode() + flightOperation.getFlightNumber());
		flightInfo.setLoadingPoint(flightOperation.getFlightOrigin());
		flightInfo.setFirstPointArrival(flightOperation.getFlightDestination());
		String flightDate = commonUtil.convertDateString(flightOperation.getFlightDate(), fromFormat, toFormat);
		flightInfo.setFlightDate(commonUtil.convertStringToLocalDate(flightDate, toFormat));
		flightInfo.setDateSTD(flightOperation.getDateSTD());
		flightInfo.setDateSTA(flightOperation.getDateSTA());
		flightInfo.setFlightStatus(flightOperation.getFlightStatus());
		if (flightOperation.getFlightStatus().equals(ValidationConstant.CANCEL)) {
			flightInfo.setFlightCancelFag(ValidationConstant.FLIGHT_CANCEL_FLAG);
		} else {
			flightInfo.setFlightCancelFag(ValidationConstant.FLIGHT_ACTIVE_FLAG);
		}
		if (flightOperation.getFlightOrigin().equals(ValidationConstant.PORT_SIN)) {
			flightInfo.setInboundAircraftRegNo(flightOperation.getAircraftRegNo());
		} else if (flightOperation.getFlightDestination().equals(ValidationConstant.PORT_SIN)) {
			flightInfo.setOutboundAircraftRegNo(flightOperation.getAircraftRegNo());
		}
		flightInfo.setFlightRemarks(flightOperation.getFlightRemarks());
		flightInfo.setFlightType(flightOperation.getFlightType());

		// map flight Segment Details
		mapFlightSegmentDetails(operationalFlightPublish, flightInfo, segmentOrder);

		// map flight Leg Details
		mapFlightLegDetails(operationalFlightPublish, flightInfo, segmentOrder);
		LOGGER.info("End mapXmlValuesToFlightModel method ::");
		return flightInfo;
	}

	private void mapFlightSegmentDetails(OperationalFlightPublish operationalFlightPublish,
			OperationalFlightInfo flightInfo, int segmentOrder) throws ParseException {
		FlightOperation flightOperation = operationalFlightPublish.getObjEntity().getPublishData().getFlightoperation();
		List<FlightSegmentDetails> flightSegmentList = operationalFlightPublish.getObjEntity().getPublishData()
				.getFlightoperation().getFlightSegmentDetails();
		List<OperationalFlightSegmentInfo> newFlightSegmentList = new ArrayList<OperationalFlightSegmentInfo>();
		for (FlightSegmentDetails flightSegmentInfo : flightSegmentList) {
			segmentOrder++;
			OperationalFlightSegmentInfo segmentInfo = new OperationalFlightSegmentInfo();
			segmentInfo.setSegmentOrigin(flightSegmentInfo.getSegmentOrigin());
			segmentInfo.setSegmentDestination(flightSegmentInfo.getSegmentDestination());
			if (commonUtil.isValidDate(flightSegmentInfo.getArrivalTime(), ValidationConstant.XML_DATETIME_FORMAT)) {
				segmentInfo.setDateSTA(flightSegmentInfo.getArrivalTime());
			} else {
				throw new MessageParseException("Invalid STA date");
			}
			if (commonUtil.isValidDate(flightSegmentInfo.getDepartureTime(), ValidationConstant.XML_DATETIME_FORMAT)) {
				segmentInfo.setDateSTD(flightSegmentInfo.getDepartureTime());
			} else {
				throw new MessageParseException("Invalid STD date");
			}
//			if (flightOperation.getFlightOrigin().equals(flightSegmentInfo.getSegmentOrigin())
//					&& flightOperation.getFlightDestination().equals(flightSegmentInfo.getSegmentDestination())) {
				
			segmentInfo.setSegmentOrder(segmentOrder);
			newFlightSegmentList.add(segmentInfo);
		}
		if (ValidationConstant.OPEN_FOR_BOOKING.equalsIgnoreCase(flightSegmentList.get(0).getSegmentStatus())) {
			flightInfo.setClosedForBooking(ValidationConstant.ZERO);
		} else if (ValidationConstant.CLOSED_FOR_BOOKING.equalsIgnoreCase(flightSegmentList.get(0).getSegmentStatus())) {
			flightInfo.setClosedForBooking(ValidationConstant.ONE);
		}
	
		flightInfo.setFlightSegmentInfo(newFlightSegmentList);
	}

	private void mapFlightLegDetails(OperationalFlightPublish operationalFlightPublish,
			OperationalFlightInfo flightInfo, int segmentOrder) throws ParseException {
		List<FlightLegDetails> flightLegList = operationalFlightPublish.getObjEntity().getPublishData()
				.getFlightoperation().getFlightLegDetails();
		List<OperationalFlightLegInfo> newFlightList = new ArrayList<OperationalFlightLegInfo>();
		for (FlightLegDetails flightLegInfo : flightLegList) {
			segmentOrder++;
			OperationalFlightLegInfo legInfo = new OperationalFlightLegInfo();
			legInfo.setLegOrigin(flightLegInfo.getLegOrigin());
			legInfo.setLegDestination(flightLegInfo.getLegDestination());
			legInfo.setFlightSegmentOrder(segmentOrder);
			if (commonUtil.isValidDate(flightLegInfo.getSTA(), ValidationConstant.XML_DATETIME_FORMAT)) {
				legInfo.setDateSTA(flightLegInfo.getSTA());
			} else {
				throw new MessageParseException("Invalid STA date");
			}
			if (commonUtil.isValidDate(flightLegInfo.getSTD(), ValidationConstant.XML_DATETIME_FORMAT)) {
				legInfo.setDateSTD(flightLegInfo.getSTD());
			} else {
				throw new MessageParseException("Invalid STD date");
			}

			AirCraftdetails aircraft = flightLegInfo.getLegCapacityDetails().getAircraftDetails();
			legInfo.setAircraftType(aircraft.getAirCraftTypeCode());
			legInfo.setServiceType(aircraft.getServiceType());
			flightInfo.setServiceType(aircraft.getServiceType());
			flightInfo.setAircraftType(aircraft.getAirCraftTypeCode());
			newFlightList.add(legInfo);
		}
		flightInfo.setFlightLegInfo(newFlightList);
	}

}
