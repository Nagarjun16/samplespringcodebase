package com.ngen.cosys.shipment.model;

import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Damage extends BaseBO{
   
   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   private String content;
   private String finalRemarks; 
   
   private List<DamageDetails> damageDetails;
}
