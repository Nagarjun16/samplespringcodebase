/**
 * Chinese Text Data Item Event Handler
 * 
 * @copyright SATS Singapore 2019-20
 */
package com.ngen.cosys.report.event;

import java.util.Objects;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.eventadapter.DataItemEventAdapter;
import org.eclipse.birt.report.engine.api.script.instance.IDataItemInstance;

/**
 * Chinese Text Data Item Event Handler
 */
public class BarCodeTextDataItemEventHandler extends DataItemEventAdapter {

	/**
	 * @see org.eclipse.birt.report.engine.api.script.eventadapter.DataItemEventAdapter#onRender(org.eclipse.birt.report.engine.api.script.instance.IDataItemInstance,
	 *      org.eclipse.birt.report.engine.api.script.IReportContext)
	 */
	@Override
	public void onRender(IDataItemInstance data, IReportContext reportContext) throws ScriptException {
		try {
			String value = Objects.nonNull(data.getValue()) ? data.getValue().toString() : "";
			// Set Font
			data.getStyle().setFontFamily("ngc-bar-code");
			//
			data.setDisplayValue(value);
		} catch (Exception e) {
		}
	}

}
