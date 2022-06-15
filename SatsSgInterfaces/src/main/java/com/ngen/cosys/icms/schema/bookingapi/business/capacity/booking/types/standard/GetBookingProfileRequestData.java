
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetBookingProfileRequestData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetBookingProfileRequestData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestID" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t300"/>
 *         &lt;element name="bookingfilter" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingProfileType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetBookingProfileRequestData", propOrder = {
    "requestID",
    "bookingfilter"
})
public class GetBookingProfileRequestData {

    @XmlElement(required = true)
    protected String requestID;
    @XmlElement(required = true)
    protected BookingProfileType bookingfilter;

    /**
     * Gets the value of the requestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestID() {
        return requestID;
    }

    /**
     * Sets the value of the requestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestID(String value) {
        this.requestID = value;
    }

    /**
     * Gets the value of the bookingfilter property.
     * 
     * @return
     *     possible object is
     *     {@link BookingProfileType }
     *     
     */
    public BookingProfileType getBookingfilter() {
        return bookingfilter;
    }

    /**
     * Sets the value of the bookingfilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingProfileType }
     *     
     */
    public void setBookingfilter(BookingProfileType value) {
        this.bookingfilter = value;
    }

}
