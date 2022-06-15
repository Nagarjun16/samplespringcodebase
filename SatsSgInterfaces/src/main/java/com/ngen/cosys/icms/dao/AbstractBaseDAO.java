/**
 * Lower version of BaseDAO. Rather using the same class this has been
 * introduced since cargo messaging project as its own Base Model object which
 * has less fields
 */
package com.ngen.cosys.icms.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.dao.MapResultHandler;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.satssginterfaces.message.exception.MessageProcessingException;


public class AbstractBaseDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseDAO.class);

   private static final String LOG_SEPERATOR = " | ";

   @Autowired
   private MapResultHandler mapHandler;

   /**
    * Constructor.
    */
   public AbstractBaseDAO() {
      super();
   }

   /**
    * Log Query ID
    * 
    * @param sqlId
    *           Query Id
    */
   private static void logiBatis(String sqlId) {
      LOGGER.debug("Query ID : " + sqlId);
   }

   private <T> T handleException(String className, String methodName, Exception e) throws MessageProcessingException {
      StringBuilder sb = new StringBuilder();
      sb.append(className).append(LOG_SEPERATOR).append(methodName).append(LOG_SEPERATOR).append("SQL Exception")
            .append(LOG_SEPERATOR);
      String dbBusError = checkForDBBusinessError(e);
      String errCause = null;
      if (!StringUtils.isEmpty(dbBusError) && e instanceof SQLException) {
         SQLException sysExc = (SQLException) e;
         errCause = sysExc.getCause().toString();
      } else if (!StringUtils.isEmpty(dbBusError) && e instanceof DuplicateKeyException) {
         DuplicateKeyException duplicateKeyException = (DuplicateKeyException) e;
         errCause = duplicateKeyException.getCause().toString();
      }

      if (!StringUtils.isEmpty(errCause)) {
         String dbErrMsg = getDBErrorMessage(errCause);
         sb.append(" dbErrMsg: ");
         String exMssg = sb.toString();
         LOGGER.error(exMssg + dbErrMsg);
         throw new MessageProcessingException(dbBusError);
      } else {
         throw new MessageProcessingException(sb.toString(), e);
      }
   }

   /**
    * Prepares DB Error Message.
    * 
    * @param errCause
    * @return
    */
   private String getDBErrorMessage(String errCause) {
      return parseDBErrorMessage(errCause);
   }

   /**
    * Method to check if the Exception is caused due to a Business Error coming
    * from the Database program code.
    * 
    * @return String
    */
   protected String checkForDBBusinessError(Exception expGeneral) {
      String sqlErrorCode = null;
      if (expGeneral instanceof SQLException) {
         SQLException sysExc = (SQLException) expGeneral;
         String errCause = sysExc.getCause().toString();
         sqlErrorCode = errCause;
      } else if (expGeneral instanceof DuplicateKeyException) {
         DuplicateKeyException sysExc = (DuplicateKeyException) expGeneral;
         String errCause = sysExc.getCause().toString();
         sqlErrorCode = errCause;
      }
      return sqlErrorCode;
   }

   /**
    * Method to fetch one row and populate into result object in the sqlquery xml
    * 
    * @param sqlId
    *           : to fetch the query from sqlquery.xml
    * @param parameter
    *           : parameter Object
    * @param sqlSession
    * @throws MessageProcessingException
    */
   public <T> T fetchObject(String sqlId, Object parameter, SqlSession sqlSession) throws MessageProcessingException {
      logiBatis(sqlId);
      try {
         return sqlSession.selectOne(sqlId, parameter);
      } catch (Exception e) {
         return handleException(this.getClass().getName(), "fetchObject", e);
      }
   }

   /**
    * Method to fetch the Data into List specified in the sqlquery xml
    * 
    * @param sqlId
    *           : to fetch the query from sqlquery.xml
    * @param parameter
    *           : parameter Object
    * @param sqlSession
    * @throws MessageProcessingException
    */
   protected <E> List<E> fetchList(String sqlId, Object parameter, SqlSession sqlSession)
         throws MessageProcessingException {
      logiBatis(sqlId);
      try {
         return sqlSession.selectList(sqlId, parameter);
      } catch (Exception e) {
         return handleException(this.getClass().getName(), "fetchList", e);
      }
   }

   /**
    * Method to fetch the Data into Map specified in the sqlquery xml
    * 
    * @param sqlId
    *           : to fetch the query from sqlquery.xml
    * @param parameter
    *           : parameter Object
    * @param sqlSession
    * @throws MessageProcessingException
    */
   public List<HashMap<String, Object>> fetchListMap(String sqlId, Object parameter, SqlSession sqlSession)
         throws MessageProcessingException {
      logiBatis(sqlId);
      try {
         sqlSession.select(sqlId, parameter, mapHandler);
         return mapHandler.getListMap();
      } catch (Exception e) {
         return handleException(this.getClass().getName(), "fetchListMap", e);
      }
   }

   /**
    * Method to insert Data into table specified in the sqlquery xml
    * 
    * @param sqlId
    *           : to insert Data into table from sqlquery.xml
    * @param parameter
    *           : parameter Object
    * @param sqlSession
    * @return int, specifies number of records inserted
    * @throws MessageProcessingException
    */
   public Integer insertData(String sqlId, Object parameter, SqlSession sqlSession) throws MessageProcessingException {
      logiBatis(sqlId);
      try {
         return sqlSession.insert(sqlId, parameter);
      } catch (Exception e) {
         return handleException(this.getClass().getName(), "insertData", e);
      }
   }

   /**
    * Method to update a record in the DB
    * 
    * @param sqlId
    * @param parameter
    * @param sqlSession
    * @return int, specifies number of records updated
    * @throws MessageProcessingException
    */
   public Integer updateData(String sqlId, Object parameter, SqlSession sqlSession) throws MessageProcessingException {
      logiBatis(sqlId);
      try {
         return sqlSession.update(sqlId, parameter);
      } catch (Exception e) {
         return handleException(this.getClass().getName(), "updateData", e);
      }
   }

   /**
    * Method to delete a record from the DB
    * 
    * @param sqlId
    * @param parameter
    * @param sqlSession
    * @return int, specifies number of records deleted
    * @throws MessageProcessingException
    */
   public Integer deleteData(String sqlId, Object parameter, SqlSession sqlSession) throws MessageProcessingException {
      logiBatis(sqlId);
      try {
         return sqlSession.delete(sqlId, parameter);
      } catch (Exception e) {
         return handleException(this.getClass().getName(), "deleteData", e);
      }
   }

   /**
    * Method to insert a list of records into the DB
    * 
    * @param sqlId
    * @param objList
    * @param sqlSession
    * @return int, specifies number of records inserted
    * @throws MessageProcessingException
    */
   protected ArrayList<Integer> insertData(String sqlId, List<? extends BaseBO> objList, SqlSession sqlSession)
         throws MessageProcessingException {
      ArrayList<Integer> arlList = new ArrayList<>();
      for (int nCount = 0; nCount < objList.size(); ++nCount) {
         arlList.add(insertData(sqlId, objList.get(nCount), sqlSession));
      }
      return arlList;
   }

   /**
    * Method to insert a list of records into the DB, and expect the Sequence
    * generated ID would be returned automatically.
    * 
    * @param sqlId
    * @param objList
    * @param sqlSession
    * @return int, specifies number of records inserted
    * @throws MessageProcessingException
    */
   protected ArrayList<? extends BaseBO> insertList(String sqlId, List<? extends BaseBO> objList,
         SqlSession sqlSession) throws MessageProcessingException {
      insertData(sqlId, objList, sqlSession);
      return new ArrayList<>(objList);
   }

   /**
    * Method to update a list of records in the DB
    * 
    * @param sqlId
    * @param objList
    * @param sqlSession
    * @return int, specifies number of records updated
    * @throws MessageProcessingException
    */
   protected ArrayList<Integer> updateData(String sqlId, List<? extends BaseBO> objList, SqlSession sqlSession)
         throws MessageProcessingException {
      ArrayList<Integer> arlList = new ArrayList<>();
      for (int nCount = 0; nCount < objList.size(); ++nCount) {
         arlList.add(Integer.valueOf(updateData(sqlId, objList.get(nCount), sqlSession)));
      }
      return arlList;
   }

   /**
    * Method to delete a list of records in the DB
    * 
    * @param sqlId
    * @param objList
    * @param sqlSession
    * @return int, specifies number of records updated
    * @throws MessageProcessingException
    */
   protected ArrayList<Integer> deleteData(String sqlId, List<? extends BaseBO> objList, SqlSession sqlSession)
         throws MessageProcessingException {
      ArrayList<Integer> arlList = new ArrayList<>();
      for (int nCount = 0; nCount < objList.size(); ++nCount) {
         arlList.add(Integer.valueOf(deleteData(sqlId, objList.get(nCount), sqlSession)));
      }
      return arlList;
   }

   /**
    * Method to update a list of records in the DB
    * 
    * @param sqlId
    * @param objList
    * @param isConcurrentCheck
    * @param sqlSession
    * @return int, specifies number of records updated
    * @throws MessageProcessingException
    */
   protected ArrayList<Integer> updateData(String sqlId, List<? extends BaseBO> objList, boolean isConcurrentCheck,
         SqlSession sqlSession) throws MessageProcessingException {
      ArrayList<Integer> arlList = new ArrayList<>();
      if (isConcurrentCheck) {
         for (int nCount = 0; nCount < objList.size(); ++nCount) {
            arlList.add(Integer.valueOf(updateData(sqlId, objList.get(nCount), sqlSession)));
         }
      }
      return arlList;
   }

   /**
    * This method parses the DB Errors.
    * 
    * @param srcMessage
    * @return
    */
   protected String parseDBErrorMessage(String srcMessage) {
      String keywordDB = "MSSQL-";
      String errMessage = srcMessage;
      try {
         LOGGER.debug("Index of MSSQL-:" + srcMessage.indexOf(keywordDB));
         if (!StringUtils.isEmpty(srcMessage) && srcMessage.length() > keywordDB.length()
               && srcMessage.contains(keywordDB)) {
            String parseMsg1 = srcMessage.substring(srcMessage.indexOf(':', srcMessage.indexOf(keywordDB)) + 1);
            LOGGER.debug("\n\n\n\nparseMsg1 is:" + parseMsg1);
            if (StringUtils.isEmpty(parseMsg1)) {
               parseMsg1 = "INVALID MESSAGE";
            } else {
               parseMsg1 = parseMsg1.trim();
            }
            int nIndexRef = parseMsg1.indexOf("Ref:");
            if (nIndexRef > 0) {
               errMessage = parseMsg1.substring(0, nIndexRef);
            } else {
               errMessage = parseMsg1;
            }
            LOGGER.debug("Formatted Error Message:" + errMessage);
         }
      } catch (Exception expGen) {
         LOGGER.error("Error parsing DB Error Message:", expGen);
      }
      return errMessage;
   }

}