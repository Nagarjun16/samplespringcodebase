/**
 * 
 * ProduceSingPostMessageDaoImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 7 May, 2018 NIIT -
 */
package com.ngen.cosys.satssg.interfaces.singpost.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.model.MailBagRequestModel;

@Repository("produceSingPostMessageDao")
public class ProduceSingPostMessageDaoImpl extends BaseDAO implements ProduceSingPostMessageDao {

   @Autowired
   private SqlSession sqlSessionTemplate;

   @Override
   public MailBagRequestModel pushMailBagReceivingScanStatus(MailBagRequestModel mailBagModel)
         throws CustomException {
      return  super.fetchObject("fetchBagReceivingStatus", mailBagModel, sqlSessionTemplate);
   }

}
