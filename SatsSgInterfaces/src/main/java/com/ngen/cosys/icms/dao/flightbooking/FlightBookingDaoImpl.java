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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.icms.schema.flightbooking.BookingFlightDetails;
import com.ngen.cosys.icms.schema.flightbooking.DimensionDetaills;
import com.ngen.cosys.icms.schema.flightbooking.SHCCodes;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
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


@Service
public class FlightBookingDaoImpl implements FlightBookingDao {

	@Autowired
	private FlightBookingRepository flightBookingRepository;


	@Override
	public void insertIntermediateBookingDetails(BookingDetails bookingDetails, List<BookingFlightDetails> flightList) {
		flightBookingRepository.insertIntermediateBookingDetails(bookingDetails,flightList);
	}


	@Override
	public ShipmentBookingDetails getExistingShipmentBooking(Map<String, Object> queryParam) {
		return flightBookingRepository.getExistingShipmentBooking(queryParam);
	}


	@Override
	public Boolean isPartExist(Long bookingId) {
		return flightBookingRepository.isPartExist(bookingId);
	}


	@Override
	public ShipmentBookingDetails isShipmentInShipmentMaster(BookingShipment search) {
		return flightBookingRepository.isShipmentInShipmentMaster(search);
	}


	@Override
	public ShipmentBookingDetails getBookingData(BookingShipment search) {
		return flightBookingRepository.getBookingData(search);
	}


	@Override
	public ShipmentFlightPartBookingDetails fetchPartShcRemarksDimensionBookingDetails(
			ShipmentFlightPartBookingDetails part) {
		return flightBookingRepository.fetchPartShcRemarksDimensionBookingDetails(part);
	}


	@Override
	public ShipmentFlightBookingDetails fetchTotalBookingDetails(ShipmentFlightBookingDetails flight) {
		return flightBookingRepository.fetchTotalBookingDetails(flight);
	}
	
	@Override
	public List<TTTransferType> fetchTransferType() {
		List<TTTransferType> transferTypeList = flightBookingRepository.fetchTransferType();
		if (!transferTypeList.isEmpty()) {
			for (TTTransferType transferType : transferTypeList) {
				transferType.setMinConnTime(Integer.parseInt(transferType.getToMinutes()) - Integer.parseInt(transferType.getFromMinutes()));
			}
		}
		return transferTypeList;
	}
	
	@Override
	public Boolean isShipmentLoaded(BookingShipment bookingShipment) {
		return flightBookingRepository.isShipmentLoaded(bookingShipment);
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENT_BOOKING)
	public boolean createBooking(ShipmentBookingDetails singleShipmentBooking) throws CustomException {
	      return flightBookingRepository.createBooking(singleShipmentBooking);
	}

/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.export.booking.service.ExportValidatorService#
	 * checkFlightBookingPrimaryKey(com.ngen.cosys.export.booking.model.
	 * SingleShipmentFlightBooking)
	 */
	@Override
	public boolean checkFlightBookingPrimaryKey(ShipmentFlightBookingDetails singleFlightBooking)
			throws CustomException {
		return flightBookingRepository.checkFlightBookingPrimaryKey(singleFlightBooking);
	}
	
	@Override
	public boolean createFlightBooking(ShipmentFlightBookingDetails booking) throws CustomException {
	      return flightBookingRepository.createFlightBooking(booking);
	 }

	@Override
	public void deleteRemarks(Long flightBookId, Long partBookingId) throws CustomException {
		 flightBookingRepository.deleteRemarks(flightBookId,partBookingId);
	}


	@Override
	public BigInteger getflightBookingIdForFlight(ShipmentFlightBookingDetails flightBooking)
			throws CustomException {
		return flightBookingRepository.getflightBookingIdForFlight(flightBooking);
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.DIMENSION_BOOKING)
	public boolean createDimension(List<ShipmentBookingDimensions> dimension) throws CustomException {
		return flightBookingRepository.createDimension(dimension);
	}

	@Override
	public void insertShipmentRemarks(ShipmentBookingRemark obj) throws CustomException {
		flightBookingRepository.insertShipmentRemarks(obj);
	}


