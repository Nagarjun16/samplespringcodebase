
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.AdditionalIndicatorType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.OperationalFlagType;


/**
 * <p>Java class for iCargoSaveAWBRequestDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="iCargoSaveAWBRequestDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestID" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5"/>
 *         &lt;element name="operationalFlag" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}operationalFlagType" minOccurs="0"/>
 *         &lt;element name="awbDetails" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}AWBDetailsType"/>
 *         &lt;element name="indicators" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}AdditionalIndicatorType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "iCargoSaveAWBRequestDetails", propOrder = {
    "requestID",
    "operationalFlag",
    "awbDetails",
    "indicators"
})
public class ICargoSaveAWBRequestDetails {

    @XmlElement(required = true)
    protected BigInteger requestID;
    @XmlSchemaType(name = "string")
    protected OperationalFlagType operationalFlag;
    @XmlElement(required = true)
    protected AWBDetailsType awbDetails;
    protected List<AdditionalIndicatorType> indicators;

    /**
     * Gets the value of the requestID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRequestID() {
        return requestID;
    }

    /**
     * Sets the value of the requestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRequestID(BigInteger value) {
        this.requestID = value;
    }

    /**
     * Gets the value of the operationalFlag property.
     * 
     * @return
     *     possible object is
     *     {@link OperationalFlagType }
     *     
     */
    public OperationalFlagType getOperationalFlag() {
        return operationalFlag;
    }

    /**
     * Sets the value of the operationalFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationalFlagType }
     *     
     */
    public void setOperationalFlag(OperationalFlagType value) {
        this.operationalFlag = value;
    }

    /**
     * Gets the value of the awbDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AWBDetailsType }
     *     
     */
    public AWBDetailsType getAwbDetails() {
        return awbDetails;
    }

    /**
     * Sets the value of the awbDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBDetailsType }
     *     
     */
    public void setAwbDetails(AWBDetailsType value) {
        this.awbDetails = value;
    }

    /**
     * Gets the value of the indicators property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indicators property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndicators().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdditionalIndicatorType }
     * 
     * 
     */
    public List<AdditionalIndicatorType> getIndicators() {
        if (indicators == null) {
            indicators = new ArrayList<AdditionalIndicatorType>();
        }
        return this.indicators;
    }

}
