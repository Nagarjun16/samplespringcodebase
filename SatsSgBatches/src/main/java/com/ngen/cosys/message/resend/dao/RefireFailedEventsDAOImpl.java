/**
 * 
 */
package com.ngen.cosys.message.resend.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.events.payload.InterfaceMessageLogModel;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.enums.MessageResendSQL;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;

/**
 * @author NIIT Technologies ltd
 *
 */
@Repository
public class RefireFailedEventsDAOImpl implements RefireFailedEventsDAO {

	@Autowired
	@Qualifier(BeanFactoryConstants.SESSION_TEMPLATE)
	private SqlSession sqlSession;

	@Autowired
	@Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
	private SqlSession sqlSessionROI;

	@Override
	public List<InterfaceMessageLogModel> getfailedEvents() throws CustomException {
		// TODO Auto-generated method stub
		 return sqlSessionROI.selectList(MessageResendSQL.SQL_SELECT_FAILED_EVENTS.getQueryId());
	}

	@Override
	public void updateRetryCount(InterfaceMessageLogModel interfaceMessageLogModel) {
		// TODO Auto-generated method stub
		sqlSession.update("updateRetryCountForFailedEvents",interfaceMessageLogModel);
	}

}
