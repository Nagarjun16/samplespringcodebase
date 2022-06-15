
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.framework.services.jaxws.types.common.PageFilter;


/**
 * <p>Java class for FindBookingsAWBDetailsRequestData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindBookingsAWBDetailsRequestData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestID" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t300"/>
 *         &lt;element name="bookingDetailsFilter" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingFilterType"/>
 *         &lt;element name="pageDetails" type="{http://www.ibsplc.com/icargo/services}PageFilter" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindBookingsAWBDetailsRequestData", propOrder = {
    "requestID",
    "bookingDetailsFilter",
    "pageDetails"
})
public class FindBookingsAWBDetailsRequestData {

    @XmlElement(required = true)
    protected String requestID;
    @XmlElement(required = true)
    protected BookingFilterType bookingDetailsFilter;
    protected PageFilter pageDetails;

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
     * Gets the value of the bookingDetailsFilter property.
     * 
     * @return
     *     possible object is
     *     {@link BookingFilterType }
     *     
     */
    public BookingFilterType getBookingDetailsFilter() {
        return bookingDetailsFilter;
    }

    /**
     * Sets the value of the bookingDetailsFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingFilterType }
     *     
     */
    public void setBookingDetailsFilter(BookingFilterType value) {
        this.bookingDetailsFilter = value;
    }

    /**
     * Gets the value of the pageDetails property.
     * 
     * @return
     *     possible object is
     *     {@link PageFilter }
     *     
     */
    public PageFilter getPageDetails() {
        return pageDetails;
    }

    /**
     * Sets the value of the pageDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link PageFilter }
     *     
     */
    public void setPageDetails(PageFilter value) {
        this.pageDetails = value;
    }

}
