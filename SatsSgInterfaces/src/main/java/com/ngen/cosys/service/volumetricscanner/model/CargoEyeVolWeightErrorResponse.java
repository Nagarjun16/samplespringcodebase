package com.ngen.cosys.service.volumetricscanner.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CargoEyeVolWeightErrorResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoEyeVolWeightErrorResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private long messageId;
    private String errorFlag;
    private String errorCode;
    
    private String errorDescription;
    
}