
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
 * 				The node containing the details of volume. Must be
 * 				included if no Dimensions, ULD number details is
 * 				included
 * 			
 * 
 * <p>Java class for VolumeDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VolumeDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="volume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal"/>
 *         &lt;element name="volumeCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}volumeUnit"/>
 *         &lt;element name="volumeThreeDecimal" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VolumeDetailsType", propOrder = {
    "volume",
    "volumeCode",
    "volumeThreeDecimal"
})
public class VolumeDetailsType {

    @XmlElement(required = true)
    protected BigDecimal volume;
    @XmlElement(required = true)
    @XmlSchemaType(name = "token")
    protected VolumeUnit volumeCode;
    protected BigDecimal volumeThreeDecimal;

    /**
     * Gets the value of the volume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVolume() {
        return volume;
    }

    /**
     * Sets the value of the volume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVolume(BigDecimal value) {
        this.volume = value;
    }

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
     * Gets the value of the volumeThreeDecimal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVolumeThreeDecimal() {
        return volumeThreeDecimal;
    }

    /**
     * Sets the value of the volumeThreeDecimal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVolumeThreeDecimal(BigDecimal value) {
        this.volumeThreeDecimal = value;
    }

}
