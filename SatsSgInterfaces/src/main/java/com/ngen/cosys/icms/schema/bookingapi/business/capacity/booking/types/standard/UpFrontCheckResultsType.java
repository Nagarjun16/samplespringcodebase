
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpFrontCheckResultsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpFrontCheckResultsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="upFrontCheckResult" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01}UpFrontCheckResultType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpFrontCheckResultsType", namespace = "http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01", propOrder = {
    "upFrontCheckResult"
})
public class UpFrontCheckResultsType {

    protected List<UpFrontCheckResultType> upFrontCheckResult;

    /**
     * Gets the value of the upFrontCheckResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the upFrontCheckResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpFrontCheckResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UpFrontCheckResultType }
     * 
     * 
     */
    public List<UpFrontCheckResultType> getUpFrontCheckResult() {
        if (upFrontCheckResult == null) {
            upFrontCheckResult = new ArrayList<UpFrontCheckResultType>();
        }
        return this.upFrontCheckResult;
    }

}
