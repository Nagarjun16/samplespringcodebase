
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.ChargeCode;


/**
 * <p>Java class for AWBChargeDeclarationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBChargeDeclarationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="awbCurrencyCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}currencyCode" minOccurs="0"/>
 *         &lt;element name="chargeCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeCode" minOccurs="0"/>
 *         &lt;element name="prepaidCollectChargeDeclaration" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}PrepaidCollectChargeDeclarationType" minOccurs="0"/>
 *         &lt;element name="declaredValueForCarriage" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="declaredValueForCustoms" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}chargeAmountType" minOccurs="0"/>
 *         &lt;element name="declaredValueForInsurance" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;fractionDigits value="3"/>
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="99999999999"/>
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
@XmlType(name = "AWBChargeDeclarationType", propOrder = {
    "awbCurrencyCode",
    "chargeCode",
    "prepaidCollectChargeDeclaration",
    "declaredValueForCarriage",
    "declaredValueForCustoms",
    "declaredValueForInsurance"
})
public class AWBChargeDeclarationType {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String awbCurrencyCode;
    @XmlSchemaType(name = "token")
    protected ChargeCode chargeCode;
    protected PrepaidCollectChargeDeclarationType prepaidCollectChargeDeclaration;
    protected BigDecimal declaredValueForCarriage;
    protected BigDecimal declaredValueForCustoms;
    protected BigDecimal declaredValueForInsurance;

    /**
     * Gets the value of the awbCurrencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwbCurrencyCode() {
        return awbCurrencyCode;
    }

    /**
     * Sets the value of the awbCurrencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwbCurrencyCode(String value) {
        this.awbCurrencyCode = value;
    }

    /**
     * Gets the value of the chargeCode property.
     * 
     * @return
     *     possible object is
     *     {@link ChargeCode }
     *     
     */
    public ChargeCode getChargeCode() {
        return chargeCode;
    }

    /**
     * Sets the value of the chargeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChargeCode }
     *     
     */
    public void setChargeCode(ChargeCode value) {
        this.chargeCode = value;
    }

    /**
     * Gets the value of the prepaidCollectChargeDeclaration property.
     * 
     * @return
     *     possible object is
     *     {@link PrepaidCollectChargeDeclarationType }
     *     
     */
    public PrepaidCollectChargeDeclarationType getPrepaidCollectChargeDeclaration() {
        return prepaidCollectChargeDeclaration;
    }

    /**
     * Sets the value of the prepaidCollectChargeDeclaration property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrepaidCollectChargeDeclarationType }
     *     
     */
    public void setPrepaidCollectChargeDeclaration(PrepaidCollectChargeDeclarationType value) {
        this.prepaidCollectChargeDeclaration = value;
    }

    /**
     * Gets the value of the declaredValueForCarriage property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDeclaredValueForCarriage() {
        return declaredValueForCarriage;
    }

    /**
     * Sets the value of the declaredValueForCarriage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDeclaredValueForCarriage(BigDecimal value) {
        this.declaredValueForCarriage = value;
    }

    /**
     * Gets the value of the declaredValueForCustoms property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDeclaredValueForCustoms() {
        return declaredValueForCustoms;
    }

    /**
     * Sets the value of the declaredValueForCustoms property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDeclaredValueForCustoms(BigDecimal value) {
        this.declaredValueForCustoms = value;
    }

    /**
     * Gets the value of the declaredValueForInsurance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDeclaredValueForInsurance() {
        return declaredValueForInsurance;
    }

    /**
     * Sets the value of the declaredValueForInsurance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDeclaredValueForInsurance(BigDecimal value) {
        this.declaredValueForInsurance = value;
    }

}
