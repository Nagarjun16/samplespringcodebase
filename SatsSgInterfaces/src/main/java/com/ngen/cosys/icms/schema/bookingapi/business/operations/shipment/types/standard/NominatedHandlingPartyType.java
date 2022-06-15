
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				The node containing the Nominated Handling Party details
 * 			
 * 
 * <p>Java class for NominatedHandlingPartyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NominatedHandlingPartyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nominatedHandlingPartyName" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t70"/>
 *         &lt;element name="nominatedHandlingPartyPlace">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="70"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NominatedHandlingPartyType", propOrder = {
    "nominatedHandlingPartyName",
    "nominatedHandlingPartyPlace"
})
public class NominatedHandlingPartyType {

    @XmlElement(required = true)
    protected String nominatedHandlingPartyName;
    @XmlElement(required = true)
    protected String nominatedHandlingPartyPlace;

    /**
     * Gets the value of the nominatedHandlingPartyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNominatedHandlingPartyName() {
        return nominatedHandlingPartyName;
    }

    /**
     * Sets the value of the nominatedHandlingPartyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNominatedHandlingPartyName(String value) {
        this.nominatedHandlingPartyName = value;
    }

    /**
     * Gets the value of the nominatedHandlingPartyPlace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNominatedHandlingPartyPlace() {
        return nominatedHandlingPartyPlace;
    }

    /**
     * Sets the value of the nominatedHandlingPartyPlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNominatedHandlingPartyPlace(String value) {
        this.nominatedHandlingPartyPlace = value;
    }

}
