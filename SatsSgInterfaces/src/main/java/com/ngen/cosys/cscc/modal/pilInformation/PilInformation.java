package com.ngen.cosys.cscc.modal.pilInformation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ngen.cosys.cscc.modal.BaseMessageObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class PilInformation extends BaseMessageObject implements Serializable {
    @JsonProperty("message")
    private Message message;

}