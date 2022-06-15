package com.ngen.cosys.application.dao;

import java.math.BigInteger;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.application.model.AutoFreightoutInventoryModel;
import com.ngen.cosys.application.model.AutoFreightoutModel;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository
public class AutoFreightoutJobDAOImpl extends BaseDAO implements AutoFreightoutJobDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<AutoFreightoutModel> getShipments() throws CustomException {
		return this.fetchList("sqlQueryAutoFreightout", new Object(), sqlSessionTemplate);
	}

	@Override
	public List<AutoFreightoutInventoryModel> getInventoryDetails(AutoFreightoutModel requestModel)
			throws CustomException {
		return this.fetchList("sqlQueryFetchInventoryDetails", requestModel, sqlSessionTemplate);
	}

	@Override
	public void freightoutInventory(AutoFreightoutInventoryModel inventoryModel) throws CustomException {

		// Move the inventory
		this.insertData("moveInventoryToFreightout", inventoryModel, sqlSessionTemplate);

		BigInteger inventorySHCCount = this.fetchObject("sqlFetchInventorySHCCount", inventoryModel,
				sqlSessionTemplate);
		// Move the SHC of an inventory
		if (!ObjectUtils.isEmpty(inventoryModel.getFreightOutId())) {
			if (inventorySHCCount.equals(BigInteger.ZERO)) {
				this.insertData("moveShipmentSHCToFreightoutSHC", inventoryModel, sqlSessionTemplate);
			} else {
				this.insertData("moveInventorySHCToFreightoutSHC", inventoryModel, sqlSessionTemplate);
			}

			// Move the House of an Inventory
			this.insertData("moveInventoryHouseToFreightoutHouse", inventoryModel, sqlSessionTemplate);

			// Delete the inventory SHC
			this.deleteData("deleteInventorySHC", inventoryModel, sqlSessionTemplate);

			// Delete the inventory house
			this.deleteData("deleteInventoryHouse", inventoryModel, sqlSessionTemplate);

			// Delete the inventory
			this.deleteData("deleteInventoryDetails", inventoryModel, sqlSessionTemplate);

		}
	}

	@Override
	public void updateInboundWorksheetShipmentStatus(AutoFreightoutModel requestModel) throws CustomException {

		this.updateData("sqlUpdateAgentPlanningWorksheetShipmentStatus", requestModel, sqlSessionTemplate);

		this.updateData("sqlUpdateDeliveredOnFromAutoDO", requestModel, sqlSessionTemplate);
		
		boolean allPiecesDelivered = this.fetchObject("sqlCheckForAllPiecesDelivered", requestModel,
				sqlSessionTemplate);

		if (allPiecesDelivered) {
			this.updateData("sqlUpdateDeliveredOnFromAutoFreightOutJob", requestModel, sqlSessionTemplate);

			this.updateData("sqlUpdateCaseStatusFromAutoDO", requestModel, sqlSessionTemplate);
		}

	}

}