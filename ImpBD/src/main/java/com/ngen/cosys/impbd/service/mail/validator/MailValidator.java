/**
 * 
 * MailValidator.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          17 JUN, 2018   NIIT      -
 */
package com.ngen.cosys.impbd.service.mail.validator;

import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Mail Validator for Mail Service
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface MailValidator {

  /**
    * @param baseBO
    * @return
    * @throws CustomException
    */
   boolean validate(EMailEvent emailEvent) throws CustomException;
	   
}
