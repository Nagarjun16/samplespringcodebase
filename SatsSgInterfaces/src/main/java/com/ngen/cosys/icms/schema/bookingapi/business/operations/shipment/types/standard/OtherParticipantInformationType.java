
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OtherParticipantInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OtherParticipantInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="otherParticipantDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}CustomerDetailsType"/>
 *         &lt;element name="officeFileReference" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OtherParticipantInformationType", propOrder = {
    "otherParticipantDetails",
    "officeFileReference"
})
public class OtherParticipantInformationType {

    @XmlElement(required = true)
    protected CustomerDetailsType otherParticipantDetails;
    protected String officeFileReference;

    /**
     * Gets the value of the otherParticipantDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerDetailsType }
     *     
     */
    public CustomerDetailsType getOtherParticipantDetails() {
        return otherParticipantDetails;
    }

    /**
     * Sets the value of the otherParticipantDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerDetailsType }
     *     
     */
    public void setOtherParticipantDetails(CustomerDetailsType value) {
        this.otherParticipantDetails = value;
    }

    /**
     * Gets the value of the officeFileReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfficeFileReference() {
        return officeFileReference;
    }

    /**
     * Sets the value of the officeFileReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfficeFileReference(String value) {
        this.officeFileReference = value;
    }

}
