
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for PrepaidCollectChargeDeclarationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PrepaidCollectChargeDeclarationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="prepaidCollectIndicatorWgtValChgs" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="P"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="prepaidCollectIndicatorOthChgs" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="P"/>
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
@XmlType(name = "PrepaidCollectChargeDeclarationType", propOrder = {
    "prepaidCollectIndicatorWgtValChgs",
    "prepaidCollectIndicatorOthChgs"
})
public class PrepaidCollectChargeDeclarationType {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String prepaidCollectIndicatorWgtValChgs;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String prepaidCollectIndicatorOthChgs;

    /**
     * Gets the value of the prepaidCollectIndicatorWgtValChgs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrepaidCollectIndicatorWgtValChgs() {
        return prepaidCollectIndicatorWgtValChgs;
    }

    /**
     * Sets the value of the prepaidCollectIndicatorWgtValChgs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrepaidCollectIndicatorWgtValChgs(String value) {
        this.prepaidCollectIndicatorWgtValChgs = value;
    }

    /**
     * Gets the value of the prepaidCollectIndicatorOthChgs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrepaidCollectIndicatorOthChgs() {
        return prepaidCollectIndicatorOthChgs;
    }

    /**
     * Sets the value of the prepaidCollectIndicatorOthChgs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrepaidCollectIndicatorOthChgs(String value) {
        this.prepaidCollectIndicatorOthChgs = value;
    }

}
