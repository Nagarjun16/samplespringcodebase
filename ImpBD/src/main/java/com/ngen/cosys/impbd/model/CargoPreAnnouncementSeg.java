package com.ngen.cosys.impbd.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
@Validated
public class CargoPreAnnouncementSeg {
   
   private String flightId;
   private String flightBoardPoint;
   private String flightOffPoint;
   private String flightSegmentOrder;

}
