package com.ngen.cosys.icms.processor.flightbooking;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.icms.dao.flightbooking.FlightBookingDao;
import com.ngen.cosys.icms.model.BookingInterface.SHCDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightBookingDetails;
import com.ngen.cosys.icms.util.BookingConstant;
import com.ngen.cosys.processing.engine.rule.executor.RuleExecutor;
import com.ngen.cosys.processing.engine.rule.fact.FactFlight;
import com.ngen.cosys.processing.engine.rule.fact.FactPayload;
import com.ngen.cosys.processing.engine.rule.fact.FactShipment;
import com.ngen.cosys.processing.engine.rule.fact.FactShipmentSHC;
import com.ngen.cosys.processing.engine.rule.triggerpoint.InboundMessage;

@Component
public class ValidateEmbargoRule {
	
	@Autowired
	private RuleExecutor ruleExecutor;
	
	@Autowired
	FlightBookingDao flightBookingDao;
	
	public void methodToValidateEmbargoRules(ShipmentBookingDetails booking, ShipmentFlightBookingDetails flight,
			 List<SHCDetails> shcDetailsList) throws CustomException {

	      List<FactFlight> factFlightList = new ArrayList<>();
	      List<FactShipmentSHC> factShcList = new ArrayList<>();
	      Map<String, Object> queryParam = new HashMap<>();
	      queryParam.put("tenantAirport", BookingConstant.TENANT_SINGAPORE);
	      queryParam.put("flightId",flight.getFlightId());

	      FactFlight factFlight = flightBookingDao.getFactDetails(queryParam);
	      flight.setFactFlightDetails(factFlight);
	      if (flight.getFactFlightDetails() != null) {
	         flight.getFactFlightDetails().setFlightKey(flight.getFlightKey());
	         flight.getFactFlightDetails().setShipmentDate(booking.getShipmentDate());
	         flight.getFactFlightDetails().setShipmentNumber(booking.getShipmentNumber());
	         flight.getFactFlightDetails().setFlightOriginDate(flight.getFlightOriginDate());
	         flight.getFactFlightDetails().setOffPoint(flight.getFlightOffPoint());
	         flight.getFactFlightDetails().setBoardingPoint(flight.getFlightBoardPoint());
	         flight.getFactFlightDetails().setFlightId(BigInteger.valueOf(flight.getFlightId()));
	         factFlightList.add(flight.getFactFlightDetails());
	         booking.setCarrierCode(flight.getFactFlightDetails().getFromAirline());
	         if (!CollectionUtils.isEmpty(shcDetailsList)) {
	            for (SHCDetails shc : shcDetailsList) {
	               List<String> shcGroupCode = flightBookingDao.fetchShcGroupCode(shc.getSpecialHandlingCode());
	               if (!CollectionUtils.isEmpty(shcGroupCode)) {
	                  // fetch shcGroupCode
	                  for (String s : shcGroupCode) {
	                     FactShipmentSHC factShc = new FactShipmentSHC();
	                     factShc.setShipmentDate(booking.getShipmentDate());
	                     factShc.setShipmentNumber(booking.getShipmentNumber());
	                     factShc.setSpecialHandlingCode(shc.getSpecialHandlingCode());
	                     factShc.setSpecialHandlingGroupCode(s);
	                     factShcList.add(factShc);
	                  }
	               }

	            }
	         }
	         initRuleEngineProcessExecution(booking, "ADD", factFlightList, factShcList, flight);
	      }

	   }

	   private void initRuleEngineProcessExecution(ShipmentBookingDetails booking, String actionType,
	         List<FactFlight> factFlightList, List<FactShipmentSHC> factShcList, ShipmentFlightBookingDetails flight)
	         throws CustomException {
	      if (!CollectionUtils.isEmpty(factFlightList)) {
	         FactPayload factPayload = new FactPayload();
	         factPayload.setFactFlights(factFlightList);
	         factPayload.setFactShipment(
	               initializeShipmentFact(booking.getShipmentNumber(), booking.getShipmentDate(), factShcList, booking));
	         initializeRuleEnginePayload(factPayload, booking);
	         factPayload.setPriorExecution(true);
	         factPayload.setRulesConfigured(true);
	         factPayload.setFactPayloadLookup(false);
	         FactPayload factPayloadResponse = this.ruleExecutor.executeRule(factPayload);
	         if (factPayloadResponse != null) {
	            if (Objects.nonNull(factPayloadResponse.getExecutionDetails())) {
	               if (!StringUtils.isEmpty(factPayloadResponse.getExecutionDetails().getFailureMessage())) {
	                  flight.setBookingStatusCode("EM");

	               } else if (!CollectionUtils.isEmpty(factPayloadResponse.getExecutionDetails().getExecInfoList())
	                     || !CollectionUtils.isEmpty(factPayloadResponse.getExecutionDetails().getExecWarnList())) {
	                  flight.setBookingStatusCode("EM");
	               }
	            }
	         }

	      }

	   }
	   
	   private FactShipment initializeShipmentFact(String shipmentNumber, LocalDate shipmentDate,
		         List<FactShipmentSHC> factShcList,ShipmentBookingDetails booking) throws CustomException {
		      FactShipment factShipment = new FactShipment();
		      factShipment.setCarrierCode(booking.getCarrierCode());
		      factShipment.setShipmentNumber(shipmentNumber);
		      factShipment.setShipmentDate(shipmentDate);
		      factShipment.setOrigin(booking.getOrigin());
		      factShipment.setDestination(booking.getDestination());
		      factShipment.setShcList(factShcList);
		      return factShipment;
		   }

		   private void initializeRuleEnginePayload(FactPayload factPayload, ShipmentBookingDetails booking) {
		      this.rulePayloadGroups(factPayload);
		      // Set Audit details
		      factPayload.setCreatedBy(booking.getCreatedBy());
		      factPayload.setCreatedOn(booking.getCreatedOn());
		      factPayload.setModifiedBy(booking.getModifiedBy());
		      factPayload.setModifiedOn(booking.getModifiedOn());
		   }

		   private void rulePayloadGroups(FactPayload factPayload) {
		      factPayload.setTriggerPoint(InboundMessage.class);
		   }
}
