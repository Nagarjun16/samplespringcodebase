/**
 * AlteaFMDatasetUtil.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.altea.fm.amadeus.xml.request.AllPackedInOneLoadDetailsType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.CargoDetailsType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.CompanyIdentificationTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.AllPackedInOneForDeadload;
import com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.OverpackForDeadload.AllPackedInOneForOVP;
import com.ngen.cosys.altea.fm.amadeus.xml.request.DummySegmentTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.EquipmentIdentificationType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.EquipmentInformationType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.FlightDetailsQueryType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.FreeTextQualificationTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.HazardCodeType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.HazardIdentificationTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.InboundCarrierDetailsTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.InboundFlightNumberDetailsTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.InteractiveFreeTextType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.InteractiveFreeTextTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.InteractiveFreeTextTypeI84224S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ItemDescriptionDetailsTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ItemDescriptionDetailsTypeI139541C;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ItemDescriptionDetailsTypeI139765C;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ItemDescriptionTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ItemDescriptionTypeI92732S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ItemDescriptionTypeI92894S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.LoadDetailsInformationType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.LoadDetailsInformationType92764S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.LoadDetailsType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.MeasurementsType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.MeasurementsType92900S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.NumberOfUnitDetailsType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.NumberOfUnitDetailsTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.NumberOfUnitsType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.NumberOfUnitsType83983S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.OriginAndDestinationDetailsTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.OriginAndDestinationDetailsTypeI67289S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.OutboundCarrierDetailsTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.OutboundFlightNumberDetailstypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.OverpackLoadDetailsType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.PhoneAndEmailAddressType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ProductIdentificationDetailsTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.QuantityDetailsType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.QuantityDetailsTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.QuantityType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.QuantityType83982S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.QuantityType92784S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.QuantityTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ReferenceInfoType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ReferenceInfoType175451S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ReferenceInfoType193774S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ReferenceInformationTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ReferenceInformationTypeI83948S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ReferenceInformationTypeI84021S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ReferenceInformationTypeI84045S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ReferenceInformationTypeI92892S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ReferencingDetailsType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ReferencingDetailsType247148C;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ReferencingDetailsTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ReferencingDetailsTypeI128135C;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ReferencingDetailsTypeI139763C;
import com.ngen.cosys.altea.fm.amadeus.xml.request.SpecialLoadDetailsType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.StatusDetailsType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.StatusDetailsTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.StatusDetailsTypeI128105C;
import com.ngen.cosys.altea.fm.amadeus.xml.request.StatusDetailsTypeI171610C;
import com.ngen.cosys.altea.fm.amadeus.xml.request.StatusType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.StatusTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.StatusTypeI83998S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.StatusTypeI84225S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.StructuredDateTimeInformationType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.StructuredDateTimeInformationType84018S;
import com.ngen.cosys.altea.fm.amadeus.xml.request.StructuredDateTimeType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.StructuredDateTimeType129165C;
import com.ngen.cosys.altea.fm.amadeus.xml.request.StructuredTelephoneNumberType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.TransportIdentifierType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.TravellerDetailsTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.TravellerInformationTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.TravellerSurnameInformationTypeI;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ValueRangeType;
import com.ngen.cosys.altea.fm.amadeus.xml.request.ValueRangeTypeI;
import com.ngen.cosys.altea.fm.model.AgentNameForNOTOC;
import com.ngen.cosys.altea.fm.model.AircraftTypeRegistration;
import com.ngen.cosys.altea.fm.model.AllPackedInOne;
import com.ngen.cosys.altea.fm.model.AllPackedInOneIdentifier;
import com.ngen.cosys.altea.fm.model.AllPackedInOneNbr;
import com.ngen.cosys.altea.fm.model.BigReferenceDetail;
import com.ngen.cosys.altea.fm.model.CargoAgentName;
import com.ngen.cosys.altea.fm.model.CargoAgentNameSeparator;
import com.ngen.cosys.altea.fm.model.CommercialLinkID;
import com.ngen.cosys.altea.fm.model.CompanyIdentification;
import com.ngen.cosys.altea.fm.model.ConnectionTime;
import com.ngen.cosys.altea.fm.model.DCSFMUpdateCargoFigures;
import com.ngen.cosys.altea.fm.model.DGSL;
import com.ngen.cosys.altea.fm.model.DGSLCode;
import com.ngen.cosys.altea.fm.model.DGSLIndicator;
import com.ngen.cosys.altea.fm.model.DGSLNbr;
import com.ngen.cosys.altea.fm.model.DLDReference;
import com.ngen.cosys.altea.fm.model.DWSFreeTextQualifier;
import com.ngen.cosys.altea.fm.model.DateTime;
import com.ngen.cosys.altea.fm.model.DeadloadFlight;
import com.ngen.cosys.altea.fm.model.DeadloadForLoad;
import com.ngen.cosys.altea.fm.model.DeadloadIndicator;
import com.ngen.cosys.altea.fm.model.DeadloadNbr;
import com.ngen.cosys.altea.fm.model.DeadloadType;
import com.ngen.cosys.altea.fm.model.DeadloadWeightAndPieces;
import com.ngen.cosys.altea.fm.model.DescriptionDetail;
import com.ngen.cosys.altea.fm.model.DescriptionItem;
import com.ngen.cosys.altea.fm.model.DispatchTime;
import com.ngen.cosys.altea.fm.model.EquipmentDetail;
import com.ngen.cosys.altea.fm.model.FlightDate;
import com.ngen.cosys.altea.fm.model.FlightDetail;
import com.ngen.cosys.altea.fm.model.FlightNumberInformation;
import com.ngen.cosys.altea.fm.model.FreeTextQualifier;
import com.ngen.cosys.altea.fm.model.Indicator;
import com.ngen.cosys.altea.fm.model.LegOrigin;
import com.ngen.cosys.altea.fm.model.LoadInfo;
import com.ngen.cosys.altea.fm.model.LoadTareWeight;
import com.ngen.cosys.altea.fm.model.LoadTypeAndData;
import com.ngen.cosys.altea.fm.model.LoadVolume;
import com.ngen.cosys.altea.fm.model.Measurement;
import com.ngen.cosys.altea.fm.model.NetWeightCentre;
import com.ngen.cosys.altea.fm.model.OriginDestination;
import com.ngen.cosys.altea.fm.model.OtherPaxDetail;
import com.ngen.cosys.altea.fm.model.OverpackForDeadload;
import com.ngen.cosys.altea.fm.model.OverpackIdentifier;
import com.ngen.cosys.altea.fm.model.OverpackNbr;
import com.ngen.cosys.altea.fm.model.PaxDetail;
import com.ngen.cosys.altea.fm.model.PhoneAndFax;
import com.ngen.cosys.altea.fm.model.Pieces;
import com.ngen.cosys.altea.fm.model.QuantityDetail;
import com.ngen.cosys.altea.fm.model.ReferenceDetail;
import com.ngen.cosys.altea.fm.model.SourceSystemInfo;
import com.ngen.cosys.altea.fm.model.StatusDetail;
import com.ngen.cosys.altea.fm.model.TelephoneNumberDetail;
import com.ngen.cosys.altea.fm.model.TextSubjectQualifier;
import com.ngen.cosys.altea.fm.model.ULDWarehouseLocation;
import com.ngen.cosys.altea.fm.model.WeightAndHeight;
import com.ngen.cosys.altea.fm.model.WorkStationAndPrinter;

/**
 * This class is used for Update carg data set preparation and limitation
 * 
 * @author NIIT Technologies Ltd
 *
 */
public class AlteaFMDatasetUtil {

   private static final Logger LOGGER = LoggerFactory.getLogger(AlteaFMDatasetUtil.class);
   
   /**
    * Flight Number Information
    * 
    * @param dcsfmUpdateCargofigures
    * @return
    */
   public static TransportIdentifierType flightNumberInformation(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures) {
      // Source
      FlightNumberInformation _flightInformation = dcsfmUpdateCargofigures.getFlightNumberInformation();
      CompanyIdentification _companyIdentification = _flightInformation.getCompanyIdentification();
      FlightDetail _flightDetail = _flightInformation.getFlightDetail();
      //
      TransportIdentifierType flightNumberInformation = new TransportIdentifierType();
      if (Objects.nonNull(_companyIdentification)) {
         CompanyIdentificationTypeI companyIdentification = new CompanyIdentificationTypeI();
         companyIdentification.setOperatingCompany(_companyIdentification.getOperatingCompany());
         flightNumberInformation.setCompanyIdentification(companyIdentification);
      }
      if (Objects.nonNull(_flightDetail)) {
         ProductIdentificationDetailsTypeI flightDetails = new ProductIdentificationDetailsTypeI();
         flightDetails.setFlightNumber(new BigInteger(_flightDetail.getFlightNumber()));
         flightNumberInformation.setFlightDetails(flightDetails);
      }
      return flightNumberInformation;
   }
   
   /**
    * Flight Date
    * 
    * @param dcsfmUpdateCargofigures
    * @param flightDates
    */
   public static void flightDate(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures,
         List<StructuredDateTimeInformationType> flightDates) {
      // Source
      List<FlightDate> _flightDates = dcsfmUpdateCargofigures.getFlightDate();
      flightDateInformation(_flightDates, flightDates);
   }
   
   /**
    * Leg Origin
    * 
    * @param dcsfmUpdateCargofigures
    * @return
    */
   public static OriginAndDestinationDetailsTypeI67289S legOrigin(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures) {
      // Source
      LegOrigin _legOrigin = dcsfmUpdateCargofigures.getLegOrigin();
      //
      OriginAndDestinationDetailsTypeI67289S legOrigin = new OriginAndDestinationDetailsTypeI67289S();
      if (Objects.nonNull(_legOrigin)) {
         legOrigin.setOrigin(_legOrigin.getOrigin());
      }
      return legOrigin;
   }
   
   /**
    * Flight Leg Date
    * 
    * @param dcsfmUpdateCargofigures
    * @param flightDates
    */
   public static void flightLegDate(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures,
         List<StructuredDateTimeInformationType> flightDates) {
      // Source
      List<FlightDate> _flightLegDates = dcsfmUpdateCargofigures.getFlightLegDate();
      flightDateInformation(_flightLegDates, flightDates);
   }
   
   /**
    * Source System Info
    * 
    * @param dcsfmUpdateCargofigures
    * @return
    */
   public static ReferenceInformationTypeI84021S sourceSystemInfo(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures) {
      // Source
      SourceSystemInfo _sourceSystemInfo = dcsfmUpdateCargofigures.getSourceSystemInfo();
      List<ReferenceDetail> _referenceDetails = _sourceSystemInfo.getReferenceDetail();
      //
      ReferenceInformationTypeI84021S sourceSystemInfo = new ReferenceInformationTypeI84021S();
      List<ReferencingDetailsTypeI128135C> referenceDetails = sourceSystemInfo.getReferenceDetails();
      //
      if (!CollectionUtils.isEmpty(_referenceDetails)) {
         ReferencingDetailsTypeI128135C referenceDetail = null;
         for (ReferenceDetail _referenceDetail : _referenceDetails) {
            referenceDetail = new ReferencingDetailsTypeI128135C();
            referenceDetail.setType(_referenceDetail.getType());
            referenceDetail.setValue(_referenceDetail.getValue());
            referenceDetails.add(referenceDetail);
         }
      }
      return sourceSystemInfo;
   }
   
   /**
    * Aircraft Type and Registration
    * 
    * @param dcsfmUpdateCargofigures
    * @return
    */
   public static EquipmentInformationType acTypeAndReg(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures) {
      // Source
      AircraftTypeRegistration _aircraftTypeRegistration = dcsfmUpdateCargofigures.getAircraftTypeRegistration();
      EquipmentDetail _equipmentDetail = _aircraftTypeRegistration.getEquipmentDetail();
      //
      EquipmentInformationType acTypeAndReg = new EquipmentInformationType();
      if (Objects.nonNull(_equipmentDetail)) {
         EquipmentIdentificationType equipmentDetails = new EquipmentIdentificationType();
         equipmentDetails.setAircraftType(_equipmentDetail.getAircraftType());
         equipmentDetails.setRegistrationNumber(_equipmentDetail.getRegistrationNumber());
         acTypeAndReg.setEquipmentDetails(equipmentDetails);
      }
      return acTypeAndReg;
   }
   
