
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for CollectChargesInDestinationCurrencyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CollectChargesInDestinationCurrencyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="destinationCurrencyCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}currencyCode"/>
 *         &lt;element name="rateOfCurrencyConversion">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;fractionDigits value="6"/>
 *               &lt;minInclusive value="0.000001"/>
 *               &lt;maxInclusive value="99999999999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ccChargesInDestinationCurrency" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType"/>
 *         &lt;element name="chargesAtDestination" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType"/>
 *         &lt;element name="totalCollectChargesAmount" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType"/>
 *         &lt;element name="dateOfExchange" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date" minOccurs="0"/>
 *         &lt;element name="exchangeRateBasis" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="T"/>
 *               &lt;enumeration value="30D"/>
 *               &lt;enumeration value="3M"/>
 *               &lt;enumeration value="6M"/>
 *               &lt;enumeration value="D"/>
 *               &lt;enumeration value="E"/>
 *               &lt;enumeration value="F"/>
 *               &lt;enumeration value="M"/>
 *               &lt;enumeration value="L"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CollectChargesInDestinationCurrencyType", propOrder = {
    "destinationCurrencyCode",
    "rateOfCurrencyConversion",
    "ccChargesInDestinationCurrency",
    "chargesAtDestination",
    "totalCollectChargesAmount",
    "dateOfExchange",
    "exchangeRateBasis"
})
public class CollectChargesInDestinationCurrencyType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String destinationCurrencyCode;
    @XmlElement(required = true)
    protected BigDecimal rateOfCurrencyConversion;
    @XmlElement(required = true)
    protected BigDecimal ccChargesInDestinationCurrency;
    @XmlElement(required = true)
    protected BigDecimal chargesAtDestination;
    @XmlElement(required = true)
    protected BigDecimal totalCollectChargesAmount;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String dateOfExchange;
    protected String exchangeRateBasis;

    /**
     * Gets the value of the destinationCurrencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationCurrencyCode() {
        return destinationCurrencyCode;
    }

    /**
     * Sets the value of the destinationCurrencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationCurrencyCode(String value) {
        this.destinationCurrencyCode = value;
    }

    /**
     * Gets the value of the rateOfCurrencyConversion property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRateOfCurrencyConversion() {
        return rateOfCurrencyConversion;
    }

    /**
     * Sets the value of the rateOfCurrencyConversion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRateOfCurrencyConversion(BigDecimal value) {
        this.rateOfCurrencyConversion = value;
    }

    /**
     * Gets the value of the ccChargesInDestinationCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCcChargesInDestinationCurrency() {
        return ccChargesInDestinationCurrency;
    }

    /**
     * Sets the value of the ccChargesInDestinationCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCcChargesInDestinationCurrency(BigDecimal value) {
        this.ccChargesInDestinationCurrency = value;
    }

    /**
     * Gets the value of the chargesAtDestination property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getChargesAtDestination() {
        return chargesAtDestination;
    }

    /**
     * Sets the value of the chargesAtDestination property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setChargesAtDestination(BigDecimal value) {
        this.chargesAtDestination = value;
    }

    /**
     * Gets the value of the totalCollectChargesAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalCollectChargesAmount() {
        return totalCollectChargesAmount;
    }

    /**
     * Sets the value of the totalCollectChargesAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalCollectChargesAmount(BigDecimal value) {
        this.totalCollectChargesAmount = value;
    }

    /**
     * Gets the value of the dateOfExchange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateOfExchange() {
        return dateOfExchange;
    }

    /**
     * Sets the value of the dateOfExchange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOfExchange(String value) {
        this.dateOfExchange = value;
    }

    /**
     * Gets the value of the exchangeRateBasis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExchangeRateBasis() {
        return exchangeRateBasis;
    }

    /**
     * Sets the value of the exchangeRateBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExchangeRateBasis(String value) {
        this.exchangeRateBasis = value;
    }

}
