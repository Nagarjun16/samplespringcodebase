
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
 * <p>Java class for AWBValidationDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBValidationDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}masterDocumentNumber" minOccurs="0"/>
 *         &lt;element name="shipmentPrefix" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}shipmentPrefix" minOccurs="0"/>
 *         &lt;element name="origin" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode" minOccurs="0"/>
 *         &lt;element name="destination" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode" minOccurs="0"/>
 *         &lt;element name="statedPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces" minOccurs="0"/>
 *         &lt;element name="awbtabname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="statedWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *         &lt;element name="shipmentDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consigneeCountry" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}countryCode" minOccurs="0"/>
 *         &lt;element name="shipperCountry" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}countryCode" minOccurs="0"/>
 *         &lt;element name="notifyCountry" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}countryCode" minOccurs="0"/>
 *         &lt;element name="duplicateNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}duplicateNumber" minOccurs="0"/>
 *         &lt;element name="sequenceNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}sequenceNumber" minOccurs="0"/>
 *         &lt;element name="ownerId" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ownerId" minOccurs="0"/>
 *         &lt;element name="scc" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t250" minOccurs="0"/>
 *         &lt;element name="issuedOn" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t0_20" minOccurs="0"/>
 *         &lt;element name="bookingscc" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t250" minOccurs="0"/>
 *         &lt;element name="stationCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}stationCode" minOccurs="0"/>
 *         &lt;element name="agentCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}agentCode" minOccurs="0"/>
 *         &lt;element name="iataCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}agentIATACOde" minOccurs="0"/>
 *         &lt;element name="hawbNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}hawbNumber" minOccurs="0"/>
 *         &lt;element name="commodityCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[a-zA-Z0-9]{1,10}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="uldDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ulds" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ULDDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="productCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t30" minOccurs="0"/>
 *         &lt;element name="awbRoutingDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBRoutingDetailsType" minOccurs="0"/>
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
 *         &lt;element name="dimensionsDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="dimension" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}DimensionType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="volume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *         &lt;element name="consigneeDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}CustomerDetailsType" minOccurs="0"/>
 *         &lt;element name="shipperDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}CustomerDetailsType" minOccurs="0"/>
 *         &lt;element name="chargeableWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *         &lt;element name="chargeableWeightThreeDecimal" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeightThreeDecimal" minOccurs="0"/>
 *         &lt;element name="HarmonizedCommodityCodes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="HarmonizedCommodityCode" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}HarmonizedCommodityCode" maxOccurs="9" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="awbScreeningDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentScreeningType" minOccurs="0"/>
 *         &lt;element name="otherCustomsInformation" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}OtherCustomsInformationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="awbHandlingInfoDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBHandlingInfoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AWBValidationDetailsType", propOrder = {
    "documentNumber",
    "shipmentPrefix",
    "origin",
    "destination",
    "statedPieces",
    "awbtabname",
    "statedWeight",
    "shipmentDescription",
    "consigneeCountry",
    "shipperCountry",
    "notifyCountry",
    "duplicateNumber",
    "sequenceNumber",
    "ownerId",
    "scc",
    "issuedOn",
    "bookingscc",
    "stationCode",
    "agentCode",
    "iataCode",
    "hawbNumber",
    "commodityCode",
    "uldDetails",
    "productCode",
    "awbRoutingDetails",
    "customsOriginCode",
    "dimensionsDetails",
    "volume",
    "consigneeDetails",
    "shipperDetails",
    "chargeableWeight",
    "chargeableWeightThreeDecimal",
    "harmonizedCommodityCodes",
    "awbScreeningDetails",
    "otherCustomsInformation",
    "awbHandlingInfoDetails"
})
public class AWBValidationDetailsType {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String documentNumber;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String shipmentPrefix;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String origin;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String destination;
    protected BigInteger statedPieces;
    @XmlElement(required = true)
    protected String awbtabname;
    protected BigDecimal statedWeight;
    protected String shipmentDescription;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String consigneeCountry;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String shipperCountry;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String notifyCountry;
    protected BigInteger duplicateNumber;
    protected BigInteger sequenceNumber;
    protected BigInteger ownerId;
    protected String scc;
    protected String issuedOn;
    protected String bookingscc;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String stationCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String agentCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String iataCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String hawbNumber;
    protected String commodityCode;
    protected AWBValidationDetailsType.UldDetails uldDetails;
    protected String productCode;
    protected AWBRoutingDetailsType awbRoutingDetails;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String customsOriginCode;
    protected AWBValidationDetailsType.DimensionsDetails dimensionsDetails;
    protected BigDecimal volume;
    protected CustomerDetailsType consigneeDetails;
    protected CustomerDetailsType shipperDetails;
    protected BigDecimal chargeableWeight;
    protected BigDecimal chargeableWeightThreeDecimal;
    @XmlElement(name = "HarmonizedCommodityCodes")
    protected AWBValidationDetailsType.HarmonizedCommodityCodes harmonizedCommodityCodes;
    protected ShipmentScreeningType awbScreeningDetails;
    protected List<OtherCustomsInformationType> otherCustomsInformation;
    protected AWBHandlingInfoType awbHandlingInfoDetails;

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
     * Gets the value of the shipmentPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipmentPrefix() {
        return shipmentPrefix;
    }

