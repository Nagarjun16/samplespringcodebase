
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for AbstractBookingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractBookingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ubrNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ubrNumber" minOccurs="0"/>
 *         &lt;element name="bookingRemarks" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}desc_1000" minOccurs="0"/>
 *         &lt;element name="handlingInformation" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}desc_1000" minOccurs="0"/>
 *         &lt;element name="bookingStatus" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="Q"/>
 *               &lt;enumeration value="X"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="slacPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces" minOccurs="0"/>
 *         &lt;element name="serviceCargoClass" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a1" minOccurs="0"/>
 *         &lt;element name="serviceCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a1" minOccurs="0"/>
 *         &lt;element name="capacityType" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a1" minOccurs="0"/>
 *         &lt;element name="baselineStatus" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a1" minOccurs="0"/>
 *         &lt;element name="ccShipmentIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="latestAcceptanceTime" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="latestAcceptanceTimeUTC" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="timeOfAvailability" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="timeOfAvailabilityUTC" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="reBookingReasonCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m1_50" minOccurs="0"/>
 *         &lt;element name="customsIndicator" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m0_2" minOccurs="0"/>
 *         &lt;element name="shipmentCategory" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m30" minOccurs="0"/>
 *         &lt;element name="userStation" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a3" minOccurs="0"/>
 *         &lt;element name="userOffice" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m1_3" minOccurs="0"/>
 *         &lt;element name="isSplitBooking" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="bookingVersion" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n3" minOccurs="0"/>
 *         &lt;element name="airFreightCharge" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="shipmentPickupPoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="weightUnit" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a1"/>
 *         &lt;element name="volumeUnit" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a1"/>
 *         &lt;element name="dimensionUnit" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a1" minOccurs="0"/>
 *         &lt;element name="spotRateRequestId" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m30" minOccurs="0"/>
 *         &lt;element name="spotRateStatus" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a3" minOccurs="0"/>
 *         &lt;element name="spotRateRequestedUser" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}userCode" minOccurs="0"/>
 *         &lt;element name="spotRate" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;maxInclusive value="99999999"/>
 *               &lt;minInclusive value="0"/>
 *               &lt;fractionDigits value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="spotCharge" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="allInRate" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="allInAttribute" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastUpdateSource" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t50" minOccurs="0"/>
 *         &lt;element name="isAWBAutoPopulatedFromStock" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="bookingDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="bookingDateUTC" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="createdUser" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}userCode" minOccurs="0"/>
 *         &lt;element name="associatedTemplate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sccCodes" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m200" minOccurs="0"/>
 *         &lt;element name="dvForCustoms" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16p" minOccurs="0"/>
 *         &lt;element name="dvForCarriage" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16p" minOccurs="0"/>
 *         &lt;element name="insuranceAmount" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16p" minOccurs="0"/>
 *         &lt;element name="latModifiedCounter" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="autoAllocationEligible" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}yn" minOccurs="0"/>
 *         &lt;element name="bookingSourceName" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t50" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractBookingType", propOrder = {
    "ubrNumber",
    "bookingRemarks",
    "handlingInformation",
    "bookingStatus",
    "slacPieces",
    "serviceCargoClass",
    "serviceCode",
    "capacityType",
    "baselineStatus",
    "ccShipmentIndicator",
    "latestAcceptanceTime",
    "latestAcceptanceTimeUTC",
    "timeOfAvailability",
    "timeOfAvailabilityUTC",
    "reBookingReasonCode",
    "customsIndicator",
    "shipmentCategory",
    "userStation",
    "userOffice",
    "isSplitBooking",
    "bookingVersion",
    "airFreightCharge",
    "shipmentPickupPoint",
    "weightUnit",
    "volumeUnit",
    "dimensionUnit",
    "spotRateRequestId",
    "spotRateStatus",
    "spotRateRequestedUser",
    "spotRate",
    "spotCharge",
    "allInRate",
    "allInAttribute",
    "lastUpdateSource",
    "isAWBAutoPopulatedFromStock",
    "bookingDate",
    "bookingDateUTC",
    "createdUser",
    "associatedTemplate",
    "sccCodes",
    "dvForCustoms",
    "dvForCarriage",
    "insuranceAmount",
    "latModifiedCounter",
    "autoAllocationEligible",
    "bookingSourceName"
})
@XmlSeeAlso({
    BookingType.class
})
public class AbstractBookingType {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String ubrNumber;
    protected String bookingRemarks;
    protected String handlingInformation;
    protected String bookingStatus;
    protected BigInteger slacPieces;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String serviceCargoClass;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String serviceCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String capacityType;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String baselineStatus;
    protected Boolean ccShipmentIndicator;
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
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String reBookingReasonCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String customsIndicator;
    protected String shipmentCategory;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String userStation;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String userOffice;
    protected Boolean isSplitBooking;
    protected BigInteger bookingVersion;
    protected BigDecimal airFreightCharge;
    protected String shipmentPickupPoint;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String weightUnit;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String volumeUnit;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String dimensionUnit;
    protected String spotRateRequestId;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String spotRateStatus;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String spotRateRequestedUser;
    protected BigDecimal spotRate;
    protected BigDecimal spotCharge;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String allInRate;
    protected String allInAttribute;
    protected String lastUpdateSource;
    protected Boolean isAWBAutoPopulatedFromStock;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String bookingDate;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String bookingDateUTC;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String createdUser;
    protected String associatedTemplate;
    protected String sccCodes;
    protected BigDecimal dvForCustoms;
    protected BigDecimal dvForCarriage;
    protected BigDecimal insuranceAmount;
    protected Integer latModifiedCounter;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String autoAllocationEligible;
    protected String bookingSourceName;

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
     * Gets the value of the handlingInformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandlingInformation() {
        return handlingInformation;
    }

