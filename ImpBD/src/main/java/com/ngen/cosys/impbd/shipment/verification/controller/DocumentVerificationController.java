/** 
 * DocumentVerificationController.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          23 December, 2017   NIIT      -
 */

package com.ngen.cosys.impbd.shipment.verification.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.shipment.verification.model.DgRegulations;
import com.ngen.cosys.impbd.shipment.verification.model.DocumentVerificationFlightModel;
import com.ngen.cosys.impbd.shipment.verification.model.EliElmDGDModel;
import com.ngen.cosys.impbd.shipment.verification.model.EliElmDGDModelList;
import com.ngen.cosys.impbd.shipment.verification.model.SearchDGDeclations;
import com.ngen.cosys.impbd.shipment.verification.model.SearchRegulationDetails;
import com.ngen.cosys.impbd.shipment.verification.model.ShipperDeclaration;
import com.ngen.cosys.impbd.shipment.verification.service.DocumentVerificationService;
import com.ngen.cosys.impbd.shipment.verification.validator.DgDetailsSave;
import com.ngen.cosys.timezone.util.TenantZoneTime;
import com.ngen.cosys.validators.DocumentVerificationValidation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This class is controller for Document verification for in coming flight
 * ,which will take care of Searching for Flight key and FlightOriginDate
 * against the shipment verification
 * 
 * @author NIIT Technologies Ltd
 *
 */

@NgenCosysAppInfraAnnotation
public class DocumentVerificationController {
   
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentVerificationController.class);

	@Autowired
	UtilitiesModelConfiguration utility;

	@Autowired
	DocumentVerificationService documentVerificationService;

