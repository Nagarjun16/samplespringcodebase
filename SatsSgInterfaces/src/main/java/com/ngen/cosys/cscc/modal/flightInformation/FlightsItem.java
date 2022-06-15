package com.ngen.cosys.cscc.modal.flightInformation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;
import java.io.Serializable;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        creatorVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class FlightsItem implements Serializable {
    @JsonProperty("flightNo")
    private String flightNo;

    @JsonProperty("importExport")
    private String importExport;

    @JsonProperty("STA")
    private String sTA;

    @JsonProperty("ETA")
    private String eTA;

    @JsonProperty("ATA")
    private String aTA;

    @JsonProperty("flightType")
    private String flightType;

    @JsonProperty("aircraftType")
    private String aircraftType;

    @JsonProperty("bay")
    private String bay;

    @JsonProperty("segment")
    private String segment;

    @JsonProperty("REG")
    private String rEG;

    @JsonProperty("STD")
    private String sTD;

    @JsonProperty("ETD")
    private String eTD;

    @JsonProperty("ATD")
    private String aTD;

    @JsonProperty("palletTotal")
    private Integer palletTotal;

    @JsonProperty("palletCargoOffer")
    private Integer palletCargoOffer;

    @JsonProperty("palletCargoUsed")
    private Integer palletCargoUsed;

    @JsonProperty("containerTotal")
    private Integer containerTotal;

    @JsonProperty("containerCargoOffer")
    private Integer containerCargoOffer;

    @JsonProperty("containerCargoUsed")
    private Integer containerCargoUsed;

    @JsonProperty("LastULDTowIn")
    private String lastULDTowIn;

    @JsonProperty("BDComplete")
    private String bDComplete;

    @JsonProperty("DocComplete")
    private String docComplete;

    @JsonProperty("NOTOC")
    private String nOTOC;

    @JsonProperty("Manifest")
    private String manifest;

    @JsonProperty("PouchComplete")
    private String pouchComplete;

    @JsonProperty("DLS")
    private String dLS;

    @JsonProperty("firstDLS")
    private String firstDLS;

    @JsonProperty("releaseTimingULD")
    private String releaseTimingULD;

    @JsonProperty("NOTOCEntries")
    private int nOTOCEntries;

    @JsonProperty("flightComplete")
    private String flightComplete;

    @JsonProperty("FFMReceived")
    private String fFMReceived;

    @JsonProperty("FDL")
    private boolean fDL;

    @JsonProperty("XAF1")
    private String xAF1;

    @JsonProperty("XAF2")
    private String xAF2;

    @JsonProperty("XAF3")
    private String xAF3;
}