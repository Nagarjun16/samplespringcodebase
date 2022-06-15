package com.ngen.cosys.satssg.interfaces.psn.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@ApiModel
@ToString
@Getter
@Setter
@NoArgsConstructor
public class AirwayBillIdentification extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private String awbPrefix;
   private String awbNo;
   private String shipmentNumber;
   private LocalDate shipmentDate;
   private String hwbNo;
   private String remark;
   private String ackCode;
   private LocalDateTime transactionDateTime;

}
