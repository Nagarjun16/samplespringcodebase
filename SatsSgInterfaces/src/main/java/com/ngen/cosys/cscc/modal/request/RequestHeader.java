package com.ngen.cosys.cscc.modal.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        creatorVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@Getter
public class RequestHeader {

    @JsonProperty("type")
    private String type;

    @JsonProperty("msgTime")
    private String msgTime;

    @JsonProperty("UUID")
    private String UUID;


    @Override
    public String toString() {
        return "RequestHeader{" +
                "msgTime='" + msgTime + '\'' +
                ", type='" + type + '\'' +
                ", uUID='" + UUID + '\'' +
                '}';
    }
}