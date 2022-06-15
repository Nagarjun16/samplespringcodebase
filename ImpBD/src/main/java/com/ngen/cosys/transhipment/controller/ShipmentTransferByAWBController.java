package com.ngen.cosys.transhipment.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.printer.util.PrinterService;
import com.ngen.cosys.service.util.enums.PrinterType;
import com.ngen.cosys.service.util.enums.ReportRequestType;
import com.ngen.cosys.service.util.model.ReportRequest;
import com.ngen.cosys.timezone.util.TenantZoneTime;
import com.ngen.cosys.transhipment.model.TransferManifestDetails;
import com.ngen.cosys.transhipment.model.TranshipmentTransferManifestByAWB;
import com.ngen.cosys.transhipment.model.TranshipmentTransferManifestByAWBInfo;
import com.ngen.cosys.transhipment.model.TranshipmentTransferManifestByAWBSearch;
import com.ngen.cosys.transhipment.service.ShipmentTransferByAWBService;
import com.ngen.cosys.transhipment.validator.group.TransferManifestByAWBMaintain;
import com.ngen.cosys.transhipment.validator.group.TranshipmenntMaintainMobile;
import com.ngen.cosys.transhipment.validator.group.TranshipmentCancelForAWB;
import com.ngen.cosys.transhipment.validator.group.TranshipmentPrintDropDownValidator;
import com.ngen.cosys.transhipment.validator.group.TranshipmentSearchForAWB;

import io.swagger.annotations.ApiParam;
import reactor.util.CollectionUtils;

@NgenCosysAppInfraAnnotation(path = "api/transfer-manifest-by-awb")
public class ShipmentTransferByAWBController {

	private static final String EMPTY_STRING = "";

