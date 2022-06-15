package com.ngen.cosys.satssg.interfaces.singpost.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.dao.ConsumeSingPostMessageDao;
import com.ngen.cosys.satssg.interfaces.singpost.model.MailBagResponseModel;
import com.ngen.cosys.satssg.interfaces.singpost.model.PullMailBagResponseModel;

@Service(value = "deliveryAcknowledgement")
public class ConsumeSingPostMessageDeliveryAckServiceImpl implements ConsumeSingPostMessageService{
   
   @Autowired
   private ConsumeSingPostMessageDao consumeSingPostMessageDao; 
   
   @Override
   public PullMailBagResponseModel pullMailBagStatus(PullMailBagResponseModel pullMailBagResponseModel)
         throws CustomException {
      List<MailBagResponseModel> mailBags = new ArrayList<>();
      for (MailBagResponseModel bag : pullMailBagResponseModel.getMailBag()) {
         MailBagResponseModel bagStatus= consumeSingPostMessageDao.insertMailBagStatus(bag);
         bagStatus.setBagStatus("DL");
         bagStatus.setDestinationCountry("BH");
         bagStatus.setServiceType("INTMail");
         mailBags.add(bagStatus);
      }
      PullMailBagResponseModel mailBagResponse = new PullMailBagResponseModel();
      mailBagResponse.setMailBag(mailBags);
      return mailBagResponse;
   }

   @Override
   public List<MailBagResponseModel> processPASummary(List<MailBagResponseModel> paSummaryMailBags)
         throws CustomException {
      return paSummaryMailBags;
   }

   @Override
   public List<MailBagResponseModel> processPADetail(List<MailBagResponseModel> paDetailMailBags)
         throws CustomException {
      return paDetailMailBags;
   }

   @Override
   public List<MailBagResponseModel> processDLV(List<MailBagResponseModel> dlvMailBags) throws CustomException {
      return dlvMailBags;
   }

   @Override
   public List<MailBagResponseModel> processIPSAA(List<MailBagResponseModel> ipsAaMailBags) throws CustomException {
      return ipsAaMailBags;
   }

}
