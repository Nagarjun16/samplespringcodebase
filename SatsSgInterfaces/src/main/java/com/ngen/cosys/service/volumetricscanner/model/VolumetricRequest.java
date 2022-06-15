package com.ngen.cosys.service.volumetricscanner.model;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class VolumetricRequest extends BaseBO {

   private static final long serialVersionUID = 1L;
   
   private long messageId;
   private String scannerType;
   private String scannerId;
   private String shipmentNumber;
   
   private String hawb;
   private String oddSize;
   private float declaredVolume;
   private float skidHeight;
   private long measuredPieces;
   private String scannerIP;
}
