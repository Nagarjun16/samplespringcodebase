
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

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
 * <p>Java class for ShipmentMessagesDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipmentMessagesDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shipmentIdentifierDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}ShipmentIdentifierDetailsType"/>
 *         &lt;element name="hawbNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}hawbNumber" minOccurs="0"/>
 *         &lt;element name="messageDetailsType" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}MessageDetailsType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipmentMessagesDetailsType", propOrder = {
    "shipmentIdentifierDetails",
    "hawbNumber",
    "messageDetailsType"
})
public class ShipmentMessagesDetailsType {

    @XmlElement(required = true)
    protected ShipmentIdentifierDetailsType shipmentIdentifierDetails;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String hawbNumber;
    @XmlElement(required = true)
    protected List<MessageDetailsType> messageDetailsType;

    /**
     * Gets the value of the shipmentIdentifierDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentIdentifierDetailsType }
     *     
     */
    public ShipmentIdentifierDetailsType getShipmentIdentifierDetails() {
        return shipmentIdentifierDetails;
    }

    /**
     * Sets the value of the shipmentIdentifierDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentIdentifierDetailsType }
     *     
     */
    public void setShipmentIdentifierDetails(ShipmentIdentifierDetailsType value) {
        this.shipmentIdentifierDetails = value;
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
     * Gets the value of the messageDetailsType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messageDetailsType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessageDetailsType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageDetailsType }
     * 
     * 
     */
    public List<MessageDetailsType> getMessageDetailsType() {
        if (messageDetailsType == null) {
            messageDetailsType = new ArrayList<MessageDetailsType>();
        }
        return this.messageDetailsType;
    }

}
