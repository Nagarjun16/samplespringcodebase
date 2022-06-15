package com.ngen.cosys.message.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ngen.cosys.message.model.UserSystemNotification;

@Repository
public class ChatAndNotificationMessageDAOImpl implements ChatAndNotificationMessageDAO {

   
   @Override
   public List<UserSystemNotification> getAllSystemNotificationDetails(BigInteger userId) {
      // TODO Auto-generated method stub
      return null;
   }
   
   @Override
   public void saveSystemNotificationDetails(UserSystemNotification systemNotification) {
      // TODO Auto-generated method stub
      
   }
}
