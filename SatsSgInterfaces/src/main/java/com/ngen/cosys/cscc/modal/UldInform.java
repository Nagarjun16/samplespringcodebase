package com.ngen.cosys.cscc.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cr148_CargoInformation
 *
 * @author
 */
@Data
public class UldInform implements Serializable {
    private String ULDNo;
    private String SHC;
    private BigDecimal weight;
    private String importExport;
    private String flightNo;
    private String flightDate;
    private String TTHConnectedFlightNo;
    private String TTHConnectedFlightDate;
    private String rampReleaseTime;
    private String handlingArea;
    private String towInTimeByAFT;
    private String warehouseLocation;
    private String breakdownTime;
    private String XAF1;
    private String XAF2;
    private String XAF3;

}