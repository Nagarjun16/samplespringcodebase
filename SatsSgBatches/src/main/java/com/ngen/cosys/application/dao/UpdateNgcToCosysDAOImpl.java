/**
 * This is a repository implementation class for creating delivery request based
 * on EDelivery Request shipments
 */
package com.ngen.cosys.application.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository
public class UpdateNgcToCosysDAOImpl extends BaseDAO implements UpdateNgcToCosysDAO {

   @Autowired
   private SqlSessionTemplate sqlSessionTemplate;

   public void getPreLodgeData() throws CustomException {
      this.fetchList("getPreLodgeData", new Object(), sqlSessionTemplate);
   }
}