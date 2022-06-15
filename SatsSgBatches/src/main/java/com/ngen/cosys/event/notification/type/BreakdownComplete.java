/**
 * BreakdownComplete.java
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
 * This interface is used for breakdown complete design pattern
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface BreakdownComplete extends EventNotificationConfiguration {

   EventEntity entity = EventEntity.AWB;
   EntityEventTypes eventType = EntityEventTypes.AWB_INBOUND_BREAKDOWN_COMPLETE;
   
   /**
    * @throws CustomException
    */
   void breakdownCompleteTesting() throws CustomException;
   
   /**
    * @param eventNotificationModel
    * @throws CustomException
    */
   void breakdownCompleteEvent(EventNotificationModel eventNotificationModel) throws CustomException;
   
}
