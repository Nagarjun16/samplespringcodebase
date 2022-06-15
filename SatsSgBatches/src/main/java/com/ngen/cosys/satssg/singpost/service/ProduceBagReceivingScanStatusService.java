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
package com.ngen.cosys.satssg.singpost.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.singpost.model.PushMailBagRequestModel;

public interface ProduceBagReceivingScanStatusService {

   PushMailBagRequestModel pushBagReceivingScanStatus(Object value) throws CustomException;

}
