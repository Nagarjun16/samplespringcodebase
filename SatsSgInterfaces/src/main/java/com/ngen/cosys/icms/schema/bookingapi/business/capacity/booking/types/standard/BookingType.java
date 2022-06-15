
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.Setter;


/**
 * <p>Java class for BookingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}AbstractBookingType">
 *       &lt;sequence>
 *         &lt;element name="shipmentIdentifierDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ShipmentIdentifierDetailType" minOccurs="0"/>
 *         &lt;element name="shipmentDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ShipmentDetailType"/>
 *         &lt;element name="bookingCommodityDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingDetailType" maxOccurs="unbounded"/>
 *         &lt;element name="otherChargeDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}OtherChargeDetailType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="bookingFlightDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingFlightDetailType" maxOccurs="unbounded"/>
 *         &lt;element name="uldBookingIdentifiers" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}UldBookingIdentifierType" minOccurs="0"/>
 *         &lt;element name="shipmentScreeningInformation" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ShipmentScreeningType" minOccurs="0"/>
 *         &lt;element name="dangerousGoodsInformation" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}DangerousGoodsType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="emergencyDangerousGoodsContact" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}emergencyDangerousGoodsContactType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="bookableProductServices" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookableProductServicesType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="correlationId" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}iCargoCorrelationId" minOccurs="0"/>
 *         &lt;element name="promoCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t50" minOccurs="0"/>
 *         &lt;element name="uniqueReference" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}UniqueReferenceType" minOccurs="0"/>
 *         &lt;element name="quotationID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bookingFlightPairDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingFlightPairDetails" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingType", propOrder = {
    "shipmentIdentifierDetails",
    "shipmentDetails",
    "bookingCommodityDetails",
    "otherChargeDetails",
    "bookingFlightDetails",
    "uldBookingIdentifiers",
    "shipmentScreeningInformation",
    "dangerousGoodsInformation",
    "emergencyDangerousGoodsContact",
    "bookableProductServices",
    "correlationId",
    "promoCode",
    "uniqueReference",
    "quotationID",
    "bookingFlightPairDetails"
})
@Getter
@Setter
public class BookingType
    extends AbstractBookingType
{

    protected ShipmentIdentifierDetailType shipmentIdentifierDetails;
    @XmlElement(required = true)
    protected ShipmentDetailType shipmentDetails;
    @XmlElement(required = true)
    protected List<BookingDetailType> bookingCommodityDetails;
    protected List<OtherChargeDetailType> otherChargeDetails;
    @XmlElement(required = true)
    protected List<BookingFlightDetailType> bookingFlightDetails;
    protected UldBookingIdentifierType uldBookingIdentifiers;
    protected ShipmentScreeningType shipmentScreeningInformation;
    protected List<DangerousGoodsType> dangerousGoodsInformation;
    protected List<EmergencyDangerousGoodsContactType> emergencyDangerousGoodsContact;
    protected List<BookableProductServicesType> bookableProductServices;
    protected String correlationId;
    protected String promoCode;
    protected UniqueReferenceType uniqueReference;
    protected String quotationID;
    protected BookingFlightPairDetails bookingFlightPairDetails;

    /**
     * Gets the value of the shipmentIdentifierDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentIdentifierDetailType }
     *     
     */
    public ShipmentIdentifierDetailType getShipmentIdentifierDetails() {
        return shipmentIdentifierDetails;
    }

    /**
     * Sets the value of the shipmentIdentifierDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentIdentifierDetailType }
     *     
     */
    public void setShipmentIdentifierDetails(ShipmentIdentifierDetailType value) {
        this.shipmentIdentifierDetails = value;
    }

    /**
     * Gets the value of the shipmentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentDetailType }
     *     
     */
    public ShipmentDetailType getShipmentDetails() {
        return shipmentDetails;
    }

    /**
     * Sets the value of the shipmentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentDetailType }
     *     
     */
    public void setShipmentDetails(ShipmentDetailType value) {
        this.shipmentDetails = value;
    }

    /**
     * Gets the value of the bookingCommodityDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bookingCommodityDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBookingCommodityDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BookingDetailType }
     * 
     * 
     */
    public List<BookingDetailType> getBookingCommodityDetails() {
        if (bookingCommodityDetails == null) {
            bookingCommodityDetails = new ArrayList<BookingDetailType>();
        }
        return this.bookingCommodityDetails;
    }

    /**
     * Gets the value of the otherChargeDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otherChargeDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtherChargeDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OtherChargeDetailType }
     * 
     * 
     */
    public List<OtherChargeDetailType> getOtherChargeDetails() {
        if (otherChargeDetails == null) {
            otherChargeDetails = new ArrayList<OtherChargeDetailType>();
        }
        return this.otherChargeDetails;
    }

    /**
     * Gets the value of the bookingFlightDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bookingFlightDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBookingFlightDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BookingFlightDetailType }
     * 
     * 
     */
    public List<BookingFlightDetailType> getBookingFlightDetails() {
        if (bookingFlightDetails == null) {
            bookingFlightDetails = new ArrayList<BookingFlightDetailType>();
        }
        return this.bookingFlightDetails;
    }

    /**
     * Gets the value of the uldBookingIdentifiers property.
     * 
     * @return
     *     possible object is
     *     {@link UldBookingIdentifierType }
     *     
     */
    public UldBookingIdentifierType getUldBookingIdentifiers() {
        return uldBookingIdentifiers;
    }

    /**
     * Sets the value of the uldBookingIdentifiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link UldBookingIdentifierType }
     *     
     */
    public void setUldBookingIdentifiers(UldBookingIdentifierType value) {
        this.uldBookingIdentifiers = value;
    }

    /**
     * Gets the value of the shipmentScreeningInformation property.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentScreeningType }
     *     
     */
    public ShipmentScreeningType getShipmentScreeningInformation() {
        return shipmentScreeningInformation;
    }

    /**
     * Sets the value of the shipmentScreeningInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentScreeningType }
     *     
     */
    public void setShipmentScreeningInformation(ShipmentScreeningType value) {
        this.shipmentScreeningInformation = value;
    }

    /**
     * Gets the value of the dangerousGoodsInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dangerousGoodsInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDangerousGoodsInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DangerousGoodsType }
     * 
     * 
     */
    public List<DangerousGoodsType> getDangerousGoodsInformation() {
        if (dangerousGoodsInformation == null) {
            dangerousGoodsInformation = new ArrayList<DangerousGoodsType>();
        }
        return this.dangerousGoodsInformation;
    }

    /**
     * Gets the value of the emergencyDangerousGoodsContact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the emergencyDangerousGoodsContact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmergencyDangerousGoodsContact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EmergencyDangerousGoodsContactType }
     * 
     * 
     */
    public List<EmergencyDangerousGoodsContactType> getEmergencyDangerousGoodsContact() {
        if (emergencyDangerousGoodsContact == null) {
            emergencyDangerousGoodsContact = new ArrayList<EmergencyDangerousGoodsContactType>();
        }
        return this.emergencyDangerousGoodsContact;
    }

    /**
     * Gets the value of the bookableProductServices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bookableProductServices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBookableProductServices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BookableProductServicesType }
     * 
     * 
     */
    public List<BookableProductServicesType> getBookableProductServices() {
        if (bookableProductServices == null) {
            bookableProductServices = new ArrayList<BookableProductServicesType>();
        }
        return this.bookableProductServices;
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

    /**
     * Gets the value of the promoCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromoCode() {
        return promoCode;
    }

    /**
     * Sets the value of the promoCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromoCode(String value) {
        this.promoCode = value;
    }

    /**
     * Gets the value of the uniqueReference property.
     * 
     * @return
     *     possible object is
     *     {@link UniqueReferenceType }
     *     
     */
    public UniqueReferenceType getUniqueReference() {
        return uniqueReference;
    }

    /**
     * Sets the value of the uniqueReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link UniqueReferenceType }
     *     
     */
    public void setUniqueReference(UniqueReferenceType value) {
        this.uniqueReference = value;
    }

    /**
     * Gets the value of the quotationID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuotationID() {
        return quotationID;
    }

    /**
     * Sets the value of the quotationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuotationID(String value) {
        this.quotationID = value;
    }

    /**
     * Gets the value of the bookingFlightPairDetails property.
     * 
     * @return
     *     possible object is
     *     {@link BookingFlightPairDetails }
     *     
     */
    public BookingFlightPairDetails getBookingFlightPairDetails() {
        return bookingFlightPairDetails;
    }

    /**
     * Sets the value of the bookingFlightPairDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingFlightPairDetails }
     *     
     */
    public void setBookingFlightPairDetails(BookingFlightPairDetails value) {
        this.bookingFlightPairDetails = value;
    }

}
