/**
 * {@link CiQReportDAOImpl}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.ciq.report.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.ciq.report.enums.CiQReportEnums;
import com.ngen.cosys.ciq.report.model.CiQReportLog;
import com.ngen.cosys.ciq.report.model.NotificationSchedule;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;

/**
 * CiQ Report Service Data access layer implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Repository
public class CiQReportDAOImpl extends BaseDAO implements CiQReportDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(CiQReportDAO.class);

   @Autowired
   @Qualifier(BeanFactoryConstants.SESSION_TEMPLATE)
   private SqlSession sqlSession;
   
   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSessionROI;
   
   /**
    * @see com.ngen.cosys.ciq.report.dao.CiQReportDAO#getScheduledReports(java.lang.String)
    */
   @Override
   public List<NotificationSchedule> getScheduledReports(String frequency) throws CustomException {
      LOGGER.debug("CiQ Report DAO :: getScheduledReports Frequency - {}", frequency);
      return sqlSessionROI.selectList(CiQReportEnums.SELECT_CIQ_REPORT_DAILY_CONFIG.getQueryId(), frequency);
   }

   /**
    * @see com.ngen.cosys.ciq.report.dao.CiQReportDAO#logCiQReport(com.ngen.cosys.ciq.report.model.CiQReportLog)
    */
   @Override
   public BigInteger logCiQReport(CiQReportLog reportLog) throws CustomException {
      LOGGER.debug("CiQ Report DAO :: logCiQReport - {}");
      sqlSession.insert(CiQReportEnums.INSERT_CIQ_REPORT_LOG.getQueryId(), reportLog);
      if(!ObjectUtils.isEmpty(reportLog.getReportLogId())){
    	  return reportLog.getReportLogId();  
      }
      return null;
   }

   /**
    * @see com.ngen.cosys.ciq.report.dao.CiQReportDAO#updateCiQReport(com.ngen.cosys.ciq.report.model.CiQReportLog)
    */
   @Override
   public void updateCiQReport(CiQReportLog reportLog) throws CustomException {
      LOGGER.debug("CiQ Report DAO :: updateCiQReport - {}");
      sqlSession.update(CiQReportEnums.UPDATE_CIQ_REPORT_LOG.getQueryId(), reportLog);
   }

   /**
    * @see com.ngen.cosys.ciq.report.dao.CiQReportDAO#verifyCiQReport(com.ngen.cosys.ciq.report.model.CiQReportLog)
    */
   @Override
   public boolean verifyCiQReport(CiQReportLog reportLog) throws CustomException {
      LOGGER.debug("CiQ Report DAO :: verifyCiQReport - {}");
      return sqlSessionROI.selectOne(CiQReportEnums.SELECT_CIQ_REPORT_LOG.getQueryId(), reportLog);
   }

	@Override
	public boolean dataExists(NotificationSchedule reportSchedule) throws CustomException {
		LOGGER.debug("CiQ Report DAO :: reportDataExists - {}");
		boolean dataExist=false;
		if ("RCS".equalsIgnoreCase(reportSchedule.getMessageType())) {
			return dataExist = sqlSessionROI.selectOne("sqlIsReportDataExistsRCS", reportSchedule);
		}
		if ("NFD".equalsIgnoreCase(reportSchedule.getMessageType())) {
			return dataExist = sqlSessionROI.selectOne("sqlIsReportDataExistsNFD", reportSchedule);
		}
		if ("RCF".equalsIgnoreCase(reportSchedule.getMessageType())) {
			return dataExist = sqlSessionROI.selectOne("sqlIsReportDataExistsRCF", reportSchedule);
		}
		if ("AWD".equalsIgnoreCase(reportSchedule.getMessageType())) {
			return dataExist = sqlSessionROI.selectOne("sqlIsReportDataExistsAWD", reportSchedule);
		}
		if ("DEP".equalsIgnoreCase(reportSchedule.getMessageType()) && reportSchedule.isTransitFlag()) {
			return dataExist = sqlSessionROI.selectOne("sqlIsReportDataExistsDEPWithTrasistFlag1", reportSchedule);
		}
		if ("DEP".equalsIgnoreCase(reportSchedule.getMessageType()) && !reportSchedule.isTransitFlag()) {
			return dataExist = sqlSessionROI.selectOne("sqlIsReportDataExistsDEPWithTrasistFlag0", reportSchedule);
		}
		if ("DLV".equalsIgnoreCase(reportSchedule.getMessageType())) {
			return dataExist = sqlSessionROI.selectOne("sqlIsReportDataExistsDLV", reportSchedule);
		}
		if ("TFD".equalsIgnoreCase(reportSchedule.getMessageType())) {
			return dataExist = sqlSessionROI.selectOne("sqlIsReportDataExistsTFD", reportSchedule);
		}
		if ("RCT".equalsIgnoreCase(reportSchedule.getMessageType())) {
			return dataExist = sqlSessionROI.selectOne("sqlIsReportDataExistsRCT", reportSchedule);
		}
		return dataExist;

	}
   
}
