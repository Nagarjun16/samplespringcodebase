
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

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
 * <p>Java class for BookingsAWBDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingsAWBDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ubrNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ubrNumber"/>
 *         &lt;element name="bookingRemarks" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}desc_1000" minOccurs="0"/>
 *         &lt;element name="acceptedPieces" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="deliveredPieces" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="awbDocumentStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bookingStatus">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="Q"/>
 *               &lt;enumeration value="X"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="eAWBFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="capacityType" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a1" minOccurs="0"/>
 *         &lt;element name="queueReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="latestAcceptanceTime" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="latestAcceptanceTimeUTC" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="timeOfAvailability" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="timeOfAvailabilityUTC" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="sccCodes" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m200" minOccurs="0"/>
 *         &lt;element name="totalNumberOfPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces"/>
 *         &lt;element name="totalWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight"/>
 *         &lt;element name="weightUnit" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a1"/>
 *         &lt;element name="totalVolume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolume"/>
 *         &lt;element name="volumeUnit" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a1"/>
 *         &lt;element name="bookingDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime"/>
 *         &lt;element name="bookingDateUTC" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime"/>
 *         &lt;element name="readyForCarriage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
 *         &lt;element name="destinationCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
 *         &lt;element name="agentCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}agentCode"/>
 *         &lt;element name="shipperCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m15" minOccurs="0"/>
 *         &lt;element name="consigneeCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m15" minOccurs="0"/>
 *         &lt;element name="bookingStation" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
 *         &lt;element name="productName" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t30" minOccurs="0"/>
 *         &lt;element name="dataCaptureFlag" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="shipmentDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ShipmentBookingDetailsType"/>
 *         &lt;element name="bookingFlightDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingAWBFlightDetailsType" maxOccurs="unbounded"/>
 *         &lt;element name="bookingCommodityDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingCommodityDetailsType" maxOccurs="unbounded"/>
 *         &lt;element name="documentDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}DocumentDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingsAWBDetailsType", propOrder = {
    "ubrNumber",
    "bookingRemarks",
    "acceptedPieces",
    "deliveredPieces",
    "awbDocumentStatus",
    "bookingStatus",
    "eawbFlag",
    "capacityType",
    "queueReasonCode",
    "latestAcceptanceTime",
    "latestAcceptanceTimeUTC",
    "timeOfAvailability",
    "timeOfAvailabilityUTC",
    "sccCodes",
    "totalNumberOfPieces",
    "totalWeight",
    "weightUnit",
    "totalVolume",
    "volumeUnit",
    "bookingDate",
    "bookingDateUTC",
    "readyForCarriage",
    "originCode",
    "destinationCode",
    "agentCode",
    "shipperCode",
    "consigneeCode",
    "bookingStation",
    "productName",
    "dataCaptureFlag",
    "shipmentDetails",
    "bookingFlightDetails",
    "bookingCommodityDetails",
    "documentDetails"
})
public class BookingsAWBDetailsType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String ubrNumber;
    protected String bookingRemarks;
    protected Integer acceptedPieces;
    protected Integer deliveredPieces;
    protected String awbDocumentStatus;
    @XmlElement(required = true)
    protected String bookingStatus;
    @XmlElement(name = "eAWBFlag")
    protected String eawbFlag;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String capacityType;
    protected String queueReasonCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String latestAcceptanceTime;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String latestAcceptanceTimeUTC;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String timeOfAvailability;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String timeOfAvailabilityUTC;
    protected String sccCodes;
    @XmlElement(required = true)
    protected BigInteger totalNumberOfPieces;
    @XmlElement(required = true)
    protected BigDecimal totalWeight;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String weightUnit;
    @XmlElement(required = true)
    protected BigDecimal totalVolume;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String volumeUnit;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String bookingDate;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String bookingDateUTC;
    protected String readyForCarriage;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String originCode;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String destinationCode;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String agentCode;
    protected String shipperCode;
    protected String consigneeCode;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String bookingStation;
    protected String productName;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String dataCaptureFlag;
    @XmlElement(required = true)
    protected ShipmentBookingDetailsType shipmentDetails;
    @XmlElement(required = true)
    protected List<BookingAWBFlightDetailsType> bookingFlightDetails;
    @XmlElement(required = true)
    protected List<BookingCommodityDetailsType> bookingCommodityDetails;
    protected List<DocumentDetailsType> documentDetails;

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
     * Gets the value of the bookingRemarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingRemarks() {
        return bookingRemarks;
    }

    /**
     * Sets the value of the bookingRemarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingRemarks(String value) {
        this.bookingRemarks = value;
    }

    /**
     * Gets the value of the acceptedPieces property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAcceptedPieces() {
        return acceptedPieces;
    }

    /**
     * Sets the value of the acceptedPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAcceptedPieces(Integer value) {
        this.acceptedPieces = value;
    }

    /**
     * Gets the value of the deliveredPieces property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDeliveredPieces() {
        return deliveredPieces;
    }

    /**
     * Sets the value of the deliveredPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDeliveredPieces(Integer value) {
        this.deliveredPieces = value;
    }

    /**
     * Gets the value of the awbDocumentStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwbDocumentStatus() {
        return awbDocumentStatus;
    }

    /**
     * Sets the value of the awbDocumentStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwbDocumentStatus(String value) {
        this.awbDocumentStatus = value;
    }

    /**
     * Gets the value of the bookingStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingStatus() {
        return bookingStatus;
    }

    /**
     * Sets the value of the bookingStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingStatus(String value) {
        this.bookingStatus = value;
    }

    /**
     * Gets the value of the eawbFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEAWBFlag() {
        return eawbFlag;
    }

    /**
     * Sets the value of the eawbFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEAWBFlag(String value) {
        this.eawbFlag = value;
    }

    /**
     * Gets the value of the capacityType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapacityType() {
        return capacityType;
    }

    /**
     * Sets the value of the capacityType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapacityType(String value) {
        this.capacityType = value;
    }

    /**
     * Gets the value of the queueReasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueueReasonCode() {
        return queueReasonCode;
    }

    /**
     * Sets the value of the queueReasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueueReasonCode(String value) {
        this.queueReasonCode = value;
    }

    /**
     * Gets the value of the latestAcceptanceTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLatestAcceptanceTime() {
        return latestAcceptanceTime;
    }

    /**
     * Sets the value of the latestAcceptanceTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLatestAcceptanceTime(String value) {
        this.latestAcceptanceTime = value;
    }

    /**
     * Gets the value of the latestAcceptanceTimeUTC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLatestAcceptanceTimeUTC() {
        return latestAcceptanceTimeUTC;
    }

    /**
     * Sets the value of the latestAcceptanceTimeUTC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLatestAcceptanceTimeUTC(String value) {
        this.latestAcceptanceTimeUTC = value;
    }

    /**
     * Gets the value of the timeOfAvailability property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeOfAvailability() {
        return timeOfAvailability;
    }

    /**
     * Sets the value of the timeOfAvailability property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeOfAvailability(String value) {
        this.timeOfAvailability = value;
    }

    /**
     * Gets the value of the timeOfAvailabilityUTC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeOfAvailabilityUTC() {
        return timeOfAvailabilityUTC;
    }

    /**
     * Sets the value of the timeOfAvailabilityUTC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeOfAvailabilityUTC(String value) {
        this.timeOfAvailabilityUTC = value;
    }

    /**
     * Gets the value of the sccCodes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSccCodes() {
        return sccCodes;
    }

    /**
     * Sets the value of the sccCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSccCodes(String value) {
        this.sccCodes = value;
    }

    /**
     * Gets the value of the totalNumberOfPieces property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalNumberOfPieces() {
        return totalNumberOfPieces;
    }

    /**
     * Sets the value of the totalNumberOfPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalNumberOfPieces(BigInteger value) {
        this.totalNumberOfPieces = value;
    }

    /**
     * Gets the value of the totalWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    /**
     * Sets the value of the totalWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalWeight(BigDecimal value) {
        this.totalWeight = value;
    }

    /**
     * Gets the value of the weightUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeightUnit() {
        return weightUnit;
    }

    /**
     * Sets the value of the weightUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeightUnit(String value) {
        this.weightUnit = value;
    }

    /**
     * Gets the value of the totalVolume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    /**
     * Sets the value of the totalVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalVolume(BigDecimal value) {
        this.totalVolume = value;
    }

    /**
     * Gets the value of the volumeUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVolumeUnit() {
        return volumeUnit;
    }

    /**
     * Sets the value of the volumeUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVolumeUnit(String value) {
        this.volumeUnit = value;
    }

    /**
     * Gets the value of the bookingDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets the value of the bookingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingDate(String value) {
        this.bookingDate = value;
    }

    /**
     * Gets the value of the bookingDateUTC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingDateUTC() {
        return bookingDateUTC;
    }

    /**
     * Sets the value of the bookingDateUTC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingDateUTC(String value) {
        this.bookingDateUTC = value;
    }

    /**
     * Gets the value of the readyForCarriage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReadyForCarriage() {
        return readyForCarriage;
    }

    /**
     * Sets the value of the readyForCarriage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReadyForCarriage(String value) {
        this.readyForCarriage = value;
    }

    /**
     * Gets the value of the originCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginCode() {
        return originCode;
    }

    /**
     * Sets the value of the originCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginCode(String value) {
        this.originCode = value;
    }

    /**
     * Gets the value of the destinationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationCode() {
        return destinationCode;
    }

    /**
     * Sets the value of the destinationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationCode(String value) {
        this.destinationCode = value;
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
     * Gets the value of the bookingStation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingStation() {
        return bookingStation;
    }

    /**
     * Sets the value of the bookingStation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingStation(String value) {
        this.bookingStation = value;
    }

    /**
     * Gets the value of the productName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the value of the productName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductName(String value) {
        this.productName = value;
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
     * Gets the value of the shipmentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentBookingDetailsType }
     *     
     */
    public ShipmentBookingDetailsType getShipmentDetails() {
        return shipmentDetails;
    }

    /**
     * Sets the value of the shipmentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentBookingDetailsType }
     *     
     */
    public void setShipmentDetails(ShipmentBookingDetailsType value) {
        this.shipmentDetails = value;
    }

    /**
     * Gets the value of the bookingFlightDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bookingFlightDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBookingFlightDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BookingAWBFlightDetailsType }
     * 
     * 
     */
    public List<BookingAWBFlightDetailsType> getBookingFlightDetails() {
        if (bookingFlightDetails == null) {
            bookingFlightDetails = new ArrayList<BookingAWBFlightDetailsType>();
        }
        return this.bookingFlightDetails;
    }

    /**
     * Gets the value of the bookingCommodityDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bookingCommodityDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBookingCommodityDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BookingCommodityDetailsType }
     * 
     * 
     */
    public List<BookingCommodityDetailsType> getBookingCommodityDetails() {
        if (bookingCommodityDetails == null) {
            bookingCommodityDetails = new ArrayList<BookingCommodityDetailsType>();
        }
        return this.bookingCommodityDetails;
    }

    /**
     * Gets the value of the documentDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentDetailsType }
     * 
     * 
     */
    public List<DocumentDetailsType> getDocumentDetails() {
        if (documentDetails == null) {
            documentDetails = new ArrayList<DocumentDetailsType>();
        }
        return this.documentDetails;
    }

}
