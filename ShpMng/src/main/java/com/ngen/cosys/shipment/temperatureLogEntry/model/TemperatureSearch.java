package com.ngen.cosys.shipment.temperatureLogEntry.model;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Qualifier;

import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This Model Class takes care Stock.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@Setter
@Getter
@ToString
@NoArgsConstructor
@Qualifier("temperatureLogSearch")
public class TemperatureSearch extends BaseBO {
   private static final long serialVersionUID = 1L;

   private String shipmentNumber;

   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   private LocalDate shipmentDate;

}