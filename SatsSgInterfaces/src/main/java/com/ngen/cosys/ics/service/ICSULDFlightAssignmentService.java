package com.ngen.cosys.ics.service;

import java.util.Map;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.ICSULDFlightAssignmentResponsePayload;

public interface ICSULDFlightAssignmentService {

   public ICSULDFlightAssignmentResponsePayload prepareMessage(Map<String, Object> data) throws CustomException;

}
