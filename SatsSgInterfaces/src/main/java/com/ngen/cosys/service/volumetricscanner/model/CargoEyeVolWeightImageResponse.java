package com.ngen.cosys.service.volumetricscanner.model;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * CargoEyeVolWeightImageResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoEyeVolWeightImageResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private long messageId;
    private String awb;
    private String[] scannedImages;

    private ArrayList<ScannedImage> images;

    public void prepareScannedImages() {
        if (this.scannedImages != null && this.scannedImages.length > 0) {
            this.images = new ArrayList<>();
            for (int i=0; i<this.scannedImages.length; i++) {
            	String scannedImage = this.scannedImages[i];
                ScannedImage imageDocument = ScannedImage.builder().referenceId(String.valueOf(this.messageId)).entityType("AWB").entityKey(this.awb).associatedTo(String.valueOf(this.messageId))
                    .documentFormat("image/jpg").documentType("IMAGE/JPEG").stage("INTERFACE").document(scannedImage).documentName(String.valueOf(i)+".jpg").build();
                imageDocument.setCreatedBy("SYSO");
                this.images.add(imageDocument);
            }
        }
    }

}