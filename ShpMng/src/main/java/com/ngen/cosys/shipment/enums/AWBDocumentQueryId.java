package com.ngen.cosys.shipment.enums;

public enum AWBDocumentQueryId {

   SQL_CHECK_FOR_SVC_SHIPMENT("sqlCheckForSVCShipment"),

   SQL_CHECK_FOR_PART_SHIPMENT("sqlCheckForPartShipment"),

   SQL_GET_AWBDOCUMENT_INFO("sqlGetShipmentMasterAWBDocument"),
   SQL_GET_VERIFICATION_INFO("sqlGetDocumentVerificationData"),
   SQL_GET_DOWNSTREAM_FLAGS("sqlGetDownstreamFlags"),
   SQL_GET_ACCEPTANCE_INFO("sqlGetAcceptanceInfo"),
   SQL_GET_CC_FEE("sqlgetccFee"),
   SQL_GET_ALL_APPOINTED_AGENT("getAllAppointedAgents"),
   SQL_GET_MIN_CC_FEE("sqlgetMinccFee"),

   SQL_GET_FWB_INFO("sqlGetShipmentFreightWayBill"),

   SQL_GET_SHIPMENT_INFO_FROM_ECC("sqlGetShipmentInfoFromImportECC"),
   
   SQL_GET_SHIPMENT_INFO_FROM_ECC_SHC("sqlGetShipmentInfoFromImportECCSHC"),

   SQL_GET_SHIPMENT_INFO_BOOKING("sqlGetShipmentInfoFromBooking"),

   DELETE_AWB_SHC_DETAILS("deleteForUpdateAWBSHCDetailsShipmentMaster"),
   DELETE_AWB_CUST_CONTACT_INFO_DETAILS("deleteForUpdateAWBShipperContactInfoDetailsShipmentMaster"),
   UPDATE_AWB_SSROSI_DETAILS_BY_SHIPMENT("updateAWBSSROSIDetailsShipmentMasterByShipment"),
   UPDATE_AWB_SSROSI_DETAILS_BY_ID("updateAWBSSROSIDetailsShipmentMaster"),
   UPDATE_AWB_ROUTING("updateAWBRoutingShipmentMaster"),

   SQL_CHECK_SHIPMENTMASTER_EXISTS("sqlCheckShipmentMasterExists"),
   SQL_CREATE_SHIPMENTMASTER("sqlInsertShipmentMaster"),
   SQL_UPDATE_SHIPMENTMASTER("sqlUpdateShipmentMaster"),
   SQL_UPDATE_SHIPMENTTYPE("sqlUpdateShipmentType"),
   SQL_GET_SHIPMENTID("sqlGetShipmentId"),
   SQL_GET_SHIPMENTMASTERID("sqlGetShipmentMasterId"),
   SQL_GET_SHIPMENTMASTER_DESTINATION("sqlGetShipmentMasterDestination"),
   SQL_CHECK_IMPORT_SHIPMENT("sqlcheckImportShipment"),
   SQL_CHECK_SHIPMENTMASTERCUSTOMERADDRESSINFO("sqlGetShipmentMasterCustomerAddressInfo"),
   SQL_CREATE_SHIPMENTMASTERCUSTOMERADDRESSINFO("sqlInsertShipmentMasterCustomerAddressInfo"),
   SQL_UPDATE_SHIPMENTMASTERCUSTOMERADDRESSINFO("sqlUpdateShipmentMasterCustomerAddressInfo"),

   SQL_CHECK_SHIPMENTMASTERCUSTOMERCONTACTINFO("sqlGetShipmentMasterCustomerContactInfo"),
   SQL_CREATE_SHIPMENTMASTERCUSTOMERCONTACTINFO("sqlInsertShipmentMasterCustomerContactInfo"),

   SQL_CHECK_SHIPMENTMASTERCUSTOMERINFO("sqlGetShipmentMasterCustomerInfo"),
   SQL_CREATE_SHIPMENTMASTERCUSTOMERINFO("sqlInsertShipmentMasterCustomerInfo"),
   SQL_UPDATE_SHIPMENTMASTERCUSTOMERINFO("sqlUpdateShipmentMasterCustomerInfo"),

   SQL_CHECK_SHIPMENTMASTERHANDLINGAREA("sqlGetShipmentMasterHandlingArea"),
   SQL_CREATE_SHIPMENTMASTERHANDLINGAREA("sqlInsertShipmentMasterHandlingArea"),

   SQL_CHECK_SHIPMENTMASTERROUTINGINFO("sqlGetShipmentMasterRoutingInfo"),
   SQL_CREATE_SHIPMENTMASTERROUTINGINFO("sqlInsertShipmentMasterRoutingInfo"),
   SQL_DELETE_SHIPMENTMASTERROUTINGINFO("sqlDeleteShipmentMasterRoutingInfo"),
   SQL_CHECK_SHIPMENTMASTERROUTINGINFO_WITH_SHIPMENTNUMBER("sqlGetShipmentMasterRoutingInfoWithShipmentNumberAndDate"),

