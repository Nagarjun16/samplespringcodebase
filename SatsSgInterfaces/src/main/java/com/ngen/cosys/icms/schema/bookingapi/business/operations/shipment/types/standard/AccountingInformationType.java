
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * 				Accounting Information Type. The node contains the
 * 				accounting info details captured against the AWB. The
 * 				node is part of the FWB.
 * 			
 * 
 * <p>Java class for AccountingInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountingInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountingInformationIdentifier">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="CRN"/>
 *               &lt;enumeration value="CRD"/>
 *               &lt;enumeration value="CRI"/>
 *               &lt;enumeration value="GEN"/>
 *               &lt;enumeration value="GBL"/>
 *               &lt;enumeration value="MCO"/>
 *               &lt;enumeration value="STL"/>
 *               &lt;enumeration value="RET"/>
 *               &lt;enumeration value="SRN"/>
 *               &lt;enumeration value="HLC"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="accountingInformation" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t70"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountingInformationType", propOrder = {
    "accountingInformationIdentifier",
    "accountingInformation"
})
public class AccountingInformationType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String accountingInformationIdentifier;
    @XmlElement(required = true)
    protected String accountingInformation;

    /**
     * Gets the value of the accountingInformationIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountingInformationIdentifier() {
        return accountingInformationIdentifier;
    }

    /**
     * Sets the value of the accountingInformationIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountingInformationIdentifier(String value) {
        this.accountingInformationIdentifier = value;
    }

    /**
     * Gets the value of the accountingInformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountingInformation() {
        return accountingInformation;
    }

    /**
     * Sets the value of the accountingInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountingInformation(String value) {
        this.accountingInformation = value;
    }

}
