
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				The node contains different AgentContact node
 * 			
 * 
 * <p>Java class for AgentContactDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AgentContactDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="agentContact" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AgentContactType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AgentContactDetailsType", propOrder = {
    "agentContact"
})
public class AgentContactDetailsType {

    @XmlElement(required = true)
    protected List<AgentContactType> agentContact;

    /**
     * Gets the value of the agentContact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the agentContact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAgentContact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AgentContactType }
     * 
     * 
     */
    public List<AgentContactType> getAgentContact() {
        if (agentContact == null) {
            agentContact = new ArrayList<AgentContactType>();
        }
        return this.agentContact;
    }

}
