
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FindBookingTemplatesRequestData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindBookingTemplatesRequestData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestID" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t300"/>
 *         &lt;element name="bookingTemplate" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingTemplateType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindBookingTemplatesRequestData", propOrder = {
    "requestID",
    "bookingTemplate"
})
public class FindBookingTemplatesRequestData {

    @XmlElement(required = true)
    protected String requestID;
    @XmlElement(required = true)
    protected BookingTemplateType bookingTemplate;

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
     * Gets the value of the bookingTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link BookingTemplateType }
     *     
     */
    public BookingTemplateType getBookingTemplate() {
        return bookingTemplate;
    }

    /**
     * Sets the value of the bookingTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingTemplateType }
     *     
     */
    public void setBookingTemplate(BookingTemplateType value) {
        this.bookingTemplate = value;
    }

}
