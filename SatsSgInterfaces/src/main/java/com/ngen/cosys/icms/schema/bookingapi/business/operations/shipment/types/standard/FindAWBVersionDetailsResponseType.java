
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.MessageHeaderType;


/**
 * <p>Java class for FindAWBVersionDetailsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindAWBVersionDetailsResponseType">
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
 *                   &lt;element name="awbHistoryDetails" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="revisionDetails">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="awbVersionNumber">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             &lt;pattern value="[0-9]{1,3}"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="versionCreatedBy" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             &lt;pattern value="[0-9a-zA-Z]{1,20}"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="versionCreatedTime" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="awbDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBDetailsType"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
@XmlType(name = "FindAWBVersionDetailsResponseType", propOrder = {
    "messageHeader",
    "responseDetails"
})
public class FindAWBVersionDetailsResponseType {

    @XmlElement(required = true)
    protected MessageHeaderType messageHeader;
    @XmlElement(required = true)
    protected FindAWBVersionDetailsResponseType.ResponseDetails responseDetails;

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
     *     {@link FindAWBVersionDetailsResponseType.ResponseDetails }
     *     
     */
    public FindAWBVersionDetailsResponseType.ResponseDetails getResponseDetails() {
        return responseDetails;
    }

    /**
     * Sets the value of the responseDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link FindAWBVersionDetailsResponseType.ResponseDetails }
     *     
     */
    public void setResponseDetails(FindAWBVersionDetailsResponseType.ResponseDetails value) {
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
     *         &lt;element name="awbHistoryDetails" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="revisionDetails">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="awbVersionNumber">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;pattern value="[0-9]{1,3}"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="versionCreatedBy" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;pattern value="[0-9a-zA-Z]{1,20}"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="versionCreatedTime" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="awbDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBDetailsType"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "awbHistoryDetails",
        "errorDetails"
    })
    public static class ResponseDetails {

        @XmlElement(required = true)
        protected String requestId;
        @XmlElement(required = true)
        protected String status;
        protected FindAWBVersionDetailsResponseType.ResponseDetails.AwbHistoryDetails awbHistoryDetails;
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
         * Gets the value of the awbHistoryDetails property.
         * 
         * @return
         *     possible object is
         *     {@link FindAWBVersionDetailsResponseType.ResponseDetails.AwbHistoryDetails }
         *     
         */
        public FindAWBVersionDetailsResponseType.ResponseDetails.AwbHistoryDetails getAwbHistoryDetails() {
            return awbHistoryDetails;
        }

        /**
         * Sets the value of the awbHistoryDetails property.
         * 
         * @param value
         *     allowed object is
         *     {@link FindAWBVersionDetailsResponseType.ResponseDetails.AwbHistoryDetails }
         *     
         */
        public void setAwbHistoryDetails(FindAWBVersionDetailsResponseType.ResponseDetails.AwbHistoryDetails value) {
            this.awbHistoryDetails = value;
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
         *         &lt;element name="revisionDetails">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="awbVersionNumber">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;pattern value="[0-9]{1,3}"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="versionCreatedBy" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;pattern value="[0-9a-zA-Z]{1,20}"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="versionCreatedTime" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
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
            "revisionDetails",
            "awbDetails"
        })
        public static class AwbHistoryDetails {

            @XmlElement(required = true)
            protected FindAWBVersionDetailsResponseType.ResponseDetails.AwbHistoryDetails.RevisionDetails revisionDetails;
            @XmlElement(required = true)
            protected AWBDetailsType awbDetails;

            /**
             * Gets the value of the revisionDetails property.
             * 
             * @return
             *     possible object is
             *     {@link FindAWBVersionDetailsResponseType.ResponseDetails.AwbHistoryDetails.RevisionDetails }
             *     
             */
            public FindAWBVersionDetailsResponseType.ResponseDetails.AwbHistoryDetails.RevisionDetails getRevisionDetails() {
                return revisionDetails;
            }

            /**
             * Sets the value of the revisionDetails property.
             * 
             * @param value
             *     allowed object is
             *     {@link FindAWBVersionDetailsResponseType.ResponseDetails.AwbHistoryDetails.RevisionDetails }
             *     
             */
            public void setRevisionDetails(FindAWBVersionDetailsResponseType.ResponseDetails.AwbHistoryDetails.RevisionDetails value) {
                this.revisionDetails = value;
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
             *         &lt;element name="awbVersionNumber">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;pattern value="[0-9]{1,3}"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="versionCreatedBy" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;pattern value="[0-9a-zA-Z]{1,20}"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="versionCreatedTime" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime"/>
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
                "awbVersionNumber",
                "versionCreatedBy",
                "versionCreatedTime"
            })
            public static class RevisionDetails {

                @XmlElement(required = true)
                protected String awbVersionNumber;
                protected String versionCreatedBy;
                @XmlElement(required = true)
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                @XmlSchemaType(name = "token")
                protected String versionCreatedTime;

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

                /**
                 * Gets the value of the versionCreatedBy property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getVersionCreatedBy() {
                    return versionCreatedBy;
                }

                /**
                 * Sets the value of the versionCreatedBy property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setVersionCreatedBy(String value) {
                    this.versionCreatedBy = value;
                }

                /**
                 * Gets the value of the versionCreatedTime property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getVersionCreatedTime() {
                    return versionCreatedTime;
                }

                /**
                 * Sets the value of the versionCreatedTime property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setVersionCreatedTime(String value) {
                    this.versionCreatedTime = value;
                }

            }

        }

    }

}
