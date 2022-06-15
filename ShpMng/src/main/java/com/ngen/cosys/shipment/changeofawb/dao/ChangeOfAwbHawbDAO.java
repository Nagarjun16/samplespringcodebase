/**
 *   ChangeOfAwbHawbDAO.java
 *   
 *   Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          30 May, 2018   NIIT      -
 */
package com.ngen.cosys.shipment.changeofawb.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.changeofawb.model.ChangeOfAwbHawb;

public interface ChangeOfAwbHawbDAO {

	/**
	 * Method to update database for shipment number
	 * @return
	 * @throws CustomException
	 */
	public ChangeOfAwbHawb updateAwbNumber(ChangeOfAwbHawb request) throws CustomException;
	/**
	 * DAO method to update hawb number if found 
	 * else add error and throw it as exception
	 * @param request
	 * @return
	 * @throws CustomException
	 */
	public ChangeOfAwbHawb updateHawbNumber(ChangeOfAwbHawb request) throws CustomException;
	
	/**
	 * DAO method to update hawb number(handled by house) if found 
	 * else add error and throw it as exception
	 * @param request
	 * @return
	 * @throws CustomException
	 */
	public ChangeOfAwbHawb updateHawbNumberHandledByHouse(ChangeOfAwbHawb request) throws CustomException;
	
	/**
	 * DAO method to check if any paid charges for given shipment
	 * if true throw exception else proceed to update
	 * @param request
	 * @return boolean
	 * @throws CustomException
	 */
	public boolean CheckAnyPaidChargeForAWB(ChangeOfAwbHawb request) throws CustomException;

}
