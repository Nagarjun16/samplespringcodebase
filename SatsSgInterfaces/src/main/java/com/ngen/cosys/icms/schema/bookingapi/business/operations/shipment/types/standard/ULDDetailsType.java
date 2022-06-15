
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ULDDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ULDDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ULDNumberDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ULDNumberDetailsType" minOccurs="0"/>
 *         &lt;element name="ULDWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *         &lt;element name="slacPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}slacPieces" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ULDDetailsType", propOrder = {
    "uldNumberDetails",
    "uldWeight",
    "slacPieces"
})
public class ULDDetailsType {

    @XmlElement(name = "ULDNumberDetails")
    protected ULDNumberDetailsType uldNumberDetails;
    @XmlElement(name = "ULDWeight")
    protected BigDecimal uldWeight;
    protected BigInteger slacPieces;

    /**
     * Gets the value of the uldNumberDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ULDNumberDetailsType }
     *     
     */
    public ULDNumberDetailsType getULDNumberDetails() {
        return uldNumberDetails;
    }

    /**
     * Sets the value of the uldNumberDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ULDNumberDetailsType }
     *     
     */
    public void setULDNumberDetails(ULDNumberDetailsType value) {
        this.uldNumberDetails = value;
    }

    /**
     * Gets the value of the uldWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getULDWeight() {
        return uldWeight;
    }

    /**
     * Sets the value of the uldWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setULDWeight(BigDecimal value) {
        this.uldWeight = value;
    }

    /**
     * Gets the value of the slacPieces property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSlacPieces() {
        return slacPieces;
    }

    /**
     * Sets the value of the slacPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSlacPieces(BigInteger value) {
        this.slacPieces = value;
    }

}