    /**
     * Sets the value of the shipmentPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipmentPrefix(String value) {
        this.shipmentPrefix = value;
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
     * Gets the value of the awbtabname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwbtabname() {
        return awbtabname;
    }

    /**
     * Sets the value of the awbtabname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwbtabname(String value) {
        this.awbtabname = value;
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
     * Gets the value of the notifyCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotifyCountry() {
        return notifyCountry;
    }

    /**
     * Sets the value of the notifyCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotifyCountry(String value) {
        this.notifyCountry = value;
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
     * Gets the value of the issuedOn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuedOn() {
        return issuedOn;
    }

    /**
     * Sets the value of the issuedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuedOn(String value) {
        this.issuedOn = value;
    }

    /**
     * Gets the value of the bookingscc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingscc() {
        return bookingscc;
    }

    /**
     * Sets the value of the bookingscc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingscc(String value) {
        this.bookingscc = value;
    }

    /**
     * Gets the value of the stationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStationCode() {
        return stationCode;
    }

    /**
     * Sets the value of the stationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStationCode(String value) {
        this.stationCode = value;
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
     * Gets the value of the iataCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataCode() {
        return iataCode;
    }

    /**
     * Sets the value of the iataCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataCode(String value) {
        this.iataCode = value;
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
     * Gets the value of the commodityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * Sets the value of the commodityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommodityCode(String value) {
        this.commodityCode = value;
    }

    /**
     * Gets the value of the uldDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBValidationDetailsType.UldDetails }
     *     
     */
    public AWBValidationDetailsType.UldDetails getUldDetails() {
        return uldDetails;
    }

    /**
     * Sets the value of the uldDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBValidationDetailsType.UldDetails }
     *     
     */
    public void setUldDetails(AWBValidationDetailsType.UldDetails value) {
        this.uldDetails = value;
    }

    /**
     * Gets the value of the productCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Sets the value of the productCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductCode(String value) {
        this.productCode = value;
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
     * Gets the value of the dimensionsDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBValidationDetailsType.DimensionsDetails }
     *     
     */
    public AWBValidationDetailsType.DimensionsDetails getDimensionsDetails() {
        return dimensionsDetails;
    }

    /**
     * Sets the value of the dimensionsDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBValidationDetailsType.DimensionsDetails }
     *     
     */
    public void setDimensionsDetails(AWBValidationDetailsType.DimensionsDetails value) {
        this.dimensionsDetails = value;
    }

