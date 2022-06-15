
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.VolumeUnit;


/**
 * 
 * 				AWB Volume Details node contains the stated volume
 * 				information of the AWB.
 * 			
 * 
 * <p>Java class for AWBVolumeDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBVolumeDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="volumeCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}volumeUnit"/>
 *         &lt;element name="volumeAmount" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal"/>
 *         &lt;element name="volumeAmountThreeDecimal" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AWBVolumeDetailsType", propOrder = {
    "volumeCode",
    "volumeAmount",
    "volumeAmountThreeDecimal"
})
public class AWBVolumeDetailsType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "token")
    protected VolumeUnit volumeCode;
    @XmlElement(required = true)
    protected BigDecimal volumeAmount;
    protected BigDecimal volumeAmountThreeDecimal;

    /**
     * Gets the value of the volumeCode property.
     * 
     * @return
     *     possible object is
     *     {@link VolumeUnit }
     *     
     */
    public VolumeUnit getVolumeCode() {
        return volumeCode;
    }

    /**
     * Sets the value of the volumeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link VolumeUnit }
     *     
     */
    public void setVolumeCode(VolumeUnit value) {
        this.volumeCode = value;
    }

    /**
     * Gets the value of the volumeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVolumeAmount() {
        return volumeAmount;
    }

    /**
     * Sets the value of the volumeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVolumeAmount(BigDecimal value) {
        this.volumeAmount = value;
    }

    /**
     * Gets the value of the volumeAmountThreeDecimal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVolumeAmountThreeDecimal() {
        return volumeAmountThreeDecimal;
    }

    /**
     * Sets the value of the volumeAmountThreeDecimal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVolumeAmountThreeDecimal(BigDecimal value) {
        this.volumeAmountThreeDecimal = value;
    }

}
