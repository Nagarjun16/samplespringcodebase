package com.ngen.cosys.ulms.constant;

public enum ErrorCode {
    AUTHORITY_FAIL("ulms.AUTHORITY_FAIL.20101","20101"),
    CLIENT_IS_BLANK("ulms.CLIENT_IS_BLANK.20001","20001"),
    FLIGHT_INFORM_MISSED("ulms.FLIGHT_INFORM_MISSED.30002","30002"),
    FLIGHT_NOT_FOUND("FlIGHTDOESNTEXIST","30001"),
    FLIGHT_CANCELLED("flight.is.cancelled","30011"),
    FLIGHT_DEPARTED("flight.is.departed","30012"),
    FLIGHT_BREAKDOWN_COMPLETED("edi.flt.brkdown.completed","30013"),
    DATE_RANGE_MISSED("ulms.DATE_RANGE_MISSED.30003","30003"),
    DATE_FORMAT_INVALID("ulms.DATE_FORMAT_INVALID.30004","30004"),
    ULD_PD_CAN_NOT_BLANK("ulms.ULD_PD_CAN_NOT_BLANK.30005","30005"),
    GENERIC_ERROR("ulms.GENERIC_ERROR.10001","10001"),
    IMPORT_EXPORT_FLAG("ulms.IMPORT_EXPORT_FLAG.30003","30003"),
    JAXB_ERROR("ulms.JAXB_ERROR.10100","10100"),
    USERNAME_PASSWORD_IS_BLANK("ulms.USERNAME_PASSWORD_IS_BLANK.20002","20002"),
    NO_DATA_FOUND("ulms.NO_DATA_FOUND.10002","10002"),
    CUSTOMER_NOT_FOUND("ulms.CUSTOMER_NOT_FOUND.10003","10003"),
    INVALID_CHANGE_TYPE("ulms.INVALID_CHANGE_TYPE.10004","10004"),
    SERVER_INTERNAL_ERROR("ulms.SERVER_INTERNAL_ERROR.99001","99001"),
    INVALID_XML_FORMAT("ulms.INVALID_XML_FORMAT.99002","99002"),
    DATABASE_ERROR("ulms.DATABASE_ERROR.99004","99004"),
    ENDPOINT_NOT_FOUND("ulms.ENDPOINT_NOT_FOUND.99003","99003"),
    ULD_NOT_FOUND_FOR_PD("ulms.ULD_NOT_FOUND_FOR_PD.80001","80001"),
    ;

    private String errorCode;
    private String errorNumber;

    ErrorCode(String errorCode, String errorNumber) {
        this.errorCode = errorCode;
        this.errorNumber = errorNumber;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorNumber() {
        return errorNumber;
    }
}
