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
 * Specify information relating to the vehicle type.
 * 
 * <p>Java class for EquipmentInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EquipmentInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="equipmentDetails" type="{http://xml.amadeus.com/FECFUQ_17_1_1A}EquipmentIdentificationType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EquipmentInformationType", propOrder = {
    "equipmentDetails"
})
public class EquipmentInformationType {

    @XmlElement(required = true)
    protected EquipmentIdentificationType equipmentDetails;

    /**
     * Gets the value of the equipmentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link EquipmentIdentificationType }
     *     
     */
    public EquipmentIdentificationType getEquipmentDetails() {
        return equipmentDetails;
    }

    /**
     * Sets the value of the equipmentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link EquipmentIdentificationType }
     *     
     */
    public void setEquipmentDetails(EquipmentIdentificationType value) {
        this.equipmentDetails = value;
    }

}