   /**
    * Agent Name for NOTOC
    * 
    * @param dcsfmUpdateCargofigures
    * @return
    */
   public static TravellerInformationTypeI agentNameForNOTOC(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures) {
      // Source
      AgentNameForNOTOC _agentNameForNOTOC = dcsfmUpdateCargofigures.getAgentNameForNOTOC();
      //
      PaxDetail _paxDetail = null;
      OtherPaxDetail _otherPaxDetail = null;
      if (Objects.nonNull(_agentNameForNOTOC)) {
         _paxDetail = _agentNameForNOTOC.getPaxDetail();
         _otherPaxDetail = _agentNameForNOTOC.getOtherPaxDetail();
      }
      //
      TravellerInformationTypeI agentNameForNOTOC = new TravellerInformationTypeI();
      return cargoAgentInformation(agentNameForNOTOC, _paxDetail, _otherPaxDetail);
   }
   
   /**
    * Cargo Agent Name Separator
    * 
    * @param dcsfmUpdateCargofigures
    * @return
    */
   public static DummySegmentTypeI cargoAgentNameSeparator(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures) {
      // Source
      CargoAgentNameSeparator _cargoAgentNameForSeparator = dcsfmUpdateCargofigures.getCargoAgentNameForSeparator();
      //
      DummySegmentTypeI cargoAgentNameSeparator = new DummySegmentTypeI();
      if (Objects.nonNull(_cargoAgentNameForSeparator)) {
         //
      }
      return cargoAgentNameSeparator;
   }
   
   /**
    * Cargo Agent Name
    * 
    * @param dcsfmUpdateCargofigures
    * @return
    */
   public static TravellerInformationTypeI cargoAgentName(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures) {
      // Source
      CargoAgentName _cargoAgentName = dcsfmUpdateCargofigures.getCargoAgentName();
      //
      PaxDetail _paxDetail = null;
      OtherPaxDetail _otherPaxDetail = null;
      if (Objects.nonNull(_cargoAgentName)) {
         _paxDetail = _cargoAgentName.getPaxDetail();
         _otherPaxDetail = _cargoAgentName.getOtherPaxDetail();
      }
      //
      TravellerInformationTypeI cargoAgentName = new TravellerInformationTypeI();
      return cargoAgentInformation(cargoAgentName, _paxDetail, _otherPaxDetail);
   }
   
   /**
    * Work Station and Printer
    * 
    * @param dcsfmUpdateCargofigures
    * @return
    */
   public static ReferenceInformationTypeI84045S workStationAndPrinter(
         DCSFMUpdateCargoFigures dcsfmUpdateCargofigures) {
      // Source
      WorkStationAndPrinter _workStationAndPrinter = dcsfmUpdateCargofigures.getWorkStationAndPrinter();
      //
      List<ReferenceDetail> _referenceDetails = Collections.emptyList();
      if (Objects.nonNull(_workStationAndPrinter)) {
         _referenceDetails = _workStationAndPrinter.getReferenceDetail();
      }
      //
      ReferenceInformationTypeI84045S workStationAndPrinter = null;
      List<ReferencingDetailsTypeI128135C> referenceDetails = Collections.emptyList();
      //
      if (!CollectionUtils.isEmpty(_referenceDetails)) {
         workStationAndPrinter = new ReferenceInformationTypeI84045S();
         referenceDetails = workStationAndPrinter.getReferenceDetails();
         ReferencingDetailsTypeI128135C referenceDetail = null;
         //
         for (ReferenceDetail _referenceDetail : _referenceDetails) {
            referenceDetail = new ReferencingDetailsTypeI128135C();
            referenceDetail.setType(_referenceDetail.getType());
            referenceDetail.setValue(_referenceDetail.getValue());
            referenceDetails.add(referenceDetail);
         }
      }
      return workStationAndPrinter;
   }
   
   /**
    * Phone and Fax
    * 
    * @param dcsfmUpdateCargofigures
    * @param phoneAndFax
    */
   public static void phoneAndFax(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures,
         List<PhoneAndEmailAddressType> phoneAndFax) {
      // Source
      List<PhoneAndFax> _phoneAndFaxDetails = dcsfmUpdateCargofigures.getPhoneAndFax();
      //
      if (!CollectionUtils.isEmpty(_phoneAndFaxDetails)) {
         PhoneAndEmailAddressType phoneAndEmailAddressType = null;
         for (PhoneAndFax _phoneAndFax : _phoneAndFaxDetails) {
            phoneAndEmailAddressType = new PhoneAndEmailAddressType();
            phoneAndEmailAddressType.setPhoneOrEmailType(_phoneAndFax.getPhoneOrEmailType());
            StructuredTelephoneNumberType telephoneNumberDetails = null;
            TelephoneNumberDetail telephoneNumberDetail = _phoneAndFax.getTelephoneNumberDetail();
            if (Objects.nonNull(telephoneNumberDetail)) {
               telephoneNumberDetails = new StructuredTelephoneNumberType();
               telephoneNumberDetails.setTelephoneNumber(telephoneNumberDetail.getTelephoneNumber());
               phoneAndEmailAddressType.setTelephoneNumberDetails(telephoneNumberDetails);
            }
         }
      }
   }

   /**
    * DWS Comments
    * 
    * @param dcsfmUpdateCargofigures
    * @return
    */
   public static InteractiveFreeTextTypeI84224S dwsComments(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures) {
      // Source
      DWSFreeTextQualifier _dwsComments = dcsfmUpdateCargofigures.getDwsComments();
      //
      TextSubjectQualifier _textSubjectQualifier = null;
      List<String> _freeTexts = Collections.emptyList();
      List<String> freeTexts = Collections.emptyList();
      if (Objects.nonNull(_dwsComments)) {
         _textSubjectQualifier = _dwsComments.getTextSubjectQualifier();
         _freeTexts = _dwsComments.getFreeText();
      }
      //
      InteractiveFreeTextTypeI84224S dwsComments = null;
      if (Objects.nonNull(_textSubjectQualifier)) {
         dwsComments = new InteractiveFreeTextTypeI84224S();
         FreeTextQualificationTypeI freeTextQualification = new FreeTextQualificationTypeI();
         freeTextQualification.setTextSubjectQualifier(_textSubjectQualifier.getTextSubjectQualifier());
         dwsComments.setFreeTextQualification(freeTextQualification);
      }
      //
      if (!CollectionUtils.isEmpty(_freeTexts)) {
         freeTexts = dwsComments.getFreeText();
         for (String freeText : _freeTexts) {
            freeTexts.add(freeText);
         }
      }
      return dwsComments;
   }
   
   /**
    * Indicator
    * 
    * @param dcsfmUpdateCargofigures
    * @return
    */
   public static StatusTypeI84225S indicators(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures) {
      // Source
      Indicator _indicator = dcsfmUpdateCargofigures.getIndicator();
      //
      StatusDetail _statusDetails = null;
      if (Objects.nonNull(_indicator)) {
         _statusDetails = _indicator.getStatusDetails();
      }
      //
      StatusTypeI84225S indicators = new StatusTypeI84225S();
      if (Objects.nonNull(_statusDetails)) {
         StatusDetailsTypeI statusDetails = new StatusDetailsTypeI();
         statusDetails.setIndicator(_statusDetails.getIndicator());
         indicators.setStatusDetails(statusDetails);
      }
      return indicators;
   }
   
   /**
    * Dispatch Time
    * 
    * @param dcsfmUpdateCargofigures
    * @return
    */
   public static StructuredDateTimeInformationType84018S dispatchTime(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures) {
      // Source
      DispatchTime _dispatchTime = dcsfmUpdateCargofigures.getDispatchTime();
      //
      DateTime _dateTime = null;
      StructuredDateTimeInformationType84018S dispatchTime = new StructuredDateTimeInformationType84018S();
      if (Objects.nonNull(_dispatchTime)) {
         _dateTime = _dispatchTime.getDateTime();
         dispatchTime.setBusinessSemantic(_dispatchTime.getBusinessSemantic());
         dispatchTime.setTimeMode(_dispatchTime.getTimeMode());
      }
      //
      if (Objects.nonNull(_dateTime)) {
         StructuredDateTimeType129165C dateTime = new StructuredDateTimeType129165C();
         dateTime.setYear(String.valueOf(_dateTime.getYear()));
         dateTime.setMonth(String.valueOf(_dateTime.getMonth()));
         dateTime.setDay(String.valueOf(_dateTime.getDay()));
         dateTime.setHour(String.valueOf(_dateTime.getHour()));
         dateTime.setMinutes(String.valueOf(_dateTime.getMinutes()));
         dateTime.setSeconds(BigInteger.valueOf(_dateTime.getSeconds()));
         dispatchTime.setDateTime(dateTime);
      }
      return dispatchTime;
   }
   
   /**
    * @param dcsfmUpdateCargofigures
    * @param loadInfo
    */
   public static void loadInfo(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures,
         List<com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo> loadInfo) {
      LOGGER.warn("Altea FM Message Payload load info - Progress {}");
      // Source
      List<LoadInfo> _loadInfoDetails = dcsfmUpdateCargofigures.getLoadInfo();
      //
      if (!CollectionUtils.isEmpty(_loadInfoDetails)) {
         com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo loadInfoDetail = null;
         for (LoadInfo _loadInfo : _loadInfoDetails) {
            //
            loadInfoDetail = new com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo();
            // Load Type and Data
            loadInfoDetail.setLoadTypeAndData(loadTypeAndData(_loadInfo));
            // Barrow ID
            //loadInfoDetail.setBarrowID(barrowID(_loadInfo));
            // Load TareWeight
            loadInfoDetail.setLoadTareWeight(loadTareWeight(_loadInfo));
            // Load Volume
            //loadInfoDetail.setLoadVolume(loadVolume(_loadInfo));
            // Load Description
            loadInfoDetail.setLoadDescription(loadDescription(_loadInfo));
            // BigReference Details
            //bigReferenceDetails(_loadInfo, loadInfoDetail.getBIGReferenceDetails());
            // Load Indicators
            //loadIndicators(_loadInfo, loadInfoDetail.getLoadIndicators());
            // Measurements
            //measurements(_loadInfo, loadInfoDetail.getMeasurements());
            // Load Origin and Destination
            loadInfoDetail.setLoadOriginDest(loadOriginDest(_loadInfo));
            // ULD Warehouse Location
            //loadInfoDetail.setUldWarehouseLocation(uldWarehouseLocation(_loadInfo));
            // Linked ULD
            //linkedULDId(_loadInfo, loadInfoDetail.getLinkedULDId());
            // Net Weight Centre
            //netWeightCentre(_loadInfo, loadInfoDetail.getNetWeightCentre());
            // Load Separator
            loadInfoDetail.setLoadSeparator(loadSeparator(_loadInfo));
            // Deadload for Load
            deadloadForLoad(_loadInfo, loadInfoDetail.getDeadloadForLoad());
            //
            loadInfo.add(loadInfoDetail);
         }
      }
   }
   
   /**
    * Load Type and Data
    * 
    * @param _loadInfo
    * @return
    */
   private static ReferenceInformationTypeI loadTypeAndData(LoadInfo _loadInfo) {
      // Source
      LoadTypeAndData _loadTypeAndData = _loadInfo.getLoadTypeAndData();
      //
      ReferenceInformationTypeI loadTypeAndData = null;
      if (Objects.nonNull(_loadTypeAndData)) {
         loadTypeAndData = new ReferenceInformationTypeI();
         List<ReferenceDetail> _referenceDetails = _loadTypeAndData.getReferenceDetail();
         List<ReferencingDetailsTypeI139763C> referenceDetails = loadTypeAndData.getReferenceDetails();
         ReferencingDetailsTypeI139763C referenceDetail = null;
         for (ReferenceDetail _referenceDetail : _referenceDetails) {
            referenceDetail = new ReferencingDetailsTypeI139763C();
            referenceDetail.setType(_referenceDetail.getType());
            referenceDetail.setValue(_referenceDetail.getValue());
            referenceDetails.add(referenceDetail);
         }
      }
      return loadTypeAndData;
   }
   
   /**
    * Barrow ID
    * 
    * @param _loadInfo
    * @return
    */
   private static ItemDescriptionTypeI barrowID(LoadInfo _loadInfo) {
      // Source
      DescriptionItem _barrowID = _loadInfo.getBarrowID();
      //
      DescriptionDetail _descriptionDetails = null;
      ItemDescriptionTypeI barrowID = null;
      if (Objects.nonNull(_barrowID)) {
         barrowID = new ItemDescriptionTypeI();
         _descriptionDetails = _barrowID.getDescriptionDetails();
         barrowID.setType(_barrowID.getType());
      }
      //
      if (Objects.nonNull(_descriptionDetails)) {
         ItemDescriptionDetailsTypeI descriptionDetails = new ItemDescriptionDetailsTypeI();
         descriptionDetails.setIdentifier(_descriptionDetails.getDescription());
         barrowID.setDescriptionDetails(descriptionDetails);
      }
      return barrowID;
   }
   
