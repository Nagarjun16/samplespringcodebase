package com.ngen.cosys.cscc.service.impl;

import com.ngen.cosys.cscc.constant.MessageType;
import com.ngen.cosys.cscc.constant.ResponseStatus;
import com.ngen.cosys.cscc.modal.BaseMessageObject;
import com.ngen.cosys.cscc.modal.ErrorMessage;
import com.ngen.cosys.cscc.modal.Errors;
import com.ngen.cosys.cscc.modal.ResponseModel;
import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import com.ngen.cosys.cscc.service.*;
import com.ngen.cosys.esb.jackson.util.JacksonUtility;
import com.ngen.cosys.framework.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class BuildMessageServiceImpl implements BuildMessageService {
    @Autowired
    KeyShipmentInformationService keyShipmentInformationService;

    @Autowired
    FlightInformationService flightInformationService;

    @Autowired
    UldInformationService uldInformationService;

    @Autowired
    PilInformationService pilInformationService;

    @Autowired
    ValidateService validateService;

    @Autowired
    CSCCLogServiceCallService logServiceCallService;

    @Override
    @Transactional
    public ResponseEntity<Object> buildMessage(MessageType messageType, CSCCRequest request) {
        Errors errors = new Errors(new ArrayList<>());
        Object messageObject = null;
        try {
            validateRequest(request, errors);
            if (errors.getErrors().size() > 0) {
                return buildFailMessage(messageType.getMessageType(), errors, request);
            }
            if (messageType.equals(MessageType.FLIGHTINFO)) {
                messageObject = flightInformationService.buildMessageObject(request);
            } else if (messageType.equals(MessageType.KEYSHIPMENTINFO)) {
                messageObject = keyShipmentInformationService.buildMessageObject(request);
            } else if (messageType.equals(MessageType.PILINFO)) {
                messageObject = pilInformationService.buildMessageObject(request);
            } else if (messageType.equals(MessageType.ULDINFO)) {
                messageObject = uldInformationService.buildMessageObject(request);
            }
            BaseMessageObject bmo = (BaseMessageObject) messageObject;
            if (messageObject == null || (bmo.gerErrorList().size() > 0)) {
                if (messageObject == null) {
                    errors.getErrors().add(new ErrorMessage("999", "99999", "Un-handled exception"));
                } else {
                    if (bmo.gerErrorList().size() > 0) {
                        errors.getErrors().addAll(bmo.gerErrorList());
                    }
                }

                return buildFailMessage(messageType.getMessageType(), errors, request);
            }
            return buildSuccessMessage(messageType.getMessageType(), messageObject, request);
        } catch (CustomException ex) {
            errors.getErrors().add(new ErrorMessage("999", ex.getErrorCode(), ex.getMessage()));
            return buildFailMessage(messageType.getMessageType(), errors, request);
        }

    }

    private ResponseEntity<Object> buildSuccessMessage(String messageSubType,
                                                       Object messageObject, CSCCRequest request) {
        Object message = JacksonUtility.convertObjectToJSONString(messageObject);
        logServiceCallService.logSuccessMessageLog(messageSubType, request, message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    private ResponseEntity<Object> buildFailMessage(String messageSubType,
                                                    Errors errors, CSCCRequest request) {
        ResponseModel responseModel = new ResponseModel();
        StringBuilder errorMessage = new StringBuilder();
        String errorDesc = "";
        Object responseMessage;
        boolean firstError = true;
        responseModel.setStatus(ResponseStatus.FAIL);

        responseModel.setErrorNumber(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        for (ErrorMessage error : errors.getErrors()) {
            if (!error.getErrorNumber().equals(responseModel.getErrorNumber())) {
                responseModel.setErrorNumber(error.getErrorNumber());
            }

            try {
                errorDesc = validateService.getAppErrorMessage(error.getErrorCode(),
                        "en_US", error.getErrorDesc());
            } catch (CustomException e) {
                errorDesc = "";
            }
            if (!firstError) {
                errorMessage.append(";");
            }

            errorMessage.append(errorDesc + " " + error.getErrorDesc());

            firstError = false;
        }
        responseModel.setErrorDescription(errorMessage.toString());

        responseMessage = JacksonUtility.convertObjectToJSONString(responseModel);
        logServiceCallService.logFailMessageLog(messageSubType, request, responseMessage);

        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }

    private void validateRequest(CSCCRequest request, Errors errors) throws CustomException {
        validateService.validateRequest(request, errors);
    }
}