
package com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for volumeUnit.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="volumeUnit">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="CC"/>
 *     &lt;enumeration value="CF"/>
 *     &lt;enumeration value="CI"/>
 *     &lt;enumeration value="MC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "volumeUnit")
@XmlEnum
public enum VolumeUnit {

    CC,
    CF,
    CI,
    MC;

    public String value() {
        return name();
    }

    public static VolumeUnit fromValue(String v) {
        return valueOf(v);
    }

}
