package com.ngen.cosys.impbd.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.common.validator.LoadingMoveableLocationValidator;
import com.ngen.cosys.common.validator.MoveableLocationTypeModel;
import com.ngen.cosys.common.validator.MoveableLocationTypeProcess;
import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.payload.InboundULDCheckInStoreEvent;
import com.ngen.cosys.events.payload.OutboundUCMMessageEvent;
import com.ngen.cosys.events.producer.InboundULDCheckInStoreEventProducer;
import com.ngen.cosys.events.producer.OutboundUCMMessageStoreEventProducer;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.dao.RampCheckInDAO;
import com.ngen.cosys.impbd.model.RampCheckInDetails;
import com.ngen.cosys.impbd.model.RampCheckInList;
import com.ngen.cosys.impbd.model.RampCheckInModel;
import com.ngen.cosys.impbd.model.RampCheckInParentModel;
import com.ngen.cosys.impbd.model.RampCheckInPendVerified;
import com.ngen.cosys.impbd.model.RampCheckInPending;
import com.ngen.cosys.impbd.model.RampCheckInPiggyback;
import com.ngen.cosys.impbd.model.RampCheckInPiggybackList;
import com.ngen.cosys.impbd.model.RampCheckInPiggybackModel;
import com.ngen.cosys.impbd.model.RampCheckInSHC;
import com.ngen.cosys.impbd.model.RampCheckInSHCInput;
import com.ngen.cosys.impbd.model.RampCheckInSearchFlight;
import com.ngen.cosys.impbd.model.RampCheckInSegmentList;
import com.ngen.cosys.impbd.model.RampCheckInStatus;
import com.ngen.cosys.impbd.model.RampCheckInUld;
import com.ngen.cosys.impbd.model.RampCheckInUldModel;
import com.ngen.cosys.impbd.model.RampCheckInUldNumber;
import com.ngen.cosys.impbd.model.RampCheckInVerified;
import com.ngen.cosys.inward.model.InwardServiceReportModel;
import com.ngen.cosys.inward.service.InwardServiceReportService;
import com.ngen.cosys.multitenancy.timezone.support.TenantTimeZoneUtility;
import com.ngen.cosys.uldinfo.dao.UldInfoDAO;
import com.ngen.cosys.uldinfo.model.UldInfoModel;

@Service
public class RampCheckInServiceImpl implements RampCheckInService {

   @Autowired
   private RampCheckInDAO rampCheckInDAO;

   @Autowired
   private UldInfoDAO uldinfoDao;

   @Autowired
   private InboundULDCheckInStoreEventProducer producer;

   @Autowired
   private OutboundUCMMessageStoreEventProducer ucmProducer;

   @Autowired
   private LoadingMoveableLocationValidator loadingmoveablelocationvalidator;

   @Autowired
   private InwardServiceReportService inwardServiceReportService;

