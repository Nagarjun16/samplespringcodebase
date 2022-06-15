package com.ngen.cosys.application.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.ManifesrReconciliationStatementModel;

public interface ProduceManifestReconcillationStatementMessageService {

   String buildManifestReconcillationStatementMesaage(
         ManifesrReconciliationStatementModel manifesrReconciliationStatementModel) throws CustomException, Exception;

   void updateMrsSentDateToCustomsFlight(ManifesrReconciliationStatementModel mrs) throws CustomException;

}