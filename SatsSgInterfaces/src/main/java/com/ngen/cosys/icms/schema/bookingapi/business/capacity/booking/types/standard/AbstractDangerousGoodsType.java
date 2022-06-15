
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
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.OperationalFlagType;


/**
 * <p>Java class for AbstractDangerousGoodsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractDangerousGoodsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="unid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="unidSerNum" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5" minOccurs="0"/>
 *         &lt;element name="serNum" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5" minOccurs="0"/>
 *         &lt;element name="properShippingName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cargoOnlyFlag" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}yn" minOccurs="0"/>
 *         &lt;element name="shipmentClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subRisk" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subRiskOne" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subRiskThree" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subRiskTwo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="radioactiveFlag" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}yn" minOccurs="0"/>
 *         &lt;element name="packingGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="packingType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cargoPackingInstruction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transportIndex" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d4" minOccurs="0"/>
 *         &lt;element name="sccCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}sccCode" minOccurs="0"/>
 *         &lt;element name="sccCode1" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}sccCode" minOccurs="0"/>
 *         &lt;element name="sccCode2" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}sccCode" minOccurs="0"/>
 *         &lt;element name="numberOfPackages" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5" minOccurs="0"/>
 *         &lt;element name="netQuantityPerPackage" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d16_5" minOccurs="0"/>
 *         &lt;element name="netQuantityPerPackageUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operationFlag" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}operationalFlagType" minOccurs="0"/>
 *         &lt;element name="forbiddenFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="passengerLtdQuantityFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="reportableQuantity" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a1" minOccurs="0"/>
 *         &lt;element name="specialAuthorization" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a1" minOccurs="0"/>
 *         &lt;element name="authorizationRemarks" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t300" minOccurs="0"/>
 *         &lt;element name="packingDimensionLength" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d7_2" minOccurs="0"/>
 *         &lt;element name="packingDimensionWidth" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d7_2" minOccurs="0"/>
 *         &lt;element name="packingDimensionHeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d7_2" minOccurs="0"/>
 *         &lt;element name="allPackedSerialNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5" minOccurs="0"/>
 *         &lt;element name="numberOfAllPackages" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5" minOccurs="0"/>
 *         &lt;element name="overPackedSerialNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5" minOccurs="0"/>
 *         &lt;element name="totalTransportIndex" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d4" minOccurs="0"/>
 *         &lt;element name="packingGroupDimensionLength" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d7_2" minOccurs="0"/>
 *         &lt;element name="packingGroupDimensionWidth" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d7_2" minOccurs="0"/>
 *         &lt;element name="packingGroupDimensionHeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d7_2" minOccurs="0"/>
 *         &lt;element name="dimensionUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="packingGroupDimensionUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numberOfOverPackages" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5" minOccurs="0"/>
 *         &lt;element name="grossIndicator" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="G"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="technicalName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rmc" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="30"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="packagingDimLength" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d7_2" minOccurs="0"/>
 *         &lt;element name="packagingDimWidth" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d7_2" minOccurs="0"/>
 *         &lt;element name="packagingDimHeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d7_2" minOccurs="0"/>
 *         &lt;element name="ergCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="30"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="specialAuthorizationFlag" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}yn" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractDangerousGoodsType", propOrder = {
    "unid",
    "unidSerNum",
    "serNum",
    "properShippingName",
    "cargoOnlyFlag",
    "shipmentClass",
    "subRisk",
    "subRiskOne",
    "subRiskThree",
    "source",
    "subRiskTwo",
    "radioactiveFlag",
    "packingGroup",
    "packingType",
    "cargoPackingInstruction",
    "transportIndex",
    "sccCode",
    "sccCode1",
    "sccCode2",
    "numberOfPackages",
    "netQuantityPerPackage",
    "netQuantityPerPackageUnit",
    "operationFlag",
    "forbiddenFlag",
    "passengerLtdQuantityFlag",
    "reportableQuantity",
    "specialAuthorization",
    "authorizationRemarks",
    "packingDimensionLength",
    "packingDimensionWidth",
    "packingDimensionHeight",
    "allPackedSerialNumber",
    "numberOfAllPackages",
    "overPackedSerialNumber",
    "totalTransportIndex",
    "packingGroupDimensionLength",
    "packingGroupDimensionWidth",
    "packingGroupDimensionHeight",
    "dimensionUnit",
    "packingGroupDimensionUnit",
    "numberOfOverPackages",
    "grossIndicator",
    "technicalName",
    "rmc",
    "packagingDimLength",
    "packagingDimWidth",
    "packagingDimHeight",
    "ergCode",
    "specialAuthorizationFlag"
})
@XmlSeeAlso({
    DangerousGoodsType.class
})
public class AbstractDangerousGoodsType {

    @XmlElement(required = true)
    protected String unid;
    protected BigInteger unidSerNum;
    protected BigInteger serNum;
    protected String properShippingName;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String cargoOnlyFlag;
    protected String shipmentClass;
    protected String subRisk;
    protected String subRiskOne;
    protected String subRiskThree;
    protected String source;
    protected String subRiskTwo;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String radioactiveFlag;
    protected String packingGroup;
    protected String packingType;
    protected String cargoPackingInstruction;
    protected BigDecimal transportIndex;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String sccCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String sccCode1;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String sccCode2;
    protected BigInteger numberOfPackages;
    protected BigDecimal netQuantityPerPackage;
    protected String netQuantityPerPackageUnit;
    @XmlSchemaType(name = "string")
    protected OperationalFlagType operationFlag;
    protected Boolean forbiddenFlag;
    protected Boolean passengerLtdQuantityFlag;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String reportableQuantity;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String specialAuthorization;
    protected String authorizationRemarks;
    protected BigDecimal packingDimensionLength;
    protected BigDecimal packingDimensionWidth;
    protected BigDecimal packingDimensionHeight;
    protected BigInteger allPackedSerialNumber;
    protected BigInteger numberOfAllPackages;
    protected BigInteger overPackedSerialNumber;
    protected BigDecimal totalTransportIndex;
    protected BigDecimal packingGroupDimensionLength;
    protected BigDecimal packingGroupDimensionWidth;
    protected BigDecimal packingGroupDimensionHeight;
    protected String dimensionUnit;
    protected String packingGroupDimensionUnit;
    protected BigInteger numberOfOverPackages;
    protected String grossIndicator;
    protected String technicalName;
    protected String rmc;
    protected BigDecimal packagingDimLength;
    protected BigDecimal packagingDimWidth;
    protected BigDecimal packagingDimHeight;
    protected String ergCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String specialAuthorizationFlag;

    /**
     * Gets the value of the unid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnid() {
        return unid;
    }

    /**
     * Sets the value of the unid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnid(String value) {
        this.unid = value;
    }

    /**
     * Gets the value of the unidSerNum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUnidSerNum() {
        return unidSerNum;
    }

    /**
     * Sets the value of the unidSerNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUnidSerNum(BigInteger value) {
        this.unidSerNum = value;
    }

    /**
     * Gets the value of the serNum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSerNum() {
        return serNum;
    }

    /**
     * Sets the value of the serNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSerNum(BigInteger value) {
        this.serNum = value;
    }

    /**
     * Gets the value of the properShippingName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProperShippingName() {
        return properShippingName;
    }

    /**
     * Sets the value of the properShippingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProperShippingName(String value) {
        this.properShippingName = value;
    }

    /**
     * Gets the value of the cargoOnlyFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCargoOnlyFlag() {
        return cargoOnlyFlag;
    }

    /**
     * Sets the value of the cargoOnlyFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCargoOnlyFlag(String value) {
        this.cargoOnlyFlag = value;
    }

    /**
     * Gets the value of the shipmentClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipmentClass() {
        return shipmentClass;
    }

    /**
     * Sets the value of the shipmentClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipmentClass(String value) {
        this.shipmentClass = value;
    }

    /**
     * Gets the value of the subRisk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubRisk() {
        return subRisk;
    }

    /**
     * Sets the value of the subRisk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubRisk(String value) {
        this.subRisk = value;
    }

    /**
     * Gets the value of the subRiskOne property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubRiskOne() {
        return subRiskOne;
    }

    /**
     * Sets the value of the subRiskOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubRiskOne(String value) {
        this.subRiskOne = value;
    }

    /**
     * Gets the value of the subRiskThree property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubRiskThree() {
        return subRiskThree;
    }

    /**
     * Sets the value of the subRiskThree property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubRiskThree(String value) {
        this.subRiskThree = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the subRiskTwo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubRiskTwo() {
        return subRiskTwo;
    }

    /**
     * Sets the value of the subRiskTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubRiskTwo(String value) {
        this.subRiskTwo = value;
    }

    /**
     * Gets the value of the radioactiveFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRadioactiveFlag() {
        return radioactiveFlag;
    }

    /**
     * Sets the value of the radioactiveFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRadioactiveFlag(String value) {
        this.radioactiveFlag = value;
    }

    /**
     * Gets the value of the packingGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackingGroup() {
        return packingGroup;
    }

    /**
     * Sets the value of the packingGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackingGroup(String value) {
        this.packingGroup = value;
    }

    /**
     * Gets the value of the packingType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackingType() {
        return packingType;
    }

    /**
     * Sets the value of the packingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackingType(String value) {
        this.packingType = value;
    }

    /**
     * Gets the value of the cargoPackingInstruction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCargoPackingInstruction() {
        return cargoPackingInstruction;
    }

    /**
     * Sets the value of the cargoPackingInstruction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCargoPackingInstruction(String value) {
        this.cargoPackingInstruction = value;
    }

    /**
     * Gets the value of the transportIndex property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTransportIndex() {
        return transportIndex;
    }

    /**
     * Sets the value of the transportIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTransportIndex(BigDecimal value) {
        this.transportIndex = value;
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
     * Gets the value of the sccCode1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSccCode1() {
        return sccCode1;
    }

    /**
     * Sets the value of the sccCode1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSccCode1(String value) {
        this.sccCode1 = value;
    }

    /**
     * Gets the value of the sccCode2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSccCode2() {
        return sccCode2;
    }

    /**
     * Sets the value of the sccCode2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSccCode2(String value) {
        this.sccCode2 = value;
    }

    /**
     * Gets the value of the numberOfPackages property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfPackages() {
        return numberOfPackages;
    }

    /**
     * Sets the value of the numberOfPackages property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfPackages(BigInteger value) {
        this.numberOfPackages = value;
    }

    /**
     * Gets the value of the netQuantityPerPackage property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNetQuantityPerPackage() {
        return netQuantityPerPackage;
    }

    /**
     * Sets the value of the netQuantityPerPackage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNetQuantityPerPackage(BigDecimal value) {
        this.netQuantityPerPackage = value;
    }

    /**
     * Gets the value of the netQuantityPerPackageUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetQuantityPerPackageUnit() {
        return netQuantityPerPackageUnit;
    }

    /**
     * Sets the value of the netQuantityPerPackageUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetQuantityPerPackageUnit(String value) {
        this.netQuantityPerPackageUnit = value;
    }

    /**
     * Gets the value of the operationFlag property.
     * 
     * @return
     *     possible object is
     *     {@link OperationalFlagType }
     *     
     */
    public OperationalFlagType getOperationFlag() {
        return operationFlag;
    }

    /**
     * Sets the value of the operationFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationalFlagType }
     *     
     */
    public void setOperationFlag(OperationalFlagType value) {
        this.operationFlag = value;
    }

    /**
     * Gets the value of the forbiddenFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isForbiddenFlag() {
        return forbiddenFlag;
    }

    /**
     * Sets the value of the forbiddenFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setForbiddenFlag(Boolean value) {
        this.forbiddenFlag = value;
    }

    /**
     * Gets the value of the passengerLtdQuantityFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPassengerLtdQuantityFlag() {
        return passengerLtdQuantityFlag;
    }

    /**
     * Sets the value of the passengerLtdQuantityFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPassengerLtdQuantityFlag(Boolean value) {
        this.passengerLtdQuantityFlag = value;
    }

    /**
     * Gets the value of the reportableQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportableQuantity() {
        return reportableQuantity;
    }

    /**
     * Sets the value of the reportableQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportableQuantity(String value) {
        this.reportableQuantity = value;
    }

    /**
     * Gets the value of the specialAuthorization property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialAuthorization() {
        return specialAuthorization;
    }

    /**
     * Sets the value of the specialAuthorization property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialAuthorization(String value) {
        this.specialAuthorization = value;
    }

    /**
     * Gets the value of the authorizationRemarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationRemarks() {
        return authorizationRemarks;
    }

    /**
     * Sets the value of the authorizationRemarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationRemarks(String value) {
        this.authorizationRemarks = value;
    }

    /**
     * Gets the value of the packingDimensionLength property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPackingDimensionLength() {
        return packingDimensionLength;
    }

    /**
     * Sets the value of the packingDimensionLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPackingDimensionLength(BigDecimal value) {
        this.packingDimensionLength = value;
    }

    /**
     * Gets the value of the packingDimensionWidth property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPackingDimensionWidth() {
        return packingDimensionWidth;
    }

    /**
     * Sets the value of the packingDimensionWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPackingDimensionWidth(BigDecimal value) {
        this.packingDimensionWidth = value;
    }

    /**
     * Gets the value of the packingDimensionHeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPackingDimensionHeight() {
        return packingDimensionHeight;
    }

    /**
     * Sets the value of the packingDimensionHeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPackingDimensionHeight(BigDecimal value) {
        this.packingDimensionHeight = value;
    }

    /**
     * Gets the value of the allPackedSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAllPackedSerialNumber() {
        return allPackedSerialNumber;
    }

    /**
     * Sets the value of the allPackedSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAllPackedSerialNumber(BigInteger value) {
        this.allPackedSerialNumber = value;
    }

    /**
     * Gets the value of the numberOfAllPackages property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfAllPackages() {
        return numberOfAllPackages;
    }

    /**
     * Sets the value of the numberOfAllPackages property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfAllPackages(BigInteger value) {
        this.numberOfAllPackages = value;
    }

    /**
     * Gets the value of the overPackedSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOverPackedSerialNumber() {
        return overPackedSerialNumber;
    }

    /**
     * Sets the value of the overPackedSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOverPackedSerialNumber(BigInteger value) {
        this.overPackedSerialNumber = value;
    }

    /**
     * Gets the value of the totalTransportIndex property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalTransportIndex() {
        return totalTransportIndex;
    }

    /**
     * Sets the value of the totalTransportIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalTransportIndex(BigDecimal value) {
        this.totalTransportIndex = value;
    }

    /**
     * Gets the value of the packingGroupDimensionLength property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPackingGroupDimensionLength() {
        return packingGroupDimensionLength;
    }

    /**
     * Sets the value of the packingGroupDimensionLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPackingGroupDimensionLength(BigDecimal value) {
        this.packingGroupDimensionLength = value;
    }

    /**
     * Gets the value of the packingGroupDimensionWidth property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPackingGroupDimensionWidth() {
        return packingGroupDimensionWidth;
    }

    /**
     * Sets the value of the packingGroupDimensionWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPackingGroupDimensionWidth(BigDecimal value) {
        this.packingGroupDimensionWidth = value;
    }

    /**
     * Gets the value of the packingGroupDimensionHeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPackingGroupDimensionHeight() {
        return packingGroupDimensionHeight;
    }

    /**
     * Sets the value of the packingGroupDimensionHeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPackingGroupDimensionHeight(BigDecimal value) {
        this.packingGroupDimensionHeight = value;
    }

    /**
     * Gets the value of the dimensionUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDimensionUnit() {
        return dimensionUnit;
    }

    /**
     * Sets the value of the dimensionUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDimensionUnit(String value) {
        this.dimensionUnit = value;
    }

    /**
     * Gets the value of the packingGroupDimensionUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackingGroupDimensionUnit() {
        return packingGroupDimensionUnit;
    }

    /**
     * Sets the value of the packingGroupDimensionUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackingGroupDimensionUnit(String value) {
        this.packingGroupDimensionUnit = value;
    }

    /**
     * Gets the value of the numberOfOverPackages property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfOverPackages() {
        return numberOfOverPackages;
    }

    /**
     * Sets the value of the numberOfOverPackages property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfOverPackages(BigInteger value) {
        this.numberOfOverPackages = value;
    }

    /**
     * Gets the value of the grossIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrossIndicator() {
        return grossIndicator;
    }

    /**
     * Sets the value of the grossIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrossIndicator(String value) {
        this.grossIndicator = value;
    }

    /**
     * Gets the value of the technicalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTechnicalName() {
        return technicalName;
    }

    /**
     * Sets the value of the technicalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTechnicalName(String value) {
        this.technicalName = value;
    }

    /**
     * Gets the value of the rmc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRmc() {
        return rmc;
    }

    /**
     * Sets the value of the rmc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRmc(String value) {
        this.rmc = value;
    }

    /**
     * Gets the value of the packagingDimLength property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPackagingDimLength() {
        return packagingDimLength;
    }

    /**
     * Sets the value of the packagingDimLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPackagingDimLength(BigDecimal value) {
        this.packagingDimLength = value;
    }

    /**
     * Gets the value of the packagingDimWidth property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPackagingDimWidth() {
        return packagingDimWidth;
    }

    /**
     * Sets the value of the packagingDimWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPackagingDimWidth(BigDecimal value) {
        this.packagingDimWidth = value;
    }

    /**
     * Gets the value of the packagingDimHeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPackagingDimHeight() {
        return packagingDimHeight;
    }

    /**
     * Sets the value of the packagingDimHeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPackagingDimHeight(BigDecimal value) {
        this.packagingDimHeight = value;
    }

    /**
     * Gets the value of the ergCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErgCode() {
        return ergCode;
    }

    /**
     * Sets the value of the ergCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErgCode(String value) {
        this.ergCode = value;
    }

    /**
     * Gets the value of the specialAuthorizationFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialAuthorizationFlag() {
        return specialAuthorizationFlag;
    }

    /**
     * Sets the value of the specialAuthorizationFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialAuthorizationFlag(String value) {
        this.specialAuthorizationFlag = value;
    }

}
