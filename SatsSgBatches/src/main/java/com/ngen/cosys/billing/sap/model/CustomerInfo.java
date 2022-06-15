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
public class CustomerInfo extends SAPFileRecord {

   private static final long serialVersionUID = 1L;

   @NotEmpty(message = "g.customer.code.required")
   private String cutomerCode;

   @NotEmpty(message = "g.customer.name.required")
   private String customerName;

   @NotEmpty(message = "g.indicator.required")
   private String indicator;

   private String blockIndicator;

}
