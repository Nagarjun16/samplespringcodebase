package com.ngen.cosys.shipment.model;

import java.time.LocalDate;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.validators.SearchFWBGroup;
import com.ngen.cosys.shipment.validators.SearchNonIataFWBGroup;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@ToString
@Getter
@Setter
@NoArgsConstructor
@Validated
public class FetchFWBRequest extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   @CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = { SearchFWBGroup.class })
   @NotBlank(message = "export.awb.number.required", groups = { SearchFWBGroup.class, SearchNonIataFWBGroup.class })
   @Size(min = 11, message = "export.awb.number.length.validation", groups = { SearchFWBGroup.class,
         SearchNonIataFWBGroup.class })
   private String awbNumber;
   @JsonSerialize(using = LocalDateSerializer.class)
   @InjectShipmentDate(shipmentNumberField = "awbNumber")
   private LocalDate awbDate;

   private boolean nonIATA;
}
