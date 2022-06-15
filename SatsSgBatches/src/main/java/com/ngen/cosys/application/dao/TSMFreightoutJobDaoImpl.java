package com.ngen.cosys.application.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.application.model.TSMFreightoutModel;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository
public class TSMFreightoutJobDaoImpl extends BaseDAO implements TSMFreightoutJobDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	private static final String SQL_FOR_DATA_SYNC_FUNCTIONING = "sqlQueryDatasyncFunctioningTSMJOB";

	@Override
	public List<TSMFreightoutModel> getShipmentInfoForFreightOut() throws CustomException {
		List<TSMFreightoutModel> shipmentList=new ArrayList<TSMFreightoutModel>();
		String data = "";
		Integer isDataSyncFunctioning = sqlSessionTemplate.selectOne(SQL_FOR_DATA_SYNC_FUNCTIONING);
		if(isDataSyncFunctioning > 0) {
			shipmentList=this.fetchList("sqlQueryTSMOutGoingShipments", data, sqlSessionTemplate);
		}
		return shipmentList;
	}

	@Override
	public List<TSMFreightoutModel> getTSMOutgoingInventoryDetails(TSMFreightoutModel requestModel)
			throws CustomException {
		return this.fetchList("sqlQueryFetchInventoryDetailsTSM", requestModel, sqlSessionTemplate);
	}

	@Override
	public void tsmInventoryToFreightOut(TSMFreightoutModel inventoryModel) throws CustomException {
		// Move the inventory
		this.insertData("moveInventoryToFreightoutTSM", inventoryModel, sqlSessionTemplate);
		BigInteger inventorySHCCount = this.fetchObject("sqlFetchInventorySHCCountTSM", inventoryModel,
				sqlSessionTemplate);
		if (!ObjectUtils.isEmpty(inventoryModel.getFreightOutId())) {

			if (inventorySHCCount.equals(BigInteger.ZERO)) {
				this.insertData("moveShipmentSHCToFreightoutSHCTSM", inventoryModel, sqlSessionTemplate);
			} else {
				this.insertData("moveInventorySHCToFreightoutSHCTSM", inventoryModel, sqlSessionTemplate);
			}
			
			// Move the House of an Inventory
			this.insertData("moveInventoryHouseToFreightoutHouseTSM", inventoryModel, sqlSessionTemplate);

			// Delete the inventory SHC
			this.deleteData("deleteInventorySHCTSM", inventoryModel, sqlSessionTemplate);

			// Delete the inventory house
			this.deleteData("deleteInventoryHouseTSM", inventoryModel, sqlSessionTemplate);

			// Delete the inventory
			this.deleteData("deleteInventoryDetailsTSM", inventoryModel, sqlSessionTemplate);
			
			inventoryModel.setRemarks("TRANSFERRED TO COSYS");
			this.insertData("sqlInsertAutoDOShipmentRemarksTSM", inventoryModel, sqlSessionTemplate);
			this.updateData("updateDataSyncOutShipmentInfo", inventoryModel, sqlSessionTemplate);
			this.updateData("updateDataSyncInShipmentInfo", inventoryModel, sqlSessionTemplate);
		}
	}

}
