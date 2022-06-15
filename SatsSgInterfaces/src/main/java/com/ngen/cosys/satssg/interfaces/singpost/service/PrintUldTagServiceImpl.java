package com.ngen.cosys.satssg.interfaces.singpost.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.AutoWeigh;
import com.ngen.cosys.ics.model.AutoWeighDG;
import com.ngen.cosys.ics.model.PrintULDTagList;
import com.ngen.cosys.ics.model.PrintULDTagSubList;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.satssg.interfaces.singpost.dao.PrintULDTagDao;
import com.ngen.cosys.satssg.interfaces.singpost.model.PrintULDTagRequestModel;
import com.ngen.cosys.satssg.interfaces.util.PrinterService;
import com.ngen.cosys.service.util.enums.PrinterType;
import com.ngen.cosys.service.util.enums.ReportRequestType;
import com.ngen.cosys.timezone.util.TenantZoneTime;
import com.ngen.cosys.service.util.model.ReportRequest;

@Service
public class PrintUldTagServiceImpl implements PrintUldTagService {

	private static final Logger lOGGER = LoggerFactory.getLogger(PrintUldTagService.class);

	@Autowired
	private PrintULDTagDao printULDTagDao;

	@Autowired
	private PrinterService printerService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public String printUldTag(PrintULDTagRequestModel request) throws CustomException {

		ReportRequest report = new ReportRequest();
		report.setRequestType(ReportRequestType.PRINT);
		report.setPrinterType(PrinterType.ULD_TAG);

		lOGGER.warn("value of workstation id=", request.getWorkstationId());

		// get printer IP address based on XPS Flag
		String printer = printULDTagDao.getprinterIPAddressAppSystemParams(request);
		//String printer ="NGCUATPRT02";
		
		lOGGER.warn("printer name=", printer);

		if (!StringUtils.isEmpty(printer)) {
			report.setQueueName(printer);
		} else {
			lOGGER.warn("There is no printer configuration for ULD Tag " +request.getWorkstationId()+" - "+request.getContainerId());
		}

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("remark", request.getUldNog());
		if (!StringUtils.isEmpty(request.getContentCode())) {
			if (request.getContentCode().equalsIgnoreCase("C")) {
				request.setContent("Cargo".toUpperCase());
				request.setUldNog("Cargo".toUpperCase());
			} else if (request.getContentCode().equalsIgnoreCase("D")) {
				request.setContent("Dangerous Goods".toUpperCase());
				request.setUldNog("Dangerous Goods".toUpperCase());
			} else if (request.getContentCode().equalsIgnoreCase("M")) {
				request.setContent("Mail".toUpperCase());
				request.setUldNog("Mail".toUpperCase());
			}
		}

		if ("D".equalsIgnoreCase(request.getContentCode()) && request.getDgClassList() != null) {
			parameters.put("dgDetailsList", convertDgDetailsDataForPrinterConfiguration(request));
		}
		
		parameters.put("content", request.getContent());
		parameters.put("flightDate", getFormattedDate(request.getOutgoingFlightDate()));
		parameters.put("flightNo", request.getOutgoingFlightCarrier().concat(request.getOutgoingFlightNumber()));
		parameters.put("loaded", MultiTenantUtility.getAirportCodeFromContext());
		parameters.put("totalweight", request.getUldGrossWeight());
		parameters.put("tareweight", request.getUldTareWeight());
		parameters.put("destination", request.getOffPoint());
		parameters.put("uldtagId", request.getContainerId());
		parameters.put("time", LocalTime.now().toString().toUpperCase());
		parameters.put("date", getFormattedDate(LocalDate.now()));
		parameters.put("user", request.getStaffLoginId()+ "(ICS)" +getFormattedDateWithTime(TenantZoneTime.getZoneDateTime(LocalDateTime.now(),report.getTenantId())));
		parameters.put("netweight", request.getUldNetWeight());

		parameters.put("workstationId", request.getWorkstationId());
		parameters.put("outgoingFlightCarrier", request.getOutgoingFlightCarrier());
		parameters.put("STDTime", request.getStdTime());
	    if(!StringUtils.isEmpty(request.getXpsFlag()) && request.getXpsFlag().equalsIgnoreCase("Y")) {
	    	parameters.put("IsXPS", "Yes");
	    }

		report.setParameters(parameters);

		// Store the info
		this.printULDTagDao.insertPrintULDTagInfo(request);
		
		//insert into auto weigh tables
		AutoWeigh autoweigh=new AutoWeigh();
		autoweigh.setUldNumber(request.getContainerId());
		autoweigh.setCarrierCode(request.getOutgoingFlightCarrier());
		autoweigh.setFlightKey(request.getOutgoingFlightCarrier().concat(request.getOutgoingFlightNumber()));
		autoweigh.setDate(request.getOutgoingFlightDate());
		autoweigh.setOffPoint(request.getOffPoint());
		if(!StringUtils.isEmpty(request.getUldGrossWeight())) {
			autoweigh.setGrossWeight(new BigDecimal(request.getUldGrossWeight()));
		}
		if(!StringUtils.isEmpty(request.getXpsFlag()) && request.getXpsFlag().equalsIgnoreCase("Y")) {
			autoweigh.setXpsShipment(true);
		}else {
			autoweigh.setXpsShipment(false);
		}
		if (request.getContentCode().equalsIgnoreCase("D")) {
			autoweigh.setDgShipment(true);
		}else if(request.getContentCode().equalsIgnoreCase("C")) {
			autoweigh.setCargo(true);
		}else if(request.getContentCode().equalsIgnoreCase("M")) {
			autoweigh.setMail(true);
		}
		autoweigh.setTagRemarks(request.getUldNog());
		autoweigh.setCreatedBy(request.getStaffLoginId());
		
		this.printULDTagDao.insertAutoWeighInfo(autoweigh);
		
		// insert into auto weigh DG table
		// 2. Save the ULD Tag information
		if (!CollectionUtils.isEmpty(request.getDgClassList())) {
			for (PrintULDTagList t : request.getDgClassList()) {
				AutoWeighDG dgdetails = new AutoWeighDG();
				dgdetails.setAutoWeighBupHeaderId(autoweigh.getAutoWeighBupHeaderId());
				dgdetails.setClassCode(t.getDgClassCode());
				dgdetails.setSpecialHandlingCode(t.getShc());
				dgdetails.setCreatedBy(request.getStaffLoginId());
				this.printULDTagDao.insertAutoWeighDG(dgdetails);
			}
		}

		// 3. Save the ULD Sub Tag information
		if (!CollectionUtils.isEmpty(request.getDgSubClassList())) {
			for (PrintULDTagSubList t : request.getDgSubClassList()) {
				AutoWeighDG dgdetails = new AutoWeighDG();
				dgdetails.setAutoWeighBupHeaderId(autoweigh.getAutoWeighBupHeaderId());
				dgdetails.setClassCode(t.getSubRiskId());
				dgdetails.setSpecialHandlingCode(t.getShc());
				dgdetails.setCreatedBy(request.getStaffLoginId());
				this.printULDTagDao.insertAutoWeighDG(dgdetails);
			}
		}

		// Store the info in audit log
		HashMap<String, Object> auditMap = new HashMap<>();
		auditMap.put("eventActor", request.getStaffLoginId() + "(ICS)");
		auditMap.put("eventAction", "ADD");
		auditMap.put("entityType", NgenAuditEntityType.ULD);
		auditMap.put("eventName", "PrintULDTag");
		auditMap.put("entityValue", request.getContainerId());
		

		// Construct the EventValue
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonStr = null;
		try {
			HashMap<String, Object> auditEventMap = new HashMap<>();
			auditEventMap.put("eventValue", parameters);
			jsonStr = objectMapper.writeValueAsString(auditEventMap);
		} catch (JsonProcessingException e) {
			// Do nothing
		}
		auditMap.put("eventValue", jsonStr);
		LocalDateTime gmtTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),TenantContext.get().getTenantId());
		auditMap.put("gmtTime", gmtTime);
		this.printULDTagDao.logMasterAuditData(auditMap);

		// Print the tag
		try {
		   printerService.printULDTag(report);
		}catch(Exception ex) {
		   lOGGER.error("There is no printer configuration for ULD Tag", ex);
		}
		return null;

	}

	private static String getFormattedDate(LocalDate date) {
		if (!ObjectUtils.isEmpty(date)) {
			return date.format(DateTimeFormatter.ofPattern("ddMMMyy")).toUpperCase();
		}
		return "";
	}
	
	private static String getFormattedDateWithTime(LocalDateTime date) {
		if (!ObjectUtils.isEmpty(date)) {
			return date.format(DateTimeFormatter.ofPattern("ddMMM HHmm")).toUpperCase();
		}
		return "";
	}

	List<String> convertDgDetailsDataForPrinterConfiguration(PrintULDTagRequestModel request) {
		List<String> dgDetailList = new ArrayList<>();
		request.getDgClassList().forEach(data -> dgDetailList.add(data.getDgClassCode() + " " + data.getShc()));
		if(request.getDgSubClassList() != null) {
			request.getDgSubClassList().forEach(data -> dgDetailList.add(data.getSubRiskId() + " " + data.getShc()));
		}
		return dgDetailList;
	}

}