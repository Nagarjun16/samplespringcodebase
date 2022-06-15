package com.ngen.cosys.impbd.flightdiscrepancylist.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.InboundFlightDiscrepancyListStoreEvent;
import com.ngen.cosys.events.payload.ShipmentDiscrepancyEvent;
import com.ngen.cosys.events.producer.InboundFlightDiscrepancyListStoreEventProducer;
import com.ngen.cosys.events.producer.ShipmentDiscrepancyEventProducer;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.flightdiscrepancylist.model.FlightDiscrepancyList;
import com.ngen.cosys.impbd.flightdiscrepancylist.model.FlightDiscrepancyListModel;
import com.ngen.cosys.impbd.flightdiscrepancylist.service.FlightDiscrepancyListService;
import com.ngen.cosys.impbd.flightdiscrepancylist.validators.FlightValidatorGroup;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This APIs gives an overview of Flight Discrepancy List
 * 
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NgenCosysAppInfraAnnotation
public class FlightDiscrepancyListController {

   @Autowired
   private FlightDiscrepancyListService flightDiscrepancyListService;

   @Autowired
   private UtilitiesModelConfiguration utilitiesModel;

   @Autowired
   private InboundFlightDiscrepancyListStoreEventProducer producer;

   @Autowired
   private ShipmentDiscrepancyEventProducer shipmentDiscrepancyProducer;

   /**
    * API to get Discrepancy List of flight based on flightkey and date,
    * 
    * @param searchDiscrepancyList
    * @return FlightDiscrepancyListModel
    * @throws CustomException
    * 
    */
   @ApiOperation("Get  flight discrepancy list  based on flightkey and  date")
   @PostRequest(value = "/api/impbd/fdl/fetch", method = RequestMethod.POST)
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.FLIGHT_DISCREPANCY)
   @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
   public BaseResponse<FlightDiscrepancyListModel> search(
         @ApiParam(value = "FlightDiscrepancy", required = true) @Validated(value = {
               FlightValidatorGroup.class }) @RequestBody FlightDiscrepancyListModel searchDiscrepancyList)
         throws CustomException {
      boolean send = searchDiscrepancyList.isSendEvent();
      @SuppressWarnings("unchecked")
      BaseResponse<FlightDiscrepancyListModel> flightDiscrepancyList = utilitiesModel.getBaseResponseInstance();
      FlightDiscrepancyListModel flightsData = flightDiscrepancyListService.fetch(searchDiscrepancyList);
      if (send) {
         
         // Set the version
         this.flightDiscrepancyListService.updateFDLVersion(searchDiscrepancyList);
         
         InboundFlightDiscrepancyListStoreEvent event = new InboundFlightDiscrepancyListStoreEvent();
         event.setFlightId(new BigInteger(flightsData.getFlightId() + ""));
         event.setShipmentType("CARGO");
         event.setCreatedBy(searchDiscrepancyList.getLoggedInUser());
         event.setCreatedOn(LocalDateTime.now());
         event.setLastModifiedBy(searchDiscrepancyList.getModifiedBy());
         event.setLastModifiedOn(LocalDateTime.now());
         producer.publish(event);
         
         Map<String,List<FlightDiscrepancyList>> mapByShipmentNumber = new HashMap<String,List<FlightDiscrepancyList>>();
         mapByShipmentNumber =flightsData.getFlightDiscrepancyList().stream().
					    collect(Collectors.groupingBy(FlightDiscrepancyList::getShipmentNumber));
		   for(Map.Entry<String,List<FlightDiscrepancyList>> entry : mapByShipmentNumber.entrySet())
	        {
			   List<FlightDiscrepancyList> 	shipmentDetails=entry.getValue();
			 //FlightDiscrepancyList data1=shipmentDetails.get(0);
			   FlightDiscrepancyList data=shipmentDetails.stream().findFirst().get();
			   ShipmentDiscrepancyEvent eventFis = new ShipmentDiscrepancyEvent();
	            eventFis.setFlightId(new BigInteger(flightsData.getFlightId() + ""));
	            eventFis.setShipmentNumber(data.getShipmentNumber() + "");
	            eventFis.setShipmentDate(data.getShipmentDate());
	            eventFis.setShipmentId(data.getShipmentId());
	            eventFis.setCreatedBy(searchDiscrepancyList.getLoggedInUser());
	            eventFis.setShipmentType("CARGO");
	            eventFis.setCreatedOn(LocalDateTime.now());
	            eventFis.setLastModifiedBy(searchDiscrepancyList.getModifiedBy());
	            eventFis.setLastModifiedOn(LocalDateTime.now());
	            eventFis.setFunction("Flight Discrepany List");
	            eventFis.setEventName(EventTypes.Names.SHIPMENT_DISCREPANCY_EVENT);
	            shipmentDiscrepancyProducer.publish(eventFis);
	        }

         for (FlightDiscrepancyList data : flightsData.getFlightDiscrepancyList()) {
            ShipmentDiscrepancyEvent eventFis = new ShipmentDiscrepancyEvent();
            eventFis.setFlightId(new BigInteger(flightsData.getFlightId() + ""));
            eventFis.setShipmentNumber(data.getShipmentNumber() + "");
            eventFis.setShipmentDate(data.getShipmentDate());
            eventFis.setShipmentId(data.getShipmentId());
            eventFis.setCreatedBy(searchDiscrepancyList.getLoggedInUser());
            eventFis.setShipmentType("CARGO");
            eventFis.setCreatedOn(LocalDateTime.now());
            eventFis.setLastModifiedBy(searchDiscrepancyList.getModifiedBy());
            eventFis.setLastModifiedOn(LocalDateTime.now());
            if(CollectionUtils.isEmpty(data.getShipmentIrregularityIds())) {
            	//For DAMG irregularity Ids is null
            	List<BigInteger> shipmentIrregularityIds=new ArrayList<BigInteger>();
            	shipmentIrregularityIds.add(BigInteger.ZERO);
            	data.setShipmentIrregularityIds(shipmentIrregularityIds);
            }
            eventFis.setShipmentIrregularityId(data.getShipmentIrregularityIds());
            eventFis.setFunction("Flight Discrepany List");
            eventFis.setEventName(EventTypes.Names.SHIPMENT_DISCREPANCY_EVENT);
            shipmentDiscrepancyProducer.publish(eventFis);
         }
      }
      FlightDiscrepancyListModel  fdlVersion =flightDiscrepancyListService.getFDLVersion(searchDiscrepancyList);
      flightsData.setFlightDiscrepncyListSentAt(fdlVersion.getFlightDiscrepncyListSentAt());
      flightsData.setFlightDiscrepncyListSentBy(fdlVersion.getFlightDiscrepncyListSentBy());
      flightDiscrepancyList.setData(flightsData);
      return flightDiscrepancyList;
   }
}