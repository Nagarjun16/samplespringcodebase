
package com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for serviceCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="serviceCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="E"/>
 *     &lt;enumeration value="T"/>
 *     &lt;enumeration value="H"/>
 *     &lt;enumeration value="C"/>
 *     &lt;enumeration value="I"/>
 *     &lt;enumeration value="G"/>
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="X"/>
 *     &lt;enumeration value="F"/>
 *     &lt;enumeration value="J"/>
 *     &lt;enumeration value="B"/>
 *     &lt;enumeration value="P"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "serviceCodeType")
@XmlEnum
public enum ServiceCodeType {

    A,
    E,
    T,
    H,
    C,
    I,
    G,
    D,
    X,
    F,
    J,
    B,
    P;

    public String value() {
        return name();
    }

    public static ServiceCodeType fromValue(String v) {
        return valueOf(v);
    }

}
