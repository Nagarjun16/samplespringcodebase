
package com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for participantIdentifierType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="participantIdentifierType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="AIR"/>
 *     &lt;enumeration value="APT"/>
 *     &lt;enumeration value="AGT"/>
 *     &lt;enumeration value="BRK"/>
 *     &lt;enumeration value="CAG"/>
 *     &lt;enumeration value="CNE"/>
 *     &lt;enumeration value="CTM"/>
 *     &lt;enumeration value="DCL"/>
 *     &lt;enumeration value="DEC"/>
 *     &lt;enumeration value="FFW"/>
 *     &lt;enumeration value="GHA"/>
 *     &lt;enumeration value="PTT"/>
 *     &lt;enumeration value="SHP"/>
 *     &lt;enumeration value="TRK"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "participantIdentifierType")
@XmlEnum
public enum ParticipantIdentifierType {

    AIR,
    APT,
    AGT,
    BRK,
    CAG,
    CNE,
    CTM,
    DCL,
    DEC,
    FFW,
    GHA,
    PTT,
    SHP,
    TRK;

    public String value() {
        return name();
    }

    public static ParticipantIdentifierType fromValue(String v) {
        return valueOf(v);
    }

}
