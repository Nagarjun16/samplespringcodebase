/**
 * {@link CacheUpdateService}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.cache.client.service;

import com.ngen.cosys.framework.exception.CustomException;

/**
 * Shipment Date Cache Update Service
 * 
 * @author NIIT Technologies Ltd
 */
public interface CacheUpdateService {

   /**
    * Expired (Departed/Delivered/Rejected/Returned) Shipment Date details to remove from Caching Service
    * 
    * @throws CustomException
    */
   void shipmentDateCacheRefresh() throws CustomException;
   
}
