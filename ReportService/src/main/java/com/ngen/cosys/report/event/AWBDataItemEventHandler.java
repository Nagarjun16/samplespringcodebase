/**
 * AWB Data Item Event Handler
 * 
 * @copyright SATS Singapore 2019-20
 */
package com.ngen.cosys.report.event;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.element.IDataItem;
import org.eclipse.birt.report.engine.api.script.eventadapter.DataItemEventAdapter;
import org.eclipse.birt.report.engine.api.script.instance.IDataItemInstance;

/**
 * AWB Data Item Event Handler
 */
public class AWBDataItemEventHandler extends DataItemEventAdapter {

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
		super.onRender(data, reportContext);
	}
}
