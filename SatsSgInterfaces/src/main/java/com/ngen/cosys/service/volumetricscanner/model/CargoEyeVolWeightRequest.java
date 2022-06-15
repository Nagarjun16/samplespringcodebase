package com.ngen.cosys.service.volumetricscanner.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CargoEyeVolWeightRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoEyeVolWeightRequest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private long messageId;
    private String awb;
    
    private String hawb;
    
    private long measuredPieces;
    private float skidHeight;
    private float declaredVolume;
    private String oddSize;
    
}