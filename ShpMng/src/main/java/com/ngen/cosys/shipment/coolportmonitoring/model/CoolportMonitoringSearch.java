package com.ngen.cosys.shipment.coolportmonitoring.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nikhil.5.Gupta
 *
 */
@ApiModel
@Setter
@Getter
@NoArgsConstructor
public class CoolportMonitoringSearch extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   private String by;
   private String carrierGroup;
   private String carrierCode;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime dateTimeFrom;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime dateTimeTo;
   private String temparature;
   private String awbNumber;
}