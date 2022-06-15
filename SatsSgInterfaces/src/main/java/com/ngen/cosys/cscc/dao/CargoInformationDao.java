package com.ngen.cosys.cscc.dao;

import com.ngen.cosys.cscc.modal.ShipmentInform;
import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import com.ngen.cosys.framework.exception.CustomException;

import java.util.List;

public interface CargoInformationDao {
    List<ShipmentInform> getExportCargoInformation(CSCCRequest request) throws CustomException;
    List<ShipmentInform> getImportCargoInformation(CSCCRequest request) throws CustomException;
}