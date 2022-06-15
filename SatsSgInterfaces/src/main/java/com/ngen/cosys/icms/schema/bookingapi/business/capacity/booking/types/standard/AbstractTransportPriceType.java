
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for AbstractTransportPriceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractTransportPriceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rateType" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m1_3" minOccurs="0"/>
 *         &lt;element name="netnetRevenuePrice" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountTypeNeg" minOccurs="0"/>
 *         &lt;element name="exchangeRate" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="15"/>
 *               &lt;fractionDigits value="7"/>
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="99999999.9999999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="valuationCharge" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="paymentInstrument" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="CA"/>
 *               &lt;enumeration value="CT"/>
 *               &lt;enumeration value="CQ"/>
 *               &lt;enumeration value="CR"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="paymentResponsibility" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="P"/>
 *               &lt;enumeration value="C"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="pricingTool" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[a-zA-Z0-9]{0,3}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ratesheetID" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="30"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="techID" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;pattern value="[a-zA-Z0-9]{1,8}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="promotionType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="CH"/>
 *               &lt;enumeration value="FL"/>
 *               &lt;enumeration value="RA"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="lastEvalDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date" minOccurs="0"/>
 *         &lt;element name="globalSalesCommission" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="addCom" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountTypeNeg" minOccurs="0"/>
 *         &lt;element name="jointRev" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="evaluationType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="G"/>
 *               &lt;enumeration value="M"/>
 *               &lt;enumeration value="S"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="quotationNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[-a-zA-Z0-9]{0,30}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="quotationRevisionNo" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_5" minOccurs="0"/>
 *         &lt;element name="contractNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t6" minOccurs="0"/>
 *         &lt;element name="rateValue" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;fractionDigits value="4"/>
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="99999999"/>
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
@XmlType(name = "AbstractTransportPriceType", propOrder = {
    "rateType",
    "netnetRevenuePrice",
    "exchangeRate",
    "valuationCharge",
    "paymentInstrument",
    "paymentResponsibility",
    "pricingTool",
    "ratesheetID",
    "techID",
    "promotionType",
    "lastEvalDate",
    "globalSalesCommission",
    "addCom",
    "jointRev",
    "evaluationType",
    "quotationNumber",
    "quotationRevisionNo",
    "contractNumber",
    "rateValue"
})
@XmlSeeAlso({
    TransportPriceType.class
})
public class AbstractTransportPriceType {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String rateType;
    protected BigDecimal netnetRevenuePrice;
    protected BigDecimal exchangeRate;
    protected BigDecimal valuationCharge;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String paymentInstrument;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String paymentResponsibility;
    protected String pricingTool;
    protected String ratesheetID;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String techID;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String promotionType;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String lastEvalDate;
    protected BigDecimal globalSalesCommission;
    protected BigDecimal addCom;
    protected BigDecimal jointRev;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String evaluationType;
    protected String quotationNumber;
    protected BigInteger quotationRevisionNo;
    protected String contractNumber;
    protected BigDecimal rateValue;

    /**
     * Gets the value of the rateType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateType() {
        return rateType;
    }

    /**
     * Sets the value of the rateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateType(String value) {
        this.rateType = value;
    }

    /**
     * Gets the value of the netnetRevenuePrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNetnetRevenuePrice() {
        return netnetRevenuePrice;
    }

    /**
     * Sets the value of the netnetRevenuePrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNetnetRevenuePrice(BigDecimal value) {
        this.netnetRevenuePrice = value;
    }

    /**
     * Gets the value of the exchangeRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Sets the value of the exchangeRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExchangeRate(BigDecimal value) {
        this.exchangeRate = value;
    }

    /**
     * Gets the value of the valuationCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValuationCharge() {
        return valuationCharge;
    }

    /**
     * Sets the value of the valuationCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValuationCharge(BigDecimal value) {
        this.valuationCharge = value;
    }

    /**
     * Gets the value of the paymentInstrument property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentInstrument() {
        return paymentInstrument;
    }

    /**
     * Sets the value of the paymentInstrument property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentInstrument(String value) {
        this.paymentInstrument = value;
    }

    /**
     * Gets the value of the paymentResponsibility property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentResponsibility() {
        return paymentResponsibility;
    }

    /**
     * Sets the value of the paymentResponsibility property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentResponsibility(String value) {
        this.paymentResponsibility = value;
    }

    /**
     * Gets the value of the pricingTool property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingTool() {
        return pricingTool;
    }

    /**
     * Sets the value of the pricingTool property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingTool(String value) {
        this.pricingTool = value;
    }

    /**
     * Gets the value of the ratesheetID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatesheetID() {
        return ratesheetID;
    }

    /**
     * Sets the value of the ratesheetID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatesheetID(String value) {
        this.ratesheetID = value;
    }

    /**
     * Gets the value of the techID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTechID() {
        return techID;
    }

    /**
     * Sets the value of the techID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTechID(String value) {
        this.techID = value;
    }

    /**
     * Gets the value of the promotionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromotionType() {
        return promotionType;
    }

    /**
     * Sets the value of the promotionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromotionType(String value) {
        this.promotionType = value;
    }

    /**
     * Gets the value of the lastEvalDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastEvalDate() {
        return lastEvalDate;
    }

    /**
     * Sets the value of the lastEvalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastEvalDate(String value) {
        this.lastEvalDate = value;
    }

    /**
     * Gets the value of the globalSalesCommission property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGlobalSalesCommission() {
        return globalSalesCommission;
    }

    /**
     * Sets the value of the globalSalesCommission property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGlobalSalesCommission(BigDecimal value) {
        this.globalSalesCommission = value;
    }

    /**
     * Gets the value of the addCom property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAddCom() {
        return addCom;
    }

    /**
     * Sets the value of the addCom property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAddCom(BigDecimal value) {
        this.addCom = value;
    }

    /**
     * Gets the value of the jointRev property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getJointRev() {
        return jointRev;
    }

    /**
     * Sets the value of the jointRev property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setJointRev(BigDecimal value) {
        this.jointRev = value;
    }

    /**
     * Gets the value of the evaluationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvaluationType() {
        return evaluationType;
    }

    /**
     * Sets the value of the evaluationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvaluationType(String value) {
        this.evaluationType = value;
    }

    /**
     * Gets the value of the quotationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuotationNumber() {
        return quotationNumber;
    }

    /**
     * Sets the value of the quotationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuotationNumber(String value) {
        this.quotationNumber = value;
    }

    /**
     * Gets the value of the quotationRevisionNo property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getQuotationRevisionNo() {
        return quotationRevisionNo;
    }

    /**
     * Sets the value of the quotationRevisionNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setQuotationRevisionNo(BigInteger value) {
        this.quotationRevisionNo = value;
    }

    /**
     * Gets the value of the contractNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNumber() {
        return contractNumber;
    }

    /**
     * Sets the value of the contractNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNumber(String value) {
        this.contractNumber = value;
    }

    /**
     * Gets the value of the rateValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRateValue() {
        return rateValue;
    }

    /**
     * Sets the value of the rateValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRateValue(BigDecimal value) {
        this.rateValue = value;
    }

}
