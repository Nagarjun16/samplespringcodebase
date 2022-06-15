
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				The node contains a summary of the prepaid charges
 * 				printed on the AWB document
 * 			
 * 
 * <p>Java class for AWBPrepaidChargesSummmaryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBPrepaidChargesSummmaryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="totalWeightChargeAmount" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="totalValuationChargeAmount" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="totalTaxes" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="totalOtherChargesDueAgent" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="totalOtherChargesDueCarrier" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="totalPrepaidChargesSummary" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AWBPrepaidChargesSummmaryType", propOrder = {
    "totalWeightChargeAmount",
    "totalValuationChargeAmount",
    "totalTaxes",
    "totalOtherChargesDueAgent",
    "totalOtherChargesDueCarrier",
    "totalPrepaidChargesSummary"
})
public class AWBPrepaidChargesSummmaryType {

    protected BigDecimal totalWeightChargeAmount;
    protected BigDecimal totalValuationChargeAmount;
    protected BigDecimal totalTaxes;
    protected BigDecimal totalOtherChargesDueAgent;
    protected BigDecimal totalOtherChargesDueCarrier;
    @XmlElement(required = true)
    protected BigDecimal totalPrepaidChargesSummary;

    /**
     * Gets the value of the totalWeightChargeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalWeightChargeAmount() {
        return totalWeightChargeAmount;
    }

    /**
     * Sets the value of the totalWeightChargeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalWeightChargeAmount(BigDecimal value) {
        this.totalWeightChargeAmount = value;
    }

    /**
     * Gets the value of the totalValuationChargeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalValuationChargeAmount() {
        return totalValuationChargeAmount;
    }

    /**
     * Sets the value of the totalValuationChargeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalValuationChargeAmount(BigDecimal value) {
        this.totalValuationChargeAmount = value;
    }

    /**
     * Gets the value of the totalTaxes property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalTaxes() {
        return totalTaxes;
    }

    /**
     * Sets the value of the totalTaxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalTaxes(BigDecimal value) {
        this.totalTaxes = value;
    }

    /**
     * Gets the value of the totalOtherChargesDueAgent property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalOtherChargesDueAgent() {
        return totalOtherChargesDueAgent;
    }

    /**
     * Sets the value of the totalOtherChargesDueAgent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalOtherChargesDueAgent(BigDecimal value) {
        this.totalOtherChargesDueAgent = value;
    }

    /**
     * Gets the value of the totalOtherChargesDueCarrier property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalOtherChargesDueCarrier() {
        return totalOtherChargesDueCarrier;
    }

    /**
     * Sets the value of the totalOtherChargesDueCarrier property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalOtherChargesDueCarrier(BigDecimal value) {
        this.totalOtherChargesDueCarrier = value;
    }

    /**
     * Gets the value of the totalPrepaidChargesSummary property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalPrepaidChargesSummary() {
        return totalPrepaidChargesSummary;
    }

    /**
     * Sets the value of the totalPrepaidChargesSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalPrepaidChargesSummary(BigDecimal value) {
        this.totalPrepaidChargesSummary = value;
    }

}
