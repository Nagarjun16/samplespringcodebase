
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for AWBDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="awbConsignmentDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBConsignmentDetailsType"/>
 *         &lt;element name="flightBookingDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}FlightBookingDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="awbRoutingDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBRoutingDetailsType"/>
 *         &lt;element name="awbShipperDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}CustomerDetailsType"/>
 *         &lt;element name="awbConsigneeDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}CustomerDetailsType"/>
 *         &lt;element name="awbAgentDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AgentDetailsType" minOccurs="0"/>
 *         &lt;element name="awbScreeningDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentScreeningType" minOccurs="0"/>
 *         &lt;element name="awbHandlingInfoDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBHandlingInfoType" minOccurs="0"/>
 *         &lt;element name="additionalNotificationDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="additionalNotification" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}CustomerDetailsType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="awbAccountingInformationDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="awbAccountingInformation" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AccountingInformationType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="awbChargeDeclaration" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBChargeDeclarationType" minOccurs="0"/>
 *         &lt;element name="houseAWBs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="houseSummaryDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="houseSummary" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}HouseSummaryDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="awbRateLineDetails">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="awbRateLine" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBRateLineType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="awbOtherChargeDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="awbOtherCharges" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBOtherChargesType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="prepaidChargeSummary" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBPrepaidChargesSummmaryType" minOccurs="0"/>
 *         &lt;element name="collectChargeSummary" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBCollectChargesSummmaryType" minOccurs="0"/>
 *         &lt;element name="shipperCertification" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipperCertificationType" minOccurs="0"/>
 *         &lt;element name="awbExecutionDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBExecutionDetailsType" minOccurs="0"/>
 *         &lt;element name="collectChargesInDestinationCurrency" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}CollectChargesInDestinationCurrencyType" minOccurs="0"/>
 *         &lt;element name="customsOriginCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="T1"/>
 *               &lt;enumeration value="T2"/>
 *               &lt;enumeration value="TD"/>
 *               &lt;enumeration value="TF"/>
 *               &lt;enumeration value="X"/>
 *               &lt;enumeration value="F"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="nominatedHandlingParty" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}NominatedHandlingPartyType" minOccurs="0"/>
 *         &lt;element name="shipmentReferenceInformation" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentReferenceInformationType" minOccurs="0"/>
 *         &lt;element name="otherCustomsInformationDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="otherCustomsInformation" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}OtherCustomsInformationType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="additionalCustomsInformation" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="customsAuthority" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;enumeration value="GB"/>
 *                                   &lt;enumeration value="SA"/>
 *                                   &lt;enumeration value="AU"/>
 *                                   &lt;enumeration value="ZOLL"/>
 *                                   &lt;enumeration value="CN"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="parameterType" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="0"/>
 *                                   &lt;maxLength value="250"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="parameterValue" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="0"/>
 *                                   &lt;maxLength value="50"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="otherParticipantInformationDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="OtherParticipantInformation" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}OtherParticipantInformationType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="commissionInformation" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}CommissionInformationType" minOccurs="0"/>
 *         &lt;element name="salesIncentiveInformation" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}SalesIncentiveInformationType" minOccurs="0"/>
 *         &lt;element name="agentFileReferenceData" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *         &lt;element name="serviceCargoClass" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="F"/>
 *               &lt;enumeration value="T"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="isConsolShipmentFlag" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="eHouseManifestFlag" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="executeAWBFlag" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="houseAWBDocumentFinalizedFlag" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="awbStatus" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="productDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ProductDetailsType" minOccurs="0"/>
 *         &lt;element name="awbSupplementaryDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="awbVersionNumber" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;pattern value="[0-9]{1,5}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="awbQuality" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="0"/>
 *                         &lt;maxLength value="3"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="shipmentDescription" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="0"/>
 *                         &lt;maxLength value="250"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="awbAirlineChargeDetails" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="netNetCharge" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountTypeNeg" minOccurs="0"/>
 *                             &lt;element name="ValuationCharge" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *                             &lt;element name="meansOfPayment" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                                   &lt;enumeration value="CA"/>
 *                                   &lt;enumeration value="CT"/>
 *                                   &lt;enumeration value="CQ"/>
 *                                   &lt;enumeration value="CR"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="paymentType" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                                   &lt;enumeration value="P"/>
 *                                   &lt;enumeration value="C"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="modeOfChargeCalculation" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;pattern value="[a-zA-Z0-9]{0,3}"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="marketRateLineID" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="0"/>
 *                                   &lt;maxLength value="30"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="techKeyForMarketLineID" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                                   &lt;pattern value="[a-zA-Z0-9]{1,8}"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="promotionType" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                                   &lt;enumeration value="CH"/>
 *                                   &lt;enumeration value="FL"/>
 *                                   &lt;enumeration value="RA"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="lastEvalDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date" minOccurs="0"/>
 *                             &lt;element name="globalSalesCommission" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *                             &lt;element name="additionalCommission" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountTypeNeg" minOccurs="0"/>
 *                             &lt;element name="jointRevenue" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *                             &lt;element name="evaluationType" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                                   &lt;enumeration value="G"/>
 *                                   &lt;enumeration value="M"/>
 *                                   &lt;enumeration value="S"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="quotationNumber" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;pattern value="[a-zA-Z0-9\-]{0,30}"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="quotationRevisionNo" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;pattern value="[0-9]{1,5}"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="awbHandlingChargeDetails" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="awbTerminalCharge" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBTerminalChargeType" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="numberOfHouses" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;pattern value="[0-9]{1,5}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="isCCAAvailable" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="Y"/>
 *                         &lt;enumeration value="N"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="reqFlightCarrierCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}flightCarrierCode" minOccurs="0"/>
 *         &lt;element name="ubrNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ubrNumber" minOccurs="0"/>
 *         &lt;element name="reqFlightNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}flightNumber" minOccurs="0"/>
 *         &lt;element name="reqFlightDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date" minOccurs="0"/>
 *         &lt;element name="eFreightFlag" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="lastUpdatedTime" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}DateTime_MiliSec" minOccurs="0"/>
 *         &lt;element name="dataCaptureFlag" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="shipmenteDGDFlag" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="X"/>
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="uniqueReference" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}UniqueReferenceType" minOccurs="0"/>
 *         &lt;element name="awbMiscellaneousDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="allotmentName" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="0"/>
 *                         &lt;maxLength value="50"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="allotmentDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date" minOccurs="0"/>
 *                   &lt;element name="awbSource" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="0"/>
 *                         &lt;maxLength value="50"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="surfaceOrigin" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode" minOccurs="0"/>
 *                   &lt;element name="surfaceDestination" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode" minOccurs="0"/>
 *                   &lt;element name="awbCostCenter" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="0"/>
 *                         &lt;maxLength value="50"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="additionalAccountingRemarks" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="0"/>
 *                         &lt;maxLength value="500"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="eventInformationDetails" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="timeOfReceiptUTC" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *                             &lt;element name="component" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="0"/>
 *                                   &lt;maxLength value="250"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="processName" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="0"/>
 *                                   &lt;maxLength value="250"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="actorType" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="0"/>
 *                                   &lt;maxLength value="250"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="actorName" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="0"/>
 *                                   &lt;maxLength value="250"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="station" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}stationCode" minOccurs="0"/>
 *                             &lt;element name="office" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="0"/>
 *                                   &lt;maxLength value="250"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="processOriginator" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="0"/>
 *                                   &lt;maxLength value="250"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AWBDetailsType", propOrder = {
    "awbConsignmentDetails",
    "flightBookingDetails",
    "awbRoutingDetails",
    "awbShipperDetails",
    "awbConsigneeDetails",
    "awbAgentDetails",
    "awbScreeningDetails",
    "awbHandlingInfoDetails",
    "additionalNotificationDetails",
    "awbAccountingInformationDetails",
    "awbChargeDeclaration",
    "houseAWBs",
    "houseSummaryDetails",
    "awbRateLineDetails",
    "awbOtherChargeDetails",
    "prepaidChargeSummary",
    "collectChargeSummary",
    "shipperCertification",
    "awbExecutionDetails",
    "collectChargesInDestinationCurrency",
    "customsOriginCode",
    "nominatedHandlingParty",
    "shipmentReferenceInformation",
    "otherCustomsInformationDetails",
    "otherParticipantInformationDetails",
    "commissionInformation",
    "salesIncentiveInformation",
    "agentFileReferenceData",
    "serviceCargoClass",
    "isConsolShipmentFlag",
    "eHouseManifestFlag",
    "executeAWBFlag",
    "houseAWBDocumentFinalizedFlag",
    "awbStatus",
    "productDetails",
    "awbSupplementaryDetails",
    "reqFlightCarrierCode",
    "ubrNumber",
    "reqFlightNumber",
    "reqFlightDate",
    "eFreightFlag",
    "lastUpdatedTime",
    "dataCaptureFlag",
    "shipmenteDGDFlag",
    "uniqueReference",
    "awbMiscellaneousDetails"
})
public class AWBDetailsType {

