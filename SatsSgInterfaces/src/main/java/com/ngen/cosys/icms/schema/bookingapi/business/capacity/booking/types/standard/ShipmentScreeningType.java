
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ShipmentScreeningType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipmentScreeningType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="companyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ownerId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="masterDocumentNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="duplicateNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="sequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="shipmentPrefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="screeningMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="screeningPieces" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="passCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="failCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="screeningSerialNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="operationFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fullScreeningFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partScreeningFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="screeningWeight" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="screeningDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="screeningStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="canValidateScreening" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="regulatedAgentTypeFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="screeningRemarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="screenerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exemptionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shipmentDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="screeningTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sourceIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="totalscreenedPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces" minOccurs="0"/>
 *         &lt;element name="successfulScreenedPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces" minOccurs="0"/>
 *         &lt;element name="isExecuted" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parentScreen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="origin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destination" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="screeningDateModified" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="authorityType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isScreeningRowsRequired" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="OtherCustomsInformationType" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}OtherCustomsInformationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ScreeningDetailsType" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}ScreeningDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipmentScreeningType", propOrder = {
    "companyCode",
    "ownerId",
    "masterDocumentNumber",
    "duplicateNumber",
    "sequenceNumber",
    "shipmentPrefix",
    "screeningMethod",
    "screeningPieces",
    "passCount",
    "failCount",
    "screeningSerialNumber",
    "operationFlag",
    "fullScreeningFlag",
    "partScreeningFlag",
    "screeningWeight",
    "screeningDate",
    "screeningStatus",
    "canValidateScreening",
    "regulatedAgentTypeFlag",
    "screeningRemarks",
    "screenerName",
    "exemptionCode",
    "shipmentDescription",
    "screeningTime",
    "userId",
    "sourceIndicator",
    "totalscreenedPieces",
    "successfulScreenedPieces",
    "isExecuted",
    "parentScreen",
    "origin",
    "destination",
    "screeningDateModified",
    "authorityType",
    "isScreeningRowsRequired",
    "otherCustomsInformationType",
    "screeningDetailsType"
})
public class ShipmentScreeningType {

    protected String companyCode;
    protected Integer ownerId;
    protected String masterDocumentNumber;
    protected Integer duplicateNumber;
    protected Integer sequenceNumber;
    protected String shipmentPrefix;
    protected String screeningMethod;
    protected Integer screeningPieces;
    protected Integer passCount;
    protected Integer failCount;
    protected Integer screeningSerialNumber;
    protected String operationFlag;
    protected String fullScreeningFlag;
    protected String partScreeningFlag;
    protected Double screeningWeight;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String screeningDate;
    protected String screeningStatus;
    protected Boolean canValidateScreening;
    protected String regulatedAgentTypeFlag;
    protected String screeningRemarks;
    protected String screenerName;
    protected String exemptionCode;
    protected String shipmentDescription;
    protected String screeningTime;
    protected String userId;
    protected String sourceIndicator;
    protected BigInteger totalscreenedPieces;
    protected BigInteger successfulScreenedPieces;
    protected String isExecuted;
    protected String parentScreen;
    protected String origin;
    protected String destination;
    protected String screeningDateModified;
    protected String authorityType;
    protected Boolean isScreeningRowsRequired;
    @XmlElement(name = "OtherCustomsInformationType")
    protected List<OtherCustomsInformationType> otherCustomsInformationType;
    @XmlElement(name = "ScreeningDetailsType")
    protected List<ScreeningDetailsType> screeningDetailsType;

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
     * Gets the value of the passCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPassCount() {
        return passCount;
    }

