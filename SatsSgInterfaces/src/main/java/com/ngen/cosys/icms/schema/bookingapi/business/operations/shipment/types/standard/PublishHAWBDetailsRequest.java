
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.PublishHeaderType;


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
 *         &lt;element name="publishHeader" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}publishHeaderType"/>
 *         &lt;element name="publishEntity" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}publishHAWBEntityType"/>
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
    "publishHeader",
    "publishEntity"
})
@XmlRootElement(name = "publishHAWBDetailsRequest")
public class PublishHAWBDetailsRequest {

    @XmlElement(required = true)
    protected PublishHeaderType publishHeader;
    @XmlElement(required = true)
    protected PublishHAWBEntityType publishEntity;

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
     * Gets the value of the publishEntity property.
     * 
     * @return
     *     possible object is
     *     {@link PublishHAWBEntityType }
     *     
     */
    public PublishHAWBEntityType getPublishEntity() {
        return publishEntity;
    }

    /**
     * Sets the value of the publishEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link PublishHAWBEntityType }
     *     
     */
    public void setPublishEntity(PublishHAWBEntityType value) {
        this.publishEntity = value;
    }

}
