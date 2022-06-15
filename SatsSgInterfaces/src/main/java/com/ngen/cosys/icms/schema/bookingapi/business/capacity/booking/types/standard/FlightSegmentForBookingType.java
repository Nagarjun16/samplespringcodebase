
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigDecimal;
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
 * <p>Java class for FlightSegmentForBookingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlightSegmentForBookingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="aircraftType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="combinationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="combinationSerialNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="connectionFlights" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01}FlightSegmentForBookingType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="connectionSerialNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="arrivalDateLeg" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="departureDateLeg" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="directFlight" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="estimatedArrivalDateLeg" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="estimatedDepartureDateLeg" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="flightCarrierCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}flightCarrierCode"/>
 *         &lt;element name="flightCarrierId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="flightDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date"/>
 *         &lt;element name="flightNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}flightNumber"/>
 *         &lt;element name="flightSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="flightType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="route" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="segmentDestination" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
 *         &lt;element name="segmentOrigin" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
 *         &lt;element name="stops" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cumulativeJourneyTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flyingHours" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="layoverTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="totalAllotmentWeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16Neg" minOccurs="0"/>
 *         &lt;element name="totalAllotmentVolume" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16Neg" minOccurs="0"/>
 *         &lt;element name="totalFreesaleWeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16Neg" minOccurs="0"/>
 *         &lt;element name="totalFreesaleVolume" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16Neg" minOccurs="0"/>
 *         &lt;element name="availableAllotmentWeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16Neg" minOccurs="0"/>
 *         &lt;element name="availableAllotmentVolume" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16Neg" minOccurs="0"/>
 *         &lt;element name="availableFreesaleWeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16Neg" minOccurs="0"/>
 *         &lt;element name="availableFreesaleVolume" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16Neg" minOccurs="0"/>
 *         &lt;element name="totalWeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16Neg" minOccurs="0"/>
 *         &lt;element name="totalVolume" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16Neg" minOccurs="0"/>
 *         &lt;element name="charterFlight" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="aircraftClassification" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flightStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isFlightBookable" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="promoCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t20" minOccurs="0"/>
 *         &lt;element name="spotRateCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ratedCustomer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currencyCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m0_3" minOccurs="0"/>
 *         &lt;element name="combinationCheckResults" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01}CombinationCheckResultsType" minOccurs="0"/>
 *         &lt;element name="segmentUpfrontCheckResults" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01}SegmentUpfrontCheckResultsType" minOccurs="0"/>
 *         &lt;element name="segmentAllotmentDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01}SegmentAllotmentDetailsType" minOccurs="0"/>
 *         &lt;element name="segmentStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightSegmentForBookingType", namespace = "http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01", propOrder = {
    "aircraftType",
    "combinationId",
    "combinationSerialNumber",
    "connectionFlights",
    "connectionSerialNumber",
    "arrivalDateLeg",
    "departureDateLeg",
    "directFlight",
    "estimatedArrivalDateLeg",
    "estimatedDepartureDateLeg",
    "flightCarrierCode",
    "flightCarrierId",
    "flightDate",
    "flightNumber",
    "flightSequenceNumber",
    "flightType",
    "route",
    "segmentDestination",
    "segmentOrigin",
    "stops",
    "cumulativeJourneyTime",
    "flyingHours",
    "layoverTime",
    "totalAllotmentWeight",
    "totalAllotmentVolume",
    "totalFreesaleWeight",
    "totalFreesaleVolume",
    "availableAllotmentWeight",
    "availableAllotmentVolume",
    "availableFreesaleWeight",
    "availableFreesaleVolume",
    "totalWeight",
    "totalVolume",
    "charterFlight",
    "aircraftClassification",
    "flightStatus",
    "isFlightBookable",
    "promoCode",
    "spotRateCode",
    "ratedCustomer",
    "currencyCode",
    "combinationCheckResults",
    "segmentUpfrontCheckResults",
    "segmentAllotmentDetails",
    "segmentStatus"
})
public class FlightSegmentForBookingType {

    @XmlElement(required = true)
    protected String aircraftType;
    protected String combinationId;
    protected Integer combinationSerialNumber;
    protected List<FlightSegmentForBookingType> connectionFlights;
    protected Integer connectionSerialNumber;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String arrivalDateLeg;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String departureDateLeg;
    protected String directFlight;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String estimatedArrivalDateLeg;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String estimatedDepartureDateLeg;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String flightCarrierCode;
    protected int flightCarrierId;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String flightDate;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String flightNumber;
    protected int flightSequenceNumber;
    @XmlElement(required = true)
    protected String flightType;
    protected String route;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String segmentDestination;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String segmentOrigin;
    protected Integer stops;
    protected String cumulativeJourneyTime;
    protected String flyingHours;
    protected String layoverTime;
    protected BigDecimal totalAllotmentWeight;
    protected BigDecimal totalAllotmentVolume;
    protected BigDecimal totalFreesaleWeight;
    protected BigDecimal totalFreesaleVolume;
    protected BigDecimal availableAllotmentWeight;
    protected BigDecimal availableAllotmentVolume;
    protected BigDecimal availableFreesaleWeight;
    protected BigDecimal availableFreesaleVolume;
    protected BigDecimal totalWeight;
    protected BigDecimal totalVolume;
    protected String charterFlight;
    @XmlElement(required = true)
    protected String aircraftClassification;
    @XmlElement(required = true)
    protected String flightStatus;
    protected String isFlightBookable;
    protected String promoCode;
    protected String spotRateCode;
    protected String ratedCustomer;
    protected String currencyCode;
    protected CombinationCheckResultsType combinationCheckResults;
    protected SegmentUpfrontCheckResultsType segmentUpfrontCheckResults;
    protected SegmentAllotmentDetailsType segmentAllotmentDetails;
    protected String segmentStatus;

