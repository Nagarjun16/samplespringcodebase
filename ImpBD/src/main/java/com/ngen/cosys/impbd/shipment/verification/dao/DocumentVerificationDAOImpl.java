package com.ngen.cosys.impbd.shipment.verification.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.events.payload.ShipmentNotification;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.constants.SqlIDs;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.notification.ShipmentNotificationDetail;
import com.ngen.cosys.impbd.shipment.irregularity.model.ShipmentIrregularityModel;
import com.ngen.cosys.impbd.shipment.verification.model.BookingInfo;
import com.ngen.cosys.impbd.shipment.verification.model.DgRegulations;
import com.ngen.cosys.impbd.shipment.verification.model.DocumentVerificationFlightModel;
import com.ngen.cosys.impbd.shipment.verification.model.DocumentVerificationShipmentModel;
import com.ngen.cosys.impbd.shipment.verification.model.EliElmDGDModel;
import com.ngen.cosys.impbd.shipment.verification.model.EliElmDGDModelList;
import com.ngen.cosys.impbd.shipment.verification.model.SearchDGDeclations;
import com.ngen.cosys.impbd.shipment.verification.model.SearchRegulationDetails;
import com.ngen.cosys.impbd.shipment.verification.model.ShipperDeclaration;
import com.ngen.cosys.impbd.shipment.verification.model.ShipperDeclarationDetail;
import com.ngen.cosys.impbd.shipment.verification.model.UNIDOverpackDetails;
import com.ngen.cosys.inward.constants.ServiceReportQueryIds;
import com.ngen.cosys.inward.model.InwardServiceReportModel;
import com.ngen.cosys.inward.model.InwardServiceReportShipmentDiscrepancyModel;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Repository
public class DocumentVerificationDAOImpl extends BaseDAO implements DocumentVerificationDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	private String formName = "declarationDetails";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.shipment.verification.dao.ShipmentVerificationDAO#get(
	 * com.ngen.cosys.impbd.shipment.verification.model.
	 * DocumentVerificationFlightModel)
	 */
	@Override
	public DocumentVerificationFlightModel get(DocumentVerificationFlightModel requestModel) throws CustomException {
		DocumentVerificationFlightModel response = fetchObject(SqlIDs.GETDOCUMENTVERIFICATIONFLIGHT.toString(),
				requestModel, sqlSessionTemplate);
		List<String> boardPoints = fetchList("fetchBoardPoint", response, sqlSessionTemplate);
		String boardPoint = boardPoints.stream().map(Object::toString).collect(Collectors.joining(" - "));
		if (response != null && !StringUtils.isEmpty(boardPoint)) {
			boardPoint += " - " + response.getTenantAirport();
			response.setBoardPoint(boardPoint);
		}
		if (response != null) {
			response.setCodeAdminStatus(getStatusOfCodeAdministration(requestModel.getCarrierCode()));
		}
		return response;

	}
	
	@Override
	public String getFlightType(DocumentVerificationFlightModel flightId) throws CustomException
	{
		return super.fetchObject("sqlQueryforInternationalValidation", flightId, sqlSessionTemplate);				
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.verification.dao.DocumentVerificationDAO#
	 * offloadToArrivalManifest(com.ngen.cosys.impbd.model.
	 * ArrivalManifestShipmentInfoModel)
	 */
	@Override
	public void offloadToArrivalManifest(ArrivalManifestShipmentInfoModel arrivalManifestShipmentInfoModel)
			throws CustomException {
		updateData(SqlIDs.UPDATEOFFLOADTOARRIVALMANIFEST.toString(), arrivalManifestShipmentInfoModel,
				sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.verification.dao.DocumentVerificationDAO#
	 * isFlightExistsinRemarks(com.ngen.cosys.impbd.shipment.verification.model.
	 * DocumentVerificationFlightModel)
	 */
	@Override
	public boolean isFlightExistsinRemarks(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		int count = (Integer) super.fetchObject(SqlIDs.ISFLIGHTEXISTSINREMARKS.toString(),
				documentVerificationFlightModel, sqlSessionTemplate);
		return count != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.verification.dao.DocumentVerificationDAO#
	 * insertIntoDocumentCompleteRemarks(com.ngen.cosys.impbd.shipment.verification.
	 * model.DocumentVerificationFlightModel)
	 */

	@Override
	public void insertIntoDocumentCompleteRemarks(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		insertData(SqlIDs.INSERTINTODOCUMENTCOMPLETEREMARKS.toString(), documentVerificationFlightModel,
				sqlSessionTemplate);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.verification.dao.DocumentVerificationDAO#
	 * isChecksforFirstTime(com.ngen.cosys.impbd.shipment.verification.model.
	 * DocumentVerificationFlightModel)
	 */
	@Override
	public boolean isChecksforFirstTime(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		int count = (Integer) super.fetchObject(SqlIDs.ISCHECKSFORFIRSTTIME.toString(), documentVerificationFlightModel,
				sqlSessionTemplate);
		return count != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.verification.dao.DocumentVerificationDAO#
	 * updateDocumentCompleteNextTimeStatus(com.ngen.cosys.impbd.shipment.
	 * verification.model.DocumentVerificationFlightModel)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION)
	public void updateDocumentCompleteNextTimeStatus(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		DocumentVerificationFlightModel updatedFlightInfo = new DocumentVerificationFlightModel();
		updatedFlightInfo.setCreatedBy(documentVerificationFlightModel.getCreatedBy());
		updatedFlightInfo.setCreatedOn(documentVerificationFlightModel.getCreatedOn());
		updatedFlightInfo.setModifiedBy(documentVerificationFlightModel.getCreatedBy());
		updatedFlightInfo.setModifiedOn(documentVerificationFlightModel.getModifiedOn());
		updatedFlightInfo.setFlightId(documentVerificationFlightModel.getFlightId());
		updatedFlightInfo.setFlightNumber(documentVerificationFlightModel.getFlightNumber());
		updatedFlightInfo.setFlightDate(documentVerificationFlightModel.getFlightDate());
		updatedFlightInfo.setLastupdatedBy(documentVerificationFlightModel.getModifiedBy());
		updatedFlightInfo.setLastmodifiedOn(documentVerificationFlightModel.getModifiedOn());
		updatedFlightInfo.setDocumentCompleteStatus(Boolean.TRUE);
		updateData(SqlIDs.UPDATEDOCUMENTCOMPLETENEXTTIMESTATUS.toString(), updatedFlightInfo, sqlSessionTemplate);
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION)
	public void updateDocumentCompleReopen(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		DocumentVerificationFlightModel updatedFlightInfo = new DocumentVerificationFlightModel();
		updatedFlightInfo.setCreatedBy(documentVerificationFlightModel.getCreatedBy());
		updatedFlightInfo.setCreatedOn(documentVerificationFlightModel.getCreatedOn());
		updatedFlightInfo.setModifiedBy(documentVerificationFlightModel.getCreatedBy());
		updatedFlightInfo.setModifiedOn(documentVerificationFlightModel.getModifiedOn());
		updatedFlightInfo.setFlightId(documentVerificationFlightModel.getFlightId());
		updatedFlightInfo.setFlightNumber(documentVerificationFlightModel.getFlightNumber());
		updatedFlightInfo.setFlightDate(documentVerificationFlightModel.getFlightDate());
		updatedFlightInfo.setLastupdatedBy(documentVerificationFlightModel.getModifiedBy());
		updatedFlightInfo.setLastmodifiedOn(documentVerificationFlightModel.getModifiedOn());
		updatedFlightInfo.setDocumentCompleteStatus(Boolean.FALSE);
		updateData(SqlIDs.UPDATEDOCUMENTCOMPLEREOPEN.toString(), updatedFlightInfo, sqlSessionTemplate);
	}

	@Override
	public List<ShipmentIrregularityModel> getIrregularity(
			DocumentVerificationFlightModel documentVerificationFlightModel) throws CustomException {
		return super.fetchList(SqlIDs.GETIRREGULARITY.toString(), documentVerificationFlightModel, sqlSessionTemplate);
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION)
	public void updateDocumentCompleteFirstTimeStatus(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		DocumentVerificationFlightModel updatedFlightInfo = new DocumentVerificationFlightModel();
		updatedFlightInfo.setCreatedBy(documentVerificationFlightModel.getCreatedBy());
		updatedFlightInfo.setCreatedOn(documentVerificationFlightModel.getCreatedOn());
		updatedFlightInfo.setModifiedBy(documentVerificationFlightModel.getCreatedBy());
		updatedFlightInfo.setModifiedOn(documentVerificationFlightModel.getModifiedOn());
		updatedFlightInfo.setFlightId(documentVerificationFlightModel.getFlightId());
		updatedFlightInfo.setFlightNumber(documentVerificationFlightModel.getFlightNumber());
		updatedFlightInfo.setFlightDate(documentVerificationFlightModel.getFlightDate());
		updatedFlightInfo.setLastupdatedBy(documentVerificationFlightModel.getModifiedBy());
		updatedFlightInfo.setLastmodifiedOn(documentVerificationFlightModel.getModifiedOn());
		updatedFlightInfo.setDocumentCompleteStatus(Boolean.TRUE);
		updateData(SqlIDs.UPDATEDOCUMENTCOMPLETEFIRSTTIMESTATUS.toString(), updatedFlightInfo, sqlSessionTemplate);
	}

	@Override
	public DocumentVerificationFlightModel onHoldShipments(
			DocumentVerificationFlightModel documentVerificationFlightModel) throws CustomException {

		super.updateData("updateShipmentsOnHold",
				documentVerificationFlightModel.getDocumentVerificationShipmentModelList(), sqlSessionTemplate);
		return documentVerificationFlightModel;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ngen.cosys.export.dgd.dao.DGDeclarationsDAO#saveDGDeclarationsDetails(com
	 * .ngen.cosys.export.dgd.model.ShipperDeclaration)
	 */

	@Override
	public ShipperDeclaration saveDgDeclarationsDetails(ShipperDeclaration shippersDeclarationDetails)
			throws CustomException {
		List<ShipperDeclarationDetail> distinctElements = shippersDeclarationDetails.getDeclarationDetails().stream()
				.filter(p -> !ObjectUtils.isEmpty(p.getDgRegulationId()))
				.filter(distinctByKey(p -> p.getDgRegulationId())).collect(Collectors.toList());
		int numDupRegID = shippersDeclarationDetails.getDeclarationDetails().size() - distinctElements.size();
		if (shippersDeclarationDetails.getDgdReferenceNo() == 0) {

			saveDgdDetail(shippersDeclarationDetails, numDupRegID);

		} else {

			updateDgdDetail(shippersDeclarationDetails, numDupRegID);

		}
		return shippersDeclarationDetails;
	}

	private void saveDgdDetail(ShipperDeclaration shippersDeclarationDetails, int numDupRegID) throws CustomException {
		Short dgdReferenceNo = genereteDgd(shippersDeclarationDetails.getShipmentNumber());
		shippersDeclarationDetails.setDgdReferenceNo(dgdReferenceNo);
		super.insertData("saveDGdeclarationDetails", shippersDeclarationDetails, sqlSessionTemplate);
		shippersDeclarationDetails.getDeclarationDetails().forEach(obj -> {
			obj.setDgdReferenceNo(dgdReferenceNo);
			obj.setExpDgShipperDeclarationId(shippersDeclarationDetails.getExpDgShipperDeclarationId());
		});
		performDuplicateCheck(numDupRegID);
		super.insertData("insertshipperDeclarationDetails", shippersDeclarationDetails.getDeclarationDetails(),
				sqlSessionTemplate);
		for (ShipperDeclarationDetail shpObj : shippersDeclarationDetails.getDeclarationDetails()) {
			shpObj.getOverPackDetails().forEach(unidObj -> unidObj.setDgdReferenceNo(dgdReferenceNo));
			shpObj.getOverPackDetails()
					.forEach(unidObj -> unidObj.setExpDgShipperDeclarationId(shpObj.getExpDgShipperDeclarationId()));
			super.insertData("insertshipperOverPackDetails", shpObj.getOverPackDetails(), sqlSessionTemplate);
		}
	}

	private void performDuplicateCheck(int numDupRegID) throws CustomException {
		if (numDupRegID > 0) {
			throw new CustomException("DUP_REGID", formName, ErrorType.ERROR);
		}
	}

	/**
	 * Method returns DGD Reference Number for Shipment
	 * 
	 * @param string
	 * @return
	 * @throws CustomException
	 */
	private Short genereteDgd(String string) throws CustomException {
		return super.fetchObject("generateDGD", string, sqlSessionTemplate);
	}

	private void updateDgdDetail(ShipperDeclaration shippersDeclarationDetails, int numDupRegID)
			throws CustomException {
		super.updateData("updateshipperDeclarationDetails", shippersDeclarationDetails, sqlSessionTemplate);
		shippersDeclarationDetails.getDeclarationDetails().forEach(obj -> {
			obj.setDgdReferenceNo(shippersDeclarationDetails.getDgdReferenceNo());
			obj.setExpDgShipperDeclarationId(shippersDeclarationDetails.getExpDgShipperDeclarationId());
		});
		for (ShipperDeclarationDetail shpObj : shippersDeclarationDetails.getDeclarationDetails()) {
			if (Action.UPDATE.toString().equalsIgnoreCase(shpObj.getFlagCRUD())) {
				super.updateData("updateDGDDetails", shpObj, sqlSessionTemplate);
				super.updateData("updateDGDetailsHeader", shpObj, sqlSessionTemplate);
			}
			if (Action.CREATE.toString().equalsIgnoreCase(shpObj.getFlagCRUD())) {
				performDuplicateCheck(numDupRegID);
				super.insertData("insertshipperDeclarationDetails", shpObj, sqlSessionTemplate);
			}
			for (UNIDOverpackDetails shp : shpObj.getOverPackDetails()) {
				if (Action.UPDATE.toString().equalsIgnoreCase(shp.getFlagCRUD())) {
					super.updateData("updateDGDOverPackDetails", shp, sqlSessionTemplate);
				}
				if (Action.CREATE.toString().equalsIgnoreCase(shp.getFlagCRUD())) {
					shp.setExpDgShipperDeclarationId(shippersDeclarationDetails.getExpDgShipperDeclarationId());
					super.insertData("insertshipperOverPackDetails", shp, sqlSessionTemplate);
				}
			}
		}
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	@Override
	public List<ShipperDeclaration> getDgdDetails(SearchDGDeclations search) throws CustomException {
		List<ShipperDeclaration> returnList = fetchList("getDGDetails", search, sqlSessionTemplate);
		ShipperDeclaration sd = fetchObject("getShipmentDetailsForDGD", search, sqlSessionTemplate);
		returnList.forEach(shpObj -> {
			shpObj.setFlagCRUD("R");
			shpObj.getDeclarationDetails().forEach(declObj -> declObj.setFlagCRUD("R"));
		});

		if (sd != null && MultiTenantUtility.isTenantCityOrAirport(sd.getOrigin())
				&& MultiTenantUtility.isTenantCityOrAirport(sd.getDestination())) {
			sd.setDgdReferenceNo((short) -1);
			sd.setTranshipmentFlag("T");
			sd.setShipmentID(sd.getShipmentID());

		}
		if (sd != null) {
			returnList.add(0, sd);
		}
		return returnList;
	}

	@Override
	public void deleteUnidOverPackDetails(List<UNIDOverpackDetails> unidDetails) throws CustomException {
		super.deleteData("deleteUnidOverpackDetail", unidDetails, sqlSessionTemplate);
	}

	@Override
	public void deleteShipperDeclarationDetails(List<ShipperDeclarationDetail> dgdDetails) throws CustomException {
		super.deleteData("deleteShipperDeclarationDetail", dgdDetails, sqlSessionTemplate);
	}

	@Override
	public List<DgRegulations> getRegDetails(SearchRegulationDetails dgRegulationId) throws CustomException {
		return fetchList("getDgRegulationData", dgRegulationId, sqlSessionTemplate);
	}

	@Override
	public Integer getSeqNO(SearchDGDeclations search) throws CustomException {
		return super.fetchObject("getOverpackSeqNo", search, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.verification.dao.DocumentVerificationDAO#
	 * isBreakDownComplete(com.ngen.cosys.impbd.shipment.verification.model.
	 * DocumentVerificationFlightModel)
	 */
	@Override
	public boolean isBreakDownComplete(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		return this.fetchObject("sqlIsBreakDownCompleteFromDocument", documentVerificationFlightModel,
				sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.verification.dao.DocumentVerificationDAO#
	 * updateFlightComplete(com.ngen.cosys.impbd.shipment.verification.model.
	 * DocumentVerificationFlightModel)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_COMPLETE)
	public void updateFlightComplete(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		DocumentVerificationFlightModel updatedFlightInfo = new DocumentVerificationFlightModel();
		updatedFlightInfo.setCreatedBy(documentVerificationFlightModel.getCreatedBy());
		updatedFlightInfo.setCreatedOn(documentVerificationFlightModel.getCreatedOn());
		updatedFlightInfo.setModifiedBy(documentVerificationFlightModel.getCreatedBy());
		updatedFlightInfo.setModifiedOn(documentVerificationFlightModel.getModifiedOn());
		updatedFlightInfo.setFlightId(documentVerificationFlightModel.getFlightId());
		updatedFlightInfo.setFlightNumber(documentVerificationFlightModel.getFlightNumber());
		updatedFlightInfo.setFlightDate(documentVerificationFlightModel.getFlightDate());
		updatedFlightInfo.setLastupdatedBy(documentVerificationFlightModel.getModifiedBy());
		updatedFlightInfo.setLastmodifiedOn(documentVerificationFlightModel.getModifiedOn());
		updatedFlightInfo.setFlightCompleted(Boolean.TRUE);
		this.updateData("sqlUpdateFlightCompleteFromDocument", updatedFlightInfo, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.verification.dao.DocumentVerificationDAO#
	 * getEmailBasedOnShipmentId(java.lang.String)
	 */
	public List<String> getEmailBasedOnShipmentId(String shipmentId) throws CustomException {
		return fetchList("sqlGetEmailList", shipmentId, sqlSessionTemplate);
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ELI_ELM_DEATILS)
	public EliElmDGDModel saveOrDelEliElm(EliElmDGDModel elmDGDModel) throws CustomException {
		for (EliElmDGDModelList elielmModel : elmDGDModel.getEliElmFormDetails()) {
			elielmModel.setShipmentNumber(elmDGDModel.getShipmentNumber());
			if (Action.UPDATE.toString().equalsIgnoreCase(elielmModel.getFlagCRUD())) {
				flightTypeValidation(elmDGDModel, elielmModel);
				super.updateData("sqlUpdateDgdEliElmDetails", elielmModel, sqlSessionTemplate);
			}
			if (Action.CREATE.toString().equalsIgnoreCase(elielmModel.getFlagCRUD())) {
				flightTypeValidation(elmDGDModel, elielmModel);
				super.insertData("sqlSaveDgdEliElmDetails", elielmModel, sqlSessionTemplate);
			}

		}
		return elmDGDModel;
	}

	private void flightTypeValidation(EliElmDGDModel elmDGDModel, EliElmDGDModelList elielmModel)
			throws CustomException {

		if (Objects.nonNull(elmDGDModel) && !MultiTenantUtility.isTenantAirport(elmDGDModel.getBoardPoint())
				&& !MultiTenantUtility.isTenantAirport(elmDGDModel.getOffPoint())) {
			EliElmDGDModelList bookingDoneForShipment = null;
			if (StringUtils.isEmpty(elielmModel.getCarrierCode())) {
				bookingDoneForShipment = isBookingDoneForShipment(elmDGDModel);
				elielmModel.setCarrierCode(bookingDoneForShipment.getCarrierCode());
				if (ObjectUtils.isEmpty(bookingDoneForShipment)) {
					throw new CustomException("booking.notdone", "Booking not done", "E");
				}
			}

			bookingDoneForShipment = isBookingDoneForShipment(elmDGDModel);
			if (!ObjectUtils.isEmpty(bookingDoneForShipment) && elielmModel.getFlightType().startsWith("F")
					&& !bookingDoneForShipment.getFlightType().equalsIgnoreCase("C")) {
				throw new CustomException("booking.flighttype", "FlightType", "E");

			}

		}
	}

	@Override
	public EliElmDGDModel deleteEliElmDetails(EliElmDGDModel elmDGDModel) throws CustomException {
		for (EliElmDGDModelList elielmModel : elmDGDModel.getEliElmFormDetails()) {
			super.deleteData("sqldelDgdEliElmDetails", elielmModel, sqlSessionTemplate);
		}
		return elmDGDModel;
	}

	@Override
	public EliElmDGDModel getEliElmDetails(EliElmDGDModel elmDGDModel) throws CustomException {
		EliElmDGDModel searchRes = new EliElmDGDModel();
		List<EliElmDGDModelList> responseEliElmFormDetails = fetchList("sqlGetDgdEliList", elmDGDModel,
				sqlSessionTemplate);
		searchRes.setEliElmFormDetails(responseEliElmFormDetails);
		return searchRes;
	}

	@Override
	public EliElmDGDModelList getEliElmRemark(EliElmDGDModelList elmDGDModel) throws CustomException {
		if (StringUtils.isEmpty(elmDGDModel.getCarrierCode())) {
			EliElmDGDModelList modelList = fetchObject("sqlIsBookingDone", elmDGDModel, sqlSessionTemplate);
			elmDGDModel.setCarrierCode(modelList.getCarrierCode());
		}
		return super.fetchObject("sqlGetDgdEliElmRemark", elmDGDModel, sqlSessionTemplate);
	}

	@Override
	public void deleteDataFromShipmentVerification(DocumentVerificationShipmentModel documentVerificationShipmentModel)
			throws CustomException {
		updateData("sqlClearShipmentVerificationForFlight", documentVerificationShipmentModel, sqlSessionTemplate);
		deleteData("sqlDeleteShipmentVerification", documentVerificationShipmentModel, sqlSessionTemplate);
		updateData("sqlUpdateShipmentMasterDocumentReceivedOn", documentVerificationShipmentModel, sqlSessionTemplate);
	}

	@Override
	public int recordExistInCDH(DocumentVerificationShipmentModel model) throws CustomException {
		return fetchObject("sqlRecordExistInCDH", model, sqlSessionTemplate);
	}

	@Override
	public EliElmDGDModelList isBookingDoneForShipment(EliElmDGDModel request) throws CustomException {
		return fetchObject("sqlIsBookingDone", request, sqlSessionTemplate);
	}

	@Override
	public EliElmDGDModelList getDangerousGoodsHandlingInstructions(EliElmDGDModelList request) throws CustomException {
		return fetchObject("sqlgetDangerousGoodsHandlingInstructions", request, sqlSessionTemplate);
	}

	@Override
	public void insertOffloadInfoinInwardServiceRerort(InwardServiceReportModel model,
			InwardServiceReportShipmentDiscrepancyModel descripacyModel) throws CustomException {
		BigInteger segmentId = null;
		if (MultiTenantUtility.isTenantCityOrAirport(descripacyModel.getDestination())) {
			segmentId = fetchObject("sqlgetSegmentId", model, sqlSessionTemplate);
		}
		if (!ObjectUtils.isEmpty(segmentId)) {
			model.setSegmentId(segmentId.toString());
			// Get the primary key id
			BigInteger serviceReportId = super.fetchObject(
					ServiceReportQueryIds.SQL_GET_SERVICE_REPORT_BY_FLIGHT.getQueryId(), model, sqlSessionTemplate);
			// If exists update the data/insert
			Optional<BigInteger> oServiceReportId = Optional.ofNullable(serviceReportId);
			if (!oServiceReportId.isPresent()) {
				super.insertData(ServiceReportQueryIds.SQL_INSERT_SERVICE_REPORT_BY_FLIGHT.getQueryId(), model,
						sqlSessionTemplate);
				descripacyModel.setInwardServiceReportId(model.getInwardServiceReportId());
			} else {
				descripacyModel.setInwardServiceReportId(serviceReportId);
			}
			super.insertData(ServiceReportQueryIds.SQL_INSERT_SERVICE_REPORT_SHIPMENT_DISCREPANCY.getQueryId(),
					descripacyModel, sqlSessionTemplate);
		}

	}

	@Override
	public ShipmentNotificationDetail getShipmentNotificationDetail(ShipmentNotification shipmentNotification)
			throws CustomException {
		return fetchObject("sqlSelectShipmentNotificationContactDetail", shipmentNotification, sqlSessionTemplate);
	}

	@Override
	public List<BookingInfo> getBookingInfo(DocumentVerificationShipmentModel verificationModel)
			throws CustomException {

		return fetchList("sqlgetBookingInfoForVerification", verificationModel, sqlSessionTemplate);
	}

	@Override
	public void updateBookingInfo(BookingInfo bookingDetails) throws CustomException {
		updateData("updateBookingInfoShipmentVerificationDetails", bookingDetails, sqlSessionTemplate);
		updateData("updatePartBookingInfoShipmentVerificationDetails", bookingDetails, sqlSessionTemplate);
		updateData("updateFlightBookingInfoShipmentVerificationDetails", bookingDetails, sqlSessionTemplate);
	}

	@Override
	public List<BookingInfo> getPartBookingInfo(DocumentVerificationShipmentModel verificationModel)
			throws CustomException {
		return fetchList("sqlgetBookingInfoForVerificationPartCase", verificationModel, sqlSessionTemplate);
	}

	@Override
	public List<String> getCarrierCodesForSQGroup(String carrier) throws CustomException {
		return fetchList("sqlQueryCarrierCodesForSQGroupVerification", carrier, sqlSessionTemplate);
	}

	@Override
	public String getStatusOfCodeAdministration(String carrier) throws CustomException {
		return fetchObject("sqlQueryGetAdministrationGropupStatusForFinalise", carrier, sqlSessionTemplate);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.verification.dao.DocumentVerificationDAO#
	 * reopenFlightComplete(com.ngen.cosys.impbd.shipment.verification.model.
	 * DocumentVerificationFlightModel)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_COMPLETE)
	public void reopenFlightComplete(DocumentVerificationFlightModel requestModel) throws CustomException {
		DocumentVerificationFlightModel updatedFlightInfo = new DocumentVerificationFlightModel();
		updatedFlightInfo.setCreatedBy(requestModel.getCreatedBy());
		updatedFlightInfo.setCreatedOn(requestModel.getCreatedOn());
		updatedFlightInfo.setModifiedBy(requestModel.getCreatedBy());
		updatedFlightInfo.setModifiedOn(requestModel.getModifiedOn());
		updatedFlightInfo.setFlightId(requestModel.getFlightId());
		updatedFlightInfo.setFlightNumber(requestModel.getFlightNumber());
		updatedFlightInfo.setFlightDate(requestModel.getFlightDate());
		updatedFlightInfo.setLastupdatedBy(requestModel.getModifiedBy());
		updatedFlightInfo.setLastmodifiedOn(requestModel.getModifiedOn());
		updatedFlightInfo.setFlightCompleted(Boolean.FALSE);
		this.updateData("sqlQueryReOpenFlightCompleteForDocumentVerification", updatedFlightInfo, sqlSessionTemplate);
	}

}