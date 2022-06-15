package com.ngen.cosys.service.volumetricscanner.service;

import java.math.BigInteger;

import org.springframework.http.ResponseEntity;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.service.volumetricscanner.model.CancelScanVolWgtRequest;
import com.ngen.cosys.service.volumetricscanner.model.CancelScanVolWgtResponse;
import com.ngen.cosys.service.volumetricscanner.model.ScanVolWgtRequest;
import com.ngen.cosys.service.volumetricscanner.model.ScanVolWgtResponse;
import com.ngen.cosys.service.volumetricscanner.model.UpdateVolWgtResponse;
import com.ngen.cosys.service.volumetricscanner.model.VolumetricRequest;
import com.ngen.cosys.service.volumetricscanner.model.VolumetricResponse;

public interface VolumetricScannerService {

	ResponseEntity<CancelScanVolWgtRequest> cancelScanVolWgtReq(CancelScanVolWgtRequest payload) throws CustomException;

	@Deprecated
	ResponseEntity<ScanVolWgtRequest> scanVolWgtReq(ScanVolWgtRequest payload) throws CustomException;

	
	ResponseEntity<String> updateVolWgtReqLog(String payload) throws CustomException;

	ResponseEntity<UpdateVolWgtResponse> updateVolWgtResLog(UpdateVolWgtResponse payload) throws CustomException;

	ResponseEntity<CancelScanVolWgtResponse> cancelScanVolWgtRes(String payload) throws CustomException;

	ResponseEntity<ScanVolWgtResponse> scanVolWgtRes(String payload) throws CustomException;

	BigInteger getVolumetricScannerReferenceLogId(String shipmentNumber) throws CustomException;

	String updateVolumetricResponse(long messageId, boolean success) throws CustomException;

	ResponseEntity<VolumetricResponse> saveVolumetricScanRequest(VolumetricRequest volumetricRequest)
			throws CustomException;

}
