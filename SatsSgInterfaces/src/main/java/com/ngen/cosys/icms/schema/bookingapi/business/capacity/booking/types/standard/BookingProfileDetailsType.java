
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BookingProfileDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingProfileDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shipmentDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingProfileShipmentType" minOccurs="0"/>
 *         &lt;element name="bookingShipperDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ShipperDetailType" minOccurs="0"/>
 *         &lt;element name="bookingConsigneeDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ShipperDetailType" minOccurs="0"/>
 *         &lt;element name="bookingCommodityDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingProfileCommodityType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="bookingFlightDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingPorfileFlightDetailType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="errorDetail" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ErrorDetailType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingProfileDetailsType", propOrder = {
    "shipmentDetails",
    "bookingShipperDetails",
    "bookingConsigneeDetails",
    "bookingCommodityDetails",
    "bookingFlightDetails",
    "errorDetail"
})
public class BookingProfileDetailsType {

    protected BookingProfileShipmentType shipmentDetails;
    protected ShipperDetailType bookingShipperDetails;
    protected ShipperDetailType bookingConsigneeDetails;
    protected List<BookingProfileCommodityType> bookingCommodityDetails;
    protected List<BookingPorfileFlightDetailType> bookingFlightDetails;
    protected List<ErrorDetailType> errorDetail;

    /**
     * Gets the value of the shipmentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link BookingProfileShipmentType }
     *     
     */
    public BookingProfileShipmentType getShipmentDetails() {
        return shipmentDetails;
    }

    /**
     * Sets the value of the shipmentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingProfileShipmentType }
     *     
     */
    public void setShipmentDetails(BookingProfileShipmentType value) {
        this.shipmentDetails = value;
    }

    /**
     * Gets the value of the bookingShipperDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ShipperDetailType }
     *     
     */
    public ShipperDetailType getBookingShipperDetails() {
        return bookingShipperDetails;
    }

    /**
     * Sets the value of the bookingShipperDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipperDetailType }
     *     
     */
    public void setBookingShipperDetails(ShipperDetailType value) {
        this.bookingShipperDetails = value;
    }

    /**
     * Gets the value of the bookingConsigneeDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ShipperDetailType }
     *     
     */
    public ShipperDetailType getBookingConsigneeDetails() {
        return bookingConsigneeDetails;
    }

    /**
     * Sets the value of the bookingConsigneeDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipperDetailType }
     *     
     */
    public void setBookingConsigneeDetails(ShipperDetailType value) {
        this.bookingConsigneeDetails = value;
    }

    /**
     * Gets the value of the bookingCommodityDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bookingCommodityDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBookingCommodityDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BookingProfileCommodityType }
     * 
     * 
     */
    public List<BookingProfileCommodityType> getBookingCommodityDetails() {
        if (bookingCommodityDetails == null) {
            bookingCommodityDetails = new ArrayList<BookingProfileCommodityType>();
        }
        return this.bookingCommodityDetails;
    }

    /**
     * Gets the value of the bookingFlightDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bookingFlightDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBookingFlightDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BookingPorfileFlightDetailType }
     * 
     * 
     */
    public List<BookingPorfileFlightDetailType> getBookingFlightDetails() {
        if (bookingFlightDetails == null) {
            bookingFlightDetails = new ArrayList<BookingPorfileFlightDetailType>();
        }
        return this.bookingFlightDetails;
    }

    /**
     * Gets the value of the errorDetail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorDetail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ErrorDetailType }
     * 
     * 
     */
    public List<ErrorDetailType> getErrorDetail() {
        if (errorDetail == null) {
            errorDetail = new ArrayList<ErrorDetailType>();
        }
        return this.errorDetail;
    }

}
