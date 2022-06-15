/**
 * Report Label
 * 
 * @copyright SATS Singapore 2019-20
 */
package com.ngen.cosys.report.model;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Report Label
 */
@ApiModel("Report Label")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReportLabel extends BaseBO {
	private static final long serialVersionUID = 6336935087284765781L;

	// Label Code
	String code;

	// Label Description
	String label;
}
