/**
 * 
 * ProduceSingPostMessageServiceImpl.java
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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.dao.ProduceSingPostMessageDao;
import com.ngen.cosys.satssg.interfaces.singpost.model.MailBagRequestModel;
import com.ngen.cosys.satssg.interfaces.singpost.model.PushMailBagRequestModel;

@Service(value = "bagReceivingScan")
public class ProduceSingPostMessageBagReceivingServiceImpl implements ProduceSingPostMessageService {
   
   @Autowired
   private ProduceSingPostMessageDao produceSingPostMessageDao;   
   @Override
   public PushMailBagRequestModel pushMailBagStatus(PushMailBagRequestModel mailBagRequestModel) throws CustomException {
       
      List<MailBagRequestModel> mailBags = new ArrayList<>();
      for (MailBagRequestModel bag : mailBagRequestModel.getMailBag()) {
         MailBagRequestModel bagStatus= produceSingPostMessageDao.pushMailBagReceivingScanStatus(bag);
         bagStatus.setBagConditionCode("20");
         bagStatus.setBagStatus("AA");
         mailBags.add(bagStatus);
      }
      PushMailBagRequestModel mailBagResponse = new PushMailBagRequestModel();
      mailBagResponse.setMailBag(mailBags);
      return mailBagResponse;
   }
}
