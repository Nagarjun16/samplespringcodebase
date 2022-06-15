/**
 * {@link FFMUtils}
 * 
 * @copyright SATS Singapore 2020-21
 * @author Coforge PTE Ltd
 */
package com.ngen.cosys.message.resend.util;

import static com.ngen.cosys.message.resend.common.MessageResendConstants.ADD;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.HOLD;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.INITIATED;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.REJECTED;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.UPD;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.message.resend.model.IncomingESBMessageLog;
import com.ngen.cosys.message.resend.model.IncomingFFMLog;
import com.ngen.cosys.message.resend.model.IncomingFFMLogDetail;
import com.ngen.cosys.message.resend.parser.FFMParser;

/**
 * FFM Utility
 * 
 * @author Coforge PTE Ltd
 */
@Component
public class FFMUtils {

   private static final Logger LOGGER = LoggerFactory.getLogger(FFMUtils.class);
   
   @Autowired
   FFMParser messageParser;
   
   /**
    * Merge FFM Logs
    * 
    * @param holdFFMLogs
    * @param initiatedFFMLogs
    * @return
    */
   public List<IncomingFFMLog> mergeFFMLogs(List<IncomingFFMLog> holdFFMLogs, List<IncomingESBMessageLog> messageLogs) {
      List<IncomingFFMLog> initiatedFFMLogs = getInitiatedFFMLogs(messageLogs);
      holdFFMLogs = setLastIndicator(holdFFMLogs);
      if (!CollectionUtils.isEmpty(holdFFMLogs) && !CollectionUtils.isEmpty(initiatedFFMLogs)) {
         for (IncomingFFMLog holdFFMLog : holdFFMLogs) {
            attachToHoldFFMLog(holdFFMLog, initiatedFFMLogs);
         }
      } else {
         holdFFMLogs = new ArrayList<>();
      }
      if (!CollectionUtils.isEmpty(initiatedFFMLogs)) {
         // Unread messages Copy to hold Message list
         for (IncomingFFMLog initiatedFFMLog : initiatedFFMLogs) {
            if (!initiatedFFMLog.isMessageRead()) {
               holdFFMLogs.add(initiatedFFMLog);
            }
         }
      }
      return analyze(holdFFMLogs);
   }
   
   private List<IncomingFFMLog> setLastIndicator(List<IncomingFFMLog> holdFFMLogs) {
	   for (IncomingFFMLog messageLog : holdFFMLogs) {
		   for(IncomingFFMLogDetail incomingMessageDetail : messageLog.getIncomingFFMLogDetails())
		   {
			   try {
				   incomingMessageDetail = messageParser.parseForLastIndicator(incomingMessageDetail);		             
		           } catch (Exception ex) {
		              LOGGER.error("FFM Utils Message - getInitiatedFFMLogs exception occurred - {}", ex);
		           }
		   }
           
        }
	   return holdFFMLogs;
}

/**
    * GET Initiated FFM Logs
    * 
    * @param messageLogs
    * @return
    */
   private List<IncomingFFMLog> getInitiatedFFMLogs(List<IncomingESBMessageLog> messageLogs) {
      LOGGER.debug("FFM Utils - GET Initiated FFM Logs {}");
      List<IncomingFFMLog> initiatedFFMLogs = Collections.emptyList();
      if (!CollectionUtils.isEmpty(messageLogs)) {
         initiatedFFMLogs = new LinkedList<>();
         IncomingFFMLog incomingFFMLog = null;
         for (IncomingESBMessageLog messageLog : messageLogs) {
            try {
               incomingFFMLog = (IncomingFFMLog) messageParser.parse(messageLog);
               attachToInitiatedFFMLog(initiatedFFMLogs, incomingFFMLog);
            } catch (Exception ex) {
               LOGGER.error("FFM Utils Message - getInitiatedFFMLogs exception occurred - {}", ex);
            }
         }
      }
      return initiatedFFMLogs;
   }
   
   /**
    * Analyze - Latest message to be processed
    * 
    * All parts received case & Flag CRUD selection
    * 
    * @param holdFFMLogs
    * @return
    */
   private List<IncomingFFMLog> analyze(List<IncomingFFMLog> holdFFMLogs) {
      for (IncomingFFMLog holdFFMLog : holdFFMLogs) {
         if (Objects.nonNull(holdFFMLog.getIncomingFFMLogId())) {
            holdFFMLog.setFlagCRUD(UPD);
         } else {
            holdFFMLog.setFlagCRUD(ADD);
         }
         try {
         messagePrioritization(holdFFMLog);
         }catch(Exception ex) {
  		   LOGGER.error("FFM Utils Message - messagePrioritization exception occurred - {}", ex);
  	   }
      }
      return holdFFMLogs;
   }
   
