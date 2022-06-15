
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.HandlingCode;

import lombok.Setter;


/**
 * <p>Java class for BookingDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingDetailType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}AbstractBookingDetailType">
 *       &lt;sequence>
 *         &lt;element name="handlingCodes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="handlingCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}handlingCode" maxOccurs="45"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="miscellaneousRatingDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}TransportPriceType" minOccurs="0"/>
 *         &lt;element name="dimensionDetaills" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}DimensionDetailType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="complementarySCC" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ComplementarySCCType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="uldInfoDetaills" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}UldInfoType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ratingDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}RatingDetailType" minOccurs="0"/>
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
@XmlType(name = "BookingDetailType", propOrder = {
    "handlingCodes",
    "miscellaneousRatingDetails",
    "dimensionDetaills",
    "complementarySCC",
    "uldInfoDetaills",
    "ratingDetails",
    "correlationId"
})
@Setter
public class BookingDetailType
    extends AbstractBookingDetailType
{

    protected BookingDetailType.HandlingCodes handlingCodes;
    protected TransportPriceType miscellaneousRatingDetails;
    protected List<DimensionDetailType> dimensionDetaills;
    protected List<ComplementarySCCType> complementarySCC;
    protected List<UldInfoType> uldInfoDetaills;
    protected RatingDetailType ratingDetails;
    protected String correlationId;

    /**
     * Gets the value of the handlingCodes property.
     * 
     * @return
     *     possible object is
     *     {@link BookingDetailType.HandlingCodes }
     *     
     */
    public BookingDetailType.HandlingCodes getHandlingCodes() {
        return handlingCodes;
    }

    /**
     * Sets the value of the handlingCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingDetailType.HandlingCodes }
     *     
     */
    public void setHandlingCodes(BookingDetailType.HandlingCodes value) {
        this.handlingCodes = value;
    }

    /**
     * Gets the value of the miscellaneousRatingDetails property.
     * 
     * @return
     *     possible object is
     *     {@link TransportPriceType }
     *     
     */
    public TransportPriceType getMiscellaneousRatingDetails() {
        return miscellaneousRatingDetails;
    }

    /**
     * Sets the value of the miscellaneousRatingDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportPriceType }
     *     
     */
    public void setMiscellaneousRatingDetails(TransportPriceType value) {
        this.miscellaneousRatingDetails = value;
    }

    /**
     * Gets the value of the dimensionDetaills property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dimensionDetaills property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDimensionDetaills().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DimensionDetailType }
     * 
     * 
     */
    public List<DimensionDetailType> getDimensionDetaills() {
        if (dimensionDetaills == null) {
            dimensionDetaills = new ArrayList<DimensionDetailType>();
        }
        return this.dimensionDetaills;
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
     * Gets the value of the uldInfoDetaills property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the uldInfoDetaills property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUldInfoDetaills().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UldInfoType }
     * 
     * 
     */
    public List<UldInfoType> getUldInfoDetaills() {
        if (uldInfoDetaills == null) {
            uldInfoDetaills = new ArrayList<UldInfoType>();
        }
        return this.uldInfoDetaills;
    }

    /**
     * Gets the value of the ratingDetails property.
     * 
     * @return
     *     possible object is
     *     {@link RatingDetailType }
     *     
     */
    public RatingDetailType getRatingDetails() {
        return ratingDetails;
    }

    /**
     * Sets the value of the ratingDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link RatingDetailType }
     *     
     */
    public void setRatingDetails(RatingDetailType value) {
        this.ratingDetails = value;
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="handlingCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}handlingCode" maxOccurs="45"/>
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
        "handlingCode"
    })
    public static class HandlingCodes {

        @XmlElement(required = true)
        protected List<HandlingCode> handlingCode;

        /**
         * Gets the value of the handlingCode property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the handlingCode property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHandlingCode().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HandlingCode }
         * 
         * 
         */
        public List<HandlingCode> getHandlingCode() {
            if (handlingCode == null) {
                handlingCode = new ArrayList<HandlingCode>();
            }
            return this.handlingCode;
        }

    }

}