	private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentTransferByAWBController.class);

	@Autowired
	private ShipmentTransferByAWBService awbService;

	@Autowired
	private UtilitiesModelConfiguration utilitiesModelConfiguration;

	@Autowired
	private PrinterService printerService;

	@ApiParam("Search Transfer manifest By AWB List.")
	@PostRequest(value = "/search-list", method = RequestMethod.POST)
	public BaseResponse<TranshipmentTransferManifestByAWBSearch> searchList(
			@Validated(value = TranshipmentSearchForAWB.class) @RequestBody TranshipmentTransferManifestByAWBSearch search)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<TranshipmentTransferManifestByAWBSearch> returnOnj = utilitiesModelConfiguration
				.getBaseResponseInstance();
		returnOnj.setData(awbService.searchList(search));
		return returnOnj;
	}

	@ApiParam("Search Transfer manifest By AWB.")
	@PostRequest(value = "/search", method = RequestMethod.POST)
	public BaseResponse<TranshipmentTransferManifestByAWB> search(
			@Valid @RequestBody TranshipmentTransferManifestByAWBSearch search) throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<TranshipmentTransferManifestByAWB> returnOnj = utilitiesModelConfiguration
				.getBaseResponseInstance();
		returnOnj.setData(awbService.search(search));
		return returnOnj;
	}

	@ApiParam("Maintain Transfer manifest By AWB.")
	@PostRequest(value = "/maintain", method = RequestMethod.POST)
	public BaseResponse<TranshipmentTransferManifestByAWB> maintain(
			@Validated(TransferManifestByAWBMaintain.class) @RequestBody TranshipmentTransferManifestByAWB maintain)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<TranshipmentTransferManifestByAWB> returnOnj = utilitiesModelConfiguration
				.getBaseResponseInstance();
		returnOnj.setData(awbService.maintain(maintain));
		return returnOnj;
	}

	@ApiParam("Maintain Transfer manifest By AWB.")
	@PostRequest(value = "/cancel", method = RequestMethod.POST)
	public BaseResponse<TranshipmentTransferManifestByAWBSearch> cancelAWB(
			@Validated(value = TranshipmentCancelForAWB.class) @RequestBody TranshipmentTransferManifestByAWBSearch maintain)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<TranshipmentTransferManifestByAWBSearch> returnOnj = utilitiesModelConfiguration
				.getBaseResponseInstance();
		returnOnj.setData(awbService.cancelAWB(maintain));
		return returnOnj;
	}

	@ApiParam("Maintain Transfer manifest By AWB.")
	@PostRequest(value = "/finalize", method = RequestMethod.POST)
	public BaseResponse<TranshipmentTransferManifestByAWBSearch> finalizeAWB(
			@Validated(value = TranshipmentPrintDropDownValidator.class) @RequestBody TranshipmentTransferManifestByAWBSearch maintain)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<TranshipmentTransferManifestByAWBSearch> returnOnj = utilitiesModelConfiguration
				.getBaseResponseInstance();
		TranshipmentTransferManifestByAWBSearch responseData = awbService.finalizeAWB(maintain);
		if (responseData.getMessageList() != null && !responseData.getMessageList().isEmpty()) {
			returnOnj.setSuccess(false);
			returnOnj.setMessageList(responseData.getMessageList());
		} else {
			returnOnj.setData(responseData);
			returnOnj.setSuccess(true);
			maintain.getAwbList().forEach(trmReference -> {
				if (trmReference.getFinalizedFlag() != null && trmReference.getFinalizedFlag()) {
					try {
						if (!StringUtils.isEmpty(trmReference.getPrinterName())) {
							printTRMTag(trmReference);
						}
					} catch (CustomException e) {
						LOGGER.error("TRM Tag printing failed", e);
					}
				}
			});
		}
		return returnOnj;
	}

	@ApiParam("Maintain Transfer manifest By AWB.")
	@PostRequest(value = "/unfinalize", method = RequestMethod.POST)
	public BaseResponse<TranshipmentTransferManifestByAWBSearch> unfinalizeAWB(
			@Valid @RequestBody TranshipmentTransferManifestByAWBSearch maintain) throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<TranshipmentTransferManifestByAWBSearch> returnOnj = utilitiesModelConfiguration
				.getBaseResponseInstance();
		TranshipmentTransferManifestByAWBSearch responseData = awbService.finalizeAWB(maintain);
		if (responseData.getMessageList() != null && !responseData.getMessageList().isEmpty()) {
			returnOnj.setSuccess(false);
			returnOnj.setMessageList(responseData.getMessageList());
		} else {
			returnOnj.setData(responseData);
			returnOnj.setSuccess(true);
			maintain.getAwbList().forEach(trmReference -> {
				if (trmReference.getFinalizedFlag() != null && trmReference.getFinalizedFlag()) {
					try {
						if (!StringUtils.isEmpty(trmReference.getPrinterName())) {
							printTRMTag(trmReference);
						}
					} catch (CustomException e) {
						LOGGER.error("TRM Tag printing failed", e);
					}
				}
			});
		}
		return returnOnj;
	}

	@ApiParam("Maintain Transfer manifest By AWB.")
	@PostRequest(value = "/getTrmNumberWithIssueDate", method = RequestMethod.POST)
	public BaseResponse<TranshipmentTransferManifestByAWB> getTRMNumber(
			@Valid @RequestBody TranshipmentTransferManifestByAWB maintain) throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<TranshipmentTransferManifestByAWB> returnOnj = utilitiesModelConfiguration
				.getBaseResponseInstance();
		returnOnj.setData(awbService.getTRMNumber(maintain));
		return returnOnj;
	}

	@ApiParam("Maintain Transfer manifest By AWB.")
	@PostRequest(value = "/getShipmentInfo", method = RequestMethod.POST)
	public BaseResponse<TranshipmentTransferManifestByAWBInfo> getShipmentDetail(
			@Valid @RequestBody TranshipmentTransferManifestByAWBInfo maintain) throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<TranshipmentTransferManifestByAWBInfo> returnOnj = utilitiesModelConfiguration
				.getBaseResponseInstance();
		returnOnj.setData(awbService.getShipmentDetail(maintain));
		return returnOnj;
	}

	@ApiParam("Mobile Search Transfer manifest By AWB.")
	@PostRequest(value = "/mobile/search", method = RequestMethod.POST)
	public BaseResponse<TranshipmentTransferManifestByAWB> mobileSearch(
			@Validated @RequestBody TranshipmentTransferManifestByAWBSearch search) throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<TranshipmentTransferManifestByAWB> returnOnj = utilitiesModelConfiguration
				.getBaseResponseInstance();
		TranshipmentTransferManifestByAWB rerunData = awbService.mobileSearch(search);
		if (rerunData.isError()) {
			returnOnj.setSuccess(false);
			returnOnj.setMessageList(rerunData.getMessageList());
		} else {
			returnOnj.setData(rerunData);
			returnOnj.setSuccess(true);
		}
		return returnOnj;
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.TRM, eventName = NgenAuditEventType.MAINTAIN_TRM_AWB)
	@ApiParam("Mobile Maintain Transfer manifest By AWB.")
	@PostRequest(value = "/mobile/maintain", method = RequestMethod.POST)
	public BaseResponse<TranshipmentTransferManifestByAWB> mobileMaintain(
			@Validated(value = TranshipmenntMaintainMobile.class) @RequestBody TranshipmentTransferManifestByAWB search)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<TranshipmentTransferManifestByAWB> returnOnj = utilitiesModelConfiguration
				.getBaseResponseInstance();

		TranshipmentTransferManifestByAWB rerunData = awbService.mobileMaintain(search);
		if (rerunData.isError()) {
			returnOnj.setSuccess(false);
			returnOnj.setMessageList(rerunData.getMessageList());
		} else {
			returnOnj.setData(rerunData);
			returnOnj.setSuccess(true);
		}

		return returnOnj;
	}

	@ApiParam("Printing of TRM")
	@PostRequest(value = "/printtrm", method = RequestMethod.POST)
	public BaseResponse<TranshipmentTransferManifestByAWB> printTRM(
			@RequestBody @Validated(value = TranshipmentPrintDropDownValidator.class) TranshipmentTransferManifestByAWB printTagData)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<TranshipmentTransferManifestByAWB> returnOnj = utilitiesModelConfiguration
				.getBaseResponseInstance();
		returnOnj.setData(printTagData);
		printTRMTag(printTagData);
		return returnOnj;

	}

	private void printTRMTag(TranshipmentTransferManifestByAWB request) throws CustomException {

		if (StringUtils.isEmpty(request.getPrinterName())) {
			throw new CustomException("g.selectprinter.m", "printerName", ErrorType.ERROR);
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMMyy");
		DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");
		ReportRequest report = new ReportRequest();
		report.setRequestType(ReportRequestType.PRINT);
		report.setPrinterType(PrinterType.TRANSFER_MANIFEST);
		report.setQueueName(request.getPrinterName());
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("transferringCarrier", request.getCarrierCodeFrom() == null ? EMPTY_STRING
				: awbService.getCarrierNameBasedOnCarrierCode(request.getCarrierCodeFrom()));
		parameters.put("tmNo", request.getTrmNumber() == null ? EMPTY_STRING : request.getTrmNumber());
		parameters.put("airport", request.getTenantAirport() == null ? EMPTY_STRING : request.getTenantAirport());
		parameters.put("date",
				formatter.format(TenantZoneTime.getZoneDateTime(LocalDateTime.now(), request.getTenantId())));
		parameters.put("receivingCarrier", request.getCarrierCodeTo() == null ? EMPTY_STRING
				: awbService.getCarrierNameBasedOnCarrierCode(request.getCarrierCodeTo()));
		parameters.put("userId", request.getCreatedBy() == null ? EMPTY_STRING : request.getCreatedBy());
		if (!ObjectUtils.isEmpty(request.getIssuedDate())) {
			parameters.put("receivetime", timeformatter.format(
					TenantZoneTime.getZoneDateTime(request.getIssuedDate(), request.getTenantId()).toLocalTime()));
		} else {
			parameters.put("receivetime", EMPTY_STRING);
		}
		if (!ObjectUtils.isEmpty(request.getIssuedDate())) {
			parameters.put("receiveDate",
					formatter.format(TenantZoneTime.getZoneDateTime(request.getIssuedDate(), request.getTenantId())));
		} else {
			parameters.put("receiveDate", EMPTY_STRING);
		}
		List<TransferManifestDetails> detailsOfAwb = new ArrayList<TransferManifestDetails>();
		if (!CollectionUtils.isEmpty(request.getAwbInfoList())) {
			for (TranshipmentTransferManifestByAWBInfo listData : request.getAwbInfoList()) {
				TransferManifestDetails printData = new TransferManifestDetails();
				printData.setAwbNo(listData.getShipmentNumber() == null ? EMPTY_STRING : listData.getShipmentNumber());
				printData
						.setAwbDest(listData.getAwbDestination() == null ? EMPTY_STRING : listData.getAwbDestination());
				printData.setNoPackage(String.valueOf(listData.getPieces()) == null ? EMPTY_STRING
						: String.valueOf(listData.getPieces()));
				printData.setWeight(listData.getWeight() == null ? EMPTY_STRING : listData.getWeight().toString());
				printData.setWeightUnitCode(
						listData.getWeightUnitCode() == null ? EMPTY_STRING : listData.getWeightUnitCode());
				printData.setRemarks(listData.getRemarks() == null ? EMPTY_STRING : listData.getRemarks());
				detailsOfAwb.add(printData);
			}
		}
		parameters.put("trmList", detailsOfAwb);
		report.setParameters(parameters);
		if (null != report.getQueueName()) {
			printerService.printReport(report);
		}
	}

}