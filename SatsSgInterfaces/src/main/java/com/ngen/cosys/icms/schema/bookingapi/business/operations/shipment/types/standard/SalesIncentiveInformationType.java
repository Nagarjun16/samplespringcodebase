
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * 				The node containing the Sales Incentive details
 * 			
 * 
 * <p>Java class for SalesIncentiveInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesIncentiveInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="salesIncentiveAmount" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType"/>
 *         &lt;element name="salesIncentiveIndication" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="I"/>
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="CC"/>
 *               &lt;enumeration value="DC"/>
 *               &lt;enumeration value="ID"/>
 *               &lt;enumeration value="L"/>
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="DL"/>
 *               &lt;enumeration value="AD"/>
 *               &lt;enumeration value="S"/>
 *               &lt;enumeration value="T"/>
 *               &lt;enumeration value="V"/>
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
@XmlType(name = "SalesIncentiveInformationType", propOrder = {
    "salesIncentiveAmount",
    "salesIncentiveIndication"
})
public class SalesIncentiveInformationType {

    @XmlElement(required = true)
    protected BigDecimal salesIncentiveAmount;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String salesIncentiveIndication;

    /**
     * Gets the value of the salesIncentiveAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSalesIncentiveAmount() {
        return salesIncentiveAmount;
    }

    /**
     * Sets the value of the salesIncentiveAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSalesIncentiveAmount(BigDecimal value) {
        this.salesIncentiveAmount = value;
    }

    /**
     * Gets the value of the salesIncentiveIndication property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesIncentiveIndication() {
        return salesIncentiveIndication;
    }

    /**
     * Sets the value of the salesIncentiveIndication property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesIncentiveIndication(String value) {
        this.salesIncentiveIndication = value;
    }

}
