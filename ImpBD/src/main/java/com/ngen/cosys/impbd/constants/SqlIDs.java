package com.ngen.cosys.impbd.constants;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SqlIDs {
	AWB_EXISTS("isAWBExists"),

	SHIPMENT_INFO("getShpInfo"),
	INSERTSHIPMENTDIMENSION("insertShipmentDimension"),
	INSERTSHIPMENTOCI("insertShipmentOCI"),
	INSERTSHIPMENTONWARDMVMT("insertShipmentOnwardMvmt"),
	GETSHIPMENTONWARDMVMT("getShipmentOnwardMvmt"),
	SHP_VERFTN_DOC("createVerficationDocumnet"),
    GET_ANNOUNCEMENT_TABLE("getAnnoucementTable"),
    GET_ANNOUNCEMENT_FLIGHT("getAnnoucementFlight"),
    UPDATE_CARGO_PREANNOUNCEMENT("updateCargoPreAnnouncement"),
    DELETE_CARGO_PREANNOUNCEMENT("deleteCargoPreannouncement"),
    INSERT_CARGO_PREANNOUNCEMENT("insertCargoPreannouncement"),
    UPDATE_CARGO_PREANNOUNCEMENT_MANIFEST("updateCargoPreAnnouncement_by_manifest"),
    UPDATE_BREAK_BULK("updateBreakBulkStatus"),
    CONTENTTYPE_C("C"),
    CONTENTTYPE_M("M"),
    CONTENTTYPE_Q("Q"),
    CONTENTTYPE_X("X"),
    CONTENTTYPE_N("N"),
	UPDATESHIPMENTDENSITY("updateShipmentDetails"),
	UPDATESHIPMENTOCI("updateShipmentOCI"),
    UPDATESHIPMENTDIMENSION("updateShipmentDimension"),
    UPDATESHIPMENTONWARD("updateShipmentOnward"),
    DELETESHIPMENTOCI("deleteShipmentOCI"),
    DELETESHIPMENTDIMENSION("deleteDimension"),
    DELETESHIPMENTONWARD("deleteShipmentOnwardMvmt"),
    UPDATESHIPMENTINFO("updateShipmentInfo"),
    UPDATEULDINFO("updateULDInfo"),
    INSERTSHIPMENTINFO("insertShipmentInfo"),
    INSERTULDINFO("insertULDInfo"),
    INSERTSHCINFO("insertSHCInfo"),
    UPDATESHCINFO("updateSHCInfo"),
    DELETEULDINFO("deleteULDInfo"),
    DELETESHIPMENTINFO("deleteShipmentInfo"),
    DELETESHCINFO("deleteSHCInfo"),
    SQL_GET_ARRIVAL_MANIFEST_INFO("sqlGetArrivalManifestInfo"),
    INSERTOSIINFO("insertShipmentOSI"),
    UPDATEOSIINFO("updateOSIInfo"),
    DELETEOSIINFO("deleteShipmentOSI"),
    CHECKARRIVALMANIFESTFLIGHT("sqlCheckArrivalManifestFlight"),
    CHECKARRIVALMANIFESTSEGMENT("sqlCheckArrivalManifestSegment"),
    INSERTARRIVALMANIFESTFLIGHT("insertArrivalManifestFlight"),
    INSERTARRIVALMANIFESTSEGMENT("insertArrivalManifestSegment"),
    CHECKARRIVALMANIFESTULD("sqlCheckArrivalManifestUld"),
    CHECKARRIVALMANIFESTSHIPMENT("sqlCheckArrivalManifestShipment"),
    FETCHULDNUMBER("sqlFetchULDNumber"),
    FETCHSHIPMENTS("fetchDuplicateShipments"),
    FETCHFLIGHTSTATUS("fetchFlightStatus"),
    CHECKMANUALSHIPMENT("sqltoCheckmanualShipment"),
    CHECKOUTBOUNDFLIGHT("checkOutBoundFlight"),
    OCIDELETE("deleteOCI"),
    ONWARDDELETE("onwardDelete"),
    DIMENSIONDELETE("dimensionDelete"),
    UPDATEOFFLOADTOARRIVALMANIFEST("updateOffloadStatusToArrivalManifest"),
    GETDOCUMENTVERIFICATIONFLIGHT("getDocumentVerificationFlight"),
    ISFLIGHTEXISTSINREMARKS("isFlightExistsinRemarks"),
    UPDATEDOCUMENTCOMPLETEFIRSTTIMESTATUS("updateDocumentCompleteFirstTimeStatus"), 
    INSERTINTODOCUMENTCOMPLETEREMARKS("insertIntoDocumentCompleteRemarks"), 
    ISCHECKSFORFIRSTTIME("isChecksforFirstTime"), 
    UPDATEDOCUMENTCOMPLETENEXTTIMESTATUS("updateDocumentCompleteNextTimeStatus"),
    UPDATEDOCUMENTCOMPLEREOPEN("updateDocumentCompleReopen"),
    UPDATEBREAKDOWNCOMPLETEFIRSTTIMESTATUS("updateBreakdownCompleteFirstTimeStatus"),
    UPDATEBREAKDOWNCOMPLETENEXTTIMESTATUS("updateBreakdownCompleteNextTimeStatus"), 
    GETIRREGULARITY("getIrregularity"), 
    ISCARGOPREANNOUNCEMENTRECORDEXIST("isCargoPreAnnouncementRecordExist"),
    UPDATE_CARGO_PREANNOUNCEMENT_FINALIZE("cargoPrefinalize"),
    UPDATE_CARGO_PREANNOUNCEMENT_UNFINALIZE("cargoPreunfinalize"), 
    ISFINALIZE_OR_UNFINALIZE("isFinalizeORunFinalize"),
    PREANNOUNCEMENT_FINALIZE_INFO("preAnnoucementfinlazedInfo"),
    SQL_GET_DISPLAY_FFM_INFO("sqlGetDisplayFFMInfo"), 
    INSERT_CARGOPREANNOUNCEMENT_RAMCHECKIN("insertCargoPreannouncement_RamCheckIn"),
    UPDATE_CARGOPREANNOUNCEMENT_RAMCHECKIN("updateCargoPreAnnouncement_RamCheckIn"), 
    IS_EXIST_RAMPCHECKIN("isExistRampcheckin"), 
    INSERT_CARGOPREANNOUNCEMENT_SHC("insertCargopreannouncementShc"),
    INSERT_CARGOPREANNOUNCEMENT_RAMPCHECKIN_SHC("insertCargopreannouncementRampcheckinShc"),
    DELETE_CARGO_PREANNOUNCEMENT_SHC("deleteCargoPreannouncementShc"),
    DELETE_CARGO_PREANNOUNCEMENT_RAMP_SHC("deleteCargoPreannouncementRampShc"),
    CHECKONWARDMOVEMENT("checkOnwardDetails"),
    UPDATEONWARDFLIGHT("updateShipmentOnwardFlight"),
    SQL_GET_SERVICE_PROVIDER_INFO("getServiceProviderInfo"),
    SQL_CREATE_SERVICE_PROVIDER("createServiceProvider"),
    SQL_UPDATE_SERVICE_PROVIDER("updateServiceProvider"),
    SQL_DELETE_SERVICE_PROVIDER("deleteServiceProvider"),
    SQL_TERMINAL_ID("sqlTerminalID"),
    SQL_EDIT_SERVICE("sqlEditService"),
    SQL_CUSTOMER_ID("sqlCustomerId"),
    SQL_SERVICE_ID("sqlServiceId"), 
    SQL_CUSTOMER_ID_LIST("sqlCustomerIdList"),
    UPDATE_MAIL_PREANNOUNCEMENT_FINALIZE("mailPrefinalize"), 
    UPDATE_MAIL_PREANNOUNCEMENT_UNFINALIZE("mailPreunfinalize"),
    INSERT_PREANNOUNCEMENT("insertCargoPreannouncement"),
    UPDATE_PREANNOUNCEMENT_BULK("sqlUpdateQueryCheckBulkShipmentExistence"),
    UPDATE_PREANNOUNCEMENT("updateCargoPreAnnouncement"),
    CHECK_PREANNOUNCEMENT("isCargoPreAnnouncementRecordExist"),
    CHECK_BOOKING_EXISTS("checkBookingExists"),
    CHECK_SAME_BOOKING_EXISTS("checkSameBookingExists"),
    UPDATE_SERVICE_FLAG_BOOKING_EXISTS("updateServiceIndicator"),
    FETCH_SEGMENT_ID("fetchArrivalManifestSegmentID"),
    FETCH_OPERATIVE_SEGMENTS("FETCH_ARRIVALSEGMENT"),
    UPDATE_BULK_INDICATOR("updateBulkShipmentIndicator"), 
    IS_FLIGHT_EXIST("isFlightExist");
	
   
	 private final String type;
	   
	   private SqlIDs(String value) {
	      this.type = value;
	   }
	   
	   /* (non-Javadoc)
	    * @see java.lang.Enum#toString()
	    */
	   @Override
	   public String toString() {
	      return String.valueOf(this.type);
	   }
	   

	   /**
	    * Returns the ENUM for the specified String.
	    * 
	    * @param value
	    * @return
	    */
	 @JsonCreator 
	   public static SqlIDs fromValue(String value) {
	      for (SqlIDs eType : values()) {
	         if (eType.type.equalsIgnoreCase(value)) {
	            return eType;
	         }
	      }
	      throw new IllegalArgumentException(
	            "Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
	   }
	
}
