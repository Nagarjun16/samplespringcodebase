package com.ngen.cosys.cscc.dao.impl;

import com.ngen.cosys.cscc.dao.ValidateDao;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class validateDaoImpl extends BaseDAO implements ValidateDao {
    @Autowired
    //@Qualifier("sqlSessionTemplate")
    @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
    private SqlSession sqlSessionROI;

    @Override
    public LocalDateTime validateAwbNo(String awbNo) throws CustomException {
        return fetchObject("validateAwbNo", awbNo, sqlSessionROI);
    }

    @Override
    public String validateUldNo(String uldNo) throws CustomException {
        return fetchObject("validateUldNo", uldNo, sqlSessionROI);
    }

    @Override
    public LocalDateTime validateFlight(String flightNo, String flightDate, String inOutFlag) {
        Map<String, Object> params = new HashedMap();
        params.put("flightNo", flightNo);
        params.put("flightDate", flightDate);
        try {
            return fetchObject("validateFlight", params, sqlSessionROI);
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public String getAppErrorMessage(String errorCode, String local) throws CustomException {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("errorCode", errorCode);
        parameters.put("local", local);

        return super.fetchObject("getAppErrorMessage", parameters, sqlSessionROI);
    }

    public Long getCSCCDateRangeValue(@Param("subGroupCode") String subGroupCode){
        Long dateRange ;
        try {
            dateRange = super.fetchObject("getCSCCDateRangeValue", subGroupCode, sqlSessionROI);
            if(dateRange == null || dateRange<=0L){
                dateRange = 48L;
            }
        }catch (Exception ex){
            dateRange = 48L;
        }
        return dateRange;
    }
}
