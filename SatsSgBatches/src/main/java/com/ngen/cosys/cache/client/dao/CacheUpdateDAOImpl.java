/**
 * {@link CacheUpdateDAOImpl}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.cache.client.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.cache.client.model.ShipmentDateCache;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;

/**
 * Cache update DAO Implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Repository
public class CacheUpdateDAOImpl implements CacheUpdateDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(CacheUpdateDAO.class);

   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSessionROI;

   /**
    * @see com.ngen.cosys.cache.client.dao.CacheUpdateDAO#getCachedShipmentDateDetails()
    */
   @Override
   public List<ShipmentDateCache> getCachedShipmentDateDetails() throws CustomException {
      LOGGER.info("Cache Update DAO :: GET Cached Shipment Date Details - {}");
      return sqlSessionROI.selectList(SELECT_CACHED_SHIPMENT_DATE_DETAILS);
   }

}
