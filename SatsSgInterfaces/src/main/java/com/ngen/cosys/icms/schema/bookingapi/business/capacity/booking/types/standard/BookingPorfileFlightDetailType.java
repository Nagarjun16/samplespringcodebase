
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BookingPorfileFlightDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingPorfileFlightDetailType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}AbstractBookingPorfileFlightDetailType">
 *       &lt;sequence>
 *         &lt;element name="bookedLowerdeckOne" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ContourPositionType" minOccurs="0"/>
 *         &lt;element name="bookedLowerdeckTwo" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ContourPositionType" minOccurs="0"/>
 *         &lt;element name="bookedUpperDeckOne" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ContourPositionType" minOccurs="0"/>
 *         &lt;element name="bookedUpperDeckTwo" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ContourPositionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingPorfileFlightDetailType", propOrder = {
    "bookedLowerdeckOne",
    "bookedLowerdeckTwo",
    "bookedUpperDeckOne",
    "bookedUpperDeckTwo"
})
public class BookingPorfileFlightDetailType
    extends AbstractBookingPorfileFlightDetailType
{

    protected ContourPositionType bookedLowerdeckOne;
    protected ContourPositionType bookedLowerdeckTwo;
    protected ContourPositionType bookedUpperDeckOne;
    protected ContourPositionType bookedUpperDeckTwo;

    /**
     * Gets the value of the bookedLowerdeckOne property.
     * 
     * @return
     *     possible object is
     *     {@link ContourPositionType }
     *     
     */
    public ContourPositionType getBookedLowerdeckOne() {
        return bookedLowerdeckOne;
    }

    /**
     * Sets the value of the bookedLowerdeckOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContourPositionType }
     *     
     */
    public void setBookedLowerdeckOne(ContourPositionType value) {
        this.bookedLowerdeckOne = value;
    }

    /**
     * Gets the value of the bookedLowerdeckTwo property.
     * 
     * @return
     *     possible object is
     *     {@link ContourPositionType }
     *     
     */
    public ContourPositionType getBookedLowerdeckTwo() {
        return bookedLowerdeckTwo;
    }

    /**
     * Sets the value of the bookedLowerdeckTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContourPositionType }
     *     
     */
    public void setBookedLowerdeckTwo(ContourPositionType value) {
        this.bookedLowerdeckTwo = value;
    }

    /**
     * Gets the value of the bookedUpperDeckOne property.
     * 
     * @return
     *     possible object is
     *     {@link ContourPositionType }
     *     
     */
    public ContourPositionType getBookedUpperDeckOne() {
        return bookedUpperDeckOne;
    }

    /**
     * Sets the value of the bookedUpperDeckOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContourPositionType }
     *     
     */
    public void setBookedUpperDeckOne(ContourPositionType value) {
        this.bookedUpperDeckOne = value;
    }

    /**
     * Gets the value of the bookedUpperDeckTwo property.
     * 
     * @return
     *     possible object is
     *     {@link ContourPositionType }
     *     
     */
    public ContourPositionType getBookedUpperDeckTwo() {
        return bookedUpperDeckTwo;
    }

    /**
     * Sets the value of the bookedUpperDeckTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContourPositionType }
     *     
     */
    public void setBookedUpperDeckTwo(ContourPositionType value) {
        this.bookedUpperDeckTwo = value;
    }

}
