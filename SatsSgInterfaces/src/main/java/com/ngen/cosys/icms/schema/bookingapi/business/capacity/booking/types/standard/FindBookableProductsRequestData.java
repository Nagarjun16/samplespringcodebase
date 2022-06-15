
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FindBookableProductsRequestData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindBookableProductsRequestData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestID" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n1_5"/>
 *         &lt;element name="productFilterDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}BookableProductsRequestType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindBookableProductsRequestData", propOrder = {
    "requestID",
    "productFilterDetails"
})
public class FindBookableProductsRequestData {

    @XmlElement(required = true)
    protected BigInteger requestID;
    @XmlElement(required = true)
    protected BookableProductsRequestType productFilterDetails;

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
     * Gets the value of the productFilterDetails property.
     * 
     * @return
     *     possible object is
     *     {@link BookableProductsRequestType }
     *     
     */
    public BookableProductsRequestType getProductFilterDetails() {
        return productFilterDetails;
    }

    /**
     * Sets the value of the productFilterDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookableProductsRequestType }
     *     
     */
    public void setProductFilterDetails(BookableProductsRequestType value) {
        this.productFilterDetails = value;
    }

}
