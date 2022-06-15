package com.ngen.cosys.message.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ngen.cosys.message.model.UserSystemNotification;

@Service
public class ChatAndNotificationMessageServiceImpl implements ChatAndNotificationMessageService {

   @Override
   public void saveSystemNotificationDetails(UserSystemNotification systemNotification) {
      // TODO Auto-generated method stub

   }

   @Override
   public List<UserSystemNotification> getAllSystemNotificationDetails(BigInteger userId) {
      // TODO Auto-generated method stub
      return null;
   }

}
