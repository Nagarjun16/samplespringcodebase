
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Setter;


/**
 * <p>Java class for FlightPairType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlightPairType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flightDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}FlightPairDetailType" maxOccurs="unbounded"/>
 *         &lt;element name="pairParameterType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pairParameterCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightPairType", propOrder = {
    "flightDetails",
    "pairParameterType",
    "pairParameterCode"
})
@Setter
public class FlightPairType {

    @XmlElement(required = true)
    protected List<FlightPairDetailType> flightDetails;
    protected String pairParameterType;
    protected String pairParameterCode;

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
     * {@link FlightPairDetailType }
     * 
     * 
     */
    public List<FlightPairDetailType> getFlightDetails() {
        if (flightDetails == null) {
            flightDetails = new ArrayList<FlightPairDetailType>();
        }
        return this.flightDetails;
    }

    /**
     * Gets the value of the pairParameterType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPairParameterType() {
        return pairParameterType;
    }

    /**
     * Sets the value of the pairParameterType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPairParameterType(String value) {
        this.pairParameterType = value;
    }

    /**
     * Gets the value of the pairParameterCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPairParameterCode() {
        return pairParameterCode;
    }

    /**
     * Sets the value of the pairParameterCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPairParameterCode(String value) {
        this.pairParameterCode = value;
    }

}
