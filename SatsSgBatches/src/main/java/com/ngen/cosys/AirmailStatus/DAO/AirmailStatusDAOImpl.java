package com.ngen.cosys.AirmailStatus.DAO;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.AirmailStatus.Model.AirmailStatusChildModel;
import com.ngen.cosys.AirmailStatus.Model.AirmailStatusFlightModel;
import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.events.payload.CAMSModel;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Repository
public class AirmailStatusDAOImpl extends BaseDAO implements AirmailStatusDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;
   
   @Override
   public List<AirmailStatusEvent> getAirmailStoredEvents() throws CustomException {
      return this.fetchList("getStoredEventForAirmailStatus", null, sqlSession);
   }

   @Override
   public void updateStatus(AirmailStatusEvent requestModel) throws CustomException {
      this.updateData("changeStatusToProcessing", requestModel, sqlSession);
      
   }
   
   @Override
   public AirmailStatusFlightModel getFlightInformationForImport(AirmailStatusChildModel requestModel)
         throws CustomException {
      return this.fetchObject("getFlightInfoForImportCAMS", requestModel, sqlSession);
   }

   @Override
   public AirmailStatusChildModel getContainerInfo(AirmailStatusChildModel brkdwnModel) throws CustomException {
      
      return this.fetchObject("getContainerinfoForExpEventCAMS", brkdwnModel, sqlSession);
   }

   @Override
   public String getCarrierCodeForDamageEvents(AirmailStatusChildModel brkdwnModel) throws CustomException {
      return this.fetchObject("getCarrierCodeForDamageEvent", brkdwnModel, sqlSession);
   }

	@Override
	public AirmailStatusEvent getFlightInformationForExport(AirmailStatusEvent value) throws CustomException {
		return this.fetchObject("getFlightInfoForExpMan", MultiTenantUtility.getAirportCityMap(value.getFlightId()), sqlSession);
	}

	@Override
	public String getErrorMessage(String errorCode) throws CustomException {
		return this.fetchObject("getErrorMessage", errorCode, sqlSession);
	}

	@Override
	public List<AirmailStatusEvent> fetchFailCAMSMessages() throws CustomException {
		  return this.fetchList("sqlGetDetailsforCAMSResendMessage", null, sqlSession);
	}

	@Override
	public void updateCAMSEventLog(AirmailStatusEvent event) throws CustomException {
		this.updateData("sqlUpdateCAMSEventLog", event, sqlSession);
	}

	@Override
	public List<String> getMailListForCAMSAlert() throws CustomException {
		return  this.fetchList("sqlGetCAMSAlertEmailId", null, sqlSession);
	}

	@Override
	public String getTemplateForMail() throws CustomException {
		return this.fetchObject("sqlGetCAMSAlertTemplateForMail", null, sqlSession);
	}

	@Override
	public CAMSModel getCAMSInterfaceConfigurations() throws CustomException {
		return this.fetchObject("sqlGetCAMSInterfaceConfigurations", null, sqlSession);
	}

}