   /**
    * Load Tare Weight
    * 
    * @param _loadInfo
    * @return
    */
   private static QuantityType83982S loadTareWeight(LoadInfo _loadInfo) {
      // Source
      LoadTareWeight _loadTareWeight = _loadInfo.getLoadTareWeight();
      //
      QuantityType83982S loadTareWeight = null;
      if (Objects.nonNull(_loadTareWeight)) {
         loadTareWeight = new QuantityType83982S();
         if (!CollectionUtils.isEmpty(_loadTareWeight.getQuantityDetail())) {
            List<QuantityDetailsTypeI> quantityDetails = loadTareWeight.getQuantityDetails();
            QuantityDetailsTypeI quantityDetail = null;
            for (QuantityDetail _quantityDetail : _loadTareWeight.getQuantityDetail()) {
               quantityDetail = new QuantityDetailsTypeI();
               quantityDetail.setQualifier(_quantityDetail.getQualifier());
               quantityDetail.setValue(_quantityDetail.getValue());
               quantityDetails.add(quantityDetail);
            }
         }
      }
      return loadTareWeight;
   }
   
   /**
    * Load Volume
    * 
    * @param _loadInfo
    * @return
    */
   private static LoadDetailsInformationType loadVolume(LoadInfo _loadInfo) {
      // Source
      LoadVolume _loadVolume = _loadInfo.getLoadVolume();
      //
      LoadDetailsInformationType loadVolume = null;
      if (Objects.nonNull(_loadVolume)) {
         loadVolume = new LoadDetailsInformationType();
         if (Objects.nonNull(_loadVolume.getLoadDetail())) {
            LoadDetailsType loadDetails = new LoadDetailsType();
            loadDetails.setFullEmptyIndicator(_loadVolume.getLoadDetail().getFullEmptyIndicator());
            loadVolume.setLoadDetails(loadDetails);
         }
      }
      return loadVolume;
   }
   
   /**
    * Load Description
    * 
    * @param _loadInfo
    * @return
    */
   private static InteractiveFreeTextTypeI loadDescription(LoadInfo _loadInfo) {
      // Source
      FreeTextQualifier _loadDescription = _loadInfo.getLoadDescription();
      //
      InteractiveFreeTextTypeI loadDescription = null;
      if (Objects.nonNull(_loadDescription)) {
         loadDescription = new InteractiveFreeTextTypeI();
         TextSubjectQualifier _textSubjectQualifier = _loadDescription.getTextSubjectQualifier();
         if (Objects.nonNull(_textSubjectQualifier)) {
            FreeTextQualificationTypeI freeTextQualification = new FreeTextQualificationTypeI();
            freeTextQualification.setTextSubjectQualifier(_textSubjectQualifier.getTextSubjectQualifier());
            loadDescription.setFreeTextQualification(freeTextQualification);
         }
         loadDescription.setFreeText(_loadDescription.getFreeText());
      }
      return loadDescription;
   }
   
   /**
    * Big Reference Details
    * 
    * @param _loadInfo
    * @param bigReferenceDetails
    */
   private static void bigReferenceDetails(LoadInfo _loadInfo,
         List<ReferenceInformationTypeI92892S> bigReferenceDetails) {
      // Source
      List<BigReferenceDetail> _bigReferenceDetails = _loadInfo.getBigReferenceDetail();
      //
      if (!CollectionUtils.isEmpty(_bigReferenceDetails)) {
         for (BigReferenceDetail _bigReferenceDetail : _bigReferenceDetails) {
            ReferenceInformationTypeI92892S bigReferenceDetail = null;
            if (Objects.nonNull(_bigReferenceDetail.getReferenceDetail())) {
               bigReferenceDetail = new ReferenceInformationTypeI92892S();
               ReferencingDetailsTypeI139763C referenceDetail = new ReferencingDetailsTypeI139763C();
               referenceDetail.setType(_bigReferenceDetail.getReferenceDetail().getType());
               referenceDetail.setValue(_bigReferenceDetail.getReferenceDetail().getValue());
               bigReferenceDetail.setReferenceDetails(referenceDetail);
            }
            bigReferenceDetails.add(bigReferenceDetail);
         }
      }
   }
   
   /**
    * Load Indicators
    * 
    * @param _loadInfo
    * @param loadIndicators
    */
   private static void loadIndicators(LoadInfo _loadInfo, List<StatusType> loadIndicators) {
      // Source
      List<Indicator> _loadIndicators = _loadInfo.getLoadIndicator();
      //
      if (!CollectionUtils.isEmpty(_loadIndicators)) {
         StatusType statusType = null;
         for (Indicator _indicator : _loadIndicators) {
            statusType = new StatusType();
            StatusDetailsType statusDetail = null;
            if (Objects.nonNull(_indicator.getStatusDetails())) {
               statusDetail = new StatusDetailsType();
               statusDetail.setIndicator(_indicator.getStatusDetails().getIndicator());
               statusDetail.setAction(_indicator.getStatusDetails().getAction());
               statusType.setStatusDetails(statusDetail);
            }
            loadIndicators.add(statusType);
         }
      }
   }
   
   /**
    * Measurements
    * 
    * @param _loadInfo
    * @param measurements
    */
   private static void measurements(LoadInfo _loadInfo, List<MeasurementsType92900S> measurements) {
      // Source
      List<Measurement> _measurementDetails = _loadInfo.getMeasurement();
      //
      if (!CollectionUtils.isEmpty(_measurementDetails)) {
         MeasurementsType92900S measurement = null;
         ValueRangeTypeI valueRange = null;
         for (Measurement _measurement : _measurementDetails) {
            measurement = new MeasurementsType92900S();
            measurement.setMeasurementQualifier(_measurement.getMeasurementQualifier());
            if (Objects.nonNull(_measurement.getValueRange())) {
               valueRange = new ValueRangeTypeI();
               valueRange.setUnit(_measurement.getValueRange().getUnit());
               valueRange.setValue(new BigDecimal(_measurement.getValueRange().getValue()));
               measurement.setValueRange(valueRange);
            }
            measurements.add(measurement);
         }
      }
   }
   
   /**
    * Origin and Destination details type
    * 
    * @param _loadInfo
    * @return
    */
   private static OriginAndDestinationDetailsTypeI loadOriginDest(LoadInfo _loadInfo) {
      // Source
      OriginDestination _loadOriginDestination = _loadInfo.getLoadOriginDestination();
      //
      OriginAndDestinationDetailsTypeI loadOriginDest = null;
      if (Objects.nonNull(_loadOriginDestination)) {
         loadOriginDest = new OriginAndDestinationDetailsTypeI();
         loadOriginDest.setOrigin(_loadOriginDestination.getOrigin());
         loadOriginDest.setDestination(_loadOriginDestination.getDestination());
      }
      return loadOriginDest;
   }
   
   /**
    * ULD Warehouse Location
    * 
    * @param _loadInfo
    * @return
    */
   private static ReferenceInformationTypeI92892S uldWarehouseLocation(LoadInfo _loadInfo) {
      // Source
      ULDWarehouseLocation _uldWarehouseLocation = _loadInfo.getUldWarehouseLocation();
      //
      ReferenceInformationTypeI92892S uldWarehouseLocation = null;
      if (Objects.nonNull(_uldWarehouseLocation)) {
         uldWarehouseLocation = new ReferenceInformationTypeI92892S();
         if (Objects.nonNull(_uldWarehouseLocation.getReferenceDetail())) {
            ReferencingDetailsTypeI139763C referenceDetail = new ReferencingDetailsTypeI139763C();
            referenceDetail.setType(_uldWarehouseLocation.getReferenceDetail().getType());
            referenceDetail.setValue(_uldWarehouseLocation.getReferenceDetail().getValue());
            uldWarehouseLocation.setReferenceDetails(referenceDetail);
         }
      }
      return uldWarehouseLocation;
   }
   
   /**
    * Linked ULD Ids
    * 
    * @param _loadInfo
    * @param linkedULDIds
    * @return
    */
   private static void linkedULDId(LoadInfo _loadInfo, List<ItemDescriptionTypeI92894S> linkedULDIds) {
      // Source
      List<DescriptionItem> _linkedULDIds = _loadInfo.getLinkedULDId();
      //
      if (!CollectionUtils.isEmpty(_linkedULDIds)) {
         ItemDescriptionTypeI92894S descriptionType = null;
         ItemDescriptionDetailsTypeI139765C descriptionDetail = null;
         for (DescriptionItem _descriptionItem : _linkedULDIds) {
            descriptionType = new ItemDescriptionTypeI92894S();
            descriptionType.setType(_descriptionItem.getType());
            descriptionDetail = new ItemDescriptionDetailsTypeI139765C();
            if (Objects.nonNull(_descriptionItem.getDescriptionDetails())) {
               descriptionDetail.setDescription(_descriptionItem.getDescriptionDetails().getDescription());
               descriptionType.setDescriptionDetails(descriptionDetail);
            }
            linkedULDIds.add(descriptionType);
         }
      }
   }
   
   /**
    * Net weight Centre
    * 
    * @param _loadInfo
    * @param netWeightCentre
    */
   private static void netWeightCentre(LoadInfo _loadInfo,
         List<com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.NetWeightCentre> netWeightCentreDetails) {
      // Source
      List<NetWeightCentre> _netWeightCentreDetails = _loadInfo.getNetWeightCentre();
      //
      if (!CollectionUtils.isEmpty(_netWeightCentreDetails)) {
         com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.NetWeightCentre netWeightCentre = null;
         for (NetWeightCentre _netWeightCentre : _netWeightCentreDetails) {
            netWeightCentre = new com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.NetWeightCentre();
            MeasurementsType netWeightCentrePos = null;
            QuantityTypeI weight = null;
            //
            if (Objects.nonNull(_netWeightCentre.getNetWeightCentrePosition())) {
               netWeightCentrePos = new MeasurementsType();
               netWeightCentrePos
                     .setMeasurementQualifier(_netWeightCentre.getNetWeightCentrePosition().getMeasurementQualifier());
               if (Objects.nonNull(_netWeightCentre.getNetWeightCentrePosition().getValueRange())) {
                  ValueRangeType valueRange = new ValueRangeType();
                  valueRange.setUnit(_netWeightCentre.getNetWeightCentrePosition().getValueRange().getUnit());
                  valueRange.setValue(
                        new BigDecimal(_netWeightCentre.getNetWeightCentrePosition().getValueRange().getValue()));
                  netWeightCentrePos.setValueRange(valueRange);
               }
               netWeightCentre.setNetWeightCentrePos(netWeightCentrePos);
            }
            if (Objects.nonNull(_netWeightCentre.getWeight())) {
               weight = new QuantityTypeI();
               if (Objects.nonNull(_netWeightCentre.getWeight().getQuantityDetail())) {
                  QuantityDetailsTypeI quantityDetails = new QuantityDetailsTypeI();
                  quantityDetails.setQualifier(_netWeightCentre.getWeight().getQuantityDetail().getQualifier());
                  quantityDetails.setValue(_netWeightCentre.getWeight().getQuantityDetail().getValue());
                  weight.setQuantityDetails(quantityDetails);
               }
               netWeightCentre.setWeight(weight);
            }
            netWeightCentreDetails.add(netWeightCentre);
         }
      }
   }
   
   /**
    * Load Separator
    * 
    * @param _loadInfo
    * @return
    */
   private static DummySegmentTypeI loadSeparator(LoadInfo _loadInfo) {
      DummySegmentTypeI loadSeparator = new DummySegmentTypeI();
      return loadSeparator;
   }
   
   /**
    * Deadload for Load
    * 
    * @param _loadInfo
    * @param deadloadForLoad
    * @return
    */
   private static void deadloadForLoad(LoadInfo _loadInfo,
         List<com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad> deadloadForLoad) {
      // Source
      List<DeadloadForLoad> _deadloadForLoadDetails = _loadInfo.getDeadloadForLoad();
      if (!CollectionUtils.isEmpty(_deadloadForLoadDetails)) {
         com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad deadloadForLoadDetail = null;
         for (DeadloadForLoad _deadloadForLoad : _deadloadForLoadDetails) {
            //
            deadloadForLoadDetail = new com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad();
            // Deadload Type
            deadloadForLoadDetail.setDeadloadType(deadloadType(_deadloadForLoad));
            // Deadload Number
            deadloadForLoadDetail.setDeadloadNbr(deadloadNbr(_deadloadForLoad));
            // DLD References
            deadloadForLoadDetail.setDldReferences(dldReferences(_deadloadForLoad));
            // Connection Time
            deadloadForLoadDetail.setConnectionTime(connectionTime(_deadloadForLoad));
            // Deadload Origin and Destination
            deadloadForLoadDetail.setDeadloadOriginDest(deadloadOriginDest(_deadloadForLoad));
            // Commercial Link ID
            deadloadForLoadDetail.setCommercialLinkID(commercialLinkID(_deadloadForLoad));
            // Deadload Weight and Pieces
            deadloadWeightAndPieces(_deadloadForLoad, deadloadForLoadDetail.getDeadloadWeightAndPieces());
            // Deadload Flights
            deadloadForLoadDetail.setDeadloadFlights(deadloadFlights(_deadloadForLoad));
            // Deadload Indicators
            deadloadForLoadDetail.setDeadloadIndicators(deadloadIndicators(_deadloadForLoad));
            // Deadload Description
            deadloadForLoadDetail.setDeadloadDescription(deadloadDescription(_deadloadForLoad));
            // DGSL for Deadload
            dgslForDeadload(_deadloadForLoad, deadloadForLoadDetail.getDgslForDeadload());
            // AllPacked In One for Deadload
            allPackedInOneForDeadload(_deadloadForLoad, deadloadForLoadDetail.getAllPackedInOneForDeadload());
            // Overpack for Deadload
            overpackForDeadload(_deadloadForLoad, deadloadForLoadDetail.getOverpackForDeadload());
            //
            deadloadForLoad.add(deadloadForLoadDetail);
         }
      }
   }
   
