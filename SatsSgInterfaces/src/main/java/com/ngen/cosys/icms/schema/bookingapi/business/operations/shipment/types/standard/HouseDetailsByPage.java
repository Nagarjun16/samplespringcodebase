
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.framework.services.jaxws.types.common.PageType;


/**
 * <p>Java class for HouseDetailsByPage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HouseDetailsByPage">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ibsplc.com/icargo/services}pageType">
 *       &lt;sequence>
 *         &lt;element name="houseDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}HouseType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HouseDetailsByPage", propOrder = {
    "houseDetails"
})
public class HouseDetailsByPage
    extends PageType
{

    protected List<HouseType> houseDetails;

    /**
     * Gets the value of the houseDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the houseDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHouseDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HouseType }
     * 
     * 
     */
    public List<HouseType> getHouseDetails() {
        if (houseDetails == null) {
            houseDetails = new ArrayList<HouseType>();
        }
        return this.houseDetails;
    }

}
