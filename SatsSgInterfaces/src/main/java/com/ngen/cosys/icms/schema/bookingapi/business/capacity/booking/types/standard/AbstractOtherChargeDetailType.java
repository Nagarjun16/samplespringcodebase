
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
 * <p>Java class for AbstractOtherChargeDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractOtherChargeDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="otherChargeHeadCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}a1_5"/>
 *         &lt;element name="otherChargeHeadName" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t100" minOccurs="0"/>
 *         &lt;element name="otherChargeAmount" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d16" minOccurs="0"/>
 *         &lt;element name="otherChargeAmountInSystemCurrency" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d16" minOccurs="0"/>
 *         &lt;element name="dueCarrierFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="dueAgentFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="taxAmount" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractOtherChargeDetailType", propOrder = {
    "otherChargeHeadCode",
    "otherChargeHeadName",
    "otherChargeAmount",
    "otherChargeAmountInSystemCurrency",
    "dueCarrierFlag",
    "dueAgentFlag",
    "taxAmount"
})
@XmlSeeAlso({
    OtherChargeDetailType.class
})
public class AbstractOtherChargeDetailType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String otherChargeHeadCode;
    protected String otherChargeHeadName;
    protected BigDecimal otherChargeAmount;
    protected BigDecimal otherChargeAmountInSystemCurrency;
    protected Boolean dueCarrierFlag;
    protected Boolean dueAgentFlag;
    protected BigDecimal taxAmount;

    /**
     * Gets the value of the otherChargeHeadCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherChargeHeadCode() {
        return otherChargeHeadCode;
    }

    /**
     * Sets the value of the otherChargeHeadCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherChargeHeadCode(String value) {
        this.otherChargeHeadCode = value;
    }

    /**
     * Gets the value of the otherChargeHeadName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherChargeHeadName() {
        return otherChargeHeadName;
    }

    /**
     * Sets the value of the otherChargeHeadName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherChargeHeadName(String value) {
        this.otherChargeHeadName = value;
    }

    /**
     * Gets the value of the otherChargeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOtherChargeAmount() {
        return otherChargeAmount;
    }

    /**
     * Sets the value of the otherChargeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOtherChargeAmount(BigDecimal value) {
        this.otherChargeAmount = value;
    }

    /**
     * Gets the value of the otherChargeAmountInSystemCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOtherChargeAmountInSystemCurrency() {
        return otherChargeAmountInSystemCurrency;
    }

    /**
     * Sets the value of the otherChargeAmountInSystemCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOtherChargeAmountInSystemCurrency(BigDecimal value) {
        this.otherChargeAmountInSystemCurrency = value;
    }

    /**
     * Gets the value of the dueCarrierFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDueCarrierFlag() {
        return dueCarrierFlag;
    }

    /**
     * Sets the value of the dueCarrierFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDueCarrierFlag(Boolean value) {
        this.dueCarrierFlag = value;
    }

    /**
     * Gets the value of the dueAgentFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDueAgentFlag() {
        return dueAgentFlag;
    }

    /**
     * Sets the value of the dueAgentFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDueAgentFlag(Boolean value) {
        this.dueAgentFlag = value;
    }

    /**
     * Gets the value of the taxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    /**
     * Sets the value of the taxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTaxAmount(BigDecimal value) {
        this.taxAmount = value;
    }

}
