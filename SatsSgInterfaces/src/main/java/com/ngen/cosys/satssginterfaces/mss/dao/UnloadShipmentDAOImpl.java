/**
 * 
 * UnloadShipmentDAOImpl.java
 * 
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version Date Author Reason 1.0 2 February,2018 NIIT -
 */
package com.ngen.cosys.satssginterfaces.mss.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.enums.UnloadShipmentQuery;
import com.ngen.cosys.satssginterfaces.mss.model.DLSULD;
import com.ngen.cosys.satssginterfaces.mss.model.Flight;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentHouse;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipment;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipmentInventory;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipmentRequest;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipmentSHCs;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipmentSearch;

/**
 * This class takes care of the responsibilities related to the Unload Shipment DAO
 * operation that comes from the service.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Repository
public class UnloadShipmentDAOImpl extends BaseDAO implements UnloadShipmentDAO {

	@Autowired
	 @Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionUnloadShipment;
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#SearchForFlight(com.ngen.cosys.export.assignULD.model.Flight)
	 */
	@Override
	public Flight searchForFlight(Flight flight) throws CustomException {
 		return fetchObject(UnloadShipmentQuery.SEARCH_FLIGHT.getQueryId(),flight,sqlSessionUnloadShipment);
	}
	
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#getShpmentInfo(com.ngen.cosys.export.buildup.model.UnloadShipmentSearch)
	 */
	@Override
	public List<UnloadShipment>  getShipmentInfo(UnloadShipmentSearch uld) throws CustomException {	
		return fetchList(UnloadShipmentQuery.SHIPMENT_INFO.getQueryId(),uld,sqlSessionUnloadShipment);
	}
	@Override
	public BigInteger getShipmentId(String shipmentNumber) throws CustomException {
		return fetchObject(UnloadShipmentQuery.SHIPMENT_ID.getQueryId(),shipmentNumber,sqlSessionUnloadShipment);
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#unloadShipment(java.util.List)
	 */
	@Override
	public List<Integer>  reduceLoadShipment(List<UnloadShipment> shpment) throws CustomException {
	
		return updateData(UnloadShipmentQuery.REDUCE_LOADSHIPMENT.getQueryId(),shpment,sqlSessionUnloadShipment);
		
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#saveShipmentInvetory(java.util.List)
	 */
	@Override
	public int addShipmentInventory(UnloadShipmentInventory inventory) throws CustomException {
	
		return insertData(UnloadShipmentQuery.INSERT_SHIPMENT_INVENTORY.getQueryId(),inventory,sqlSessionUnloadShipment);
	}
	
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#updateShipmentInvetory(java.util.List)
	 */
	@Override
	public int updateShipmentInvetory(UnloadShipmentInventory inventory) throws CustomException {
	
		return updateData(UnloadShipmentQuery.UPDATE_SHIPMENT_INVENTORY.getQueryId(),inventory,sqlSessionUnloadShipment);
	}
	
	@Override
	public List<Integer> updateHouseInfo(List<UnloadShipment> shpment) throws CustomException {
		return updateData("updateHouseInfoForMSS",shpment,sqlSessionUnloadShipment);
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#getLocationCode(com.ngen.cosys.export.model.UnloadShipmentInventory)
	 */
	@Override
	public String getLocationCode(UnloadShipmentInventory inventory) throws CustomException {
	
		return fetchObject(UnloadShipmentQuery.INVENTORY_LOACTION.getQueryId(),inventory,sqlSessionUnloadShipment);
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#updateManifestShpmtInfo(java.util.List)
	 */
	@Override
	public List<Integer> updateManifestShpmtInfo(List<UnloadShipment> unloadShipmentList) throws CustomException {
		return updateData(UnloadShipmentQuery.UPADTE_MANIFEST_SHIPMENT_INFO.getQueryId(),unloadShipmentList,sqlSessionUnloadShipment);
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#deleteShipment()
	 */
	@Override
	public List<Integer>  deleteLoadShipment(List<UnloadShipment> unloadShipmentList) throws CustomException {
	return deleteData(UnloadShipmentQuery.DELETE_LOAD_SHIPMENT.getQueryId(),unloadShipmentList,sqlSessionUnloadShipment);	
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#deleteAssingedULDTrolly()
	 */
	@Override 
	public List<Integer> deleteAssingedULDTrolly(List<UnloadShipment> unloadShipmentList) throws CustomException {
		return 	deleteData(UnloadShipmentQuery.DELETE_ASSINGED_ULDTROLLEY.getQueryId(),unloadShipmentList,sqlSessionUnloadShipment);
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#deleteLoadShipmentSHCInfo()
	 */
	@Override
	public List<Integer> deleteLoadShipmentSHCInfo(List<UnloadShipment> unloadShipmentList) throws CustomException {
		return 	deleteData(UnloadShipmentQuery.DELETE_LOADSHIPMENT_SHCINFO.getQueryId(),unloadShipmentList,sqlSessionUnloadShipment);
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#deleteManifestShpmtInfo()
	 */
	@Override
	public List<Integer> deleteManifestShpmtInfo(List<UnloadShipment> unloadShipmentList) throws CustomException {
		return 	deleteData(UnloadShipmentQuery.DELETE_MANIFEST_SHIPMENTINFO.getQueryId(),unloadShipmentList,sqlSessionUnloadShipment);
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#deleteManifestShpmtShcs()
	 */
	@Override
	public List<Integer> deleteManifestShpmtShcs(List<UnloadShipment> unloadShipmentList) throws CustomException {
		return 	deleteData(UnloadShipmentQuery.DELETE_MANIFEST_SHIPMENT_SHCS.getQueryId(),unloadShipmentList,sqlSessionUnloadShipment);
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#deleteManifestULDInfo()
	 */
	@Override
	public List<Integer> deleteManifestULDInfo(List<UnloadShipment> unloadShipmentList) throws CustomException {
		return 	deleteData(UnloadShipmentQuery.DELETE_MANIFEST_ULDINFO.getQueryId(),unloadShipmentList,sqlSessionUnloadShipment);
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#deleteManifestInfo()
	 */
	@Override
	public List<Integer> deleteManifestInfo(List<UnloadShipment> unloadShipmentList) throws CustomException {
		return 	deleteData(UnloadShipmentQuery.DELETE_MANIFEST_INFO.getQueryId(),unloadShipmentList,sqlSessionUnloadShipment);
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#updateDLSULDTrolly(java.util.List)
	 */
	@Override
	public List<Integer> updateDLSULDTrolly(List<UnloadShipment> unloadShipmentList) throws CustomException {
		
		return updateData(UnloadShipmentQuery.UPDATE_DLSULD_TROLLEY.getQueryId(),unloadShipmentList,sqlSessionUnloadShipment);
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#deleteDLSULDTrolly()
	 */
	@Override
	public List<Integer> deleteDLSULDTrolly(List<UnloadShipment> unloadShipmentList) throws CustomException {
		return deleteData(UnloadShipmentQuery.DELETE_DLSULD_TROLLEY.getQueryId(),unloadShipmentList,sqlSessionUnloadShipment);
	}
	
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#deleteDLSULDTrollySHCInfo(java.util.List)
	 */
	@Override
	public Integer deleteDLSULDTrollySHCInfo(List<BigInteger> dlsIds) throws CustomException {
		return  deleteData(UnloadShipmentQuery.DELETE_DLSULD_TROLLEY_SHCS.getQueryId(),dlsIds,sqlSessionUnloadShipment);
	}
		
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#deleteDLSULDTrollyAccesorryInfo(java.util.List)
	 */
	@Override
	public  Integer deleteDLSULDTrollyAccesorryInfo(List<BigInteger> dlsIds) throws CustomException {
		return  deleteData(UnloadShipmentQuery.DELETE_DLSULD_ACCESORRY_INFO.getQueryId(),dlsIds,sqlSessionUnloadShipment);
		
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#getDLSId()
	 */
	@Override
	public List<DLSULD> getDLSId(UnloadShipmentRequest unloadShipment) throws CustomException {
		return fetchList(UnloadShipmentQuery.GET_DLS_ID.getQueryId(),unloadShipment,sqlSessionUnloadShipment);
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#deleteLoadShipmentHouseInfo(java.util.List)
	 */
	@Override
	public List<Integer> deleteLoadShipmentHouseInfo(List<UnloadShipment> unloadShipmentList) throws CustomException {
		return  deleteData(UnloadShipmentQuery.DELETE_LOADSHIPMENT_INFO.getQueryId(),unloadShipmentList,sqlSessionUnloadShipment);
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#insertShipmentInventorySHCs(java.util.List)
	 */
	@Override
	public List<Integer> insertShipmentInventorySHCs(List<UnloadShipmentSHCs> shcs) throws CustomException {

		return insertData(UnloadShipmentQuery.INSERT_SHIPMENT_INVENTORY_SHCS.getQueryId(),shcs,sqlSessionUnloadShipment);
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#getShipmentHouseId(com.ngen.cosys.export.model.ShipmentHouse)
	 */
	@Override
	public BigInteger getShipmentHouseId(ShipmentHouse houseInfo) throws CustomException {
	
		return fetchObject(UnloadShipmentQuery.GET_SHIPMENT_HOUSE_IDS.getQueryId(),houseInfo,sqlSessionUnloadShipment);
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#createShipmentInventoryHouseInfo(java.util.List)
	 */
	@Override
	public List<Integer> createShipmentInventoryHouseInfo(List<ShipmentHouse> houseInfoList) throws CustomException {
		
		return insertData(UnloadShipmentQuery.CREATE_SHIPMENTINVENTORY_HOUSEINFO.getQueryId(),houseInfoList,sqlSessionUnloadShipment);
	}
	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.buildup.dao.UnloadShipmentDAO#getShipmentInventoryId(com.ngen.cosys.export.buildup.model.UnloadShipmentInventory)
	 */
	@Override
	public BigInteger getShipmentInventoryId(UnloadShipmentInventory inventory) throws CustomException {
		
		return fetchObject(UnloadShipmentQuery.GET_SHIPMENTINVENTORY_ID.getQueryId(),inventory,sqlSessionUnloadShipment);
	}
	@Override
	public List<Integer> deleteDLS(List<UnloadShipment> unloadShipmentList) throws CustomException {
		
		return deleteData(UnloadShipmentQuery.DELETE_DLS.getQueryId(),unloadShipmentList,sqlSessionUnloadShipment);
	}

	@Override
	public int getUnloadUldsCount(UnloadShipment shpment) throws CustomException {
	
		return fetchObject(UnloadShipmentQuery.GET_ULD_COUNT.getQueryId(),shpment,sqlSessionUnloadShipment);
	}

	@Override
	public boolean valiadateShipmentLocation(UnloadShipmentInventory inv) throws CustomException {
	 int count = fetchObject(UnloadShipmentQuery.VALIDATE_SHIPMENT_LOCATION.getQueryId(),inv,sqlSessionUnloadShipment);
		return count > 0;
	}

	@Override
	public List<Integer> deleteAssignedULDTrolleyToFlightPiggyBackInfo(List<UnloadShipment> unloadShipmentList) throws CustomException {
		
		return deleteData(UnloadShipmentQuery.DELETE_ULDTOFLIGHT_PIGGYBAG_INFO.getQueryId(),unloadShipmentList,sqlSessionUnloadShipment);
	}

	@Override
	public Boolean getLoadShipmentHousePieceCount(UnloadShipmentInventory inv) throws CustomException {
		
		return  fetchObject(UnloadShipmentQuery.HOUSEPIECE_COUNT.getQueryId(),inv,sqlSessionUnloadShipment);
	}

	@Override
	public Integer validSpecialhandlingCodeList(UnloadShipmentInventory inv) throws CustomException {
		
		return fetchObject(UnloadShipmentQuery.VLD_SHC_LIST.getQueryId(),inv,sqlSessionUnloadShipment);
	}

	/*@Override
	public FlightDetails getFlightDetailsByULD(UnloadShipmentSearch uld) throws CustomException {
		
		return fetchObject(UnloadShipmentQuery.GET_FLIGHT_DETAILBY_ULD.getQueryId(),uld,sqlSessionUnloadShipment); 
	}*/

	/*@Override
	public List<FlightDetails> getFlightDetailsByShipmentNumber(UnloadShipmentSearch shipmentDetail)
			throws CustomException {
	
		return fetchList(UnloadShipmentQuery.GET_FLIGHT_DETAILBY_SHPMTNUM.getQueryId(),shipmentDetail,sqlSessionUnloadShipment); 
	}*/

	/*@Override
	public List<Segment> getSegmentDetail(FlightDetails flight) throws CustomException {
		
		return fetchList(UnloadShipmentQuery.GET_SEGMENT.getQueryId(),flight,sqlSessionUnloadShipment); 
	}*/

	/*@Override
	public UnloadShipmentRequest searchFlightDetails(FlightDetails searchResult) throws CustomException {
	
		return fetchObject(UnloadShipmentQuery.GET_FLIGHT_DETAILS.getQueryId(),searchResult,sqlSessionUnloadShipment); 
	}*/

	@Override
	public boolean isShcExists(UnloadShipmentInventory inventory) throws CustomException {
		int count = fetchObject(UnloadShipmentQuery.IS_SHC_EXISTS.getQueryId(),inventory,sqlSessionUnloadShipment);
		return  count > 0;
	}

	/*@Override
	public Shipment getBookedPiecesAndWeight(FlightDetails loadedShipmentInfo) throws CustomException {
		
		return fetchObject(UnloadShipmentQuery.BOOKED_PIECES_WEIGHT.getQueryId(),loadedShipmentInfo,sqlSessionUnloadShipment);
	}*/

	@Override
	public List<Integer> deleteManifestShpmtHouseInfo(List<UnloadShipment> unloadShipmentList) throws CustomException {
		return 	deleteData(UnloadShipmentQuery.DELETE_MANIFEST_SHIPMENT_HOUSE_INFO.getQueryId(),unloadShipmentList,sqlSessionUnloadShipment);
	}

	@Override
	public boolean  isLoadDone(Flight flight) throws CustomException {
		int count = fetchObject("isLoadDone",flight,sqlSessionUnloadShipment);
		return count > 0;
	}

	@Override
	public int deleteShipmentInvetory(UnloadShipmentInventory inventory) throws CustomException {
	
		return deleteData(UnloadShipmentQuery.DELETE_INVENTORY_FOR_FLIGHT.getQueryId(),inventory,sqlSessionUnloadShipment);
	}

	@Override
	public int updateInventoryforExistedFlight(UnloadShipmentInventory inventory) throws CustomException {
	
		return updateData(UnloadShipmentQuery.UPDATE_INVENTORY_FOR_FLIGHT.getQueryId(),inventory,sqlSessionUnloadShipment);
	}

	/*@Override
	public BigInteger getShipmentID(UnloadShipmentSearch shipmentDetail) throws CustomException {
		
		return fetchObject(UnloadShipmentQuery.SHIPMENT_ID.getQueryId(),shipmentDetail,sqlSessionUnloadShipment); 
	}
	*/		
	
	@Override
	public UnloadShipment getLoadedShipmentIdForZeroPieces(UnloadShipment unloadModel) throws CustomException {
	   return fetchObject("getLoadedShipmentIdForZeroPieces", unloadModel, sqlSessionUnloadShipment);
	}
}
