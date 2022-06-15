/**
 * 
 * ProduceSingPostMessageService.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 7 May, 2018 NIIT -
 */
package com.ngen.cosys.satssg.interfaces.singpost.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.model.PushMailBagRequestModel;

public interface ProduceSingPostMessageService {

   PushMailBagRequestModel pushMailBagStatus(PushMailBagRequestModel mailBagRequestModel) throws CustomException;

}
