package com.ngen.cosys.shipment.enums;

public enum MaintainHouseQueryIds {

   SQL_GET_MAWB_HAWB_INFO("sqlGetMasterAWBInfo"),

   SQL_GET_HAWB_INFO("sqlGetHouseInfo"),

   SQL_GET_HOUSE_MAWB_ID("sqlGetShipmentFreightOutHouseAWBId"),

   SQL_GET_SUM_HOUSE_PCS_WT("sqlGetSumHousePieceWeight"),

   SQL_DELETE_HOUSE_MAWB("sqlDeleteHouseMAWB"), SQL_UPDATE_PCS_WT_HOUSE_MAWB(
         "sqlUpdateMAWBPcWt"), SQL_INSERT_HOUSE_MAWB(
               "sqlInsertAWB"), SQL_DECREASE_PCS_WT_HOUSE_MAWB("sqlDecreaseMAWBPcWt"), SQL_UPDATE_ORIGIN_DESTINATION_HOUSE_MAWB("sqlUpdateMAWBOriginDestination"),

   SQL_DELETE_HOUSE("sqlDeleteHouse"), SQL_UPDATE_HOUSE("sqlUpdateHouse"), SQL_INSERT_HOUSE("sqlInsertHouse"),

   SQL_DELETE_HOUSE_OTHERCHARGES("sqlDeleteAllHouseOtherCharges"), SQL_UPDATE_HOUSE_OTHERCHARGES(
         "sqlUpdateHouseChargeDeclarations"), SQL_INSERT_HOUSE_OTHERCHARGES("sqlInsertforChargedeclaration"),

   SQL_DELETE_ALL_HOUSE_SHC("sqlDeleteAllHouseShcs"), SQL_UPDATE_HOUSE_SHC(
         "sqlUpdateHouseSpecialHandlingCode"), SQL_INSERT_HOUSE_SHC("sqlInsertHouseSpecialHandlingCode"),

   SQL_DELETE_ALL_OTHERCUSTOMSINFORMATION("sqlDeleteAllHouseOtherCustomInfo"), SQL_DELETE_HOUSE_OTHERCUSTOMSINFORMATION(
         "sqlDeleteHouseOtherCharges"), SQL_UPDATE_HOUSE_OTHERCUSTOMSINFORMATION(
               "sqlUpdateHouseOtherCustomsInfo"), SQL_INSERT_HOUSE_OTHERCUSTOMSINFORMATION(
                     "sqlInsertHouseOtherCustomsInformation"),

   SQL_DELETE_ALL_HOUSE_DESCRIPTIONOFGOODS("sqlDeleteAllHouseDescriptions"), SQL_DELETE_HOUSE_DESCRIPTIONOFGOODS(
         "sqlDeleteHouseDescriptionOfGoods"), SQL_UPDATE_HOUSE_DESCRIPTIONOFGOODS(
               "sqlUpdateHouseDescriptionOfGoods"), SQL_INSERT_HOUSE_DESCRIPTIONOFGOODS(
                     "sqlInsertHouseDescriptionOfGoods"),

   SQL_DELETE_ALL_HOUSE_TARIFF("sqlDeleteAllHouseTariffs"), SQL_DELETE_HOUSE_TARIFF(
         "sqlDeleteHouseHarmonisedTariffSchedule"), SQL_UPDATE_HOUSE_TARIFF(
               "sqlUpdateHouseHarmonisedTariffSchedule"), SQL_INSERT_HOUSE_TARIFF(
                     "sqlInsertHouseHarmonisedTariffSchedule"),

   SQL_DELETE_HOUSE_CUSTOMER("sqlDeleteHouseCustomer"), SQL_UPDATE_HOUSE_CUSTOMER(
         "sqlUpdateHouseCustomer"), SQL_INSERT_HOUSE_CUSTOMER("sqlInsertHouseCustomer"),

   SQL_DELETE_HOUSE_CUSTOMER_ADDRESS("sqlDeleteHouseCustomerAddress"), SQL_UPDATE_HOUSE_CUSTOMER_ADDRESS(
         "sqlUpdateHouseCustomerAddress"), SQL_INSERT_HOUSE_CUSTOMER_ADDRESS("sqlInsertHouseCustomerAddress"),

   SQL_DELETE_ALL_HOUSE_CUSTOMER_ADDRESS_CONTACT(
         "sqlDeleteAllHouseCustomerAddressContacts"), SQL_DELETE_HOUSE_CUSTOMER_ADDRESS_CONTACT(
               "sqlDeleteHouseCustomerContacts"), SQL_UPDATE_HOUSE_CUSTOMER_ADDRESS_CONTACT(
                     "sqlUpdateHouseCustomerContacts"), SQL_INSERT_HOUSE_CUSTOMER_ADDRESS_CONTACT(
                           "sqlInsertHouseCustomerContacts"),

   SQL_CHECK_HAWB_EXISTS("sqlCheckHAWBExists"),
   
   SQL_CHECK_AIRPORT_BELONGS_TO_CHINA("sqlCheckAirportBelongsToChina"),
   
