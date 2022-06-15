
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BookingDetailsForAWBType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingDetailsForAWBType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bookingFlightDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookingFlightDetailsType" maxOccurs="unbounded"/>
 *         &lt;element name="bookingSummaryDetails">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="totalBookedPieces" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_6"/>
 *                   &lt;element name="totalBookedWeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d12"/>
 *                   &lt;element name="totalBookedVolume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolume" minOccurs="0"/>
 *                   &lt;element name="chargeableWeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d12" minOccurs="0"/>
 *                   &lt;element name="isSplitBooking" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="specialHandlingCodes" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}HandlingCodeType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingDetailsForAWBType", propOrder = {
    "bookingFlightDetails",
    "bookingSummaryDetails"
})
public class BookingDetailsForAWBType {

    @XmlElement(required = true)
    protected List<BookingFlightDetailsType> bookingFlightDetails;
    @XmlElement(required = true)
    protected BookingDetailsForAWBType.BookingSummaryDetails bookingSummaryDetails;

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
     * {@link BookingFlightDetailsType }
     * 
     * 
     */
    public List<BookingFlightDetailsType> getBookingFlightDetails() {
        if (bookingFlightDetails == null) {
            bookingFlightDetails = new ArrayList<BookingFlightDetailsType>();
        }
        return this.bookingFlightDetails;
    }

    /**
     * Gets the value of the bookingSummaryDetails property.
     * 
     * @return
     *     possible object is
     *     {@link BookingDetailsForAWBType.BookingSummaryDetails }
     *     
     */
    public BookingDetailsForAWBType.BookingSummaryDetails getBookingSummaryDetails() {
        return bookingSummaryDetails;
    }

    /**
     * Sets the value of the bookingSummaryDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingDetailsForAWBType.BookingSummaryDetails }
     *     
     */
    public void setBookingSummaryDetails(BookingDetailsForAWBType.BookingSummaryDetails value) {
        this.bookingSummaryDetails = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="totalBookedPieces" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_6"/>
     *         &lt;element name="totalBookedWeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d12"/>
     *         &lt;element name="totalBookedVolume" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedVolume" minOccurs="0"/>
     *         &lt;element name="chargeableWeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d12" minOccurs="0"/>
     *         &lt;element name="isSplitBooking" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="specialHandlingCodes" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}HandlingCodeType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "totalBookedPieces",
        "totalBookedWeight",
        "totalBookedVolume",
        "chargeableWeight",
        "isSplitBooking",
        "specialHandlingCodes"
    })
    public static class BookingSummaryDetails {

        @XmlElement(required = true)
        protected BigInteger totalBookedPieces;
        @XmlElement(required = true)
        protected BigDecimal totalBookedWeight;
        protected BigDecimal totalBookedVolume;
        protected BigDecimal chargeableWeight;
        protected boolean isSplitBooking;
        protected List<HandlingCodeType> specialHandlingCodes;

        /**
         * Gets the value of the totalBookedPieces property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTotalBookedPieces() {
            return totalBookedPieces;
        }

        /**
         * Sets the value of the totalBookedPieces property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTotalBookedPieces(BigInteger value) {
            this.totalBookedPieces = value;
        }

        /**
         * Gets the value of the totalBookedWeight property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTotalBookedWeight() {
            return totalBookedWeight;
        }

        /**
         * Sets the value of the totalBookedWeight property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTotalBookedWeight(BigDecimal value) {
            this.totalBookedWeight = value;
        }

        /**
         * Gets the value of the totalBookedVolume property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTotalBookedVolume() {
            return totalBookedVolume;
        }

        /**
         * Sets the value of the totalBookedVolume property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTotalBookedVolume(BigDecimal value) {
            this.totalBookedVolume = value;
        }

        /**
         * Gets the value of the chargeableWeight property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getChargeableWeight() {
            return chargeableWeight;
        }

        /**
         * Sets the value of the chargeableWeight property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setChargeableWeight(BigDecimal value) {
            this.chargeableWeight = value;
        }

        /**
         * Gets the value of the isSplitBooking property.
         * 
         */
        public boolean isIsSplitBooking() {
            return isSplitBooking;
        }

        /**
         * Sets the value of the isSplitBooking property.
         * 
         */
        public void setIsSplitBooking(boolean value) {
            this.isSplitBooking = value;
        }

        /**
         * Gets the value of the specialHandlingCodes property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the specialHandlingCodes property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSpecialHandlingCodes().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HandlingCodeType }
         * 
         * 
         */
        public List<HandlingCodeType> getSpecialHandlingCodes() {
            if (specialHandlingCodes == null) {
                specialHandlingCodes = new ArrayList<HandlingCodeType>();
            }
            return this.specialHandlingCodes;
        }

    }

}
