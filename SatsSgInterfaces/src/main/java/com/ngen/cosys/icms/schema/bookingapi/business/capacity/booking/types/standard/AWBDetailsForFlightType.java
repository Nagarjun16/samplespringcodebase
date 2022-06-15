
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AWBDetailsForFlightType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBDetailsForFlightType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shipmentIdentifier" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ShipmentIdentifierDetailType"/>
 *         &lt;element name="bookingDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingDetailsForAWBType" minOccurs="0"/>
 *         &lt;element name="documentDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}AWBDocumentDetailsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AWBDetailsForFlightType", propOrder = {
    "shipmentIdentifier",
    "bookingDetails",
    "documentDetails"
})
public class AWBDetailsForFlightType {

    @XmlElement(required = true)
    protected ShipmentIdentifierDetailType shipmentIdentifier;
    protected BookingDetailsForAWBType bookingDetails;
    protected AWBDocumentDetailsType documentDetails;

    /**
     * Gets the value of the shipmentIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentIdentifierDetailType }
     *     
     */
    public ShipmentIdentifierDetailType getShipmentIdentifier() {
        return shipmentIdentifier;
    }

    /**
     * Sets the value of the shipmentIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentIdentifierDetailType }
     *     
     */
    public void setShipmentIdentifier(ShipmentIdentifierDetailType value) {
        this.shipmentIdentifier = value;
    }

    /**
     * Gets the value of the bookingDetails property.
     * 
     * @return
     *     possible object is
     *     {@link BookingDetailsForAWBType }
     *     
     */
    public BookingDetailsForAWBType getBookingDetails() {
        return bookingDetails;
    }

    /**
     * Sets the value of the bookingDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingDetailsForAWBType }
     *     
     */
    public void setBookingDetails(BookingDetailsForAWBType value) {
        this.bookingDetails = value;
    }

    /**
     * Gets the value of the documentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBDocumentDetailsType }
     *     
     */
    public AWBDocumentDetailsType getDocumentDetails() {
        return documentDetails;
    }

    /**
     * Sets the value of the documentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBDocumentDetailsType }
     *     
     */
    public void setDocumentDetails(AWBDocumentDetailsType value) {
        this.documentDetails = value;
    }

}
