
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FlightMapType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlightMapType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flightKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flightDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}FlightDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightMapType", propOrder = {
    "flightKey",
    "flightDetails"
})
public class FlightMapType {

    @XmlElement(required = true)
    protected String flightKey;
    protected List<FlightDetailsType> flightDetails;

    /**
     * Gets the value of the flightKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightKey() {
        return flightKey;
    }

    /**
     * Sets the value of the flightKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightKey(String value) {
        this.flightKey = value;
    }

    /**
     * Gets the value of the flightDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flightDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlightDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FlightDetailsType }
     * 
     * 
     */
    public List<FlightDetailsType> getFlightDetails() {
        if (flightDetails == null) {
            flightDetails = new ArrayList<FlightDetailsType>();
        }
        return this.flightDetails;
    }

}