    /**
     * Gets the value of the volume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVolume() {
        return volume;
    }

    /**
     * Sets the value of the volume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVolume(BigDecimal value) {
        this.volume = value;
    }

    /**
     * Gets the value of the consigneeDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerDetailsType }
     *     
     */
    public CustomerDetailsType getConsigneeDetails() {
        return consigneeDetails;
    }

    /**
     * Sets the value of the consigneeDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerDetailsType }
     *     
     */
    public void setConsigneeDetails(CustomerDetailsType value) {
        this.consigneeDetails = value;
    }

    /**
     * Gets the value of the shipperDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerDetailsType }
     *     
     */
    public CustomerDetailsType getShipperDetails() {
        return shipperDetails;
    }

    /**
     * Sets the value of the shipperDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerDetailsType }
     *     
     */
    public void setShipperDetails(CustomerDetailsType value) {
        this.shipperDetails = value;
    }

    /**
     * Gets the value of the chargeableWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getChargeableWeight() {
        return chargeableWeight;
    }

    /**
     * Sets the value of the chargeableWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setChargeableWeight(BigDecimal value) {
        this.chargeableWeight = value;
    }

    /**
     * Gets the value of the chargeableWeightThreeDecimal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getChargeableWeightThreeDecimal() {
        return chargeableWeightThreeDecimal;
    }

    /**
     * Sets the value of the chargeableWeightThreeDecimal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setChargeableWeightThreeDecimal(BigDecimal value) {
        this.chargeableWeightThreeDecimal = value;
    }

    /**
     * Gets the value of the harmonizedCommodityCodes property.
     * 
     * @return
     *     possible object is
     *     {@link AWBValidationDetailsType.HarmonizedCommodityCodes }
     *     
     */
    public AWBValidationDetailsType.HarmonizedCommodityCodes getHarmonizedCommodityCodes() {
        return harmonizedCommodityCodes;
    }

    /**
     * Sets the value of the harmonizedCommodityCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBValidationDetailsType.HarmonizedCommodityCodes }
     *     
     */
    public void setHarmonizedCommodityCodes(AWBValidationDetailsType.HarmonizedCommodityCodes value) {
        this.harmonizedCommodityCodes = value;
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="dimension" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}DimensionType" maxOccurs="unbounded"/>
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
        "dimension"
    })
    public static class DimensionsDetails {

        @XmlElement(required = true)
        protected List<DimensionType> dimension;

        /**
         * Gets the value of the dimension property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dimension property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDimension().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DimensionType }
         * 
         * 
         */
        public List<DimensionType> getDimension() {
            if (dimension == null) {
                dimension = new ArrayList<DimensionType>();
            }
            return this.dimension;
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
     *         &lt;element name="HarmonizedCommodityCode" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}HarmonizedCommodityCode" maxOccurs="9" minOccurs="0"/>
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
        "harmonizedCommodityCode"
    })
    public static class HarmonizedCommodityCodes {

        @XmlElement(name = "HarmonizedCommodityCode")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected List<String> harmonizedCommodityCode;

        /**
         * Gets the value of the harmonizedCommodityCode property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the harmonizedCommodityCode property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHarmonizedCommodityCode().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getHarmonizedCommodityCode() {
            if (harmonizedCommodityCode == null) {
                harmonizedCommodityCode = new ArrayList<String>();
            }
            return this.harmonizedCommodityCode;
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
     *         &lt;element name="ulds" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ULDDetailsType" maxOccurs="unbounded" minOccurs="0"/>
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
        "ulds"
    })
    public static class UldDetails {

        protected List<ULDDetailsType> ulds;

        /**
         * Gets the value of the ulds property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ulds property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getUlds().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ULDDetailsType }
         * 
         * 
         */
        public List<ULDDetailsType> getUlds() {
            if (ulds == null) {
                ulds = new ArrayList<ULDDetailsType>();
            }
            return this.ulds;
        }

    }

}
