/**
 * {@link CacheUpdateDAO}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.cache.client.dao;

import java.util.List;

import com.ngen.cosys.cache.client.model.ShipmentDateCache;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Cache Update repository
 * 
 * @author NIIT Technologies Ltd
 */
public interface CacheUpdateDAO {

   static final String SELECT_CACHED_SHIPMENT_DATE_DETAILS = "selectCachedShipmentDateDetails";
   
   /**
    * Expired (Departed/Delivered/Rejected/Returned) Shipment date details
    * 
    * @return
    * @throws CustomException
    */
   List<ShipmentDateCache> getCachedShipmentDateDetails() throws CustomException;
   
}
