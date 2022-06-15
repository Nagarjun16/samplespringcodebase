/**
 * 
 * HandoverInboundUldTrollyServiceImplTest.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 24 January, 2018 NIIT -
 *//*
package com.ngen.cosys.impbd.service;

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
import com.ngen.cosys.impbd.dao.HandoverInboundUldTrollyDao;
import com.ngen.cosys.impbd.dao.HandoverInboundUldTrollyDaoImpl;
import com.ngen.cosys.impbd.model.HandoverInboundContainerTrolly;
import com.ngen.cosys.impbd.model.HandoverInboundTrolly;
import com.ngen.cosys.model.FlightModel;

*//**
 * This class is Unit Testing for HandoverInboundServiceImplTest
 * Implementation.
 * 
 * @author NIIT Technologies Ltd.
 *
 *//*
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImpBdApplication.class)
@ActiveProfiles("test")
public class HandoverInboundServiceImpITest {
   @Autowired
   private HandoverInboundUldTrollyDaoImpl handoverInboundUldTrollyDaoImpl;
   @Autowired
   private HandoverInboundUldTrollyDao handoverInboundUldTrollyDao;
  // @Autowired
  // InboundFlightArrivalStoreEventProducer producer;
   @Before
   public void setup() {
      assertNotNull(this);
   }
   @Test
   @Sql(scripts = { "/tddscripts/impbd-handover-schema-tdd-h2.sql", "/tddscripts/impbd-handover-data-tdd-h2.sql" })
   public void testfetchInboundTrolly() throws CustomException {
      FlightModel flightModel = new FlightModel();
      flightModel.setFlightNumber("12H0012");
      flightModel.setFlightDate(LocalDate.now());
      List<HandoverInboundTrolly> data = handoverInboundUldTrollyDao.fetchInboundTrolly(flightModel);
      assertEquals(1,data.size());
   }

   @Test
   @Sql(scripts = { "/tddscripts/impbd-handover-schema-tdd-h2.sql", "/tddscripts/impbd-handover-data-tdd-h2.sql" })
   public void testfetchInbpoundTrollywithexception() throws CustomException {
      FlightModel flightModel = new FlightModel();
      flightModel.setFlightNumber("12H0013");
      flightModel.setFlightDate(LocalDate.now());
      List<HandoverInboundTrolly> data = handoverInboundUldTrollyDaoImpl.fetchInboundTrolly(flightModel);
      //assertNull(null);
      assertEquals(0,data.size());
   }

   @Test
   @Sql(scripts = { "/tddscripts/impbd-handover-schema-tdd-h2.sql", "/tddscripts/impbd-handover-data-tdd-h2.sql" })
   public void testinsertInbpoundTrolly() throws CustomException {
      HandoverInboundTrolly handoverTrolly = new HandoverInboundTrolly();
      handoverTrolly.setFlightNumber("12H0013");
      handoverTrolly.setFlightDate(LocalDate.now());
      handoverTrolly.setFlightId(new BigInteger("10"));
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
      HandoverInboundTrolly handoverFlightId = handoverInboundUldTrollyDao.getFlightId(handoverTrolly);
      HandoverInboundTrolly tripDetails = handoverInboundUldTrollyDao.tripDetails(handoverTrolly);
      List<HandoverInboundTrolly> data = handoverInboundUldTrollyDaoImpl.insertInboundTrolly(handoverTrolly);
      assertNotNull(data);
   }

   @Test
   @Sql(scripts = { "/tddscripts/impbd-handover-schema-tdd-h2.sql", "/tddscripts/impbd-handover-data-tdd-h2.sql" })
   public void testaddUpdateTrolly() throws CustomException {
      HandoverInboundTrolly handoverTrolly = new HandoverInboundTrolly();
      handoverTrolly.setFlightId(new BigInteger("10"));
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
      List<HandoverInboundTrolly> data = handoverInboundUldTrollyDaoImpl.updateInboundTrolly(handoverTrolly);
      assertNotNull(data);

   }

   @Test
   @Sql(scripts = { "/tddscripts/impbd-handover-schema-tdd-h2.sql", "/tddscripts/impbd-handover-data-tdd-h2.sql" })
   public void testdeleteTrollyNo() throws CustomException {
      HandoverInboundContainerTrolly handoverTrolly = new HandoverInboundContainerTrolly();
      handoverTrolly.setContainerTrolleyNumber("AKE88889SQ");
      List<HandoverInboundContainerTrolly> data = handoverInboundUldTrollyDaoImpl.deleteTrollyNo(handoverTrolly);
      assertNotNull(data);
   }

   @Test
   @Sql(scripts = { "/tddscripts/impbd-handover-schema-tdd-h2.sql", "/tddscripts/impbd-handover-data-tdd-h2.sql" })
   public void testeditInboundTrolly() throws CustomException {
      HandoverInboundTrolly handoverTrolly = new HandoverInboundTrolly();
      handoverTrolly.setImpHandOverId(new BigInteger("294"));
      handoverTrolly.setHandedOverBy("EE");
      handoverTrolly.setHandedOverArea("EEE");
      List<HandoverInboundTrolly> data = handoverInboundUldTrollyDaoImpl.editInboundTrolly(handoverTrolly);
      assertNotNull(data);
   }
@Test
@Sql(scripts = { "/tddscripts/impbd-handover-schema-tdd-h2.sql", "/tddscripts/impbd-handover-data-tdd-h2.sql" })
public void testinsertData() throws CustomException {
   HandoverInboundContainerTrolly handoverTrollys = new HandoverInboundContainerTrolly();
   handoverTrollys.setImpHandOverId(new BigInteger("294"));
   handoverTrollys.setImpHandOverContainerTrolleyInformationId(new BigInteger("2"));
   handoverTrollys.setContainerTrolleyNumber("AKE88889SQ");
   handoverTrollys.setUsedAsTrolley(new Boolean("0"));
   handoverTrollys.setCapturedManual(new Boolean("0"));
   handoverTrollys.setSourceOfInformation("ABC");
   handoverTrollys.setCreatedBy("SYSADMIN");
   List<HandoverInboundContainerTrolly> handoverList = handoverInboundUldTrollyDao.searchContainerTrollyId(handoverTrollys);
   HandoverInboundContainerTrolly data = handoverInboundUldTrollyDaoImpl.insertContainerTrolly(handoverTrollys);
   assertNotNull(data);
}
@Test
@Sql(scripts = { "/tddscripts/impbd-handover-schema-tdd-h2.sql", "/tddscripts/impbd-handover-data-tdd-h2.sql" })
public void testinsertDatawithexception() throws CustomException {
   HandoverInboundContainerTrolly handoverTrollys = new HandoverInboundContainerTrolly();
   handoverTrollys.setImpHandOverId(new BigInteger("294"));
   handoverTrollys.setImpHandOverContainerTrolleyInformationId(new BigInteger("2"));
   handoverTrollys.setContainerTrolleyNumber("AKE88889SQ");
   handoverTrollys.setUsedAsTrolley(new Boolean("0"));
   handoverTrollys.setCapturedManual(new Boolean("0"));
   handoverTrollys.setSourceOfInformation("ABC");
   handoverTrollys.setCreatedBy("SYSADMIN");
   HandoverInboundContainerTrolly data = handoverInboundUldTrollyDaoImpl.updateContainerTrolly(handoverTrollys);
   assertNotNull(data);
}


}
*/