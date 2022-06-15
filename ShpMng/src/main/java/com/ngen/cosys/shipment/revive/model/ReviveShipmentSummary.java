package com.ngen.cosys.shipment.revive.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@NgenCosysAppAnnotation
public class ReviveShipmentSummary extends BaseBO {

   private static final long serialVersionUID = 1L;

   private String shipmentNumber;

   @JsonSerialize(using = LocalDateSerializer.class)
   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   private LocalDate shipmentDate;

   private List<ReviveShipmentModel> reviveShipmentList;

}