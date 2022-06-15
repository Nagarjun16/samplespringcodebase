/*package com.ngen.cosys.shipment.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;

*//**
 * test case of model of Maintaining Shipment Irregularities
 * 
 * @author NIIT technologies
 *
 *//*
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class IrregularitySummaryTest {

   @InjectMocks
   private IrregularitySummary shipmentIrregularity;
   
   @InjectMocks
   private IrregularityDetail irregularityDetails;

   *//**
    * test Maintain Shipment Irregularity Model
    *//*
   @Test
   public void testShipmentIrregularityModel() {
	   List<String> shc = new ArrayList<>();
	      shc.add("EAT");
	      shc.add("PER EAT");
      shipmentIrregularity.setShipmentId("123");
      shipmentIrregularity.setShipmentType("AWB Number");
      shipmentIrregularity.setShipmentNumber("816-88198989");
      shipmentIrregularity.setOrigin("BKK");
      shipmentIrregularity.setDestination("SIN");
      shipmentIrregularity.setPieces(10);
      shipmentIrregularity.setWeight(100.0);
      shipmentIrregularity.setNatureOfGoods("EATABLES");
      shipmentIrregularity.setSpecialHandlingCodes(shc);
      irregularityDetails.setIrregularityType("Found Cargo (FDCA)");
      irregularityDetails.setPieces(new BigInteger("2"));
      irregularityDetails.setWeight(new BigDecimal("10.0"));
      irregularityDetails.setSequenceNumber(1);
      irregularityDetails.setFlightKey("SQ101");
      irregularityDetails.setFlightDate(LocalDateTime.of(2018-01-01,1,1,1,1));
      irregularityDetails.setRemark(null);
      irregularityDetails.setFdlSentFlag(true);
      irregularityDetails.setCreatedBy("NIIT");
      assertNotNull(shipmentIrregularity.getShipmentType());
      assertNotNull(shipmentIrregularity.getSpecialHandlingCodes());
      assertNotNull(irregularityDetails.getCreatedBy());
      
      IrregularitySummary maintain = new IrregularitySummary();
      maintain.setDestination("SIN");
      maintain.setPieces(10);
      assertFalse(maintain.equals(shipmentIrregularity));
      assertEquals("SIN", maintain.getDestination());
      assertNotNull(maintain.getPieces());
      assertNotEquals(shipmentIrregularity, maintain);
      assertNotEquals(shipmentIrregularity.hashCode(), maintain.hashCode());
      assertNotEquals("MaintainShipmentIrregularity(shipmentId=null, selectShipmentType=null, shipmentNumber=null, origin=null, destination=SIN, pieces=10, weight=0.0, natureOfGoods=null, specialHandlingCode=null, irrDetails=null)", maintain.toString());
      
      List<IrregularityDetail> irrDetails = new ArrayList<IrregularityDetail>();
      irrDetails.add(irregularityDetails);
      shipmentIrregularity.setIrregularityDetails(irrDetails);
      
      IrregularitySummary maintain2 = new IrregularitySummary("123", "AWB Number", "816-88198989", "BKK", "SIN", 10, 100.0, "EATABLES", shc, irrDetails, 1);
      assertNotEquals(shipmentIrregularity, maintain2);
      assertFalse(shipmentIrregularity.equals(maintain2));
      assertNotEquals(shipmentIrregularity.hashCode(), maintain2.hashCode());
      assertFalse(maintain2.equals(shipmentIrregularity));
      assertFalse(maintain.equals(maintain2));
      assertNotEquals("MaintainShipmentIrregularity(shipmentId=123, selectShipmentType=AWB Number, shipmentNumber=816-88198989, origin=BKK, destination=SIN, pieces=10, weight=100.0, natureOfGoods=EATABLES, specialHandlingCode=[PER EAT, EAT], irrDetails=[IrregularityDetails(shipmentId=123, selectIrregularityType=Found Cargo (FDCA), pieces=2, weight=90.0, flightNumber=SQ101, flightDate=2018-01-03, remarks=null, fdlSent=true, user=NIIT, createDate=null)])", maintain2.toString());
      assertFalse(maintain2.equals(new IrregularitySummary("123", "AWB Number", "816-88198989", "BKK", "SIN", 10, 100.0, "EATABLES", shc, irrDetails, 1)));
      
      assertNotEquals(maintain, maintain2);
      assertNotEquals(maintain.hashCode(), maintain2.hashCode());
      
      IrregularitySummary maintain3 = new IrregularitySummary("123", "AWB Number", "816-88198989", "BKK", "SIN", 10, 100.0, "EATABLES", shc, irrDetails, 1);
      assertNotEquals(maintain2, maintain3);
      assertNotEquals(maintain2.hashCode(), maintain3.hashCode());
   }
   
   *//**
    * test Irregularity Details Model
    *//*
   @Test
   public void testIrregularityDetailsModel() {
      IrregularityDetail irregularDetails = new IrregularityDetail();
      irregularDetails.setFdlSentFlag(true);
      irregularDetails.setFlightDate(LocalDateTime.of(2018-01-01,1,1,1,1));
      irregularDetails.setShipmentNumber("1234");
      
      IrregularityDetail irregularDetails2 = new IrregularityDetail();
      assertNotEquals(irregularDetails, irregularDetails2);
      assertNotEquals(irregularDetails.hashCode(), irregularDetails2.hashCode());
      
      IrregularityDetail irregularDetails3 = new IrregularityDetail();
      assertNotEquals(irregularDetails2, irregularDetails3);
      assertFalse(irregularDetails2.equals(irregularDetails3));
      assertFalse(irregularDetails3.equals(irregularDetails));
   }
   
   *//**
    * test Search Shipment Irregularity Model
    *//*
   @Test
   public void testSearchShipmentIrregularityModel() {
      SearchShipmentIrregularity searchShipmentIrregularity = new SearchShipmentIrregularity();
      searchShipmentIrregularity.setShipmentType("AWB");
      searchShipmentIrregularity.setShipmentNumber("123");
      
      assertNotNull(searchShipmentIrregularity.getShipmentType());
      assertNotNull(searchShipmentIrregularity.getShipmentNumber());
      
      SearchShipmentIrregularity searchShipmentIrregularity2 = new SearchShipmentIrregularity("AWB","123", LocalDate.now());
      assertNotEquals(searchShipmentIrregularity, searchShipmentIrregularity2);
      assertNotEquals(searchShipmentIrregularity.hashCode(), searchShipmentIrregularity2.hashCode());
      
      SearchShipmentIrregularity searchShipmentIrregularity3 = new SearchShipmentIrregularity("AWB","123", LocalDate.now());
      assertNotEquals(searchShipmentIrregularity2, searchShipmentIrregularity3);
      assertFalse(searchShipmentIrregularity2.equals(searchShipmentIrregularity3));
      assertFalse(searchShipmentIrregularity3.equals(searchShipmentIrregularity));
   }
}
*/