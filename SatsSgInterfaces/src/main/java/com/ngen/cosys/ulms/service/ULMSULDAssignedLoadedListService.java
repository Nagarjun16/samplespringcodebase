package com.ngen.cosys.ulms.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ulms.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.List;

public interface ULMSULDAssignedLoadedListService {
    FlightAssignedULDResponse buildFlightAssignedULDResponse(FlightAssignedULDRequest request) throws CustomException;
    FlightULDPDAssociationResponse buildULDPDAssociationResponse(FlightULDPDAssociationRequest request) throws CustomException;
    FlightAssignedULDChangesResponse buildFlightAssignedULDChangesResponse(FlightAssignedULDChangesRequest request) throws CustomException;
}
