/**
 * HandoverInboundTrollyTest.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0         3 FEB, 2018 NIIT      -
 *//*
package com.ngen.cosys.impbd.model;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;

*//**
 * This class is Unit Testing for HandoverInboundTrolly  model class.
 * 
 * @author NIIT Technologies Ltd.
 *
 *//*
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class HandoverInboundTrollyTest {
   @Test
   public void testHandoverInboundTrollyRequest() {
      HandoverInboundTrolly handoverInboundTrolly = new HandoverInboundTrolly();
      handoverInboundTrolly.setImpHandOverId("1");
      handoverInboundTrolly.setFlightId("SQ0502");
      handoverInboundTrolly.setHandedOverAt("1");
      handoverInboundTrolly.setTractorNumber("123");
      handoverInboundTrolly.setHandedOverBy("ABC");
      handoverInboundTrolly.setStartedAt("28JAN2018");
      handoverInboundTrolly.setCompletedAt("28JAN2018");
      handoverInboundTrolly.setCountofTrolleys("1");
      assertNotNull(handoverInboundTrolly.getImpHandOverId());
      assertNotNull(handoverInboundTrolly.getFlightId());
      assertNotNull(handoverInboundTrolly.getHandedOverAt());
      assertNotNull(handoverInboundTrolly.getTractorNumber());
      assertNotNull(handoverInboundTrolly.getHandedOverBy());
      assertNotNull(handoverInboundTrolly.getStartedAt());
      assertNotNull(handoverInboundTrolly.getCompletedAt());
      assertNotNull(handoverInboundTrolly.getCountofTrolleys());
      assertNotEquals(handoverInboundTrolly.toString(),"handoverInboundTrolly(active=y, code=Base, flgAct=false, flagdelete=yes,flagInsert=no,FlagSaved=yes,FlagUpdate=no,lastModifiedBy=sin, Value=sin, purpose=sin,name=sin,lastModifiedDate=null)");
      assertNotNull(handoverInboundTrolly);
      HandoverInboundTrolly handoverInboundTrolly1=new HandoverInboundTrolly("SQ0502","1","123","abc","28JAN2018","28JAN2018", "28JAN2018", null, null);
      
      assertNotEquals(handoverInboundTrolly,handoverInboundTrolly1);
      assertNotEquals(handoverInboundTrolly.hashCode(), handoverInboundTrolly1.hashCode());
      
      
      
   }
}
*/