    /**
     * Sets the value of the handlingInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandlingInformation(String value) {
        this.handlingInformation = value;
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
     * Gets the value of the serviceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceCode() {
        return serviceCode;
    }

    /**
     * Sets the value of the serviceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceCode(String value) {
        this.serviceCode = value;
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
     * Gets the value of the baselineStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaselineStatus() {
        return baselineStatus;
    }

    /**
     * Sets the value of the baselineStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaselineStatus(String value) {
        this.baselineStatus = value;
    }

    /**
     * Gets the value of the ccShipmentIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCcShipmentIndicator() {
        return ccShipmentIndicator;
    }

    /**
     * Sets the value of the ccShipmentIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCcShipmentIndicator(Boolean value) {
        this.ccShipmentIndicator = value;
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
     * Gets the value of the reBookingReasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReBookingReasonCode() {
        return reBookingReasonCode;
    }

    /**
     * Sets the value of the reBookingReasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReBookingReasonCode(String value) {
        this.reBookingReasonCode = value;
    }

    /**
     * Gets the value of the customsIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomsIndicator() {
        return customsIndicator;
    }

    /**
     * Sets the value of the customsIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomsIndicator(String value) {
        this.customsIndicator = value;
    }

    /**
     * Gets the value of the shipmentCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipmentCategory() {
        return shipmentCategory;
    }

    /**
     * Sets the value of the shipmentCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipmentCategory(String value) {
        this.shipmentCategory = value;
    }

    /**
     * Gets the value of the userStation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserStation() {
        return userStation;
    }

    /**
     * Sets the value of the userStation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserStation(String value) {
        this.userStation = value;
    }

    /**
     * Gets the value of the userOffice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserOffice() {
        return userOffice;
    }

    /**
     * Sets the value of the userOffice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserOffice(String value) {
        this.userOffice = value;
    }

    /**
     * Gets the value of the isSplitBooking property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSplitBooking() {
        return isSplitBooking;
    }

    /**
     * Sets the value of the isSplitBooking property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSplitBooking(Boolean value) {
        this.isSplitBooking = value;
    }

    /**
     * Gets the value of the bookingVersion property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBookingVersion() {
        return bookingVersion;
    }

    /**
     * Sets the value of the bookingVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBookingVersion(BigInteger value) {
        this.bookingVersion = value;
    }

    /**
     * Gets the value of the airFreightCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAirFreightCharge() {
        return airFreightCharge;
    }

    /**
     * Sets the value of the airFreightCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAirFreightCharge(BigDecimal value) {
        this.airFreightCharge = value;
    }

    /**
     * Gets the value of the shipmentPickupPoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipmentPickupPoint() {
        return shipmentPickupPoint;
    }

    /**
     * Sets the value of the shipmentPickupPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipmentPickupPoint(String value) {
        this.shipmentPickupPoint = value;
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
     * Gets the value of the dimensionUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDimensionUnit() {
        return dimensionUnit;
    }

    /**
     * Sets the value of the dimensionUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDimensionUnit(String value) {
        this.dimensionUnit = value;
    }

    /**
     * Gets the value of the spotRateRequestId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpotRateRequestId() {
        return spotRateRequestId;
    }

    /**
     * Sets the value of the spotRateRequestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpotRateRequestId(String value) {
        this.spotRateRequestId = value;
    }

    /**
     * Gets the value of the spotRateStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpotRateStatus() {
        return spotRateStatus;
    }

    /**
     * Sets the value of the spotRateStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpotRateStatus(String value) {
        this.spotRateStatus = value;
    }

    /**
     * Gets the value of the spotRateRequestedUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpotRateRequestedUser() {
        return spotRateRequestedUser;
    }

    /**
     * Sets the value of the spotRateRequestedUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpotRateRequestedUser(String value) {
        this.spotRateRequestedUser = value;
    }

    /**
     * Gets the value of the spotRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSpotRate() {
        return spotRate;
    }

    /**
     * Sets the value of the spotRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSpotRate(BigDecimal value) {
        this.spotRate = value;
    }

    /**
     * Gets the value of the spotCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSpotCharge() {
        return spotCharge;
    }

    /**
     * Sets the value of the spotCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSpotCharge(BigDecimal value) {
        this.spotCharge = value;
    }

    /**
     * Gets the value of the allInRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllInRate() {
        return allInRate;
    }

    /**
     * Sets the value of the allInRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllInRate(String value) {
        this.allInRate = value;
    }

    /**
     * Gets the value of the allInAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllInAttribute() {
        return allInAttribute;
    }

    /**
     * Sets the value of the allInAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllInAttribute(String value) {
        this.allInAttribute = value;
    }

    /**
     * Gets the value of the lastUpdateSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdateSource() {
        return lastUpdateSource;
    }

    /**
     * Sets the value of the lastUpdateSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdateSource(String value) {
        this.lastUpdateSource = value;
    }

    /**
     * Gets the value of the isAWBAutoPopulatedFromStock property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsAWBAutoPopulatedFromStock() {
        return isAWBAutoPopulatedFromStock;
    }

    /**
     * Sets the value of the isAWBAutoPopulatedFromStock property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAWBAutoPopulatedFromStock(Boolean value) {
        this.isAWBAutoPopulatedFromStock = value;
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
     * Gets the value of the createdUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedUser() {
        return createdUser;
    }

    /**
     * Sets the value of the createdUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedUser(String value) {
        this.createdUser = value;
    }

    /**
     * Gets the value of the associatedTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssociatedTemplate() {
        return associatedTemplate;
    }

    /**
     * Sets the value of the associatedTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssociatedTemplate(String value) {
        this.associatedTemplate = value;
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
     * Gets the value of the dvForCustoms property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDvForCustoms() {
        return dvForCustoms;
    }

    /**
     * Sets the value of the dvForCustoms property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDvForCustoms(BigDecimal value) {
        this.dvForCustoms = value;
    }

    /**
     * Gets the value of the dvForCarriage property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDvForCarriage() {
        return dvForCarriage;
    }

    /**
     * Sets the value of the dvForCarriage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDvForCarriage(BigDecimal value) {
        this.dvForCarriage = value;
    }

    /**
     * Gets the value of the insuranceAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getInsuranceAmount() {
        return insuranceAmount;
    }

    /**
     * Sets the value of the insuranceAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setInsuranceAmount(BigDecimal value) {
        this.insuranceAmount = value;
    }

    /**
     * Gets the value of the latModifiedCounter property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLatModifiedCounter() {
        return latModifiedCounter;
    }

    /**
     * Sets the value of the latModifiedCounter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLatModifiedCounter(Integer value) {
        this.latModifiedCounter = value;
    }

    /**
     * Gets the value of the autoAllocationEligible property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutoAllocationEligible() {
        return autoAllocationEligible;
    }

    /**
     * Sets the value of the autoAllocationEligible property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutoAllocationEligible(String value) {
        this.autoAllocationEligible = value;
    }

    /**
     * Gets the value of the bookingSourceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingSourceName() {
        return bookingSourceName;
    }

    /**
     * Sets the value of the bookingSourceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingSourceName(String value) {
        this.bookingSourceName = value;
    }

}
