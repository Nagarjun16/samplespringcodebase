/**
 * Report Service
 * 
 * @copyright SATS Singapore 2018-19
 */
package com.ngen.cosys.report.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.birt.report.engine.api.EngineException;
import org.json.JSONException;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.report.custom.model.NGCReportRequestCustom;
import com.ngen.cosys.report.model.ReportRequest;

/**
 * Report Service
 */
public interface ReportService {

	/**
	 * Get Reports
	 * 
	 * @return Reports
	 */
	default String[] getReports() {
		return null;
	}

	/**
	 * Create Report
	 * 
	 * @param reportRequest Report Request
	 * @param request       HTTP Request
	 * @param response      HTTP Response
	 * @return Report Output Stream
	 * @throws IOException, EngineException, SQLException
	 */
	OutputStream createReport(ReportRequest reportRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, EngineException, SQLException;

	OutputStream createReportCustom(ReportRequest reportRequest, HttpServletRequest request,
			HttpServletResponse response, NGCReportRequestCustom ngcReportCustom)
			throws IOException, EngineException, SQLException, JSONException, CustomException;

	/**
	 * Convert Output Stream Base64 format
	 * 
	 * @param outputStream Output Stream
	 * @return Base 64 Text
	 */
	default String convertOutputStreamToBase64Text(OutputStream outputStream) {
		//
		if (outputStream instanceof ByteArrayOutputStream) {
			return Base64.getEncoder().encodeToString(((ByteArrayOutputStream) outputStream).toByteArray());
		}
		return Base64.getEncoder().encodeToString(outputStream.toString().getBytes());
	}

}