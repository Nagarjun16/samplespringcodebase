package com.ngen.cosys.application.service;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.application.dao.TracingBatchJobDAO;
import com.ngen.cosys.application.model.TracingShipmentModel;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Service
@Transactional
public class TracingBatchJobServiceImpl extends BaseDAO implements TracingBatchJobService {

	private static final String MOVETOABANDON = "MOVETOABANDON";

   @Autowired
   private TracingBatchJobDAO tracingBatchJobDAO;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.application.service.TracingBatchJobService#
    * getUndeliverdShipments()
    */
   @Override
   public List<TracingShipmentModel> getUndeliverdShipments() throws CustomException {
      return this.tracingBatchJobDAO.fetchUndeliverdShipments();
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.application.service.TracingBatchJobService#createTracing(com.
    * ngen.cosys.application.model.TracingShipmentModel)
    */
   @Override
   public void createTracing(TracingShipmentModel requestModel) throws CustomException {
		
		//Set the shipment type if it is empty
		if(StringUtils.isEmpty(requestModel.getShipmentType())) {
			requestModel.setShipmentType("AWB");
		}
				
      if ("Y".equalsIgnoreCase(requestModel.getTracingShipmentInfoInsertIndicator())
            && ObjectUtils.isEmpty(requestModel.getComTracingShipmentInfoId())) {
         BigInteger lastCaseNumber = tracingBatchJobDAO.getMaxCaseNumber();
         lastCaseNumber = lastCaseNumber.add(BigInteger.ONE);
         String caseNumber = "CT" + StringUtils.leftPad(String.valueOf(lastCaseNumber.intValue()), 5, '0');
         requestModel.setCaseNumber(caseNumber);
			if (MOVETOABANDON.equalsIgnoreCase(requestModel.getCurrentSlab())) {
				requestModel.setCaseStatus(MOVETOABANDON);
            tracingBatchJobDAO.updateShipmentMaster(requestModel);
         } 
         else if ("SLAB4".equalsIgnoreCase(requestModel.getCurrentSlab())) {
            requestModel.setCaseStatus("PENDING");
            
         }
         else if ("SLAB3".equalsIgnoreCase(requestModel.getCurrentSlab())) {
             requestModel.setCaseStatus("PENDING");
             
          }
         else {
            requestModel.setCaseStatus("DRAFT");
         }
         requestModel.setIrregularityTypeCode("UNDELIVEREDSHIPMENT");
         tracingBatchJobDAO.insertTracing(requestModel);

         if (!StringUtils.isEmpty(requestModel.getFollowupActionInsertIndicator())) {
            requestModel.setActivity(requestModel.getFollowupActionInsertIndicator());
            tracingBatchJobDAO.insertActivity(requestModel);
         }
      } else if ("N".equalsIgnoreCase(requestModel.getTracingShipmentInfoInsertIndicator())
            && "Y".equalsIgnoreCase(requestModel.getTracingShipmentInfoUpdateIndicator())
            && !ObjectUtils.isEmpty(requestModel.getComTracingShipmentInfoId())) {
         requestModel.setCaseStatus(requestModel.getNewStatus());
         tracingBatchJobDAO.updateTracing(requestModel);
			if (MOVETOABANDON.equalsIgnoreCase(requestModel.getNewStatus())) {
        	 tracingBatchJobDAO.updateShipmentMaster(requestModel);
         }

         if (!StringUtils.isEmpty(requestModel.getFollowupActionInsertIndicator())) {
        	 requestModel.setActivity(requestModel.getFollowupActionInsertIndicator());
        	//Check if same followupAction exists in the Com_TracingShipmentFollowupAction table
            boolean ActivityExist = tracingBatchJobDAO.checkActivityExist(requestModel);
            if(!ActivityExist) {
                tracingBatchJobDAO.insertActivity(requestModel);
            }
         }
      }
   }

}