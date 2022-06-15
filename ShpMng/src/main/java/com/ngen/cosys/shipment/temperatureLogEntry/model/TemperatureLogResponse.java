package com.ngen.cosys.shipment.temperatureLogEntry.model;

import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

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
@Setter
@Getter
@ToString
@NoArgsConstructor
public class TemperatureLogResponse extends BaseBO {
   private static final long serialVersionUID = 1L;
   private String shipmentNumber;
   private Boolean svc = Boolean.FALSE;
   private String shipmentId;
   private String temperatureRange;
   private String temperature;
   private String origin;
   private String destination;
   private int pieces;
   private double weight;
   private String chargeCode;
   private String weightCode;
   private List<TemperatureLogEntry> temperatureLogEntryData;

}