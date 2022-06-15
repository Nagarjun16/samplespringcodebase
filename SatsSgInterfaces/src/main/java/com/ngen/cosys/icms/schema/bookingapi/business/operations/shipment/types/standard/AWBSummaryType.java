
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
 * <p>Java class for AWBSummaryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBSummaryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="awbConsignmentDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBConsignmentDetailsType"/>
 *         &lt;element name="awbRoutingDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBRoutingDetailsType" minOccurs="0"/>
 *         &lt;element name="awbShipperDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}CustomerDetailsType" minOccurs="0"/>
 *         &lt;element name="awbConsigneeDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}CustomerDetailsType" minOccurs="0"/>
 *         &lt;element name="awbAgentDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AgentDetailsType" minOccurs="0"/>
 *         &lt;element name="awbHandlingInfoDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBHandlingInfoType" minOccurs="0"/>
 *         &lt;element name="awbExecutionDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBExecutionDetailsType" minOccurs="0"/>
 *         &lt;element name="isConsolShipmentFlag" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="awbStatus" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="productDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ProductDetailsType" minOccurs="0"/>
 *         &lt;element name="slacPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces" minOccurs="0"/>
 *         &lt;element name="houseDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}HouseType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="houseDetailsByPage" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}HouseDetailsByPage" minOccurs="0"/>
 *         &lt;element name="lastUpdatedTime" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="lastUpdatedUser" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}userCode" minOccurs="0"/>
 *         &lt;element name="bookingCreatedUser" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}userCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AWBSummaryType", propOrder = {
    "awbConsignmentDetails",
    "awbRoutingDetails",
    "awbShipperDetails",
    "awbConsigneeDetails",
    "awbAgentDetails",
    "awbHandlingInfoDetails",
    "awbExecutionDetails",
    "isConsolShipmentFlag",
    "awbStatus",
    "productDetails",
    "slacPieces",
    "houseDetails",
    "houseDetailsByPage",
    "lastUpdatedTime",
    "lastUpdatedUser",
    "bookingCreatedUser"
})
public class AWBSummaryType {

    @XmlElement(required = true)
    protected AWBConsignmentDetailsType awbConsignmentDetails;
    protected AWBRoutingDetailsType awbRoutingDetails;
    protected CustomerDetailsType awbShipperDetails;
    protected CustomerDetailsType awbConsigneeDetails;
    protected AgentDetailsType awbAgentDetails;
    protected AWBHandlingInfoType awbHandlingInfoDetails;
    protected AWBExecutionDetailsType awbExecutionDetails;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isConsolShipmentFlag;
    protected String awbStatus;
    protected ProductDetailsType productDetails;
    protected BigInteger slacPieces;
    protected List<HouseType> houseDetails;
    protected HouseDetailsByPage houseDetailsByPage;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String lastUpdatedTime;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String lastUpdatedUser;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String bookingCreatedUser;

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
     * Gets the value of the awbRoutingDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBRoutingDetailsType }
     *     
     */
    public AWBRoutingDetailsType getAwbRoutingDetails() {
        return awbRoutingDetails;
    }

    /**
     * Sets the value of the awbRoutingDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBRoutingDetailsType }
     *     
     */
    public void setAwbRoutingDetails(AWBRoutingDetailsType value) {
        this.awbRoutingDetails = value;
    }

    /**
     * Gets the value of the awbShipperDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerDetailsType }
     *     
     */
    public CustomerDetailsType getAwbShipperDetails() {
        return awbShipperDetails;
    }

    /**
     * Sets the value of the awbShipperDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerDetailsType }
     *     
     */
    public void setAwbShipperDetails(CustomerDetailsType value) {
        this.awbShipperDetails = value;
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
     * Gets the value of the awbAgentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AgentDetailsType }
     *     
     */
    public AgentDetailsType getAwbAgentDetails() {
        return awbAgentDetails;
    }

    /**
     * Sets the value of the awbAgentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgentDetailsType }
     *     
     */
    public void setAwbAgentDetails(AgentDetailsType value) {
        this.awbAgentDetails = value;
    }

    /**
     * Gets the value of the awbHandlingInfoDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBHandlingInfoType }
     *     
     */
    public AWBHandlingInfoType getAwbHandlingInfoDetails() {
        return awbHandlingInfoDetails;
    }

    /**
     * Sets the value of the awbHandlingInfoDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBHandlingInfoType }
     *     
     */
    public void setAwbHandlingInfoDetails(AWBHandlingInfoType value) {
        this.awbHandlingInfoDetails = value;
    }

    /**
     * Gets the value of the awbExecutionDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBExecutionDetailsType }
     *     
     */
    public AWBExecutionDetailsType getAwbExecutionDetails() {
        return awbExecutionDetails;
    }

    /**
     * Sets the value of the awbExecutionDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBExecutionDetailsType }
     *     
     */
    public void setAwbExecutionDetails(AWBExecutionDetailsType value) {
        this.awbExecutionDetails = value;
    }

    /**
     * Gets the value of the isConsolShipmentFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsConsolShipmentFlag() {
        return isConsolShipmentFlag;
    }

    /**
     * Sets the value of the isConsolShipmentFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsConsolShipmentFlag(String value) {
        this.isConsolShipmentFlag = value;
    }

    /**
     * Gets the value of the awbStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwbStatus() {
        return awbStatus;
    }

    /**
     * Sets the value of the awbStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwbStatus(String value) {
        this.awbStatus = value;
    }

    /**
     * Gets the value of the productDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ProductDetailsType }
     *     
     */
    public ProductDetailsType getProductDetails() {
        return productDetails;
    }

    /**
     * Sets the value of the productDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductDetailsType }
     *     
     */
    public void setProductDetails(ProductDetailsType value) {
        this.productDetails = value;
    }

    /**
     * Gets the value of the slacPieces property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSlacPieces() {
        return slacPieces;
    }

    /**
     * Sets the value of the slacPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSlacPieces(BigInteger value) {
        this.slacPieces = value;
    }

    /**
     * Gets the value of the houseDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the houseDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHouseDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HouseType }
     * 
     * 
     */
    public List<HouseType> getHouseDetails() {
        if (houseDetails == null) {
            houseDetails = new ArrayList<HouseType>();
        }
        return this.houseDetails;
    }

    /**
     * Gets the value of the houseDetailsByPage property.
     * 
     * @return
     *     possible object is
     *     {@link HouseDetailsByPage }
     *     
     */
    public HouseDetailsByPage getHouseDetailsByPage() {
        return houseDetailsByPage;
    }

    /**
     * Sets the value of the houseDetailsByPage property.
     * 
     * @param value
     *     allowed object is
     *     {@link HouseDetailsByPage }
     *     
     */
    public void setHouseDetailsByPage(HouseDetailsByPage value) {
        this.houseDetailsByPage = value;
    }

    /**
     * Gets the value of the lastUpdatedTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /**
     * Sets the value of the lastUpdatedTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdatedTime(String value) {
        this.lastUpdatedTime = value;
    }

    /**
     * Gets the value of the lastUpdatedUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    /**
     * Sets the value of the lastUpdatedUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdatedUser(String value) {
        this.lastUpdatedUser = value;
    }

    /**
     * Gets the value of the bookingCreatedUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingCreatedUser() {
        return bookingCreatedUser;
    }

    /**
     * Sets the value of the bookingCreatedUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingCreatedUser(String value) {
        this.bookingCreatedUser = value;
    }

}
