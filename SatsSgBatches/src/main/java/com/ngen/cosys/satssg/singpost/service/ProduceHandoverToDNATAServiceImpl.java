package com.ngen.cosys.satssg.singpost.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.singpost.dao.SingPostOutgoingMessageDAO;
import com.ngen.cosys.satssg.singpost.model.MailBagRequestModel;
import com.ngen.cosys.satssg.singpost.model.PushMailBagRequestModel;

@Service
public class ProduceHandoverToDNATAServiceImpl implements ProduceHandoverToDNATAService {

   @Autowired
   private SingPostOutgoingMessageDAO singPostDao;

   @Override
   public PushMailBagRequestModel pushHandoverToDNATAStatus(Object value) throws CustomException {
      List<MailBagRequestModel> mailBags = singPostDao.pushHandoverToDNATAStatus(value);
      for (MailBagRequestModel bag : mailBags) {
         bag.setBagConditionCode("20");
         bag.setBagStatus("TA");
      }
      PushMailBagRequestModel mailBagResponse = new PushMailBagRequestModel();
      mailBagResponse.setMailBag(mailBags);
      singPostDao.logDataIntoAirmailEventTable(mailBags);
      return mailBagResponse;
   }

}
