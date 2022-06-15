package com.ngen.cosys.satssginterfaces.mss.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
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
 * @author NIIT Technologies
 *
 */
public interface LoadShipmentDao {

	/**
	 * @param search
	 * @return
	 * @throws CustomException
	 */
	SearchLoadShipment fetchFlightInfoForMobile(SearchLoadShipment search) throws CustomException;

	/**
	 * @param search
	 * @return
	 * @throws CustomException
	 */
	SearchBuildupFlight fetchFlightInfo(SearchLoadShipment search) throws CustomException;

	/**
	 * @param search
	 * @return
	 * @throws CustomException
	 */
/*	List<ShipmentToBeLoaded> fetchToBeLoadShipment(SearchLoadShipment search) throws CustomException;*/

	/**
	 * @param search
	 * @return
	 * @throws CustomException
	 */
	List<LoadedShipment> fetchLoadedShipment(SearchLoadShipment search) throws CustomException;

	/**
	 * @param search
	 * @return
	 * @throws CustomException
	 */
/*	List<UldInventory> fetchInventoryByUld(SearchShipmentUld search) throws CustomException;*/

	/**
	 * @param laod
	 * @return
	 * @throws CustomException
	 */
	boolean checkLoadShipmentPK(LoadedShipment laod) throws CustomException;

	/**
	 * @param laod
	 * @throws CustomException
	 */
	void insertLoadShipment(LoadedShipment laod) throws CustomException;

	/**
	 * @param laod
	 * @throws CustomException
	 */
	void updateLoadShipment(LoadedShipment laod) throws CustomException;

	/**
	 * @param laod
	 * @throws CustomException
	 */
	void updateInventory(LoadedShipment laod) throws CustomException;

	/**
	 * @param laod
	 * @throws CustomException
	 */
	void deleteInventory(BigInteger laod) throws CustomException;

	/**
	 * @param laod
	 * @return
	 * @throws CustomException
	 */
	LoadedShipment fetchLoadedPieceWeight(LoadedShipment laod) throws CustomException;

	/**
	 * @param load
	 * @throws CustomException
	 */
	void insertSHC(List<SHCS> load) throws CustomException;

	/**
	 * @param load
	 * @throws CustomException
	 */
	void insertTagNumber(List<ShipmentHouse> load) throws CustomException;

	/**
	 * @param load
	 * @return
	 * @throws CustomException
	 */
	Integer getLoadedShipmentInfoId(LoadedShipment load) throws CustomException;

	/**
	 * @param shc
	 * @throws CustomException
	 */
	void deleteInventorySHC(Integer shc) throws CustomException;

	/**
	 * @param house
	 * @throws CustomException
	 */
	void deleteInventoryHouse(Integer house) throws CustomException;

	/**
	 * @param laod
	 * @return
	 * @throws CustomException
	 */
	LoadedShipment fetchInventoryPieceWeight(Integer laod) throws CustomException;

	/**
	 * @param laod
	 * @throws CustomException
	 */
	void deleteLoadShc(Integer laod) throws CustomException;

	/**
	 * @param laod
	 * @throws CustomException
	 */
	void deleteLoadInventory(Integer laod) throws CustomException;

	/**
	 * @param shc
	 * @return
	 * @throws CustomException
	 */
	List<SHCS> getSHC(SHCS shc) throws CustomException;

	/**
	 * @param shc
	 * @return
	 * @throws CustomException
	 */
	List<ShipmentHouse> getTagNo(ShipmentHouse shc) throws CustomException;

	/**
	 * @param load
	 * @return
	 * @throws CustomException
	 */
	boolean checkULDCarrierAndTypeMatch(UldShipment load) throws CustomException;

	/**
	 * @param load
	 * @return
	 * @throws CustomException
	 */
	boolean checkBypassForULDAndTypeMatch(UldShipment load) throws CustomException;

	/**
	 * @param load
	 * @return
	 * @throws CustomException
	 */
	Double getLoadedWeight(Integer load) throws CustomException;

	/**
	 * @param load
	 * @return
	 * @throws CustomException
	 */
	boolean checkBypassForWeightCheck(UldShipment load) throws CustomException;

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	boolean checkCaoShcFlight(Integer id) throws CustomException;

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	boolean checkShipmentLock(Integer id) throws CustomException;

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	boolean checkInventoryLock(Integer id) throws CustomException;

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	UldShipment checkAcceptance(Integer id) throws CustomException;

	/**
	 * @param shc
	 * @return
	 * @throws CustomException
	 */
/*	List<BuildUpSHC> checkNonCoLoadableSHC(BuildUpSHC shc) throws CustomException;*/

