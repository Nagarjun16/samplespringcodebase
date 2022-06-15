package com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.model;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class CmdLocalAuthorityInfoModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   private String cmdProcessingId;
   private String awbPrefix;
   private String awbSerialNumber;
   private String localAuthorityType;
   private String permitNumber;
   private String houseWayBillNumber;

}