/**
 * 
 */
package com.ngen.cosys.shipment.changeofawb.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.changeofawb.model.ChangeOfAwbHawb;

/**
 * @author Yuganshu.K
 *
 */
public interface ChangeAwbHawbService {

	/**
	 * Method used to make DAO call
	 * and do all necessary checks
	 * @return
	 * @throws CustomException
	 */
	public ChangeOfAwbHawb updateAwbNumber(ChangeOfAwbHawb request) throws CustomException;
	/**
	 * Method to update hawb number 
	 * @param request
	 * @return
	 * @throws CustomException
	 */
	public ChangeOfAwbHawb updateHawbNumber(ChangeOfAwbHawb request) throws CustomException;
}
