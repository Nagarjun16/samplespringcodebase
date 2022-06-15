
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				The consignment details node has the basic information
 * 				of the AWB.
 * 			
 * 
 * <p>Java class for AWBConsignmentDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBConsignmentDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shipmentDescription" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="250"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="shipmentIdentifierDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentIdentifierDetailsType"/>
 *         &lt;element name="awbOriginDestination" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBOriginDestinationType"/>
 *         &lt;element name="quantityDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBQuantityDetailsType"/>
 *         &lt;element name="volumeDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBVolumeDetailsType" minOccurs="0"/>
 *         &lt;element name="densityGroupDetail" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}DensityGroupDetailsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AWBConsignmentDetailsType", propOrder = {
    "shipmentDescription",
    "shipmentIdentifierDetails",
    "awbOriginDestination",
    "quantityDetails",
    "volumeDetails",
    "densityGroupDetail"
})
public class AWBConsignmentDetailsType {

    protected String shipmentDescription;
    @XmlElement(required = true)
    protected ShipmentIdentifierDetailsType shipmentIdentifierDetails;
    @XmlElement(required = true)
    protected AWBOriginDestinationType awbOriginDestination;
    @XmlElement(required = true)
    protected AWBQuantityDetailsType quantityDetails;
    protected AWBVolumeDetailsType volumeDetails;
    protected DensityGroupDetailsType densityGroupDetail;

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
     * Gets the value of the shipmentIdentifierDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentIdentifierDetailsType }
     *     
     */
    public ShipmentIdentifierDetailsType getShipmentIdentifierDetails() {
        return shipmentIdentifierDetails;
    }

    /**
     * Sets the value of the shipmentIdentifierDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentIdentifierDetailsType }
     *     
     */
    public void setShipmentIdentifierDetails(ShipmentIdentifierDetailsType value) {
        this.shipmentIdentifierDetails = value;
    }

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
     * Gets the value of the volumeDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBVolumeDetailsType }
     *     
     */
    public AWBVolumeDetailsType getVolumeDetails() {
        return volumeDetails;
    }

    /**
     * Sets the value of the volumeDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBVolumeDetailsType }
     *     
     */
    public void setVolumeDetails(AWBVolumeDetailsType value) {
        this.volumeDetails = value;
    }

    /**
     * Gets the value of the densityGroupDetail property.
     * 
     * @return
     *     possible object is
     *     {@link DensityGroupDetailsType }
     *     
     */
    public DensityGroupDetailsType getDensityGroupDetail() {
        return densityGroupDetail;
    }

    /**
     * Sets the value of the densityGroupDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link DensityGroupDetailsType }
     *     
     */
    public void setDensityGroupDetail(DensityGroupDetailsType value) {
        this.densityGroupDetail = value;
    }

}
