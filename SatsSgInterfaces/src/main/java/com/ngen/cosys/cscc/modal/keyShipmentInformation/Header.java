package com.ngen.cosys.cscc.modal.keyShipmentInformation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Generated;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class Header implements Serializable {
    @JsonProperty("type")
    private String type;

    @JsonProperty("msgTime")
    private String msgTime;

    @JsonProperty("UUID")
    private String UUID;
}