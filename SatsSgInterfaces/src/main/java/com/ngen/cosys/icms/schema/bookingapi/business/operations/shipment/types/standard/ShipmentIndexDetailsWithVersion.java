
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
 * <p>Java class for ShipmentIndexDetailsWithVersion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipmentIndexDetailsWithVersion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shipmentPrefix" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}shipmentPrefix"/>
 *         &lt;element name="masterDocumentNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}masterDocumentNumber"/>
 *         &lt;element name="ownerId" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ownerId"/>
 *         &lt;element name="duplicateNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}duplicateNumber"/>
 *         &lt;element name="sequenceNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}sequenceNumber"/>
 *         &lt;element name="ubrNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ubrNumber" minOccurs="0"/>
 *         &lt;element name="shipmentStatus" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m1" minOccurs="0"/>
 *         &lt;element name="awbOrigin" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
 *         &lt;element name="awbDestination" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode"/>
 *         &lt;element name="awbShipperCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}shipperConsigneeCode" minOccurs="0"/>
 *         &lt;element name="awbConsigneeCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}shipperConsigneeCode" minOccurs="0"/>
 *         &lt;element name="awbCreationDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}Date"/>
 *         &lt;element name="awbVersionNumber" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipmentIndexDetailsWithVersion", propOrder = {
    "shipmentPrefix",
    "masterDocumentNumber",
    "ownerId",
    "duplicateNumber",
    "sequenceNumber",
    "ubrNumber",
    "shipmentStatus",
    "awbOrigin",
    "awbDestination",
    "awbShipperCode",
    "awbConsigneeCode",
    "awbCreationDate",
    "awbVersionNumber"
})
public class ShipmentIndexDetailsWithVersion {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String shipmentPrefix;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String masterDocumentNumber;
    @XmlElement(required = true)
    protected BigInteger ownerId;
    @XmlElement(required = true)
    protected BigInteger duplicateNumber;
    @XmlElement(required = true)
    protected BigInteger sequenceNumber;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String ubrNumber;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String shipmentStatus;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String awbOrigin;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String awbDestination;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String awbShipperCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String awbConsigneeCode;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String awbCreationDate;
    @XmlElement(required = true)
    protected BigInteger awbVersionNumber;

    /**
     * Gets the value of the shipmentPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipmentPrefix() {
        return shipmentPrefix;
    }

    /**
     * Sets the value of the shipmentPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipmentPrefix(String value) {
        this.shipmentPrefix = value;
    }

    /**
     * Gets the value of the masterDocumentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMasterDocumentNumber() {
        return masterDocumentNumber;
    }

    /**
     * Sets the value of the masterDocumentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMasterDocumentNumber(String value) {
        this.masterDocumentNumber = value;
    }

    /**
     * Gets the value of the ownerId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOwnerId() {
        return ownerId;
    }

    /**
     * Sets the value of the ownerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOwnerId(BigInteger value) {
        this.ownerId = value;
    }

    /**
     * Gets the value of the duplicateNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDuplicateNumber() {
        return duplicateNumber;
    }

    /**
     * Sets the value of the duplicateNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDuplicateNumber(BigInteger value) {
        this.duplicateNumber = value;
    }

    /**
     * Gets the value of the sequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSequenceNumber(BigInteger value) {
        this.sequenceNumber = value;
    }

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
     * Gets the value of the shipmentStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipmentStatus() {
        return shipmentStatus;
    }

    /**
     * Sets the value of the shipmentStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipmentStatus(String value) {
        this.shipmentStatus = value;
    }

    /**
     * Gets the value of the awbOrigin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwbOrigin() {
        return awbOrigin;
    }

    /**
     * Sets the value of the awbOrigin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwbOrigin(String value) {
        this.awbOrigin = value;
    }

    /**
     * Gets the value of the awbDestination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwbDestination() {
        return awbDestination;
    }

    /**
     * Sets the value of the awbDestination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwbDestination(String value) {
        this.awbDestination = value;
    }

    /**
     * Gets the value of the awbShipperCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwbShipperCode() {
        return awbShipperCode;
    }

    /**
     * Sets the value of the awbShipperCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwbShipperCode(String value) {
        this.awbShipperCode = value;
    }

    /**
     * Gets the value of the awbConsigneeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwbConsigneeCode() {
        return awbConsigneeCode;
    }

    /**
     * Sets the value of the awbConsigneeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwbConsigneeCode(String value) {
        this.awbConsigneeCode = value;
    }

    /**
     * Gets the value of the awbCreationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwbCreationDate() {
        return awbCreationDate;
    }

    /**
     * Sets the value of the awbCreationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwbCreationDate(String value) {
        this.awbCreationDate = value;
    }

    /**
     * Gets the value of the awbVersionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAwbVersionNumber() {
        return awbVersionNumber;
    }

    /**
     * Sets the value of the awbVersionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAwbVersionNumber(BigInteger value) {
        this.awbVersionNumber = value;
    }

}
