
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.MessageHeaderType;


/**
 * <p>Java class for ShipmentMessagesDetailsRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipmentMessagesDetailsRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageHeader" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}messageHeaderType"/>
 *         &lt;element name="requestData">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="requestID" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5"/>
 *                   &lt;element name="shipmentFilterDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentFilterDetailsType"/>
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
@XmlType(name = "ShipmentMessagesDetailsRequestType", propOrder = {
    "messageHeader",
    "requestData"
})
public class ShipmentMessagesDetailsRequestType {

    @XmlElement(required = true)
    protected MessageHeaderType messageHeader;
    @XmlElement(required = true)
    protected ShipmentMessagesDetailsRequestType.RequestData requestData;

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
     * Gets the value of the requestData property.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentMessagesDetailsRequestType.RequestData }
     *     
     */
    public ShipmentMessagesDetailsRequestType.RequestData getRequestData() {
        return requestData;
    }

    /**
     * Sets the value of the requestData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentMessagesDetailsRequestType.RequestData }
     *     
     */
    public void setRequestData(ShipmentMessagesDetailsRequestType.RequestData value) {
        this.requestData = value;
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
     *         &lt;element name="requestID" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5"/>
     *         &lt;element name="shipmentFilterDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentFilterDetailsType"/>
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
        "requestID",
        "shipmentFilterDetails"
    })
    public static class RequestData {

        @XmlElement(required = true)
        protected BigInteger requestID;
        @XmlElement(required = true)
        protected ShipmentFilterDetailsType shipmentFilterDetails;

        /**
         * Gets the value of the requestID property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getRequestID() {
            return requestID;
        }

        /**
         * Sets the value of the requestID property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setRequestID(BigInteger value) {
            this.requestID = value;
        }

        /**
         * Gets the value of the shipmentFilterDetails property.
         * 
         * @return
         *     possible object is
         *     {@link ShipmentFilterDetailsType }
         *     
         */
        public ShipmentFilterDetailsType getShipmentFilterDetails() {
            return shipmentFilterDetails;
        }

        /**
         * Sets the value of the shipmentFilterDetails property.
         * 
         * @param value
         *     allowed object is
         *     {@link ShipmentFilterDetailsType }
         *     
         */
        public void setShipmentFilterDetails(ShipmentFilterDetailsType value) {
            this.shipmentFilterDetails = value;
        }

    }

}
