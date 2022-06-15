
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for AbstractUldType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractUldType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serialNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_3" minOccurs="0"/>
 *         &lt;element name="uldSerialNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_3" minOccurs="0"/>
 *         &lt;element name="uldType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contour" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numberOfUlds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="uldWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *         &lt;element name="uldVolume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolume" minOccurs="0"/>
 *         &lt;element name="uldWeightDisplay" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *         &lt;element name="uldVolumeDisplay" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolume" minOccurs="0"/>
 *         &lt;element name="uldRemarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="commodityCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m1_10" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractUldType", namespace = "http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01", propOrder = {
    "serialNumber",
    "uldSerialNumber",
    "uldType",
    "contour",
    "numberOfUlds",
    "uldWeight",
    "uldVolume",
    "uldWeightDisplay",
    "uldVolumeDisplay",
    "uldRemarks",
    "commodityCode"
})
@XmlSeeAlso({
    UldInfoDetailsType.class
})
public class AbstractUldType {

    @XmlSchemaType(name = "unsignedByte")
    protected Short serialNumber;
    @XmlSchemaType(name = "unsignedByte")
    protected Short uldSerialNumber;
    @XmlElement(required = true)
    protected String uldType;
    protected String contour;
    protected int numberOfUlds;
    protected BigDecimal uldWeight;
    protected BigDecimal uldVolume;
    protected BigDecimal uldWeightDisplay;
    protected BigDecimal uldVolumeDisplay;
    protected String uldRemarks;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String commodityCode;

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
     * Gets the value of the uldSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getUldSerialNumber() {
        return uldSerialNumber;
    }

    /**
     * Sets the value of the uldSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setUldSerialNumber(Short value) {
        this.uldSerialNumber = value;
    }

    /**
     * Gets the value of the uldType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUldType() {
        return uldType;
    }

    /**
     * Sets the value of the uldType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUldType(String value) {
        this.uldType = value;
    }

    /**
     * Gets the value of the contour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContour() {
        return contour;
    }

    /**
     * Sets the value of the contour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContour(String value) {
        this.contour = value;
    }

    /**
     * Gets the value of the numberOfUlds property.
     * 
     */
    public int getNumberOfUlds() {
        return numberOfUlds;
    }

    /**
     * Sets the value of the numberOfUlds property.
     * 
     */
    public void setNumberOfUlds(int value) {
        this.numberOfUlds = value;
    }

    /**
     * Gets the value of the uldWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUldWeight() {
        return uldWeight;
    }

    /**
     * Sets the value of the uldWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUldWeight(BigDecimal value) {
        this.uldWeight = value;
    }

    /**
     * Gets the value of the uldVolume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUldVolume() {
        return uldVolume;
    }

    /**
     * Sets the value of the uldVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUldVolume(BigDecimal value) {
        this.uldVolume = value;
    }

    /**
     * Gets the value of the uldWeightDisplay property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUldWeightDisplay() {
        return uldWeightDisplay;
    }

    /**
     * Sets the value of the uldWeightDisplay property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUldWeightDisplay(BigDecimal value) {
        this.uldWeightDisplay = value;
    }

    /**
     * Gets the value of the uldVolumeDisplay property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUldVolumeDisplay() {
        return uldVolumeDisplay;
    }

    /**
     * Sets the value of the uldVolumeDisplay property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUldVolumeDisplay(BigDecimal value) {
        this.uldVolumeDisplay = value;
    }

    /**
     * Gets the value of the uldRemarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUldRemarks() {
        return uldRemarks;
    }

    /**
     * Sets the value of the uldRemarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUldRemarks(String value) {
        this.uldRemarks = value;
    }

    /**
     * Gets the value of the commodityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * Sets the value of the commodityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommodityCode(String value) {
        this.commodityCode = value;
    }

}
