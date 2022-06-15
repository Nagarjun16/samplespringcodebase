/**
 * * This is a interface which has business methods for capturing information in
 * InwardServiceReport
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 04/04/2018
 */
package com.ngen.cosys.inward.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.constants.CRUDTypes;
import com.ngen.cosys.impbd.events.payload.DamageReportEvent;
import com.ngen.cosys.impbd.events.payload.InwardReportEvent;
import com.ngen.cosys.impbd.events.producer.DamageReportEventProducer;
import com.ngen.cosys.impbd.events.producer.InwardReportEventProducer;
import com.ngen.cosys.impbd.tracing.activity.model.TracingActivityShipmentModel;
import com.ngen.cosys.impbd.tracing.activity.service.TracingActivityService;
import com.ngen.cosys.inward.dao.InwardServiceReportDao;
import com.ngen.cosys.inward.model.InwardSegmentConcatenate;
import com.ngen.cosys.inward.model.InwardSegmentModel;
import com.ngen.cosys.inward.model.InwardServiceReportModel;
import com.ngen.cosys.inward.model.InwardServiceReportOtherDiscrepancyModel;
import com.ngen.cosys.inward.model.InwardServiceReportShipmentDiscrepancyModel;
import com.ngen.cosys.timezone.util.TenantZoneTime;
import com.ngen.cosys.validator.enums.ShipmentType;

@Service
public class InwardServiceReportServiceImpl implements InwardServiceReportService {

   private static final String PART_SHIPMENT = "PART-SHIPMENT";

   @Autowired
   private InwardServiceReportDao dao;

   @Autowired
   private TracingActivityService tracingActivityService;

   @Autowired
   private ShipmentProcessorService shipmentProcessorService;

   @Autowired
   private InwardReportEventProducer inwardReportEventProducer;

   @Autowired
   private DamageReportEventProducer damageReportEventProducer;
   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.inward.service.InwardServiceReportService#
    * createServiceReportOnFlightComplete(com.ngen.cosys.inward.model.
    * InwardServiceReportModel)
    */
   private String form = "inwardServiceForm";

