package com.ngen.cosys.shipment.information.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.UserCarrierValidation;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@XmlRootElement
@Getter
@Setter
@NoArgsConstructor
@UserCarrierValidation(shipmentNumber = "shipmentNumber", flightKey = "", loggedInUser = "loggedInUser", type = "AWB")
public class ShipmentInfoSearchReq extends BaseBO {
   /**
   * 
   */
   private static final long serialVersionUID = 1L;

   @NotEmpty(message = "export.awb.number.required")
   private String shipmentNumber;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;
   
   private String shipmentType;
   private String printerName;
   private String userLoginCode;
   private String hwbNumber;

}