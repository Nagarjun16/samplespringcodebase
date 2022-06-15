package com.ngen.cosys.report.event;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.eventadapter.DataItemEventAdapter;
import org.eclipse.birt.report.engine.api.script.instance.IDataItemInstance;

public class FeatureDataItemEventHandler extends DataItemEventAdapter {

	
	@Override
	public void onRender(IDataItemInstance data, IReportContext reportContext) throws ScriptException {
		try {
			
			System.out.println(data.getUserPropertyValue("feature"));
			String feature = data.getUserPropertyValue("feature").toString();
			if("HOUSEWAYBILLHANDLING".equalsIgnoreCase(feature)) {
				reportContext.setParameterValue("feature", true);	
			} else {
				reportContext.setParameterValue("feature", false);	
			}
				
			
		} catch (Exception e) {
		}
	}
	
}
