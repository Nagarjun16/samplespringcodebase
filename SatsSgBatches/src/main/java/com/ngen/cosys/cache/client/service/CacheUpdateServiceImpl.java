/**
 * {@link CacheUpdateServiceImpl}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.cache.client.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.cache.client.dao.CacheUpdateDAO;
import com.ngen.cosys.cache.client.model.ShipmentDateCache;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Shipment Date Cache Update Service Implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Service
public class CacheUpdateServiceImpl implements CacheUpdateService {

   private static final Logger LOGGER = LoggerFactory.getLogger(CacheUpdateService.class);
   
   @Autowired
   CacheUpdateDAO cacheUpdateDAO;
   
   @Autowired
   ShipmentDateCacheUpdateService shipmentDateCacheUpdateService;
   
   /**
    * @see com.ngen.cosys.cache.client.service.CacheUpdateService#shipmentDateCacheRefresh()
    */
   @Override
   public void shipmentDateCacheRefresh() throws CustomException {
      LOGGER.info("Cache Update Service :: Shipment Date Cache Refresh - {}");
      List<ShipmentDateCache> cachedShipmentDates = cacheUpdateDAO.getCachedShipmentDateDetails();
      boolean cachedShipmentDatesAvailability = !CollectionUtils.isEmpty(cachedShipmentDates) ? true : false;
      LOGGER.info("Cache Update Service :: Cached Shipment Dates availability - {}, Size - {}",
            String.valueOf(cachedShipmentDatesAvailability), cachedShipmentDates.size());
      if (cachedShipmentDatesAvailability) {
         shipmentDateCacheUpdateService.refresh(cachedShipmentDates);
      }
   }

}
