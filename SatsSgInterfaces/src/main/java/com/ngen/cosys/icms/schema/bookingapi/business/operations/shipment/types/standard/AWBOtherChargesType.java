
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
 * <p>Java class for AWBOtherChargesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBOtherChargesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="otherChargeCode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;pattern value="[a-zA-Z]{2,6}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="prepaidCollectIndicator" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="P"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="chargeEntitlementCodeParty">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="A"/>
 *               &lt;enumeration value="C"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="chargeAmount" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType"/>
 *         &lt;element name="manualIndicator" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="G"/>
 *               &lt;enumeration value="M"/>
 *               &lt;enumeration value="C"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
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
 *         &lt;element name="chargeAmountInDestinationCurrency" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="entitlementSubCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[a-zA-Z0-9]{2}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="customerDetailsForOtherCharges" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="customerRole">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                         &lt;enumeration value="S"/>
 *                         &lt;enumeration value="C"/>
 *                         &lt;enumeration value="B"/>
 *                         &lt;enumeration value="D"/>
 *                         &lt;enumeration value="T"/>
 *                         &lt;enumeration value="E"/>
 *                         &lt;enumeration value="N"/>
 *                         &lt;enumeration value="X"/>
 *                         &lt;enumeration value="Y"/>
 *                         &lt;enumeration value="Z"/>
 *                         &lt;enumeration value="A"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="customerCode" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                         &lt;pattern value="[a-zA-Z0-9]{0,15}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="customerVATNo" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                         &lt;pattern value="[a-zA-Z0-9\-]{0,17}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="chargeHeadName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="otherChargesMiscDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="chargeAmountType" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;pattern value="[a-zA-Z]{1,2}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="freeHours" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;pattern value="[0-9]{1,5}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="quantity" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;pattern value="[0-9]{1,5}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="time" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;pattern value="([0-1][0-9]|[2][0-3]):[0-5][0-9]"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="stationCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}stationCode" minOccurs="0"/>
 *                   &lt;element name="otherChargesDescription" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="0"/>
 *                         &lt;maxLength value="25"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="remarks" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="500"/>
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
@XmlType(name = "AWBOtherChargesType", propOrder = {
    "otherChargeCode",
    "prepaidCollectIndicator",
    "chargeEntitlementCodeParty",
    "chargeAmount",
    "manualIndicator",
    "meansOfPayment",
    "chargeAmountInDestinationCurrency",
    "entitlementSubCode",
    "customerDetailsForOtherCharges",
    "chargeHeadName",
    "otherChargesMiscDetails",
    "remarks"
})
public class AWBOtherChargesType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String otherChargeCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String prepaidCollectIndicator;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String chargeEntitlementCodeParty;
    @XmlElement(required = true)
    protected BigDecimal chargeAmount;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String manualIndicator;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String meansOfPayment;
    protected BigDecimal chargeAmountInDestinationCurrency;
    protected String entitlementSubCode;
    protected AWBOtherChargesType.CustomerDetailsForOtherCharges customerDetailsForOtherCharges;
    protected String chargeHeadName;
    protected AWBOtherChargesType.OtherChargesMiscDetails otherChargesMiscDetails;
    protected String remarks;

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
     * Gets the value of the chargeAmountInDestinationCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getChargeAmountInDestinationCurrency() {
        return chargeAmountInDestinationCurrency;
    }

    /**
     * Sets the value of the chargeAmountInDestinationCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setChargeAmountInDestinationCurrency(BigDecimal value) {
        this.chargeAmountInDestinationCurrency = value;
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
     * Gets the value of the customerDetailsForOtherCharges property.
     * 
     * @return
     *     possible object is
     *     {@link AWBOtherChargesType.CustomerDetailsForOtherCharges }
     *     
     */
    public AWBOtherChargesType.CustomerDetailsForOtherCharges getCustomerDetailsForOtherCharges() {
        return customerDetailsForOtherCharges;
    }

    /**
     * Sets the value of the customerDetailsForOtherCharges property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBOtherChargesType.CustomerDetailsForOtherCharges }
     *     
     */
    public void setCustomerDetailsForOtherCharges(AWBOtherChargesType.CustomerDetailsForOtherCharges value) {
        this.customerDetailsForOtherCharges = value;
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
     * Gets the value of the otherChargesMiscDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBOtherChargesType.OtherChargesMiscDetails }
     *     
     */
    public AWBOtherChargesType.OtherChargesMiscDetails getOtherChargesMiscDetails() {
        return otherChargesMiscDetails;
    }

    /**
     * Sets the value of the otherChargesMiscDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBOtherChargesType.OtherChargesMiscDetails }
     *     
     */
    public void setOtherChargesMiscDetails(AWBOtherChargesType.OtherChargesMiscDetails value) {
        this.otherChargesMiscDetails = value;
    }

    /**
     * Gets the value of the remarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the value of the remarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks(String value) {
        this.remarks = value;
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
     *         &lt;element name="customerRole">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *               &lt;enumeration value="S"/>
     *               &lt;enumeration value="C"/>
     *               &lt;enumeration value="B"/>
     *               &lt;enumeration value="D"/>
     *               &lt;enumeration value="T"/>
     *               &lt;enumeration value="E"/>
     *               &lt;enumeration value="N"/>
     *               &lt;enumeration value="X"/>
     *               &lt;enumeration value="Y"/>
     *               &lt;enumeration value="Z"/>
     *               &lt;enumeration value="A"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="customerCode" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *               &lt;pattern value="[a-zA-Z0-9]{0,15}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="customerVATNo" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *               &lt;pattern value="[a-zA-Z0-9\-]{0,17}"/>
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
        "customerRole",
        "customerCode",
        "customerVATNo"
    })
    public static class CustomerDetailsForOtherCharges {

        @XmlElement(required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String customerRole;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String customerCode;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String customerVATNo;

        /**
         * Gets the value of the customerRole property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomerRole() {
            return customerRole;
        }

        /**
         * Sets the value of the customerRole property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomerRole(String value) {
            this.customerRole = value;
        }

        /**
         * Gets the value of the customerCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomerCode() {
            return customerCode;
        }

        /**
         * Sets the value of the customerCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomerCode(String value) {
            this.customerCode = value;
        }

        /**
         * Gets the value of the customerVATNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomerVATNo() {
            return customerVATNo;
        }

        /**
         * Sets the value of the customerVATNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomerVATNo(String value) {
            this.customerVATNo = value;
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
     *         &lt;element name="chargeAmountType" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;pattern value="[a-zA-Z]{1,2}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="freeHours" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;pattern value="[0-9]{1,5}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="quantity" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;pattern value="[0-9]{1,5}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="time" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;pattern value="([0-1][0-9]|[2][0-3]):[0-5][0-9]"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="stationCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}stationCode" minOccurs="0"/>
     *         &lt;element name="otherChargesDescription" minOccurs="0">
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
    @XmlType(name = "", propOrder = {
        "chargeAmountType",
        "freeHours",
        "quantity",
        "time",
        "stationCode",
        "otherChargesDescription"
    })
    public static class OtherChargesMiscDetails {

        protected String chargeAmountType;
        protected String freeHours;
        protected String quantity;
        protected String time;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String stationCode;
        protected String otherChargesDescription;

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
         * Gets the value of the freeHours property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFreeHours() {
            return freeHours;
        }

        /**
         * Sets the value of the freeHours property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFreeHours(String value) {
            this.freeHours = value;
        }

        /**
         * Gets the value of the quantity property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getQuantity() {
            return quantity;
        }

        /**
         * Sets the value of the quantity property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setQuantity(String value) {
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
         * Gets the value of the otherChargesDescription property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOtherChargesDescription() {
            return otherChargesDescription;
        }

        /**
         * Sets the value of the otherChargesDescription property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOtherChargesDescription(String value) {
            this.otherChargesDescription = value;
        }

    }

}
