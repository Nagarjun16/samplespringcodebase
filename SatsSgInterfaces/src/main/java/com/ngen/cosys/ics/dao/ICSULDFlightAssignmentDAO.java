package com.ngen.cosys.ics.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.ICSULDFlightAssignmentRequestPayload;
import com.ngen.cosys.ics.model.ICSULDFlightAssignmentResponsePayload;

public interface ICSULDFlightAssignmentDAO {
   ICSULDFlightAssignmentResponsePayload prepareMessage(ICSULDFlightAssignmentRequestPayload requestPayload)
         throws CustomException;
}