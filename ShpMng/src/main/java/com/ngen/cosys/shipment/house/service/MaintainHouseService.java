package com.ngen.cosys.shipment.house.service;

import com.ngen.cosys.dimension.model.Dimension;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.house.model.HouseModel;
import com.ngen.cosys.shipment.house.model.HouseSearch;
import com.ngen.cosys.shipment.house.model.MasterAirWayBillModel;

/**
 * This interface takes care of the responsibilities related to the HAWB Number
 * according to given AWB Number.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface MaintainHouseService {

	/**
	 * find all Hawb Number with given awb number
	 * 
	 * @param mhsAwb
	 * @return maintainHouse
	 * @throws CustomException
	 */
	MasterAirWayBillModel getMasterAWBInformation(MasterAirWayBillModel masterAWBRequestModel) throws CustomException;
	

	/**
	 * Method to save the individual house information
	 * 
	 * @param houseRequestModel
	 * @return HouseModel
	 * @throws CustomException
	 */
	HouseModel save(HouseModel houseRequestModel) throws CustomException;

	/**
	 * Method to delete multiple houses of an AWB
	 * 
	 * @param masterAWBRequestModel
	 * @return MasterAirWayBillModel
	 * @throws CustomException
	 */
	MasterAirWayBillModel delete(MasterAirWayBillModel masterAWBRequestModel) throws CustomException;
	/**
	 * Method to update individual house information
	 * 
	 * @param masterAWBRequestModel
	 * @return MasterAirWayBillModel
	 * @throws CustomException
	 */

	HouseModel getHouseAWBInformation(HouseModel houseRequestModel) throws CustomException;


   HouseModel getShipperAndconsigneeInfoForFirstHouse(HouseModel houseRequestModel) throws CustomException;
   
   MasterAirWayBillModel getHouseWayBillMaster(HouseSearch houseSearchRequest) throws CustomException;
   
   void setHouseWayBillMaster(MasterAirWayBillModel request) throws CustomException;
   

	

   // HAWB LIST starts Here

  	/**
  	 * Method to update individual house information
  	 * 
  	 * @param houseSearchRequest
  	 * @return List<HouseModel>
  	 * @throws CustomException
  	 */
  	MasterAirWayBillModel getHouseWayBillList(HouseSearch houseSearchRequest) throws CustomException;
    HouseModel getConsigneeShipperDetails(HouseSearch houseModel) throws CustomException;

	/**
	 * Method to save the individual house information
	 * 
	 * @param houseRequestModel
	 * @return HouseModel
	 * @throws CustomException
	 */
  	void onSaveHouseNumber(MasterAirWayBillModel houseRequestModel) throws CustomException;
    /**
	 * Method to save the individual house Consignee/ Shipper Details
	 * 
	 * @param houseRequestModel
	 * @return HouseModel
	 * @throws CustomException
	 */
  	void onSaveCustomerinformation(HouseModel houseRequestModel) throws CustomException;
    void onSaveCustomerinformationList(MasterAirWayBillModel houseRequestModel) throws CustomException;


  	public Dimension getVolumeWithVolumetricWeight(Dimension d) throws CustomException;
    HouseModel editHouseDimension(HouseModel houseModel) throws CustomException;

    // // HAWB LIST ends Here
  	
}