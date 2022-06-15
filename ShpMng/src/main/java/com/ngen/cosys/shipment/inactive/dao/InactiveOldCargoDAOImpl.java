package com.ngen.cosys.shipment.inactive.dao;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.shipment.inactive.model.InactiveSearch;
import com.ngen.cosys.shipment.inactive.model.InactiveSearchList;
import com.ngen.cosys.shipment.inactive.model.ShipmentData;
import com.ngen.cosys.shipment.inactive.model.ShipmentFreightOut;
import com.ngen.cosys.timezone.util.TenantZoneTime;

@Repository
public class InactiveOldCargoDAOImpl extends BaseDAO implements InactiveOldCargoDAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InactiveOldCargoDAOImpl.class);

   @Autowired
   private SqlSessionTemplate sqlSessionShipment;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.inactive.dao.InactiveOldCargoDAO#getInactiveList(com.
    * ngen.cosys.shipment.inactive.model.InactiveSearch)
    */
   @Override
   public List<InactiveSearchList> getInactiveList(InactiveSearch searchParams) throws CustomException {
      getDefaultCreationDays(searchParams);
      searchParams.setCustomerType(super.fetchObject("fetchCustomerType", searchParams, sqlSessionShipment));
      List<InactiveSearchList> inactiveSearchlist = super.fetchList("sqlGetInactiveCargoList", searchParams, sqlSessionShipment);
//      if (searchParams.getShcode() != null) {
//         for (String listOfSHC : searchParams.getShcode()) {
//            searchParams.setShc(listOfSHC);
//            inactiveSearchlist = super.fetchList("sqlGetInactiveCargoList", searchParams, sqlSessionShipment);
//         }
//      } else {
//         inactiveSearchlist = super.fetchList("sqlGetInactiveCargoList", searchParams, sqlSessionShipment);
//      }
      return inactiveSearchlist;
   }

   public InactiveSearch getDefaultCreationDays(InactiveSearch searchParams) throws CustomException {
      if (searchParams.getCreationdays() <= 0) {
         long days = super.fetchObject("defaultCreationDays", searchParams, sqlSessionShipment);
         searchParams.setCreationdays(days);
      }
      return searchParams;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.inactive.dao.InactiveOldCargoDAO#moveToFreightOut(com
    * .ngen.cosys.shipment.inactive.model.InactiveSearch)
    */
  
   @Override
   public String moveToFreightOut(InactiveSearch shipmentInfo) throws CustomException {
      String status = "";
      if (!CollectionUtils.isEmpty(shipmentInfo.getShipmentData())) {
         for (ShipmentData shipment : shipmentInfo.getShipmentData()) {
        	 
        	 shipmentInfo.setShipmentId(shipment.getShipmentId());
        	 if(shipmentInfo.getIsHandledByHouse()) {
             BigInteger houseId=super.fetchObject("fetchHouseIDtoGetInventory", shipmentInfo, sqlSessionShipment);
        	 shipment.setShipmentHouseId(houseId);
        	 shipment.setHawbnumber(shipmentInfo.getHawbNumber());
        	 }
        	 
            String shipmentNumberType = this.fetchObject("sqlGetShipmentNumberType", shipment.getAwbNumber(),
                  sqlSessionShipment);

            shipment.setRemarks(shipmentInfo.getRemarks());
            shipment.setRemarkType(shipmentInfo.getRemarkType());
            shipment.setDeliveryOrderNo(shipmentInfo.getDeliveryOrderNo());
            shipment.setTerminal(shipmentInfo.getTerminal());
            shipment.setSectorId(shipmentInfo.getSectorId());
            shipment.setShipmentType(shipmentNumberType);
            shipment.setTrmNumber(shipmentInfo.getTrmNumber());
            shipment.setTrmDate(shipmentInfo.getTrmDate());

            BigInteger isShipmentOnHold = super.fetchObject("sqlCheckInactiveCargoShipmentOnHold",
                  shipment.getShipmentId(), sqlSessionShipment);
            if (!ObjectUtils.isEmpty(isShipmentOnHold) && isShipmentOnHold.intValue() > 0) {
               throw new CustomException("HOLD_SHIPMENT", "Check Shipment", ErrorType.ERROR);
            }
            // If Inventory Piece/Weight is Empty then fetch the same
            ShipmentData pieceWeightInfo = this.fetchObject("sqlGetManualFreightOutShipmentPieceWeight", shipmentInfo,
                  sqlSessionShipment);
            if (ObjectUtils.isEmpty(pieceWeightInfo)) {
               throw new CustomException("awb.inv.pcs.zero.or.shpmnt.frtout", "Check Shipment",
                     ErrorType.ERROR);
            }
            if (!ObjectUtils.isEmpty(pieceWeightInfo) && !StringUtils.isEmpty(pieceWeightInfo.getPoStatus())
                  && pieceWeightInfo.getPoStatus().equalsIgnoreCase("DRAFT")) {
               throw new CustomException("awb.cancel.draft.po.monitoring.screen", "Check PO Status",
                     ErrorType.ERROR);
            }

            BigInteger freightOutPieces = this.fetchObject("sqlGetFreightOutPieces", shipment, sqlSessionShipment);
            BigInteger totalPieces = null;
            if (freightOutPieces.intValue() != 0) {
               totalPieces = pieceWeightInfo.getPieces().add(freightOutPieces);
            } else {
               totalPieces = pieceWeightInfo.getPieces();
            }

            if (totalPieces.intValue() != 0 && totalPieces.compareTo(shipment.getPieces()) > 0) {
               throw new CustomException("awb.frtout.pcs.equal.to.doc.pcs",
                     "Check Shipment", ErrorType.ERROR);
            }

            //Check for Document Weight vs Inventory Weight
            Boolean doubleCheckShipmentWeight = this.fetchObject("sqlDoubleCheckShipmentWeightInManualDO", shipment,
                  sqlSessionShipment);
            if (!ObjectUtils.isEmpty(doubleCheckShipmentWeight) && doubleCheckShipmentWeight) {
               throw new CustomException("awb.wgt.more.or.less.doc.wgt", "",
                     ErrorType.ERROR);
            }

            // If flight info exists then get the flight id
            if (!StringUtils.isEmpty(shipmentInfo.getFlightkey()) && !ObjectUtils.isEmpty(shipment)) {
               shipment.setFlightkey(shipmentInfo.getFlightkey());
               shipment.setFlightDate(shipmentInfo.getFlightDate());
               ShipmentData flightData = super.fetchObject("sqlGetFlightIdForManualFreightOut", shipment,
                     sqlSessionShipment);
               if (!ObjectUtils.isEmpty(flightData) && !ObjectUtils.isEmpty(flightData.getFlightId())) {
                  shipment.setFlightId(flightData.getFlightId());
                  shipment.setGroundHandlingCode(flightData.getGroundHandlingCode());
                  shipment.setFlightOffPoint(flightData.getFlightOffPoint());
                  shipment.setCarrier(flightData.getCarrier());
               } else {
                  throw new CustomException("flight.invalid.fligh", "Check Flight", ErrorType.ERROR);
               }
            }
            // Set piece/weight
            shipment.setPieces(pieceWeightInfo.getPieces());
            shipment.setWeight(pieceWeightInfo.getWeight());
            shipment.setChargeCode(pieceWeightInfo.getChargeCode());
            // check is Dnata handled flight or not
            if (!StringUtils.isEmpty(shipment.getGroundHandlingCode())
                  && shipment.getGroundHandlingCode().equalsIgnoreCase("DNATA")
                  && shipment.getRemarkType().equalsIgnoreCase("DEP")) {
               throw new CustomException("awb.remark.type.trm", "Check flight type", ErrorType.ERROR);
            } else if (shipment.getRemarkType().equalsIgnoreCase("TRM")
                  && !StringUtils.isEmpty(shipment.getGroundHandlingCode())
                  && !shipment.getGroundHandlingCode().equalsIgnoreCase("DNATA")) {
               throw new CustomException("awb.enter.dnta.handl.flight", "Check flight type", ErrorType.ERROR);
            }
            Boolean trmFlag = this.fetchObject("validateTRMNumber", shipment, sqlSessionShipment);
            if (!trmFlag && "TRM".equalsIgnoreCase(shipment.getRemarkType())) {
               throw new CustomException("awb.enter.valid.trm", "Check TRM Number", ErrorType.ERROR);
            }
            if (!StringUtils.isEmpty(shipment.getDeliveryOrderNo()) && !"TRM".equalsIgnoreCase(shipment.getRemarkType())
                  && !"DEP".equalsIgnoreCase(shipment.getRemarkType())) {
               Boolean continueCustomsSubmission = false;
               // Check if delivery order no. exists in Delivery
               continueCustomsSubmission = this.fetchObject("isDeliveryOrderNumberExist", shipment, sqlSessionShipment);
               shipmentInfo.setContinueCustomsSubmission(continueCustomsSubmission);
               if (shipment.getDeliveryOrderNo().length() <= 8 && continueCustomsSubmission) {
                  throw new CustomException("awb.do.exists", "DO Number",
                        ErrorType.ERROR);
               }
               if (!continueCustomsSubmission) {
                  // insert data into imp_delivery
                  this.insertData("sqlInsertManualShipmentDelivery", shipment, sqlSessionShipment);
                  // insert LocalAutorty information
                  shipmentInfo.setDeliveryId(shipment.getDeliveryId());
                  this.insertData("sqlInsertLocalAuthorityInfo", shipmentInfo, sqlSessionShipment);
                  if (!CollectionUtils.isEmpty(shipmentInfo.getLocalAuthorityDetail())) {
                  shipmentInfo.getLocalAuthorityDetail()
                        .forEach(t -> t.setLocalAutorityInfoId(shipmentInfo.getLocalAuthorityInfoId()));
                  if (shipmentInfo.getLocalAuthorityDetail().get(0).getCustomerAppAgentId() == null) {
                     shipmentInfo.getLocalAuthorityDetail().get(0).setCustomerAppAgentId(
                           super.fetchObject("getAppointedAgentId", shipment, sqlSessionShipment));
                  }
                  this.insertData("sqlInsertLocalAuthorityInfoDetails", shipmentInfo.getLocalAuthorityDetail(),
                        sqlSessionShipment);
                  }
               }else
               {
            	   this.updateData("sqlUpdateDeliveredOnInManualDO", shipment, sqlSessionShipment);
               }
            }
            if("DEP".equalsIgnoreCase(shipment.getRemarkType()) && !Objects.isNull(shipment.getFlightId())){
            	boolean checkifFLightDeparted= this.fetchObject("sqlQueryCheckFlightDeparted", shipment, sqlSessionShipment);
            	if(!checkifFLightDeparted) {
            		 throw new CustomException("flight.not.dep",
                             null, ErrorType.ERROR);
            	}
            }

            if(!shipmentInfo.getIsHandledByHouse()) {
            // Move the inventory to freight out
            status = this.moveInventoryToFreightOut(shipment, shipmentInfo);
       	 }
       	 else {
       		status = this.moveInventoryToFreightOutHAWB(shipment, shipmentInfo);
       		
       		ShipmentData totalShipmentHousePieces=this.fetchObject("sqlgetTotalShipmentHousePieces", shipment, sqlSessionShipment);
       		if (ObjectUtils.isEmpty(totalShipmentHousePieces.getTotalShipmentPieces())) {
  				shipment.setTotalShipmentPieces(BigInteger.ZERO);
			}else {
				shipment.setTotalShipmentPieces(totalShipmentHousePieces.getTotalShipmentPieces());
			}
       		
       		if (ObjectUtils.isEmpty(totalShipmentHousePieces.getTotalHousePieces())) {
				shipment.setTotalHousePieces(BigInteger.ZERO);
			}else {
				shipment.setTotalHousePieces(totalShipmentHousePieces.getTotalHousePieces());
			}
       		
       		ShipmentData totalFreighOutShipmentHousePieces=this.fetchObject("sqltotalFreighOutPiecesShipmentHouse", shipment, sqlSessionShipment);
       		if (ObjectUtils.isEmpty(totalFreighOutShipmentHousePieces.getTotalFreightoutShipmentPieces())) {
  				shipment.setTotalFreightoutShipmentPieces(BigInteger.ZERO);
			}else {
				shipment.setTotalFreightoutShipmentPieces(totalFreighOutShipmentHousePieces.getTotalFreightoutShipmentPieces());
			}
       		if (ObjectUtils.isEmpty(totalFreighOutShipmentHousePieces.getTotalFreightoutHousePieces())) {
				shipment.setTotalFreightoutHousePieces(BigInteger.ZERO);
			}else {
				shipment.setTotalFreightoutHousePieces(totalFreighOutShipmentHousePieces.getTotalFreightoutHousePieces());
			}
       		
       		
			// Check all pieces of an shipment has been delivered or not if yes update theshipment delivered on
			if (shipment.getTotalShipmentPieces().equals(shipment.getTotalFreightoutShipmentPieces())) {
				this.updateData("sqlUpdateDeliveredOnmaster", shipment, sqlSessionShipment);
             	this.updateData("sqlUpdateCaseStatustracing", shipment, sqlSessionShipment);
			}
			//Update Shipment_HouseInformation DeliveredOn
			if (FeatureUtility.isFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class) &&
				shipment.getTotalHousePieces().equals(shipment.getTotalFreightoutHousePieces())) {
				this.updateData("sqlUpdateShipmentDeliveryInfoToInventoryHousebyHawb", shipment, sqlSessionShipment);
			}
			// Update Customs_ImportShipment ShipmentDeliveryDate
			if (FeatureUtility.isFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class) && 
				shipment.getTotalShipmentPieces().equals(shipment.getTotalFreightoutShipmentPieces())) {
				this.updateData("sqlUpdateCustomsImportShipmentInfobyHawb", shipment, sqlSessionShipment);
			}

       	 }
            BigInteger count = super.fetchObject("sqlCheckShipmentRemarksForManualFreightOutExists", shipment,
                  sqlSessionShipment);
            if (!ObjectUtils.isEmpty(count) && count.intValue() == 0) {
               if (!StringUtils.isEmpty(shipment.getDeliveryOrderNo())) {
            	   
            	   String dateTime = TenantZoneTime.getZoneDateTime(shipment.getCreatedOn(),shipment.getTenantId()).toString();
           		
           		//Changing the Date Format in Remarks
           				String createdDate=null;
           				try {
           				String stringCreatedDate = dateTime;
           				Date createdDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(stringCreatedDate);
           				SimpleDateFormat formatter = new SimpleDateFormat("ddMMMYYYY HH:mm");
           				createdDate = formatter.format(createdDateFormat);
           				}catch(ParseException e) {
           					  LOGGER.error("Date Format Issue");
           				}
                  shipment.setRemarks(shipment.getRemarks() + "-" + shipment.getDeliveryOrderNo()+"/"+createdDate);
               } else {
                  shipment.setRemarks(shipment.getRemarks());
               }
               
               this.insertShipmentRemarks(shipment);
             
            }
         }
      }
      return status;
   }

   /*
    * Method to move inventory info to freight out
    */
 
   private String moveInventoryToFreightOut(ShipmentData shipment, InactiveSearch inventoryInfo)
         throws CustomException {
      String status = "";
      if (!ObjectUtils.isEmpty(shipment)) {
         List<ShipmentFreightOut> freightOutList = super.fetchList("sqlGetManualFreightOutShipmentInventoryList",
               inventoryInfo, sqlSessionShipment);
         BigInteger totalpieces = super.fetchObject("sqlTotalPieces", shipment, sqlSessionShipment);
         if (shipment.getOrigin() != null && MultiTenantUtility.isTenantCityOrAirport(shipment.getOrigin())
               && (totalpieces == shipment.getPieces())) {
            super.updateData("sqlUpdateShipmentMasterDeparted", shipment.getShipmentId(), sqlSessionShipment);
         } else if (shipment.getDestination() != null && MultiTenantUtility.isTenantCityOrAirport(shipment.getDestination())
               && (totalpieces == shipment.getPieces())) {
            super.updateData("sqlUpdateShipmentMasterDelivered", shipment.getShipmentId(), sqlSessionShipment);
         }
         if (!CollectionUtils.isEmpty(freightOutList)) {
            for (ShipmentFreightOut t : freightOutList) {
               // Check for data empty
               if (!ObjectUtils.isEmpty(shipment.getFlightId())) {
                  t.setInboundFlightId(t.getFlightId());
                  t.setFlightId(shipment.getFlightId());
                  t.setAssignedUldTrolley("BULK");
               } else {
                  t.setInboundFlightId(t.getFlightId());
                  t.setAssignedUldTrolley("BULK");
               }

               // Set Delivery Info
               if (!ObjectUtils.isEmpty(shipment.getDeliveryId())) {
                  t.setDeliveredOn(shipment.getCreatedOn());
                  t.setDeliveryOrderNo(shipment.getDeliveryId());
                  t.setDeliveredBy(shipment.getCreatedBy());
               }

               if (!StringUtils.isEmpty(shipment.getTrmNumber())) {
                  t.setTrmNumber(shipment.getTrmNumber());
               }
               if(!shipment.getRemarkType().equalsIgnoreCase("NON") && StringUtils.isEmpty(shipment.getTrmNumber())){
            	   t.setTrmNumber(null);
               }
               // update deliveryOrdernNumber in inboundbreakdown StorageInfo
               super.updateData("sqlUpdateShipmentDeliveryInfoToStorageInfo", t, sqlSessionShipment);

               // Move the inventory to ShimentFreightOut
               this.insertData("sqlInsertManualShipmentFreightOut", t, sqlSessionShipment);

               // Move the SHC of an inventory
               BigInteger inventorySHCCount = this.fetchObject("sqlInvenSHCCount", t, sqlSessionShipment);
               if (inventorySHCCount.equals(BigInteger.ZERO)) {

                  this.insertData("sqlCopyMasterSHCToFreightOutSHC", t, sqlSessionShipment);

               } else {
                  this.insertData("sqlMoveShipmentInventoryToFreightOutSHC", t, sqlSessionShipment);
               }

               // Move the House of an Inventory
               this.insertData("sqlMoveShipmentInventoryToFreightOutHouse", t, sqlSessionShipment);

               // Delete the inventory SHC
               this.deleteData("sqlDeleteManualFreightOutShipmentInventorySHC", t.getShipmentInventoryId(),
                     sqlSessionShipment);

               // Delete the inventory house
               this.deleteData("sqlDeleteManualFreightOutShipmentInventoryHouse", t.getShipmentInventoryId(),
                     sqlSessionShipment);

               // Delete the inventory
               this.deleteData("sqlDeleteManualFreightOutShipmentInventory", t.getShipmentInventoryId(),
                     sqlSessionShipment);
            }
         }
         status = "sucess";
      }
      return status;
   }
   
   
   /*
    * Method to move inventory of house to freight out
    */
 
   private String moveInventoryToFreightOutHAWB(ShipmentData shipment, InactiveSearch inventoryInfo)
         throws CustomException {
      String status = "";
      if (!ObjectUtils.isEmpty(shipment)) {
         List<ShipmentFreightOut> freightOutList = super.fetchList("sqlGetManualFreightOutShipmentInventoryList",
               inventoryInfo, sqlSessionShipment);
         for (ShipmentFreightOut t : freightOutList) {
        	 
        	   // Set Delivery Info
             if (!ObjectUtils.isEmpty(shipment.getDeliveryId())) {
                t.setDeliveredOn(shipment.getCreatedOn());
                t.setDeliveryOrderNo(shipment.getDeliveryId());
                t.setDeliveredBy(shipment.getCreatedBy());
             }
             
             // update deliveryOrdernNumber in Inventory
            this.updateData("sqlUpdateShipmentDeliveryInfoToInventoryHAWB", t,sqlSessionShipment);
            
            // update deliveryOrdernNumber in inboundbreakdown StorageInfo
     		this.updateData("sqlUpdateShipmentDeliveryInfoToStorageInfoHAWB",t, sqlSessionShipment);
        	 	 
 			// Move the inventory
            BigInteger inventorySHCCount = this.fetchObject("sqlInventorySHCCountHAWB", t, sqlSessionShipment);

 			LOGGER.warn(" Before Delete InventorySHC Count :: " + inventorySHCCount + " ,InventoryID"
 					+ t.getShipmentInventoryId() + ",ShipmentID ::" + t.getShipmentId());

 			this.insertData("sqlMoveShipmentInventoryToFreightOutHAWB", t, sqlSessionShipment);
 			
 	   if(!ObjectUtils.isEmpty(t.getFreightOutId()))
 	   {

 			// Move the SHC of an inventory
 			if (inventorySHCCount.equals(BigInteger.ZERO)) {
 				
 				this.insertData("sqlMoveMasterSHCToFreightOutHAWB",t, sqlSessionShipment);
 				
 			} else {
                   
 				this.insertData("sqlMoveShipmentInventoryToFreightOutSHCHAWB", t,sqlSessionShipment);
 			}

 			// Move the House of an Inventory
 			this.insertData("sqlMoveShipmentInventoryToFreightOutHouseHAWB", t, sqlSessionShipment);
 	   }
 		

 			// Delete the inventory SHC
 			this.deleteData("sqlDeleteShipmentInventorySHCHAWB", t,sqlSessionShipment);

 			// Delete the inventory house
 			this.deleteData("sqlDeleteShipmentInventoryHouseHAWB", t,sqlSessionShipment);

 			// Delete the inventory
 			this.deleteData("sqlDeleteShipmentInventoryHAWB", t, sqlSessionShipment);

 		}
         
         
         status = "sucess";
      }
      return status;
   }

   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.INACTIVE_OLD_CARGO)
   public void insertShipmentRemarks(ShipmentData shipment) throws CustomException
   {
	   super.insertData("sqlInsertShipmentRemarksForManualFreightOut", shipment, sqlSessionShipment);  
   }
}