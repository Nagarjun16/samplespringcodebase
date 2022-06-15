
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.ErrorDetailType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.MessageHeaderType;


/**
 * <p>Java class for ShipmentMessagesDetailsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipmentMessagesDetailsResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageHeader" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}messageHeaderType"/>
 *         &lt;element name="responseDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="requestID" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5"/>
 *                   &lt;element name="status" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                         &lt;enumeration value="S"/>
 *                         &lt;enumeration value="F"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="shipmentMessagesDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentMessagesDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="errorDetails" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}ErrorDetailType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "ShipmentMessagesDetailsResponseType", propOrder = {
    "messageHeader",
    "responseDetails"
})
public class ShipmentMessagesDetailsResponseType {

    @XmlElement(required = true)
    protected MessageHeaderType messageHeader;
    protected ShipmentMessagesDetailsResponseType.ResponseDetails responseDetails;

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
     *     {@link ShipmentMessagesDetailsResponseType.ResponseDetails }
     *     
     */
    public ShipmentMessagesDetailsResponseType.ResponseDetails getResponseDetails() {
        return responseDetails;
    }

    /**
     * Sets the value of the responseDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentMessagesDetailsResponseType.ResponseDetails }
     *     
     */
    public void setResponseDetails(ShipmentMessagesDetailsResponseType.ResponseDetails value) {
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
     *         &lt;element name="requestID" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5"/>
     *         &lt;element name="status" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *               &lt;enumeration value="S"/>
     *               &lt;enumeration value="F"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="shipmentMessagesDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentMessagesDetailsType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="errorDetails" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}ErrorDetailType" maxOccurs="unbounded" minOccurs="0"/>
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
        "status",
        "shipmentMessagesDetails",
        "errorDetails"
    })
    public static class ResponseDetails {

        @XmlElement(required = true)
        protected BigInteger requestID;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String status;
        protected List<ShipmentMessagesDetailsType> shipmentMessagesDetails;
        protected List<ErrorDetailType> errorDetails;

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
         * Gets the value of the shipmentMessagesDetails property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the shipmentMessagesDetails property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getShipmentMessagesDetails().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ShipmentMessagesDetailsType }
         * 
         * 
         */
        public List<ShipmentMessagesDetailsType> getShipmentMessagesDetails() {
            if (shipmentMessagesDetails == null) {
                shipmentMessagesDetails = new ArrayList<ShipmentMessagesDetailsType>();
            }
            return this.shipmentMessagesDetails;
        }

        /**
         * Gets the value of the errorDetails property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the errorDetails property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getErrorDetails().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ErrorDetailType }
         * 
         * 
         */
        public List<ErrorDetailType> getErrorDetails() {
            if (errorDetails == null) {
                errorDetails = new ArrayList<ErrorDetailType>();
            }
            return this.errorDetails;
        }

    }

}
