package com.ngen.cosys.cscc.dao.impl;

import com.ngen.cosys.cscc.dao.PilInformationDao;
import com.ngen.cosys.cscc.modal.PilInform;
import com.ngen.cosys.cscc.modal.request.RequestBody;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PilInformationDaoImpl extends BaseDAO implements PilInformationDao {
    @Autowired
    //@Qualifier("sqlSessionTemplate")
    @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
    private SqlSession sqlSessionROI;

    @Override
    public List<PilInform> getImportPilInformation(RequestBody request) throws CustomException {
        return fetchList("getImportPilInformation", request, sqlSessionROI);
    }

    @Override
    public List<PilInform> getExportPilInformation(RequestBody request) throws CustomException {
        return fetchList("getExportPilInformation", request, sqlSessionROI);
    }

    @Override
    public List<PilInform> getImportPilInformationByDateRange(RequestBody request) throws CustomException {
        return fetchList("getImportPilInformationByDateRange", request, sqlSessionROI);
    }
}
