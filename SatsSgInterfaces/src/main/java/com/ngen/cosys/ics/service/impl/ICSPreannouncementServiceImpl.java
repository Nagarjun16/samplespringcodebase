package com.ngen.cosys.ics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.dao.ICSPreAnnouncementDAO;
import com.ngen.cosys.ics.model.PreAnnouncementRequestModel;
import com.ngen.cosys.ics.model.ULD;
import com.ngen.cosys.ics.service.ICSPreannouncementService;

@Service
public class ICSPreannouncementServiceImpl implements ICSPreannouncementService {

   @Autowired
   private ICSPreAnnouncementDAO dao;

   @Override
   public ULD preannouncementUldMessage(PreAnnouncementRequestModel model) throws CustomException {
      return dao.preannouncementUldMessage(model);
   }

}
