
package com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for chargeCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="chargeCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="CC"/>
 *     &lt;enumeration value="CZ"/>
 *     &lt;enumeration value="CG"/>
 *     &lt;enumeration value="PP"/>
 *     &lt;enumeration value="PX"/>
 *     &lt;enumeration value="PZ"/>
 *     &lt;enumeration value="PG"/>
 *     &lt;enumeration value="CP"/>
 *     &lt;enumeration value="CX"/>
 *     &lt;enumeration value="CM"/>
 *     &lt;enumeration value="NC"/>
 *     &lt;enumeration value="NT"/>
 *     &lt;enumeration value="NZ"/>
 *     &lt;enumeration value="NG"/>
 *     &lt;enumeration value="NP"/>
 *     &lt;enumeration value="NX"/>
 *     &lt;enumeration value="CA"/>
 *     &lt;enumeration value="CB"/>
 *     &lt;enumeration value="CE"/>
 *     &lt;enumeration value="CH"/>
 *     &lt;enumeration value="PC"/>
 *     &lt;enumeration value="PD"/>
 *     &lt;enumeration value="PE"/>
 *     &lt;enumeration value="PH"/>
 *     &lt;enumeration value="PF"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "chargeCode")
@XmlEnum
public enum ChargeCode {

    CC,
    CZ,
    CG,
    PP,
    PX,
    PZ,
    PG,
    CP,
    CX,
    CM,
    NC,
    NT,
    NZ,
    NG,
    NP,
    NX,
    CA,
    CB,
    CE,
    CH,
    PC,
    PD,
    PE,
    PH,
    PF;

    public String value() {
        return name();
    }

    public static ChargeCode fromValue(String v) {
        return valueOf(v);
    }

}
