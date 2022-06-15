package com.ngen.cosys.cscc.dao.impl;

import com.ngen.cosys.cscc.dao.UldInformationDao;
import com.ngen.cosys.cscc.modal.UldInform;
import com.ngen.cosys.cscc.modal.request.CSCCRequest;
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
public class UldInformationDaoImpl extends BaseDAO implements UldInformationDao {

    @Autowired
    //@Qualifier("sqlSessionTemplate")
    @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
    private SqlSession sqlSessionROI;

    @Override
    public List<UldInform> getUldExportInformation(RequestBody request) throws CustomException {
        return fetchList("getUldExportInformation", request, sqlSessionROI);
    }

    @Override
    public List<UldInform> getUldImportInformation(RequestBody request) throws CustomException {
        return fetchList("getUldImportInformation", request, sqlSessionROI);
    }
}
