package com.ngen.cosys.impbd.shipment.breakdown.dao;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.export.commonbooking.model.PartSuffix;
import com.ngen.cosys.export.commonbooking.service.CommonBookingService;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.breakdown.constant.InboundBreakDownSqlId;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentHouseModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentInventoryModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentShcModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.SingleShipmentBookingBreakDown;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster;



@Repository
public class InboundBreakdownDAOImpl extends BaseDAO implements InboundBreakdownDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSessionTemplate;
   
   @Autowired
   private CommonBookingService commonBookingService;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.inboundbreakdown.dao.InboundBreakdownDAO#get(com.ngen.
    * cosys.impbd.inboundbreakdown.model.InboundBreakdownModel)
    */
   @Override
	public InboundBreakdownModel get(InboundBreakdownModel inboundBreakdownModel) throws CustomException {

		InboundBreakdownModel response = fetchObject(InboundBreakDownSqlId.GET_BREAK_DOWN_DATA.toString(),
				inboundBreakdownModel, sqlSessionTemplate);
		if (Objects.nonNull(response) && Objects.nonNull(response.getShipment())) {
			
			//get breakdown instructions
			String breakdownInstruction="";
			response.getShipment().setHawbNumber(inboundBreakdownModel.getHawbNumber());
			List<String> breakdownInstructionList = fetchList("getBreakdownInstruction", response, sqlSessionTemplate);
			if (!CollectionUtils.isEmpty(breakdownInstructionList)) {
				breakdownInstruction = breakdownInstructionList.stream().map(t -> t)
						.collect(Collectors.joining(","+"\n"));
			}
			response.getShipment().setBreakdownInstruction(breakdownInstruction);
			
			PartSuffix partSuffix = new PartSuffix();
			partSuffix.setShipmentNumber(inboundBreakdownModel.getShipment().getShipmentNumber());
			partSuffix.setShipmentDate(inboundBreakdownModel.getShipment().getShipmentdate());
			List<String> flightKeys = new ArrayList<String>();
			flightKeys.add(inboundBreakdownModel.getFlightNumber());
			partSuffix.setFlightKeyList(flightKeys);
			response.setCheckForSQGroupCarrier(this.commonBookingService.methodToCheckSQOrOalShipment(partSuffix));
			List<SingleShipmentBookingBreakDown> bookingDetails = super.fetchList("searchBooking",
					inboundBreakdownModel.getShipment(), sqlSessionTemplate);
			if (!CollectionUtils.isEmpty(bookingDetails)) {
				Set<String> shipmentDuplicateSet = new HashSet<>();
				bookingDetails.stream().filter(e -> shipmentDuplicateSet.add(e.getPartSuffix()))
						.collect(Collectors.toList());
				response.getShipment().setBookingPieces(bookingDetails.get(0).getPieces());
				response.getShipment().setBookingWeight(bookingDetails.get(0).getGrossWeight());
				response.getShipment().setBookingDetails(bookingDetails);
			}
		}
		// Initialize the value for accessLocation
		if (!ObjectUtils.isEmpty(response) && !CollectionUtils.isEmpty(response.getShipment().getInventory())) {
			for (int j = 0; j < response.getShipment().getInventory().size(); j++) {
				if (!StringUtils.isEmpty(response.getShipment().getInventory().get(j).getWarehouseLocation())) {
					InboundBreakdownShipmentInventoryModel data = new InboundBreakdownShipmentInventoryModel();
					data.setWarehouseLocation(response.getShipment().getInventory().get(j).getWarehouseLocation());
					data.setLoggedInUser(inboundBreakdownModel.getLoggedInUser());
					// Check if it is a location in the warehouse, then only give it access
					if (checkIfWarehouseLocationExist(data)) {
						// If location is present in db then access is given, based on the Terminal
						// Access Logic
						response.getShipment().getInventory().get(j)
								.setAccessLocation(!checkValidWarehouseForUser(data));
					} else {
						// If location does not exist in db then we have access to it

						response.getShipment().getInventory().get(j).setAccessLocation(false);
					}

				}
			}
		}
		return response;
	}

   @Override
   public Boolean checkBreakDownStorageInfo(
         InboundBreakdownShipmentInventoryModel inboundBreakdownShipmentInventoryModel) throws CustomException {
      return this.fetchObject(InboundBreakDownSqlId.CHECK_BREAK_DOWN_STORAGE_INFO.getQueryId(),
            inboundBreakdownShipmentInventoryModel, sqlSessionTemplate);
   }

   @Override
   public InboundBreakdownShipmentInventoryModel selectInboundBreakdownShipmentInventoryModel(
         InboundBreakdownShipmentInventoryModel inboundBreakdownShipmentInventoryModel) throws CustomException {
      return this.fetchObject(InboundBreakDownSqlId.SELECT_BREAK_DOWN_STORAGE_INFO.getQueryId(),
            inboundBreakdownShipmentInventoryModel, sqlSessionTemplate);
   }

   @Override
   public Integer insertBreakDownStorageInfo(
         InboundBreakdownShipmentInventoryModel inboundBreakdownShipmentInventoryModel) throws CustomException {
      return this.insertData(InboundBreakDownSqlId.INSERT_BREAK_DOWN_STORAGE_INFO.getQueryId(),
            inboundBreakdownShipmentInventoryModel, sqlSessionTemplate);
   }

   @Override
   public Integer updateBreakDownStorageInfo(
         InboundBreakdownShipmentInventoryModel inboundBreakdownShipmentInventoryModel) throws CustomException {
      return this.updateData(InboundBreakDownSqlId.UPDATE_BREAK_DOWN_STORAGE_INFO.getQueryId(),
            inboundBreakdownShipmentInventoryModel, sqlSessionTemplate);
   }

   @Override
   public Boolean checkBreakDownShipmentHouseModel(
         InboundBreakdownShipmentHouseModel inboundBreakdownShipmentHouseModel) throws CustomException {
      return this.fetchObject(InboundBreakDownSqlId.CHECK_BREAK_DOWN_HOUSE_INFO.getQueryId(),
            inboundBreakdownShipmentHouseModel, sqlSessionTemplate);
   }

   @Override
   public Integer insertBreakDownShipmentHouseModel(
         InboundBreakdownShipmentHouseModel inboundBreakdownShipmentHouseModel) throws CustomException {
      return this.insertData(InboundBreakDownSqlId.INSERT_BREAK_DOWN_HOUSE_INFO.getQueryId(),
            inboundBreakdownShipmentHouseModel, sqlSessionTemplate);
   }

   @Override
   public Integer updateBreakDownShipmentHouseModel(
         InboundBreakdownShipmentHouseModel inboundBreakdownShipmentHouseModel) throws CustomException {
      return this.updateData(InboundBreakDownSqlId.UPDATE_BREAK_DOWN_HOUSE_INFO.getQueryId(),
            inboundBreakdownShipmentHouseModel, sqlSessionTemplate);
   }

   @Override
   public Boolean checkBreakDownStorageSHCInfo(InboundBreakdownShipmentShcModel inboundBreakdownShipmentShcModel)
         throws CustomException {
      return this.fetchObject(InboundBreakDownSqlId.CHECK_BREAK_DOWN_STORAGE_SHC_INFO.getQueryId(),
            inboundBreakdownShipmentShcModel, sqlSessionTemplate);
   }

   @Override
   public Integer insertBreakDownStorageSHCInfo(InboundBreakdownShipmentShcModel inboundBreakdownShipmentShcModel)
         throws CustomException {
      return this.insertData(InboundBreakDownSqlId.INSERT_BREAK_DOWN_STORAGE_SHC_INFO.getQueryId(),
            inboundBreakdownShipmentShcModel, sqlSessionTemplate);
   }

   @Override
   public Integer updateBreakDownStorageSHCInfo(InboundBreakdownShipmentShcModel inboundBreakdownShipmentShcModel)
         throws CustomException {
      return this.updateData(InboundBreakDownSqlId.UPDATE_BREAK_DOWN_SHC_INFO.getQueryId(),
            inboundBreakdownShipmentShcModel, sqlSessionTemplate);

   }

   @Override
   public Integer insertBreakDownULDTrolleyInfo(InboundBreakdownShipmentModel inboundBreakdownShipmentModel)
         throws CustomException {
      return this.insertData(InboundBreakDownSqlId.INSERT_BREAK_DOWN_ULD_TROLLEY_INFO.getQueryId(),
            inboundBreakdownShipmentModel, sqlSessionTemplate);
   }

   @Override
   public Integer updateBreakDownULDTrolleyInfo(InboundBreakdownShipmentModel inboundBreakdownShipmentModel)
         throws CustomException {
      return this.updateData(InboundBreakDownSqlId.UPDATE_BREAK_DOWN_ULD_TROLLEY_INFO.getQueryId(),
            inboundBreakdownShipmentModel, sqlSessionTemplate);
   }

   @Override
   public InboundBreakdownShipmentModel selectBreakDownULDTrolleyInfo(
         InboundBreakdownShipmentModel inboundBreakdownShipmentModel) throws CustomException {
      return this.fetchObject(InboundBreakDownSqlId.SELECT_BREAK_DOWN_ULD_TROLLEY_INFO.getQueryId(),
            inboundBreakdownShipmentModel, sqlSessionTemplate);
   }

   @Override
   public InboundBreakdownShipmentModel checkTransferType(InboundBreakdownShipmentModel shipmentData)
         throws CustomException {
      return this.fetchObject(InboundBreakDownSqlId.CHECK_TRANSFER_TYPE.getQueryId(), shipmentData, sqlSessionTemplate);
   }

   @Override
   public InboundBreakdownShipmentModel checkHandlingMode(InboundBreakdownShipmentModel shipmentData)
         throws CustomException {
      return this.fetchObject(InboundBreakDownSqlId.CHECK_HANDLING_MODE.getQueryId(), shipmentData, sqlSessionTemplate);
   }

   @Override
   public void updateAgentPlanningWorksheetShipmentStatus(ShipmentMaster requestModel) throws CustomException {
      updateData("updateECCPlanningShipmentStatus", requestModel, sqlSessionTemplate);
   }

   @Override
   public InboundBreakdownShipmentModel fetchShipmentType(InboundBreakdownShipmentModel inboundBreakdownModel)
         throws CustomException {
      return fetchObject("sqlGetShipmentType", inboundBreakdownModel, sqlSessionTemplate);
   }

   @Override
   public void deleteStorageInfo(InboundBreakdownShipmentInventoryModel inventoryData) throws CustomException {
	  deleteData("deleteStorageHouseInfo", inventoryData, sqlSessionTemplate);
      deleteData("deleteShipmentStorage", inventoryData, sqlSessionTemplate);
   }

   @Override
   public void deleteTrolleyInfo(InboundBreakdownShipmentModel shipmentInfo) throws CustomException {
      deleteData("deleteULDTrolleyIfNoInventoryExist", shipmentInfo, sqlSessionTemplate);
      deleteData("sqlDeleteShipmentVerificationBreakDown", shipmentInfo, sqlSessionTemplate);

   }

   @Override
   public void deleteInventoryInfo(InboundBreakdownShipmentModel inventoryData) throws CustomException {
      deleteData("deleteShipmentinventory", inventoryData, sqlSessionTemplate);

   }
   
   @Override
   public boolean checkInventoryExists(InboundBreakdownShipmentInventoryModel inventoryData) throws CustomException {
      return fetchObject("sqlCheckInventoryExists", inventoryData, sqlSessionTemplate);
   }

   @Override
   public void deleteStorageSHCInfo(InboundBreakdownShipmentInventoryModel inventoryData) throws CustomException {
      deleteData("deleteShipmentStorageSHC", inventoryData, sqlSessionTemplate);
   }

   @Override
   public void deleteInventorySHCInfo(InboundBreakdownShipmentModel inventoryData) throws CustomException {
      deleteData("deleteShipmentInventorySHC", inventoryData, sqlSessionTemplate);
   }

   @Override
   public List<InboundBreakdownShipmentModel> fetchInventoryId(InboundBreakdownShipmentModel shipmentId)
         throws CustomException {
      return fetchList("sqlGetInboundBreakdowninventoryid", shipmentId, sqlSessionTemplate);
   }
   
   @Override
   public List<InboundBreakdownShipmentModel> fetchHouseInventoryId(InboundBreakdownShipmentModel shipmentId)
         throws CustomException {
      return fetchList("sqlGetInboundBreakdownHouseinventoryid", shipmentId, sqlSessionTemplate);
   }
   
   @Override
   public BigInteger isInventoryExistInLocation(InboundBreakdownShipmentInventoryModel inboundBreakdownModel)
         throws CustomException {
      return fetchObject("sqlInventoryExistinLocation", inboundBreakdownModel, sqlSessionTemplate);
   }

   @Override
   public InboundBreakdownShipmentModel fetchHouseDetails(InboundBreakdownShipmentModel inboundBreakdownModel)
         throws CustomException {
      return fetchObject("sqlGetHouseBreakDownInstructions", inboundBreakdownModel, sqlSessionTemplate);
   }

   @Override
   public BigInteger checkDeliveryInitiated(InboundBreakdownShipmentInventoryModel inboundBreakdownModel)
         throws CustomException {
      return fetchObject("sqlCheckDeliveryInitiated", inboundBreakdownModel, sqlSessionTemplate);
   }
   @Override
   public BigInteger checkTrmInitiated(InboundBreakdownShipmentInventoryModel inboundBreakdownModel)
			throws CustomException {
		BigInteger count = BigInteger.ZERO;
		if (!ObjectUtils.isEmpty(inboundBreakdownModel.getInventoryId())) {
			count = fetchObject("sqlTrmInitiated", inboundBreakdownModel, sqlSessionTemplate);
		}
		return count;
	}

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.shipment.breakdown.dao.InboundBreakdownDAO#
    * isInventoryPieceWeightGreaterThanShipmentMasterPieceWeight(com.ngen.cosys.
    * impbd.shipment.breakdown.model.InboundBreakdownShipmentModel)
    */
   @Override
   public Boolean isInventoryPieceWeightGreaterThanShipmentMasterPieceWeight(InboundBreakdownShipmentModel requestModel)
         throws CustomException {
      return this.fetchObject("sqlCheckShipmentMasterPiecesWeightGreaterThanInventoryFreightOutPiecesWeight",
            requestModel, sqlSessionTemplate);
   }

