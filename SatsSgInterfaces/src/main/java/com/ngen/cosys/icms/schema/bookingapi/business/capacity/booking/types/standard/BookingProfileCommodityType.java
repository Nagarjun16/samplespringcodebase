
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.HandlingCode;


/**
 * <p>Java class for BookingProfileCommodityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingProfileCommodityType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}AbstractBookingProfileCommodityType">
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
 *         &lt;element name="dimensionDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}DimensionDetailType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingProfileCommodityType", propOrder = {
    "handlingCodes",
    "dimensionDetails"
})
public class BookingProfileCommodityType
    extends AbstractBookingProfileCommodityType
{

    protected BookingProfileCommodityType.HandlingCodes handlingCodes;
    protected List<DimensionDetailType> dimensionDetaills;

    /**
     * Gets the value of the handlingCodes property.
     * 
     * @return
     *     possible object is
     *     {@link BookingProfileCommodityType.HandlingCodes }
     *     
     */
    public BookingProfileCommodityType.HandlingCodes getHandlingCodes() {
        return handlingCodes;
    }

    /**
     * Sets the value of the handlingCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingProfileCommodityType.HandlingCodes }
     *     
     */
    public void setHandlingCodes(BookingProfileCommodityType.HandlingCodes value) {
        this.handlingCodes = value;
    }

    /**
     * Gets the value of the dimensionDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dimensionDetails property.
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
