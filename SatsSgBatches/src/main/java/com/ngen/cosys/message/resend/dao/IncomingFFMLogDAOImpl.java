/**
 * {@link IncomingFFMLogDAOImpl}
 * 
 * @copyright SATS Singapore 2020-21
 * @author Coforge PTE Ltd
 */
package com.ngen.cosys.message.resend.dao;


import static com.ngen.cosys.message.resend.common.MessageResendConstants.ADD;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.UPD;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.SPLIT;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.FFM;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.LAST;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.SCHEDULER;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.HOLD;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.INITIATED;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.REJECTED;

import com.ngen.cosys.message.resend.model.MessageLeadingZerosDefinitionModel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

import com.ngen.cosys.message.resend.enums.MessageResendSQL;
import com.ngen.cosys.message.resend.model.CargoMessageInLog;
import com.ngen.cosys.message.resend.model.IncomingFFMLog;
import com.ngen.cosys.message.resend.model.IncomingFFMLogDetail;

import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;

/**
 * Incoming FFM Log DAO Implementation
 * 
 * @author Coforge PTE Ltd
 */
@Repository
public class IncomingFFMLogDAOImpl extends BaseDAO implements IncomingFFMLogDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(IncomingFFMLogDAO.class);
   
   @Autowired
   @Qualifier(BeanFactoryConstants.SESSION_TEMPLATE)
   private SqlSession sqlSession;
   
   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSessionROI;
   
   /**
    * @see com.ngen.cosys.message.resend.dao.IncomingFFMLogDAO#getFFMHOLDMessages()
    */
   @Override
   public List<IncomingFFMLog> getFFMHOLDMessages() throws CustomException {
      LOGGER.info("Incoming FFM Log DAO :: HOLD Message log details - {}");
      return sqlSessionROI.selectList(MessageResendSQL.SQL_SELECT_INCOMING_FFM_MESSAGE_LOGS.getQueryId());
   }

   /**
    * @see com.ngen.cosys.message.resend.dao.IncomingFFMLogDAO#updateFFMLogDetails(java.util.List)
    */
   @Override
	public void updateFFMLogDetails(List<IncomingFFMLog> incomingFFMLogs) throws CustomException {
		LOGGER.info("Incoming FFM Log DAO :: Update Message log details - {}");
		for (IncomingFFMLog incomingFFMLog : incomingFFMLogs) {
			// FFM Log Ids			
		if (!CollectionUtils.isEmpty(incomingFFMLog.getIncomingFFMLogDetails())) {
			List<IncomingFFMLogDetail> hold = incomingFFMLog.getIncomingFFMLogDetails().stream().filter(incomingFFMLogDetails->incomingFFMLogDetails.getMessageStatus().equals(HOLD)).sorted(Comparator.comparing(IncomingFFMLogDetail::getMessageESBSequenceNo)).collect(Collectors.toList());
			boolean holdNew = false;
			if(!CollectionUtils.isEmpty(hold))
			{
				IncomingFFMLogDetail ffmLogDetail= hold.stream().findFirst().get();
				Integer partGroup = ffmLogDetail.getPartGroup();
				String message = null;
				if (!CollectionUtils.isEmpty(incomingFFMLog.getPartGroupMessage()) && incomingFFMLog
						.getPartGroupMessage().containsKey(partGroup)) {
					message = incomingFFMLog.getPartGroupMessage().get(partGroup);
				}
				IncomingFFMLog copyIncomingFFMLog = copyIncomingFFMLog(incomingFFMLog.getAirportCode(), message,
						incomingFFMLog.getMessageProcessedTime(), ffmLogDetail);
				copyIncomingFFMLog.setMessage(message);
				copyIncomingFFMLog.setMessageStatus(HOLD);
				Optional<IncomingFFMLogDetail> optional = hold.stream().filter(incomingFFMLogDetails->incomingFFMLogDetails.getIncomingFFMLogId()!=null).findFirst();
				if(optional.isPresent())
				{
					BigInteger incomingFFMLogId= optional.get().getIncomingFFMLogId();					
					copyIncomingFFMLog.setIncomingFFMLogId(incomingFFMLogId);					
					updateData(MessageResendSQL.SQL_UPDATE_INCOMING_FFM_MESSAGE_LOG.getQueryId(), copyIncomingFFMLog,
							sqlSession);
					updateIncomingFFMDetails(hold,incomingFFMLog);				
				}
				else
				{
					holdNew = true;
					insertData(MessageResendSQL.SQL_INSERT_INCOMING_FFM_MESSAGE_LOG.getQueryId(),
							copyIncomingFFMLog, sqlSession);					
					updateIncomingFFMDetails(hold,copyIncomingFFMLog);						
				}
			}
			Map<Integer, BigInteger> incomingFFMLogIds = new HashMap<>();
			 List<IncomingFFMLogDetail> incomingFFMLogDetails = null;
			if(!CollectionUtils.isEmpty(incomingFFMLog.getIncomingFFMLogDetails()))
			{
				incomingFFMLogDetails=incomingFFMLog.getIncomingFFMLogDetails().stream().filter(incomingFFMLogDetail->!incomingFFMLogDetail.getMessageStatus().equals(HOLD) ).sorted(Comparator.comparing(IncomingFFMLogDetail::getMessageESBSequenceNo)).collect(Collectors.toList());
			}
			for (IncomingFFMLogDetail incomingFFMLogDetail : incomingFFMLogDetails) {
				if (Objects.equals(ADD, incomingFFMLogDetail.getFlagCRUD())) {
					if (incomingFFMLogIds.containsKey(incomingFFMLogDetail.getPartGroup())) {
						incomingFFMLogDetail
								.setIncomingFFMLogId(incomingFFMLogIds.get(incomingFFMLogDetail.getPartGroup()));
					} else {
						insertIntoIncomingFFM(incomingFFMLog,incomingFFMLogDetail,incomingFFMLogIds);
						
					}
					insertData(MessageResendSQL.SQL_INSERT_INCOMING_FFM_MESSAGE_LOG_DETAIL.getQueryId(),
							incomingFFMLogDetail, sqlSession);
					// insert with SPLIT Status
					CargoMessageInLog cargoMessageInLog = createRequestForIncomingMessageLog(incomingFFMLogDetail,
							incomingFFMLog);
					// Store the incoming message
					insertData(MessageResendSQL.SQL_INSERT_INCOMING_MESSAGE_LOG.getQueryId(), cargoMessageInLog,
							sqlSession);
				} else  {
					if(CollectionUtils.isEmpty(hold))
					{
						incomingFFMLog.setMessageStatus(incomingFFMLogDetail.getMessageStatus());
						updateData(MessageResendSQL.SQL_UPDATE_INCOMING_FFM_MESSAGE_LOG.getQueryId(), incomingFFMLog,
								sqlSession);
						updateData(MessageResendSQL.SQL_UPDATE_INCOMING_FFM_MESSAGE_LOG_DETAIL.getQueryId(),
								incomingFFMLogDetail, sqlSession);
					}
					else
					{
						if(holdNew)
						{
							//delete old HOLD; now new status
							deleteData(MessageResendSQL.SQL_DELETE_INCOMING_FFM_MESSAGE_LOG.getQueryId(),
									incomingFFMLogDetail, sqlSession);
						}
						//delete old HOLD; now new status
						deleteData(MessageResendSQL.SQL_DELETE_INCOMING_FFM_MESSAGE_LOG_DETAIL.getQueryId(),
								incomingFFMLogDetail, sqlSession);
						//As hold exists so cannot update it have to create new record in table
						insertIntoIncomingFFM(incomingFFMLog,incomingFFMLogDetail,incomingFFMLogIds);
						insertData(MessageResendSQL.SQL_INSERT_INCOMING_FFM_MESSAGE_LOG_DETAIL.getQueryId(),
								incomingFFMLogDetail, sqlSession);
						
					}
					
					
				}
				
			}
		}
	}
	}
   
	private void insertIntoIncomingFFM(IncomingFFMLog incomingFFMLog, IncomingFFMLogDetail incomingFFMLogDetail, Map<Integer, BigInteger> incomingFFMLogIds) throws CustomException {
		// New FFM Log
		String message = null;
		if (!CollectionUtils.isEmpty(incomingFFMLog.getPartGroupMessage()) && incomingFFMLog
				.getPartGroupMessage().containsKey(incomingFFMLogDetail.getPartGroup())) {
			message = incomingFFMLog.getPartGroupMessage().get(incomingFFMLogDetail.getPartGroup());
		}
		IncomingFFMLog copyIncomingFFMLog = copyIncomingFFMLog(incomingFFMLog.getAirportCode(), message,
				incomingFFMLog.getMessageProcessedTime(), incomingFFMLogDetail);
		insertData(MessageResendSQL.SQL_INSERT_INCOMING_FFM_MESSAGE_LOG.getQueryId(),
				copyIncomingFFMLog, sqlSession);
		
		// Detail Log
		incomingFFMLogDetail.setIncomingFFMLogId(copyIncomingFFMLog.getIncomingFFMLogId());
	
}

	private void updateIncomingFFMDetails(List<IncomingFFMLogDetail> hold, IncomingFFMLog incomingFFMLog) throws CustomException {
		for (IncomingFFMLogDetail incomingFFMLogDetail : hold) {
			incomingFFMLogDetail.setIncomingFFMLogId(incomingFFMLog.getIncomingFFMLogId());
			if (Objects.equals(ADD, incomingFFMLogDetail.getFlagCRUD())) {			
				
				insertData(MessageResendSQL.SQL_INSERT_INCOMING_FFM_MESSAGE_LOG_DETAIL.getQueryId(),
						incomingFFMLogDetail, sqlSession);	
				// insert with SPLIT Status
				CargoMessageInLog cargoMessageInLog = createRequestForIncomingMessageLog(incomingFFMLogDetail,
						incomingFFMLog);
				// Store the incoming message
				insertData(MessageResendSQL.SQL_INSERT_INCOMING_MESSAGE_LOG.getQueryId(), cargoMessageInLog,
						sqlSession);
			}
			else
			{
				updateData(MessageResendSQL.SQL_UPDATE_INCOMING_FFM_MESSAGE_LOG_DETAIL.getQueryId(),
						incomingFFMLogDetail, sqlSession);
			}
		}
	
}



	private CargoMessageInLog createRequestForIncomingMessageLog(IncomingFFMLogDetail incomingFFMLogDetail,
			IncomingFFMLog incomingFFMLog) throws CustomException {
		CargoMessageInLog cargoMessageInLog = new CargoMessageInLog();
		cargoMessageInLog.setChannel(SCHEDULER);
		cargoMessageInLog.setInterfacingSystem(incomingFFMLogDetail.getChannel());
		cargoMessageInLog.setSenderOriginAddress(incomingFFMLogDetail.getSenderAddress());
		cargoMessageInLog.setMsgType(FFM);
		// get Carrier & Number
		if (!StringUtils.isEmpty(incomingFFMLogDetail.getFlightKey())) {
			String carrierCode = incomingFFMLogDetail.getFlightKey().substring(0, 2);
			String flightNumber = incomingFFMLogDetail.getFlightKey().substring(2, incomingFFMLogDetail.getFlightKey().length());
			flightNumber=  zerosToAppend(carrierCode,flightNumber);
			
				if (incomingFFMLogDetail.getFlightKey().length() == 6) {
					// then carrier is of two char
					carrierCode = incomingFFMLogDetail.getFlightKey().substring(0, 2);
					flightNumber = incomingFFMLogDetail.getFlightKey().substring(2, 6);
				} else if (incomingFFMLogDetail.getFlightKey().length() == 7) {
					// then check last chrac is alphabet
					if (StringUtils.isAlpha(incomingFFMLogDetail.getFlightKey()
							.substring(incomingFFMLogDetail.getFlightKey().length() - 1))) {
						// then carrier is of two char
						carrierCode = incomingFFMLogDetail.getFlightKey().substring(0, 2);
						flightNumber = incomingFFMLogDetail.getFlightKey().substring(2, 7);
					} else {
						// carrier is of three char
						carrierCode = incomingFFMLogDetail.getFlightKey().substring(0, 3);
						flightNumber = incomingFFMLogDetail.getFlightKey().substring(3, 7);
					}
				} else if (incomingFFMLogDetail.getFlightKey().length() == 8) {
					// carrier is of three char
					carrierCode = incomingFFMLogDetail.getFlightKey().substring(0, 3);
					flightNumber = incomingFFMLogDetail.getFlightKey().substring(3, 8);
				}	
			
			
			cargoMessageInLog.setCarrCode(carrierCode);
			cargoMessageInLog.setFltNo(flightNumber);
			cargoMessageInLog.setFltDate(getFlightSTA(cargoMessageInLog,incomingFFMLogDetail.getDateSTA()));
		}
		
		if(cargoMessageInLog.getFltDate()==null)
		{
			cargoMessageInLog.setFltDate(incomingFFMLogDetail.getDateSTA());
		}
			
		cargoMessageInLog.setRequest(incomingFFMLogDetail.getMessage());
		cargoMessageInLog.setMessageVersion(BigDecimal.valueOf(incomingFFMLogDetail.getVersionNo()));
		cargoMessageInLog.setSuccessMsgsSeqNo(incomingFFMLogDetail.getSequenceNo().toString());
		if (incomingFFMLogDetail.isLastMessage())
			cargoMessageInLog.setMessageContentEndIndicator(LAST);
		cargoMessageInLog.setStatus(SPLIT);
		cargoMessageInLog.setEsbMessageLogId(incomingFFMLogDetail.getIncomingESBMessageLogId());
		cargoMessageInLog.setAirportCode(incomingFFMLog.getAirportCode());
		cargoMessageInLog.setCreatedUserId(incomingFFMLogDetail.getChannel());
		cargoMessageInLog.setCreatedDate(Objects.isNull(incomingFFMLogDetail.getCreatedDateTime())?LocalDateTime.now():incomingFFMLogDetail.getCreatedDateTime());
		return cargoMessageInLog;

	}

