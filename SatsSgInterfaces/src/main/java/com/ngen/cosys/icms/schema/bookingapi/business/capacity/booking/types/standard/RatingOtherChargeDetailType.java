
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for RatingOtherChargeDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RatingOtherChargeDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="chargeHeadCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m1_10"/>
 *         &lt;element name="chargeHeadName" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t50"/>
 *         &lt;element name="chargeAmount" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RatingOtherChargeDetailType", namespace = "http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01", propOrder = {
    "chargeHeadCode",
    "chargeHeadName",
    "chargeAmount"
})
public class RatingOtherChargeDetailType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String chargeHeadCode;
    @XmlElement(required = true)
    protected String chargeHeadName;
    @XmlElement(required = true)
    protected BigDecimal chargeAmount;

    /**
     * Gets the value of the chargeHeadCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeHeadCode() {
        return chargeHeadCode;
    }

    /**
     * Sets the value of the chargeHeadCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeHeadCode(String value) {
        this.chargeHeadCode = value;
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

}
