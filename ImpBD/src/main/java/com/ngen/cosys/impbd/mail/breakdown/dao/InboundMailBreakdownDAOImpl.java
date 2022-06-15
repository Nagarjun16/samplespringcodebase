package com.ngen.cosys.impbd.mail.breakdown.dao;

import java.math.BigInteger;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.mail.breakdown.constant.InboundMailBreakDownSqlId;
import com.ngen.cosys.impbd.mail.breakdown.model.InboundMailBreakDownModel;
import com.ngen.cosys.impbd.mail.breakdown.model.InboundMailBreakDownShipmentModel;
import com.ngen.cosys.impbd.mail.breakdown.model.UldDestinationEntry;
import com.ngen.cosys.impbd.mail.breakdown.model.UldDestinationRequest;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentInventoryModel;
import com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerificationModel;

@Repository
public class InboundMailBreakdownDAOImpl extends BaseDAO implements InboundMailBreakDownDAO {
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	@Override
	public InboundMailBreakDownModel flightDetail(InboundMailBreakDownModel requestModel) throws CustomException {

		return super.fetchObject(InboundMailBreakDownSqlId.GET_FLIGHTDETAIL.toString(), requestModel, sqlSession);
	}

	@Override
	public InboundMailBreakDownModel search(InboundMailBreakDownModel requestModel) throws CustomException {

		return super.fetchObject(InboundMailBreakDownSqlId.SEARCH_MAILBREAKDOWN_WORKINGLIST.toString(), requestModel,
				sqlSession);
	}

	@Override
	public InboundMailBreakDownModel searchMailBreakDown(InboundMailBreakDownModel requestModel)
			throws CustomException {
		return super.fetchObject(InboundMailBreakDownSqlId.SEARCH_MAILBREAKDOWN.toString(), requestModel, sqlSession);
	}

	@Override
	public InboundMailBreakDownModel flightData(InboundMailBreakDownModel requestModel) throws CustomException {
		return super.fetchObject(InboundMailBreakDownSqlId.GET_FLIGHTDETAILS.toString(), requestModel, sqlSession);
	}

