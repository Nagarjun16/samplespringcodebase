
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * 				The common type containing the details of the customer.
 * 				The same type is applicable for different customer roles
 * 				like Shipper, Consignee, Notifier, Broker, Trucker etc.
 * 			
 * 
 * <p>Java class for CustomerDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customerRole">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="S"/>
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="B"/>
 *               &lt;enumeration value="D"/>
 *               &lt;enumeration value="T"/>
 *               &lt;enumeration value="E"/>
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="X"/>
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="Z"/>
 *               &lt;enumeration value="A"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="customerCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;pattern value="[a-zA-Z0-9]{0,15}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="customerName">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="70"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="customerAccountNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}accountNumber" minOccurs="0"/>
 *         &lt;element name="customerStreetAddress" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="70"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="stationCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}stationCode" minOccurs="0"/>
 *         &lt;element name="customerPlace" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="70"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="customerState" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="70"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="customerCountryCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}countryCode" minOccurs="0"/>
 *         &lt;element name="customerZipCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}postalCodeType" minOccurs="0"/>
 *         &lt;element name="customerContactDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}CustomerContactDetailsType" minOccurs="0"/>
 *         &lt;element name="participantIdentifier" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="AIR"/>
 *               &lt;enumeration value="APT"/>
 *               &lt;enumeration value="AGT"/>
 *               &lt;enumeration value="BRK"/>
 *               &lt;enumeration value="CAG"/>
 *               &lt;enumeration value="CNE"/>
 *               &lt;enumeration value="CTM"/>
 *               &lt;enumeration value="DCL"/>
 *               &lt;enumeration value="DEC"/>
 *               &lt;enumeration value="FFW"/>
 *               &lt;enumeration value="GHA"/>
 *               &lt;enumeration value="PTT"/>
 *               &lt;enumeration value="SHP"/>
 *               &lt;enumeration value="TRK"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="customerLAVNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;pattern value="[a-zA-Z0-9]{7,8}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="customerCASSNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;pattern value="[a-zA-Z0-9]{0,15}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="customerVATNo" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m100" minOccurs="0"/>
 *         &lt;element name="customerCreditStatus" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="A"/>
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
@XmlType(name = "CustomerDetailsType", propOrder = {
    "customerRole",
    "customerCode",
    "customerName",
    "customerAccountNumber",
    "customerStreetAddress",
    "stationCode",
    "customerPlace",
    "customerState",
    "customerCountryCode",
    "customerZipCode",
    "customerContactDetails",
    "participantIdentifier",
    "customerLAVNumber",
    "customerCASSNumber",
    "customerVATNo",
    "customerCreditStatus"
})
public class CustomerDetailsType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String customerRole;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String customerCode;
    @XmlElement(required = true)
    protected String customerName;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String customerAccountNumber;
    protected String customerStreetAddress;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String stationCode;
    protected String customerPlace;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String customerState;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String customerCountryCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String customerZipCode;
    protected CustomerContactDetailsType customerContactDetails;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String participantIdentifier;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String customerLAVNumber;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String customerCASSNumber;
    protected String customerVATNo;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String customerCreditStatus;

    /**
     * Gets the value of the customerRole property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerRole() {
        return customerRole;
    }

    /**
     * Sets the value of the customerRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerRole(String value) {
        this.customerRole = value;
    }

    /**
     * Gets the value of the customerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * Sets the value of the customerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerCode(String value) {
        this.customerCode = value;
    }

    /**
     * Gets the value of the customerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the value of the customerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerName(String value) {
        this.customerName = value;
    }

    /**
     * Gets the value of the customerAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerAccountNumber() {
        return customerAccountNumber;
    }

    /**
     * Sets the value of the customerAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerAccountNumber(String value) {
        this.customerAccountNumber = value;
    }

    /**
     * Gets the value of the customerStreetAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerStreetAddress() {
        return customerStreetAddress;
    }

    /**
     * Sets the value of the customerStreetAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerStreetAddress(String value) {
        this.customerStreetAddress = value;
    }

    /**
     * Gets the value of the stationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStationCode() {
        return stationCode;
    }

    /**
     * Sets the value of the stationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStationCode(String value) {
        this.stationCode = value;
    }

    /**
     * Gets the value of the customerPlace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerPlace() {
        return customerPlace;
    }

    /**
     * Sets the value of the customerPlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerPlace(String value) {
        this.customerPlace = value;
    }

    /**
     * Gets the value of the customerState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerState() {
        return customerState;
    }

    /**
     * Sets the value of the customerState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerState(String value) {
        this.customerState = value;
    }

    /**
     * Gets the value of the customerCountryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerCountryCode() {
        return customerCountryCode;
    }

    /**
     * Sets the value of the customerCountryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerCountryCode(String value) {
        this.customerCountryCode = value;
    }

    /**
     * Gets the value of the customerZipCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerZipCode() {
        return customerZipCode;
    }

    /**
     * Sets the value of the customerZipCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerZipCode(String value) {
        this.customerZipCode = value;
    }

    /**
     * Gets the value of the customerContactDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerContactDetailsType }
     *     
     */
    public CustomerContactDetailsType getCustomerContactDetails() {
        return customerContactDetails;
    }

    /**
     * Sets the value of the customerContactDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerContactDetailsType }
     *     
     */
    public void setCustomerContactDetails(CustomerContactDetailsType value) {
        this.customerContactDetails = value;
    }

    /**
     * Gets the value of the participantIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParticipantIdentifier() {
        return participantIdentifier;
    }

    /**
     * Sets the value of the participantIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParticipantIdentifier(String value) {
        this.participantIdentifier = value;
    }

    /**
     * Gets the value of the customerLAVNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerLAVNumber() {
        return customerLAVNumber;
    }

    /**
     * Sets the value of the customerLAVNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerLAVNumber(String value) {
        this.customerLAVNumber = value;
    }

    /**
     * Gets the value of the customerCASSNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerCASSNumber() {
        return customerCASSNumber;
    }

    /**
     * Sets the value of the customerCASSNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerCASSNumber(String value) {
        this.customerCASSNumber = value;
    }

    /**
     * Gets the value of the customerVATNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerVATNo() {
        return customerVATNo;
    }

    /**
     * Sets the value of the customerVATNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerVATNo(String value) {
        this.customerVATNo = value;
    }

    /**
     * Gets the value of the customerCreditStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerCreditStatus() {
        return customerCreditStatus;
    }

    /**
     * Sets the value of the customerCreditStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerCreditStatus(String value) {
        this.customerCreditStatus = value;
    }

}
