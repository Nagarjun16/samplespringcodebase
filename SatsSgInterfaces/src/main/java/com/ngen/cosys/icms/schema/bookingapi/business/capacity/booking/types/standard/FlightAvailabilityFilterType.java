
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

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
 * <p>Java class for FlightAvailabilityFilterType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlightAvailabilityFilterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="destination" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
 *         &lt;element name="expectedArrivalDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date" minOccurs="0"/>
 *         &lt;element name="origin" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
 *         &lt;element name="flightType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shippingDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date"/>
 *         &lt;element name="shippingTime" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Time" minOccurs="0"/>
 *         &lt;element name="offSetfromShipgDate" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="flightCarrierCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}flightCarrierCode" minOccurs="0"/>
 *         &lt;element name="flightDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date" minOccurs="0"/>
 *         &lt;element name="flightNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}flightNumber" minOccurs="0"/>
 *         &lt;element name="skipAirport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="viaAirports" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="directFlight" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="aircraftClassification" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="W"/>
 *               &lt;enumeration value="F"/>
 *               &lt;enumeration value="T"/>
 *               &lt;enumeration value="AN"/>
 *               &lt;enumeration value="AW"/>
 *               &lt;enumeration value="AF"/>
 *               &lt;enumeration value="AT"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="noOfStops" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isActiveAlone" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="includeClosedFlight" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="promoCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t20" minOccurs="0"/>
 *         &lt;element name="spotRateCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bookingStation" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode" minOccurs="0"/>
 *         &lt;element name="agentCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}agentCode" minOccurs="0"/>
 *         &lt;element name="shipperCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}shipperConsigneeCode" minOccurs="0"/>
 *         &lt;element name="consigneeCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}shipperConsigneeCode" minOccurs="0"/>
 *         &lt;element name="ratedCustomer" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}shipperConsigneeCode" minOccurs="0"/>
 *         &lt;element name="isCCShipment" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="weightUnit" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a1" minOccurs="0"/>
 *         &lt;element name="volumeUnit" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a1" minOccurs="0"/>
 *         &lt;element name="productDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01}ProductType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sccDetails" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="commodityDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01}CommodityType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}currencyCode" minOccurs="0"/>
 *         &lt;element name="uniqueReferenceOne" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t30" minOccurs="0"/>
 *         &lt;element name="uniqueReferenceTwo" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t30" minOccurs="0"/>
 *         &lt;element name="uniqueReferenceThree" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t30" minOccurs="0"/>
 *         &lt;element name="uniqueReferenceFour" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t30" minOccurs="0"/>
 *         &lt;element name="uniqueReferenceFive" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t30" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightAvailabilityFilterType", namespace = "http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01", propOrder = {
    "destination",
    "expectedArrivalDate",
    "origin",
    "flightType",
    "shippingDate",
    "shippingTime",
    "offSetfromShipgDate",
    "flightCarrierCode",
    "flightDate",
    "flightNumber",
    "skipAirport",
    "viaAirports",
    "directFlight",
    "aircraftClassification",
    "noOfStops",
    "isActiveAlone",
    "includeClosedFlight",
    "promoCode",
    "spotRateCode",
    "bookingStation",
    "agentCode",
    "shipperCode",
    "consigneeCode",
    "ratedCustomer",
    "isCCShipment",
    "weightUnit",
    "volumeUnit",
    "productDetails",
    "sccDetails",
    "commodityDetails",
    "currency",
    "uniqueReferenceOne",
    "uniqueReferenceTwo",
    "uniqueReferenceThree",
    "uniqueReferenceFour",
    "uniqueReferenceFive"
})
public class FlightAvailabilityFilterType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String destination;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String expectedArrivalDate;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String origin;
    protected String flightType;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String shippingDate;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String shippingTime;
    protected Integer offSetfromShipgDate;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String flightCarrierCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String flightDate;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String flightNumber;
    protected String skipAirport;
    protected String viaAirports;
    protected String directFlight;
    protected String aircraftClassification;
    protected String noOfStops;
    protected String isActiveAlone;
    protected String includeClosedFlight;
    protected String promoCode;
    protected String spotRateCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String bookingStation;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String agentCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String shipperCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String consigneeCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String ratedCustomer;
    protected String isCCShipment;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String weightUnit;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String volumeUnit;
    protected List<ProductType> productDetails;
    protected String sccDetails;
    protected List<CommodityType> commodityDetails;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String currency;
    protected String uniqueReferenceOne;
    protected String uniqueReferenceTwo;
    protected String uniqueReferenceThree;
    protected String uniqueReferenceFour;
    protected String uniqueReferenceFive;

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
     * Gets the value of the expectedArrivalDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpectedArrivalDate() {
        return expectedArrivalDate;
    }

    /**
     * Sets the value of the expectedArrivalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpectedArrivalDate(String value) {
        this.expectedArrivalDate = value;
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
     * Gets the value of the flightType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightType() {
        return flightType;
    }

    /**
     * Sets the value of the flightType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightType(String value) {
        this.flightType = value;
    }

    /**
     * Gets the value of the shippingDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShippingDate() {
        return shippingDate;
    }

    /**
     * Sets the value of the shippingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShippingDate(String value) {
        this.shippingDate = value;
    }

    /**
     * Gets the value of the shippingTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShippingTime() {
        return shippingTime;
    }

    /**
     * Sets the value of the shippingTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShippingTime(String value) {
        this.shippingTime = value;
    }

    /**
     * Gets the value of the offSetfromShipgDate property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOffSetfromShipgDate() {
        return offSetfromShipgDate;
    }

    /**
     * Sets the value of the offSetfromShipgDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOffSetfromShipgDate(Integer value) {
        this.offSetfromShipgDate = value;
    }

    /**
     * Gets the value of the flightCarrierCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightCarrierCode() {
        return flightCarrierCode;
    }

    /**
     * Sets the value of the flightCarrierCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightCarrierCode(String value) {
        this.flightCarrierCode = value;
    }

    /**
     * Gets the value of the flightDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightDate() {
        return flightDate;
    }

    /**
     * Sets the value of the flightDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightDate(String value) {
        this.flightDate = value;
    }

    /**
     * Gets the value of the flightNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Sets the value of the flightNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightNumber(String value) {
        this.flightNumber = value;
    }

    /**
     * Gets the value of the skipAirport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSkipAirport() {
        return skipAirport;
    }

    /**
     * Sets the value of the skipAirport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSkipAirport(String value) {
        this.skipAirport = value;
    }

    /**
     * Gets the value of the viaAirports property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViaAirports() {
        return viaAirports;
    }

    /**
     * Sets the value of the viaAirports property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViaAirports(String value) {
        this.viaAirports = value;
    }

    /**
     * Gets the value of the directFlight property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectFlight() {
        return directFlight;
    }

    /**
     * Sets the value of the directFlight property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectFlight(String value) {
        this.directFlight = value;
    }

    /**
     * Gets the value of the aircraftClassification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAircraftClassification() {
        return aircraftClassification;
    }

    /**
     * Sets the value of the aircraftClassification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAircraftClassification(String value) {
        this.aircraftClassification = value;
    }

    /**
     * Gets the value of the noOfStops property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoOfStops() {
        return noOfStops;
    }

    /**
     * Sets the value of the noOfStops property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoOfStops(String value) {
        this.noOfStops = value;
    }

    /**
     * Gets the value of the isActiveAlone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsActiveAlone() {
        return isActiveAlone;
    }

    /**
     * Sets the value of the isActiveAlone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsActiveAlone(String value) {
        this.isActiveAlone = value;
    }

    /**
     * Gets the value of the includeClosedFlight property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncludeClosedFlight() {
        return includeClosedFlight;
    }

    /**
     * Sets the value of the includeClosedFlight property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncludeClosedFlight(String value) {
        this.includeClosedFlight = value;
    }

    /**
     * Gets the value of the promoCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromoCode() {
        return promoCode;
    }

    /**
     * Sets the value of the promoCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromoCode(String value) {
        this.promoCode = value;
    }

    /**
     * Gets the value of the spotRateCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpotRateCode() {
        return spotRateCode;
    }

    /**
     * Sets the value of the spotRateCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpotRateCode(String value) {
        this.spotRateCode = value;
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
     * Gets the value of the ratedCustomer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatedCustomer() {
        return ratedCustomer;
    }

    /**
     * Sets the value of the ratedCustomer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatedCustomer(String value) {
        this.ratedCustomer = value;
    }

    /**
     * Gets the value of the isCCShipment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsCCShipment() {
        return isCCShipment;
    }

    /**
     * Sets the value of the isCCShipment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsCCShipment(String value) {
        this.isCCShipment = value;
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
     * Gets the value of the productDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductType }
     * 
     * 
     */
    public List<ProductType> getProductDetails() {
        if (productDetails == null) {
            productDetails = new ArrayList<ProductType>();
        }
        return this.productDetails;
    }

    /**
     * Gets the value of the sccDetails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSccDetails() {
        return sccDetails;
    }

    /**
     * Sets the value of the sccDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSccDetails(String value) {
        this.sccDetails = value;
    }

    /**
     * Gets the value of the commodityDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the commodityDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommodityDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommodityType }
     * 
     * 
     */
    public List<CommodityType> getCommodityDetails() {
        if (commodityDetails == null) {
            commodityDetails = new ArrayList<CommodityType>();
        }
        return this.commodityDetails;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the uniqueReferenceOne property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueReferenceOne() {
        return uniqueReferenceOne;
    }

    /**
     * Sets the value of the uniqueReferenceOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueReferenceOne(String value) {
        this.uniqueReferenceOne = value;
    }

    /**
     * Gets the value of the uniqueReferenceTwo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueReferenceTwo() {
        return uniqueReferenceTwo;
    }

    /**
     * Sets the value of the uniqueReferenceTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueReferenceTwo(String value) {
        this.uniqueReferenceTwo = value;
    }

    /**
     * Gets the value of the uniqueReferenceThree property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueReferenceThree() {
        return uniqueReferenceThree;
    }

    /**
     * Sets the value of the uniqueReferenceThree property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueReferenceThree(String value) {
        this.uniqueReferenceThree = value;
    }

    /**
     * Gets the value of the uniqueReferenceFour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueReferenceFour() {
        return uniqueReferenceFour;
    }

    /**
     * Sets the value of the uniqueReferenceFour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueReferenceFour(String value) {
        this.uniqueReferenceFour = value;
    }

    /**
     * Gets the value of the uniqueReferenceFive property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueReferenceFive() {
        return uniqueReferenceFive;
    }

    /**
     * Sets the value of the uniqueReferenceFive property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueReferenceFive(String value) {
        this.uniqueReferenceFive = value;
    }

}
