/**
 * 
 * MailLoggerService.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          09 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.impbd.service.mail.logger;

import java.math.BigInteger;

import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Email Logger service
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface MailLoggerService {

   /**
    * @param emailEvent
    */
   BigInteger logEMailEventMessage(EMailEvent emailEvent) throws CustomException;
   
   /**
    * @param emailEvent
    * @param messageId
    * @throws CustomException
    */
   void updateEMailMessageLog(String msgStatus, String errorMessage, BigInteger messageId) throws CustomException;
   
}
