package com.ngen.cosys.impbd.displayffm.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.impbd.model.FFMCountDetails;
import com.ngen.cosys.model.FlightModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
public class DisplayffmByFlightModel extends FlightModel {

   private static final long serialVersionUID = 1L;
   
   private BigInteger impFreightFlightManifestByFlightId;

   private BigInteger uldCount;

   private BigInteger looseCargo;

   private BigInteger cargoInULD;

   private BigInteger pieceCount;

   private BigDecimal weight;

   private String flightStatus;

   private BigInteger shipmentCount;
   
   private Boolean isFFMProcessed;
   
   private BigInteger segmentId;
   
   private BigInteger segmentCopy;
   
   private String flightType;
   
   private String typeOfFFM;
   
   private String flightDigits;

   private String segment;
   
   private List<FFMCountDetails> ffmReceivedDetails;
   
   private List<FFMCountDetails> ffmRejectedDetails;
   
   private List<FFMCountDetails> ffmUnProcessedDtails;

   private List<FreightFlightManifestBySegmentListModel> segmentsList;

}