package com.ngen.cosys.service.volumetricscanner.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CargoEyeVolWeightResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CargoEyeVolWeightResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private long messageId;
    private String awb;
    private String hawb;
    private long measuredPieces;
    
    private float skidHeight;
    
    private String oddSize;
    private float measuredLength;
    private float measuredWidth;
    private float measuredHeight;
    private float measuredVolume;
    private float measuredVolWeight;
    private String texture;
    private String statusId;
    private String statusDescription;
    private CargoEyeScannedImages scannedImages;
    
}