   /**
    * Deadoad Type
    * 
    * @param _deadloadForLoad
    * @return
    */
   private static LoadDetailsInformationType92764S deadloadType(DeadloadForLoad _deadloadForLoad) {
      // Source
      DeadloadType _deadloadType = _deadloadForLoad.getDeadloadType();
      //
      LoadDetailsInformationType92764S deadloadType = null;
      if (Objects.nonNull(_deadloadType)) {
         deadloadType = new LoadDetailsInformationType92764S();
         if (Objects.nonNull(_deadloadType.getCargoDetail())) {
            CargoDetailsType cargoDetails = new CargoDetailsType();
            cargoDetails.setNatureCargo(_deadloadType.getCargoDetail().getNatureOfCargo());
            cargoDetails.setCargoSubtype(_deadloadType.getCargoDetail().getCargoSubtype());
            deadloadType.setCargoDetails(cargoDetails);
         }
         // TODO
         deadloadType.setPriorityNumber(null);
      }
      return deadloadType;
   }
   
   /**
    * Deadload Nbr
    * 
    * @param _deadloadForLoad
    * @return
    */
   private static ReferenceInformationTypeI83948S deadloadNbr(DeadloadForLoad _deadloadForLoad) {
      // Source
      DeadloadNbr _deadloadNbr = _deadloadForLoad.getDeadloadNbr();
      //
      ReferenceInformationTypeI83948S deadloadNbr = null;
      if (Objects.nonNull(_deadloadNbr)) {
         deadloadNbr = new ReferenceInformationTypeI83948S();
         ReferenceDetail _referenceDetail = _deadloadNbr.getReferenceDetail();
         if (Objects.nonNull(_referenceDetail)) {
            ReferencingDetailsTypeI referenceDetails = new ReferencingDetailsTypeI();
            referenceDetails.setType(_referenceDetail.getType());
            referenceDetails.setValue(_referenceDetail.getValue());
            deadloadNbr.setReferenceDetails(referenceDetails);
         }
      }
      return deadloadNbr;
   }
   
   /**
    * DLD References
    * 
    * @param _deadloadForLoad
    * @return
    */
   private static ReferenceInfoType175451S dldReferences(DeadloadForLoad _deadloadForLoad) {
      // Source
      DLDReference _dldReference = _deadloadForLoad.getDldReference();
      //
      ReferenceInfoType175451S dldReferences = null;
      if (Objects.nonNull(_dldReference)) {
         dldReferences = new ReferenceInfoType175451S();
         ReferenceDetail _referenceDetail = _dldReference.getReferenceDetail();
         if (Objects.nonNull(_referenceDetail)) {
            ReferencingDetailsType247148C referenceDetails = new ReferencingDetailsType247148C();
            referenceDetails.setType(_referenceDetail.getType());
            referenceDetails.setValue(_referenceDetail.getValue());
            dldReferences.setReferenceDetails(referenceDetails);
         }
      }
      return dldReferences;
   }
   
   /**
    * Connection Time
    * 
    * @param _deadloadForLoad
    * @return
    */
   private static QuantityType connectionTime(DeadloadForLoad _deadloadForLoad) {
      // Source
      ConnectionTime _connectionTime = _deadloadForLoad.getConnectionTime();
      //
      QuantityType connectionTime = null;
      if (Objects.nonNull(_connectionTime)) {
         connectionTime = new QuantityType();
         QuantityDetail _quantityDetail = _connectionTime.getQuantityDetail();
         if (Objects.nonNull(_quantityDetail)) {
            QuantityDetailsType quantityDetails = new QuantityDetailsType();
            quantityDetails.setQualifier(_quantityDetail.getQualifier());
            quantityDetails.setValue(_quantityDetail.getValue());
            quantityDetails.setUnit(_quantityDetail.getUnit());
            connectionTime.setQuantityDetails(quantityDetails);
         }
      }
      return connectionTime;
   }
   
   /**
    * Deadload Origin And Destination
    * 
    * @param _deadloadForLoad
    * @return
    */
   private static OriginAndDestinationDetailsTypeI deadloadOriginDest(DeadloadForLoad _deadloadForLoad) {
      // Source
      OriginDestination _deadloadOriginDest = _deadloadForLoad.getDeadloadOriginDest();
      //
      OriginAndDestinationDetailsTypeI deadloadOriginDest = null;
      if (Objects.nonNull(_deadloadOriginDest)) {
         deadloadOriginDest = new OriginAndDestinationDetailsTypeI();
         deadloadOriginDest.setOrigin(_deadloadOriginDest.getOrigin());
         deadloadOriginDest.setDestination(_deadloadOriginDest.getDestination());
      }
      return deadloadOriginDest;
   }
   
   /**
    * Commercial Link ID
    * 
    * @param _deadloadForLoad
    * @return
    */
   private static ReferenceInformationTypeI83948S commercialLinkID(DeadloadForLoad _deadloadForLoad) {
      // Source
      CommercialLinkID _commercialLinkID = _deadloadForLoad.getCommercialLinkID();
      //
      ReferenceInformationTypeI83948S commercialLinkID = null;
      if (Objects.nonNull(_commercialLinkID)) {
         commercialLinkID = new ReferenceInformationTypeI83948S();
         if (Objects.nonNull(_commercialLinkID.getReferenceDetail())) {
            ReferencingDetailsTypeI referenceDetail = new ReferencingDetailsTypeI();
            referenceDetail.setType(_commercialLinkID.getReferenceDetail().getType());
            referenceDetail.setValue(_commercialLinkID.getReferenceDetail().getValue());
            commercialLinkID.setReferenceDetails(referenceDetail);
         }
      }
      return commercialLinkID;
   }
   
   /**
    * Deadload Weight and Pieces
    * 
    * @param _deadloadForLoad
    * @param deadloadWeightAndPieces
    */
   private static void deadloadWeightAndPieces(DeadloadForLoad _deadloadForLoad,
         List<QuantityType92784S> deadloadWeightAndPieces) {
      // Source
      List<DeadloadWeightAndPieces> _deadloadWeightAndPiecesDetails = _deadloadForLoad.getDeadloadWeightAndPieces();
      //
      if (!CollectionUtils.isEmpty(_deadloadWeightAndPiecesDetails)) {
         QuantityType92784S quantityType = new QuantityType92784S();
         QuantityDetailsTypeI quantityDetails = null;
         for (DeadloadWeightAndPieces _deadloadWeightAndPieces : _deadloadWeightAndPiecesDetails) {
            QuantityDetail _deadloadWeight = _deadloadWeightAndPieces.getDeadloadWeight();
            if (Objects.nonNull(_deadloadWeight)) {
               quantityDetails = new QuantityDetailsTypeI();
               quantityDetails.setQualifier(_deadloadWeight.getQualifier());
               quantityDetails.setValue(_deadloadWeight.getValue());
               quantityType.setQuantityDetails(quantityDetails);
            }
            deadloadWeightAndPieces.add(quantityType);
         }
      }
   }
   
   /**
    * Deadload Flights
    * 
    * @param _deadloadForLoad
    * @return
    */
   private static FlightDetailsQueryType deadloadFlights(DeadloadForLoad _deadloadForLoad) {
      // Source
      DeadloadFlight _deadloadFlight = _deadloadForLoad.getDeadloadFlight();
      //
      FlightDetailsQueryType deadloadFlights = null;
      if (Objects.nonNull(_deadloadFlight)) {
         deadloadFlights = new FlightDetailsQueryType();
         //
         if (Objects.nonNull(_deadloadFlight.getCarrierDetail())) {
            OutboundCarrierDetailsTypeI carrierDetail = new OutboundCarrierDetailsTypeI();
            carrierDetail.setOperatingCarrier(_deadloadFlight.getCarrierDetail().getOperatingCarrier());
            deadloadFlights.setCarrierDetails(carrierDetail);
         }
         if (Objects.nonNull(_deadloadFlight.getFlightDetail())) {
            OutboundFlightNumberDetailstypeI flightDetail = new OutboundFlightNumberDetailstypeI();
            flightDetail.setFlightNumber(new BigInteger(_deadloadFlight.getFlightDetail().getFlightNumber()));
            flightDetail.setOperationalSuffix(_deadloadFlight.getFlightDetail().getOperationalSuffix());
            deadloadFlights.setFlightDetails(flightDetail);
         }
         deadloadFlights.setOffPoint(_deadloadFlight.getOffPoint());
         if (Objects.nonNull(_deadloadFlight.getInboundCarrierDetail())) {
            InboundCarrierDetailsTypeI inboundCarrierDetail = new InboundCarrierDetailsTypeI();
            inboundCarrierDetail.setCarrier(_deadloadFlight.getInboundCarrierDetail().getInboundCarrier());
            deadloadFlights.setInboundCarrierDetails(inboundCarrierDetail);
         }
         if (Objects.nonNull(_deadloadFlight.getInboundFlightDetail())) {
            InboundFlightNumberDetailsTypeI inboundFlightDetail = new InboundFlightNumberDetailsTypeI();
            inboundFlightDetail
                  .setFlightNumber(new BigInteger(_deadloadFlight.getInboundFlightDetail().getFlightNumber()));
            inboundFlightDetail.setOperationalSuffix(_deadloadFlight.getInboundFlightDetail().getOperationalSuffix());
            deadloadFlights.setInboundFlightDetails(inboundFlightDetail);
         }
      }
      return deadloadFlights;
   }
   
   /**
    * Deadload Indicators
    * 
    * @param _deadloadForLoad
    * @return
    */
   private static StatusTypeI deadloadIndicators(DeadloadForLoad _deadloadForLoad) {
      // Source
      DeadloadIndicator _deadloadIndicator = _deadloadForLoad.getDeadloadIndicator();
      //
      StatusTypeI deadloadIndicator = null;
      if (Objects.nonNull(_deadloadIndicator)) {
         deadloadIndicator = new StatusTypeI();
         StatusDetail _statusDetails = _deadloadIndicator.getStatusDetails();
         List<StatusDetail> _otherStatusDetails = _deadloadIndicator.getOtherStatusDetails();
         if (Objects.nonNull(_statusDetails)) {
            StatusDetailsTypeI128105C statusDetails = new StatusDetailsTypeI128105C();
            statusDetails.setIndicator(_statusDetails.getIndicator());
            statusDetails.setAction(_statusDetails.getAction());
            deadloadIndicator.setStatusDetails(statusDetails);
         }
         if (!CollectionUtils.isEmpty(_otherStatusDetails)) {
            List<StatusDetailsTypeI171610C> otherStatusDetails = deadloadIndicator.getOtherStatusDetails();
            StatusDetailsTypeI171610C statusDetailsType = null;
            for (StatusDetail _statusDetail : _otherStatusDetails) {
               statusDetailsType = new StatusDetailsTypeI171610C();
               statusDetailsType.setIndicator(_statusDetail.getIndicator());
               statusDetailsType.setAction(_statusDetail.getAction());
               otherStatusDetails.add(statusDetailsType);
            }
         }
      }
      return deadloadIndicator;
   }
   