    @XmlElement(required = true)
    protected AWBConsignmentDetailsType awbConsignmentDetails;
    protected List<FlightBookingDetailsType> flightBookingDetails;
    @XmlElement(required = true)
    protected AWBRoutingDetailsType awbRoutingDetails;
    @XmlElement(required = true)
    protected CustomerDetailsType awbShipperDetails;
    @XmlElement(required = true)
    protected CustomerDetailsType awbConsigneeDetails;
    protected AgentDetailsType awbAgentDetails;
    protected ShipmentScreeningType awbScreeningDetails;
    protected AWBHandlingInfoType awbHandlingInfoDetails;
    protected AWBDetailsType.AdditionalNotificationDetails additionalNotificationDetails;
    protected AWBDetailsType.AwbAccountingInformationDetails awbAccountingInformationDetails;
    protected AWBChargeDeclarationType awbChargeDeclaration;
    protected String houseAWBs;
    protected AWBDetailsType.HouseSummaryDetails houseSummaryDetails;
    @XmlElement(required = true)
    protected AWBDetailsType.AwbRateLineDetails awbRateLineDetails;
    protected AWBDetailsType.AwbOtherChargeDetails awbOtherChargeDetails;
    protected AWBPrepaidChargesSummmaryType prepaidChargeSummary;
    protected AWBCollectChargesSummmaryType collectChargeSummary;
    protected ShipperCertificationType shipperCertification;
    protected AWBExecutionDetailsType awbExecutionDetails;
    protected CollectChargesInDestinationCurrencyType collectChargesInDestinationCurrency;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String customsOriginCode;
    protected NominatedHandlingPartyType nominatedHandlingParty;
    protected ShipmentReferenceInformationType shipmentReferenceInformation;
    protected AWBDetailsType.OtherCustomsInformationDetails otherCustomsInformationDetails;
    protected AWBDetailsType.OtherParticipantInformationDetails otherParticipantInformationDetails;
    protected CommissionInformationType commissionInformation;
    protected SalesIncentiveInformationType salesIncentiveInformation;
    protected String agentFileReferenceData;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String serviceCargoClass;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isConsolShipmentFlag;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String eHouseManifestFlag;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String executeAWBFlag;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String houseAWBDocumentFinalizedFlag;
    protected String awbStatus;
    protected ProductDetailsType productDetails;
    protected AWBDetailsType.AwbSupplementaryDetails awbSupplementaryDetails;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String reqFlightCarrierCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String ubrNumber;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String reqFlightNumber;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String reqFlightDate;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String eFreightFlag;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String lastUpdatedTime;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String dataCaptureFlag;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String shipmenteDGDFlag;
    protected UniqueReferenceType uniqueReference;
    protected AWBDetailsType.AwbMiscellaneousDetails awbMiscellaneousDetails;

    /**
     * Gets the value of the awbConsignmentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBConsignmentDetailsType }
     *     
     */
    public AWBConsignmentDetailsType getAwbConsignmentDetails() {
        return awbConsignmentDetails;
    }

    /**
     * Sets the value of the awbConsignmentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBConsignmentDetailsType }
     *     
     */
    public void setAwbConsignmentDetails(AWBConsignmentDetailsType value) {
        this.awbConsignmentDetails = value;
    }

    /**
     * Gets the value of the flightBookingDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flightBookingDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlightBookingDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FlightBookingDetailsType }
     * 
     * 
     */
    public List<FlightBookingDetailsType> getFlightBookingDetails() {
        if (flightBookingDetails == null) {
            flightBookingDetails = new ArrayList<FlightBookingDetailsType>();
        }
        return this.flightBookingDetails;
    }

    /**
     * Gets the value of the awbRoutingDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBRoutingDetailsType }
     *     
     */
    public AWBRoutingDetailsType getAwbRoutingDetails() {
        return awbRoutingDetails;
    }

    /**
     * Sets the value of the awbRoutingDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBRoutingDetailsType }
     *     
     */
    public void setAwbRoutingDetails(AWBRoutingDetailsType value) {
        this.awbRoutingDetails = value;
    }

    /**
     * Gets the value of the awbShipperDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerDetailsType }
     *     
     */
    public CustomerDetailsType getAwbShipperDetails() {
        return awbShipperDetails;
    }

    /**
     * Sets the value of the awbShipperDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerDetailsType }
     *     
     */
    public void setAwbShipperDetails(CustomerDetailsType value) {
        this.awbShipperDetails = value;
    }

    /**
     * Gets the value of the awbConsigneeDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerDetailsType }
     *     
     */
    public CustomerDetailsType getAwbConsigneeDetails() {
        return awbConsigneeDetails;
    }

    /**
     * Sets the value of the awbConsigneeDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerDetailsType }
     *     
     */
    public void setAwbConsigneeDetails(CustomerDetailsType value) {
        this.awbConsigneeDetails = value;
    }

    /**
     * Gets the value of the awbAgentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AgentDetailsType }
     *     
     */
    public AgentDetailsType getAwbAgentDetails() {
        return awbAgentDetails;
    }

