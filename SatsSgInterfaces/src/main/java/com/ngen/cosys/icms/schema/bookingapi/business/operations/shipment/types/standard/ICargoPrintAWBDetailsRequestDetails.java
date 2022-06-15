
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.OperationalFlagType;


/**
 * <p>Java class for iCargoPrintAWBDetailsRequestDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="iCargoPrintAWBDetailsRequestDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestID" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5"/>
 *         &lt;element name="operationalFlag" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}operationalFlagType" minOccurs="0"/>
 *         &lt;element name="awbDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentDetails"/>
 *         &lt;element name="printName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reportId" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m15" minOccurs="0"/>
 *         &lt;element name="referenceOne" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceTwo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceThree" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceFour" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceFive" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceSix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceSeven" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "iCargoPrintAWBDetailsRequestDetails", propOrder = {
    "requestID",
    "operationalFlag",
    "awbDetails",
    "printName",
    "reportId",
    "referenceOne",
    "referenceTwo",
    "referenceThree",
    "referenceFour",
    "referenceFive",
    "referenceSix",
    "referenceSeven"
})
public class ICargoPrintAWBDetailsRequestDetails {

    @XmlElement(required = true)
    protected BigInteger requestID;
    @XmlSchemaType(name = "string")
    protected OperationalFlagType operationalFlag;
    @XmlElement(required = true)
    protected ShipmentDetails awbDetails;
    @XmlElement(required = true)
    protected String printName;
    protected String reportId;
    protected String referenceOne;
    protected String referenceTwo;
    protected String referenceThree;
    protected String referenceFour;
    protected String referenceFive;
    protected String referenceSix;
    protected String referenceSeven;

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
     * Gets the value of the operationalFlag property.
     * 
     * @return
     *     possible object is
     *     {@link OperationalFlagType }
     *     
     */
    public OperationalFlagType getOperationalFlag() {
        return operationalFlag;
    }

    /**
     * Sets the value of the operationalFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationalFlagType }
     *     
     */
    public void setOperationalFlag(OperationalFlagType value) {
        this.operationalFlag = value;
    }

    /**
     * Gets the value of the awbDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentDetails }
     *     
     */
    public ShipmentDetails getAwbDetails() {
        return awbDetails;
    }

    /**
     * Sets the value of the awbDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentDetails }
     *     
     */
    public void setAwbDetails(ShipmentDetails value) {
        this.awbDetails = value;
    }

    /**
     * Gets the value of the printName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrintName() {
        return printName;
    }

    /**
     * Sets the value of the printName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrintName(String value) {
        this.printName = value;
    }

    /**
     * Gets the value of the reportId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportId() {
        return reportId;
    }

    /**
     * Sets the value of the reportId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportId(String value) {
        this.reportId = value;
    }

    /**
     * Gets the value of the referenceOne property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceOne() {
        return referenceOne;
    }

    /**
     * Sets the value of the referenceOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceOne(String value) {
        this.referenceOne = value;
    }

    /**
     * Gets the value of the referenceTwo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceTwo() {
        return referenceTwo;
    }

    /**
     * Sets the value of the referenceTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceTwo(String value) {
        this.referenceTwo = value;
    }

    /**
     * Gets the value of the referenceThree property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceThree() {
        return referenceThree;
    }

    /**
     * Sets the value of the referenceThree property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceThree(String value) {
        this.referenceThree = value;
    }

    /**
     * Gets the value of the referenceFour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceFour() {
        return referenceFour;
    }

    /**
     * Sets the value of the referenceFour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceFour(String value) {
        this.referenceFour = value;
    }

    /**
     * Gets the value of the referenceFive property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceFive() {
        return referenceFive;
    }

    /**
     * Sets the value of the referenceFive property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceFive(String value) {
        this.referenceFive = value;
    }

    /**
     * Gets the value of the referenceSix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceSix() {
        return referenceSix;
    }

    /**
     * Sets the value of the referenceSix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceSix(String value) {
        this.referenceSix = value;
    }

    /**
     * Gets the value of the referenceSeven property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceSeven() {
        return referenceSeven;
    }

    /**
     * Sets the value of the referenceSeven property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceSeven(String value) {
        this.referenceSeven = value;
    }

}
