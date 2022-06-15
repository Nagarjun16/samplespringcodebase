
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.PublishHeaderType;


/**
 * <p>Java class for PublishBookingEnquiryRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PublishBookingEnquiryRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="publishHeader" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}publishHeaderType"/>
 *         &lt;element name="publishData" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}PublishData"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PublishBookingEnquiryRequestType", propOrder = {
    "publishHeader",
    "publishData"
})
public class PublishBookingEnquiryRequestType {

    @XmlElement(required = true)
    protected PublishHeaderType publishHeader;
    @XmlElement(required = true)
    protected PublishData publishData;

    /**
     * Gets the value of the publishHeader property.
     * 
     * @return
     *     possible object is
     *     {@link PublishHeaderType }
     *     
     */
    public PublishHeaderType getPublishHeader() {
        return publishHeader;
    }

    /**
     * Sets the value of the publishHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link PublishHeaderType }
     *     
     */
    public void setPublishHeader(PublishHeaderType value) {
        this.publishHeader = value;
    }

    /**
     * Gets the value of the publishData property.
     * 
     * @return
     *     possible object is
     *     {@link PublishData }
     *     
     */
    public PublishData getPublishData() {
        return publishData;
    }

    /**
     * Sets the value of the publishData property.
     * 
     * @param value
     *     allowed object is
     *     {@link PublishData }
     *     
     */
    public void setPublishData(PublishData value) {
        this.publishData = value;
    }

}
