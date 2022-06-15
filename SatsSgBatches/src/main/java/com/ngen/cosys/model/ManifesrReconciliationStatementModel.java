package com.ngen.cosys.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
@ApiModel
@NoArgsConstructor
@Validated
public class ManifesrReconciliationStatementModel extends BaseBO {
   /**
    * System generated default serial version id
    */
   private static final long serialVersionUID = 1L;
   private String carriersAgent;
   private String typeofShipment;
   private String cargoStatus;
   private String flightNumber;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate scheduledFlightDate;

   private String pointOfLading;
   private String pointOfUnlading;
   private String matchStatus;
   private BigInteger customsFlightId;
   private BigInteger customShipmentInfoId;
   private BigInteger mrsSequenceNo;
   private String flightCancelFlag;
   List<MasterAwbDetailModel> mAwbDetails;
   List<HwbModel> hwbModel;
   List<CustomerModel> shipper;
   List<CustomerModel>consignee;
}
