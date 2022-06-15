package com.ngen.cosys.cscc.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BaseMessageObject {
    @JsonIgnore
    private Errors errors;
    @JsonIgnore
    private List<ErrorMessage> errorList;

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public List<ErrorMessage> gerErrorList() {
        if (errorList == null) {
            errorList = new ArrayList<>();
        }
        return errorList;
    }

    public void addError(ErrorMessage error) {
        gerErrorList().add(error);
    }
}
