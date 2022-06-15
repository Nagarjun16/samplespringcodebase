package com.ngen.cosys.cscc.controller;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.cscc.constant.MessageType;
import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import com.ngen.cosys.cscc.service.BuildMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@NgenCosysAppInfraAnnotation(path = "/api/cscc")
public class CSCCController {
    @Autowired
    BuildMessageService buildMessageService;

    @PostMapping(path = "/v1/postFlightInfo",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postFlightInfo(@RequestBody CSCCRequest csccRequest, HttpServletRequest request) {
        return buildMessageService.buildMessage(MessageType.FLIGHTINFO, csccRequest);
    }

    @PostMapping(path = "/v1/postKeyShipmentInfo",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postKeyShipmentInfo(@RequestBody  CSCCRequest csccRequest, HttpServletRequest request) {
        return buildMessageService.buildMessage(MessageType.KEYSHIPMENTINFO, csccRequest);
    }

    @PostMapping(path = "/v1/postPilInfo",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postPilInfo(@RequestBody  CSCCRequest csccRequest, HttpServletRequest request) {
        return buildMessageService.buildMessage(MessageType.PILINFO, csccRequest);
    }

    @PostMapping(path = "/v1/postULDInfo",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postULDInfo(@RequestBody  CSCCRequest csccRequest, HttpServletRequest request) {
        return buildMessageService.buildMessage(MessageType.ULDINFO, csccRequest);
    }
}
