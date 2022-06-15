package com.ngen.cosys.service.volumetricscanner.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CargoEyeScannerImage
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoEyeScannerImage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private long imageId;
    private String imageValue;
    private String url;
}