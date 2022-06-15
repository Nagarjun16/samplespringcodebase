package com.ngen.cosys.billing.sap.dao;

import java.util.List;

import com.ngen.cosys.billing.sap.model.MaterialInfo;
import com.ngen.cosys.framework.exception.CustomException;

public interface SapMaterialMasterProcessDAO {

   List<MaterialInfo> saveSapMaterialMasterInfo(List<MaterialInfo> materialDataList) throws CustomException;

}
