package com.ngen.cosys.cscc.dao;

import com.ngen.cosys.cscc.modal.FlightInform;
import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import com.ngen.cosys.framework.exception.CustomException;

import java.sql.SQLException;
import java.util.List;

public interface FlightInformationDao {
    List<FlightInform> getExportFlightInformation(CSCCRequest request) throws CustomException;
    List<FlightInform> getImportFlightInformation(CSCCRequest request) throws CustomException;
}