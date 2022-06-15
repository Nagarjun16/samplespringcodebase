
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				The node contains different CustomerContact node
 * 			
 * 
 * <p>Java class for CustomerContactDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerContactDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customerContact" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}CustomerContactType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerContactDetailsType", propOrder = {
    "customerContact"
})
public class CustomerContactDetailsType {

    @XmlElement(required = true)
    protected List<CustomerContactType> customerContact;

    /**
     * Gets the value of the customerContact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customerContact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomerContact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomerContactType }
     * 
     * 
     */
    public List<CustomerContactType> getCustomerContact() {
        if (customerContact == null) {
            customerContact = new ArrayList<CustomerContactType>();
        }
        return this.customerContact;
    }

}
