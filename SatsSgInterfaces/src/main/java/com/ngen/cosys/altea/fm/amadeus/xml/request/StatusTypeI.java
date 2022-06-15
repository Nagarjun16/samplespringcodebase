//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.07.06 at 07:22:06 PM SGT 
//


package com.ngen.cosys.altea.fm.amadeus.xml.request;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * To identify a status and related information.
 * 
 * <p>Java class for StatusTypeI complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StatusTypeI">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="statusDetails" type="{http://xml.amadeus.com/FECFUQ_17_1_1A}StatusDetailsTypeI_128105C"/>
 *         &lt;element name="otherStatusDetails" type="{http://xml.amadeus.com/FECFUQ_17_1_1A}StatusDetailsTypeI_171610C" maxOccurs="2"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatusTypeI", propOrder = {
    "statusDetails",
    "otherStatusDetails"
})
public class StatusTypeI {

    @XmlElement(required = true)
    protected StatusDetailsTypeI128105C statusDetails;
    @XmlElement(required = true)
    protected List<StatusDetailsTypeI171610C> otherStatusDetails;

    /**
     * Gets the value of the statusDetails property.
     * 
     * @return
     *     possible object is
     *     {@link StatusDetailsTypeI128105C }
     *     
     */
    public StatusDetailsTypeI128105C getStatusDetails() {
        return statusDetails;
    }

    /**
     * Sets the value of the statusDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusDetailsTypeI128105C }
     *     
     */
    public void setStatusDetails(StatusDetailsTypeI128105C value) {
        this.statusDetails = value;
    }

    /**
     * Gets the value of the otherStatusDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otherStatusDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtherStatusDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StatusDetailsTypeI171610C }
     * 
     * 
     */
    public List<StatusDetailsTypeI171610C> getOtherStatusDetails() {
        if (otherStatusDetails == null) {
            otherStatusDetails = new ArrayList<StatusDetailsTypeI171610C>();
        }
        return this.otherStatusDetails;
    }

}
