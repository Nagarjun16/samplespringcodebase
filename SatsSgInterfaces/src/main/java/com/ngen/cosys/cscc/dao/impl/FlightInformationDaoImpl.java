package com.ngen.cosys.cscc.dao.impl;

import com.ngen.cosys.cscc.dao.FlightInformationDao;
import com.ngen.cosys.cscc.modal.FlightInform;
import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FlightInformationDaoImpl extends BaseDAO implements FlightInformationDao {
    @Autowired
    //@Qualifier("sqlSessionTemplate")
    @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
    private SqlSession sqlSessionROI;

    @Override
    public List<FlightInform> getExportFlightInformation(CSCCRequest request) throws CustomException {
        return fetchList("getExportFlightInformation", request.getMessage().getBody(), sqlSessionROI);
    }

    @Override
    public List<FlightInform> getImportFlightInformation(CSCCRequest request) throws CustomException {
        return fetchList("getImportFlightInformation", request.getMessage().getBody(), sqlSessionROI);
    }
}
