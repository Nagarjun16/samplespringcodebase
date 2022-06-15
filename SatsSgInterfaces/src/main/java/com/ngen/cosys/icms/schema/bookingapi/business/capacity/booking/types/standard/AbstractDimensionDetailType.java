
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
 * <p>Java class for AbstractDimensionDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractDimensionDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dimensionSerialNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_5" minOccurs="0"/>
 *         &lt;element name="lengthPerPiece" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d7"/>
 *         &lt;element name="displayLengthPerPiece" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d7" minOccurs="0"/>
 *         &lt;element name="heightPerpiece" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d7"/>
 *         &lt;element name="displayHeightPerpiece" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d7" minOccurs="0"/>
 *         &lt;element name="widthPerPiece" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d7"/>
 *         &lt;element name="displayWidthPerPiece" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d7" minOccurs="0"/>
 *         &lt;element name="numberOfPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces"/>
 *         &lt;element name="volume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *         &lt;element name="displayVolume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *         &lt;element name="volumeThreeDecimal" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *         &lt;element name="weight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight"/>
 *         &lt;element name="displayWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *         &lt;element name="tiltable" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}yn" minOccurs="0"/>
 *         &lt;element name="stackable" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}yn" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractDimensionDetailType", propOrder = {
    "dimensionSerialNumber",
    "lengthPerPiece",
    "displayLengthPerPiece",
    "heightPerpiece",
    "displayHeightPerpiece",
    "widthPerPiece",
    "displayWidthPerPiece",
    "numberOfPieces",
    "volume",
    "displayVolume",
    "volumeThreeDecimal",
    "weight",
    "displayWeight",
    "tiltable",
    "stackable"
})
@XmlSeeAlso({
    DimensionDetailType.class
})
public class AbstractDimensionDetailType {

    protected BigInteger dimensionSerialNumber;
    @XmlElement(required = true)
    protected BigDecimal lengthPerPiece;
    protected BigDecimal displayLengthPerPiece;
    @XmlElement(required = true)
    protected BigDecimal heightPerpiece;
    protected BigDecimal displayHeightPerpiece;
    @XmlElement(required = true)
    protected BigDecimal widthPerPiece;
    protected BigDecimal displayWidthPerPiece;
    @XmlElement(required = true)
    protected BigInteger numberOfPieces;
    protected BigDecimal volume;
    protected BigDecimal displayVolume;
    protected BigDecimal volumeThreeDecimal;
    @XmlElement(required = true)
    protected BigDecimal weight;
    protected BigDecimal displayWeight;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String tiltable;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String stackable;

    /**
     * Gets the value of the dimensionSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDimensionSerialNumber() {
        return dimensionSerialNumber;
    }

    /**
     * Sets the value of the dimensionSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDimensionSerialNumber(BigInteger value) {
        this.dimensionSerialNumber = value;
    }

    /**
     * Gets the value of the lengthPerPiece property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLengthPerPiece() {
        return lengthPerPiece;
    }

    /**
     * Sets the value of the lengthPerPiece property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLengthPerPiece(BigDecimal value) {
        this.lengthPerPiece = value;
    }

    /**
     * Gets the value of the displayLengthPerPiece property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDisplayLengthPerPiece() {
        return displayLengthPerPiece;
    }

    /**
     * Sets the value of the displayLengthPerPiece property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDisplayLengthPerPiece(BigDecimal value) {
        this.displayLengthPerPiece = value;
    }

    /**
     * Gets the value of the heightPerpiece property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHeightPerpiece() {
        return heightPerpiece;
    }

    /**
     * Sets the value of the heightPerpiece property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHeightPerpiece(BigDecimal value) {
        this.heightPerpiece = value;
    }

    /**
     * Gets the value of the displayHeightPerpiece property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDisplayHeightPerpiece() {
        return displayHeightPerpiece;
    }

    /**
     * Sets the value of the displayHeightPerpiece property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDisplayHeightPerpiece(BigDecimal value) {
        this.displayHeightPerpiece = value;
    }

    /**
     * Gets the value of the widthPerPiece property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWidthPerPiece() {
        return widthPerPiece;
    }

    /**
     * Sets the value of the widthPerPiece property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWidthPerPiece(BigDecimal value) {
        this.widthPerPiece = value;
    }

    /**
     * Gets the value of the displayWidthPerPiece property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDisplayWidthPerPiece() {
        return displayWidthPerPiece;
    }

    /**
     * Sets the value of the displayWidthPerPiece property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDisplayWidthPerPiece(BigDecimal value) {
        this.displayWidthPerPiece = value;
    }

    /**
     * Gets the value of the numberOfPieces property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfPieces() {
        return numberOfPieces;
    }

    /**
     * Sets the value of the numberOfPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfPieces(BigInteger value) {
        this.numberOfPieces = value;
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
     * Gets the value of the tiltable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTiltable() {
        return tiltable;
    }

    /**
     * Sets the value of the tiltable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTiltable(String value) {
        this.tiltable = value;
    }

    /**
     * Gets the value of the stackable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStackable() {
        return stackable;
    }

    /**
     * Sets the value of the stackable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStackable(String value) {
        this.stackable = value;
    }

}
