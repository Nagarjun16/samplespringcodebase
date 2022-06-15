package com.ngen.cosys.aed.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ngen.cosys.aed.dao.OutboundShipmentStartCargoAcceptanceDAO;
import com.ngen.cosys.aed.model.GhaFlightSchdRequestModel;
import com.ngen.cosys.aed.model.GhaMawbInfoRequestModel;
import com.ngen.cosys.aed.model.GhaMawbNoRequestModel;
import com.ngen.cosys.aed.model.GhaScanInfoRequestModel;
import com.ngen.cosys.aed.model.ScInspecRmkGhaResponseModel;
import com.ngen.cosys.aed.model.ScSumofWtGhaResposeModel;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.payload.OutboundFlightCompleteStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentRcarScreenScanInfoStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentStartCargoAcceptanceStoreEvent;
import com.ngen.cosys.framework.exception.CustomException;

@Service
@Transactional
public class OutboundShipmentEAcceptanceServiceImpl implements OutboundShipmentEAcceptanceService {

	@Autowired
	OutboundShipmentStartCargoAcceptanceDAO scAedDao;

	// AD1 Message
	@Override
	public String getOutboundShipmentCargoAcceptancePayload(OutboundShipmentStartCargoAcceptanceStoreEvent payload)
			throws CustomException, IOException {
		GhaMawbNoRequestModel xmlMessage = scAedDao.constructingCargoAcceptanceMessage(payload);
		XmlMapper xmlMapper = new XmlMapper();
		return xmlMapper.writeValueAsString(xmlMessage);
	}

	// AD4 Message
	@Override
	public String getOutboundShipmentPiecesEqualsToAcceptedPieces(
			OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEvent payload)
			throws JsonProcessingException, CustomException {
		GhaMawbInfoRequestModel finalizedMessage = scAedDao.constructingFinalizedAutoWaightMessage(payload);
		String xmlData = null;
		if (finalizedMessage != null) {
			XmlMapper xmlMapper = new XmlMapper();
			xmlData = xmlMapper.writeValueAsString(finalizedMessage);
			if (!xmlData.contains("<EXEMPTION_CODE>")) {
				xmlData = xmlData.replace("<EXEMPTION_CODE/>", "");
			}

		}
		return xmlData;
	}

	// AD5 Message
	@Override
	public String getOutboundShipmentRcarScreenScanInfo(OutboundShipmentRcarScreenScanInfoStoreEvent payload)
			throws JsonProcessingException, CustomException {
		GhaScanInfoRequestModel scanInfoMessage = scAedDao.constructingRcarScanInfoMessage(payload);
		XmlMapper xmlMapper = new XmlMapper();
		return xmlMapper.writeValueAsString(scanInfoMessage);
	}

	// AD6 Message
	@Override
	public String getOutboundShipmentFlightCompleted(OutboundFlightCompleteStoreEvent payload)
			throws JsonProcessingException, CustomException {
		GhaFlightSchdRequestModel flightschd = scAedDao.constructingFlihtScheduleMessage(payload);
		XmlMapper xmlMapper = new XmlMapper();
		if (!ObjectUtils.isEmpty(flightschd)) {
			return xmlMapper.writeValueAsString(flightschd);
		}
		return null;
	}

	// AD2 Message
	@Override
	public String insertOuboundShipmentSumOfDeclaredWaight(ScSumofWtGhaResposeModel respoceData) {
		String status = null;
		try {
			status = scAedDao.saveOuboundShipmentSumOfDeclaredWaighte(respoceData);
		} catch (Exception e) {
			status = "Failure";
		}
		return status;

	}

	// AD3 Message
	@Override
	public String insertOuboundShipmentInspectionRemarks(ScInspecRmkGhaResponseModel respoceData)
			throws CustomException {
		String status = null;
		try {
			status = scAedDao.saveOuboundShipmentInspectionRemarks(respoceData);
		} catch (Exception e) {
			status = "Failure";
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.aed.service.OutboundShipmentEAcceptanceService#getShipmentInfo
	 * (java.lang.String)
	 */
	@Override
	public Map<String, Object> getShipmentInfo(BigInteger shipmentId) throws CustomException {
		return this.scAedDao.getShipmentInfo(shipmentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.aed.service.OutboundShipmentEAcceptanceService#getFlightInfo(
	 * java.math.BigInteger)
	 */
	@Override
	public Map<String, Object> getFlightInfo(BigInteger flightId) throws CustomException {
		return this.scAedDao.getFlightInfo(flightId);
	}

	public OutgoingMessageLog getFlightDetailsForLogging(OutgoingMessageLog log) throws CustomException {
		return scAedDao.getFlightDetailsForLogging(log);

	}
}