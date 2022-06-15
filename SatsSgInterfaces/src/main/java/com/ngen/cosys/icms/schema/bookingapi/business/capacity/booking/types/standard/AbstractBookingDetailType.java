
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for AbstractBookingDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractBookingDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serialNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_3" minOccurs="0"/>
 *         &lt;element name="commodityCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m1_10"/>
 *         &lt;element name="iataCommodityCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m1_15" minOccurs="0"/>
 *         &lt;element name="pieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces"/>
 *         &lt;element name="weight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight"/>
 *         &lt;element name="displayWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *         &lt;element name="volume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *         &lt;element name="displayVolume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *         &lt;element name="grossVolume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *         &lt;element name="displayGrossVolume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *         &lt;element name="volumeThreeDecimal" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *         &lt;element name="shipmentDescription" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m1_500_RestrictSpecialCh"/>
 *         &lt;element name="uldType" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ULDType" minOccurs="0"/>
 *         &lt;element name="numberOfUlds" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_5" minOccurs="0"/>
 *         &lt;element name="sccCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}sccCode" minOccurs="0"/>
 *         &lt;element name="rateContractReference" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m1_20" minOccurs="0"/>
 *         &lt;element name="chargeableWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *         &lt;element name="displayChargableWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *         &lt;element name="fromTemperature" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_4Neg" minOccurs="0"/>
 *         &lt;element name="toTemperature" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_4Neg" minOccurs="0"/>
 *         &lt;element name="temperatureRange" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t20" minOccurs="0"/>
 *         &lt;element name="adjustedWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractBookingDetailType", propOrder = {
    "serialNumber",
    "commodityCode",
    "iataCommodityCode",
    "pieces",
    "weight",
    "displayWeight",
    "volume",
    "displayVolume",
    "grossVolume",
    "displayGrossVolume",
    "volumeThreeDecimal",
    "shipmentDescription",
    "uldType",
    "numberOfUlds",
    "sccCode",
    "rateContractReference",
    "chargeableWeight",
    "displayChargableWeight",
    "fromTemperature",
    "toTemperature",
    "temperatureRange",
    "adjustedWeight"
})
@XmlSeeAlso({
    BookingDetailType.class
})
public class AbstractBookingDetailType {

    @XmlSchemaType(name = "unsignedByte")
    protected Short serialNumber;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String commodityCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String iataCommodityCode;
    @XmlElement(required = true)
    protected BigInteger pieces;
    @XmlElement(required = true)
    protected BigDecimal weight;
    protected BigDecimal displayWeight;
    protected BigDecimal volume;
    protected BigDecimal displayVolume;
    protected BigDecimal grossVolume;
    protected BigDecimal displayGrossVolume;
    protected BigDecimal volumeThreeDecimal;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String shipmentDescription;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String uldType;
    protected BigInteger numberOfUlds;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String sccCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String rateContractReference;
    protected BigDecimal chargeableWeight;
    protected BigDecimal displayChargableWeight;
    protected BigInteger fromTemperature;
    protected BigInteger toTemperature;
    protected String temperatureRange;
    protected BigDecimal adjustedWeight;

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

    /**
     * Gets the value of the iataCommodityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIataCommodityCode() {
        return iataCommodityCode;
    }

    /**
     * Sets the value of the iataCommodityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIataCommodityCode(String value) {
        this.iataCommodityCode = value;
    }

    /**
     * Gets the value of the pieces property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPieces() {
        return pieces;
    }

    /**
     * Sets the value of the pieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPieces(BigInteger value) {
        this.pieces = value;
    }

    /**
     * Gets the value of the weight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWeight(BigDecimal value) {
        this.weight = value;
    }

    /**
     * Gets the value of the displayWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDisplayWeight() {
        return displayWeight;
    }

    /**
     * Sets the value of the displayWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDisplayWeight(BigDecimal value) {
        this.displayWeight = value;
    }

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
     * Gets the value of the displayVolume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDisplayVolume() {
        return displayVolume;
    }

    /**
     * Sets the value of the displayVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDisplayVolume(BigDecimal value) {
        this.displayVolume = value;
    }

    /**
     * Gets the value of the grossVolume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGrossVolume() {
        return grossVolume;
    }

    /**
     * Sets the value of the grossVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGrossVolume(BigDecimal value) {
        this.grossVolume = value;
    }

    /**
     * Gets the value of the displayGrossVolume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDisplayGrossVolume() {
        return displayGrossVolume;
    }

    /**
     * Sets the value of the displayGrossVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDisplayGrossVolume(BigDecimal value) {
        this.displayGrossVolume = value;
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

    /**
     * Gets the value of the shipmentDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipmentDescription() {
        return shipmentDescription;
    }

    /**
     * Sets the value of the shipmentDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipmentDescription(String value) {
        this.shipmentDescription = value;
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
     * Gets the value of the numberOfUlds property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfUlds() {
        return numberOfUlds;
    }

    /**
     * Sets the value of the numberOfUlds property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfUlds(BigInteger value) {
        this.numberOfUlds = value;
    }

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
     * Gets the value of the rateContractReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateContractReference() {
        return rateContractReference;
    }

    /**
     * Sets the value of the rateContractReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateContractReference(String value) {
        this.rateContractReference = value;
    }

    /**
     * Gets the value of the chargeableWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getChargeableWeight() {
        return chargeableWeight;
    }

    /**
     * Sets the value of the chargeableWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setChargeableWeight(BigDecimal value) {
        this.chargeableWeight = value;
    }

    /**
     * Gets the value of the displayChargableWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDisplayChargableWeight() {
        return displayChargableWeight;
    }

    /**
     * Sets the value of the displayChargableWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDisplayChargableWeight(BigDecimal value) {
        this.displayChargableWeight = value;
    }

    /**
     * Gets the value of the fromTemperature property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFromTemperature() {
        return fromTemperature;
    }

    /**
     * Sets the value of the fromTemperature property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFromTemperature(BigInteger value) {
        this.fromTemperature = value;
    }

    /**
     * Gets the value of the toTemperature property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getToTemperature() {
        return toTemperature;
    }

    /**
     * Sets the value of the toTemperature property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setToTemperature(BigInteger value) {
        this.toTemperature = value;
    }

    /**
     * Gets the value of the temperatureRange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemperatureRange() {
        return temperatureRange;
    }

    /**
     * Sets the value of the temperatureRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemperatureRange(String value) {
        this.temperatureRange = value;
    }

    /**
     * Gets the value of the adjustedWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAdjustedWeight() {
        return adjustedWeight;
    }

    /**
     * Sets the value of the adjustedWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAdjustedWeight(BigDecimal value) {
        this.adjustedWeight = value;
    }

}