	@Override
	public int getMailBagNumberCount(String mailBagNumber) throws CustomException {
		return super.fetchObject(InboundMailBreakDownSqlId.GET_MAILBAGNUMBERCOUNT.toString(), mailBagNumber,
				sqlSession);
	}

	
	@Override
	public List<InboundMailBreakDownShipmentModel> updateOutgoingCarrier(
			List<InboundMailBreakDownShipmentModel> requestModel) throws CustomException {
		for (InboundMailBreakDownShipmentModel value : requestModel) {
			Integer count = fetchObject("countShipment", value, sqlSession);
			if (count > 0) {
				updateData("sqlUpdateMasterRouting", value, sqlSession);
			} else {
				insertData("sqlInsertMasterRouting", value, sqlSession);
			}
		}
		return requestModel;
	}

	
	@Override
	public List<InboundMailBreakDownShipmentModel> insertOutgoingCarrier(
			List<InboundMailBreakDownShipmentModel> requestModel) throws CustomException {
		for (InboundMailBreakDownShipmentModel value : requestModel) {
			Integer count = fetchObject("sqlCheckOutgoingCarrier", value, sqlSession);
			if (count >= 1) {
				updateData("sqlUpdateMasterRouting", value, sqlSession);
			} else {
				insertData("sqlInsertMasterRouting", value, sqlSession);
			}
		}
		return requestModel;
	}

	
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAIL_BREAKDOWN)
	public List<InboundMailBreakDownShipmentModel> deleteOutgoingCarrier(
			List<InboundMailBreakDownShipmentModel> requestModel) throws CustomException {

		for (InboundMailBreakDownShipmentModel value : requestModel) {
			// Update pieces when it is greater than 1, each house/mailbag is 1 piece

			BigInteger breakDownPiece = fetchObject("getBreakDownPieces", value, sqlSession);
			value.setBreakDownPieces(breakDownPiece);
			if (breakDownPiece != null && breakDownPiece.compareTo(BigInteger.ONE) > 0) {
				updateData("updateShipmentVerificationOnBreakDownPieces", value, sqlSession);
			} else {
				deleteData("deleteShipmentHouseInfo", value, sqlSession);
				deleteData("deleteShipmentStorageSHCInfo", value, sqlSession);
				deleteData("deleteShipmentStorageInfo", value, sqlSession);
				deleteData("deleteShipmentBreakDownTrolleyInfo", value, sqlSession);
				deleteData("deleteShipmentVerification", value, sqlSession);

			}
			// Delete from Shipment Inventory House
			deleteData("deleteShipmentInventoryHouse", value, sqlSession);

			// Update pieces when it is greater than 1.
			BigInteger pieces = fetchObject("getInventoryPieces", value, sqlSession);
			value.setPieces(pieces);
			if (pieces != null && pieces.compareTo(BigInteger.ONE) > 0) {
				updateData("updateShipmentInventoryPieces", value, sqlSession);
			} else {
				deleteData("deleteShipmentInventorySHCInfo", value, sqlSession);
				deleteData("deleteShipmentInventory", value, sqlSession);
			}

			// Delete from Shipment house verification
			deleteData("deleteShipmentHouseVerification", value, sqlSession);
		}

		return requestModel;
	}

	public int checkBreakdownComplete(BigInteger flightId) throws CustomException {
		return super.fetchObject(InboundMailBreakDownSqlId.GET_BREAKDOWN_COMPLETE_CHECK.toString(), flightId,
				sqlSession);
	}

	@Override
	public void insertDataintoEmbargoFailure(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		Integer count = fetchObject("selectCountFromAirmailEmbargoFailure", requestModel, sqlSession);
		if (count < 1) {
			insertData("insertIntoAirmailEmbargoFaliure", requestModel, sqlSession);
		}
	}

	@Override
	public BigInteger getShipmentHouseInformationId(InboundMailBreakDownShipmentModel requestModel)
			throws CustomException {
		return fetchObject("getShipmentHouseId", requestModel, sqlSession);
	}

	@Override
	public String getMailShipmentType(String requestModel) throws CustomException {
		return fetchObject("getShipmentMailType", requestModel, sqlSession);
	}

	@Override
	public void updateShipmentMasterComplete(BigInteger request) throws CustomException {
		updateData("updateShipmentMasterComplete", request, sqlSession);
	}

	@Override
	public ShipmentVerificationModel getShipmentVerificationPiecesAndWeight(ShipmentVerificationModel requestModel)
			throws CustomException {
		return fetchObject("getPiecesAndWeightForShipmentVerification", requestModel, sqlSession);

	}

	@Override
	public InboundBreakdownShipmentInventoryModel getShipmentInventoryPiecesAndWeight(
			InboundBreakdownShipmentInventoryModel requestModel) throws CustomException {
		return fetchObject("getPiecesAndWeightForShipmentInventory", requestModel, sqlSession);

	}

	@Override
	public InboundBreakdownShipmentInventoryModel getPiecesAndWeightForStorageInfo(
			InboundBreakdownShipmentInventoryModel requestModel) throws CustomException {
		return fetchObject("getPiecesAndWeightForStorageInfo", requestModel, sqlSession);
	}

	/* Delete Function Dao Methods starts here */
	/*
	 * Delete Function which checks if there is any SHipment house Informn Avalaible
	 * or not
	 */
	@Override
	public int checkShipmentHouseInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		int countShpHse = fetchObject("checkShipmentHouseInfo", requestModel, sqlSession);
		return countShpHse;
	}

	/* Delete Function which checks if there is any inventory houses are availble */
	@Override
	public int fetchInvHseInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		int countInvHse = fetchObject("fetchInvHseInfo", requestModel, sqlSession);
		return countInvHse;
	}

	/*
	 * Delete Function which deletes inventory SHC info before deleting inventory
	 * info
	 */
	@Override
	public void deleteInvntryShc(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		deleteData("deleteInvntryShc", requestModel, sqlSession);

	}

	/*
	 * Delete Function which deletes inventory info if there is no inventory houses
	 * available i.e Piece & Weight of inv is 0
	 */
	@Override
	public void deleteInvntry(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		deleteData("deleteInvntry", requestModel, sqlSession);
	}

	/*
	 * Delete Function which deletes breakdown house info before deleting breakdown
	 * storage info
	 */
	@Override
	public void deletebreakdownHouseInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		deleteData("deletebreakdownHouseInfo", requestModel, sqlSession);
	}

	/*
	 * Delete Function which updates breakdown storage Piece & Weight info if there
	 * are multiple houses and one of them is deleted
	 */
	@Override
	public void updateBreakdownStorageInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		updateData("updateBreakdownStorageInfo", requestModel, sqlSession);
	}

	/*
	 * Delete Function which fetches Piece & weight info of breakdown storage info
	 * to judge wethr to delete storg or update
	 */
	@Override
	public int fetchBreakdownStorageInfoPieceWeight(InboundMailBreakDownShipmentModel requestModel)
			throws CustomException {
		int countBrkDwnPcWgt = fetchObject("fetchBreakdownStorageInfoPieceWeight", requestModel, sqlSession);
		return countBrkDwnPcWgt;
	}

	/*
	 * Delete Function which deletes breakdown storage shc info before deleteing
	 * Breakdown storage info
	 */
	@Override
	public void deleteBreakdownStorageShcInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		deleteData("deleteBreakdownStorageShcInfo", requestModel, sqlSession);
	}

	/*
	 * Delete Function which deletes breakdown storage info if no breakdown houses
	 * are left or piece/weight of breakdown storage left are 0
	 */
	@Override
	public void deleteBreakdownStorageInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		deleteData("deleteBreakdownStorageInfo", requestModel, sqlSession);
	}

	/* Delete Function which deletes trolley info if there is no storage left */
	@Override
	public void deleteBreakdownTrolleyInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		deleteData("deleteBreakdownTrolleyInfo", requestModel, sqlSession);
	}

	/*
	 * Delete Function which deletes verification info if there is no trolley left
	 */
	@Override
	public void deleteShipmentVerification(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		deleteData("deletePermanentShipmentVerification", requestModel, sqlSession);
	}

	/*
	 * Delete Function which updates verification piece & weight if breakdown house
	 * is deleted
	 */
	@Override
	public void updateShipmentVerificationinfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		updateData("updateShipmentVerificationinfo", requestModel, sqlSession);
	}

	/*
	 * Delete Function which deletes Shipment house info if there is no mailbag left
	 * of that perticular shipment
	 */
	@Override
	public void deleteShpHseInf(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		deleteData("deleteShpHseInf", requestModel, sqlSession);
	}

	/* Delete Function which deletes routing info */
	@Override
	public void deleteShipmentMasterRoutingInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		deleteData("deleteShipmentMasterRoutingInfo", requestModel, sqlSession);
	}

	/*
	 * Delete Function which delete shipment master info if there is no house left
	 */
	@Override
	public void deleteShipmentMaster(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		deleteData("deletePermanentShipmentMaster", requestModel, sqlSession);
	}

	/* Delete Function which deletes inventory houses */
	@Override
	public void deleteInventoryHouse(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		deleteData("deleteInventoryHouse", requestModel, sqlSession);
	}

	/* Delete Function which updates inv Piece & Weight if any mailbag is dleted */
	@Override
	public void updateInventoryPieceWeight(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		updateData("updateInventoryPieceWeight", requestModel, sqlSession);
	}

	/*
	 * Delete Function which fetches invenotry piece & Weight to judge it should get
	 * deleted or updated
	 */
	@Override
	public int getPieceWeightInventryCheck(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		int invPieceWeight = fetchObject("getPieceWeightInventryCheck", requestModel, sqlSession);
		return invPieceWeight;
	}
	/* Delete Function ends here */

	@Override
	public String getContentCode(String shipmentLocation) throws CustomException {
		// TODO Auto-generated method stub
		return super.fetchObject("getContentCode", shipmentLocation, sqlSession);
	}

	@Override
	public int getLoadedSHC(String shipmentLocation) throws CustomException {

		return super.fetchObject("getLoadedSHC", shipmentLocation, sqlSession);
	}

	@Override
	public List<UldDestinationEntry> getUldDestinations(UldDestinationRequest destinationRequest)
			throws CustomException {
		return super.fetchList("getUldContainerdestination", destinationRequest, sqlSession);
	}

	@Override
	public String checkContainerDestinationForBreakDown(String shipmentLocation) throws CustomException {
		return super.fetchObject("checkContainerDestinationForBreakDown", shipmentLocation, sqlSession);
	}

	@Override
	public BigInteger getBreakDownStoragePiecesInfo(InboundMailBreakDownShipmentModel requestModel)
			throws CustomException {
		return super.fetchObject("getBreakDownStoragePiecesInfo", requestModel, sqlSession);
	}

	@Override
	public BigInteger getShpVerPiecesInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
		return super.fetchObject("getShpVerPiecesInfo", requestModel, sqlSession);
	}

   @Override
   public int checkMailBagAlreadyBrokenDown(InboundMailBreakDownShipmentModel requestModel) throws CustomException {
      return fetchObject("sqlGetAlreadyBrokenDownBagCount", requestModel, sqlSession);
   }
}
