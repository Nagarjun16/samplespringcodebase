
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for FindAWBDuplicatesResponseDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindAWBDuplicatesResponseDetailsType">
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
 *         &lt;element name="status" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}StatusCheckType"/>
 *         &lt;element name="awbDuplicateDetailsList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="awbDuplicateDetails" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="shipmentPrefix" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}shipmentPrefix"/>
 *                             &lt;element name="masterDocumentNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}masterDocumentNumber"/>
 *                             &lt;element name="ubrNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ubrNumber" minOccurs="0"/>
 *                             &lt;element name="duplicateNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}duplicateNumber"/>
 *                             &lt;element name="awbOrigin" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
 *                             &lt;element name="awbDestination" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
 *                             &lt;element name="awbExecutionDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date"/>
 *                             &lt;element name="ownerId" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ownerId" minOccurs="0"/>
 *                             &lt;element name="shipmentStatus">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;enumeration value="N"/>
 *                                   &lt;enumeration value="E"/>
 *                                   &lt;enumeration value="F"/>
 *                                   &lt;enumeration value="R"/>
 *                                   &lt;enumeration value="V"/>
 *                                   &lt;enumeration value="D"/>
 *                                   &lt;enumeration value="P"/>
 *                                   &lt;enumeration value="C"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="lastUpdatedTime" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime"/>
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
 *         &lt;element name="errorDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ErrorDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindAWBDuplicatesResponseDetailsType", propOrder = {
    "requestId",
    "status",
    "awbDuplicateDetailsList",
    "errorDetails"
})
public class FindAWBDuplicatesResponseDetailsType {

