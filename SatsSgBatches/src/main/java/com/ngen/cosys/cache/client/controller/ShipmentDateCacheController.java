/**
 * {@link ShipmentDateCacheController}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.cache.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.cache.client.service.ShipmentDateCacheUpdateService;

import io.swagger.annotations.ApiOperation;

/**
 * Shipment Date Cache Controller
 *  - Refresh/cleanup shipment date cache
 * 
 * @author NIIT Technologies Ltd
 */
@NgenCosysAppInfraAnnotation(path = "/cache/shipment-date")
public class ShipmentDateCacheController {

   private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentDateCacheController.class);
   
   @Autowired
   ShipmentDateCacheUpdateService shipmentDateCacheUpdateService;
   
   @ApiOperation("Remove Shipment Date cache from all instances")
   @PostRequest(value = "/remove", method = RequestMethod.POST)
   public ResponseEntity<Void> removeShipmentDate(@RequestBody String shipmentNumber) throws Exception {
      LOGGER.info("Shipment Date Cache Controller :: Remove - Shipment Number - {}", shipmentNumber);
      shipmentDateCacheUpdateService.remove(shipmentNumber);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
   
}
