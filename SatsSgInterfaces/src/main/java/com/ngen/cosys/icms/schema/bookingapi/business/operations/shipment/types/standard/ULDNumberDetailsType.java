
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ULDNumberDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ULDNumberDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ULDType" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ULDType"/>
 *         &lt;element name="ULDSerialNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ULDSerialNumberType" minOccurs="0"/>
 *         &lt;element name="ULDOwnerCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ULDOwnerCodeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ULDNumberDetailsType", propOrder = {
    "uldType",
    "uldSerialNumber",
    "uldOwnerCode"
})
public class ULDNumberDetailsType {

    @XmlElement(name = "ULDType", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String uldType;
    @XmlElement(name = "ULDSerialNumber")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String uldSerialNumber;
    @XmlElement(name = "ULDOwnerCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String uldOwnerCode;

    /**
     * Gets the value of the uldType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getULDType() {
        return uldType;
    }

    /**
     * Sets the value of the uldType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setULDType(String value) {
        this.uldType = value;
    }

    /**
     * Gets the value of the uldSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getULDSerialNumber() {
        return uldSerialNumber;
    }

    /**
     * Sets the value of the uldSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setULDSerialNumber(String value) {
        this.uldSerialNumber = value;
    }

    /**
     * Gets the value of the uldOwnerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getULDOwnerCode() {
        return uldOwnerCode;
    }

    /**
     * Sets the value of the uldOwnerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setULDOwnerCode(String value) {
        this.uldOwnerCode = value;
    }

}