@Override
public InboundBreakdownShipmentModel getUtilisedPieces(InboundBreakdownShipmentModel requestModel)
		throws CustomException {
	return fetchObject("sqlGetTotalUtilisedPieces", requestModel, sqlSessionTemplate);
}

@Override
public InboundBreakdownShipmentModel getFreightOutUtilisedPieces(InboundBreakdownShipmentModel requestModel)
		throws CustomException {
	return fetchObject("sqlGetFreightOutTotalUtilisedPieces", requestModel, sqlSessionTemplate);
}

@Override
public List<String> getChegeableLocationSHC(InboundBreakdownShipmentInventoryModel value) throws CustomException {
	
	return super.fetchList("chargeableSHCs", value, sqlSessionTemplate);
}

@Override
public List<String> getInventoryBillingGroup(InboundBreakdownShipmentInventoryModel value) throws CustomException {
	
	return super.fetchList("inventoryBillingGroups", value, sqlSessionTemplate);
}

@Override
public List<String> getInventoryBillingGroupHandlingCodes(InboundBreakdownShipmentInventoryModel value) throws CustomException {
	
	return super.fetchList("inventoryBillingGroupsSpecialHandlingCode", value, sqlSessionTemplate);
}

@Override
public BigInteger getTotalRcfPicesFormStatusUpdateEvent(InboundBreakdownShipmentModel shipmentModel)
		throws CustomException {
	return fetchObject("getTotalRCFStatusupdateEventPices", shipmentModel, sqlSessionTemplate);
}

