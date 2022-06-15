package com.ngen.cosys.ulms.service.impl;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.exception.SystemException;
import com.ngen.cosys.ulms.constant.ChangeType;
import com.ngen.cosys.ulms.constant.ErrorCode;
import com.ngen.cosys.ulms.constant.ImportExportFlag;
import com.ngen.cosys.ulms.dao.ULMSAssignedLoadedULDListDao;
import com.ngen.cosys.ulms.exception.ServiceFaultException;
import com.ngen.cosys.ulms.model.*;
import com.ngen.cosys.ulms.service.ULMSErrorMessageService;
import com.ngen.cosys.ulms.service.ULMSULDAssignedLoadedListService;
import com.ngen.cosys.ulms.utility.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static com.ngen.cosys.ulms.constant.ImportExportFlag.*;
import static com.ngen.cosys.ulms.constant.ChangeType.*;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ULMSULDAssignedLoadedListServiceImpl implements ULMSULDAssignedLoadedListService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ULMSULDAssignedLoadedListServiceImpl.class);

    private static ObjectFactory objectFactory = new ObjectFactory();


    @Autowired
    ULMSAssignedLoadedULDListDao uldAssignedLoadedInFlightDao;

    @Autowired
    ULMSErrorMessageService errorMessageService;

    private void validateRequest(ULMSInterfaceDetail ulmsInterfaceDetail) {
        if (StringUtils.isEmpty(ulmsInterfaceDetail.getFlightNumber())) {
            throw new ServiceFaultException("FLIGHT-INFORM-MISSED",
                    errorMessageService.buildServiceStatus(ErrorCode.FLIGHT_INFORM_MISSED));
        }

        if (StringUtils.isEmpty(ulmsInterfaceDetail.getFlightDate())) {
            throw new ServiceFaultException("FLIGHT-INFORM-MISSED",
                    errorMessageService.buildServiceStatus(ErrorCode.FLIGHT_INFORM_MISSED));
        }

        if (!ImportExportFlag.isValidFlag(ulmsInterfaceDetail.getImportExportFlag(), true)) {
            throw new ServiceFaultException("IMPORT-EXPORT-FLAG",
                    errorMessageService.buildServiceStatus(ErrorCode.IMPORT_EXPORT_FLAG));
        }
    }

    private void validateFlightStatus(ULMSInterfaceDetail ulmsInterfaceDetail) {
        if (Objects.isNull(ulmsInterfaceDetail)) {
            throw new ServiceFaultException("FLIGHT-NOT-FOUND",
                    errorMessageService.buildServiceStatus(ErrorCode.FLIGHT_NOT_FOUND));
        }
        if (ulmsInterfaceDetail.getFlightCancelFlag() != null)
            if ("D".equals(ulmsInterfaceDetail.getFlightCancelFlag())) {
                throw new ServiceFaultException("FLIGHT-CANCELLED",
                        errorMessageService.buildServiceStatus(ErrorCode.FLIGHT_CANCELLED));
            }
        if ("DEP".equals(ulmsInterfaceDetail.getFlightStatus()) ||
                "DEP".equals(ulmsInterfaceDetail.getOperativeFlightStatus())) {
            throw new ServiceFaultException("FLIGHT-DEPARTED",
                    errorMessageService.buildServiceStatus(ErrorCode.FLIGHT_DEPARTED));
        }
        if ("COM".equals(ulmsInterfaceDetail.getFlightStatus()) ||
                "COM".equals(ulmsInterfaceDetail.getOperativeFlightStatus())) {
            throw new ServiceFaultException("FLIGHT-BREAKDOWN-COMPLETED",
                    errorMessageService.buildServiceStatus(ErrorCode.FLIGHT_BREAKDOWN_COMPLETED));
        }
    }

    @Override
    @Transactional
    public FlightAssignedULDResponse buildFlightAssignedULDResponse(FlightAssignedULDRequest request) throws CustomException {
        ULMSInterfaceDetail ulmsInterfaceDetail = new ULMSInterfaceDetail();
        ulmsInterfaceDetail.setFlightNumber(request.getFlightNumber());
        ulmsInterfaceDetail.setFlightDate(request.getFLightDate());
        ulmsInterfaceDetail.setImportExportFlag(request.getImportExportFlag());

        validateRequest(ulmsInterfaceDetail);

        List<ULD> assignedLoadedULDs = null;
        FlightAssignedULDResponse response = objectFactory.createFlightAssignedULDResponse();

        ulmsInterfaceDetail.setFlightNumber(request.getFlightNumber());
        ulmsInterfaceDetail.setFlightDate(request.getFLightDate());
        ulmsInterfaceDetail.setImportExportFlag(request.getImportExportFlag());

        ulmsInterfaceDetail = uldAssignedLoadedInFlightDao.getFlightInfo(ulmsInterfaceDetail);

        //added new flight status Check
        validateFlightStatus(ulmsInterfaceDetail);
        /*
        if (Objects.isNull(ulmsInterfaceDetail)) {
            throw new ServiceFaultException("FLIGHT-NOT-FOUND",
                    errorMessageService.buildServiceStatus(ErrorCode.FLIGHT_NOT_FOUND));
        }
        */

        ulmsInterfaceDetail.setImportExportFlag(request.getImportExportFlag());
        ulmsInterfaceDetail.setChangeType("N");

        if (EXPORT.getFlag().equals(ulmsInterfaceDetail.getImportExportFlag())) {
            assignedLoadedULDs = this.uldAssignedLoadedInFlightDao.getULMSExportAssignedULDList(ulmsInterfaceDetail);
        } else {
            assignedLoadedULDs = this.uldAssignedLoadedInFlightDao.getULMSImportAssignedULDList(ulmsInterfaceDetail);
        }

        if (Objects.isNull(assignedLoadedULDs) || assignedLoadedULDs.isEmpty()) {
            throw new ServiceFaultException("NO-DATA-FOUND",
                    errorMessageService.buildServiceStatus(ErrorCode.NO_DATA_FOUND));
        }

        response.setFlightNumber(ulmsInterfaceDetail.getFlightNumber());
        response.setFlightDate(ulmsInterfaceDetail.getFlightDate());
        response.setImportExportFlag(ulmsInterfaceDetail.getImportExportFlag());
        if (ulmsInterfaceDetail.getAircraftRegCode() == null) ulmsInterfaceDetail.setAircraftRegCode("");
        response.setACReg(ulmsInterfaceDetail.getAircraftRegCode());

        if (IMPORT.getFlag().equals(request.getImportExportFlag())) {
            response.setSTA(ulmsInterfaceDetail.getDateSta());
            response.setBay(ulmsInterfaceDetail.getArrivalBay());
            response.setSTD("");
            ulmsInterfaceDetail.setDateStd(null);
        } else {
            response.setSTD(ulmsInterfaceDetail.getDateStd());
            response.setBay(ulmsInterfaceDetail.getDepartureBay());
            response.setSTA("");
            ulmsInterfaceDetail.setDateSta(null);
        }

        if (StringUtils.isEmpty(response.getBay())) response.setBay("");

        ULDDetail uldDetail = objectFactory.createULDDetail();

        uldDetail.getULD().addAll(assignedLoadedULDs);

        response.setULDBTList(uldDetail);

        uldAssignedLoadedInFlightDao.updateUlMSInterfaceDetailVersion(ulmsInterfaceDetail);

        for (ULD uld : assignedLoadedULDs) {
            ulmsInterfaceDetail.setUldNumber(uld.getULDNumber());
            ulmsInterfaceDetail.setArrivalBay(uld.getThroughTrasitFlag());
            uldAssignedLoadedInFlightDao.insertUlMSInterfaceDetail(ulmsInterfaceDetail);
        }
        ulmsInterfaceDetail.setUldNumber("X");
        uldAssignedLoadedInFlightDao.insertUlMSInterfaceDetail(ulmsInterfaceDetail);

        return response;
    }

    @Override
    @Transactional
    public FlightULDPDAssociationResponse buildULDPDAssociationResponse(FlightULDPDAssociationRequest request) throws CustomException {
        ULMSInterfaceDetail ulmsInterfaceDetail = new ULMSInterfaceDetail();
        ulmsInterfaceDetail.setFlightNumber(request.getFlightNumber());
        ulmsInterfaceDetail.setFlightDate(request.getFlightDate());
        ulmsInterfaceDetail.setImportExportFlag(request.getImportExportFlag());

        validateRequest(ulmsInterfaceDetail);

        if (StringUtils.isEmpty(request.getAssociatedAssociatedPDList()) ||
                request.getAssociatedAssociatedPDList().getULD().isEmpty()) {
            throw new ServiceFaultException("NO-DATA-FOUND",
                    errorMessageService.buildServiceStatus(ErrorCode.NO_DATA_FOUND));
        }

        ulmsInterfaceDetail = uldAssignedLoadedInFlightDao.getFlightInfo(ulmsInterfaceDetail);

        validateFlightStatus(ulmsInterfaceDetail);

        List<ULD> ULDPDAssociated = request.getAssociatedAssociatedPDList().getULD();
        for (ULD uld : ULDPDAssociated) {
            if (StringUtils.isEmpty(uld.getAssociatedPD())
                    || StringUtils.isEmpty(uld.getULDNumber())) {
                throw new ServiceFaultException("ULD-PD-CAN-NOT-BLANK",
                        errorMessageService.buildServiceStatus(ErrorCode.ULD_PD_CAN_NOT_BLANK));
            }
        }

        int updatedRecord;
        StringBuilder errors = new StringBuilder();
        for (ULD uld : ULDPDAssociated) {
            ulmsInterfaceDetail.setUldNumber(uld.getULDNumber());
            ulmsInterfaceDetail.setAssociatedPd(uld.getAssociatedPD());

            updatedRecord = uldAssignedLoadedInFlightDao.updateULDAssociatedPD(ulmsInterfaceDetail);
            if (updatedRecord == 0) {
                errors.append(uld.getULDNumber() + "->" + uld.getAssociatedPD());
            }
        }

        FlightULDPDAssociationResponse response = objectFactory.createFlightULDPDAssociationResponse();

        if (errors.toString() != null && errors.toString().length() > 0) {
            response.setStatus("1");
            response.setErrorDetails("the pair for " + errors.toString() + " not found in COSYS+");
        } else {
            response.setStatus("0");
            response.setErrorDetails("SUCCESS");
        }
        errors.delete(0, errors.toString().length());
        return response;
    }

    private ULMSInterfaceDetail prepareChangeRequest(FlightAssignedULDChangesRequest request) throws CustomException {
        ULMSInterfaceDetail ulmsInterfaceDetail = new ULMSInterfaceDetail();
        ulmsInterfaceDetail.setFlightNumber(request.getFlightNumber());
        ulmsInterfaceDetail.setFlightDate(request.getFLightDate());
        ulmsInterfaceDetail.setImportExportFlag(request.getImportExportFlag());

        if (StringUtils.hasLength((ulmsInterfaceDetail.getFlightNumber())) ||
                StringUtils.hasLength((ulmsInterfaceDetail.getFlightDate()))) {
            validateRequest(ulmsInterfaceDetail);
            ulmsInterfaceDetail = uldAssignedLoadedInFlightDao.getFlightInfo(ulmsInterfaceDetail);

            //added new flight status Check
            validateFlightStatus(ulmsInterfaceDetail);
        }

        if ((request.getChangeStartDate() == null && StringUtils.hasLength(request.getChangeEndDate())) ||
                (request.getChangeEndDate() == null && StringUtils.hasLength(request.getChangeStartDate()))) {
            throw new ServiceFaultException("DATE-RANGE-MISSED",
                    errorMessageService.buildServiceStatus(ErrorCode.DATE_RANGE_MISSED));
        }

        if (StringUtils.isEmpty(ulmsInterfaceDetail.getImportExportFlag())) {
            ulmsInterfaceDetail.setImportExportFlag("B");
        }

        LocalDateTime startDate = null, endDate = null;

        Long minusHours = 5L;
        try {
            minusHours = uldAssignedLoadedInFlightDao.getAdministrationSubGroupDetailCode("ULDChangeDuration");
        } catch (Exception e) {
            minusHours = 5L;
        }
        if (request.getChangeStartDate() != null && !"".equals(request.getChangeStartDate())) {
            if (request.getChangeStartDate().length() != 13) {
                throw new ServiceFaultException("DATE-FORMAT-INVALID",
                        errorMessageService.buildServiceStatus(ErrorCode.DATE_FORMAT_INVALID));
            }
            request.setChangeStartDate(request.getChangeStartDate() + "00");
        }
        startDate = Utilities.getDBDateTime(request.getChangeStartDate(), LocalDateTime.now().minusHours(minusHours));
        if (startDate == null) {
            throw new ServiceFaultException("DATE-FORMAT-INVALID",
                    errorMessageService.buildServiceStatus(ErrorCode.DATE_FORMAT_INVALID));
        }

        if (request.getChangeEndDate() != null && !"".equals(request.getChangeEndDate())) {
            if (request.getChangeEndDate().length() != 13) {
                throw new ServiceFaultException("DATE-FORMAT-INVALID",
                        errorMessageService.buildServiceStatus(ErrorCode.DATE_FORMAT_INVALID));
            }
            request.setChangeEndDate(request.getChangeEndDate() + "59");
        }
        endDate = Utilities.getDBDateTime(request.getChangeEndDate(), LocalDateTime.now());
        if (endDate == null) {
            throw new ServiceFaultException("DATE-FORMAT-INVALID",
                    errorMessageService.buildServiceStatus(ErrorCode.DATE_FORMAT_INVALID));
        }

        ulmsInterfaceDetail.setStartDate(Utilities.formatToDBDateTime(startDate));
        ulmsInterfaceDetail.setEndDate(Utilities.formatToDBDateTime(endDate));


        String changeType = "A";

        if (StringUtils.isEmpty(request.getChangeType())) {
            changeType = "A";
        } else {
            changeType = request.getChangeType();
        }

        if (ChangeType.isNoneValid(changeType)) {
            throw new ServiceFaultException("INVALID-CHANGE-TYPE",
                    errorMessageService.buildServiceStatus(ErrorCode.INVALID_CHANGE_TYPE));
        }

        ulmsInterfaceDetail.setChangeType(changeType);

        return ulmsInterfaceDetail;
    }

    private void createChangeDetail(
            List<ULMSInterfaceDetail> details,
            ImportExportFlag importExportFlag,
            ChangeType changeType,
            Map<Long, Map<ChangeType, Map<Long, FlightChangeDetail>>> flightChanges
    ) throws CustomException {
        if (Objects.isNull(details) || details.isEmpty()) {
            return;
        }
        Long flightKey = null;
        FlightChangeDetail flightChangeDetail = null;
        Map<ChangeType, Map<Long, FlightChangeDetail>> flightTypeChangeDetail = null;
        Map<Long, FlightChangeDetail> flightKeyChangeDetail = null;
        ULMSInterfaceDetail changed = null;
        ChangeType  localChangeType;

        for (ULMSInterfaceDetail detail : details) {
            if ((changeType == NEW && detail.getNewFlightId() > 0) ||
                    (changeType == DELETE && detail.getNewFlightId() > 0) ||
                    (changeType == UPDATE && (detail.getNewFlightId() == 0 ||
                            detail.getFlightId().compareTo(detail.getNewFlightId()) != 0)) ||
                    (changeType == BOOKINGCHANGED &&
                            (detail.getFlightId().compareTo(detail.getNewFlightId()) == 0 ||
                                    detail.getNewFlightId() == 0))) {
                continue;
            }
            localChangeType = changeType;
            if(changeType == NEW || changeType == UPDATE) localChangeType = NEW;

            flightKey = detail.getNewFlightId() > 0 ? detail.getNewFlightId() : detail.getFlightId();

            flightTypeChangeDetail = flightChanges.get(detail.getFlightId());

            if (Objects.isNull(flightTypeChangeDetail)) {
                flightTypeChangeDetail = new HashMap<>();
                flightKeyChangeDetail = new HashMap<>();
                flightChangeDetail = objectFactory.createFlightChangeDetail();
                flightKeyChangeDetail.put(flightKey, flightChangeDetail);
                flightTypeChangeDetail.put(localChangeType, flightKeyChangeDetail);
                flightChanges.put(detail.getFlightId(), flightTypeChangeDetail);
            }

            flightKeyChangeDetail = flightTypeChangeDetail.get(localChangeType);

            if (Objects.isNull(flightKeyChangeDetail)) {
                flightKeyChangeDetail = new HashMap<>();
                flightChangeDetail = objectFactory.createFlightChangeDetail();
                flightKeyChangeDetail.put(flightKey, flightChangeDetail);
                flightTypeChangeDetail.put(localChangeType, flightKeyChangeDetail);
            }

            flightChangeDetail = flightKeyChangeDetail.get(flightKey);

            if (Objects.isNull(flightChangeDetail)) {
                flightChangeDetail = objectFactory.createFlightChangeDetail();
                flightKeyChangeDetail.put(flightKey, flightChangeDetail);
            }

            changed = null;
            if (detail.getFlightId() == detail.getNewFlightId()) {
                changed = detail;
            } else {
                if (detail.getNewFlightId() > 0) {
                    changed = uldAssignedLoadedInFlightDao.getExportChangedFlight(detail);
                }
            }

            flightChangeDetail.setOldFlightNumber(detail.getFlightNumber());
            flightChangeDetail.setOldFLightDate(detail.getFlightDate());

            if (NEW == changeType) {
                flightChangeDetail.setNewFLightNumber(detail.getFlightNumber());
                flightChangeDetail.setNewFLightDate(detail.getFlightDate());
            } else {
                flightChangeDetail.setNewFLightNumber("");
                flightChangeDetail.setNewFLightDate("");
            }

            flightChangeDetail.setImportExportFlag(importExportFlag.getFlag());
            if (detail.getAircraftRegCode() == null) detail.setAircraftRegCode("");
            flightChangeDetail.setACReg(detail.getAircraftRegCode());
            if (EXPORT.equals(importExportFlag)) {
                flightChangeDetail.setSTA("");
                flightChangeDetail.setSTD(detail.getDateStd());
                flightChangeDetail.setBay(detail.getDepartureBay());
            } else {
                flightChangeDetail.setSTD("");
                flightChangeDetail.setSTA(detail.getDateSta());
                flightChangeDetail.setBay(detail.getArrivalBay());
            }

            if (flightChangeDetail.getBay() == null) {
                flightChangeDetail.setBay("");
            }

            ULDDetail uldDetail = flightChangeDetail.getChangedULDBTList();
            if (uldDetail == null) {
                uldDetail = objectFactory.createULDDetail();
            }

            ULD uld = objectFactory.createULD();
            uld.setULDNumber(detail.getUldNumber());
            if("E".equals(detail.getImportExportFlag())) {
                uld.setThroughTrasitFlag(detail.getThroughTransitFlag());
            }else{
                uld.setThroughTrasitFlag("0");
            }

            if (NEW == changeType) {
                uld.setStatus("N");
            } else if (UPDATE == changeType) {
                uld.setStatus("D");
            } else {
                uld.setStatus(null);
            }
            if (changed != null) {
                flightChangeDetail.setNewFLightNumber(changed.getFlightNumber());
                flightChangeDetail.setNewFLightDate(changed.getFlightDate());
                if (changed.getAircraftRegCode() == null) changed.setAircraftRegCode("");
                flightChangeDetail.setACReg(changed.getAircraftRegCode());
                flightChangeDetail.setSTD(changed.getDateStd());
                flightChangeDetail.setBay(changed.getDepartureBay());

                uld.setStatus(null);
                if(detail.getFlightId().compareTo(detail.getNewFlightId())==0){
                    uld.setStatus("U");
                }
            }
            uldDetail.getULD().add(uld);
            flightChangeDetail.setChangedULDBTList(uldDetail);
        }
    }

    private void prepareDeletedChangedFlight(ULMSInterfaceDetail detail, Map<Long, Map<ChangeType, Map<Long, FlightChangeDetail>>> flightChanges) throws CustomException {
        if (ChangeType.isNoneValid(detail.getChangeType())) {
            return;
        }
        if (ImportExportFlag.isImport(detail.getImportExportFlag())) {
            List<ULMSInterfaceDetail> importDeleteDetails = uldAssignedLoadedInFlightDao.getImportDeleteUldList(detail);
            if (detail.getChangeType().equals(ALL.getChangeType())) {
                createChangeDetail(importDeleteDetails, IMPORT, DELETE, flightChanges);
                createChangeDetail(importDeleteDetails, IMPORT, UPDATE, flightChanges);
            }else {
                createChangeDetail(importDeleteDetails, IMPORT,
                        getChangeTypeByValue(detail.getChangeType()), flightChanges);
            }
        }

        if (ImportExportFlag.isExport(detail.getImportExportFlag())) {
            List<ULMSInterfaceDetail> exportDeleteDetail = uldAssignedLoadedInFlightDao.getExportDeleteUldList(detail);

            if (detail.getChangeType().equals(ALL.getChangeType())) {
                createChangeDetail(exportDeleteDetail, EXPORT, DELETE, flightChanges);
                createChangeDetail(exportDeleteDetail, EXPORT, UPDATE, flightChanges);
                createChangeDetail(exportDeleteDetail, EXPORT, BOOKINGCHANGED, flightChanges);
            } else {
                createChangeDetail(exportDeleteDetail, EXPORT,
                        getChangeTypeByValue(detail.getChangeType()), flightChanges);
            }
        }
    }

    private void prepareAddedFlight(ULMSInterfaceDetail detail, Map<Long, Map<ChangeType, Map<Long, FlightChangeDetail>>> flightChanges) throws CustomException {
        if (!ChangeType.isNew(detail.getChangeType())) {
            return;
        }
        if (ImportExportFlag.isImport(detail.getImportExportFlag())) {
            List<ULMSInterfaceDetail> importAddedUld = uldAssignedLoadedInFlightDao.getImportAddedUldList(detail);
            createChangeDetail(importAddedUld, IMPORT, NEW, flightChanges);
        }

        if (ImportExportFlag.isExport(detail.getImportExportFlag())) {
            List<ULMSInterfaceDetail> exportAddedUld = uldAssignedLoadedInFlightDao.getExportAddedUldList(detail);
            createChangeDetail(exportAddedUld, EXPORT, NEW, flightChanges);
        }
    }

    @Override
    @Transactional
    public FlightAssignedULDChangesResponse buildFlightAssignedULDChangesResponse(FlightAssignedULDChangesRequest request) throws CustomException {
        Map<Long, Map<ChangeType, Map<Long, FlightChangeDetail>>> flightChanges = new HashMap<>();

        ULMSInterfaceDetail ulmsInterfaceDetail = prepareChangeRequest(request);

        if (ChangeType.isChanged(ulmsInterfaceDetail.getChangeType())) {
            prepareDeletedChangedFlight(ulmsInterfaceDetail, flightChanges);
        }

        if (ChangeType.isNew(ulmsInterfaceDetail.getChangeType())) {
            prepareAddedFlight(ulmsInterfaceDetail, flightChanges);
        }

        FlightAssignedULDChangesResponse response = objectFactory.createFlightAssignedULDChangesResponse();

        if (flightChanges.isEmpty()) {
            throw new ServiceFaultException("NO-DATA-FOUND",
                    errorMessageService.buildServiceStatus(ErrorCode.NO_DATA_FOUND));
        }

        updateInsertInterfaceLog(flightChanges, response);

        return response;
    }

    private void updateInsertInterfaceLog(Map<Long, Map<ChangeType, Map<Long, FlightChangeDetail>>> flightChanges,
                                          FlightAssignedULDChangesResponse response) throws CustomException {
        ULMSInterfaceDetail ulmsInterfaceDetail = new ULMSInterfaceDetail();
        int numberOfUld;
        Long flightChangeCount, newFlightChangeCount;
        for (Map.Entry<Long, Map<ChangeType, Map<Long, FlightChangeDetail>>> flightEntry : flightChanges.entrySet()) {
            for (Map.Entry<ChangeType, Map<Long, FlightChangeDetail>> changeTypeEntry : flightEntry.getValue().entrySet()) {
                for (Map.Entry<Long, FlightChangeDetail> changeEntry : changeTypeEntry.getValue().entrySet()) {
                    ulmsInterfaceDetail.setChangeType(changeTypeEntry.getKey().getChangeType());
                    ulmsInterfaceDetail.setFlightId(flightEntry.getKey());
                    ulmsInterfaceDetail.setNewFlightId(changeEntry.getKey());
                    ulmsInterfaceDetail.setImportExportFlag(changeEntry.getValue().getImportExportFlag());
                    ulmsInterfaceDetail.setDateSta(changeEntry.getValue().getSTA());
                    ulmsInterfaceDetail.setDateStd(changeEntry.getValue().getSTD());

                    if (changeEntry.getValue().getACReg() == null) changeEntry.getValue().setACReg("");
                    ulmsInterfaceDetail.setAircraftRegCode(changeEntry.getValue().getACReg());

                    if (IMPORT.getFlag().equals(ulmsInterfaceDetail.getImportExportFlag())) {
                        ulmsInterfaceDetail.setArrivalBay(changeEntry.getValue().getBay());
                    } else {
                        ulmsInterfaceDetail.setDepartureBay(changeEntry.getValue().getBay());
                    }

                    numberOfUld = changeEntry.getValue().getChangedULDBTList().getULD().size();

                    for (int i = numberOfUld - 1; i >= 0; i--) {
                        ULD uld = changeEntry.getValue().getChangedULDBTList().getULD().get(i);

                        ulmsInterfaceDetail.setUldNumber(uld.getULDNumber());

                        if("E".equals(changeEntry.getValue().getImportExportFlag())){
                            ulmsInterfaceDetail.setArrivalBay(uld.getThroughTrasitFlag());
                        }

                        if (changeTypeEntry.getKey() == BOOKINGCHANGED) {
                            ulmsInterfaceDetail.setChangeType(DELETE.getChangeType());
                        } else {
                            ulmsInterfaceDetail.setChangeType(changeTypeEntry.getKey().getChangeType());
                        }

                        if (uld.getULDNumber().equals("X")) {
                            changeEntry.getValue().getChangedULDBTList().getULD().remove(i);
                        }

                        flightChangeCount = 0L;
                        newFlightChangeCount = 0L;

                        flightChangeCount = uldAssignedLoadedInFlightDao.getUlmsInterfaceLogCount(ulmsInterfaceDetail);

                        if (flightChangeCount == 0) {
                            uldAssignedLoadedInFlightDao.insertUlMSInterfaceDetail(ulmsInterfaceDetail);
                        } else {
                            if (ulmsInterfaceDetail.getFlightId() != ulmsInterfaceDetail.getNewFlightId()) {
                                ulmsInterfaceDetail.setChangeType(DELETE.getChangeType());
                            }
                            uldAssignedLoadedInFlightDao.updateULDChangeType(ulmsInterfaceDetail);

                            if (ulmsInterfaceDetail.getFlightId() != ulmsInterfaceDetail.getNewFlightId()) {
                                ulmsInterfaceDetail.setFlightId(ulmsInterfaceDetail.getNewFlightId());
                                newFlightChangeCount = uldAssignedLoadedInFlightDao.getUlmsInterfaceLogCount(ulmsInterfaceDetail);
                                if (newFlightChangeCount > 0) {
                                    ulmsInterfaceDetail.setChangeType(UPDATE.getChangeType());
                                    uldAssignedLoadedInFlightDao.updateULDChangeType(ulmsInterfaceDetail);
                                } else {
                                    newFlightChangeCount = uldAssignedLoadedInFlightDao.getUlmsInterfaceLogCountForFlight(ulmsInterfaceDetail);
                                    if (newFlightChangeCount > 0) {
                                        ulmsInterfaceDetail.setChangeType(NEW.getChangeType());
                                        uldAssignedLoadedInFlightDao.insertUlMSInterfaceDetail(ulmsInterfaceDetail);
                                    }
                                }
                            }
                        }
                    }

                    if (UPDATE == changeTypeEntry.getKey() && changeTypeEntry.getValue().size() > 1 &&
                            changeEntry.getValue().getChangedULDBTList().getULD().size() == 0) {
                        continue;
                    }

                    response.getFlightDetail().add(changeEntry.getValue());
                }
            }
        }
    }
}