   /**
    * Message Prioritization 
    *   - All Parts case check & Latest messages check
    * 
    * @param holdFFMLog
    */
   private void messagePrioritization(IncomingFFMLog holdFFMLog) {
     /* // Total Sequence Nos
      Set<Integer> sequenceNos = getPartSequenceNos(holdFFMLog);
      // Check if all Parts Received
      verifyAllPartsReceived(holdFFMLog, sequenceNos);
      // Check how many part sequence with Group received
      Map<Integer, BigInteger> latestSequence = partGroupSequenceNos(holdFFMLog);
      // Priority
      latestSequenceMessages(holdFFMLog, latestSequence);*/	   
	  
	   holdFFMLog.setIncomingFFMLogDetails(analyse(holdFFMLog));
	   
	   
   }   
   /**
    * Sequence the message as they come as first and next message rejected should be set
    *   - All Parts case check & Latest messages check
    * 
    * @param holdFFMLog
    */
   private List<IncomingFFMLogDetail> analyse(IncomingFFMLog holdFFMLog) {
	   if(!CollectionUtils.isEmpty(holdFFMLog.getIncomingFFMLogDetails()))
	   {
		   //Sort by ascending order
		   List<IncomingFFMLogDetail> incomingFFMLogDetails =holdFFMLog.getIncomingFFMLogDetails().stream().sorted(Comparator.comparing(IncomingFFMLogDetail::getMessageESBSequenceNo)).collect(Collectors.toList());
		   List<IncomingFFMLogDetail> initiatedFFM = new ArrayList<IncomingFFMLogDetail>();
		   List<IncomingFFMLogDetail> holdFFM = new ArrayList<IncomingFFMLogDetail>();
		   List<IncomingFFMLogDetail> rejectedFFM = new ArrayList<IncomingFFMLogDetail>();
		   List<IncomingFFMLogDetail> incomingFFM = new ArrayList<IncomingFFMLogDetail>();
		  
		   //Sequentially loop first and its next FFM message
		   for(int i=0;i<incomingFFMLogDetails.size();i++)
		   {
			   IncomingFFMLogDetail incomingFFMLogDetail = incomingFFMLogDetails.get(i);
			   Integer sequence = incomingFFMLogDetail.getSequenceNo(); 
			   boolean lastIndicator =incomingFFMLogDetail.isLastMessage();
			   Integer versionNo = incomingFFMLogDetail.getVersionNo();
			   //If sequence 1 and LAST then continue the loop
			   if(sequence.equals(1) && lastIndicator)
			   {
				   holdFFMLog.setTotalPartGroup(holdFFMLog.getTotalPartGroup()+1);
				   incomingFFMLogDetail.setMessageStatus(INITIATED);
				   incomingFFMLogDetail.setPartGroup(holdFFMLog.getTotalPartGroup());
				   initiatedFFM.add(incomingFFMLogDetail);
				   continue;
			   }
				   
			   if(i != incomingFFMLogDetails.size()-1)
			   {
				   for(int j=i+1;j==i+1;j++)
				   {
					   IncomingFFMLogDetail incomingFFMLogDetailNext = incomingFFMLogDetails.get(j);
					   Integer sequenceNext = incomingFFMLogDetailNext.getSequenceNo();	
					   boolean lastIndicatorNext =incomingFFMLogDetailNext.isLastMessage();
					   Integer versionNoNext = incomingFFMLogDetailNext.getVersionNo();
					   //Mark rejected if next is same sequence as previous and both should be CONT or LAST
					   if(sequence.equals(sequenceNext) && lastIndicator==lastIndicatorNext && versionNo.equals(versionNoNext))
					   {
						   incomingFFMLogDetail.setMessageStatus(REJECTED);
						   rejectedFFM.add(incomingFFMLogDetail);
					   }
					   else// create group on sequence and CONT and LAST
					   {
						   holdFFM.add(incomingFFMLogDetail);
						   holdFFM= groupMessages(holdFFM,initiatedFFM,rejectedFFM,holdFFMLog);						   
					   }
				   }	   
				   
			   }
			   else//last in loop
			   {
				   holdFFM.add(incomingFFMLogDetail);
				   holdFFM= groupMessages(holdFFM,initiatedFFM,rejectedFFM,holdFFMLog);	
			   }
			   
		   }
		   incomingFFM.addAll(initiatedFFM);
		   if(!CollectionUtils.isEmpty(holdFFM))
		   {
			   //If list is not empty then set the partGroup
			   incomingFFM.addAll(setPartGroup(holdFFM,holdFFMLog));
		   }	   
		   if(!CollectionUtils.isEmpty(rejectedFFM))
		   {
			 //If list is not empty then set the partGroup
			   incomingFFM.addAll(setPartGroup(rejectedFFM,holdFFMLog));
		   }
		   setCrudStatus(incomingFFM);
		   return incomingFFM;
	   }
	   return null;
	  
}
   
