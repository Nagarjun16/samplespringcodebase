
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for AbstractRatingDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractRatingDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rateInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spotRateInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="netRate" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;maxInclusive value="99999999"/>
 *               &lt;minInclusive value="0"/>
 *               &lt;fractionDigits value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="netCharge" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="quotationID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quotationGeneratedDateTime" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}DateTime_MiliSec" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}currencyCode" minOccurs="0"/>
 *         &lt;element name="rateLineDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01}RateLineType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="totalFreightCharge" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="totalFreightRate" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;maxInclusive value="99999999"/>
 *               &lt;minInclusive value="0"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="otherChargeDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01}RatingOtherChargeDetailType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractRatingDetailsType", namespace = "http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01", propOrder = {
    "rateInfo",
    "spotRateInfo",
    "netRate",
    "netCharge",
    "quotationID",
    "quotationGeneratedDateTime",
    "currency",
    "rateLineDetails",
    "totalFreightCharge",
    "totalFreightRate",
    "otherChargeDetails"
})
@XmlSeeAlso({
    RatingDetailsType.class
})
public class AbstractRatingDetailsType {

    protected String rateInfo;
    protected String spotRateInfo;
    protected BigDecimal netRate;
    protected BigDecimal netCharge;
    protected String quotationID;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String quotationGeneratedDateTime;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String currency;
    protected List<RateLineType> rateLineDetails;
    protected BigDecimal totalFreightCharge;
    protected BigDecimal totalFreightRate;
    protected List<RatingOtherChargeDetailType> otherChargeDetails;

    /**
     * Gets the value of the rateInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateInfo() {
        return rateInfo;
    }

    /**
     * Sets the value of the rateInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateInfo(String value) {
        this.rateInfo = value;
    }

    /**
     * Gets the value of the spotRateInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpotRateInfo() {
        return spotRateInfo;
    }

    /**
     * Sets the value of the spotRateInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpotRateInfo(String value) {
        this.spotRateInfo = value;
    }

    /**
     * Gets the value of the netRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNetRate() {
        return netRate;
    }

    /**
     * Sets the value of the netRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNetRate(BigDecimal value) {
        this.netRate = value;
    }

    /**
     * Gets the value of the netCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNetCharge() {
        return netCharge;
    }

    /**
     * Sets the value of the netCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNetCharge(BigDecimal value) {
        this.netCharge = value;
    }

    /**
     * Gets the value of the quotationID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuotationID() {
        return quotationID;
    }

    /**
     * Sets the value of the quotationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuotationID(String value) {
        this.quotationID = value;
    }

    /**
     * Gets the value of the quotationGeneratedDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuotationGeneratedDateTime() {
        return quotationGeneratedDateTime;
    }

    /**
     * Sets the value of the quotationGeneratedDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuotationGeneratedDateTime(String value) {
        this.quotationGeneratedDateTime = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the rateLineDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rateLineDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRateLineDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RateLineType }
     * 
     * 
     */
    public List<RateLineType> getRateLineDetails() {
        if (rateLineDetails == null) {
            rateLineDetails = new ArrayList<RateLineType>();
        }
        return this.rateLineDetails;
    }

    /**
     * Gets the value of the totalFreightCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalFreightCharge() {
        return totalFreightCharge;
    }

    /**
     * Sets the value of the totalFreightCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalFreightCharge(BigDecimal value) {
        this.totalFreightCharge = value;
    }

    /**
     * Gets the value of the totalFreightRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalFreightRate() {
        return totalFreightRate;
    }

    /**
     * Sets the value of the totalFreightRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalFreightRate(BigDecimal value) {
        this.totalFreightRate = value;
    }

    /**
     * Gets the value of the otherChargeDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otherChargeDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtherChargeDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RatingOtherChargeDetailType }
     * 
     * 
     */
    public List<RatingOtherChargeDetailType> getOtherChargeDetails() {
        if (otherChargeDetails == null) {
            otherChargeDetails = new ArrayList<RatingOtherChargeDetailType>();
        }
        return this.otherChargeDetails;
    }

}
