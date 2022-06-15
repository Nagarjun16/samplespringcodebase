
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BookingFlightDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingFlightDetailType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}AbstractBookingFlightDetailType">
 *       &lt;sequence>
 *         &lt;element name="bookedLowerdeckOne" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ContourPositionType" minOccurs="0"/>
 *         &lt;element name="bookedLowerdeckTwo" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ContourPositionType" minOccurs="0"/>
 *         &lt;element name="bookedUpperDeckOne" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ContourPositionType" minOccurs="0"/>
 *         &lt;element name="bookedUpperDeckTwo" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ContourPositionType" minOccurs="0"/>
 *         &lt;element name="confirmedlowerDeckOne" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ContourPositionType" minOccurs="0"/>
 *         &lt;element name="confirmedlowerDeckTwo" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ContourPositionType" minOccurs="0"/>
 *         &lt;element name="confirmedupperDeckOne" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ContourPositionType" minOccurs="0"/>
 *         &lt;element name="confirmedupperDeckTwo" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ContourPositionType" minOccurs="0"/>
 *         &lt;element name="complementarySCC" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ComplementarySCCType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="correlationId" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}iCargoCorrelationId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingFlightDetailType", propOrder = {
    "bookedLowerdeckOne",
    "bookedLowerdeckTwo",
    "bookedUpperDeckOne",
    "bookedUpperDeckTwo",
    "confirmedlowerDeckOne",
    "confirmedlowerDeckTwo",
    "confirmedupperDeckOne",
    "confirmedupperDeckTwo",
    "complementarySCC",
    "correlationId"
})
public class BookingFlightDetailType
    extends AbstractBookingFlightDetailType
{

    protected ContourPositionType bookedLowerdeckOne;
    protected ContourPositionType bookedLowerdeckTwo;
    protected ContourPositionType bookedUpperDeckOne;
    protected ContourPositionType bookedUpperDeckTwo;
    protected ContourPositionType confirmedlowerDeckOne;
    protected ContourPositionType confirmedlowerDeckTwo;
    protected ContourPositionType confirmedupperDeckOne;
    protected ContourPositionType confirmedupperDeckTwo;
    protected List<ComplementarySCCType> complementarySCC;
    protected String correlationId;

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

    /**
     * Gets the value of the confirmedlowerDeckOne property.
     * 
     * @return
     *     possible object is
     *     {@link ContourPositionType }
     *     
     */
    public ContourPositionType getConfirmedlowerDeckOne() {
        return confirmedlowerDeckOne;
    }

    /**
     * Sets the value of the confirmedlowerDeckOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContourPositionType }
     *     
     */
    public void setConfirmedlowerDeckOne(ContourPositionType value) {
        this.confirmedlowerDeckOne = value;
    }

    /**
     * Gets the value of the confirmedlowerDeckTwo property.
     * 
     * @return
     *     possible object is
     *     {@link ContourPositionType }
     *     
     */
    public ContourPositionType getConfirmedlowerDeckTwo() {
        return confirmedlowerDeckTwo;
    }

    /**
     * Sets the value of the confirmedlowerDeckTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContourPositionType }
     *     
     */
    public void setConfirmedlowerDeckTwo(ContourPositionType value) {
        this.confirmedlowerDeckTwo = value;
    }

    /**
     * Gets the value of the confirmedupperDeckOne property.
     * 
     * @return
     *     possible object is
     *     {@link ContourPositionType }
     *     
     */
    public ContourPositionType getConfirmedupperDeckOne() {
        return confirmedupperDeckOne;
    }

    /**
     * Sets the value of the confirmedupperDeckOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContourPositionType }
     *     
     */
    public void setConfirmedupperDeckOne(ContourPositionType value) {
        this.confirmedupperDeckOne = value;
    }

    /**
     * Gets the value of the confirmedupperDeckTwo property.
     * 
     * @return
     *     possible object is
     *     {@link ContourPositionType }
     *     
     */
    public ContourPositionType getConfirmedupperDeckTwo() {
        return confirmedupperDeckTwo;
    }

    /**
     * Sets the value of the confirmedupperDeckTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContourPositionType }
     *     
     */
    public void setConfirmedupperDeckTwo(ContourPositionType value) {
        this.confirmedupperDeckTwo = value;
    }

    /**
     * Gets the value of the complementarySCC property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the complementarySCC property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComplementarySCC().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplementarySCCType }
     * 
     * 
     */
    public List<ComplementarySCCType> getComplementarySCC() {
        if (complementarySCC == null) {
            complementarySCC = new ArrayList<ComplementarySCCType>();
        }
        return this.complementarySCC;
    }

    /**
     * Gets the value of the correlationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * Sets the value of the correlationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrelationId(String value) {
        this.correlationId = value;
    }

}