   /**
    * group Messages
    *   
    * @param incomingFFM    
    */
   private List<IncomingFFMLogDetail> groupMessages(List<IncomingFFMLogDetail> holdFFM, List<IncomingFFMLogDetail> initiatedFFM,
		List<IncomingFFMLogDetail> rejectedFFM, IncomingFFMLog holdFFMLog) {	  					 
		 //Filter LAST PART
		   List<IncomingFFMLogDetail> listOfLastMessage = filterLastPartandSortByESBSequence(holdFFM);
		   //Filter CONT PART
		   List<IncomingFFMLogDetail> listOfContMessage = filterContPartandSortByESBSequence(holdFFM);	
		   holdFFM =createGroup(listOfLastMessage,listOfContMessage,initiatedFFM,rejectedFFM,holdFFMLog);
		   return holdFFM;
}
   /**
    * create group for hold and rejected messages
    *   
    * @param incomingFFM    
    */
private Collection<? extends IncomingFFMLogDetail> setPartGroup(List<IncomingFFMLogDetail> incomingFFM, IncomingFFMLog holdFFMLog) {
	   holdFFMLog.setTotalPartGroup(holdFFMLog.getTotalPartGroup()+1);
	   
	   for(IncomingFFMLogDetail ffm:incomingFFM)
		{			
			 ffm.setPartGroup(holdFFMLog.getTotalPartGroup());			 
		}
	   return incomingFFM;
}

/**
    * set CRUD status
    *   
    * @param incomingFFM    
    */
   private void setCrudStatus(List<IncomingFFMLogDetail> incomingFFM) {
		for(IncomingFFMLogDetail ffm:incomingFFM)
		{
			 String flagCRUD = Objects.nonNull(ffm.getIncomingFFMLogDetailId()) ? UPD : ADD;
			 ffm.setFlagCRUD(flagCRUD);
		}	  
   }

/**
    * Create group of all messages depending on LAST messages and grouping all CONT depending on this last message
    *   - Filter all CONT messages
 * @param initiatedFFM 
 * @param holdFFM 
 * @param rejectedFFM 
 * @param holdFFMLog 
 * @param partGroup 
 * @return 
   
    */
private List<IncomingFFMLogDetail> createGroup(List<IncomingFFMLogDetail> listOfLastMessage, List<IncomingFFMLogDetail> listOfContMessage,  List<IncomingFFMLogDetail> initiatedFFM, List<IncomingFFMLogDetail> rejectedFFM, IncomingFFMLog holdFFMLog) 
{
	int partGroup = holdFFMLog.getTotalPartGroup();
	List<IncomingFFMLogDetail> holdFFMNew = new ArrayList<IncomingFFMLogDetail>();
		if(!CollectionUtils.isEmpty(listOfLastMessage))
		{
			//Create a list of sequence and ESBSequence of last sequence Number
			List<IncomingFFMLogDetail> listOflastSequence = listOfLastMessage.stream().map(incomingFFMLogDetail->new IncomingFFMLogDetail(incomingFFMLogDetail.getSequenceNo(),incomingFFMLogDetail.getMessageESBSequenceNo(),incomingFFMLogDetail.getVersionNo())).collect(Collectors.toList());
					  		  
			   //Iterate last sequence and find CONT with sequences less than it. Ex: Last = sequence 3 then find sequence 2 and 1 in CONT list
	        for(IncomingFFMLogDetail incomingFFMLogDetail :listOflastSequence)
	        { 
	        	Integer sequence = incomingFFMLogDetail.getSequenceNo();	
	        	BigInteger messageESBSequence = incomingFFMLogDetail.getMessageESBSequenceNo();
	        	Integer versionNo = incomingFFMLogDetail.getVersionNo();
	        	//If last sequence is 3 then find  2 and 1 in CONT list; only if LAST sequence is greater than 1
	        	if(sequence.intValue()>1)
	        	{
	        		List<IncomingFFMLogDetail> matchedFFMList = new ArrayList<IncomingFFMLogDetail>();
	        		boolean allMatchFound = true;
	        		List<IncomingFFMLogDetail> copyContMessage = new ArrayList<IncomingFFMLogDetail>();
	        		copyContMessage = listOfContMessage.stream().collect(Collectors.toList());
	        		for(int i=sequence.intValue()-1;i>=1; i--)
		        	{
	        			if(!CollectionUtils.isEmpty(copyContMessage))
	        			{
	        				//get matching sequence from cONT List
	        				final int  sequenceValue =i;	        		
			        		Optional<IncomingFFMLogDetail> matchedFFMLog = copyContMessage.stream().filter(incomingFFMLogDetails->incomingFFMLogDetails.getSequenceNo().intValue()==(sequenceValue)
			        				&& incomingFFMLogDetails.getVersionNo().intValue()==versionNo).findFirst();
			        		//any match not found then should be HOLD		        		
			        		if(!matchedFFMLog.isPresent())
			        		{
			        			allMatchFound = false;
			        		}
			        		else
			        		{
			        			matchedFFMList.add(matchedFFMLog.get());
			        			//remove already matched from CONT list			        			
			        			copyContMessage = removeMatchedFFM(copyContMessage,matchedFFMLog.get());		
				        		
			        		} 				        		
	        			}
	        			else
	        			{
	        				allMatchFound = false;
	        			}
		        		
		        	}
	        		if(allMatchFound)
	        		{
	        			partGroup++;
	        			initiatedFFM.addAll(setMessageStatus(matchedFFMList,INITIATED,partGroup));
	        			//Add last Message
		        		initiatedFFM.add(addLastMessage(listOfLastMessage,messageESBSequence,INITIATED,partGroup));	
		        		//remove match cONT
		        		listOfContMessage= removeMatchedFFM(listOfContMessage,matchedFFMList);	
		        		//remove matched last
		        		listOfLastMessage = removeMatchedFFM(listOfLastMessage,messageESBSequence);
	        		}
	        		else
	        		{
	        			//holdFFMNew.addAll(setMessageStatus(matchedFFMList,HOLD,null));
	        			holdFFMNew.add(addLastMessage(listOfLastMessage,messageESBSequence,HOLD,null));	
	        		}
	        			
	        	}
	        	else // last sequence =1 then set as INITIATED
	        	{
	        		partGroup++;
	        		//Add last Message
	        		initiatedFFM.add(addLastMessage(listOfLastMessage,messageESBSequence,INITIATED,partGroup));
	        	}
	        	
	        }
	      //Remaining listOfContMessage and listOfLastMessage  with no match have to be made into HOLD/Rejected Status
	        if(!CollectionUtils.isEmpty(listOfContMessage)  )
	        {
	        	listOfContMessage.addAll(holdFFMNew);	        	
	        	holdFFMNew= setHOLDAndRejectedStatus(holdFFMNew,rejectedFFM,listOfContMessage);
	        	
	        }
	        else// hold ones which did not match above chcek if any rejected 
	        {
	        	holdFFMNew = setHOLDAndRejectedStatus(holdFFMNew,rejectedFFM,holdFFMNew);
	        }       
	        holdFFMLog.setTotalPartGroup(partGroup);   
		}
		else
		{
			holdFFMNew = setHOLDAndRejectedStatus(holdFFMNew,rejectedFFM,listOfContMessage);
			
		}
		return holdFFMNew;
	
   }



/**
* Filter LAST messages 
*   - Filter all CONT messages
* 
* @param holdFFMLog
*/
	private List<IncomingFFMLogDetail> filterLastPartandSortByESBSequence(List<IncomingFFMLogDetail> holdFFM) {
		 List<IncomingFFMLogDetail> listOfContMessage = holdFFM.stream().filter(obj-> obj.isLastMessage()).sorted(Comparator.comparing(IncomingFFMLogDetail::getMessageESBSequenceNo)).collect(Collectors.toList());
		 return listOfContMessage;	
	}

/**
    * Filter CONT messages 
    *   - Filter all CONT messages
    * 
    * @param holdFFMLog
    */
   private List<IncomingFFMLogDetail> filterContPartandSortByESBSequence(List<IncomingFFMLogDetail> holdFFM) {
	   		   
		   List<IncomingFFMLogDetail> listOfContMessage = holdFFM.stream().filter(obj-> !obj.isLastMessage()).sorted(Comparator.comparing(IncomingFFMLogDetail::getMessageESBSequenceNo)).collect(Collectors.toList());
		   return listOfContMessage;		   
	   
}
   

   
 //Last Message
   private IncomingFFMLogDetail addLastMessage(List<IncomingFFMLogDetail> listOfLastMessage,
   		BigInteger messageESBSequence, String status, Integer partGroup) {
   	IncomingFFMLogDetail lastMessage = listOfLastMessage.stream().filter(obj->obj.getMessageESBSequenceNo().equals(messageESBSequence)).findFirst().get();
   	lastMessage.setMessageStatus(status);   	
   	lastMessage.setPartGroup(partGroup);
   	return lastMessage;
   }
   
