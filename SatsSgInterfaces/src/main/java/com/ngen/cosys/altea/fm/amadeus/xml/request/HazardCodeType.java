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
 * The identification of the dangerous goods in code.
 * 
 * <p>Java class for HazardCodeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HazardCodeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sldgIATACode" type="{http://xml.amadeus.com/FECFUQ_17_1_1A}AlphaNumericString_Length1To3"/>
 *         &lt;element name="radioactiveCategory" type="{http://xml.amadeus.com/FECFUQ_17_1_1A}AlphaNumericString_Length1To3" minOccurs="0"/>
 *         &lt;element name="subsidiaryRiskGrp" type="{http://xml.amadeus.com/FECFUQ_17_1_1A}AlphaNumericString_Length1To3" maxOccurs="3" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HazardCodeType", propOrder = {
    "sldgIATACode",
    "radioactiveCategory",
    "subsidiaryRiskGrp"
})
public class HazardCodeType {

    @XmlElement(required = true)
    protected String sldgIATACode;
    protected String radioactiveCategory;
    protected List<String> subsidiaryRiskGrp;

    /**
     * Gets the value of the sldgIATACode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSldgIATACode() {
        return sldgIATACode;
    }

    /**
     * Sets the value of the sldgIATACode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSldgIATACode(String value) {
        this.sldgIATACode = value;
    }

    /**
     * Gets the value of the radioactiveCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRadioactiveCategory() {
        return radioactiveCategory;
    }

    /**
     * Sets the value of the radioactiveCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRadioactiveCategory(String value) {
        this.radioactiveCategory = value;
    }

    /**
     * Gets the value of the subsidiaryRiskGrp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subsidiaryRiskGrp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubsidiaryRiskGrp().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSubsidiaryRiskGrp() {
        if (subsidiaryRiskGrp == null) {
            subsidiaryRiskGrp = new ArrayList<String>();
        }
        return this.subsidiaryRiskGrp;
    }

}