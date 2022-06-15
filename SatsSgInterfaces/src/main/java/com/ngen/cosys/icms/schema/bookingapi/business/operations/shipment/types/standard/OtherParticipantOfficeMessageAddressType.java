
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for OtherParticipantOfficeMessageAddressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OtherParticipantOfficeMessageAddressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="airportOrCityCode" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m3" minOccurs="0"/>
 *         &lt;element name="officeFunctionDesignator" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m1_2"/>
 *         &lt;element name="companyDesignator" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}m1_2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OtherParticipantOfficeMessageAddressType", propOrder = {
    "airportOrCityCode",
    "officeFunctionDesignator",
    "companyDesignator"
})
public class OtherParticipantOfficeMessageAddressType {

    protected String airportOrCityCode;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String officeFunctionDesignator;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String companyDesignator;

    /**
     * Gets the value of the airportOrCityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirportOrCityCode() {
        return airportOrCityCode;
    }

    /**
     * Sets the value of the airportOrCityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirportOrCityCode(String value) {
        this.airportOrCityCode = value;
    }

    /**
     * Gets the value of the officeFunctionDesignator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfficeFunctionDesignator() {
        return officeFunctionDesignator;
    }

    /**
     * Sets the value of the officeFunctionDesignator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfficeFunctionDesignator(String value) {
        this.officeFunctionDesignator = value;
    }

    /**
     * Gets the value of the companyDesignator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyDesignator() {
        return companyDesignator;
    }

    /**
     * Sets the value of the companyDesignator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyDesignator(String value) {
        this.companyDesignator = value;
    }

}
