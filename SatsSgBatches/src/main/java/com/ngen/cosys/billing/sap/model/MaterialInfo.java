package com.ngen.cosys.billing.sap.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Setter
@Getter
@NoArgsConstructor
@Component
@ToString
public class MaterialInfo extends SAPFileRecord {

   private static final long serialVersionUID = 1L;

   @NotEmpty(message = "g.material.number.required")
   private String materialNumber;

   @NotEmpty(message = "g.material.desc.required")
   private String materialDescription;

   @NotEmpty(message = "g.measurment.required")
   private String unitOfMeasurement;

   @NotEmpty(message = "g.price.type.required")
   private String pricingType;

   @NotEmpty(message = "g.indicator.required")
   private String indicator;

}
