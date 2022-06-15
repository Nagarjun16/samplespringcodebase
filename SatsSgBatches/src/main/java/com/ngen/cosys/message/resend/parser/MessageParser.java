/**
 * {@link MessageParser}
 * 
 * @copyright SATS Singapore 2020-21
 * @author Coforge PTE Ltd
 */
package com.ngen.cosys.message.resend.parser;

import com.ngen.cosys.message.resend.model.IncomingESBMessageLog;

/**
 * Message Parser
 * 
 * @author Coforge PTE Ltd
 */
public interface MessageParser {

   /**
    * Parse Message
    * 
    * @param messageLog
    * @return
    */
   Object parse(IncomingESBMessageLog messageLog);
   
}
