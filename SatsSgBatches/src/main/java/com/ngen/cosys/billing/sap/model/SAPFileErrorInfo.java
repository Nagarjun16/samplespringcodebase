package com.ngen.cosys.billing.sap.model;

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
public class SAPFileErrorInfo extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private long sapFileErrorId;

   private long sapFileInfoId;

   private int lineNo;

   private String referenceInfo;

   private String processingInfo;

}
