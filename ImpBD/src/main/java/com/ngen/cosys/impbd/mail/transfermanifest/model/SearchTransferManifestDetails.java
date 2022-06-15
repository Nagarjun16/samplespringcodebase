package com.ngen.cosys.impbd.mail.transfermanifest.model;

import org.hibernate.validator.constraints.NotBlank;

import com.ngen.cosys.framework.model.BaseBO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class SearchTransferManifestDetails extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   @NotBlank(message="g.mandatory")
   private String incomingCarrier;
   @NotBlank(message="g.mandatory")
   private String transferCarrier;
   private String destination;
   private String dispatchNumber;

}
