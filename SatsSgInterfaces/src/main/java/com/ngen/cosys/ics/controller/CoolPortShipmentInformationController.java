package com.ngen.cosys.ics.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.WebRequest;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.BookingInfo;
import com.ngen.cosys.ics.model.CoolPortShipmentExceptionResponseModel;
import com.ngen.cosys.ics.model.CoolPortShipmentRequestModel;
import com.ngen.cosys.ics.model.CoolPortShipmentResponseModel;
import com.ngen.cosys.ics.model.Info;
import com.ngen.cosys.ics.model.TemperatureInfo;
import com.ngen.cosys.ics.service.CoolPortShipmentInformationService;

@NgenCosysAppInfraAnnotation(path = "/api/ics")
public class CoolPortShipmentInformationController {

   @Autowired
   private CoolPortShipmentInformationService service;

   private String awbNumber = null;

   @PostMapping(path = "/fetch-shipment-information", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<Object> fetchShipmentInformation(@RequestBody @Valid CoolPortShipmentRequestModel requestModel, HttpServletRequest request) {
      awbNumber = requestModel.getAwbNumber();
      CoolPortShipmentResponseModel response = null;
      try {
         // response = service.fetchShipmentInformation(requestModel);
         response = getDummyResponse();
      } catch (CustomException e) {
         e.printStackTrace();
      }

      return new ResponseEntity<>(response, HttpStatus.OK);
   }

   private CoolPortShipmentResponseModel getDummyResponse() throws CustomException {
      CoolPortShipmentResponseModel response = new CoolPortShipmentResponseModel();
      response.setAwbNumber("61811041811");
      response.setOrigin("SIN");
      response.setDestination("HKG");
      response.setPieces("10");
      response.setWeight("100.07");
      response.setShc("AVI,PER");
      response.setDescription("CONSOL");

      BookingInfo bookingInfo = new BookingInfo();
      bookingInfo.setFlight("SQ860");
      bookingInfo.setFlightDate(LocalDate.now());
      bookingInfo.setSegment("SINHKG");

      response.setBookingInfo(bookingInfo);

      response.setTempUnit('C');

      TemperatureInfo temperatureInfo1 = new TemperatureInfo();

      temperatureInfo1.setUldNumber("AKE3456SQ");
      temperatureInfo1.setTrackingId("4324");

      Info info1 = new Info();
      info1.setTime(LocalDateTime.now());
      info1.setTemperature("30");
      info1.setHumidity("");
      info1.setStage("Warehouse Temperature");

      Info info2 = new Info();
      info2.setTime(LocalDateTime.now());
      info2.setTemperature("30");
      info2.setHumidity("");
      info2.setStage("ULD Temperature");
      temperatureInfo1.setInfo(Arrays.asList(info1, info2));

      TemperatureInfo temperatureInfo2 = new TemperatureInfo();

      temperatureInfo2.setUldNumber("AKE3456SQ");
      temperatureInfo2.setTrackingId("4324");

      Info info3 = new Info();
      info3.setTime(LocalDateTime.now());
      info3.setTemperature("30");
      info3.setHumidity("");
      info3.setStage("Warehouse Temperature");

      Info info4 = new Info();
      info4.setTime(LocalDateTime.now());
      info4.setTemperature("30");
      info4.setHumidity("");
      info4.setStage("ULD Temperature");
      temperatureInfo2.setInfo(Arrays.asList(info3, info4));

      response.setTemperatureInfo(Arrays.asList(temperatureInfo1, temperatureInfo2));
      return response;
   }

   @ExceptionHandler({ Exception.class })
   public ResponseEntity<CoolPortShipmentExceptionResponseModel> handleMethodArgumentTypeMismatch(final Exception ex, HttpServletRequest req, final WebRequest request) {

      CoolPortShipmentExceptionResponseModel response = new CoolPortShipmentExceptionResponseModel();
      response.setAwbNumber(awbNumber);
      response.setErrorNumber(HttpStatus.BAD_REQUEST.toString());
      response.setErrorType("Invalid Request");
      response.setErrorDescription(ex.getMessage());

      return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
   }

}