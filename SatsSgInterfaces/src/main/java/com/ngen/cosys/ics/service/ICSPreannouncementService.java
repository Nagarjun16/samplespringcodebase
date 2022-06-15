package com.ngen.cosys.ics.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.PreAnnouncementRequestModel;
import com.ngen.cosys.ics.model.ULD;

public interface ICSPreannouncementService {
   ULD preannouncementUldMessage(PreAnnouncementRequestModel requestPayload) throws CustomException;
}
