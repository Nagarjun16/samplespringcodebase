
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PrintAWBDocumentRequestDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PrintAWBDocumentRequestDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="awbDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentDetails"/>
 *         &lt;element name="awbConsignmentDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBConsignmentDetailsType"/>
 *         &lt;element name="awbRoutingDetailsType" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBRoutingDetailsType"/>
 *         &lt;element name="awbConsigneeDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}CustomerDetailsType"/>
 *         &lt;element name="hawbNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceOne" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceTwo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceThree" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceFour" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceFive" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="printFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PrintAWBDocumentRequestDetailsType", propOrder = {
    "awbDetails",
    "awbConsignmentDetails",
    "awbRoutingDetailsType",
    "awbConsigneeDetails",
    "hawbNumber",
    "referenceOne",
    "referenceTwo",
    "referenceThree",
    "referenceFour",
    "referenceFive",
    "reportID",
    "printFormat"
})
public class PrintAWBDocumentRequestDetailsType {

    @XmlElement(required = true)
    protected ShipmentDetails awbDetails;
    @XmlElement(required = true)
    protected AWBConsignmentDetailsType awbConsignmentDetails;
    @XmlElement(required = true)
    protected AWBRoutingDetailsType awbRoutingDetailsType;
    @XmlElement(required = true)
    protected CustomerDetailsType awbConsigneeDetails;
    protected String hawbNumber;
    protected String referenceOne;
    protected String referenceTwo;
    protected String referenceThree;
    protected String referenceFour;
    protected String referenceFive;
    @XmlElement(required = true)
    protected String reportID;
    @XmlElement(required = true)
    protected String printFormat;

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
     * Gets the value of the awbConsignmentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBConsignmentDetailsType }
     *     
     */
    public AWBConsignmentDetailsType getAwbConsignmentDetails() {
        return awbConsignmentDetails;
    }

    /**
     * Sets the value of the awbConsignmentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBConsignmentDetailsType }
     *     
     */
    public void setAwbConsignmentDetails(AWBConsignmentDetailsType value) {
        this.awbConsignmentDetails = value;
    }

    /**
     * Gets the value of the awbRoutingDetailsType property.
     * 
     * @return
     *     possible object is
     *     {@link AWBRoutingDetailsType }
     *     
     */
    public AWBRoutingDetailsType getAwbRoutingDetailsType() {
        return awbRoutingDetailsType;
    }

    /**
     * Sets the value of the awbRoutingDetailsType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBRoutingDetailsType }
     *     
     */
    public void setAwbRoutingDetailsType(AWBRoutingDetailsType value) {
        this.awbRoutingDetailsType = value;
    }

    /**
     * Gets the value of the awbConsigneeDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerDetailsType }
     *     
     */
    public CustomerDetailsType getAwbConsigneeDetails() {
        return awbConsigneeDetails;
    }

    /**
     * Sets the value of the awbConsigneeDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerDetailsType }
     *     
     */
    public void setAwbConsigneeDetails(CustomerDetailsType value) {
        this.awbConsigneeDetails = value;
    }

    /**
     * Gets the value of the hawbNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHawbNumber() {
        return hawbNumber;
    }

    /**
     * Sets the value of the hawbNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHawbNumber(String value) {
        this.hawbNumber = value;
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
     * Gets the value of the reportID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportID() {
        return reportID;
    }

    /**
     * Sets the value of the reportID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportID(String value) {
        this.reportID = value;
    }

    /**
     * Gets the value of the printFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrintFormat() {
        return printFormat;
    }

    /**
     * Sets the value of the printFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrintFormat(String value) {
        this.printFormat = value;
    }

}
