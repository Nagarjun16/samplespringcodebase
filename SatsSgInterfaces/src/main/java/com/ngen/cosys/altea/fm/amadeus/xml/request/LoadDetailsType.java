//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.07.06 at 07:22:06 PM SGT 
//


package com.ngen.cosys.altea.fm.amadeus.xml.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Specifications of the dimensions of a load.
 * 
 * <p>Java class for LoadDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoadDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fullEmptyIndicator" type="{http://xml.amadeus.com/FECFUQ_17_1_1A}AlphaNumericString_Length1To3"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoadDetailsType", propOrder = {
    "fullEmptyIndicator"
})
public class LoadDetailsType {

    @XmlElement(required = true)
    protected String fullEmptyIndicator;

    /**
     * Gets the value of the fullEmptyIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullEmptyIndicator() {
        return fullEmptyIndicator;
    }

    /**
     * Sets the value of the fullEmptyIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullEmptyIndicator(String value) {
        this.fullEmptyIndicator = value;
    }

}
