
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
 *         &lt;element name="publishData">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="publishID">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;pattern value="[1-9][0-9]{0,4}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="entity">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;pattern value="[a-zA-Z]{0,50}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="awbDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBDetailsType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "publishData"
})
@XmlRootElement(name = "publishAWBDetailsRequest")
public class PublishAWBDetailsRequest {

    @XmlElement(required = true)
    protected PublishHeaderType publishHeader;
    @XmlElement(required = true)
    protected PublishAWBDetailsRequest.PublishData publishData;

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
     *     {@link PublishAWBDetailsRequest.PublishData }
     *     
     */
    public PublishAWBDetailsRequest.PublishData getPublishData() {
        return publishData;
    }

    /**
     * Sets the value of the publishData property.
     * 
     * @param value
     *     allowed object is
     *     {@link PublishAWBDetailsRequest.PublishData }
     *     
     */
    public void setPublishData(PublishAWBDetailsRequest.PublishData value) {
        this.publishData = value;
    }


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
     *         &lt;element name="publishID">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;pattern value="[1-9][0-9]{0,4}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="entity">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;pattern value="[a-zA-Z]{0,50}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="awbDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBDetailsType"/>
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
        "publishID",
        "entity",
        "awbDetails"
    })
    public static class PublishData {

        protected int publishID;
        @XmlElement(required = true)
        protected String entity;
        @XmlElement(required = true)
        protected AWBDetailsType awbDetails;

        /**
         * Gets the value of the publishID property.
         * 
         */
        public int getPublishID() {
            return publishID;
        }

        /**
         * Sets the value of the publishID property.
         * 
         */
        public void setPublishID(int value) {
            this.publishID = value;
        }

        /**
         * Gets the value of the entity property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEntity() {
            return entity;
        }

        /**
         * Sets the value of the entity property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEntity(String value) {
            this.entity = value;
        }

        /**
         * Gets the value of the awbDetails property.
         * 
         * @return
         *     possible object is
         *     {@link AWBDetailsType }
         *     
         */
        public AWBDetailsType getAwbDetails() {
            return awbDetails;
        }

        /**
         * Sets the value of the awbDetails property.
         * 
         * @param value
         *     allowed object is
         *     {@link AWBDetailsType }
         *     
         */
        public void setAwbDetails(AWBDetailsType value) {
            this.awbDetails = value;
        }

    }

}
