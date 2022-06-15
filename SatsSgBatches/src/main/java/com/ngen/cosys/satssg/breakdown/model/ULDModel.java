package com.ngen.cosys.satssg.breakdown.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.annotations.CheckRemarksConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
public class ULDModel extends BaseBO {

   private static final long serialVersionUID = 1L;

   private String uldType;
   private String uldSerialNumber;
   private String uldOwnerCode;

  
   private String uldNumber;
   private String uldLoadingIndicator;

   
   private String uldRemarks;

   private int volumeAvailableCode;

}