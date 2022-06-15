package com.ngen.cosys.satssg.singpost.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.singpost.dao.SingPostOutgoingMessageDAO;
import com.ngen.cosys.satssg.singpost.model.MailBagRequestModel;
import com.ngen.cosys.satssg.singpost.model.PushMailBagRequestModel;

@Service
public class ProduceBagHandoverToAirlineServiceImpl implements ProduceBagHandoverToAirlineService{
   
   @Autowired
   private SingPostOutgoingMessageDAO singPostDao;
   
   @Override
   public PushMailBagRequestModel pushBagHandoverToAirlineStatus(Object value) throws CustomException {
      List<MailBagRequestModel> mailBags = singPostDao.pushBagHandoverToAirlineStatus(value);
      for (MailBagRequestModel bag : mailBags) {    
         bag.setBagConditionCode("20");
         bag.setBagStatus("HA");
      }
      PushMailBagRequestModel mailBagResponse = new PushMailBagRequestModel();
      mailBagResponse.setMailBag(mailBags);
      singPostDao.logDataIntoAirmailEventTable(mailBags);
      return mailBagResponse;
    }
 }
