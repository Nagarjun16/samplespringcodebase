package com.ngen.cosys.cscc.modal.uldInformation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@ToString
public class ULDsItem {
    @JsonProperty("ULDNo")
    private String ULDNo;
    @JsonProperty("SHC")
    private String SHC;
    @JsonProperty("weight")
    private BigDecimal weight;
    @JsonProperty("importExport")
    private String importExport;
    @JsonProperty("flightNo")
    private String flightNo;
    @JsonProperty("flightDate")
    private String flightDate;
    @JsonProperty("TTHConnectedFlightNo")
    private String TTHConnectedFlightNo;
    @JsonProperty("TTHConnectedFlightDate")
    private String TTHConnectedFlightDate;
    @JsonProperty("rampReleaseTime")
    private String rampReleaseTime;
    @JsonProperty("warehouseLocation")
    private String warehouseLocation;
    @JsonProperty("towInTimeByAFT")
    private String towInTimeByAFT;
    @JsonProperty("breakdownTime")
    private String breakdownTime;
    @JsonProperty("XAF1")
    private String XAF1;
    @JsonProperty("XAF2")
    private String XAF2;
    @JsonProperty("XAF3")
    private String XAF3;
}