
package com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for unitOfLength.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="unitOfLength">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="BQL"/>
 *     &lt;enumeration value="CGM"/>
 *     &lt;enumeration value="CLT"/>
 *     &lt;enumeration value="CMT"/>
 *     &lt;enumeration value="CUR"/>
 *     &lt;enumeration value="DLT"/>
 *     &lt;enumeration value="DMT"/>
 *     &lt;enumeration value="OZI"/>
 *     &lt;enumeration value="OZA"/>
 *     &lt;enumeration value="FOT"/>
 *     &lt;enumeration value="GLI"/>
 *     &lt;enumeration value="GII"/>
 *     &lt;enumeration value="GIA"/>
 *     &lt;enumeration value="GRM"/>
 *     &lt;enumeration value="INH"/>
 *     &lt;enumeration value="KBQ"/>
 *     &lt;enumeration value="KGM"/>
 *     &lt;enumeration value="GLL"/>
 *     &lt;enumeration value="PTL"/>
 *     &lt;enumeration value="QTL"/>
 *     &lt;enumeration value="LTR"/>
 *     &lt;enumeration value="MBQ"/>
 *     &lt;enumeration value="MTR"/>
 *     &lt;enumeration value="MLT"/>
 *     &lt;enumeration value="MMT"/>
 *     &lt;enumeration value="ONZ"/>
 *     &lt;enumeration value="PTI"/>
 *     &lt;enumeration value="LBR"/>
 *     &lt;enumeration value="QTI"/>
 *     &lt;enumeration value="TBQ"/>
 *     &lt;enumeration value="YRD"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "unitOfLength")
@XmlEnum
public enum UnitOfLength {

    BQL,
    CGM,
    CLT,
    CMT,
    CUR,
    DLT,
    DMT,
    OZI,
    OZA,
    FOT,
    GLI,
    GII,
    GIA,
    GRM,
    INH,
    KBQ,
    KGM,
    GLL,
    PTL,
    QTL,
    LTR,
    MBQ,
    MTR,
    MLT,
    MMT,
    ONZ,
    PTI,
    LBR,
    QTI,
    TBQ,
    YRD;

    public String value() {
        return name();
    }

    public static UnitOfLength fromValue(String v) {
        return valueOf(v);
    }

}
