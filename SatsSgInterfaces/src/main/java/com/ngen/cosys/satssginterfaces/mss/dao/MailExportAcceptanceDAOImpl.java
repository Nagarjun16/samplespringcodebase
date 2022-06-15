package com.ngen.cosys.satssginterfaces.mss.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.MailExportAcceptance;
import com.ngen.cosys.satssginterfaces.mss.model.MailExportAcceptanceDetails;
import com.ngen.cosys.satssginterfaces.mss.model.MailExportAcceptanceRequest;

@Repository("mailExportAcceptanceDAOImpl")
public class MailExportAcceptanceDAOImpl extends BaseDAO implements MailExportAcceptanceDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * getPAFlightDetails(com.ngen.cosys.export.acceptance.model.
	 * MailExportAcceptanceRequest)
	 */
	@Override
	public MailExportAcceptanceRequest getPAFlightDetails(MailExportAcceptanceRequest request) throws CustomException {
		int count=fetchObject("checkMailBag", request,sqlSession);
		if(count!=0) {
			request.addError("checkMailB", null, ErrorType.ERROR);
		}
		else {
			MailExportAcceptanceRequest mail = super.fetchObject("getPAFlightDetails", request, sqlSession);
			if(null!=mail) {
				request.setBookingId(mail.getBookingId());
				request.setPaFlightId(mail.getPaFlightId());
				request.setPaFlightKey(mail.getPaFlightKey());
				request.setPaFlightDate(mail.getPaFlightDate());
				request.setPaFlightSeg(mail.getPaFlightSeg());
			}	
		}
		
		return request;
	}
	
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#isSatsAssistedFlight(java.lang.String)
	 */
	@Override
	public boolean isSatsAssistedFlight(String carrier) throws CustomException {
		return super.fetchObject("isCarrierSats", carrier, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#getDnNumber(com.
	 * ngen.cosys.export.acceptance.model.MailExportAcceptanceRequest)
	 */
	@Override
	public MailExportAcceptanceRequest getDnDetails(MailExportAcceptanceRequest request) throws CustomException {
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		return super.fetchObject("getDnDetails", request, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * updateDocPiecesWeight(com.ngen.cosys.export.acceptance.model.
	 * MailExportAcceptanceRequest)
	 */
	@Override
	public MailExportAcceptanceRequest updateDocPiecesWeight(MailExportAcceptanceRequest request)
			throws CustomException {
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		super.updateData("updateDnPiecesWeight", request, sqlSession);
		return mail;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * updateShpMasterPiecesWeight(com.ngen.cosys.export.acceptance.model.
	 * MailExportAcceptanceRequest)
	 */
	@Override
	public MailExportAcceptanceRequest updateShpMasterPiecesWeight(MailExportAcceptanceRequest request)
			throws CustomException {
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		super.updateData("updateShipmentMasterPiecesWeight", request, sqlSession);
		return mail;
	}

	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#getShipmentInventoryId(com.ngen.cosys.export.acceptance.model.MailExportAcceptanceRequest)
	 */
	public int getShipmentInventoryId (MailExportAcceptanceRequest request) throws CustomException{
		int count=0;
		 if(super.fetchObject("getShpInventoryId", request, sqlSession)!=null) {
			  count=super.fetchObject("getShpInventoryId", request, sqlSession);
		 }
		return count;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * updateShpInventoryPiecesWeight(com.ngen.cosys.export.acceptance.model.
	 * MailExportAcceptanceRequest)
	 */
	@Override
	public MailExportAcceptanceRequest updateShpInventoryPiecesWeight(MailExportAcceptanceRequest request)
			throws CustomException {
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		super.updateData("updateShipmentInventoryPiecesWeight", request, sqlSession);
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * insertServiceInfo(com.ngen.cosys.export.acceptance.model.
	 * MailExportAcceptanceRequest)
	 */
	@Override
	public MailExportAcceptanceRequest insertServiceInfo(MailExportAcceptanceRequest request) throws CustomException {
		int result=super.updateData("updateServiceInfo", request, sqlSession);
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		if(result==0) {
			super.insertData("insertServiceInfo", request, sqlSession);
			mail.setServiceId(request.getServiceId());
		}
	
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * insertDocumentInfo(com.ngen.cosys.export.acceptance.model.
	 * MailExportAcceptanceRequest)
	 */
	@Override
	public MailExportAcceptanceRequest insertDocumentInfo(MailExportAcceptanceRequest request) throws CustomException {
		super.insertData("insertDocumentInformation", request, sqlSession);
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		mail.setDocumentInfoId(request.getDocumentInfoId());
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#insertHouseInfo(
	 * java.util.List)
	 */
	@Override
	public MailExportAcceptanceRequest insertEacceptnaceHouseInfo(List<MailExportAcceptance> request) throws CustomException {
		List<MailExportAcceptance> distinctAwb = request.stream().filter(distinctByKey(obj -> obj.getMailBagNumber()))
				.collect(Collectors.toList());
		int difference = request.size() - distinctAwb.size();
		if (difference > 0) {
			throw new CustomException("MAILEXPORTACCPT05", "eDeliveryForm", ErrorType.ERROR);
		} else {
			super.insertList("insertHouseInfo", request, sqlSession);
			return new MailExportAcceptanceRequest();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * insertDocumentInfoSHC(com.ngen.cosys.export.acceptance.model.
	 * MailExportAcceptanceRequest)
	 */
	@Override
	public MailExportAcceptanceRequest insertDocumentInfoSHC(MailExportAcceptanceRequest request)
			throws CustomException {
		super.insertData("insertDocumentInformationSHC", request, sqlSession);
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * insertShipmentinfo(com.ngen.cosys.export.acceptance.model.
	 * MailExportAcceptanceRequest)
	 */
	@Override
	public MailExportAcceptanceRequest insertShipmentinfo(MailExportAcceptanceRequest request) throws CustomException {
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		int count=super.updateData("updateShipmentMasterPiecesWeight", request, sqlSession);
		if(count==0) {
			super.insertData("insertShipmentInfo", request, sqlSession);
			
			mail.setShipmentId(request.getShipmentId());
		}
		else {
			int shipmentId= super.fetchObject("getShipmentIdDetails", request, sqlSession);
			mail.setShipmentId(shipmentId);
		}
		
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * insertShipmentCustomerInfo(com.ngen.cosys.export.acceptance.model.
	 * MailExportAcceptanceRequest)
	 */
	@Override
	public MailExportAcceptanceRequest insertShipmentCustomerInfo(MailExportAcceptanceRequest request)
			throws CustomException {
		super.insertData("insertShipmentCustomerInfo", request, sqlSession);
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		mail.setShipmentCustomerInfoId(request.getShipmentCustomerInfoId());
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * insertShipmentHandlingAreaInfo(com.ngen.cosys.export.acceptance.model.
	 * MailExportAcceptanceRequest)
	 */
	@Override
	public MailExportAcceptanceRequest insertShipmentHandlingAreaInfo(MailExportAcceptanceRequest request)
			throws CustomException {
		super.insertData("insertShipmentHandlingArea", request, sqlSession);
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		mail.setShipmentMasterHandlAreaId(request.getShipmentMasterHandlAreaId());
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * insertShipmentHouseInfo(java.util.List)
	 */
	@Override
	public MailExportAcceptanceRequest insertShipmentHouseInfo(List<MailExportAcceptance> request)
			throws CustomException {
   super.insertList("insertShipmentHouseInformation", request, sqlSession);
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * insertShipmentSHC(com.ngen.cosys.export.acceptance.model.
	 * MailExportAcceptanceRequest)
	 */
	@Override
	public MailExportAcceptanceRequest insertShipmentSHC(MailExportAcceptanceRequest request) throws CustomException {
		super.insertData("insertShipmentSHC", request, sqlSession);
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * insertShipmentInventory(com.ngen.cosys.export.acceptance.model.
	 * MailExportAcceptanceRequest)
	 */
	@Override
	public MailExportAcceptanceRequest insertShipmentInventory(MailExportAcceptanceRequest request)
			throws CustomException {
		super.insertData("insertShipmentInventory", request, sqlSession);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * insertShipmentInventoryHouse(java.util.List)
	 */
	@Override
	public MailExportAcceptanceRequest insertShipmentInventoryHouse(List<MailExportAcceptance> request)
			throws CustomException {
		super.insertList("insertShipmentInventoryHouse", request, sqlSession);
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		return mail;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * insertShipmentInventorySHC(com.ngen.cosys.export.acceptance.model.
	 * MailExportAcceptanceRequest)
	 */
	@Override
	public MailExportAcceptanceRequest insertShipmentInventorySHC(MailExportAcceptanceRequest request)
			throws CustomException {
		super.insertData("insertShipmentInventorySHC", request, sqlSession);
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#updateHouseInfo(
	 * java.util.List)
	 */
	@Override
	public MailExportAcceptanceRequest updateHouseInfo(List<MailExportAcceptance> request) throws CustomException {
		List<MailExportAcceptance> distinctAwb = request.stream().filter(distinctByKey(obj -> obj.getMailBagNumber()))
				.collect(Collectors.toList());
		int difference = request.size() - distinctAwb.size();
		if (difference > 0) {
			throw new CustomException("MAILEXPORTACCPT05", "eDeliveryForm", ErrorType.ERROR);
		} else {

			super.insertList("updateHouseInfo", request, sqlSession);
			MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
			return mail;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * updateShipmentHouseInfo(java.util.List)
	 */
	@Override
	public MailExportAcceptanceRequest updateShipmentHouseInfo(List<MailExportAcceptance> request)
			throws CustomException {
		super.insertList("updateShipmentHouseInformation", request, sqlSession);
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * updateShipmentInventoryHouse(java.util.List)
	 */
	@Override
	public MailExportAcceptanceRequest updateShipmentInventoryHouse(List<MailExportAcceptance> request)
			throws CustomException {
		super.insertList("updateShipmentInventoryHouse", request, sqlSession);
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * getMailBagNumbereAcceptanceHouse(java.util.List)
	 */
	@Override
	public MailExportAcceptanceRequest getMailBagNumbereAcceptanceHouse(MailExportAcceptance request)
			throws CustomException {
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		int count = super.fetchObject("getMailBagNumbereAcceptanceHouse", request, sqlSession);
		mail.setMailBagNumbereAcceptanceCount(count);
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * updateAcceptanceHouseInfoWeight(java.util.List)
	 */
	@Override
	public MailExportAcceptanceRequest updateAcceptanceHouseInfoWeight(List<MailExportAcceptance> request)
			throws CustomException {
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		super.updateData("updateAcceptanceHouseInfoWeight", request, sqlSession);
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * updateShipmentHouseInfoWeight(java.util.List)
	 */
	@Override
	public MailExportAcceptanceRequest updateShipmentHouseInfoWeight(List<MailExportAcceptance> request)
			throws CustomException {
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		super.updateData("updateShipmentHouseInfoWeight", request, sqlSession);
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * updateInventoryHouseWeight(java.util.List)
	 */
	@Override
	public MailExportAcceptanceRequest updateInventoryHouseWeight(List<MailExportAcceptance> request)
			throws CustomException {
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		super.updateData("updateInventoryHouseWeight", request, sqlSession);
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#
	 * fetchAcceptanceDetails(com.ngen.cosys.export.acceptance.model.
	 * MailExportAcceptanceDetails)
	 */
	@Override
	public List<MailExportAcceptance> fetchAcceptanceDetails(MailExportAcceptanceDetails request)
			throws CustomException {
		List<MailExportAcceptance> list = super.fetchList("fetchAcceptanceDetails", request, sqlSession);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#updateNestedId(
	 * com.ngen.cosys.export.acceptance.model.MailExportAcceptanceDetails)
	 */
	@Override
	public MailExportAcceptanceDetails updateNestedId(MailExportAcceptanceDetails request) throws CustomException {
		MailExportAcceptanceDetails mail = new MailExportAcceptanceDetails();
		int count = super.fetchObject("verifyUldKey", request, sqlSession);
		if (count > 0) {
			super.updateData("updateNestedId", request, sqlSession);
		} else {
		    request.addError("uld.does.not.exist", "nestedStoreLocation", ErrorType.ERROR);
		    if(!request.getMessageList().isEmpty()) {
		         throw new CustomException();
		      }
		}
		return mail;
	}
	
	@Override
    public MailExportAcceptanceDetails updateUldMasterLocation(MailExportAcceptanceRequest request) throws CustomException {
        MailExportAcceptanceDetails mail = new MailExportAcceptanceDetails();
        int count = super.fetchObject("verifyShipmentNumber", request, sqlSession);
        if (count > 0) {
            super.updateData("updateUldMasterLocation", request, sqlSession);
            super.insertData("insertInMovementUld", request, sqlSession);
            
        } else {
        	
           super.insertData("insertUldMasterLocation", request, sqlSession);
           super.insertData("insertInMovementUld", request, sqlSession);
        }
        return mail;
    }
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.acceptance.dao.MailExportAcceptanceDAO#getCountryCode(
	 * com.ngen.cosys.export.acceptance.model.MailExportAcceptanceRequest)
	 */
	@Override
	public MailExportAcceptanceRequest getCountryCode(MailExportAcceptanceRequest request) throws CustomException {
		MailExportAcceptanceRequest mail = new MailExportAcceptanceRequest();
		mail = super.fetchObject("getCountryCode", request, sqlSession);
		return mail;
	}

	/**
	 * @param keyExtractor
	 * @return
	 */
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

   @Override
   public int getMailBagNumberCount(String mailBagNumber) throws CustomException {
      int count = super.fetchObject("getMailNumberCount", mailBagNumber, sqlSession);
      return Integer.valueOf(count);
   }

   @Override
   public MailExportAcceptanceRequest insertOuthouseInfo(List<MailExportAcceptance> mailExportAcceptance)
         throws CustomException {
      MailExportAcceptanceRequest resp = null;
     for(MailExportAcceptance e:mailExportAcceptance ) {
    	 if(e.getFlagCRUD().equalsIgnoreCase("C")) {
    		  Integer data=super.insertData("insertOuthouseInfo", e, sqlSession);
    	 }
     }
      return resp;
   }

@Override
public MailExportAcceptanceRequest getUldDetails(MailExportAcceptanceRequest request) throws CustomException {
	
	return super.fetchObject("getUldDetails", request, sqlSession);
}

@Override
public int updateShipmentMasterComplete(int request) throws CustomException {
	return super.updateData("updateShipmentMasterComplete", request, sqlSession);
}

@Override
public BigInteger getCustomerIdForPAFlight(MailExportAcceptanceRequest requestModel) throws CustomException {
   BigInteger custometId = super.fetchObject("getCustomerIdForPAFlight", requestModel, sqlSession);
   return custometId;
}



}
