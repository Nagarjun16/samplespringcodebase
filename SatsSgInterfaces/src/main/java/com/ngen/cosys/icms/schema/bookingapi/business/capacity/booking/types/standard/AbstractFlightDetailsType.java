
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
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.OperationalFlagType;


/**
 * <p>Java class for AbstractFlightDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractFlightDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="carrierCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}flightCarrierCode"/>
 *         &lt;element name="flightNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}flightNumber"/>
 *         &lt;element name="flightCarrierId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="flightSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="origin" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
 *         &lt;element name="destination" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
 *         &lt;element name="departureDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="arrivalDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="departureTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="arrivalTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flightType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="stops" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="aircraftType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="legSerialNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces" minOccurs="0"/>
 *         &lt;element name="weight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *         &lt;element name="volume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *         &lt;element name="flightBookingStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="segmentSerialNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="validForBooking" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flightDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date"/>
 *         &lt;element name="operationFlag" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}operationalFlagType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractFlightDetailsType", propOrder = {
    "carrierCode",
    "flightNumber",
    "flightCarrierId",
    "flightSequenceNumber",
    "origin",
    "destination",
    "departureDate",
    "arrivalDate",
    "departureTime",
    "arrivalTime",
    "flightType",
    "stops",
    "aircraftType",
    "legSerialNumber",
    "pieces",
    "weight",
    "volume",
    "flightBookingStatus",
    "segmentSerialNumber",
    "validForBooking",
    "flightDate",
    "operationFlag"
})
@XmlSeeAlso({
    FlightDetailsType.class
})
public class AbstractFlightDetailsType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String carrierCode;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String flightNumber;
    protected int flightCarrierId;
    protected long flightSequenceNumber;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String origin;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String destination;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String departureDate;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String arrivalDate;
    @XmlElement(required = true)
    protected String departureTime;
    @XmlElement(required = true)
    protected String arrivalTime;
    @XmlElement(required = true)
    protected String flightType;
    @XmlElement(required = true)
    protected String stops;
    @XmlElement(required = true)
    protected String aircraftType;
    protected int legSerialNumber;
    protected BigInteger pieces;
    protected BigDecimal weight;
    protected BigDecimal volume;
    @XmlElement(required = true)
    protected String flightBookingStatus;
    protected int segmentSerialNumber;
    @XmlElement(required = true)
    protected String validForBooking;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String flightDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected OperationalFlagType operationFlag;

    /**
     * Gets the value of the carrierCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarrierCode() {
        return carrierCode;
    }

    /**
     * Sets the value of the carrierCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarrierCode(String value) {
        this.carrierCode = value;
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
     * Gets the value of the flightSequenceNumber property.
     * 
     */
    public long getFlightSequenceNumber() {
        return flightSequenceNumber;
    }

    /**
     * Sets the value of the flightSequenceNumber property.
     * 
     */
    public void setFlightSequenceNumber(long value) {
        this.flightSequenceNumber = value;
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
     * Gets the value of the departureDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the value of the departureDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureDate(String value) {
        this.departureDate = value;
    }

    /**
     * Gets the value of the arrivalDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Sets the value of the arrivalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrivalDate(String value) {
        this.arrivalDate = value;
    }

    /**
     * Gets the value of the departureTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the value of the departureTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureTime(String value) {
        this.departureTime = value;
    }

    /**
     * Gets the value of the arrivalTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the value of the arrivalTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrivalTime(String value) {
        this.arrivalTime = value;
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
     * Gets the value of the stops property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStops() {
        return stops;
    }

    /**
     * Sets the value of the stops property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStops(String value) {
        this.stops = value;
    }

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
     * Gets the value of the legSerialNumber property.
     * 
     */
    public int getLegSerialNumber() {
        return legSerialNumber;
    }

    /**
     * Sets the value of the legSerialNumber property.
     * 
     */
    public void setLegSerialNumber(int value) {
        this.legSerialNumber = value;
    }

    /**
     * Gets the value of the pieces property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPieces() {
        return pieces;
    }

    /**
     * Sets the value of the pieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPieces(BigInteger value) {
        this.pieces = value;
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
     * Gets the value of the flightBookingStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightBookingStatus() {
        return flightBookingStatus;
    }

    /**
     * Sets the value of the flightBookingStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightBookingStatus(String value) {
        this.flightBookingStatus = value;
    }

    /**
     * Gets the value of the segmentSerialNumber property.
     * 
     */
    public int getSegmentSerialNumber() {
        return segmentSerialNumber;
    }

    /**
     * Sets the value of the segmentSerialNumber property.
     * 
     */
    public void setSegmentSerialNumber(int value) {
        this.segmentSerialNumber = value;
    }

    /**
     * Gets the value of the validForBooking property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidForBooking() {
        return validForBooking;
    }

    /**
     * Sets the value of the validForBooking property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidForBooking(String value) {
        this.validForBooking = value;
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
     * Gets the value of the operationFlag property.
     * 
     * @return
     *     possible object is
     *     {@link OperationalFlagType }
     *     
     */
    public OperationalFlagType getOperationFlag() {
        return operationFlag;
    }

    /**
     * Sets the value of the operationFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationalFlagType }
     *     
     */
    public void setOperationFlag(OperationalFlagType value) {
        this.operationFlag = value;
    }

}
