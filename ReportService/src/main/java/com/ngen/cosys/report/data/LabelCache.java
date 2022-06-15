/**
 * Label Cache
 */
package com.ngen.cosys.report.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.report.model.ReportLabel;

/**
 * Label Cache
 */
public class LabelCache implements Serializable {
	private static final long serialVersionUID = 285553930966627974L;
	//
	private static LabelCache labelCache = null;
	private Map<String, Map<String, String>> labelMap = new HashMap<>();

	/**
	 * Initialize
	 */
	private LabelCache() {
	}

	/**
	 * Get Label Cache Instance
	 * 
	 * @return Label Cache Instance
	 */
	public static LabelCache getInstance() {
		if (labelCache == null) {
			labelCache = new LabelCache();
		}
		return labelCache;
	}

	/**
	 * Update Label
	 * 
	 * @param code
	 *            Code
	 * @param label
	 *            Label
	 * @param locale
	 *            Locale as String
	 */
	public void updateLabel(String code, String label, String locale) {
		String localeKey = String.format("%s.%s", TenantContext.getTenantId(), locale);
		Map<String, String> localeMap = labelMap.get(localeKey);
		//
		if (localeMap == null) {
			localeMap = new HashMap<String, String>();
			// Update Locale Map in Label Map
			labelMap.put(localeKey, localeMap);
		}
		// Update Code Map
		if (code != null) {
			localeMap.put(code.toLowerCase().trim(), label);
		}
	}

	/**
	 * Update Labels
	 * 
	 * @param labels
	 *            List of Label
	 * @param locale
	 *            Locale as String
	 */
	public void updateLabels(List<ReportLabel> labels, String locale) {
		// Update All Labels
		if (labels != null && labels.size() > 0 && locale != null) {
			for (ReportLabel label : labels) {
				updateLabel(label.getCode(), label.getLabel(), locale);
			}
		}
	}

	/**
	 * Get Label
	 * 
	 * @param code
	 *            Code
	 * @param locale
	 *            Locale as String
	 */
	public String getLabel(String code, String locale) {
		String localeKey = String.format("%s.%s", TenantContext.getTenantId(), locale);
		Map<String, String> localeMap = labelMap.get(localeKey);
		// Get the Label for Particular Locale
		if (localeMap != null && code != null) {
			return localeMap.get(code.toLowerCase().trim());
		}
		return null;
	}

	/**
	 * Has Locale Labels
	 * 
	 * @param locale
	 *            Locale as String
	 * @return True if Locale Labels Exists
	 */
	public boolean hasLocaleLabels(String locale) {
		String localeKey = String.format("%s.%s", TenantContext.getTenantId(), locale);
		Map<String, String> localeMap = labelMap.get(localeKey);
		// Check for Locale Labels
		return (localeMap != null);
	}
}