//	@Autowired
//	BillingUtilsExpbd billingUtilsExpbd;

	@ApiOperation("Get all incoming flights based on terminal, date, time and carrier group")
	@RequestMapping(value = "/api/impbd/documentVerification/fetch", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<DocumentVerificationFlightModel> searchDocuments(
			 @Validated(value = DocumentVerificationValidation.class) @RequestBody DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		// @SuppressWarnings("unchecked")
		BaseResponse<DocumentVerificationFlightModel> documentVerificationRs = utility.getBaseResponseInstance();
		documentVerificationFlightModel = documentVerificationService.fetch(documentVerificationFlightModel);
		documentVerificationRs.setData(documentVerificationFlightModel);
		return documentVerificationRs;
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION)
	@ApiOperation("Offloading the Shipments based on ShipmentNumber and Shipmentdate")
	@RequestMapping(value = "/api/impbd/documentVerification/offload", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<DocumentVerificationFlightModel> documentsOffload(
			@ApiParam(value = "offload", required = true) @Valid @RequestBody DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		BaseResponse<DocumentVerificationFlightModel> documentVerificationRs = utility.getBaseResponseInstance();
		documentVerificationRs.setData(documentVerificationService.documentsOffload(documentVerificationFlightModel));
		return documentVerificationRs;
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION)
	@ApiOperation("update the Shipmentremarks based on ShipmentNumber and Shipmentdate")
	@RequestMapping(value = "/api/impbd/documentVerification/updateremarks", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<DocumentVerificationFlightModel> documentsOnHold(
			@ApiParam(value = "updateremarks", required = true) @Valid @RequestBody DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		BaseResponse<DocumentVerificationFlightModel> documentVerificationRs = utility.getBaseResponseInstance();
		documentVerificationRs
				.setData(documentVerificationService.updateShipmentRemarks(documentVerificationFlightModel));
		return documentVerificationRs;
	}
	
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION)
	@ApiOperation("onHold the Shipments based on ShipmentNumber and Shipmentdate")
	@RequestMapping(value = "/api/impbd/documentVerification/onhold", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<DocumentVerificationFlightModel> updateShipmentRemarks(
			@ApiParam(value = "offload", required = true) @Valid @RequestBody DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		BaseResponse<DocumentVerificationFlightModel> documentVerificationRs = utility.getBaseResponseInstance();
		documentVerificationRs.setData(documentVerificationService.documentsOnHold(documentVerificationFlightModel));
		return documentVerificationRs;
	}

	@ApiOperation("Document completd after checking the all document recevied")
	@RequestMapping(value = "/api/impbd/documentVerification/documentsComplete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<DocumentVerificationFlightModel> documentsComplete(
			@ApiParam(value = "documentsComplete", required = true) @Valid @RequestBody DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<DocumentVerificationFlightModel> documentVerificationRs = utility.getBaseResponseInstance();
		String tenant = null;

        LocalDateTime startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), documentVerificationFlightModel.getTenantId());
		documentVerificationRs.setData(documentVerificationService.documentComplete(documentVerificationFlightModel));
		LocalDateTime endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),  documentVerificationFlightModel.getTenantId());
        LOGGER.warn("Document Verification Controller - DocumentComplete :: Start Time : {}, End Time : {}", startTime,
              endTime);
        // Billing Time measure
        startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),  documentVerificationFlightModel.getTenantId()); 
		// Bill Calculation		
        // billingUtilsExpbd.calculateBillForAWB(documentVerificationFlightModel);
		endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),  documentVerificationFlightModel.getTenantId());
        LOGGER.warn("Document Verification Controller - DocumentComplete - BILLING API :: Start Time : {}, End Time : {}",
            startTime, endTime);
		return documentVerificationRs;
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION)
	@ApiOperation("updatein the status of the flight delay or not")
	@RequestMapping(value = "/api/impbd/documentVerification/updateFlightDelay", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<DocumentVerificationFlightModel> updateFlightDelay(
			@ApiParam(value = "updateFlightDelay", required = true) @Valid @RequestBody DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		BaseResponse<DocumentVerificationFlightModel> documentVerificationRs = utility.getBaseResponseInstance();
		documentVerificationRs.setData(documentVerificationService.updateFlightDelay(documentVerificationFlightModel));
		return documentVerificationRs;
	}
	
	@ApiOperation("update the status the reOpenDocumentComplete")
	@RequestMapping(value = "/api/impbd/documentVerification/reOpenDocumentComplete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<DocumentVerificationFlightModel> reOpenDocumentComplete(
			@ApiParam(value = "reOpenDocumentComplete", required = true) @Valid @RequestBody DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		BaseResponse<DocumentVerificationFlightModel> documentVerificationRs = utility.getBaseResponseInstance();
		documentVerificationService.reOpenDocumentComplete(documentVerificationFlightModel);
		documentVerificationRs.setData(documentVerificationFlightModel);
		return documentVerificationRs;
	}

	
	@ApiOperation("Document completd after checking the all document recevied")
	@RequestMapping(value = "/api/impbd/documentVerification/saveDocumentVerification", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<DocumentVerificationFlightModel> saveDocumentVerification(
			@ApiParam(value = "documentsComplete", required = true) @Valid @RequestBody DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		BaseResponse<DocumentVerificationFlightModel> documentVerificationRs = utility.getBaseResponseInstance();
	   
	    LocalDateTime startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),  documentVerificationFlightModel.getTenantId());
		documentVerificationRs
				.setData(documentVerificationService.saveDocumentVerification(documentVerificationFlightModel));
		LocalDateTime endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), documentVerificationFlightModel.getTenantId());
		LOGGER.warn("Document Verification Controller - SAVE :: Start Time : {}, End Time : {}", startTime, endTime);
		// Billing Time measure
        startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), documentVerificationFlightModel.getTenantId()); 
		// Bill Calculation
        // billingUtilsExpbd.calculateBillForAWB(documentVerificationFlightModel);
		endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), documentVerificationFlightModel.getTenantId());
        LOGGER.warn("Document Verification Controller - SAVE - BILLING API :: Start Time : {}, End Time : {}", startTime,
            endTime);
		return documentVerificationRs;
	}

	/**
	 * REST api to save or update shippers DG declaration Details
	 * 
	 * @param Shippers
	 *            DG Declaration Details Instruction which have to be inserted or
	 *            updated
	 * @return DG declartions which have successfully inserted or updated
	 * @throws CustomException
	 * @throws SQLException
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION)
	@ApiOperation(value = "save or update shipper DG Declarations Details")
	@RequestMapping(value = "api/impbd/dgd/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<ShipperDeclaration> saveOrUpdateDGDeclarationsDetails(
			@Validated(value = DgDetailsSave.class) @Valid @RequestBody ShipperDeclaration shippersDeclarationDetails) throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<ShipperDeclaration> shippersDeclarationDetailsResponse = utility.getBaseResponseInstance();
		shippersDeclarationDetailsResponse.setData(documentVerificationService.create(shippersDeclarationDetails));
		if (shippersDeclarationDetailsResponse.getData() != null) {
			shippersDeclarationDetailsResponse.setSuccess(true);
		} else {
			shippersDeclarationDetailsResponse.setSuccess(false);
		}
		return shippersDeclarationDetailsResponse;
	}

	/**
	 * This Function will handle request for searching shippers declaration data For
	 * dangerous goods
	 * 
	 * @param search
	 * @return responses
	 * @throws CustomException
	 */
	@ApiOperation(value = "Search DG declaration data against Shipment Number/DGD reference Number ")
	@RequestMapping(value = "/api/impbd/dgd/getDgdDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@SuppressWarnings({ "unchecked" })
	public BaseResponse<List<ShipperDeclaration>> getDgdDetails(
			@ApiParam(value = "Search DGD shippers Declaration Data", required = true, allowMultiple = true) @Valid @RequestBody SearchDGDeclations search)
			throws CustomException {
		BaseResponse<List<ShipperDeclaration>> response = utility.getBaseResponseInstance();
		List<ShipperDeclaration> fetchList = null;
		fetchList = documentVerificationService.find(search);
		response.setData(fetchList);
		response.setSuccess(true);
		return response;
	}

	/**
	 * This Function deletes DGD data
	 *
	 *
	 * @param search
	 * @return responses
	 * @throws CustomException
	 */

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION)
	@ApiOperation(value = "Delete DG Declarations Data")
	@RequestMapping(value = "/api/impbd/dgd/deleteDgdDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@SuppressWarnings({ "unchecked" })
	public BaseResponse<ShipperDeclaration> deleteDgdDetails(
			@ApiParam(value = "Search Shipper address details and consignee address details", required = true, allowMultiple = true) @Valid @RequestBody ShipperDeclaration shipperDeclaration)
			throws CustomException {
		BaseResponse<ShipperDeclaration> response = utility.getBaseResponseInstance();
		ShipperDeclaration fetchList = null;
		fetchList = documentVerificationService.deleteDgdDetails(shipperDeclaration);
		response.setData(fetchList);
		response.setSuccess(true);
		return response;
	}

	/**
	 * Searching for DG Regulations.
	 * 
	 * @param dgd
	 * @return
	 * @throws CustomException
	 */
	@ApiOperation("Searching for DG Regulations by regulation Id")
	@RequestMapping(value = "/api/impbd/dgd/getUnidDetailsByRegId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<List<DgRegulations>> getDgRegulationDetails(@Valid @RequestBody SearchRegulationDetails dgd)
			throws CustomException {
		BaseResponse<List<DgRegulations>> regDetail = utility.getBaseResponseInstance();
		List<DgRegulations> fetchList = documentVerificationService.getDgdRegulationDetails(dgd);
		regDetail.setData(fetchList);
		return regDetail;
	}

	/**
	 * This Function returns Overpack sequence number
	 *
	 *
	 * @param search
	 * @return responses
	 * @throws CustomException
	 */

	@ApiOperation(value = "Returns overpack Sequence number")
	@RequestMapping(value = "/api/impbd/dgd/getOverPackSeqNo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@SuppressWarnings({ "unchecked" })
	public BaseResponse<Integer> getOverPackSeqNo(
			@ApiParam(value = "Returns overpack Sequence number", required = true, allowMultiple = true) @Valid @RequestBody SearchDGDeclations search)
			throws CustomException {
		BaseResponse<Integer> response = utility.getBaseResponseInstance();
		Integer seqNo = 0;
		seqNo = documentVerificationService.getOverPackSeqNo(search);
		response.setData(seqNo);
		response.setSuccess(true);
		return response;
	}

	/**
	 * REST api to save or update shippers DG eli elm Details
	 * 
	 * @param Shippers
	 *            DGD DG Eli Elm Details Instruction which have to be inserted or
	 *            updated
	 * @return DG eli elm which have successfully inserted or updated
	 * @throws CustomException
	 * @throws SQLException
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION)
	@ApiOperation(value = "save or update shipper DG Eli Elm Details")
	@RequestMapping(value = "api/impbd/dgd/saveelielm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<EliElmDGDModel> saveEliElmDGDeclarationsDetails(@Valid @RequestBody EliElmDGDModel elmDGDModel)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<EliElmDGDModel> eliElmResponse = utility.getBaseResponseInstance();
		eliElmResponse.setData(documentVerificationService.createOrSaveEliElmData(elmDGDModel));
		eliElmResponse.setSuccess(true);

		return eliElmResponse;
	}

	/**
	 * REST api to delete shippers DG eli elm Details
	 * 
	 * @param Shippers
	 *            DGD DG Eli Elm Details Instruction which have to be delete
	 * @return DG eli elm which have successfully delete
	 * @throws CustomException
	 * @throws SQLException
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION)
	@ApiOperation(value = "delete shipper DG Eli Elm Details")
	@RequestMapping(value = "api/impbd/dgd/deleteelielm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<EliElmDGDModel> deleteEliElmDGDeclarationsDetails(
			@Valid @RequestBody EliElmDGDModel elmDGDModel) throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<EliElmDGDModel> eliElmResponse = utility.getBaseResponseInstance();
		eliElmResponse.setData(documentVerificationService.deleteEliElmData(elmDGDModel));
		eliElmResponse.setSuccess(true);

		return eliElmResponse;
	}

	/**
	 * REST api to get shippers DG eli elm Details
	 * 
	 * @param Shippers
	 *            DGD DG Eli Elm Details Instruction which have to be fetched
	 * @return DG eli elm which have successfully delete
	 * @throws CustomException
	 * @throws SQLException
	 */
	@ApiOperation(value = "get shipper DG Eli Elm Details")
	@RequestMapping(value = "api/impbd/dgd/getelielm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<EliElmDGDModel> getEliElmDGDeclarationsDetails(@Valid @RequestBody EliElmDGDModel elmDGDModel)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<EliElmDGDModel> eliElmResponse = utility.getBaseResponseInstance();
		eliElmResponse.setData(documentVerificationService.getEliElmData(elmDGDModel));
		eliElmResponse.setSuccess(true);

		return eliElmResponse;
	}

	/**
	 * REST api to get shippers DG eli elm Remark
	 * 
	 * @param Shippers
	 *            DGD DG Eli Elm Remark
	 * @return DG eli elm Remark
	 * @throws CustomException
	 * @throws SQLException
	 */
	@ApiOperation(value = "get shipper DG Eli Elm Remark")
	@RequestMapping(value = "api/impbd/dgd/getelielmremark", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<EliElmDGDModelList> getRemarkForPiAndImpcode(@Valid @RequestBody EliElmDGDModelList elmDGDModel)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<EliElmDGDModelList> eliElmResponse = utility.getBaseResponseInstance();
		eliElmResponse.setData(documentVerificationService.getEliElmRemark(elmDGDModel));
		eliElmResponse.setSuccess(true);

		return eliElmResponse;
	}

}
