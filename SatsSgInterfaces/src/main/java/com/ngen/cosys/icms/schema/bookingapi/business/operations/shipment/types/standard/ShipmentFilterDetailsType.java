
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ShipmentFilterDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipmentFilterDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shipmentPrefix" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}shipmentPrefix"/>
 *         &lt;element name="masterDocumentNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}masterDocumentNumber"/>
 *         &lt;element name="duplicateNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}duplicateNumber" minOccurs="0"/>
 *         &lt;element name="sequenceNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}sequenceNumber" minOccurs="0"/>
 *         &lt;element name="ownerId" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}ownerId" minOccurs="0"/>
 *         &lt;element name="hawbNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}hawbNumber" minOccurs="0"/>
 *         &lt;element name="messageType" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}MessageType" minOccurs="0"/>
 *         &lt;element name="messageDirection" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="I"/>
 *               &lt;enumeration value="O"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipmentFilterDetailsType", propOrder = {
    "shipmentPrefix",
    "masterDocumentNumber",
    "duplicateNumber",
    "sequenceNumber",
    "ownerId",
    "hawbNumber",
    "messageType",
    "messageDirection"
})
public class ShipmentFilterDetailsType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String shipmentPrefix;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String masterDocumentNumber;
    protected BigInteger duplicateNumber;
    protected BigInteger sequenceNumber;
    protected BigInteger ownerId;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String hawbNumber;
    protected MessageType messageType;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String messageDirection;

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
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDuplicateNumber() {
        return duplicateNumber;
    }

    /**
     * Sets the value of the duplicateNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDuplicateNumber(BigInteger value) {
        this.duplicateNumber = value;
    }

    /**
     * Gets the value of the sequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSequenceNumber(BigInteger value) {
        this.sequenceNumber = value;
    }

    /**
     * Gets the value of the ownerId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOwnerId() {
        return ownerId;
    }

    /**
     * Sets the value of the ownerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOwnerId(BigInteger value) {
        this.ownerId = value;
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
     * Gets the value of the messageType property.
     * 
     * @return
     *     possible object is
     *     {@link MessageType }
     *     
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * Sets the value of the messageType property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageType }
     *     
     */
    public void setMessageType(MessageType value) {
        this.messageType = value;
    }

    /**
     * Gets the value of the messageDirection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageDirection() {
        return messageDirection;
    }

    /**
     * Sets the value of the messageDirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageDirection(String value) {
        this.messageDirection = value;
    }

}
