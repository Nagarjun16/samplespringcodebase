/**
 * 
 * NeutralAWBQueryIds.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          29 January, 2018 NIIT      -
 */
package com.ngen.cosys.shipment.enums;
/**
 * This Enum contains QueryIds used in  Neutral AWB maintenance.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public enum NeutralAWBQueryIds {
   FETCH_SID_LIST("fetchSIDList"),
   FETCH_AGENT_DETAILS("sqlGetAgentInfos"),
   SEARCH_SID_DETAILS("searchSIDDetails"),
   FETCH_AWB_FROM_STOCK_LIST("fetchAWBFromStockList"),
   SEARCH_NAWB_DETAILS("searchNeutralAWBDetails"),
   INSERT_NAWB_MASTER_DETAILS("insertNeutralAWBMasterDetails"),
   INSERT_NAWB_SHC_DETAILS("insertNeutralSHCDetails"),
   INSERT_NAWB_CUSTOMER_INFO_DETAILS("insertNeutralAWbCustomerInfoDetails"),
   INSERT_NAWB_CUSTOMER_ADDRESS_DETAILS("insertNeutralAWBCustomerAddressDetails"),
   INSERT_NAWB_CUSTOMER_CONTACT_INFO_DETAILS("insertNeutralAWBCustomerContactInfoDetails"),
   INSERT_NAWB_FLIGHT_BOOKING("insertNeutralAWBFlightBooking"),
   INSERT_NAWB_RATE_DESCRIPTION("insertNeutralAWBRateDescription"),
   INSERT_NAWB_ACCOUNTING_INFO("insertNeutralAWBAccountingInfo"),
   INSERT_NAWB_AGENT_INFO("insertNeutralAWBAgentInfo"),
   INSERT_NAWB_CHARGE_DECLARATION("insertNeutralAWBChargeDeclaration"),
   INSERT_NAWB_NOMINATED_HANDLING_PARTY("insertNeutralAWBNominatedHandlingParty"),
   INSERT_NAWB_OTHER_CHARGES("insertNeutralAWBOtherCharges"),
   INSERT_NAWB_OTHER_CUSTOMS_INFO("insertNeutralAWBOtherCustomsInfo"),
   INSERT_NAWB_OTHER_PARTICIPANT_INFO("insertNeutralAWBOtherParticipantInfo"),
   INSERT_NAWB_PREPAID_COLLECT_CHARGE_SUMMARY("insertNeutralAWBPrepaidCollectChargeSummary"),
   INSERT_NAWB_RATE_DESC_OTHER_INFO("insertNeutralAWBRateDescOtherInfo"),
   INSERT_NAWB_ROUTING("insertNeutralAWBRouting"),
   INSERT_NAWB_SHIPMENT_REF_INFO("insertNeutralAWBShpReferenceInformation"),
   INSERT_NAWB_SSROS_INFO("insertNeutralAWBSSROSIInfo"),
   INSERT_NAWB_SENDER_REF_INFO("insertNeutralAWBSenderReferenceInfo"),
   INSERT_NAWB_CC_CHARGES_INFO("insertNeutralAWBCCCharges"),
   ISSUE_AWB_NUMBER_IN_STOCK("issueAWBNumberInStock"),
   SEARCH_AWB_RESERVATION_DETAILS("fetchAWBReservationDetails")
   ;
   String queryId; 
   
   NeutralAWBQueryIds(String queryId){
      this.queryId = queryId;
   }
   
   public String getQueryId() {
      return this.queryId;
   }
}
