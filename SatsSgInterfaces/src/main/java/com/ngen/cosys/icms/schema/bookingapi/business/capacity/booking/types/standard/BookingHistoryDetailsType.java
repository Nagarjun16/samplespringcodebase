
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BookingHistoryDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingHistoryDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="latestBookingVersion" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n3"/>
 *         &lt;element name="bookingVersions" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingVersionType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingHistoryDetailsType", propOrder = {
    "latestBookingVersion",
    "bookingVersions"
})
public class BookingHistoryDetailsType {

    @XmlElement(required = true)
    protected BigInteger latestBookingVersion;
    @XmlElement(required = true)
    protected BookingVersionType bookingVersions;

    /**
     * Gets the value of the latestBookingVersion property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLatestBookingVersion() {
        return latestBookingVersion;
    }

    /**
     * Sets the value of the latestBookingVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLatestBookingVersion(BigInteger value) {
        this.latestBookingVersion = value;
    }

    /**
     * Gets the value of the bookingVersions property.
     * 
     * @return
     *     possible object is
     *     {@link BookingVersionType }
     *     
     */
    public BookingVersionType getBookingVersions() {
        return bookingVersions;
    }

    /**
     * Sets the value of the bookingVersions property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingVersionType }
     *     
     */
    public void setBookingVersions(BookingVersionType value) {
        this.bookingVersions = value;
    }

}
