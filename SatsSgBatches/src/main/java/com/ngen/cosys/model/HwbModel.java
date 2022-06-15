package com.ngen.cosys.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@XmlRootElement
@ApiModel
@NoArgsConstructor
@Validated
public class HwbModel extends BaseBO{
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private String hawbNumber;
   private BigInteger numberOfPackages;
   private BigDecimal  weight ;
   private String matchStatusOfHouseWayBill;
   private String exemptionCode;
   private String descriptionOfGoods;
   private String permitNumber;
}
