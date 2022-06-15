/**
 * This is a repository component implementation for Auto Expire PO
 * 
 * @author NIIT Technologies Pvt. Ltd
 */
package com.ngen.cosys.application.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.application.model.AutoExpireDeliveryRequestModel;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository
public class AutoExpireDeliveryRequestDAOImpl extends BaseDAO implements AutoExpireDeliveryRequestDAO {

   @Autowired
   private SqlSessionTemplate sqlSessionTemplate;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.application.dao.AutoExpireDeliveryRequestDAO#getShipments()
    */
   @Override
   public List<AutoExpireDeliveryRequestModel> getShipments() throws CustomException {
      return this.fetchList("sqlGetDeliveryRequestsForExpiry", new Object(), sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.application.dao.AutoExpireDeliveryRequestDAO#expirePO(com.ngen
    * .cosys.application.model.AutoExpireDeliveryRequestModel)
    */
   @Override
   public boolean expirePO(AutoExpireDeliveryRequestModel autoExpireDeliveryRequestModel) throws CustomException {
      // Nullify the inventory request details
      this.updateData("sqlUpdateNullInventoryDeliveryRequest", autoExpireDeliveryRequestModel, sqlSessionTemplate);

      // Expire PO
      int recordsUpdated = this.updateData("sqlUpdateAutoExpireDeliveryRequest", autoExpireDeliveryRequestModel,
            sqlSessionTemplate);

      return recordsUpdated > 0 ? true : false;
   }

}