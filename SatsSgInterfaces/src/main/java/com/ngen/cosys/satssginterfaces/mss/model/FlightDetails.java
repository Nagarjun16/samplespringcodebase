package com.ngen.cosys.satssginterfaces.mss.model;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
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
@NgenCosysAppAnnotation
@Validated
public class FlightDetails extends BaseBO{
	
	/**
    * 
    */
   private static final long serialVersionUID = 1L;
   @Valid
	Flight flight;
	@NgenCosysAppAnnotation
	Shipment shipment;
	Segment segment;
	private boolean loadedOrNot;
	UldShipment uld;
    private  String reason;
	
	

}
