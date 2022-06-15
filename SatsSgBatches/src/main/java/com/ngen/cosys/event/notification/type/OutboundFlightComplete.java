/**
 * OutboundFlightComplete.java
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
 * This interface is used for Outbound Flight complete design pattern
 * 
 * @author NIIT Technologies Ltd
 */
public interface OutboundFlightComplete extends EventNotificationConfiguration {

   EventEntity entity = EventEntity.FLIGHT;
   EntityEventTypes eventType = EntityEventTypes.FLIGHT_OUTBOUND_COMPLETE;
   
   /**
    * @param eventNotificationModel
    * @throws CustomException
    */
   void flightCompleteEvent(EventNotificationModel eventNotificationModel) throws CustomException;
   
}
