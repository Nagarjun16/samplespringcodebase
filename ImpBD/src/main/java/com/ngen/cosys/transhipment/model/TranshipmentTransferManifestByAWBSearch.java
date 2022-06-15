package com.ngen.cosys.transhipment.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@Setter
@Getter
@NoArgsConstructor
public class TranshipmentTransferManifestByAWBSearch extends BaseBO {
   /**
   * 
   */
   private static final long serialVersionUID = 1L;

   private String trmNumber;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime issueDateFrom;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime issueDateTo;

   @Size(min = 2, max = 3, message = "ERROR_MINIMUM_2_MAXIMUM_3")
   private String carrierCodeFrom;

   @Size(min = 2, max = 3, message = "ERROR_MINIMUM_2_MAXIMUM_3")
   private String carrierCodeTo;

   private String airlineNumber;

   private String printerName;
   
   private String shipmentNumber;
   
   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate; 

   @Valid
   private List<TranshipmentTransferManifestByAWB> awbList;

}
