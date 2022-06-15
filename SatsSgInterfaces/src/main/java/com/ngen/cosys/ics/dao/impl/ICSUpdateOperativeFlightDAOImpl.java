/**
 * 
 */
package com.ngen.cosys.ics.dao.impl;


import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.dao.ICSUpdateOperativeFlightDAO;
import com.ngen.cosys.ics.model.ICSOperativeFlightModel;
import com.ngen.cosys.ics.model.ICSUpdateOperativeFlightRequestModel;


/**
 * @author Ashwin.Bantoo
 *
 */
@Repository
public class ICSUpdateOperativeFlightDAOImpl extends BaseDAO implements ICSUpdateOperativeFlightDAO{
	
	 @Autowired
	   @Qualifier("sqlSessionTemplate")
	   private SqlSessionTemplate sqlSession;

	   @Override
	   public ICSUpdateOperativeFlightRequestModel getUpdatedOperativeFlightDetails() throws CustomException {
	      List<ICSOperativeFlightModel> operativeFlightList = fetchList("fetchUpdatedOperativeFlightDetails", null, sqlSession);
	      ICSUpdateOperativeFlightRequestModel responseModel = new ICSUpdateOperativeFlightRequestModel();
	      responseModel.setOperativeFlightList(operativeFlightList);
	      return responseModel;
	   }

}
