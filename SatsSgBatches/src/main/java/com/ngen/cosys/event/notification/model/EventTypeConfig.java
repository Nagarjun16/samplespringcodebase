/**
 * EventTypeConfig.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used for Event Type config
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class EventTypeConfig extends BaseBO {

   /**
    * Default serialVersionUID 
    */
   private static final long serialVersionUID = 1L;
   //
   private BigInteger eventTypeId;
   private String module;
   private String entity;
   private String eventName;
   private boolean active;
   //
   private String eventModule;
   
}
