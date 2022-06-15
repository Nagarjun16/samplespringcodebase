
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PrintAWBDocumentResponseDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PrintAWBDocumentResponseDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="awbDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentDetails"/>
 *         &lt;element name="reportID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hawbNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileData">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="800000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="referenceOne" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceTwo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceThree" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceFour" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceFive" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ErrorDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PrintAWBDocumentResponseDetailsType", propOrder = {
    "awbDetails",
    "reportID",
    "hawbNumber",
    "fileData",
    "referenceOne",
    "referenceTwo",
    "referenceThree",
    "referenceFour",
    "referenceFive",
    "errorDetails"
})
public class PrintAWBDocumentResponseDetailsType {

    @XmlElement(required = true)
    protected ShipmentDetails awbDetails;
    @XmlElement(required = true)
    protected String reportID;
    protected String hawbNumber;
    @XmlElement(required = true)
    protected String fileData;
    protected String referenceOne;
    protected String referenceTwo;
    protected String referenceThree;
    protected String referenceFour;
    protected String referenceFive;
    protected List<ErrorDetailsType> errorDetails;

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
     * Gets the value of the fileData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileData() {
        return fileData;
    }

    /**
     * Sets the value of the fileData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileData(String value) {
        this.fileData = value;
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
     * Gets the value of the errorDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ErrorDetailsType }
     * 
     * 
     */
    public List<ErrorDetailsType> getErrorDetails() {
        if (errorDetails == null) {
            errorDetails = new ArrayList<ErrorDetailsType>();
        }
        return this.errorDetails;
    }

}
