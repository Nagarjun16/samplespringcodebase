package com.ngen.cosys.satssginterfaces.mss.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.enums.ShipmentMasterAirmailInterfaceSqlId;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterface;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceCustomerAddressInfo;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceCustomerContactInfo;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceCustomerInfo;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceHandlingArea;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceRoutingInfo;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceShc;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceShcHandlingGroup;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentOtherChargeInfoAirmailInterface;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentVerificationAirmailInterfaceModel;

@Repository
public class ShipmentMasterAirmailInterfaceDAOImpl extends BaseDAO implements ShipmentMasterAirmailInterfaceDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSessionTemplate;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO#getFwbInfo(com.
    * ngen.cosys.impbd.shipment.document.model.ShipmentMaster)
    */
   @Override
   public ShipmentMasterAirmailInterface getFwbInfo(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException {
      return this.fetchObject(ShipmentMasterAirmailInterfaceSqlId.SQL_GET_FWB_INFO.toString(), shipmentMaster, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO#getShipment(com.
    * ngen.cosys.impbd.shipment.document.model.ShipmentMaster)
    */
   @Override
   public ShipmentMasterAirmailInterface getShipment(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException {
      return this.fetchObject(ShipmentMasterAirmailInterfaceSqlId.SQL_GET_SHIPMENTMASTER.toString(), shipmentMaster,
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
   public Boolean isPartShipment(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException {
      return this.fetchObject(ShipmentMasterAirmailInterfaceSqlId.SQL_CHECK_FOR_PART_SHIPMENT.toString(), shipmentMaster,
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
   public Boolean isSVCShipment(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException {
      return this.fetchObject(ShipmentMasterAirmailInterfaceSqlId.SQL_CHECK_FOR_SVC_SHIPMENT.toString(), shipmentMaster,
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
   public void createShipment(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException {
      if (Optional.ofNullable(shipmentMaster.getShipmentId()).isPresent()) {
         this.updateData(ShipmentMasterAirmailInterfaceSqlId.SQL_UPDATE_SHIPMENTMASTER.toString(), shipmentMaster, sqlSessionTemplate);
      } else {
         this.insertData(ShipmentMasterAirmailInterfaceSqlId.SQL_CREATE_SHIPMENTMASTER.toString(), shipmentMaster, sqlSessionTemplate);
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO#
    * createShipmentMasterCustomerInfo(com.ngen.cosys.impbd.shipment.document.model
    * .ShipmentMasterCustomerInfo)
    */
   @Override
   public void createShipmentMasterCustomerInfo(ShipmentMasterAirmailInterfaceCustomerInfo shipmentMasterCustomerInfo)
         throws CustomException {
      BigInteger shipmentMasterCustomerId = this.fetchObject(
            ShipmentMasterAirmailInterfaceSqlId.SQL_CHECK_SHIPMENTMASTERCUSTOMERINFO.toString(), shipmentMasterCustomerInfo,
            sqlSessionTemplate);
      if (Optional.ofNullable(shipmentMasterCustomerId).isPresent()) {
         shipmentMasterCustomerInfo.setId(shipmentMasterCustomerId);
         this.updateData(ShipmentMasterAirmailInterfaceSqlId.SQL_UPDATE_SHIPMENTMASTERCUSTOMERINFO.toString(),
               shipmentMasterCustomerInfo, sqlSessionTemplate);
      } else {
         this.insertData(ShipmentMasterAirmailInterfaceSqlId.SQL_CREATE_SHIPMENTMASTERCUSTOMERINFO.toString(),
               shipmentMasterCustomerInfo, sqlSessionTemplate);
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO#
    * createShipmentMasterCustomerAddressInfo(com.ngen.cosys.impbd.shipment.
    * document.model.ShipmentMasterCustomerAddressInfo)
    */
   @Override
   public void createShipmentMasterCustomerAddressInfo(
         ShipmentMasterAirmailInterfaceCustomerAddressInfo shipmentMasterCustomerAddressInfo) throws CustomException {
      BigInteger shipmentMasterCustomerAddressId = this.fetchObject(
            ShipmentMasterAirmailInterfaceSqlId.SQL_CHECK_SHIPMENTMASTERCUSTOMERADDRESSINFO.toString(),
            shipmentMasterCustomerAddressInfo, sqlSessionTemplate);
      if (Optional.ofNullable(shipmentMasterCustomerAddressId).isPresent()) {
         shipmentMasterCustomerAddressInfo.setId(shipmentMasterCustomerAddressId);
         this.updateData(ShipmentMasterAirmailInterfaceSqlId.SQL_UPDATE_SHIPMENTMASTERCUSTOMERADDRESSINFO.toString(),
               shipmentMasterCustomerAddressInfo, sqlSessionTemplate);
      } else {
         this.insertData(ShipmentMasterAirmailInterfaceSqlId.SQL_CREATE_SHIPMENTMASTERCUSTOMERADDRESSINFO.toString(),
               shipmentMasterCustomerAddressInfo, sqlSessionTemplate);
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO#
    * createShipmentMasterCustomerContactInfo(com.ngen.cosys.impbd.shipment.
    * document.model.ShipmentMasterCustomerContactInfo)
    */
   @Override
   public void createShipmentMasterCustomerContactInfo(List<ShipmentMasterAirmailInterfaceCustomerContactInfo> contacts)
         throws CustomException {
      for (ShipmentMasterAirmailInterfaceCustomerContactInfo contact : contacts) {
         Boolean b = this.fetchObject(ShipmentMasterAirmailInterfaceSqlId.SQL_CHECK_SHIPMENTMASTERCUSTOMERCONTACTINFO.toString(),
               contact, sqlSessionTemplate);
         if (!b) {
            this.insertData(ShipmentMasterAirmailInterfaceSqlId.SQL_CREATE_SHIPMENTMASTERCUSTOMERCONTACTINFO.toString(), contact,
                  sqlSessionTemplate);
         }
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO#
    * createShipmentMasterHandlingArea(com.ngen.cosys.impbd.shipment.document.model
    * .ShipmentMasterHandlingArea)
    */
   @Override
   public void createShipmentMasterHandlingArea(ShipmentMasterAirmailInterfaceHandlingArea shipmentMasterHandlingArea)
         throws CustomException {
      Boolean b = this.fetchObject(ShipmentMasterAirmailInterfaceSqlId.SQL_CHECK_SHIPMENTMASTERHANDLINGAREA.toString(),
            shipmentMasterHandlingArea, sqlSessionTemplate);
      if (!b) {
         this.insertData(ShipmentMasterAirmailInterfaceSqlId.SQL_CREATE_SHIPMENTMASTERHANDLINGAREA.toString(),
               shipmentMasterHandlingArea, sqlSessionTemplate);
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
   public void createShipmentMasterRoutingInfo(List<ShipmentMasterAirmailInterfaceRoutingInfo> routing) throws CustomException {
      for (ShipmentMasterAirmailInterfaceRoutingInfo route : routing) {
         Boolean b = this.fetchObject(ShipmentMasterAirmailInterfaceSqlId.SQL_CHECK_SHIPMENTMASTERROUTINGINFO.toString(), route,
               sqlSessionTemplate);
         if (!b) {
            this.insertData(ShipmentMasterAirmailInterfaceSqlId.SQL_CREATE_SHIPMENTMASTERROUTINGINFO.toString(), route,
                  sqlSessionTemplate);
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
   public void createShipmentMasterShc(List<ShipmentMasterAirmailInterfaceShc> shcs) throws CustomException {
      for (ShipmentMasterAirmailInterfaceShc shc : shcs) {
         Boolean b = this.fetchObject(ShipmentMasterAirmailInterfaceSqlId.SQL_CHECK_SHIPMENTMASTERSHC.toString(), shc,
               sqlSessionTemplate);
         if (!b) {
            this.insertData(ShipmentMasterAirmailInterfaceSqlId.SQL_CREATE_SHIPMENTMASTERSHC.toString(), shc, sqlSessionTemplate);
         }
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO#
    * createShipmentMasterShcHandlingGroup(com.ngen.cosys.impbd.shipment.document.
    * model.ShipmentMasterShcHandlingGroup)
    */
   @Override
   public void createShipmentMasterShcHandlingGroup(List<ShipmentMasterAirmailInterfaceShcHandlingGroup> shcHandlingGroup)
         throws CustomException {
      for (ShipmentMasterAirmailInterfaceShcHandlingGroup t : shcHandlingGroup) {
         Boolean b = this.fetchObject(ShipmentMasterAirmailInterfaceSqlId.SQL_CHECK_SHIPMENTMASTERSHCHANDLINGGROUP.toString(), t,
               sqlSessionTemplate);
         if (!b) {
            this.insertData(ShipmentMasterAirmailInterfaceSqlId.SQL_CREATE_SHIPMENTMASTERSHCHANDLINGGROUP.toString(), t,
                  sqlSessionTemplate);
         }
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO#
    * createShipmentOtherChargeInfo(com.ngen.cosys.impbd.shipment.document.model.
    * ShipmentOtherChargeInfo)
    */
   @Override
   public void createShipmentOtherChargeInfo(ShipmentOtherChargeInfoAirmailInterface shipmentOtherChargeInfo) throws CustomException {
      Boolean b = this.fetchObject(ShipmentMasterAirmailInterfaceSqlId.SQL_CHECK_SHIPMENTOTHERCHARGEINFO.toString(),
            shipmentOtherChargeInfo, sqlSessionTemplate);
      if (b) {
         this.updateData(ShipmentMasterAirmailInterfaceSqlId.SQL_UPDATE_SHIPMENTOTHERCHARGEINFO.toString(), shipmentOtherChargeInfo,
               sqlSessionTemplate);
      } else {
         this.insertData(ShipmentMasterAirmailInterfaceSqlId.SQL_CREATE_SHIPMENTOTHERCHARGEINFO.toString(), shipmentOtherChargeInfo,
               sqlSessionTemplate);
      }
   }

	@Override
	public void updateShipmentDocumentReceivedOn(ShipmentVerificationAirmailInterfaceModel shipmentVerModel) throws CustomException {
		this.updateData(ShipmentMasterAirmailInterfaceSqlId.SQL_UPDATE_DOCUMENTRECEIVEDON.toString(), shipmentVerModel,sqlSessionTemplate);
	}
	
}