   /**
    * Free Text Qualification
    * 
    * @param _deadloadForLoad
    * @return
    */
   private static InteractiveFreeTextTypeI deadloadDescription(DeadloadForLoad _deadloadForLoad) {
      // Source
      FreeTextQualifier _deadloadDescription = _deadloadForLoad.getDeadloadDescription();
      //
      InteractiveFreeTextTypeI deadloadDescription = null;
      if (Objects.nonNull(deadloadDescription)) {
         deadloadDescription = new InteractiveFreeTextTypeI();
         TextSubjectQualifier _textSubjectQualifier = _deadloadDescription.getTextSubjectQualifier();
         if (Objects.nonNull(_textSubjectQualifier)) {
            FreeTextQualificationTypeI freeTextQualification = new FreeTextQualificationTypeI();
            freeTextQualification.setTextSubjectQualifier(_textSubjectQualifier.getTextSubjectQualifier());
            deadloadDescription.setFreeTextQualification(freeTextQualification);
         }
         deadloadDescription.setFreeText(_deadloadDescription.getFreeText());
      }
      return deadloadDescription;
   }
   
   /**
    * DGSL for Dead Load
    * 
    * @param _deadloadForLoad
    * @param dgslForDeadload
    */
   private static void dgslForDeadload(DeadloadForLoad _deadloadForLoad,
         List<com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.DgslForDeadload> dgslForDeadload) {
      // Source
      List<DGSL> _dgslForDeadload = _deadloadForLoad.getDgslForDeadload();
      //
      if (!CollectionUtils.isEmpty(_dgslForDeadload)) {
         com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.DgslForDeadload dgslForDeadloadDetail = null;
         for (DGSL _dgsl : _dgslForDeadload) {
            //
            dgslForDeadloadDetail = new com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.DgslForDeadload();
            // DGSL Code
            dgslForDeadloadDetail.setDgslCode(dgslCode(_dgsl));
            // DGSL Number
            dgslForDeadloadDetail.setDgslNbr(dgslNbr(_dgsl));
            // SLDG Weight and Height Data
            dgslForDeadloadDetail.setSldgWeightAndHeightData(sldgWeightAndHeightData(_dgsl));
            // DGSL Pieces
            dgslForDeadloadDetail.setDgslPieces(dgslPieces(_dgsl));
            // DGSL Indicators
            dgslForDeadloadDetail.setDgslIndicators(dgslIndicators(_dgsl));
            // Temperature Ventilation Setting
            temperatureVentilationSetting(_dgsl, dgslForDeadloadDetail.getTemperatureVentilationSetting());
            // Supplementary Info and Quantity TI
            supplementaryInfoAndQuantityTI(_dgsl, dgslForDeadloadDetail.getSupplementaryInfoAndQuantityTI());
            //
            dgslForDeadload.add(dgslForDeadloadDetail);
         }
      }
   }
   
   /**
    * DGSL Code
    * 
    * @param _dgsl
    * @return
    */
   private static SpecialLoadDetailsType dgslCode(DGSL _dgsl) {
      // Source
      DGSLCode _dgslCode = _dgsl.getDgslCode();
      //
      SpecialLoadDetailsType dgslCode = null;
      if (Objects.nonNull(_dgslCode)) {
         dgslCode = new SpecialLoadDetailsType();
         dgslCode.setEmergencyRespCode(_dgslCode.getEmergencyRespCode());
         dgslCode.setAirwayBillNbr(_dgslCode.getAirwayBillNbr());
         dgslCode.setUNOrIDNbr(_dgslCode.getUnOrIdNbr());
         dgslCode.setDescription(_dgslCode.getDescription());
         dgslCode.setSldgPackingGroup(_dgslCode.getSldgPackingGroup());
         //
         if (Objects.nonNull(_dgslCode.getHazardID())) {
            HazardIdentificationTypeI hazardID = new HazardIdentificationTypeI();
            hazardID.setClassDivision(_dgslCode.getHazardID().getClassDivision());
            dgslCode.setHazardID(hazardID);
         }
         if (Objects.nonNull(_dgslCode.getHazardReference())) {
            HazardCodeType hazardReference = new HazardCodeType();
            hazardReference.setSldgIATACode(_dgslCode.getHazardReference().getSldgIATACode());
            hazardReference.setRadioactiveCategory(_dgslCode.getHazardReference().getRadioactiveCategory());
            // TODO: subsidiaryRiskGrp
            List<String> subsidiaryRiskGrp = hazardReference.getSubsidiaryRiskGrp();
            dgslCode.setHazardReference(hazardReference);
         }
         dgslCode.setTechnicalDescription(_dgslCode.getTechnicalDescription());
      }
      return dgslCode;
   }
   
   /**
    * DGSL Number
    * 
    * @param _dgsl
    * @return
    */
   private static ReferenceInfoType dgslNbr(DGSL _dgsl) {
      // Source
      DGSLNbr _dgslNbr = _dgsl.getDgslNbr();
      //
      ReferenceInfoType dgslNbr = null;
      if (Objects.nonNull(_dgslNbr)) {
         dgslNbr = new ReferenceInfoType();
         List<ReferencingDetailsType> referenceDetails = dgslNbr.getReferenceDetails();
         if (Objects.nonNull(_dgslNbr.getReferenceDetail())) {
            ReferencingDetailsType referenceDetail = new ReferencingDetailsType();
            referenceDetail.setType(_dgslNbr.getReferenceDetail().getType());
            referenceDetail.setValue(_dgslNbr.getReferenceDetail().getValue());
            referenceDetails.add(referenceDetail);
         }
      }
      return dgslNbr;
   }
   
   /**
    * SLDG Weight and Height Data
    * 
    * @param _dgsl
    * @return
    */
   private static QuantityType83982S sldgWeightAndHeightData(DGSL _dgsl) {
      // Source
      WeightAndHeight _sldgWeightAndHeightData = _dgsl.getSldgWeightAndHeightData();
      //
      QuantityType83982S sldgWeightAndHeightData = null;
      if (Objects.nonNull(_sldgWeightAndHeightData)) {
         sldgWeightAndHeightData = new QuantityType83982S();
         List<QuantityDetailsTypeI> quantityDetails = sldgWeightAndHeightData.getQuantityDetails();
         if (Objects.nonNull(_sldgWeightAndHeightData.getQuantityDetail())) {
            QuantityDetailsTypeI quantityDetail = new QuantityDetailsTypeI();
            quantityDetail.setQualifier(_sldgWeightAndHeightData.getQuantityDetail().getQualifier());
            quantityDetail.setValue(_sldgWeightAndHeightData.getQuantityDetail().getValue());
            quantityDetails.add(quantityDetail);
         }
      }
      return sldgWeightAndHeightData;
   }
   
   /**
    * DGSL Pieces
    * 
    * @param _dgsl
    * @return
    */
   private static NumberOfUnitsType83983S dgslPieces(DGSL _dgsl) {
      // Source
      Pieces _dgslPieces = _dgsl.getDgslPieces();
      //
      NumberOfUnitsType83983S dgslPieces = null;
      if (Objects.nonNull(_dgslPieces)) {
         dgslPieces = new NumberOfUnitsType83983S();
         if (Objects.nonNull(_dgslPieces.getQuantityDetail())) {
            NumberOfUnitDetailsTypeI quantityDetails = new NumberOfUnitDetailsTypeI();
            quantityDetails.setNumberOfUnit(BigInteger.valueOf(_dgslPieces.getQuantityDetail().getNumberOfUnit()));
            quantityDetails.setUnitQualifier(_dgslPieces.getQuantityDetail().getUnitQualifier());
            dgslPieces.setQuantityDetails(quantityDetails);
         }
      }
      return dgslPieces;
   }
   
   /**
    * DGSL Indicators
    * 
    * @param _dgsl
    * @return
    */
   private static StatusTypeI83998S dgslIndicators(DGSL _dgsl) {
      // Source
      DGSLIndicator _dgslIndicators = _dgsl.getDgslIndicators();
      //
      StatusTypeI83998S dgslIndicators = null;
      if (Objects.nonNull(_dgslIndicators)) {
         dgslIndicators = new StatusTypeI83998S();
         StatusDetailsTypeI statusDetails = null;
         StatusDetailsTypeI128105C otherStatusDetails = null;
         if (Objects.nonNull(_dgslIndicators.getStatusDetails())) {
            statusDetails = new StatusDetailsTypeI();
            statusDetails.setIndicator(_dgslIndicators.getStatusDetails().getIndicator());
            dgslIndicators.setStatusDetails(statusDetails);
         }
         if (Objects.nonNull(_dgslIndicators.getOtherStatusDetails())) {
            otherStatusDetails = new StatusDetailsTypeI128105C();
            otherStatusDetails.setIndicator(_dgslIndicators.getOtherStatusDetails().getIndicator());
            otherStatusDetails.setAction(_dgslIndicators.getOtherStatusDetails().getAction());
            dgslIndicators.setOtherStatusDetails(otherStatusDetails);
         }
      }
      return dgslIndicators;
   }
   
   /**
    * Temperature Ventilation Setting
    * 
    * @param _dgsl
    * @param temperatureVentilationSetting
    */
   private static void temperatureVentilationSetting(DGSL _dgsl,
         List<ItemDescriptionTypeI92732S> temperatureVentilationSetting) {
      // Source
      List<DescriptionItem> _temperatureVentilationSetting = _dgsl.getTemperatureVentilationSetting();
      //
      if (!CollectionUtils.isEmpty(_temperatureVentilationSetting)) {
         ItemDescriptionTypeI92732S itemDescriptionType = null;
         for (DescriptionItem _descriptionItem : _temperatureVentilationSetting) {
            itemDescriptionType = new ItemDescriptionTypeI92732S();
            itemDescriptionType.setType(_descriptionItem.getType());
            if (Objects.nonNull(_descriptionItem.getDescriptionDetails())) {
               ItemDescriptionDetailsTypeI139541C descriptionDetails = new ItemDescriptionDetailsTypeI139541C();
               descriptionDetails.setDescription(_descriptionItem.getDescriptionDetails().getDescription());
               itemDescriptionType.setDescriptionDetails(descriptionDetails);
            }
            temperatureVentilationSetting.add(itemDescriptionType);
         }
      }
   }
   
   /**
    * Supplementary Info And Quantity TI
    * 
    * @param _dgsl
    * @param supplementaryInfoAndQuantityTI
    */
   private static void supplementaryInfoAndQuantityTI(DGSL _dgsl,
         List<InteractiveFreeTextType> supplementaryInfoAndQuantityTI) {
      // Source
      List<FreeTextQualifier> _supplementaryInfoAndQuantityTI = _dgsl.getSupplementaryInfoAndQuantityTI();
      //
      interactiveFreeTextType(_supplementaryInfoAndQuantityTI, supplementaryInfoAndQuantityTI);
   }
   
   /**
    * All Packed In One for Deadload
    * 
    * @param _deadloadForLoad
    * @param allPackedInOneForDeadload
    */
   private static void allPackedInOneForDeadload(DeadloadForLoad _deadloadForLoad,
         List<AllPackedInOneForDeadload> allPackedInOneForDeadload) {
      // Source
      List<AllPackedInOne> _allPackedInOneForDeadload = _deadloadForLoad.getAllPackedInOneForDeadload();
      //
      if (!CollectionUtils.isEmpty(_allPackedInOneForDeadload)) {
         AllPackedInOneForDeadload allPackedInOneForDeadloadDetail = null;
         for (AllPackedInOne _allPackedInOne : _allPackedInOneForDeadload) {
            //
            allPackedInOneForDeadloadDetail = new AllPackedInOneForDeadload();
            // All Packed In One Identifier
            allPackedInOneForDeadloadDetail.setAllPackedInOneIdentifier(allPackedInOneIdentifier(_allPackedInOne));
            // All Packed In One Number
            allPackedInOneForDeadloadDetail.setAllPackedInOneNbr(allPackedInOneNbr(_allPackedInOne));
            // Weight and Height of APIO
            allPackedInOneForDeadloadDetail.setWeightAndHeightOfAPIO(weightAndHeightOfAPIO(_allPackedInOne));
            // Pieces of APIO
            allPackedInOneForDeadloadDetail.setPiecesOfAPIO(piecesOfAPIO(_allPackedInOne));
            // Supplementary Info for APIO
            supplementaryInfoForAPIO(_allPackedInOne, allPackedInOneForDeadloadDetail.getSupplementaryInfoForAPIO());
            // DGSL for APIO
            dgslForAPIO(_allPackedInOne, allPackedInOneForDeadloadDetail.getDgslForAPIO());
            //
            allPackedInOneForDeadload.add(allPackedInOneForDeadloadDetail);
         }
      }
   }
   
