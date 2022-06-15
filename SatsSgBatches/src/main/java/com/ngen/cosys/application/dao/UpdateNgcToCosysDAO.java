/**
 * This is a repository interface class for creating delivery request based on
 * EDelivery Request shipments
 */
package com.ngen.cosys.application.dao;

import com.ngen.cosys.framework.exception.CustomException;

public interface UpdateNgcToCosysDAO {
   void getPreLodgeData() throws CustomException;
}