/**
 * HandoverInboundContainerTrollyTest.java
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
public class HandoverInboundContainerTrollyTest {
   @Test
   public void testHandoverInboundTrollyRequest() {
      HandoverInboundContainerTrolly handoverTrolly = new HandoverInboundContainerTrolly();
      handoverTrolly.setImpHandOverId("1");
      handoverTrolly.setImpHandOverContainerTrolleyInformationId("1");
      handoverTrolly.setContainerTrolleyNumber("AKE88889SQ");
      handoverTrolly.setUsedAsTrolley("Y");
      handoverTrolly.setCapturedManual("1");
      handoverTrolly.setSourceOfInformation("ABC");
      assertNotNull(handoverTrolly.getImpHandOverId());
      assertNotNull(handoverTrolly.getImpHandOverContainerTrolleyInformationId());
      assertNotNull(handoverTrolly.getContainerTrolleyNumber());
      assertNotNull(handoverTrolly.getUsedAsTrolley());
      assertNotNull(handoverTrolly.getCapturedManual());
      assertNotNull(handoverTrolly.getSourceOfInformation());
      assertNotEquals(handoverTrolly.toString(),"handoverTrolly(active=y, code=Base, flgAct=false, flagdelete=yes,flagInsert=no,FlagSaved=yes,FlagUpdate=no,lastModifiedBy=sin, Value=sin, purpose=sin,name=sin,lastModifiedDate=null)");
      assertNotNull(handoverTrolly);
      HandoverInboundContainerTrolly handoverTrolly1=new HandoverInboundContainerTrolly("1","1","AKE88889SQ","Y","1","ABC" );
      
      assertNotEquals(handoverTrolly,handoverTrolly1);
      assertNotEquals(handoverTrolly.hashCode(), handoverTrolly1.hashCode());
      
      
      
   }
}
*/