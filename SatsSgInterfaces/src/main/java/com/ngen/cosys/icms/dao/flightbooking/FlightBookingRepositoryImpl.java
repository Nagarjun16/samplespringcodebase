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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;


import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.icms.dao.AbstractBaseDAO;
import com.ngen.cosys.icms.schema.flightbooking.BookingFlightDetails;
import com.ngen.cosys.icms.schema.flightbooking.DimensionDetaills;
import com.ngen.cosys.icms.schema.flightbooking.FlightDetails;
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
import com.ngen.cosys.icms.schema.flightbooking.FlightPair;
import com.ngen.cosys.icms.schema.flightbooking.SHCCodes;
import com.ngen.cosys.icms.schema.flightbooking.TTTransferType;
import com.ngen.cosys.icms.util.BookingConstant;
import com.ngen.cosys.processing.engine.rule.fact.FactFlight;
import com.ngen.cosys.transfertype.model.TransferTypeModel;



/**
 * @author Rashmi.Kushwaha
 *
 */
@Repository
public class FlightBookingRepositoryImpl extends AbstractBaseDAO implements FlightBookingRepository {
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightBookingRepositoryImpl.class);
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionBooking;

	/**
	 * BookingDetails
	 */
	@Override
	public void insertIntermediateBookingDetails(BookingDetails bookingDetails,List<BookingFlightDetails> flightList) {
		LOGGER.info("Start method insertIntermediateBookingDetails"+ bookingDetails.toString());
		String bookingDetailsId = fetchObject("checkBookingExist", bookingDetails.getAWBNumber(), sqlSessionBooking);
		this.deleteDataIfAWBNumberExist(bookingDetails, bookingDetailsId);

		insertData("insertBookingDetails", bookingDetails, sqlSessionBooking);
		if (bookingDetails.getBookingFlightPairDetails() != null) {
			this.inserIntoBookingFlightPairDetails(bookingDetails, bookingDetails.getAWBNumber());
		}
		if (bookingDetails.getShipmentIdentifierDetails() != null) {
			this.insertIntoShipmentIdentifierDetailsTables(bookingDetails);
		}
		if (bookingDetails.getShipmentDetails() != null) {
			this.insertIntoBookingShipmentDetailsTables(bookingDetails);
		}
		if (bookingDetails.getBookingCommodityDetails() != null) {
			this.insertIntoBookingCommodityDetailsTables(bookingDetails);
		}
		if (bookingDetails.getOtherChargeDetails() != null) {
			insertIntoChargeDetailsTables(bookingDetails);
		}
		if (!CollectionUtils.isEmpty(flightList)) {
			this.inserIntoBookingFlightDetailsTables(bookingDetails,flightList);
		}
		LOGGER.info("End method insertIntermediateBookingDetails");
	}

	private void inserIntoBookingFlightDetailsTables(BookingDetails bookingDetails, List<BookingFlightDetails> flightList) {
		flightList.forEach(e1 -> e1.setBookingDetailsId(bookingDetails.getBookingDetailsId()));
		insertList("insertBookingFlightDetails", flightList, sqlSessionBooking);
	}

	private void deleteDataIfAWBNumberExist(BookingDetails bookingDetails, String bookingDetailsId) {
		if (bookingDetailsId != null) {
			deleteData("deleteShipmentDetails", bookingDetailsId, sqlSessionBooking);
			deleteData("deleteBookingCommodityDetails", bookingDetailsId, sqlSessionBooking);
			deleteData("deleteBookingDetails", bookingDetailsId, sqlSessionBooking);
		}
	}

	private void inserIntoBookingFlightPairDetails(BookingDetails bookingDetails, String awbNumber) {
		if (!CollectionUtils.isEmpty(bookingDetails.getBookingFlightPairDetails().getFlightPair())) {
			for (FlightPair flightPair : bookingDetails.getBookingFlightPairDetails().getFlightPair()) {
				List<FlightDetails> sortedFlightPairDetails = new ArrayList<>();
				flightPair.getFlightDetails().forEach(e1 -> e1.setAwbNumber(awbNumber));
				flightPair.getFlightDetails()
						.forEach(e1 -> e1.setFlightNumber(e1.getFlightCarrierCode() + e1.getFlightNumber()));
				flightPair.getFlightDetails()
						.forEach(e1 -> e1.setBookingDetailsId(bookingDetails.getBookingDetailsId()));
				for(FlightDetails flight : flightPair.getFlightDetails()) {
					if(flight.getSegmentDestination().equalsIgnoreCase(BookingConstant.TENANT_SINGAPORE)) {
						sortedFlightPairDetails.add(flight);
					}
				}
				for(FlightDetails flight : flightPair.getFlightDetails()) {
					if(flight.getSegmentOrigin().equalsIgnoreCase(BookingConstant.TENANT_SINGAPORE)) {
						sortedFlightPairDetails.add(flight);
					}
				}
				
				insertList("insertFlightPairingDetails", sortedFlightPairDetails, sqlSessionBooking);
			}
		}
	}

	private void insertIntoBookingCommodityDetailsTables(BookingDetails bookingDetails) {
		if(!CollectionUtils.isEmpty(bookingDetails.getBookingCommodityDetails())) {
			for(BookingCommodityDetails commodity : bookingDetails.getBookingCommodityDetails()) {
				commodity.setBookingDetailsId(bookingDetails.getBookingDetailsId());
				insertData("insertBookingCommodityDetails", commodity, sqlSessionBooking);
		
				if (commodity.getDimensionDetaills() != null) {
					commodity.getDimensionDetaills()
							.forEach(e1 -> e1.setBookingCommodityDetailsId(
									commodity.getBookingCommodityDetailsId()));
					insertList("insertDimentionDetails", commodity.getDimensionDetaills(),
							sqlSessionBooking);
				}
				if (commodity.getRatingDetails() != null) {
					commodity.getRatingDetails().setBookingCommodityDetailsId(
							commodity.getBookingCommodityDetailsId());
					insertData("insertRatingDetails", commodity.getRatingDetails(),
							sqlSessionBooking);
				}
			}
		}

	}

	private void insertIntoChargeDetailsTables(BookingDetails bookingDetails) {
		bookingDetails.getOtherChargeDetails().forEach(a->a.setBookingDetailsId(bookingDetails.getBookingDetailsId()));
		insertList("insertOtherChargeDetails", bookingDetails.getOtherChargeDetails(), sqlSessionBooking);
		bookingDetails.getOtherChargeDetails().forEach(a->a.setBookingDetailsId(bookingDetails.getBookingDetailsId()));
	}

	private void insertIntoShipmentIdentifierDetailsTables(BookingDetails bookingDetails) {
		bookingDetails.getShipmentIdentifierDetails().setBookingDetailsId(bookingDetails.getBookingDetailsId());
		insertData("insertShipmentIdentifierDetails", bookingDetails.getShipmentIdentifierDetails(), sqlSessionBooking);
		bookingDetails.getShipmentIdentifierDetails().setBookingDetailsId(bookingDetails.getBookingDetailsId());
	}

	private void insertIntoBookingShipmentDetailsTables(BookingDetails bookingDetails) {
		bookingDetails.getShipmentDetails().setBookingDetailsId(bookingDetails.getBookingDetailsId());
		insertData("insertShipmentDetails", bookingDetails.getShipmentDetails(), sqlSessionBooking);

		if (bookingDetails.getShipmentDetails().getBookingShipperDetails() != null) {
			bookingDetails.getShipmentDetails().getBookingShipperDetails()
					.setShipmentDetailsId(bookingDetails.getShipmentDetails().getShipmentDetailsId());
			insertData("insertBookingShipperDetails", bookingDetails.getShipmentDetails().getBookingShipperDetails(),
					sqlSessionBooking);
		}
		if (bookingDetails.getShipmentDetails().getBookingConsigneeDetails() != null) {
			bookingDetails.getShipmentDetails().getBookingConsigneeDetails()
					.setShipmentDetailsId(bookingDetails.getShipmentDetails().getShipmentDetailsId());
			insertData("insertBookingConsigneeDetails",
					bookingDetails.getShipmentDetails().getBookingConsigneeDetails(), sqlSessionBooking);
		}
		if (bookingDetails.getShipmentDetails().getProductDetails() != null) {
			bookingDetails.getShipmentDetails().getProductDetails()
					.setShipmentDetailsId(bookingDetails.getShipmentDetails().getShipmentDetailsId());
			insertData("insertProductDetails", bookingDetails.getShipmentDetails().getProductDetails(),
					sqlSessionBooking);
		}

	}

	@Override
	public ShipmentBookingDetails getExistingShipmentBooking(Map<String, Object> queryParam) {
		return fetchObject("getExistingShipmentBooking", queryParam, sqlSessionBooking);
	}

	@Override
	public Boolean isPartExist(Long bookingId) {
		return fetchObject("isPartExist", bookingId, sqlSessionBooking);
	}

	@Override
	public ShipmentBookingDetails isShipmentInShipmentMaster(BookingShipment search) {
		return fetchObject("isShipmentInShipmentMaster", search, sqlSessionBooking);
	}

	@Override
	public ShipmentBookingDetails getBookingData(BookingShipment search) {
		return fetchObject("getBookingData", search, sqlSessionBooking);
	}

	@Override
	public ShipmentFlightPartBookingDetails fetchPartShcRemarksDimensionBookingDetails(
			ShipmentFlightPartBookingDetails part) {
		return fetchObject("fetchPartShcRemarksDimensionBookingDetails", part, sqlSessionBooking);
	}

	@Override
	public ShipmentFlightBookingDetails fetchTotalBookingDetails(ShipmentFlightBookingDetails flight) {
		return fetchObject("fetchTotalBookingDetails", flight, sqlSessionBooking);
	}

	@Override
	public Boolean isShipmentLoaded(BookingShipment bookingShipment) {
		return fetchObject("isShipmentLoaded", bookingShipment, sqlSessionBooking);
	}

	@Override
	public List<TTTransferType> fetchTransferType() {
		return fetchList("fetchTransferType", null, sqlSessionBooking);
	}

	@Override
	public boolean createBooking(ShipmentBookingDetails singleShipmentBooking) throws CustomException {
		insertData("createShipmentBooking", singleShipmentBooking, sqlSessionBooking);
		return true;
	}

	@Override
	public boolean checkFlightBookingPrimaryKey(ShipmentFlightBookingDetails singleFlightBooking)
			throws CustomException {
		int count = super.fetchObject("checkFlightBookingPrimaryKey", singleFlightBooking, sqlSessionBooking);
		return count > 0;
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.FLIGHTPART_BOOKING)
	public boolean createFlightBooking(ShipmentFlightBookingDetails booking) throws CustomException {
		insertData("createFlightShipmentBooking", booking, sqlSessionBooking);
		return true;
	}

	@Override
	public void deleteRemarks(Long flightBookId, Long partBookingId) throws CustomException {
		HashMap<String, Object> queryMap = new HashMap<>();
		queryMap.put("flightBookingId", flightBookId);
		if (partBookingId != null && partBookingId != 0) {
			queryMap.put("partBookingId", partBookingId);
		} else {
			queryMap.put("partBookingId", null);
		}
		deleteData("sqlDeleteRemarksBasedOnFlightAndPartBookingId", queryMap, sqlSessionBooking);
	}

	@Override
	public BigInteger getflightBookingIdForFlight(ShipmentFlightBookingDetails flightBooking) throws CustomException {
		return fetchObject("getflightBookingIdForFlight", flightBooking, sqlSessionBooking);
	}

	@Override
	public boolean createDimension(List<ShipmentBookingDimensions> dimension) throws CustomException {
		insertData("insertShipmentBookingDimension", dimension, sqlSessionBooking);
		return true;
	}

	@Override
	public void insertShipmentRemarks(ShipmentBookingRemark obj) throws CustomException {
		insertData("insertShipmentRemarks", obj, sqlSessionBooking);
	}

	@Override
	public void insertShipmentRemarksWithFlightId(ShipmentBookingRemark obj) throws CustomException {
		insertData("insertShipmentRemarksWithFlightId", obj, sqlSessionBooking);
	}

	@Override
	public void insertRemarks(ShipmentBookingRemark singleShipmentBooking) throws CustomException {
		updateData("createBookingRemark", singleShipmentBooking, sqlSessionBooking);

	}

	@Override
	public Long getShipmentId(ShipmentBookingDetails obj) throws CustomException {
		return fetchObject("getShipmentId_Publish", obj, sqlSessionBooking);
	}

	@Override
	public Integer getSegmentId(ShipmentFlightBookingDetails obj) throws CustomException {
		return fetchObject("getSegmentIdSSB", obj, sqlSessionBooking);
	}

	@Override
	public void deleteRemarks(Map<String,String> queryParam) throws CustomException {
		deleteData("sqlDeleteRemarks", queryParam, sqlSessionBooking);
	}

	@Override
	public List<BigInteger> getFlightDimensionsId(Long obj) throws CustomException {
		return fetchList("getFlightDimensionId_NEW", obj, sqlSessionBooking);
	}

	@Override
	public void deleteFlightDimensions(List<BigInteger> obj) throws CustomException {
		deleteData("deleteFlihgtDimensionsSSB", obj, sqlSessionBooking);
	}

	@Override
	public void deleteFlightPartAssociation(Long obj) throws CustomException {
		deleteData("deleteFlightPartAssociationSSB", obj, sqlSessionBooking);
	}

	@Override
	public void deleteFlight(ShipmentFlightBookingDetails booking) throws CustomException {
		deleteData("deleteFlight", booking, sqlSessionBooking);
	}

	@Override
	public void deletePartDimensionByPartBookingId(Long partBookingId) throws CustomException {
		deleteData("deletePartDimensionByPartBookingId", partBookingId, sqlSessionBooking);
	}

	@Override
	public void deletePart(ShipmentFlightPartBookingDetails booking) throws CustomException {
		deleteData("deletePart", booking, sqlSessionBooking);
	}

	@Override
	public boolean deleteBooking(int bookingId) throws CustomException {
		deleteData("deleteSingleShipment", bookingId, sqlSessionBooking);
		return true;
	}

	@Override
	public List<BookingFlightDetails> fetchIntermidiateFlightDetails(Map<String, String> inputMap) {
		return fetchList("fetchIntermidiateFlightDetail", inputMap, sqlSessionBooking);
	}

	@Override
	public List<shipmentMasterRoutingInfo> fetchMasterRoutingInfo(String shipmentNo) {
		return fetchList("fetchMasterRoutingInfo", shipmentNo, sqlSessionBooking);
	}

	@Override
	public PartSuffix getPartSuffixDetails(String carrierCode) {
		return fetchObject("fetchPartSuffixes", carrierCode, sqlSessionBooking);
	}

	@Override
	public List<BookingCommodityDetails> fetchIntermidiateCommodityDetails(Map<String, String> awbNo) {
		return fetchList("fetchIntermidiateCommodityDetail", awbNo, sqlSessionBooking);
	}
	
	@Override
	public List<DimensionDetaills> fetchIntermidiateCommodityDimensionDetails(String bookingCommodityDetailsId) {
		return fetchList("fetchIntermidiateCommodityDimensionDetail", bookingCommodityDetailsId, sqlSessionBooking);
	}
	
	@Override
	public BookingDetails fetchIntermidiateBookingDetails(Map<String, String> awbNo) {
		return fetchObject("fetchIntermidiateBookingDetail", awbNo, sqlSessionBooking);
	}

	@Override
	public List<ConstantValueDetails> getConstantValues() {
		return sqlSessionBooking.selectList("getConstantValues", BookingConstant.BASE_CONSTANT_LIKE_PARAM);

	}

	@Override
	public String getFlightRemarks(BookingShipmentDetails bookingShipmentDetails) {
		return fetchObject("getFlightRemarks", bookingShipmentDetails, sqlSessionBooking);
	}

	@Override
	public long getShipmentId(Map<String, String> map) {
		String id = fetchObject("getShipmentId_Booking", map, sqlSessionBooking);
		if (id != null) {
			return Long.valueOf(id);
		} else {
			return 0;
		}

	}

	// Check the code
	@Override
	public List<Boolean> hasDocumentArrived(String awbNumber) {
		return fetchList("hasDocumentArrived", awbNumber, sqlSessionBooking);
	}

	@Override
	public List<Boolean> hasAcceptanceWeight(String awbNumber) {
		return fetchList("hasAcceptanceWeight", awbNumber, sqlSessionBooking);
	}

	@Override
	public Boolean hasManifested(BookingFlightDetails bookingflightDetails) {
		return fetchObject("hasManifested", bookingflightDetails, sqlSessionBooking);
	}

	@Override
	public void updateBooking(ShipmentBookingDetails shipmentBookingDetails) {
		updateData("updateShipmentBooking", shipmentBookingDetails, sqlSessionBooking);
	}

	@Override
	public int deleteFlightBooking(int flightBookingId) {
		return updateData("deleteFlightBooking", flightBookingId, sqlSessionBooking);
	}

	@Override
	public int getExistingFlightBookingId(BookingFlightDetails bookingFlightInfo) {
		return fetchObject("getExistingFlightBookingId", bookingFlightInfo, sqlSessionBooking);
	}

	@Override
	public int checkInventoryExist(Map inputMap) {
		return fetchObject("checkInventory", inputMap, sqlSessionBooking);
	}

	@Override
	//@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENTPART_BOOKING)
	public void createPart(ShipmentFlightPartBookingDetails part) throws CustomException {
		insertData("createPartShipmentBooking", part, sqlSessionBooking);
	}

	@Override
	public String getFlightBookingStatus(String flightBookingStatus) {
		return fetchObject("getFlightBookingStatus", flightBookingStatus, sqlSessionBooking);
	}

	@Override
	public void createFlightPartDetail(ShipmentFlightPartDetails flightPartDetails) {
		insertData("createFlightPartDetails", flightPartDetails, sqlSessionBooking);
	}

	@Override
	public List<BookingFlightDetails> getFlightId(Map<String, String> queryParam) {
		return fetchList("getFlightId", queryParam, sqlSessionBooking);
	}

	@Override
	public int getFlightIdFromOperativeFlights(Map<String, String> map) {
		return fetchObject("getFlightIDFromOperativeFlight", map, sqlSessionBooking);
	}

	@Override
	public Long fetchFlightBookingId(ShipmentFlightBookingDetails flightBooking) throws CustomException {
		return fetchObject("fetchFlightBookingId", flightBooking, sqlSessionBooking);
	}

	@Override
	public Boolean checkShipmentLoaded(Map<String, Object> queryMap) {
		return fetchObject("checkShipmentLoaded", queryMap, sqlSessionBooking);
	}

	@Override
	public Boolean checkShipmentManifested(Map<String, Object> queryMap) {
		return fetchObject("checkShipmentManifested", queryMap, sqlSessionBooking);
	}

	@Override
	public Boolean checkShipmentsDeparted(Map<String, Object> queryMap) {
		return fetchObject("checkShipmentsDeparted", queryMap, sqlSessionBooking);
	}

	@Override
	public String getSegmentDepartureDate(Map<String, String> queryMap) {
		return fetchObject("getSegmentDepartureDate", queryMap, sqlSessionBooking);
	}

	@Override
	public List<ShipmentBookingRemark> getShipmentRemarks(Map<String, Object> shipmentNumber) {
		return fetchList("getShipmentRemarks", shipmentNumber, sqlSessionBooking);
	}

	@Override
	public void createOutgoingMessageRecipients(Map<String, Object> recipient) {
		insertData("sqlInsertOutgoingMessageRecipients", recipient, sqlSessionBooking);
	}

	@Override
	public List<String> fetchSccCodeFromShc(long shipmentId) {
		return fetchList("fetchSccCodeFromShc", shipmentId, sqlSessionBooking);
	}
	
	@Override
	public List<String> fetchSccCodeFromBookingShc(Map<String,String> shipmentMap) {
		return fetchList("fetchSccCodeFromBookingSHCTable", shipmentMap, sqlSessionBooking);
	}

	@Override
	public void createFlightBookingSHC(List<SHCDetails> shcDetailsList) {
		insertList("createFlightBookingSHC", shcDetailsList, sqlSessionBooking);
	}

	@Override
	public void createPartBookingSHC(List<SHCDetails> shcDetailsList) {
		insertList("createPartBookingSHC", shcDetailsList, sqlSessionBooking);
	}

	@Override
	public List<SHCCodes> fetchSHCMaster() {
		return fetchList("fetchSHCMaster", "", sqlSessionBooking);
	}

	@Override
	public List<ShipmentBookingRemark> getShipmentFlightRemarks(Map<String, Object> shipmentNumber) {
		return fetchList("getShipmentFlightRemarks", shipmentNumber, sqlSessionBooking);
	}

	@Override
	public List<String> getExpBookingFlightRemarks(Map<String, Object> map) {
		return fetchList("getExpBookingFlightRemarks", map, sqlSessionBooking);
	}

	@Override
	public List<BigInteger> getDimensionsId(Map<String,Object> dimensionMap) throws CustomException {
		return fetchList("getDimensionId", dimensionMap, sqlSessionBooking);
	}
	
	@Override
	public List<BigInteger> getDimensionsId(Long dimension) throws CustomException {
		return fetchList("getDimensionId", dimension, sqlSessionBooking);
	}
	
	@Override
	public void deleteDimensions(List<BigInteger> obj) throws CustomException {
		deleteData("deleteDimensions", obj, sqlSessionBooking);
	}

	@Override
	public void createBookingShipment(ShipmentBookingDetails shipmentBookingDetails) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createBookingFlight(BookingFlightDetails bookingFlightDetails) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createPartShipmentBooking(ShipmentFlightPartBookingDetails parbookingDetails) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InterfaceExternalSystemUrl getExternalUrlDetails(String icms) {
		return fetchObject("getInterfaceExternalSystemValues", icms, sqlSessionBooking);
	}

	@Override
	public BookingShipmentDetails getShipmentDetails(BookingShipmentDetails bookingDetails) {
		return fetchObject("getShipmentDetails", bookingDetails, sqlSessionBooking);
	}

	@Override
	public String getBookingStatus(String bookingStatus) {
		return fetchObject("getBookingStatus", bookingStatus, sqlSessionBooking);
	}

	@Override
	public boolean isBlacklistShipmentNumber(String shipmentNumber) {
		return fetchObject("isBlacklistShipmentNumber", shipmentNumber, sqlSessionBooking);
	}

	@Override
	public boolean isFreightNAFlight(BookingFlightDetails flightId) {
		Boolean isFreightNA = fetchObject("isFreightNA", flightId, sqlSessionBooking);
		if(isFreightNA == null) {
			return false;
		}
		return isFreightNA;
	}
	
	@Override
	public OperationalFlightLegInfo getSegmentDepartureAndArrivalDate(Map<String, String> queryParam){
		return fetchObject("getSegmentDepartureAndArrivalDate",queryParam,sqlSessionBooking);
	}

	@Override
	public void setCancelledBooking(List<BookingDeltaDetails> deltaList) {
		for (BookingDeltaDetails delta : deltaList) {
			insertData("updateBookingDeltaForBookingCancelled", delta, sqlSessionBooking);
		}
	}

	@Override
	public String checkFlightClosedForBooking(BookingFlightDetails bookingFlightInfo) {
		return fetchObject("checkFlightClosedForBooking",bookingFlightInfo,sqlSessionBooking);
	}

	@Override
	public boolean checkFlightBookingExist(BookingFlightDetails bookingFlightDetails) {
		return fetchObject("checkFlightBookingExist",bookingFlightDetails,sqlSessionBooking);
	}
	
	@Override
	public List<String> fetchShcGroupCode(String shc) throws CustomException {
		return fetchList("getShcGroupBasedOnShc",shc,sqlSessionBooking);
	}
	
	@Override
	public FactFlight getFactDetails(Map<String,Object> queryParam) {
		return fetchObject("getFactFlightInfoForShipmentBooking",queryParam,sqlSessionBooking);
	}

	@Override
	public Integer fetchBookingVersion(String shipmentNumber) {
		Integer bookingVersion = fetchObject("fetchBookingVersion", shipmentNumber, sqlSessionBooking);
		if (null == bookingVersion) {
			bookingVersion = 0;
		}
		return bookingVersion;
	}

	@Override
	public BigInteger fetchFlightSegmentID(Map<String, String> map) {
		BigInteger segmentId = null;
		Integer flightSegmentId = fetchObject("fetchFlightSegmentID", map, sqlSessionBooking);
		if(null != flightSegmentId) {
			segmentId = BigInteger.valueOf(flightSegmentId);
		}
		return segmentId;
	}

	@Override
	public List<TransferTypeModel> getTransferTypes() {
		List<TransferTypeModel> transferTypeList= fetchList("getTransferTypes",null,  sqlSessionBooking);
		return transferTypeList;
	}
	
	@Override
	public void deleteFlightPartAssociation(Map<String,Long> queryParam) throws CustomException {
		deleteData("deleteFlightPartWithPartFlightId", queryParam, sqlSessionBooking);
	}

	@Override
	public void insertInterfaceOutgoingMessage(OutgoingMessageLog log) {
		insertData("insertInterfaceOutgoingMessageICMS", log, sqlSessionBooking);
	}


}
