package com.ngen.cosys.impbd.shipment.verification.model;

import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
public class EliElmDGDModel extends BaseBO {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private String shipmentNumber;

   @JsonSerialize(using = LocalDateSerializer.class)
   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   private LocalDate shipmentdate;
   
   private String boardPoint;
   private String offPoint;

   @NgenAuditField(fieldName = "ElieElmInfo")
   private List<EliElmDGDModelList> eliElmFormDetails;

}
