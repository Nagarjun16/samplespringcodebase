
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
 * <p>Java class for AWBValidationResponseDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBValidationResponseDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestId" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5"/>
 *         &lt;element name="status" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m1"/>
 *         &lt;element name="errorDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ErrorDetailsType" minOccurs="0"/>
 *         &lt;element name="errorDetailsWithData" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ErrorDetailsWithDataType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AWBValidationResponseDetails", propOrder = {
    "requestId",
    "status",
    "errorDetails",
    "errorDetailsWithData"
})
public class AWBValidationResponseDetails {

    @XmlElement(required = true)
    protected BigInteger requestId;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String status;
    protected ErrorDetailsType errorDetails;
    protected ErrorDetailsWithDataType errorDetailsWithData;

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

    /**
     * Gets the value of the errorDetailsWithData property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorDetailsWithDataType }
     *     
     */
    public ErrorDetailsWithDataType getErrorDetailsWithData() {
        return errorDetailsWithData;
    }

    /**
     * Sets the value of the errorDetailsWithData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorDetailsWithDataType }
     *     
     */
    public void setErrorDetailsWithData(ErrorDetailsWithDataType value) {
        this.errorDetailsWithData = value;
    }

}
