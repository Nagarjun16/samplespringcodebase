package com.ngen.cosys.cscc.modal.keyShipmentInformation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Generated;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class BodyItem implements Serializable {
    @JsonProperty("flightNo")
    private String flightNo;

    @JsonProperty("flightDate")
    private String flightDate;

    @JsonProperty("importExport")
    private String importExport;

    @JsonProperty("shipments")
    private List<ShipmentsItem> shipments;

}