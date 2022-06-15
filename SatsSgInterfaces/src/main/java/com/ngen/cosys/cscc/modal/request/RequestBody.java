package com.ngen.cosys.cscc.modal.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        creatorVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@Getter
@Setter
@ToString
public class RequestBody {
    @JsonProperty("AWBNo")
    private String awbNo;

    @JsonProperty("ULDNo")
    private String uldNo;

    @JsonProperty("flightNo")
    private String flightNo;

    @JsonProperty("flightDate")
    private String flightDate;

    @JsonProperty("flightOriginDate")
    private LocalDateTime flightOriginDate;

    @JsonProperty("fromTime")
    private String fromTime;

    @JsonProperty("toTime")
    private String toTime;

    @JsonProperty("importExport")
    private String importExport;

    @JsonIgnore
    private String flightCarrier;
    @JsonIgnore
    private String flightNumber;
    @JsonIgnore
    private String flightLocalDate;
    @JsonIgnore
    private String dbFromTime;
    @JsonIgnore
    private String dbToTime;
    @JsonIgnore
    private LocalDateTime shipmentDate;


}