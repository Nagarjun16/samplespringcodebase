package com.ngen.cosys.application.job;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.application.service.MssbatchesServiceImpl;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.AgentListMessageModel;
import com.ngen.cosys.model.MssMessageParentModel;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class RdftPdReturnBefore extends AbstractCronJob {
	
	
	 @Override
	   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
	      super.executeInternal(jobExecutionContext);
	      
	    /*  try {
	         //hit to your service
	      }*/ /*catch (CustomException e) {
	         throw new JobExecutionException(e.getCause());
	      }
	     */
	   }
}
