
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for publishHAWBEntityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="publishHAWBEntityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="publishID" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}publishIDType"/>
 *         &lt;element name="entity" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}entityNameTyp"/>
 *         &lt;element name="publishData" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBSummaryType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "publishHAWBEntityType", propOrder = {
    "publishID",
    "entity",
    "publishData"
})
public class PublishHAWBEntityType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String publishID;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String entity;
    @XmlElement(required = true)
    protected AWBSummaryType publishData;

    /**
     * Gets the value of the publishID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublishID() {
        return publishID;
    }

    /**
     * Sets the value of the publishID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublishID(String value) {
        this.publishID = value;
    }

    /**
     * Gets the value of the entity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntity() {
        return entity;
    }

    /**
     * Sets the value of the entity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntity(String value) {
        this.entity = value;
    }

    /**
     * Gets the value of the publishData property.
     * 
     * @return
     *     possible object is
     *     {@link AWBSummaryType }
     *     
     */
    public AWBSummaryType getPublishData() {
        return publishData;
    }

    /**
     * Sets the value of the publishData property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBSummaryType }
     *     
     */
    public void setPublishData(AWBSummaryType value) {
        this.publishData = value;
    }

}
