
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for OtherCustomsInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OtherCustomsInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="otherCusInfoIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="otherCusInfoIdentifierDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customsInfomation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="infoIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sourceIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expiryDateCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expiryDateValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ociSerialNumChild" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dupRowRemoveFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parentSerialNumToDisplay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expiryDateOfAgentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modeOfOperation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="countryCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}countryCode" minOccurs="0"/>
 *         &lt;element name="operationFlag" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="I"/>
 *               &lt;enumeration value="U"/>
 *               &lt;enumeration value="D"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="parentSerialNum" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ociSerialNum" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
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
@XmlType(name = "OtherCustomsInformationType", propOrder = {
    "otherCusInfoIdentifier",
    "otherCusInfoIdentifierDescription",
    "customsInfomation",
    "infoIdentifier",
    "sourceIndicator",
    "expiryDateCode",
    "expiryDateValue",
    "ociSerialNumChild",
    "dupRowRemoveFlag",
    "parentSerialNumToDisplay",
    "expiryDateOfAgentType",
    "modeOfOperation",
    "countryCode",
    "operationFlag",
    "parentSerialNum",
    "ociSerialNum"
})
public class OtherCustomsInformationType {

    protected String otherCusInfoIdentifier;
    protected String otherCusInfoIdentifierDescription;
    protected String customsInfomation;
    protected String infoIdentifier;
    protected String sourceIndicator;
    protected String expiryDateCode;
    protected String expiryDateValue;
    protected Integer ociSerialNumChild;
    protected String dupRowRemoveFlag;
    protected String parentSerialNumToDisplay;
    protected String expiryDateOfAgentType;
    protected String modeOfOperation;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String countryCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String operationFlag;
    protected String parentSerialNum;
    protected String ociSerialNum;

    /**
     * Gets the value of the otherCusInfoIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherCusInfoIdentifier() {
        return otherCusInfoIdentifier;
    }

    /**
     * Sets the value of the otherCusInfoIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherCusInfoIdentifier(String value) {
        this.otherCusInfoIdentifier = value;
    }

    /**
     * Gets the value of the otherCusInfoIdentifierDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherCusInfoIdentifierDescription() {
        return otherCusInfoIdentifierDescription;
    }

    /**
     * Sets the value of the otherCusInfoIdentifierDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherCusInfoIdentifierDescription(String value) {
        this.otherCusInfoIdentifierDescription = value;
    }

    /**
     * Gets the value of the customsInfomation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomsInfomation() {
        return customsInfomation;
    }

    /**
     * Sets the value of the customsInfomation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomsInfomation(String value) {
        this.customsInfomation = value;
    }

    /**
     * Gets the value of the infoIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfoIdentifier() {
        return infoIdentifier;
    }

    /**
     * Sets the value of the infoIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfoIdentifier(String value) {
        this.infoIdentifier = value;
    }

    /**
     * Gets the value of the sourceIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceIndicator() {
        return sourceIndicator;
    }

    /**
     * Sets the value of the sourceIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceIndicator(String value) {
        this.sourceIndicator = value;
    }

    /**
     * Gets the value of the expiryDateCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpiryDateCode() {
        return expiryDateCode;
    }

    /**
     * Sets the value of the expiryDateCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpiryDateCode(String value) {
        this.expiryDateCode = value;
    }

    /**
     * Gets the value of the expiryDateValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpiryDateValue() {
        return expiryDateValue;
    }

    /**
     * Sets the value of the expiryDateValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpiryDateValue(String value) {
        this.expiryDateValue = value;
    }

    /**
     * Gets the value of the ociSerialNumChild property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOciSerialNumChild() {
        return ociSerialNumChild;
    }

    /**
     * Sets the value of the ociSerialNumChild property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOciSerialNumChild(Integer value) {
        this.ociSerialNumChild = value;
    }

    /**
     * Gets the value of the dupRowRemoveFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDupRowRemoveFlag() {
        return dupRowRemoveFlag;
    }

    /**
     * Sets the value of the dupRowRemoveFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDupRowRemoveFlag(String value) {
        this.dupRowRemoveFlag = value;
    }

    /**
     * Gets the value of the parentSerialNumToDisplay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentSerialNumToDisplay() {
        return parentSerialNumToDisplay;
    }

    /**
     * Sets the value of the parentSerialNumToDisplay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentSerialNumToDisplay(String value) {
        this.parentSerialNumToDisplay = value;
    }

    /**
     * Gets the value of the expiryDateOfAgentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpiryDateOfAgentType() {
        return expiryDateOfAgentType;
    }

    /**
     * Sets the value of the expiryDateOfAgentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpiryDateOfAgentType(String value) {
        this.expiryDateOfAgentType = value;
    }

    /**
     * Gets the value of the modeOfOperation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeOfOperation() {
        return modeOfOperation;
    }

    /**
     * Sets the value of the modeOfOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeOfOperation(String value) {
        this.modeOfOperation = value;
    }

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    /**
     * Gets the value of the operationFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationFlag() {
        return operationFlag;
    }

    /**
     * Sets the value of the operationFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationFlag(String value) {
        this.operationFlag = value;
    }

    /**
     * Gets the value of the parentSerialNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentSerialNum() {
        return parentSerialNum;
    }

    /**
     * Sets the value of the parentSerialNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentSerialNum(String value) {
        this.parentSerialNum = value;
    }

    /**
     * Gets the value of the ociSerialNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOciSerialNum() {
        return ociSerialNum;
    }

    /**
     * Sets the value of the ociSerialNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOciSerialNum(String value) {
        this.ociSerialNum = value;
    }

}
