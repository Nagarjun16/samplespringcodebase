package com.ngen.cosys.satssg.singpost.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.singpost.model.MailBagRequestModel;

@Repository("ProduceBagReceivingScanStatusDao")
public class ProduceBagReceivingScanStatusDaoImpl extends BaseDAO implements ProduceBagReceivingScanStatusDao {
  
   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;
  
   @Override
   public List<MailBagRequestModel> pushMailBagReceivingScanStatus(Object value) throws CustomException {
      return  super.fetchList("fetchBagReceivingStatus", null, sqlSession);
   }

}
