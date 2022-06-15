/*******************************************************************************
 * Copyright (c) 2021 Coforge Technologies PVT LTD
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

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

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
import com.ngen.cosys.icms.schema.flightbooking.TTTransferType;
import com.ngen.cosys.processing.engine.rule.fact.FactFlight;
import com.ngen.cosys.transfertype.model.TransferTypeModel;


public interface FlightBookingDao {

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
	 * This method helps to create flight booking
	 *
	 * @throws CustomException
	 * 
	 */
	boolean createFlightBooking(ShipmentFlightBookingDetails booking) throws CustomException;
	
	void createFlightPartDetail(ShipmentFlightPartDetails flightPartDetails);
		
	public boolean checkFlightBookingPrimaryKey(ShipmentFlightBookingDetails singleFlightBooking) throws CustomException; 
	
	public void deleteRemarks(Long flightBookId, Long partBookingId) throws CustomException;
	
	BigInteger getflightBookingIdForFlight(ShipmentFlightBookingDetails flightBooking) throws CustomException;
	
	/**
	 * This method helps to insert Dimension data
	 * 
	 * @param ShipmentPartBookingDimension
	 * @throws CustomException
	 * 
	 */
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
	
	String getFlightRemarks(BookingShipmentDetails bookingShipmentDetails);

	long getShipmentId(Map<String,String> map);
	
	List<ConstantValueDetails> getConstantValues();
	
	List<BookingCommodityDetails> fetchIntermidiateCommodityDetails(Map<String,String> map);
	
	List<DimensionDetaills> fetchIntermidiateCommodityDimensionDetails(String bookingCommodityDetailsId);
	
	BookingDetails fetchIntermidiateBookingDetails(Map<String,String> map);
	
	List<shipmentMasterRoutingInfo> fetchMasterRoutingInfo(String shipmentNo);
	
	List<BookingFlightDetails> fetchIntermidiateFlightDetails(String prefix,String masterNo);
	
	List<Boolean> hasDocumentArrived(String awbNumber);
	
	List<Boolean> hasAcceptanceWeight(String awbNumber);
	
	Boolean hasManifested(BookingFlightDetails bookingFlightDetails);
	
	public void createBookingShipment(ShipmentBookingDetails shipmentBookingDetails);
	
	void createPart(ShipmentFlightPartBookingDetails part) throws CustomException;
	
	public void createBookingFlight(BookingFlightDetails bookingFlightDetails);
	
	public void updateBooking(ShipmentBookingDetails shipmentBookingDetails);
	
	public void createPartShipmentBooking(ShipmentFlightPartBookingDetails parbookingDetails);
	
	int deleteFlightBooking(int flightBookingId);

	int getExistingFlightBookingId(BookingFlightDetails bookingFlightInfo);
	
	
	int checkInventoryExist(Map inputMap);
	
	PartSuffix getPartSuffix(String carrierCode);

	String getFlightBookingStatus(String flightBookingStatus);
	
	public List<SHCCodes> fetchSHCMaster();
	
	public List<String> fetchSccCodeFromBookingShc(Map<String,String> shipmentMap);

	List<BookingFlightDetails> getFlightId(Map<String, String> queryParam);
	
	OperationalFlightLegInfo getSegmentDepartureAndArrivalDate(Map<String, String> queryParam);
	
	int getFlightIdFromOperativeFlights(Map<String,String> map);
	

	 /**
	    * This method helps to get flight booking ID
	    * 
	    * @param flightBooking
	    * @throws CustomException
	  */
	 Long fetchFlightBookingId(ShipmentFlightBookingDetails flightBooking) throws CustomException;

	Boolean checkShipmentLoaded(Map<String, Object> queryMap);

	Boolean checkShipmentManifested(Map<String, Object> queryMap);

	Boolean checkShipmentsDeparted(Map<String, Object> queryMap);
	
	String getSegmentDepartureDate(Map<String, String> queryMap);
	
	List<ShipmentBookingRemark> getShipmentRemarks(Map<String,Object> shipmentNumber);
	
	List<ShipmentBookingRemark> getShipmentFlightRemarks(Map<String,Object> shipmentNumber);
	
	List<String> getExpBookingFlightRemarks(Map<String,Object> map);
	
	void createOutgoingMessageRecipients(Map<String,Object> recipient);
	
	List<String> fetchSccCodeFromShc(long shipmentId);

	void createFlightBookingSHC(List<SHCDetails> shcDetailsList);

	void createPartBookingSHC(List<SHCDetails> shcDetailsList);
	
	public List<BigInteger> getDimensionsId(Long obj) throws CustomException;
	
	public List<BigInteger> getDimensionsId(Map<String,Object> dimensionMap) throws CustomException;
	
	public void deleteDimensions(List<BigInteger> obj) throws CustomException;
	
	public void insertShipmentRemarksWithFlightId(ShipmentBookingRemark obj) throws CustomException;
	
	public InterfaceExternalSystemUrl getExternalUrlDetails(String icms);
	
	BookingShipmentDetails getShipmentDetails(BookingShipmentDetails bookingDetails);

	String getBookingStatus(String bookingStatus);

	boolean isBlacklistShipmentNumber(String shipmentNumber);
	
	boolean isFreightNAFlight(BookingFlightDetails flightDetails);
	
	void setCancelledBooking(List<BookingDeltaDetails> deltaList);

	String checkFlightClosedForBooking(BookingFlightDetails bookingFlightInfo);

	boolean checkFlightBookingExist(BookingFlightDetails bookingFlightDetails);
	
	public List<String> fetchShcGroupCode(String shc) throws CustomException;
	
	public FactFlight getFactDetails(Map<String,Object> queryParam);

	Integer fetchBookingVersion(String shipmentNumber);

	BigInteger fetchFlightSegmentId(Map<String, String> map);

	List<TransferTypeModel> getTransferTypes();
	
void insertInterfaceOutgoingMessageICMS(OutgoingMessageLog log);
	public void deleteFlightPartAssociation(Map<String,Long> queryParam) throws CustomException;	
}