   /**
    * All Packed In One Identifier
    * 
    * @param _allPackedInOne
    * @return
    */
   private static AllPackedInOneLoadDetailsType allPackedInOneIdentifier(AllPackedInOne _allPackedInOne) {
      // Source
      AllPackedInOneIdentifier _allPackedInOneIdentifier = _allPackedInOne.getAllPackedInOneIdentifier();
      //
      AllPackedInOneLoadDetailsType allPackedInOneIdentifier = null;
      if (Objects.nonNull(_allPackedInOneIdentifier)) {
         allPackedInOneIdentifier = new AllPackedInOneLoadDetailsType();
         allPackedInOneIdentifier.setAirwayBillNbr(_allPackedInOneIdentifier.getAirwayBillNbr());
         allPackedInOneIdentifier.setDescription(_allPackedInOneIdentifier.getDescription());
         allPackedInOneIdentifier.setRadioactiveCategory(_allPackedInOneIdentifier.getRadioactiveCategory());
      }
      return allPackedInOneIdentifier;
   }
   
   /**
    * All Packed In One Number
    * 
    * @param _allPackedInOne
    * @return
    */
   private static ReferenceInfoType193774S allPackedInOneNbr(AllPackedInOne _allPackedInOne) {
      // Source
      AllPackedInOneNbr _allPackedInOneNbr = _allPackedInOne.getAllPackedInOneNbr();
      //
      ReferenceInfoType193774S allPackedInOneNbr = null;
      if (Objects.nonNull(_allPackedInOneNbr)) {
         allPackedInOneNbr = new ReferenceInfoType193774S();
         ReferencingDetailsType referenceDetails = null;
         if (Objects.nonNull(_allPackedInOneNbr.getReferenceDetail())) {
            referenceDetails = new ReferencingDetailsType();
            referenceDetails.setType(_allPackedInOneNbr.getReferenceDetail().getType());
            referenceDetails.setValue(_allPackedInOneNbr.getReferenceDetail().getValue());
            allPackedInOneNbr.setReferenceDetails(referenceDetails);
         }
      }
      return allPackedInOneNbr;
   }
   
   /**
    * Weight And Height of APIO
    * 
    * @param _allPackedInOne
    * @return
    */
   private static QuantityType83982S weightAndHeightOfAPIO(AllPackedInOne _allPackedInOne) {
      // Source
      WeightAndHeight _weightAndHeightOfAPIO = _allPackedInOne.getWeightAndHeightOfAPIO();
      //
      QuantityType83982S weightAndHeightOfAPIO = null;
      if (Objects.nonNull(_weightAndHeightOfAPIO)) {
         weightAndHeightOfAPIO = new QuantityType83982S();
         List<QuantityDetailsTypeI> quantityDetails = weightAndHeightOfAPIO.getQuantityDetails();
         if (Objects.nonNull(_weightAndHeightOfAPIO.getQuantityDetail())) {
            QuantityDetailsTypeI quantityDetailsType = new QuantityDetailsTypeI();
            quantityDetailsType.setQualifier(_weightAndHeightOfAPIO.getQuantityDetail().getQualifier());
            quantityDetailsType.setValue(_weightAndHeightOfAPIO.getQuantityDetail().getValue());
            quantityDetails.add(quantityDetailsType);
         }
      }
      return weightAndHeightOfAPIO;
   }
   
   /**
    * Pieces Of APIO
    * 
    * @param _allPackedInOne
    * @return
    */
   private static NumberOfUnitsType piecesOfAPIO(AllPackedInOne _allPackedInOne) {
      // Source
      Pieces _piecesOfAPIO = _allPackedInOne.getPiecesOfAPIO();
      //
      NumberOfUnitsType piecesOfAPIO = null;
      if (Objects.nonNull(_piecesOfAPIO)) {
         piecesOfAPIO = new NumberOfUnitsType();
         NumberOfUnitDetailsType quantityDetails = null;
         if (Objects.nonNull(_piecesOfAPIO.getQuantityDetail())) {
            quantityDetails = new NumberOfUnitDetailsType();
            quantityDetails.setNumberOfUnit(BigInteger.valueOf(_piecesOfAPIO.getQuantityDetail().getNumberOfUnit()));
            quantityDetails.setUnitQualifier(_piecesOfAPIO.getQuantityDetail().getUnitQualifier());
            piecesOfAPIO.setQuantityDetails(quantityDetails);
         }
      }
      return piecesOfAPIO;
   }
   
   /**
    * Supplementary Info For APIO
    * 
    * @param _allPackedInOne
    * @param supplementaryInfoForAPIO
    */
   private static void supplementaryInfoForAPIO(AllPackedInOne _allPackedInOne,
         List<InteractiveFreeTextType> supplementaryInfoForAPIO) {
      // Source
      List<FreeTextQualifier> _supplementaryInfoForAPIO = _allPackedInOne.getSupplementaryInfoForAPIO();
      //
      interactiveFreeTextType(_supplementaryInfoForAPIO, supplementaryInfoForAPIO);
   }
   
   /**
    * All Packed In One
    * 
    * @param _allPackedInOne
    */
   private static void dgslForAPIO(AllPackedInOne _allPackedInOne,
         List<com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.AllPackedInOneForDeadload.DgslForAPIO> dgslForAPIO) {
      // Source
      List<DGSL> _dgslForAPIO = _allPackedInOne.getDgslForAPIO();
      //
      if (!CollectionUtils.isEmpty(_dgslForAPIO)) {
         com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.AllPackedInOneForDeadload.DgslForAPIO dgslForAPIODetail = null;
         for (DGSL _dgsl : _dgslForAPIO) {
            //
            dgslForAPIODetail = new com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.AllPackedInOneForDeadload.DgslForAPIO();
            // DGSL Code for APIO
            dgslForAPIODetail.setDgslCode(dgslCodeForAPIO(_dgsl));
            // DGSL Number for APIO
            dgslForAPIODetail.setDgslNbr(dgslNbrForAPIO(_dgsl));
            // SLDG Weight and Height data for APIO
            dgslForAPIODetail.setSldgWeightAndHeightData(sldgWeightAndHeightDataForAPIO(_dgsl));
            // DGSL Pieces for APIO
            dgslForAPIODetail.setDgslPieces(dgslPiecesForAPIO(_dgsl));
            // DGSL Indicators for APIO
            dgslForAPIODetail.setDgslIndicators(dgslIndicatorsForAPIO(_dgsl));
            // Temperature Ventilation Setting for APIO
            temperatureVentilationSettingForAPIO(_dgsl, dgslForAPIODetail.getTemperatureVentilationSetting());
            // Supplementary Info and quantity TI for APIO
            supplementaryInfoAndQuantityTIForAPIO(_dgsl, dgslForAPIODetail.getSupplementaryInfoAndQuantityTI());
            //
            dgslForAPIO.add(dgslForAPIODetail);
         }
      }
   }
   
   /**
    * DGSL Code for APIO
    * 
    * @param _dgsl
    * @return
    */
   private static SpecialLoadDetailsType dgslCodeForAPIO(DGSL _dgsl) {
      // Source
      DGSLCode _dgslCode = _dgsl.getDgslCode();
      //
      SpecialLoadDetailsType dgslCode = null;
      if (Objects.nonNull(_dgslCode)) {
         dgslCode = new SpecialLoadDetailsType();
         dgslCode.setEmergencyRespCode(_dgslCode.getEmergencyRespCode());
         dgslCode.setAirwayBillNbr(_dgslCode.getAirwayBillNbr());
         dgslCode.setUNOrIDNbr(_dgslCode.getUnOrIdNbr());
         dgslCode.setDescription(_dgslCode.getDescription());
         dgslCode.setSldgPackingGroup(_dgslCode.getSldgPackingGroup());
         //
         if (Objects.nonNull(_dgslCode.getHazardID())) {
            HazardIdentificationTypeI hazardID = new HazardIdentificationTypeI();
            hazardID.setClassDivision(_dgslCode.getHazardID().getClassDivision());
            dgslCode.setHazardID(hazardID);
         }
         if (Objects.nonNull(_dgslCode.getHazardReference())) {
            HazardCodeType hazardReference = new HazardCodeType();
            hazardReference.setSldgIATACode(_dgslCode.getHazardReference().getSldgIATACode());
            hazardReference.setRadioactiveCategory(_dgslCode.getHazardReference().getRadioactiveCategory());
            // TODO: subsidiaryRiskGrp
            List<String> subsidiaryRiskGrp = hazardReference.getSubsidiaryRiskGrp();
            dgslCode.setHazardReference(hazardReference);
         }
         dgslCode.setTechnicalDescription(_dgslCode.getTechnicalDescription());
      }
      return dgslCode;
   }
   
   /**
    * DGSL Number for APIO
    * 
    * @param _dgsl
    * @return
    */
   private static ReferenceInfoType193774S dgslNbrForAPIO(DGSL _dgsl) {
      // Source
      DGSLNbr _dgslNbr = _dgsl.getDgslNbr();
      //
      ReferenceInfoType193774S dgslNbr = null;
      if (Objects.nonNull(_dgslNbr)) {
         dgslNbr = new ReferenceInfoType193774S();
         if (Objects.nonNull(_dgslNbr.getReferenceDetail())) {
            ReferencingDetailsType referenceDetails = new ReferencingDetailsType();
            referenceDetails.setType(_dgslNbr.getReferenceDetail().getType());
            referenceDetails.setValue(_dgslNbr.getReferenceDetail().getValue());
            dgslNbr.setReferenceDetails(referenceDetails);
         }
      }
      return dgslNbr;
   }
   
   /**
    * SLDG Weight and Height Data for APIO
    * 
    * @param _dgsl
    * @return
    */
   private static QuantityType83982S sldgWeightAndHeightDataForAPIO(DGSL _dgsl) {
      // Source
      WeightAndHeight _sldgWeightAndHeightData = _dgsl.getSldgWeightAndHeightData();
      //
      QuantityType83982S sldgWeightAndHeightData = null;
      if (Objects.nonNull(_sldgWeightAndHeightData)) {
         sldgWeightAndHeightData = new QuantityType83982S();
         if (Objects.nonNull(_sldgWeightAndHeightData)) {
            List<QuantityDetailsTypeI> quantityDetails = sldgWeightAndHeightData.getQuantityDetails();
            if (Objects.nonNull(_sldgWeightAndHeightData.getQuantityDetail())) {
               QuantityDetailsTypeI quantityDetail = new QuantityDetailsTypeI();
               quantityDetail.setQualifier(_sldgWeightAndHeightData.getQuantityDetail().getQualifier());
               quantityDetail.setValue(_sldgWeightAndHeightData.getQuantityDetail().getValue());
               quantityDetails.add(quantityDetail);
            }
         }
      }
      return sldgWeightAndHeightData;
   }
   
   /**
    * DGSL Pieces for APIO
    * 
    * @param _dgsl
    * @return
    */
   private static NumberOfUnitsType83983S dgslPiecesForAPIO(DGSL _dgsl) {
      // Source
      Pieces _dgslPieces = _dgsl.getDgslPieces();
      //
      NumberOfUnitsType83983S dgslPieces = null;
      if (Objects.nonNull(_dgslPieces)) {
         dgslPieces = new NumberOfUnitsType83983S();
         if (Objects.nonNull(_dgslPieces.getQuantityDetail())) {
            NumberOfUnitDetailsTypeI quantityDetails = new NumberOfUnitDetailsTypeI();
            quantityDetails.setNumberOfUnit(BigInteger.valueOf(_dgslPieces.getQuantityDetail().getNumberOfUnit()));
            quantityDetails.setUnitQualifier(_dgslPieces.getQuantityDetail().getUnitQualifier());
            dgslPieces.setQuantityDetails(quantityDetails);
         }
      }
      return dgslPieces;
   }
   
   /**
    * DGSL Indicator for APIO
    * 
    * @param _dgsl
    * @return
    */
   private static StatusTypeI83998S dgslIndicatorsForAPIO(DGSL _dgsl) {
      // Source
      DGSLIndicator _dgslIndicators = _dgsl.getDgslIndicators();
      //
      StatusTypeI83998S dgslIndicators = null;
      if (Objects.nonNull(_dgslIndicators)) {
         dgslIndicators = new StatusTypeI83998S();
         StatusDetailsTypeI statusDetails = null;
         StatusDetailsTypeI128105C otherStatusDetails = null;
         if (Objects.nonNull(_dgslIndicators.getStatusDetails())) {
            statusDetails = new StatusDetailsTypeI();
            statusDetails.setIndicator(_dgslIndicators.getStatusDetails().getIndicator());
            dgslIndicators.setStatusDetails(statusDetails);
         }
         if (Objects.nonNull(_dgslIndicators.getOtherStatusDetails())) {
            otherStatusDetails = new StatusDetailsTypeI128105C();
            otherStatusDetails.setIndicator(_dgslIndicators.getOtherStatusDetails().getIndicator());
            otherStatusDetails.setAction(_dgslIndicators.getOtherStatusDetails().getAction());
            dgslIndicators.setOtherStatusDetails(otherStatusDetails);
         }
      }
      return dgslIndicators;
   }
   
