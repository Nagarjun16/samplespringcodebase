package com.ngen.cosys.shipment.enums;

public enum MaintainFWBQueryIds {
	
	INSERT_AWB_DETAILS("insertAWBDetails"),
	INSERT_AWB_OTHER_CHARGES("insertAWBOtherCharges"),
	INSERT_AWB_ROUTING("insertAWBRouting"),
	INSERT_AWB_SHC_DETAILS ("insertAWBSHCDetails"),
	DELETE_AWB_SHC_DETAILS ("deleteForUpdateAWBSHCDetails"),
	INSERT_AWB_FLIGHTBOOKING_DETAILS ("insertAWBFlightBookingDetails"),
	INSERT_AWB_CUSTINFO_DETAILS ("insertAWBShipperInfoDetails"),
	INSERT_AWB_CUST_ADD_INFO_DETAILS ("insertAWBShipperAddInfoDetails"),
	INSERT_AWB_CUST_CONTACT_INFO_DETAILS ("insertAWBShipperContactInfoDetails"),
	INSERT_AWB_Agent_INFO_DETAILS ("insertAWBAgentInfoDetails"),
	INSERT_AWB_SSROSI_DETAILS ("insertAWBSSROSIDetails"),
	INSERT_AWB_ALSONOTIFY_DETAILS ("insertAWBAlsoNotifyDetails"),
	INSERT_AWB_RATE_DESCRIPTION ("insertAWBRateDescription"),
	INSERT_AWB_RATE_DESC_OTHER ("insertAWBRateDescOtherInfo"),
	INSERT_AWB_ACCOUNTINGINFO ("insertAWBAccountingInfo"),
	INSERT_AWB_CHARGE_DECLARATION_DETAILS ("insertAWBChargeDeclaration"),
	INSERT_AWB_PREPAID_COLLECT_CHARGE_SUMMARY ("insertAWBPrepaidCollectChargeSummary"),
	UPDATE_AWB_DETAILS ("updateAWBDetails"),
	UPDATE_AWB_RATE_DESCRIPTION ("updateAWBRateDescription"),
	DELETE_AWB_RATE_DESCRIPTION("deleteForUpdateAWBRateDescription"),
	UPDATE_AWB_CUSTINFO_DETAILS ("updateAWBShipperInfoDetails"),
	UPDATE_AWB_CUST_ADD_INFO_DETAILS ("updateAWBShipperAddInfoDetails"),
	UPDATE_AWB_CUST_CONTACT_INFO_DETAILS ("updateAWBShipperContactInfoDetails"),
	INSERT_AWB_NOMINATE_HANDLING_PARTY("insertAWBNominatedHandlingParty"),
	UPDATE_AWB_PREPAID_COLLECT_CHARGE_SUMMARY ("updateAWBPrepaidCollectChargeSummary"),
	UPDATE_AWB_RATE_DESC_OTHER ("updateAWBRateDescOtherInfo"),
	DELETE_AWB_RATE_DESC_OTHER ("deleteForUpdateAWBRateDescOtherInfo"),
	UPDATE_AWB_SSROSI_DETAILS ("updateAWBSSROSIDetails"),
	DELETE_AWB_SSROSI_DETAILS ("deleteForUpdateAWBSSROSIDetails"),
	UPDATE_AWB_NOMINATE_HANDLING_PARTY("updateAWBNominatedHandlingParty"),
	UPDATE_AWB_OTHER_CHARGES("updateAWBOtherCharges"),
	DELETE_AWB_OTHER_CHARGES("deleteForUpdateAWBOtherCharges"),
	
	UPDATE_AWB_OTHER_PARTICIPANT_INFO("updateAWBOtherParticipantInfo"),
	INSERT_AWB_OTHER_PARTICIPANT_INFO("insertAWBOtherParticipantInfo"),
	DELETE_AWB_OTHER_PARTICIPANT_INFO("deleteForUpdateAWBOtherParticipantInfo"),
	
	UPDATE_AWB_OTHER_SHP_REF_INFO("updateAWBShpReferInfo"),
	INSERT_AWB_OTHER_SHP_REF_INFO("insertAWBShpReferInfo"),
	INSERT_AWB_OTHER_CUSTINFO("insertAWBOtherCustomsInfo"),
	UPDATE_AWB_OTHER_CUSTINFO("updateAWBOtherCustomsInfo"),
	DELETE_AWB_OTHER_CUSTINFO("deleteForUpdateAWBOtherCustomsInfo"),
	UPDATE_AWB_CHARGE_DECLARATION_DETAILS ("updateAWBChargeDeclaration"),
	
   DELETE_AWB_CUST_CONTACT_INFO_DETAILS ("deleteForUpdateAWBShipperContactInfoDetails"),
   UPDATE_AWB_ACCOUNTINGINFO ("updateAWBAccountingInfo"),
   DELETE_AWB_ACCOUNTINGINFO ("deleteForUpdateAWBAccountingInfo"),
   UPDATE_AWB_Agent_INFO_DETAILS ("updateAWBAgentInfoDetails"),
   UPDATE_AWB_ROUTING("updateAWBRouting"),
   DELETE_AWB_ROUTING("deleteAwbRouting"),
   
   DELETE_AWB_CHARGE_DECLARATION_DETAILS("deleteForUpdateAWBChargeDeclaration"),
   
   
   UPDATE_AWB_FLIGHTBOOKING_DETAILS ("updateAWBFlightBookingDetails");
   
   
	    
	private String queryId;
	
	MaintainFWBQueryIds(String queryId){
		this.queryId = queryId;
	}

	public String getQueryId() {
		return this.queryId;
	}
	
}