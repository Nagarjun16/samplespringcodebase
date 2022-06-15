
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListFlightInformationRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListFlightInformationRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FlightAvailabilityFilterType" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01}FlightAvailabilityFilterType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListFlightInformationRequestType", namespace = "http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01", propOrder = {
    "flightAvailabilityFilterType"
})
public class ListFlightInformationRequestType {

    @XmlElement(name = "FlightAvailabilityFilterType", required = true)
    protected FlightAvailabilityFilterType flightAvailabilityFilterType;

    /**
     * Gets the value of the flightAvailabilityFilterType property.
     * 
     * @return
     *     possible object is
     *     {@link FlightAvailabilityFilterType }
     *     
     */
    public FlightAvailabilityFilterType getFlightAvailabilityFilterType() {
        return flightAvailabilityFilterType;
    }

    /**
     * Sets the value of the flightAvailabilityFilterType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlightAvailabilityFilterType }
     *     
     */
    public void setFlightAvailabilityFilterType(FlightAvailabilityFilterType value) {
        this.flightAvailabilityFilterType = value;
    }

}