    /**
     * Sets the value of the awbAgentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgentDetailsType }
     *     
     */
    public void setAwbAgentDetails(AgentDetailsType value) {
        this.awbAgentDetails = value;
    }

    /**
     * Gets the value of the awbScreeningDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentScreeningType }
     *     
     */
    public ShipmentScreeningType getAwbScreeningDetails() {
        return awbScreeningDetails;
    }

    /**
     * Sets the value of the awbScreeningDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentScreeningType }
     *     
     */
    public void setAwbScreeningDetails(ShipmentScreeningType value) {
        this.awbScreeningDetails = value;
    }

    /**
     * Gets the value of the awbHandlingInfoDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBHandlingInfoType }
     *     
     */
    public AWBHandlingInfoType getAwbHandlingInfoDetails() {
        return awbHandlingInfoDetails;
    }

    /**
     * Sets the value of the awbHandlingInfoDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBHandlingInfoType }
     *     
     */
    public void setAwbHandlingInfoDetails(AWBHandlingInfoType value) {
        this.awbHandlingInfoDetails = value;
    }

    /**
     * Gets the value of the additionalNotificationDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBDetailsType.AdditionalNotificationDetails }
     *     
     */
    public AWBDetailsType.AdditionalNotificationDetails getAdditionalNotificationDetails() {
        return additionalNotificationDetails;
    }

    /**
     * Sets the value of the additionalNotificationDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBDetailsType.AdditionalNotificationDetails }
     *     
     */
    public void setAdditionalNotificationDetails(AWBDetailsType.AdditionalNotificationDetails value) {
        this.additionalNotificationDetails = value;
    }

    /**
     * Gets the value of the awbAccountingInformationDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBDetailsType.AwbAccountingInformationDetails }
     *     
     */
    public AWBDetailsType.AwbAccountingInformationDetails getAwbAccountingInformationDetails() {
        return awbAccountingInformationDetails;
    }

    /**
     * Sets the value of the awbAccountingInformationDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBDetailsType.AwbAccountingInformationDetails }
     *     
     */
    public void setAwbAccountingInformationDetails(AWBDetailsType.AwbAccountingInformationDetails value) {
        this.awbAccountingInformationDetails = value;
    }

    /**
     * Gets the value of the awbChargeDeclaration property.
     * 
     * @return
     *     possible object is
     *     {@link AWBChargeDeclarationType }
     *     
     */
    public AWBChargeDeclarationType getAwbChargeDeclaration() {
        return awbChargeDeclaration;
    }

    /**
     * Sets the value of the awbChargeDeclaration property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBChargeDeclarationType }
     *     
     */
    public void setAwbChargeDeclaration(AWBChargeDeclarationType value) {
        this.awbChargeDeclaration = value;
    }

    /**
     * Gets the value of the houseAWBs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHouseAWBs() {
        return houseAWBs;
    }

    /**
     * Sets the value of the houseAWBs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHouseAWBs(String value) {
        this.houseAWBs = value;
    }

    /**
     * Gets the value of the houseSummaryDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBDetailsType.HouseSummaryDetails }
     *     
     */
    public AWBDetailsType.HouseSummaryDetails getHouseSummaryDetails() {
        return houseSummaryDetails;
    }

    /**
     * Sets the value of the houseSummaryDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBDetailsType.HouseSummaryDetails }
     *     
     */
    public void setHouseSummaryDetails(AWBDetailsType.HouseSummaryDetails value) {
        this.houseSummaryDetails = value;
    }

    /**
     * Gets the value of the awbRateLineDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBDetailsType.AwbRateLineDetails }
     *     
     */
    public AWBDetailsType.AwbRateLineDetails getAwbRateLineDetails() {
        return awbRateLineDetails;
    }

    /**
     * Sets the value of the awbRateLineDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBDetailsType.AwbRateLineDetails }
     *     
     */
    public void setAwbRateLineDetails(AWBDetailsType.AwbRateLineDetails value) {
        this.awbRateLineDetails = value;
    }

    /**
     * Gets the value of the awbOtherChargeDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBDetailsType.AwbOtherChargeDetails }
     *     
     */
    public AWBDetailsType.AwbOtherChargeDetails getAwbOtherChargeDetails() {
        return awbOtherChargeDetails;
    }

    /**
     * Sets the value of the awbOtherChargeDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBDetailsType.AwbOtherChargeDetails }
     *     
     */
    public void setAwbOtherChargeDetails(AWBDetailsType.AwbOtherChargeDetails value) {
        this.awbOtherChargeDetails = value;
    }

    /**
     * Gets the value of the prepaidChargeSummary property.
     * 
     * @return
     *     possible object is
     *     {@link AWBPrepaidChargesSummmaryType }
     *     
     */
    public AWBPrepaidChargesSummmaryType getPrepaidChargeSummary() {
        return prepaidChargeSummary;
    }

    /**
     * Sets the value of the prepaidChargeSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBPrepaidChargesSummmaryType }
     *     
     */
    public void setPrepaidChargeSummary(AWBPrepaidChargesSummmaryType value) {
        this.prepaidChargeSummary = value;
    }

    /**
     * Gets the value of the collectChargeSummary property.
     * 
     * @return
     *     possible object is
     *     {@link AWBCollectChargesSummmaryType }
     *     
     */
    public AWBCollectChargesSummmaryType getCollectChargeSummary() {
        return collectChargeSummary;
    }

    /**
     * Sets the value of the collectChargeSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBCollectChargesSummmaryType }
     *     
     */
    public void setCollectChargeSummary(AWBCollectChargesSummmaryType value) {
        this.collectChargeSummary = value;
    }

    /**
     * Gets the value of the shipperCertification property.
     * 
     * @return
     *     possible object is
     *     {@link ShipperCertificationType }
     *     
     */
    public ShipperCertificationType getShipperCertification() {
        return shipperCertification;
    }

    /**
     * Sets the value of the shipperCertification property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipperCertificationType }
     *     
     */
    public void setShipperCertification(ShipperCertificationType value) {
        this.shipperCertification = value;
    }

    /**
     * Gets the value of the awbExecutionDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBExecutionDetailsType }
     *     
     */
    public AWBExecutionDetailsType getAwbExecutionDetails() {
        return awbExecutionDetails;
    }

    /**
     * Sets the value of the awbExecutionDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBExecutionDetailsType }
     *     
     */
    public void setAwbExecutionDetails(AWBExecutionDetailsType value) {
        this.awbExecutionDetails = value;
    }

    /**
     * Gets the value of the collectChargesInDestinationCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link CollectChargesInDestinationCurrencyType }
     *     
     */
    public CollectChargesInDestinationCurrencyType getCollectChargesInDestinationCurrency() {
        return collectChargesInDestinationCurrency;
    }

    /**
     * Sets the value of the collectChargesInDestinationCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollectChargesInDestinationCurrencyType }
     *     
     */
    public void setCollectChargesInDestinationCurrency(CollectChargesInDestinationCurrencyType value) {
        this.collectChargesInDestinationCurrency = value;
    }

