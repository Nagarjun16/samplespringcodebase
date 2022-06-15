package com.ngen.cosys.shipment.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.annotations.UserCarrierValidation;
import com.ngen.cosys.validator.enums.MandatoryType;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This model class SearchShipmentLocation entity to show user shipment location
 * details
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@XmlRootElement
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@UserCarrierValidation(shipmentNumber = "shipmentNumber", flightKey = "", loggedInUser = "loggedInUser", type = "AWB")
public class SearchShipmentLocation extends BaseBO {

   /**
    * Default serial version id
    */
   private static final long serialVersionUID = 1L;

   private int shipmentId;

   private String shipmentType;

   @NotEmpty(message = "awb.no.of.awb.blank")
   @CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED)
   private String shipmentNumber;

   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;

   private int shipmentIdfreight;

   private boolean abandoned;
   

	@NgenAuditField(fieldName = "hwbNumber")
	   private String hwbNumber;

}