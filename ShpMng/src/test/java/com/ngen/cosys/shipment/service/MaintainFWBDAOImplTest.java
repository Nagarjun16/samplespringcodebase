/*package com.ngen.cosys.shipment.service;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
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

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.controller.MaintainFWBController;
import com.ngen.cosys.shipment.model.AccountingInfo;
import com.ngen.cosys.shipment.model.AgentInfo;
import com.ngen.cosys.shipment.model.ChargeDeclaration;
import com.ngen.cosys.shipment.model.CustomerAddressInfo;
import com.ngen.cosys.shipment.model.CustomerContactInfo;
import com.ngen.cosys.shipment.model.CustomerInfo;
import com.ngen.cosys.shipment.model.FWB;
import com.ngen.cosys.shipment.model.FetchFWBRequest;
import com.ngen.cosys.shipment.model.FlightBooking;
import com.ngen.cosys.shipment.model.NominatedHandlingParty;
import com.ngen.cosys.shipment.model.OtherCharges;
import com.ngen.cosys.shipment.model.OtherCustomsInfo;
import com.ngen.cosys.shipment.model.OtherParticipantInfo;
import com.ngen.cosys.shipment.model.PrepaidCollectChargeSummary;
import com.ngen.cosys.shipment.model.RateDescOtherInfo;
import com.ngen.cosys.shipment.model.RateDescription;
import com.ngen.cosys.shipment.model.Routing;
import com.ngen.cosys.shipment.model.SHC;
import com.ngen.cosys.shipment.model.SSROSIInfo;
import com.ngen.cosys.shipment.model.ShpReferenceInformation;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MaintainFWBDAOImplTest {

   @Autowired
   private MaintainFWBController maintainFWBController;

   @Before
   public void setup() {
      assertNotNull(this);
   }

   
  @Test
   @Sql(scripts = { "/tddscripts/fwb-schema-tdd-h2.sql", "/tddscripts/fwb-data-tdd-h2.sql" })
   public void createFWB() throws CustomException {

      FWB fwbDetails = new FWB();
      fwbDetails.setFlagCRUD("C");
      SHC shcData = new SHC();
      FlightBooking flightBookingData = new FlightBooking();
      Routing routeData = new Routing();
      AccountingInfo accountInfoData = new AccountingInfo();
      SSROSIInfo ssrosInfoData = new SSROSIInfo();
      RateDescription rateDescData = new RateDescription();
      OtherCharges otherChargesData = new OtherCharges();
      RateDescOtherInfo rateDescOtherInfoData = new RateDescOtherInfo();
      OtherParticipantInfo otherParticipantInfoData = new OtherParticipantInfo();
      OtherCustomsInfo otherCustomsInfoData = new OtherCustomsInfo();
      List<SHC> shcode = new ArrayList<SHC>();
      List<FlightBooking> flightBooking = new ArrayList<FlightBooking>();
      List<Routing> routing = new ArrayList<Routing>();
      CustomerInfo consigneeInfo = new CustomerInfo();
      CustomerInfo shipperInfo = new CustomerInfo();
      CustomerInfo alsoNotify = new CustomerInfo();
      List<AccountingInfo> accountingInfo = new ArrayList<AccountingInfo>();
      ChargeDeclaration chargeDeclaration = new ChargeDeclaration();
      List<SSROSIInfo> ssrOsiInfo = new ArrayList<SSROSIInfo>();
      AgentInfo agentInfo = new AgentInfo();
      List<RateDescription> rateDescription = new ArrayList<RateDescription>();
      List<OtherCharges> otherCharges = new ArrayList<OtherCharges>();
      PrepaidCollectChargeSummary ppd = new PrepaidCollectChargeSummary();
      PrepaidCollectChargeSummary col = new PrepaidCollectChargeSummary();
     
      List<RateDescOtherInfo> rateDescriptionOtherInfo = new ArrayList<RateDescOtherInfo>();
      ShpReferenceInformation shipmentReferenceInfor = new ShpReferenceInformation();
      NominatedHandlingParty fwbNominatedHandlingParty = new NominatedHandlingParty();
      List<OtherParticipantInfo> otherParticipantInfo = new ArrayList<OtherParticipantInfo>();
      List<OtherCustomsInfo> otherCustomsInfo = new ArrayList<OtherCustomsInfo>();

      // fwb

      fwbDetails.setAwbPrefix("123");
      fwbDetails.setAwbSuffix("4567890");
      fwbDetails.setAwbNumber("1234567890");
      fwbDetails.setAwbDate(LocalDateTime.of(2018, 01, 30, 00, 00, 00));
      fwbDetails.setMessageProcessedDate(LocalDateTime.of(2018, 01, 30, 00, 00, 00));
      fwbDetails.setOrigin("SIN");
      fwbDetails.setDestination("HKG");
      fwbDetails.setPieces(new BigInteger("10"));
      fwbDetails.setWeightUnitCode("K");
      fwbDetails.setWeight(new BigDecimal("10.2"));
      fwbDetails.setVolumeUnitCode("CM");
      fwbDetails.setVolumeAmount(new BigDecimal("10.2"));
      fwbDetails.setDensityGroupCode(12);
      fwbDetails.setDensityIndicator("CC");
      fwbDetails.setCarriersExecutionPlace("BANGALORE");
      fwbDetails.setCarriersExecutionAuthSign("JUNIT");
      fwbDetails.setCustomOrigin("IN");
      fwbDetails.setShpCertificateSign("ACCEPTABLE");
      fwbDetails.setMessageSequence(5);
      fwbDetails.setMessageVersion(9);
      fwbDetails.setMessageStatus("TESTED");
      fwbDetails.setFlagCRUD("C");
      

      // shc
      shcData.setSpecialHandlingCode("SHC");
      shcData.setFlagCRUD("C");
     
      shcode.add(shcData);
      fwbDetails.setShcode(shcode);
      // FLIGHT BOOKING
      flightBookingData.setCarrierCode("FBD");
      flightBookingData.setFlagCRUD("C");
      flightBookingData.setFlightNumber("ASA12");
      //flightBookingData.setFlightDate("2");
      flightBooking.add(flightBookingData);
      fwbDetails.setFlightBooking(flightBooking);

      // ROUTING
      routeData.setAirportCode("JFK");
      routeData.setCarrierCode("DTC");
      routeData.setFlagCRUD("C");
      
      routing.add(routeData);
      fwbDetails.setRouting(routing);
      // accountInfo

      accountInfoData.setAccountingInformation("TESTABLE");
      accountInfoData.setFlagCRUD("C");
      
      accountInfoData.setInformationIdentifier("AIF");
      accountingInfo.add(accountInfoData);
      fwbDetails.setAccountingInfo(accountingInfo);

      // ssrosInfoData
      ssrosInfoData.setFlagCRUD("C");
      
      ssrosInfoData.setServiceRequestcontent("SEND TO SIN");
      ssrosInfoData.setServiceRequestType("SRT");
      ssrOsiInfo.add(ssrosInfoData);
      fwbDetails.setSsrOsiInfo(ssrOsiInfo);

      // rateDescData
      rateDescData.setNumberOfPieces(new BigInteger("50"));
      rateDescData.setRateLineNumber("RLN");
      rateDescData.setFlagCRUD("C");
     
      rateDescription.add(rateDescData);
      fwbDetails.setRateDescription(rateDescription);

      // otherChargesData
      otherChargesData.setFlagCRUD("C");
      
      otherChargesData.setOtherChargeCode("TX");
      otherChargesData.setOtherChargeIndicator("S");
      otherCharges.add(otherChargesData);
      fwbDetails.setOtherCharges(otherCharges);

      // rateDescOtherInfoData
      rateDescOtherInfoData.setDimensionHeight("23");
      rateDescOtherInfoData.setDimensionLength("12");
      rateDescOtherInfoData.setDimensionWIdth("13");
      rateDescOtherInfoData.setFlagCRUD("C");
   
      rateDescOtherInfoData.setNumberOfPieces("50");
      rateDescOtherInfoData.setMeasurementUnitCode("KGS");
      rateDescriptionOtherInfo.add(rateDescOtherInfoData);
      fwbDetails.setRateDescriptionOtherInfo(rateDescriptionOtherInfo);

      // otherParticipantInfoData

      otherParticipantInfoData.setFlagCRUD("C");
      
      otherParticipantInfoData.setAirportCityCode("JFK");
      otherParticipantInfoData.setCompanyDesignator("TC");
      otherParticipantInfoData.setOfficeFunctionDesignator("OF");
      otherParticipantInfoData.setParticipantName("LILLY");
      otherParticipantInfo.add(otherParticipantInfoData);
      fwbDetails.setOtherParticipantInfo(otherParticipantInfo);

      // otherCustomsInfo
      otherCustomsInfoData.setInformationIdentifier("SBI");
      otherCustomsInfoData.setCsrciIdentifier("PQ");
      otherCustomsInfoData.setScrcInformation("SUCCESS");
      otherCustomsInfoData.setCountryCode("IN");
      otherCustomsInfoData.setFlagCRUD("C");
     
      otherCustomsInfo.add(otherCustomsInfoData);
      fwbDetails.setOtherCustomsInfo(otherCustomsInfo);

      // fwbNominatedHandlingParty
      fwbNominatedHandlingParty.setHandlingPartyName("TEXAS");
      fwbNominatedHandlingParty.setHandlingPartyPlace("MUMBAI");
      fwbNominatedHandlingParty.setFlagCRUD("C");

      fwbDetails.setFwbNominatedHandlingParty(fwbNominatedHandlingParty);

      // shipmentReferenceInfor
      shipmentReferenceInfor.setFlagCRUD("C");
 
      shipmentReferenceInfor.setReferenceNumber("1DS");
      shipmentReferenceInfor.setSupplementaryShipmentInformation1("UNITED");
      shipmentReferenceInfor.setSupplementaryShipmentInformation2("STATE");
      fwbDetails.setShipmentReferenceInfor(shipmentReferenceInfor);
      // agentInfo
      agentInfo.setAccountNumber("12345");
      agentInfo.setParticipantIdentifier("XYZ");
      agentInfo.setAgentName("TONY");
      agentInfo.setAgentPlace("SMILA");
     // agentInfo.setIATACargoAgentCASSAddress("2345");
      //agentInfo.setIATACargoAgentNumericCode("12");
      agentInfo.setFlagCRUD("C");
   
      fwbDetails.setAgentInfo(agentInfo);

      // consigneeInfo
      CustomerContactInfo contactInfoData1 = new CustomerContactInfo();
      contactInfoData1.setContactIdentifier("TE");
      contactInfoData1.setContactDetail("9876543");
      contactInfoData1.setFlagCRUD("C");
      CustomerContactInfo contactInfoData2 = new CustomerContactInfo();
      contactInfoData2.setContactIdentifier("FX");
      contactInfoData2.setContactDetail("ABC@GMAIL.COM");
      contactInfoData2.setFlagCRUD("C");
      List<CustomerContactInfo> consigneeContactList = new ArrayList<CustomerContactInfo>();
      consigneeContactList.add(contactInfoData1);
      consigneeContactList.add(contactInfoData2);
      CustomerAddressInfo consigneeAddress = new CustomerAddressInfo();
      consigneeAddress.setFlagCRUD("C");

      //consigneeAddress.setStreetAddress("google");
      consigneeAddress.setCustomerPlace("UNITED STATE");
      consigneeAddress.setStateCode("UK");
      consigneeAddress.setCountryCode("US");
      consigneeAddress.setPostalCode("123456");
      consigneeAddress.setCustomerContactInfo(consigneeContactList);
      consigneeInfo.setCustomerType("CNE");
      consigneeInfo.setCustomerName("ROSE");
      consigneeInfo.setCustomerAccountNumber("101011");
      consigneeInfo.setCustomerCode("12233");
      consigneeInfo.setCustomerAddressInfo(consigneeAddress);
      fwbDetails.setConsigneeInfo(consigneeInfo);
      // SHIPPER
      CustomerContactInfo contactInfoData3 = new CustomerContactInfo();
      contactInfoData3.setContactIdentifier("TE");
      contactInfoData3.setContactDetail("9876543");
      contactInfoData3.setFlagCRUD("C");
      CustomerContactInfo contactInfoData4 = new CustomerContactInfo();
      contactInfoData4.setContactIdentifier("FX");
      contactInfoData4.setContactDetail("ABC@GMAIL.COM");
      contactInfoData4.setFlagCRUD("C");
      List<CustomerContactInfo> shipperContactList = new ArrayList<CustomerContactInfo>();
      shipperContactList.add(contactInfoData3);
      shipperContactList.add(contactInfoData4);
      CustomerAddressInfo shipperAddress = new CustomerAddressInfo();
      shipperAddress.setFlagCRUD("C");

      //shipperAddress.setStreetAddress("google");
      shipperAddress.setCustomerPlace("UNITED STATE");
      shipperAddress.setStateCode("UK");
      shipperAddress.setCountryCode("US");
      shipperAddress.setPostalCode("123456");
      shipperAddress.setCustomerContactInfo(shipperContactList);
      shipperInfo.setCustomerType("SHP");
      shipperInfo.setCustomerName("MARI");
      shipperInfo.setCustomerAccountNumber("101111");
      shipperInfo.setCustomerCode("44433");
      shipperInfo.setCustomerAddressInfo(shipperAddress);
      fwbDetails.setShipperInfo(shipperInfo);
      // also notify
      CustomerContactInfo contactInfoData5 = new CustomerContactInfo();
      contactInfoData5.setContactIdentifier("TE");
      contactInfoData5.setContactDetail("9876543");
      contactInfoData5.setFlagCRUD("C");
      CustomerContactInfo contactInfoData6 = new CustomerContactInfo();
      contactInfoData6.setContactIdentifier("FX");
      contactInfoData6.setContactDetail("ABC@GMAIL.COM");
      contactInfoData6.setFlagCRUD("C");
      List<CustomerContactInfo> alsoNotifyContactList = new ArrayList<CustomerContactInfo>();
      alsoNotifyContactList.add(contactInfoData5);
      alsoNotifyContactList.add(contactInfoData6);
      CustomerAddressInfo alsoNotifyAddress = new CustomerAddressInfo();
      alsoNotifyAddress.setFlagCRUD("C");
 
      //alsoNotifyAddress.setStreetAddress("google");
      alsoNotifyAddress.setCustomerPlace("UNITED STATE");
      alsoNotifyAddress.setStateCode("UK");
      alsoNotifyAddress.setCountryCode("US");
      alsoNotifyAddress.setPostalCode("123456");
      alsoNotifyAddress.setCustomerContactInfo(alsoNotifyContactList);
      alsoNotify.setCustomerType("NFY");
      alsoNotify.setCustomerName("MARI");
      alsoNotify.setCustomerAddressInfo(alsoNotifyAddress);
      fwbDetails.setAlsoNotify(alsoNotify);

      // chargeDeclaration
      chargeDeclaration.setFlagCRUD("C");

      chargeDeclaration.setCurrencyCode("DOL");
      chargeDeclaration.setPrepaIdCollectChargeDeclaration("D");
      fwbDetails.setChargeDeclaration(chargeDeclaration);

      // ppd
      ppd.setChargeTypeLineIdentifier("PPD");
      ppd.setFlagCRUD("C");

      ppd.setValuationChargeAmount(new BigDecimal("77.90"));
      fwbDetails.setPpd(ppd);
      
   // col
      col.setChargeTypeLineIdentifier("COL");
      col.setFlagCRUD("C");

      col.setValuationChargeAmount(new BigDecimal("55.90"));
      fwbDetails.setCol(col);
      
      //COL col.setChargeTypeLineIdentifier("COL"); col.setFlagCRUD("C");
     //col.setValuationChargeAmount("77.90"); fwbDetails.setPpd(col);
       
      assertNotNull(fwbDetails);
      //BaseResponse<FWB> fwbData = maintainFWBController.saveUpdateDeleteAWBFollowedIATA(fwbDetails);
      
   }
   
   @Test
   @Sql(scripts = { "/tddscripts/fwb-schema-tdd-h2.sql", "/tddscripts/fwb-data-tdd-h2.sql" })
   public void fetchFWB() throws CustomException {
      FWB fwb = new FWB();
      // String number = fwb.setAwbNumber("65432101987");
      FetchFWBRequest fetchFWBRequest=new FetchFWBRequest();
      fetchFWBRequest.setAwbNumber("1234567890");
     // BaseResponse<FWB> fwbDetails = maintainFWBController.fetchAWBFollowedIATA(fetchFWBRequest);
     // assertNotNull(fwbDetails);
     // assertNotNull(fwbDetails.getData().getConsigneeInfo());
   }



  
   
   
   
   @Test
   @Sql(scripts = { "/tddscripts/fwb-schema-tdd-h2.sql", "/tddscripts/fwb-data-tdd-h2.sql" })
   public void updateFWB() throws CustomException {

      FWB fwbDetails = new FWB();
      fwbDetails.setFlagCRUD("U");
      SHC shcData = new SHC();
      FlightBooking flightBookingData = new FlightBooking();
      Routing routeData = new Routing();
      AccountingInfo accountInfoData = new AccountingInfo();
      SSROSIInfo ssrosInfoData = new SSROSIInfo();
      RateDescription rateDescData = new RateDescription();
      OtherCharges otherChargesData = new OtherCharges();
      RateDescOtherInfo rateDescOtherInfoData = new RateDescOtherInfo();
      OtherParticipantInfo otherParticipantInfoData = new OtherParticipantInfo();
      OtherCustomsInfo otherCustomsInfoData = new OtherCustomsInfo();
      List<SHC> shcode = new ArrayList<SHC>();
      List<FlightBooking> flightBooking = new ArrayList<FlightBooking>();
      List<Routing> routing = new ArrayList<Routing>();
      CustomerInfo consigneeInfo = new CustomerInfo();
      CustomerInfo shipperInfo = new CustomerInfo();
      CustomerInfo alsoNotify = new CustomerInfo();
      List<AccountingInfo> accountingInfo = new ArrayList<AccountingInfo>();
      ChargeDeclaration chargeDeclaration = new ChargeDeclaration();
      List<SSROSIInfo> ssrOsiInfo = new ArrayList<SSROSIInfo>();
      AgentInfo agentInfo = new AgentInfo();
      List<RateDescription> rateDescription = new ArrayList<RateDescription>();
      List<OtherCharges> otherCharges = new ArrayList<OtherCharges>();
      PrepaidCollectChargeSummary ppd = new PrepaidCollectChargeSummary();
      PrepaidCollectChargeSummary col = new PrepaidCollectChargeSummary();
      PrepaidCollectChargeSummary ppdCol = new PrepaidCollectChargeSummary();
      List<RateDescOtherInfo> rateDescriptionOtherInfo = new ArrayList<RateDescOtherInfo>();
      ShpReferenceInformation shipmentReferenceInfor = new ShpReferenceInformation();
      NominatedHandlingParty fwbNominatedHandlingParty = new NominatedHandlingParty();
      List<OtherParticipantInfo> otherParticipantInfo = new ArrayList<OtherParticipantInfo>();
      List<OtherCustomsInfo> otherCustomsInfo = new ArrayList<OtherCustomsInfo>();

      // fwb

      fwbDetails.setAwbPrefix("123");
      fwbDetails.setAwbSuffix("4567890");
      fwbDetails.setAwbNumber("1234567890");
      fwbDetails.setAwbDate(LocalDateTime.of(2018, 01, 30, 00, 00, 00));
      fwbDetails.setMessageProcessedDate(LocalDateTime.of(2018, 01, 30, 00, 00, 00));
      fwbDetails.setOrigin("SIN");
      fwbDetails.setDestination("HKG");
      fwbDetails.setPieces(new BigInteger("10"));
      fwbDetails.setWeightUnitCode("K");
      fwbDetails.setWeight(new BigDecimal("10.2"));
      fwbDetails.setVolumeUnitCode("CM");
      fwbDetails.setVolumeAmount(new BigDecimal("10.2"));
      fwbDetails.setDensityGroupCode(12);
      fwbDetails.setDensityIndicator("CC");
      fwbDetails.setCarriersExecutionPlace("MUMBAI");
      fwbDetails.setCarriersExecutionAuthSign("CROSS");
      fwbDetails.setCustomOrigin("IN");
      fwbDetails.setShpCertificateSign("ACCEPTABLE");
      fwbDetails.setMessageSequence(5);
      fwbDetails.setMessageVersion(9);
      fwbDetails.setMessageStatus("TESTED");
      
      fwbDetails.setFlagCRUD("U");

      // shc
      shcData.setSpecialHandlingCode("SHC");
      
      shcData.setFlagCRUD("U");
      shcode.add(shcData);
      fwbDetails.setShcode(shcode);
      // FLIGHT BOOKING
      flightBookingData.setCarrierCode("FBD");
      flightBookingData.setFlagCRUD("U");
      flightBookingData.setFlightNumber("ASA24");
      flightBookingData.setFlightDays("2");
      flightBooking.add(flightBookingData);
      fwbDetails.setFlightBooking(flightBooking);

      // ROUTING
      routeData.setAirportCode("JFK");
      routeData.setCarrierCode("CTD");
     
      routeData.setFlagCRUD("U");
      routing.add(routeData);
      fwbDetails.setRouting(routing);
      // accountInfo

      accountInfoData.setAccountingInformation("UPDATED");
      
      accountInfoData.setFlagCRUD("U");
      accountInfoData.setInformationIdentifier("FLR");
      accountingInfo.add(accountInfoData);
      fwbDetails.setAccountingInfo(accountingInfo);

      // ssrosInfoData
      
      ssrosInfoData.setFlagCRUD("U");
      ssrosInfoData.setServiceRequestcontent("FETCH TO SIN");
      ssrosInfoData.setServiceRequestType("SRT");
      ssrOsiInfo.add(ssrosInfoData);
      fwbDetails.setSsrOsiInfo(ssrOsiInfo);

      // rateDescData
      rateDescData.setNumberOfPieces(new BigInteger("50"));
      rateDescData.setRateLineNumber("NRL");
      
      rateDescData.setFlagCRUD("U");
      rateDescription.add(rateDescData);
      fwbDetails.setRateDescription(rateDescription);

      // otherChargesData
      
      otherChargesData.setFlagCRUD("U");
      otherChargesData.setOtherChargeCode("TX");
      otherChargesData.setOtherChargeIndicator("P");
      otherCharges.add(otherChargesData);
      fwbDetails.setOtherCharges(otherCharges);

      // rateDescOtherInfoData
      rateDescOtherInfoData.setDimensionHeight("3");
      rateDescOtherInfoData.setDimensionLength("2");
      rateDescOtherInfoData.setDimensionWIdth("11");
      
      rateDescOtherInfoData.setFlagCRUD("U");
      rateDescOtherInfoData.setNumberOfPieces("150");
      rateDescOtherInfoData.setMeasurementUnitCode("KGS");
      rateDescriptionOtherInfo.add(rateDescOtherInfoData);
      fwbDetails.setRateDescriptionOtherInfo(rateDescriptionOtherInfo);

      // otherParticipantInfoData

      
      otherParticipantInfoData.setFlagCRUD("U");
      otherParticipantInfoData.setAirportCityCode("JFK");
      otherParticipantInfoData.setCompanyDesignator("TC");
      otherParticipantInfoData.setOfficeFunctionDesignator("OF");
      otherParticipantInfoData.setParticipantName("ROZY");
      otherParticipantInfo.add(otherParticipantInfoData);
      fwbDetails.setOtherParticipantInfo(otherParticipantInfo);

      // otherCustomsInfo
      otherCustomsInfoData.setInformationIdentifier("SBI");
      otherCustomsInfoData.setCsrciIdentifier("PQ");
      otherCustomsInfoData.setScrcInformation("FLAG");
      otherCustomsInfoData.setCountryCode("IN");
      
      otherCustomsInfoData.setFlagCRUD("U");
      otherCustomsInfo.add(otherCustomsInfoData);
      fwbDetails.setOtherCustomsInfo(otherCustomsInfo);

      // fwbNominatedHandlingParty
      fwbNominatedHandlingParty.setHandlingPartyName("TEXAS");
      fwbNominatedHandlingParty.setHandlingPartyPlace("MUMBAI");
      
      fwbNominatedHandlingParty.setFlagCRUD("U");
      fwbDetails.setFwbNominatedHandlingParty(fwbNominatedHandlingParty);

      // shipmentReferenceInfor
      
      shipmentReferenceInfor.setFlagCRUD("U");
      shipmentReferenceInfor.setReferenceNumber("1DS");
      shipmentReferenceInfor.setSupplementaryShipmentInformation1("UNITED");
      shipmentReferenceInfor.setSupplementaryShipmentInformation2("KINDOM");
      fwbDetails.setShipmentReferenceInfor(shipmentReferenceInfor);
      // agentInfo
      agentInfo.setAccountNumber("12345");
      agentInfo.setParticipantIdentifier("XYZ");
      agentInfo.setAgentName("SONU");
      agentInfo.setAgentPlace("CHENNAI");
      agentInfo.setIATACargoAgentCASSAddress(new BigInteger("2345"));
      agentInfo.setIATACargoAgentNumericCode(new BigInteger("12"));
     
      agentInfo.setFlagCRUD("U");
      fwbDetails.setAgentInfo(agentInfo);

      // consigneeInfo
      CustomerContactInfo contactInfoData1 = new CustomerContactInfo();
      contactInfoData1.setContactIdentifier("TE");
      contactInfoData1.setContactDetail("9876543");
      CustomerContactInfo contactInfoData2 = new CustomerContactInfo();
      contactInfoData2.setContactIdentifier("FX");
      contactInfoData2.setContactDetail("ABC@GMAIL.COM");
      List<CustomerContactInfo> consigneeContactList = new ArrayList<CustomerContactInfo>();
      consigneeContactList.add(contactInfoData1);
      consigneeContactList.add(contactInfoData2);
      CustomerAddressInfo consigneeAddress = new CustomerAddressInfo();
      
      consigneeAddress.setFlagCRUD("U");
      consigneeAddress.setStreetAddress1("google");
      consigneeAddress.setCustomerPlace("UNITED STATE");
      consigneeAddress.setStateCode("UK");
      consigneeAddress.setCountryCode("US");
      consigneeAddress.setPostalCode("123456");
      consigneeAddress.setCustomerContactInfo(consigneeContactList);
      consigneeInfo.setCustomerType("CNE");
      consigneeInfo.setCustomerName("ROSE");
      consigneeInfo.setCustomerAccountNumber("101011");
      consigneeInfo.setCustomerCode("12233");
      consigneeInfo.setCustomerAddressInfo(consigneeAddress);
      fwbDetails.setConsigneeInfo(consigneeInfo);
      // SHIPPER
      CustomerContactInfo contactInfoData3 = new CustomerContactInfo();
      contactInfoData3.setContactIdentifier("TE");
      contactInfoData3.setContactDetail("9876543");
      CustomerContactInfo contactInfoData4 = new CustomerContactInfo();
      contactInfoData4.setContactIdentifier("FX");
      contactInfoData4.setContactDetail("ABC@GOOGLE.COM");
      List<CustomerContactInfo> shipperContactList = new ArrayList<CustomerContactInfo>();
      shipperContactList.add(contactInfoData3);
      shipperContactList.add(contactInfoData4);
      CustomerAddressInfo shipperAddress = new CustomerAddressInfo();
      
      shipperAddress.setFlagCRUD("U");
      shipperAddress.setStreetAddress1("google");
      shipperAddress.setCustomerPlace("UNITED STATE");
      shipperAddress.setStateCode("UK");
      shipperAddress.setCountryCode("US");
      shipperAddress.setPostalCode("123456");
      shipperAddress.setCustomerContactInfo(shipperContactList);
      shipperInfo.setCustomerType("SHP");
      shipperInfo.setCustomerName("RAAZ");
      shipperInfo.setCustomerAccountNumber("101111");
      shipperInfo.setCustomerCode("44433");
      shipperInfo.setCustomerAddressInfo(shipperAddress);
      fwbDetails.setShipperInfo(shipperInfo);
      // also notify
      CustomerContactInfo contactInfoData5 = new CustomerContactInfo();
      contactInfoData5.setContactIdentifier("TE");
      contactInfoData5.setContactDetail("9876543");
      CustomerContactInfo contactInfoData6 = new CustomerContactInfo();
      contactInfoData6.setContactIdentifier("FX");
      contactInfoData6.setContactDetail("XYZ@GMAIL.COM");
      List<CustomerContactInfo> alsoNotifyContactList = new ArrayList<CustomerContactInfo>();
      alsoNotifyContactList.add(contactInfoData5);
      alsoNotifyContactList.add(contactInfoData6);
      CustomerAddressInfo alsoNotifyAddress = new CustomerAddressInfo();
     
      alsoNotifyAddress.setFlagCRUD("U");
      alsoNotifyAddress.setStreetAddress1("YAHOO");
      alsoNotifyAddress.setCustomerPlace("UNITED STATE");
      alsoNotifyAddress.setStateCode("UK");
      alsoNotifyAddress.setCountryCode("US");
      alsoNotifyAddress.setPostalCode("123456");
      alsoNotifyAddress.setCustomerContactInfo(alsoNotifyContactList);
      alsoNotify.setCustomerType("NFY");
      alsoNotify.setCustomerName("SONI");
      alsoNotify.setCustomerAddressInfo(alsoNotifyAddress);
      fwbDetails.setAlsoNotify(alsoNotify);

      // chargeDeclaration
      
      chargeDeclaration.setFlagCRUD("U");
      chargeDeclaration.setCurrencyCode("DOL");
      chargeDeclaration.setPrepaIdCollectChargeDeclaration("F");
      fwbDetails.setChargeDeclaration(chargeDeclaration);

      // ppd
      ppdCol.setChargeTypeLineIdentifier("PPD");
     
      ppdCol.setFlagCRUD("U");
      ppdCol.setValuationChargeAmount(new BigDecimal("88.90"));
      //fwbDetails.setPpdCol(ppdCol);
      
       //COL col.setChargeTypeLineIdentifier("COL"); col.setFlagCRUD("C");
       //col.setValuationChargeAmount("77.90"); fwbDetails.setPpd(col);
       
      assertNotNull(fwbDetails);
      //BaseResponse<FWB> fwbData = maintainFWBController.saveUpdateDeleteAWBFollowedIATA(fwbDetails);
      
   }
   
   
   @Test
   @Sql(scripts = { "/tddscripts/fwb-schema-tdd-h2.sql", "/tddscripts/fwb-data-tdd-h2.sql" })
   public void deleteFWB() throws CustomException {
      FWB fwbDetails = new FWB();
      // account info
      fwbDetails.setFlagCRUD("D");
      AccountingInfo accountInfoData = new AccountingInfo();
      List<AccountingInfo> accountingInfo = new ArrayList<AccountingInfo>();
      accountInfoData.setAccountingInformation("TESTABLE");
      accountInfoData.setFlagCRUD("D");
      accountInfoData.setInformationIdentifier("AIF");
      accountingInfo.add(accountInfoData);
      fwbDetails.setAccountingInfo(accountingInfo);

      // CHARGEdECLARATION
      ChargeDeclaration chargeDeclaration = new ChargeDeclaration();
      chargeDeclaration.setFlagCRUD("D");
      chargeDeclaration.setCurrencyCode("DOL");
      chargeDeclaration.setPrepaIdCollectChargeDeclaration("D");
      fwbDetails.setChargeDeclaration(chargeDeclaration);

      // RATE DESCRIPTION
      RateDescription rateDescData = new RateDescription();
      List<RateDescription> rateDescription = new ArrayList<RateDescription>();
      rateDescData.setNumberOfPieces(new BigInteger("50"));
      rateDescData.setRateLineNumber("RLN");
      rateDescData.setFlagCRUD("D");
      rateDescription.add(rateDescData);
      fwbDetails.setRateDescription(rateDescription);
      // OTHER RATE DESCRIPTION
      RateDescOtherInfo rateDescOtherInfoData = new RateDescOtherInfo();
      List<RateDescOtherInfo> rateDescriptionOtherInfo = new ArrayList<RateDescOtherInfo>();
      rateDescOtherInfoData.setDimensionHeight("23");
      rateDescOtherInfoData.setDimensionLength("12");
      rateDescOtherInfoData.setDimensionWIdth("13");
      rateDescOtherInfoData.setFlagCRUD("D");
      rateDescOtherInfoData.setNumberOfPieces("50");
      rateDescOtherInfoData.setMeasurementUnitCode("KGS");
      rateDescriptionOtherInfo.add(rateDescOtherInfoData);
      fwbDetails.setRateDescriptionOtherInfo(rateDescriptionOtherInfo);

     
      
      //COL col.setChargeTypeLineIdentifier("COL"); col.setFlagCRUD("C");
       // col.setValuationChargeAmount("77.90"); fwbDetails.setPpd(col);
       
      // assertNull(outputFlight);

      // OTHER CHARGES
      OtherCharges otherChargesData = new OtherCharges();
      List<OtherCharges> otherCharges = new ArrayList<OtherCharges>();
      otherChargesData.setFlagCRUD("D");
      otherChargesData.setOtherChargeCode("TX");
      otherChargesData.setOtherChargeIndicator("S");
      otherCharges.add(otherChargesData);
      fwbDetails.setOtherCharges(otherCharges);
   //OTHER PARTICIPANT
      OtherParticipantInfo otherParticipantInfoData = new OtherParticipantInfo();
      List<OtherParticipantInfo> otherParticipantInfo = new ArrayList<OtherParticipantInfo>();
      otherParticipantInfoData.setFlagCRUD("D");
      otherParticipantInfoData.setAirportCityCode("JFK");
      otherParticipantInfoData.setCompanyDesignator("TC");
      otherParticipantInfoData.setOfficeFunctionDesignator("OF");
      otherParticipantInfoData.setParticipantName("LILLY");
      otherParticipantInfo.add(otherParticipantInfoData);
      fwbDetails.setOtherParticipantInfo(otherParticipantInfo);
     //OTHER CUSTOMS 
      OtherCustomsInfo otherCustomsInfoData = new OtherCustomsInfo();
      List<OtherCustomsInfo> otherCustomsInfo = new ArrayList<OtherCustomsInfo>();
      otherCustomsInfoData.setInformationIdentifier("SBI");
      otherCustomsInfoData.setCsrciIdentifier("PQ");
      otherCustomsInfoData.setScrcInformation("SUCCESS");
      otherCustomsInfoData.setCountryCode("IN");
      otherCustomsInfoData.setFlagCRUD("D");
      otherCustomsInfo.add(otherCustomsInfoData);
      fwbDetails.setOtherCustomsInfo(otherCustomsInfo);
      assertNotNull(fwbDetails);
      
     // BaseResponse<FWB> fwbData = maintainFWBController.saveUpdateDeleteAWBFollowedIATA(fwbDetails);
      
   }

}
*/