private LocalDateTime getFlightSTA(CargoMessageInLog cargoMessageInLog, LocalDateTime originDate) throws CustomException {
	IncomingFFMLogDetail ffm = new IncomingFFMLogDetail();
	ffm.setFlightKey(cargoMessageInLog.getCarrCode()+cargoMessageInLog.getFltNo());
	ffm.setDateSTA(originDate);
	  return this.fetchObject(MessageResendSQL.SQL_GET_FLIGHT_STA.getQueryId(), ffm,
   		   sqlSession);
	}

private String zerosToAppend(String carrierCode, String flightNumber) throws CustomException {
	MessageLeadingZerosDefinitionModel zeroDefinitionModel = new MessageLeadingZerosDefinitionModel();
    zeroDefinitionModel.setCarrierCode(carrierCode);
    zeroDefinitionModel.setMessageType("FFM");
    zeroDefinitionModel.setFlowType("IN");
    zeroDefinitionModel.setReferenceBy("FLIGHT");
  /* 
    zeroDefinitionModel.setFlightNumber(incomingFFMLogDetail.getFlightKey());
    zeroDefinitionModel.setOffPoint(incomingFFMLog.getAirportCode());*/
    if(!StringUtils.isEmpty(flightNumber))
    {
    	if (flightNumber.length() < 4) {
    	     
    			zeroDefinitionModel =    getDefinitionForLeadingZeros(zeroDefinitionModel);
    	
    		}

    	       // Remove zeros if configured
    	       if (zeroDefinitionModel != null && zeroDefinitionModel.getZerosToRemove().intValue() > 0) {
    	    	   flightNumber = removeZeroFromString(flightNumber,
    	                zeroDefinitionModel.getZerosToRemove().intValue());
    	       } else if (zeroDefinitionModel != null && zeroDefinitionModel.getZerosToAppend().intValue() > 0) {
    	    	   flightNumber =appendZeroToString(flightNumber,
    	                zeroDefinitionModel.getZerosToAppend().intValue());
    	       }

    	     
    	}
    
    
    // Set the fliight with new flight no
    return flightNumber;
}
   
   
    private MessageLeadingZerosDefinitionModel getDefinitionForLeadingZeros(
          MessageLeadingZerosDefinitionModel requestModel) throws CustomException {
       // Append default values for parameters which is empty
       // FlightType
       if (StringUtils.isEmpty(requestModel.getFlightType())) {
          requestModel.setFlightType("**");
       }
       // Message Type
       if (StringUtils.isEmpty(requestModel.getMessageType())) {
          requestModel.setMessageType("**");
       }
       // Country
       if (StringUtils.isEmpty(requestModel.getFlightNumber())) {
          requestModel.setFlightNumber("**");
       }
       // FlightKey
       if (StringUtils.isEmpty(requestModel.getOffPoint())) {
          requestModel.setOffPoint("**");
       }
       return this.fetchObject(MessageResendSQL.SQL_GET_LEADING_ZEROS_FOR_MESSAGE.getQueryId(), requestModel,
    		   sqlSession);
    }
    
    /**
     * @param characterString
     * @param noOfZeroToRemove
     * @return string after removing zero
     */
    public static String removeZeroFromString(String characterString, int noOfZeroToRemove) {
       int indexOfZero = characterString.indexOf("0");
       if (indexOfZero == -1) {
          return characterString;
       } else {
          for (int i = 0; i < noOfZeroToRemove; i++) {
             if (characterString.toCharArray()[0] != '0') {
                break;
             } else {
                characterString = removeCharAt(characterString, 1);
             }
          }
       }
       return characterString;

    }
    
    

    /**
     * @param characterString
     * @param noOfZeroToAdd
     * @return string after appending zero
     */
    public static String appendZeroToString(String characterString, int noOfZeroToAdd) {
       int startIndexOfDigit = 0;
       for (int i = 0; i < characterString.length(); i++) {
          if (Character.isDigit(characterString.charAt(i))) {
             startIndexOfDigit = i;
             break;
          }
       }
       String stringBeforeDigit = characterString.substring(0, startIndexOfDigit);
       String stringWithDigit = characterString.substring(startIndexOfDigit, characterString.length());
       String zeroString = "";
       for (int i = 0; i < noOfZeroToAdd; i++) {
          zeroString = zeroString + "0";
       }
       if (StringUtils.isEmpty(stringBeforeDigit)) {
          return new String(zeroString + stringWithDigit);
       } else {
          return new String(stringBeforeDigit + zeroString + stringWithDigit);
       }

    }
    
    /**
     * @param s
     * @param pos
     * @return string after finding substring with position specified
     */
    public static String removeCharAt(String s, int pos) {
       return s.substring(pos);
    }
    
  

