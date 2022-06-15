
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				The node contains the contact information of the agent.
 * 				At-least one of the attribute is mandatory.
 * 			
 * 
 * <p>Java class for AgentContactType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AgentContactType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="agentPhoneNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *         &lt;element name="agentFaxNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *         &lt;element name="agentEMailId" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="70"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="agentTelexNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *         &lt;element name="agentMobilePhoneNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AgentContactType", propOrder = {
    "agentPhoneNumber",
    "agentFaxNumber",
    "agentEMailId",
    "agentTelexNumber",
    "agentMobilePhoneNumber"
})
public class AgentContactType {

    protected String agentPhoneNumber;
    protected String agentFaxNumber;
    protected String agentEMailId;
    protected String agentTelexNumber;
    protected String agentMobilePhoneNumber;

    /**
     * Gets the value of the agentPhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentPhoneNumber() {
        return agentPhoneNumber;
    }

    /**
     * Sets the value of the agentPhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentPhoneNumber(String value) {
        this.agentPhoneNumber = value;
    }

    /**
     * Gets the value of the agentFaxNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentFaxNumber() {
        return agentFaxNumber;
    }

    /**
     * Sets the value of the agentFaxNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentFaxNumber(String value) {
        this.agentFaxNumber = value;
    }

    /**
     * Gets the value of the agentEMailId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentEMailId() {
        return agentEMailId;
    }

    /**
     * Sets the value of the agentEMailId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentEMailId(String value) {
        this.agentEMailId = value;
    }

    /**
     * Gets the value of the agentTelexNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentTelexNumber() {
        return agentTelexNumber;
    }

    /**
     * Sets the value of the agentTelexNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentTelexNumber(String value) {
        this.agentTelexNumber = value;
    }

    /**
     * Gets the value of the agentMobilePhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentMobilePhoneNumber() {
        return agentMobilePhoneNumber;
    }

    /**
     * Sets the value of the agentMobilePhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentMobilePhoneNumber(String value) {
        this.agentMobilePhoneNumber = value;
    }

}
