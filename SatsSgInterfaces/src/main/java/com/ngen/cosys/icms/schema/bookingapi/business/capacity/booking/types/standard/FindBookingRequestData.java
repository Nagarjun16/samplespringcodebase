
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.OperationalFlagType;


/**
 * <p>Java class for FindBookingRequestData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindBookingRequestData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestID" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t300"/>
 *         &lt;element name="operationalFlag" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}operationalFlagType" minOccurs="0"/>
 *         &lt;element name="bookingDetailsFilter" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingFilterType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindBookingRequestData", propOrder = {
    "requestID",
    "operationalFlag",
    "bookingDetailsFilter"
})
public class FindBookingRequestData {

    @XmlElement(required = true)
    protected String requestID;
    @XmlSchemaType(name = "string")
    protected OperationalFlagType operationalFlag;
    @XmlElement(required = true)
    protected BookingFilterType bookingDetailsFilter;

    /**
     * Gets the value of the requestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestID() {
        return requestID;
    }

    /**
     * Sets the value of the requestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestID(String value) {
        this.requestID = value;
    }

    /**
     * Gets the value of the operationalFlag property.
     * 
     * @return
     *     possible object is
     *     {@link OperationalFlagType }
     *     
     */
    public OperationalFlagType getOperationalFlag() {
        return operationalFlag;
    }

    /**
     * Sets the value of the operationalFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationalFlagType }
     *     
     */
    public void setOperationalFlag(OperationalFlagType value) {
        this.operationalFlag = value;
    }

    /**
     * Gets the value of the bookingDetailsFilter property.
     * 
     * @return
     *     possible object is
     *     {@link BookingFilterType }
     *     
     */
    public BookingFilterType getBookingDetailsFilter() {
        return bookingDetailsFilter;
    }

    /**
     * Sets the value of the bookingDetailsFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingFilterType }
     *     
     */
    public void setBookingDetailsFilter(BookingFilterType value) {
        this.bookingDetailsFilter = value;
    }

}