/**
    * @param airportCode
    * @param message
    * @param processedTime
    * @param incomingFFMLogDetail
    * @return
    */
   private IncomingFFMLog copyIncomingFFMLog(String airportCode, String message, LocalDateTime processedTime,
         IncomingFFMLogDetail incomingFFMLogDetail) {
      boolean messageProcessed = !StringUtils.isEmpty(message) ? true : false;
      IncomingFFMLog copyIncomingFFMLog = new IncomingFFMLog();
      //
      copyIncomingFFMLog.setFlightKey(incomingFFMLogDetail.getFlightKey());
      copyIncomingFFMLog.setDateSTA(incomingFFMLogDetail.getDateSTA());
      copyIncomingFFMLog.setAirportCode(airportCode);
      // Initial set to FALSE
      copyIncomingFFMLog.setNilCargo(false);
      copyIncomingFFMLog.setLastPartReceived(messageProcessed);
      copyIncomingFFMLog.setAllPartsReceived(messageProcessed);
      copyIncomingFFMLog.setMessage(message);
      copyIncomingFFMLog.setMessageProcessedTime(messageProcessed ? processedTime : null);
      copyIncomingFFMLog.setMessageStatus(incomingFFMLogDetail.getMessageStatus());
     
      //
      return copyIncomingFFMLog;
   }
   
}
