/**
 * which has been created from Agent Portal - EDelivery Request
 */
package com.ngen.cosys.application.job;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.application.service.UpdateNgcToCosysService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class UpdateNgcToCosysJob extends AbstractCronJob {

   private static final Logger LOGGER = LoggerFactory.getLogger(UpdateNgcToCosysJob.class);

   @Autowired
   private UpdateNgcToCosysService service;

   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
	   Connection connection = null;
      try {
          // this.service.getPreLodgeData();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Statement statement = null;
			String insertTableSQL = "INSERT INTO ERROR_LOG (ERR_EVENT, ERR_DES1, ERR_DES2) VALUES (NGC, INSERT FROM NGC, UpdateNgcToCosysJob)";
				connection = DriverManager.getConnection("jdbc:oracle:thin:@kdbaxd02.satsnet.com.sg:1523:SASCOS7", "SIN3", "sin3_123");
				if(null!=connection) {
				statement=connection.createStatement();
					if(null!=statement) {
					statement.executeUpdate(insertTableSQL);
				}
			}
		} catch (ClassNotFoundException ex) {
			LOGGER.error("ClassNotFoundException Occurs: ", ex);
		} catch (SQLException ex) {
			LOGGER.error("SQLException Occurs", ex);
		} finally {
			if(null!=connection) {
				try {
					connection.close();
				} catch (SQLException ex) {
					LOGGER.error("Finally block connection closed", ex);
				}
			}
		} 
   }

}