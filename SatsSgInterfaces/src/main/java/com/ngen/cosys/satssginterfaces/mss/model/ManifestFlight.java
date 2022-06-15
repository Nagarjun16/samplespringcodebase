package com.ngen.cosys.satssginterfaces.mss.model;

import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@ToString
@Getter
@Setter
@Component
@Validated
@NoArgsConstructor
public class ManifestFlight extends BaseBO{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	@Valid
	private Flight flight;
	@Valid
	private List<ManifestSegment> segment;
	
	/**
	 * 
	 */
	//private RuleShipmentExecutionDetails ruleShipmentExecutionDetails;
	
	
}
