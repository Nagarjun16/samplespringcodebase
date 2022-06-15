
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BookingCommodityDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingCommodityDetailsType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}AbstractBookingProfileCommodityType">
 *       &lt;sequence>
 *         &lt;element name="handlingCodes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingCommodityDetailsType", propOrder = {
    "handlingCodes"
})
public class BookingCommodityDetailsType
    extends AbstractBookingProfileCommodityType
{

    protected String handlingCodes;

    /**
     * Gets the value of the handlingCodes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandlingCodes() {
        return handlingCodes;
    }

    /**
     * Sets the value of the handlingCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandlingCodes(String value) {
        this.handlingCodes = value;
    }

}
