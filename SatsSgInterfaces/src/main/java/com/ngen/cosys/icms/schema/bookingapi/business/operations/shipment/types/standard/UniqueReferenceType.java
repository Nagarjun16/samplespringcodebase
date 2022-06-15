
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UniqueReferenceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UniqueReferenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uniqueReferenceOne" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uniqueReferenceTwo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uniqueReferenceThree" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uniqueReferenceFour" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uniqueReferenceFive" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UniqueReferenceType", propOrder = {
    "uniqueReferenceOne",
    "uniqueReferenceTwo",
    "uniqueReferenceThree",
    "uniqueReferenceFour",
    "uniqueReferenceFive"
})
public class UniqueReferenceType {

    protected String uniqueReferenceOne;
    protected String uniqueReferenceTwo;
    protected String uniqueReferenceThree;
    protected String uniqueReferenceFour;
    protected String uniqueReferenceFive;

    /**
     * Gets the value of the uniqueReferenceOne property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueReferenceOne() {
        return uniqueReferenceOne;
    }

    /**
     * Sets the value of the uniqueReferenceOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueReferenceOne(String value) {
        this.uniqueReferenceOne = value;
    }

    /**
     * Gets the value of the uniqueReferenceTwo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueReferenceTwo() {
        return uniqueReferenceTwo;
    }

    /**
     * Sets the value of the uniqueReferenceTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueReferenceTwo(String value) {
        this.uniqueReferenceTwo = value;
    }

    /**
     * Gets the value of the uniqueReferenceThree property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueReferenceThree() {
        return uniqueReferenceThree;
    }

    /**
     * Sets the value of the uniqueReferenceThree property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueReferenceThree(String value) {
        this.uniqueReferenceThree = value;
    }

    /**
     * Gets the value of the uniqueReferenceFour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueReferenceFour() {
        return uniqueReferenceFour;
    }

    /**
     * Sets the value of the uniqueReferenceFour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueReferenceFour(String value) {
        this.uniqueReferenceFour = value;
    }

    /**
     * Gets the value of the uniqueReferenceFive property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueReferenceFive() {
        return uniqueReferenceFive;
    }

    /**
     * Sets the value of the uniqueReferenceFive property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueReferenceFive(String value) {
        this.uniqueReferenceFive = value;
    }

}
