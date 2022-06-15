
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShipmentBookingDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipmentBookingDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shipmentIdentifierDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ShipmentIdentifierDetailType" minOccurs="0"/>
 *         &lt;element name="statedPieces" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="statedWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight"/>
 *         &lt;element name="statedVolume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolume"/>
 *         &lt;element name="volumeUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="weightUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipmentBookingDetailsType", propOrder = {
    "shipmentIdentifierDetails",
    "statedPieces",
    "statedWeight",
    "statedVolume",
    "volumeUnit",
    "weightUnit"
})
public class ShipmentBookingDetailsType {

    protected ShipmentIdentifierDetailType shipmentIdentifierDetails;
    protected int statedPieces;
    @XmlElement(required = true)
    protected BigDecimal statedWeight;
    @XmlElement(required = true)
    protected BigDecimal statedVolume;
    @XmlElement(required = true)
    protected String volumeUnit;
    @XmlElement(required = true)
    protected String weightUnit;

    /**
     * Gets the value of the shipmentIdentifierDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentIdentifierDetailType }
     *     
     */
    public ShipmentIdentifierDetailType getShipmentIdentifierDetails() {
        return shipmentIdentifierDetails;
    }

    /**
     * Sets the value of the shipmentIdentifierDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentIdentifierDetailType }
     *     
     */
    public void setShipmentIdentifierDetails(ShipmentIdentifierDetailType value) {
        this.shipmentIdentifierDetails = value;
    }

    /**
     * Gets the value of the statedPieces property.
     * 
     */
    public int getStatedPieces() {
        return statedPieces;
    }

    /**
     * Sets the value of the statedPieces property.
     * 
     */
    public void setStatedPieces(int value) {
        this.statedPieces = value;
    }

    /**
     * Gets the value of the statedWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStatedWeight() {
        return statedWeight;
    }

    /**
     * Sets the value of the statedWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStatedWeight(BigDecimal value) {
        this.statedWeight = value;
    }

    /**
     * Gets the value of the statedVolume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStatedVolume() {
        return statedVolume;
    }

    /**
     * Sets the value of the statedVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStatedVolume(BigDecimal value) {
        this.statedVolume = value;
    }

    /**
     * Gets the value of the volumeUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVolumeUnit() {
        return volumeUnit;
    }

    /**
     * Sets the value of the volumeUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVolumeUnit(String value) {
        this.volumeUnit = value;
    }

    /**
     * Gets the value of the weightUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeightUnit() {
        return weightUnit;
    }

    /**
     * Sets the value of the weightUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeightUnit(String value) {
        this.weightUnit = value;
    }

}
