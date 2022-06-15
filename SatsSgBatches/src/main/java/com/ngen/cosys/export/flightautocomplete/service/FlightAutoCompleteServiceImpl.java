/**
 * Implementation class for Auto Complete Outgoing Flight. This class holds all
 * business logic which needs to be performed when marking a flight as complete.
 * 
 * E.g.
 * 
 * Freight Out Inventory of every shipment manifested on the flight/ Trigger
 * event specific messages/ Custom out the shipment info/ Send email if any/
 * Mark the flight as complete
 * 
 */
package com.ngen.cosys.export.flightautocomplete.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.customs.api.SubmitDataToCustoms;
import com.ngen.cosys.customs.api.enums.CustomsEventTypes;
import com.ngen.cosys.customs.api.enums.CustomsShipmentType;
import com.ngen.cosys.customs.api.model.CustomsFlight;
import com.ngen.cosys.customs.api.model.CustomsShipmentInfo;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.events.payload.AirmailStatusEventParentModel;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.payload.JapanFlightCompleteCustomsEvent;
import com.ngen.cosys.events.payload.OutboundFlightCompleteStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentFlightCompletedStoreEvent;
import com.ngen.cosys.events.producer.AirmailStatusEventProducer;
import com.ngen.cosys.events.producer.JapanFlightCompleteCustomsEventProducer;
import com.ngen.cosys.events.producer.OutboundFlightCompleteStoreEventProducer;
import com.ngen.cosys.events.producer.OutboundShipmentFlightCompletedStoreEventProducer;
import com.ngen.cosys.export.flightautocomplete.dao.FlightAutoCompleteDao;
import com.ngen.cosys.export.flightautocomplete.model.FlightAutoCompleteDetails;
import com.ngen.cosys.export.flightautocomplete.model.FlightAutoCompleteShipmentDetails;
import com.ngen.cosys.export.flightautocomplete.model.FlightAutoCompleteShipmentHouseDetails;
import com.ngen.cosys.export.flightautocomplete.model.Volume;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.report.mail.service.ReportMailService;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportMailPayload;

@Service
public class FlightAutoCompleteServiceImpl implements FlightAutoCompleteService {

   private static final String PFA = "PFA, ";

   private static final String _D_FFM = "_D_FFM";

   private static final String _D_FWB = "_D_FWB";

   private static final String _D_RTD = "_D_RTD";

   private static final String EXPORT = "export";

   private static final String CARRIER_CODE = "carrierCode";

   private static final String TENANT_ID = "tenantId";

   private static final String FLIGHT_TYPE = "flightType";

   private static final String FLIGHT_ID = "flightId";

   private static final Logger LOGGER = LoggerFactory.getLogger(FlightAutoCompleteServiceImpl.class);

   private static final String DEFAULT_USER = "AUTOCOMPLETE";

   private static final String DEFAULT_TENANT = "SIN";

   private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

   @Autowired
   private ReportMailService reportMailService;

   @Autowired
   private FlightAutoCompleteDao dao;

   @Autowired
   private SubmitDataToCustoms submitDataToCustoms;

   @Autowired
   private OutboundShipmentFlightCompletedStoreEventProducer outboundShipmentFlightCompletedStoreEventProducer;

   @Autowired
   private OutboundFlightCompleteStoreEventProducer outboundFlightCompleteStoreEventProducer;

   @Autowired
   private AirmailStatusEventProducer airmailStatusEventProducer;

  

