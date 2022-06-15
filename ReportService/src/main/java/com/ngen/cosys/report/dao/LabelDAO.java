/**
 * Label Data Access Object Interface
 * 
 * @copyright SATS Singapore 2019-20
 */
package com.ngen.cosys.report.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.report.model.ReportLabel;

/**
 * Label Data Access Object Interface
 */
public interface LabelDAO {

	/**
	 * Retrieve I18N Labels
	 * 
	 * @param locale
	 *            Locale as String
	 * @return Labels
	 * @throws CustomException
	 */
	List<ReportLabel> getI18NLabels(String locale) throws CustomException;
	/**
	 * Retrieve TenantCityName
	 * 
	 * @param cityCode
	 *            
	 * @return Tenant City Name
	 * @throws CustomException
	 */
	String getTenantCityName(String cityCode) throws CustomException;
}