   /**
    *Remove match FFM from CONT List
    * @param listOfContMessage, sequenceMatchList
    * 
    * 
    * @return
    */
   private List<IncomingFFMLogDetail> removeMatchedFFM(List<IncomingFFMLogDetail> listOfContMessage,
   		List<IncomingFFMLogDetail> sequenceMatchList) {
   	if(!CollectionUtils.isEmpty(listOfContMessage) && !ObjectUtils.isEmpty(sequenceMatchList))
   	{
   		
   			listOfContMessage = listOfContMessage.stream().filter(contMessage->sequenceMatchList.stream().allMatch(sequenceMatch->!sequenceMatch.getMessageESBSequenceNo().equals(contMessage.getMessageESBSequenceNo())))
   					.collect(Collectors.toList());
   		
   	}
   	
   	return listOfContMessage;
   }
   
   private List<IncomingFFMLogDetail> removeMatchedFFM(List<IncomingFFMLogDetail> listOfLastMessage,
			BigInteger messageESBSequence) {
		if(!CollectionUtils.isEmpty(listOfLastMessage) && !ObjectUtils.isEmpty(messageESBSequence))
	   	{   		
			listOfLastMessage = listOfLastMessage.stream().filter(lastMessage->!lastMessage.getMessageESBSequenceNo().equals(messageESBSequence))
	   					.collect(Collectors.toList());
	   		
	   	}
		return listOfLastMessage;
	}