   SQL_CHECK_SHIPMENTMASTERSHC("sqlGetShipmentMasterShc"),
   SQL_CREATE_SHIPMENTMASTERSHC("sqlInsertShipmentMasterShc"),

   SQL_CHECK_SHIPMENTMASTERSHCHANDLINGGROUP("sqlGetShipmentMasterShcHandlingGroup"),
   SQL_CREATE_SHIPMENTMASTERSHCHANDLINGGROUP("sqlInsertShipmentMasterShcHandlingGroup"),
   INSERT_SHIPMENT_REMARKS("sqlInsertShipmentRemarks"),
   GET_SHIPMENT_REMARKS("sqlGetShipmentRemarks"),

   // local authority
   DELETE_SHIPMENT_MASTER_LOCAL_AUTHORITY_DETAILS("deleteLocalAuthInfoDetails"),
   DELETE_SHIPMENT_MASTER_LOCAL_AUTHORITY_INFO("deleteLocalAuthInfo"),
   INSERT_SHIPMENT_MASTER_LOCAL_AUTHORITY_INFO("sqlInsertShipmentMasterLocalAuthorityInfo"),
   INSERT_SHIPMENT_MASTER_LOCAL_AUTHORITY_DETAILS("sqlInsertShipmentMasterLocalAuthorityDetails"),
   GET_SHIPMENT_MASTER_LOCAL_AUTHORITY_INFO("getShipmentMasterLocalAuthorityInfo"),

   // local authority....
   SQL_INSERT_SHIPMENT_DELIVERY_REQUEST_LOCAL_AUTHORITY("sqlInsertShipmentDeliveryRequestLocalAuthority"),
   SQL_INSERT_SHIPMENT_DELIVERY_REQUEST_LOCAL_AUTHORITY_DETAIL("sqlInsertShipmentDeliveryRequestLocalAuthorityDetail"),

   SQL_CHECK_SHIPMENTOTHERCHARGEINFO("sqlGetShipmentOtherChargeInfo"),
   SQL_CREATE_SHIPMENTOTHERCHARGEINFO("sqlInsertShipmentOtherChargeInfo"),
   SQL_UPDATE_SHIPMENTOTHERCHARGEINFO("sqlUpdateShipmentOtherChargeInfo"),

   SQL_UPDATE_SHIPMENTMASTERVERFICATION("sqlupdateShipmentMasterVerification"),
   SQL_CREATE_SHIPMENTMASTERVERFICATION("sqlInsertShipmentMasterVerification"),
   SQL_GET_FLIGHTDETAILS("getAwbFlightdetails"),

   FETCH_EXCHANGE_RATE("fetchExchangeRate"),
   GETIRREGULARITYCOUNT("getIrregularityCount"),
   GETTRACINGCOUNT("getTracingCount"),
   SQL_CREATE_IRREGULARITY("addIrregularity"),
   SQL_CREATE_TRACING("sqlCreateTracing"),
   SQL_UPDATE_DOCUMENTRECEIVEDON("sqlUpdateDocumentreceivedon"),
   SQL_GET_CARRIERCODE_ON_PREFIX_AWB("getCarrierCodeonPrefixAwb"),
   SQL_GET_FWB_CONSIGNEE_AGENT_INFO("sqlGetFWBConsigneeAgentInfo"),
   SQL_GET_FWB_CONSIGNEE_AGENT_INFO_ON_SELECT("sqlGetFWBConsigneeAgentInfoOnSelect"),
   SQL_GET_FWB_CONSIGNEE_INFO("sqlGetFWBConsigneeInfo"),
   SQL_GET_EMAIL_INFO("sqlGetEmailInfo"),
   SQL_GET_DIRECT_CONSIGNEE_CUSTOMER_ID("sqlGetDefaultDirectConsigneeCustomerId"),
   SQL_GET_DIRECT_SHIPPER_CUSTOMER_ID("sqlGetDefaultDirectShipperCustomerId"),
   SQL_IS_VALID_APPOINTED_AGENT_ID("sqlIsValidAppointedAgentId"),
   SQL_UPDATE_SHIPMENTMASTER_FROM_FWB("sqlUpdateShipmentMasterFromFwb"),
   SQL_GET_IS_SHIPMENT_LOADED("isShipmentLoaded"),
   SQL_GET_IS_PO_GENERATED("isPoGenerated"),
   SQL_GET_SHIPMENT_TYPE("getShipmentType"),
   SQL_GET_STATUS_UPDATE_EVENT_PIECES("getTotalRCFStatusupdateEventPicesAwbDocument"),
   SQL_GET_BREAKDOWN_FOUND_PIECES("getBrekDownAndFoundPieces"),
   SQL_IS_VALID_CURRENCY("sqlIsValidCurrency");

   private final String queryId;

   AWBDocumentQueryId(String queryId) {
      this.queryId = queryId;
   }

   @Override
   public String toString() {
      return String.valueOf(this.queryId);
   }

}