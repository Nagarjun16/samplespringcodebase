
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ShipmentScreeningDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipmentScreeningDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="screeningSerialNumber" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="screeningMethod" type="{http://www.ibsplc.com/icargo/services/types/SharedDefaultService/ICargoCommon/standard/2012/12/12_01}t35" minOccurs="0"/>
 *         &lt;element name="anyOtherMethod" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="31"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="screeningResult" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="screeningPieces" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalNumberOfPieces" minOccurs="0"/>
 *         &lt;element name="screeningWeight" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}totalStatedWeight" minOccurs="0"/>
 *         &lt;element name="screeningAirportCode" type="{http://www.ibsplc.com/icargo/services/types/OperationsShipmentExternalService/ICargoCommonBusiness/standard/2013/02/04_01}airportCode" minOccurs="0"/>
 *         &lt;element name="operationFlag" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="I"/>
 *               &lt;enumeration value="U"/>
 *               &lt;enumeration value="D"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipmentScreeningDetailsType", propOrder = {
    "screeningSerialNumber",
    "screeningMethod",
    "anyOtherMethod",
    "screeningResult",
    "screeningPieces",
    "screeningWeight",
    "screeningAirportCode",
    "operationFlag"
})
public class ShipmentScreeningDetailsType {

    protected BigInteger screeningSerialNumber;
    protected String screeningMethod;
    protected String anyOtherMethod;
    protected Boolean screeningResult;
    protected BigInteger screeningPieces;
    protected BigDecimal screeningWeight;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String screeningAirportCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String operationFlag;

    /**
     * Gets the value of the screeningSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getScreeningSerialNumber() {
        return screeningSerialNumber;
    }

    /**
     * Sets the value of the screeningSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setScreeningSerialNumber(BigInteger value) {
        this.screeningSerialNumber = value;
    }

    /**
     * Gets the value of the screeningMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningMethod() {
        return screeningMethod;
    }

    /**
     * Sets the value of the screeningMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningMethod(String value) {
        this.screeningMethod = value;
    }

    /**
     * Gets the value of the anyOtherMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnyOtherMethod() {
        return anyOtherMethod;
    }

    /**
     * Sets the value of the anyOtherMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnyOtherMethod(String value) {
        this.anyOtherMethod = value;
    }

    /**
     * Gets the value of the screeningResult property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isScreeningResult() {
        return screeningResult;
    }

    /**
     * Sets the value of the screeningResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setScreeningResult(Boolean value) {
        this.screeningResult = value;
    }

    /**
     * Gets the value of the screeningPieces property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getScreeningPieces() {
        return screeningPieces;
    }

    /**
     * Sets the value of the screeningPieces property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setScreeningPieces(BigInteger value) {
        this.screeningPieces = value;
    }

    /**
     * Gets the value of the screeningWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getScreeningWeight() {
        return screeningWeight;
    }

    /**
     * Sets the value of the screeningWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setScreeningWeight(BigDecimal value) {
        this.screeningWeight = value;
    }

    /**
     * Gets the value of the screeningAirportCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningAirportCode() {
        return screeningAirportCode;
    }

    /**
     * Sets the value of the screeningAirportCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningAirportCode(String value) {
        this.screeningAirportCode = value;
    }

    /**
     * Gets the value of the operationFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationFlag() {
        return operationFlag;
    }

    /**
     * Sets the value of the operationFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationFlag(String value) {
        this.operationFlag = value;
    }

}
