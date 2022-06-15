/**
 * This is a repository implementation class for creating delivery request based
 * on EDelivery Request shipments
 */
package com.ngen.cosys.application.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.ngen.cosys.events.payload.CreateDeliveryRequestStoreEvent;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository
public class AutoCreateDeliveryRequestDAOImpl extends BaseDAO implements AutoCreateDeliveryRequestDAO {

   @Autowired
   private SqlSessionTemplate sqlSessionTemplate;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.application.dao.AutoCreateDeliveryRequestDAO#
    * scheduleDeliveryRequests()
    */
   @Override
   public List<CreateDeliveryRequestStoreEvent> scheduleDeliveryRequests() throws CustomException {
      return this.fetchList("sqlQueryAutoCreateDeliveryRequest", new Object(), sqlSessionTemplate);
   }

   public String getPrinterName(CreateDeliveryRequestStoreEvent request) throws CustomException {

		return fetchObject("getPrinterNameForPickOrder", request, sqlSessionTemplate);
	}
}