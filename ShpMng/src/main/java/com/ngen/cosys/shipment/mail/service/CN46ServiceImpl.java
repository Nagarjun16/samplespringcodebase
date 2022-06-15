/**
 * 
 * CN46ServiceImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason v0.3 14 April, 2018 NIIT -
 */
package com.ngen.cosys.shipment.mail.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.mail.dao.CN46DAO;
import com.ngen.cosys.shipment.mail.model.CN46Details;
import com.ngen.cosys.shipment.mail.model.CreateCN46;

/**
 * This class takes care of the responsibilities related to the CN 46 service
 * operation that comes from the controller.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
public class CN46ServiceImpl implements CN46Service {

	private static final String FORM_CTRL_CN46_FORM = "cn46Form";

	@Autowired
	private CN46DAO cn46DAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.mail.service.CN46Service#searchCN46Details(com.ngen.
	 * cosys.shipment.mail.model.CreateCN46)
	 */
	@Override
	public CreateCN46 searchCN46Details(CreateCN46 request) throws CustomException {
		CreateCN46 cn = new CreateCN46();
		if (StringUtils.isEmpty(request.getFlightKey()) || (request.getFlightDate() == null)
				|| (BigInteger.ZERO.equals(request.getSegmentId()))) {
			throw new CustomException("CN46_06", FORM_CTRL_CN46_FORM, ErrorType.ERROR);
		} else {
			cn = cn46DAO.getFlightId(request);
			request.setFlightId(cn.getFlightId());
			if (BigInteger.ZERO.equals(request.getFlightId())) {
				throw new CustomException("CN46_02", FORM_CTRL_CN46_FORM, ErrorType.ERROR);
			}
		}
		if (request.isBulkFlag()) {
			request.setTrolleyNumber("BULK");
		}
		if (request.getTrolleyNumber() == "") {
			request.setTrolleyNumber(null);
		}
		cn = cn46DAO.searchCN46Details(request);
		return cn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.mail.service.CN46Service#insertCN46Request(com.ngen.
	 * cosys.shipment.mail.model.CreateCN46)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = CustomException.class)
	public CreateCN46 insertCN46Request(CreateCN46 request) throws CustomException {
		// For Audit Trail
		for (CN46Details cn46Details : request.getCn46Details()) {
			cn46Details.setFlightKey(request.getFlightKey());
			cn46Details.setFlightDate(request.getFlightDate());
			cn46Details.setSegment(cn46DAO.getSegmentName(request));
			cn46Details.setTrolleyNumber(request.getTrolleyNumber());
			cn46Details.setBulkFlag(request.isBulk());
			cn46Details.setObservations(request.getObservations());
			cn46Details.setAdminOfOriginOfMails(request.getAdminOfOriginOfMails());
			cn46Details.setAirportOfLoading(request.getAirportOfLoading());
			cn46Details.setAirportOffLoading(request.getAirportOfOffLoading());
			cn46Details.setDestinationOffice(request.getDestinationOffice());
			cn46Details.setOutgoingFlightKey(request.getOutgoingFlightKey());
			cn46Details.setOutgoingFlightDate(request.getOutgoingFlightDate());
		}
		
		CreateCN46 cn = cn46DAO.getFlightId(request);
		request.setFlightId(cn.getFlightId());
		List<CN46Details> updateData = request.getCn46Details().stream()
				.filter(obj -> (Action.UPDATE.toString().equalsIgnoreCase(obj.getFlagCRUD())))
				.collect(Collectors.toList());
		List<CN46Details> createData = request.getCn46Details().stream()
				.filter(obj -> (Action.CREATE.toString().equalsIgnoreCase(obj.getFlagCRUD())))
				.collect(Collectors.toList());
		/* cn46DAO.updateCN46Request(request); */
		List<CN46Details> deletData = request.getCn46Details().stream()
				.filter(obj -> (Action.DELETE.toString().equalsIgnoreCase(obj.getFlagCRUD())))
				.collect(Collectors.toList());
		if(!CollectionUtils.isEmpty(updateData) || !CollectionUtils.isEmpty(createData)) {
		   validateInsertRequest(request, updateData, createData);
		}
		CreateCN46 insertedData = cn46DAO.insertCN46Request(request);
		if (!CollectionUtils.isEmpty(deletData)) {
			deleteCN46Record(request, deletData);
		}
		request.getCn46Details().forEach(e -> {
			if (StringUtils.isEmpty(e.getUldNumber())) {
				e.setUldNumber("BULK");
			}
		});
		if (!CollectionUtils.isEmpty(updateData)) {
			cn46DAO.updateCN46RequestDetails(updateData);
		}
		if (!CollectionUtils.isEmpty(createData)) {
			if (StringUtils.isEmpty(request.getObservations()) || StringUtils.isEmpty(request.getAdminOfOriginOfMails())
					|| StringUtils.isEmpty(request.getAirportOfLoading())
					|| StringUtils.isEmpty(request.getAirportOfOffLoading())
					|| StringUtils.isEmpty(request.getDestinationOffice())) {
				throw new CustomException("CN46_04", FORM_CTRL_CN46_FORM, ErrorType.ERROR);
			}
			for (CN46Details req : createData) {
				if (!Optional.ofNullable(request.getAirmailManifestId()).isPresent()) {
					req.setManifestId(insertedData.getAirmailManifestId());
				} else {
					req.setManifestId(request.getAirmailManifestId());
				}
			}
			cn46DAO.insertCN46RequestDetails(createData);
		}
		return cn;
	}

	private void deleteCN46Record(CreateCN46 requestModel, List<CN46Details> deletData) throws CustomException {
		// delete record from child table(Airmail_ManifestShipments) where there is more
		// than one data is present for a particular flight
		for (CN46Details deleteValue : deletData) {
			cn46DAO.deleteManifestShipments(deleteValue);
		}
		// checking if manifest shipment is present or not for the flight
		int count = cn46DAO.checkDNExistance(requestModel);
		// if there is no data for that DN then it will delete the Airmail_Manifest
		if (count == 0) {
			cn46DAO.deleteManifest(requestModel);
		}

	}

	private void validateInsertRequest(CreateCN46 request, List<CN46Details> updateData, List<CN46Details> createData)
			throws CustomException {
		if (!CollectionUtils.isEmpty(updateData) || !CollectionUtils.isEmpty((createData))) {
			if ((BigInteger.ZERO.equals(request.getFlightId())) || (BigInteger.ZERO.equals(request.getSegmentId()))) {
				throw new CustomException("CN46_02", FORM_CTRL_CN46_FORM, ErrorType.ERROR);
			}
		}

		for (CN46Details e : request.getCn46Details()) {
			if (StringUtils.isEmpty(e.getMailNumber()) || StringUtils.isEmpty(e.getOriginOfficeExchange())
					|| StringUtils.isEmpty(e.getDestinationOfficeExchange())
					|| StringUtils.isEmpty(e.getAirportOfTranshipment())
					|| StringUtils.isEmpty(e.getAirportOfOffloading()) || (e.getDateOfDispactch() == null)) {
				throw new CustomException("CN46_04", FORM_CTRL_CN46_FORM, ErrorType.ERROR);
			}
			if ((BigInteger.ZERO.equals(e.getCp())) && (BigInteger.ZERO.equals(e.getLetterPost()))
					&& (BigInteger.ZERO.equals(e.getOtherItems())) && (BigInteger.ZERO.equals(e.getEms()))) {
				throw new CustomException("CN46_01", FORM_CTRL_CN46_FORM, ErrorType.ERROR);
			}
		}
	}
}