   /**
    * Temperature Ventilation Setting for APIO
    * 
    * @param _dgsl
    * @param temperatureVentilationSetting
    */
   private static void temperatureVentilationSettingForAPIO(DGSL _dgsl,
         List<ItemDescriptionTypeI92732S> temperatureVentilationSetting) {
      // Source
      List<DescriptionItem> _temperatureVentilationSetting = _dgsl.getTemperatureVentilationSetting();
      //
      if (!CollectionUtils.isEmpty(_temperatureVentilationSetting)) {
         ItemDescriptionTypeI92732S itemDescriptionType = null;
         for (DescriptionItem _descriptionItem : _temperatureVentilationSetting) {
            itemDescriptionType = new ItemDescriptionTypeI92732S();
            itemDescriptionType.setType(_descriptionItem.getType());
            if (Objects.nonNull(_descriptionItem.getDescriptionDetails())) {
               ItemDescriptionDetailsTypeI139541C descriptionDetails = new ItemDescriptionDetailsTypeI139541C();
               descriptionDetails.setDescription(_descriptionItem.getDescriptionDetails().getDescription());
               itemDescriptionType.setDescriptionDetails(descriptionDetails);
            }
            temperatureVentilationSetting.add(itemDescriptionType);
         }
      }
   }
   
   /**
    * Supplementary Info and Quantity TI for APIO
    * 
    * @param _dgsl
    * @param supplementaryInfoAndQuantityTI
    */
   private static void supplementaryInfoAndQuantityTIForAPIO(DGSL _dgsl,
         List<InteractiveFreeTextType> supplementaryInfoAndQuantityTI) {
      // Source
      List<FreeTextQualifier> _supplementaryInfoAndQuantityTI = _dgsl.getSupplementaryInfoAndQuantityTI();
      //
      interactiveFreeTextType(_supplementaryInfoAndQuantityTI, supplementaryInfoAndQuantityTI);
   }
   
   /**
    * Overpack for Deadload
    * 
    * @param _deadloadForLoad
    * @param overpackForDeadload
    */
   private static void overpackForDeadload(DeadloadForLoad _deadloadForLoad,
         List<com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.OverpackForDeadload> overpackForDeadload) {
      // Source
      List<OverpackForDeadload> _overpackForDeadloadDetails = _deadloadForLoad.getOverpackForDeadload();
      //
      if (!CollectionUtils.isEmpty(_overpackForDeadloadDetails)) {
         com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.OverpackForDeadload overpackForDeadloadDetail = null;
         for (OverpackForDeadload _overpackForDeadload : _overpackForDeadloadDetails) {
            //
            overpackForDeadloadDetail = new com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.OverpackForDeadload();
            // Overpack Identifier
            overpackForDeadloadDetail.setOverpackIdentifier(overpackIdentifier(_overpackForDeadload));
            // Overpack Number
            overpackForDeadloadDetail.setOverpackNbr(overpackNbr(_overpackForDeadload));
            // Weight and Height of OVP
            overpackForDeadloadDetail.setWeightAndHeightOfOVP(weightAndHeightOfOVP(_overpackForDeadload));
            // Pieces of OVP
            overpackForDeadloadDetail.setPiecesOfOVP(piecesOfOVP(_overpackForDeadload));
            // Supplementary Info for OVP
            supplementaryInfoForOVP(_overpackForDeadload, overpackForDeadloadDetail.getSupplementaryInfoForOVP());
            // DGSL for OVP
            dgslForOVP(_overpackForDeadload, overpackForDeadloadDetail.getDgslForOVP());
            // All Packed In One for OVP
            allPackedInOneForOVP(_overpackForDeadload, overpackForDeadloadDetail.getAllPackedInOneForOVP());
            //
            overpackForDeadload.add(overpackForDeadloadDetail);
         }
      }
   }
   
   /**
    * Overpack Identifier
    * 
    * @param _overpackForDeadload
    * @return
    */
   private static OverpackLoadDetailsType overpackIdentifier(OverpackForDeadload _overpackForDeadload) {
      // Source
      OverpackIdentifier _overpackIdentifier = _overpackForDeadload.getOverpackIdentifier();
      //
      OverpackLoadDetailsType overpackIdentifier = null;
      if (Objects.nonNull(_overpackIdentifier)) {
         overpackIdentifier = new OverpackLoadDetailsType();
         overpackIdentifier.setAirwayBillNbr(_overpackIdentifier.getAirwayBillNbr());
         overpackIdentifier.setDescription(_overpackIdentifier.getDescription());
         overpackIdentifier.setRadioactiveCategory(_overpackIdentifier.getRadioactiveCategory());
      }
      return overpackIdentifier;
   }
   
   /**
    * Overpack Number
    * 
    * @param _overpackForDeadload
    * @return
    */
   private static ReferenceInfoType193774S overpackNbr(OverpackForDeadload _overpackForDeadload) {
      // Source
      OverpackNbr _overpackNbr = _overpackForDeadload.getOverpackNbr();
      //
      ReferenceInfoType193774S overpackNbr = null;
      if (Objects.nonNull(_overpackNbr)) {
         overpackNbr = new ReferenceInfoType193774S();
         if (Objects.nonNull(_overpackNbr.getReferenceDetail())) {
            ReferencingDetailsType referenceDetails = new ReferencingDetailsType();
            referenceDetails.setType(_overpackNbr.getReferenceDetail().getType());
            referenceDetails.setValue(_overpackNbr.getReferenceDetail().getValue());
            overpackNbr.setReferenceDetails(referenceDetails);
         }
      }
      return overpackNbr;
   }
   
   /**
    * Weight and Height of OVP
    * 
    * @param _overpackForDeadload
    * @return
    */
   private static QuantityType83982S weightAndHeightOfOVP(OverpackForDeadload _overpackForDeadload) {
      // Source
      QuantityDetail _weightAndHeightOfOVP = _overpackForDeadload.getWeightAndHeightOfOVP();
      //
      QuantityType83982S weightAndHeightOfOVP = null;
      if (Objects.nonNull(_weightAndHeightOfOVP)) {
         weightAndHeightOfOVP = new QuantityType83982S();
         List<QuantityDetailsTypeI> quantityDetails = weightAndHeightOfOVP.getQuantityDetails();
         QuantityDetailsTypeI quantityDetailsType = new QuantityDetailsTypeI();
         quantityDetailsType.setQualifier(_weightAndHeightOfOVP.getQualifier());
         quantityDetailsType.setValue(_weightAndHeightOfOVP.getValue());
         quantityDetails.add(quantityDetailsType);
      }
      return weightAndHeightOfOVP;
   }
   
   /**
    * Pieces of OVP
    * 
    * @param _overpackForDeadload
    * @return
    */
   private static NumberOfUnitsType piecesOfOVP(OverpackForDeadload _overpackForDeadload) {
      // Source
      QuantityDetail _piecesOfOVP = _overpackForDeadload.getPiecesOfOVP();
      //
      NumberOfUnitsType piecesOfOVP = null;
      if (Objects.nonNull(_piecesOfOVP)) {
         piecesOfOVP = new NumberOfUnitsType();
         NumberOfUnitDetailsType quantityDetails = new NumberOfUnitDetailsType();
         quantityDetails.setNumberOfUnit(BigInteger.valueOf(_piecesOfOVP.getNumberOfUnit()));
         quantityDetails.setUnitQualifier(_piecesOfOVP.getUnitQualifier());
         piecesOfOVP.setQuantityDetails(quantityDetails);
      }
      return piecesOfOVP;
   }
   
   /**
    * Supplementary Info for OVP
    * 
    * @param _overpackForDeadload
    * @param supplementaryInfoForOVP
    * @return
    */
   private static List<InteractiveFreeTextType> supplementaryInfoForOVP(OverpackForDeadload _overpackForDeadload,
         List<InteractiveFreeTextType> supplementaryInfoForOVP) {
      // Source
      List<FreeTextQualifier> _supplementaryInfoForOVP = _overpackForDeadload.getSupplementaryInfoForOVP();
      //
      return interactiveFreeTextType(_supplementaryInfoForOVP, supplementaryInfoForOVP);
   }
   
   /**
    * DGSL for OVP
    * 
    * @param _overpackForDeadload
    * @param dgslForOVP
    * @return
    */
   private static void dgslForOVP(OverpackForDeadload _overpackForDeadload,
         List<com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.OverpackForDeadload.DgslForOVP> dgslForOVP) {
      // Source
      List<DGSL> _dgslForOVP = _overpackForDeadload.getDgslForOVP();
      //
      if (!CollectionUtils.isEmpty(_dgslForOVP)) {
         com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.OverpackForDeadload.DgslForOVP dgslForOVPDetail = null;
         for (DGSL _dgsl : _dgslForOVP) {
            //
            dgslForOVPDetail = new com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.OverpackForDeadload.DgslForOVP();
            // DGSL Code for OVP
            dgslForOVPDetail.setDgslCode(null);
            // DGSL Code for OVP
            dgslForOVPDetail.setDgslCode(dgslCodeForOVP(_dgsl));
            // DGSL Number for OVP
            dgslForOVPDetail.setDgslNbr(dgslNbrForOVP(_dgsl));
            // SLDG Weight and Height data for OVP
            dgslForOVPDetail.setSldgWeightAndHeightData(sldgWeightAndHeightDataForOVP(_dgsl));
            // DGSL Pieces for OVP
            dgslForOVPDetail.setDgslPieces(dgslPiecesForOVP(_dgsl));
            // DGSL Indicators for OVP
            dgslForOVPDetail.setDgslIndicators(dgslIndicatorsForOVP(_dgsl));
            // Temperature Ventilation Setting for OVP
            temperatureVentilationSettingForOVP(_dgsl, dgslForOVPDetail.getTemperatureVentilationSetting());
            // Supplementary Info and quantity TI for OVP
            supplementaryInfoAndQuantityTIForOVP(_dgsl, dgslForOVPDetail.getSupplementaryInfoAndQuantityTI());
            //
            dgslForOVP.add(dgslForOVPDetail);
         }
      }
   }
   
   /**
    * DGSL Code for OVP
    * 
    * @param _dgsl
    * @return
    */
   private static SpecialLoadDetailsType dgslCodeForOVP(DGSL _dgsl) {
      // Source
      DGSLCode _dgslCode = _dgsl.getDgslCode();
      //
      SpecialLoadDetailsType dgslCode = null;
      if (Objects.nonNull(_dgslCode)) {
         dgslCode = new SpecialLoadDetailsType();
         dgslCode.setEmergencyRespCode(_dgslCode.getEmergencyRespCode());
         dgslCode.setAirwayBillNbr(_dgslCode.getAirwayBillNbr());
         dgslCode.setUNOrIDNbr(_dgslCode.getUnOrIdNbr());
         dgslCode.setDescription(_dgslCode.getDescription());
         dgslCode.setSldgPackingGroup(_dgslCode.getSldgPackingGroup());
         //
         if (Objects.nonNull(_dgslCode.getHazardID())) {
            HazardIdentificationTypeI hazardID = new HazardIdentificationTypeI();
            hazardID.setClassDivision(_dgslCode.getHazardID().getClassDivision());
            dgslCode.setHazardID(hazardID);
         }
         if (Objects.nonNull(_dgslCode.getHazardReference())) {
            HazardCodeType hazardReference = new HazardCodeType();
            hazardReference.setSldgIATACode(_dgslCode.getHazardReference().getSldgIATACode());
            hazardReference.setRadioactiveCategory(_dgslCode.getHazardReference().getRadioactiveCategory());
            // TODO: subsidiaryRiskGrp
            List<String> subsidiaryRiskGrp = hazardReference.getSubsidiaryRiskGrp();
            dgslCode.setHazardReference(hazardReference);
         }
         dgslCode.setTechnicalDescription(_dgslCode.getTechnicalDescription());
      }
      return dgslCode;
   }
   
   /**
    * DGSL Number for OVP
    * 
    * @param _dgsl
    * @return
    */
   private static ReferenceInfoType193774S dgslNbrForOVP(DGSL _dgsl) {
      // Source
      DGSLNbr _dgslNbr = _dgsl.getDgslNbr();
      //
      ReferenceInfoType193774S dgslNbr = null;
      if (Objects.nonNull(_dgslNbr)) {
         dgslNbr = new ReferenceInfoType193774S();
         if (Objects.nonNull(_dgslNbr.getReferenceDetail())) {
            ReferencingDetailsType referenceDetails = new ReferencingDetailsType();
            referenceDetails.setType(_dgslNbr.getReferenceDetail().getType());
            referenceDetails.setValue(_dgslNbr.getReferenceDetail().getValue());
            dgslNbr.setReferenceDetails(referenceDetails);
         }
      }
      return dgslNbr;
   }
   
