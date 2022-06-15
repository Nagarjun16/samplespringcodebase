package com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.model;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@ApiModel
@ToString
@Getter
@Setter
@NoArgsConstructor
public class AttachAndDetatchCargoManifestDeclerationModel extends BaseBO{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private String cmdProcessingId;
   private String awbPrefix;
   private String awbSerialNumber;
   private String hwbNumber;
   private BigInteger earliestFlight;
   private String shipmentNumber;
   private String houseWayBillNumber;
}
