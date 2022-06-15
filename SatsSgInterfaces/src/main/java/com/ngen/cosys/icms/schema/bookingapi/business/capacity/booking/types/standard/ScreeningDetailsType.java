
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ScreeningDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScreeningDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="companyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ownerId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="masterDocumentNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="duplicateNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="sequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="shipmentPrefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="screeningAirport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="screeningResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="screeningMethod" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *         &lt;element name="screeningResultDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="screeningMethodDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="screeningPieces" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="correlationId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="screeningSerialNumber" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="operationFlag" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="I"/>
 *               &lt;enumeration value="U"/>
 *               &lt;enumeration value="D"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="screeningWeight" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="screeningWeightUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trackingIdPieces" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="anyOtherMethod" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="31"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="screeningRemarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="screeningSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScreeningDetailsType", propOrder = {
    "companyCode",
    "ownerId",
    "masterDocumentNumber",
    "duplicateNumber",
    "sequenceNumber",
    "shipmentPrefix",
    "screeningAirport",
    "screeningResult",
    "screeningMethod",
    "screeningResultDescription",
    "screeningMethodDescription",
    "screeningPieces",
    "correlationId",
    "screeningSerialNumber",
    "operationFlag",
    "screeningWeight",
    "screeningWeightUnit",
    "trackingIdPieces",
    "anyOtherMethod",
    "screeningRemarks",
    "screeningSource"
})
public class ScreeningDetailsType {

    protected String companyCode;
    protected Integer ownerId;
    protected String masterDocumentNumber;
    protected Integer duplicateNumber;
    protected Integer sequenceNumber;
    protected String shipmentPrefix;
    protected String screeningAirport;
    protected String screeningResult;
    protected String screeningMethod;
    protected String screeningResultDescription;
    protected String screeningMethodDescription;
    protected Integer screeningPieces;
    protected Integer correlationId;
    protected BigInteger screeningSerialNumber;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String operationFlag;
    protected Double screeningWeight;
    protected String screeningWeightUnit;
    protected Integer trackingIdPieces;
    protected String anyOtherMethod;
    protected String screeningRemarks;
    protected String screeningSource;

    /**
     * Gets the value of the companyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * Sets the value of the companyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyCode(String value) {
        this.companyCode = value;
    }

    /**
     * Gets the value of the ownerId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOwnerId() {
        return ownerId;
    }

    /**
     * Sets the value of the ownerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOwnerId(Integer value) {
        this.ownerId = value;
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
     * Gets the value of the duplicateNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDuplicateNumber() {
        return duplicateNumber;
    }

    /**
     * Sets the value of the duplicateNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDuplicateNumber(Integer value) {
        this.duplicateNumber = value;
    }

    /**
     * Gets the value of the sequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSequenceNumber(Integer value) {
        this.sequenceNumber = value;
    }

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
     * Gets the value of the screeningAirport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningAirport() {
        return screeningAirport;
    }

    /**
     * Sets the value of the screeningAirport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningAirport(String value) {
        this.screeningAirport = value;
    }

    /**
     * Gets the value of the screeningResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningResult() {
        return screeningResult;
    }

    /**
     * Sets the value of the screeningResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningResult(String value) {
        this.screeningResult = value;
    }

    /**
     * Gets the value of the screeningMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningMethod() {
        return screeningMethod;
    }

    /**
     * Sets the value of the screeningMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningMethod(String value) {
        this.screeningMethod = value;
    }

    /**
     * Gets the value of the screeningResultDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningResultDescription() {
        return screeningResultDescription;
    }

    /**
     * Sets the value of the screeningResultDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningResultDescription(String value) {
        this.screeningResultDescription = value;
    }

    /**
     * Gets the value of the screeningMethodDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningMethodDescription() {
        return screeningMethodDescription;
    }

    /**
     * Sets the value of the screeningMethodDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningMethodDescription(String value) {
        this.screeningMethodDescription = value;
    }

    /**
     * Gets the value of the screeningPieces property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getScreeningPieces() {
        return screeningPieces;
    }

    /**
     * Sets the value of the screeningPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setScreeningPieces(Integer value) {
        this.screeningPieces = value;
    }

    /**
     * Gets the value of the correlationId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCorrelationId() {
        return correlationId;
    }

    /**
     * Sets the value of the correlationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCorrelationId(Integer value) {
        this.correlationId = value;
    }

    /**
     * Gets the value of the screeningSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getScreeningSerialNumber() {
        return screeningSerialNumber;
    }

    /**
     * Sets the value of the screeningSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setScreeningSerialNumber(BigInteger value) {
        this.screeningSerialNumber = value;
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
     * Gets the value of the screeningWeight property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getScreeningWeight() {
        return screeningWeight;
    }

    /**
     * Sets the value of the screeningWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setScreeningWeight(Double value) {
        this.screeningWeight = value;
    }

    /**
     * Gets the value of the screeningWeightUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningWeightUnit() {
        return screeningWeightUnit;
    }

    /**
     * Sets the value of the screeningWeightUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningWeightUnit(String value) {
        this.screeningWeightUnit = value;
    }

    /**
     * Gets the value of the trackingIdPieces property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTrackingIdPieces() {
        return trackingIdPieces;
    }

    /**
     * Sets the value of the trackingIdPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTrackingIdPieces(Integer value) {
        this.trackingIdPieces = value;
    }

    /**
     * Gets the value of the anyOtherMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnyOtherMethod() {
        return anyOtherMethod;
    }

    /**
     * Sets the value of the anyOtherMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnyOtherMethod(String value) {
        this.anyOtherMethod = value;
    }

    /**
     * Gets the value of the screeningRemarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningRemarks() {
        return screeningRemarks;
    }

    /**
     * Sets the value of the screeningRemarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningRemarks(String value) {
        this.screeningRemarks = value;
    }

    /**
     * Gets the value of the screeningSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningSource() {
        return screeningSource;
    }

    /**
     * Sets the value of the screeningSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningSource(String value) {
        this.screeningSource = value;
    }

}
