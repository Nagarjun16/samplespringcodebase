/**
 * {@link IVRSController}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.events.payload.ShipmentNotification;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.service.ivrs.model.IVRSAWBRequest;
import com.ngen.cosys.service.ivrs.model.IVRSAWBResponse;
import com.ngen.cosys.service.ivrs.model.IVRSDataResponse;
import com.ngen.cosys.service.ivrs.model.IVRSResponse;
import com.ngen.cosys.service.ivrs.service.IVRSInterfaceService;

import io.swagger.annotations.ApiOperation;

/**
 * IVRS Controller
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NgenCosysAppInfraAnnotation(path = "/api/ivrs")
public class IVRSController {

   private static final Logger LOGGER = LoggerFactory.getLogger(IVRSController.class);
   
   @Autowired
   IVRSInterfaceService ivrsInterfaceService;
   
   /**
    * Send Arrival Notification of a shipment
    * 
    * @param shipmentNotification
    * @return
    * @throws CustomException
    */
   @ApiOperation("Notify Arrival Of a Shipment")
   @PostRequest(method = RequestMethod.POST, value = "/shipment-arrival-notification")
   public ResponseEntity<Void> notifyArrivalOfShipment(@RequestBody ShipmentNotification shipmentNotification)
         throws CustomException {
      LOGGER.info("IVRS Controller :: Notify Arrival of a Shipment - {}", shipmentNotification);
      ivrsInterfaceService.notifyArrivalOfShipment(shipmentNotification);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
   
   /**
    * Send AirWayBill FAX Copy
    * 
    * @param shipmentNotification
    * @return
    * @throws CustomException
    */
   @ApiOperation("Send AirWayBill Fax Copy")
   @PostRequest(method = RequestMethod.POST, value = "/send/awb-fax-copy")
   public ResponseEntity<Void> sendAirWayBillFaxCopy(@RequestBody ShipmentNotification shipmentNotification)
         throws CustomException {
      LOGGER.info("IVRS Controller :: Send AirWayBill Fax Copy - {}", shipmentNotification);
      ivrsInterfaceService.sendAirWayBillFaxCopy(shipmentNotification);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
   
   /**
    * IVRS/FAX Status Acknowledgement
    * 
    * @param dataResponse
    * @return
    */
   @ApiOperation("Acknowledgement status response for IVRS/FAX Requests")
   @PostRequest(method = RequestMethod.POST, value = "/status/ivrs-fax-acknowledgement")
   public ResponseEntity<IVRSResponse> acknowledgementResponse(@RequestBody IVRSDataResponse dataResponse)
         throws CustomException {
      LOGGER.info("IVRS Controller :: IVRS/FAX acknowledgement response - {}", dataResponse);
      IVRSResponse response = ivrsInterfaceService.acknowledgementStatusUpdate(dataResponse);
      return new ResponseEntity<>(response, HttpStatus.OK);
   }
   
   /**
    * Enquire AirWayBill Details
    * 
    * @param dataRequest
    * @return
    */
   @ApiOperation("Enquire AirWayBill Details by IVRS System")
   @PostRequest(method = RequestMethod.POST, value = "/enquire/ivrs-awb-data")
   public ResponseEntity<IVRSAWBResponse> enquireAirWayBillDetails(@RequestBody IVRSAWBRequest dataRequest)
         throws CustomException {
      LOGGER.info("IVRS Controller :: Enquire AirWayBill Details - {}", dataRequest);
      IVRSAWBResponse dataResponse = ivrsInterfaceService.enquireAirWayBillDetail(dataRequest);
      return new ResponseEntity<>(dataResponse, HttpStatus.OK);
   }
   
}