   /*
    * Get Flight Information
    * 
    */
   @Override
   public RampCheckInDetails fetch(RampCheckInSearchFlight query) throws CustomException {
      Integer nilCount = rampCheckInDAO.getCountOfNilCargo(query);
      Integer segCount = rampCheckInDAO.getCountOfSegments(query);

      RampCheckInDetails rampCheckData = rampCheckInDAO.fetch(query);
      if (rampCheckData == null) {
         throw new CustomException("AWBERRORS", "No Records Found", ErrorType.ERROR);
      } else {
    	  List<String> shcs = new ArrayList<>();
    	  rampCheckData.getPerGroupshcs().forEach(shc -> { shcs.add(shc.getShc());});
    	  
 	if(query.getSegment()!=null) {
    		  rampCheckData.setSegment(query.getSegment()); 
    	  }
       
         rampCheckData.setCheckInStatus(reopen(rampCheckData.getFlightId()));
         RampCheckInStatus status = getRamcheckinStatus(rampCheckData.getFlightId());
         if (status != null) {
            rampCheckData.setCheckindateTime(status.getRampCheckInCompletedAt());
            rampCheckData.setCheckInCompletedBy(status.getRampCheckInCompletedBy());
         }
         rampCheckData.setBulk(rampCheckInDAO.getBulkStatus(rampCheckData).getBulk());
        
         rampCheckData.setUldManifested(String.valueOf(getFFMULDCount(rampCheckData)));
         if (nilCount.intValue() == segCount.intValue()) {
            rampCheckData.setNilCargo(true);
         }
         
         for (int i = 0; i < rampCheckData.getUlds().size(); i++) {
        	 RampCheckInUld uld =rampCheckData.getUlds().get(i);
        	 uld.setFlightId(rampCheckData.getFlightId());
        	 boolean is= rampCheckInDAO.checkUploadPhoto(uld);
        	 rampCheckData.getUlds().get(i).setUploadphotoFlag(is);
        	 
        	List<RampCheckInSHC> specialHandlingCodes = rampCheckData.getUlds().get(i).getShcs();
 			String uldShcs = "";
 			if (!CollectionUtils.isEmpty(specialHandlingCodes)) {
 				uldShcs = specialHandlingCodes.stream().map(t -> t.getShc())
 						.collect(Collectors.joining(","));
		}
 			rampCheckData.getUlds().get(i).setShcCode(uldShcs);
 			rampCheckData.getUlds().get(i).setShcs(specialHandlingCodes);
 			if (rampCheckData.getUlds().get(i).getPhc() 
 					|| (!StringUtils.isEmpty(rampCheckData.getUlds().get(i).getPhcFlag()) 
 							&& rampCheckData.getUlds().get(i).getPhcFlag().equalsIgnoreCase("Y"))) {
 				rampCheckData.getUlds().get(i).setPhc(true);
 			} else if (specialHandlingCodes.size() == 0) {
 				rampCheckData.getUlds().get(i).setPhc(false);
 			} else {
 				int count = 0;
 				for (int j = 0; j < specialHandlingCodes.size(); j++) {
 					if (!shcs.contains(specialHandlingCodes.get(j).getShc())) {
 						count++;
        }
 				}

 				if (count > 0) {
 					rampCheckData.getUlds().get(i).setPhc(false);
					} else {
						if (!ObjectUtils.isEmpty(rampCheckData.getUlds().get(i).getManualUpdate())
								&& rampCheckData.getUlds().get(i).getManualUpdate()
								&& !StringUtils.isEmpty(rampCheckData.getUlds().get(i).getPhcFlag())
								&& rampCheckData.getUlds().get(i).getPhcFlag().equalsIgnoreCase("Y")) {
							rampCheckData.getUlds().get(i).setPhc(true);
						} else if (!ObjectUtils.isEmpty(rampCheckData.getUlds().get(i).getManualUpdate())
								&& rampCheckData.getUlds().get(i).getManualUpdate()
								&& !StringUtils.isEmpty(rampCheckData.getUlds().get(i).getPhcFlag())
								&& rampCheckData.getUlds().get(i).getPhcFlag().equalsIgnoreCase("N")) {
							rampCheckData.getUlds().get(i).setPhc(false);
						}
					}
 			}
		}
        }
      return rampCheckData;
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public RampCheckInModel updateAll(RampCheckInModel model) throws CustomException {
      List<RampCheckInUld> query = model.getUldList();
      List<RampCheckInUld> filterData = new ArrayList<>();
      BigInteger flightId = query.get(0).getFlightId();
      
      checkDamagedUldsRemarksPresent(query);
      
      for (RampCheckInUld checkInULD : query) {
         rampCheckInDAO.deleteSHCs(checkInULD);
         List<RampCheckInSHC> addSHC = new ArrayList<>();
         if (checkInULD != null && checkInULD.getShcs() != null && !checkInULD.getShcs().isEmpty()) {
            for (RampCheckInSHC shc : checkInULD.getShcs()) {
               int count = rampCheckInDAO.checkSHC(shc);
               if (count == 0) {
                  shc.setImpRampCheckInId(checkInULD.getImpRampCheckInId());
                  addSHC.add(shc);
               }
               if (!rampCheckInDAO.checkShcMaster(shc)) {
                  throw new CustomException("SHCCODE011", "form", ErrorType.ERROR);
               }
            }
            rampCheckInDAO.insertSHCUsingId(addSHC);
         }

         if (checkInULD != null && (checkInULD.getDriverId() == null || checkInULD.getDriverId() == ""
               || checkInULD.getDriverId().isEmpty())) {
            checkInULD.setCheckedinAt(null);
            checkInULD.setCheckedinBy(null);
            filterData.add(checkInULD);
         } else {
            filterData.add(checkInULD);
         }

      }
      rampCheckInDAO.insertDataIntoUldMasterAndMovements(query);
      RampCheckInStatus rcs = new RampCheckInStatus();
      rcs.setFlightId(flightId);
      rampCheckInDAO.updateFirstULDCheckedIn(rcs);
      return rampCheckInDAO.updateAll(filterData);
   }

   private void checkDamagedUldsRemarksPresent(List<RampCheckInUld> query) throws CustomException{

	   if(!ObjectUtils.isEmpty(query)) {
	    	  for (RampCheckInUld uldData : query) {
	    		  if (!ObjectUtils.isEmpty(uldData.getDamaged()) && uldData.getDamaged()==Boolean.TRUE 
	    	    		  && ObjectUtils.isEmpty(uldData.getRemarks())  ) {
	    	          throw new CustomException("RAMPCOMPCHEKINDUPLICATEULD", "", ErrorType.NOTIFICATION, new String[] {uldData.getUldNumber()});
	    	    	 
	    	       }
	    	  }
	      }
   }

@Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public RampCheckInModel updateAllList(RampCheckInModel query) throws CustomException {
      for (RampCheckInUld checkInULD : query.getUldList()) {
         BigInteger flightId = checkInULD.getFlightId();
         rampCheckInDAO.deleteSHCs(checkInULD);
         List<RampCheckInSHC> addSHC = new ArrayList<RampCheckInSHC>();
         for (RampCheckInSHC shc : checkInULD.getShcs()) {
            shc.setImpRampCheckInId(checkInULD.getImpRampCheckInId());
            addSHC.add(shc);
            if (!rampCheckInDAO.checkShcMaster(shc)) {
               throw new CustomException("SHCCODE011", "form", ErrorType.ERROR);
            }
         }
         rampCheckInDAO.insertSHCUsingId(addSHC);

         RampCheckInStatus rcs = new RampCheckInStatus();
         rcs.setFlightId(flightId);
         rampCheckInDAO.updateFirstULDCheckedIn(rcs);
         rampCheckInDAO.updateAllList(checkInULD);
      }

      return query;
   }

   @Override
   public List<RampCheckInUld> create(List<RampCheckInUld> query) throws CustomException {
      return rampCheckInDAO.create(query);
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public RampCheckInModel checkIn(RampCheckInModel model) throws CustomException {
      List<RampCheckInUld> query = model.getUldList();
      BigInteger flightId = query.get(0).getFlightId();
      RampCheckInStatus rampCheckInStatus = new RampCheckInStatus();
      rampCheckInStatus.setFlightId(flightId);
      rampCheckInStatus.setCreatedBy(model.getCreatedBy());
      rampCheckInStatus.setFlightNumber(query.get(0).getFlight());
      rampCheckInStatus.setFlightDate(query.get(0).getFlightDate());
      rampCheckInStatus.setCreatedBy(model.getCreatedBy());
      rampCheckInStatus.setCreatedOn(model.getCreatedOn());
      //set ULD List
      rampCheckInStatus.setUldList(query);
      rampCheckInStatus.setUldManifested(model.getUldManifested());
      rampCheckInStatus.setUldReceived(model.getUldReceived());
      rampCheckInStatus.setTrollyReceived(model.getTrollyReceived());
      rampCheckInStatus.setRampCheckInCompletedStatus(true);
      if(!ObjectUtils.isEmpty(query)) {
    	  for (RampCheckInUld uldData : query) {
    		  if (!ObjectUtils.isEmpty(uldData.getDamaged()) && uldData.getDamaged()==Boolean.TRUE 
    	    		  && ObjectUtils.isEmpty(uldData.getRemarks())  ) {
    	          throw new CustomException("RAMPCOMPCHEKINDUPLICATEULD", "", ErrorType.NOTIFICATION, new String[] { uldData.getUldNumber()});
    	    	 
    	       }
    	  }
      }
      
      if (!query.get(0).getNilCargo().equalsIgnoreCase("NIL")) {
         for (RampCheckInUld rampCheckInUld : query) {
            if ((rampCheckInUld != null && (rampCheckInUld.getCheckedinBy() == null
                  || rampCheckInUld.getCheckedinBy() == "" || rampCheckInUld.getCheckedinBy().isEmpty()))
                  && (rampCheckInUld.getOffloadReason() == null) && (rampCheckInUld.getUsedAsTrolley() == false)) {
               checkContentCodeForCompleteCheckin(rampCheckInUld);
            }

         }
         rampCheckInDAO.insertDataIntoUldMasterAndMovements(query);
         rampCheckInDAO.insertULDsIntoUldUCMTables(query);
      }
      RampCheckInStatus status = rampCheckInDAO.getEventStatus(rampCheckInStatus);
      String type = rampCheckInDAO.getFlightType(query.get(0).getFlightId());
      OutboundUCMMessageEvent ucmEvent;
      if (type.equalsIgnoreCase("C")) {
         ucmEvent = new OutboundUCMMessageEvent();
         ucmEvent.setFlightKey(query.get(0).getFlight());
         ucmEvent.setFlightId(query.get(0).getFlightId());
         ucmEvent.setFlightDate(query.get(0).getFlightDate().atStartOfDay());
         ucmEvent.setFlightBoardingPoint(query.get(0).getOrigin());
         ucmEvent.setFlightCarrier(query.get(0).getFlight().substring(0, 2));
         ucmEvent.setFlightType("C");
         ucmEvent.setUcmType("IN");
         ucmEvent.setCreatedBy(model.getLoggedInUser());
         ucmEvent.setCreatedOn(LocalDateTime.now());
         ucmProducer.publish(ucmEvent);
      }
      if (status == null) {
         subscribeToEvent(query);
         rampCheckInDAO.insertEvent(rampCheckInStatus);
      } else {
         if (status.getFirstTimeRampCheckInCompletedBy() == null) {
            rampCheckInDAO.updateEventFirstTime(rampCheckInStatus);
         } else {
            rampCheckInDAO.updateEvent(rampCheckInStatus);
         }
      }

      // Add other discrepancy which has been added for each ULD in inward service
      // report
      InwardServiceReportModel requestModel = new InwardServiceReportModel();
      requestModel.setFlightId(query.get(0).getFlightId());
      requestModel.setCreatedBy(model.getCreatedBy());
      requestModel.setLoggedInUser(model.getLoggedInUser());
      this.inwardServiceReportService.createServiceReportOnRampCheckInComplete(requestModel);

      return model;
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public RampCheckInModel checkInData(RampCheckInModel query) throws CustomException {
      if (query == null) {
         throw new CustomException("EMPTYLIST", "form", ErrorType.ERROR);
      }
      for (RampCheckInUld uldData : query.getUldList()) {
         BigInteger flightId = uldData.getFlightId();
         RampCheckInStatus rampCheckInStatus = new RampCheckInStatus();
         rampCheckInStatus.setFlightId(flightId);
         RampCheckInStatus status = rampCheckInDAO.getEventStatus(rampCheckInStatus);
         if (status == null) {
            rampCheckInDAO.insertEvent(rampCheckInStatus);
         } else {
            if (status.getFirstTimeRampCheckInCompletedBy() == null) {
               rampCheckInDAO.updateEventFirstTime(rampCheckInStatus);
            } else {
               rampCheckInDAO.updateEvent(rampCheckInStatus);
            }
         }
      }

      return query;
   }

   /**
    * @param query
    * @throws CustomException
    */
   private void subscribeToEvent(List<RampCheckInUld> query) throws CustomException {
      InboundULDCheckInStoreEvent event;
      for (RampCheckInUld checkInULD : query) {
         event = new InboundULDCheckInStoreEvent();
         event.setUldNumber(checkInULD.getUldNumber());
         event.setFlightId(checkInULD.getFlightId());
         event.setStatus(EventStatus.NEW.getStatus());
         event.setCheckedInAt(LocalDateTime.now());
         event.setCheckedInBy("SYSADMIN");
         event.setCreatedBy("SYSADMIN");
         event.setCreatedOn(LocalDateTime.now());
         producer.publish(event);
      }

   }

   @Override
   public RampCheckInModel reopen(RampCheckInModel model) throws CustomException {
      List<RampCheckInUld> query = model.getUldList();
      BigInteger flightId = query.get(0).getFlightId();
      RampCheckInStatus rampCheckInStatus = new RampCheckInStatus();
      rampCheckInStatus.setFlightId(flightId);
      rampCheckInStatus.setFlightNumber(query.get(0).getFlight());
      rampCheckInStatus.setFlightDate(query.get(0).getFlightDate());
      rampCheckInStatus.setCreatedBy(model.getCreatedBy());
      rampCheckInStatus.setCreatedOn(model.getCreatedOn());
      rampCheckInStatus.setUldList(query);
      rampCheckInStatus.setRampCheckInCompletedStatus(false);
      rampCheckInStatus.setUldManifested(model.getUldManifested());
      rampCheckInStatus.setUldReceived(model.getUldReceived());
      rampCheckInStatus.setTrollyReceived(model.getTrollyReceived());
      RampCheckInStatus status = rampCheckInDAO.getEventStatus(rampCheckInStatus);
      if (status == null) {
         rampCheckInDAO.insertEvent(rampCheckInStatus);
         rampCheckInDAO.reopenEvent(rampCheckInStatus);
      } else if (status.getRampCheckInCompletedBy() != null) {
         rampCheckInDAO.reopenEvent(rampCheckInStatus);
      } else {
         // throw CustoException
      }
      return model;
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public RampCheckInParentModel createUld(RampCheckInParentModel parentModel) throws CustomException {
      for (RampCheckInUld query : parentModel.getUldInfoList()) {
         MoveableLocationTypeModel requestModel = new MoveableLocationTypeModel();
         requestModel.setKey(query.getUldNumber());
         requestModel.setFlightKey(query.getFlight());
         requestModel.setFlightDate(query.getFlightDate());
         requestModel.setProcess(MoveableLocationTypeProcess.ProcessTypes.EXPORT);
         requestModel.setPropertyKey("uldTrolleyNo");
         requestModel.setContentCode(query.getContentCode());


         MoveableLocationTypeModel moveableLocationTypeModel = loadingmoveablelocationvalidator.split(requestModel);

         // Check if the location is an dummy location then do not allow
         if (moveableLocationTypeModel.getDummyLocation()) {
            moveableLocationTypeModel.addError("data.invalid.moveable.location", "uldTrolleyNo", ErrorType.APP);
         }
         if (!moveableLocationTypeModel.getMessageList().isEmpty()) {
            query.addError("data.invalid.moveable.location", "uldNumber", ErrorType.APP);
            return parentModel;
         }
         List<RampCheckInSHC> addSHC = new ArrayList<RampCheckInSHC>();
         for (RampCheckInSHCInput shc : query.getCreateshc()) {
            RampCheckInSHC shccheck = new RampCheckInSHC();
            shccheck.setShc(shc.getShc());
            shccheck.setImpRampCheckInId(query.getImpRampCheckInId());
            addSHC.add(shccheck);
            if (!rampCheckInDAO.checkShcMaster(shccheck)) {
               throw new CustomException("SHCCODE011", "form", ErrorType.ERROR);
            }
         }
         String Uld = query.getUldNumber();
         String uldType = Uld.substring(0, 3);
         String UldType = rampCheckInDAO.isTrolley(uldType);
         if (UldType == null) {
            query.setUsedAsTrolley(true);
         }
         // on Rampcheck in complete ulds will get inserted in to uld master
         // insertInUldMaster(query, requestModel);
         rampCheckInDAO.createUld(query);
         rampCheckInDAO.insertSHC(addSHC);
         rampCheckInDAO.createHandOver(query);
         rampCheckInDAO.insertHandOverULD(query);
         RampCheckInStatus rcs = new RampCheckInStatus();
         rcs.setFlightId(query.getFlightId());
         rampCheckInDAO.updateFirstULDCheckedIn(rcs);
         rcs.setFirstULDTowedBy(query.getDriverId());
         rampCheckInDAO.updateFirstULDTowedIn(rcs);

      }

      return parentModel;
   }

   void insertInUldMaster(RampCheckInUld rampCheckIn, MoveableLocationTypeModel requestModel) throws CustomException {
      RampCheckInUld Object = rampCheckInDAO.getFlightOrigin(rampCheckIn);
      UldInfoModel uldInfoModel = new UldInfoModel();
      uldInfoModel.setUldKey(rampCheckIn.getUldNumber());
      uldInfoModel.setInboundFlightId(rampCheckIn.getFlightId());
      uldInfoModel.setFlightBoardPoint(Object.getOrigin());
      uldInfoModel.setFlightOffPoint(rampCheckIn.getTenantAirport());
      uldInfoModel.setContentCode(rampCheckIn.getContentCode());
      uldInfoModel.setIntact(Boolean.FALSE);
      uldInfoModel.setMovableLocationType(requestModel.getLocationType());
      uldInfoModel.setOutboundFlightId(rampCheckIn.getFlightId());
      uldInfoModel.setTerminal(rampCheckIn.getTerminal());
      uldInfoModel.setUldLocationCode(rampCheckIn.getTerminal());
      uldInfoModel.setHandlingCarrierCode(Object.getCarrierCode());
      uldInfoModel.setUldCarrierCode(requestModel.getPart3());
      uldInfoModel.setUldNumber(String.valueOf(requestModel.getPart2()));
      uldInfoModel.setUldType(requestModel.getPart1());
      uldInfoModel.setUldStatus("INW");
      uldInfoModel.setApronCargoLocation("CARGO");
      uldInfoModel.setUldConditionType("SER");
      uldInfoModel.setOutboundFlightId(rampCheckIn.getOuboundFlightId());
      uldinfoDao.updateUldInfo(uldInfoModel);
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public RampCheckInUld onDriverUpdate(RampCheckInUld query) throws CustomException {
	   //check record in imp_handover table based on ULD and flight info
	   String handoverExist = rampCheckInDAO.fecthHandoverinfo(query);
      if (StringUtils.isEmpty(handoverExist) && (query.getHandOverId() == null || query.getHandOverId().intValue() == 0)) {
         rampCheckInDAO.createHandOver(query);
         rampCheckInDAO.insertHandOverULD(query);
      } else {
    	  if(query.getHandOverId().intValue() == 0 || ObjectUtils.isEmpty(query.getHandOverId()) )
    	  {
    		BigInteger handoverID =rampCheckInDAO.fetchHandOverID(query);
    	    query.setHandOverId(handoverID);
    		  
    	  }
         rampCheckInDAO.updateHandOverULD(query);
      }
      return query;
   }

   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public RampCheckInUldModel onDriverUpdateId(RampCheckInUldModel query) throws CustomException {
      rampCheckInDAO.createHandOverId(query);
      RampCheckInUldNumber data = new RampCheckInUldNumber();
      for (RampCheckInUldNumber model : query.getUldNumber()) {
         data.setHandOverId(query.getHandOverId());
         data.setUldNumber(model.getUldNumber());
         rampCheckInDAO.insertHandOverULDId(data);
      }
      return query;
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public RampCheckInModel assignDriver(RampCheckInModel bquery) throws CustomException {
      List<RampCheckInUld> query = bquery.getUldList();

      for (RampCheckInUld data : query) {
         onDriverUpdate(data);
      }

      RampCheckInStatus rcs = new RampCheckInStatus();
      rcs.setFlightId(query.get(0).getFlightId());
      rcs.setFirstULDTowedBy(query.get(0).getDriverId());
      rampCheckInDAO.updateFirstULDTowedIn(rcs);
      rampCheckInDAO.updateAll(bquery.getUldList());
      return bquery;
   }
   
   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public RampCheckInModel saveAllULDTemperature(RampCheckInModel bquery) throws CustomException {
//      List<RampCheckInUld> query = bquery.getUldList();
//
//      for (RampCheckInUld data : query) {
//         //onDriverUpdate(data);
//    	  
//      }
//
//      RampCheckInStatus rcs = new RampCheckInStatus();
//      rcs.setFlightId(query.get(0).getFlightId());
//      rcs.setFirstULDTowedBy(query.get(0).getDriverId());
//      rampCheckInDAO.updateFirstULDTowedIn(rcs);
      rampCheckInDAO.saveAllULDTemperature(bquery.getUldList());
      return bquery;
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public RampCheckInUldModel assignDriverId(RampCheckInUldModel query) throws CustomException {

      if (query != null) {
         onDriverUpdateId(query);

         RampCheckInStatus rcs = new RampCheckInStatus();
         rcs.setFlightId(query.getFlightId());
         rcs.setFirstULDTowedBy(query.getDriverId());
         rampCheckInDAO.updateFirstULDTowedIn(rcs);
      }
      return query;
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public RampCheckInModel delete(RampCheckInModel query1) throws CustomException {

      List<RampCheckInUld> query = query1.getUldList();

      for (RampCheckInUld rampCheckInUld : query) {
         RampCheckInSHC deleteSHC = new RampCheckInSHC();
         RampCheckInPiggyback deletePiggyback = new RampCheckInPiggyback();
         deleteSHC.setImpRampCheckInId(rampCheckInUld.getImpRampCheckInId());
         deletePiggyback.setImpRampCheckInId(rampCheckInUld.getImpRampCheckInId());
         rampCheckInDAO.deleteSHCs(rampCheckInUld);
         rampCheckInDAO.deletePiggyback(rampCheckInUld);
      }

      rampCheckInDAO.deleteUldTrollyInfo(query);
      rampCheckInDAO.deleteuldFromImpHandOver(query);
      return rampCheckInDAO.delete(query);
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public List<RampCheckInPiggyback> getPiggyback(RampCheckInPiggyback query) throws CustomException {
      return rampCheckInDAO.fetchPiggyback(query);
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public List<RampCheckInPiggyback> managePiggyback(List<RampCheckInPiggyback> paramObject) throws CustomException {
      List<RampCheckInPiggyback> createPiggyback = paramObject.stream().filter(item -> item.getFlagCRUD().equals("C"))
            .collect(Collectors.toList());
      List<RampCheckInPiggyback> updatePiggyback = paramObject.stream().filter(item -> item.getFlagCRUD().equals("U"))
            .collect(Collectors.toList());
      List<RampCheckInPiggyback> deletePiggyback = paramObject.stream().filter(item -> item.getFlagCRUD().equals("D"))
            .collect(Collectors.toList());

      if (!createPiggyback.isEmpty()) {
         rampCheckInDAO.insertPiggyback(createPiggyback);
      }
      if (!updatePiggyback.isEmpty()) {
         rampCheckInDAO.updatePiggyback(updatePiggyback);
      }
      if (!deletePiggyback.isEmpty()) {
         rampCheckInDAO.deletePiggyback(deletePiggyback);
      }
      return paramObject;
   }

   @Override
   public BigInteger reopen(BigInteger query) throws CustomException {
      BigInteger statusOfFLight;
      RampCheckInStatus status = getRamcheckinStatus(query);
      if (status == null) {
         statusOfFLight = new BigInteger("0");
      } else if (status.getRampCheckInCompletedBy() == null) {
         statusOfFLight = new BigInteger("1");
      } else {
         statusOfFLight = query;
      }
      return statusOfFLight;
   }

   private RampCheckInStatus getRamcheckinStatus(BigInteger query) throws CustomException {
      RampCheckInStatus rampCheckInStatus = new RampCheckInStatus();
      rampCheckInStatus.setFlightId(query);
      RampCheckInStatus status = rampCheckInDAO.getEventStatus(rampCheckInStatus);
      return status;
   }

   @Override
   public RampCheckInDetails updateBulkStatus(RampCheckInDetails data) throws CustomException {
      rampCheckInDAO.updateBulkStatus(data);
      return data;
   }

   public RampCheckInList fetchList(RampCheckInSearchFlight query) throws CustomException {
      RampCheckInList rampCheckData = rampCheckInDAO.fetchList(query);
      RampCheckInDetails details = new RampCheckInDetails();
      if (rampCheckData == null) {
         throw new CustomException("AWBERRORS", "No Records Found", ErrorType.ERROR);
      } else {
         int countList1 = rampCheckData.getPendVerified().size();
         int countList2 = rampCheckData.getVerified().size();
         int countList3 = rampCheckData.getPending().size();
         for (RampCheckInPendVerified pendVeried : rampCheckData.getPendVerified()) {
            pendVeried.setCountPenVerified(String.valueOf(countList1));
         }
         RampCheckInStatus status = getRamcheckinStatus(rampCheckData.getFlightId());
         if (status != null) {
            rampCheckData.setCheckindateTime(status.getRampCheckInCompletedAt());
         }
         for (RampCheckInVerified veried : rampCheckData.getVerified()) {
            veried.setVerifiedCount(String.valueOf(countList2));
         }
         for (RampCheckInPending pend : rampCheckData.getPending()) {
            pend.setPendingCount(String.valueOf(countList3));
         }
         details.setFlightId(rampCheckData.getFlightId());
         rampCheckData.setUldManifested(String.valueOf(getFFMULDCount(details)));
         rampCheckData.setUldReceived(String.valueOf(countList1));
         rampCheckData.setCheckInStatus(reopen(rampCheckData.getFlightId()));
         rampCheckData.setBulk(rampCheckInDAO.getBulkStatusList(rampCheckData).getBulk());
      }
      return rampCheckData;
   }

   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public RampCheckInPiggybackModel maintainPiggybackList(RampCheckInPiggybackModel paramObject)
         throws CustomException {
      RampCheckInPiggybackList dataList = new RampCheckInPiggybackList();
      for (RampCheckInPiggybackList data : paramObject.getUldNumberList()) {
         dataList.setUldNumber(data.getUldNumber());
         RampCheckInPiggybackList listValue = rampCheckInDAO.existUldNumber(dataList);
         if (listValue == null) {
            rampCheckInDAO.insertPiggybackList(data);
         } else {
            rampCheckInDAO.updatePiggybackList(data);
         }
      }
      return paramObject;
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public RampCheckInPiggybackModel getPiggybackList(RampCheckInPiggybackModel query) throws CustomException {
      for (RampCheckInPiggybackList data : query.getUldNumberList()) {
         List<RampCheckInPiggybackList> dataList = rampCheckInDAO.fetchPiggybackList(data);
         query.setUldNumberList(dataList);
      }
      return query;
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public RampCheckInPiggybackModel deletePiggybackList(RampCheckInPiggybackModel query) throws CustomException {
      for (RampCheckInPiggybackList data : query.getUldNumberList()) {
         List<RampCheckInPiggybackList> dataList = rampCheckInDAO.deletePiggybackList(data);
         query.setUldNumberList(dataList);
      }
      return query;
   }

   @Override
   public Integer checkCarrierCode(RampCheckInUld data) throws CustomException {

      return rampCheckInDAO.getCountofCarrierCode(data);
   }

   private void checkContentCodeForCompleteCheckin(RampCheckInUld uldData) throws CustomException {
      Integer count = rampCheckInDAO.checkContentCode(uldData);
      if (!ObjectUtils.isEmpty(count) && count.intValue() > 0) {
         throw new CustomException("RAMPCOMPCHEKIN", "form", ErrorType.ERROR);
      }
      
      
      
     
   }

   private String segmentData(RampCheckInDetails rampCheckIn) throws CustomException {

      List<String> segment = new ArrayList<>();

      String seg = null;

      List<RampCheckInSegmentList> list = rampCheckInDAO.fetchSegments(rampCheckIn);

      String offpoint = "";
      for (RampCheckInSegmentList rampCheckInSegmentList : list) {
         if (!offpoint.equals(rampCheckInSegmentList.getBoardPoint())) {
            segment.add(rampCheckInSegmentList.getBoardPoint());
         }
         segment.add(rampCheckInSegmentList.getOffPoint());
         offpoint = rampCheckInSegmentList.getOffPoint();
      }

      for (String rampCheckInSegmentList : segment) {
         if (seg == null) {
            seg = rampCheckInSegmentList;
         } else {
            seg = seg + "-" + rampCheckInSegmentList;
         }
      }
      return seg;
   }

   private Integer getFFMULDCount(RampCheckInDetails flight) throws CustomException {
      return rampCheckInDAO.getFFMUldCount(flight);
   }

   @Override
   public RampCheckInModel unCheckIn(RampCheckInModel query) throws CustomException {
      return rampCheckInDAO.unCheckIn(query.getUldList());
   }

}