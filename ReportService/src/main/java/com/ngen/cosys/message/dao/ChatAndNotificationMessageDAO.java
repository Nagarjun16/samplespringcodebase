package com.ngen.cosys.message.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.message.model.UserSystemNotification;

public interface ChatAndNotificationMessageDAO {

 public void saveSystemNotificationDetails(UserSystemNotification systemNotification);
   
   public List<UserSystemNotification> getAllSystemNotificationDetails(BigInteger userId);
}
