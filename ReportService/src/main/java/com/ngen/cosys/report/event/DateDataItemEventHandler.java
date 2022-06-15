/**
 * Date Data Item Event Handler
 * 
 * @copyright SATS Singapore 2019-20
 */
package com.ngen.cosys.report.event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.eventadapter.DataItemEventAdapter;
import org.eclipse.birt.report.engine.api.script.instance.IDataItemInstance;

import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.context.TenantRequest;
import com.ngen.cosys.multitenancy.timezone.support.TenantTimeZoneUtility;
import com.ngen.cosys.report.common.ReportConstants;

/**
 * Date Data Item Event Handler
 */
public class DateDataItemEventHandler extends DataItemEventAdapter {
	//
	private static Map<String, DateTimeFormatter> formatCache = new HashMap<>();

	/**
	 * @see org.eclipse.birt.report.engine.api.script.eventadapter.DataItemEventAdapter#onRender(org.eclipse.birt.report.engine.api.script.instance.IDataItemInstance,
	 *      org.eclipse.birt.report.engine.api.script.IReportContext)
	 */
	@Override
	public void onRender(IDataItemInstance data, IReportContext reportContext) throws ScriptException {
		TenantRequest tenantRequest = TenantContext.get();
		Object value = data.getValue();
		// Set Font
		ReportEventUtility.setDataItemFontFamily(reportContext, data);
		//
		if (Objects.nonNull(value) && Objects.nonNull(tenantRequest)
				&& Objects.nonNull(tenantRequest.getTenantConfig())) {
			String format = tenantRequest.getTenantConfig().getFormat().getDateFormat();
			DateTimeFormatter df = formatCache
					.get(Objects.isNull(format) ? ReportConstants.DEFAULT_DATE_FORMAT : format);
			LocalDateTime dateTime = null;
			// If Non Cached
			if (Objects.isNull(df)) {
				df = tenantRequest.getTenantConfig().getFormat().getDateFormatter();
				formatCache.put(format, df);
			}
			// Format
			if (value instanceof LocalDateTime) {
				dateTime = ((LocalDateTime) value);
			} else if (value instanceof java.sql.Timestamp) {
				dateTime = ((java.sql.Timestamp) value).toLocalDateTime();
			} else if (value instanceof LocalDate) {
				dateTime = ((LocalDate) value).atTime(LocalTime.MIDNIGHT);
			} else if (value instanceof java.util.Date) {
				//dateTime = TenantTimeZoneUtility.toDbLocalDateTime((Date) value);
				dateTime = new java.sql.Timestamp(((java.util.Date) value).getTime()).toLocalDateTime();
			} else if (value instanceof java.sql.Date) {
				dateTime = new java.sql.Timestamp(((java.sql.Date) value).getTime()).toLocalDateTime();
			}
			// Convert to Time Zone
			if (Objects.nonNull(dateTime)) {
				Boolean timeZoneConversion = data.getUserPropertyValue("timeZoneConversion") != null ? 
						new Boolean(data.getUserPropertyValue("timeZoneConversion").toString()) 
						: Boolean.TRUE;
				if(!timeZoneConversion) {
					data.setDisplayValue(dateTime.format(df).toUpperCase());
				} else {
				LocalDate date = TenantTimeZoneUtility.fromDatabaseToTenantDateTime(dateTime).toLocalDate();
				// Set Display Value
				data.setDisplayValue(date.format(df).toUpperCase());
				}
			}
		} else {
			data.setDisplayValue("");
		}
	}

}
