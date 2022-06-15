/**
 * Text Data Item Event Handler
 * 
 * @copyright SATS Singapore 2019-20
 */
package com.ngen.cosys.report.event;

import java.util.Locale;
import java.util.Objects;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.eventadapter.DataItemEventAdapter;
import org.eclipse.birt.report.engine.api.script.instance.IDataItemInstance;
import org.springframework.context.ApplicationContext;

import com.ngen.cosys.common.ConfigurationConstant;
import com.ngen.cosys.report.data.LabelCache;

/**
 * Text Data Item Event Handler
 */
public class TextDataItemEventHandler extends DataItemEventAdapter {

	/**
	 * @see org.eclipse.birt.report.engine.api.script.eventadapter.DataItemEventAdapter#onRender(org.eclipse.birt.report.engine.api.script.instance.IDataItemInstance,
	 *      org.eclipse.birt.report.engine.api.script.IReportContext)
	 */
	@Override
	public void onRender(IDataItemInstance data, IReportContext reportContext) throws ScriptException {
		try {
			@SuppressWarnings("unused")
			ApplicationContext applicationContext = (ApplicationContext) reportContext.getAppContext()
					.get(ConfigurationConstant.REPORT_SPRING_CONTEXT);
			Locale locale = reportContext.getLocale();
			String value = Objects.nonNull(data.getValue()) ? data.getValue().toString() : "";
			String i18nLabel = LabelCache.getInstance().getLabel(value, locale.toString());
			// Set Font
			ReportEventUtility.setDataItemFontFamily(reportContext, data);
			//
			data.setDisplayValue(i18nLabel == null ? value : i18nLabel);
		} catch (Exception e) {
		}
	}

}
