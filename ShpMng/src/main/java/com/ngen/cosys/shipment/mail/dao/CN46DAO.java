/**
 * 
 * CN46DAO.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 14 April, 2018 NIIT -
 */
package com.ngen.cosys.shipment.mail.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.mail.model.CN46Details;
import com.ngen.cosys.shipment.mail.model.CreateCN46;

public interface CN46DAO {

	/**
	 * @param request
	 * @return
	 * @throws CustomException
	 */
	CreateCN46 getFlightId(CreateCN46 request) throws CustomException;

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

	/**
	 * @param request
	 * @return
	 * @throws CustomException
	 */
	CreateCN46 insertCN46RequestDetails(List<CN46Details> request) throws CustomException;

	/**
	 * @param request
	 * @return
	 * @throws CustomException
	 */
	CreateCN46 updateCN46Request(CreateCN46 request) throws CustomException;

	/**
	 * @param request
	 * @return
	 * @throws CustomException
	 */
	CreateCN46 updateCN46RequestDetails(List<CN46Details> request) throws CustomException;

	void deleteManifestShipments(CN46Details deleteValue) throws CustomException;

	int checkDNExistance(CreateCN46 requestModel) throws CustomException;

	void deleteManifest(CreateCN46 requestModel) throws CustomException;

	String getSegmentName(CreateCN46 requestModel) throws CustomException;
}
