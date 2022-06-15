
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.MessageHeaderType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.OperationalFlagType;


/**
 * <p>Java class for FindAWBVersionDetailsRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindAWBVersionDetailsRequestType">
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
 *                   &lt;element name="shipmentVersionDetailsFilter">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="shipmentDetailsFilter" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}shipmentDetailsFilterType"/>
 *                             &lt;element name="awbVersionNumber" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;pattern value="[0-9]{1,3}"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
@XmlType(name = "FindAWBVersionDetailsRequestType", propOrder = {
    "messageHeader",
    "requestData"
})
public class FindAWBVersionDetailsRequestType {

    @XmlElement(required = true)
    protected MessageHeaderType messageHeader;
    @XmlElement(required = true)
    protected FindAWBVersionDetailsRequestType.RequestData requestData;

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
     *     {@link FindAWBVersionDetailsRequestType.RequestData }
     *     
     */
    public FindAWBVersionDetailsRequestType.RequestData getRequestData() {
        return requestData;
    }

    /**
     * Sets the value of the requestData property.
     * 
     * @param value
     *     allowed object is
     *     {@link FindAWBVersionDetailsRequestType.RequestData }
     *     
     */
    public void setRequestData(FindAWBVersionDetailsRequestType.RequestData value) {
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
     *         &lt;element name="shipmentVersionDetailsFilter">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="shipmentDetailsFilter" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}shipmentDetailsFilterType"/>
     *                   &lt;element name="awbVersionNumber" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;pattern value="[0-9]{1,3}"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
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
        "requestID",
        "operationalFlag",
        "shipmentVersionDetailsFilter"
    })
    public static class RequestData {

        @XmlElement(required = true)
        protected String requestID;
        @XmlSchemaType(name = "string")
        protected OperationalFlagType operationalFlag;
        @XmlElement(required = true)
        protected FindAWBVersionDetailsRequestType.RequestData.ShipmentVersionDetailsFilter shipmentVersionDetailsFilter;

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
         * Gets the value of the shipmentVersionDetailsFilter property.
         * 
         * @return
         *     possible object is
         *     {@link FindAWBVersionDetailsRequestType.RequestData.ShipmentVersionDetailsFilter }
         *     
         */
        public FindAWBVersionDetailsRequestType.RequestData.ShipmentVersionDetailsFilter getShipmentVersionDetailsFilter() {
            return shipmentVersionDetailsFilter;
        }

        /**
         * Sets the value of the shipmentVersionDetailsFilter property.
         * 
         * @param value
         *     allowed object is
         *     {@link FindAWBVersionDetailsRequestType.RequestData.ShipmentVersionDetailsFilter }
         *     
         */
        public void setShipmentVersionDetailsFilter(FindAWBVersionDetailsRequestType.RequestData.ShipmentVersionDetailsFilter value) {
            this.shipmentVersionDetailsFilter = value;
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
         *         &lt;element name="shipmentDetailsFilter" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}shipmentDetailsFilterType"/>
         *         &lt;element name="awbVersionNumber" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;pattern value="[0-9]{1,3}"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
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
            "shipmentDetailsFilter",
            "awbVersionNumber"
        })
        public static class ShipmentVersionDetailsFilter {

            @XmlElement(required = true)
            protected ShipmentDetailsFilterType shipmentDetailsFilter;
            protected String awbVersionNumber;

            /**
             * Gets the value of the shipmentDetailsFilter property.
             * 
             * @return
             *     possible object is
             *     {@link ShipmentDetailsFilterType }
             *     
             */
            public ShipmentDetailsFilterType getShipmentDetailsFilter() {
                return shipmentDetailsFilter;
            }

            /**
             * Sets the value of the shipmentDetailsFilter property.
             * 
             * @param value
             *     allowed object is
             *     {@link ShipmentDetailsFilterType }
             *     
             */
            public void setShipmentDetailsFilter(ShipmentDetailsFilterType value) {
                this.shipmentDetailsFilter = value;
            }

            /**
             * Gets the value of the awbVersionNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAwbVersionNumber() {
                return awbVersionNumber;
            }

            /**
             * Sets the value of the awbVersionNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAwbVersionNumber(String value) {
                this.awbVersionNumber = value;
            }

        }

    }

}
