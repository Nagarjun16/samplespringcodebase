
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.OperationalFlagType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.VolumeUnit;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.WeightUnit;


/**
 * <p>Java class for DimensionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DimensionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="unitOfLength">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="CMT"/>
 *               &lt;enumeration value="FOT"/>
 *               &lt;enumeration value="INH"/>
 *               &lt;enumeration value="MTR"/>
 *               &lt;enumeration value="YRD"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="lengthPerPiece" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}dimensionMeasureType"/>
 *         &lt;element name="widthPerPiece" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}dimensionMeasureType"/>
 *         &lt;element name="heightPerpiece" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}dimensionMeasureType"/>
 *         &lt;element name="numberOfPieces">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="99999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="unitOfWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}weightUnit" minOccurs="0"/>
 *         &lt;element name="weight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *         &lt;element name="weightThreeDecimal" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeightThreeDecimal" minOccurs="0"/>
 *         &lt;element name="tiltableFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unitOfVolume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}volumeUnit" minOccurs="0"/>
 *         &lt;element name="volume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *         &lt;element name="pieceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dimensionSerialNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_2" minOccurs="0"/>
 *         &lt;element name="operationalFlag" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}operationalFlagType" minOccurs="0"/>
 *         &lt;element name="volumeThreeDecimal" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolumeThreeDecimal" minOccurs="0"/>
 *         &lt;element name="storageUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DimensionType", propOrder = {
    "unitOfLength",
    "lengthPerPiece",
    "widthPerPiece",
    "heightPerpiece",
    "numberOfPieces",
    "unitOfWeight",
    "weight",
    "weightThreeDecimal",
    "tiltableFlag",
    "unitOfVolume",
    "volume",
    "pieceId",
    "dimensionSerialNumber",
    "operationalFlag",
    "volumeThreeDecimal",
    "storageUnit"
})
public class DimensionType {

    @XmlElement(required = true)
    protected String unitOfLength;
    @XmlElement(required = true)
    protected BigDecimal lengthPerPiece;
    @XmlElement(required = true)
    protected BigDecimal widthPerPiece;
    @XmlElement(required = true)
    protected BigDecimal heightPerpiece;
    protected int numberOfPieces;
    @XmlSchemaType(name = "token")
    protected WeightUnit unitOfWeight;
    protected BigDecimal weight;
    protected BigDecimal weightThreeDecimal;
    protected String tiltableFlag;
    @XmlSchemaType(name = "token")
    protected VolumeUnit unitOfVolume;
    protected BigDecimal volume;
    protected String pieceId;
    protected BigInteger dimensionSerialNumber;
    @XmlSchemaType(name = "string")
    protected OperationalFlagType operationalFlag;
    protected BigDecimal volumeThreeDecimal;
    protected String storageUnit;

    /**
     * Gets the value of the unitOfLength property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitOfLength() {
        return unitOfLength;
    }

    /**
     * Sets the value of the unitOfLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitOfLength(String value) {
        this.unitOfLength = value;
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
     * Gets the value of the numberOfPieces property.
     * 
     */
    public int getNumberOfPieces() {
        return numberOfPieces;
    }

    /**
     * Sets the value of the numberOfPieces property.
     * 
     */
    public void setNumberOfPieces(int value) {
        this.numberOfPieces = value;
    }

    /**
     * Gets the value of the unitOfWeight property.
     * 
     * @return
     *     possible object is
     *     {@link WeightUnit }
     *     
     */
    public WeightUnit getUnitOfWeight() {
        return unitOfWeight;
    }

    /**
     * Sets the value of the unitOfWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link WeightUnit }
     *     
     */
    public void setUnitOfWeight(WeightUnit value) {
        this.unitOfWeight = value;
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
     * Gets the value of the weightThreeDecimal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWeightThreeDecimal() {
        return weightThreeDecimal;
    }

    /**
     * Sets the value of the weightThreeDecimal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWeightThreeDecimal(BigDecimal value) {
        this.weightThreeDecimal = value;
    }

    /**
     * Gets the value of the tiltableFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTiltableFlag() {
        return tiltableFlag;
    }

    /**
     * Sets the value of the tiltableFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTiltableFlag(String value) {
        this.tiltableFlag = value;
    }

    /**
     * Gets the value of the unitOfVolume property.
     * 
     * @return
     *     possible object is
     *     {@link VolumeUnit }
     *     
     */
    public VolumeUnit getUnitOfVolume() {
        return unitOfVolume;
    }

    /**
     * Sets the value of the unitOfVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link VolumeUnit }
     *     
     */
    public void setUnitOfVolume(VolumeUnit value) {
        this.unitOfVolume = value;
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
     * Gets the value of the pieceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPieceId() {
        return pieceId;
    }

    /**
     * Sets the value of the pieceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPieceId(String value) {
        this.pieceId = value;
    }

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
     * Gets the value of the operationalFlag property.
     * 
     * @return
     *     possible object is
     *     {@link OperationalFlagType }
     *     
     */
    public OperationalFlagType getOperationalFlag() {
        return operationalFlag;
    }

    /**
     * Sets the value of the operationalFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationalFlagType }
     *     
     */
    public void setOperationalFlag(OperationalFlagType value) {
        this.operationalFlag = value;
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
     * Gets the value of the storageUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStorageUnit() {
        return storageUnit;
    }

    /**
     * Sets the value of the storageUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStorageUnit(String value) {
        this.storageUnit = value;
    }

}
