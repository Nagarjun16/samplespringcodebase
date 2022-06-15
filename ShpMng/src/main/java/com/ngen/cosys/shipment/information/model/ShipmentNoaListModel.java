package com.ngen.cosys.shipment.information.model;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@XmlRootElement
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
public class ShipmentNoaListModel  extends BaseBO {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private String noaemail;
   
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime noadate;

}
