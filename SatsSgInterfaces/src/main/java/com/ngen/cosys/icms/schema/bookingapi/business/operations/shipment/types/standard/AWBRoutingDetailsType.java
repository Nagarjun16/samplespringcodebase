
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				AWB Routing details. The parent node which contains
 * 				different routing nodes.
 * 			
 * 
 * <p>Java class for AWBRoutingDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBRoutingDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="awbRouting" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBRoutingType" maxOccurs="5"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AWBRoutingDetailsType", propOrder = {
    "awbRouting"
})
public class AWBRoutingDetailsType {

    @XmlElement(required = true)
    protected List<AWBRoutingType> awbRouting;

    /**
     * Gets the value of the awbRouting property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the awbRouting property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAwbRouting().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AWBRoutingType }
     * 
     * 
     */
    public List<AWBRoutingType> getAwbRouting() {
        if (awbRouting == null) {
            awbRouting = new ArrayList<AWBRoutingType>();
        }
        return this.awbRouting;
    }

}
