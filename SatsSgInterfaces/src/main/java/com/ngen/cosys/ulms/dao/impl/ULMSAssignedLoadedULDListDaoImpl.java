package com.ngen.cosys.ulms.dao.impl;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ulms.dao.ULMSAssignedLoadedULDListDao;
import com.ngen.cosys.ulms.model.ULD;
import com.ngen.cosys.ulms.model.ULMSInterfaceDetail;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class ULMSAssignedLoadedULDListDaoImpl extends BaseDAO implements ULMSAssignedLoadedULDListDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ULMSAssignedLoadedULDListDaoImpl.class);

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSession sqlSession;

    @Override
    public ULMSInterfaceDetail getFlightInfo(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException {
        return super.fetchObject("getFlightInfo", ulmsInterfaceDetail, sqlSession);
    }

    @Override
    public List<ULD> getULMSExportAssignedULDList(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException {
        return super.fetchList("getULMSExportAssignedULDList", ulmsInterfaceDetail, sqlSession);
    }

    @Override
    public List<ULD> getULMSImportAssignedULDList(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException {
        return super.fetchList("getULMSImportAssignedULDList", ulmsInterfaceDetail, sqlSession);
    }

    @Override
    public List<ULMSInterfaceDetail> getExportDeleteUldList(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException {
        return super.fetchList("getExportDeleteUldList", ulmsInterfaceDetail, sqlSession);
    }

    @Override
    public List<ULMSInterfaceDetail> getExportAddedUldList(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException {
        return super.fetchList("getExportAddedUldList", ulmsInterfaceDetail, sqlSession);
    }

    @Override
    public ULMSInterfaceDetail getExportChangedFlight(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException {
        return super.fetchObject("getExportChangedFlight", ulmsInterfaceDetail, sqlSession);
    }

    @Override
    public List<ULMSInterfaceDetail> getImportDeleteUldList(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException {
        return super.fetchList("getImportDeleteUldList", ulmsInterfaceDetail, sqlSession);
    }

    @Override
    public List<ULMSInterfaceDetail> getImportAddedUldList(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException {
        return super.fetchList("getImportAddedUldList", ulmsInterfaceDetail, sqlSession);
    }

    @Override
    public int insertUlMSInterfaceDetail(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException {
        return super.insertData("insertUlMSInterfaceDetail", ulmsInterfaceDetail, sqlSession);
    }

    @Override
    public int updateUlMSInterfaceDetailVersion(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException {
        return super.updateData("updateUlMSInterfaceDetailVersion", ulmsInterfaceDetail, sqlSession);
    }

    @Override
    public int updateULDChangeType(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException {
        return super.updateData("updateULDChangeType", ulmsInterfaceDetail, sqlSession);
    }

    @Override
    public int updateULDAssociatedPD(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException {
        return super.updateData("updateULDAssociatedPD", ulmsInterfaceDetail, sqlSession);
    }

    @Override
    public Long getAdministrationSubGroupDetailCode(String subGroupCode) throws CustomException {
        return super.fetchObject("getAdministrationSubGroupDetailCode", subGroupCode, sqlSession);
    }

    @Override
    public String getAppErrorMessage4ULMS(String errorCode, String local) throws CustomException {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("errorCode", errorCode);
        parameters.put("local", local);

        return super.fetchObject("getAppErrorMessage4ULMS", parameters, sqlSession);
    }

    @Override
    public Long getUlmsInterfaceLogCount(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException {
        return super.fetchObject("getUlmsInterfaceLogCount", ulmsInterfaceDetail, sqlSession);
    }

    @Override
    public Long getUlmsInterfaceLogCountForFlight(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException {
        return super.fetchObject("getUlmsInterfaceLogCountForFlight", ulmsInterfaceDetail, sqlSession);
    }
}
