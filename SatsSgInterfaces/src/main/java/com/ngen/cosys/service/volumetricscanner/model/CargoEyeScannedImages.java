package com.ngen.cosys.service.volumetricscanner.model;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CargoEyeScannedImages
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoEyeScannedImages implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<CargoEyeScannerImage> images;
}