package com.ngen.cosys.cscc.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ngen.cosys.cscc.constant.ImportExport;
import com.ngen.cosys.cscc.dao.ValidateDao;
import com.ngen.cosys.cscc.modal.ErrorMessage;
import com.ngen.cosys.cscc.modal.Errors;
import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import com.ngen.cosys.cscc.modal.request.RequestBody;
import com.ngen.cosys.cscc.service.ValidateService;
import com.ngen.cosys.cscc.utils.Utils;
import com.ngen.cosys.framework.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

@Service
public class ValidateServiceImpl implements ValidateService {
    @Autowired
    ValidateDao validateDao;

    @Override
    public LocalDateTime validateFlight(String flightNo, String flightDate, String inOutFlag) throws CustomException {
        LocalDateTime retValue = validateDao.validateFlight(flightNo, flightDate, inOutFlag);
        if (retValue == null) {
            return null;
        }
        return retValue;
    }

    @Override
    public LocalDateTime validateAwbNo(String awbNo) throws CustomException {
        if (Utils.isNullOrBlank(awbNo)) {
            return null;
        }
        boolean awbIsValidate = Utils.validatedAWB(awbNo);

        if (!awbIsValidate) {
            return null;
        }
        LocalDateTime retValue = validateDao.validateAwbNo(awbNo);

        if (retValue == null) {
            return null;
        }
        return retValue;
    }

    @Override
    public boolean validateULDNo(String uldNo) throws CustomException {
        if (Utils.isNullOrBlank(uldNo)) {
            return true;
        }

        String retValue = validateDao.validateUldNo(uldNo);

        if (retValue == null || !"Y".equals(retValue)) {
            return false;
        }
        return true;
    }

    private void validateFlight(RequestBody request, Errors errors) {
        if (Utils.isNullOrBlank(request.getFlightNo()) && Utils.isNullOrBlank(request.getFlightDate())) {
            return;
        }
        if ((!Utils.isNullOrBlank(request.getFlightNo()) &&
                Utils.isNullOrBlank(request.getFlightDate())) ||
                (Utils.isNullOrBlank(request.getFlightNo()) &&
                        !Utils.isNullOrBlank(request.getFlightDate()))) {
            errors.getErrors().add(new ErrorMessage("400", "ulms.FLIGHT_INFORM_MISSED.30002",
                    "(" + Utils.isnull(request.getFlightNo(), "?")
                            + "/" + Utils.isnull(request.getFlightDate(), "?") + ")"));
            return;
        }


        LocalDate flightDate = Utils.convert2Date(request.getFlightDate(), Utils.DATE_FORMAT);

        if (flightDate == null) {
            errors.getErrors().add(new ErrorMessage("400", "INVALID",
                    " flightDate is invalid(" + request.getFlightDate() + ")"));
            return;
        }

        request.setFlightLocalDate(Utils.formatDate(flightDate, Utils.DB_DATE_FORMAT));

        LocalDateTime flightOriginDate = validateDao.validateFlight(request.getFlightNo(),
                request.getFlightLocalDate(), request.getImportExport());

        if (flightOriginDate == null) {
            errors.getErrors().add(new ErrorMessage("400", "INFLT001",
                    " (" + request.getFlightNo() + "/" + request.getFlightDate() + ")"));
            return;
        }
        request.setFlightOriginDate(flightOriginDate);
    }

