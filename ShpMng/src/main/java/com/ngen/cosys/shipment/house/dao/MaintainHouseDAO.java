package com.ngen.cosys.shipment.house.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.house.model.HouseCustomerModel;
import com.ngen.cosys.shipment.house.model.HouseDescriptionOfGoodsModel;
import com.ngen.cosys.shipment.house.model.HouseDimensionDetailsModel;
import com.ngen.cosys.shipment.house.model.HouseDimensionModel;
import com.ngen.cosys.shipment.house.model.HouseHarmonisedTariffScheduleModel;
import com.ngen.cosys.shipment.house.model.HouseModel;
import com.ngen.cosys.shipment.house.model.HouseOtherChargeDeclarationModel;
import com.ngen.cosys.shipment.house.model.HouseOtherCustomsInformationModel;
import com.ngen.cosys.shipment.house.model.HouseSearch;
import com.ngen.cosys.shipment.house.model.HouseSpecialHandlingCodeModel;
import com.ngen.cosys.shipment.house.model.MasterAirWayBillModel;

public interface MaintainHouseDAO {

	/**
	 * Method to retrieve whether shipment destination/shipment booking offpoint
	 * belongs to china
	 * 
	 * @param houseRequestModel
	 * @return Boolean - true if belongs to china OR false
	 * @throws CustomException
	 */
	Boolean isAirportBelongsToChina(HouseModel houseRequestModel) throws CustomException;

	/**
	 * 1. find all Hawb Number with given awb number
	 * 
	 * @param mhsAwb
	 * @return maintainHouse
	 * @throws CustomException
	 */

	MasterAirWayBillModel getMasterAWBInformation(MasterAirWayBillModel masterAWBRequestModel) throws CustomException;

	/**
	 * Method to get house information if exists
	 * 
	 * @param houseRequestModel
	 * @return HouseModel and it's associated information
	 * @throws CustomException
	 */
	HouseModel getHouseAWBInformation(HouseModel houseRequestModel) throws CustomException;

	/**
	 * 3. Method to save the AWB information for a house
	 * 
	 * @param masterAWBRequestModel
	 * @throws CustomException
	 */
	MasterAirWayBillModel saveMasterAWB(MasterAirWayBillModel masterAWBRequestModel) throws CustomException;

	/**
	 * 4. Method to save the House information for a house
	 * 
	 * @param houseRequestModel
	 * @throws CustomException
	 */
	HouseModel saveHouse(HouseModel houseRequestModel) throws CustomException;

	/**
	 * 5. Method to save the Customer information for a house
	 * 
	 * @param houseCustomerRequestModel
	 * @throws CustomException
	 */
	HouseCustomerModel saveHouseCustomer(HouseCustomerModel houseCustomerRequestModel) throws CustomException;

	/**
	 * 6. Method to save the Description of Goods information for a house
	 * 
	 * @param houseDescriptionOfGoodsRequestModel
	 * @throws CustomException
	 */
	void saveHouseDescriptionOfGoods(List<HouseDescriptionOfGoodsModel> houseDescriptionOfGoodsRequestModel)
			throws CustomException;

	/**
	 * 7. Method to save the Other Customs information for a house
	 * 
	 * @param houseOtherCustomsInformationRequestModel
	 * @throws CustomException
	 */
	void saveHouseOtherCustomInfo(List<HouseOtherCustomsInformationModel> houseOtherCustomsInformationRequestModel)
			throws CustomException;

	/**
	 * 8. Method to save the Other Charge Declaration information for a house
	 * 
	 * @param houseHarmonisedTariffScheduleRequestModel
	 * @throws CustomException
	 */
	void saveHarmonisedTariffScheduleInfo(
			List<HouseHarmonisedTariffScheduleModel> houseHarmonisedTariffScheduleRequestModel) throws CustomException;

	/**
	 * 9. Method to save the SHC information for a house
	 * 
	 * @param houseSpecialHandlingCodeRequestModel
	 * @param houseModel
	 * @throws CustomException
	 */
	void saveHouseSHC(HouseModel houseModel, List<HouseSpecialHandlingCodeModel> houseSpecialHandlingCodeRequestModel)
			throws CustomException;

	/**
	 * 10. Method to save the Other Charge Declaration information for a house
	 * 
	 * @param houseOtherChargeDeclarationModel
	 * @throws CustomException
	 */
	void saveHouseChargeDeclaration(HouseOtherChargeDeclarationModel houseOtherChargeDeclarationModel)
			throws CustomException;

	/**
	 * 11. Method to delete multiple houses of an AWB
	 * 
	 * @param masterAWBRequestModel
	 * @throws CustomException
	 */
	void delete(MasterAirWayBillModel masterAWBRequestModel) throws CustomException;

