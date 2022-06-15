
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

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
 *         &lt;element name="reasonForExemption" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="SMUS"/>
 *               &lt;enumeration value="BIOM"/>
 *               &lt;enumeration value="MAIL"/>
 *               &lt;enumeration value="DIPL"/>
 *               &lt;enumeration value="LFSM"/>
 *               &lt;enumeration value="NUCL"/>
 *               &lt;enumeration value="TRNS"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="screeningDetailsApprovalCategory" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="ISS"/>
 *               &lt;enumeration value="OSS"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="securityDataReviewFlag" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="totalscreenedPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces" minOccurs="0"/>
 *         &lt;element name="successfulScreenedPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces" minOccurs="0"/>
 *         &lt;element name="screeningRemarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="screeningDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="screenerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secureFlag" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="AgentScreeningDetailsType" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AgentScreeningDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="shipmentScreeningDetailsType" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentScreeningDetailsType" maxOccurs="unbounded" minOccurs="0"/>
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
    "reasonForExemption",
    "screeningDetailsApprovalCategory",
    "securityDataReviewFlag",
    "totalscreenedPieces",
    "successfulScreenedPieces",
    "screeningRemarks",
    "screeningDate",
    "screenerName",
    "secureFlag",
    "agentScreeningDetailsType",
    "shipmentScreeningDetailsType"
})
public class ShipmentScreeningType {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String reasonForExemption;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String screeningDetailsApprovalCategory;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String securityDataReviewFlag;
    protected BigInteger totalscreenedPieces;
    protected BigInteger successfulScreenedPieces;
    protected String screeningRemarks;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String screeningDate;
    protected String screenerName;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String secureFlag;
    @XmlElement(name = "AgentScreeningDetailsType")
    protected List<AgentScreeningDetailsType> agentScreeningDetailsType;
    protected List<ShipmentScreeningDetailsType> shipmentScreeningDetailsType;

    /**
     * Gets the value of the reasonForExemption property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonForExemption() {
        return reasonForExemption;
    }

    /**
     * Sets the value of the reasonForExemption property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonForExemption(String value) {
        this.reasonForExemption = value;
    }

    /**
     * Gets the value of the screeningDetailsApprovalCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningDetailsApprovalCategory() {
        return screeningDetailsApprovalCategory;
    }

    /**
     * Sets the value of the screeningDetailsApprovalCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningDetailsApprovalCategory(String value) {
        this.screeningDetailsApprovalCategory = value;
    }

    /**
     * Gets the value of the securityDataReviewFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityDataReviewFlag() {
        return securityDataReviewFlag;
    }

    /**
     * Sets the value of the securityDataReviewFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityDataReviewFlag(String value) {
        this.securityDataReviewFlag = value;
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
     * Gets the value of the secureFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecureFlag() {
        return secureFlag;
    }

    /**
     * Sets the value of the secureFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecureFlag(String value) {
        this.secureFlag = value;
    }

    /**
     * Gets the value of the agentScreeningDetailsType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the agentScreeningDetailsType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAgentScreeningDetailsType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AgentScreeningDetailsType }
     * 
     * 
     */
    public List<AgentScreeningDetailsType> getAgentScreeningDetailsType() {
        if (agentScreeningDetailsType == null) {
            agentScreeningDetailsType = new ArrayList<AgentScreeningDetailsType>();
        }
        return this.agentScreeningDetailsType;
    }

    /**
     * Gets the value of the shipmentScreeningDetailsType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shipmentScreeningDetailsType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShipmentScreeningDetailsType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ShipmentScreeningDetailsType }
     * 
     * 
     */
    public List<ShipmentScreeningDetailsType> getShipmentScreeningDetailsType() {
        if (shipmentScreeningDetailsType == null) {
            shipmentScreeningDetailsType = new ArrayList<ShipmentScreeningDetailsType>();
        }
        return this.shipmentScreeningDetailsType;
    }

}