    /**
     * Sets the value of the passCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPassCount(Integer value) {
        this.passCount = value;
    }

    /**
     * Gets the value of the failCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFailCount() {
        return failCount;
    }

    /**
     * Sets the value of the failCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFailCount(Integer value) {
        this.failCount = value;
    }

    /**
     * Gets the value of the screeningSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getScreeningSerialNumber() {
        return screeningSerialNumber;
    }

    /**
     * Sets the value of the screeningSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setScreeningSerialNumber(Integer value) {
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
     * Gets the value of the fullScreeningFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullScreeningFlag() {
        return fullScreeningFlag;
    }

    /**
     * Sets the value of the fullScreeningFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullScreeningFlag(String value) {
        this.fullScreeningFlag = value;
    }

    /**
     * Gets the value of the partScreeningFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartScreeningFlag() {
        return partScreeningFlag;
    }

    /**
     * Sets the value of the partScreeningFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartScreeningFlag(String value) {
        this.partScreeningFlag = value;
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
     * Gets the value of the screeningDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningDate() {
        return screeningDate;
    }

    /**
     * Sets the value of the screeningDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningDate(String value) {
        this.screeningDate = value;
    }

    /**
     * Gets the value of the screeningStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningStatus() {
        return screeningStatus;
    }

    /**
     * Sets the value of the screeningStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningStatus(String value) {
        this.screeningStatus = value;
    }

    /**
     * Gets the value of the canValidateScreening property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCanValidateScreening() {
        return canValidateScreening;
    }

    /**
     * Sets the value of the canValidateScreening property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCanValidateScreening(Boolean value) {
        this.canValidateScreening = value;
    }

    /**
     * Gets the value of the regulatedAgentTypeFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegulatedAgentTypeFlag() {
        return regulatedAgentTypeFlag;
    }

    /**
     * Sets the value of the regulatedAgentTypeFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegulatedAgentTypeFlag(String value) {
        this.regulatedAgentTypeFlag = value;
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
     * Gets the value of the screenerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreenerName() {
        return screenerName;
    }

    /**
     * Sets the value of the screenerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreenerName(String value) {
        this.screenerName = value;
    }

    /**
     * Gets the value of the exemptionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExemptionCode() {
        return exemptionCode;
    }

    /**
     * Sets the value of the exemptionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExemptionCode(String value) {
        this.exemptionCode = value;
    }

    /**
     * Gets the value of the shipmentDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipmentDescription() {
        return shipmentDescription;
    }

    /**
     * Sets the value of the shipmentDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipmentDescription(String value) {
        this.shipmentDescription = value;
    }

    /**
     * Gets the value of the screeningTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningTime() {
        return screeningTime;
    }

    /**
     * Sets the value of the screeningTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningTime(String value) {
        this.screeningTime = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
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
     * Gets the value of the totalscreenedPieces property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalscreenedPieces() {
        return totalscreenedPieces;
    }

    /**
     * Sets the value of the totalscreenedPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalscreenedPieces(BigInteger value) {
        this.totalscreenedPieces = value;
    }

    /**
     * Gets the value of the successfulScreenedPieces property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSuccessfulScreenedPieces() {
        return successfulScreenedPieces;
    }

    /**
     * Sets the value of the successfulScreenedPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSuccessfulScreenedPieces(BigInteger value) {
        this.successfulScreenedPieces = value;
    }

    /**
     * Gets the value of the isExecuted property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsExecuted() {
        return isExecuted;
    }

    /**
     * Sets the value of the isExecuted property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsExecuted(String value) {
        this.isExecuted = value;
    }

    /**
     * Gets the value of the parentScreen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentScreen() {
        return parentScreen;
    }

    /**
     * Sets the value of the parentScreen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentScreen(String value) {
        this.parentScreen = value;
    }

    /**
     * Gets the value of the origin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the value of the origin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigin(String value) {
        this.origin = value;
    }

    /**
     * Gets the value of the destination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestination(String value) {
        this.destination = value;
    }

    /**
     * Gets the value of the screeningDateModified property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningDateModified() {
        return screeningDateModified;
    }

    /**
     * Sets the value of the screeningDateModified property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningDateModified(String value) {
        this.screeningDateModified = value;
    }

    /**
     * Gets the value of the authorityType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorityType() {
        return authorityType;
    }

    /**
     * Sets the value of the authorityType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorityType(String value) {
        this.authorityType = value;
    }

    /**
     * Gets the value of the isScreeningRowsRequired property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsScreeningRowsRequired() {
        return isScreeningRowsRequired;
    }

    /**
     * Sets the value of the isScreeningRowsRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsScreeningRowsRequired(Boolean value) {
        this.isScreeningRowsRequired = value;
    }

    /**
     * Gets the value of the otherCustomsInformationType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otherCustomsInformationType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtherCustomsInformationType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OtherCustomsInformationType }
     * 
     * 
     */
    public List<OtherCustomsInformationType> getOtherCustomsInformationType() {
        if (otherCustomsInformationType == null) {
            otherCustomsInformationType = new ArrayList<OtherCustomsInformationType>();
        }
        return this.otherCustomsInformationType;
    }

    /**
     * Gets the value of the screeningDetailsType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the screeningDetailsType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScreeningDetailsType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ScreeningDetailsType }
     * 
     * 
     */
    public List<ScreeningDetailsType> getScreeningDetailsType() {
        if (screeningDetailsType == null) {
            screeningDetailsType = new ArrayList<ScreeningDetailsType>();
        }
        return this.screeningDetailsType;
    }

}
