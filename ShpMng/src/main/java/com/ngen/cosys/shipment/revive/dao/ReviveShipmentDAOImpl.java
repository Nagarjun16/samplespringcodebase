package com.ngen.cosys.shipment.revive.dao;


import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.shipment.model.SHC;
import com.ngen.cosys.shipment.revive.model.ReviveShipmentModel;
import com.ngen.cosys.shipment.revive.model.ReviveShipmentSummary;

@Repository
public class ReviveShipmentDAOImpl extends BaseDAO implements ReviveShipmentDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<ReviveShipmentModel> getReviveShipmentInfo(ReviveShipmentSummary requestModel) throws CustomException {
		return this.fetchList("sqlGetReviveShipmentInfo", requestModel, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.revive.dao.ReviveShipmentDAO#createInventory(com.ngen
	 * .cosys.shipment.revive.model.ReviveShipmentModel)
	 */
	@Override
	public void createInventory(ReviveShipmentModel requestModel) throws CustomException {
		
		BigInteger checkIfInventoryExists = this.fetchObject("sqlQueryCheckIfInventoryExistsOrNotForReviveCase", requestModel, sqlSessionTemplate);
		
		if(!Objects.isNull(checkIfInventoryExists) && !checkIfInventoryExists.equals(BigInteger.ZERO)) {
			  requestModel.setShipmentInventoryId(checkIfInventoryExists);
			  this.updateData("sqlQueryMergeShipmentInventoryandUpdatePieceWgt", requestModel, sqlSessionTemplate);
		  }else {
			// Move the frieght out to inventory
			  if(MultiTenantUtility.isTenantCityOrAirport(requestModel.getOrigin())){
				  requestModel.setFlightId(null);
			  }
		      this.insertData("sqlMoveShipmentFromFreightOutToInventory", requestModel, sqlSessionTemplate);  
		  }
		
		//Update Storage info with Reference details.
		this.updateData("sqlUpdateShipmentInventoryId", requestModel, sqlSessionTemplate);
		//update Transhipment_TransferManifestByAWB FinalizedDate flag to Null
		List<BigInteger>  transTransferManifestByAwbIds = this.fetchList("sqlGetTrmAWBinfoId", requestModel,sqlSessionTemplate);
		if (!CollectionUtils.isEmpty(transTransferManifestByAwbIds)) {
			for (BigInteger transTransferManifestByAwbId : transTransferManifestByAwbIds) {
				this.updateData("sqlupdateTranshipment_TransferManifestByAWB", transTransferManifestByAwbId,
						sqlSessionTemplate);
			}
		}
		
		 if(!Objects.isNull(requestModel.getInventorySHCSList())) {
			  for(SHC shcList: requestModel.getInventorySHCSList()) {
					//This neutralAWBSHCId is actually i am seting FreightOutId(Reusing the model so not changed any name but using it)
					shcList.setNeutralAWBSHCId(requestModel.getShipmentInventoryId());
					BigInteger inventorySHCCount = this.fetchObject("sqlFetchInventorySHCCountReviveCase", shcList,
							sqlSessionTemplate);
					if(inventorySHCCount.equals(BigInteger.ZERO)) {
						// Move the SHC back to inventory
					      this.insertData("sqlMoveFromFreightOutToShipmentInventorySHC", requestModel, sqlSessionTemplate);
					}
				}
		  }

		// Move the House back to inventory
		this.insertData("sqlMoveFromFreightOutToShipmentInventoryHouse", requestModel, sqlSessionTemplate);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.revive.dao.ReviveShipmentDAO#deleteFreightOut(com.
	 * ngen.cosys.shipment.revive.model.ReviveShipmentModel)
	 */
	@Override
	public void deleteFreightOut(ReviveShipmentModel requestModel) throws CustomException {
		// Delete the Freight Out SHC
		this.deleteData("sqlDeleteShipmentFreightOutFromSHC", requestModel, sqlSessionTemplate);

		// Delete the Freight Out House
		this.deleteData("sqlDeleteShipmentFreightOutFromHouse", requestModel, sqlSessionTemplate);

		// Delete the Freight Out
		this.deleteData("sqlDeleteShipmentFromFreightOut", requestModel, sqlSessionTemplate);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.revive.dao.ReviveShipmentDAO#deleteTRMInfo(com.ngen.
	 * cosys.shipment.revive.model.ReviveShipmentModel)
	 */
	@Override
	public void deleteTRMInfo(ReviveShipmentModel requestModel) throws CustomException {
		// Check if the TRM Number is there for the inventory
		if (!StringUtils.isEmpty(requestModel.getTrmNumber())) {
			// Get the TRM Number reference id
			BigInteger referenceId = this.fetchObject("sqlGetTransshipmentInfoAWBOnRevive", requestModel,
					sqlSessionTemplate);

			// If found
			if (!ObjectUtils.isEmpty(referenceId)) {
				requestModel.setReferenceId(referenceId);

				Boolean isSelectedPiecesEqualToTrmPieces = this.fetchObject("sqlIsSelectedPiecesequaltoTrmPieces",
						requestModel, sqlSessionTemplate);

				if (isSelectedPiecesEqualToTrmPieces) {
					// Delete the Freight Out House
					this.deleteData("sqlDeleteTransshipmentInfoAWBSHCOnRevive", requestModel, sqlSessionTemplate);

					// Delete the Freight Out
					this.deleteData("sqlDeleteTransshipmentInfoAWBOnRevive", requestModel, sqlSessionTemplate);
				}
			}
		}
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.REVIVE_SHIPMENT)
	public void createRemarks(ReviveShipmentModel requestModel) throws CustomException {
		// TODO Auto-generated method stub

		this.insertData("sqlInsertReviveRemarks", requestModel, sqlSessionTemplate);
	}

}