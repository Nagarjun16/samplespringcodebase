
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * 				The common type containing the details of the agent.
 * 			
 * 
 * <p>Java class for AgentDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AgentDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="agentCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;pattern value="[a-zA-Z0-9]{0,15}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="agentName">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="70"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="agentAccountNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}accountNumber" minOccurs="0"/>
 *         &lt;element name="agentStreetAddress" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="70"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="stationCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}stationCode" minOccurs="0"/>
 *         &lt;element name="agentPlace" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="70"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="agentState" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="70"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="agentCountryCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}countryCode" minOccurs="0"/>
 *         &lt;element name="agentZipCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}postalCodeType" minOccurs="0"/>
 *         &lt;element name="agentContactDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AgentContactDetailsType" minOccurs="0"/>
 *         &lt;element name="participantIdentifier" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="AIR"/>
 *               &lt;enumeration value="APT"/>
 *               &lt;enumeration value="AGT"/>
 *               &lt;enumeration value="BRK"/>
 *               &lt;enumeration value="CAG"/>
 *               &lt;enumeration value="CNE"/>
 *               &lt;enumeration value="CTM"/>
 *               &lt;enumeration value="DCL"/>
 *               &lt;enumeration value="DEC"/>
 *               &lt;enumeration value="FFW"/>
 *               &lt;enumeration value="GHA"/>
 *               &lt;enumeration value="PTT"/>
 *               &lt;enumeration value="SHP"/>
 *               &lt;enumeration value="TRK"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="agentLAVNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;pattern value="[a-zA-Z0-9]{7,8}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="agentVATNo" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m100" minOccurs="0"/>
 *         &lt;element name="agentCreditStatus" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="A"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="commissionableAgentDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="agentIATACode">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                         &lt;pattern value="[0-9]{7}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="agentIATACassAddress" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                         &lt;pattern value="[0-9]{4}"/>
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
@XmlType(name = "AgentDetailsType", propOrder = {
    "agentCode",
    "agentName",
    "agentAccountNumber",
    "agentStreetAddress",
    "stationCode",
    "agentPlace",
    "agentState",
    "agentCountryCode",
    "agentZipCode",
    "agentContactDetails",
    "participantIdentifier",
    "agentLAVNumber",
    "agentVATNo",
    "agentCreditStatus",
    "commissionableAgentDetails"
})
public class AgentDetailsType {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String agentCode;
    @XmlElement(required = true)
    protected String agentName;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String agentAccountNumber;
    protected String agentStreetAddress;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String stationCode;
    protected String agentPlace;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String agentState;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String agentCountryCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String agentZipCode;
    protected AgentContactDetailsType agentContactDetails;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String participantIdentifier;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String agentLAVNumber;
    protected String agentVATNo;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String agentCreditStatus;
    protected AgentDetailsType.CommissionableAgentDetails commissionableAgentDetails;

    /**
     * Gets the value of the agentCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentCode() {
        return agentCode;
    }

    /**
     * Sets the value of the agentCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentCode(String value) {
        this.agentCode = value;
    }

    /**
     * Gets the value of the agentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * Sets the value of the agentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentName(String value) {
        this.agentName = value;
    }

    /**
     * Gets the value of the agentAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentAccountNumber() {
        return agentAccountNumber;
    }

    /**
     * Sets the value of the agentAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentAccountNumber(String value) {
        this.agentAccountNumber = value;
    }

    /**
     * Gets the value of the agentStreetAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentStreetAddress() {
        return agentStreetAddress;
    }

    /**
     * Sets the value of the agentStreetAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentStreetAddress(String value) {
        this.agentStreetAddress = value;
    }

    /**
     * Gets the value of the stationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStationCode() {
        return stationCode;
    }

    /**
     * Sets the value of the stationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStationCode(String value) {
        this.stationCode = value;
    }

    /**
     * Gets the value of the agentPlace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentPlace() {
        return agentPlace;
    }

    /**
     * Sets the value of the agentPlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentPlace(String value) {
        this.agentPlace = value;
    }

    /**
     * Gets the value of the agentState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentState() {
        return agentState;
    }

    /**
     * Sets the value of the agentState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentState(String value) {
        this.agentState = value;
    }

    /**
     * Gets the value of the agentCountryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentCountryCode() {
        return agentCountryCode;
    }

    /**
     * Sets the value of the agentCountryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentCountryCode(String value) {
        this.agentCountryCode = value;
    }

    /**
     * Gets the value of the agentZipCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentZipCode() {
        return agentZipCode;
    }

    /**
     * Sets the value of the agentZipCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentZipCode(String value) {
        this.agentZipCode = value;
    }

    /**
     * Gets the value of the agentContactDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AgentContactDetailsType }
     *     
     */
    public AgentContactDetailsType getAgentContactDetails() {
        return agentContactDetails;
    }

    /**
     * Sets the value of the agentContactDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgentContactDetailsType }
     *     
     */
    public void setAgentContactDetails(AgentContactDetailsType value) {
        this.agentContactDetails = value;
    }

    /**
     * Gets the value of the participantIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParticipantIdentifier() {
        return participantIdentifier;
    }

    /**
     * Sets the value of the participantIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParticipantIdentifier(String value) {
        this.participantIdentifier = value;
    }

    /**
     * Gets the value of the agentLAVNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentLAVNumber() {
        return agentLAVNumber;
    }

    /**
     * Sets the value of the agentLAVNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentLAVNumber(String value) {
        this.agentLAVNumber = value;
    }

    /**
     * Gets the value of the agentVATNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentVATNo() {
        return agentVATNo;
    }

    /**
     * Sets the value of the agentVATNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentVATNo(String value) {
        this.agentVATNo = value;
    }

    /**
     * Gets the value of the agentCreditStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentCreditStatus() {
        return agentCreditStatus;
    }

    /**
     * Sets the value of the agentCreditStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentCreditStatus(String value) {
        this.agentCreditStatus = value;
    }

    /**
     * Gets the value of the commissionableAgentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AgentDetailsType.CommissionableAgentDetails }
     *     
     */
    public AgentDetailsType.CommissionableAgentDetails getCommissionableAgentDetails() {
        return commissionableAgentDetails;
    }

    /**
     * Sets the value of the commissionableAgentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgentDetailsType.CommissionableAgentDetails }
     *     
     */
    public void setCommissionableAgentDetails(AgentDetailsType.CommissionableAgentDetails value) {
        this.commissionableAgentDetails = value;
    }


    /**
     * 
     * 							The node contains additional details of the
     * 							agent
     * 						
     * 
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="agentIATACode">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *               &lt;pattern value="[0-9]{7}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="agentIATACassAddress" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *               &lt;pattern value="[0-9]{4}"/>
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
        "agentIATACode",
        "agentIATACassAddress"
    })
    public static class CommissionableAgentDetails {

        @XmlElement(required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String agentIATACode;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String agentIATACassAddress;

        /**
         * Gets the value of the agentIATACode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAgentIATACode() {
            return agentIATACode;
        }

        /**
         * Sets the value of the agentIATACode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAgentIATACode(String value) {
            this.agentIATACode = value;
        }

        /**
         * Gets the value of the agentIATACassAddress property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAgentIATACassAddress() {
            return agentIATACassAddress;
        }

        /**
         * Sets the value of the agentIATACassAddress property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAgentIATACassAddress(String value) {
            this.agentIATACassAddress = value;
        }

    }

}