   @Autowired
   private JapanFlightCompleteCustomsEventProducer japanCustomEventProducer;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.export.flightautocomplete.service.FlightAutoCompleteService#
    * getFlightList()
    */
   @Override
   public List<FlightAutoCompleteDetails> getFlightList() throws CustomException {
      return dao.getFlightList();
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.export.flightautocomplete.service.FlightAutoCompleteService#
    * markFirstFlightComplete(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteDetails)
    */
   @Override
   public void markFirstFlightComplete(FlightAutoCompleteDetails request) throws CustomException {
      dao.markFirstFlightComplete(request);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.export.flightautocomplete.service.FlightAutoCompleteService#
    * unmarkFirstFlightComplete(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteDetails)
    */
   @Override
   public void unmarkFirstFlightComplete(FlightAutoCompleteDetails request) throws CustomException {
      dao.markFirstFlightComplete(request);
   }

   @Override
   public void freightOut(FlightAutoCompleteDetails request) throws CustomException {
      // Iterate each shipment and do freight out
      if (!CollectionUtils.isEmpty(request.getShipments())) {
         for (FlightAutoCompleteShipmentDetails t : request.getShipments()) {

            LOGGER.warn("Shipment :{}", t.getFlightKey() + " - " + t.getFlightId() + " - " + t.getFlightDate() + " - "
                  + t.getShipmentNumber() + " - " + t.getShipmentId() + " - " + t.getShipmentDate());

            // Set the user details
            t.setFlightKey(request.getFlightKey());
            t.setFlightDate(request.getDateSTD());
            t.setCreatedBy(DEFAULT_USER);
            t.setModifiedBy(DEFAULT_USER);
            t.setTenantId(DEFAULT_TENANT);

            // 1. Freight out shipment
            this.dao.moveInventoryToFreightOut(t);

            // 2. Update shipment status
            this.dao.updateShipmentStatus(t);
            // To check Shipment is completely departed from the Status (Removing Cached
            // Shipment Date)

            // 3. Update shipment house status
            if (!CollectionUtils.isEmpty(t.getHouses())) {
               this.dao.updateShipmentHouseStatus(t.getHouses());
            }
         }
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.export.flightautocomplete.service.FlightAutoCompleteService#
    * submitShipmentsToCustoms(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteDetails)
    */
   @Override
   public void submitShipmentsToCustoms(FlightAutoCompleteDetails request) throws CustomException {
      CustomsFlight customsFlight = new CustomsFlight();
      customsFlight.setFlightId(request.getFlightId());
      customsFlight.setFlightKey(request.getFlightKey());
      customsFlight.setFlightType(CustomsShipmentType.Type.EXPORT);
      customsFlight.setFlightOffPoint(request.getOffPoint());
      customsFlight.setFlightBoardPoint(request.getBoardPoint());
      customsFlight.setFlightDate(request.getDateSTD().toLocalDate());
      customsFlight.setFlightOriginDate(request.getFlightOriginDate());
      customsFlight.setCreatedBy(DEFAULT_USER);
      customsFlight.setModifiedBy(DEFAULT_USER);
      customsFlight.setTenantId(DEFAULT_TENANT);
      customsFlight.setEventType(CustomsEventTypes.EXP_FLIGHT_COMPLETE.getType());

      if (!CollectionUtils.isEmpty(request.getShipments())) {
         List<CustomsShipmentInfo> shipmentsList = new ArrayList<>();
         for (FlightAutoCompleteShipmentDetails t : request.getShipments()) {
            if ("AWB".equalsIgnoreCase(t.getShipmentType())) {
               CustomsShipmentInfo customsShipmentInfo = new CustomsShipmentInfo();
               customsShipmentInfo.setShipmentNumber(t.getShipmentNumber());
               customsShipmentInfo.setShipmentDate(t.getShipmentDate());
               customsShipmentInfo.setOrigin(t.getOrigin());
               customsShipmentInfo.setDestination(t.getDestination());
               customsShipmentInfo.setTotalPieces(t.getShipmentPieces());
               customsShipmentInfo.setTotalWeight(t.getShipmentWeight());
               customsShipmentInfo.setPieces(t.getManifestedPieces());
               customsShipmentInfo.setWeight(t.getManifestWeight());
               customsShipmentInfo.setEventType(CustomsEventTypes.EXP_FLIGHT_COMPLETE.getType());
               customsShipmentInfo.setShipmentType(CustomsShipmentType.Type.EXPORT);
               customsShipmentInfo.setCreatedBy(DEFAULT_USER);
               customsShipmentInfo.setModifiedBy(DEFAULT_USER);
               customsShipmentInfo.setTenantId(DEFAULT_TENANT);
               customsShipmentInfo.setNatureOfGoods(t.getNatureOfGoodsDescription());
               shipmentsList.add(customsShipmentInfo);
            }
         }
         customsFlight.setShipments(shipmentsList);
         try {
            submitDataToCustoms.submitShipment(customsFlight);
         } catch (CustomException e) {
            LOGGER.error("Exception while submitting the customs data for flight ", e);
         }
      }

   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.export.flightautocomplete.service.FlightAutoCompleteService#
    * publishEvents(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteDetails)
    */
   @Override
   public void publishEvents(FlightAutoCompleteDetails request) throws CustomException {

      // check for JAPAN customs
      if (request.getTenantAirport() == null) {
         request.setTenantAirport(DEFAULT_TENANT);
      }
      BigInteger check = dao.checkJapanCustomsRequirement(request.getFlightId(), request.getTenantAirport(),
            request.getCarrierCode());
      if (check != null && check.intValue() != 0) {
         JapanFlightCompleteCustomsEvent flightEvent = new JapanFlightCompleteCustomsEvent();
         flightEvent.setFlightId(request.getFlightId());
         flightEvent.setShipmentType("CARGO");
         flightEvent.setStatus("NEW");
         flightEvent.setLastModifiedBy(DEFAULT_USER);
         flightEvent.setCreatedBy(DEFAULT_USER);
         flightEvent
               .setCreatedOn(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
         if (!CollectionUtils.isEmpty(request.getShipments())) {
            List<OutboundShipmentFlightCompletedStoreEvent> shipmentEventList = new ArrayList<>();
            for (FlightAutoCompleteShipmentDetails f : request.getShipments()) {
               if (!"MAIL".equalsIgnoreCase(f.getShipmentType()) && !"UCB".equalsIgnoreCase(f.getShipmentType())
                     && f.getShipmentId() != null) {

                  OutboundShipmentFlightCompletedStoreEvent event = new OutboundShipmentFlightCompletedStoreEvent();
                  event.setFlightId(f.getFlightId());
                  event.setFlightKey(request.getFlightKey());
                  event.setShipmentId(f.getShipmentId());
                  event.setCarrierCode(request.getCarrierCode());
                  event.setShipmentNumber(f.getShipmentNumber());
                  event.setShipmentDate(f.getShipmentDate());
                  event.setShipmentType(f.getShipmentType());
                  event.setPieces(f.getManifestedPieces());
                  event.setWeight(f.getManifestWeight());
                  event.setStatus("NEW");
                  event.setCompletedBy(DEFAULT_USER);
                  event.setCompletedAt(
                        LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
                  event.setCreatedBy(DEFAULT_USER);
                  event.setCreatedOn(
                        LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
                  event.setSector(f.getFlightOffPoint());
                  event.setFunction("Flight Complete" + " - " + request.getCreatedBy());
                  event.setEventName(EventTypes.Names.JAPAN_CUSTOMS_FLIGHT_COMPLETE_EVENT);
                  shipmentEventList.add(event);

               }
               // Submit event for each house if applicable
               if (!CollectionUtils.isEmpty(f.getHouses()) && !"SQ".equalsIgnoreCase(request.getCarrierCode())
                     && "MAIL".equalsIgnoreCase(f.getShipmentType())) {
                  AirmailStatusEventParentModel parentEvent = new AirmailStatusEventParentModel();
                  List<AirmailStatusEvent> listEvent = new ArrayList<>();
                  for (FlightAutoCompleteShipmentHouseDetails h : f.getHouses()) {
                     AirmailStatusEvent event = new AirmailStatusEvent();
                     event.setSourceTriggerType("EXPMANIFESTFLIGHTCOMPLETE");
                     event.setFlightId(request.getFlightId());
                     event.setCarrierCode(request.getCarrierCode());
                     event.setNextDestination(f.getDestination());
                     event.setMailBag(h.getNumber());
                     event.setTenantId(DEFAULT_TENANT);
                     event.setCreatedBy(DEFAULT_USER);
                     event.setCreatedOn(
                           LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
                     event.setStatus("CREATED");
                     listEvent.add(event);
                  }
                  parentEvent.setAllMessage(listEvent);
                  airmailStatusEventProducer.publish(parentEvent);
               }
            }
            flightEvent.setShipmentList(shipmentEventList);

         }
         japanCustomEventProducer.publish(flightEvent);
      } else {
         // Trigger event by Flight
         OutboundFlightCompleteStoreEvent outboundFlightCompleteStoreEvent = new OutboundFlightCompleteStoreEvent();
         outboundFlightCompleteStoreEvent.setFlightId(request.getFlightId());
         outboundFlightCompleteStoreEvent.setFlightKey(request.getFlightKey());
         outboundFlightCompleteStoreEvent.setCarrierCode(request.getCarrierCode());
         outboundFlightCompleteStoreEvent.setShipmentType("CARGO");
         outboundFlightCompleteStoreEvent.setStatus("NEW");
         outboundFlightCompleteStoreEvent.setLastModifiedBy(DEFAULT_USER);
         outboundFlightCompleteStoreEvent.setTriggeredByBatch(Boolean.TRUE);
         outboundFlightCompleteStoreEvent.setCreatedBy(DEFAULT_USER);
         outboundFlightCompleteStoreEvent
               .setCreatedOn(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
         this.outboundFlightCompleteStoreEventProducer.publish(outboundFlightCompleteStoreEvent);

         // Trigger event by Each Shipment
         if (!CollectionUtils.isEmpty(request.getShipments())) {
            for (FlightAutoCompleteShipmentDetails f : request.getShipments()) {
               if (!"MAIL".equalsIgnoreCase(f.getShipmentType()) && !"UCB".equalsIgnoreCase(f.getShipmentType())) {
                  OutboundShipmentFlightCompletedStoreEvent outboundShipmentFlightCompletedStoreEvent = new OutboundShipmentFlightCompletedStoreEvent();
                  outboundShipmentFlightCompletedStoreEvent.setFlightKey(request.getFlightKey());
                  outboundShipmentFlightCompletedStoreEvent.setCarrierCode(request.getCarrierCode());
                  outboundShipmentFlightCompletedStoreEvent.setShipmentNumber(f.getShipmentNumber());
                  outboundShipmentFlightCompletedStoreEvent.setShipmentDate(f.getShipmentDate());
                  outboundShipmentFlightCompletedStoreEvent.setShipmentType(f.getShipmentType());
                  outboundShipmentFlightCompletedStoreEvent.setSector(f.getFlightOffPoint());
                  outboundShipmentFlightCompletedStoreEvent.setFlightId(request.getFlightId());
                  outboundShipmentFlightCompletedStoreEvent.setShipmentId(f.getShipmentId());
                  outboundShipmentFlightCompletedStoreEvent.setPieces(f.getFreightOutPieces());
                  outboundShipmentFlightCompletedStoreEvent.setWeight(f.getFreightOutWeight());
                  outboundShipmentFlightCompletedStoreEvent.setStatus("NEW");
                  outboundShipmentFlightCompletedStoreEvent.setCompletedBy(DEFAULT_USER);
                  outboundShipmentFlightCompletedStoreEvent.setCompletedAt(
                        LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
                  outboundShipmentFlightCompletedStoreEvent.setCreatedBy(DEFAULT_USER);
                  outboundShipmentFlightCompletedStoreEvent.setCreatedOn(
                        LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
                  outboundShipmentFlightCompletedStoreEvent.setSector(f.getFlightOffPoint());
                  outboundShipmentFlightCompletedStoreEvent.setFunction("FLIGHT AUTO COMPLETE");
                  outboundShipmentFlightCompletedStoreEvent
                        .setEventName(EventTypes.Names.OUTBOUND_SHIPMENT_FLIGHT_COMPLETED_EVENT);
                  this.outboundShipmentFlightCompletedStoreEventProducer
                        .publish(outboundShipmentFlightCompletedStoreEvent);
               }

               // Submit event for each house if applicable
               if (!CollectionUtils.isEmpty(f.getHouses()) && !"SQ".equalsIgnoreCase(request.getCarrierCode())
                     && "MAIL".equalsIgnoreCase(f.getShipmentType())) {
                  AirmailStatusEventParentModel parentEvent = new AirmailStatusEventParentModel();
                  List<AirmailStatusEvent> listEvent = new ArrayList<>();
                  for (FlightAutoCompleteShipmentHouseDetails h : f.getHouses()) {
                     AirmailStatusEvent event = new AirmailStatusEvent();
                     event.setSourceTriggerType("EXPMANIFESTFLIGHTCOMPLETE");
                     event.setFlightId(request.getFlightId());
                     event.setCarrierCode(request.getCarrierCode());
                     event.setNextDestination(f.getDestination());
                     event.setMailBag(h.getNumber());
                     event.setTenantId(DEFAULT_TENANT);
                     event.setCreatedBy(DEFAULT_USER);
                     event.setCreatedOn(
                           LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
                     event.setStatus("CREATED");
                     listEvent.add(event);

                  }
                  parentEvent.setAllMessage(listEvent);
                  airmailStatusEventProducer.publish(parentEvent);
               }
            }
         }
      }

   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.export.flightautocomplete.service.FlightAutoCompleteService#
    * markFlightComplete(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteDetails)
    */
   @Override
   public void markFlightComplete(FlightAutoCompleteDetails request) throws CustomException {
      // Mark the flight complete
      this.dao.markFlightComplete(request);
      // Mark the flight as DEP
      this.dao.markFlightStats(request);
      //mark OFL movement
      this.dao.createUldOutMovement(request);
      // De-Associate the ULD's used for flight
      this.dao.deAssociateULDFromFlight(request);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.export.flightautocomplete.service.FlightAutoCompleteService#
    * getCommunicationEmailIds(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteDetails)
    */
   @Override
   public List<String> getCommunicationEmailIds(FlightAutoCompleteDetails request) throws CustomException {
      return this.dao.getCommunicationEmailIds(request);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.export.flightautocomplete.service.FlightAutoCompleteService#
    * sendEmailOnFFMInfo(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteDetails)
    */
   @Override
   public void sendEmailOnFFMInfo(FlightAutoCompleteDetails request) throws CustomException {
      Map<String, Object> reportParams = new HashMap<>();
      reportParams.put(FLIGHT_ID, request.getFlightId());
      reportParams.put(FLIGHT_TYPE, EXPORT);
      reportParams.put(TENANT_ID, DEFAULT_TENANT);
      reportParams.put(CARRIER_CODE, request.getCarrierCode());

      String[] mailIds = request.getCommunicationEmails().toArray(new String[request.getCommunicationEmails().size()]);

      String flightDate = request.getDateSTD().format(dateTimeFormatter);

      List<ReportMailPayload> reportPayload = new ArrayList<>();

      ReportMailPayload reportMailPayload = new ReportMailPayload();
      reportMailPayload.setReportFormat(ReportFormat.CSV);
      reportMailPayload.setReportParams(reportParams);
      reportMailPayload.setReportName("FFM_Departure");
      reportMailPayload.setFileName(request.getFlightKey() + "_" + flightDate + _D_FFM);
      reportPayload.add(reportMailPayload);

      EMailEvent emailEvent = new EMailEvent();
      emailEvent.setMailToAddress(mailIds);
      emailEvent.setMailSubject(request.getFlightKey() + "_" + flightDate + _D_FFM);
      emailEvent.setMailBody(PFA + request.getFlightKey() + "_" + flightDate + _D_FFM);
      emailEvent.setNotifyAddress(null);

      reportMailService.sendReport(reportPayload, emailEvent);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.export.flightautocomplete.service.FlightAutoCompleteService#
    * sendEmailOnFWBInfo(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteDetails)
    */
   @Override
   public void sendEmailOnFWBInfo(FlightAutoCompleteDetails request) throws CustomException {
      Map<String, Object> reportParams = new HashMap<>();
      reportParams.put(FLIGHT_ID, request.getFlightId());
      reportParams.put(FLIGHT_TYPE, EXPORT);
      reportParams.put(TENANT_ID, DEFAULT_TENANT);
      reportParams.put(CARRIER_CODE, request.getCarrierCode());

      String[] mailIds = request.getCommunicationEmails().toArray(new String[request.getCommunicationEmails().size()]);

      String flightDate = request.getDateSTD().format(dateTimeFormatter);

      List<ReportMailPayload> reportPayload = new ArrayList<>();
      ReportMailPayload reportMailPayload = new ReportMailPayload();
      reportMailPayload.setReportFormat(ReportFormat.CSV);
      reportMailPayload.setReportParams(reportParams);
      reportMailPayload.setReportName("FWB_Departure");
      reportMailPayload.setFileName(request.getFlightKey() + "_" + flightDate + _D_FWB);
      reportPayload.add(reportMailPayload);

      EMailEvent emailEvent = new EMailEvent();
      emailEvent.setMailToAddress(mailIds);
      emailEvent.setMailSubject(request.getFlightKey() + "_" + flightDate + _D_FWB);
      emailEvent.setMailBody(PFA + request.getFlightKey() + "_" + flightDate + _D_FWB);
      emailEvent.setNotifyAddress(null);

      reportMailService.sendReport(reportPayload, emailEvent);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.export.flightautocomplete.service.FlightAutoCompleteService#
    * sendEmailOnRTDInfo(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteDetails)
    */
   @Override
   public void sendEmailOnRTDInfo(FlightAutoCompleteDetails request) throws CustomException {

      Map<String, Object> reportParams = new HashMap<>();
      reportParams.put(FLIGHT_ID, request.getFlightId());
      reportParams.put(FLIGHT_TYPE, EXPORT);
      reportParams.put(TENANT_ID, DEFAULT_TENANT);
      reportParams.put(CARRIER_CODE, request.getCarrierCode());

      String[] mailIds = request.getCommunicationEmails().toArray(new String[request.getCommunicationEmails().size()]);

      String flightDate = request.getDateSTD().format(dateTimeFormatter);

      List<ReportMailPayload> reportPayload = new ArrayList<>();
      ReportMailPayload reportMailPayload = new ReportMailPayload();
      reportMailPayload.setReportFormat(ReportFormat.CSV);
      reportMailPayload.setReportParams(reportParams);
      reportMailPayload.setReportName("RTD_Departure");
      reportMailPayload.setFileName(request.getFlightKey() + "_" + flightDate + _D_RTD);
      reportPayload.add(reportMailPayload);

      EMailEvent emailEvent = new EMailEvent();
      emailEvent.setMailToAddress(mailIds);
      emailEvent.setMailSubject(request.getFlightKey() + "_" + flightDate + _D_RTD);
      emailEvent.setMailBody(PFA + request.getFlightKey() + "_" + flightDate + _D_RTD);
      emailEvent.setNotifyAddress(null);

      reportMailService.sendReport(reportPayload, emailEvent);

   }

   @Override
   public void calculateVolume(FlightAutoCompleteDetails request) throws CustomException {

      // Default Volume Multiplier
      BigDecimal multiplier = new BigDecimal("0.006");

      String volumeCode = "MC";

      // Derive whether volume needs to be calculated or not
      Boolean isVolumeNeedsToBeDerived = request.isIncludeVolumeInFFM();

      Optional<Boolean> oIsVolumeNeedsToBeDerived = Optional.ofNullable(isVolumeNeedsToBeDerived);
      if (oIsVolumeNeedsToBeDerived.isPresent() && oIsVolumeNeedsToBeDerived.get()) {

         // Get the manifest shipment info
         List<FlightAutoCompleteShipmentDetails> shipments = request.getShipmentsFfm();
         if (!CollectionUtils.isEmpty(shipments)) {
            // For each shipment calculate the volume
            for (FlightAutoCompleteShipmentDetails t : shipments) {
               // Set the user
               t.setModifiedBy(DEFAULT_USER);

               // Get the derived volume for current flight and deduct from available volume
               t.setFlightId(request.getFlightId());

               BigDecimal totalVolume = BigDecimal.ZERO;

               // Calculate if chargeable weight is present
               if (!ObjectUtils.isEmpty(t.getChargeableWeight())) {
                  // Formula Total Volume = Total chargeable Weight in kilograms * 0.006
                  totalVolume = t.getChargeableWeight().multiply(multiplier, new MathContext(2, RoundingMode.CEILING));
               } else if (!ObjectUtils.isEmpty(t.getTotalConsignmentWeight())
                     && BigDecimal.ZERO.compareTo(t.getTotalConsignmentWeight()) < 0) {
                  // Formula Total Volume = Total AWB Gross Weight in kilograms * 0.006
                  totalVolume = t.getTotalConsignmentWeight().multiply(multiplier,
                        new MathContext(2, RoundingMode.CEILING));
               }

               // Identify individual piece level volume
               BigDecimal totalConsignmentPieces = BigDecimal.valueOf(t.getTotalConsignmentPieces().intValue());
               BigDecimal volumeBySinglePiece = totalVolume.divide(totalConsignmentPieces, 9, BigDecimal.ROUND_HALF_UP);

               // Deduct the already set volume with available volume
               BigDecimal consignmentPieces = BigDecimal.valueOf(t.getManifestedPieces().doubleValue());
               BigDecimal consignmentVolume = volumeBySinglePiece.multiply(consignmentPieces);
               consignmentVolume = consignmentVolume.setScale(2, BigDecimal.ROUND_HALF_UP);

               // If the value of consignmentVolume is 0 then set value to 0.01 default
               if (consignmentVolume.compareTo(BigDecimal.ZERO) == 0) {
                  consignmentVolume = BigDecimal.valueOf(0.01);
               }

               // Set the volume
               if (!ObjectUtils.isEmpty(consignmentVolume) && consignmentVolume.compareTo(BigDecimal.ZERO) > 0) {
                  Volume volume = new Volume();
                  volume.setVolumeCode(volumeCode);
                  volume.setVolumeAmount(consignmentVolume);
                  // Set it to the shipment
                  t.setVolume(volume);

                  // Update the volume information
                  this.dao.updateManifestShipmentVolume(t);
               }

            }
         }
      }

   }

}