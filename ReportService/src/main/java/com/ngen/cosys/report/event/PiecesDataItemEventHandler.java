package com.ngen.cosys.report.event;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.eventadapter.DataItemEventAdapter;
import org.eclipse.birt.report.engine.api.script.instance.IDataItemInstance;

import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.context.TenantRequest;
import com.ngen.cosys.report.common.ReportConstants;

public class PiecesDataItemEventHandler extends DataItemEventAdapter {
	
	private static Map<String, DecimalFormat> formatCache = new HashMap<>();

	@Override
	public void onRender(IDataItemInstance data, IReportContext reportContext) throws ScriptException {
		
		TenantRequest tenantRequest = TenantContext.get();
		String value = Objects.nonNull(data.getValue()) ? data.getValue().toString() : null;
		
		if (Objects.nonNull(value) && Objects.nonNull(tenantRequest)
				&& Objects.nonNull(tenantRequest.getTenantConfig()) && Objects.nonNull(tenantRequest.getTenantConfig().getFormat())) {
			
			String format = tenantRequest.getTenantConfig().getFormat().getPiecesFormat();
			
			DecimalFormat df = formatCache.get(Objects.isNull(format) ? ReportConstants.DEFAULT_PIECES_FORMAT : format);
			// If Non Cached
			if (Objects.isNull(df)) {
				df = tenantRequest.getTenantConfig().getFormat().getPiecesFormatter();
				formatCache.put(format, df);
			}
			
			// Format
			String formattedValue = df.format(Double.parseDouble(value.replaceAll(",", "")));
			// Set Display Value
			data.setDisplayValue(formattedValue);
		} else {
			data.setDisplayValue(new DecimalFormat(ReportConstants.DEFAULT_PIECES_FORMAT).format(0));
			//super.onRender(data, reportContext);
		}
	}
}
