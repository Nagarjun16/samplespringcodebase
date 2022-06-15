/**
 * {@link IVRSInterfaceService}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.service;

import com.ngen.cosys.events.payload.ShipmentNotification;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.service.ivrs.model.IVRSAWBRequest;
import com.ngen.cosys.service.ivrs.model.IVRSAWBResponse;
import com.ngen.cosys.service.ivrs.model.IVRSDataResponse;
import com.ngen.cosys.service.ivrs.model.IVRSResponse;

/**
 * IVRS Interface Service
 * 
 * @author NIIT Technologies Ltd
 */
public interface IVRSInterfaceService {

   /**
    * Notify Arrival Of a Shipment
    * 
    * @param shipmentNotification
    * @return
    * @throws CustomException
    */
   void notifyArrivalOfShipment(ShipmentNotification shipmentNotification) throws CustomException;
   
   /**
    * SEND AirWayBill Fax Copy
    * 
    * @param shipmentNotification
    * @throws CustomException
    */
   void sendAirWayBillFaxCopy(ShipmentNotification shipmentNotification) throws CustomException;
   
   /**
    * Acknowledgement STATUS Update
    * 
    * @param dataResponse
    * @return
    * @throws CustomException
    */
   IVRSResponse acknowledgementStatusUpdate(IVRSDataResponse dataResponse) throws CustomException;
   
   /**
    * Enquire AirWayBill Detail
    * 
    * @param dataRequest
    * @return
    * @throws CustomException
    */
   IVRSAWBResponse enquireAirWayBillDetail(IVRSAWBRequest dataRequest) throws CustomException;
   
}