   /**
    *Set message status
 * @param partGroup 
    * @param matchedFFMList, status
    * 
    *
    * @return
    */
   private List<IncomingFFMLogDetail> setMessageStatus(List<IncomingFFMLogDetail> matchedFFMList,String status, Integer partGroup) {
   	 List<IncomingFFMLogDetail> ffm = new ArrayList<IncomingFFMLogDetail>();
   	for(IncomingFFMLogDetail matchFFM:matchedFFMList) 
   	{
   		matchFFM.setMessageStatus(status);
   		matchFFM.setPartGroup(partGroup);   		
   		ffm.add(matchFFM);
   	}
   	return ffm;
   	
   }

   /**
    *Remove matched FFM from the continuelist
    * @param incomingFFMLogDetail 
    * 
    * @param listOfIncomingFFMDetails
    * @return
    */
   private List<IncomingFFMLogDetail> removeMatchedFFM(List<IncomingFFMLogDetail> listOfContMessage, IncomingFFMLogDetail incomingFFMLogDetail) {
   	if(!CollectionUtils.isEmpty(listOfContMessage) && !ObjectUtils.isEmpty(incomingFFMLogDetail))
   	{   		
   			listOfContMessage = listOfContMessage.stream().filter(contMessage->!contMessage.getMessageESBSequenceNo().equals(incomingFFMLogDetail.getMessageESBSequenceNo()))
   					.collect(Collectors.toList());
   		
   	}
   	
   	return listOfContMessage;
   }
   
   

  


/**
 *Set Hold and rejected status for matched FFM
 * @param holdFFM, rejectedFFM,listOfContMessage
 * 
 * 
 * @return
 */
private List<IncomingFFMLogDetail> setHOLDAndRejectedStatus(List<IncomingFFMLogDetail> holdFFM, List<IncomingFFMLogDetail> rejectedFFM,
		List<IncomingFFMLogDetail> listOfContMessage) {	
	List<IncomingFFMLogDetail> holdFFMnew = new ArrayList<IncomingFFMLogDetail>();	
	if(!CollectionUtils.isEmpty(listOfContMessage) )
	{
		for(IncomingFFMLogDetail ffm :listOfContMessage)
		{
			if(!CollectionUtils.isEmpty(listOfContMessage))
			{
				Integer sequenceNo= ffm.getSequenceNo();
				 boolean lastIndicator =ffm.isLastMessage();
				 Integer versionNo =  ffm.getVersionNo();
				//Sort in descending order
				List<IncomingFFMLogDetail> sequenceMatchList = listOfContMessage.stream().filter(obj->obj.getSequenceNo().equals(sequenceNo) && obj.isLastMessage()==lastIndicator && obj.getVersionNo().equals(versionNo) ).sorted(Comparator.comparing(IncomingFFMLogDetail::getMessageESBSequenceNo).reversed()).collect(Collectors.toList());
				if(sequenceMatchList.size()>1)
				{
					//Chcek any same sequence number latest should be picked other should be rejected
					for(int i=0;i<sequenceMatchList.size();i++)
					{
						IncomingFFMLogDetail sequenceMatch = sequenceMatchList.get(i);
						if(i==0)
						{
							sequenceMatch.setMessageStatus(HOLD);						
							holdFFMnew.add(sequenceMatch);
						}
						else
						{
							sequenceMatch.setMessageStatus(REJECTED);
							rejectedFFM.add(sequenceMatch);
						}
					}
				}
				else
				{
					if(sequenceMatchList!=null && sequenceMatchList.size()>0)
					{
						//if sequence does not repeat then HOLD
						IncomingFFMLogDetail sequenceMatch = sequenceMatchList.get(0);
						sequenceMatch.setMessageStatus(HOLD);
						holdFFMnew.add(sequenceMatch);
					}
					
				}	
				//remove alreaday filtered sequences
				listOfContMessage= removeMatchedFFM(listOfContMessage,sequenceMatchList);
			}
			
		}
		//New Hold List to be returned
		
		
	}
	return holdFFMnew;
	
	
}



/**
 *Get Map of Sequence No
 * 
 * @param listOfIncomingFFMDetails
 * @return
 *//*
private Map<Integer, IncomingFFMLogDetail> getSequenceMap(List<IncomingFFMLogDetail> listOfIncomingFFMDetails) {
	   Map<Integer,IncomingFFMLogDetail> mapOfSequence = new HashMap<Integer,IncomingFFMLogDetail>();
	   if(!CollectionUtils.isEmpty(listOfIncomingFFMDetails))
	   {
		   List<IncomingFFMLogDetail> incomingFFMLogDetailList  = listOfIncomingFFMDetails;
		   mapOfSequence = incomingFFMLogDetailList.stream().collect(Collectors.toMap(IncomingFFMLogDetail::getSequenceNo,incomingFFMLogDetail->incomingFFMLogDetail));
	   }
	   return mapOfSequence;
   }*/




/*
*//**
    * Part Sequence Nos
    * 
    * @param holdFFMLog
    * @return
    *//*
   private Set<Integer> getPartSequenceNos(IncomingFFMLog holdFFMLog) {
      Set<Integer> sequenceNos = new TreeSet<>();
      for (IncomingFFMLogDetail incomingFFMLogDetail : holdFFMLog.getIncomingFFMLogDetails()) {
         sequenceNos.add(incomingFFMLogDetail.getSequenceNo());
      }
      // SequenceNo Update
      holdFFMLog.setSequenceNos(sequenceNos);
      return sequenceNos;
   }
   
   *//**
    * Verify All Parts Received
    * 
    * @param holdFFMLog
    * @param sequenceNos
    *//*
   private void verifyAllPartsReceived(IncomingFFMLog holdFFMLog, Set<Integer> sequenceNos) {
      int j = 1;
      // All Parts Received
      boolean allPartsReceived = true;
      for (Integer sequence : sequenceNos) {
         if (j != sequence) {
            allPartsReceived = false;
            break;
         }
         j += 1;
      }
      if (holdFFMLog.isLastPartReceived() && allPartsReceived) {
         holdFFMLog.setAllPartsReceived(allPartsReceived);
      }
   }
   
   *//**
    * Part Group Sequence Nos
    * 
    * @param holdFFMLog
    *//*
   private Map<Integer, BigInteger> partGroupSequenceNos(IncomingFFMLog holdFFMLog) {
      // Check how many part sequence with Group received
      Map<Integer, BigInteger> latestSequence = new HashMap<>();
      // All Part received case, In case of more than 1 Part Group received 
      // More than one group part sample -- 1-2-3 # 1-3-2
      if (holdFFMLog.isAllPartsReceived()) {
         getAllPartsReceivedPartGroupSequence(holdFFMLog);
         latestSequence = holdFFMLog.getPartGroupSequenceNos();
      } else {
         int partGroup = 1;
         // Not fully received
         holdFFMLog.setTotalPartGroup(partGroup);
         for (IncomingFFMLogDetail incomingFFMLogDetail : holdFFMLog.getIncomingFFMLogDetails()) {
            incomingFFMLogDetail.setPartGroup(partGroup);
            if (latestSequence.containsKey(incomingFFMLogDetail.getSequenceNo())) {
               BigInteger messageESBSequenceNo = incomingFFMLogDetail.getMessageESBSequenceNo();
               if (messageESBSequenceNo.compareTo(latestSequence.get(incomingFFMLogDetail.getSequenceNo())) == 1) {
                  latestSequence.put(incomingFFMLogDetail.getSequenceNo(), messageESBSequenceNo);
               }
            } else {
               latestSequence.put(incomingFFMLogDetail.getSequenceNo(), incomingFFMLogDetail.getMessageESBSequenceNo());
            }
         }
         holdFFMLog.setPartGroupSequenceNos(latestSequence);
      }
      return latestSequence;
   }
   
   *//**
    * Total Part Group Sequence - In case of more than 1 part received
    * 
    * If total Part is 3 received 2 Part Groups 
    * - Part 1-2-3 Sequence No will be 1-2-3
    * - Part 1-3-2 Sequence No will be 4-6-5 (+1) each part
    * 
    * @param holdFFMLog
    *//*
   private void getAllPartsReceivedPartGroupSequence(IncomingFFMLog holdFFMLog) {
      // Sequence Nos
      Set<Integer> sequenceNos = holdFFMLog.getSequenceNos();
      int totalPartGroup = sequenceNos.size();
      // Part Sequence Nos
      Map<Integer, BigInteger> partGroupSequenceNos = new HashMap<>();
      // Total Part Received, Once all received set to 1
      // Till the next part completes still it remains 1
      int i = 0;
      int j = 0;
      int partGroup = 1;
      for (IncomingFFMLogDetail incomingFFMLogDetail : holdFFMLog.getIncomingFFMLogDetails()) {
    	  i += 1;
         // Part belongs to which Set of Group
         incomingFFMLogDetail.setPartGroup(partGroup + j);
         if (partGroupSequenceNos.containsKey(incomingFFMLogDetail.getSequenceNo() + (totalPartGroup * j))) {
            BigInteger messageESBSequenceNo = incomingFFMLogDetail.getMessageESBSequenceNo();
            BigInteger partMessageESBSequenceNo = partGroupSequenceNos
                  .get(incomingFFMLogDetail.getSequenceNo() + (totalPartGroup * j));
            if (messageESBSequenceNo.compareTo(partMessageESBSequenceNo) == 1) {
               partGroupSequenceNos.put(incomingFFMLogDetail.getSequenceNo() + (totalPartGroup * j),
                     messageESBSequenceNo);
            	incomingFFMLogDetail.setRejectedFFM(true);
            }
         } else {
            partGroupSequenceNos.put(incomingFFMLogDetail.getSequenceNo() + (totalPartGroup * j),
                  incomingFFMLogDetail.getMessageESBSequenceNo());
         }
         // Part Group Match then capture latest sequence into Part Group Sequence
         // And reset latest Sequence to ZERO to start from Beginning for next Part Group
         if (sequenceNos.size() == i) {
            j += 1;
            if (!CollectionUtils.isEmpty(holdFFMLog.getPartGroupSequenceNos())) {
               holdFFMLog.getPartGroupSequenceNos().putAll(partGroupSequenceNos);
            } else {
               holdFFMLog.setPartGroupSequenceNos(partGroupSequenceNos);
            }
            partGroupSequenceNos = new HashMap<>();
            i=0;
         }
      }
      // Attach the Last one
      if (!CollectionUtils.isEmpty(partGroupSequenceNos)) {
         if (!CollectionUtils.isEmpty(holdFFMLog.getPartGroupSequenceNos())) {
            holdFFMLog.getPartGroupSequenceNos().putAll(partGroupSequenceNos);
         } else {
            holdFFMLog.setPartGroupSequenceNos(partGroupSequenceNos);
         }
      }
      // Total Part Group Sequence Received
      holdFFMLog.setTotalPartGroup(j);
   }
   
   *//**
    * Latest Sequence Messages
    * 
    * @param holdFFMLog
    * @param latestSequence
    *//*
   private void latestSequenceMessages(IncomingFFMLog holdFFMLog, Map<Integer, BigInteger> latestSequence) {
      // Total Parts Sequence in case of all Parts received
      int totalSequence = holdFFMLog.isAllPartsReceived() ? holdFFMLog.getSequenceNos().size() : 0;
      int totalPartGroup = holdFFMLog.getTotalPartGroup();
      int partGroup = 1; // Part Group starts at 1 and continues
      String messageStatus = holdFFMLog.isAllPartsReceived() ? INITIATED : HOLD;
      holdFFMLog.setMessageStatus(messageStatus);
      //
      for (IncomingFFMLogDetail incomingFFMLogDetail : holdFFMLog.getIncomingFFMLogDetails()) {
         // Incoming FFM Log ID
         // If Total Part Group matches with j (Counter) then attach the same ID else New 
         if (Objects.nonNull(holdFFMLog.getIncomingFFMLogId())
               && totalPartGroup == incomingFFMLogDetail.getPartGroup()) {
            incomingFFMLogDetail.setIncomingFFMLogId(holdFFMLog.getIncomingFFMLogId());
         }
         int partSequence = totalSequence * (incomingFFMLogDetail.getPartGroup() - partGroup);
         // If all parts received then status change to INITIATED / HOLD
         // If not matches found then REJECTED
         if (latestSequence.containsKey(incomingFFMLogDetail.getSequenceNo() + partSequence)) {
            if (Objects.equals(incomingFFMLogDetail.getMessageESBSequenceNo(),
                  latestSequence.get(incomingFFMLogDetail.getSequenceNo() + partSequence) ) && !incomingFFMLogDetail.isRejectedFFM()) {
               String status = holdFFMLog.isAllPartsReceived() && totalPartGroup >= incomingFFMLogDetail.getPartGroup() //
                     ? INITIATED //
                     : HOLD;
               incomingFFMLogDetail.setMessageStatus(status);
            } else {
               incomingFFMLogDetail.setMessageStatus(REJECTED);
            }
            String flagCRUD = Objects.nonNull(incomingFFMLogDetail.getIncomingFFMLogDetailId()) ? UPD : ADD;
            incomingFFMLogDetail.setFlagCRUD(flagCRUD);
         } else { // Never Happens case
            LOGGER.warn("FFM Utils - Latest Sequence Messages - FlightKey - {}, DateSTA - {}, Sequence No - {}",
                  incomingFFMLogDetail.getFlightKey(), incomingFFMLogDetail.getDateSTA(),
                  incomingFFMLogDetail.getSequenceNo());
         }
      }
   }*/
   
