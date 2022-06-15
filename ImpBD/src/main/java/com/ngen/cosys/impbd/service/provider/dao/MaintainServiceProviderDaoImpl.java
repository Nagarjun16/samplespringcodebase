/**
 * 
 * MaintainServiceProviderDaoImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 31 May, 2018 NIIT -
 */
package com.ngen.cosys.impbd.service.provider.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.constants.SqlIDs;
import com.ngen.cosys.impbd.service.provider.model.ServiceProviderModel;

/**
 * This class takes care of the responsibilities related to the Maintain Service
 * Provider DAO operation that comes from the service.
 * 
 * @author NIIT Technologies Ltd.
 *
 */
@Repository
public class MaintainServiceProviderDaoImpl extends BaseDAO implements MaintainServiceProviderDao {

	private Logger LOGGER = LoggerFactory.getLogger(MaintainServiceProviderDaoImpl.class);
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionImpSP;

	private static final String FETCH_CARRIER_ID_ERROR = "FLIGHT_ID_NO";

	/**
	 * Find Maintain Service Provider Details using Service Code.
	 * 
	 * @param Service
	 *            Code
	 * @return ServiceProviderModel
	 * @throws CustomException
	 */
	public List<ServiceProviderModel> getAllServiceProvider(ServiceProviderModel serviceProviderModel)
			throws CustomException {
		List<ServiceProviderModel> allList = fetchList(SqlIDs.SQL_GET_SERVICE_PROVIDER_INFO.toString(),
				serviceProviderModel, sqlSessionImpSP);

		return allList;
	}

