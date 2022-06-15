package com.ngen.cosys.billing.sap.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

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
public class SAPFileRecord extends BaseBO {

   private static final long serialVersionUID = 1L;

   private String recordType;

   private String lineType;

   private int lineSequenceNo;

   @NotEmpty(message = "g.company.code.required")
   private String companyCode;

   @NotEmpty(message = "g.sales.organization.required")
   private String salesOrganization;

   @NotEmpty(message = "g.distibution.channel.required")
   private String distributionChannel;

   private String division;

   private int lineCount;

}
