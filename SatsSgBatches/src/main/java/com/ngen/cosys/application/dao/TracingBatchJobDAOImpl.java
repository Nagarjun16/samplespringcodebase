package com.ngen.cosys.application.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.application.model.TracingShipmentModel;
import com.ngen.cosys.application.model.TracingShipmentModelSHC;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.multitenancy.context.TenantContext;

@Repository
public class TracingBatchJobDAOImpl extends BaseDAO implements TracingBatchJobDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<TracingShipmentModel> fetchUndeliverdShipments() throws CustomException {
		TracingShipmentModel tracingShipmentModel = new TracingShipmentModel();
		tracingShipmentModel.setTenantAirport(TenantContext.get().getTenantConfig().getAirportCode());
		tracingShipmentModel.setTenantCity(TenantContext.get().getTenantConfig().getCityCode());
		return fetchList("getUndeliveredShipments", tracingShipmentModel, sqlSessionTemplate);
	}

	@Override
	public void insertTracing(TracingShipmentModel shipments) throws CustomException {
		insertData("insertTracingDetails", shipments, sqlSessionTemplate);
		if (!CollectionUtils.isEmpty(shipments.getShcArray())) {
			for (TracingShipmentModelSHC shc : shipments.getShcArray()) {
				shc.setComTracingShipmentInfoId(shipments.getComTracingShipmentInfoId());
				insertData("insertTracingDetailsSHC", shc, sqlSessionTemplate);
			}
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void updateTracing(TracingShipmentModel shipments) throws CustomException {
		List<TracingShipmentModel>  hawbList=shipments.getHawbNumber();
		if(CollectionUtils.isEmpty(hawbList)) {
		updateData("updateTracingDetails", shipments, sqlSessionTemplate);
		}
		else {
                //fetch data to insert for house again
				List<TracingShipmentModel> existingShipmentList=this.fetchList("getTracingDataFromComTracingShipmentInfo", shipments, sqlSessionTemplate);
				TracingShipmentModel shipmentInsert=existingShipmentList.get(0);
				Map<BigInteger,String> houseMap=new HashMap<BigInteger,String>();
				for(TracingShipmentModel existingShipment:existingShipmentList) {
					houseMap.put(existingShipment.getComTracingShipmentInfoId(),existingShipment.getHouseNumber());
				}
				shipmentInsert.setCaseStatus(shipments.getCaseStatus());
				int count=0;
				for(TracingShipmentModel house:hawbList) {
					BigInteger shipmentInfoId=BigInteger.ZERO;
					if(!CollectionUtils.isEmpty(houseMap) && house.getHouseNumber()!=null && houseMap.containsValue(house.getHouseNumber())) {
                          for (Map.Entry<BigInteger, String> entry: houseMap.entrySet()) {
				            if (house.getHouseNumber().equals(entry.getValue())) {
				            	shipmentInfoId=entry.getKey();}
				        }
						if(shipments.getComTracingShipmentInfoId()!=null && 
						   shipments.getComTracingShipmentInfoId().compareTo(shipmentInfoId)==0) {
							shipments.setHouseNumber(house.getHouseNumber());
							updateData("updateTracingDetails", shipments, sqlSessionTemplate);
						}
					}else if(houseMap.keySet().size()==1 && ++count==1) {
							shipments.setHouseNumber(house.getHouseNumber());
							updateData("updateTracingDetails", shipments, sqlSessionTemplate);
						}
					else {
					shipmentInsert.setHouseNumber(house.getHouseNumber());
					BigInteger lastCaseNumber = this.fetchObject("getMaxTracingCaseNumber", null, sqlSessionTemplate);
			        lastCaseNumber = lastCaseNumber.add(BigInteger.ONE);
			        String caseNumber = "CT" + StringUtils.leftPad(String.valueOf(lastCaseNumber.intValue()), 5, '0');
			        shipmentInsert.setCaseNumber(caseNumber);
			        insertData("insertTracingDetails", shipmentInsert, sqlSessionTemplate);
					}
				}
			}
		}

	@Override
	public void insertActivity(TracingShipmentModel shipments) throws CustomException {
		if (!ObjectUtils.isEmpty(shipments.getComTracingShipmentInfoId())) {
			insertData("insertActivity", shipments, sqlSessionTemplate);
		}
	}

	@Override
	public BigInteger getMaxCaseNumber() throws CustomException {
		return this.fetchObject("getMaxTracingCaseNumber", null, sqlSessionTemplate);
	}

	@Override
	public void updateShipmentMaster(TracingShipmentModel shipments) throws CustomException {
		updateData("updateShipmentMasterDate", shipments, sqlSessionTemplate);
	}

	@Override
	public boolean checkActivityExist(TracingShipmentModel shipments) throws CustomException {
		return super.fetchObject("checkActivityExist", shipments, sqlSessionTemplate);
	}

}