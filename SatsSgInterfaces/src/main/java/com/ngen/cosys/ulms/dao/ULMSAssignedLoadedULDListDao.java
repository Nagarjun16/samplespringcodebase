package com.ngen.cosys.ulms.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ulms.model.ULD;
import com.ngen.cosys.ulms.model.ULMSInterfaceDetail;
import feign.Param;

import java.util.List;


public interface ULMSAssignedLoadedULDListDao {
    ULMSInterfaceDetail getFlightInfo(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException;

    List<ULD> getULMSExportAssignedULDList(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException;

    List<ULD> getULMSImportAssignedULDList(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException;

    List<ULMSInterfaceDetail> getExportDeleteUldList(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException;

    List<ULMSInterfaceDetail> getExportAddedUldList(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException;

    ULMSInterfaceDetail getExportChangedFlight(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException;

    List<ULMSInterfaceDetail> getImportDeleteUldList(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException;

    List<ULMSInterfaceDetail> getImportAddedUldList(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException;

    int insertUlMSInterfaceDetail(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException;

    int updateUlMSInterfaceDetailVersion(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException;

    int updateULDChangeType(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException;

    int updateULDAssociatedPD(ULMSInterfaceDetail ulmsInterfaceDetail) throws CustomException;

    Long getAdministrationSubGroupDetailCode(@Param("subGroupCode") String subGroupCode) throws CustomException;

    String getAppErrorMessage4ULMS( String errorCode, String local)  throws CustomException;

    Long getUlmsInterfaceLogCount(ULMSInterfaceDetail ulmsInterfaceDetail) throws  CustomException;

    Long getUlmsInterfaceLogCountForFlight(ULMSInterfaceDetail ulmsInterfaceDetail) throws  CustomException;
}