	@Override
	public void insertRemarks(ShipmentBookingRemark singleShipmentBooking) throws CustomException {
		flightBookingRepository.insertRemarks(singleShipmentBooking);
	}
	
	@Override
	public Long getShipmentId(ShipmentBookingDetails obj) throws CustomException{
		return flightBookingRepository.getShipmentId(obj);
	}
	
	@Override
	public Integer getSegmentId(ShipmentFlightBookingDetails obj) throws CustomException{
		return flightBookingRepository.getSegmentId(obj);
	}
	
	@Override
	public void deleteRemarks(Map<String,String> queryParam) throws CustomException{
		flightBookingRepository.deleteRemarks(queryParam);
	}
	
	@Override
	public List<BigInteger> getFlightDimensionsId(Long obj) throws CustomException{
		return flightBookingRepository.getFlightDimensionsId(obj);
	}
	
	@Override
	public void deleteFlightDimensions(List<BigInteger> obj) throws CustomException{
		 flightBookingRepository.deleteFlightDimensions(obj);
	}
	
	@Override
	public void deleteFlightPartAssociation(Long obj) throws CustomException{
		flightBookingRepository.deleteFlightPartAssociation(obj);
	}
	
	@Override
	public void deleteFlightPartAssociation(Map<String,Long> queryParam) throws CustomException{
		flightBookingRepository.deleteFlightPartAssociation(queryParam);
	}
	
	@Override
	public void deleteFlight(ShipmentFlightBookingDetails booking) throws CustomException{
		flightBookingRepository.deleteFlight(booking);
	}
	
	@Override
	public void deletePartDimensionByPartBookingId(Long partBookingId) throws CustomException{
		flightBookingRepository.deletePartDimensionByPartBookingId(partBookingId);
	}
	
