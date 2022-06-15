package com.ngen.cosys.report.service.poi.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Response model for SpecialCargoMonitering Function
 *
 */
@ApiModel
@XmlRootElement
@ToString
@Setter
@Getter
@NoArgsConstructor
@NgenCosysAppAnnotation
public class SpecialCargoMoniteringFlight extends BaseBO {


	private static final long serialVersionUID = 1L;
	private String flightKey;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate flightDate;
    
    private String atdetdstd; 
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime atdEtdStdMonitering;
    private String segment;
    //
    private String carrierCode;
    private boolean sqCarrier;
    private String flightType;
    private String flightBoardPoint;
    private String flightOffPoint;
    private int segmentId;
    private String shcGroup;
    private BigInteger flightId;
   // private List<SpecialCargoMoniteringShipment> specialCargoMoniteringShipmentList;
    private List<SpecialCargoShipment> shipmentList;
    private List<SpecialCargoShipment> specialCargoMoniteringShipmentList;

    private List<SpecialCargoShipment> readyShipment;
    private List<SpecialCargoShipment> notReadyShipment;
    private List<SpecialCargoMissingUldShc> missingDLSUldList;
}
