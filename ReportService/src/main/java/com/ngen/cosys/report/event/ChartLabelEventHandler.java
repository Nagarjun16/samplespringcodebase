/**
 * Chart Label Event Handler
 * 
 * @copyright SATS Singapore 2019-20
 */
package com.ngen.cosys.report.event;

import org.eclipse.birt.chart.computation.LegendEntryRenderingHints;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.component.Label;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.script.ChartEventHandlerAdapter;
import org.eclipse.birt.chart.script.IChartScriptContext;

/**
 * Chart Label Event Handler
 */
public class ChartLabelEventHandler extends ChartEventHandlerAdapter {

	/**
	 * @see org.eclipse.birt.chart.script.ChartEventHandlerAdapter#beforeDrawLegendItem(org.eclipse.birt.chart.computation.LegendEntryRenderingHints,
	 *      org.eclipse.birt.chart.model.attribute.Bounds,
	 *      org.eclipse.birt.chart.script.IChartScriptContext)
	 */
	@Override
	public void beforeDrawLegendItem(LegendEntryRenderingHints lerh, Bounds bo, IChartScriptContext icsc) {
		super.beforeDrawLegendItem(lerh, bo, icsc);
	}

	/**
	 * @see org.eclipse.birt.chart.script.ChartEventHandlerAdapter#beforeDrawAxisLabel(org.eclipse.birt.chart.model.component.Axis,
	 *      org.eclipse.birt.chart.model.component.Label,
	 *      org.eclipse.birt.chart.script.IChartScriptContext)
	 */
	@Override
	public void beforeDrawAxisLabel(Axis axis, Label label, IChartScriptContext icsc) {
		super.beforeDrawAxisLabel(axis, label, icsc);
	}

	/**
	 * @see org.eclipse.birt.chart.script.ChartEventHandlerAdapter#beforeDrawAxisTitle(org.eclipse.birt.chart.model.component.Axis,
	 *      org.eclipse.birt.chart.model.component.Label,
	 *      org.eclipse.birt.chart.script.IChartScriptContext)
	 */
	@Override
	public void beforeDrawAxisTitle(Axis axis, Label label, IChartScriptContext icsc) {
		super.beforeDrawAxisTitle(axis, label, icsc);
	}

	/**
	 * @see org.eclipse.birt.chart.script.ChartEventHandlerAdapter#beforeDrawSeriesTitle(org.eclipse.birt.chart.model.component.Series,
	 *      org.eclipse.birt.chart.model.component.Label,
	 *      org.eclipse.birt.chart.script.IChartScriptContext)
	 */
	@Override
	public void beforeDrawSeriesTitle(Series series, Label label, IChartScriptContext icsc) {
		super.beforeDrawSeriesTitle(series, label, icsc);
	}

}
