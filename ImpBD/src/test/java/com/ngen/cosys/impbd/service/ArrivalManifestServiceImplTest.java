/*package com.ngen.cosys.impbd.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.controller.ArrivalManifestController;
import com.ngen.cosys.impbd.model.ArrivalManifestByFlightModel;
import com.ngen.cosys.impbd.model.ArrivalManifestBySegmentModel;
import com.ngen.cosys.impbd.model.ArrivalManifestByShipmentShcModel;
import com.ngen.cosys.impbd.model.ArrivalManifestOtherServiceInfoModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentDimensionInfoModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentOciModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentOnwardMovementModel;
import com.ngen.cosys.impbd.model.ArrivalManifestUldModel;
import com.ngen.cosys.impbd.model.SearchArrivalManifestModel;



@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"/tddscripts/impbd-ArrivalManifest-schema-tdd-h2.sql","/tddscripts/impbd-ArrivalManifest-data-tdd-h2.sql"})
public class ArrivalManifestServiceImplTest {
   
   @Autowired
   ArrivalManifestController manifestController;
   
   
   public void fetchArrivalManifestDetails() throws CustomException{
      SearchArrivalManifestModel searchArrivalManifestModel = new SearchArrivalManifestModel();
      searchArrivalManifestModel.setFlightNumber("SQ1234");
      searchArrivalManifestModel.setFlightDate(LocalDate.of(2018, 01, 30));
      BaseResponse<ArrivalManifestByFlightModel> outputFlight =manifestController.fetchArrivalManifestDetails(searchArrivalManifestModel);
      assertNotNull(outputFlight.getData());      
   }
   
   
   public void createULDShipment()throws CustomException{
      ArrivalManifestByFlightModel arrivalFlightDetails = new ArrivalManifestByFlightModel();
      ArrivalManifestBySegmentModel segmentData = new ArrivalManifestBySegmentModel();
      ArrivalManifestUldModel uldData = new ArrivalManifestUldModel();
      ArrivalManifestShipmentInfoModel shipmentData = new ArrivalManifestShipmentInfoModel();
      ArrivalManifestByShipmentShcModel shcData = new ArrivalManifestByShipmentShcModel();
      List<ArrivalManifestBySegmentModel> segmentInfo = new ArrayList<ArrivalManifestBySegmentModel>();
      List<ArrivalManifestUldModel> uldInfo = new ArrayList<ArrivalManifestUldModel>();
      List<ArrivalManifestShipmentInfoModel> shipmentInfo = new ArrayList<ArrivalManifestShipmentInfoModel>();
      List<ArrivalManifestByShipmentShcModel> shcInfo = new ArrayList<ArrivalManifestByShipmentShcModel>();      
      arrivalFlightDetails.setAircraftRegCode("12134");
      arrivalFlightDetails.setFlightId(new BigInteger("1"));
      arrivalFlightDetails.setFlagCRUD("C");
      segmentData.setSegmentId(new BigInteger("1"));
      segmentData.setFlagCRUD("C");
      segmentInfo.add(segmentData);
      arrivalFlightDetails.setSegments(segmentInfo);
      
      uldData.setImpArrivalManifestUldId(new BigInteger("1"));
      uldData.setUldNumber("AAF12354SQ");
      uldData.setUldRemarks("Test");
      uldData.setFlagCRUD("C");
      uldInfo.add(uldData);
      segmentData.setManifestedUlds(uldInfo);
      
      shipmentData.setShipmentId(new BigInteger("1"));
      shipmentData.setShipmentNumber("96008597645");
      shipmentData.setOrigin("KIX");
      shipmentData.setDestination("SIN");
      shipmentData.setPiece(new BigInteger("10"));
      shipmentData.setWeight(new BigDecimal("12.5"));
      shipmentData.setWeightUnitCode("K");
      shipmentData.setTotalPieces(new BigInteger("30"));
      shipmentData.setNatureOfGoodsDescription("PERISHABLE");
      shipmentData.setFlagCRUD("C");
      shipmentData.setShipmentDescriptionCode("S");
     
      
      
      
      shcData.setSpecialHandlingCode("DGR");
      shcData.setFlagCRUD("C");
      shcInfo.add(shcData);
      shipmentData.setShc(shcInfo);
      shipmentInfo.add(shipmentData);
      uldData.setShipments(shipmentInfo);
      
      
      BaseResponse<ArrivalManifestByFlightModel> outputFlight =manifestController.createULDShipment(arrivalFlightDetails);
      arrivalFlightDetails.setAircraftRegCode("12134");
      arrivalFlightDetails.setFlightId(new BigInteger("3"));
      arrivalFlightDetails.setFlagCRUD("U");
      segmentData.setFlagCRUD("U");
      uldData.setFlagCRUD("U");
      shipmentData.setWeightUnitCode("L");
      shipmentData.setFlagCRUD("U");
      shcData.setFlagCRUD("U");
      manifestController.createULDShipment(arrivalFlightDetails);
      outputFlight.setData(null);
      
      
      assertNull(outputFlight.getData());
      
   }
   
   
   public void deleteULDShipment() throws CustomException{
      List<ArrivalManifestShipmentInfoModel> shipmentInfo = new ArrayList<ArrivalManifestShipmentInfoModel>();
      ArrivalManifestShipmentInfoModel shipmentData = new ArrivalManifestShipmentInfoModel();
      ArrivalManifestUldModel arrivalShipmentInfo = new ArrivalManifestUldModel();
      shipmentData.setShipmentId(new BigInteger("1"));
      shipmentData.setFlagCRUD("D");
      shipmentInfo.add(shipmentData);
      arrivalShipmentInfo.setShipments(shipmentInfo);
      
      BaseResponse<List<ArrivalManifestByFlightModel>> outputFlight =manifestController.deleteULDShipment(arrivalShipmentInfo);
      outputFlight.setData(null);
      assertNull(outputFlight.getData());           
   }
   
      
   public void createShipmentAdditionalInformation() throws CustomException{
      ArrivalManifestShipmentInfoModel shipmentData = new ArrivalManifestShipmentInfoModel();
      ArrivalManifestShipmentDimensionInfoModel dimensiondata = new ArrivalManifestShipmentDimensionInfoModel();
      ArrivalManifestShipmentOciModel ociData = new ArrivalManifestShipmentOciModel();
      ArrivalManifestOtherServiceInfoModel serviceData = new ArrivalManifestOtherServiceInfoModel();
      ArrivalManifestShipmentOnwardMovementModel movementData = new ArrivalManifestShipmentOnwardMovementModel();
      List<ArrivalManifestShipmentDimensionInfoModel> dimensionInfo = new ArrayList<ArrivalManifestShipmentDimensionInfoModel>();
      List<ArrivalManifestShipmentOciModel> ociInfo = new ArrayList<ArrivalManifestShipmentOciModel>();
      List<ArrivalManifestOtherServiceInfoModel> serviceInfo = new ArrayList<ArrivalManifestOtherServiceInfoModel>();
      List<ArrivalManifestShipmentOnwardMovementModel> movementInfo = new ArrayList<ArrivalManifestShipmentOnwardMovementModel>();
      
      dimensiondata.setNoOfPieces(new BigInteger("10"));
      dimensiondata.setWeight(new BigDecimal("10.2"));
      dimensiondata.setWeightUnitCode("K");
      dimensiondata.setLength(new BigDecimal("10.2"));
      dimensiondata.setWidth(new BigDecimal("10.2"));
      dimensiondata.setHeight(new BigDecimal("10.2"));
      dimensiondata.setMeasurementUnitCode("CMT");
      dimensiondata.setFlagCRUD("C");
      dimensiondata.setShipmentId(new BigInteger("1"));
      dimensionInfo.add(dimensiondata);
      shipmentData.setDimensions(dimensionInfo);
      
      ociData.setCountryCode("AE");
      ociData.setInformationIdentifier("TE");
      ociData.setCsrciIdentifier("TE");
      ociData.setScsrcInformation("TE");
      ociData.setFlagCRUD("C");
      ociData.setShipmentId(new BigInteger("1"));
      ociInfo.add(ociData);
      shipmentData.setOci(ociInfo);
      
     movementData.setAirportCityCode("BLR");
      movementData.setCarrierCode("1R");
      movementData.setFlightNumber("8985");
      movementData.setDepartureDate(LocalDate.of(2018, 01, 30));
      movementData.setFlagCRUD("C");
      movementData.setShipmentId(new BigInteger("1"));
      
      movementInfo.add(movementData);
      shipmentData.setMovementInfo(movementInfo);
      
      
      serviceData.setRemarks("Tesdt");
      serviceData.setShipmentId(new BigInteger("1"));
      serviceData.setFlagCRUD("C");
      serviceInfo.add(serviceData);
      shipmentData.setOsi(serviceInfo);
      
      shipmentData.setDensityGroupCode(new BigInteger("1"));
      shipmentData.setShipmentId(new BigInteger("1"));
      
      
      shipmentData.setShipmentNumber("96008597645");
      shipmentData.setOrigin("KIX");
      shipmentData.setDestination("SIN");
      shipmentData.setPiece(new BigInteger("10"));
      shipmentData.setWeight(new BigDecimal("12.5"));
      shipmentData.setWeightUnitCode("K");
      shipmentData.setTotalPieces(new BigInteger("30"));
      shipmentData.setNatureOfGoodsDescription("PERISHABLE");      
      shipmentData.setShipmentDescriptionCode("S");
      
      
      BaseResponse<List<ArrivalManifestByFlightModel>> outputFlight =manifestController.createShipmentAdditionalInformation(shipmentData);
      outputFlight.setData(null);
      assertNull(outputFlight.getData());
      dimensiondata.setFlagCRUD("U");      
      dimensionInfo.add(dimensiondata);
      shipmentData.setDimensions(dimensionInfo);
      
      ociData.setFlagCRUD("U");      
      ociInfo.add(ociData);
      shipmentData.setOci(ociInfo);
      
      movementData.setFlagCRUD("U");      
      movementInfo.add(movementData);
      shipmentData.setMovementInfo(movementInfo);
      
      
      serviceData.setFlagCRUD("U");
      serviceInfo.add(serviceData);
      shipmentData.setOsi(serviceInfo);
      BaseResponse<List<ArrivalManifestByFlightModel>> outputFlightt =manifestController.createShipmentAdditionalInformation(shipmentData);
      
      
      
   }
   

}
*/