
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.AdditionalIndicatorType;


/**
 * <p>Java class for FindBookingsResponseData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindBookingsResponseData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestID" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t300"/>
 *         &lt;element name="status">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="S"/>
 *               &lt;enumeration value="F"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="bookingDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="indicators" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}AdditionalIndicatorType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="errorDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ErrorDetailType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="totalRecordCount" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_8" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindBookingsResponseData", propOrder = {
    "requestID",
    "status",
    "bookingDetails",
    "indicators",
    "errorDetails",
    "totalRecordCount"
})
public class FindBookingsResponseData {

    @XmlElement(required = true)
    protected String requestID;
    @XmlElement(required = true)
    protected String status;
    protected List<BookingType> bookingDetails;
    protected List<AdditionalIndicatorType> indicators;
    protected List<ErrorDetailType> errorDetails;
    protected BigInteger totalRecordCount;

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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the bookingDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bookingDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBookingDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BookingType }
     * 
     * 
     */
    public List<BookingType> getBookingDetails() {
        if (bookingDetails == null) {
            bookingDetails = new ArrayList<BookingType>();
        }
        return this.bookingDetails;
    }

    /**
     * Gets the value of the indicators property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indicators property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndicators().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdditionalIndicatorType }
     * 
     * 
     */
    public List<AdditionalIndicatorType> getIndicators() {
        if (indicators == null) {
            indicators = new ArrayList<AdditionalIndicatorType>();
        }
        return this.indicators;
    }

    /**
     * Gets the value of the errorDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ErrorDetailType }
     * 
     * 
     */
    public List<ErrorDetailType> getErrorDetails() {
        if (errorDetails == null) {
            errorDetails = new ArrayList<ErrorDetailType>();
        }
        return this.errorDetails;
    }

    /**
     * Gets the value of the totalRecordCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalRecordCount() {
        return totalRecordCount;
    }

    /**
     * Sets the value of the totalRecordCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalRecordCount(BigInteger value) {
        this.totalRecordCount = value;
    }

}
