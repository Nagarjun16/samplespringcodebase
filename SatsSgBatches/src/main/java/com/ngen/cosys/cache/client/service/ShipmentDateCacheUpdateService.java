/**
 * {@link ShipmentDateCacheUpdateService}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.cache.client.service;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.cache.client.model.ShipmentDateCache;
import com.ngen.cosys.multi.tenancy.constants.TenantContextConstants;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.service.cache.config.NGCCacheConfig;
import com.ngen.cosys.service.cache.config.NGCCacheRequest;
import com.ngen.cosys.service.cache.config.ServiceClientConstants;

/**
 * Shipment Date Cache Update Service
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class ShipmentDateCacheUpdateService {

   private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentDateCacheUpdateService.class);
   
   @Autowired
   NGCCacheConfig ngcCacheConfig;

   @Autowired
   DiscoveryClient discoveryClient;
   
   /**
    * Remove Shipment number
    * 
    * @param shipmentNumber
    */
   public void remove(String shipmentNumber) {
      LOGGER.info("Shipment Date Cache Update Service :: remove cache data {}");
      if (!ngcCacheConfig.shipmentDateCacheAccessEnabled() || ngcCacheConfig.devInstanceActive()) {
         return;
      }
      List<ServiceInstance> serviceInstances = Collections.emptyList();
      if (ngcCacheConfig.discoveryInstanceActive()) {
         serviceInstances = discoveryClient.getInstances(ServiceClientConstants.CACHE_PROVIDER);
      }
      if (ngcCacheConfig.discoveryInstanceActive()) {
         if (!CollectionUtils.isEmpty(serviceInstances)) {
            for (ServiceInstance serviceInstance : serviceInstances) {
               URI uri = serviceInstance.getUri();
               String serviceURI = String.format("%s://%s:%s%s", uri.getScheme(), uri.getHost(), uri.getPort(),
                     ServiceClientConstants.EVICT_SHIPMENT_DATE_URI);
               cachingService(shipmentNumber, serviceURI);
            }
         }
      } else {
         if (!ngcCacheConfig.nonDiscoveryInstanceActive()) {
            cachingService(shipmentNumber, ngcCacheConfig.shipmentDateCacheEvictionAPI());
         }
      }
   }
   
   /**
    * Cache Refresh
    * 
    * @param payload
    */
   public void refresh(List<ShipmentDateCache> cachedShipmentDates) {
      LOGGER.info("Shipment Date Cache Update Service :: refresh cache data {}");
      if (!ngcCacheConfig.shipmentDateCacheAccessEnabled() || ngcCacheConfig.devInstanceActive()) {
         return;
      }
      List<ServiceInstance> serviceInstances = Collections.emptyList();
      if (ngcCacheConfig.discoveryInstanceActive()) {
         serviceInstances = discoveryClient.getInstances(ServiceClientConstants.CACHE_PROVIDER);
      }
      for (ShipmentDateCache shipmentDateCache : cachedShipmentDates) {
         LOGGER.info("Shipment Date Cache Update Service :: Shipment Payload - {}", shipmentDateCache.toString());
         if (ngcCacheConfig.discoveryInstanceActive()) {
            if (!CollectionUtils.isEmpty(serviceInstances)) {
               for (ServiceInstance serviceInstance : serviceInstances) {
                  URI uri = serviceInstance.getUri();
                  String serviceURI = String.format("%s://%s:%s%s", uri.getScheme(), uri.getHost(), uri.getPort(),
                        ServiceClientConstants.EVICT_SHIPMENT_DATE_URI);
                  cachingService(shipmentDateCache.getShipmentNumber(), serviceURI);
               }
            }
         } else {
            if (!ngcCacheConfig.nonDiscoveryInstanceActive()) {
               cachingService(shipmentDateCache.getShipmentNumber(), ngcCacheConfig.shipmentDateCacheEvictionAPI());
            }
         }
      }
   }
   
   /**
    * Caching Service
    * 
    * @param payload
    * @param serviceURI
    */
   private void cachingService(Object payload, String serviceURI) {
      try {
         RestTemplate restTemplate = CosysApplicationContext.getRestTemplate();
         ResponseEntity<Void> response = restTemplate.exchange(serviceURI, HttpMethod.POST,
               getServicePayload(payload, MediaType.APPLICATION_JSON), Void.class);
         LOGGER.info("Shipment Date Cache Update Service :: Http Response Status code - {}", response.getStatusCode());
      } catch (Exception ex) {
         LOGGER.error("Shipment Date Cache Update Service :: Exception occured : Payload - {} and stack trace - {}",
               String.valueOf(payload), ex);
      }
   }

   /**
    * @param payload
    * @param mediaType
    * @return
    */
   private static HttpEntity<Object> getServicePayload(Object payload, MediaType mediaType) {
      final HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Arrays.asList(mediaType));
      headers.setContentType(mediaType);
      // Set Message Header here
      if (Objects.nonNull(TenantContext.get())) {
         headers.set(TenantContextConstants.TENANT_ID, TenantContext.get().getTenantId());
         headers.set(TenantContextConstants.AUTH_USER_ID, TenantContext.get().getRequestUser());
         headers.set(TenantContextConstants.AUTH_USER_TYPE, TenantContext.get().getUserType());
         headers.set(TenantContextConstants.SECTOR_ID, TenantContext.get().getSectorId());
         headers.set(TenantContextConstants.TERMINAL_ID, TenantContext.get().getTerminal());
      }
      // Tenant specific cache so wrapping in Object Payload
      NGCCacheRequest cachePayload = new NGCCacheRequest();
      cachePayload.setShipmentNumber(String.valueOf(payload));
      //
      return new HttpEntity<>(cachePayload, headers);
   }
   
}
