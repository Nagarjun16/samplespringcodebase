package com.ngen.cosys.transhipment.model;

import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
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
public class TransferByCarrierSearch extends BaseBO {
   /**
    * default serial number
    */
   private static final long serialVersionUID = 1L;
   @NotEmpty(message = "ERROR_TRANSFERRING-CARRIER_REQUIRED")
   private String transferringCarrier;
   private String onwardCarrier;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate fromDate;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate toDate;

   private List<TransferByCarrier> transferByCarrierList;
}
