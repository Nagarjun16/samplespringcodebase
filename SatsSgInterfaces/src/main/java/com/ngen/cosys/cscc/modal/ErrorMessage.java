package com.ngen.cosys.cscc.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {
    String errorNumber;
    String errorCode;
    String errorDesc;
}
