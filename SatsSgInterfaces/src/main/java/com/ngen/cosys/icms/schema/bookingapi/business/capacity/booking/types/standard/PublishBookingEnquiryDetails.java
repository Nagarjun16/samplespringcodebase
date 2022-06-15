
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PublishBookingEnquiryRequest" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}PublishBookingEnquiryRequestType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "publishBookingEnquiryRequest"
})
@XmlRootElement(name = "PublishBookingEnquiryDetails")
public class PublishBookingEnquiryDetails {

    @XmlElement(name = "PublishBookingEnquiryRequest", required = true)
    protected PublishBookingEnquiryRequestType publishBookingEnquiryRequest;

    /**
     * Gets the value of the publishBookingEnquiryRequest property.
     * 
     * @return
     *     possible object is
     *     {@link PublishBookingEnquiryRequestType }
     *     
     */
    public PublishBookingEnquiryRequestType getPublishBookingEnquiryRequest() {
        return publishBookingEnquiryRequest;
    }

    /**
     * Sets the value of the publishBookingEnquiryRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link PublishBookingEnquiryRequestType }
     *     
     */
    public void setPublishBookingEnquiryRequest(PublishBookingEnquiryRequestType value) {
        this.publishBookingEnquiryRequest = value;
    }

}
