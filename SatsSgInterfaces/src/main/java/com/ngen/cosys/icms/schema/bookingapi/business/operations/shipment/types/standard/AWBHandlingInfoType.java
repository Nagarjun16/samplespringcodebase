
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.HandlingCode;


/**
 * 
 * 				The node contains AWB Handling details like SCC, SSR and
 * 				OSI
 * 			
 * 
 * <p>Java class for AWBHandlingInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AWBHandlingInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="specialHandlingCodes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="handlingCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}handlingCode" maxOccurs="45"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="awbSpecialServiceRequest" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t195" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="otherServiceInformation" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t195" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AWBHandlingInfoType", propOrder = {
    "specialHandlingCodes",
    "awbSpecialServiceRequest",
    "otherServiceInformation"
})
public class AWBHandlingInfoType {

    protected AWBHandlingInfoType.SpecialHandlingCodes specialHandlingCodes;
    protected List<String> awbSpecialServiceRequest;
    protected List<String> otherServiceInformation;

    /**
     * Gets the value of the specialHandlingCodes property.
     * 
     * @return
     *     possible object is
     *     {@link AWBHandlingInfoType.SpecialHandlingCodes }
     *     
     */
    public AWBHandlingInfoType.SpecialHandlingCodes getSpecialHandlingCodes() {
        return specialHandlingCodes;
    }

    /**
     * Sets the value of the specialHandlingCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link AWBHandlingInfoType.SpecialHandlingCodes }
     *     
     */
    public void setSpecialHandlingCodes(AWBHandlingInfoType.SpecialHandlingCodes value) {
        this.specialHandlingCodes = value;
    }

    /**
     * Gets the value of the awbSpecialServiceRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the awbSpecialServiceRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAwbSpecialServiceRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAwbSpecialServiceRequest() {
        if (awbSpecialServiceRequest == null) {
            awbSpecialServiceRequest = new ArrayList<String>();
        }
        return this.awbSpecialServiceRequest;
    }

    /**
     * Gets the value of the otherServiceInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otherServiceInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtherServiceInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOtherServiceInformation() {
        if (otherServiceInformation == null) {
            otherServiceInformation = new ArrayList<String>();
        }
        return this.otherServiceInformation;
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
     *         &lt;element name="handlingCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}handlingCode" maxOccurs="45"/>
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
        "handlingCode"
    })
    public static class SpecialHandlingCodes {

        @XmlElement(required = true)
        protected List<HandlingCode> handlingCode;

        /**
         * Gets the value of the handlingCode property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the handlingCode property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHandlingCode().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HandlingCode }
         * 
         * 
         */
        public List<HandlingCode> getHandlingCode() {
            if (handlingCode == null) {
                handlingCode = new ArrayList<HandlingCode>();
            }
            return this.handlingCode;
        }

    }

}
