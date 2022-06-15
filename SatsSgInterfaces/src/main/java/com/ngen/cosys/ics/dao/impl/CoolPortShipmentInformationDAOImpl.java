package com.ngen.cosys.ics.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.dao.CoolPortShipmentInformationDAO;
import com.ngen.cosys.ics.model.CoolPortShipmentRequestModel;
import com.ngen.cosys.ics.model.CoolPortShipmentResponseModel;

@Repository
public class CoolPortShipmentInformationDAOImpl extends BaseDAO implements CoolPortShipmentInformationDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSession;

   @Override
   public CoolPortShipmentResponseModel fetchShipmentInformation(CoolPortShipmentRequestModel request) throws CustomException {
      CoolPortShipmentResponseModel response = fetchObject("fetchShipmentInformation", request, sqlSession);
      System.out.println("=============================== :: " + response);
      return response;
   }

}
