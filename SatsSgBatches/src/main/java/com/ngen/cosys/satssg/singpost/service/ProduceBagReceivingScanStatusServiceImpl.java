package com.ngen.cosys.satssg.singpost.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.singpost.dao.SingPostOutgoingMessageDAO;
import com.ngen.cosys.satssg.singpost.model.MailBagRequestModel;
import com.ngen.cosys.satssg.singpost.model.PushMailBagRequestModel;

@Service
public class ProduceBagReceivingScanStatusServiceImpl implements ProduceBagReceivingScanStatusService {

   @Autowired
   private SingPostOutgoingMessageDAO singPostDao;

   @Autowired
   ConnectorLoggerService connectorLogger;

   @Override
   public PushMailBagRequestModel pushBagReceivingScanStatus(Object value) throws CustomException {

      List<MailBagRequestModel> mailBags = singPostDao.pushMailBagReceivingScanStatus(value);
      for (MailBagRequestModel bag : mailBags) {
         bag.setBagConditionCode("20");
         bag.setBagStatus("AA");
      }
      PushMailBagRequestModel mailBagResponse = new PushMailBagRequestModel();
      mailBagResponse.setMailBag(mailBags);
      singPostDao.logDataIntoAirmailEventTable(mailBags);
      return mailBagResponse;
   }

}
