package com.ngen.cosys.cscc.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Errors {
    List<ErrorMessage> errors;
}
