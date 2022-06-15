package com.ngen.cosys.service.volumetricscanner.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.service.volumetricscanner.model.CancelScanVolWgtRequest;
import com.ngen.cosys.service.volumetricscanner.model.CancelScanVolWgtResponse;
import com.ngen.cosys.service.volumetricscanner.model.CargoEyeVolWeightErrorResponse;
import com.ngen.cosys.service.volumetricscanner.model.CargoEyeVolWeightImageResponse;
import com.ngen.cosys.service.volumetricscanner.model.CargoEyeVolWeightResponse;
import com.ngen.cosys.service.volumetricscanner.model.ScanVolWgtRequest;
import com.ngen.cosys.service.volumetricscanner.model.ScanVolWgtResponse;
import com.ngen.cosys.service.volumetricscanner.model.ScannedImage;
import com.ngen.cosys.service.volumetricscanner.model.UpdateVolWgtRequest;
import com.ngen.cosys.service.volumetricscanner.model.UpdateVolWgtResponse;
import com.ngen.cosys.service.volumetricscanner.model.VolumetricRequest;

public interface VolumetricScannerDAO {

  @Deprecated
  public  void scanVolWgtReqLog(ScanVolWgtRequest payload) throws CustomException;

public void scanVolWgtResLog(ScanVolWgtResponse payload)throws CustomException;
public void cancelScanVolWgtRes(CancelScanVolWgtResponse payload)throws CustomException;

public void cancelScanVolWgtReq(CancelScanVolWgtRequest payload) throws CustomException;

public void updateVolWgtReqLog(UpdateVolWgtRequest payload) throws CustomException;

public void updateVolWgtResLog(UpdateVolWgtResponse payload) throws CustomException;

BigInteger getVolumetricScannerReferenceLogId(String shipmentNumber) throws CustomException;

void saveVolumetricScanRequest(VolumetricRequest volumetricRequest) throws CustomException;

void saveVolumetricScanResponse(CargoEyeVolWeightResponse volumetricScanResponse) throws CustomException;

void saveVolumetricScanErrorResponse(CargoEyeVolWeightErrorResponse volumetricScanErrorResponse) throws CustomException;

void saveVolumetricScanImageResponse(ArrayList<ScannedImage> images) throws CustomException;

HashMap<String, String> getCargoEyeConfigurations() throws CustomException;


void deletePreviousVolumeWeightLogData(long previousMessageId) throws CustomException;

}
