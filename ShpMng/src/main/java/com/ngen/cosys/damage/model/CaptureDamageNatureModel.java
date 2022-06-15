package com.ngen.cosys.damage.model;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
@Component
public class CaptureDamageNatureModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   private BigInteger damageLineItemsId;
   private String natureOfDamage;
   private BigInteger damageLineItemsConditionsId;

}