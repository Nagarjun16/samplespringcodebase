
package com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DangerousGoodsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DangerousGoodsType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}AbstractDangerousGoodsType">
 *       &lt;sequence>
 *         &lt;element name="nuclideDetails" type="{http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01}DGRNuclideDetailType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DangerousGoodsType", propOrder = {
    "nuclideDetails"
})
public class DangerousGoodsType
    extends AbstractDangerousGoodsType
{

    protected List<DGRNuclideDetailType> nuclideDetails;

    /**
     * Gets the value of the nuclideDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nuclideDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNuclideDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DGRNuclideDetailType }
     * 
     * 
     */
    public List<DGRNuclideDetailType> getNuclideDetails() {
        if (nuclideDetails == null) {
            nuclideDetails = new ArrayList<DGRNuclideDetailType>();
        }
        return this.nuclideDetails;
    }

}