    @XmlElement(required = true)
    protected String requestId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected StatusCheckType status;
    protected FindAWBDuplicatesResponseDetailsType.AwbDuplicateDetailsList awbDuplicateDetailsList;
    protected List<ErrorDetailsType> errorDetails;

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
     *     {@link StatusCheckType }
     *     
     */
    public StatusCheckType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusCheckType }
     *     
     */
    public void setStatus(StatusCheckType value) {
        this.status = value;
    }

    /**
     * Gets the value of the awbDuplicateDetailsList property.
     * 
     * @return
     *     possible object is
     *     {@link FindAWBDuplicatesResponseDetailsType.AwbDuplicateDetailsList }
     *     
     */
    public FindAWBDuplicatesResponseDetailsType.AwbDuplicateDetailsList getAwbDuplicateDetailsList() {
        return awbDuplicateDetailsList;
    }

    /**
     * Sets the value of the awbDuplicateDetailsList property.
     * 
     * @param value
     *     allowed object is
     *     {@link FindAWBDuplicatesResponseDetailsType.AwbDuplicateDetailsList }
     *     
     */
    public void setAwbDuplicateDetailsList(FindAWBDuplicatesResponseDetailsType.AwbDuplicateDetailsList value) {
        this.awbDuplicateDetailsList = value;
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
     * {@link ErrorDetailsType }
     * 
     * 
     */
    public List<ErrorDetailsType> getErrorDetails() {
        if (errorDetails == null) {
            errorDetails = new ArrayList<ErrorDetailsType>();
        }
        return this.errorDetails;
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
     *         &lt;element name="awbDuplicateDetails" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="shipmentPrefix" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}shipmentPrefix"/>
     *                   &lt;element name="masterDocumentNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}masterDocumentNumber"/>
     *                   &lt;element name="ubrNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ubrNumber" minOccurs="0"/>
     *                   &lt;element name="duplicateNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}duplicateNumber"/>
     *                   &lt;element name="awbOrigin" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
     *                   &lt;element name="awbDestination" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
     *                   &lt;element name="awbExecutionDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date"/>
     *                   &lt;element name="ownerId" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ownerId" minOccurs="0"/>
     *                   &lt;element name="shipmentStatus">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;enumeration value="N"/>
     *                         &lt;enumeration value="E"/>
     *                         &lt;enumeration value="F"/>
     *                         &lt;enumeration value="R"/>
     *                         &lt;enumeration value="V"/>
     *                         &lt;enumeration value="D"/>
     *                         &lt;enumeration value="P"/>
     *                         &lt;enumeration value="C"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="lastUpdatedTime" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime"/>
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
        "awbDuplicateDetails"
    })
    public static class AwbDuplicateDetailsList {

        @XmlElement(required = true)
        protected List<FindAWBDuplicatesResponseDetailsType.AwbDuplicateDetailsList.AwbDuplicateDetails> awbDuplicateDetails;

        /**
         * Gets the value of the awbDuplicateDetails property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the awbDuplicateDetails property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAwbDuplicateDetails().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FindAWBDuplicatesResponseDetailsType.AwbDuplicateDetailsList.AwbDuplicateDetails }
         * 
         * 
         */
        public List<FindAWBDuplicatesResponseDetailsType.AwbDuplicateDetailsList.AwbDuplicateDetails> getAwbDuplicateDetails() {
            if (awbDuplicateDetails == null) {
                awbDuplicateDetails = new ArrayList<FindAWBDuplicatesResponseDetailsType.AwbDuplicateDetailsList.AwbDuplicateDetails>();
            }
            return this.awbDuplicateDetails;
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
         *         &lt;element name="shipmentPrefix" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}shipmentPrefix"/>
         *         &lt;element name="masterDocumentNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}masterDocumentNumber"/>
         *         &lt;element name="ubrNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ubrNumber" minOccurs="0"/>
         *         &lt;element name="duplicateNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}duplicateNumber"/>
         *         &lt;element name="awbOrigin" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
         *         &lt;element name="awbDestination" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
         *         &lt;element name="awbExecutionDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date"/>
         *         &lt;element name="ownerId" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ownerId" minOccurs="0"/>
         *         &lt;element name="shipmentStatus">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;enumeration value="N"/>
         *               &lt;enumeration value="E"/>
         *               &lt;enumeration value="F"/>
         *               &lt;enumeration value="R"/>
         *               &lt;enumeration value="V"/>
         *               &lt;enumeration value="D"/>
         *               &lt;enumeration value="P"/>
         *               &lt;enumeration value="C"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="lastUpdatedTime" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime"/>
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
            "shipmentPrefix",
            "masterDocumentNumber",
            "ubrNumber",
            "duplicateNumber",
            "awbOrigin",
            "awbDestination",
            "awbExecutionDate",
            "ownerId",
            "shipmentStatus",
            "lastUpdatedTime"
        })
        public static class AwbDuplicateDetails {

            @XmlElement(required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String shipmentPrefix;
            @XmlElement(required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String masterDocumentNumber;
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String ubrNumber;
            @XmlElement(required = true)
            protected BigInteger duplicateNumber;
            @XmlElement(required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String awbOrigin;
            @XmlElement(required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String awbDestination;
            @XmlElement(required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String awbExecutionDate;
            protected BigInteger ownerId;
            @XmlElement(required = true)
            protected String shipmentStatus;
            @XmlElement(required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String lastUpdatedTime;

            /**
             * Gets the value of the shipmentPrefix property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getShipmentPrefix() {
                return shipmentPrefix;
            }

            /**
             * Sets the value of the shipmentPrefix property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setShipmentPrefix(String value) {
                this.shipmentPrefix = value;
            }

            /**
             * Gets the value of the masterDocumentNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMasterDocumentNumber() {
                return masterDocumentNumber;
            }

            /**
             * Sets the value of the masterDocumentNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMasterDocumentNumber(String value) {
                this.masterDocumentNumber = value;
            }

            /**
             * Gets the value of the ubrNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUbrNumber() {
                return ubrNumber;
            }

            /**
             * Sets the value of the ubrNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUbrNumber(String value) {
                this.ubrNumber = value;
            }

            /**
             * Gets the value of the duplicateNumber property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getDuplicateNumber() {
                return duplicateNumber;
            }

            /**
             * Sets the value of the duplicateNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setDuplicateNumber(BigInteger value) {
                this.duplicateNumber = value;
            }

            /**
             * Gets the value of the awbOrigin property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAwbOrigin() {
                return awbOrigin;
            }

            /**
             * Sets the value of the awbOrigin property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAwbOrigin(String value) {
                this.awbOrigin = value;
            }

            /**
             * Gets the value of the awbDestination property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAwbDestination() {
                return awbDestination;
            }

            /**
             * Sets the value of the awbDestination property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAwbDestination(String value) {
                this.awbDestination = value;
            }

            /**
             * Gets the value of the awbExecutionDate property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAwbExecutionDate() {
                return awbExecutionDate;
            }

            /**
             * Sets the value of the awbExecutionDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAwbExecutionDate(String value) {
                this.awbExecutionDate = value;
            }

            /**
             * Gets the value of the ownerId property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getOwnerId() {
                return ownerId;
            }

            /**
             * Sets the value of the ownerId property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setOwnerId(BigInteger value) {
                this.ownerId = value;
            }

            /**
             * Gets the value of the shipmentStatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getShipmentStatus() {
                return shipmentStatus;
            }

            /**
             * Sets the value of the shipmentStatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setShipmentStatus(String value) {
                this.shipmentStatus = value;
            }

            /**
             * Gets the value of the lastUpdatedTime property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLastUpdatedTime() {
                return lastUpdatedTime;
            }

            /**
             * Sets the value of the lastUpdatedTime property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLastUpdatedTime(String value) {
                this.lastUpdatedTime = value;
            }

        }

    }

}
