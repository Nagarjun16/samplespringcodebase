/**
 * This is a repository component for Auto Expire PO
 * 
 * @author NIIT Technologies Pvt. Ltd
 */
package com.ngen.cosys.application.dao;

import java.util.List;

import com.ngen.cosys.application.model.AutoExpireDeliveryRequestModel;
import com.ngen.cosys.framework.exception.CustomException;

public interface AutoExpireDeliveryRequestDAO {

   /**
    * Method to get list of shipments which has to be expired
    * 
    * @return List<AutoExpireDeliveryRequestModel> - List of expirable shipments
    * @throws CustomException
    */
   List<AutoExpireDeliveryRequestModel> getShipments() throws CustomException;

   /**
    * Method which auto expires PO requests which has not been yet issued
    * 
    * @param AutoExpireDeliveryRequestModel
    *           - Request for cancelling an Delivery Request
    * @throws CustomException
    */
   boolean expirePO(AutoExpireDeliveryRequestModel autoExpireDeliveryRequestModel) throws CustomException;

}