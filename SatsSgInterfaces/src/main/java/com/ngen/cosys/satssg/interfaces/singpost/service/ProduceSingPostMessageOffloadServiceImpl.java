package com.ngen.cosys.satssg.interfaces.singpost.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.dao.ProduceSingPostMessageDao;
import com.ngen.cosys.satssg.interfaces.singpost.model.MailBagRequestModel;
import com.ngen.cosys.satssg.interfaces.singpost.model.PushMailBagRequestModel;

@Service(value = "offload")
public class ProduceSingPostMessageOffloadServiceImpl implements ProduceSingPostMessageService {
   
   @Autowired
   private ProduceSingPostMessageDao produceSingPostMessageDao; 
   
   @Override
   public PushMailBagRequestModel pushMailBagStatus(PushMailBagRequestModel mailBagRequestModel)
         throws CustomException {
      List<MailBagRequestModel> mailBags = new ArrayList<>();
      for (MailBagRequestModel bag : mailBagRequestModel.getMailBag()) {
         MailBagRequestModel bagStatus= produceSingPostMessageDao.pushMailBagReceivingScanStatus(bag);
         bagStatus.setBagConditionCode("20");
         bagStatus.setBagStatus("OF");
         bagStatus.setFlightNumber("SQ0285");
         bagStatus.setFlightTouchDownDate(LocalDateTime.now());
         mailBags.add(bagStatus);
      }
      PushMailBagRequestModel mailBagResponse = new PushMailBagRequestModel();
      mailBagResponse.setMailBag(mailBags);
      return mailBagResponse;
   }

}
