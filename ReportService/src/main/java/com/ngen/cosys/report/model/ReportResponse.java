/**
 * Report Response
 * 
 * @copyright SATS Singapore 2018-19
 */
package com.ngen.cosys.report.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Report Response
 */
@ApiModel("Report Response")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReportResponse implements Serializable {
	private static final long serialVersionUID = 6149056670351094061L;
	// Report Name
	private String reportName;
	// Report Data
	private String reportData;
}
