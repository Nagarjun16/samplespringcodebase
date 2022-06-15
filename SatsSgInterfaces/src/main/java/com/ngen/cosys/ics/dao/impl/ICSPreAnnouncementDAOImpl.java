package com.ngen.cosys.ics.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.dao.ICSPreAnnouncementDAO;
import com.ngen.cosys.ics.model.PreAnnouncementRequestModel;
import com.ngen.cosys.ics.model.ULD;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Repository
public class ICSPreAnnouncementDAOImpl extends BaseDAO implements ICSPreAnnouncementDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSessionTemplate;

   @Override
   public ULD preannouncementUldMessage(PreAnnouncementRequestModel model) throws CustomException {
      return fetchObject("getICSPreannouncementMessageData", MultiTenantUtility.getAirportCityMap(model.getContainerId()), sqlSessionTemplate);
      // return fetchObject("fetchImportULDData", model, sqlSessionTemplate);
   }

}
