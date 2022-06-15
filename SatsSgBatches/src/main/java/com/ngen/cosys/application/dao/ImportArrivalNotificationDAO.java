/**
 * Repository interface which has business method for sending notification
 */
package com.ngen.cosys.application.dao;

import java.util.List;

import com.ngen.cosys.application.model.ImportArrivalNotificationModel;
import com.ngen.cosys.framework.exception.CustomException;

public interface ImportArrivalNotificationDAO {

   /**
    * Method to get list of shipments for notification
    * 
    * @return List<ImportArrivalNotificationModel> - List of shipments for which
    *         notification needs to be sent
    * @throws CustomException
    */
   List<ImportArrivalNotificationModel> get() throws CustomException;

   /**
    * Method to update NOA status for shipments on which notification has been sent
    * 
    * @param shipments
    * @throws CustomException
    */
   void updateNOAForShipment(List<ImportArrivalNotificationModel> shipments) throws CustomException;

}