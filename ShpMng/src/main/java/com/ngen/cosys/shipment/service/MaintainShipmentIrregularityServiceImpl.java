/**
 * 
 * MaintainShipmentIrregularityServiceImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 4 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent;
import com.ngen.cosys.events.producer.InboundShipmentPiecesEqualsToBreakDownPiecesStoreEventProducer;
import com.ngen.cosys.damage.enums.FlagCRUD;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.shipment.dao.CommonFlightIdDAOImpl;
import com.ngen.cosys.shipment.dao.MaintainShipmentIrregularityDAOImpl;
import com.ngen.cosys.shipment.enums.ShipmentIrregularity;
import com.ngen.cosys.shipment.model.CommonFlightId;
import com.ngen.cosys.shipment.model.IrregularityDetail;
import com.ngen.cosys.shipment.model.IrregularitySummary;
import com.ngen.cosys.shipment.model.SearchShipmentIrregularity;

/**
 * This class takes care of the responsibilities related to Maintaining Shipment
 * Irregularity service operation that comes from the controller.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
public class MaintainShipmentIrregularityServiceImpl implements MaintainShipmentIrregularityService {

   private static final Logger LOGGER = LoggerFactory.getLogger(MaintainShipmentIrregularityService.class);

   private static final String EXCEPTION = "Exception Happened ... ";

   private static final String SHPIRR_EXP_FLT = "SHPIRR_EXP_FLT";

   private static final String SHPIRR_IMP_FLT = "SHPIRR_IMP_FLT";

   private static final String SHPIRR_INV_FLT = "SHPIRR_INV_FLT";

   @Autowired
   private MaintainShipmentIrregularityDAOImpl maintainirregularityDAO;

   @Autowired
   private CommonFlightIdDAOImpl commonDAO;
   
   @Autowired
   private InboundShipmentPiecesEqualsToBreakDownPiecesStoreEventProducer producer;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.service.MaintainShipmentIrregularityService#search(
    * com.ngen.cosys.shipment.model.SearchShipmentIrregularity)
    */
   @Override
   public IrregularitySummary search(SearchShipmentIrregularity search) throws CustomException {
      IrregularitySummary irrSum = new IrregularitySummary();
      if (search.getShipmentType().isEmpty()) {
         search.addError("SHPIRR_SHPTYP_NULL", "shipmentType", ErrorType.ERROR);
      } else if (search.getShipmentNumber().isEmpty()) {
         search.addError("SHPIRR_SHPNUM_NUL", "shipmentNumber", ErrorType.ERROR);
      } else {
         irrSum = maintainirregularityDAO.search(search);
         if (null == irrSum) {
            search.addError("SHPIRR_NO_RECORD", null, ErrorType.NOTIFICATION);
         } else {
            getIrrDetails(search, irrSum);
            getIrrDetailsHAWB(search, irrSum);
         }
      }
      if (search.getMessageList().isEmpty()) {
         return irrSum;
      } else {
         throw new CustomException();
      }
   }

   /**
    * @param search
    * @param irrSum
    * @throws CustomException
    */
   private void getIrrDetails(SearchShipmentIrregularity search, IrregularitySummary irrSum) throws CustomException {
      List<IrregularityDetail> list = maintainirregularityDAO.fetchFlightDetails(search);
      list.forEach(obj -> {
         if (!StringUtils.isEmpty(obj.getFlightKey())) {
            CommonFlightId c = new CommonFlightId();
            c.setFlightKey(obj.getFlightKey());
            c.setSource(irrSum.getOrigin());
            c.setDestination(irrSum.getDestination());
			try {
				List<CommonFlightId> listData = commonDAO.getFlightKeyDate(c);
				if (!CollectionUtils.isEmpty(listData)) {
					obj.setFlightKey(listData.get(0).getFlightKey());
					obj.setFlightDate(listData.get(0).getFlightDate());
				}
			} catch (CustomException e) {
					LOGGER.error(EXCEPTION, e);
			}
         } else {
            obj.setFlightKey(null);
            obj.setFlightDate(null);
         }
      });
      irrSum.setIrregularityDetails(list);
   }
   
   private void getIrrDetailsHAWB(SearchShipmentIrregularity search, IrregularitySummary irrSum) throws CustomException {
	      List<IrregularityDetail> list = maintainirregularityDAO.fetchHAWBIrregularity(search);
	      list.forEach(obj -> {
	         if (!StringUtils.isEmpty(obj.getFlightKey())) {
	            CommonFlightId c = new CommonFlightId();
	            c.setFlightKey(obj.getFlightKey());
	            c.setSource(irrSum.getOrigin());
	            c.setDestination(irrSum.getDestination());
				try {
					List<CommonFlightId> listData = commonDAO.getFlightKeyDate(c);
					if (!CollectionUtils.isEmpty(listData)) {
						obj.setFlightKey(listData.get(0).getFlightKey());
						obj.setFlightDate(listData.get(0).getFlightDate());
					}
				} catch (CustomException e) {
						LOGGER.error(EXCEPTION, e);
				}
	         } else {
	            obj.setFlightKey(null);
	            obj.setFlightDate(null);
	         }
	      });
	      irrSum.setIrregularityDetailsHAWB(list);
	   }


   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.service.MaintainShipmentIrregularityService#add(java.
    * util.List)
    */
   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public IrregularitySummary add(IrregularitySummary maintain) throws CustomException {
      List<IrregularityDetail> maintainList = maintain.getIrregularityDetails();
      
      //for HAWB Irregularity
      List<IrregularityDetail> maintainListHAWB = maintain.getIrregularityDetailsHAWB();
      for(IrregularityDetail mlist:maintainListHAWB) {
    	  mlist.setHawbNumber(maintain.getHawbNumber());
      }
      
      this.checkValidations(maintainList,maintain);
      
      if(!CollectionUtils.isEmpty(maintainListHAWB)) {
      this.checkValidations(maintainListHAWB,maintain);}
      

      if (maintain.getMessageList().isEmpty()) {
         int index = 0;
         for (IrregularityDetail a : maintain.getIrregularityDetails()) {
            if (!a.getMessageList().isEmpty() && !a.getFlagCRUD().equalsIgnoreCase(Action.DELETE.toString())) {
               index++;
            }
         }
         if (index > 0) {
            throw new CustomException();
         }
      }

      // Save the irregularity
      this.save(maintain);

      // Delete the irregularity
      this.delete(maintainList);
      
      //Delete HAWB Irregularity
      if(!CollectionUtils.isEmpty(maintainListHAWB)) {
      this.delete(maintainListHAWB);}
  

      List<IrregularityDetail> irrAddList = maintainList.stream()
            .filter(a -> a.getFlagCRUD().equalsIgnoreCase(Action.CREATE.toString())).collect(Collectors.toList());
      
     for (IrregularityDetail add : irrAddList) {
         flightValidationForAdd(maintain, add);
      }
     
     //New HAWB Irregularity Add
     if(!CollectionUtils.isEmpty(maintainListHAWB)) {
     List<IrregularityDetail> irrAddListHAWB = maintainListHAWB.stream()
             .filter(a -> a.getFlagCRUD().equalsIgnoreCase(Action.CREATE.toString())).collect(Collectors.toList());
     
     for (IrregularityDetail add : irrAddListHAWB) {
         flightValidationForAdd(maintain, add);
      }
     }
     
     if (!maintain.getMessageList().isEmpty()) {
         int index = 0;
         for (IrregularityDetail a : maintain.getIrregularityDetails()) {
            if (a.getMessageList().isEmpty()) {
               index++;
            }
         }
         if (index == maintain.getIrregularityDetails().size()) {
            throw new CustomException();
         } else {
            return maintain;
         }
      } else {
         return maintain;
      }
   }
   
   private void checkValidations(List<IrregularityDetail> maintainValid,IrregularitySummary maintain) throws CustomException{
	   
	   for (IrregularityDetail irr : maintainValid) {
	         irr.setSource(maintain.getOrigin());
	         irr.setDestination(maintain.getDestination());
	         irr.setSegment(maintainirregularityDAO.getFlightSegment(irr));
	         if(!StringUtils.isEmpty(irr.getFlightKey()))
	        	 irr.setFlight_No(irr.getFlightKey());
	         
	         Boolean docFlag = maintainirregularityDAO.checkDocumentFlag(irr);
	        //Checking validations for AWB irregularity
	         if(irr.getHawbNumber()==null) {
	         if(!irr.getFlagCRUD().equalsIgnoreCase("D") && irr.getIrregularityType().equalsIgnoreCase("MSAW") &&  (docFlag != null && docFlag)) {
	        	irr.addError("awb.original.doc.avail.msaw.not.allowed", "document info", ErrorType.ERROR);
	         }
	         
	         if (!irr.getFlagCRUD().equalsIgnoreCase("D") && !irr.getIrregularityType().equalsIgnoreCase("FDAW") && !irr.getIrregularityType().equalsIgnoreCase("MSAW")
	               && irr.getPieces().compareTo(new BigInteger("0")) == 0) {
	            irr.addError("shpIrr.pieces.required", "pieces", ErrorType.ERROR);
	         }
	         }
	         
	         //Checking validations for HAWB Irregularities
	         if(irr.getHawbNumber()!=null) {
	        	 if(irr.getPieces()==null) {
	        		 irr.setPieces(new BigInteger("0"));
	        	 }
	         if (!irr.getFlagCRUD().equalsIgnoreCase("D") && irr.getIrregularityType().equalsIgnoreCase("FDAW") ) {
		            irr.addError("hawb.irregularity.not.allowed", "", ErrorType.ERROR);
		         }
		         
		         if (!irr.getFlagCRUD().equalsIgnoreCase("D") && irr.getIrregularityType().equalsIgnoreCase("MSAW")) {
			            irr.addError("hawb.irregularity.not.allowed", "", ErrorType.ERROR);
			         }
		       
		         if (!irr.getFlagCRUD().equalsIgnoreCase("D") && !irr.getIrregularityType().equalsIgnoreCase("FDAW") && !irr.getIrregularityType().equalsIgnoreCase("MSAW")
			               && irr.getPieces().compareTo(new BigInteger("0")) == 0) {
			            irr.addError("shpIrr.pieces.required", "pieces", ErrorType.ERROR);
			         }
	         }
		         Boolean acceptanceFlag = maintainirregularityDAO.checkAcceptanceFlag(irr);
	         
	         if(acceptanceFlag == null || !acceptanceFlag) {
	        	 if(!irr.getFlagCRUD().equalsIgnoreCase(FlagCRUD.Type.DELETE)) {
		        	 if(!Optional.ofNullable(irr.getFlightKey()).isPresent() || !Optional.ofNullable(irr.getFlightDate()).isPresent() || !Optional.ofNullable(irr.getFlightSegmentId()).isPresent() || irr.getFlightSegmentId().compareTo(BigInteger.ZERO) == 0) {
		        		if(!Optional.ofNullable(irr.getFlightKey()).isPresent() || !Optional.ofNullable(irr.getFlightDate()).isPresent()) {
		        			irr.addError("exp.accptweighing.flightdetailmandatory", "", ErrorType.ERROR);
		        		} else if(!Optional.ofNullable(irr.getFlightSegmentId()).isPresent() || irr.getFlightSegmentId().compareTo(BigInteger.ZERO) == 0) {
		        			irr.addError("flight.segment.mandatory", "", ErrorType.ERROR);
		        		} 
		        	 } else {
		        		 if (MultiTenantUtility.isTenantCityOrAirport(maintain.getDestination()) && !maintainirregularityDAO.checkForFlightDetails(irr)) {
		                     irr.addError("DATESTACHECK", "flightKey", ErrorType.ERROR);
		                  }
		        	 }
	        	 }
	         }
	         
	      }
	   
   }
   

   private void flightValidationForAdd(IrregularitySummary maintain, IrregularityDetail add) throws CustomException {
      if (MultiTenantUtility.isTenantCityOrAirport(maintain.getOrigin())) {
         int keyIndex = maintain.getIrregularityDetails().indexOf(add);
         if (StringUtils.isEmpty(add.getFlightKey()) && !StringUtils.isEmpty(add.getFlightDate())) {
            maintain.addError(ShipmentIrregularity.SHPIRR_FLT_KEY.getEnum(),
                  ShipmentIrregularity.IRR_DETAIL_ERR.getEnum() + keyIndex + ShipmentIrregularity.IRR_FLT_KEY.getEnum(),
                  ErrorType.ERROR);
         } else if (!StringUtils.isEmpty(add.getFlightKey()) && StringUtils.isEmpty(add.getFlightDate())) {
            maintain.addError(ShipmentIrregularity.SHPIRR_FLT_DATE.getEnum(),
                  ShipmentIrregularity.IRR_DETAIL_ERR.getEnum() + keyIndex
                        + ShipmentIrregularity.IRR_FLT_DATE.getEnum(),
                  ErrorType.ERROR);
            
         } else if (StringUtils.isEmpty(add.getFlightKey()) && StringUtils.isEmpty(add.getFlightDate())) {
            add.setFlightKey(null);

            maintainirregularityDAO.add(add);
         } else {

            performDefaultAdd(maintain, add);
         }
      } else if ( MultiTenantUtility.isTenantCityOrAirport(maintain.getDestination())
            || MultiTenantUtility.isTranshipment(maintain.getOrigin(), maintain.getDestination())) {
         validImportExportFlightCheckForAdd(maintain, add);
      }
   }

   private void performDefaultAdd(IrregularitySummary maintain, IrregularityDetail add) throws CustomException {
      CommonFlightId c = new CommonFlightId();
      c.setFlightKey(add.getFlightKey());
      c.setFlightDate(add.getFlightDate());
      c.setSource(maintain.getOrigin());
      c.setDestination(maintain.getDestination());
      String flightId = commonDAO.getFlightId(c);
      add.setSource(maintain.getOrigin());
      add.setDestination(maintain.getDestination());
      add.setOldflightKey(add.getFlightKey());
     // add.setHawbNumber(maintain.getHawbNumber());
      IrregularityDetail irrDet = checkAdd(add, flightId);
      if (null == irrDet) {
         maintainirregularityDAO.add(add);
      }
   }

   private void validImportExportFlightCheckForAdd(IrregularitySummary maintain, IrregularityDetail add)
         throws CustomException {
      int keyIndex = maintain.getIrregularityDetails().indexOf(add);
      if (StringUtils.isEmpty(add.getFlightKey()) && !StringUtils.isEmpty(add.getFlightDate())) {
         maintain.addError(ShipmentIrregularity.SHPIRR_FLT_KEY.getEnum(),
               ShipmentIrregularity.IRR_DETAIL_ERR.getEnum() + keyIndex + ShipmentIrregularity.IRR_FLT_KEY.getEnum(),
               ErrorType.ERROR);
      } else if (!StringUtils.isEmpty(add.getFlightKey()) && StringUtils.isEmpty(add.getFlightDate())) {
         maintain.addError(ShipmentIrregularity.SHPIRR_FLT_DATE.getEnum(),
               ShipmentIrregularity.IRR_DETAIL_ERR.getEnum() + keyIndex + ShipmentIrregularity.IRR_FLT_DATE.getEnum(),
               ErrorType.ERROR);
      } else if (StringUtils.isEmpty(add.getFlightKey()) && StringUtils.isEmpty(add.getFlightDate())) {
         maintain.addError(ShipmentIrregularity.VAL_FID_IN.getEnum(),
               ShipmentIrregularity.IRR_DETAIL_ERR.getEnum() + keyIndex + ShipmentIrregularity.IRR_FLT_KEY.getEnum(),
               ErrorType.ERROR);
         maintain.addError(ShipmentIrregularity.VAL_FID_IN.getEnum(),
               ShipmentIrregularity.IRR_DETAIL_ERR.getEnum() + keyIndex + ShipmentIrregularity.IRR_FLT_DATE.getEnum(),
               ErrorType.ERROR);
      } else {
         Integer count = maintainirregularityDAO.checkforduplicate(add);
         if (count > 0) {
            maintain.addError("Duplicate irregularity type not allowed for same flight/date",
                  "maintainIrregularityform", ErrorType.ERROR);
            throw new CustomException();
         }
         performDefaultAdd(maintain, add);
      }
   }

   private IrregularityDetail checkAdd(IrregularityDetail add, String flightId) throws CustomException {
      if (flightId != null) {

         CommonFlightId cc = new CommonFlightId();
         cc.setFlightKey(flightId);
         cc.setSource(add.getSource());
         cc.setDestination(add.getDestination());
         List<CommonFlightId> coList = commonDAO.getFlightKeyDate(cc);
         CommonFlightId co = coList.get(0);

         if (coList.size() == 1 && MultiTenantUtility.isTenantAirport(add.getSource())
               && MultiTenantUtility.isTenantAirport(co.getDestination())) {
            add.addError(SHPIRR_EXP_FLT, ShipmentIrregularity.FLT_KEY.getEnum(), ErrorType.ERROR);
            add.addError(SHPIRR_EXP_FLT, ShipmentIrregularity.FLT_DATE.getEnum(), ErrorType.ERROR);
         } else if (coList.size() == 1 && MultiTenantUtility.isTenantAirport(add.getDestination())
               && MultiTenantUtility.isTenantAirport(co.getSource())) {
            add.addError(SHPIRR_IMP_FLT, ShipmentIrregularity.FLT_KEY.getEnum(), ErrorType.ERROR);
            add.addError(SHPIRR_IMP_FLT, ShipmentIrregularity.FLT_DATE.getEnum(), ErrorType.ERROR);
         } else {
            add.setFlightKey(flightId);
         }
      } else {
         add.addError(SHPIRR_INV_FLT, ShipmentIrregularity.FLT_KEY.getEnum(), ErrorType.ERROR);
         add.addError(SHPIRR_INV_FLT, ShipmentIrregularity.FLT_DATE.getEnum(), ErrorType.ERROR);
      }
      if (add.getMessageList().isEmpty()) {
         return null;
      } else {
         return add;
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.service.MaintainShipmentIrregularityService#save(java
    * .util.List)
    */
   @Override
   public IrregularitySummary save(IrregularitySummary maintain) throws CustomException {
	   
	  /* if (!maintain.getIrregularityDetailsHAWB().getFlagCRUD().equalsIgnoreCase("D") && irr.getIrregularityType().equalsIgnoreCase("FDAW") ) {
           irr.addError("", "", ErrorType.ERROR);
        }*/

      List<IrregularityDetail> listSave = maintain.getIrregularityDetails().stream()
            .filter(a -> a.getFlagCRUD().equalsIgnoreCase(Action.UPDATE.toString())).collect(Collectors.toList());
      
      for (IrregularityDetail update : listSave) {
         flightValidationForSave(maintain, update);
      }
      
      //To Update HAWB Irregularity
      if(!CollectionUtils.isEmpty(maintain.getIrregularityDetailsHAWB())) {
      List<IrregularityDetail> listSaveHAWB = maintain.getIrregularityDetailsHAWB().stream()
              .filter(a -> a.getFlagCRUD().equalsIgnoreCase(Action.UPDATE.toString())).collect(Collectors.toList());
      for (IrregularityDetail update : listSaveHAWB) {
          flightValidationForSave(maintain, update);
       }
      }
      
      if (!maintain.getMessageList().isEmpty()) {
         throw new CustomException();
      } else {
         return maintain;
      }
   }

   private void flightValidationForSave(IrregularitySummary maintain, IrregularityDetail update)
         throws CustomException {
      if (MultiTenantUtility.isTenantCityOrAirport(maintain.getOrigin())) {
         int keyIndex = maintain.getIrregularityDetails().indexOf(update);
         if (StringUtils.isEmpty(update.getFlightKey()) && !StringUtils.isEmpty(update.getFlightDate())) {
            maintain.addError(ShipmentIrregularity.SHPIRR_FLT_KEY.getEnum(),
                  ShipmentIrregularity.IRR_DETAIL_ERR.getEnum() + keyIndex + ShipmentIrregularity.IRR_FLT_KEY.getEnum(),
                  ErrorType.ERROR);
         } else if (!StringUtils.isEmpty(update.getFlightKey()) && StringUtils.isEmpty(update.getFlightDate())) {
            maintain.addError(ShipmentIrregularity.SHPIRR_FLT_DATE.getEnum(),
                  ShipmentIrregularity.IRR_DETAIL_ERR.getEnum() + keyIndex
                        + ShipmentIrregularity.IRR_FLT_DATE.getEnum(),
                  ErrorType.ERROR);
         } else if (StringUtils.isEmpty(update.getFlightKey()) && StringUtils.isEmpty(update.getFlightDate())) {
            update.setFlightKey(null);
            maintainirregularityDAO.save(update);
         } else {
            performDefaultSave(maintain, update);
         }
      } else {
         validImportExportFlightCheckForSave(maintain, update);
      }
   }

   private void validImportExportFlightCheckForSave(IrregularitySummary maintain, IrregularityDetail update)
         throws CustomException {
      int keyIndex = maintain.getIrregularityDetails().indexOf(update);
      if (StringUtils.isEmpty(update.getFlightKey()) && !StringUtils.isEmpty(update.getFlightDate())) {
         maintain.addError(ShipmentIrregularity.SHPIRR_FLT_KEY.getEnum(),
               ShipmentIrregularity.IRR_DETAIL_ERR.getEnum() + keyIndex + ShipmentIrregularity.IRR_FLT_KEY.getEnum(),
               ErrorType.ERROR);
      } else if (!StringUtils.isEmpty(update.getFlightKey()) && StringUtils.isEmpty(update.getFlightDate())) {
         maintain.addError(ShipmentIrregularity.SHPIRR_FLT_DATE.getEnum(),
               ShipmentIrregularity.IRR_DETAIL_ERR.getEnum() + keyIndex + ShipmentIrregularity.IRR_FLT_DATE.getEnum(),
               ErrorType.ERROR);
      } else if (StringUtils.isEmpty(update.getFlightKey()) && StringUtils.isEmpty(update.getFlightDate())) {
         maintain.addError(ShipmentIrregularity.VAL_FID_IN.getEnum(),
               ShipmentIrregularity.IRR_DETAIL_ERR.getEnum() + keyIndex + ShipmentIrregularity.IRR_FLT_KEY.getEnum(),
               ErrorType.ERROR);
         maintain.addError(ShipmentIrregularity.VAL_FID_IN.getEnum(),
               ShipmentIrregularity.IRR_DETAIL_ERR.getEnum() + keyIndex + ShipmentIrregularity.IRR_FLT_DATE.getEnum(),
               ErrorType.ERROR);
      } else {
         performDefaultSave(maintain, update);
      }
   }

   private void performDefaultSave(IrregularitySummary maintain, IrregularityDetail update) throws CustomException {
      CommonFlightId c = new CommonFlightId();
      c.setFlightKey(update.getFlightKey());
      c.setFlightDate(update.getFlightDate());
      c.setSource(maintain.getOrigin());
      c.setDestination(maintain.getDestination());
      String flightId = commonDAO.getFlightId(c);
      update.setSource(maintain.getOrigin());
      update.setDestination(maintain.getDestination());
      IrregularityDetail irrDet = checkAdd(update, flightId);

      if (irrDet == null) {
         maintainirregularityDAO.save(update);
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.service.MaintainShipmentIrregularityService#delete(
    * com.ngen.cosys.shipment.model.IrregularityDetail)
    */
   @Override
   public IrregularityDetail delete(List<IrregularityDetail> maintainList) throws CustomException {
      List<IrregularityDetail> listDelete = maintainList.stream()
            .filter(a -> a.getFlagCRUD().equalsIgnoreCase(Action.DELETE.toString())).collect(Collectors.toList());
      for (IrregularityDetail irrDetail : listDelete) {
         maintainirregularityDAO.delete(irrDetail);
      }
      return new IrregularityDetail();
   }
   
   

   @Override
   public BigInteger getFlightId(IrregularityDetail irregularityInfo) throws CustomException {
      return maintainirregularityDAO.getFlightId(irregularityInfo);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.service.MaintainShipmentIrregularityService#
    * raiseBreakDownEvent(com.ngen.cosys.shipment.model.IrregularityDetail)
    */
   @Override
   public void raiseBreakDownEvent(IrregularityDetail irregularityInfo) throws CustomException {
      // Raise the event for RCF/NFD
      InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent payload = new InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent();
      payload.setShipmentId(irregularityInfo.getShipmentId());
      payload.setFlightId(irregularityInfo.getFlightId());
      payload.setPieces(irregularityInfo.getPieces());
      payload.setWeight(irregularityInfo.getWeight());
      payload.setStatus(EventStatus.NEW.getStatus());
      payload.setConfirmedAt(LocalDateTime.now());
      payload.setConfirmedBy(irregularityInfo.getLoggedInUser());
      payload.setCreatedBy(irregularityInfo.getLoggedInUser());
      payload.setCreatedOn(LocalDateTime.now());
      payload.setFunction("Shipment Irregularity");
      payload.setEventName(EventTypes.Names.INBOUND_SHIPMENT_PIECES_EQUALS_TO_BREAK_DOWN_PIECES_EVENT);
      producer.publish(payload);
   }

}