package com.ngen.cosys.impbd.shipment.document.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.model.CdhDocumentmaster;
import com.ngen.cosys.impbd.shipment.document.constant.ShipmentMasterSqlId;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMasterRoutingInfo;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMasterShc;

@Repository
public class ShipmentMasterDAOImpl extends BaseDAO implements ShipmentMasterDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSessionTemplate;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO#getShipment(com.
    * ngen.cosys.impbd.shipment.document.model.ShipmentMaster)
    */
   @Override
   public ShipmentMaster getShipment(ShipmentMaster shipmentMaster) throws CustomException {
      return this.fetchObject(ShipmentMasterSqlId.SQL_GET_SHIPMENTMASTER.toString(), shipmentMaster,
            sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO#isPartShipment(
    * com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster)
    */
   @Override
   public Boolean isPartShipment(ShipmentMaster shipmentMaster) throws CustomException {
      return this.fetchObject(ShipmentMasterSqlId.SQL_CHECK_FOR_PART_SHIPMENT.toString(), shipmentMaster,
            sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO#isSVCShipment(
    * com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster)
    */
   @Override
   public Boolean isSVCShipment(ShipmentMaster shipmentMaster) throws CustomException {
      return this.fetchObject(ShipmentMasterSqlId.SQL_CHECK_FOR_SVC_SHIPMENT.toString(), shipmentMaster,
            sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO#createShipment(
    * com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster)
    */

   @Override
   public void createShipment(ShipmentMaster shipmentMaster) throws CustomException {
      if (Optional.ofNullable(shipmentMaster.getShipmentId()).isPresent()) {
         this.updateData(ShipmentMasterSqlId.SQL_UPDATE_SHIPMENTMASTER.toString(), shipmentMaster, sqlSessionTemplate);
      } else {
         this.insertData(ShipmentMasterSqlId.SQL_CREATE_SHIPMENTMASTER.toString(), shipmentMaster, sqlSessionTemplate);
      }

   }

   @Override
   public void updateExportBookingPieceWieght(ShipmentMaster shipmentMaster) throws CustomException {
      // Update shipment exp booking pieces/weight
      if (!ObjectUtils.isEmpty(shipmentMaster.getPiece()) && !ObjectUtils.isEmpty(shipmentMaster.getWeight())) {
         this.updateData(ShipmentMasterSqlId.SQL_UPDATE_EXP_SHIPMENTBOOKING.toString(), shipmentMaster,
               sqlSessionTemplate);
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO#
    * createShipmentMasterRoutingInfo(com.ngen.cosys.impbd.shipment.document.model.
    * ShipmentMasterRoutingInfo)
    */
   @Override
   public void createShipmentMasterRoutingInfo(List<ShipmentMasterRoutingInfo> routing) throws CustomException {
	   if (!CollectionUtils.isEmpty(routing)) {
			this.deleteData(ShipmentMasterSqlId.SQL_DELETE_SHIPMENTMASTERROUTINGINFO.toString(),
					routing.get(0).getShipmentId(), sqlSessionTemplate);
			
      for (ShipmentMasterRoutingInfo route : routing) {
         Boolean b = this.fetchObject(ShipmentMasterSqlId.SQL_CHECK_SHIPMENTMASTERROUTINGINFO.toString(), route,
               sqlSessionTemplate);
         if (!b) {
            this.insertData(ShipmentMasterSqlId.SQL_CREATE_SHIPMENTMASTERROUTINGINFO.toString(), route,
                  sqlSessionTemplate);
         } else {
            this.updateData(ShipmentMasterSqlId.SQL_UPDATE_SHIPMENTMASTERROUTINGINFO.toString(), route,
                  sqlSessionTemplate);
         }
      }
	 }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO#
    * createShipmentMasterShc(com.ngen.cosys.impbd.shipment.document.model.
    * ShipmentMasterShc)
    */
   @Override
   public void createShipmentMasterShc(List<ShipmentMasterShc> shcs) throws CustomException {
      for (ShipmentMasterShc shc : shcs) {
         Boolean b = this.fetchObject(ShipmentMasterSqlId.SQL_CHECK_SHIPMENTMASTERSHC.toString(), shc,
               sqlSessionTemplate);
         if (!b) {
            this.insertData(ShipmentMasterSqlId.SQL_CREATE_SHIPMENTMASTERSHC.toString(), shc, sqlSessionTemplate);
         }
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO#
    * updateCDHShipmetMasterData(com.ngen.cosys.impbd.model.CdhDocumentmaster)
    */
   @Override
   public void updateCDHShipmetMasterData(CdhDocumentmaster cdhDocumentMaster) throws CustomException {
	      cdhDocumentMaster.setDocumentstatus("Received");
		  cdhDocumentMaster.setLocationName("Transit");
		  cdhDocumentMaster.setReceiveddate(LocalDateTime.now());
		  insertCdhDocumentMaster(cdhDocumentMaster);
      this.updateData("updateBarcodeFlag", cdhDocumentMaster, sqlSessionTemplate);
   }
   
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CDH_DOC_VERIFICATION)
   public void insertCdhDocumentMaster(CdhDocumentmaster cdhDocumentMaster) throws CustomException {
	   this.insertData("insertCdhDocumentMaster", cdhDocumentMaster, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO#
    * deriveDocumentReceivedDateTime(com.ngen.cosys.impbd.shipment.document.model.
    * ShipmentMaster)
    */
   @Override
   public LocalDateTime deriveDocumentReceivedDateTime(ShipmentMaster shipmentMaster) throws CustomException {
      return fetchObject("sqlGetEarliestDocumentReceiveDateForShipment", shipmentMaster, sqlSessionTemplate);
   }
   
   @Override
   public ShipmentMaster checkCOUShipment(ArrivalManifestShipmentInfoModel shipmentMaster) throws CustomException{
	   
	  return this.fetchObject("sqlGetCOUShipment", shipmentMaster,
	            sqlSessionTemplate);  
   }

   @Override
   public ShipmentMaster getShipmentInfo(ShipmentMaster shipmentMaster) throws CustomException {
	   
	   return this.fetchObject(ShipmentMasterSqlId.SQL_GET_SHIPMENTMASTER_INFO.toString(), shipmentMaster,
	            sqlSessionTemplate);
   }

}