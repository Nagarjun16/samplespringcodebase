package com.ngen.cosys.impbd.shipment.verification.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
public class DgRegulationDetails extends BaseBO {

   /**
	 * 
	 */
   private static final long serialVersionUID = 1L;

   private int regId; 
   
   private String psn;
   
   @NotBlank(message = "g.mandatory")
   private String pg;
   
   @NotBlank(message = "g.mandatory")
   private String fbd;
   
   @NotBlank(message = "g.mandatory")
   private String mlqPInfo;
   
   @NotBlank(message = "g.mandatory")
   private String mlqQuantity;
   
   @NotBlank(message = "g.mandatory")
   private String mlqUnit;
   
   @NotBlank(message = "g.mandatory")
   private String mpcPInfo;
   
   @NotBlank(message = "g.mandatory")
   private String mpcQuantity;
   
   @NotBlank(message = "g.mandatory")
   private String mpcUnit;
   
   @NotBlank(message = "mandatory")
   private String mcoPInfo;
   
   @NotBlank(message = "g.mandatory")
   private String mcoQuantity;
   
   @NotBlank(message = "g.mandatory")
   private String mcoUnit;
   
   private String remarks;
}
