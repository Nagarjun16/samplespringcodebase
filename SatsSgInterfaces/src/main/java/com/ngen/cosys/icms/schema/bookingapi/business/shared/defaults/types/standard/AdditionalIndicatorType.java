
package com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 						A <b>AdditionalIndicatorType</b>
 * 					
 * 
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdditionalIndicatorType", propOrder = {
    "parameterCode",
    "parameterValue"
})
public class AdditionalIndicatorType {

    protected String parameterCode;
    protected String parameterValue;

    /**
     * Gets the value of the parameterCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParameterCode() {
        return parameterCode;
    }

    /**
     * Sets the value of the parameterCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParameterCode(String value) {
        this.parameterCode = value;
    }

    /**
     * Gets the value of the parameterValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParameterValue() {
        return parameterValue;
    }

    /**
     * Sets the value of the parameterValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParameterValue(String value) {
        this.parameterValue = value;
    }

}
