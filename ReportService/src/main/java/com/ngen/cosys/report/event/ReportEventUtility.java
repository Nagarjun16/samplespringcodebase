/**
 * Report Event Utility
 */
package com.ngen.cosys.report.event;

import java.util.Locale;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.element.ILabel;
import org.eclipse.birt.report.engine.api.script.instance.IDataItemInstance;

/**
 * Report Event Utility
 */
public class ReportEventUtility {
	//
	private static final String ZH_HK = "zh_HK";
	private static final String ZH_TH = "zh_TH";
	private static final String ZH_CN = "zh_CN";
	private static final String EN_US = "en_US";
	//
	private static final String NGC_ZH_TC_FONT = "ngc-zh-tc";
	private static final String NGC_ZH_SC_FONT = "ngc-zh-sc";
	private static final String NGC_EN_FONT = "ngc-en";

	/**
	 * Sets Font Family
	 * 
	 * @param reportContext
	 *            Report Context
	 * @param data
	 *            Data
	 */
	public static void setDataItemFontFamily(IReportContext reportContext, IDataItemInstance data) {
		Locale locale = reportContext.getLocale();
		// Set Font
		switch (locale.toString()) {
		case EN_US:
			data.getStyle().setFontFamily(NGC_EN_FONT);
			break;
		case ZH_CN:
			data.getStyle().setFontFamily(NGC_ZH_SC_FONT);
			break;
		case ZH_TH:
			data.getStyle().setFontFamily(NGC_ZH_TC_FONT);
			break;
		case ZH_HK:
			data.getStyle().setFontFamily(NGC_ZH_SC_FONT);
			break;
		}
	}

	/**
	 * Sets Font Family
	 * 
	 * @param reportContext
	 *            Report Context
	 * @param data
	 *            Data
	 */
	public static void setDataItemFontFamily(Locale locale, IReportContext reportContext, IDataItemInstance data) {
		// Set Font
		switch (locale.toString()) {
		case EN_US:
			data.getStyle().setFontFamily(NGC_EN_FONT);
			break;
		case ZH_CN:
			data.getStyle().setFontFamily(NGC_ZH_SC_FONT);
			break;
		case ZH_TH:
			data.getStyle().setFontFamily(NGC_ZH_TC_FONT);
			break;
		case ZH_HK:
			data.getStyle().setFontFamily(NGC_ZH_SC_FONT);
			break;
		}
	}

	/**
	 * Sets Font Family
	 * 
	 * @param reportContext
	 *            Report Context
	 * @param label
	 *            Label
	 * @throws ScriptException
	 */
	public static void setLabelFontFamily(IReportContext reportContext, ILabel label) throws ScriptException {
		Locale locale = reportContext.getLocale();
		// Set Font
		switch (locale.toString()) {
		case EN_US:
			label.getStyle().setFontFamily(NGC_EN_FONT);
			break;
		case ZH_CN:
			label.getStyle().setFontFamily(NGC_ZH_SC_FONT);
			break;
		case ZH_TH:
			label.getStyle().setFontFamily(NGC_ZH_TC_FONT);
			break;
		case ZH_HK:
			label.getStyle().setFontFamily(NGC_ZH_SC_FONT);
			break;
		}
	}

	/**
	 * Sets Font Family
	 * 
	 * @param reportContext
	 *            Report Context
	 * @param label
	 *            Label
	 * @throws ScriptException
	 */
	public static void setLabelFontFamily(Locale locale, IReportContext reportContext, ILabel label)
			throws ScriptException {
		// Set Font
		switch (locale.toString()) {
		case EN_US:
			label.getStyle().setFontFamily(NGC_EN_FONT);
			break;
		case ZH_CN:
			label.getStyle().setFontFamily(NGC_ZH_SC_FONT);
			break;
		case ZH_TH:
			label.getStyle().setFontFamily(NGC_ZH_TC_FONT);
			break;
		case ZH_HK:
			label.getStyle().setFontFamily(NGC_ZH_SC_FONT);
			break;
		}
	}
}
