package com.ngen.cosys.satssginterfaces.mss.enums;

public enum MapperId {

   SQL_GET_FLIGHT_INFO("sqlGetFlightInfo"),
   SQL_GET_TO_BE_LOADED("sqlGetToBeLoaded"),
   SQL_GET_LOADED_SHIPMENT("sqlGetLoadedShipment"),
   SQL_CHEKC_LOAD_SHIPMENT_PK("sqlCheckLoadShipmentPK"),  
   SQL_GET_ULD_SHIP_DATA("sqlGetUldShipmentData"),
   
   SQL_INSERT_LOAD_SHIPMENT("sqlInsertLoadShipment"),
   SQL_UPDATE_LOAD_SHIPMENT("sqlUpdateLoadShipment"),
   SQL_UPDATE_INVENTORY("sqlUpdateInventory"),
   SQL_DELETE_INVENTORY("sqlDeleteInventory"),
   SQL_GET_LOADED_DATA("sqlGetLoadedData"),
   SQL_INSERT_SHC("insertSHC"),
   
   SQL_INSERT_TAG_NO("insertTagNo"),
   SQL_GET_LOAD_SHIP_INFO_ID("sqlGetLoadedShipmentInfoId"),
   SQL_DELETE_INVENTORY_SHC("sqlDeleteInventorySHC"),
   SQL_DELETE_INVENTORY_HOUSE("sqlDeleteInventoryHouse"),
   SQL_GET_INVENTORY_PIECES_WEIGHT("sqlGetInventoryPiecesWeight"),
   SQL_DELETE_LOAD_SHC("sqlDeleteLoadedSHC"),
   
   SQL_DELETE_LOAD_HOUSE("sqlDeleteLoadedTagNo"),
   SQL_GET_SHC("sqlGetSHC"),
   SQL_GET_TAG_NO("sqlGetTagNo"),
   SQL_CHECK_ULD_TYPE_AND_CARRIER_MATCH("sqlCheckUldTypeAndCarrierMatch"),
   SQL_CHECK_BYPASS_ULD_TYPE_AND_CARRIER_MATCH("sqlCheckBypassUldTypeAndCarrierMatch"),
   
   SQL_GET_LOADED_WEIGHT("sqlGetLoadedWeight"),
   SQL_CHECK_BYPASS_WEIGHT_CHECK("sqlCheckBypassWeightCheck"),
   SQL_CHECK_COLADABLE_SHC("sqlCheckNonColadableSHC"),
   SQL_CHECK_CAO_SHC("sqlCheckCAOSHC"),
   SQL_CHECK_PERISHABLE_SHC("sqlCheckPerisableSHC"),
   SQL_CHECK_SHIPMENT_LOCK("sqlCheckShipmentLock"),
   
   SQL_CHECK_INVENTORY_LOCK("sqlCheckInventoryLock"),
   SQL_EACCEPTANCE_DOC_CHECK("sqleAcceptanceDocCheck"),
   SQL_EACCEPTANCE_WEIGHING_CHECK("sqleAcceptanceWeighing"),
   SQL_EMBARGO_FLIGHT_CHECK("sqlEmbargoFlightCheck"),
   SQL_GET_SHIPMENT_DETAILS("sqlGetShipmentDetails"),
   
   SQL_GET_SHIPMENT_SHC("sqlGetBuildUpShipmentSHC"),
   SQL_GET_SHIPMENT_HOUSE("sqlGetBuildUpShipmentHouse"),
   SQL_GET_WORKING_LIST_DATA("sqlGetWorkingListData"),
   SQL_GET_ULD_DATA("sqlGetULDData"),
   SQL_GET_ULD_MAX_WEIGHT("sqlGetULDMaxWeight"),
   
   SQL_CHECK_IS_ULD("sqlCheckIsULD"),
   SQL_CHECK_ULD_ASSIGN_TO_FLIGHT("sqlcheckULDAssignTOFlight"),
   SQL_BUILDUP_COMPLETED("sqlBuildUpCompleted"),
   SQL_GET_BUILDUP_COMPLETED("sqlGetBuildUpCompleted"),  
   SQL_CHECK_EVENT_PUBLISHED("sqlCheckIsEventPublished"),
   SQL_UPDATE_BUILDUPCOMPLETE_EVENT("sqlUpdateBuildUpEvent"), 
   
   SQL_CHECK_ECC_SHC("sqlCheckEccSHC"),
   SQL_CHECK_ULD_EXIST("sqlCheckULDExist"),
   SQL_GET_ULD_SEGMENT("sqlgetULDSegment"),
   SQL_INVENTORY_COMMSON_SERVICE("sqlGetInventoryForCommonService"),
   SQL_INVENTORY_BY_MAIL_BAG("sqlgetInventoryByMailBag"),
   
   SQL_GET_FLIGHT_DETAIL("sqlGetFlightDetail"),
   SQL_PRE_BOOKED_LIST("sqlGetPrebookedDetail"),
   SQL_GET_CONTAINER_LIST("sqlGetContainerDetails"),
   SQL_CHECK_MAIL_BAG("sqlCheckMailBag"),
   SQL_FOR_DLS_COMPLETE("sqlCheckDlsStatusFlight"),
   SQL_MAIL_BAG_DETAIL("sqlGetMailBagtDetail"),
   
   // Queries Id's for Mobile specific 
   SQL_GET_FLIGHT_INFO_FOR_MO("sqlGetFlightInfoForMo"),
   CHECK_LOADING("checkLoading"),
   SQL_FOR_UPLOAD_PHOTO("updatephotos"),
   GET_MAIL_BAG("getMailBagDetails"), 
   GET_ADD_AWB_DETAILS("getAddAwbDetails"),
   GET_ULD_MAX_WEIGHT_ACCEPTED("getULDMaxWeightAccepted"),
   IS_ULD_WEIGHT_CHECK_NOT_REQ("isUldWeightCheckNotRequired"),
   
   // for creating Inventory
   CREATE_INVENTORY("createInventory_Load"),
   GET_WEIGHING_ID("getWeighingId_Load"),
   IS_NEW_INVENTORY_EXIST("isNewInventoryExist"),
   UPDATE_NEW_INVENTORY("updateNewInventoryLocation"),
   IS_PART_CONFIRM_OR_FINALIZE("isPartConfirmOrFinalized"),
   
   GET_SHIPMENT_NUMBER("getShipmentNumber_Load"),
   GET_SHIPMENT_SERVICE_FLAG("getShipmentServiceFlag_Load"),
   CHECK_FLIGHT_RULE("checkFlightRules_Load"),
   GET_WEIGHT_WEIGHED("getWeightWeighed_Load"),
   GET_ASSIGN_ULD_TROLLEY_ID("getAssignUldTrolleyId_Load"),
   CREATE_ASSIGN_ULD_TO_FLIGHT("createAssingULDToFLigt_Load");
   
   private String id;

   private MapperId(String id) {
      this.id = id;
   }

   public String getId() {
      return id;
   }

}
