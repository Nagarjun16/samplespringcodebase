/**
 * UnloadShipmentDAO.java
 * 
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version Date Author Reason 1.0  2 February,2018 NIIT -
 */
package com.ngen.cosys.satssginterfaces.mss.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.DLSULD;
import com.ngen.cosys.satssginterfaces.mss.model.Flight;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentHouse;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipment;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipmentInventory;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipmentRequest;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipmentSHCs;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipmentSearch;

/**
 * This class takes care of the responsibilities related to the Unload Shipment
 * DAO operation that comes from the service.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface UnloadShipmentDAO {
	/**
	 * This method search for flight details
	 * 
	 * @param flight
	 * @return
	 * @throws CustomException
	 */
	Flight searchForFlight(Flight flight) throws CustomException;
	/**
	 * This method checks whether shipment is loaded in the flight or not
	 * @param flight
	 * @return int
	 * @throws CustomException
	 */
	boolean isLoadDone(Flight flight) throws CustomException;
	/**
	 *  This method fetches the shipment Details
	 *  
	 * @param shpmtInfo
	 * @return
	 * @throws CustomException
	 */
	List<UnloadShipment> getShipmentInfo(UnloadShipmentSearch uld) throws CustomException;
	/**
	 * this method gets the shipment ID from shipment master table
	 * 
	 * @param shipmentNumber
	 * @return
	 * @throws CustomException
	 */
	BigInteger getShipmentId(String shipmentNumber) throws CustomException;
	
	/**
	 * this method unloads the shipment from load shipment table
	 * 
	 * @param shpmtList
	 * @throws CustomException
	 */
	List<Integer>  reduceLoadShipment(List<UnloadShipment> shpment) throws CustomException;
	
	
	/**
	 * This method Inserts the unload shipment details into shipment Inventory table
	 * 
	 * @param inventory
	 * @return
	 * @throws CustomException
	*/ 
	int addShipmentInventory(UnloadShipmentInventory inventory) throws CustomException;
	
	/**
	 * This method updates  the Shipment details in shipment Inventory table
	 * 
	 * @param inventory
	 * @return
	 * @throws CustomException
	 */
	int updateShipmentInvetory(UnloadShipmentInventory inventory) throws CustomException;
	/**
	 *  This method updates  the load Shipment house Info
	 * 
	 * @param shpment
	 * @return List<Integer> 
	 * @throws CustomException
	 */
	List<Integer> updateHouseInfo(List<UnloadShipment> shpment) throws CustomException;
	
	/**
	 * this method is to get the code for finding shipment inventory location
	 * 
	 * @param inventory
	 * @return 	String 
	 * @throws CustomException
	 */
	String getLocationCode(UnloadShipmentInventory inventory)throws CustomException;
	
	/**
	 * This method updates the manifest shipment information
	 * 
	 * @param unloadShipmentList
	 * @return List<Integer> 
	 * @throws CustomException
	 */
	List<Integer> updateManifestShpmtInfo(List<UnloadShipment> unloadShipmentList) throws CustomException;
	/**
	 * this method deletes the shipment from the Exp_Loadshipment table if the piece and weight count reaches to 0
	 * 
	 * @return List<Integer>
	 * @throws CustomException
	 */
	List<Integer> deleteLoadShipment(List<UnloadShipment> unloadShipmentList) throws CustomException;
	/**
	 * this method deletes Special handling codes related to the that shipment
	 * 
	 * @return List<Integer>
	 * @throws CustomException
	 */
	List<Integer>  deleteLoadShipmentSHCInfo(List<UnloadShipment> unloadShipmentList) throws CustomException;
	
	/**
	 * this method deletes the data from load shipment house info
	 * 
	 * @param unloadShipmentList
	 * @return
	 * @throws CustomException
	 */
	List<Integer> deleteLoadShipmentHouseInfo(List<UnloadShipment> unloadShipmentList) throws CustomException;
	/**
	 * this method deletes the ULD from the Exp_AssingedULDTrollyToFlight table
	 * 
	 * @return List<Integer>
	 * @throws CustomException
	 */
	List<Integer> deleteAssingedULDTrolly(List<UnloadShipment> unloadShipmentList) throws CustomException;
	/**
	 * this method deletes shipment info   from the Exp_ManifestShpmtInfo table
	 * 
	 * @return List<Integer>
	 * @throws CustomException
	 */
	List<Integer> deleteManifestShpmtInfo(List<UnloadShipment> unloadShipmentList) throws CustomException;
	
	/**
	 * this method deletes Special handling codes related to the that shipment
	 * 
	 * @return List<Integer>
	 * @throws CustomException
	 */
	List<Integer> deleteManifestShpmtShcs(List<UnloadShipment> unloadShipmentList) throws CustomException;
	/**
	 * this method deletes ULD from the manifest if shipment is empty
	 * 
	 * @return List<Integer>
	 * @throws CustomException
	 */
	List<Integer> deleteManifestULDInfo(List<UnloadShipment> unloadShipmentList) throws CustomException;
	/**
	 * this method deletes the manifest info if ULD is not existed
	 * 
	 * @return List<Integer>
	 * @throws CustomException
	 */
	List<Integer> deleteManifestInfo(List<UnloadShipment> unloadShipmentList) throws CustomException;
	
	/**
	 * 
	 *  this method updates  the DLS ULD trolley  information
	 *  
	 * @param unloadShipmentList
	 * @return List<Integer>
	 * @throws CustomException
	 */
	List<Integer> updateDLSULDTrolly(List<UnloadShipment> unloadShipmentList) throws CustomException;
	/**this method deletes the info from   DLS ULD trolley  table 
	 * 
	 * @param unloadShipmentList
	 * @return List<Integer>
	 * @throws CustomException
	 */
	List<Integer> deleteDLSULDTrolly(List<UnloadShipment> unloadShipmentList) throws CustomException;
	
	/**
	 * this method deletes the info from  DLSULDTrollyAccesorryInfo table if ULD deletes from DLSULDTrolley table
	 * 
	 * @param dlsIds
	 * @return Integer
	 * @throws CustomException
	 */
	Integer  deleteDLSULDTrollyAccesorryInfo(List<BigInteger> dlsIds) throws CustomException;
	
	/**
	 *  this methos deletes the info from DLS ULD trolley  table  if piece and weight count is zero
	 * @param dlsIds
	 * @return Integer
	 * @throws CustomException
	 */
	Integer  deleteDLSULDTrollySHCInfo(List<BigInteger> dlsIds) throws CustomException;
	
	/**
	 *  this method gets the DLS IDs whose iece and weight count is zero
	 * @return List<BigInteger>
	 * @throws CustomException
	 */
	List<DLSULD> getDLSId(UnloadShipmentRequest unloadShipment) throws CustomException;
	
	/**
	 * This method inserts the data into  ShipmentInventorySHCs tabel
	 * 
	 * @param shcs
	 * @return List<Integer>
	 * @throws CustomException
	 */
	List<Integer> insertShipmentInventorySHCs(List<UnloadShipmentSHCs> shcs) throws CustomException;
	
	/**
	 * This method gets the  ShipmentHouseId from ShipmentHouse  table 
	 * 
	 * @param houseInfo
	 * @return BigInteger 
	 * @throws CustomException
	 */
	BigInteger getShipmentHouseId(ShipmentHouse houseInfo) throws CustomException;
	
	/**
	 * thia method creates the shipment Inventory house information
	 * 
	 * @param houseInfoList
	 * @return
	 * @throws CustomException
	 */
	List<Integer> createShipmentInventoryHouseInfo(List<ShipmentHouse> houseInfoList) throws CustomException;
	
	/**
	 * this method gets the shipment inventory ID
	 * 
	 * @param inventory
	 * @return BigInteger
	 * @throws CustomException
	 */
	BigInteger getShipmentInventoryId(UnloadShipmentInventory inventory) throws CustomException;
	
	/**
	 * 
	 */
	List<Integer> deleteDLS(List<UnloadShipment> unloadShipmentList) throws CustomException;
	int getUnloadUldsCount(UnloadShipment shpment) throws CustomException;
	boolean valiadateShipmentLocation(UnloadShipmentInventory  inv) throws CustomException;
	Boolean getLoadShipmentHousePieceCount(UnloadShipmentInventory  inv) throws CustomException;
	List<Integer>  deleteAssignedULDTrolleyToFlightPiggyBackInfo(List<UnloadShipment> unloadShipmentList) throws CustomException;
	Integer validSpecialhandlingCodeList(UnloadShipmentInventory  inv) throws CustomException;
	//FlightDetails getFlightDetailsByULD(UnloadShipmentSearch uld) throws CustomException;
	//List<FlightDetails>  getFlightDetailsByShipmentNumber(UnloadShipmentSearch shipmentDetail) throws CustomException;
	//BigInteger getShipmentID(UnloadShipmentSearch shipmentDetail) throws CustomException;
	//List<Segment>  getSegmentDetail(FlightDetails flight)throws CustomException;
	//UnloadShipmentRequest  searchFlightDetails(FlightDetails searchResult) throws CustomException;
	boolean isShcExists(UnloadShipmentInventory inventory) throws CustomException;
	//Shipment getBookedPiecesAndWeight(FlightDetails loadedShipmentInfo) throws CustomException;
	
	/**
	 * this method deletes Special handling codes related to the that shipment
	 * 
	 * @return List<Integer>
	 * @throws CustomException
	 */
	List<Integer> deleteManifestShpmtHouseInfo(List<UnloadShipment> unloadShipmentList) throws CustomException;
	
	 /**
	  * deletes the Inventory if Load and unload shipment are same
	  * 
	 * @param inventory
	 * @return int
	 * @throws CustomException
	 */
	 int deleteShipmentInvetory(UnloadShipmentInventory inventory) throws CustomException ;
	 /**
	  * Substract  the piececs and weight in the Inventory for the existed flight if load and unloaded pieces are unequal
	  * 
	 * @param inventory
	 * @return int
	 * @throws CustomException
	 */
	int updateInventoryforExistedFlight(UnloadShipmentInventory inventory) throws CustomException;
	
	UnloadShipment getLoadedShipmentIdForZeroPieces(UnloadShipment unloadModel) throws CustomException;
	 

}