@Override
public InboundBreakdownShipmentInventoryModel validateInventory(InboundBreakdownShipmentInventoryModel value)
		throws CustomException {
	return fetchObject("validateInventoryInfo", value, sqlSessionTemplate);
}

public LocalDateTime getLastUpdatedDateTime (InboundBreakdownShipmentModel shipmentModel)
		throws CustomException{
	return fetchObject("getLastUpdatedTimeforShipmentVerification", shipmentModel, sqlSessionTemplate);
}

@Override
public boolean checkValidWarehouseForUser(InboundBreakdownShipmentInventoryModel data) throws CustomException {
	Boolean isRestrictedHandlingAreaExists = false;
	Boolean isValidLocation = true;
	isRestrictedHandlingAreaExists = fetchObject("isRestrictedHandlingAreaExistsBreakDown",data,sqlSessionTemplate);
	if(isRestrictedHandlingAreaExists) {		
		isValidLocation = fetchObject("sqlCheckAllowedLocationBreakDown", data, sqlSessionTemplate);
	}else {
		Boolean isRestrictedTerminalLocation = fetchObject("sqlCheckLocationInRestrictedTerminalBreakDown", data,
				sqlSessionTemplate);
		if (isRestrictedTerminalLocation) {
			isValidLocation = false;
		}
	}
	return isValidLocation;
}
@Override
public boolean checkIfWarehouseLocationExist(InboundBreakdownShipmentInventoryModel data) throws CustomException {
		
		return fetchObject("checkIfWarehouseLocationExist",data,sqlSessionTemplate);

}

@Override
public Boolean isRampUldVerified(InboundBreakdownShipmentInventoryModel inventory) throws CustomException {
		
		return fetchObject("isRampUldVerified",inventory,sqlSessionTemplate);

}

@Override
public InboundBreakdownShipmentModel getTotalHousePiecesWeight(InboundBreakdownShipmentModel shipmentModel)
		throws CustomException {
	
	return  fetchObject("sqlHouseTotalPicesWeight",shipmentModel ,sqlSessionTemplate);
}
}