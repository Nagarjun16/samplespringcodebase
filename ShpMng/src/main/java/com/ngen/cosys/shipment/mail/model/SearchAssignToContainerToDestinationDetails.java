package com.ngen.cosys.shipment.mail.model;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Validated
public class SearchAssignToContainerToDestinationDetails extends BaseBO{
   
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   @CheckContainerNumberConstraint(mandatory = MandatoryType.Type.REQUIRED) 
   private String storeLocation;
   
   private String destination;
   

}
