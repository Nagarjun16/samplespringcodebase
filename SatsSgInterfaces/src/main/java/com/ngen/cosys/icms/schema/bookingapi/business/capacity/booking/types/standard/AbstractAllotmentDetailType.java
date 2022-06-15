
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AbstractAllotmentDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractAllotmentDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allotmentReference" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m30" minOccurs="0"/>
 *         &lt;element name="allotmentType" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m20" minOccurs="0"/>
 *         &lt;element name="allotmentCategory" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m30" minOccurs="0"/>
 *         &lt;element name="availableWeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16Neg"/>
 *         &lt;element name="availableVolume" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16Neg" minOccurs="0"/>
 *         &lt;element name="totalWeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16p"/>
 *         &lt;element name="totalVolume" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n16p" minOccurs="0"/>
 *         &lt;element name="availableLowerDeckOne" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_4Neg" minOccurs="0"/>
 *         &lt;element name="availableLowerDeckTwo" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_4Neg" minOccurs="0"/>
 *         &lt;element name="availableUpperDeckOne" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_4Neg" minOccurs="0"/>
 *         &lt;element name="availableUpperDeckTwo" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_4Neg" minOccurs="0"/>
 *         &lt;element name="totalLowerDeckOne" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_4" minOccurs="0"/>
 *         &lt;element name="totalLowerDeckTwo" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_4" minOccurs="0"/>
 *         &lt;element name="totalUpperDeckOne" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_4" minOccurs="0"/>
 *         &lt;element name="totalUpperDeckTwo" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_4" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractAllotmentDetailType", namespace = "http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01", propOrder = {
    "allotmentReference",
    "allotmentType",
    "allotmentCategory",
    "availableWeight",
    "availableVolume",
    "totalWeight",
    "totalVolume",
    "availableLowerDeckOne",
    "availableLowerDeckTwo",
    "availableUpperDeckOne",
    "availableUpperDeckTwo",
    "totalLowerDeckOne",
    "totalLowerDeckTwo",
    "totalUpperDeckOne",
    "totalUpperDeckTwo"
})
@XmlSeeAlso({
    AllotmentDetailType.class
})
public class AbstractAllotmentDetailType {

    protected String allotmentReference;
    protected String allotmentType;
    protected String allotmentCategory;
    @XmlElement(required = true)
    protected BigDecimal availableWeight;
    protected BigDecimal availableVolume;
    @XmlElement(required = true)
    protected BigDecimal totalWeight;
    protected BigDecimal totalVolume;
    protected BigInteger availableLowerDeckOne;
    protected BigInteger availableLowerDeckTwo;
    protected BigInteger availableUpperDeckOne;
    protected BigInteger availableUpperDeckTwo;
    protected BigInteger totalLowerDeckOne;
    protected BigInteger totalLowerDeckTwo;
    protected BigInteger totalUpperDeckOne;
    protected BigInteger totalUpperDeckTwo;

    /**
     * Gets the value of the allotmentReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllotmentReference() {
        return allotmentReference;
    }

    /**
     * Sets the value of the allotmentReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllotmentReference(String value) {
        this.allotmentReference = value;
    }

    /**
     * Gets the value of the allotmentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllotmentType() {
        return allotmentType;
    }

    /**
     * Sets the value of the allotmentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllotmentType(String value) {
        this.allotmentType = value;
    }

    /**
     * Gets the value of the allotmentCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllotmentCategory() {
        return allotmentCategory;
    }

    /**
     * Sets the value of the allotmentCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllotmentCategory(String value) {
        this.allotmentCategory = value;
    }

    /**
     * Gets the value of the availableWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAvailableWeight() {
        return availableWeight;
    }

    /**
     * Sets the value of the availableWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAvailableWeight(BigDecimal value) {
        this.availableWeight = value;
    }

    /**
     * Gets the value of the availableVolume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAvailableVolume() {
        return availableVolume;
    }

    /**
     * Sets the value of the availableVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAvailableVolume(BigDecimal value) {
        this.availableVolume = value;
    }

    /**
     * Gets the value of the totalWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    /**
     * Sets the value of the totalWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalWeight(BigDecimal value) {
        this.totalWeight = value;
    }

    /**
     * Gets the value of the totalVolume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    /**
     * Sets the value of the totalVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalVolume(BigDecimal value) {
        this.totalVolume = value;
    }

    /**
     * Gets the value of the availableLowerDeckOne property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAvailableLowerDeckOne() {
        return availableLowerDeckOne;
    }

    /**
     * Sets the value of the availableLowerDeckOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAvailableLowerDeckOne(BigInteger value) {
        this.availableLowerDeckOne = value;
    }

    /**
     * Gets the value of the availableLowerDeckTwo property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAvailableLowerDeckTwo() {
        return availableLowerDeckTwo;
    }

    /**
     * Sets the value of the availableLowerDeckTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAvailableLowerDeckTwo(BigInteger value) {
        this.availableLowerDeckTwo = value;
    }

    /**
     * Gets the value of the availableUpperDeckOne property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAvailableUpperDeckOne() {
        return availableUpperDeckOne;
    }

    /**
     * Sets the value of the availableUpperDeckOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAvailableUpperDeckOne(BigInteger value) {
        this.availableUpperDeckOne = value;
    }

    /**
     * Gets the value of the availableUpperDeckTwo property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAvailableUpperDeckTwo() {
        return availableUpperDeckTwo;
    }

    /**
     * Sets the value of the availableUpperDeckTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAvailableUpperDeckTwo(BigInteger value) {
        this.availableUpperDeckTwo = value;
    }

    /**
     * Gets the value of the totalLowerDeckOne property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalLowerDeckOne() {
        return totalLowerDeckOne;
    }

    /**
     * Sets the value of the totalLowerDeckOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalLowerDeckOne(BigInteger value) {
        this.totalLowerDeckOne = value;
    }

    /**
     * Gets the value of the totalLowerDeckTwo property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalLowerDeckTwo() {
        return totalLowerDeckTwo;
    }

    /**
     * Sets the value of the totalLowerDeckTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalLowerDeckTwo(BigInteger value) {
        this.totalLowerDeckTwo = value;
    }

    /**
     * Gets the value of the totalUpperDeckOne property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalUpperDeckOne() {
        return totalUpperDeckOne;
    }

    /**
     * Sets the value of the totalUpperDeckOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalUpperDeckOne(BigInteger value) {
        this.totalUpperDeckOne = value;
    }

    /**
     * Gets the value of the totalUpperDeckTwo property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalUpperDeckTwo() {
        return totalUpperDeckTwo;
    }

    /**
     * Sets the value of the totalUpperDeckTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalUpperDeckTwo(BigInteger value) {
        this.totalUpperDeckTwo = value;
    }

}
