package com.ngen.cosys.cscc.modal.pilInformation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Header {
    @JsonProperty("type")
    private String type;

    @JsonProperty("msgTime")
    private String msgTime;

    @JsonProperty("UUID")
    private String UUID;
}