package com.ngen.cosys.cscc.modal.pilInformation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PILsItem {
    @JsonProperty("AWBNo")
    private String AWBNo;

    @JsonProperty("flightNumberIN")
    private List<String> flightNumberIN;

    @JsonProperty("towInTime")
    private String towInTime;

    @JsonProperty("intoColdRoomTime")
    private String intoColdRoomTime;

    @JsonProperty("ULDNumIN")
    private List<String> ULDNumIN;

    @JsonProperty("PHC")
    private boolean PHC;

    @JsonProperty("transshipment")
    private boolean transshipment;

    @JsonProperty("flightNumOut")
    private List<String> flightNumOut;

    @JsonProperty("ULDNumOut")
    private List<String> ULDNumOut;

    @JsonProperty("outOfColdRoomTime")
    private String outOfColdRoomTime;

    @JsonProperty("rampReleaseTime")
    private String rampReleaseTime;

    @JsonProperty("XAF1")
    private String XAF1;

    @JsonProperty("XAF2")
    private String XAF2;

    @JsonProperty("XAF3")
    private String XAF3;
}