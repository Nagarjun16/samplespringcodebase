/**
 * Label Event Handler
 * 
 * @copyright SATS Singapore 2019-20
 */
package com.ngen.cosys.report.event;

import java.util.Objects;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.element.ILabel;
import org.eclipse.birt.report.engine.api.script.eventadapter.LabelEventAdapter;
import org.springframework.context.ApplicationContext;

import com.ngen.cosys.common.ConfigurationConstant;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.report.data.LabelCache;

/**
 * Label Event Handler
 */
public class LabelEventHandler extends LabelEventAdapter {

	/**
	 * @see org.eclipse.birt.report.engine.api.script.eventadapter.LabelEventAdapter#onPrepare(org.eclipse.birt.report.engine.api.script.element.ILabel,
	 *      org.eclipse.birt.report.engine.api.script.IReportContext)
	 */
	@Override
	public void onPrepare(ILabel labelHandle, IReportContext reportContext) throws ScriptException {
		try {
			@SuppressWarnings("unused")
			ApplicationContext applicationContext = (ApplicationContext) reportContext.getAppContext()
					.get(ConfigurationConstant.REPORT_SPRING_CONTEXT);
			//Locale locale = TenantContext.get().getLocale();
			String locale = TenantContext.get().getLocale();
			String i18nLabel = LabelCache.getInstance().getLabel(labelHandle.getText(), locale.toString());
			// Set Font
			ReportEventUtility.setLabelFontFamily(reportContext, labelHandle);
			//
			labelHandle.setText(Objects.isNull(i18nLabel) ? labelHandle.getText() : i18nLabel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
