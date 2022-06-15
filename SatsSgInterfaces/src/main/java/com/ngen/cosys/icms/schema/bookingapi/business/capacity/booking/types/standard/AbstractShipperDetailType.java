
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for AbstractShipperDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractShipperDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shipperCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}agentCode" minOccurs="0"/>
 *         &lt;element name="shipperName" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m150" minOccurs="0"/>
 *         &lt;element name="shipperFirstAddress" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}desc_1000" minOccurs="0"/>
 *         &lt;element name="shipperSecondAddress" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}desc_1000" minOccurs="0"/>
 *         &lt;element name="shipperCity" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t70" minOccurs="0"/>
 *         &lt;element name="shipperState" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t70" minOccurs="0"/>
 *         &lt;element name="shipperCountry" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}countryCode" minOccurs="0"/>
 *         &lt;element name="shipperPostalCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}postalCodeType" minOccurs="0"/>
 *         &lt;element name="shipperPhonenumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *         &lt;element name="shipperMobileNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *         &lt;element name="shipperFaxNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *         &lt;element name="shipperEmailAddress" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t70" minOccurs="0"/>
 *         &lt;element name="shipperCertificateNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t50" minOccurs="0"/>
 *         &lt;element name="shipperCertificateType" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t50" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractShipperDetailType", propOrder = {
    "shipperCode",
    "shipperName",
    "shipperFirstAddress",
    "shipperSecondAddress",
    "shipperCity",
    "shipperState",
    "shipperCountry",
    "shipperPostalCode",
    "shipperPhonenumber",
    "shipperMobileNumber",
    "shipperFaxNumber",
    "shipperEmailAddress",
    "shipperCertificateNumber",
    "shipperCertificateType"
})
@XmlSeeAlso({
    ShipperDetailType.class
})
public class AbstractShipperDetailType {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String shipperCode;
    protected String shipperName;
    protected String shipperFirstAddress;
    protected String shipperSecondAddress;
    protected String shipperCity;
    protected String shipperState;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String shipperCountry;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String shipperPostalCode;
    protected String shipperPhonenumber;
    protected String shipperMobileNumber;
    protected String shipperFaxNumber;
    protected String shipperEmailAddress;
    protected String shipperCertificateNumber;
    protected String shipperCertificateType;

    /**
     * Gets the value of the shipperCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperCode() {
        return shipperCode;
    }

    /**
     * Sets the value of the shipperCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperCode(String value) {
        this.shipperCode = value;
    }

    /**
     * Gets the value of the shipperName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperName() {
        return shipperName;
    }

    /**
     * Sets the value of the shipperName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperName(String value) {
        this.shipperName = value;
    }

    /**
     * Gets the value of the shipperFirstAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperFirstAddress() {
        return shipperFirstAddress;
    }

    /**
     * Sets the value of the shipperFirstAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperFirstAddress(String value) {
        this.shipperFirstAddress = value;
    }

    /**
     * Gets the value of the shipperSecondAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperSecondAddress() {
        return shipperSecondAddress;
    }

    /**
     * Sets the value of the shipperSecondAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperSecondAddress(String value) {
        this.shipperSecondAddress = value;
    }

    /**
     * Gets the value of the shipperCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperCity() {
        return shipperCity;
    }

    /**
     * Sets the value of the shipperCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperCity(String value) {
        this.shipperCity = value;
    }

    /**
     * Gets the value of the shipperState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperState() {
        return shipperState;
    }

    /**
     * Sets the value of the shipperState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperState(String value) {
        this.shipperState = value;
    }

    /**
     * Gets the value of the shipperCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperCountry() {
        return shipperCountry;
    }

    /**
     * Sets the value of the shipperCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperCountry(String value) {
        this.shipperCountry = value;
    }

    /**
     * Gets the value of the shipperPostalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperPostalCode() {
        return shipperPostalCode;
    }

    /**
     * Sets the value of the shipperPostalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperPostalCode(String value) {
        this.shipperPostalCode = value;
    }

    /**
     * Gets the value of the shipperPhonenumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperPhonenumber() {
        return shipperPhonenumber;
    }

    /**
     * Sets the value of the shipperPhonenumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperPhonenumber(String value) {
        this.shipperPhonenumber = value;
    }

    /**
     * Gets the value of the shipperMobileNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperMobileNumber() {
        return shipperMobileNumber;
    }

    /**
     * Sets the value of the shipperMobileNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperMobileNumber(String value) {
        this.shipperMobileNumber = value;
    }

    /**
     * Gets the value of the shipperFaxNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperFaxNumber() {
        return shipperFaxNumber;
    }

    /**
     * Sets the value of the shipperFaxNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperFaxNumber(String value) {
        this.shipperFaxNumber = value;
    }

    /**
     * Gets the value of the shipperEmailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperEmailAddress() {
        return shipperEmailAddress;
    }

    /**
     * Sets the value of the shipperEmailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperEmailAddress(String value) {
        this.shipperEmailAddress = value;
    }

    /**
     * Gets the value of the shipperCertificateNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperCertificateNumber() {
        return shipperCertificateNumber;
    }

    /**
     * Sets the value of the shipperCertificateNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperCertificateNumber(String value) {
        this.shipperCertificateNumber = value;
    }

    /**
     * Gets the value of the shipperCertificateType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperCertificateType() {
        return shipperCertificateType;
    }

    /**
     * Sets the value of the shipperCertificateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperCertificateType(String value) {
        this.shipperCertificateType = value;
    }

}
