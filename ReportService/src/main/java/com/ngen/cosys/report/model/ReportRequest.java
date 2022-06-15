/**
 * Report Request
 * 
 * @copyright SATS Singapore 2018-19
 */
package com.ngen.cosys.report.model;

import java.util.Map;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.printer.enums.PrinterType;
import com.ngen.cosys.report.common.ReportFormat;
import com.ngen.cosys.report.common.ReportRequestType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Report Request
 */
@ApiModel("Report Request")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReportRequest extends BaseBO {
	private static final long serialVersionUID = -48553458854327341L;

	// Report Name
	private String reportName;

	// Download/Inline/Print
	private ReportRequestType requestType;

	// Format
	private ReportFormat format;

	// Printer Type
	private PrinterType printerType;

	// Report Parameters
	private Map<String, Object> parameters;
	
	// Printer queue
	private String queueName;
	
	// DataSource Property
	private String dataSource;
	
}