    /**
     * Gets the value of the customsOriginCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomsOriginCode() {
        return customsOriginCode;
    }

    /**
     * Sets the value of the customsOriginCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomsOriginCode(String value) {
        this.customsOriginCode = value;
    }

    /**
     * Gets the value of the nominatedHandlingParty property.
     * 
     * @return
     *     possible object is
     *     {@link NominatedHandlingPartyType }
     *     
     */
    public NominatedHandlingPartyType getNominatedHandlingParty() {
        return nominatedHandlingParty;
    }

    /**
     * Sets the value of the nominatedHandlingParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link NominatedHandlingPartyType }
     *     
     */
    public void setNominatedHandlingParty(NominatedHandlingPartyType value) {
        this.nominatedHandlingParty = value;
    }

    /**
     * Gets the value of the shipmentReferenceInformation property.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentReferenceInformationType }
     *     
     */
    public ShipmentReferenceInformationType getShipmentReferenceInformation() {
        return shipmentReferenceInformation;
    }

    /**
     * Sets the value of the shipmentReferenceInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentReferenceInformationType }
     *     
     */
    public void setShipmentReferenceInformation(ShipmentReferenceInformationType value) {
        this.shipmentReferenceInformation = value;
    }

    /**
     * Gets the value of the otherCustomsInformationDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBDetailsType.OtherCustomsInformationDetails }
     *     
     */
    public AWBDetailsType.OtherCustomsInformationDetails getOtherCustomsInformationDetails() {
        return otherCustomsInformationDetails;
    }

    /**
     * Sets the value of the otherCustomsInformationDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBDetailsType.OtherCustomsInformationDetails }
     *     
     */
    public void setOtherCustomsInformationDetails(AWBDetailsType.OtherCustomsInformationDetails value) {
        this.otherCustomsInformationDetails = value;
    }

    /**
     * Gets the value of the otherParticipantInformationDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBDetailsType.OtherParticipantInformationDetails }
     *     
     */
    public AWBDetailsType.OtherParticipantInformationDetails getOtherParticipantInformationDetails() {
        return otherParticipantInformationDetails;
    }

    /**
     * Sets the value of the otherParticipantInformationDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBDetailsType.OtherParticipantInformationDetails }
     *     
     */
    public void setOtherParticipantInformationDetails(AWBDetailsType.OtherParticipantInformationDetails value) {
        this.otherParticipantInformationDetails = value;
    }

    /**
     * Gets the value of the commissionInformation property.
     * 
     * @return
     *     possible object is
     *     {@link CommissionInformationType }
     *     
     */
    public CommissionInformationType getCommissionInformation() {
        return commissionInformation;
    }

    /**
     * Sets the value of the commissionInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommissionInformationType }
     *     
     */
    public void setCommissionInformation(CommissionInformationType value) {
        this.commissionInformation = value;
    }

    /**
     * Gets the value of the salesIncentiveInformation property.
     * 
     * @return
     *     possible object is
     *     {@link SalesIncentiveInformationType }
     *     
     */
    public SalesIncentiveInformationType getSalesIncentiveInformation() {
        return salesIncentiveInformation;
    }

    /**
     * Sets the value of the salesIncentiveInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesIncentiveInformationType }
     *     
     */
    public void setSalesIncentiveInformation(SalesIncentiveInformationType value) {
        this.salesIncentiveInformation = value;
    }

    /**
     * Gets the value of the agentFileReferenceData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentFileReferenceData() {
        return agentFileReferenceData;
    }

    /**
     * Sets the value of the agentFileReferenceData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentFileReferenceData(String value) {
        this.agentFileReferenceData = value;
    }

    /**
     * Gets the value of the serviceCargoClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceCargoClass() {
        return serviceCargoClass;
    }

    /**
     * Sets the value of the serviceCargoClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceCargoClass(String value) {
        this.serviceCargoClass = value;
    }

    /**
     * Gets the value of the isConsolShipmentFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsConsolShipmentFlag() {
        return isConsolShipmentFlag;
    }

    /**
     * Sets the value of the isConsolShipmentFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsConsolShipmentFlag(String value) {
        this.isConsolShipmentFlag = value;
    }

    /**
     * Gets the value of the eHouseManifestFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEHouseManifestFlag() {
        return eHouseManifestFlag;
    }

    /**
     * Sets the value of the eHouseManifestFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEHouseManifestFlag(String value) {
        this.eHouseManifestFlag = value;
    }

    /**
     * Gets the value of the executeAWBFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExecuteAWBFlag() {
        return executeAWBFlag;
    }

    /**
     * Sets the value of the executeAWBFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExecuteAWBFlag(String value) {
        this.executeAWBFlag = value;
    }

    /**
     * Gets the value of the houseAWBDocumentFinalizedFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHouseAWBDocumentFinalizedFlag() {
        return houseAWBDocumentFinalizedFlag;
    }

    /**
     * Sets the value of the houseAWBDocumentFinalizedFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHouseAWBDocumentFinalizedFlag(String value) {
        this.houseAWBDocumentFinalizedFlag = value;
    }

    /**
     * Gets the value of the awbStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwbStatus() {
        return awbStatus;
    }

    /**
     * Sets the value of the awbStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwbStatus(String value) {
        this.awbStatus = value;
    }

    /**
     * Gets the value of the productDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ProductDetailsType }
     *     
     */
    public ProductDetailsType getProductDetails() {
        return productDetails;
    }

    /**
     * Sets the value of the productDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductDetailsType }
     *     
     */
    public void setProductDetails(ProductDetailsType value) {
        this.productDetails = value;
    }

    /**
     * Gets the value of the awbSupplementaryDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBDetailsType.AwbSupplementaryDetails }
     *     
     */
    public AWBDetailsType.AwbSupplementaryDetails getAwbSupplementaryDetails() {
        return awbSupplementaryDetails;
    }

    /**
     * Sets the value of the awbSupplementaryDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBDetailsType.AwbSupplementaryDetails }
     *     
     */
    public void setAwbSupplementaryDetails(AWBDetailsType.AwbSupplementaryDetails value) {
        this.awbSupplementaryDetails = value;
    }

