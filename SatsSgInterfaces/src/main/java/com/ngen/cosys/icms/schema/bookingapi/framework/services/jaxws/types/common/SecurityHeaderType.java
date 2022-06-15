
package com.ngen.cosys.icms.schema.bookingapi.framework.services.jaxws.types.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for securityHeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="securityHeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ic_sessionid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "securityHeaderType", propOrder = {
    "icSessionid"
})
public class SecurityHeaderType {

    @XmlElement(name = "ic_sessionid")
    protected String icSessionid;

    /**
     * Gets the value of the icSessionid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcSessionid() {
        return icSessionid;
    }

    /**
     * Sets the value of the icSessionid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcSessionid(String value) {
        this.icSessionid = value;
    }

}
