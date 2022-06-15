
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShipmentDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipmentDetailType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}AbstractShipmentDetailType">
 *       &lt;sequence>
 *         &lt;element name="bookingShipperDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}CustomerDetailType" minOccurs="0"/>
 *         &lt;element name="bookingConsigneeDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}CustomerDetailType" minOccurs="0"/>
 *         &lt;element name="emergencyContactDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}CustomerDetailType" minOccurs="0"/>
 *         &lt;element name="notificationContactDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}CustomerDetailType" minOccurs="0"/>
 *         &lt;element name="productDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ProductDetailsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipmentDetailType", propOrder = {
    "bookingShipperDetails",
    "bookingConsigneeDetails",
    "emergencyContactDetails",
    "notificationContactDetails",
    "productDetails"
})
public class ShipmentDetailType
    extends AbstractShipmentDetailType
{

    protected CustomerDetailType bookingShipperDetails;
    protected CustomerDetailType bookingConsigneeDetails;
    protected CustomerDetailType emergencyContactDetails;
    protected CustomerDetailType notificationContactDetails;
    protected ProductDetailsType productDetails;

    /**
     * Gets the value of the bookingShipperDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerDetailType }
     *     
     */
    public CustomerDetailType getBookingShipperDetails() {
        return bookingShipperDetails;
    }

    /**
     * Sets the value of the bookingShipperDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerDetailType }
     *     
     */
    public void setBookingShipperDetails(CustomerDetailType value) {
        this.bookingShipperDetails = value;
    }

    /**
     * Gets the value of the bookingConsigneeDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerDetailType }
     *     
     */
    public CustomerDetailType getBookingConsigneeDetails() {
        return bookingConsigneeDetails;
    }

    /**
     * Sets the value of the bookingConsigneeDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerDetailType }
     *     
     */
    public void setBookingConsigneeDetails(CustomerDetailType value) {
        this.bookingConsigneeDetails = value;
    }

    /**
     * Gets the value of the emergencyContactDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerDetailType }
     *     
     */
    public CustomerDetailType getEmergencyContactDetails() {
        return emergencyContactDetails;
    }

    /**
     * Sets the value of the emergencyContactDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerDetailType }
     *     
     */
    public void setEmergencyContactDetails(CustomerDetailType value) {
        this.emergencyContactDetails = value;
    }

    /**
     * Gets the value of the notificationContactDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerDetailType }
     *     
     */
    public CustomerDetailType getNotificationContactDetails() {
        return notificationContactDetails;
    }

    /**
     * Sets the value of the notificationContactDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerDetailType }
     *     
     */
    public void setNotificationContactDetails(CustomerDetailType value) {
        this.notificationContactDetails = value;
    }

    /**
     * Gets the value of the productDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ProductDetailsType }
     *     
     */
    public ProductDetailsType getProductDetails() {
        return productDetails;
    }

    /**
     * Sets the value of the productDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductDetailsType }
     *     
     */
    public void setProductDetails(ProductDetailsType value) {
        this.productDetails = value;
    }

}