	@Override
	public void deletePart(ShipmentFlightPartBookingDetails booking) throws CustomException{
		flightBookingRepository.deletePart(booking);
	}
	
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENT_BOOKING)
	public boolean deleteBooking(int bookingId) throws CustomException {
		return flightBookingRepository.deleteBooking(bookingId);
	}
	
	@Override
	public String getFlightRemarks(BookingShipmentDetails bookingShipmentDetails) {
		return this.flightBookingRepository.getFlightRemarks(bookingShipmentDetails);
	}

	@Override
	public long getShipmentId(Map<String,String> map) {
		return this.flightBookingRepository.getShipmentId(map);
	}

	@Override
	public List<ConstantValueDetails> getConstantValues() {
		return this.flightBookingRepository.getConstantValues();
	}


	@Override
	public List<BookingFlightDetails> fetchIntermidiateFlightDetails(String prefix, String masterNo) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("prefix", prefix);
		map.put("masterNo", masterNo);
		return this.flightBookingRepository.fetchIntermidiateFlightDetails(map);
	}

	@Override
	public List<shipmentMasterRoutingInfo> fetchMasterRoutingInfo(String shipmentNo) {
		return this.flightBookingRepository.fetchMasterRoutingInfo(shipmentNo);
	}
	

	@Override
	public List<BookingCommodityDetails> fetchIntermidiateCommodityDetails(Map<String,String> map) {
		return this.flightBookingRepository.fetchIntermidiateCommodityDetails(map);
	}
	
	@Override
	public List<DimensionDetaills> fetchIntermidiateCommodityDimensionDetails(String bookingCommodityDetailsId) {
		return this.flightBookingRepository.fetchIntermidiateCommodityDimensionDetails(bookingCommodityDetailsId);
	}
	
	@Override
	public BookingDetails fetchIntermidiateBookingDetails(Map<String,String> map) {
		return this.flightBookingRepository.fetchIntermidiateBookingDetails(map);
	}
	
	@Override
	public List<Boolean> hasDocumentArrived(String awbNumber) {
		return flightBookingRepository.hasDocumentArrived(awbNumber);
	}
	
	@Override
	public List<Boolean> hasAcceptanceWeight(String awbNumber) {
		return flightBookingRepository.hasAcceptanceWeight(awbNumber);
	}


	@Override
	public Boolean hasManifested(BookingFlightDetails bookingFlightDetails) {
		return flightBookingRepository.hasManifested(bookingFlightDetails);
	}


	@Override
	public void createBookingShipment(ShipmentBookingDetails shipmentBookingDetails) {
		flightBookingRepository.createBookingShipment(shipmentBookingDetails);
		
	}


	@Override
	public void createBookingFlight(BookingFlightDetails bookingFlightDetails) {
		flightBookingRepository.createBookingFlight(bookingFlightDetails);
		
	}


	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENT_BOOKING)
	public void updateBooking(ShipmentBookingDetails shipmentBookingDetails) {
		flightBookingRepository.updateBooking(shipmentBookingDetails);
		
	}


	@Override
	//@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENTPART_BOOKING)
	public void createPartShipmentBooking(ShipmentFlightPartBookingDetails parbookingDetails) {
		flightBookingRepository.createPartShipmentBooking(parbookingDetails);
		
	}
	
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENTPART_BOOKING)
	public void createPart(ShipmentFlightPartBookingDetails part) throws CustomException{
		flightBookingRepository.createPart(part);
	}
	
	@Override
	public int deleteFlightBooking(int flightBookingId) {
		return this.flightBookingRepository.deleteFlightBooking(flightBookingId);
	}

	@Override
	public int getExistingFlightBookingId(BookingFlightDetails bookingFlightInfo) {
		return this.flightBookingRepository.getExistingFlightBookingId(bookingFlightInfo);
	}

	@Override
	public int checkInventoryExist(Map inputMap) {
		return this.flightBookingRepository.checkInventoryExist(inputMap);
	}

	@Override
	public PartSuffix getPartSuffix(String carrierCode) {
		return flightBookingRepository.getPartSuffixDetails(carrierCode);
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.FLIGHTPART_BOOKING)
	public void createFlightPartDetail(ShipmentFlightPartDetails flightPartDetails) {
		flightBookingRepository.createFlightPartDetail(flightPartDetails);
	}

	@Override
	public List<SHCCodes> fetchSHCMaster() {
		return flightBookingRepository.fetchSHCMaster();
	}

	@Override
	public String getFlightBookingStatus(String flightBookingStatus) {
		return flightBookingRepository.getFlightBookingStatus(flightBookingStatus);
	}
	@Override
	public List<BookingFlightDetails> getFlightId(Map<String, String> queryParam) {
		return flightBookingRepository.getFlightId(queryParam);
	}
	
	@Override
	public OperationalFlightLegInfo getSegmentDepartureAndArrivalDate(Map<String, String> queryParam) {
		return flightBookingRepository.getSegmentDepartureAndArrivalDate(queryParam);
	}

	@Override
	public int getFlightIdFromOperativeFlights(Map<String, String> map) {
		return flightBookingRepository.getFlightIdFromOperativeFlights(map);
	}

	@Override
	public Long fetchFlightBookingId(ShipmentFlightBookingDetails flightBooking) throws CustomException {
		return flightBookingRepository.fetchFlightBookingId(flightBooking);
	}

	@Override
	public Boolean checkShipmentLoaded(Map<String, Object> queryMap) {
		return flightBookingRepository.checkShipmentLoaded(queryMap);
	}

	@Override
	public Boolean checkShipmentManifested(Map<String, Object> queryMap) {
		return flightBookingRepository.checkShipmentManifested(queryMap);
	}

	@Override
	public Boolean checkShipmentsDeparted(Map<String, Object> queryMap) {
		return flightBookingRepository.checkShipmentsDeparted(queryMap);
	}

	@Override
	public String getSegmentDepartureDate(Map<String, String> queryMap) {
		return flightBookingRepository.getSegmentDepartureDate(queryMap);
	}

	@Override
	public List<ShipmentBookingRemark> getShipmentRemarks(Map<String,Object> shipmentNumber) {
		return flightBookingRepository.getShipmentRemarks(shipmentNumber);
	}

	@Override
	public void createOutgoingMessageRecipients(Map<String,Object> recipient) {
		 flightBookingRepository.createOutgoingMessageRecipients(recipient);
	}
	
	@Override
	public List<String> fetchSccCodeFromShc(long shipmentId) {
		return flightBookingRepository.fetchSccCodeFromShc(shipmentId);
	}
	
	@Override
	public List<String> fetchSccCodeFromBookingShc(Map<String,String> shipmentMap) {
		return flightBookingRepository.fetchSccCodeFromBookingShc(shipmentMap);
	}

	@Override
	public void createFlightBookingSHC(List<SHCDetails> shcDetailsList) {
		flightBookingRepository.createFlightBookingSHC(shcDetailsList);
	}


	@Override
	public void createPartBookingSHC(List<SHCDetails> shcDetailsList) {
		flightBookingRepository.createPartBookingSHC(shcDetailsList);
	}


	@Override
	public List<ShipmentBookingRemark> getShipmentFlightRemarks(Map<String, Object> shipmentNumber) {
		return flightBookingRepository.getShipmentFlightRemarks(shipmentNumber);
	}


	@Override
	public List<String> getExpBookingFlightRemarks(Map<String,Object> map) {
		return flightBookingRepository.getExpBookingFlightRemarks(map);
	}


	@Override
	public List<BigInteger> getDimensionsId(Map<String,Object> dimensionMap) throws CustomException {
		return flightBookingRepository.getDimensionsId(dimensionMap);
	}
	
	@Override
	public List<BigInteger> getDimensionsId(Long dimensionMap) throws CustomException {
		return flightBookingRepository.getDimensionsId(dimensionMap);
	}


	@Override
	public void deleteDimensions(List<BigInteger> obj) throws CustomException {
		flightBookingRepository.deleteDimensions(obj);
	}
	
	@Override
	public void insertShipmentRemarksWithFlightId(ShipmentBookingRemark obj) throws CustomException{
		flightBookingRepository.insertShipmentRemarksWithFlightId(obj);
	}


	@Override
	public InterfaceExternalSystemUrl getExternalUrlDetails(String icms) {
		return flightBookingRepository.getExternalUrlDetails(icms);
	}

	
	@Override
	public BookingShipmentDetails getShipmentDetails(BookingShipmentDetails bookingDetails) {
		return flightBookingRepository.getShipmentDetails(bookingDetails);
	}


	@Override
	public String getBookingStatus(String bookingStatus) {
		return flightBookingRepository.getBookingStatus(bookingStatus);
	}


	@Override
	public boolean isBlacklistShipmentNumber(String shipmentNumber) {
		return flightBookingRepository.isBlacklistShipmentNumber(shipmentNumber);
	}


	@Override
	public boolean isFreightNAFlight(BookingFlightDetails flightDetails) {
		return flightBookingRepository.isFreightNAFlight(flightDetails);
	}


	@Override
	public void setCancelledBooking(List<BookingDeltaDetails> deltaList) {
		flightBookingRepository.setCancelledBooking(deltaList);
	}


	@Override
	public String checkFlightClosedForBooking(BookingFlightDetails bookingFlightInfo) {
		return flightBookingRepository.checkFlightClosedForBooking(bookingFlightInfo);
	}


	@Override
	public boolean checkFlightBookingExist(BookingFlightDetails bookingFlightDetails) {
		return flightBookingRepository.checkFlightBookingExist(bookingFlightDetails);
	}
	
	@Override
	public List<String> fetchShcGroupCode(String shc) throws CustomException {
		return flightBookingRepository.fetchShcGroupCode(shc);
	}
	
	@Override
	public FactFlight getFactDetails(Map<String,Object> queryParam) {
		return flightBookingRepository.getFactDetails(queryParam);
	}


	@Override
	public Integer fetchBookingVersion(String shipmentNumber) {
		return flightBookingRepository.fetchBookingVersion(shipmentNumber);
	}


	@Override
	public BigInteger fetchFlightSegmentId(Map<String, String> map) {
		return flightBookingRepository.fetchFlightSegmentID(map);
	}


	@Override
	public List<TransferTypeModel> getTransferTypes() {
		return flightBookingRepository.getTransferTypes();
	}


	@Override
	public void insertInterfaceOutgoingMessageICMS(OutgoingMessageLog log) {
		flightBookingRepository.insertInterfaceOutgoingMessage(log);
	}
	

}
