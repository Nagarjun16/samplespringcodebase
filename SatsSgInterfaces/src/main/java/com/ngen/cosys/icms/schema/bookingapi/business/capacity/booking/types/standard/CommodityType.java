
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommodityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommodityType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01}AbstractCommodityType">
 *       &lt;sequence>
 *         &lt;element name="dimensionDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01}DimensionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="uldInfoDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01}UldInfoDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommodityType", namespace = "http://www.ibsplc.com/icargo/services/types/CapacityBookingService/standard/2012/12/12_01", propOrder = {
    "dimensionDetails",
    "uldInfoDetails"
})
public class CommodityType
    extends AbstractCommodityType
{

    protected List<DimensionType> dimensionDetaills;
    protected List<UldInfoDetailsType> uldInfoDetails;

    /**
     * Gets the value of the dimensionDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dimensionDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDimensionDetaills().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DimensionType }
     * 
     * 
     */
    public List<DimensionType> getDimensionDetaills() {
        if (dimensionDetaills == null) {
            dimensionDetaills = new ArrayList<DimensionType>();
        }
        return this.dimensionDetaills;
    }

    /**
     * Gets the value of the uldInfoDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the uldInfoDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUldInfoDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UldInfoDetailsType }
     * 
     * 
     */
    public List<UldInfoDetailsType> getUldInfoDetails() {
        if (uldInfoDetails == null) {
            uldInfoDetails = new ArrayList<UldInfoDetailsType>();
        }
        return this.uldInfoDetails;
    }

}
