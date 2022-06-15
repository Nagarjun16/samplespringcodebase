/**
 * Report Format Enums
 * 
 * @copyright SATS Singapore 2018-19
 */
package com.ngen.cosys.report.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Report Format Enums
 */
public enum ReportFormat {
	PDF("pdf", "pdf"), //
	HTML("html", "html"), //
	XLS("xls", "xls"), //
	XLSX("xlsx", "xlsx"), //
	CSV("csv", "csv"), //
	TXT("txt", "txt");

	private String format;
	private String fileExtension;

	/**
	 * Initialize
	 * 
	 * @param format
	 *            Format
	 * @param fileExtension
	 *            File Extension
	 */
	ReportFormat(String format, String fileExtension) {
		this.format = format;
		this.fileExtension = fileExtension;
	}

	/**
	 * Format
	 * 
	 * @return Format
	 */
	@JsonValue
	public String format() {
		return this.format;
	}

	/**
	 * File Extension
	 * 
	 * @return File Extension
	 */
	public String fileExtension() {
		return this.fileExtension;
	}

	/**
	 * Gets Enum Of Value
	 * 
	 * @param value
	 *            Value
	 * @return Enum
	 */
	public static ReportFormat enumOf(String value) {
		ReportFormat[] reportFormats = values();
		//
		for (ReportFormat reportFormat : reportFormats) {
			if (reportFormat.format().equalsIgnoreCase(value)) {
				return reportFormat;
			}
		}
		//
		return null;
	}
}
