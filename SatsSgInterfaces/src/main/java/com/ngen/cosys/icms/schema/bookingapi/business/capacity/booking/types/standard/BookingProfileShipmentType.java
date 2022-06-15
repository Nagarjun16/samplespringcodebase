
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BookingProfileShipmentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingProfileShipmentType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}AbstractBookingProfileShipmentType">
 *       &lt;sequence>
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
@XmlType(name = "BookingProfileShipmentType", propOrder = {
    "productDetails"
})
public class BookingProfileShipmentType
    extends AbstractBookingProfileShipmentType
{

    protected ProductDetailsType productDetails;

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
