package com.ngen.cosys.billing.sap.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.google.common.collect.Lists;
import com.ngen.cosys.billing.sap.enums.query.SAPInterfaceQuery;
import com.ngen.cosys.billing.sap.model.SAPFileErrorInfo;
import com.ngen.cosys.billing.sap.model.SAPFileInfo;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository("sapinterfaceprocessdaol")
public class SapInterfaceProcessDAOImpl extends BaseDAO implements SapInterfaceProcessDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSession;

   private static Logger logger = LoggerFactory.getLogger(SapInterfaceProcessDAOImpl.class);

   @Override
	public void updateCustomerPostingStatus(List<Long> billGenIds) throws CustomException {
         
		if (!ObjectUtils.isEmpty(billGenIds)) {
		
			List<List<Long>> lists = Lists.partition(billGenIds, 500);
			{

				for (List<Long> subListId : lists) {
					
				   System.out.println("sublist===="+subListId.size());
				   
				   super.updateData(SAPInterfaceQuery.UPDATE_BILLGEN_POSTING_STATUS.getQueryId(), subListId, sqlSession);

					super.updateData(SAPInterfaceQuery.UPDATE_BILLENTRY_POSTING_STATUS.getQueryId(), subListId,
							sqlSession);

				}
			}

		}

	}

   @Override
   public String getOutboundFileFolder(String folderId) throws CustomException {
      return fetchObject(SAPInterfaceQuery.FETCH_OUT_BOUND_FILE_FOLDER.getQueryId(), folderId, sqlSession);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.billing.sap.service.SapInvoiceAndCreditNoteProcessImpl#
    * searchCode(com.ngen.cosys.billing.sap.model.SAPFileInfo)
    */

   @Override
   public SAPFileInfo saveSAPFileInfo(SAPFileInfo sapFileInfo) throws CustomException {
      logger.info("Processing of the file started {}", ' ');
      super.insertData(SAPInterfaceQuery.INSERT_SAP_FILE_INFO_DETAILS.getQueryId(), sapFileInfo, sqlSession);
      return sapFileInfo;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.billing.sap.service.SapInvoiceAndCreditNoteProcessImpl#
    * searchCode(com.ngen.cosys.billing.sap.model.SAPFileInfo)
    */

   @Override
   public SAPFileInfo updateInvoiceAndCreditFileInfo(SAPFileInfo sapFileInfo) throws CustomException {
      logger.info("Processing of the file ended {}", ' ');
      super.updateData(SAPInterfaceQuery.UPDATE_SAP_FILE_INFO_DETAILS.getQueryId(), sapFileInfo, sqlSession);
      return sapFileInfo;
   }

   @Override
   public List<SAPFileErrorInfo> saveFileParsingErrorMsg(List<SAPFileErrorInfo> sapFileProcessingError) throws CustomException {
      super.insertList(SAPInterfaceQuery.INSERT_SAP_FILE_PROCESSING_ERROR_DETAILS.getQueryId(), sapFileProcessingError, sqlSession);

      return sapFileProcessingError;
   }

   @Override
   public String getInboundFileFolder(String folderId) throws CustomException {
      return fetchObject("getFileInboundFolder", folderId, sqlSession);
   }

   @Override
   public String getCurrentDate() throws CustomException {
      return fetchObject("getCurrentDate", null, sqlSession);
   }

@Override
public int getFileStatus(String fileName) throws CustomException {
	return fetchObject("getFileStatus",fileName,sqlSession);
}

@Override
public int checkOutBoundFileGeneratedOrNot() throws CustomException {
	
	return fetchObject("getOutBoundFileStatus",null,sqlSession);
}

@Override
public void saveOutBoundSAPFileInfo(SAPFileInfo sapFileInfo) throws CustomException {
	 super.insertData("insertOutBoundSAPFileInfo", sapFileInfo, sqlSession);

}

}
