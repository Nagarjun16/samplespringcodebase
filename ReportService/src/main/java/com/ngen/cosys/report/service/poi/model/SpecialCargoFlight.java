package com.ngen.cosys.report.service.poi.model;

import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@ToString
@Setter
@Getter
@NoArgsConstructor
@NgenCosysAppAnnotation
public class SpecialCargoFlight extends BaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String flightKey;
	private LocalDate flightDate;
	private String atdetdstd;
	private List<SpecialCargoSegment> segmentList;
}
