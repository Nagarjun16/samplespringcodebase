//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.07.06 at 07:22:06 PM SGT 
//


package com.ngen.cosys.altea.fm.amadeus.xml.request;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * To specify the details concerning the load to be transported.
 * 
 * <p>Java class for LoadDetailsInformationType_92764S complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoadDetailsInformationType_92764S">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cargoDetails" type="{http://xml.amadeus.com/FECFUQ_17_1_1A}CargoDetailsType"/>
 *         &lt;element name="priorityNumber" type="{http://xml.amadeus.com/FECFUQ_17_1_1A}NumericInteger_Length1To2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoadDetailsInformationType_92764S", propOrder = {
    "cargoDetails",
    "priorityNumber"
})
public class LoadDetailsInformationType92764S {

    @XmlElement(required = true)
    protected CargoDetailsType cargoDetails;
    protected BigInteger priorityNumber;

    /**
     * Gets the value of the cargoDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CargoDetailsType }
     *     
     */
    public CargoDetailsType getCargoDetails() {
        return cargoDetails;
    }

    /**
     * Sets the value of the cargoDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CargoDetailsType }
     *     
     */
    public void setCargoDetails(CargoDetailsType value) {
        this.cargoDetails = value;
    }

    /**
     * Gets the value of the priorityNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPriorityNumber() {
        return priorityNumber;
    }

    /**
     * Sets the value of the priorityNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPriorityNumber(BigInteger value) {
        this.priorityNumber = value;
    }

}