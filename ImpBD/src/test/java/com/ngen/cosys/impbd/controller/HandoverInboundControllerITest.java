/**
 * 
 * HandoverInboundControllerlTest.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 24 January, 2018 NIIT -
 *//*
package com.ngen.cosys.impbd.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.ngen.cosys.ImpBdApplication;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.HandoverInboundContainerTrolly;
import com.ngen.cosys.impbd.model.HandoverInboundTrolly;
import com.ngen.cosys.impbd.service.HandoverInboundUldTrollyService;
import com.ngen.cosys.model.FlightModel;

*//**
 * This class is Unit Testing for HandoverInboundControllerlTest
 * Implementation.
 * 
 * @author NIIT Technologies Ltd.
 *
 *//*
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImpBdApplication.class)
@ActiveProfiles("test")
public class HandoverInboundControllerITest {
   @Autowired
   HandoverInboundUldTrollyService handoverInboundUldTrollyService;  
   HandoverInboundTrolly handoverInboundTrolly;
   @Before
   public void setup() {
      handoverInboundTrolly = new HandoverInboundTrolly();
   }
   
   @Test
   @Sql(scripts = { "/tddscripts/impbd-handover-schema-tdd-h2.sql", "/tddscripts/impbd-handover-data-tdd-h2.sql" })
   public void testfetchInboundTrolly() throws CustomException {
      FlightModel flightModel = new FlightModel();
      flightModel.setFlightNumber("12H0012");
      flightModel.setFlightDate(LocalDate.now());
      List<HandoverInboundTrolly> data = handoverInboundUldTrollyService.fetchInboundTrolly(flightModel);
      assertNotNull(data);
   }
   @Test
   @Sql(scripts = { "/tddscripts/impbd-handover-schema-tdd-h2.sql", "/tddscripts/impbd-handover-data-tdd-h2.sql" })
   public void testfetchInboundTrollywithexception() throws CustomException {
      FlightModel flightModel = new FlightModel();
      flightModel.setFlightNumber("12H0012");
      flightModel.setFlightDate(LocalDate.now());
      List<HandoverInboundTrolly> data = handoverInboundUldTrollyService.fetchInboundTrolly(flightModel);
      assertEquals(0,data.size());
     // assertThat(data, IsEmptyCollection.empty());
   }

   @Test
   @Sql(scripts = { "/tddscripts/impbd-handover-schema-tdd-h2.sql", "/tddscripts/impbd-handover-data-tdd-h2.sql" })
   public void testinsertInbpoundTrolly() throws CustomException {
      HandoverInboundTrolly handoverTrolly = new HandoverInboundTrolly();
      handoverTrolly.setFlightNumber("12H0012");
      handoverTrolly.setFlightDate(LocalDate.now());
      handoverTrolly.setHandedOverArea("EEE");
      handoverTrolly.setTractorNumber("E");
      handoverTrolly.setHandedOverBy("EE");
      handoverTrolly.setStartedAt(null);
      handoverTrolly.setCompletedAt(null);
      handoverTrolly.setCreatedBy("SYSADMIN");
      HandoverInboundContainerTrolly handoverTrollys = new HandoverInboundContainerTrolly();
      handoverTrollys.setImpHandOverId(new BigInteger("294"));
      handoverTrollys.setImpHandOverContainerTrolleyInformationId(new BigInteger("2"));
      handoverTrollys.setContainerTrolleyNumber("AKE88889SQ");
      handoverTrollys.setUsedAsTrolley(new Boolean("0"));
      handoverTrollys.setCapturedManual(new Boolean("0"));
      handoverTrollys.setSourceOfInformation("ABC");
      List<HandoverInboundContainerTrolly> handoverTrollyList = new ArrayList<>();
      handoverTrollyList.add(handoverTrollys);
      handoverTrolly.setHandoverInboundContainerTrolly(handoverTrollyList);
      List<HandoverInboundTrolly> data = handoverInboundUldTrollyService.insertInboundTrolly(handoverTrolly);
      assertNotNull(data);
   }
   @Test
   @Sql(scripts = { "/tddscripts/impbd-handover-schema-tdd-h2.sql", "/tddscripts/impbd-handover-data-tdd-h2.sql" })
   public void testaddUpdateTrolly() throws CustomException {
      HandoverInboundTrolly handoverTrolly = new HandoverInboundTrolly();
      handoverTrolly.setFlightId(new BigInteger("10"));
      handoverTrolly.setImpHandOverId(new BigInteger("10"));
      handoverTrolly.setHandedOverArea("EEE");
      handoverTrolly.setTractorNumber("E");
      handoverTrolly.setHandedOverBy("EE");
      handoverTrolly.setStartedAt(null);
      handoverTrolly.setCompletedAt(null);
      handoverTrolly.setCreatedBy("SYSADMIN");
      HandoverInboundContainerTrolly handoverTrollys = new HandoverInboundContainerTrolly();
      handoverTrollys.setImpHandOverId(new BigInteger("294"));
      handoverTrollys.setImpHandOverContainerTrolleyInformationId(new BigInteger("2"));
      handoverTrollys.setContainerTrolleyNumber("AKE88889SQ");
      handoverTrollys.setUsedAsTrolley(new Boolean("0"));
      handoverTrollys.setCapturedManual(new Boolean("0"));
      handoverTrollys.setSourceOfInformation("ABC");
      List<HandoverInboundContainerTrolly> handoverTrollyList = new ArrayList<>();
      handoverTrollyList.add(handoverTrollys);
      handoverTrolly.setHandoverInboundContainerTrolly(handoverTrollyList);
      List<HandoverInboundTrolly> data = handoverInboundUldTrollyService.addUpdateTrolly(handoverTrolly);
      assertNotNull(data);

   }

   @Test
   @Sql(scripts = { "/tddscripts/impbd-handover-schema-tdd-h2.sql", "/tddscripts/impbd-handover-data-tdd-h2.sql" })
   public void testdeleteTrollyNo() throws CustomException {
      HandoverInboundContainerTrolly handoverTrolly = new HandoverInboundContainerTrolly();
      handoverTrolly.setContainerTrolleyNumber("AKE88889SQ");
      List<HandoverInboundContainerTrolly> data = handoverInboundUldTrollyService.deleteTrollyNo(handoverTrolly);
      assertNotNull(data);
   }
   @Test
   @Sql(scripts = { "/tddscripts/impbd-handover-schema-tdd-h2.sql", "/tddscripts/impbd-handover-data-tdd-h2.sql" })
   public void testeditInboundTrolly() throws CustomException {
      HandoverInboundTrolly handoverTrolly = new HandoverInboundTrolly();
      handoverTrolly.setImpHandOverId(new BigInteger("294"));
      handoverTrolly.setHandedOverBy("EE");
      handoverTrolly.setHandedOverArea("EEE");
      List<HandoverInboundTrolly> data = handoverInboundUldTrollyService.editInboundTrolly(handoverTrolly);
      assertNotNull(data);
   }
   
   
}
*/