package com.ngen.cosys.ulms.endpoint;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ulms.constant.ErrorCode;
import com.ngen.cosys.ulms.exception.ServiceFaultException;
import com.ngen.cosys.ulms.service.ULMSErrorMessageService;
import com.ngen.cosys.ulms.service.ULMSULDAssignedLoadedListService;
import com.ngen.cosys.ulms.service.ULMSAuthorityService;
import com.ngen.cosys.ulms.service.UldReleaseToAgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.ngen.cosys.ulms.model.*;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.server.endpoint.annotation.SoapHeader;

@Endpoint
public class UldAssociationEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(UldAssociationEndpoint.class);
    private static final String NAMESPACE_URI = "http://www.sats.com.sg/uldAssociationWebservice";
    private static final String HEADER_NAMESPACE_URI = "{" + NAMESPACE_URI + "}AuthenticationHeader";

    @Autowired
    ULMSULDAssignedLoadedListService ulmsuldAssignedLoadedListService;

    @Autowired
    UldReleaseToAgentService uldReleaseToAgentService;

    @Autowired
    ULMSAuthorityService ulmsAuthorityService;

    @Autowired
    ULMSErrorMessageService  errorMessageService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FlightAssignedULDRequest")
    @ResponsePayload
    public FlightAssignedULDResponse flightAssignedULD(@RequestPayload FlightAssignedULDRequest request,
                                                       @SoapHeader(value = HEADER_NAMESPACE_URI) SoapHeaderElement soapHeaderElement) {
        try {
            ulmsAuthorityService.authorityCaller(soapHeaderElement);
            return ulmsuldAssignedLoadedListService.buildFlightAssignedULDResponse(request);
        } catch (CustomException e) {
            throw new ServiceFaultException("DATABASE-ERROR",
                    errorMessageService.buildServiceStatus(ErrorCode.DATABASE_ERROR));
        }

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FlightULDPDAssociationRequest")
    @ResponsePayload
    public FlightULDPDAssociationResponse flightULDPDAssociation(@RequestPayload FlightULDPDAssociationRequest request,
                                                                 @SoapHeader(value = HEADER_NAMESPACE_URI) SoapHeaderElement soapHeaderElement) {
        try {
            ulmsAuthorityService.authorityCaller(soapHeaderElement);
            return ulmsuldAssignedLoadedListService.buildULDPDAssociationResponse(request);
        } catch (CustomException e) {
            throw new ServiceFaultException("DATABASE-ERROR",
                    errorMessageService.buildServiceStatus(ErrorCode.DATABASE_ERROR));
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FlightAssignedULDChangesRequest")
    @ResponsePayload
    public FlightAssignedULDChangesResponse flightAssignedULDChanges(@RequestPayload FlightAssignedULDChangesRequest request,
                                                                     @SoapHeader(value = HEADER_NAMESPACE_URI) SoapHeaderElement soapHeaderElement) {
        try {
            ulmsAuthorityService.authorityCaller(soapHeaderElement);
            return ulmsuldAssignedLoadedListService.buildFlightAssignedULDChangesResponse(request);
        } catch (CustomException e) {
            throw new ServiceFaultException("DATABASE-ERROR",
                    errorMessageService.buildServiceStatus(ErrorCode.DATABASE_ERROR));
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ULDReleaseToAgentRequest")
    @ResponsePayload
    public ULDReleaseToAgentResponse ULDReleaseToAgent(@RequestPayload ULDReleaseToAgentRequest request,
                                                       @SoapHeader(value = HEADER_NAMESPACE_URI) SoapHeaderElement soapHeaderElement) {
        try {
            ulmsAuthorityService.authorityCaller(soapHeaderElement);
            return uldReleaseToAgentService.uldReleaseToAgent(request);
        } catch (CustomException e) {
            throw new ServiceFaultException("DATABASE-ERROR",
                    errorMessageService.buildServiceStatus(ErrorCode.DATABASE_ERROR));
        }
    }
}
