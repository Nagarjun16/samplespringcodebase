package com.ngen.cosys.ics.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.dao.ICSULDFlightAssignmentDAO;
import com.ngen.cosys.ics.model.ICSULDFlightAssignmentRequestPayload;
import com.ngen.cosys.ics.model.ICSULDFlightAssignmentResponsePayload;

@Repository
public class ICSULDFlightAssignmentDAOImpl extends BaseDAO implements ICSULDFlightAssignmentDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSession;

   @Override
   public ICSULDFlightAssignmentResponsePayload prepareMessage(ICSULDFlightAssignmentRequestPayload payload) throws CustomException {
      return fetchObject("flightAssignmentStatus", payload, sqlSession);
   }

}