
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard.AWBOriginDestinationType;
import com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard.AWBQuantityDetailsType;


/**
 * <p>Java class for AWBDocumentDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBDocumentDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="awbOriginDestination" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBOriginDestinationType"/>
 *         &lt;element name="quantityDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBQuantityDetailsType"/>
 *         &lt;element name="specialHandlingCodes" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}HandlingCodeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="chargeableWeight" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}d12"/>
 *         &lt;element name="awbQuality">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;/restriction>
 *           &lt;/simpleType>
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
@XmlType(name = "AWBDocumentDetailsType", propOrder = {
    "awbOriginDestination",
    "quantityDetails",
    "specialHandlingCodes",
    "chargeableWeight",
    "awbQuality"
})
public class AWBDocumentDetailsType {

    @XmlElement(required = true)
    protected AWBOriginDestinationType awbOriginDestination;
    @XmlElement(required = true)
    protected AWBQuantityDetailsType quantityDetails;
    protected List<HandlingCodeType> specialHandlingCodes;
    @XmlElement(required = true)
    protected BigDecimal chargeableWeight;
    @XmlElement(required = true)
    protected String awbQuality;

    /**
     * Gets the value of the awbOriginDestination property.
     * 
     * @return
     *     possible object is
     *     {@link AWBOriginDestinationType }
     *     
     */
    public AWBOriginDestinationType getAwbOriginDestination() {
        return awbOriginDestination;
    }

    /**
     * Sets the value of the awbOriginDestination property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBOriginDestinationType }
     *     
     */
    public void setAwbOriginDestination(AWBOriginDestinationType value) {
        this.awbOriginDestination = value;
    }

    /**
     * Gets the value of the quantityDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBQuantityDetailsType }
     *     
     */
    public AWBQuantityDetailsType getQuantityDetails() {
        return quantityDetails;
    }

    /**
     * Sets the value of the quantityDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBQuantityDetailsType }
     *     
     */
    public void setQuantityDetails(AWBQuantityDetailsType value) {
        this.quantityDetails = value;
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
     * Gets the value of the awbQuality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwbQuality() {
        return awbQuality;
    }

    /**
     * Sets the value of the awbQuality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwbQuality(String value) {
        this.awbQuality = value;
    }

}