    /**
     * Gets the value of the reqFlightCarrierCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqFlightCarrierCode() {
        return reqFlightCarrierCode;
    }

    /**
     * Sets the value of the reqFlightCarrierCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqFlightCarrierCode(String value) {
        this.reqFlightCarrierCode = value;
    }

    /**
     * Gets the value of the ubrNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUbrNumber() {
        return ubrNumber;
    }

    /**
     * Sets the value of the ubrNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUbrNumber(String value) {
        this.ubrNumber = value;
    }

    /**
     * Gets the value of the reqFlightNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqFlightNumber() {
        return reqFlightNumber;
    }

    /**
     * Sets the value of the reqFlightNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqFlightNumber(String value) {
        this.reqFlightNumber = value;
    }

    /**
     * Gets the value of the reqFlightDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqFlightDate() {
        return reqFlightDate;
    }

    /**
     * Sets the value of the reqFlightDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqFlightDate(String value) {
        this.reqFlightDate = value;
    }

    /**
     * Gets the value of the eFreightFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEFreightFlag() {
        return eFreightFlag;
    }

    /**
     * Sets the value of the eFreightFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEFreightFlag(String value) {
        this.eFreightFlag = value;
    }

    /**
     * Gets the value of the lastUpdatedTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /**
     * Sets the value of the lastUpdatedTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdatedTime(String value) {
        this.lastUpdatedTime = value;
    }

    /**
     * Gets the value of the dataCaptureFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataCaptureFlag() {
        return dataCaptureFlag;
    }

    /**
     * Sets the value of the dataCaptureFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataCaptureFlag(String value) {
        this.dataCaptureFlag = value;
    }

    /**
     * Gets the value of the shipmenteDGDFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipmenteDGDFlag() {
        return shipmenteDGDFlag;
    }

    /**
     * Sets the value of the shipmenteDGDFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipmenteDGDFlag(String value) {
        this.shipmenteDGDFlag = value;
    }

    /**
     * Gets the value of the uniqueReference property.
     * 
     * @return
     *     possible object is
     *     {@link UniqueReferenceType }
     *     
     */
    public UniqueReferenceType getUniqueReference() {
        return uniqueReference;
    }

    /**
     * Sets the value of the uniqueReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link UniqueReferenceType }
     *     
     */
    public void setUniqueReference(UniqueReferenceType value) {
        this.uniqueReference = value;
    }

    /**
     * Gets the value of the awbMiscellaneousDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBDetailsType.AwbMiscellaneousDetails }
     *     
     */
    public AWBDetailsType.AwbMiscellaneousDetails getAwbMiscellaneousDetails() {
        return awbMiscellaneousDetails;
    }

