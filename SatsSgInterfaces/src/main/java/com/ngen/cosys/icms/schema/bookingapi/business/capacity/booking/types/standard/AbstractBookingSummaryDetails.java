
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AbstractBookingSummaryDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractBookingSummaryDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="totalBookedPieces" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_6"/>
 *         &lt;element name="totalBookedWeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d12"/>
 *         &lt;element name="chargeableWeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d12" minOccurs="0"/>
 *         &lt;element name="isSplitBooking" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractBookingSummaryDetails", propOrder = {
    "totalBookedPieces",
    "totalBookedWeight",
    "chargeableWeight",
    "isSplitBooking"
})
public class AbstractBookingSummaryDetails {

    @XmlElement(required = true)
    protected BigInteger totalBookedPieces;
    @XmlElement(required = true)
    protected BigDecimal totalBookedWeight;
    protected BigDecimal chargeableWeight;
    protected boolean isSplitBooking;

    /**
     * Gets the value of the totalBookedPieces property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalBookedPieces() {
        return totalBookedPieces;
    }

    /**
     * Sets the value of the totalBookedPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalBookedPieces(BigInteger value) {
        this.totalBookedPieces = value;
    }

    /**
     * Gets the value of the totalBookedWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalBookedWeight() {
        return totalBookedWeight;
    }

    /**
     * Sets the value of the totalBookedWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalBookedWeight(BigDecimal value) {
        this.totalBookedWeight = value;
    }

    /**
     * Gets the value of the chargeableWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getChargeableWeight() {
        return chargeableWeight;
    }

    /**
     * Sets the value of the chargeableWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setChargeableWeight(BigDecimal value) {
        this.chargeableWeight = value;
    }

    /**
     * Gets the value of the isSplitBooking property.
     * 
     */
    public boolean isIsSplitBooking() {
        return isSplitBooking;
    }

    /**
     * Sets the value of the isSplitBooking property.
     * 
     */
    public void setIsSplitBooking(boolean value) {
        this.isSplitBooking = value;
    }

}
