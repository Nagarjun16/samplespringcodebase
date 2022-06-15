
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

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
 * <p>Java class for CombinationCheckResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CombinationCheckResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="productCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_6"/>
 *         &lt;element name="productName" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m30"/>
 *         &lt;element name="ratingDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01}RatingDetailsType" minOccurs="0"/>
 *         &lt;element name="latestTimeOfAcceptance" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="timeOfAvailability" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="upfrontCheckResults" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01}UpFrontCheckResultsType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CombinationCheckResultType", namespace = "http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01", propOrder = {
    "productCode",
    "productName",
    "ratingDetails",
    "latestTimeOfAcceptance",
    "timeOfAvailability",
    "upfrontCheckResults"
})
public class CombinationCheckResultType {

    @XmlElement(required = true)
    protected BigInteger productCode;
    @XmlElement(required = true)
    protected String productName;
    protected RatingDetailsType ratingDetails;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String latestTimeOfAcceptance;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String timeOfAvailability;
    protected List<UpFrontCheckResultsType> upfrontCheckResults;

    /**
     * Gets the value of the productCode property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getProductCode() {
        return productCode;
    }

    /**
     * Sets the value of the productCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setProductCode(BigInteger value) {
        this.productCode = value;
    }

    /**
     * Gets the value of the productName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the value of the productName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductName(String value) {
        this.productName = value;
    }

    /**
     * Gets the value of the ratingDetails property.
     * 
     * @return
     *     possible object is
     *     {@link RatingDetailsType }
     *     
     */
    public RatingDetailsType getRatingDetails() {
        return ratingDetails;
    }

    /**
     * Sets the value of the ratingDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link RatingDetailsType }
     *     
     */
    public void setRatingDetails(RatingDetailsType value) {
        this.ratingDetails = value;
    }

    /**
     * Gets the value of the latestTimeOfAcceptance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLatestTimeOfAcceptance() {
        return latestTimeOfAcceptance;
    }

    /**
     * Sets the value of the latestTimeOfAcceptance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLatestTimeOfAcceptance(String value) {
        this.latestTimeOfAcceptance = value;
    }

    /**
     * Gets the value of the timeOfAvailability property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeOfAvailability() {
        return timeOfAvailability;
    }

    /**
     * Sets the value of the timeOfAvailability property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeOfAvailability(String value) {
        this.timeOfAvailability = value;
    }

    /**
     * Gets the value of the upfrontCheckResults property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the upfrontCheckResults property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpfrontCheckResults().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UpFrontCheckResultsType }
     * 
     * 
     */
    public List<UpFrontCheckResultsType> getUpfrontCheckResults() {
        if (upfrontCheckResults == null) {
            upfrontCheckResults = new ArrayList<UpFrontCheckResultsType>();
        }
        return this.upfrontCheckResults;
    }

}
