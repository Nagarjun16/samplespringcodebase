
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.MessageHeaderType;


/**
 * <p>Java class for AWBValidationRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBValidationRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageHeader" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}messageHeaderType"/>
 *         &lt;element name="requestData" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBValidationRequestDetails"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AWBValidationRequestType", propOrder = {
    "messageHeader",
    "requestData"
})
public class AWBValidationRequestType {

    @XmlElement(required = true)
    protected MessageHeaderType messageHeader;
    @XmlElement(required = true)
    protected AWBValidationRequestDetails requestData;

    /**
     * Gets the value of the messageHeader property.
     * 
     * @return
     *     possible object is
     *     {@link MessageHeaderType }
     *     
     */
    public MessageHeaderType getMessageHeader() {
        return messageHeader;
    }

    /**
     * Sets the value of the messageHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageHeaderType }
     *     
     */
    public void setMessageHeader(MessageHeaderType value) {
        this.messageHeader = value;
    }

    /**
     * Gets the value of the requestData property.
     * 
     * @return
     *     possible object is
     *     {@link AWBValidationRequestDetails }
     *     
     */
    public AWBValidationRequestDetails getRequestData() {
        return requestData;
    }

    /**
     * Sets the value of the requestData property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBValidationRequestDetails }
     *     
     */
    public void setRequestData(AWBValidationRequestDetails value) {
        this.requestData = value;
    }

}
