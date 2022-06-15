
package com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomsOriginType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CustomsOriginType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="C"/>
 *     &lt;enumeration value="T1"/>
 *     &lt;enumeration value="T2"/>
 *     &lt;enumeration value="TD"/>
 *     &lt;enumeration value="TF"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CustomsOriginType")
@XmlEnum
public enum CustomsOriginType {

    C("C"),
    @XmlEnumValue("T1")
    T_1("T1"),
    @XmlEnumValue("T2")
    T_2("T2"),
    TD("TD"),
    TF("TF");
    private final String value;

    CustomsOriginType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CustomsOriginType fromValue(String v) {
        for (CustomsOriginType c: CustomsOriginType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
