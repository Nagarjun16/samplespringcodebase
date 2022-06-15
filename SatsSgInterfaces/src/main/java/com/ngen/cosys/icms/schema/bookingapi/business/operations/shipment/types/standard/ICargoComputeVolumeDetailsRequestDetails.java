
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for iCargoComputeVolumeDetailsRequestDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="iCargoComputeVolumeDetailsRequestDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestID" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5"/>
 *         &lt;element name="ratelineDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBRateLineType" maxOccurs="unbounded"/>
 *         &lt;element name="errorDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ErrorDetailsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "iCargoComputeVolumeDetailsRequestDetails", propOrder = {
    "requestID",
    "ratelineDetails",
    "errorDetails"
})
public class ICargoComputeVolumeDetailsRequestDetails {

    @XmlElement(required = true)
    protected BigInteger requestID;
    @XmlElement(required = true)
    protected List<AWBRateLineType> ratelineDetails;
    protected ErrorDetailsType errorDetails;

    /**
     * Gets the value of the requestID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRequestID() {
        return requestID;
    }

    /**
     * Sets the value of the requestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRequestID(BigInteger value) {
        this.requestID = value;
    }

    /**
     * Gets the value of the ratelineDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ratelineDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRatelineDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AWBRateLineType }
     * 
     * 
     */
    public List<AWBRateLineType> getRatelineDetails() {
        if (ratelineDetails == null) {
            ratelineDetails = new ArrayList<AWBRateLineType>();
        }
        return this.ratelineDetails;
    }

    /**
     * Gets the value of the errorDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorDetailsType }
     *     
     */
    public ErrorDetailsType getErrorDetails() {
        return errorDetails;
    }

    /**
     * Sets the value of the errorDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorDetailsType }
     *     
     */
    public void setErrorDetails(ErrorDetailsType value) {
        this.errorDetails = value;
    }

}
