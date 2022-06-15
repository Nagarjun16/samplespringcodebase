
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for BookingOverViewType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingOverViewType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reason" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t300" minOccurs="0"/>
 *         &lt;element name="origin" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode" minOccurs="0"/>
 *         &lt;element name="destination" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode" minOccurs="0"/>
 *         &lt;element name="lat" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="product" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="commodity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sccCodes" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m200" minOccurs="0"/>
 *         &lt;element name="shipmentDescription" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t100" minOccurs="0"/>
 *         &lt;element name="bookingDateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rateAndCharges" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}RateAndChargesType" minOccurs="0"/>
 *         &lt;element name="shipmentDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingShipmentDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingOverViewType", propOrder = {
    "reason",
    "origin",
    "destination",
    "lat",
    "product",
    "commodity",
    "sccCodes",
    "shipmentDescription",
    "bookingDateTime",
    "rateAndCharges",
    "shipmentDetails"
})
public class BookingOverViewType {

    protected String reason;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String origin;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String destination;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String lat;
    protected String product;
    protected String commodity;
    protected String sccCodes;
    protected String shipmentDescription;
    protected String bookingDateTime;
    protected RateAndChargesType rateAndCharges;
    protected List<BookingShipmentDetailsType> shipmentDetails;

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the origin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the value of the origin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigin(String value) {
        this.origin = value;
    }

    /**
     * Gets the value of the destination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestination(String value) {
        this.destination = value;
    }

    /**
     * Gets the value of the lat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLat() {
        return lat;
    }

    /**
     * Sets the value of the lat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLat(String value) {
        this.lat = value;
    }

    /**
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProduct(String value) {
        this.product = value;
    }

    /**
     * Gets the value of the commodity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommodity() {
        return commodity;
    }

    /**
     * Sets the value of the commodity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommodity(String value) {
        this.commodity = value;
    }

    /**
     * Gets the value of the sccCodes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSccCodes() {
        return sccCodes;
    }

    /**
     * Sets the value of the sccCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSccCodes(String value) {
        this.sccCodes = value;
    }

    /**
     * Gets the value of the shipmentDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipmentDescription() {
        return shipmentDescription;
    }

    /**
     * Sets the value of the shipmentDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipmentDescription(String value) {
        this.shipmentDescription = value;
    }

    /**
     * Gets the value of the bookingDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingDateTime() {
        return bookingDateTime;
    }

    /**
     * Sets the value of the bookingDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingDateTime(String value) {
        this.bookingDateTime = value;
    }

    /**
     * Gets the value of the rateAndCharges property.
     * 
     * @return
     *     possible object is
     *     {@link RateAndChargesType }
     *     
     */
    public RateAndChargesType getRateAndCharges() {
        return rateAndCharges;
    }

    /**
     * Sets the value of the rateAndCharges property.
     * 
     * @param value
     *     allowed object is
     *     {@link RateAndChargesType }
     *     
     */
    public void setRateAndCharges(RateAndChargesType value) {
        this.rateAndCharges = value;
    }

    /**
     * Gets the value of the shipmentDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shipmentDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShipmentDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BookingShipmentDetailsType }
     * 
     * 
     */
    public List<BookingShipmentDetailsType> getShipmentDetails() {
        if (shipmentDetails == null) {
            shipmentDetails = new ArrayList<BookingShipmentDetailsType>();
        }
        return this.shipmentDetails;
    }

}
