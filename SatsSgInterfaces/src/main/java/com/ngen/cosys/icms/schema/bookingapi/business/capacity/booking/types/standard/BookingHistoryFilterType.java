
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for BookingHistoryFilterType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingHistoryFilterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ubrNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ubrNumber" minOccurs="0"/>
 *         &lt;element name="shipmentIdentifierDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ShipmentIdentifierDetailType" minOccurs="0"/>
 *         &lt;element name="fromVersionNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n3" minOccurs="0"/>
 *         &lt;element name="toVersionNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n3" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingHistoryFilterType", propOrder = {
    "ubrNumber",
    "shipmentIdentifierDetails",
    "fromVersionNumber",
    "toVersionNumber"
})
public class BookingHistoryFilterType {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String ubrNumber;
    protected ShipmentIdentifierDetailType shipmentIdentifierDetails;
    protected BigInteger fromVersionNumber;
    protected BigInteger toVersionNumber;

    /**
     * Gets the value of the ubrNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUbrNumber() {
        return ubrNumber;
    }

    /**
     * Sets the value of the ubrNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUbrNumber(String value) {
        this.ubrNumber = value;
    }

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
     * Gets the value of the fromVersionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFromVersionNumber() {
        return fromVersionNumber;
    }

    /**
     * Sets the value of the fromVersionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFromVersionNumber(BigInteger value) {
        this.fromVersionNumber = value;
    }

    /**
     * Gets the value of the toVersionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getToVersionNumber() {
        return toVersionNumber;
    }

    /**
     * Sets the value of the toVersionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setToVersionNumber(BigInteger value) {
        this.toVersionNumber = value;
    }

}
