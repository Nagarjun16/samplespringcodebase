/**
 * Repository implementation component which has business method for sending
 * notification
 */
package com.ngen.cosys.application.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.application.model.ImportArrivalNotificationModel;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Repository
public class ImportArrivalNotificationDAOImpl extends BaseDAO implements ImportArrivalNotificationDAO {

   @Autowired
   private SqlSessionTemplate sqlSessionTemplate;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.application.dao.ImportArrivalNotificationDAO#get()
    */
   @Override
   public List<ImportArrivalNotificationModel> get() throws CustomException {
      return this.fetchList("sqlGetImportArrivalNotificationShipments",MultiTenantUtility.getAirportCityMap(""), sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.application.dao.ImportArrivalNotificationDAO#
    * updateNOAForShipment(java.util.List)
    */
   @Override
   public void updateNOAForShipment(List<ImportArrivalNotificationModel> shipments) throws CustomException {
      this.updateData("sqlQueryUpdateShipmentVerificationNOAStatus", shipments, sqlSessionTemplate);

   }

}