   /**
    * @param initiatedFFMLogs
    * @param incomingFFMLog
 * @param isHold 
    */
   private void attachToInitiatedFFMLog(List<IncomingFFMLog> initiatedFFMLogs, IncomingFFMLog incomingFFMLog) {
      if (!CollectionUtils.isEmpty(initiatedFFMLogs)) {
         IncomingFFMLog initiatedFFMLog = null;
         boolean recordPatched = false;
         for (int j = 0; j < initiatedFFMLogs.size(); j++) {
            initiatedFFMLog = initiatedFFMLogs.get(j);
            if (Objects.equals(initiatedFFMLog.getFlightKey(), incomingFFMLog.getFlightKey())
                  && Objects.equals(initiatedFFMLog.getDateSTA(), incomingFFMLog.getDateSTA())
                  && Objects.equals(initiatedFFMLog.getAirportCode(), incomingFFMLog.getAirportCode())) {
               // NIL Cargo
               if (!initiatedFFMLog.isNilCargo() && incomingFFMLog.isNilCargo()) {
                  initiatedFFMLog.setNilCargo(incomingFFMLog.isNilCargo());
               }
               // LAST Part Received
               if (!initiatedFFMLog.isLastPartReceived() && incomingFFMLog.isLastPartReceived()) {
                  initiatedFFMLog.setLastPartReceived(incomingFFMLog.isLastPartReceived());
               }
               initiatedFFMLog.setIncomingFFMLogDetails(copyFFMLogDetail(initiatedFFMLog, incomingFFMLog));
               recordPatched = true;
               
              
            }
         }
         if (!recordPatched) {
            initiatedFFMLogs.add(incomingFFMLog);
         }
      } else { // First message
         initiatedFFMLogs.add(incomingFFMLog);
      }
   }
   
