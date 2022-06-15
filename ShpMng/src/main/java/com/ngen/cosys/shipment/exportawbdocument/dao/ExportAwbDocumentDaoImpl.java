package com.ngen.cosys.shipment.exportawbdocument.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.exportawbdocument.model.ExportAwbDocumentModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ExportAwbDocumentSearchModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ShipmentMasterCustomerAddressInfoModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ShipmentMasterCustomerContactInfoModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ShipmentMasterCustomerInfoModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ShipmentMasterRoutingInfoModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ShipmentMasterSHCModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ShipmentRemarksModel;

@Repository
public class ExportAwbDocumentDaoImpl extends BaseDAO implements ExportAwbDocumentDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	private static final String SQL_GET_EXPORT_AWB_DOCUMENT = "sqlGetExportAwbDocument";
	private static final String SQL_INSERT_SHIPMENT_MASTER = "expawbdoc_sqlInsertShipmentMaster";
	private static final String SQL_INSERT_SHIPMENT_MASTER_CUSTOMER_INFO = "expawbdoc_sqlInsertShipmentMasterCustomerInfo";
	private static final String SQL_INSERT_SHIPMENT_OTHER_CHARGE_INFO = "expawbdoc_sqlInsertShipmentOtherChargeInfo";
	private static final String SQL_INSERT_SHIPMENT_MASTER_SHC = "expawbdoc_sqlInsertShipmentMasterShc";
	private static final String SQL_INSERT_SHIPMENT_MASTER_ROUTING_INFO = "expawbdoc_sqlInsertShipmentMasterRoutingInfo";
	private static final String SQL_INSERT_SHIPMENT_MASTER_CUSTOMER_ADDRESS_INFO = "expawbdoc_sqlInsertShipmentMasterCustomerAddressInfo";
	private static final String SQL_INSERT_SHIPMENT_MASTER_CUSTOMER_CONTACT_INFO = "expawbdoc_sqlInsertShipmentMasterCustomerContactInfo";
	//private static final String SQL_INSERT_IVRS_CUSTOMER_CONTACT_INFO = "expawbdoc_sqlInsertIvrsCustomerContactInfo";
	private static final String SQL_INSERT_SHIPMENT_REMARKS_INFO = "expawbdoc_sqlInsertShipmentRemarks";
	
	private static final String SQL_UPDATE_SHIPMENT_MASTER = "expawbdoc_sqlUpdateShipmentMaster";
	private static final String SQL_UPDATE_SHIPMENT_MASTER_CUSTOMER_INFO = "expawbdoc_sqlUpdateShipmentMasterCustomerInfo";
	private static final String SQL_UPDATE_SHIPMENT_OTHER_CHARGE_INFO = "expawbdoc_sqlUpdateShipmentOtherChargeInfo";
	private static final String SQL_UPDATE_SHIPMENT_MASTER_CUSTOMER_ADDRESS_INFO = "expawbdoc_sqlUpdateShipmentMasterCustomerAddressInfo";
	private static final String SQL_UPDATE_DOCUMENT_COMPLETE = "sqlDocumentComplete";
	private static final String SQL_UPDATE_DOCUMENT_REPOEN = "sqlDocumentReopen";
	
	
	private static final String SQL_DELETE_SHIPMENT_MASTER_SHC = "sqlDeleteShipmentMasterSHC";
	private static final String SQL_DELETE_SHIPMENT_MASTER_ROUTING_INFO = "expawbdoc_sqlDeleteShipmentMasterRoutingInfo";
	private static final String SQL_DELETE_SHIPMENT_MASTER_CUSTOMER_CONTACT_INFO = "expawbdoc_sqlDeleteShipmentMasterCustomerContactInfo";
	//private static final String SQL_DELETE_IVRS_NOTIFICATION_CONTACT_INFO = "sqlDeleteIvrsNotificationContactInfo";
	private static final String SQL_DELETE_OSI_REMARKS = "sqlDeleteOSIRemarks";
	private static final String SQL_DELETE_SSR_REMARKS = "sqlDeleteSSRRemarks";
	private static final String SQL_DELETE_GEN_REMARKS = "sqlDeleteGENRemarks";
	
	private static final String SQL_FETCH_FLIGHT_ID = "expawbdoc_sqlFetchflightId";
	private static final String SQL_GET_SHIPMENT_ID = "expawbdoc_sqlGetShipmentId";
	private static final String SQL_GET_SHIPMENT_MASTER_CUSTOMER_INFO_ID = "sqlGetShipmentMasterCustomerInfoId";
	private static final String SQL_GET_SHIPMENT_OTHER_CHARGE_INFO = "expawbdoc_sqlGetShipmentOtherChargeInfo";
	private static final String SQL_GET_SHIPMENT_MASTER_CUSTOMER_ADDRESS_INFO_ID = "sqlGetShipmentMasterCustomerAddressInfoId";
	private static final String SQL_GET_CUSTOMER_ADDRESS_DETAILS = "sqlGetCustomerAddressDetails";
	
	
	
	@Override
	public ExportAwbDocumentModel getExportAwbDocument(ExportAwbDocumentSearchModel exportAwbDocumentSearchModel)
			throws CustomException {
		return fetchObject(SQL_GET_EXPORT_AWB_DOCUMENT, exportAwbDocumentSearchModel, sqlSession);
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.EXPORT_AWB_DOCUMENT)
	public void saveExportAwbDocument(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException {
		validate(exportAwbDocumentModel);
		setFlightId(exportAwbDocumentModel);//setting flightId in exportAwbDocumentModel
		saveShipmentMaster(exportAwbDocumentModel); //insert/update data of Shipment_master table
		saveShipmentOtherChargeInfo(exportAwbDocumentModel); //insert/update data of Shipment_OtherChargeInfo table
		saveSHC(exportAwbDocumentModel); //insert/update data of Shipment_MasterSHC table
		saveShipmentMasterRoutingInfo(exportAwbDocumentModel);//insert/update data of Shipment_MasterRoutingInfo table
		saveShipmentMasterCustomerInfo(exportAwbDocumentModel.getShipper(),exportAwbDocumentModel.getShipmentId());//insert/update data of Shipment_MasterCustomerInfo,Shipment_MasterCustomerAddressInfo,Shipment_MasterCustomerContactInfo tables of shipper
		saveShipmentMasterCustomerInfo(exportAwbDocumentModel.getConsignee(),exportAwbDocumentModel.getShipmentId());////insert/update data of Shipment_MasterCustomerInfo,Shipment_MasterCustomerAddressInfo,Shipment_MasterCustomerContactInfo tables of consignee		
		saveOSIRemarks(exportAwbDocumentModel);//insert/update data of Shipment_Remarks table with remark type 'OSI'
		saveSSRemarks(exportAwbDocumentModel);//insert/update data of Shipment_Remarks table with remark type 'SSR'
		saveGeneralRemarks(exportAwbDocumentModel);//insert/update data of Shipment_Remarks table with remark type 'GEN'
	}
	
	@Override
	public void documentComplete(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException {
		updateData(SQL_UPDATE_DOCUMENT_COMPLETE,exportAwbDocumentModel, sqlSession);
	}

	@Override
	public void documentReopen(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException {
		updateData(SQL_UPDATE_DOCUMENT_REPOEN,exportAwbDocumentModel, sqlSession);
	}
	
	@Override
	public ShipmentMasterCustomerAddressInfoModel getAddressDetails(ShipmentMasterCustomerInfoModel shipmentMasterCustomerInfoModel) throws CustomException {
		return fetchObject(SQL_GET_CUSTOMER_ADDRESS_DETAILS,shipmentMasterCustomerInfoModel, sqlSession);
	}
	
	private void validate(ExportAwbDocumentModel exportAwbDocumentModel) {
		// TODO Auto-generated method stub
		
	}

	private void setFlightId(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException {
    	final BigInteger flightId=fetchObject(SQL_FETCH_FLIGHT_ID,exportAwbDocumentModel, sqlSession);
    	exportAwbDocumentModel.setFlightId(flightId);
    }
	
	//@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.EXPORT_AWB_DOCUMENT)
	private void saveShipmentMaster(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException {
		final BigInteger shipmentId = fetchObject(SQL_GET_SHIPMENT_ID,exportAwbDocumentModel, sqlSession);
		
		if(Optional.ofNullable(shipmentId).isPresent()) {
			exportAwbDocumentModel.setShipmentId(shipmentId);
			updateData(SQL_UPDATE_SHIPMENT_MASTER,exportAwbDocumentModel, sqlSession);
		}else {
			insertData(SQL_INSERT_SHIPMENT_MASTER, exportAwbDocumentModel, sqlSession);
		}
	}
	
	//@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.EXPORT_AWB_DOCUMENT)
    private void saveShipmentOtherChargeInfo(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException {
    	final String chargeCode=exportAwbDocumentModel.getChargeCode();
    	if(Optional.ofNullable(chargeCode).isPresent()) {
    		final Boolean isShipmentOtherChargeInfoPresent = fetchObject(SQL_GET_SHIPMENT_OTHER_CHARGE_INFO,exportAwbDocumentModel, sqlSession);
    		if(isShipmentOtherChargeInfoPresent) {
    			updateData(SQL_UPDATE_SHIPMENT_OTHER_CHARGE_INFO,exportAwbDocumentModel, sqlSession);
    		}else {
    			insertData(SQL_INSERT_SHIPMENT_OTHER_CHARGE_INFO, exportAwbDocumentModel, sqlSession);
    		}
    	}
    }
	
	//@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.EXPORT_AWB_DOCUMENT)
	private void saveSHC(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException {
		 List<ShipmentMasterSHCModel> shcs=exportAwbDocumentModel.getSpecialHandlingCodeList();
		 // Add SHC
	      if (!CollectionUtils.isEmpty(shcs)) {
	    	  shcs.forEach(t -> t.setShipmentId(exportAwbDocumentModel.getShipmentId()));
	      } 

	      deleteData(SQL_DELETE_SHIPMENT_MASTER_SHC, shcs, sqlSession);
	      
	      for (ShipmentMasterSHCModel shc : shcs) {
	         if (Optional.ofNullable(shc.getSpecialHandlingCode()).isPresent()
	               && (!shc.getSpecialHandlingCode().isEmpty())) {
	            insertData(SQL_INSERT_SHIPMENT_MASTER_SHC, shc, sqlSession);
	         }
	      }
	   }
	
	//@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.EXPORT_AWB_DOCUMENT)
	private void saveShipmentMasterRoutingInfo(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException {
	  final List<ShipmentMasterRoutingInfoModel> routing=exportAwbDocumentModel.getRouting();
		
	  // Add Routing Info
      if (!CollectionUtils.isEmpty(routing)) {
    	  routing.forEach(t -> t.setShipmentId(exportAwbDocumentModel.getShipmentId()));
      }
      
      // Delete the existing routing information
      deleteData(SQL_DELETE_SHIPMENT_MASTER_ROUTING_INFO, routing, sqlSession);
      
      for (ShipmentMasterRoutingInfoModel t : routing) {
         if (!StringUtils.isEmpty(t.getCarrier()) || !StringUtils.isEmpty(t.getFromPoint())) {
            // insert the new routing info
            insertData(SQL_INSERT_SHIPMENT_MASTER_ROUTING_INFO, t, sqlSession);
         }
      }

   }
   
   //@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.EXPORT_AWB_DOCUMENT)
   private void saveShipmentMasterCustomerInfo(ShipmentMasterCustomerInfoModel customerInfo,BigInteger shipmentId) throws CustomException {
	   customerInfo.setShipmentId(shipmentId);	   
	   final Integer b = fetchObject(SQL_GET_SHIPMENT_MASTER_CUSTOMER_INFO_ID,customerInfo, sqlSession);
       
	   if (b != null) {
		   customerInfo.setShipmentCustomerInfoId(BigInteger.valueOf(b));
           updateData(SQL_UPDATE_SHIPMENT_MASTER_CUSTOMER_INFO,customerInfo, sqlSession);
       } else {
          insertData(SQL_INSERT_SHIPMENT_MASTER_CUSTOMER_INFO,
        		  customerInfo, sqlSession);
       }

		// Save address info
	   saveAddressInfo(customerInfo);
	   
	   //  Save IVRSNotificationContactInfo
	   //saveIVRSNotificationContactInfo(customerInfo);
   }
   
   //@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.EXPORT_AWB_DOCUMENT)
   private void saveAddressInfo(ShipmentMasterCustomerInfoModel customerInfo) throws CustomException{
	   final ShipmentMasterCustomerAddressInfoModel addressInfo = customerInfo.getAddress();		
	   addressInfo.setShipmentCustomerInfoId(customerInfo.getShipmentCustomerInfoId());
		
	   final BigInteger customerAddInfoId = fetchObject(SQL_GET_SHIPMENT_MASTER_CUSTOMER_ADDRESS_INFO_ID,addressInfo, sqlSession);
	   if (Optional.ofNullable(customerAddInfoId).isPresent()) {
		   addressInfo.setShipmentCustomerAddInfoId(customerAddInfoId);
	      updateData(SQL_UPDATE_SHIPMENT_MASTER_CUSTOMER_ADDRESS_INFO,
	    		  addressInfo, sqlSession);
	   } else {
	     insertData(SQL_INSERT_SHIPMENT_MASTER_CUSTOMER_ADDRESS_INFO,
	    		 addressInfo, sqlSession);
	   }
	   
	   saveContactInfo(addressInfo);//saving shipper/consignee contact info
   }
   
   //@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.EXPORT_AWB_DOCUMENT)
   private void saveContactInfo(ShipmentMasterCustomerAddressInfoModel addressInfo) throws CustomException{
	   final List<ShipmentMasterCustomerContactInfoModel> shipperContactList=addressInfo.getContactInformation();	   
	   deleteData(SQL_DELETE_SHIPMENT_MASTER_CUSTOMER_CONTACT_INFO, addressInfo.getShipmentCustomerAddInfoId(), sqlSession);
	   if(Optional.ofNullable(shipperContactList).isPresent()) {
		   shipperContactList.forEach(t -> t.setShipmentCustomerAddInfoId(addressInfo.getShipmentCustomerAddInfoId()));
		   for (ShipmentMasterCustomerContactInfoModel contacts : shipperContactList) {
		         if (!StringUtils.isEmpty(contacts.getContactTypeCode())) {
		        	if(!StringUtils.isEmpty(contacts.getContactTypeDetail())) {
			            insertData(SQL_INSERT_SHIPMENT_MASTER_CUSTOMER_CONTACT_INFO, contacts,sqlSession);
		        	}
		        	else {
			        	 contacts.addError("awb.cne.contact.dtl.mandatory", "contactTypeDetail", ErrorType.ERROR);
			             throw new CustomException(contacts.getMessageList());
			        }
		         }
		       }
	   }
   }
   
   /*
   //@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.EXPORT_AWB_DOCUMENT)
   private void saveIVRSNotificationContactInfo(ShipmentMasterCustomerInfoModel customerInfo) throws CustomException{
	   final List<IVRSNotificationContactInfo> ivrsContactList=customerInfo.getIvrsContactInformation();	
       deleteData(SQL_DELETE_IVRS_NOTIFICATION_CONTACT_INFO, customerInfo, sqlSession);
       
       if(Optional.ofNullable(ivrsContactList).isPresent()) {
    	   ivrsContactList
				.forEach(t -> {
								t.setShipmentId(customerInfo.getShipmentId());
							   }); 
    	   for (IVRSNotificationContactInfo contacts : ivrsContactList) {
	    		   if (!StringUtils.isEmpty(contacts.getContactTypeCode()) && !StringUtils.isEmpty(contacts.getContactTypeDetail())) {
			              insertData(SQL_INSERT_IVRS_CUSTOMER_CONTACT_INFO, contacts,sqlSession);
			           }else {
			        	   contacts.addError("agent.ivrs.dtl.mandatory", "contactTypeDetail", ErrorType.ERROR);
			               throw new CustomException(contacts.getMessageList());
			           }
		       }
       }
   }
   */
   
   //@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.EXPORT_AWB_DOCUMENT)
   private void saveOSIRemarks(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException {
	      deleteData(SQL_DELETE_OSI_REMARKS, exportAwbDocumentModel, sqlSession);
	      
	      final List<ShipmentRemarksModel> otherServiceInformationList=exportAwbDocumentModel.getOtherServiceInformationList();
	      if(Optional.ofNullable(otherServiceInformationList).isPresent()) {
	    	  for (ShipmentRemarksModel t : otherServiceInformationList) {
	 	         if (!StringUtils.isEmpty(t.getShipmentRemarks())) {
	 	            t.setShipmentId(exportAwbDocumentModel.getShipmentId());
	 	            t.setShipmentNumber(exportAwbDocumentModel.getShipmentNumber());
	 	            t.setShipmentDate(exportAwbDocumentModel.getShipmentDate());
	 	            t.setRemarkType("OSI");
	 	            t.setShipmentType(exportAwbDocumentModel.getShipmentType());
	 	            t.setFlightId(exportAwbDocumentModel.getFlightId());
	 	            insertData(SQL_INSERT_SHIPMENT_REMARKS_INFO,t ,sqlSession);
	 	         }
	 	      }
	      }
	      
	   }
   
   //@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.EXPORT_AWB_DOCUMENT)
   private void saveSSRemarks(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException {
	   deleteData(SQL_DELETE_SSR_REMARKS, exportAwbDocumentModel, sqlSession);
	      
	      final List<ShipmentRemarksModel> specialServiceInformationList=exportAwbDocumentModel.getSpecialServiceRequestList();
	      if(Optional.ofNullable(specialServiceInformationList).isPresent()) {
	    	  for (ShipmentRemarksModel t : specialServiceInformationList) {
	 	         if (!StringUtils.isEmpty(t.getShipmentRemarks())) {
	 	            t.setShipmentId(exportAwbDocumentModel.getShipmentId());
	 	            t.setShipmentNumber(exportAwbDocumentModel.getShipmentNumber());
	 	            t.setShipmentDate(exportAwbDocumentModel.getShipmentDate());
	 	            t.setRemarkType("SSR");
	 	            t.setShipmentType(exportAwbDocumentModel.getShipmentType());
	 	            t.setFlightId(exportAwbDocumentModel.getFlightId());
	 	            insertData(SQL_INSERT_SHIPMENT_REMARKS_INFO,t ,sqlSession);
	 	         }
	 	      }
	      }
   }

   //@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.EXPORT_AWB_DOCUMENT)
   private void saveGeneralRemarks(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException {
	   deleteData(SQL_DELETE_GEN_REMARKS, exportAwbDocumentModel, sqlSession);
	      
	      final List<ShipmentRemarksModel> generalRemarksList=exportAwbDocumentModel.getGeneralRemarksList();
	      if(Optional.ofNullable(generalRemarksList).isPresent()) {
	    	  for (ShipmentRemarksModel t : generalRemarksList) {
	 	         if (!StringUtils.isEmpty(t.getShipmentRemarks())) {
	 	            t.setShipmentId(exportAwbDocumentModel.getShipmentId());
	 	            t.setShipmentNumber(exportAwbDocumentModel.getShipmentNumber());
	 	            t.setShipmentDate(exportAwbDocumentModel.getShipmentDate());
	 	            t.setRemarkType("GEN");
	 	            t.setShipmentType(exportAwbDocumentModel.getShipmentType());
	 	            t.setFlightId(exportAwbDocumentModel.getFlightId());
	 	            insertData(SQL_INSERT_SHIPMENT_REMARKS_INFO,t ,sqlSession);
	 	         }
	 	      }
	      }
   }

}
