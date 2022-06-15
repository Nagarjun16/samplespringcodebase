
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Setter;


/**
 * <p>Java class for BookingFlightPairDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingFlightPairDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flightPair" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}FlightPairType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingFlightPairDetails", propOrder = {
    "flightPair"
})
@Setter
public class BookingFlightPairDetails {

    @XmlElement(required = true)
    protected List<FlightPairType> flightPair;

    /**
     * Gets the value of the flightPair property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flightPair property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlightPair().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FlightPairType }
     * 
     * 
     */
    public List<FlightPairType> getFlightPair() {
        if (flightPair == null) {
            flightPair = new ArrayList<FlightPairType>();
        }
        return this.flightPair;
    }

}
