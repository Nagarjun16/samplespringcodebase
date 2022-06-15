
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.framework.services.jaxws.types.common.PageType;


/**
 * <p>Java class for BookingsAWBDetailsByPage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingsAWBDetailsByPage">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ibsplc.com/icargo/services}pageType">
 *       &lt;sequence>
 *         &lt;element name="bookingAWBDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingsAWBDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingsAWBDetailsByPage", propOrder = {
    "bookingAWBDetails"
})
public class BookingsAWBDetailsByPage
    extends PageType
{

    protected List<BookingsAWBDetailsType> bookingAWBDetails;

    /**
     * Gets the value of the bookingAWBDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bookingAWBDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBookingAWBDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BookingsAWBDetailsType }
     * 
     * 
     */
    public List<BookingsAWBDetailsType> getBookingAWBDetails() {
        if (bookingAWBDetails == null) {
            bookingAWBDetails = new ArrayList<BookingsAWBDetailsType>();
        }
        return this.bookingAWBDetails;
    }

}
