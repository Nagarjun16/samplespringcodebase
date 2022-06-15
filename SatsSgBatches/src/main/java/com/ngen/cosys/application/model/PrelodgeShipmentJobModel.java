package com.ngen.cosys.application.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@Component
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated

@NgenCosysAppAnnotation
public class PrelodgeShipmentJobModel extends BaseBO {
	
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String shipmentNumber;
	   @InjectShipmentDate(shipmentNumberField="shipmentNumber")
	   @JsonSerialize(using = LocalDateSerializer.class)
	   private LocalDate shipmentDate;
	   private String prelodgeDocumentId;
	   private String prelodgeServiceNo;
	   private String prelodgeServiceId;

}
