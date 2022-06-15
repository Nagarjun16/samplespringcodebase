
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * 				Flight Booking Details node contains the booked flight
 * 				segment details.
 * 			
 * 
 * <p>Java class for FlightBookingDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlightBookingDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flightCarrierCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}flightCarrierCode"/>
 *         &lt;element name="flightNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}flightNumber"/>
 *         &lt;element name="flightDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date"/>
 *         &lt;element name="flightStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="weight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *         &lt;element name="weightThreeDecimal" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeightThreeDecimal" minOccurs="0"/>
 *         &lt;element name="noOfPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces" minOccurs="0"/>
 *         &lt;element name="volume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *         &lt;element name="flightSegmentNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;maxInclusive value="999"/>
 *               &lt;minInclusive value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="flightSegmentOrigin" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode" minOccurs="0"/>
 *         &lt;element name="flightSegmentDestination" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode" minOccurs="0"/>
 *         &lt;element name="departureEvent" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}DateEvent" minOccurs="0"/>
 *         &lt;element name="arrivalEvent" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}DateEvent" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightBookingDetailsType", propOrder = {
    "flightCarrierCode",
    "flightNumber",
    "flightDate",
    "flightStatus",
    "weight",
    "weightThreeDecimal",
    "noOfPieces",
    "volume",
    "flightSegmentNumber",
    "flightSegmentOrigin",
    "flightSegmentDestination",
    "departureEvent",
    "arrivalEvent"
})
public class FlightBookingDetailsType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String flightCarrierCode;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String flightNumber;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String flightDate;
    protected String flightStatus;
    protected BigDecimal weight;
    protected BigDecimal weightThreeDecimal;
    protected BigInteger noOfPieces;
    protected BigDecimal volume;
    protected Integer flightSegmentNumber;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String flightSegmentOrigin;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String flightSegmentDestination;
    protected DateEvent departureEvent;
    protected DateEvent arrivalEvent;

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
     * Gets the value of the weight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWeight(BigDecimal value) {
        this.weight = value;
    }

    /**
     * Gets the value of the weightThreeDecimal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWeightThreeDecimal() {
        return weightThreeDecimal;
    }

    /**
     * Sets the value of the weightThreeDecimal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWeightThreeDecimal(BigDecimal value) {
        this.weightThreeDecimal = value;
    }

    /**
     * Gets the value of the noOfPieces property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNoOfPieces() {
        return noOfPieces;
    }

    /**
     * Sets the value of the noOfPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNoOfPieces(BigInteger value) {
        this.noOfPieces = value;
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
     * Gets the value of the flightSegmentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFlightSegmentNumber() {
        return flightSegmentNumber;
    }

    /**
     * Sets the value of the flightSegmentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFlightSegmentNumber(Integer value) {
        this.flightSegmentNumber = value;
    }

    /**
     * Gets the value of the flightSegmentOrigin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightSegmentOrigin() {
        return flightSegmentOrigin;
    }

    /**
     * Sets the value of the flightSegmentOrigin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightSegmentOrigin(String value) {
        this.flightSegmentOrigin = value;
    }

    /**
     * Gets the value of the flightSegmentDestination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightSegmentDestination() {
        return flightSegmentDestination;
    }

    /**
     * Sets the value of the flightSegmentDestination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightSegmentDestination(String value) {
        this.flightSegmentDestination = value;
    }

    /**
     * Gets the value of the departureEvent property.
     * 
     * @return
     *     possible object is
     *     {@link DateEvent }
     *     
     */
    public DateEvent getDepartureEvent() {
        return departureEvent;
    }

    /**
     * Sets the value of the departureEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateEvent }
     *     
     */
    public void setDepartureEvent(DateEvent value) {
        this.departureEvent = value;
    }

    /**
     * Gets the value of the arrivalEvent property.
     * 
     * @return
     *     possible object is
     *     {@link DateEvent }
     *     
     */
    public DateEvent getArrivalEvent() {
        return arrivalEvent;
    }

    /**
     * Sets the value of the arrivalEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateEvent }
     *     
     */
    public void setArrivalEvent(DateEvent value) {
        this.arrivalEvent = value;
    }

}
