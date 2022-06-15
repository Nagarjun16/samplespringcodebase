/**
 * Global Currency Data Item Event Handler
 * 
 * @copyright SATS Singapore 2019-20
 */
package com.ngen.cosys.report.event;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.element.IDataItem;
import org.eclipse.birt.report.engine.api.script.eventadapter.DataItemEventAdapter;
import org.eclipse.birt.report.engine.api.script.instance.IDataItemInstance;

import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.context.TenantRequest;
import com.ngen.cosys.report.common.ReportConstants;

/**
 * Global Currency Data Item Event Handler
 */
public class GlobalCurrencyDataItemEventHandler extends DataItemEventAdapter {
	
	private static Map<String, DecimalFormat> formatCache = new HashMap<>();

	/**
	 * @see org.eclipse.birt.report.engine.api.script.eventadapter.DataItemEventAdapter#onPrepare(org.eclipse.birt.report.engine.api.script.element.IDataItem,
	 *      org.eclipse.birt.report.engine.api.script.IReportContext)
	 */
	@Override
	public void onPrepare(IDataItem dataItemHandle, IReportContext reportContext) throws ScriptException {
		super.onPrepare(dataItemHandle, reportContext);
	}

	/**
	 * @see org.eclipse.birt.report.engine.api.script.eventadapter.DataItemEventAdapter#onRender(org.eclipse.birt.report.engine.api.script.instance.IDataItemInstance,
	 *      org.eclipse.birt.report.engine.api.script.IReportContext)
	 */
	@Override
	public void onRender(IDataItemInstance data, IReportContext reportContext) throws ScriptException {
		TenantRequest tenantRequest = TenantContext.get();
		String value = Objects.nonNull(data.getValue()) ? data.getValue().toString() : null;
		//
		if (Objects.nonNull(value) && Objects.nonNull(tenantRequest)
				&& Objects.nonNull(tenantRequest.getTenantConfig()) && Objects.nonNull(tenantRequest.getTenantConfig().getFormat())) {
			
			String format = tenantRequest.getTenantConfig().getFormat().getInternationalCurrencyFormat();
			
			DecimalFormat df = formatCache.get(Objects.isNull(format) ? ReportConstants.DEFAULT_NUMBER_FORMAT : format);
			// If Non Cached
			if (Objects.isNull(df)) {
				df = tenantRequest.getTenantConfig().getFormat().getInternationalCurrencyFormatter();
				formatCache.put(format, df);
			}
			
			// Format
			String formattedValue = df.format(Double.parseDouble(value.replaceAll(",", "")));
			// Set Display Value
			String currencySymbol = tenantRequest.getTenantConfig().getFormat().getInternationalCurrencySymbol() != null ? tenantRequest.getTenantConfig().getFormat().getInternationalCurrencySymbol() : "SGD";
			data.setDisplayValue(currencySymbol + formattedValue);
		} else {
			super.onRender(data, reportContext);
		}
	}
}
