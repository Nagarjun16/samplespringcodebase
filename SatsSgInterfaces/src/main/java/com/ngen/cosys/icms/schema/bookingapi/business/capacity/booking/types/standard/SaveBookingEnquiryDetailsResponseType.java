
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.MessageHeaderType;


/**
 * <p>Java class for SaveBookingEnquiryDetailsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SaveBookingEnquiryDetailsResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageHeader" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}messageHeaderType"/>
 *         &lt;element name="responseDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}SaveBookingResponseData"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SaveBookingEnquiryDetailsResponseType", propOrder = {
    "messageHeader",
    "responseDetails"
})
public class SaveBookingEnquiryDetailsResponseType {

    @XmlElement(required = true)
    protected MessageHeaderType messageHeader;
    @XmlElement(required = true)
    protected SaveBookingResponseData responseDetails;

    /**
     * Gets the value of the messageHeader property.
     * 
     * @return
     *     possible object is
     *     {@link MessageHeaderType }
     *     
     */
    public MessageHeaderType getMessageHeader() {
        return messageHeader;
    }

    /**
     * Sets the value of the messageHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageHeaderType }
     *     
     */
    public void setMessageHeader(MessageHeaderType value) {
        this.messageHeader = value;
    }

    /**
     * Gets the value of the responseDetails property.
     * 
     * @return
     *     possible object is
     *     {@link SaveBookingResponseData }
     *     
     */
    public SaveBookingResponseData getResponseDetails() {
        return responseDetails;
    }

    /**
     * Sets the value of the responseDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link SaveBookingResponseData }
     *     
     */
    public void setResponseDetails(SaveBookingResponseData value) {
        this.responseDetails = value;
    }

}