/**
 * InboundFlightComplete.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.type;

import com.ngen.cosys.event.notification.enums.EntityEventTypes;
import com.ngen.cosys.event.notification.enums.EventEntity;
import com.ngen.cosys.event.notification.model.EventNotificationModel;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * This interface is used for inbound flight close design pattern
 * 
 * @author NIIT Technologies Ltd
 */
public interface InboundFlightComplete extends EventNotificationConfiguration {

   EventEntity entity = EventEntity.FLIGHT;
   EntityEventTypes eventType = EntityEventTypes.FLIGHT_INBOUND_COMPLETE;
   
   /**
    * @param eventNotificationModel
    * @throws CustomException
    */
   void flightCompleteEvent(EventNotificationModel eventNotificationModel) throws CustomException;
   
}