   /**
    * SLDG Weight and Height Data for OVP
    * 
    * @param _dgsl
    * @return
    */
   private static QuantityType83982S sldgWeightAndHeightDataForOVP(DGSL _dgsl) {
      // Source
      WeightAndHeight _sldgWeightAndHeightData = _dgsl.getSldgWeightAndHeightData();
      //
      QuantityType83982S sldgWeightAndHeightData = null;
      if (Objects.nonNull(_sldgWeightAndHeightData)) {
         sldgWeightAndHeightData = new QuantityType83982S();
         if (Objects.nonNull(_sldgWeightAndHeightData)) {
            List<QuantityDetailsTypeI> quantityDetails = sldgWeightAndHeightData.getQuantityDetails();
            if (Objects.nonNull(_sldgWeightAndHeightData.getQuantityDetail())) {
               QuantityDetailsTypeI quantityDetail = new QuantityDetailsTypeI();
               quantityDetail.setQualifier(_sldgWeightAndHeightData.getQuantityDetail().getQualifier());
               quantityDetail.setValue(_sldgWeightAndHeightData.getQuantityDetail().getValue());
               quantityDetails.add(quantityDetail);
            }
         }
      }
      return sldgWeightAndHeightData;
   }
   
   /**
    * DGSL Pieces for OVP
    * 
    * @param _dgsl
    * @return
    */
   private static NumberOfUnitsType83983S dgslPiecesForOVP(DGSL _dgsl) {
      // Source
      Pieces _dgslPieces = _dgsl.getDgslPieces();
      //
      NumberOfUnitsType83983S dgslPieces = null;
      if (Objects.nonNull(_dgslPieces)) {
         dgslPieces = new NumberOfUnitsType83983S();
         if (Objects.nonNull(_dgslPieces.getQuantityDetail())) {
            NumberOfUnitDetailsTypeI quantityDetails = new NumberOfUnitDetailsTypeI();
            quantityDetails.setNumberOfUnit(BigInteger.valueOf(_dgslPieces.getQuantityDetail().getNumberOfUnit()));
            quantityDetails.setUnitQualifier(_dgslPieces.getQuantityDetail().getUnitQualifier());
            dgslPieces.setQuantityDetails(quantityDetails);
         }
      }
      return dgslPieces;
   }
   
   /**
    * DGSL Indicator for OVP
    * 
    * @param _dgsl
    * @return
    */
   private static StatusTypeI83998S dgslIndicatorsForOVP(DGSL _dgsl) {
      // Source
      DGSLIndicator _dgslIndicators = _dgsl.getDgslIndicators();
      //
      StatusTypeI83998S dgslIndicators = null;
      if (Objects.nonNull(_dgslIndicators)) {
         dgslIndicators = new StatusTypeI83998S();
         StatusDetailsTypeI statusDetails = null;
         StatusDetailsTypeI128105C otherStatusDetails = null;
         if (Objects.nonNull(_dgslIndicators.getStatusDetails())) {
            statusDetails = new StatusDetailsTypeI();
            statusDetails.setIndicator(_dgslIndicators.getStatusDetails().getIndicator());
            dgslIndicators.setStatusDetails(statusDetails);
         }
         if (Objects.nonNull(_dgslIndicators.getOtherStatusDetails())) {
            otherStatusDetails = new StatusDetailsTypeI128105C();
            otherStatusDetails.setIndicator(_dgslIndicators.getOtherStatusDetails().getIndicator());
            otherStatusDetails.setAction(_dgslIndicators.getOtherStatusDetails().getAction());
            dgslIndicators.setOtherStatusDetails(otherStatusDetails);
         }
      }
      return dgslIndicators;
   }
   
   /**
    * Temperature Ventilation Setting for OVP
    * 
    * @param _dgsl
    * @param temperatureVentilationSetting
    */
   private static void temperatureVentilationSettingForOVP(DGSL _dgsl,
         List<ItemDescriptionTypeI92732S> temperatureVentilationSetting) {
      // Source
      List<DescriptionItem> _temperatureVentilationSetting = _dgsl.getTemperatureVentilationSetting();
      //
      if (!CollectionUtils.isEmpty(_temperatureVentilationSetting)) {
         ItemDescriptionTypeI92732S itemDescriptionType = null;
         for (DescriptionItem _descriptionItem : _temperatureVentilationSetting) {
            itemDescriptionType = new ItemDescriptionTypeI92732S();
            itemDescriptionType.setType(_descriptionItem.getType());
            if (Objects.nonNull(_descriptionItem.getDescriptionDetails())) {
               ItemDescriptionDetailsTypeI139541C descriptionDetails = new ItemDescriptionDetailsTypeI139541C();
               descriptionDetails.setDescription(_descriptionItem.getDescriptionDetails().getDescription());
               itemDescriptionType.setDescriptionDetails(descriptionDetails);
            }
            temperatureVentilationSetting.add(itemDescriptionType);
         }
      }
   }
   
   /**
    * Supplementary Info and Quantity TI for OVP
    * 
    * @param _dgsl
    * @param supplementaryInfoAndQuantityTI
    */
   private static void supplementaryInfoAndQuantityTIForOVP(DGSL _dgsl,
         List<InteractiveFreeTextType> supplementaryInfoAndQuantityTI) {
      // Source
      List<FreeTextQualifier> _supplementaryInfoAndQuantityTI = _dgsl.getSupplementaryInfoAndQuantityTI();
      //
      interactiveFreeTextType(_supplementaryInfoAndQuantityTI, supplementaryInfoAndQuantityTI);
   }
   
   /**
    * All Packed In One for OVP
    * 
    * @param _overpackForDeadload
    * @param allPackedInOneForOVP
    */
   private static void allPackedInOneForOVP(OverpackForDeadload _overpackForDeadload,
         List<com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.OverpackForDeadload.AllPackedInOneForOVP> allPackedInOneForOVP) {
      // Source
      List<AllPackedInOne> _allPackedInOneForOVP = _overpackForDeadload.getAllPackedInOneForOVP();
      //
      if (!CollectionUtils.isEmpty(_allPackedInOneForOVP)) {
         AllPackedInOneForOVP allPackedInOneForOVPDetail = null;
         for (AllPackedInOne _allPackedInOne : _allPackedInOneForOVP) {
            //
            allPackedInOneForOVPDetail = new AllPackedInOneForOVP();
            // All Packed In One Identifier
            allPackedInOneForOVPDetail.setAllPackedInOneIdentifier(allPackedInOneIdentifier(_allPackedInOne));
            // All Packed In One Number
            allPackedInOneForOVPDetail.setAllPackedInOneNbr(allPackedInOneNbr(_allPackedInOne));
            // Weight and Height of APIO
            allPackedInOneForOVPDetail.setWeightAndHeightOfAPIO(weightAndHeightOfAPIO(_allPackedInOne));
            // Pieces of APIO
            allPackedInOneForOVPDetail.setPiecesOfAPIO(piecesOfAPIO(_allPackedInOne));
            // Supplementary Info for APIO
            supplementaryInfoForAPIO(_allPackedInOne, allPackedInOneForOVPDetail.getSupplementaryInfoForAPIO());
            // DGSL for OVP
            dgslForOVP(_allPackedInOne, allPackedInOneForOVPDetail.getDgslForAPIO());
            //
            allPackedInOneForOVP.add(allPackedInOneForOVPDetail);
         }
      }
   }
   
   /**
    * All Packed In One
    * 
    * @param _allPackedInOne
    */
   private static void dgslForOVP(AllPackedInOne _allPackedInOne,
         List<com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.OverpackForDeadload.AllPackedInOneForOVP.DgslForAPIO> dgslForOVP) {
      // Source
      List<DGSL> _dgslForAPIO = _allPackedInOne.getDgslForAPIO();
      //
      if (!CollectionUtils.isEmpty(_dgslForAPIO)) {
         com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.OverpackForDeadload.AllPackedInOneForOVP.DgslForAPIO dgslForAPIODetail = null;
         for (DGSL _dgsl : _dgslForAPIO) {
            //
            dgslForAPIODetail = new com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures.LoadInfo.DeadloadForLoad.OverpackForDeadload.AllPackedInOneForOVP.DgslForAPIO();
            // DGSL Code for APIO
            dgslForAPIODetail.setDgslCode(dgslCodeForAPIO(_dgsl));
            // DGSL Number for APIO
            dgslForAPIODetail.setDgslNbr(dgslNbrForAPIO(_dgsl));
            // SLDG Weight and Height data for APIO
            dgslForAPIODetail.setSldgWeightAndHeightData(sldgWeightAndHeightDataForAPIO(_dgsl));
            // DGSL Pieces for APIO
            dgslForAPIODetail.setDgslPieces(dgslPiecesForAPIO(_dgsl));
            // DGSL Indicators for APIO
            dgslForAPIODetail.setDgslIndicators(dgslIndicatorsForAPIO(_dgsl));
            // Temperature Ventilation Setting for APIO
            temperatureVentilationSettingForAPIO(_dgsl, dgslForAPIODetail.getTemperatureVentilationSetting());
            // Supplementary Info and quantity TI for APIO
            supplementaryInfoAndQuantityTIForAPIO(_dgsl, dgslForAPIODetail.getSupplementaryInfoAndQuantityTI());
            //
            dgslForOVP.add(dgslForAPIODetail);
         }
      }
   }
   
   /* --------------------- Utility --------------------- */
   
   /**
    * Flight Date Information
    * 
    * @param _flightDates
    * @param flightDates
    */
   private static void flightDateInformation(List<FlightDate> _flightDates,
         List<StructuredDateTimeInformationType> flightDates) {
      if (!CollectionUtils.isEmpty(_flightDates)) {
         StructuredDateTimeInformationType dateTimeInformation = null;
         //
         for (FlightDate _flightDate : _flightDates) {
            dateTimeInformation = new StructuredDateTimeInformationType();
            dateTimeInformation.setTimeMode(_flightDate.getTimeMode());
            DateTime _dateTime = _flightDate.getDateTime();
            if (Objects.nonNull(_flightDate.getDateTime())) {
               StructuredDateTimeType dateTime = new StructuredDateTimeType();
               dateTime.setYear(String.valueOf(_dateTime.getYear()));
               dateTime.setMonth(String.valueOf(_dateTime.getMonth()));
               dateTime.setDay(String.valueOf(_dateTime.getDay()));
               dateTimeInformation.setDateTime(dateTime);
            }
            flightDates.add(dateTimeInformation);
         }
      }
   }
   
   /**
    * Cargo Agent Information
    * 
    * @param cargoAgent
    * @param _paxDetail
    * @param _otherPaxDetail
    * @return
    */
   private static TravellerInformationTypeI cargoAgentInformation(TravellerInformationTypeI cargoAgent,
         PaxDetail _paxDetail, OtherPaxDetail _otherPaxDetail) {
      if (Objects.nonNull(_paxDetail)) {
         TravellerSurnameInformationTypeI paxDetails = new TravellerSurnameInformationTypeI();
         paxDetails.setSurname(_paxDetail.getSurname());
         cargoAgent.setPaxDetails(paxDetails);
      }
      if (Objects.nonNull(_otherPaxDetail)) {
         TravellerDetailsTypeI otherPaxDetails = new TravellerDetailsTypeI();
         otherPaxDetails.setGivenName(_otherPaxDetail.getGivenName());
         cargoAgent.setOtherPaxDetails(otherPaxDetails);
      }
      return cargoAgent;
   }
   
   /**
    * Interactive Free Text Type
    * 
    * @param _freeTextQualifierDetails
    * @param interactiveFreeTextTypeDetails
    * @return
    */
   private static List<InteractiveFreeTextType> interactiveFreeTextType(
         List<FreeTextQualifier> _freeTextQualifierDetails,
         List<InteractiveFreeTextType> interactiveFreeTextTypeDetails) {
      if (!CollectionUtils.isEmpty(_freeTextQualifierDetails)) {
         InteractiveFreeTextType interactiveFreeTextType = null;
         for (FreeTextQualifier _freeTextQualifier : _freeTextQualifierDetails) {
            interactiveFreeTextType = new InteractiveFreeTextType();
            if (Objects.nonNull(_freeTextQualifier.getTextSubjectQualifier())) {
               FreeTextQualificationTypeI freeTextQualification = new FreeTextQualificationTypeI();
               freeTextQualification
                     .setTextSubjectQualifier(_freeTextQualifier.getTextSubjectQualifier().getTextSubjectQualifier());
               interactiveFreeTextType.setFreeTextQualification(freeTextQualification);
            }
            interactiveFreeTextType.setFreeText(_freeTextQualifier.getFreeText());
            interactiveFreeTextTypeDetails.add(interactiveFreeTextType);
         }
         return interactiveFreeTextTypeDetails;
      }
      return null;
   }
   
}
