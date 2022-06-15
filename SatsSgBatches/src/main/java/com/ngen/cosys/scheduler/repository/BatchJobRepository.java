/**
 * 
 * BatchJobRepository.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          17 May, 2018   NIIT      -
 */
package com.ngen.cosys.scheduler.repository;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.model.BatchJobModel;

/**
 * This repository interface will take care of handling Batch Jobs.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
public interface BatchJobRepository {

   /**
    * Returns the list of Batch Jobs.
    * 
    * @return instance of List<BatchJobModel>.
    * @throws instance
    *            of CustomException.
    */
   List<BatchJobModel> getJobs() throws CustomException;

   void cleanUpJob(BatchJobModel requestModel) throws CustomException;

   void reinitiateMessages(BatchJobModel requestModel) throws CustomException;
}