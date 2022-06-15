package com.ngen.cosys.TonnageReport.model;

	
import java.time.Month;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class TonnageReportRequest extends BaseBO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Month month;
	private int year;
	private String carrier;

}
