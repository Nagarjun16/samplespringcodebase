package com.ngen.cosys.application.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.ManifesrReconciliationStatementModel;

public interface ManifestReconcillationStatementDAO {

   public ManifesrReconciliationStatementModel getConsolidatedMrsInfo(
         ManifesrReconciliationStatementModel manifesrReconciliationStatementModel) throws CustomException;

   public List<ManifesrReconciliationStatementModel> getFlightsToSendMrs() throws CustomException;

   public void updateMrsSentDateToLinkMrs(ManifesrReconciliationStatementModel mrs) throws CustomException;

   public void updateMrsSentDateToCustomsFlight(ManifesrReconciliationStatementModel mrs) throws CustomException;

   public void insertMrsOutInfo(ManifesrReconciliationStatementModel mrs) throws CustomException;

   public CharSequence getMrsDestinationAddress() throws CustomException;

   public CharSequence getMrsOriginatorAddress() throws CustomException;

   public String getMrsPimaAddress() throws CustomException;

}