	/**
	 * insert Maintain Service Provider Details using Service Code.
	 * 
	 * @param Service
	 *            Code
	 * @return ServiceProviderModel
	 * @throws CustomException
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.MASTER, eventName = NgenAuditEventType.MAINTAIN_SERVICE_PROVIDER)
	public List<ServiceProviderModel> createServiceProvider(List<ServiceProviderModel> createService)
			throws CustomException {
		int count = 0;
		Map<String, String> tempMap = new HashMap<>();

		ServiceProviderModel listAll = new ServiceProviderModel();
		ServiceProviderModel serviceId = new ServiceProviderModel();
		for (ServiceProviderModel checkList : createService) {
			if (!StringUtils.isEmpty(checkList.getCarrier())&& !StringUtils.isEmpty(checkList.getFlightKey())) {
				checkList.setFlightNo(checkList.getCarrier().concat(checkList.getFlightKey()));
			}else if(!StringUtils.isEmpty(checkList.getCarrier())) {
				checkList.setFlightNo(checkList.getCarrier());
			}else if(!StringUtils.isEmpty(checkList.getFlightKey())) {
				checkList.setFlightNo(checkList.getFlightKey());
			}

  			if (count > 0 && !StringUtils.isEmpty(checkList.getFlightNo())) {
				for (Map.Entry<String, String> entry : tempMap.entrySet()) {
					if (entry.getKey().equalsIgnoreCase(checkList.getTerminalCode())) {
						if (entry.getValue()!=null&&entry.getValue().equalsIgnoreCase(checkList.getFlightNo())) {
							throw new CustomException(FETCH_CARRIER_ID_ERROR, "", ErrorType.ERROR);
						}
						
					}
				}

			}

			tempMap.put(checkList.getTerminalCode(), checkList.getFlightNo());
			count++;
		}

		for (ServiceProviderModel list : createService) {
			if(StringUtils.isEmpty(list.getTerminalCode())) {
				throw new CustomException("TERMINAL_REQUIRED", "effectiveDateFrom", ErrorType.ERROR);
			}
			if(StringUtils.isEmpty(list.getServiceProviderType())) {
				throw new CustomException("import.service.provider.type.required", "", ErrorType.ERROR);
			}
			listAll.setTerminalCode(list.getTerminalCode());
			serviceId.setCustomerCode(list.getCustomerCode());
			serviceId.setCustomerShortName(list.getCustomerShortName());
			listAll = super.fetchObject(SqlIDs.SQL_TERMINAL_ID.toString(), listAll, sqlSessionImpSP);
			serviceId = super.fetchObject(SqlIDs.SQL_CUSTOMER_ID.toString(), serviceId, sqlSessionImpSP);
			if (list.getServiceProviderId() != null) {
				list.setTerminalId(listAll.getTerminalId());
				list.setCustomerId(serviceId.getCustomerId());
				super.updateData(SqlIDs.SQL_UPDATE_SERVICE_PROVIDER.toString(), list, sqlSessionImpSP);
			} else {
				list.setTerminalId(listAll.getTerminalId());
				list.setCustomerId(serviceId.getCustomerId());
				if (list.getEffectiveDateFrom() != null) {
					super.insertData(SqlIDs.SQL_CREATE_SERVICE_PROVIDER.toString(), list, sqlSessionImpSP);
				} else {
					throw new CustomException("EFFECTIVE_DATE_FROM_REQUIRED", "effectiveDateFrom", ErrorType.ERROR);
				}

			}

		}
		return createService;
	}

	/**
	 * Update Maintain Service Provider Details using Service Code.
	 * 
	 * @param Service
	 *            Code
	 * @return ServiceProviderModel
	 * @throws CustomException
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.MASTER, eventName = NgenAuditEventType.MAINTAIN_SERVICE_PROVIDER)
	public List<ServiceProviderModel> updateServiceProvider(List<ServiceProviderModel> updateService)
			throws CustomException {
		ServiceProviderModel listAllId = new ServiceProviderModel();
		ServiceProviderModel listAllCode = new ServiceProviderModel();
		for (ServiceProviderModel list : updateService) {
			listAllCode.setCustomerCode(list.getServiceProviderCodeNew());
			listAllCode.setCustomerShortName(list.getServiceProviderCodeOld());
			listAllId.setTerminalCode(list.getTerminalCode());
			listAllCode = super.fetchObject(SqlIDs.SQL_CUSTOMER_ID_LIST.toString(), listAllCode, sqlSessionImpSP);
			listAllId = super.fetchObject(SqlIDs.SQL_TERMINAL_ID.toString(), listAllId, sqlSessionImpSP);
			String flightKey = list.getFlightKeyType();
			if (list.getServiceProviderId() != null) {
				// list.setCarrier(flightName.substring(0,2));
				// list.setFlightKey(flightName.substring(2));
				list.setCustomerId(listAllCode.getCustomerId());
				list.setTerminalId(listAllId.getTerminalId());
				// list.setFlightKey(list.getFlightKey());
				// list.setCarrier(flightKey.substring(0,2));
				super.updateData(SqlIDs.SQL_UPDATE_SERVICE_PROVIDER.toString(), list, sqlSessionImpSP);
			} else {
				// list.setCarrier(flightName.substring(0,2));
				// list.setFlightKey(flightName.substring(2));
				list.setCustomerId(listAllCode.getCustomerId());
				list.setTerminalId(listAllId.getTerminalId());
				list.setEffectiveDateFrom(LocalDate.now());
				list.setCarrier(flightKey.substring(0, 2));
				list.setFlightKey(list.getFlightKey());
				super.insertData(SqlIDs.SQL_CREATE_SERVICE_PROVIDER.toString(), list, sqlSessionImpSP);
			}
		}
		return updateService;
	}

	/**
	 * Delete Maintain Service Provider Details using Service Code.
	 * 
	 * @param Service
	 *            Code
	 * @return ServiceProviderModel
	 * @throws CustomException
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.MASTER, eventName = NgenAuditEventType.MAINTAIN_SERVICE_PROVIDER)
	public List<ServiceProviderModel> deleteServiceProvider(List<ServiceProviderModel> deleteService)
			throws CustomException {
		if (deleteService != null) {
			super.deleteData(SqlIDs.SQL_DELETE_SERVICE_PROVIDER.toString(), deleteService, sqlSessionImpSP);
		}
		return deleteService;
	}

	/**
	 * TerminalId Maintain Service Provider Details using Service Code.
	 * 
	 * @param Service
	 *            Code
	 * @return ServiceProviderModel
	 * @throws CustomException
	 */
	public List<ServiceProviderModel> terminalId(ServiceProviderModel deleteService) throws CustomException {
		List<ServiceProviderModel> all = super.fetchObject(SqlIDs.SQL_TERMINAL_ID.toString(), deleteService,
				sqlSessionImpSP);
		return all;
	}

	/**
	 * Edit Maintain Service Provider Details using Service Code.
	 * 
	 * @param Service
	 *            Code
	 * @return ServiceProviderModel
	 * @throws CustomException
	 */
	public List<ServiceProviderModel> editServiceProvider(List<ServiceProviderModel> editService)
			throws CustomException {
		List<ServiceProviderModel> all = new ArrayList<ServiceProviderModel>();
		for (ServiceProviderModel editData : editService) {
			if (editData.isScInds() != false) {
				ServiceProviderModel data = new ServiceProviderModel();
				data.setServiceProviderId(editData.getServiceProviderId());
				all = super.fetchList(SqlIDs.SQL_EDIT_SERVICE.toString(), data, sqlSessionImpSP);
			}
		}
		return all;
	}
}
