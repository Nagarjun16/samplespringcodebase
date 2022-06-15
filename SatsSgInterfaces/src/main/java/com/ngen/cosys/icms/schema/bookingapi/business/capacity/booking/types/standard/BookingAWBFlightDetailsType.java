
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for BookingAWBFlightDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingAWBFlightDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flightCarrierId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="carrierCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}flightCarrierCode"/>
 *         &lt;element name="flightNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}flightNumber" minOccurs="0"/>
 *         &lt;element name="flightDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date"/>
 *         &lt;element name="segmentDepartureDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="segmentDepartureDateUTC" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="segmentArrivalDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="segmentArrivalDateUTC" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="flightBookingStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="allotmentId" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m30" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingAWBFlightDetailsType", propOrder = {
    "flightCarrierId",
    "carrierCode",
    "flightNumber",
    "flightDate",
    "segmentDepartureDate",
    "segmentDepartureDateUTC",
    "segmentArrivalDate",
    "segmentArrivalDateUTC",
    "flightBookingStatus",
    "allotmentId"
})
public class BookingAWBFlightDetailsType {

    protected int flightCarrierId;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String carrierCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String flightNumber;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String flightDate;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String segmentDepartureDate;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String segmentDepartureDateUTC;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String segmentArrivalDate;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String segmentArrivalDateUTC;
    @XmlElement(required = true)
    protected String flightBookingStatus;
    protected String allotmentId;

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
     * Gets the value of the segmentDepartureDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentDepartureDate() {
        return segmentDepartureDate;
    }

    /**
     * Sets the value of the segmentDepartureDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentDepartureDate(String value) {
        this.segmentDepartureDate = value;
    }

    /**
     * Gets the value of the segmentDepartureDateUTC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentDepartureDateUTC() {
        return segmentDepartureDateUTC;
    }

    /**
     * Sets the value of the segmentDepartureDateUTC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentDepartureDateUTC(String value) {
        this.segmentDepartureDateUTC = value;
    }

    /**
     * Gets the value of the segmentArrivalDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentArrivalDate() {
        return segmentArrivalDate;
    }

    /**
     * Sets the value of the segmentArrivalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentArrivalDate(String value) {
        this.segmentArrivalDate = value;
    }

    /**
     * Gets the value of the segmentArrivalDateUTC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentArrivalDateUTC() {
        return segmentArrivalDateUTC;
    }

    /**
     * Sets the value of the segmentArrivalDateUTC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentArrivalDateUTC(String value) {
        this.segmentArrivalDateUTC = value;
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
     * Gets the value of the allotmentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllotmentId() {
        return allotmentId;
    }

    /**
     * Sets the value of the allotmentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllotmentId(String value) {
        this.allotmentId = value;
    }

}