	/**
	 * @param shc
	 * @return
	 * @throws CustomException
	 */
/*	boolean checkPerishableCompatibleGroup(BuildUpSHC shc) throws CustomException;*/

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	Shipment getShipmentDetails(Integer id) throws CustomException;

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	List<ManifestSHC> getShipmentSHC(Integer id) throws CustomException;

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	List<ManifestHouse> getShipmentHouse(Integer id) throws CustomException;

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	Shipment getWorkingListData(UldShipment id) throws CustomException;

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	UldShipment getUldData(UldShipment id) throws CustomException;

	/**
	 * @param uld
	 * @return
	 * @throws CustomException
	 */
	Double getULDMaxWeight(UldShipment uld) throws CustomException;

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	Boolean checkIsULD(Integer id) throws CustomException;

	/**
	 * @param uld
	 * @return
	 * @throws CustomException
	 */
	Boolean checkULDAssignToFlight(UldShipment uld) throws CustomException;

	/**
	 * @param event
	 * @throws CustomException
	 */
/*	void insertBuildUpEvent(BuildUpCompleteEvent event) throws CustomException;*/

	/**
	 * @param event
	 * @return
	 * @throws CustomException
	 */
/*	BuildUpCompleteEvent getBuildUpCompleteEvent(Integer event) throws CustomException;*/

	/**
	 * @param event
	 * @return
	 * @throws CustomException
	 */
	boolean isBuildUpCompleteEventPublished(Integer event) throws CustomException;

	/**
	 * @param event
	 * @throws CustomException
	 */
/*	void updateBuildUpCompleteEvent(BuildUpCompleteEvent event) throws CustomException;*/

	/**
	 * @param uld
	 * @return
	 * @throws CustomException
	 */
	boolean checkIsUldExist(UldShipment uld) throws CustomException;

	/**
	 * @param uld
	 * @return
	 * @throws CustomException
	 */
	List<Integer> checkValidateSegment(String uld) throws CustomException;

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	List<LoadedShipment> getCommonServiceInventory(Integer id) throws CustomException;

	/**
	 * @param shipment
	 * @return
	 * @throws CustomException
	 */
	List<LoadedShipment> getInventoryBymailBag(UldShipment shipment) throws CustomException;

	/**
	 * @param obj
	 * @return
	 * @throws CustomException
	 */
	boolean isLoadingDone(Integer obj) throws CustomException;

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	List<ShipmentHouse> getMailBags(ShipmentHouse id) throws CustomException;

	/**
	 * @param awbDetail
	 * @return
	 * @throws CustomException
	 */
/*	List<UldInventory> addAWBForMobile(SearchShipmentUld awbDetail) throws CustomException;*/

	/**
	 * @param uldDetails
	 * @return max weight accepted in ULD
	 * @throws CustomException
	 */
	BigDecimal fetchMaxWeightAndPieces(SearchLoadShipment uldDetails) throws CustomException;

	/**
	 * @param uld
	 * @return
	 * @throws CustomException
	 */
	Boolean isUldWeightCheckNotRequired(UldShipment uld) throws CustomException;

	/**
	 * @param obj
	 * @throws CustomException
	 */
	void createInventory(LoadedShipment obj) throws CustomException;

	/**
	 * @param load
	 * @return
	 * @throws CustomException
	 */
	LoadedShipment isNewInventoryExist(LoadedShipment load) throws CustomException;

	/**
	 * @param load
	 * @throws CustomException
	 */
	void updateNewInventory(LoadedShipment load) throws CustomException;

	/**
	 * @return
	 * @throws CustomException
	 */
	Boolean isPartConfirmOrFinalized(BigInteger obj) throws CustomException;

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	String getShipmentNumber(BigInteger id) throws CustomException;

	/**
	 * @param obj
	 * @return
	 * @throws CustomException
	 */
/*	FlightRuleModel checkFlightRules(BigInteger obj) throws CustomException;*/

	/**
	 * @param obj
	 * @return
	 * @throws CustomException
	 */
	Boolean isShipmentServiceCargo(String obj) throws CustomException;

	/**
	 * @param obj
	 * @return
	 * @throws CustomException
	 */
	BigDecimal getWeightWeighed(BigInteger obj) throws CustomException;

	/**
	 * @param load
	 * @return
	 * @throws CustomException
	 */
	BigInteger getWeighingId(LoadedShipment load) throws CustomException;

	/**
	 * @param newLoadList
	 * @throws CustomException
	 */
	void updateLoadedWeight(List<LoadedShipment> newLoadList) throws CustomException;

	BigInteger getAssignULDTrolleyId(ULDIInformationDetails obj) throws CustomException;

	void createAssignUldToFlight(AssignULD obj) throws CustomException;

}
