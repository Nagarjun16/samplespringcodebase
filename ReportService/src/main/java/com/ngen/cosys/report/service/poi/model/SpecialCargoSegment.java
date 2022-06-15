package com.ngen.cosys.report.service.poi.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class SpecialCargoSegment extends BaseBO {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String segment;
	private Integer shipmentCount;
	private BigInteger requestedPiecesCount;
	private BigInteger shipmentPiecesCount;
	private BigInteger handoverPiecesCount;
	private String manifestCompleteSearch;
	private String handoverCompleteSearch;

}
