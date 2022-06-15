
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for AbstractBookingHistoryFilterType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractBookingHistoryFilterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ubrNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ubrNumber" minOccurs="0"/>
 *         &lt;element name="fromVersionNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n3" minOccurs="0"/>
 *         &lt;element name="toVersionNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n3" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractBookingHistoryFilterType", propOrder = {
    "ubrNumber",
    "fromVersionNumber",
    "toVersionNumber"
})
public class AbstractBookingHistoryFilterType {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String ubrNumber;
    protected BigInteger fromVersionNumber;
    protected BigInteger toVersionNumber;

    /**
     * Gets the value of the ubrNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUbrNumber() {
        return ubrNumber;
    }

    /**
     * Sets the value of the ubrNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUbrNumber(String value) {
        this.ubrNumber = value;
    }

    /**
     * Gets the value of the fromVersionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFromVersionNumber() {
        return fromVersionNumber;
    }

    /**
     * Sets the value of the fromVersionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFromVersionNumber(BigInteger value) {
        this.fromVersionNumber = value;
    }

    /**
     * Gets the value of the toVersionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getToVersionNumber() {
        return toVersionNumber;
    }

    /**
     * Sets the value of the toVersionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setToVersionNumber(BigInteger value) {
        this.toVersionNumber = value;
    }

}
