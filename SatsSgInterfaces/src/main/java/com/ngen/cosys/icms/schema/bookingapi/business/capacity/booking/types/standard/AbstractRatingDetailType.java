
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for AbstractRatingDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractRatingDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rateClass" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a1" minOccurs="0"/>
 *         &lt;element name="iataRate" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;maxInclusive value="99999999"/>
 *               &lt;minInclusive value="0"/>
 *               &lt;fractionDigits value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="iataRateInSystemCurrency" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;maxInclusive value="99999999"/>
 *               &lt;minInclusive value="0"/>
 *               &lt;fractionDigits value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="iataCharge" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="iataChargeInSystemCurrency" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="totalTaxes" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="totalTaxesInSystemCurrency" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="marketRate" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;maxInclusive value="99999999"/>
 *               &lt;minInclusive value="0"/>
 *               &lt;fractionDigits value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="marketRateInSystemCurrency" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;maxInclusive value="99999999"/>
 *               &lt;minInclusive value="0"/>
 *               &lt;fractionDigits value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="marketCharge" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="marketChargeInSystemCurrency" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="baseCharge" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="baseChargeInSystemCurrency" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractRatingDetailType", propOrder = {
    "rateClass",
    "iataRate",
    "iataRateInSystemCurrency",
    "iataCharge",
    "iataChargeInSystemCurrency",
    "totalTaxes",
    "totalTaxesInSystemCurrency",
    "marketRate",
    "marketRateInSystemCurrency",
    "marketCharge",
    "marketChargeInSystemCurrency",
    "baseCharge",
    "baseChargeInSystemCurrency"
})
@XmlSeeAlso({
    RatingDetailType.class
})
public class AbstractRatingDetailType {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String rateClass;
    protected BigDecimal iataRate;
    protected BigDecimal iataRateInSystemCurrency;
    protected BigDecimal iataCharge;
    protected BigDecimal iataChargeInSystemCurrency;
    protected BigDecimal totalTaxes;
    protected BigDecimal totalTaxesInSystemCurrency;
    protected BigDecimal marketRate;
    protected BigDecimal marketRateInSystemCurrency;
    protected BigDecimal marketCharge;
    protected BigDecimal marketChargeInSystemCurrency;
    protected BigDecimal baseCharge;
    protected BigDecimal baseChargeInSystemCurrency;

    /**
     * Gets the value of the rateClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateClass() {
        return rateClass;
    }

    /**
     * Sets the value of the rateClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateClass(String value) {
        this.rateClass = value;
    }

    /**
     * Gets the value of the iataRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIataRate() {
        return iataRate;
    }

    /**
     * Sets the value of the iataRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIataRate(BigDecimal value) {
        this.iataRate = value;
    }

    /**
     * Gets the value of the iataRateInSystemCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIataRateInSystemCurrency() {
        return iataRateInSystemCurrency;
    }

    /**
     * Sets the value of the iataRateInSystemCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIataRateInSystemCurrency(BigDecimal value) {
        this.iataRateInSystemCurrency = value;
    }

    /**
     * Gets the value of the iataCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIataCharge() {
        return iataCharge;
    }

    /**
     * Sets the value of the iataCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIataCharge(BigDecimal value) {
        this.iataCharge = value;
    }

    /**
     * Gets the value of the iataChargeInSystemCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIataChargeInSystemCurrency() {
        return iataChargeInSystemCurrency;
    }

    /**
     * Sets the value of the iataChargeInSystemCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIataChargeInSystemCurrency(BigDecimal value) {
        this.iataChargeInSystemCurrency = value;
    }

    /**
     * Gets the value of the totalTaxes property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalTaxes() {
        return totalTaxes;
    }

    /**
     * Sets the value of the totalTaxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalTaxes(BigDecimal value) {
        this.totalTaxes = value;
    }

    /**
     * Gets the value of the totalTaxesInSystemCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalTaxesInSystemCurrency() {
        return totalTaxesInSystemCurrency;
    }

    /**
     * Sets the value of the totalTaxesInSystemCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalTaxesInSystemCurrency(BigDecimal value) {
        this.totalTaxesInSystemCurrency = value;
    }

    /**
     * Gets the value of the marketRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMarketRate() {
        return marketRate;
    }

    /**
     * Sets the value of the marketRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMarketRate(BigDecimal value) {
        this.marketRate = value;
    }

    /**
     * Gets the value of the marketRateInSystemCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMarketRateInSystemCurrency() {
        return marketRateInSystemCurrency;
    }

    /**
     * Sets the value of the marketRateInSystemCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMarketRateInSystemCurrency(BigDecimal value) {
        this.marketRateInSystemCurrency = value;
    }

    /**
     * Gets the value of the marketCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMarketCharge() {
        return marketCharge;
    }

    /**
     * Sets the value of the marketCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMarketCharge(BigDecimal value) {
        this.marketCharge = value;
    }

    /**
     * Gets the value of the marketChargeInSystemCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMarketChargeInSystemCurrency() {
        return marketChargeInSystemCurrency;
    }

    /**
     * Sets the value of the marketChargeInSystemCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMarketChargeInSystemCurrency(BigDecimal value) {
        this.marketChargeInSystemCurrency = value;
    }

    /**
     * Gets the value of the baseCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBaseCharge() {
        return baseCharge;
    }

    /**
     * Sets the value of the baseCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBaseCharge(BigDecimal value) {
        this.baseCharge = value;
    }

    /**
     * Gets the value of the baseChargeInSystemCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBaseChargeInSystemCurrency() {
        return baseChargeInSystemCurrency;
    }

    /**
     * Sets the value of the baseChargeInSystemCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBaseChargeInSystemCurrency(BigDecimal value) {
        this.baseChargeInSystemCurrency = value;
    }

}
