
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.MessageHeaderType;


/**
 * <p>Java class for DeleteHAWBDetailsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeleteHAWBDetailsResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageHeader" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}messageHeaderType"/>
 *         &lt;element name="responseDetails">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="requestId">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;pattern value="[0-9]{1,5}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="status">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="S"/>
 *                         &lt;enumeration value="F"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="shipmentIndexDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentIndexDetails" minOccurs="0"/>
 *                   &lt;element name="errorDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ErrorDetailsType" minOccurs="0"/>
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
@XmlType(name = "DeleteHAWBDetailsResponseType", propOrder = {
    "messageHeader",
    "responseDetails"
})
public class DeleteHAWBDetailsResponseType {

    @XmlElement(required = true)
    protected MessageHeaderType messageHeader;
    @XmlElement(required = true)
    protected DeleteHAWBDetailsResponseType.ResponseDetails responseDetails;

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
     *     {@link DeleteHAWBDetailsResponseType.ResponseDetails }
     *     
     */
    public DeleteHAWBDetailsResponseType.ResponseDetails getResponseDetails() {
        return responseDetails;
    }

    /**
     * Sets the value of the responseDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeleteHAWBDetailsResponseType.ResponseDetails }
     *     
     */
    public void setResponseDetails(DeleteHAWBDetailsResponseType.ResponseDetails value) {
        this.responseDetails = value;
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
     *         &lt;element name="requestId">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;pattern value="[0-9]{1,5}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="status">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="S"/>
     *               &lt;enumeration value="F"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="shipmentIndexDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentIndexDetails" minOccurs="0"/>
     *         &lt;element name="errorDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ErrorDetailsType" minOccurs="0"/>
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
        "requestId",
        "status",
        "shipmentIndexDetails",
        "errorDetails"
    })
    public static class ResponseDetails {

        @XmlElement(required = true)
        protected String requestId;
        @XmlElement(required = true)
        protected String status;
        protected ShipmentIndexDetails shipmentIndexDetails;
        protected ErrorDetailsType errorDetails;

        /**
         * Gets the value of the requestId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRequestId() {
            return requestId;
        }

        /**
         * Sets the value of the requestId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRequestId(String value) {
            this.requestId = value;
        }

        /**
         * Gets the value of the status property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatus(String value) {
            this.status = value;
        }

        /**
         * Gets the value of the shipmentIndexDetails property.
         * 
         * @return
         *     possible object is
         *     {@link ShipmentIndexDetails }
         *     
         */
        public ShipmentIndexDetails getShipmentIndexDetails() {
            return shipmentIndexDetails;
        }

        /**
         * Sets the value of the shipmentIndexDetails property.
         * 
         * @param value
         *     allowed object is
         *     {@link ShipmentIndexDetails }
         *     
         */
        public void setShipmentIndexDetails(ShipmentIndexDetails value) {
            this.shipmentIndexDetails = value;
        }

        /**
         * Gets the value of the errorDetails property.
         * 
         * @return
         *     possible object is
         *     {@link ErrorDetailsType }
         *     
         */
        public ErrorDetailsType getErrorDetails() {
            return errorDetails;
        }

        /**
         * Sets the value of the errorDetails property.
         * 
         * @param value
         *     allowed object is
         *     {@link ErrorDetailsType }
         *     
         */
        public void setErrorDetails(ErrorDetailsType value) {
            this.errorDetails = value;
        }

    }

}
