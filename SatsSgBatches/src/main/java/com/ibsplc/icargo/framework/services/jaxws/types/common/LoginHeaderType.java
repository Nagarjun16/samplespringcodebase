//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.20 at 10:45:55 AM IST 
//


package com.ibsplc.icargo.framework.services.jaxws.types.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for loginHeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="loginHeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ic_HHTLogin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "loginHeaderType", propOrder = {
    "icHHTLogin"
})
public class LoginHeaderType {

    @XmlElement(name = "ic_HHTLogin", namespace = "")
    protected String icHHTLogin;

    /**
     * Gets the value of the icHHTLogin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcHHTLogin() {
        return icHHTLogin;
    }

    /**
     * Sets the value of the icHHTLogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcHHTLogin(String value) {
        this.icHHTLogin = value;
    }

}