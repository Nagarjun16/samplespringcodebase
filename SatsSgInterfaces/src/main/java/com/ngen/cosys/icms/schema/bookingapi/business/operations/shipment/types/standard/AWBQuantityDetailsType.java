
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.WeightUnit;


/**
 * 
 * 				AWB Quantity Details node contains stated pieces and
 * 				weight information of the AWB
 * 			
 * 
 * <p>Java class for AWBQuantityDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBQuantityDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="totalNumberOfPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces"/>
 *         &lt;element name="weightCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}weightUnit"/>
 *         &lt;element name="totalStatedWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight"/>
 *         &lt;element name="totalStatedWeightThreeDecimal" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeightThreeDecimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AWBQuantityDetailsType", propOrder = {
    "totalNumberOfPieces",
    "weightCode",
    "totalStatedWeight",
    "totalStatedWeightThreeDecimal"
})
public class AWBQuantityDetailsType {

    @XmlElement(required = true)
    protected BigInteger totalNumberOfPieces;
    @XmlElement(required = true)
    @XmlSchemaType(name = "token")
    protected WeightUnit weightCode;
    @XmlElement(required = true)
    protected BigDecimal totalStatedWeight;
    protected BigDecimal totalStatedWeightThreeDecimal;

    /**
     * Gets the value of the totalNumberOfPieces property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalNumberOfPieces() {
        return totalNumberOfPieces;
    }

    /**
     * Sets the value of the totalNumberOfPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalNumberOfPieces(BigInteger value) {
        this.totalNumberOfPieces = value;
    }

    /**
     * Gets the value of the weightCode property.
     * 
     * @return
     *     possible object is
     *     {@link WeightUnit }
     *     
     */
    public WeightUnit getWeightCode() {
        return weightCode;
    }

    /**
     * Sets the value of the weightCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link WeightUnit }
     *     
     */
    public void setWeightCode(WeightUnit value) {
        this.weightCode = value;
    }

    /**
     * Gets the value of the totalStatedWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalStatedWeight() {
        return totalStatedWeight;
    }

    /**
     * Sets the value of the totalStatedWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalStatedWeight(BigDecimal value) {
        this.totalStatedWeight = value;
    }

    /**
     * Gets the value of the totalStatedWeightThreeDecimal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalStatedWeightThreeDecimal() {
        return totalStatedWeightThreeDecimal;
    }

    /**
     * Sets the value of the totalStatedWeightThreeDecimal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalStatedWeightThreeDecimal(BigDecimal value) {
        this.totalStatedWeightThreeDecimal = value;
    }

}
