package com.ngen.cosys.cscc.dao.impl;

import com.ngen.cosys.cscc.dao.CargoInformationDao;
import com.ngen.cosys.cscc.modal.ShipmentInform;
import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CargoInformationDaoImpl extends BaseDAO implements CargoInformationDao {
    @Autowired
    //@Qualifier("sqlSessionTemplate")
    @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
    private SqlSession sqlSessionROI;

    @Override
    public List<ShipmentInform> getExportCargoInformation(CSCCRequest request) throws CustomException {
        return fetchList("getExportCargoInformation", request.getMessage().getBody(), sqlSessionROI);
    }

    @Override
    public List<ShipmentInform> getImportCargoInformation(CSCCRequest request) throws CustomException {
        return fetchList("getImportCargoInformation", request.getMessage().getBody(), sqlSessionROI);
    }
}