    /**
     * Gets the value of the aircraftType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAircraftType() {
        return aircraftType;
    }

    /**
     * Sets the value of the aircraftType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAircraftType(String value) {
        this.aircraftType = value;
    }

    /**
     * Gets the value of the combinationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCombinationId() {
        return combinationId;
    }

    /**
     * Sets the value of the combinationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCombinationId(String value) {
        this.combinationId = value;
    }

    /**
     * Gets the value of the combinationSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCombinationSerialNumber() {
        return combinationSerialNumber;
    }

    /**
     * Sets the value of the combinationSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCombinationSerialNumber(Integer value) {
        this.combinationSerialNumber = value;
    }

    /**
     * Gets the value of the connectionFlights property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the connectionFlights property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConnectionFlights().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FlightSegmentForBookingType }
     * 
     * 
     */
    public List<FlightSegmentForBookingType> getConnectionFlights() {
        if (connectionFlights == null) {
            connectionFlights = new ArrayList<FlightSegmentForBookingType>();
        }
        return this.connectionFlights;
    }

    /**
     * Gets the value of the connectionSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getConnectionSerialNumber() {
        return connectionSerialNumber;
    }

    /**
     * Sets the value of the connectionSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setConnectionSerialNumber(Integer value) {
        this.connectionSerialNumber = value;
    }

    /**
     * Gets the value of the arrivalDateLeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrivalDateLeg() {
        return arrivalDateLeg;
    }

    /**
     * Sets the value of the arrivalDateLeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrivalDateLeg(String value) {
        this.arrivalDateLeg = value;
    }

    /**
     * Gets the value of the departureDateLeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureDateLeg() {
        return departureDateLeg;
    }

    /**
     * Sets the value of the departureDateLeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureDateLeg(String value) {
        this.departureDateLeg = value;
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
     * Gets the value of the estimatedArrivalDateLeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstimatedArrivalDateLeg() {
        return estimatedArrivalDateLeg;
    }

    /**
     * Sets the value of the estimatedArrivalDateLeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstimatedArrivalDateLeg(String value) {
        this.estimatedArrivalDateLeg = value;
    }

    /**
     * Gets the value of the estimatedDepartureDateLeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstimatedDepartureDateLeg() {
        return estimatedDepartureDateLeg;
    }

    /**
     * Sets the value of the estimatedDepartureDateLeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstimatedDepartureDateLeg(String value) {
        this.estimatedDepartureDateLeg = value;
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
     * Gets the value of the flightCarrierId property.
     * 
     */
    public int getFlightCarrierId() {
        return flightCarrierId;
    }

