package com.ngen.cosys.ulms.model;

import com.ngen.cosys.framework.model.BaseBO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ULMSInterfaceDetail extends BaseBO {
    private Long flightId;
    private Long newFlightId;
    private String arrivalBay;
    private String departureBay;
    private String flightNumber;
    private String flightDate;
    private String carrierCode;
    private String flightNumberOnly;
    private LocalDateTime flightOriginDate;
    private String dateStd;
    private String dateSta;
    private String aircraftRegCode;
    private String uldNumber;
    private String pdNumber;
    private String importExportFlag;
    private String startDate;
    private String endDate;
    private String changeType;
    private String associatedPd;
    private String operativeFlightStatus;
    private String flightStatus;
    private String flightCancelFlag;
    private String throughTransitFlag;

    private HeaderInfo  headerInfo;

}
