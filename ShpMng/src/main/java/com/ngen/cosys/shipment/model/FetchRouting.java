package com.ngen.cosys.shipment.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Scope(scopeName = "prototype")
@ApiModel
@XmlRootElement
@ToString
@Getter
@Setter
@NoArgsConstructor
public class FetchRouting extends BaseBO {

   private String carrierCode;
   private String airportCode;
   private String flightNumber;
   private String origin;
   private String fromOrigin;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightDate;
   private boolean errorFalg;
   private String toDest;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate toDate;
   private String flightkey;
   private String svc;

}
