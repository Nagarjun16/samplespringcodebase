package com.ngen.cosys.shipment.deletehousewaybill.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;


import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.deletehousewaybill.model.DeleteHouseWayBillResponseModel;
import com.ngen.cosys.shipment.deletehousewaybill.model.DeleteHouseWayBillSearchModel;
import com.ngen.cosys.shipment.deletehousewaybill.model.UpdateShipmentDetails;

public interface DeleteHouseWayBillDao {

	
	BigInteger checkInvPcWgt(DeleteHouseWayBillResponseModel fetchInvDataForDel)throws CustomException;

	void insertRemarksForDeletion(DeleteHouseWayBillResponseModel fetchInvDataForDel)throws CustomException;

	DeleteHouseWayBillResponseModel getShipmentIdInCaseInvIsNotAvlbl(DeleteHouseWayBillSearchModel deleteHouseWayBillSearchModel)throws CustomException;

	void deleteShipmentHouseInfo(DeleteHouseWayBillResponseModel shipmentId)throws CustomException;

	void deleteShipmentHouseSHCInfo(DeleteHouseWayBillResponseModel shipmentHseInf)throws CustomException;

	void deleteShipmentHouseCustomerInfo(DeleteHouseWayBillResponseModel shipmentHseInf)throws CustomException;

	void deleteShipmentHouseCustomerAddressInfo(BigInteger customerAddressInfoId)throws CustomException;

	List<BigInteger> getCustomerAddressInfoIds(DeleteHouseWayBillResponseModel shipmentHseInf)throws CustomException;

	BigInteger fetchShipmentHouseCustomerContactInfo(BigInteger x)throws CustomException;

	void deleteCustomerContactInfo(BigInteger fetchShipmentHouseCustomerContactInfo)throws CustomException;

	String checkForShipmentIsImportExport(DeleteHouseWayBillSearchModel deleteHouseWayBillSearchModel)throws CustomException;

	void deleteShpInvShcExprt(DeleteHouseWayBillResponseModel invDetls)throws CustomException;

	void updateHseWgnPieceWgt(DeleteHouseWayBillResponseModel invDetls)throws CustomException;

	BigInteger fetchHseWgnPieceWgt(DeleteHouseWayBillResponseModel invDetls)throws CustomException;


	BigInteger fetchEaccHseInfPieceWgt(DeleteHouseWayBillResponseModel invDetls)throws CustomException;

	void updateAcceptByHouseFlagdocInfo(DeleteHouseWayBillResponseModel invDetls) throws CustomException;

	void updateAcceptByHouseFlagInMaster(DeleteHouseWayBillResponseModel invDetls) throws CustomException;

    void deleteShipmentHouseDimensionDetails(DeleteHouseWayBillResponseModel shipmentHseInf)throws CustomException;
   
    void deleteShipmentInventoryDetails(DeleteHouseWayBillResponseModel shipmentHseInf)throws CustomException;
    
    void deleteShipmentImpBreakDownStorageDetails(DeleteHouseWayBillResponseModel shipmentHseInf)throws CustomException;
    
    void deleteShipmentFreightOutDetails(DeleteHouseWayBillResponseModel shipmentHseInf)throws CustomException;
    
  //Update Import Shipment details to Bial
  	Integer isFlightCompleted(BigInteger flightId) throws CustomException;
  	BigInteger getFlightId(Map<String, Object> requestMapForFlight) throws CustomException;
  	
  	public UpdateShipmentDetails fetchShipmentDetails(Map<String, Object> requestMap) throws CustomException;


}
