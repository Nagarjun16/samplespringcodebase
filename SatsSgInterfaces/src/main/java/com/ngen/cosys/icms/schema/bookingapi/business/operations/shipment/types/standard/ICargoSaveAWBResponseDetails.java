
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.AdditionalIndicatorType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.ErrorDetailsWithMIPCodeType;


/**
 * <p>Java class for iCargoSaveAWBResponseDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="iCargoSaveAWBResponseDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestId" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5"/>
 *         &lt;element name="status" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m1"/>
 *         &lt;element name="shipmentIndexDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentIndexDetails" minOccurs="0"/>
 *         &lt;element name="shipmentIndexDetailsWithVersion" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentIndexDetailsWithVersion" minOccurs="0"/>
 *         &lt;element name="errorDetails" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ErrorDetailsWithMIPCodeType" minOccurs="0"/>
 *         &lt;element name="indicators" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}AdditionalIndicatorType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "iCargoSaveAWBResponseDetails", propOrder = {
    "requestId",
    "status",
    "shipmentIndexDetails",
    "shipmentIndexDetailsWithVersion",
    "errorDetails",
    "indicators"
})
public class ICargoSaveAWBResponseDetails {

    @XmlElement(required = true)
    protected BigInteger requestId;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String status;
    protected ShipmentIndexDetails shipmentIndexDetails;
    protected ShipmentIndexDetailsWithVersion shipmentIndexDetailsWithVersion;
    protected ErrorDetailsWithMIPCodeType errorDetails;
    protected List<AdditionalIndicatorType> indicators;

    /**
     * Gets the value of the requestId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRequestId(BigInteger value) {
        this.requestId = value;
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
     * Gets the value of the shipmentIndexDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentIndexDetails }
     *     
     */
    public ShipmentIndexDetails getShipmentIndexDetails() {
        return shipmentIndexDetails;
    }

    /**
     * Sets the value of the shipmentIndexDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentIndexDetails }
     *     
     */
    public void setShipmentIndexDetails(ShipmentIndexDetails value) {
        this.shipmentIndexDetails = value;
    }

    /**
     * Gets the value of the shipmentIndexDetailsWithVersion property.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentIndexDetailsWithVersion }
     *     
     */
    public ShipmentIndexDetailsWithVersion getShipmentIndexDetailsWithVersion() {
        return shipmentIndexDetailsWithVersion;
    }

    /**
     * Sets the value of the shipmentIndexDetailsWithVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentIndexDetailsWithVersion }
     *     
     */
    public void setShipmentIndexDetailsWithVersion(ShipmentIndexDetailsWithVersion value) {
        this.shipmentIndexDetailsWithVersion = value;
    }

    /**
     * Gets the value of the errorDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorDetailsWithMIPCodeType }
     *     
     */
    public ErrorDetailsWithMIPCodeType getErrorDetails() {
        return errorDetails;
    }

    /**
     * Sets the value of the errorDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorDetailsWithMIPCodeType }
     *     
     */
    public void setErrorDetails(ErrorDetailsWithMIPCodeType value) {
        this.errorDetails = value;
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

}
