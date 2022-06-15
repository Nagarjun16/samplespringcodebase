
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AbstractComplementarySCCType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractComplementarySCCType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sccCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serialNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_3" minOccurs="0"/>
 *         &lt;element name="sccSerialNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_3" minOccurs="0"/>
 *         &lt;element name="sccWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractComplementarySCCType", propOrder = {
    "sccCode",
    "serialNumber",
    "sccSerialNumber",
    "sccWeight"
})
@XmlSeeAlso({
    ComplementarySCCType.class
})
public class AbstractComplementarySCCType {

    @XmlElement(required = true)
    protected String sccCode;
    @XmlSchemaType(name = "unsignedByte")
    protected Short serialNumber;
    @XmlSchemaType(name = "unsignedByte")
    protected Short sccSerialNumber;
    protected BigDecimal sccWeight;

    /**
     * Gets the value of the sccCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSccCode() {
        return sccCode;
    }

    /**
     * Sets the value of the sccCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSccCode(String value) {
        this.sccCode = value;
    }

    /**
     * Gets the value of the serialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the value of the serialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setSerialNumber(Short value) {
        this.serialNumber = value;
    }

    /**
     * Gets the value of the sccSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getSccSerialNumber() {
        return sccSerialNumber;
    }

    /**
     * Sets the value of the sccSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setSccSerialNumber(Short value) {
        this.sccSerialNumber = value;
    }

    /**
     * Gets the value of the sccWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSccWeight() {
        return sccWeight;
    }

    /**
     * Sets the value of the sccWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSccWeight(BigDecimal value) {
        this.sccWeight = value;
    }

}
