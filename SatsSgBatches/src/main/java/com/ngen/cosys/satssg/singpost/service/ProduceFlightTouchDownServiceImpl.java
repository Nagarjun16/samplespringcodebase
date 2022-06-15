package com.ngen.cosys.satssg.singpost.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.singpost.dao.SingPostOutgoingMessageDAO;
import com.ngen.cosys.satssg.singpost.model.FlightTouchDown;
import com.ngen.cosys.satssg.singpost.model.FlightTouchDownBase;
import com.ngen.cosys.satssg.singpost.model.MailBagRequestModel;

@Service
public class ProduceFlightTouchDownServiceImpl implements ProduceFlightTouchDownService {

   @Autowired
   private SingPostOutgoingMessageDAO singPostDao;

   @Override
   public FlightTouchDownBase pushFlightTouchDownStatus(Object value) throws CustomException {
      List<FlightTouchDown> mailBags = singPostDao.pushFlightTouchDownStatus(value);
      List<MailBagRequestModel> requestModelList = new ArrayList<>();
      for (FlightTouchDown bag : mailBags) {
         bag.setBagConditionCode("20");
         bag.setBagStatus("LD");
         MailBagRequestModel requestModel = new MailBagRequestModel();
         requestModel.setBagConditionCode(bag.getBagConditionCode());
         requestModel.setBagStatus(bag.getBagStatus());
         requestModel.setBagStatusDateTime(bag.getBagStatusDateTime());
         requestModel.setFlightDate(bag.getFlightTouchDownDateTime());
         requestModel.setFlightNumber(bag.getFlightNumber());
         requestModel.setRecpID(bag.getRecpID());
         requestModel.setShipmentId(bag.getShipmentId());
         requestModel.setShipmentNumber(bag.getShipmentNumber());
         requestModel.setCarriercode(bag.getCarrierCode());
         requestModelList.add(requestModel);
      }
      FlightTouchDownBase mailBagResponse = new FlightTouchDownBase();
      mailBagResponse.setMailBag(mailBags);
      singPostDao.logDataIntoAirmailEventTable(requestModelList);
      return mailBagResponse;
   }

}
