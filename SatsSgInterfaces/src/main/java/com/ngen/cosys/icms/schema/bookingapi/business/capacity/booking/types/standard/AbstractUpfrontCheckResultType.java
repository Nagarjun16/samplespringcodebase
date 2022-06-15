
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AbstractUpfrontCheckResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractUpfrontCheckResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="checkType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="R"/>
 *               &lt;enumeration value="L"/>
 *               &lt;enumeration value="X"/>
 *               &lt;enumeration value="E"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="checkResult">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="Q"/>
 *               &lt;enumeration value="E"/>
 *               &lt;enumeration value="W"/>
 *               &lt;enumeration value="I"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="additionalCheckDetails" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t500" minOccurs="0"/>
 *         &lt;element name="checkDescription" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t500" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractUpfrontCheckResultType", namespace = "http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01", propOrder = {
    "checkType",
    "checkResult",
    "additionalCheckDetails",
    "checkDescription"
})
@XmlSeeAlso({
    UpFrontCheckResultType.class
})
public class AbstractUpfrontCheckResultType {

    @XmlElement(required = true)
    protected String checkType;
    @XmlElement(required = true)
    protected String checkResult;
    protected String additionalCheckDetails;
    protected String checkDescription;

    /**
     * Gets the value of the checkType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * Sets the value of the checkType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckType(String value) {
        this.checkType = value;
    }

    /**
     * Gets the value of the checkResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckResult() {
        return checkResult;
    }

    /**
     * Sets the value of the checkResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckResult(String value) {
        this.checkResult = value;
    }

    /**
     * Gets the value of the additionalCheckDetails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalCheckDetails() {
        return additionalCheckDetails;
    }

    /**
     * Sets the value of the additionalCheckDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalCheckDetails(String value) {
        this.additionalCheckDetails = value;
    }

    /**
     * Gets the value of the checkDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckDescription() {
        return checkDescription;
    }

    /**
     * Sets the value of the checkDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckDescription(String value) {
        this.checkDescription = value;
    }

}
