package com.ngen.cosys.uncollectedfreightout.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@XmlRootElement
@ApiModel
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
public class IRPNotificationDetails extends BaseBO{
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private BigInteger shipmentId;
   private BigInteger flightId;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime emailNotification1;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime emailNotification2;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime emailNotification3;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime emailNotification4;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime emailNotificationAbondon;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime reportGenetation1;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime reportGenetation2;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime reportGenetation3;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime reportGenetation4;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime ReportGenetationAbondon;
   private String referenceNumber;
   private String shcHandlingGroupCode;

}
