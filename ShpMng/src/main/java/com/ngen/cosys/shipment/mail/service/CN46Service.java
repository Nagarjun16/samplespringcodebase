/**
 * 
 * CN46Service.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 14 April, 2018 NIIT -
 */
package com.ngen.cosys.shipment.mail.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.mail.model.CreateCN46;

public interface CN46Service {

	/**
	 * @param request
	 * @return
	 * @throws CustomException
	 */
	CreateCN46 searchCN46Details(CreateCN46 request) throws CustomException;

	/**
	 * @param request
	 * @return
	 * @throws CustomException
	 */
	CreateCN46 insertCN46Request(CreateCN46 request) throws CustomException;
}
