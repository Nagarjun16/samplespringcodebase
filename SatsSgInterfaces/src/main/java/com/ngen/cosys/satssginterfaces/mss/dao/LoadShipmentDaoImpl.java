package com.ngen.cosys.satssginterfaces.mss.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.enums.MapperId;
import com.ngen.cosys.satssginterfaces.mss.model.AssignULD;
import com.ngen.cosys.satssginterfaces.mss.model.LoadedShipment;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestHouse;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestSHC;
import com.ngen.cosys.satssginterfaces.mss.model.SHCS;
import com.ngen.cosys.satssginterfaces.mss.model.SearchBuildupFlight;
import com.ngen.cosys.satssginterfaces.mss.model.SearchLoadShipment;
import com.ngen.cosys.satssginterfaces.mss.model.Shipment;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentHouse;
import com.ngen.cosys.satssginterfaces.mss.model.ULDIInformationDetails;
import com.ngen.cosys.satssginterfaces.mss.model.UldShipment;

/**
 * Dao to fetch Load Shipment related request
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Repository("loadShipmentDaoImpl")
public class LoadShipmentDaoImpl extends BaseDAO implements LoadShipmentDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	@Override
	public SearchLoadShipment fetchFlightInfoForMobile(SearchLoadShipment search) throws CustomException {

		SearchLoadShipment response = fetchObject(MapperId.SQL_GET_FLIGHT_INFO_FOR_MO.getId(), search, sqlSession);
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#fetchFlightInfo(com.ngen.
	 * cosys.export.buildup.model.SearchLoadShipment)
	 */
	@Override
	public SearchBuildupFlight fetchFlightInfo(SearchLoadShipment search) throws CustomException {
		return fetchObject(MapperId.SQL_GET_FLIGHT_INFO.getId(), search, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#fetchToBeLoadShipment(com.
	 * ngen.cosys.export.buildup.model.SearchLoadShipment)
	 */
	/*@Override
	public List<ShipmentToBeLoaded> fetchToBeLoadShipment(SearchLoadShipment search) throws CustomException {
		return fetchList(MapperId.SQL_GET_TO_BE_LOADED.getId(), search, sqlSession);
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#fetchLoadedShipment(com.
	 * ngen.cosys.export.buildup.model.SearchLoadShipment)
	 */
	@Override
	public List<LoadedShipment> fetchLoadedShipment(SearchLoadShipment search) throws CustomException {
		return fetchList(MapperId.SQL_GET_LOADED_SHIPMENT.getId(), search, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#fetchInventoryByUld(com.
	 * ngen.cosys.export.buildup.model.SearchShipmentUld)
	 */
	/*@Override
	public List<UldInventory> fetchInventoryByUld(SearchShipmentUld search) throws CustomException {
		return fetchList(MapperId.SQL_GET_ULD_SHIP_DATA.getId(), search, sqlSession);
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#checkLoadShipmentPK(com.
	 * ngen.cosys.export.buildup.model.LoadedShipment)
	 */
	@Override
	public boolean checkLoadShipmentPK(LoadedShipment laod) throws CustomException {
		Integer count = fetchObject(MapperId.SQL_CHEKC_LOAD_SHIPMENT_PK.getId(), laod, sqlSession);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#insertLoadShipment(com.ngen
	 * .cosys.export.buildup.model.LoadedShipment)
	 */
	@Override
	public void insertLoadShipment(LoadedShipment load) throws CustomException {
		insertData(MapperId.SQL_INSERT_LOAD_SHIPMENT.getId(), load, sqlSession);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#updateLoadShipment(com.ngen
	 * .cosys.export.buildup.model.LoadedShipment)
	 */
	@Override
	public void updateLoadShipment(LoadedShipment load) throws CustomException {
		updateData(MapperId.SQL_UPDATE_LOAD_SHIPMENT.getId(), load, sqlSession);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#updateInventory(com.ngen.
	 * cosys.export.buildup.model.LoadedShipment)
	 */
	@Override
	public void updateInventory(LoadedShipment load) throws CustomException {
		updateData(MapperId.SQL_UPDATE_INVENTORY.getId(), load, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#deleteInventory(com.ngen.
	 * cosys.export.buildup.model.LoadedShipment)
	 */
	@Override
	public void deleteInventory(BigInteger load) throws CustomException {
		deleteData(MapperId.SQL_DELETE_INVENTORY.getId(), load, sqlSession);

	}

	@Override
	public LoadedShipment fetchLoadedPieceWeight(LoadedShipment load) throws CustomException {
		return fetchObject(MapperId.SQL_GET_LOADED_DATA.getId(), load, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#insertSHC(com.ngen.cosys.
	 * export.buildup.model.LoadedShipment)
	 */
	@Override
	public void insertSHC(List<SHCS> load) throws CustomException {
		insertList(MapperId.SQL_INSERT_SHC.getId(), load, sqlSession);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#insertTagNumber(com.ngen.
	 * cosys.export.buildup.model.LoadedShipment)
	 */
	@Override
	public void insertTagNumber(List<ShipmentHouse> load) throws CustomException {
		insertList(MapperId.SQL_INSERT_TAG_NO.getId(), load, sqlSession);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#getLoadedShipmentInfoId(com
	 * .ngen.cosys.export.buildup.model.LoadedShipment)
	 */
	@Override
	public Integer getLoadedShipmentInfoId(LoadedShipment load) throws CustomException {
		return fetchObject(MapperId.SQL_GET_LOAD_SHIP_INFO_ID.getId(), load, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#deleteInventorySHC(java.
	 * lang.Integer)
	 */
	@Override
	public void deleteInventorySHC(Integer shc) throws CustomException {
		deleteData(MapperId.SQL_DELETE_INVENTORY_SHC.getId(), shc, sqlSession);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#deleteInventoryHouse(java.
	 * lang.Integer)
	 */
	@Override
	public void deleteInventoryHouse(Integer house) throws CustomException {
		deleteData(MapperId.SQL_DELETE_INVENTORY_HOUSE.getId(), house, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#fetchInventoryPieceWeight(
	 * java.lang.Integer)
	 */
	@Override
	public LoadedShipment fetchInventoryPieceWeight(Integer laod) throws CustomException {
		return fetchObject(MapperId.SQL_GET_INVENTORY_PIECES_WEIGHT.getId(), laod, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#deleteLoadShc(java.lang.
	 * Integer)
	 */
	@Override
	public void deleteLoadShc(Integer laod) throws CustomException {
		deleteData(MapperId.SQL_DELETE_LOAD_SHC.getId(), laod, sqlSession);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#deleteLoadInventory(java.
	 * lang.Integer)
	 */
	@Override
	public void deleteLoadInventory(Integer laod) throws CustomException {
		deleteData(MapperId.SQL_DELETE_LOAD_HOUSE.getId(), laod, sqlSession);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.buildup.dao.LoadShipmentDao#getSHC(com.ngen.cosys.
	 * export.model.SHC)
	 */
	@Override
	public List<SHCS> getSHC(SHCS shc) throws CustomException {
		return fetchList(MapperId.SQL_GET_SHC.getId(), shc, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#getTagNo(com.ngen.cosys.
	 * export.model.ShipmentHouse)
	 */
	@Override
	public List<ShipmentHouse> getTagNo(ShipmentHouse shc) throws CustomException {
		return fetchList(MapperId.SQL_GET_TAG_NO.getId(), shc, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#checkULDCarrierAndTypeMatch
	 * (com.ngen.cosys.export.buildup.model.LoadedShipment)
	 */
	@Override
	public boolean checkULDCarrierAndTypeMatch(UldShipment uld) throws CustomException {
		Integer count = fetchObject(MapperId.SQL_CHECK_ULD_TYPE_AND_CARRIER_MATCH.getId(), uld, sqlSession);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.buildup.dao.LoadShipmentDao#
	 * checkBypassForULDAndTypeMatch(com.ngen.cosys.export.buildup.model.
	 * LoadedShipment)
	 */
	@Override
	public boolean checkBypassForULDAndTypeMatch(UldShipment load) throws CustomException {
		Integer count = fetchObject(MapperId.SQL_CHECK_BYPASS_ULD_TYPE_AND_CARRIER_MATCH.getId(), load, sqlSession);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#getLoadedWeight(java.lang.
	 * Integer)
	 */
	@Override
	public Double getLoadedWeight(Integer load) throws CustomException {
		return fetchObject(MapperId.SQL_GET_LOADED_WEIGHT.getId(), load, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#checkBypassForWeightCheck(
	 * com.ngen.cosys.export.buildup.model.LoadedShipment)
	 */
	@Override
	public boolean checkBypassForWeightCheck(UldShipment load) throws CustomException {
		Integer count = fetchObject(MapperId.SQL_CHECK_BYPASS_WEIGHT_CHECK.getId(), load, sqlSession);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#checkCaoShcFlight(java.lang
	 * .Integer)
	 */
	@Override
	public boolean checkCaoShcFlight(Integer id) throws CustomException {
		Integer count = fetchObject(MapperId.SQL_CHECK_CAO_SHC.getId(), id, sqlSession);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#checkShipmentLock(java.lang
	 * .Integer)
	 */
	@Override
	public boolean checkShipmentLock(Integer id) throws CustomException {
		Integer count = fetchObject(MapperId.SQL_CHECK_SHIPMENT_LOCK.getId(), id, sqlSession);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#checkInventoryLock(java.
	 * lang.Integer)
	 */
	@Override
	public boolean checkInventoryLock(Integer id) throws CustomException {
		Integer count = fetchObject(MapperId.SQL_CHECK_INVENTORY_LOCK.getId(), id, sqlSession);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#checkAcceptance(java.lang.
	 * String)
	 */
	@Override
	public UldShipment checkAcceptance(Integer id) throws CustomException {
		return fetchObject(MapperId.SQL_EACCEPTANCE_DOC_CHECK.getId(), id, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#checkNonCoLoadableSHC(com.
	 * ngen.cosys.export.buildup.model.BuildUpSHC)
	 */
	/*@Override
	public List<BuildUpSHC> checkNonCoLoadableSHC(BuildUpSHC shc) throws CustomException {
		return fetchList(MapperId.SQL_CHECK_COLADABLE_SHC.getId(), shc, sqlSession);
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.buildup.dao.LoadShipmentDao#
	 * checkPerishableCompatibleGroup(com.ngen.cosys.export.buildup.model.
	 * BuildUpSHC)
	 */
/*	@Override
	public boolean checkPerishableCompatibleGroup(BuildUpSHC shc) throws CustomException {
		Integer count = fetchObject(MapperId.SQL_CHECK_PERISHABLE_SHC.getId(), shc, sqlSession);
		return count > 0;
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#getShipmentDetails(java.
	 * lang.Integer)
	 */
	@Override
	public Shipment getShipmentDetails(Integer id) throws CustomException {
		return fetchObject(MapperId.SQL_GET_SHIPMENT_DETAILS.getId(), id, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#getShipmentSHC(java.lang.
	 * Integer)
	 */
	@Override
	public List<ManifestSHC> getShipmentSHC(Integer id) throws CustomException {
		return fetchList(MapperId.SQL_GET_SHIPMENT_SHC.getId(), id, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#getShipmentHouse(java.lang.
	 * Integer)
	 */
	@Override
	public List<ManifestHouse> getShipmentHouse(Integer id) throws CustomException {
		return fetchList(MapperId.SQL_GET_SHIPMENT_HOUSE.getId(), id, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#getWorkingListData(java.
	 * lang.Integer)
	 */
	@Override
	public Shipment getWorkingListData(UldShipment id) throws CustomException {
		return fetchObject(MapperId.SQL_GET_WORKING_LIST_DATA.getId(), id, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#getWorkingListData(com.ngen
	 * .cosys.export.buildup.model.UldShipment)
	 */
	@Override
	public UldShipment getUldData(UldShipment id) throws CustomException {
		return fetchObject(MapperId.SQL_GET_ULD_DATA.getId(), id, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#getULDMaxWeight(com.ngen.
	 * cosys.export.buildup.model.UldShipment)
	 */
	@Override
	public Double getULDMaxWeight(UldShipment uld) throws CustomException {
		return fetchObject(MapperId.SQL_GET_ULD_MAX_WEIGHT.getId(), uld, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.buildup.dao.LoadShipmentDao#checkIsULD(java.lang.
	 * Integer)
	 */
	@Override
	public Boolean checkIsULD(Integer id) throws CustomException {
		return fetchObject(MapperId.SQL_CHECK_IS_ULD.getId(), id, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#checkULDAssignToFlight(com.
	 * ngen.cosys.export.buildup.model.UldShipment)
	 */
	@Override
	public Boolean checkULDAssignToFlight(UldShipment uld) throws CustomException {
		return fetchObject(MapperId.SQL_CHECK_ULD_ASSIGN_TO_FLIGHT.getId(), uld, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#insertBuildUpEvent(com.ngen
	 * .cosys.export.model.FlightEvent)
	 */
	/*@Override
	public void insertBuildUpEvent(BuildUpCompleteEvent event) throws CustomException {
		insertData(MapperId.SQL_BUILDUP_COMPLETED.getId(), event, sqlSession);
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#insertBuildUpEvent(java.
	 * lang.Integer)
	 */
	/*@Override
	public BuildUpCompleteEvent getBuildUpCompleteEvent(Integer id) throws CustomException {
		return fetchObject(MapperId.SQL_GET_BUILDUP_COMPLETED.getId(), id, sqlSession);
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.buildup.dao.LoadShipmentDao#
	 * isBuildUpCompleteEventPublished(java.lang.Integer)
	 */
	@Override
	public boolean isBuildUpCompleteEventPublished(Integer event) throws CustomException {
		return fetchObject(MapperId.SQL_CHECK_EVENT_PUBLISHED.getId(), event, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#updateBuildUpCompleteEvent(
	 * com.ngen.cosys.export.model.BuildUpCompleteEvent)
	 */
	/*@Override
	public void updateBuildUpCompleteEvent(BuildUpCompleteEvent event) throws CustomException {
		updateData(MapperId.SQL_UPDATE_BUILDUPCOMPLETE_EVENT.getId(), event, sqlSession);

	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#checkIsUldExist(java.lang.
	 * String)
	 */
	@Override
	public boolean checkIsUldExist(UldShipment uld) throws CustomException {
		return fetchObject(MapperId.SQL_CHECK_ULD_EXIST.getId(), uld, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#checkValidateSegment(java.
	 * lang.String)
	 */
	public List<Integer> checkValidateSegment(String uld) throws CustomException {
		return fetchList(MapperId.SQL_GET_ULD_SEGMENT.getId(), uld, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#getCommonServiceInventory(
	 * java.lang.Integer)
	 */
	@Override
	public List<LoadedShipment> getCommonServiceInventory(Integer id) throws CustomException {
		return fetchList(MapperId.SQL_INVENTORY_COMMSON_SERVICE.getId(), id, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#getInventoryBymailBag(com.
	 * ngen.cosys.export.buildup.model.UldShipment)
	 */
	@Override
	public List<LoadedShipment> getInventoryBymailBag(UldShipment shipment) throws CustomException {
		return fetchList(MapperId.SQL_INVENTORY_BY_MAIL_BAG.getId(), shipment, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#isLoadingDone(java.lang.
	 * Integer)
	 */
	@Override
	public boolean isLoadingDone(Integer obj) throws CustomException {
		Integer count = fetchObject(MapperId.CHECK_LOADING.getId(), obj, sqlSession);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.buildup.dao.LoadShipmentDao#getMailBags(java.lang.
	 * Integer)
	 */
	@Override
	public List<ShipmentHouse> getMailBags(ShipmentHouse id) throws CustomException {
		return fetchList(MapperId.GET_MAIL_BAG.getId(), id, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#addAWBForMobile(com.ngen.
	 * cosys.export.buildup.model.LoadedShipment)
	 */
	/*@Override
	public List<UldInventory> addAWBForMobile(SearchShipmentUld awbDetail) throws CustomException {
		return fetchList(MapperId.GET_ADD_AWB_DETAILS.getId(), awbDetail, sqlSession);
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#fetchMaxWeightAndPieces(com
	 * .ngen.cosys.export.buildup.model.SearchLoadShipment)
	 */
	@Override
	public BigDecimal fetchMaxWeightAndPieces(SearchLoadShipment uldDetails) throws CustomException {
		BigDecimal maxWeight = fetchObject(MapperId.GET_ULD_MAX_WEIGHT_ACCEPTED.getId(), uldDetails, sqlSession);
		return maxWeight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#isUldWeightCheckNotRequired
	 * (com.ngen.cosys.export.buildup.model.UldShipment)
	 */
	@Override
	public Boolean isUldWeightCheckNotRequired(UldShipment uld) throws CustomException {
		return fetchObject(MapperId.IS_ULD_WEIGHT_CHECK_NOT_REQ.getId(), uld, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#getWeighingId(java.lang.
	 * String)
	 */
	@Override
	public BigInteger getWeighingId(LoadedShipment load) throws CustomException {
		return fetchObject(MapperId.GET_WEIGHING_ID.getId(), load, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#createInventory(com.ngen.
	 * cosys.export.buildup.model.LoadedShipment)
	 */
	@Override
	public void createInventory(LoadedShipment obj) throws CustomException {
		insertData(MapperId.CREATE_INVENTORY.getId(), obj, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#isNewInventoryExist(com.
	 * ngen.cosys.export.buildup.model.LoadedShipment)
	 */
	@Override
	public LoadedShipment isNewInventoryExist(LoadedShipment load) throws CustomException {
		return fetchObject(MapperId.IS_NEW_INVENTORY_EXIST.getId(), load, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#updateNewInventory(com.ngen
	 * .cosys.export.buildup.model.LoadedShipment)
	 */
	@Override
	public void updateNewInventory(LoadedShipment load) throws CustomException {
		updateData(MapperId.UPDATE_NEW_INVENTORY.getId(), load, sqlSession);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#isPartConfirmOrFinalized(
	 * java.lang.String)
	 */
	@Override
	public Boolean isPartConfirmOrFinalized(BigInteger obj) throws CustomException {
		return fetchObject(MapperId.IS_PART_CONFIRM_OR_FINALIZE.getId(), obj, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#getShipmentNumber(java.math
	 * .BigInteger)
	 */
	@Override
	public String getShipmentNumber(BigInteger id) throws CustomException {
		return fetchObject(MapperId.GET_SHIPMENT_NUMBER.getId(), id, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#checkFlightRules(java.math.
	 * BigInteger)
	 */
/*	@Override
	public FlightRuleModel checkFlightRules(BigInteger obj) throws CustomException {
		return fetchObject(MapperId.CHECK_FLIGHT_RULE.getId(), obj, sqlSession);
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#isShipmentServiceCargo(java
	 * .lang.String)
	 */
	@Override
	public Boolean isShipmentServiceCargo(String obj) throws CustomException {
		return fetchObject(MapperId.GET_SHIPMENT_SERVICE_FLAG.getId(), obj, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#getWeightWeighed(java.math.
	 * BigInteger)
	 */
	@Override
	public BigDecimal getWeightWeighed(BigInteger obj) throws CustomException {
		return fetchObject(MapperId.GET_WEIGHT_WEIGHED.getId(), obj, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.export.buildup.dao.LoadShipmentDao#updateLoadedWeight(java.
	 * util.List)
	 */
	@Override
	public void updateLoadedWeight(List<LoadedShipment> newLoadList) throws CustomException {
		updateData("udpateLaodedWeight_Laod", newLoadList, sqlSession);
	}

	@Override
	public BigInteger getAssignULDTrolleyId(ULDIInformationDetails obj) throws CustomException {
		return fetchObject(MapperId.GET_ASSIGN_ULD_TROLLEY_ID.getId(), obj, sqlSession);
	}

	@Override
	public void createAssignUldToFlight(AssignULD obj) throws CustomException {
		insertData(MapperId.CREATE_ASSIGN_ULD_TO_FLIGHT.getId(), obj, sqlSession);
	}
}
