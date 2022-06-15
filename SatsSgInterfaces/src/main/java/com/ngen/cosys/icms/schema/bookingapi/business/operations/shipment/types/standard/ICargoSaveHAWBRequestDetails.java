
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for iCargoSaveHAWBRequestDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="iCargoSaveHAWBRequestDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestID" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5"/>
 *         &lt;element name="validateMasterAWB" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m1" minOccurs="0"/>
 *         &lt;element name="awbDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBSummaryType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "iCargoSaveHAWBRequestDetails", propOrder = {
    "requestID",
    "validateMasterAWB",
    "awbDetails"
})
public class ICargoSaveHAWBRequestDetails {

    @XmlElement(required = true)
    protected BigInteger requestID;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String validateMasterAWB;
    @XmlElement(required = true)
    protected AWBSummaryType awbDetails;

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
     * Gets the value of the validateMasterAWB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidateMasterAWB() {
        return validateMasterAWB;
    }

    /**
     * Sets the value of the validateMasterAWB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidateMasterAWB(String value) {
        this.validateMasterAWB = value;
    }

    /**
     * Gets the value of the awbDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBSummaryType }
     *     
     */
    public AWBSummaryType getAwbDetails() {
        return awbDetails;
    }

    /**
     * Sets the value of the awbDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBSummaryType }
     *     
     */
    public void setAwbDetails(AWBSummaryType value) {
        this.awbDetails = value;
    }

}
