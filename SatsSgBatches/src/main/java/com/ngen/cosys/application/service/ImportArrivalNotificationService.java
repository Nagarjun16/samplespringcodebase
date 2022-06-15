/**
 * Service interface which has business method for sending notification
 */
package com.ngen.cosys.application.service;

import com.ngen.cosys.framework.exception.CustomException;

public interface ImportArrivalNotificationService {

   /**
    * Method to trigger arrival notification for General/EAP/EAW shipments which
    * were not triggered during Break Down/Document Complete
    * 
    * @throws CustomException
    */
   void sendNotification() throws CustomException;

}