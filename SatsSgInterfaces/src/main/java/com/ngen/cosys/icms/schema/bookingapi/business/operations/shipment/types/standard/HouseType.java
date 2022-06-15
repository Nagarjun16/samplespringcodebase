
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
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.OperationalFlagType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.VolumeUnit;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.WeightUnit;


/**
 * <p>Java class for HouseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HouseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}masterDocumentNumber" minOccurs="0"/>
 *         &lt;element name="sequenceNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}sequenceNumber"/>
 *         &lt;element name="ownerId" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ownerId"/>
 *         &lt;element name="duplicateNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}duplicateNumber" minOccurs="0"/>
 *         &lt;element name="hawbNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}hawbNumber"/>
 *         &lt;element name="origin" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode" minOccurs="0"/>
 *         &lt;element name="destination" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode" minOccurs="0"/>
 *         &lt;element name="statedPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces"/>
 *         &lt;element name="statedWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight"/>
 *         &lt;element name="statedVolume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *         &lt;element name="statedDisplayVolume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *         &lt;element name="statedVolumeUnit" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}volumeUnit" minOccurs="0"/>
 *         &lt;element name="statedDisplayWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *         &lt;element name="statedWeightUnit" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}weightUnit" minOccurs="0"/>
 *         &lt;element name="slacPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces" minOccurs="0"/>
 *         &lt;element name="shipmentDescription" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t250" minOccurs="0"/>
 *         &lt;element name="consigneeName" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t70" minOccurs="0"/>
 *         &lt;element name="consigneeCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m15" minOccurs="0"/>
 *         &lt;element name="consigneeAddress1" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t100" minOccurs="0"/>
 *         &lt;element name="consigneeAddress2" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t50" minOccurs="0"/>
 *         &lt;element name="consigneeCity" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t70" minOccurs="0"/>
 *         &lt;element name="consigneePhone" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *         &lt;element name="consigneeEmail" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t70" minOccurs="0"/>
 *         &lt;element name="consigneeState" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t70" minOccurs="0"/>
 *         &lt;element name="consigneeCountry" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}countryCode" minOccurs="0"/>
 *         &lt;element name="consigneePostalCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}postalCodeType" minOccurs="0"/>
 *         &lt;element name="consigneeAccountNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}accountNumber" minOccurs="0"/>
 *         &lt;element name="shipperName" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t70" minOccurs="0"/>
 *         &lt;element name="shipperCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m15" minOccurs="0"/>
 *         &lt;element name="shipperAddress1" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t100" minOccurs="0"/>
 *         &lt;element name="shipperAddress2" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t70" minOccurs="0"/>
 *         &lt;element name="shipperPhone" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *         &lt;element name="shipperEmail" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t70" minOccurs="0"/>
 *         &lt;element name="shipperCity" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t70" minOccurs="0"/>
 *         &lt;element name="shipperState" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t70" minOccurs="0"/>
 *         &lt;element name="shipperCountry" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}countryCode" minOccurs="0"/>
 *         &lt;element name="shipperAccountNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}accountNumber" minOccurs="0"/>
 *         &lt;element name="scc" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}sccCode" minOccurs="0"/>
 *         &lt;element name="operationalFlag" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}operationalFlagType" minOccurs="0"/>
 *         &lt;element name="shipperPostalCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}postalCodeType" minOccurs="0"/>
 *         &lt;element name="lastUpdatedTime" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}DateTime_MiliSec" minOccurs="0"/>
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
 *         &lt;element name="awbAgentDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AgentDetailsType" minOccurs="0"/>
 *         &lt;element name="awbRoutingDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBRoutingDetailsType" minOccurs="0"/>
 *         &lt;element name="flightBookingDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}FlightBookingDetailsType" maxOccurs="unbounded" minOccurs="0"/>
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
 *         &lt;element name="productDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ProductDetailsType" minOccurs="0"/>
 *         &lt;element name="awbExecutionDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBExecutionDetailsType" minOccurs="0"/>
 *         &lt;element name="awbChargeDeclaration" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBChargeDeclarationType" minOccurs="0"/>
 *         &lt;element name="awbRateLineDetails" minOccurs="0">
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
 *         &lt;element name="collectChargesInDestinationCurrency" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}CollectChargesInDestinationCurrencyType" minOccurs="0"/>
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
 *         &lt;element name="awbHandlingInfoDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBHandlingInfoType" minOccurs="0"/>
 *         &lt;element name="shipperCertification" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipperCertificationType" minOccurs="0"/>
 *         &lt;element name="remarks" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="585"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="agentName" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t70" minOccurs="0"/>
 *         &lt;element name="agentCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m15" minOccurs="0"/>
 *         &lt;element name="agentAddress1" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t100" minOccurs="0"/>
 *         &lt;element name="agentAddress2" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t50" minOccurs="0"/>
 *         &lt;element name="agentCity" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t50" minOccurs="0"/>
 *         &lt;element name="agentPhone" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t0_25" minOccurs="0"/>
 *         &lt;element name="agentEmail" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t75" minOccurs="0"/>
 *         &lt;element name="agentState" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t0_15" minOccurs="0"/>
 *         &lt;element name="agentCountry" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}countryCode" minOccurs="0"/>
 *         &lt;element name="agentPostalCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}postalCodeType" minOccurs="0"/>
 *         &lt;element name="lastUpdatedUser" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}userCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HouseType", propOrder = {
    "documentNumber",
    "sequenceNumber",
    "ownerId",
    "duplicateNumber",
    "hawbNumber",
    "origin",
    "destination",
    "statedPieces",
    "statedWeight",
    "statedVolume",
    "statedDisplayVolume",
    "statedVolumeUnit",
    "statedDisplayWeight",
    "statedWeightUnit",
    "slacPieces",
    "shipmentDescription",
    "consigneeName",
    "consigneeCode",
    "consigneeAddress1",
    "consigneeAddress2",
    "consigneeCity",
    "consigneePhone",
    "consigneeEmail",
    "consigneeState",
    "consigneeCountry",
    "consigneePostalCode",
    "consigneeAccountNumber",
    "shipperName",
    "shipperCode",
    "shipperAddress1",
    "shipperAddress2",
    "shipperPhone",
    "shipperEmail",
    "shipperCity",
    "shipperState",
    "shipperCountry",
    "shipperAccountNumber",
    "scc",
    "operationalFlag",
    "shipperPostalCode",
    "lastUpdatedTime",
    "additionalNotificationDetails",
    "awbAgentDetails",
    "awbRoutingDetails",
    "flightBookingDetails",
    "customsOriginCode",
    "productDetails",
    "awbExecutionDetails",
    "awbChargeDeclaration",
    "awbRateLineDetails",
    "awbOtherChargeDetails",
    "prepaidChargeSummary",
    "collectChargeSummary",
    "collectChargesInDestinationCurrency",
    "otherCustomsInformationDetails",
    "awbAccountingInformationDetails",
    "awbHandlingInfoDetails",
    "shipperCertification",
    "remarks",
    "agentName",
    "agentCode",
    "agentAddress1",
    "agentAddress2",
    "agentCity",
    "agentPhone",
    "agentEmail",
    "agentState",
    "agentCountry",
    "agentPostalCode",
    "lastUpdatedUser"
})
public class HouseType {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String documentNumber;
    @XmlElement(required = true)
    protected BigInteger sequenceNumber;
    @XmlElement(required = true)
    protected BigInteger ownerId;
    protected BigInteger duplicateNumber;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String hawbNumber;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String origin;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String destination;
    @XmlElement(required = true)
    protected BigInteger statedPieces;
    @XmlElement(required = true)
    protected BigDecimal statedWeight;
    protected BigDecimal statedVolume;
    protected BigDecimal statedDisplayVolume;
    @XmlSchemaType(name = "token")
    protected VolumeUnit statedVolumeUnit;
    protected BigDecimal statedDisplayWeight;
    @XmlSchemaType(name = "token")
    protected WeightUnit statedWeightUnit;
    protected BigInteger slacPieces;
    protected String shipmentDescription;
    protected String consigneeName;
    protected String consigneeCode;
    protected String consigneeAddress1;
    protected String consigneeAddress2;
    protected String consigneeCity;
    protected String consigneePhone;
    protected String consigneeEmail;
    protected String consigneeState;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String consigneeCountry;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String consigneePostalCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String consigneeAccountNumber;
    protected String shipperName;
    protected String shipperCode;
    protected String shipperAddress1;
    protected String shipperAddress2;
    protected String shipperPhone;
    protected String shipperEmail;
    protected String shipperCity;
    protected String shipperState;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String shipperCountry;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String shipperAccountNumber;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String scc;
    @XmlSchemaType(name = "string")
    protected OperationalFlagType operationalFlag;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String shipperPostalCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String lastUpdatedTime;
    protected HouseType.AdditionalNotificationDetails additionalNotificationDetails;
    protected AgentDetailsType awbAgentDetails;
    protected AWBRoutingDetailsType awbRoutingDetails;
    protected List<FlightBookingDetailsType> flightBookingDetails;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String customsOriginCode;
    protected ProductDetailsType productDetails;
    protected AWBExecutionDetailsType awbExecutionDetails;
    protected AWBChargeDeclarationType awbChargeDeclaration;
    protected HouseType.AwbRateLineDetails awbRateLineDetails;
    protected HouseType.AwbOtherChargeDetails awbOtherChargeDetails;
    protected AWBPrepaidChargesSummmaryType prepaidChargeSummary;
    protected AWBCollectChargesSummmaryType collectChargeSummary;
    protected CollectChargesInDestinationCurrencyType collectChargesInDestinationCurrency;
    protected HouseType.OtherCustomsInformationDetails otherCustomsInformationDetails;
    protected HouseType.AwbAccountingInformationDetails awbAccountingInformationDetails;
    protected AWBHandlingInfoType awbHandlingInfoDetails;
    protected ShipperCertificationType shipperCertification;
    protected String remarks;
    protected String agentName;
    protected String agentCode;
    protected String agentAddress1;
    protected String agentAddress2;
    protected String agentCity;
    protected String agentPhone;
    protected String agentEmail;
    protected String agentState;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String agentCountry;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String agentPostalCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String lastUpdatedUser;

    /**
     * Gets the value of the documentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the value of the documentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentNumber(String value) {
        this.documentNumber = value;
    }

    /**
     * Gets the value of the sequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSequenceNumber(BigInteger value) {
        this.sequenceNumber = value;
    }

    /**
     * Gets the value of the ownerId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOwnerId() {
        return ownerId;
    }

    /**
     * Sets the value of the ownerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOwnerId(BigInteger value) {
        this.ownerId = value;
    }

    /**
     * Gets the value of the duplicateNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDuplicateNumber() {
        return duplicateNumber;
    }

    /**
     * Sets the value of the duplicateNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDuplicateNumber(BigInteger value) {
        this.duplicateNumber = value;
    }

    /**
     * Gets the value of the hawbNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHawbNumber() {
        return hawbNumber;
    }

    /**
     * Sets the value of the hawbNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHawbNumber(String value) {
        this.hawbNumber = value;
    }

    /**
     * Gets the value of the origin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the value of the origin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigin(String value) {
        this.origin = value;
    }

    /**
     * Gets the value of the destination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestination(String value) {
        this.destination = value;
    }

    /**
     * Gets the value of the statedPieces property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStatedPieces() {
        return statedPieces;
    }

    /**
     * Sets the value of the statedPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStatedPieces(BigInteger value) {
        this.statedPieces = value;
    }

    /**
     * Gets the value of the statedWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStatedWeight() {
        return statedWeight;
    }

    /**
     * Sets the value of the statedWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStatedWeight(BigDecimal value) {
        this.statedWeight = value;
    }

    /**
     * Gets the value of the statedVolume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStatedVolume() {
        return statedVolume;
    }

    /**
     * Sets the value of the statedVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStatedVolume(BigDecimal value) {
        this.statedVolume = value;
    }

    /**
     * Gets the value of the statedDisplayVolume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStatedDisplayVolume() {
        return statedDisplayVolume;
    }

    /**
     * Sets the value of the statedDisplayVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStatedDisplayVolume(BigDecimal value) {
        this.statedDisplayVolume = value;
    }

    /**
     * Gets the value of the statedVolumeUnit property.
     * 
     * @return
     *     possible object is
     *     {@link VolumeUnit }
     *     
     */
    public VolumeUnit getStatedVolumeUnit() {
        return statedVolumeUnit;
    }

    /**
     * Sets the value of the statedVolumeUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link VolumeUnit }
     *     
     */
    public void setStatedVolumeUnit(VolumeUnit value) {
        this.statedVolumeUnit = value;
    }

    /**
     * Gets the value of the statedDisplayWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStatedDisplayWeight() {
        return statedDisplayWeight;
    }

    /**
     * Sets the value of the statedDisplayWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStatedDisplayWeight(BigDecimal value) {
        this.statedDisplayWeight = value;
    }

    /**
     * Gets the value of the statedWeightUnit property.
     * 
     * @return
     *     possible object is
     *     {@link WeightUnit }
     *     
     */
    public WeightUnit getStatedWeightUnit() {
        return statedWeightUnit;
    }

    /**
     * Sets the value of the statedWeightUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link WeightUnit }
     *     
     */
    public void setStatedWeightUnit(WeightUnit value) {
        this.statedWeightUnit = value;
    }

    /**
     * Gets the value of the slacPieces property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSlacPieces() {
        return slacPieces;
    }

    /**
     * Sets the value of the slacPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSlacPieces(BigInteger value) {
        this.slacPieces = value;
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
     * Gets the value of the consigneeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeName() {
        return consigneeName;
    }

    /**
     * Sets the value of the consigneeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeName(String value) {
        this.consigneeName = value;
    }

    /**
     * Gets the value of the consigneeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeCode() {
        return consigneeCode;
    }

    /**
     * Sets the value of the consigneeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeCode(String value) {
        this.consigneeCode = value;
    }

    /**
     * Gets the value of the consigneeAddress1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeAddress1() {
        return consigneeAddress1;
    }

    /**
     * Sets the value of the consigneeAddress1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeAddress1(String value) {
        this.consigneeAddress1 = value;
    }

    /**
     * Gets the value of the consigneeAddress2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeAddress2() {
        return consigneeAddress2;
    }

    /**
     * Sets the value of the consigneeAddress2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeAddress2(String value) {
        this.consigneeAddress2 = value;
    }

    /**
     * Gets the value of the consigneeCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeCity() {
        return consigneeCity;
    }

    /**
     * Sets the value of the consigneeCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeCity(String value) {
        this.consigneeCity = value;
    }

    /**
     * Gets the value of the consigneePhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneePhone() {
        return consigneePhone;
    }

    /**
     * Sets the value of the consigneePhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneePhone(String value) {
        this.consigneePhone = value;
    }

    /**
     * Gets the value of the consigneeEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeEmail() {
        return consigneeEmail;
    }

    /**
     * Sets the value of the consigneeEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeEmail(String value) {
        this.consigneeEmail = value;
    }

    /**
     * Gets the value of the consigneeState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeState() {
        return consigneeState;
    }

    /**
     * Sets the value of the consigneeState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeState(String value) {
        this.consigneeState = value;
    }

    /**
     * Gets the value of the consigneeCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeCountry() {
        return consigneeCountry;
    }

    /**
     * Sets the value of the consigneeCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeCountry(String value) {
        this.consigneeCountry = value;
    }

    /**
     * Gets the value of the consigneePostalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneePostalCode() {
        return consigneePostalCode;
    }

    /**
     * Sets the value of the consigneePostalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneePostalCode(String value) {
        this.consigneePostalCode = value;
    }

    /**
     * Gets the value of the consigneeAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeAccountNumber() {
        return consigneeAccountNumber;
    }

    /**
     * Sets the value of the consigneeAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeAccountNumber(String value) {
        this.consigneeAccountNumber = value;
    }

    /**
     * Gets the value of the shipperName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperName() {
        return shipperName;
    }

    /**
     * Sets the value of the shipperName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperName(String value) {
        this.shipperName = value;
    }

    /**
     * Gets the value of the shipperCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperCode() {
        return shipperCode;
    }

    /**
     * Sets the value of the shipperCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperCode(String value) {
        this.shipperCode = value;
    }

    /**
     * Gets the value of the shipperAddress1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperAddress1() {
        return shipperAddress1;
    }

    /**
     * Sets the value of the shipperAddress1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperAddress1(String value) {
        this.shipperAddress1 = value;
    }

    /**
     * Gets the value of the shipperAddress2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperAddress2() {
        return shipperAddress2;
    }

    /**
     * Sets the value of the shipperAddress2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperAddress2(String value) {
        this.shipperAddress2 = value;
    }

    /**
     * Gets the value of the shipperPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperPhone() {
        return shipperPhone;
    }

    /**
     * Sets the value of the shipperPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperPhone(String value) {
        this.shipperPhone = value;
    }

    /**
     * Gets the value of the shipperEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperEmail() {
        return shipperEmail;
    }

    /**
     * Sets the value of the shipperEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperEmail(String value) {
        this.shipperEmail = value;
    }

    /**
     * Gets the value of the shipperCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperCity() {
        return shipperCity;
    }

    /**
     * Sets the value of the shipperCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperCity(String value) {
        this.shipperCity = value;
    }

    /**
     * Gets the value of the shipperState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperState() {
        return shipperState;
    }

    /**
     * Sets the value of the shipperState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperState(String value) {
        this.shipperState = value;
    }

    /**
     * Gets the value of the shipperCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperCountry() {
        return shipperCountry;
    }

    /**
     * Sets the value of the shipperCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperCountry(String value) {
        this.shipperCountry = value;
    }

    /**
     * Gets the value of the shipperAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperAccountNumber() {
        return shipperAccountNumber;
    }

    /**
     * Sets the value of the shipperAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperAccountNumber(String value) {
        this.shipperAccountNumber = value;
    }

    /**
     * Gets the value of the scc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScc() {
        return scc;
    }

    /**
     * Sets the value of the scc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScc(String value) {
        this.scc = value;
    }

    /**
     * Gets the value of the operationalFlag property.
     * 
     * @return
     *     possible object is
     *     {@link OperationalFlagType }
     *     
     */
    public OperationalFlagType getOperationalFlag() {
        return operationalFlag;
    }

    /**
     * Sets the value of the operationalFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationalFlagType }
     *     
     */
    public void setOperationalFlag(OperationalFlagType value) {
        this.operationalFlag = value;
    }

    /**
     * Gets the value of the shipperPostalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperPostalCode() {
        return shipperPostalCode;
    }

    /**
     * Sets the value of the shipperPostalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperPostalCode(String value) {
        this.shipperPostalCode = value;
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
     * Gets the value of the additionalNotificationDetails property.
     * 
     * @return
     *     possible object is
     *     {@link HouseType.AdditionalNotificationDetails }
     *     
     */
    public HouseType.AdditionalNotificationDetails getAdditionalNotificationDetails() {
        return additionalNotificationDetails;
    }

    /**
     * Sets the value of the additionalNotificationDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link HouseType.AdditionalNotificationDetails }
     *     
     */
    public void setAdditionalNotificationDetails(HouseType.AdditionalNotificationDetails value) {
        this.additionalNotificationDetails = value;
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
     * Gets the value of the awbRateLineDetails property.
     * 
     * @return
     *     possible object is
     *     {@link HouseType.AwbRateLineDetails }
     *     
     */
    public HouseType.AwbRateLineDetails getAwbRateLineDetails() {
        return awbRateLineDetails;
    }

    /**
     * Sets the value of the awbRateLineDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link HouseType.AwbRateLineDetails }
     *     
     */
    public void setAwbRateLineDetails(HouseType.AwbRateLineDetails value) {
        this.awbRateLineDetails = value;
    }

    /**
     * Gets the value of the awbOtherChargeDetails property.
     * 
     * @return
     *     possible object is
     *     {@link HouseType.AwbOtherChargeDetails }
     *     
     */
    public HouseType.AwbOtherChargeDetails getAwbOtherChargeDetails() {
        return awbOtherChargeDetails;
    }

    /**
     * Sets the value of the awbOtherChargeDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link HouseType.AwbOtherChargeDetails }
     *     
     */
    public void setAwbOtherChargeDetails(HouseType.AwbOtherChargeDetails value) {
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
     * Gets the value of the otherCustomsInformationDetails property.
     * 
     * @return
     *     possible object is
     *     {@link HouseType.OtherCustomsInformationDetails }
     *     
     */
    public HouseType.OtherCustomsInformationDetails getOtherCustomsInformationDetails() {
        return otherCustomsInformationDetails;
    }

    /**
     * Sets the value of the otherCustomsInformationDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link HouseType.OtherCustomsInformationDetails }
     *     
     */
    public void setOtherCustomsInformationDetails(HouseType.OtherCustomsInformationDetails value) {
        this.otherCustomsInformationDetails = value;
    }

    /**
     * Gets the value of the awbAccountingInformationDetails property.
     * 
     * @return
     *     possible object is
     *     {@link HouseType.AwbAccountingInformationDetails }
     *     
     */
    public HouseType.AwbAccountingInformationDetails getAwbAccountingInformationDetails() {
        return awbAccountingInformationDetails;
    }

    /**
     * Sets the value of the awbAccountingInformationDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link HouseType.AwbAccountingInformationDetails }
     *     
     */
    public void setAwbAccountingInformationDetails(HouseType.AwbAccountingInformationDetails value) {
        this.awbAccountingInformationDetails = value;
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
     * Gets the value of the remarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the value of the remarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks(String value) {
        this.remarks = value;
    }

    /**
     * Gets the value of the agentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * Sets the value of the agentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentName(String value) {
        this.agentName = value;
    }

    /**
     * Gets the value of the agentCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentCode() {
        return agentCode;
    }

    /**
     * Sets the value of the agentCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentCode(String value) {
        this.agentCode = value;
    }

    /**
     * Gets the value of the agentAddress1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentAddress1() {
        return agentAddress1;
    }

    /**
     * Sets the value of the agentAddress1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentAddress1(String value) {
        this.agentAddress1 = value;
    }

    /**
     * Gets the value of the agentAddress2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentAddress2() {
        return agentAddress2;
    }

    /**
     * Sets the value of the agentAddress2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentAddress2(String value) {
        this.agentAddress2 = value;
    }

    /**
     * Gets the value of the agentCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentCity() {
        return agentCity;
    }

    /**
     * Sets the value of the agentCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentCity(String value) {
        this.agentCity = value;
    }

    /**
     * Gets the value of the agentPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentPhone() {
        return agentPhone;
    }

    /**
     * Sets the value of the agentPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentPhone(String value) {
        this.agentPhone = value;
    }

    /**
     * Gets the value of the agentEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentEmail() {
        return agentEmail;
    }

    /**
     * Sets the value of the agentEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentEmail(String value) {
        this.agentEmail = value;
    }

    /**
     * Gets the value of the agentState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentState() {
        return agentState;
    }

    /**
     * Sets the value of the agentState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentState(String value) {
        this.agentState = value;
    }

    /**
     * Gets the value of the agentCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentCountry() {
        return agentCountry;
    }

    /**
     * Sets the value of the agentCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentCountry(String value) {
        this.agentCountry = value;
    }

    /**
     * Gets the value of the agentPostalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentPostalCode() {
        return agentPostalCode;
    }

    /**
     * Sets the value of the agentPostalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentPostalCode(String value) {
        this.agentPostalCode = value;
    }

    /**
     * Gets the value of the lastUpdatedUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    /**
     * Sets the value of the lastUpdatedUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdatedUser(String value) {
        this.lastUpdatedUser = value;
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
        protected List<HouseType.OtherCustomsInformationDetails.AdditionalCustomsInformation> additionalCustomsInformation;

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
         * {@link HouseType.OtherCustomsInformationDetails.AdditionalCustomsInformation }
         * 
         * 
         */
        public List<HouseType.OtherCustomsInformationDetails.AdditionalCustomsInformation> getAdditionalCustomsInformation() {
            if (additionalCustomsInformation == null) {
                additionalCustomsInformation = new ArrayList<HouseType.OtherCustomsInformationDetails.AdditionalCustomsInformation>();
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

}
