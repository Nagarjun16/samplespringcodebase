package com.ngen.cosys.cscc.dao;

import com.ngen.cosys.cscc.modal.UldInform;
import com.ngen.cosys.cscc.modal.request.RequestBody;
import com.ngen.cosys.framework.exception.CustomException;

import java.util.List;

public interface UldInformationDao {
    List<UldInform> getUldExportInformation(RequestBody requestBody) throws CustomException;
    List<UldInform> getUldImportInformation(RequestBody requestBody) throws CustomException;
}