    /**
     * Sets the value of the flightCarrierId property.
     * 
     */
    public void setFlightCarrierId(int value) {
        this.flightCarrierId = value;
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
     * Gets the value of the flightSequenceNumber property.
     * 
     */
    public int getFlightSequenceNumber() {
        return flightSequenceNumber;
    }

    /**
     * Sets the value of the flightSequenceNumber property.
     * 
     */
    public void setFlightSequenceNumber(int value) {
        this.flightSequenceNumber = value;
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
     * Gets the value of the route property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoute() {
        return route;
    }

    /**
     * Sets the value of the route property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoute(String value) {
        this.route = value;
    }

    /**
     * Gets the value of the segmentDestination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentDestination() {
        return segmentDestination;
    }

    /**
     * Sets the value of the segmentDestination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentDestination(String value) {
        this.segmentDestination = value;
    }

    /**
     * Gets the value of the segmentOrigin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentOrigin() {
        return segmentOrigin;
    }

    /**
     * Sets the value of the segmentOrigin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentOrigin(String value) {
        this.segmentOrigin = value;
    }

    /**
     * Gets the value of the stops property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStops() {
        return stops;
    }

    /**
     * Sets the value of the stops property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStops(Integer value) {
        this.stops = value;
    }

    /**
     * Gets the value of the cumulativeJourneyTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCumulativeJourneyTime() {
        return cumulativeJourneyTime;
    }

    /**
     * Sets the value of the cumulativeJourneyTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCumulativeJourneyTime(String value) {
        this.cumulativeJourneyTime = value;
    }

    /**
     * Gets the value of the flyingHours property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlyingHours() {
        return flyingHours;
    }

    /**
     * Sets the value of the flyingHours property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlyingHours(String value) {
        this.flyingHours = value;
    }

    /**
     * Gets the value of the layoverTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLayoverTime() {
        return layoverTime;
    }

    /**
     * Sets the value of the layoverTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLayoverTime(String value) {
        this.layoverTime = value;
    }

    /**
     * Gets the value of the totalAllotmentWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalAllotmentWeight() {
        return totalAllotmentWeight;
    }

    /**
     * Sets the value of the totalAllotmentWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalAllotmentWeight(BigDecimal value) {
        this.totalAllotmentWeight = value;
    }

    /**
     * Gets the value of the totalAllotmentVolume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalAllotmentVolume() {
        return totalAllotmentVolume;
    }

    /**
     * Sets the value of the totalAllotmentVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalAllotmentVolume(BigDecimal value) {
        this.totalAllotmentVolume = value;
    }

    /**
     * Gets the value of the totalFreesaleWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalFreesaleWeight() {
        return totalFreesaleWeight;
    }

    /**
     * Sets the value of the totalFreesaleWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalFreesaleWeight(BigDecimal value) {
        this.totalFreesaleWeight = value;
    }

    /**
     * Gets the value of the totalFreesaleVolume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalFreesaleVolume() {
        return totalFreesaleVolume;
    }

    /**
     * Sets the value of the totalFreesaleVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalFreesaleVolume(BigDecimal value) {
        this.totalFreesaleVolume = value;
    }

    /**
     * Gets the value of the availableAllotmentWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAvailableAllotmentWeight() {
        return availableAllotmentWeight;
    }

    /**
     * Sets the value of the availableAllotmentWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAvailableAllotmentWeight(BigDecimal value) {
        this.availableAllotmentWeight = value;
    }

    /**
     * Gets the value of the availableAllotmentVolume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAvailableAllotmentVolume() {
        return availableAllotmentVolume;
    }

    /**
     * Sets the value of the availableAllotmentVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAvailableAllotmentVolume(BigDecimal value) {
        this.availableAllotmentVolume = value;
    }

    /**
     * Gets the value of the availableFreesaleWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAvailableFreesaleWeight() {
        return availableFreesaleWeight;
    }

    /**
     * Sets the value of the availableFreesaleWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAvailableFreesaleWeight(BigDecimal value) {
        this.availableFreesaleWeight = value;
    }

    /**
     * Gets the value of the availableFreesaleVolume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAvailableFreesaleVolume() {
        return availableFreesaleVolume;
    }

    /**
     * Sets the value of the availableFreesaleVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAvailableFreesaleVolume(BigDecimal value) {
        this.availableFreesaleVolume = value;
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
     * Gets the value of the charterFlight property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharterFlight() {
        return charterFlight;
    }

    /**
     * Sets the value of the charterFlight property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharterFlight(String value) {
        this.charterFlight = value;
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
     * Gets the value of the flightStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightStatus() {
        return flightStatus;
    }

    /**
     * Sets the value of the flightStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightStatus(String value) {
        this.flightStatus = value;
    }

    /**
     * Gets the value of the isFlightBookable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsFlightBookable() {
        return isFlightBookable;
    }

    /**
     * Sets the value of the isFlightBookable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsFlightBookable(String value) {
        this.isFlightBookable = value;
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
     * Gets the value of the currencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the value of the currencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyCode(String value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the combinationCheckResults property.
     * 
     * @return
     *     possible object is
     *     {@link CombinationCheckResultsType }
     *     
     */
    public CombinationCheckResultsType getCombinationCheckResults() {
        return combinationCheckResults;
    }

    /**
     * Sets the value of the combinationCheckResults property.
     * 
     * @param value
     *     allowed object is
     *     {@link CombinationCheckResultsType }
     *     
     */
    public void setCombinationCheckResults(CombinationCheckResultsType value) {
        this.combinationCheckResults = value;
    }

    /**
     * Gets the value of the segmentUpfrontCheckResults property.
     * 
     * @return
     *     possible object is
     *     {@link SegmentUpfrontCheckResultsType }
     *     
     */
    public SegmentUpfrontCheckResultsType getSegmentUpfrontCheckResults() {
        return segmentUpfrontCheckResults;
    }

    /**
     * Sets the value of the segmentUpfrontCheckResults property.
     * 
     * @param value
     *     allowed object is
     *     {@link SegmentUpfrontCheckResultsType }
     *     
     */
    public void setSegmentUpfrontCheckResults(SegmentUpfrontCheckResultsType value) {
        this.segmentUpfrontCheckResults = value;
    }

    /**
     * Gets the value of the segmentAllotmentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link SegmentAllotmentDetailsType }
     *     
     */
    public SegmentAllotmentDetailsType getSegmentAllotmentDetails() {
        return segmentAllotmentDetails;
    }

    /**
     * Sets the value of the segmentAllotmentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link SegmentAllotmentDetailsType }
     *     
     */
    public void setSegmentAllotmentDetails(SegmentAllotmentDetailsType value) {
        this.segmentAllotmentDetails = value;
    }

    /**
     * Gets the value of the segmentStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentStatus() {
        return segmentStatus;
    }

    /**
     * Sets the value of the segmentStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentStatus(String value) {
        this.segmentStatus = value;
    }

}