    /**
     * Sets the value of the awbMiscellaneousDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBDetailsType.AwbMiscellaneousDetails }
     *     
     */
    public void setAwbMiscellaneousDetails(AWBDetailsType.AwbMiscellaneousDetails value) {
        this.awbMiscellaneousDetails = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="additionalNotification" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}CustomerDetailsType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "additionalNotification"
    })
    public static class AdditionalNotificationDetails {

        @XmlElement(required = true)
        protected List<CustomerDetailsType> additionalNotification;

        /**
         * Gets the value of the additionalNotification property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the additionalNotification property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAdditionalNotification().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CustomerDetailsType }
         * 
         * 
         */
        public List<CustomerDetailsType> getAdditionalNotification() {
            if (additionalNotification == null) {
                additionalNotification = new ArrayList<CustomerDetailsType>();
            }
            return this.additionalNotification;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="awbAccountingInformation" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AccountingInformationType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "awbAccountingInformation"
    })
    public static class AwbAccountingInformationDetails {

        @XmlElement(required = true)
        protected List<AccountingInformationType> awbAccountingInformation;

        /**
         * Gets the value of the awbAccountingInformation property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the awbAccountingInformation property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAwbAccountingInformation().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AccountingInformationType }
         * 
         * 
         */
        public List<AccountingInformationType> getAwbAccountingInformation() {
            if (awbAccountingInformation == null) {
                awbAccountingInformation = new ArrayList<AccountingInformationType>();
            }
            return this.awbAccountingInformation;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="allotmentName" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="0"/>
     *               &lt;maxLength value="50"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="allotmentDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date" minOccurs="0"/>
     *         &lt;element name="awbSource" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="0"/>
     *               &lt;maxLength value="50"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="surfaceOrigin" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode" minOccurs="0"/>
     *         &lt;element name="surfaceDestination" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode" minOccurs="0"/>
     *         &lt;element name="awbCostCenter" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="0"/>
     *               &lt;maxLength value="50"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="additionalAccountingRemarks" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="0"/>
     *               &lt;maxLength value="500"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="eventInformationDetails" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="timeOfReceiptUTC" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
     *                   &lt;element name="component" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="0"/>
     *                         &lt;maxLength value="250"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="processName" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="0"/>
     *                         &lt;maxLength value="250"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="actorType" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="0"/>
     *                         &lt;maxLength value="250"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="actorName" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="0"/>
     *                         &lt;maxLength value="250"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="station" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}stationCode" minOccurs="0"/>
     *                   &lt;element name="office" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="0"/>
     *                         &lt;maxLength value="250"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="processOriginator" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="0"/>
     *                         &lt;maxLength value="250"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "allotmentName",
        "allotmentDate",
        "awbSource",
        "surfaceOrigin",
        "surfaceDestination",
        "awbCostCenter",
        "additionalAccountingRemarks",
        "eventInformationDetails"
    })
    public static class AwbMiscellaneousDetails {

        protected String allotmentName;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String allotmentDate;
        protected String awbSource;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String surfaceOrigin;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String surfaceDestination;
        protected String awbCostCenter;
        protected String additionalAccountingRemarks;
        protected AWBDetailsType.AwbMiscellaneousDetails.EventInformationDetails eventInformationDetails;

        /**
         * Gets the value of the allotmentName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAllotmentName() {
            return allotmentName;
        }

        /**
         * Sets the value of the allotmentName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAllotmentName(String value) {
            this.allotmentName = value;
        }

        /**
         * Gets the value of the allotmentDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAllotmentDate() {
            return allotmentDate;
        }

        /**
         * Sets the value of the allotmentDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAllotmentDate(String value) {
            this.allotmentDate = value;
        }

        /**
         * Gets the value of the awbSource property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAwbSource() {
            return awbSource;
        }

        /**
         * Sets the value of the awbSource property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAwbSource(String value) {
            this.awbSource = value;
        }

        /**
         * Gets the value of the surfaceOrigin property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSurfaceOrigin() {
            return surfaceOrigin;
        }

        /**
         * Sets the value of the surfaceOrigin property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSurfaceOrigin(String value) {
            this.surfaceOrigin = value;
        }

        /**
         * Gets the value of the surfaceDestination property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSurfaceDestination() {
            return surfaceDestination;
        }

        /**
         * Sets the value of the surfaceDestination property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSurfaceDestination(String value) {
            this.surfaceDestination = value;
        }

        /**
         * Gets the value of the awbCostCenter property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAwbCostCenter() {
            return awbCostCenter;
        }

        /**
         * Sets the value of the awbCostCenter property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAwbCostCenter(String value) {
            this.awbCostCenter = value;
        }

        /**
         * Gets the value of the additionalAccountingRemarks property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAdditionalAccountingRemarks() {
            return additionalAccountingRemarks;
        }

        /**
         * Sets the value of the additionalAccountingRemarks property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAdditionalAccountingRemarks(String value) {
            this.additionalAccountingRemarks = value;
        }

        /**
         * Gets the value of the eventInformationDetails property.
         * 
         * @return
         *     possible object is
         *     {@link AWBDetailsType.AwbMiscellaneousDetails.EventInformationDetails }
         *     
         */
        public AWBDetailsType.AwbMiscellaneousDetails.EventInformationDetails getEventInformationDetails() {
            return eventInformationDetails;
        }

        /**
         * Sets the value of the eventInformationDetails property.
         * 
         * @param value
         *     allowed object is
         *     {@link AWBDetailsType.AwbMiscellaneousDetails.EventInformationDetails }
         *     
         */
        public void setEventInformationDetails(AWBDetailsType.AwbMiscellaneousDetails.EventInformationDetails value) {
            this.eventInformationDetails = value;
        }


        /**
         * 
         * 										The node containing LCAG
         * 										specific event details
         * 									
         * 
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="timeOfReceiptUTC" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
         *         &lt;element name="component" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;minLength value="0"/>
         *               &lt;maxLength value="250"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="processName" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;minLength value="0"/>
         *               &lt;maxLength value="250"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="actorType" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;minLength value="0"/>
         *               &lt;maxLength value="250"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="actorName" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;minLength value="0"/>
         *               &lt;maxLength value="250"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="station" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}stationCode" minOccurs="0"/>
         *         &lt;element name="office" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;minLength value="0"/>
         *               &lt;maxLength value="250"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="processOriginator" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;minLength value="0"/>
         *               &lt;maxLength value="250"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "timeOfReceiptUTC",
            "component",
            "processName",
            "actorType",
            "actorName",
            "station",
            "office",
            "processOriginator"
        })
        public static class EventInformationDetails {

            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String timeOfReceiptUTC;
            protected String component;
            protected String processName;
            protected String actorType;
            protected String actorName;
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String station;
            protected String office;
            protected String processOriginator;

            /**
             * Gets the value of the timeOfReceiptUTC property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTimeOfReceiptUTC() {
                return timeOfReceiptUTC;
            }

            /**
             * Sets the value of the timeOfReceiptUTC property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTimeOfReceiptUTC(String value) {
                this.timeOfReceiptUTC = value;
            }

            /**
             * Gets the value of the component property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getComponent() {
                return component;
            }

            /**
             * Sets the value of the component property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setComponent(String value) {
                this.component = value;
            }

            /**
             * Gets the value of the processName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getProcessName() {
                return processName;
            }

            /**
             * Sets the value of the processName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setProcessName(String value) {
                this.processName = value;
            }

            /**
             * Gets the value of the actorType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getActorType() {
                return actorType;
            }

            /**
             * Sets the value of the actorType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setActorType(String value) {
                this.actorType = value;
            }

            /**
             * Gets the value of the actorName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getActorName() {
                return actorName;
            }

            /**
             * Sets the value of the actorName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setActorName(String value) {
                this.actorName = value;
            }

            /**
             * Gets the value of the station property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStation() {
                return station;
            }

            /**
             * Sets the value of the station property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStation(String value) {
                this.station = value;
            }

            /**
             * Gets the value of the office property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOffice() {
                return office;
            }

            /**
             * Sets the value of the office property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOffice(String value) {
                this.office = value;
            }

            /**
             * Gets the value of the processOriginator property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getProcessOriginator() {
                return processOriginator;
            }

            /**
             * Sets the value of the processOriginator property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setProcessOriginator(String value) {
                this.processOriginator = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="awbOtherCharges" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBOtherChargesType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "awbOtherCharges"
    })
    public static class AwbOtherChargeDetails {

        @XmlElement(required = true)
        protected List<AWBOtherChargesType> awbOtherCharges;

        /**
         * Gets the value of the awbOtherCharges property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the awbOtherCharges property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAwbOtherCharges().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AWBOtherChargesType }
         * 
         * 
         */
        public List<AWBOtherChargesType> getAwbOtherCharges() {
            if (awbOtherCharges == null) {
                awbOtherCharges = new ArrayList<AWBOtherChargesType>();
            }
            return this.awbOtherCharges;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="awbRateLine" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBRateLineType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "awbRateLine"
    })
    public static class AwbRateLineDetails {

        @XmlElement(required = true)
        protected List<AWBRateLineType> awbRateLine;

        /**
         * Gets the value of the awbRateLine property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the awbRateLine property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAwbRateLine().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AWBRateLineType }
         * 
         * 
         */
        public List<AWBRateLineType> getAwbRateLine() {
            if (awbRateLine == null) {
                awbRateLine = new ArrayList<AWBRateLineType>();
            }
            return this.awbRateLine;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="awbVersionNumber" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;pattern value="[0-9]{1,5}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="awbQuality" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="0"/>
     *               &lt;maxLength value="3"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="shipmentDescription" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="0"/>
     *               &lt;maxLength value="250"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="awbAirlineChargeDetails" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="netNetCharge" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountTypeNeg" minOccurs="0"/>
     *                   &lt;element name="ValuationCharge" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
     *                   &lt;element name="meansOfPayment" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *                         &lt;enumeration value="CA"/>
     *                         &lt;enumeration value="CT"/>
     *                         &lt;enumeration value="CQ"/>
     *                         &lt;enumeration value="CR"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="paymentType" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *                         &lt;enumeration value="P"/>
     *                         &lt;enumeration value="C"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="modeOfChargeCalculation" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;pattern value="[a-zA-Z0-9]{0,3}"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="marketRateLineID" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="0"/>
     *                         &lt;maxLength value="30"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="techKeyForMarketLineID" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *                         &lt;pattern value="[a-zA-Z0-9]{1,8}"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="promotionType" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *                         &lt;enumeration value="CH"/>
     *                         &lt;enumeration value="FL"/>
     *                         &lt;enumeration value="RA"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="lastEvalDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date" minOccurs="0"/>
     *                   &lt;element name="globalSalesCommission" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
     *                   &lt;element name="additionalCommission" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountTypeNeg" minOccurs="0"/>
     *                   &lt;element name="jointRevenue" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
     *                   &lt;element name="evaluationType" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *                         &lt;enumeration value="G"/>
     *                         &lt;enumeration value="M"/>
     *                         &lt;enumeration value="S"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="quotationNumber" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;pattern value="[a-zA-Z0-9\-]{0,30}"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="quotationRevisionNo" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;pattern value="[0-9]{1,5}"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="awbHandlingChargeDetails" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="awbTerminalCharge" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBTerminalChargeType" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="numberOfHouses" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;pattern value="[0-9]{1,5}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="isCCAAvailable" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="Y"/>
     *               &lt;enumeration value="N"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "awbVersionNumber",
        "awbQuality",
        "shipmentDescription",
        "awbAirlineChargeDetails",
        "awbHandlingChargeDetails",
        "numberOfHouses",
        "isCCAAvailable"
    })
    public static class AwbSupplementaryDetails {

        protected BigInteger awbVersionNumber;
        protected String awbQuality;
        protected String shipmentDescription;
        protected AWBDetailsType.AwbSupplementaryDetails.AwbAirlineChargeDetails awbAirlineChargeDetails;
        protected AWBDetailsType.AwbSupplementaryDetails.AwbHandlingChargeDetails awbHandlingChargeDetails;
        protected Integer numberOfHouses;
        protected String isCCAAvailable;

        /**
         * Gets the value of the awbVersionNumber property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getAwbVersionNumber() {
            return awbVersionNumber;
        }

        /**
         * Sets the value of the awbVersionNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setAwbVersionNumber(BigInteger value) {
            this.awbVersionNumber = value;
        }

        /**
         * Gets the value of the awbQuality property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAwbQuality() {
            return awbQuality;
        }

        /**
         * Sets the value of the awbQuality property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAwbQuality(String value) {
            this.awbQuality = value;
        }

        /**
         * Gets the value of the shipmentDescription property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getShipmentDescription() {
            return shipmentDescription;
        }

        /**
         * Sets the value of the shipmentDescription property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setShipmentDescription(String value) {
            this.shipmentDescription = value;
        }

        /**
         * Gets the value of the awbAirlineChargeDetails property.
         * 
         * @return
         *     possible object is
         *     {@link AWBDetailsType.AwbSupplementaryDetails.AwbAirlineChargeDetails }
         *     
         */
        public AWBDetailsType.AwbSupplementaryDetails.AwbAirlineChargeDetails getAwbAirlineChargeDetails() {
            return awbAirlineChargeDetails;
        }

        /**
         * Sets the value of the awbAirlineChargeDetails property.
         * 
         * @param value
         *     allowed object is
         *     {@link AWBDetailsType.AwbSupplementaryDetails.AwbAirlineChargeDetails }
         *     
         */
        public void setAwbAirlineChargeDetails(AWBDetailsType.AwbSupplementaryDetails.AwbAirlineChargeDetails value) {
            this.awbAirlineChargeDetails = value;
        }

        /**
         * Gets the value of the awbHandlingChargeDetails property.
         * 
         * @return
         *     possible object is
         *     {@link AWBDetailsType.AwbSupplementaryDetails.AwbHandlingChargeDetails }
         *     
         */
        public AWBDetailsType.AwbSupplementaryDetails.AwbHandlingChargeDetails getAwbHandlingChargeDetails() {
            return awbHandlingChargeDetails;
        }

        /**
         * Sets the value of the awbHandlingChargeDetails property.
         * 
         * @param value
         *     allowed object is
         *     {@link AWBDetailsType.AwbSupplementaryDetails.AwbHandlingChargeDetails }
         *     
         */
        public void setAwbHandlingChargeDetails(AWBDetailsType.AwbSupplementaryDetails.AwbHandlingChargeDetails value) {
            this.awbHandlingChargeDetails = value;
        }

        /**
         * Gets the value of the numberOfHouses property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getNumberOfHouses() {
            return numberOfHouses;
        }

        /**
         * Sets the value of the numberOfHouses property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setNumberOfHouses(Integer value) {
            this.numberOfHouses = value;
        }

        /**
         * Gets the value of the isCCAAvailable property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsCCAAvailable() {
            return isCCAAvailable;
        }

        /**
         * Sets the value of the isCCAAvailable property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsCCAAvailable(String value) {
            this.isCCAAvailable = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="netNetCharge" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountTypeNeg" minOccurs="0"/>
         *         &lt;element name="ValuationCharge" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
         *         &lt;element name="meansOfPayment" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
         *               &lt;enumeration value="CA"/>
         *               &lt;enumeration value="CT"/>
         *               &lt;enumeration value="CQ"/>
         *               &lt;enumeration value="CR"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="paymentType" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
         *               &lt;enumeration value="P"/>
         *               &lt;enumeration value="C"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="modeOfChargeCalculation" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;pattern value="[a-zA-Z0-9]{0,3}"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="marketRateLineID" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;minLength value="0"/>
         *               &lt;maxLength value="30"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="techKeyForMarketLineID" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
         *               &lt;pattern value="[a-zA-Z0-9]{1,8}"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="promotionType" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
         *               &lt;enumeration value="CH"/>
         *               &lt;enumeration value="FL"/>
         *               &lt;enumeration value="RA"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="lastEvalDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date" minOccurs="0"/>
         *         &lt;element name="globalSalesCommission" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
         *         &lt;element name="additionalCommission" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountTypeNeg" minOccurs="0"/>
         *         &lt;element name="jointRevenue" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
         *         &lt;element name="evaluationType" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
         *               &lt;enumeration value="G"/>
         *               &lt;enumeration value="M"/>
         *               &lt;enumeration value="S"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="quotationNumber" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;pattern value="[a-zA-Z0-9\-]{0,30}"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="quotationRevisionNo" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;pattern value="[0-9]{1,5}"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "netNetCharge",
            "valuationCharge",
            "meansOfPayment",
            "paymentType",
            "modeOfChargeCalculation",
            "marketRateLineID",
            "techKeyForMarketLineID",
            "promotionType",
            "lastEvalDate",
            "globalSalesCommission",
            "additionalCommission",
            "jointRevenue",
            "evaluationType",
            "quotationNumber",
            "quotationRevisionNo"
        })
        public static class AwbAirlineChargeDetails {

            protected BigDecimal netNetCharge;
            @XmlElement(name = "ValuationCharge")
            protected BigDecimal valuationCharge;
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String meansOfPayment;
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String paymentType;
            protected String modeOfChargeCalculation;
            protected String marketRateLineID;
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String techKeyForMarketLineID;
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String promotionType;
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String lastEvalDate;
            protected BigDecimal globalSalesCommission;
            protected BigDecimal additionalCommission;
            protected BigDecimal jointRevenue;
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String evaluationType;
            protected String quotationNumber;
            protected BigInteger quotationRevisionNo;

            /**
             * Gets the value of the netNetCharge property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getNetNetCharge() {
                return netNetCharge;
            }

            /**
             * Sets the value of the netNetCharge property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setNetNetCharge(BigDecimal value) {
                this.netNetCharge = value;
            }

            /**
             * Gets the value of the valuationCharge property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getValuationCharge() {
                return valuationCharge;
            }

            /**
             * Sets the value of the valuationCharge property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setValuationCharge(BigDecimal value) {
                this.valuationCharge = value;
            }

            /**
             * Gets the value of the meansOfPayment property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMeansOfPayment() {
                return meansOfPayment;
            }

            /**
             * Sets the value of the meansOfPayment property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMeansOfPayment(String value) {
                this.meansOfPayment = value;
            }

            /**
             * Gets the value of the paymentType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPaymentType() {
                return paymentType;
            }

            /**
             * Sets the value of the paymentType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPaymentType(String value) {
                this.paymentType = value;
            }

            /**
             * Gets the value of the modeOfChargeCalculation property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getModeOfChargeCalculation() {
                return modeOfChargeCalculation;
            }

            /**
             * Sets the value of the modeOfChargeCalculation property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setModeOfChargeCalculation(String value) {
                this.modeOfChargeCalculation = value;
            }

            /**
             * Gets the value of the marketRateLineID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMarketRateLineID() {
                return marketRateLineID;
            }

            /**
             * Sets the value of the marketRateLineID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMarketRateLineID(String value) {
                this.marketRateLineID = value;
            }

            /**
             * Gets the value of the techKeyForMarketLineID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTechKeyForMarketLineID() {
                return techKeyForMarketLineID;
            }

            /**
             * Sets the value of the techKeyForMarketLineID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTechKeyForMarketLineID(String value) {
                this.techKeyForMarketLineID = value;
            }

            /**
             * Gets the value of the promotionType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPromotionType() {
                return promotionType;
            }

            /**
             * Sets the value of the promotionType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPromotionType(String value) {
                this.promotionType = value;
            }

            /**
             * Gets the value of the lastEvalDate property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLastEvalDate() {
                return lastEvalDate;
            }

            /**
             * Sets the value of the lastEvalDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLastEvalDate(String value) {
                this.lastEvalDate = value;
            }

            /**
             * Gets the value of the globalSalesCommission property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getGlobalSalesCommission() {
                return globalSalesCommission;
            }

            /**
             * Sets the value of the globalSalesCommission property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setGlobalSalesCommission(BigDecimal value) {
                this.globalSalesCommission = value;
            }

            /**
             * Gets the value of the additionalCommission property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getAdditionalCommission() {
                return additionalCommission;
            }

            /**
             * Sets the value of the additionalCommission property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setAdditionalCommission(BigDecimal value) {
                this.additionalCommission = value;
            }

            /**
             * Gets the value of the jointRevenue property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getJointRevenue() {
                return jointRevenue;
            }

            /**
             * Sets the value of the jointRevenue property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setJointRevenue(BigDecimal value) {
                this.jointRevenue = value;
            }

            /**
             * Gets the value of the evaluationType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEvaluationType() {
                return evaluationType;
            }

            /**
             * Sets the value of the evaluationType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEvaluationType(String value) {
                this.evaluationType = value;
            }

            /**
             * Gets the value of the quotationNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getQuotationNumber() {
                return quotationNumber;
            }

            /**
             * Sets the value of the quotationNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setQuotationNumber(String value) {
                this.quotationNumber = value;
            }

            /**
             * Gets the value of the quotationRevisionNo property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getQuotationRevisionNo() {
                return quotationRevisionNo;
            }

            /**
             * Sets the value of the quotationRevisionNo property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setQuotationRevisionNo(BigInteger value) {
                this.quotationRevisionNo = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="awbTerminalCharge" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBTerminalChargeType" maxOccurs="unbounded"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "awbTerminalCharge"
        })
        public static class AwbHandlingChargeDetails {

            @XmlElement(required = true)
            protected List<AWBTerminalChargeType> awbTerminalCharge;

            /**
             * Gets the value of the awbTerminalCharge property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the awbTerminalCharge property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAwbTerminalCharge().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link AWBTerminalChargeType }
             * 
             * 
             */
            public List<AWBTerminalChargeType> getAwbTerminalCharge() {
                if (awbTerminalCharge == null) {
                    awbTerminalCharge = new ArrayList<AWBTerminalChargeType>();
                }
                return this.awbTerminalCharge;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="houseSummary" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}HouseSummaryDetailsType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "houseSummary"
    })
    public static class HouseSummaryDetails {

        protected List<HouseSummaryDetailsType> houseSummary;

        /**
         * Gets the value of the houseSummary property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the houseSummary property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHouseSummary().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HouseSummaryDetailsType }
         * 
         * 
         */
        public List<HouseSummaryDetailsType> getHouseSummary() {
            if (houseSummary == null) {
                houseSummary = new ArrayList<HouseSummaryDetailsType>();
            }
            return this.houseSummary;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="otherCustomsInformation" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}OtherCustomsInformationType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="additionalCustomsInformation" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="customsAuthority" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;enumeration value="GB"/>
     *                         &lt;enumeration value="SA"/>
     *                         &lt;enumeration value="AU"/>
     *                         &lt;enumeration value="ZOLL"/>
     *                         &lt;enumeration value="CN"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="parameterType" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="0"/>
     *                         &lt;maxLength value="250"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="parameterValue" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="0"/>
     *                         &lt;maxLength value="50"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "otherCustomsInformation",
        "additionalCustomsInformation"
    })
    public static class OtherCustomsInformationDetails {

        protected List<OtherCustomsInformationType> otherCustomsInformation;
        protected List<AWBDetailsType.OtherCustomsInformationDetails.AdditionalCustomsInformation> additionalCustomsInformation;

        /**
         * Gets the value of the otherCustomsInformation property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the otherCustomsInformation property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOtherCustomsInformation().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OtherCustomsInformationType }
         * 
         * 
         */
        public List<OtherCustomsInformationType> getOtherCustomsInformation() {
            if (otherCustomsInformation == null) {
                otherCustomsInformation = new ArrayList<OtherCustomsInformationType>();
            }
            return this.otherCustomsInformation;
        }

        /**
         * Gets the value of the additionalCustomsInformation property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the additionalCustomsInformation property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAdditionalCustomsInformation().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AWBDetailsType.OtherCustomsInformationDetails.AdditionalCustomsInformation }
         * 
         * 
         */
        public List<AWBDetailsType.OtherCustomsInformationDetails.AdditionalCustomsInformation> getAdditionalCustomsInformation() {
            if (additionalCustomsInformation == null) {
                additionalCustomsInformation = new ArrayList<AWBDetailsType.OtherCustomsInformationDetails.AdditionalCustomsInformation>();
            }
            return this.additionalCustomsInformation;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="customsAuthority" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;enumeration value="GB"/>
         *               &lt;enumeration value="SA"/>
         *               &lt;enumeration value="AU"/>
         *               &lt;enumeration value="ZOLL"/>
         *               &lt;enumeration value="CN"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="parameterType" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;minLength value="0"/>
         *               &lt;maxLength value="250"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="parameterValue" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;minLength value="0"/>
         *               &lt;maxLength value="50"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "customsAuthority",
            "parameterType",
            "parameterValue"
        })
        public static class AdditionalCustomsInformation {

            protected String customsAuthority;
            protected String parameterType;
            protected String parameterValue;

            /**
             * Gets the value of the customsAuthority property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCustomsAuthority() {
                return customsAuthority;
            }

            /**
             * Sets the value of the customsAuthority property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCustomsAuthority(String value) {
                this.customsAuthority = value;
            }

            /**
             * Gets the value of the parameterType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getParameterType() {
                return parameterType;
            }

            /**
             * Sets the value of the parameterType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setParameterType(String value) {
                this.parameterType = value;
            }

            /**
             * Gets the value of the parameterValue property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getParameterValue() {
                return parameterValue;
            }

            /**
             * Sets the value of the parameterValue property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setParameterValue(String value) {
                this.parameterValue = value;
            }

        }

    }


    /**
     * 
     * 							The node containing the additional
     * 							participant details of the AWB
     * 						
     * 
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="OtherParticipantInformation" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}OtherParticipantInformationType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "otherParticipantInformation"
    })
    public static class OtherParticipantInformationDetails {

        @XmlElement(name = "OtherParticipantInformation", required = true)
        protected List<OtherParticipantInformationType> otherParticipantInformation;

        /**
         * Gets the value of the otherParticipantInformation property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the otherParticipantInformation property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOtherParticipantInformation().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OtherParticipantInformationType }
         * 
         * 
         */
        public List<OtherParticipantInformationType> getOtherParticipantInformation() {
            if (otherParticipantInformation == null) {
                otherParticipantInformation = new ArrayList<OtherParticipantInformationType>();
            }
            return this.otherParticipantInformation;
        }

    }

}
