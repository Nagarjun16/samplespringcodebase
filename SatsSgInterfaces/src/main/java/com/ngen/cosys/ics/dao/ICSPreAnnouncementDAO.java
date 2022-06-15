package com.ngen.cosys.ics.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.PreAnnouncementRequestModel;
import com.ngen.cosys.ics.model.ULD;

public interface ICSPreAnnouncementDAO {
   ULD preannouncementUldMessage(PreAnnouncementRequestModel model) throws CustomException;
}