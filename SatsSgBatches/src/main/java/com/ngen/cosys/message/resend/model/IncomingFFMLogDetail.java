/**
 * {@link IncomingFFMLogDetail}
 * 
 * @copyright SATS Singapore 2020-21
 * @author Coforge PTE Ltd
 */
package com.ngen.cosys.message.resend.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Incoming FFM Log details
 * 
 * @author Coforge PTE Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class IncomingFFMLogDetail extends BaseBO {

   public IncomingFFMLogDetail(Integer sequenceNo1, BigInteger messageESBSequenceNo1,Integer versionNo1) {
	   sequenceNo = sequenceNo1;
	   messageESBSequenceNo = messageESBSequenceNo1;
	   versionNo = versionNo1;
	   
	}
   private BigInteger incomingESBMessageLogId;
private BigInteger incomingFFMLogDetailId;
   private BigInteger incomingFFMLogId;
   private BigInteger messageESBSequenceNo;
   private String channel;
   private String carrierCode;
   private String flightNumber;
   private String flightKey;
   private LocalDateTime dateSTA;
   private String segment;
   private String message;
   private Integer versionNo;
   private Integer sequenceNo;
   private String messageStatus;
   private Integer messageOrder; // DESC - Latest comes first
   private Integer partGroup;
   private String createdUserCode;
   private LocalDateTime createdDateTime;
   private String lastUpdatedUserCode;
   private LocalDateTime lastUpdatedDateTime;
   private boolean lastMessage =false;
   private String senderAddress;
   
   //
   private String flagCRUD; // ADD/UPD
   private boolean rejectedFFM =false; 
   
}