   /**
    * @param holdFFMLog
    * @param initiatedFFMLogs
    */
   private void attachToHoldFFMLog(IncomingFFMLog holdFFMLog, List<IncomingFFMLog> initiatedFFMLogs) {
      for (IncomingFFMLog initiatedFFMLog : initiatedFFMLogs) {
         if (Objects.equals(holdFFMLog.getFlightKey(), initiatedFFMLog.getFlightKey())
               && Objects.equals(holdFFMLog.getDateSTA(), initiatedFFMLog.getDateSTA())
               && Objects.equals(holdFFMLog.getAirportCode(), initiatedFFMLog.getAirportCode())) {
            // NIL Cargo
            if (!holdFFMLog.isNilCargo() && initiatedFFMLog.isNilCargo()) {
               holdFFMLog.setNilCargo(initiatedFFMLog.isNilCargo());
            }
            // LAST Part Received
            if (!holdFFMLog.isLastPartReceived() && initiatedFFMLog.isLastPartReceived()) {
               holdFFMLog.setLastPartReceived(initiatedFFMLog.isLastPartReceived());
            }
            initiatedFFMLog.setMessageRead(true);
            holdFFMLog.getIncomingFFMLogDetails().addAll(initiatedFFMLog.getIncomingFFMLogDetails());
         }
      }
   }
   
   /**
    * @param initiatedFFMLog
    * @param incomingFFMLog
    * @return
    */
   private List<IncomingFFMLogDetail> copyFFMLogDetail(IncomingFFMLog initiatedFFMLog, IncomingFFMLog incomingFFMLog) {
      List<IncomingFFMLogDetail> copyFFMLogDetail = new ArrayList<>();
      for (IncomingFFMLogDetail initiatedFFMLogDetail : initiatedFFMLog.getIncomingFFMLogDetails()) {
         copyFFMLogDetail.add(initiatedFFMLogDetail);
      }
      for (IncomingFFMLogDetail incomingFFMLogDetail : incomingFFMLog.getIncomingFFMLogDetails()) {
         copyFFMLogDetail.add(incomingFFMLogDetail);
      }
      return copyFFMLogDetail;
   }
   
}
