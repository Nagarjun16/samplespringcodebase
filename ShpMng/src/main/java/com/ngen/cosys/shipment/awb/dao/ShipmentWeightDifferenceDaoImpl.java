/**
 * This is an repository implementation component for implementing functionality
 * for weight sync between Shipment Master and Shipment Inventory
 */
package com.ngen.cosys.shipment.awb.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.awb.model.ShipmentWeightDifferenceModel;

@Repository
public class ShipmentWeightDifferenceDaoImpl extends BaseDAO implements ShipmentWeightDifferenceDao {

   @Autowired
   private SqlSessionTemplate sqlSessionTemplate;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.awb.dao.ShipmentWeightDifferenceDao#
    * syncWeightWithInventory(com.ngen.cosys.shipment.awb.model.
    * ShipmentWeightDifferenceModel)
    */
   @Override
   public void syncWeightWithInventory(ShipmentWeightDifferenceModel requestModel) throws CustomException {

      // Get the weight difference
      ShipmentWeightDifferenceModel responseModel = this.fetchObject("sqlGetShipmentMasterWeightDiffrenceWithInventory",
            requestModel, sqlSessionTemplate);

      // If not empty
      if (!ObjectUtils.isEmpty(responseModel)) {
         // Set the shipment id
         responseModel.setShipmentId(requestModel.getShipmentId());
         responseModel.setCreatedBy(requestModel.getCreatedBy());
         responseModel.setLoggedInUser(requestModel.getLoggedInUser());

         // If shipment master is higher then increase the weight from last part
         if (!ObjectUtils.isEmpty(responseModel.getShipmentMasterIsHigher())
               && !ObjectUtils.isEmpty(responseModel.getShipmentInventoryIsHigher())
               && responseModel.getShipmentMasterIsHigher() && !responseModel.getShipmentInventoryIsHigher()) {
         }

         // If shipment inventory is higher then subtract the weight from last part
         if (!ObjectUtils.isEmpty(responseModel.getShipmentMasterIsHigher())
               && !ObjectUtils.isEmpty(responseModel.getShipmentInventoryIsHigher())
               && responseModel.getShipmentInventoryIsHigher() && !responseModel.getShipmentMasterIsHigher()) {
    	 
        	 throw new CustomException("awb.doc.wgt.equal.more.brkdwn.wgt","checking Invenrtory weight",ErrorType.ERROR);
         }
      }
   }

}