    private void validateDateRange(RequestBody request, Errors errors) {
        LocalDateTime fromTime, toTime;
        fromTime = Utils.convert2DateTime(request.getFromTime());
        toTime = Utils.convert2DateTime(request.getToTime());

        if (!Utils.isNullOrBlank(request.getFromTime()) && fromTime == null) {
            errors.getErrors().add(new ErrorMessage("400", "INVALID",
                    " fromTime is invalid(" + request.getFromTime() + ")"));
        }

        if (!Utils.isNullOrBlank(request.getToTime()) && toTime == null) {
            errors.getErrors().add(new ErrorMessage("400", "INVALID",
                    " toTime is invalid(" + request.getToTime() + ")"));
        }

        if (errors.getErrors().size() > 0) {
            return;
        }

        if (fromTime != null) {
            request.setDbFromTime(Utils.formatDateTime(fromTime,
                    Utils.DB_DATE_TIME_FORMAT_NO_SECOND) + ":00");
        }

        if (toTime != null) {
            request.setDbToTime(Utils.formatDateTime(toTime,
                    Utils.DB_DATE_TIME_FORMAT_NO_SECOND) + ":59");
        }

        if (fromTime != null && toTime != null) {
            Long dateRangeConfig = validateDao.getCSCCDateRangeValue("dateRangeValue");
            dateRangeConfig = dateRangeConfig * 60;
            long dateRangeActual = ChronoUnit.MINUTES.between(fromTime, toTime);
            if (dateRangeActual > dateRangeConfig) {
                errors.getErrors().add(new ErrorMessage("400", "INVALID",
                        " Time range is beyond configure value(" + dateRangeConfig / 60 + " hours)"));
            }
        }
    }

    private void validateAwbNo(RequestBody request, Errors errors) throws CustomException {
        if (!Utils.isNullOrBlank(request.getAwbNo())) {
            LocalDateTime shipmentDate = validateAwbNo(request.getAwbNo());
            if (shipmentDate == null) {
                errors.getErrors().add(new ErrorMessage("400", "data.awb.number.invalid",
                        "(" + request.getAwbNo() + ")"));
            } else {
                request.setShipmentDate(shipmentDate);
            }
        }
    }

    private void validateUldNo(RequestBody request, Errors errors) throws CustomException {
        if (!Utils.isNullOrBlank(request.getUldNo())) {
            if (!Utils.validatedULD(request.getUldNo()) || !validateULDNo(request.getUldNo()))
                errors.getErrors().add(new ErrorMessage("400", "ULDMODEL02",
                        "(" + request.getUldNo() + ")"));
        }
    }

    private void validateImportExportFlag(RequestBody request, Errors errors) {
        if (Utils.isNullOrBlank(request.getImportExport())) {
            errors.getErrors().add(new ErrorMessage("400", "ULDMODEL02",
                    "(ImportExport Flag can not be Null or blank)"));
            return;
        }

        request.setImportExport(request.getImportExport().toUpperCase());

        if (!ImportExport.isValid(request.getImportExport())) {
            errors.getErrors().add(new ErrorMessage("400", "INVALID",
                    " importExport value can be (I,E),but actual value is "
                            + request.getImportExport()));
        }
    }

    private boolean valueRequired(RequestBody body, Errors errors) {
        if (Utils.isNullOrBlank(body.getAwbNo()) &&
                Utils.isNullOrBlank(body.getAwbNo()) &&
                Utils.isNullOrBlank(body.getUldNo()) &&
                Utils.isNullOrBlank(body.getFlightNo()) &&
                Utils.isNullOrBlank(body.getFlightDate()) &&
                Utils.isNullOrBlank(body.getFromTime()) &&
                Utils.isNullOrBlank(body.getToTime()) &&
                Utils.isNullOrBlank(body.getImportExport())) {
            errors.getErrors().add(new ErrorMessage("400", "INVALID",
                    " At lease one(or one pair) value(s) should be provided!"));
            return  false;
        }
        return true;
    }

    @Override
    public void validateRequest(CSCCRequest request, Errors errors) throws CustomException {
        if (!valueRequired(request.getMessage().getBody(), errors)){
            return;
        }
        validateImportExportFlag(request.getMessage().getBody(), errors);
        validateDateRange(request.getMessage().getBody(), errors);
        validateFlight(request.getMessage().getBody(), errors);
        validateUldNo(request.getMessage().getBody(), errors);
        validateAwbNo(request.getMessage().getBody(), errors);
    }

    @Override
    public String getAppErrorMessage(String errorCode, String local, String defaultValue) throws CustomException {
        String messageDesc = validateDao.getAppErrorMessage(errorCode, local);

        if (messageDesc == null || "".equals(messageDesc)) {
            return defaultValue;
        }
        return messageDesc;
    }
}
