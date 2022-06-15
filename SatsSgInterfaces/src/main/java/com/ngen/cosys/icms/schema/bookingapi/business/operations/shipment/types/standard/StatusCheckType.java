
package com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatusCheckType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatusCheckType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="F"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StatusCheckType")
@XmlEnum
public enum StatusCheckType {

    S,
    F;

    public String value() {
        return name();
    }

    public static StatusCheckType fromValue(String v) {
        return valueOf(v);
    }

}