	/**
	 * Method to check duplicate HAWB Number
	 * 
	 * @param houseRequestModel
	 * @return boolean
	 * @throws CustomException
	 */
	boolean checkDuplicateHAWB(HouseModel houseRequestModel) throws CustomException;

	/**
	 * Method to get first house shipper and coonsignee info
	 * 
	 * @param houseRequestModel
	 * @return
	 * @throws CustomException
	 */
	HouseModel getShipperAndconsigneeInfoForFirstHouse(HouseModel houseRequestModel) throws CustomException;

	/**
	 * Method to get customer and shipment info for applying charges on FHL manual
	 * creation
	 * 
	 * @param houseRequestModel
	 * @return HouseModel - It consists of Shipment Id and Customer Id
	 * @throws CustomException
	 */
	HouseModel getShipmentInfoForCharges(HouseModel houseRequestModel) throws CustomException;

	/**
	 * Method to update the total sum of piece/weight
	 * 
	 * @param masterAWBRequestModel
	 * @return MasterAirWayBillModel
	 * @throws CustomException
	 */
	MasterAirWayBillModel saveUpdateAWB(MasterAirWayBillModel masterAWBRequestModel) throws CustomException;

	void validShc(HouseSpecialHandlingCodeModel request) throws CustomException;

	void onSaveHouseShc(HouseSpecialHandlingCodeModel houseModel) throws CustomException;

	void onSaveHouseShcUpdate(HouseSpecialHandlingCodeModel houseModel) throws CustomException;

	void onSaveHouseShcDelete(HouseSpecialHandlingCodeModel houseModel) throws CustomException;

	void onSaveConsigneeInformationInsert(HouseCustomerModel houseModel) throws CustomException;

	void onSaveConsigneeInformationUpdateMaster(HouseCustomerModel request) throws CustomException;

	void onSaveConsigneeInformationDelete(HouseCustomerModel request) throws CustomException;

	void onSaveShipperInformationInsert(HouseCustomerModel houseModel) throws CustomException;

	void onSaveShipperInformationUpdateMaster(HouseCustomerModel request) throws CustomException;

	void onSaveShipperInformationDelete(HouseCustomerModel request) throws CustomException;

	void houseValidation(MasterAirWayBillModel request) throws CustomException;

	void setHouseWayBillMaster(MasterAirWayBillModel request) throws CustomException;

	MasterAirWayBillModel getHouseWayBillMaster(HouseSearch houseSearch) throws CustomException;

	// HAWBLIST starts here
	/**
	 * Find all Hawb Number and Awb Master Data with given awb number
	 * 
	 * @param mhsAwb
	 * @return maintainHouse
	 * @throws CustomException
	 */
	MasterAirWayBillModel getHouseWayBillList(HouseSearch houseSearchRequest) throws CustomException;

	void houseListValidation(HouseModel request) throws CustomException;

	void onSaveHouseNumber(HouseModel houseModel) throws CustomException;

	void onSaveHouseNumberUpdate(HouseModel houseModel) throws CustomException;

	void onSaveConsigneeInformationUpdate(HouseCustomerModel houseModel) throws CustomException;

	void onSaveShipperInformationUpdate(HouseCustomerModel houseModel) throws CustomException;

	HouseModel getConsigneeShipperDetails(HouseSearch houseModel) throws CustomException;

	void onSaveConsigneeInformationDeleteData(HouseCustomerModel request) throws CustomException;

	void onSaveShipperInformationDeleteData(HouseCustomerModel request) throws CustomException;

	void updateMainMastersSHCData(MasterAirWayBillModel information) throws CustomException;

	Integer insertHouseDimension(HouseDimensionModel houseModel) throws CustomException;

	void insertHouseDimensionDetails(HouseDimensionDetailsModel dimensionModel) throws CustomException;

	void deleteDimension(BigInteger houseDimensionDtlsId) throws CustomException;

	void updateHouseDimension(HouseDimensionModel houseModel) throws CustomException;

	void updateDimensionalDetails(HouseDimensionDetailsModel dimensionModel) throws CustomException;

	void updateHouseVolumetricWeight(HouseDimensionModel houseDimension) throws CustomException;

	void updateHouseChargeableWeight(HouseModel houseModel) throws CustomException;

	void updateAWBChargeableWeight(HouseModel houseModel) throws CustomException;

	void updateDimension(HouseDimensionDetailsModel dimensionDtlsModel) throws CustomException;

	Integer getAppointmentAgent(BigInteger appointedAgent) throws CustomException;

	// Hawb list end here
	
	//Update Import Shipment details to Bial
	Integer isFlightCompleted(BigInteger flightId) throws CustomException;
	BigInteger getFlightId(BigInteger shipmentId) throws CustomException;
	
	BigInteger fetchAppointedAgent() throws CustomException;
	
	
	

}