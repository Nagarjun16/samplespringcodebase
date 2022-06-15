
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AbstractDGRNuclideDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractDGRNuclideDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nuclideName" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t100" minOccurs="0"/>
 *         &lt;element name="nuclideValue" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d16" minOccurs="0"/>
 *         &lt;element name="nuclideUnit" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t10" minOccurs="0"/>
 *         &lt;element name="serialNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5" minOccurs="0"/>
 *         &lt;element name="nuclideSerialNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractDGRNuclideDetailType", propOrder = {
    "nuclideName",
    "nuclideValue",
    "nuclideUnit",
    "serialNumber",
    "nuclideSerialNumber"
})
@XmlSeeAlso({
    DGRNuclideDetailType.class
})
public class AbstractDGRNuclideDetailType {

    protected String nuclideName;
    protected BigDecimal nuclideValue;
    protected String nuclideUnit;
    protected BigInteger serialNumber;
    protected BigInteger nuclideSerialNumber;

    /**
     * Gets the value of the nuclideName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNuclideName() {
        return nuclideName;
    }

    /**
     * Sets the value of the nuclideName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNuclideName(String value) {
        this.nuclideName = value;
    }

    /**
     * Gets the value of the nuclideValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNuclideValue() {
        return nuclideValue;
    }

    /**
     * Sets the value of the nuclideValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNuclideValue(BigDecimal value) {
        this.nuclideValue = value;
    }

    /**
     * Gets the value of the nuclideUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNuclideUnit() {
        return nuclideUnit;
    }

    /**
     * Sets the value of the nuclideUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNuclideUnit(String value) {
        this.nuclideUnit = value;
    }

    /**
     * Gets the value of the serialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the value of the serialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSerialNumber(BigInteger value) {
        this.serialNumber = value;
    }

    /**
     * Gets the value of the nuclideSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNuclideSerialNumber() {
        return nuclideSerialNumber;
    }

    /**
     * Sets the value of the nuclideSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNuclideSerialNumber(BigInteger value) {
        this.nuclideSerialNumber = value;
    }

}
