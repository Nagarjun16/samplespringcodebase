package com.ngen.cosys.cscc.dao;

import com.ngen.cosys.framework.exception.CustomException;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;

public interface ValidateDao {
    LocalDateTime validateAwbNo(@Param("awbNo") String awbNo) throws CustomException;

    String validateUldNo(@Param("uldNo") String uldNo) throws CustomException;

    LocalDateTime validateFlight(String flightNo,
                          String flightDate,
                          String inOutFlag);

    String getAppErrorMessage(String errorCode, String local)  throws CustomException;

    Long getCSCCDateRangeValue(@Param("subGroupCode") String subGroupCode);

}
