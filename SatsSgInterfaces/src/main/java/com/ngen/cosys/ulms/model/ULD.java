//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.04.29 at 04:11:16 PM SGT 
//


package com.ngen.cosys.ulms.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ULD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ULD"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ULDNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ThroughTrasitFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AssociatedPD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ULD", propOrder = {
    "uldNumber",
    "throughTrasitFlag",
    "associatedPD",
    "status"
})
public class ULD {

    @XmlElement(name = "ULDNumber", required = true)
    protected String uldNumber;
    @XmlElement(name = "ThroughTrasitFlag")
    protected String throughTrasitFlag;
    @XmlElement(name = "AssociatedPD")
    protected String associatedPD;
    @XmlElement(name = "Status")
    protected String status;

    /**
     * Gets the value of the uldNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getULDNumber() {
        return uldNumber;
    }

    /**
     * Sets the value of the uldNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setULDNumber(String value) {
        this.uldNumber = value;
    }

    /**
     * Gets the value of the throughTrasitFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThroughTrasitFlag() {
        return throughTrasitFlag;
    }

    /**
     * Sets the value of the throughTrasitFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThroughTrasitFlag(String value) {
        this.throughTrasitFlag = value;
    }

    /**
     * Gets the value of the associatedPD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssociatedPD() {
        return associatedPD;
    }

    /**
     * Sets the value of the associatedPD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssociatedPD(String value) {
        this.associatedPD = value;
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

}
