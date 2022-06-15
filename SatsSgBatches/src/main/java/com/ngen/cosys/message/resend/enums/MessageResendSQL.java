/**
 * {@link MessageResendSQL}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.enums;

/**
 * This Enum used for Message Resend SQL mapper values
 * 
 * @author NIIT Technologies Ltd
 *
 */
public enum MessageResendSQL {

   SQL_SELECT_MESSAGE_RESEND_RETRY_LIMIT("sqlSelectMessageResendRetryLimit"), //
   SQL_SELECT_MESSAGE_OUTGOING_ENDPOINT_CONFIG("sqlSelectMessageOutgoingEndPointConfig"), //
   SQL_SELECT_MESSAGE_INCOMING_ENDPOINT_CONFIG("sqlSelectMessageIncomingEndPointConfig"), //
   SQL_SELECT_OUTGOING_MESSAGE_ERROR_LOG_DETAILS("sqlSelectOutgoingMessageErrorLogDetails"), //
   SQL_UPDATE_OUTGOING_MESSAGE_PROCESSING_STATE("sqlUpdateOutgoingMessageProcessingState"), //
   SQL_UPDATE_OUTGOING_RESENT_MESSAGE_LOG("sqlUpdateOutgoingResentMessageLog"), //
   SQL_UPDATE_OUTGOING_FAILED_ERROR_MESSAGE_LOG("sqlUpdateOutgoingFailedErrorMessageLog"), //
   SQL_UPDATE_OUTGOING_RESEND_MESSAGE_LOG_STATS("sqlUpdateOutgoingResendMessageLogStats"), //
   SQL_INSERT_OUTGOING_RESEND_MESSAGE_LOG_STATS("sqlInsertOutgoingResendMessageLogStats"), //
   SQL_SELECT_INCOMING_ESB_PROCESSING_MESSAGE_LOG("sqlSelectIncomingESBProcessingMessageLog"), //
   SQL_SELECT_INCOMING_ESB_INITIATED_MESSAGE_LOG("sqlSelectIncomingESBInitiatedMessageLog"), //
   SQL_SELECT_INCOMING_ESB_MESSAGE_ERROR_LOG_DETAILS("sqlSelectIncomingESBMessageErrorLogDetails"), //
   SQL_UPDATE_INCOMING_ESB_RESENT_SEQUENCE_MESSAGE_LOG("sqlUpdateIncomingESBResentSequenceMessageLog"), //
   SQL_VERIFY_SHIPMENT_DUPLICATE_REFERENCE("sqlVerifyShipmentDuplicateReference"), //
   SQL_UPDATE_INCOMING_ESB_MESSAGE_DUPLICATE_REFERENCE("sqlUpdateIncomingESBMessageDuplicateReference"), //
   SQL_UPDATE_INCOMING_ESB_MESSAGE_REPROCESSING_STATE("sqlUpdateIncomingESBMessageReProcessingState"), //
   SQL_UPDATE_INCOMING_ESB_MESSAGE_PROCESSING_STATE("sqlUpdateIncomingESBMessageProcessingState"), //
   SQL_INSERT_INCOMING_ESB_ERROR_MESSAGE_LOG("sqlInsertIncomingESBErrorMessageLog"), //
   SQL_UPDATE_INCOMING_ESB_RESENT_MESSAGE_LOG("sqlUpdateIncomingESBResentMessageLog"), //
   SQL_UPDATE_INCOMING_ESB_FAILED_ERROR_MESSAGE_LOG("sqlUpdateIncomingESBFailedErrorMessageLog"), //
   SQL_UPDATE_INCOMING_ESB_RESEND_MESSAGE_LOG_STATS("sqlUpdateIncomingESBResendMessageLogStats"), //
   SQL_INSERT_INCOMING_ESB_RESEND_MESSAGE_LOG_STATS("sqlInsertIncomingESBResendMessageLogStats"), //
   SQL_SELECT_INCOMING_FFM_MESSAGE_LOGS("sqlSelectIncomingFFMLogs"), //
   SQL_INSERT_INCOMING_FFM_MESSAGE_LOG("sqlInsertIncomingFFMLog"), //
   SQL_UPDATE_INCOMING_FFM_MESSAGE_LOG("sqlUpdateIncomingFFMLog"), //
   SQL_INSERT_INCOMING_FFM_MESSAGE_LOG_DETAIL("sqlInsertIncomingFFMLogDetail"), //
   SQL_UPDATE_INCOMING_FFM_MESSAGE_LOG_DETAIL("sqlUpdateIncomingFFMLogDetail"), //
   SQL_SELECT_FAILED_EVENTS("getFailedEvents"), //,
   SQL_SELECT_FAILED_EVENTS_BY_ID("getFailedEventsById"),
   SQL_INSERT_INCOMING_MESSAGE_LOG("sqlInsertIncomingMessagesLog"), SQL_GET_LEADING_ZEROS_FOR_MESSAGE("sqlGetLeadingZeros"), SQL_GET_FLIGHT_STA("sqlSelectDATESTA"),
   SQL_DELETE_INCOMING_FFM_MESSAGE_LOG_DETAIL("sqldeleteIncomingFFMLogDetail"), SQL_DELETE_INCOMING_FFM_MESSAGE_LOG("sqldeleteIncomingFFMLog"),
   SQL_SELECT_INCOMING_DISCARD_MESSAGE_LOG("sqlSelectIncomingDiscardMessageLog"),
	SQL_UPDATE_INCOMING_MESSAGE_LOG("sqlUpdateIncomingMessageLog"),
	SQL_INSERT_OUTGOING_MESSAGE_LOG("insertOutgoingMessageLog"),
	SQL_SELECT_AISATSOUTGOING_MESSAGE_ERROR_LOG_DETAILS("sqlSelectAisatsOutgoingMessageErrorLogDetails"),
	SQL_SELECT_MESSAGE_ENDPOINT_CONFIG_AISATSOUTGOING("sqlSelectMessageOutgoingEndPointConfigAisatsOutgoing");
	
   private String queryId;
   
   /**
    * @param queryId
    */
   private MessageResendSQL(String queryId) {
      this.queryId = queryId;
   }
   
   /**
    * @return
    */
   public String getQueryId() {
      return this.queryId;
   }
   
}
