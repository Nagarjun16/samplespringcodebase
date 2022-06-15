package com.ngen.cosys.impbd.flightdiscrepancylist.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;

import lombok.Getter;

import lombok.Setter;

@XmlRootElement
@ApiModel
@ToString
@Setter
@Getter

public class FlightDiscrepancySegmentList extends BaseBO {

   private static final long serialVersionUID = 1L;
   private String flightId;
   private String segmentId;
   private String boardingPoint;
   private List<FlightDiscrepancyList> flightDiscrepancyList;
}