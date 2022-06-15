/**
 * 
 * ProcessorLoggerService.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          09 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.events.service.stream.processor.logger;

import java.math.BigInteger;

import com.ngen.cosys.framework.exception.CustomException;

/**
 * This Processor Logger service used for FAX/SMS logging
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface ProcessorLoggerService {

   /**
    * @param emailEvent
    */
   BigInteger logProcessorEventMessage(Object eventPayload) throws CustomException;
   
   /**
    * @param emailEvent
    * @param messageId
    * @throws CustomException
    */
   void updateProcessorMessageLog(String msgStatus, String errorMessage, BigInteger messageId) throws CustomException;
   
}