   @Override
   @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Throwable.class)
   public void createServiceReportOnFlightComplete(InwardServiceReportModel requestModel) throws CustomException {

      InwardServiceReportModel serviceReportModel = null;
      // Fetch the irregularity shipments for adding to service report
      switch (requestModel.getServiceReportFor()) {
      case (ShipmentType.Type.AWB):
         // This method when it is called from break/document complete system checks for
         // finalize if yes then system does not adds any data to service report
         if (!dao.checkServiceReportFinalizedForCargo(requestModel)) {
            // Get the segments for a given flight
            List<InwardServiceReportModel> segments = dao.getSegments(requestModel);
            // For each segment derive the irregularity information
            for (InwardServiceReportModel t : segments) {
               // Set the segment id
               requestModel.setSegmentId(t.getSegmentId());
               // Fetch the irregularity for AWB
               serviceReportModel = dao.getInwardServiceReportByAwb(requestModel);
               serviceReportModel.setServiceReportFor(requestModel.getServiceReportFor());
               Optional<InwardServiceReportModel> oInwardServiceReport = Optional.ofNullable(serviceReportModel);

               if (oInwardServiceReport.isPresent()
                     && !CollectionUtils.isEmpty(serviceReportModel.getPhysicalDiscrepancy())
                     || !CollectionUtils.isEmpty(serviceReportModel.getShipmentDiscrepancy())) {
                  serviceReportModel.setLoggedInUser(requestModel.getLoggedInUser());
                  serviceReportModel.setCreatedBy(requestModel.getCreatedBy());
                  serviceReportModel.setCreatedOn(requestModel.getCreatedOn());
                  this.create(serviceReportModel, false);
               }
            }
         }
         break;
      case (ShipmentType.Type.MAIL):
         // This method when it is called from break/document complete system checks for
         // finalize if yes then system does not adds any data to service report
         if (!dao.checkServiceReportFinalizedForMail(requestModel)) {
            // Get the segments for a given flight
            List<InwardServiceReportModel> segments = dao.getSegments(requestModel);
            // For each segment derive the irregularity information
            for (InwardServiceReportModel t : segments) {

               // Set the segment id
               requestModel.setSegmentId(t.getSegmentId());
               // Fetch the irregularity for Mail
               serviceReportModel = dao.getInwardServiceReportByMail(requestModel);
               serviceReportModel.setServiceReportFor(requestModel.getServiceReportFor());


               Optional<InwardServiceReportModel> oInwardServiceReport = Optional.ofNullable(serviceReportModel);
               if (oInwardServiceReport.isPresent()
                     && !CollectionUtils.isEmpty(serviceReportModel.getPhysicalDiscrepancy())
                     || !CollectionUtils.isEmpty(serviceReportModel.getShipmentDiscrepancy())) {
                  if (!CollectionUtils.isEmpty(serviceReportModel.getPhysicalDiscrepancy())) {
                     serviceReportModel.getPhysicalDiscrepancy()
                           .forEach(obj -> obj.setShipmentType(ShipmentType.Type.MAIL));
                  }

                  if (!CollectionUtils.isEmpty(serviceReportModel.getShipmentDiscrepancy())) {
                     serviceReportModel.getShipmentDiscrepancy()
                           .forEach(obj -> obj.setShipmentType(ShipmentType.Type.MAIL));
                  }
                  // Set the tenant
                  serviceReportModel.setLoggedInUser(requestModel.getLoggedInUser());
                  serviceReportModel.setCreatedBy(requestModel.getCreatedBy());
                  serviceReportModel.setCreatedOn(requestModel.getCreatedOn());
                  this.create(serviceReportModel, false);
               }
            }
         }
         break;
      default:
         break;
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.inward.service.InwardServiceReportService#
    * createServiceReportOnRampCheckInComplete(com.ngen.cosys.inward.model.
    * InwardServiceReportModel)
    */
   @Override
   @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Throwable.class)
   public void createServiceReportOnRampCheckInComplete(InwardServiceReportModel requestModel) throws CustomException {
      // This method when it is called from break/document complete system checks for
      // finalize if yes then system does not adds any data to service report
      if (!dao.checkServiceReportFinalizedForCargo(requestModel)) {
         // Get the other discrepancy if found any
         List<InwardServiceReportOtherDiscrepancyModel> otherDiscrepancy = this.dao
               .getInwardServiceReportByULDForOtherDiscrepancy(requestModel);
         if (!CollectionUtils.isEmpty(otherDiscrepancy)) {
            // Set the segment id
            requestModel.setSegmentId(String.valueOf(otherDiscrepancy.get(0).getInwardServiceReportId().intValue()));

            // Set the flag CRUD status
            otherDiscrepancy.forEach(t -> {
               t.setFlagCRUD(CRUDTypes.Type.CREATE);
               t.setManual(Boolean.TRUE);
            });

            // set the discrepancy info
            requestModel.setOtherDiscrepancy(otherDiscrepancy);

            // Create the service report
            this.create(requestModel, false);
         }
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.inward.service.InwardServiceReportService#createServiceReport(
    * com.ngen.cosys.inward.model.InwardServiceReportModel)
    */
   @Override
   @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
   public InwardServiceReportModel createServiceReport(InwardServiceReportModel requestModel) throws CustomException {

      if (requestModel.getCheckstatus()) {
         throw new CustomException("impbd.already.finalize", "inwardServiceForm", ErrorType.ERROR);
      }

      // Validate the form
      List<InwardServiceReportShipmentDiscrepancyModel> createDataForShipment = requestModel.getShipmentDiscrepancy()
            .stream().filter(obj -> (Action.CREATE.toString().equalsIgnoreCase(obj.getFlagCRUD())))
            .collect(Collectors.toList());

      for (InwardServiceReportShipmentDiscrepancyModel value : createDataForShipment) {
         if (!StringUtils.isEmpty(value.getShipmentType()) && value.getShipmentType().equals("MAIL")) {
            if ((null == value.getIrregularityPieces() && StringUtils.isEmpty(value.getIrregularityType()))
                  || (null == value.getIrregularityPieces() && !StringUtils.isEmpty(value.getIrregularityType()))
                  || (null != value.getIrregularityPieces() && StringUtils.isEmpty(value.getIrregularityType()))) {
               throw new CustomException("IRRTYPE", "inwardServiceForm", ErrorType.ERROR);
            }
         }

      }

      if (!CollectionUtils.isEmpty(requestModel.getPhysicalDiscrepancy())) {
         List<InwardServiceReportShipmentDiscrepancyModel> createDataForPhysical = requestModel.getPhysicalDiscrepancy()
               .stream().filter(obj -> (Action.CREATE.toString().equalsIgnoreCase(obj.getFlagCRUD())))
               .collect(Collectors.toList());

         for (InwardServiceReportShipmentDiscrepancyModel value : createDataForPhysical) {
            if (!StringUtils.isEmpty(value.getShipmentType()) && value.getShipmentType().equals("MAIL")) {
               if ((null == value.getIrregularityPieces() && StringUtils.isEmpty(value.getIrregularityType()))
                     || (null == value.getIrregularityPieces() && !StringUtils.isEmpty(value.getIrregularityType()))
                     || (null != value.getIrregularityPieces() && StringUtils.isEmpty(value.getIrregularityType()))) {
                  throw new CustomException("IRRTYPE", "inwardServiceForm", ErrorType.ERROR);
               }
            }
         }
      }
      // This method performs exactly same function as above but does allow staff to
      // maintain additional data even after finalization
      this.create(requestModel, true);
      
      //Delete the tracing records created for MAIL if tracing record exists
      List<TracingActivityShipmentModel> deleteTracingShipmentInfo = new ArrayList<>();
      
      // Add Shipment deleted document discrepancy info to tracing
      if (!CollectionUtils.isEmpty(requestModel.getShipmentDiscrepancy())) {
         List<InwardServiceReportShipmentDiscrepancyModel> shipmentDiscrepanyInfo = requestModel.getShipmentDiscrepancy()
               .stream().filter(obj -> (Action.DELETE.toString().equalsIgnoreCase(obj.getFlagCRUD())))
               .filter(obj -> !StringUtils.isEmpty(obj.getShipmentType()) && obj.getShipmentType().equals("MAIL"))
               .collect(Collectors.toList());
        
         // Document Discrepancy
         if (!CollectionUtils.isEmpty(shipmentDiscrepanyInfo)) {
            for (InwardServiceReportShipmentDiscrepancyModel t : shipmentDiscrepanyInfo) {
               TracingActivityShipmentModel tracingActivityShipmentModel = new TracingActivityShipmentModel();
               tracingActivityShipmentModel.setShipmentType(t.getShipmentType());
               tracingActivityShipmentModel.setFlightId(requestModel.getFlightId());
               tracingActivityShipmentModel.setShipmentdate(t.getShipmentdate());
               tracingActivityShipmentModel.setShipmentNumber(t.getShipmentNumber());     
               tracingActivityShipmentModel.setHouseNumber(t.getHouseNumber());           
               tracingActivityShipmentModel.setTracingCreatedfor(t.getShipmentType());
               tracingActivityShipmentModel.setIrregularityTypeCode(t.getIrregularityType());
               tracingActivityShipmentModel.setFlightKey(requestModel.getFlightNumber());
               tracingActivityShipmentModel.setFlightDate(requestModel.getFlightDate());               
               deleteTracingShipmentInfo.add(tracingActivityShipmentModel);
            }
         }
      }

      //Add Physical deleted document discrepancy info to tracing
      if (!CollectionUtils.isEmpty(requestModel.getPhysicalDiscrepancy())) {

         List<InwardServiceReportShipmentDiscrepancyModel> physicalDiscrepancyInfo = requestModel.getPhysicalDiscrepancy()
               .stream().filter(obj -> (Action.DELETE.toString().equalsIgnoreCase(obj.getFlagCRUD())))
               .filter(obj -> !StringUtils.isEmpty(obj.getShipmentType()) && obj.getShipmentType().equals("MAIL"))
               .collect(Collectors.toList());
         
         // Physical Discrepancy
         if (!CollectionUtils.isEmpty(physicalDiscrepancyInfo)) {
            for (InwardServiceReportShipmentDiscrepancyModel t : physicalDiscrepancyInfo) {
               TracingActivityShipmentModel tracingActivityShipmentModel = new TracingActivityShipmentModel();
               tracingActivityShipmentModel.setShipmentType(t.getShipmentType());
               tracingActivityShipmentModel.setFlightId(requestModel.getFlightId());
               tracingActivityShipmentModel.setShipmentdate(t.getShipmentdate());
               tracingActivityShipmentModel.setShipmentNumber(t.getShipmentNumber());
               tracingActivityShipmentModel.setHouseNumber(t.getHouseNumber());
               tracingActivityShipmentModel.setTracingCreatedfor(t.getShipmentType());
               tracingActivityShipmentModel.setIrregularityTypeCode(t.getIrregularityType());
               tracingActivityShipmentModel.setFlightKey(requestModel.getFlightNumber());
               tracingActivityShipmentModel.setFlightDate(requestModel.getFlightDate());
               deleteTracingShipmentInfo.add(tracingActivityShipmentModel);
            }
         }
      }
      
      //Delete the tracing for MAIL shipment types
      this.tracingActivityService.deleteTracingActivityForShipment(deleteTracingShipmentInfo);

      return requestModel;
   }

   /**
    * Local method which creates service report data
    * 
    * @param requestModel
    * @throws CustomException
    */

   private void create(InwardServiceReportModel requestModel, boolean manualServiceReportCreation)
         throws CustomException {
      // 1. Add flight info
      dao.createFlight(requestModel);

      // 2. Add Shipment document discrepancy info
      if (!CollectionUtils.isEmpty(requestModel.getShipmentDiscrepancy())) {
         List<InwardServiceReportShipmentDiscrepancyModel> createDataForShipment = requestModel.getShipmentDiscrepancy()
               .stream().filter(obj -> (Action.CREATE.toString().equalsIgnoreCase(obj.getFlagCRUD())))
               .collect(Collectors.toList());
         for (InwardServiceReportShipmentDiscrepancyModel value : createDataForShipment) {
            if (ShipmentType.MAIL.toString().equalsIgnoreCase(value.getShipmentType())
                  && 20 != value.getShipmentNumber().length()) {
               throw new CustomException("INVALIDDISPATCH", "", ErrorType.ERROR);
            }
            if (StringUtils.isEmpty(value.getWeightUnitCode())) {
               value.setWeightUnitCode("K");
            }
            Integer count = dao.checkDuplicateForDoc(value);
            if (count > 0) {
               throw new CustomException("DOCErr", form, ErrorType.ERROR);
            }
         }
         requestModel.getShipmentDiscrepancy().forEach(t -> {
            t.setInwardServiceReportId(requestModel.getId());
            if (!Optional.ofNullable(t.getPiece()).isPresent()) {
               t.setPiece(BigInteger.ZERO);
            }
            if (!Optional.ofNullable(t.getWeight()).isPresent()) {
               t.setWeight(BigDecimal.ZERO);
            }
            if (StringUtils.isEmpty(t.getWeightUnitCode())) {
               t.setWeightUnitCode("K");
            }
            if ("shpver".equalsIgnoreCase(t.getDataTypes()) || "arrivaldata".equalsIgnoreCase(t.getDataTypes())
                  || "shipmentirr".equalsIgnoreCase(t.getDataTypes())) {
               t.setFlagCRUD(CRUDTypes.Type.CREATE);
            }
         });
         if (!manualServiceReportCreation) {
            requestModel.getShipmentDiscrepancy().forEach(t -> t.setFlagCRUD(CRUDTypes.Type.CREATE));
         }

         // Create document discrepancy
         dao.createShipmentDiscrepancy(requestModel.getShipmentDiscrepancy());

      }

      // 3. Add Shipment inventory discrepancy info
      if (!CollectionUtils.isEmpty(requestModel.getPhysicalDiscrepancy())) {

         List<InwardServiceReportShipmentDiscrepancyModel> createDataForPhysical = requestModel.getPhysicalDiscrepancy()
               .stream().filter(obj -> (Action.CREATE.toString().equalsIgnoreCase(obj.getFlagCRUD())))
               .collect(Collectors.toList());
         for (InwardServiceReportShipmentDiscrepancyModel value : createDataForPhysical) {
            if (ShipmentType.MAIL.toString().equalsIgnoreCase(value.getShipmentType())
                  && 20 != value.getShipmentNumber().length()) {
               throw new CustomException("INVALIDDISPATCH", "", ErrorType.ERROR);
            }
            Integer count = dao.checkDuplicateForDoc(value);
            if (count > 0) {
               throw new CustomException("DOCErr", form, ErrorType.ERROR);
            }
         }

         requestModel.getPhysicalDiscrepancy().forEach(t -> {
            t.setInwardServiceReportId(requestModel.getId());
            if(!StringUtils.isEmpty(t.getHouseNumber())) {            	
            	t.setHawbnumber(t.getHouseNumber());
            }
            if (("physhipirr".equalsIgnoreCase(t.getDataTypes())) || ("damgdata".equalsIgnoreCase(t.getDataTypes()))) {
               t.setFlagCRUD(CRUDTypes.Type.CREATE);
            }
         });

         if (!manualServiceReportCreation) {
            requestModel.getPhysicalDiscrepancy().forEach(t -> t.setFlagCRUD(CRUDTypes.Type.CREATE));
         }

         // Create Physical discrepancy
         dao.createShipmentDiscrepancy(requestModel.getPhysicalDiscrepancy());
      }

      // 4. Add other discrepancy info
      if (!CollectionUtils.isEmpty(requestModel.getOtherDiscrepancy())) {
         requestModel.getOtherDiscrepancy().forEach(t -> t.setInwardServiceReportId(requestModel.getId()));
         dao.creatOtherDiscrepancy(requestModel.getOtherDiscrepancy());
      }

      // 5. Creating Tracing Record
      if (!manualServiceReportCreation) {
         List<TracingActivityShipmentModel> tracingShipmentInfo = new ArrayList<>();
         // Document Discrepancy
         if (!CollectionUtils.isEmpty(requestModel.getShipmentDiscrepancy())) {
            for (InwardServiceReportShipmentDiscrepancyModel t : requestModel.getShipmentDiscrepancy()) {
               TracingActivityShipmentModel tracingActivityShipmentModel = new TracingActivityShipmentModel();
               tracingActivityShipmentModel.setShipmentType(t.getShipmentType());
               tracingActivityShipmentModel.setFlightId(requestModel.getFlightId());
               tracingActivityShipmentModel.setShipmentdate(t.getShipmentdate());
               tracingActivityShipmentModel.setShipmentNumber(t.getShipmentNumber());
               tracingActivityShipmentModel.setNatureOfGoodsDescription(t.getNatureOfGoodsDescription());
               tracingActivityShipmentModel.setPiece(t.getPiece());
               tracingActivityShipmentModel.setImportStaffName(requestModel.getCreatedBy());
               tracingActivityShipmentModel.setWeight(t.getWeight());
               tracingActivityShipmentModel.setWeightUnitCode(t.getWeightUnitCode());
               tracingActivityShipmentModel.setOrigin(t.getOrigin());
               tracingActivityShipmentModel.setDestination(t.getDestination());
               tracingActivityShipmentModel.setLoggedInUser(requestModel.getCreatedBy());
               tracingActivityShipmentModel.setCreatedBy(requestModel.getCreatedBy());
               tracingActivityShipmentModel.setCreatedOn(requestModel.getCreatedOn());
               tracingActivityShipmentModel.setHouseNumber(t.getHouseNumber());

               if (!StringUtils.isEmpty(t.getShipmentType())) {
                  tracingActivityShipmentModel.setTracingCreatedfor(t.getShipmentType());
               } else {
                  tracingActivityShipmentModel.setTracingCreatedfor(requestModel.getServiceReportFor());
               }

               if (t.getPartShipment()) {
                  tracingActivityShipmentModel.setIrregularitypieces(t.getIrregularityPieces());
                  tracingActivityShipmentModel.setIrregularityTypeCode(PART_SHIPMENT);
               } else {
                  tracingActivityShipmentModel.setIrregularityTypeCode(t.getIrregularityType());
                  tracingActivityShipmentModel.setIrregularitypieces(t.getIrregularityPieces());
               }
               tracingActivityShipmentModel.setModifiedBy(requestModel.getCreatedBy());
               tracingActivityShipmentModel.setTerminal(requestModel.getTerminal());
               tracingShipmentInfo.add(tracingActivityShipmentModel);
            }
         }
         // Physical Discrepancy
         if (!CollectionUtils.isEmpty(requestModel.getPhysicalDiscrepancy())) {
            for (InwardServiceReportShipmentDiscrepancyModel t : requestModel.getPhysicalDiscrepancy()) {
               TracingActivityShipmentModel tracingActivityShipmentModel = new TracingActivityShipmentModel();
               tracingActivityShipmentModel.setShipmentType(t.getShipmentType());
               tracingActivityShipmentModel.setFlightId(requestModel.getFlightId());
               tracingActivityShipmentModel.setShipmentdate(t.getShipmentdate());
               tracingActivityShipmentModel.setShipmentNumber(t.getShipmentNumber());
               tracingActivityShipmentModel.setNatureOfGoodsDescription(t.getNatureOfGoodsDescription());
               tracingActivityShipmentModel.setPiece(t.getPiece());
               tracingActivityShipmentModel.setWeight(t.getWeight());
               tracingActivityShipmentModel.setWeightUnitCode(t.getWeightUnitCode());
               tracingActivityShipmentModel.setOrigin(t.getOrigin());
               tracingActivityShipmentModel.setDestination(t.getDestination());

               if (!StringUtils.isEmpty(t.getShipmentType())) {
                  tracingActivityShipmentModel.setTracingCreatedfor(t.getShipmentType());
               } else {
               tracingActivityShipmentModel.setTracingCreatedfor(requestModel.getServiceReportFor());
               }

               tracingActivityShipmentModel.setHouseNumber(t.getHouseNumber());

               if (t.getPartShipment()) {
                  tracingActivityShipmentModel.setIrregularitypieces(t.getIrregularityPieces());
                  tracingActivityShipmentModel.setIrregularityTypeCode(PART_SHIPMENT);
               } else {
                  tracingActivityShipmentModel.setIrregularityTypeCode(t.getIrregularityType());
                  tracingActivityShipmentModel.setIrregularitypieces(t.getIrregularityPieces());
               }
               tracingActivityShipmentModel.setLoggedInUser(requestModel.getCreatedBy());
               tracingActivityShipmentModel.setCreatedBy(requestModel.getCreatedBy());
               tracingActivityShipmentModel.setCreatedOn(requestModel.getCreatedOn());
               tracingActivityShipmentModel.setModifiedBy(requestModel.getCreatedBy());
               tracingActivityShipmentModel.setTerminal(requestModel.getTerminal());
               tracingShipmentInfo.add(tracingActivityShipmentModel);
            }
         }

         // Check for Tracing Shipment Info
         if (!CollectionUtils.isEmpty(tracingShipmentInfo)) {
            tracingActivityService.createTracing(tracingShipmentInfo);
         }
      }
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
   public void finalizeServiceReportForCargo(InwardServiceReportModel requestModel) throws CustomException {
      // Finalize the inward service report for cargo
	   requestModel.setKey(requestModel.getFlightNumber());
	   requestModel.setDate1(requestModel.getFlightDate());
      if (!requestModel.getCheckstatus()) {
         Integer count = dao.checkDocumentCompletedOrNot(requestModel);
         if (count < 1) {
            throw new CustomException("DOCUMENT", form, ErrorType.ERROR);
         }
         Integer breakcount = dao.checkBreakdownCompletedOrNot(requestModel);
         if (breakcount < 1) {
            throw new CustomException("flight.breakdown.not.completed", form, ErrorType.ERROR);
         }
         // check for damage,if damage is there and not finalize then stop finalization
         // of inwardservice report
         Integer dmgcount = dao.checkDamageStatus(requestModel);

         if (dmgcount > 0 && ("Cargo Damage Not Raised").equalsIgnoreCase(requestModel.getDamagestatus())) {
            throw new CustomException("impbd.cargo.damage.not.finalized", form, ErrorType.ERROR);
         }
         requestModel.setFinalizedate(TenantZoneTime.getZoneDateTime(LocalDateTime.now(), requestModel.getTenantId()));
         requestModel.setFinalizeunfinalize("finalized");
         dao.finalizeServiceReportForCargo(requestModel);
         InwardServiceReportModel regdata = dao.getRegistrationNumber(requestModel);
         InwardServiceReportModel damagedata = dao.getDamageInfo(requestModel);
         // Raise event for post processing InwardServiceReport
         InwardReportEvent inwardReportEvent = new InwardReportEvent();
         inwardReportEvent.setFlightId(requestModel.getFlightId());
         inwardReportEvent.setFlightKey(requestModel.getFlightNumber());
         inwardReportEvent.setFlightDate(requestModel.getFlightDate());
         inwardReportEvent.setSegmentId(requestModel.getSegmentId());
         inwardReportEvent.setSegmentDesc(requestModel.getSegmentdesc());
         inwardReportEvent.setTenantId(requestModel.getTenantAirport());
         inwardReportEvent.setDamagestatus(requestModel.getDamagestatus());
         if (!ObjectUtils.isEmpty(regdata)) {
          inwardReportEvent.setRegistration(regdata.getRegistration());
         }
         inwardReportEvent.setCreatedOn(LocalDateTime.now());
         inwardReportEvent.setCreatedBy(requestModel.getLoggedInUser());
         if (!ObjectUtils.isEmpty(damagedata)) {
         inwardReportEvent.setCargoDamageCompletedAt(damagedata.getCargoDamageCompletedAt());
         inwardReportEvent.setCargoDamageCompletedBy(damagedata.getCargoDamageCompletedBy());
         inwardReportEvent.setInwardfinalizeBy(damagedata.getInwardServiceReportFinalizedBy());
         inwardReportEvent.setInwardfinalizeAt(damagedata.getFinalizeCompletedAt());
         }
         // Invoke the event
         this.inwardReportEventProducer.publish(inwardReportEvent);
         
         // Raise event for Damage Report
         DamageReportEvent damageReportEvent = new DamageReportEvent();
         damageReportEvent.setFlightId(requestModel.getFlightId());
         damageReportEvent.setFlightKey(requestModel.getFlightNumber());
         damageReportEvent.setFlightDate(requestModel.getFlightDate());
         if (!ObjectUtils.isEmpty(regdata)) {
         damageReportEvent.setRegistration(regdata.getRegistration());
         }
         damageReportEvent.setCreatedBy(requestModel.getLoggedInUser());
         // Invoke the event for damage cargo report
         this.damageReportEventProducer.publish(damageReportEvent);
      } else if (requestModel.getCheckstatus()) {
    	 requestModel.setFinalizeunfinalize("Un-Finalized");
    	 requestModel.setFinalizedate(null);
         dao.unFinalizeServiceReport(requestModel);
         
         //Reset the time
         requestModel.setFinalizedate(TenantZoneTime.getZoneDateTime(LocalDateTime.now(), requestModel.getTenantId()));
      }
   }

   @Override
   public List<InwardServiceReportModel> fetch(InwardServiceReportModel requestModel) throws CustomException {
      InwardServiceReportModel flightId = dao.fetchId(requestModel);

      if (flightId == null) {
         throw new CustomException("BOOKING1", form, ErrorType.ERROR);
      }

      requestModel.setFlightId(flightId.getFlightId());
      if (StringUtils.isEmpty(requestModel.getServiceReportFor())) {
         requestModel.setServiceReportFor(ShipmentType.AWB.toString());
      }
      // Get inward service report data
      List<InwardServiceReportModel> data1 = dao.fetchDiscrepancy(requestModel);
      Optional<List<InwardServiceReportModel>> oInwardServiceReport = Optional.ofNullable(data1);
      if (!oInwardServiceReport.isPresent()) {
         throw new CustomException("BOOKING1", form, ErrorType.ERROR);
      }
      if (!CollectionUtils.isEmpty(data1)) {
         for (InwardServiceReportModel value : data1) {
            List<InwardSegmentModel> segList = new ArrayList<>();
            if (CollectionUtils.isEmpty(value.getSegment())) {
               InwardSegmentModel segObj = new InwardSegmentModel();
               segList.add(segObj);
               value.setSegment(segList);
            }
            List<InwardSegmentConcatenate> segCoctList = new ArrayList<>();
            if (CollectionUtils.isEmpty(value.getSegmentConcatAwb())) {
               InwardSegmentConcatenate coctObj = new InwardSegmentConcatenate();
               coctObj.setShipmentDiscrepancy(new ArrayList<InwardServiceReportShipmentDiscrepancyModel>());
               coctObj.setPhysicalDiscrepancy(new ArrayList<InwardServiceReportShipmentDiscrepancyModel>());
               coctObj.setOtherDiscrepancy(new ArrayList<InwardServiceReportOtherDiscrepancyModel>());
               segCoctList.add(coctObj);
               value.setSegmentConcatAwb(segCoctList);
            }
         }
      }
      return data1;
   }

   @Override
   public List<InwardServiceReportModel> fetchBoardPoint(InwardServiceReportModel fetchboardpoint)
         throws CustomException {
      return dao.fetchboardpoints(fetchboardpoint);
   }

   @Override
   public void finalizeServiceReportForMail(InwardServiceReportModel requestModel) throws CustomException {
      Integer count = dao.checkDocumentCompletedOrNotForMail(requestModel);
      if (count < 1) {
         throw new CustomException("DOCUMENT", form, ErrorType.ERROR);
      }
      Integer breakcount = dao.checkBreakdownCompletedOrNotForMail(requestModel);
      if (breakcount < 1) {
         throw new CustomException("flight.breakdown.not.completed", form, ErrorType.ERROR);
      }
      dao.finalizeServiceReportForMail(requestModel);
   }

   @Override
   public void sendemailReport(InwardServiceReportModel requestModel) throws CustomException {
      InwardServiceReportModel regdata = dao.getRegistrationNumber(requestModel);
      InwardServiceReportModel damagedata = dao.getDamageInfo(requestModel);
      // Raise event for post processing InwardServiceReport
      InwardReportEvent inwardReportEvent = new InwardReportEvent();
      inwardReportEvent.setFlightId(requestModel.getFlightId());
      inwardReportEvent.setFlightKey(requestModel.getFlightNumber());
      inwardReportEvent.setFlightDate(requestModel.getFlightDate());
      inwardReportEvent.setSegmentId(requestModel.getSegmentId());
      inwardReportEvent.setSegmentDesc(requestModel.getSegmentdesc());
      inwardReportEvent.setTenantId(requestModel.getTenantAirport());
      inwardReportEvent.setDamagestatus(requestModel.getDamagestatus());
      if (!ObjectUtils.isEmpty(regdata)) {
      inwardReportEvent.setRegistration(regdata.getRegistration());
      }
      inwardReportEvent.setCreatedOn(LocalDateTime.now());
      inwardReportEvent.setCreatedBy(requestModel.getLoggedInUser());
      if (!ObjectUtils.isEmpty(damagedata)) {
      inwardReportEvent.setCargoDamageCompletedAt(damagedata.getCargoDamageCompletedAt());
      inwardReportEvent.setCargoDamageCompletedBy(damagedata.getCargoDamageCompletedBy());
      inwardReportEvent.setInwardfinalizeBy(damagedata.getInwardServiceReportFinalizedBy());
      inwardReportEvent.setInwardfinalizeAt(damagedata.getFinalizeCompletedAt());
      }
      // Invoke the event
      this.inwardReportEventProducer.publish(inwardReportEvent);
   }

   @Override
   public void createServiceReportForSave(InwardServiceReportModel requestModel) throws CustomException {
      InwardServiceReportModel segSave = new InwardServiceReportModel();
      for (InwardSegmentModel segModel : requestModel.getSegment()) {
         segSave.setFlightId(segModel.getFlightId());
         segSave.setSegmentId(segModel.getSegmentId());
         segSave.setActionTaken(segModel.getActionTaken());
         segSave.setNatureOfDiscrepancies(segModel.getNatureOfDiscrepancies());
         segSave.setManifestedPages(segModel.getManifestedPages());
         segSave.setKey(requestModel.getKey());
         segSave.setDate1(requestModel.getDate1());
         segSave.setLoggedInUser(requestModel.getCreatedBy());
         List<InwardServiceReportShipmentDiscrepancyModel> shipmentDiscrepancy = new ArrayList<>();
         List<InwardServiceReportShipmentDiscrepancyModel> physicalDiscrepancy = new ArrayList<>();
         List<InwardServiceReportOtherDiscrepancyModel> otherDiscrepancy = new ArrayList<>();
         for (InwardSegmentConcatenate segAwb : requestModel.getSegmentConcatAwb()) {
            if (segModel.getSegmentId().equalsIgnoreCase(segAwb.getSegmentId())) {
               BigInteger inwId = segAwb.getId();

               List<InwardServiceReportShipmentDiscrepancyModel> createShipment = segAwb.getShipmentDiscrepancy();
               List<InwardServiceReportShipmentDiscrepancyModel> createPhysical = segAwb.getPhysicalDiscrepancy();
               List<InwardServiceReportOtherDiscrepancyModel> createOther = segAwb.getOtherDiscrepancy();

               if (!CollectionUtils.isEmpty(createShipment)) {
                  for (InwardServiceReportShipmentDiscrepancyModel shpDisc : createShipment) {
                     InwardServiceReportShipmentDiscrepancyModel shpDscObj = new InwardServiceReportShipmentDiscrepancyModel();
                     shpDscObj.setShipmentNumber(shpDisc.getShipmentNumber());
                     shpDscObj.setShipmentId(shpDisc.getShipmentId());
                     shpDscObj.setDataTypes(shpDisc.getDataTypes());
                     shpDscObj.setPartShipment(shpDisc.getPartShipment());
                     shpDscObj.setPhotoCopy(shpDisc.getPhotoCopy());
                     shpDscObj.setOrigin(shpDisc.getOrigin());
                     shpDscObj.setDestination(shpDisc.getDestination());
                     shpDscObj.setPiece(shpDisc.getPiece());
                     shpDscObj.setShipmentType(shpDisc.getShipmentType());
                     shpDscObj.setWeight(shpDisc.getWeight());
                     shpDscObj.setWeightUnitCode(shpDisc.getWeightUnitCode());
                     shpDscObj.setNatureOfGoodsDescription(shpDisc.getNatureOfGoodsDescription());
                     shpDscObj.setIrregularityType(shpDisc.getIrregularityType());
                     shpDscObj.setIrregularityPieces(shpDisc.getIrregularityPieces());
                     shpDscObj.setIrregularityDescription(shpDisc.getIrregularityDescription());
                     shpDscObj.setRemarks(shpDisc.getRemarks());
                     shpDscObj.setFlagCRUD(shpDisc.getFlagCRUD());
                     shpDscObj.setDiscrepancyType(shpDisc.getDiscrepancyType());
                     shpDscObj.setShipmentdate(shipmentProcessorService.getShipmentDate(shpDscObj.getShipmentNumber()));
                     shpDscObj.setManual(shpDisc.getManual());
                     shpDscObj.setInwardServiceReportId(inwId);
                     shpDscObj.setId(inwId);
                     shpDscObj.setCreatedBy(shpDisc.getCreatedBy());
                
                     shpDscObj.setLoggedInUser(shpDisc.getCreatedBy());
                     shipmentDiscrepancy.add(shpDscObj);
                  }
               }

               if (!CollectionUtils.isEmpty(createPhysical)) {
                  for (InwardServiceReportShipmentDiscrepancyModel phyDisc : createPhysical) {
                     InwardServiceReportShipmentDiscrepancyModel phyDscObj = new InwardServiceReportShipmentDiscrepancyModel();
                     phyDscObj.setShipmentNumber(phyDisc.getShipmentNumber());
                     phyDscObj.setShipmentId(phyDisc.getShipmentId());
                     phyDscObj.setShipmentType(phyDisc.getShipmentType());
                     phyDscObj.setDataTypes(phyDisc.getDataTypes());
                     phyDscObj.setOrigin(phyDisc.getOrigin());
                     phyDscObj.setDestination(phyDisc.getDestination());
                     phyDscObj.setPiece(phyDisc.getPiece());
                     phyDscObj.setWeight(phyDisc.getWeight());
                     phyDscObj.setWeightUnitCode(phyDisc.getWeightUnitCode());
                     phyDscObj.setNatureOfGoodsDescription(phyDisc.getNatureOfGoodsDescription());
                     phyDscObj.setIrregularityType(phyDisc.getIrregularityType());
                     phyDscObj.setIrregularityPieces(phyDisc.getIrregularityPieces());
                     phyDscObj.setIrregularityDescription(phyDisc.getIrregularityDescription());
                     phyDscObj.setRemarks(phyDisc.getRemarks());
                     phyDscObj.setFlagCRUD(phyDisc.getFlagCRUD());
                     phyDscObj.setDiscrepancyType(phyDisc.getDiscrepancyType());
                     phyDscObj.setShipmentdate(shipmentProcessorService.getShipmentDate(phyDscObj.getShipmentNumber()));
                     phyDscObj.setManual(phyDisc.getManual());
                     phyDscObj.setInwardServiceReportId(inwId);
                     phyDscObj.setCreatedBy(phyDisc.getCreatedBy());
                     phyDscObj.setLoggedInUser(phyDisc.getCreatedBy());
                     //New addition for HAWB insert
                     phyDscObj.setHawbnumber(phyDisc.getHawbnumber());                
                                                    
                     phyDscObj.setId(inwId);
                     
						
                     
                     physicalDiscrepancy.add(phyDscObj);
                  }
               }

               if (!CollectionUtils.isEmpty(createOther)) {
                  for (InwardServiceReportOtherDiscrepancyModel othDisc : createOther) {
                     InwardServiceReportOtherDiscrepancyModel othDscObj = new InwardServiceReportOtherDiscrepancyModel();
                     othDscObj.setRemarks(othDisc.getRemarks());
                     othDscObj.setFlagCRUD(othDisc.getFlagCRUD());
                     othDscObj.setInwardServiceReportId(inwId);
                     othDscObj.setCreatedBy(othDisc.getCreatedBy());
                     othDscObj.setLoggedInUser(othDisc.getCreatedBy());
                     othDscObj.setKey(segSave.getKey());
                     othDscObj.setDate1(segSave.getDate1());
                     othDscObj.setId(othDisc.getId());
                     otherDiscrepancy.add(othDscObj);
                  }
               }
            }
         }
         segSave.setShipmentDiscrepancy(shipmentDiscrepancy);
         segSave.setPhysicalDiscrepancy(physicalDiscrepancy);
         segSave.setOtherDiscrepancy(otherDiscrepancy);
         this.createServiceReport(segSave);
      }

   }

   @Override
   public InwardServiceReportShipmentDiscrepancyModel getAwbDetails(
         InwardServiceReportShipmentDiscrepancyModel requestModel) throws CustomException {
      InwardServiceReportShipmentDiscrepancyModel data = dao.getAwbDetails(requestModel);
      requestModel.setOrigin(data.getOrigin());
      requestModel.setDestination(data.getDestination());
      requestModel.setPiece(data.getPiece());
      requestModel.setWeight(data.getWeight());
      requestModel.setWeightUnitCode(data.getWeightUnitCode());
      requestModel.setNatureOfGoodsDescription(data.getNatureOfGoodsDescription());

      return requestModel;
   }

}