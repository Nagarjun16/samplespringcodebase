/*******************************************************************************
 * Copyright (c) 2021 Coforge PVT LTD
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.ngen.cosys.icms.dao.flightbooking;


import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.icms.schema.flightbooking.BookingFlightDetails;
import com.ngen.cosys.icms.schema.flightbooking.DimensionDetaills;
import com.ngen.cosys.icms.schema.flightbooking.SHCCodes;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.export.commonbooking.model.PartSuffix;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingRemark;
import com.ngen.cosys.icms.model.BookingInterface.BookingDeltaDetails;
import com.ngen.cosys.icms.model.BookingInterface.BookingShipment;
import com.ngen.cosys.icms.model.BookingInterface.InterfaceExternalSystemUrl;
import com.ngen.cosys.icms.model.BookingInterface.SHCDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingDimensions;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightPartBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightPartDetails;
import com.ngen.cosys.icms.model.bookingicms.BookingShipmentDetails;
import com.ngen.cosys.icms.model.bookingicms.ConstantValueDetails;
import com.ngen.cosys.icms.model.bookingicms.shipmentMasterRoutingInfo;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightLegInfo;
import com.ngen.cosys.icms.schema.flightbooking.BookingCommodityDetails;
import com.ngen.cosys.icms.schema.flightbooking.BookingDetails;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.ngen.cosys.icms.schema.flightbooking.TTTransferType;
import com.ngen.cosys.processing.engine.rule.fact.FactFlight;
import com.ngen.cosys.transfertype.model.TransferTypeModel;


public interface FlightBookingRepository {

	void insertIntermediateBookingDetails(BookingDetails bookingDetails, List<BookingFlightDetails> flightList);

	ShipmentBookingDetails getExistingShipmentBooking(Map<String, Object> queryParam);

	Boolean isPartExist(Long bookingId);

	ShipmentBookingDetails isShipmentInShipmentMaster(BookingShipment search);

	ShipmentBookingDetails getBookingData(BookingShipment search);

	ShipmentFlightPartBookingDetails fetchPartShcRemarksDimensionBookingDetails(
			ShipmentFlightPartBookingDetails part);

	ShipmentFlightBookingDetails fetchTotalBookingDetails(ShipmentFlightBookingDetails flight);
	
	List<TTTransferType> fetchTransferType();
	
	Boolean isShipmentLoaded(BookingShipment bookingShipment);
	
	/**
	 * This method helps to create a new single shipment booking
	 * 
	 * @param singleShipmentBooking
	 * @return SingleShipmentBooking
	 * @throws CustomException
	 */
	boolean createBooking(ShipmentBookingDetails singleShipmentBooking) throws CustomException;
	
	/**
	 * This method helps to validate primary key relation
	 * 
	 * @param singleFlightBooking
	 * @return boolean
	 * @throws CustomException
	 */
	public boolean checkFlightBookingPrimaryKey(ShipmentFlightBookingDetails singleFlightBooking) throws CustomException;
	
	public boolean createFlightBooking(ShipmentFlightBookingDetails booking) throws CustomException;
	
	void deleteRemarks(Long flightBookId, Long partBookingId) throws CustomException;
	
	BigInteger getflightBookingIdForFlight(ShipmentFlightBookingDetails flightBooking) throws CustomException;
	
	boolean createDimension(List<ShipmentBookingDimensions> dimension) throws CustomException;
	
	void insertShipmentRemarks(ShipmentBookingRemark obj) throws CustomException;
	
	void insertRemarks(ShipmentBookingRemark singleShipmentBooking) throws CustomException;
	
	public Long getShipmentId(ShipmentBookingDetails obj) throws CustomException;
	
	public Integer getSegmentId(ShipmentFlightBookingDetails obj) throws CustomException;
	
	public void deleteRemarks(Map<String,String> queryParam) throws CustomException;
	
	public List<BigInteger> getFlightDimensionsId(Long obj) throws CustomException;
	
	public void deleteFlightDimensions(List<BigInteger> obj) throws CustomException;
	
	public void deleteFlightPartAssociation(Long obj) throws CustomException;
	
	public void deleteFlight(ShipmentFlightBookingDetails booking) throws CustomException;
	
	public void deletePartDimensionByPartBookingId(Long partBookingId) throws CustomException;
	
	public void deletePart(ShipmentFlightPartBookingDetails booking) throws CustomException;
	
	public boolean deleteBooking(int bookingId) throws CustomException;
	
	List<BookingFlightDetails> fetchIntermidiateFlightDetails(Map<String,String> inputMap);
	
	List<shipmentMasterRoutingInfo> fetchMasterRoutingInfo(String shipmentNo);

	PartSuffix getPartSuffixDetails(String carrierCode);
	
	List<BookingCommodityDetails> fetchIntermidiateCommodityDetails(Map<String,String> awbNo);
	
	List<DimensionDetaills> fetchIntermidiateCommodityDimensionDetails(String bookingCommodityDetailsId);
	
	BookingDetails fetchIntermidiateBookingDetails(Map<String,String> awbNo);
	
	List<ConstantValueDetails> getConstantValues();

	long getShipmentId(Map<String,String> map);
	
	String getFlightRemarks(BookingShipmentDetails bookingShipmentDetails);
	
	List<Boolean> hasDocumentArrived(String awbNumber);
	
	List<Boolean> hasAcceptanceWeight(String awbNumber);
	
	public void createBookingShipment(ShipmentBookingDetails shipmentBookingDetails);
	
	public void createBookingFlight(BookingFlightDetails bookingFlightDetails);
	
	public void updateBooking(ShipmentBookingDetails shipmentBookingDetails);
	
	public void createPartShipmentBooking(ShipmentFlightPartBookingDetails parbookingDetails);
	
	int deleteFlightBooking(int flightBookingId);

	int getExistingFlightBookingId(BookingFlightDetails bookingFlightInfo);

	Boolean hasManifested(BookingFlightDetails bookingFlightDetails);
	
	int checkInventoryExist(Map inputMap);
	
	void createPart(ShipmentFlightPartBookingDetails part) throws CustomException;
	
	void createFlightPartDetail(ShipmentFlightPartDetails flightPartDetails);

	String getFlightBookingStatus(String flightBookingStatus);
	
	int getFlightIdFromOperativeFlights(Map<String,String> map);

	List<BookingFlightDetails> getFlightId(Map<String, String> queryParam);
	
	Long fetchFlightBookingId(ShipmentFlightBookingDetails flightBooking) throws CustomException;

	Boolean checkShipmentLoaded(Map<String, Object> queryMap);

	Boolean checkShipmentManifested(Map<String, Object> queryMap);

	Boolean checkShipmentsDeparted(Map<String, Object> queryMap);
	
	String getSegmentDepartureDate(Map<String,String> queryMap);
	
	List<ShipmentBookingRemark> getShipmentRemarks(Map<String,Object> shipmentNumber);
	
	void createOutgoingMessageRecipients(Map<String,Object> recipient);
	
	List<String> fetchSccCodeFromShc(long shipmentId);
	
	public List<String> fetchSccCodeFromBookingShc(Map<String,String> shipmentMap);

	void createFlightBookingSHC(List<SHCDetails> shcDetailsList);

	void createPartBookingSHC(List<SHCDetails> shcDetailsList);

	List<SHCCodes>fetchSHCMaster();
	
	List<ShipmentBookingRemark> getShipmentFlightRemarks(Map<String,Object> shipmentNumber);
	
	List<String> getExpBookingFlightRemarks(Map<String,Object> map);
	
	public List<BigInteger> getDimensionsId(Map<String,Object> dimensionMap) throws CustomException;
	
	public List<BigInteger> getDimensionsId(Long obj) throws CustomException;
		
	public void deleteDimensions(List<BigInteger> obj) throws CustomException;
	
	public void insertShipmentRemarksWithFlightId(ShipmentBookingRemark obj) throws CustomException;
	
	public InterfaceExternalSystemUrl getExternalUrlDetails(String icms);

	BookingShipmentDetails getShipmentDetails(BookingShipmentDetails bookingDetails);

	String getBookingStatus(String bookingStatus);

	boolean isBlacklistShipmentNumber(String shipmentNumber);

	boolean isFreightNAFlight(BookingFlightDetails flightDetails);
	
	public OperationalFlightLegInfo getSegmentDepartureAndArrivalDate(Map<String, String> queryParam);

	void setCancelledBooking(List<BookingDeltaDetails> deltaList);

	String checkFlightClosedForBooking(BookingFlightDetails bookingFlightInfo);

	boolean checkFlightBookingExist(BookingFlightDetails bookingFlightDetails);
	
	public List<String> fetchShcGroupCode(String shc) throws CustomException;
	
	public FactFlight getFactDetails(Map<String,Object> queryParam);

	Integer fetchBookingVersion(String shipmentNumber);

	BigInteger fetchFlightSegmentID(Map<String, String> map);

	List<TransferTypeModel> getTransferTypes();
	
	public void deleteFlightPartAssociation(Map<String,Long> queryParam) throws CustomException;

	void insertInterfaceOutgoingMessage(OutgoingMessageLog log);


}
