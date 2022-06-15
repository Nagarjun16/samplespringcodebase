
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.WeightUnit;


/**
 * 
 * 				The node containing the details of a single rateline for
 * 				the AWB
 * 			
 * 
 * <p>Java class for AWBRateLineType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBRateLineType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lineItemNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_2"/>
 *         &lt;element name="itemDescription" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t250" minOccurs="0"/>
 *         &lt;element name="numberofPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces" minOccurs="0"/>
 *         &lt;element name="rateCombinationPoint" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;pattern value="[a-zA-Z]{3}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="weightUnitOfMeasurement" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}weightUnit" minOccurs="0"/>
 *         &lt;element name="grossWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *         &lt;element name="grossWeightThreeDecimal" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeightThreeDecimal" minOccurs="0"/>
 *         &lt;element name="rateClassCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="B"/>
 *               &lt;enumeration value="R"/>
 *               &lt;enumeration value="S"/>
 *               &lt;enumeration value="P"/>
 *               &lt;enumeration value="M"/>
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="Q"/>
 *               &lt;enumeration value="K"/>
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="X"/>
 *               &lt;enumeration value="E"/>
 *               &lt;enumeration value="U"/>
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="W"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="rateClassCodeBasis" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[a-zA-Z]{1}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="classRatingPercentage" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;pattern value="[0-9]{0,3}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="commodityItemNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[0-9]{4,7}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="uldRateClassType" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}uldRateClassType" minOccurs="0"/>
 *         &lt;element name="chargeableWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *         &lt;element name="chargeableWeightThreeDecimal" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeightThreeDecimal" minOccurs="0"/>
 *         &lt;element name="rateOrChargeOrDiscountAmount" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;maxInclusive value="99999999"/>
 *               &lt;minInclusive value="0"/>
 *               &lt;fractionDigits value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ItemTotalCharge" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="dimensionsDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="dimension" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}DimensionType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="VolumeDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}VolumeDetailsType" minOccurs="0"/>
 *         &lt;element name="uldDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ulds" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ULDDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="HarmonizedCommodityCodes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="HarmonizedCommodityCode" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}HarmonizedCommodityCode" maxOccurs="9" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="CountryofOriginofGoods" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[a-zA-Z]{2}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="pivotRate" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="discountPercentage" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;fractionDigits value="2"/>
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="overFlowIndicator" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="Y"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="serviceCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="A"/>
 *               &lt;enumeration value="E"/>
 *               &lt;enumeration value="T"/>
 *               &lt;enumeration value="H"/>
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="I"/>
 *               &lt;enumeration value="G"/>
 *               &lt;enumeration value="D"/>
 *               &lt;enumeration value="X"/>
 *               &lt;enumeration value="F"/>
 *               &lt;enumeration value="J"/>
 *               &lt;enumeration value="B"/>
 *               &lt;enumeration value="P"/>
 *               &lt;enumeration value="S"/>
 *               &lt;enumeration value="V"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="commodityCode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[a-zA-Z0-9]{1,10}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="rateContractReference" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="16"/>
 *               &lt;minLength value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="volumetricWeight" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;maxInclusive value="9999999"/>
 *               &lt;minInclusive value="0.1"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="airlineTariffName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="airlineTariffSerialNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="airlineTariffRateSerialNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="airlineRateLineId" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="9999999999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="airlineRatingType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AWBRateLineType", propOrder = {
    "lineItemNumber",
    "itemDescription",
    "numberofPieces",
    "rateCombinationPoint",
    "weightUnitOfMeasurement",
    "grossWeight",
    "grossWeightThreeDecimal",
    "rateClassCode",
    "rateClassCodeBasis",
    "classRatingPercentage",
    "commodityItemNumber",
    "uldRateClassType",
    "chargeableWeight",
    "chargeableWeightThreeDecimal",
    "rateOrChargeOrDiscountAmount",
    "itemTotalCharge",
    "dimensionsDetails",
    "volumeDetails",
    "uldDetails",
    "harmonizedCommodityCodes",
    "countryofOriginofGoods",
    "pivotRate",
    "discountPercentage",
    "overFlowIndicator",
    "serviceCode",
    "commodityCode",
    "rateContractReference",
    "volumetricWeight",
    "airlineTariffName",
    "airlineTariffSerialNumber",
    "airlineTariffRateSerialNumber",
    "airlineRateLineId",
    "airlineRatingType"
})
public class AWBRateLineType {

    @XmlElement(required = true)
    protected BigInteger lineItemNumber;
    protected String itemDescription;
    protected BigInteger numberofPieces;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String rateCombinationPoint;
    @XmlSchemaType(name = "token")
    protected WeightUnit weightUnitOfMeasurement;
    protected BigDecimal grossWeight;
    protected BigDecimal grossWeightThreeDecimal;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String rateClassCode;
    protected String rateClassCodeBasis;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String classRatingPercentage;
    protected String commodityItemNumber;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String uldRateClassType;
    protected BigDecimal chargeableWeight;
    protected BigDecimal chargeableWeightThreeDecimal;
    protected BigDecimal rateOrChargeOrDiscountAmount;
    @XmlElement(name = "ItemTotalCharge")
    protected BigDecimal itemTotalCharge;
    protected AWBRateLineType.DimensionsDetails dimensionsDetails;
    @XmlElement(name = "VolumeDetails")
    protected VolumeDetailsType volumeDetails;
    protected AWBRateLineType.UldDetails uldDetails;
    @XmlElement(name = "HarmonizedCommodityCodes")
    protected AWBRateLineType.HarmonizedCommodityCodes harmonizedCommodityCodes;
    @XmlElement(name = "CountryofOriginofGoods")
    protected String countryofOriginofGoods;
    protected BigDecimal pivotRate;
    protected BigDecimal discountPercentage;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String overFlowIndicator;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String serviceCode;
    @XmlElement(required = true)
    protected String commodityCode;
    protected String rateContractReference;
    protected BigDecimal volumetricWeight;
    protected String airlineTariffName;
    protected Integer airlineTariffSerialNumber;
    protected Integer airlineTariffRateSerialNumber;
    protected Long airlineRateLineId;
    protected String airlineRatingType;

    /**
     * Gets the value of the lineItemNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLineItemNumber() {
        return lineItemNumber;
    }

    /**
     * Sets the value of the lineItemNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLineItemNumber(BigInteger value) {
        this.lineItemNumber = value;
    }

    /**
     * Gets the value of the itemDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * Sets the value of the itemDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemDescription(String value) {
        this.itemDescription = value;
    }

    /**
     * Gets the value of the numberofPieces property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberofPieces() {
        return numberofPieces;
    }

    /**
     * Sets the value of the numberofPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberofPieces(BigInteger value) {
        this.numberofPieces = value;
    }

    /**
     * Gets the value of the rateCombinationPoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateCombinationPoint() {
        return rateCombinationPoint;
    }

    /**
     * Sets the value of the rateCombinationPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateCombinationPoint(String value) {
        this.rateCombinationPoint = value;
    }

    /**
     * Gets the value of the weightUnitOfMeasurement property.
     * 
     * @return
     *     possible object is
     *     {@link WeightUnit }
     *     
     */
    public WeightUnit getWeightUnitOfMeasurement() {
        return weightUnitOfMeasurement;
    }

    /**
     * Sets the value of the weightUnitOfMeasurement property.
     * 
     * @param value
     *     allowed object is
     *     {@link WeightUnit }
     *     
     */
    public void setWeightUnitOfMeasurement(WeightUnit value) {
        this.weightUnitOfMeasurement = value;
    }

    /**
     * Gets the value of the grossWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    /**
     * Sets the value of the grossWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGrossWeight(BigDecimal value) {
        this.grossWeight = value;
    }

    /**
     * Gets the value of the grossWeightThreeDecimal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGrossWeightThreeDecimal() {
        return grossWeightThreeDecimal;
    }

    /**
     * Sets the value of the grossWeightThreeDecimal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGrossWeightThreeDecimal(BigDecimal value) {
        this.grossWeightThreeDecimal = value;
    }

    /**
     * Gets the value of the rateClassCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateClassCode() {
        return rateClassCode;
    }

    /**
     * Sets the value of the rateClassCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateClassCode(String value) {
        this.rateClassCode = value;
    }

    /**
     * Gets the value of the rateClassCodeBasis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateClassCodeBasis() {
        return rateClassCodeBasis;
    }

    /**
     * Sets the value of the rateClassCodeBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateClassCodeBasis(String value) {
        this.rateClassCodeBasis = value;
    }

    /**
     * Gets the value of the classRatingPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassRatingPercentage() {
        return classRatingPercentage;
    }

    /**
     * Sets the value of the classRatingPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassRatingPercentage(String value) {
        this.classRatingPercentage = value;
    }

    /**
     * Gets the value of the commodityItemNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommodityItemNumber() {
        return commodityItemNumber;
    }

    /**
     * Sets the value of the commodityItemNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommodityItemNumber(String value) {
        this.commodityItemNumber = value;
    }

    /**
     * Gets the value of the uldRateClassType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUldRateClassType() {
        return uldRateClassType;
    }

    /**
     * Sets the value of the uldRateClassType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUldRateClassType(String value) {
        this.uldRateClassType = value;
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
     * Gets the value of the chargeableWeightThreeDecimal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getChargeableWeightThreeDecimal() {
        return chargeableWeightThreeDecimal;
    }

    /**
     * Sets the value of the chargeableWeightThreeDecimal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setChargeableWeightThreeDecimal(BigDecimal value) {
        this.chargeableWeightThreeDecimal = value;
    }

    /**
     * Gets the value of the rateOrChargeOrDiscountAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRateOrChargeOrDiscountAmount() {
        return rateOrChargeOrDiscountAmount;
    }

    /**
     * Sets the value of the rateOrChargeOrDiscountAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRateOrChargeOrDiscountAmount(BigDecimal value) {
        this.rateOrChargeOrDiscountAmount = value;
    }

    /**
     * Gets the value of the itemTotalCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getItemTotalCharge() {
        return itemTotalCharge;
    }

    /**
     * Sets the value of the itemTotalCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setItemTotalCharge(BigDecimal value) {
        this.itemTotalCharge = value;
    }

    /**
     * Gets the value of the dimensionsDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBRateLineType.DimensionsDetails }
     *     
     */
    public AWBRateLineType.DimensionsDetails getDimensionsDetails() {
        return dimensionsDetails;
    }

    /**
     * Sets the value of the dimensionsDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBRateLineType.DimensionsDetails }
     *     
     */
    public void setDimensionsDetails(AWBRateLineType.DimensionsDetails value) {
        this.dimensionsDetails = value;
    }

    /**
     * Gets the value of the volumeDetails property.
     * 
     * @return
     *     possible object is
     *     {@link VolumeDetailsType }
     *     
     */
    public VolumeDetailsType getVolumeDetails() {
        return volumeDetails;
    }

    /**
     * Sets the value of the volumeDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link VolumeDetailsType }
     *     
     */
    public void setVolumeDetails(VolumeDetailsType value) {
        this.volumeDetails = value;
    }

    /**
     * Gets the value of the uldDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBRateLineType.UldDetails }
     *     
     */
    public AWBRateLineType.UldDetails getUldDetails() {
        return uldDetails;
    }

    /**
     * Sets the value of the uldDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBRateLineType.UldDetails }
     *     
     */
    public void setUldDetails(AWBRateLineType.UldDetails value) {
        this.uldDetails = value;
    }

    /**
     * Gets the value of the harmonizedCommodityCodes property.
     * 
     * @return
     *     possible object is
     *     {@link AWBRateLineType.HarmonizedCommodityCodes }
     *     
     */
    public AWBRateLineType.HarmonizedCommodityCodes getHarmonizedCommodityCodes() {
        return harmonizedCommodityCodes;
    }

    /**
     * Sets the value of the harmonizedCommodityCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBRateLineType.HarmonizedCommodityCodes }
     *     
     */
    public void setHarmonizedCommodityCodes(AWBRateLineType.HarmonizedCommodityCodes value) {
        this.harmonizedCommodityCodes = value;
    }

    /**
     * Gets the value of the countryofOriginofGoods property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryofOriginofGoods() {
        return countryofOriginofGoods;
    }

    /**
     * Sets the value of the countryofOriginofGoods property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryofOriginofGoods(String value) {
        this.countryofOriginofGoods = value;
    }

    /**
     * Gets the value of the pivotRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPivotRate() {
        return pivotRate;
    }

    /**
     * Sets the value of the pivotRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPivotRate(BigDecimal value) {
        this.pivotRate = value;
    }

    /**
     * Gets the value of the discountPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    /**
     * Sets the value of the discountPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDiscountPercentage(BigDecimal value) {
        this.discountPercentage = value;
    }

    /**
     * Gets the value of the overFlowIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverFlowIndicator() {
        return overFlowIndicator;
    }

    /**
     * Sets the value of the overFlowIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverFlowIndicator(String value) {
        this.overFlowIndicator = value;
    }

    /**
     * Gets the value of the serviceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceCode() {
        return serviceCode;
    }

    /**
     * Sets the value of the serviceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceCode(String value) {
        this.serviceCode = value;
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
     * Gets the value of the volumetricWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVolumetricWeight() {
        return volumetricWeight;
    }

    /**
     * Sets the value of the volumetricWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVolumetricWeight(BigDecimal value) {
        this.volumetricWeight = value;
    }

    /**
     * Gets the value of the airlineTariffName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirlineTariffName() {
        return airlineTariffName;
    }

    /**
     * Sets the value of the airlineTariffName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirlineTariffName(String value) {
        this.airlineTariffName = value;
    }

    /**
     * Gets the value of the airlineTariffSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAirlineTariffSerialNumber() {
        return airlineTariffSerialNumber;
    }

    /**
     * Sets the value of the airlineTariffSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAirlineTariffSerialNumber(Integer value) {
        this.airlineTariffSerialNumber = value;
    }

    /**
     * Gets the value of the airlineTariffRateSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAirlineTariffRateSerialNumber() {
        return airlineTariffRateSerialNumber;
    }

    /**
     * Sets the value of the airlineTariffRateSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAirlineTariffRateSerialNumber(Integer value) {
        this.airlineTariffRateSerialNumber = value;
    }

    /**
     * Gets the value of the airlineRateLineId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAirlineRateLineId() {
        return airlineRateLineId;
    }

    /**
     * Sets the value of the airlineRateLineId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAirlineRateLineId(Long value) {
        this.airlineRateLineId = value;
    }

    /**
     * Gets the value of the airlineRatingType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirlineRatingType() {
        return airlineRatingType;
    }

    /**
     * Sets the value of the airlineRatingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirlineRatingType(String value) {
        this.airlineRatingType = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="dimension" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}DimensionType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "dimension"
    })
    public static class DimensionsDetails {

        @XmlElement(required = true)
        protected List<DimensionType> dimension;

        /**
         * Gets the value of the dimension property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dimension property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDimension().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DimensionType }
         * 
         * 
         */
        public List<DimensionType> getDimension() {
            if (dimension == null) {
                dimension = new ArrayList<DimensionType>();
            }
            return this.dimension;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="HarmonizedCommodityCode" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}HarmonizedCommodityCode" maxOccurs="9" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "harmonizedCommodityCode"
    })
    public static class HarmonizedCommodityCodes {

        @XmlElement(name = "HarmonizedCommodityCode")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected List<String> harmonizedCommodityCode;

        /**
         * Gets the value of the harmonizedCommodityCode property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the harmonizedCommodityCode property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHarmonizedCommodityCode().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getHarmonizedCommodityCode() {
            if (harmonizedCommodityCode == null) {
                harmonizedCommodityCode = new ArrayList<String>();
            }
            return this.harmonizedCommodityCode;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ulds" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ULDDetailsType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "ulds"
    })
    public static class UldDetails {

        protected List<ULDDetailsType> ulds;

        /**
         * Gets the value of the ulds property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ulds property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getUlds().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ULDDetailsType }
         * 
         * 
         */
        public List<ULDDetailsType> getUlds() {
            if (ulds == null) {
                ulds = new ArrayList<ULDDetailsType>();
            }
            return this.ulds;
        }

    }

}
