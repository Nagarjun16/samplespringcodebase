
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


/**
 * 
 * 				Node for each terminal charge
 * 			
 * 
 * <p>Java class for AWBTerminalChargeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBTerminalChargeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="otherChargeCode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;pattern value="[a-zA-Z0-9]{2,6}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="stationCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}stationCode" minOccurs="0"/>
 *         &lt;element name="exportImportTransitFlag">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="E"/>
 *               &lt;enumeration value="I"/>
 *               &lt;enumeration value="T"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="prepaidCollectIndicator">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="P"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="chargeEntitlementCodeParty">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="A"/>
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="B"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="chargeAmount" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType"/>
 *         &lt;element name="chargeAmountCurrency" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}currencyCode"/>
 *         &lt;element name="meansOfPayment" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="CA"/>
 *               &lt;enumeration value="CT"/>
 *               &lt;enumeration value="CQ"/>
 *               &lt;enumeration value="CR"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="manualIndicator" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="G"/>
 *               &lt;enumeration value="M"/>
 *               &lt;enumeration value="C"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="customerForTerminalCharges" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}CustomerDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="chargingAttributes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="minimumAmount" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *                   &lt;element name="maximumAmount" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *                   &lt;element name="chargeAmountType" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;pattern value="[a-zA-Z]{1,2}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="beginTimestamp" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *                   &lt;element name="endTimestamp" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *                   &lt;element name="noOfDayExemptions" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;pattern value="[0-9]{0,4}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="noOfDays" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;pattern value="[0-9]{0,4}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="rate" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;fractionDigits value="4"/>
 *                         &lt;minExclusive value="0.0001"/>
 *                         &lt;maxExclusive value="99999999"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="rateUnit" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;pattern value="[a-zA-Z0-9]{0,6}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="weight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *                   &lt;element name="weightThreeDecimal" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeightThreeDecimal" minOccurs="0"/>
 *                   &lt;element name="noOfPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces" minOccurs="0"/>
 *                   &lt;element name="noOfItems" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;pattern value="[0-9]{1,5}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="noOfMinutes" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;pattern value="[0-9]{1,4}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="percentage" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;fractionDigits value="2"/>
 *                         &lt;minInclusive value="0.01"/>
 *                         &lt;maxInclusive value="100"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="baseValue" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *                   &lt;element name="freeHours" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;pattern value="[0-9]{1,5}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="quantity" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;pattern value="[0-9]{1,5}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="time" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;pattern value="([0-1][0-9]|2[0-3]):[0-5][0-9]"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="entitlementSubCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="chargeHeadName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="terminalChargesDescription" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="25"/>
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
@XmlType(name = "AWBTerminalChargeType", propOrder = {
    "otherChargeCode",
    "stationCode",
    "exportImportTransitFlag",
    "prepaidCollectIndicator",
    "chargeEntitlementCodeParty",
    "chargeAmount",
    "chargeAmountCurrency",
    "meansOfPayment",
    "manualIndicator",
    "customerForTerminalCharges",
    "chargingAttributes",
    "entitlementSubCode",
    "chargeHeadName",
    "terminalChargesDescription"
})
public class AWBTerminalChargeType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String otherChargeCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String stationCode;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String exportImportTransitFlag;
    @XmlElement(required = true)
    protected String prepaidCollectIndicator;
    @XmlElement(required = true)
    protected String chargeEntitlementCodeParty;
    @XmlElement(required = true)
    protected BigDecimal chargeAmount;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String chargeAmountCurrency;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String meansOfPayment;
    protected String manualIndicator;
    protected List<CustomerDetailsType> customerForTerminalCharges;
    protected AWBTerminalChargeType.ChargingAttributes chargingAttributes;
    protected String entitlementSubCode;
    protected String chargeHeadName;
    protected String terminalChargesDescription;

    /**
     * Gets the value of the otherChargeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherChargeCode() {
        return otherChargeCode;
    }

    /**
     * Sets the value of the otherChargeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherChargeCode(String value) {
        this.otherChargeCode = value;
    }

    /**
     * Gets the value of the stationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStationCode() {
        return stationCode;
    }

    /**
     * Sets the value of the stationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStationCode(String value) {
        this.stationCode = value;
    }

    /**
     * Gets the value of the exportImportTransitFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportImportTransitFlag() {
        return exportImportTransitFlag;
    }

    /**
     * Sets the value of the exportImportTransitFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportImportTransitFlag(String value) {
        this.exportImportTransitFlag = value;
    }

    /**
     * Gets the value of the prepaidCollectIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrepaidCollectIndicator() {
        return prepaidCollectIndicator;
    }

    /**
     * Sets the value of the prepaidCollectIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrepaidCollectIndicator(String value) {
        this.prepaidCollectIndicator = value;
    }

    /**
     * Gets the value of the chargeEntitlementCodeParty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeEntitlementCodeParty() {
        return chargeEntitlementCodeParty;
    }

    /**
     * Sets the value of the chargeEntitlementCodeParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeEntitlementCodeParty(String value) {
        this.chargeEntitlementCodeParty = value;
    }

    /**
     * Gets the value of the chargeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getChargeAmount() {
        return chargeAmount;
    }

    /**
     * Sets the value of the chargeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setChargeAmount(BigDecimal value) {
        this.chargeAmount = value;
    }

    /**
     * Gets the value of the chargeAmountCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeAmountCurrency() {
        return chargeAmountCurrency;
    }

    /**
     * Sets the value of the chargeAmountCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeAmountCurrency(String value) {
        this.chargeAmountCurrency = value;
    }

    /**
     * Gets the value of the meansOfPayment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeansOfPayment() {
        return meansOfPayment;
    }

    /**
     * Sets the value of the meansOfPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeansOfPayment(String value) {
        this.meansOfPayment = value;
    }

    /**
     * Gets the value of the manualIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManualIndicator() {
        return manualIndicator;
    }

    /**
     * Sets the value of the manualIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManualIndicator(String value) {
        this.manualIndicator = value;
    }

    /**
     * Gets the value of the customerForTerminalCharges property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customerForTerminalCharges property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomerForTerminalCharges().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomerDetailsType }
     * 
     * 
     */
    public List<CustomerDetailsType> getCustomerForTerminalCharges() {
        if (customerForTerminalCharges == null) {
            customerForTerminalCharges = new ArrayList<CustomerDetailsType>();
        }
        return this.customerForTerminalCharges;
    }

    /**
     * Gets the value of the chargingAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link AWBTerminalChargeType.ChargingAttributes }
     *     
     */
    public AWBTerminalChargeType.ChargingAttributes getChargingAttributes() {
        return chargingAttributes;
    }

    /**
     * Sets the value of the chargingAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBTerminalChargeType.ChargingAttributes }
     *     
     */
    public void setChargingAttributes(AWBTerminalChargeType.ChargingAttributes value) {
        this.chargingAttributes = value;
    }

    /**
     * Gets the value of the entitlementSubCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntitlementSubCode() {
        return entitlementSubCode;
    }

    /**
     * Sets the value of the entitlementSubCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntitlementSubCode(String value) {
        this.entitlementSubCode = value;
    }

    /**
     * Gets the value of the chargeHeadName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeHeadName() {
        return chargeHeadName;
    }

    /**
     * Sets the value of the chargeHeadName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeHeadName(String value) {
        this.chargeHeadName = value;
    }

    /**
     * Gets the value of the terminalChargesDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerminalChargesDescription() {
        return terminalChargesDescription;
    }

    /**
     * Sets the value of the terminalChargesDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminalChargesDescription(String value) {
        this.terminalChargesDescription = value;
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
     *         &lt;element name="minimumAmount" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
     *         &lt;element name="maximumAmount" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
     *         &lt;element name="chargeAmountType" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;pattern value="[a-zA-Z]{1,2}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="beginTimestamp" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
     *         &lt;element name="endTimestamp" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
     *         &lt;element name="noOfDayExemptions" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;pattern value="[0-9]{0,4}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="noOfDays" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;pattern value="[0-9]{0,4}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="rate" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;fractionDigits value="4"/>
     *               &lt;minExclusive value="0.0001"/>
     *               &lt;maxExclusive value="99999999"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="rateUnit" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;pattern value="[a-zA-Z0-9]{0,6}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="weight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
     *         &lt;element name="weightThreeDecimal" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeightThreeDecimal" minOccurs="0"/>
     *         &lt;element name="noOfPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces" minOccurs="0"/>
     *         &lt;element name="noOfItems" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;pattern value="[0-9]{1,5}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="noOfMinutes" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;pattern value="[0-9]{1,4}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="percentage" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;fractionDigits value="2"/>
     *               &lt;minInclusive value="0.01"/>
     *               &lt;maxInclusive value="100"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="baseValue" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
     *         &lt;element name="freeHours" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;pattern value="[0-9]{1,5}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="quantity" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;pattern value="[0-9]{1,5}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="time" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;pattern value="([0-1][0-9]|2[0-3]):[0-5][0-9]"/>
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
    @XmlType(name = "", propOrder = {
        "minimumAmount",
        "maximumAmount",
        "chargeAmountType",
        "beginTimestamp",
        "endTimestamp",
        "noOfDayExemptions",
        "noOfDays",
        "rate",
        "rateUnit",
        "weight",
        "weightThreeDecimal",
        "noOfPieces",
        "noOfItems",
        "noOfMinutes",
        "percentage",
        "baseValue",
        "freeHours",
        "quantity",
        "time"
    })
    public static class ChargingAttributes {

        protected BigDecimal minimumAmount;
        protected BigDecimal maximumAmount;
        protected String chargeAmountType;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String beginTimestamp;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String endTimestamp;
        protected BigInteger noOfDayExemptions;
        protected BigInteger noOfDays;
        protected BigDecimal rate;
        protected String rateUnit;
        protected BigDecimal weight;
        protected BigDecimal weightThreeDecimal;
        protected BigInteger noOfPieces;
        protected BigInteger noOfItems;
        protected Integer noOfMinutes;
        protected BigDecimal percentage;
        protected BigDecimal baseValue;
        protected Integer freeHours;
        protected Integer quantity;
        protected String time;

        /**
         * Gets the value of the minimumAmount property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getMinimumAmount() {
            return minimumAmount;
        }

        /**
         * Sets the value of the minimumAmount property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setMinimumAmount(BigDecimal value) {
            this.minimumAmount = value;
        }

        /**
         * Gets the value of the maximumAmount property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getMaximumAmount() {
            return maximumAmount;
        }

        /**
         * Sets the value of the maximumAmount property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setMaximumAmount(BigDecimal value) {
            this.maximumAmount = value;
        }

        /**
         * Gets the value of the chargeAmountType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChargeAmountType() {
            return chargeAmountType;
        }

        /**
         * Sets the value of the chargeAmountType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChargeAmountType(String value) {
            this.chargeAmountType = value;
        }

        /**
         * Gets the value of the beginTimestamp property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBeginTimestamp() {
            return beginTimestamp;
        }

        /**
         * Sets the value of the beginTimestamp property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBeginTimestamp(String value) {
            this.beginTimestamp = value;
        }

        /**
         * Gets the value of the endTimestamp property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEndTimestamp() {
            return endTimestamp;
        }

        /**
         * Sets the value of the endTimestamp property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEndTimestamp(String value) {
            this.endTimestamp = value;
        }

        /**
         * Gets the value of the noOfDayExemptions property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getNoOfDayExemptions() {
            return noOfDayExemptions;
        }

        /**
         * Sets the value of the noOfDayExemptions property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNoOfDayExemptions(BigInteger value) {
            this.noOfDayExemptions = value;
        }

        /**
         * Gets the value of the noOfDays property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getNoOfDays() {
            return noOfDays;
        }

        /**
         * Sets the value of the noOfDays property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNoOfDays(BigInteger value) {
            this.noOfDays = value;
        }

        /**
         * Gets the value of the rate property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getRate() {
            return rate;
        }

        /**
         * Sets the value of the rate property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setRate(BigDecimal value) {
            this.rate = value;
        }

        /**
         * Gets the value of the rateUnit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRateUnit() {
            return rateUnit;
        }

        /**
         * Sets the value of the rateUnit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRateUnit(String value) {
            this.rateUnit = value;
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
         * Gets the value of the noOfPieces property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getNoOfPieces() {
            return noOfPieces;
        }

        /**
         * Sets the value of the noOfPieces property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNoOfPieces(BigInteger value) {
            this.noOfPieces = value;
        }

        /**
         * Gets the value of the noOfItems property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getNoOfItems() {
            return noOfItems;
        }

        /**
         * Sets the value of the noOfItems property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNoOfItems(BigInteger value) {
            this.noOfItems = value;
        }

        /**
         * Gets the value of the noOfMinutes property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getNoOfMinutes() {
            return noOfMinutes;
        }

        /**
         * Sets the value of the noOfMinutes property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setNoOfMinutes(Integer value) {
            this.noOfMinutes = value;
        }

        /**
         * Gets the value of the percentage property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getPercentage() {
            return percentage;
        }

        /**
         * Sets the value of the percentage property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setPercentage(BigDecimal value) {
            this.percentage = value;
        }

        /**
         * Gets the value of the baseValue property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getBaseValue() {
            return baseValue;
        }

        /**
         * Sets the value of the baseValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setBaseValue(BigDecimal value) {
            this.baseValue = value;
        }

        /**
         * Gets the value of the freeHours property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getFreeHours() {
            return freeHours;
        }

        /**
         * Sets the value of the freeHours property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setFreeHours(Integer value) {
            this.freeHours = value;
        }

        /**
         * Gets the value of the quantity property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getQuantity() {
            return quantity;
        }

        /**
         * Sets the value of the quantity property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setQuantity(Integer value) {
            this.quantity = value;
        }

        /**
         * Gets the value of the time property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTime() {
            return time;
        }

        /**
         * Sets the value of the time property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTime(String value) {
            this.time = value;
        }

    }

}
