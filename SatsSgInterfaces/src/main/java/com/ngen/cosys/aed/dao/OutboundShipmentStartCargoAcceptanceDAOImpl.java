package com.ngen.cosys.aed.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.aed.model.Details;
import com.ngen.cosys.aed.model.FlightSchedule;
import com.ngen.cosys.aed.model.GhaFlightSchdRequestDetails;
import com.ngen.cosys.aed.model.GhaFlightSchdRequestModel;
import com.ngen.cosys.aed.model.GhaMawbInfoRequestDetails;
import com.ngen.cosys.aed.model.GhaMawbInfoRequestModel;
import com.ngen.cosys.aed.model.GhaMawbNoRequestModel;
import com.ngen.cosys.aed.model.GhaScanInfoRequestDetails;
import com.ngen.cosys.aed.model.GhaScanInfoRequestModel;
import com.ngen.cosys.aed.model.Header;
import com.ngen.cosys.aed.model.RcarScreenModel;
import com.ngen.cosys.aed.model.ScInspecRmkGhaInspectInd;
import com.ngen.cosys.aed.model.ScInspecRmkGhaResponseModel;
import com.ngen.cosys.aed.model.ScSumofWtGhaResposeModel;
import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.payload.OutboundFlightCompleteStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentRcarScreenScanInfoStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentStartCargoAcceptanceStoreEvent;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Repository
public class OutboundShipmentStartCargoAcceptanceDAOImpl extends BaseDAO
		implements OutboundShipmentStartCargoAcceptanceDAO {

	private static final String SUCCESS = "Success";

	private static final String FAILURE = "Failure";

	private static final Logger LOGGER = LoggerFactory.getLogger(OutboundShipmentStartCargoAcceptanceDAOImpl.class);

	private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

	@Autowired
	private SqlSessionTemplate sqlSessionShipment;

	@Autowired
	private ShipmentProcessorService shipmentProcessorService;

	// OutGoing AD1 Message (GHA MAWB Number Message for Acceptance)
	@Override
	public GhaMawbNoRequestModel constructingCargoAcceptanceMessage(
			OutboundShipmentStartCargoAcceptanceStoreEvent model) throws CustomException {
		// Check shipment can be submitted or not
		GhaMawbNoRequestModel requestModel = new GhaMawbNoRequestModel();
		String format = dateAsstring(LocalDateTime.now());
		String dateFornat = dateAsstringwith20(LocalDateTime.now());
		Header header = new Header();
		header.setMessageId(BigInteger.valueOf(Long.valueOf(format)));
		header.setMessageType("GHAMAWBNo");
		header.setSendDateTime(LocalDateTime.now().plusHours(8));
		requestModel.setHeader(header);
		Details details = new Details();
		details.setGhaMawbNoId("GMWV" + dateFornat);
		details.setMawbNo(model.getShipmentNumber());
		requestModel.setDetails(details);

		return requestModel;
	}

	// OutGoing AD4 Message (GHA MAWB INFO Message for Auto Waight)
	@Override
	public GhaMawbInfoRequestModel constructingFinalizedAutoWaightMessage(
			OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEvent requestModel) throws CustomException {
		// Get shipment number
		String shipmentNumber = super.fetchObject("getShipmentNumber", requestModel.getShipmentId(),
				sqlSessionShipment);
		// Check shipment can be submitted or not
		Boolean canSubmit = this.isCustomSubmissionRequired(shipmentNumber);
		GhaMawbInfoRequestModel infoRequestModel = null;
		if (canSubmit) {
			String exemptionCode = super.fetchObject("getExemption", requestModel.getShipmentId(), sqlSessionShipment);
			if (StringUtils.isEmpty(exemptionCode)) {
				exemptionCode = super.fetchObject("getExemptionCodeFromLocalAuthority", requestModel.getShipmentId(),
						sqlSessionShipment);
			}

			infoRequestModel = new GhaMawbInfoRequestModel();
			String format = dateAsstring(LocalDateTime.now());
			String dateFornat = dateAsstringwith20(LocalDateTime.now());
			Header header = new Header();
			header.setMessageId(BigInteger.valueOf(Long.valueOf(format)));
			header.setMessageType("GHAMAWBInfo");
			header.setSendDateTime(LocalDateTime.now().plusHours(8));
			infoRequestModel.setHeader(header);
			GhaMawbInfoRequestDetails details = new GhaMawbInfoRequestDetails();
			details.setGhaMawbInfoId("GMIV" + dateFornat);
			details.setRecordInd("N");
			details.setMawbNo(shipmentNumber);
			RcarScreenModel model = super.fetchObject("getSumOfWeightForMAWBno", requestModel.getShipmentId(),
					sqlSessionShipment);
			BigDecimal totalFinalized = BigDecimal.ZERO;
			BigDecimal totalGross = BigDecimal.ZERO;
			if (model != null) {
				if (model.getFinalizeWeight() != null) {
					totalFinalized = model.getFinalizeWeight().setScale(1, BigDecimal.ROUND_UP);
				}
				if (model.getTotalGrossWeight() != null) {
					totalGross = model.getTotalGrossWeight().setScale(1, BigDecimal.ROUND_UP);
				}
				BigDecimal weight = totalFinalized;
				String w = String.valueOf(weight);
				w = removeDecimalPoint(w);
				details.setTotalActualWt(w);
				details.setTotalActualWtUmo("KGM");
				Double tollerance = (totalGross.doubleValue() / (totalFinalized.doubleValue()))
						* (ONE_HUNDRED.doubleValue());
				BigDecimal tolleranceInBigdecimal = new BigDecimal(tollerance);
				BigDecimal tl = tolleranceInBigdecimal.setScale(1, BigDecimal.ROUND_HALF_UP);
				String t = String.valueOf(tl);
				t = removeDecimalPoint(t);
				details.setPercentTotalGross(t);
			} else {
				details.setTotalActualWt("0");
				details.setPercentTotalGross("0");
				details.setTotalActualWtUmo("KGM");
			}
			infoRequestModel.setDetails(details);
			if (exemptionCode != null) {
				details.setExemptionCode(exemptionCode);
			}
			LOGGER.error("constructing message AED4 Constructed");
		}
		return infoRequestModel;
	}

	private String removeDecimalPoint(String d) {
		if (d != null && d.contains(".0")) {
			d = d.replace(".0", "");
		}
		return d;
	}

	// OutGoing AD5 Message (GHA Scan Info Message)
	@Override
	public GhaScanInfoRequestModel constructingRcarScanInfoMessage(
			OutboundShipmentRcarScreenScanInfoStoreEvent requestModel) throws CustomException {
		// Get shipment number
		String shipmentNumber = super.fetchObject("getShipmentNumber", requestModel.getShipmentId(),
				sqlSessionShipment);
		// Check shipment can be submitted or not
		Boolean canSubmit = this.isCustomSubmissionRequired(shipmentNumber);
		GhaScanInfoRequestModel infoRequestModel = null;
		if (canSubmit) {
			infoRequestModel = new GhaScanInfoRequestModel();
			String format = dateAsstring(LocalDateTime.now());
			String dateFornat = dateAsstringwith20(LocalDateTime.now());
			Header header = new Header();
			header.setMessageId(BigInteger.valueOf(Long.valueOf(format)));
			header.setMessageType("GHAScanInfo");
			header.setSendDateTime(LocalDateTime.now().plusHours(8));
			infoRequestModel.setHeader(header);
			GhaScanInfoRequestDetails details = new GhaScanInfoRequestDetails();
			details.setGhaScanInfoId("GSIV" + dateFornat);
			details.setRecordInd("N");
			details.setMawbNo(shipmentNumber);
			details.setInspectionOutcome(requestModel.getScreeningReason());
			details.setInspectionDateTime(LocalDateTime.now().plusHours(8));
			details.setInspectionRemarks(requestModel.getRemarks());
			infoRequestModel.setDetails(details);
		}
		return infoRequestModel;
	}

	// OutGoing AD6 Message (GHA Flight Schedule Message)
	@Override
	public GhaFlightSchdRequestModel constructingFlihtScheduleMessage(OutboundFlightCompleteStoreEvent requestModel)
			throws CustomException {
		List<RcarScreenModel> shipments = this.fetchList("getOutboundFlightShipments",MultiTenantUtility.getAirportCityMap(requestModel.getFlightId()),
				sqlSessionShipment);
		if (!CollectionUtils.isEmpty(shipments)) {
			GhaFlightSchdRequestModel flightSchdRequestModel = new GhaFlightSchdRequestModel();
			String format = dateAsstring(LocalDateTime.now());
			String dateFornat = dateAsstringwith20(LocalDateTime.now());
			Header header = new Header();
			header.setMessageId(BigInteger.valueOf(Long.valueOf(format)));
			header.setMessageType("GHAFlightSchd");
			header.setSendDateTime(LocalDateTime.now().plusHours(8));
			flightSchdRequestModel.setHeader(header);
			GhaFlightSchdRequestDetails details = new GhaFlightSchdRequestDetails();
			details.setGhaFlightSchdId("GFSV" + dateFornat);
			List<FlightSchedule> listflightschd = new ArrayList<>();
			FlightSchedule[] flightSchdule = new FlightSchedule[listflightschd.size()];
			// Get the Flight Info
			Map<String, Object> flightInfoMap = this.getFlightInfo(requestModel.getFlightId());
			// Get manifest shipment info
			for (RcarScreenModel shipment : shipments) {
				if (MultiTenantUtility.isTenantCityOrAirport(shipment.getOrigin())) {
					FlightSchedule schedule = new FlightSchedule();
					schedule.setRecordIndicator(shipment.getRegisteredIndicator());
					schedule.setMawbNo(shipment.getAwbNuber());
					Timestamp tempDate = (Timestamp) flightInfoMap.get("FlightDate");
					schedule.setFlightDepartDateTime(tempDate.toLocalDateTime());
					listflightschd.add(schedule);
				}
			}
			flightSchdule = listflightschd.toArray(flightSchdule);
			details.setFlightSchdule(flightSchdule);

			flightSchdRequestModel.setDetails(details);
			return flightSchdRequestModel;
		}
		return null;

	}

	// Incoming AD2 Message
	public String saveOuboundShipmentSumOfDeclaredWaighte(ScSumofWtGhaResposeModel responseData)
			throws CustomException {
		String status = FAILURE;
		if(!StringUtils.isEmpty(responseData.getDetails().getMawbNo())) {
		  LocalDate shipmentDate = this.shipmentProcessorService.getShipmentDate(responseData.getDetails().getMawbNo());
		  if (shipmentDate != null) {
			  responseData.getDetails().setShipmentDate(shipmentDate);
		  }
		}
		String statusOfStartAcceptance = super.fetchObject("getStatusOfStartAcceptance",
				responseData.getDetails().getMawbNo(), sqlSessionShipment);
		try {
			if (responseData.getDetails().getRecordInd().equalsIgnoreCase("Y")) {
				responseData.setIndicator(1);
			} else {
				responseData.setIndicator(0);
			}
			if (statusOfStartAcceptance == null) {
				int count = super.fetchObject("getOutboundShipmnetSumOfDeclaredWaightForGha",
						responseData.getDetails().getMawbNo(), sqlSessionShipment);
				if (count == 0) {
					super.insertData("saveOutboundShipmnetSumOfDeclaredWaightForGha", responseData, sqlSessionShipment);
				} else {
					super.updateData("updateOutboundShipmnetSumOfDeclaredWaightForGha", responseData,
							sqlSessionShipment);
				}
				status = SUCCESS;
			}
		} catch (Exception e) {
			status = FAILURE;
		}
		return status;

	}

	// Incoming AD3 Message
	@Override
	public String saveOuboundShipmentInspectionRemarks(ScInspecRmkGhaResponseModel responseData)
			throws CustomException {
		ScInspecRmkGhaInspectInd[] remarksList = responseData.getDetails().getInspectIndGha();
		String status = null;
		
		if (!StringUtils.isEmpty(responseData.getDetails().getMawbNo())) {
			LocalDate shipmentDate = this.shipmentProcessorService.getShipmentDate(responseData.getDetails().getMawbNo());
			if (shipmentDate != null) {
				responseData.getDetails().setShipmentDate(shipmentDate);
			}
		}
		String statusOfStartAcceptance = super.fetchObject("getStatusOfStartAcceptance",
				responseData.getDetails().getMawbNo(), sqlSessionShipment);
		try {
			if (statusOfStartAcceptance == null) {
				Long logid = super.fetchObject("getOutboundShipmnetInspectionRemarks",
						responseData.getDetails().getMawbNo(), sqlSessionShipment);
				if (logid == null) {
					super.insertData("saveOutboundShipmnetInspectionRemarks", responseData, sqlSessionShipment);
					for (ScInspecRmkGhaInspectInd scInspecRmkGhaInspectInd : remarksList) {
						scInspecRmkGhaInspectInd.setAedMessageLogId(responseData.getAedMessageLogId());
						super.insertData("saveOutboundShipmnetListOfRemarks", scInspecRmkGhaInspectInd,
								sqlSessionShipment);
					}
				} else {
					for (ScInspecRmkGhaInspectInd scInspecRmkGhaInspectInd : remarksList) {
						scInspecRmkGhaInspectInd.setAedMessageLogId(logid);
						super.updateData("updateOutboundShipmnetListOfRemarks", scInspecRmkGhaInspectInd,
								sqlSessionShipment);
					}
				}
				status = SUCCESS;
			} else {
				status = FAILURE;
			}
		} catch (Exception e) {
			status = FAILURE;
		}
		return status;
	}

	// updating SentOn In 'Event_OutboundShipmentStartCargoAcceptanceStore'
	@Override
	public void saveOuboundShipmentSentOnDetail(OutboundShipmentStartCargoAcceptanceStoreEvent requestModel)
			throws CustomException {
		super.updateData("updateStartCargoAcceptanceSentOnDetails", requestModel, sqlSessionShipment);
	}

	private String dateAsstring(LocalDateTime time) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.N");
		String date = dateFormatter.format(time.plusHours(8));
		date = date.replaceAll("-", "");
		date = date.replaceAll(":", "");
		date = date.replaceAll(" ", "");
		date = date.replace(".", "");
		return date.substring(0, 17);
	}

	private String dateAsstringwith20(LocalDateTime time) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.N");
		String date = dateFormatter.format(time.plusHours(8));
		date = date.replaceAll("-", "");
		date = date.replaceAll(":", "");
		date = date.replaceAll(" ", "");
		date = date.replace(".", "");
		return date.substring(0, 20);
	}

	// updating log table 'Interface_OutgoingMessageLog'
	@Override
	public void updateOutGoingMessagelog(String id) throws CustomException {
		super.updateData("updatedOutGoingInterFaceLog", id, sqlSessionShipment);

	}

	/*
	 * Method to check whether customs submission required based on SHC group
	 */
	private Boolean isCustomSubmissionRequired(String shipmentNumber) throws CustomException {
		// Parameter Map
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("shipmentNumber", shipmentNumber);
		return this.fetchObject("sqlCheckShipmentCanSubmitToCustoms", parameterMap, sqlSessionShipment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.aed.dao.OutboundShipmentStartCargoAcceptanceDAO#
	 * getShipmentInfo(java.math.BigInteger)
	 */
	@Override
	public Map<String, Object> getShipmentInfo(BigInteger shipmentId) throws CustomException {
		return this.fetchObject("sqlGetAEDShipmentInfo", shipmentId, sqlSessionShipment);
	}

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.aed.dao.OutboundShipmentStartCargoAcceptanceDAO#getFlightInfo(
    * java.math.BigInteger)
    */
	@Override
	public Map<String, Object> getFlightInfo(BigInteger flightId) throws CustomException {
		return this.fetchObject("sqlGetAEDFlightInfo", MultiTenantUtility.getAirportCityMap(flightId), sqlSessionShipment);
	}

	public OutgoingMessageLog getFlightDetailsForLogging(OutgoingMessageLog log) throws CustomException {
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("tenantAirport", MultiTenantUtility.getAirportCodeFromContext());
		paramMap.put("shipmentNumber", log.getShipmentNumber());
		paramMap.put("shipmentDate", log.getShipmentDate());
		return this.fetchObject("getFlightInfoForLogger", paramMap, sqlSessionShipment);

	}
}