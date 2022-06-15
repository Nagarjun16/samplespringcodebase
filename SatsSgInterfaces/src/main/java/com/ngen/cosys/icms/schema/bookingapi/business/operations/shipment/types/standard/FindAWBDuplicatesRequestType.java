
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.MessageHeaderType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.OperationalFlagType;


/**
 * <p>Java class for FindAWBDuplicatesRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindAWBDuplicatesRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageHeader" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}messageHeaderType"/>
 *         &lt;element name="requestData">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="requestID">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;pattern value="[0-9]{1,5}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="operationalFlag" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}operationalFlagType" minOccurs="0"/>
 *                   &lt;element name="shipmentIdentifierDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentIdentifierDetailsType"/>
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
@XmlType(name = "FindAWBDuplicatesRequestType", propOrder = {
    "messageHeader",
    "requestData"
})
public class FindAWBDuplicatesRequestType {

    @XmlElement(required = true)
    protected MessageHeaderType messageHeader;
    @XmlElement(required = true)
    protected FindAWBDuplicatesRequestType.RequestData requestData;

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
     *     {@link FindAWBDuplicatesRequestType.RequestData }
     *     
     */
    public FindAWBDuplicatesRequestType.RequestData getRequestData() {
        return requestData;
    }

    /**
     * Sets the value of the requestData property.
     * 
     * @param value
     *     allowed object is
     *     {@link FindAWBDuplicatesRequestType.RequestData }
     *     
     */
    public void setRequestData(FindAWBDuplicatesRequestType.RequestData value) {
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
     *         &lt;element name="requestID">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;pattern value="[0-9]{1,5}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="operationalFlag" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}operationalFlagType" minOccurs="0"/>
     *         &lt;element name="shipmentIdentifierDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentIdentifierDetailsType"/>
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
        "operationalFlag",
        "shipmentIdentifierDetails"
    })
    public static class RequestData {

        @XmlElement(required = true)
        protected String requestID;
        @XmlSchemaType(name = "string")
        protected OperationalFlagType operationalFlag;
        @XmlElement(required = true)
        protected ShipmentIdentifierDetailsType shipmentIdentifierDetails;

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
         * Gets the value of the operationalFlag property.
         * 
         * @return
         *     possible object is
         *     {@link OperationalFlagType }
         *     
         */
        public OperationalFlagType getOperationalFlag() {
            return operationalFlag;
        }

        /**
         * Sets the value of the operationalFlag property.
         * 
         * @param value
         *     allowed object is
         *     {@link OperationalFlagType }
         *     
         */
        public void setOperationalFlag(OperationalFlagType value) {
            this.operationalFlag = value;
        }

        /**
         * Gets the value of the shipmentIdentifierDetails property.
         * 
         * @return
         *     possible object is
         *     {@link ShipmentIdentifierDetailsType }
         *     
         */
        public ShipmentIdentifierDetailsType getShipmentIdentifierDetails() {
            return shipmentIdentifierDetails;
        }

        /**
         * Sets the value of the shipmentIdentifierDetails property.
         * 
         * @param value
         *     allowed object is
         *     {@link ShipmentIdentifierDetailsType }
         *     
         */
        public void setShipmentIdentifierDetails(ShipmentIdentifierDetailsType value) {
            this.shipmentIdentifierDetails = value;
        }

    }

}
