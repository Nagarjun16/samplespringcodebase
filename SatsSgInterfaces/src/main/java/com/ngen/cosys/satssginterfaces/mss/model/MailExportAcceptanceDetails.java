package com.ngen.cosys.satssginterfaces.mss.model;

import org.hibernate.validator.constraints.NotBlank;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MailExportAcceptanceDetails extends BaseBO {
   /**
    * Default serialVersionUID
    */
   private static final long serialVersionUID = 1L;

   /**
    * This field contains agent name
    */
   private String agentCode;

   /**
    * This field contains carrier code
    */
   @NotBlank(message = "g.mandatory")
   private String carrierCode;

   /**
    * This field contains ULD number
    */
//   @CheckContainerNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
  // @CheckShipmentLocationConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
   private String uldNumber;

   /**
    * This field contains ULD type
    */
   private String uldType;
   /**
    * This field contains ULD Key
    */
   private String uldKey;

   /**
    * This field contains ULD 5 digit number
    */
   private String uldNum;

   /**
    * This field contains ULD carrier code
    */
   private String uldCarrier;

   /**
    * This field contains nested id
    */
   private String nestedId;

   /**
    * This field contains warehouseLocation
    */
   private String warehouseLocation;
   
   /**
    * This field contains searchMode
    */
   private String searchMode;
   
   /**
    * This field contains transferredFromCarrierCode
    */
   private String transferredFromCarrierCode;
}