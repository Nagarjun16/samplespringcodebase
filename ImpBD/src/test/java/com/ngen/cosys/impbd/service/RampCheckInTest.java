/*package com.ngen.cosys.impbd.service;

import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.controller.InboundRampCheckInController;
import com.ngen.cosys.impbd.controller.IncomingFlightsController;
import com.ngen.cosys.impbd.model.RampCheckInDetails;
import com.ngen.cosys.impbd.model.RampCheckInPiggyback;
import com.ngen.cosys.impbd.model.RampCheckInSHC;
import com.ngen.cosys.impbd.model.RampCheckInSearchFlight;
import com.ngen.cosys.impbd.model.RampCheckInUld;

import io.swagger.annotations.ApiOperation;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = {"/tddscripts/impbd-inbound-ramp-check-in-schema-tdd-h2.sql", "/tddscripts/impbd-inbound-ramp-check-in-data-tdd-h2.sql"})
public class RampCheckInTest {

   @Autowired
   private InboundRampCheckInController rcc;
   
   @Autowired
   private IncomingFlightsController ifc;
   
   @Autowired
   private IncomingFlightService ifs;

   @Before
   public void setUp() {

   }

   @Test
   public void fetchRampCheckIn() throws CustomException {
      RampCheckInSearchFlight rampCheckinQuery = new RampCheckInSearchFlight();
      rampCheckinQuery.setFlight("SQ202");
      rampCheckinQuery.setFlightDate(LocalDate.now());
      BaseResponse<RampCheckInDetails> rampCheckInData = rcc.search(rampCheckinQuery);
      assertNotNull(rampCheckInData);
   }

   @Test
   public void createRampCheckInUld() throws CustomException {

      RampCheckInUld rampCheckinQuery = new RampCheckInUld();
      RampCheckInSHC shc = new RampCheckInSHC();
      shc.setShc("VAL");
      List<RampCheckInSHC> shcs = new ArrayList<RampCheckInSHC>();
      shcs.add(shc);
      rampCheckinQuery.setUldNumber("AKE0007");
      rampCheckinQuery.setFlightId(new BigInteger("1"));
      rampCheckinQuery.setContentCode("C");
      rampCheckinQuery.setManual(true);
      rampCheckinQuery.setTransferType("TT");
      rampCheckinQuery.setShcs(shcs);
  //    BaseResponse<RampCheckInUld> rampCheckInData = rcc.createUld(rampCheckinQuery);
    //  assertNotNull(rampCheckInData);
   }

   @Test
   public void deleteRampCheckInUld() throws CustomException {

      List<RampCheckInUld> deleteUld = new ArrayList<RampCheckInUld>();
  //    createRampCheckInUld();
      RampCheckInUld existingUld = new RampCheckInUld();
      RampCheckInSHC shc = new RampCheckInSHC();
      shc.setShc("VAL");
      List<RampCheckInSHC> shcs = new ArrayList<RampCheckInSHC>();
      shcs.add(shc);
      existingUld.setImpRampCheckInId(new BigInteger("1"));
      existingUld.setUldNumber("AKE0007");
      existingUld.setContentCode("X");
      existingUld.setManual(true);
      existingUld.setTransferType("TT");
      existingUld.setShcs(shcs);
      
  //    deleteUld.add(existingUld);
  //    BaseResponse<List<RampCheckInUld>> rampCheckInData = rcc.deleteUld(deleteUld);
  //    assertNotNull(rampCheckInData);
   }


   @Test
   public void updateRampCheckInUld() throws CustomException {

      List<RampCheckInUld> updateUld = new ArrayList<RampCheckInUld>();
    //  createRampCheckInUld();
      RampCheckInUld existingUld = new RampCheckInUld();
      RampCheckInSHC shc = new RampCheckInSHC();
      shc.setShc("VAL");
      List<RampCheckInSHC> shcs = new ArrayList<RampCheckInSHC>();
      shcs.add(shc);
      existingUld.setImpRampCheckInId(new BigInteger("1"));
      existingUld.setUldNumber("AKE0007");
      existingUld.setContentCode("X");
      existingUld.setManual(true);
      existingUld.setTransferType("TT");
      existingUld.setShcs(shcs);
      updateUld.add(existingUld);
   //   BaseResponse<List<RampCheckInUld>> rampCheckInData = rcc.updateData(updateUld);
   //   assertNotNull(rampCheckInData);
   }

   @Test
   public void createPiggyback() throws CustomException {
      RampCheckInPiggyback piggyback = new RampCheckInPiggyback();
      List<RampCheckInPiggyback> piggybacks = new ArrayList<RampCheckInPiggyback>();
      piggyback.setFlagCRUD("C");
      piggyback.setUldNumber("AKE1986DHL");
      piggyback.setImpRampCheckInId(new BigInteger("1"));
      piggybacks.add(piggyback);
   //   BaseResponse<List<RampCheckInPiggyback>> rampCheckInData = rcc.maintainPiggyback(piggybacks);
    //  assertNotNull(rampCheckInData);
   }

   @Test
   public void updatePiggyback() throws CustomException {
      RampCheckInPiggyback piggyback = new RampCheckInPiggyback();
      List<RampCheckInPiggyback> piggybacks = new ArrayList<RampCheckInPiggyback>();
      piggyback.setFlagCRUD("U");
      piggyback.setUldNumber("AKE1986SQ");
      piggyback.setImpRampCheckInId(new BigInteger("1"));
      piggybacks.add(piggyback);
  //    createPiggyback();
   //   BaseResponse<List<RampCheckInPiggyback>> rampCheckInData = rcc.maintainPiggyback(piggybacks);
    //  assertNotNull(rampCheckInData);
   }

   @Test
   public void deletePiggyback() throws CustomException {
      RampCheckInPiggyback piggyback = new RampCheckInPiggyback();
      List<RampCheckInPiggyback> piggybacks = new ArrayList<RampCheckInPiggyback>();
      piggyback.setFlagCRUD("D");
      piggyback.setUldNumber("AKE1986SQ");
      piggyback.setImpRampCheckInId(new BigInteger("1"));
      piggybacks.add(piggyback);
    //  createPiggyback();
    //  BaseResponse<List<RampCheckInPiggyback>> rampCheckInData = rcc.maintainPiggyback(piggybacks);
    //  assertNotNull(rampCheckInData);
   }

   @Test
   public void featchPiggyback() throws CustomException {
      RampCheckInPiggyback piggyback = new RampCheckInPiggyback();
      piggyback.setUldNumber("AKE1986DHL");
      piggyback.setImpRampCheckInId(new BigInteger("1"));
    //  createPiggyback();
    //  BaseResponse<List<RampCheckInPiggyback>> rampCheckInData = rcc.getPiggyback(piggyback);
    //  assertNotNull(rampCheckInData);
   }
   
//   @Test
//   public void assignDriver() throws CustomException {
//
//       RampCheckInUld uld = new RampCheckInUld();
//       RampCheckInSHC shc = new RampCheckInSHC();
//       shc.setShc("VAL");
//       List<RampCheckInSHC> shcs = new ArrayList<RampCheckInSHC>();
//       shcs.add(shc);
//       uld.setUldNumber("AKE0007");
//       uld.setContentCode("C");
//       uld.setManual(true);
//       uld.setTransferType("TT");
//       uld.setShcs(shcs);
//       uld.setDriverId("100");
//       List<RampCheckInUld> ulds = new ArrayList<RampCheckInUld>();
//       ulds.add(uld);
//      createRampCheckInUld();
//      BaseResponse<List<RampCheckInUld>> rampCheckInData = rcc.updatedriver(ulds);
//      assertNotNull(rampCheckInData);
//   }
   
   
}
*/