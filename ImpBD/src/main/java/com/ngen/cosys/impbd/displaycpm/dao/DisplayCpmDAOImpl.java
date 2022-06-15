/**
 * This is a repository implementation component for Display CPM info
 */
package com.ngen.cosys.impbd.displaycpm.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.displaycpm.model.DisplayCpmModel;
import com.ngen.cosys.impbd.enums.DisplayCpmQueryIds;

@Repository
public class DisplayCpmDAOImpl extends BaseDAO implements DisplayCpmDAO {

   @Autowired
   private SqlSessionTemplate sqlSessionShipment;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.displaycpm.dao.DisplayCpmDAO#search(com.ngen.cosys.impbd
    * .displaycpm.model.DisplayCpmModel)
    */
   @Override
   public DisplayCpmModel search(DisplayCpmModel displayCpmModel) throws CustomException {
      return this.fetchObject(DisplayCpmQueryIds.SQL_GET_CPM_INFO.getQueryId(), displayCpmModel, sqlSessionShipment);
   }

}