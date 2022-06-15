
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for AWBExecutionDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBExecutionDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="awbIssueDate" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}dateTime" minOccurs="0"/>
 *         &lt;element name="placeOfExecution" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *         &lt;element name="executedUser" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AWBExecutionDetailsType", propOrder = {
    "awbIssueDate",
    "placeOfExecution",
    "executedUser"
})
public class AWBExecutionDetailsType {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String awbIssueDate;
    protected String placeOfExecution;
    protected String executedUser;

    /**
     * Gets the value of the awbIssueDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwbIssueDate() {
        return awbIssueDate;
    }

    /**
     * Sets the value of the awbIssueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwbIssueDate(String value) {
        this.awbIssueDate = value;
    }

    /**
     * Gets the value of the placeOfExecution property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlaceOfExecution() {
        return placeOfExecution;
    }

    /**
     * Sets the value of the placeOfExecution property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlaceOfExecution(String value) {
        this.placeOfExecution = value;
    }

    /**
     * Gets the value of the executedUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExecutedUser() {
        return executedUser;
    }

    /**
     * Sets the value of the executedUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExecutedUser(String value) {
        this.executedUser = value;
    }

}
