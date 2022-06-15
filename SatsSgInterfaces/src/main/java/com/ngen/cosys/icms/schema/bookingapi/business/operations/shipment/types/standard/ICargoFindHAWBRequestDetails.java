
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.OperationalFlagType;
import com.ngen.cosys.icms.schema.bookingapi.framework.services.jaxws.types.common.PageFilter;


/**
 * <p>Java class for iCargoFindHAWBRequestDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="iCargoFindHAWBRequestDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestID" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}n5"/>
 *         &lt;element name="operationalFlag" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}operationalFlagType" minOccurs="0"/>
 *         &lt;element name="hawbDetailsFilter">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="shipmentDetailsFilter" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}shipmentDetailsFilterType"/>
 *                   &lt;element name="hawbNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}hawbNumber" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pageFilter" type="{http://www.ibsplc.com/icargo/services}PageFilter" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "iCargoFindHAWBRequestDetails", propOrder = {
    "requestID",
    "operationalFlag",
    "hawbDetailsFilter",
    "pageFilter"
})
public class ICargoFindHAWBRequestDetails {

    @XmlElement(required = true)
    protected BigInteger requestID;
    @XmlSchemaType(name = "string")
    protected OperationalFlagType operationalFlag;
    @XmlElement(required = true)
    protected ICargoFindHAWBRequestDetails.HawbDetailsFilter hawbDetailsFilter;
    protected PageFilter pageFilter;

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
     * Gets the value of the hawbDetailsFilter property.
     * 
     * @return
     *     possible object is
     *     {@link ICargoFindHAWBRequestDetails.HawbDetailsFilter }
     *     
     */
    public ICargoFindHAWBRequestDetails.HawbDetailsFilter getHawbDetailsFilter() {
        return hawbDetailsFilter;
    }

    /**
     * Sets the value of the hawbDetailsFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link ICargoFindHAWBRequestDetails.HawbDetailsFilter }
     *     
     */
    public void setHawbDetailsFilter(ICargoFindHAWBRequestDetails.HawbDetailsFilter value) {
        this.hawbDetailsFilter = value;
    }

    /**
     * Gets the value of the pageFilter property.
     * 
     * @return
     *     possible object is
     *     {@link PageFilter }
     *     
     */
    public PageFilter getPageFilter() {
        return pageFilter;
    }

    /**
     * Sets the value of the pageFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link PageFilter }
     *     
     */
    public void setPageFilter(PageFilter value) {
        this.pageFilter = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="shipmentDetailsFilter" type="{http://www.ibsplc.com/icargo/types/AWBDetailsTypes/standard}shipmentDetailsFilterType"/>
     *         &lt;element name="hawbNumber" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}hawbNumber" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "shipmentDetailsFilter",
        "hawbNumber"
    })
    public static class HawbDetailsFilter {

        @XmlElement(required = true)
        protected ShipmentDetailsFilterType shipmentDetailsFilter;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String hawbNumber;

        /**
         * Gets the value of the shipmentDetailsFilter property.
         * 
         * @return
         *     possible object is
         *     {@link ShipmentDetailsFilterType }
         *     
         */
        public ShipmentDetailsFilterType getShipmentDetailsFilter() {
            return shipmentDetailsFilter;
        }

        /**
         * Sets the value of the shipmentDetailsFilter property.
         * 
         * @param value
         *     allowed object is
         *     {@link ShipmentDetailsFilterType }
         *     
         */
        public void setShipmentDetailsFilter(ShipmentDetailsFilterType value) {
            this.shipmentDetailsFilter = value;
        }

        /**
         * Gets the value of the hawbNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHawbNumber() {
            return hawbNumber;
        }

        /**
         * Sets the value of the hawbNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHawbNumber(String value) {
            this.hawbNumber = value;
        }

    }

}