   SQL_GETSHIPPER_AND_CONSIGNEEINFO_FOR_FIRSTHOUSE("sqlGetShipperAndConsigneeInfoOfFirstHouse"),
   
   SQL_GET_SHIPMENT_INFO_FOR_CHARGES("sqlGetFHLShipmentInfoForCharges"),
   SQL_GET_HAWB_MASTERS("sqlGetHawbMasters"),
   SQL_GET_VALID_SHC("sqlSetHawbMasterValidShc"),
   SQL_INSERT_NEW_HOUSE_SHC("sqlInsertHouseSpecialHandlingCodeNew"),
   SQL_UPDATE_NEW_HOUSE_SHC("sqlUpdateHouseSpecialHandlingCodeNew"),
   SQL_DELETE_NEW_HOUSE_SHC("sqlDeleteHouseSpecialHandlingCodeNew"),
   SQL_INSERT_CONSIGNEE_INFO("insertConsigneeInfo"),
   SQL_INSERT_CONSIGNEE_ADDRESS_INFO("insertShipperAddressInfo"),
   SQL_UPDATE_CONSIGNEE_ADDRESS_INFO_MASTER("updateShipperAddressInfoMaster"),
   SQL_UPDATE_CONSIGNEE_INFO_MASTER("updateShipperInfoMaster"),
   SQL_DELETE_HAWB_MASTERS_CONTACT("sqlDeleteHawbMastersContact"),
   SQL_DELETE_HAWB_MASTERS_ADDRESS_CONSIGNEE("sqlDeleteHawbMastersConsigneeAddress"),
   SQL_DELETE_HAWB_MASTERS_CONSIGNEE("sqlDeleteHawbMastersConsignee"),
   SQL_INSERT_SHIPPER_INFO("insertShipperInfo"),
   SQL_INSERT_SHIPPER_ADDRESS_INFO("insertShipperAddressInfo"),
   SQL_UPDATE_SHIPPER_INFO_MASTER("updateConsigneeInfoMaster"),
   SQL_DELETE_HAWB_MASTERS_ADDRESS_SHIPPER("sqlDeleteHawbMastersShipperAddress"),
   SQL_DELETE_HAWB_MASTERS_SHIPPER("sqlDeleteHawbMastersShipper"),
   SQL_GET_HAWB_PIECES("sqlGetHawbPieces"),
   SQL_GET_HAWB_WEIGHT("sqlGetHawbWeight"),
   SQL_GET_HAWB_CHARGEABLE_WEIGHT("sqlGetHawbChargeableWeight"),
   SQL_SET_HAWB_MASTERS("sqlSetHawbMasters"),

  	//HAWB LIST

     INSERT_HOUSE_DIMENSION("sqlInsertHouseDimension"),
     SQL_GET_HAWB_LIST_INFO("getHawbListInfo"),
     SQL_UPDATE_HOUSE_INFO("getUpdateHouseInfo"),
    SQL_DELETE_HAWB_MASTERS_ADDRESS_CONSIGNEE_DATA("sqlDeleteHawbAddressConsigneeData"),
    SQL_DELETE_HAWB_MASTERS_CONSIGNEE_DATA("sqlDeleteHawbShipperConsigneeDataShipper"),
    SQL_DELETE_HAWB_MASTERS_CONTACT_DATA_SHIPPER("sqlDeleteHawbMastersContactDataShipper"),
    SQL_DELETE_HAWB_MASTERS_ADDRESS_SHIPPER_DATA("sqlDeleteHawbAddressShipperData"),
    SQL_DELETE_HAWB_MASTERS_SHIPPER_DATA("sqlDeleteHawbShipperConsigneeDataConsignee"),
    SQL_DELETE_HAWB_MASTERS_CONTACT_DATA_CONSIGNEE("sqlDeleteHawbMastersContactDataConsignee"),
    SQL_CHECK_HAWB_DUPLICATE_RECORD("sqlCheckDuplicateRecord"),
    SQL_GET_AGENT_INFO("getConsigneeShipperDetails"),
    SQL_INSERT_HOUSE_INFO("getInsertHouseInfo"),
    SQL_UPDATE_SHIPPERE_INFO("updateShipperInfo"),
    SQL_UPDATE_SHIPPER_ADDRESS_INFO("updateShipperAddressInfo"),
    SQL_UPDATE_CONSIGNEE_INFO("updateConsigneeInfo"),
    SQL_UPDATE_CONSIGNEE_ADDRESS_INFO("updateConsigneeAddressInfo"),
    SQL_VERIFY_FREIGHT_OUT_ALREADY_EXISTS("sqlVerifyFreightoutExists"),
    SQL_CHECK_ISSUE_PODO_ISSUED("sqlCheckIssuePO/DOissued");

   String queryId;
   

   public class CRUDType {
      public static final String CREATE = "C";
      public static final String UPDATE = "U";
      public static final String DELETE = "D";

      private CRUDType() {
         // Nothing to add
      }
   }

   MaintainHouseQueryIds(String queryId) {
      this.queryId = queryId;
   }

   public String getQueryId() {
      return this.queryId;
   }

   public static void main(String[] args) {
   }

}
