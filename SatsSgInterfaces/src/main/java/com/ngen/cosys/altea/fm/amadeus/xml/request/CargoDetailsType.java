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
 * To identify the type of cargo, related instructions and the status of the cargo.
 * 
 * <p>Java class for CargoDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CargoDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="natureCargo" type="{http://xml.amadeus.com/FECFUQ_17_1_1A}AlphaNumericString_Length1To3"/>
 *         &lt;element name="cargoSubtype" type="{http://xml.amadeus.com/FECFUQ_17_1_1A}AlphaNumericString_Length1To5" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CargoDetailsType", propOrder = {
    "natureCargo",
    "cargoSubtype"
})
public class CargoDetailsType {

    @XmlElement(required = true)
    protected String natureCargo;
    protected String cargoSubtype;

    /**
     * Gets the value of the natureCargo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNatureCargo() {
        return natureCargo;
    }

    /**
     * Sets the value of the natureCargo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNatureCargo(String value) {
        this.natureCargo = value;
    }

    /**
     * Gets the value of the cargoSubtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCargoSubtype() {
        return cargoSubtype;
    }

    /**
     * Sets the value of the cargoSubtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCargoSubtype(String value) {
        this.cargoSubtype = value;
    }

}