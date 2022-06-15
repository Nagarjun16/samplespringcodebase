package com.ngen.cosys.cscc.dao;

import com.ngen.cosys.cscc.modal.PilInform;
import com.ngen.cosys.cscc.modal.request.RequestBody;
import com.ngen.cosys.framework.exception.CustomException;

import java.util.List;

public interface PilInformationDao {
    List<PilInform> getImportPilInformation(RequestBody requestBody) throws CustomException;
    List<PilInform> getExportPilInformation(RequestBody requestBody) throws CustomException;
    List<PilInform> getImportPilInformationByDateRange(RequestBody requestBody) throws CustomException;

}
