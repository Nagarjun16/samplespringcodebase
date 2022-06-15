
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				The node contains the contact information of the
 * 				customer. At-least one of the attribute is mandatory.
 * 			
 * 
 * <p>Java class for CustomerContactType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerContactType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customerPhoneNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *         &lt;element name="customerFaxNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *         &lt;element name="customerEMailId" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="70"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="customerTelexNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *         &lt;element name="customerMobilePhoneNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerContactType", propOrder = {
    "customerPhoneNumber",
    "customerFaxNumber",
    "customerEMailId",
    "customerTelexNumber",
    "customerMobilePhoneNumber"
})
public class CustomerContactType {

    protected String customerPhoneNumber;
    protected String customerFaxNumber;
    protected String customerEMailId;
    protected String customerTelexNumber;
    protected String customerMobilePhoneNumber;

    /**
     * Gets the value of the customerPhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    /**
     * Sets the value of the customerPhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerPhoneNumber(String value) {
        this.customerPhoneNumber = value;
    }

    /**
     * Gets the value of the customerFaxNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerFaxNumber() {
        return customerFaxNumber;
    }

    /**
     * Sets the value of the customerFaxNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerFaxNumber(String value) {
        this.customerFaxNumber = value;
    }

    /**
     * Gets the value of the customerEMailId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerEMailId() {
        return customerEMailId;
    }

    /**
     * Sets the value of the customerEMailId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerEMailId(String value) {
        this.customerEMailId = value;
    }

    /**
     * Gets the value of the customerTelexNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerTelexNumber() {
        return customerTelexNumber;
    }

    /**
     * Sets the value of the customerTelexNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerTelexNumber(String value) {
        this.customerTelexNumber = value;
    }

    /**
     * Gets the value of the customerMobilePhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerMobilePhoneNumber() {
        return customerMobilePhoneNumber;
    }

    /**
     * Sets the value of the customerMobilePhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerMobilePhoneNumber(String value) {
        this.customerMobilePhoneNumber = value;
    }

}
