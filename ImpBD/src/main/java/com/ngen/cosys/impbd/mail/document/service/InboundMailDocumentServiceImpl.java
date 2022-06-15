package com.ngen.cosys.impbd.mail.document.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.mail.document.dao.InboundMailDocumentDao;
import com.ngen.cosys.impbd.mail.document.model.InboundMailDocumentModel;
import com.ngen.cosys.impbd.mail.document.model.InboundMailDocumentShipmentModel;
import com.ngen.cosys.impbd.mail.validator.MailValidator;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster;
import com.ngen.cosys.impbd.shipment.document.service.ShipmentMasterService;
import com.ngen.cosys.impbd.shipment.remarks.constant.ShipmentRemarksType;
import com.ngen.cosys.impbd.shipment.remarks.model.ShipmentRemarksModel;
import com.ngen.cosys.impbd.shipment.remarks.service.ShipmentRemarksService;
import com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerificationModel;
import com.ngen.cosys.impbd.shipment.verification.service.ShipmentVerificationService;
import com.ngen.cosys.validator.enums.ShipmentType;

@Service
public class InboundMailDocumentServiceImpl implements InboundMailDocumentService {

	@Autowired
	@Qualifier("inboundMailDocumentValidator")
	private MailValidator validator;

	@Autowired
	private ShipmentVerificationService shipmentVerificationService;

	@Autowired
	private ShipmentMasterService shipmentMasterService;

	@Autowired
	private ShipmentRemarksService shipmentRemarksService;

	@Autowired
	private InboundMailDocumentDao inboundMailDocumentDao;

	@Autowired
    private ShipmentProcessorService shipmentProcessorService;

	private String form = "captureImportDocForm";

	@Override
	public InboundMailDocumentModel search(InboundMailDocumentModel requestModel) throws CustomException {
		if (StringUtils.isEmpty(requestModel.getFlightKey()) || null == requestModel.getFlightDate()) {
			throw new CustomException("CUST001", form, ErrorType.ERROR);
		}
		InboundMailDocumentModel flightData = inboundMailDocumentDao.flightdata(requestModel);
		if (flightData == null) {
			throw new CustomException("BOOKING1", form, ErrorType.ERROR);
		}
		requestModel.setFlightId(flightData.getFlightId());
		List<InboundMailDocumentShipmentModel> mailsBags = inboundMailDocumentDao.get(requestModel);
		if (CollectionUtils.isEmpty(mailsBags)) {
			requestModel.setFlightId(flightData.getFlightId());
			requestModel.setCarrierCode(flightData.getCarrierCode());
			return requestModel;
		} else {
			requestModel.setMailsBags(mailsBags);
			requestModel.setCarrierCode(flightData.getCarrierCode());
			return requestModel;
		}
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public InboundMailDocumentModel documentIn(InboundMailDocumentModel requestModel) throws CustomException {

		// Validate the model
		this.validator.validate(requestModel);

		// filtering the list which is having the flagCRUD C
		List<InboundMailDocumentShipmentModel> createData = requestModel.getMailsBags().stream()
				.filter(obj -> (Action.CREATE.toString().equalsIgnoreCase(obj.getFlagCRUD())))
				.collect(Collectors.toList());

		if (!CollectionUtils.isEmpty(createData)) {
			for (InboundMailDocumentShipmentModel mailBagInsertdata : createData) {

				// 1. Add shipment information to Shipment Master (Master, House, SHC,
				// SHCHandlingGroup, Customer Info)
				ShipmentMaster shipmentMaster = new ShipmentMaster();
				shipmentMaster.setShipmentType(ShipmentType.Type.MAIL);
				shipmentMaster.setShipmentNumber(mailBagInsertdata.getShipmentNumber());
				shipmentMaster.setShipmentdate(mailBagInsertdata.getShipmentdate());
				shipmentMaster.setPiece(mailBagInsertdata.getPiece());
				shipmentMaster.setWeight(mailBagInsertdata.getWeight());
				shipmentMaster.setOriginOfficeExchange(mailBagInsertdata.getOriginOfficeExchange());
				shipmentMaster.setDestinationOfficeExchange(mailBagInsertdata.getDestinationOfficeExchange());
				// Set the carrier of the flight
				shipmentMaster.setCarrierCode(requestModel.getCarrierCode());
				shipmentMaster.setMailCategory(mailBagInsertdata.getMailCategory());
				shipmentMaster.setMailSubCategory(mailBagInsertdata.getMailSubCategory());
				shipmentMaster.setRegistered(mailBagInsertdata.getRegistered());
				shipmentMaster.setNatureOfGoodsDescription(ShipmentType.Type.MAIL);
				shipmentMaster.setOrigin(mailBagInsertdata.getOriginOfficeExchange().substring(2, 5));
				shipmentMaster.setDestination(mailBagInsertdata.getDestinationOfficeExchange().substring(2, 5));
				// Get the shipment date
				LocalDate shipmentDate = shipmentProcessorService.getShipmentDate(mailBagInsertdata.getShipmentNumber());
				shipmentMaster.setShipmentdate(shipmentDate);
				shipmentMasterService.createShipment(shipmentMaster);

				// 2. Add shipment information to Shipment Verification
				ShipmentVerificationModel shipmentVerification = new ShipmentVerificationModel();
				shipmentVerification.setFlightId(requestModel.getFlightId());
				shipmentVerification.setShipmentId(shipmentMaster.getShipmentId());
				shipmentVerification.setDocumentReceivedFlag(Boolean.TRUE);
				shipmentVerification.setInvokedFromBreakDown(Boolean.FALSE);
				shipmentVerification.setDocumentPieces(mailBagInsertdata.getPiece());
				shipmentVerification.setDocumentWeight(mailBagInsertdata.getWeight());
				shipmentVerificationService.createShipmentVerification(shipmentVerification);
				
				//update the shipment master pieces with respect to the sum of the document pieces
				InboundMailDocumentShipmentModel verificationTotalInfo = new InboundMailDocumentShipmentModel();
				verificationTotalInfo.setFlightId(requestModel.getFlightId());
				verificationTotalInfo.setShipmentId(shipmentMaster.getShipmentId());
				verificationTotalInfo.setShipmentMasterAlreadyCreated(false);
				inboundMailDocumentDao.updateShpMst(verificationTotalInfo);
				// 3. Add the remarks to Shipment Remarks at DN level
				Optional<String> oRemarks = Optional.ofNullable(mailBagInsertdata.getRemarks());
				if (oRemarks.isPresent()) {
					ShipmentRemarksModel shipmentRemarksModel = new ShipmentRemarksModel();
					shipmentRemarksModel.setRemarkType(ShipmentRemarksType.Type.GEN);
					shipmentRemarksModel.setFlightId(requestModel.getFlightId());
					shipmentRemarksModel.setShipmentId(shipmentMaster.getShipmentId());
					shipmentRemarksModel.setShipmentRemarks(oRemarks.get());
					shipmentRemarksModel.setShipmentType(ShipmentType.Type.MAIL);
					shipmentRemarksService.createShipmentRemarks(shipmentRemarksModel);
				}
				// requestModel.getMailBag().setShipmentId(shipmentMaster.getShipmentId());
			}
		}
		// Re-search the mail bags after data update
		// return this.search(requestModel);
		return requestModel;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void update(InboundMailDocumentModel requestModel) throws CustomException {
		if (!CollectionUtils.isEmpty(requestModel.getMailsBags())) {
			List<InboundMailDocumentShipmentModel> updateData = requestModel.getMailsBags().stream()
					.filter(obj -> (Action.UPDATE.toString().equalsIgnoreCase(obj.getFlagCRUD())))
					.collect(Collectors.toList());
			updateData.forEach(obj -> obj.setFlightId(requestModel.getFlightId()));
			if (!CollectionUtils.isEmpty(updateData)) {
				for (InboundMailDocumentShipmentModel value : updateData) {
				    inboundMailDocumentDao.updateShpVer(value);
				    value.setShipmentMasterAlreadyCreated(true);
					inboundMailDocumentDao.updateShpMst(value);
					inboundMailDocumentDao.update(value);
				}

			}

			List<InboundMailDocumentShipmentModel> deleteData = requestModel.getMailsBags().stream()
					.filter(obj -> Action.DELETE.toString().equalsIgnoreCase(obj.getFlagCRUD()))
					.collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(deleteData)) {
				checkShipmentId(deleteData);
				List<InboundMailDocumentShipmentModel> withoutUpdateDeleteData = deleteData.stream()
						.filter(obj -> !obj.getUpdated()).collect(Collectors.toList());
				if (!CollectionUtils.isEmpty(withoutUpdateDeleteData)) {
					inboundMailDocumentDao.deleteShipmentVerificetion(withoutUpdateDeleteData);
					inboundMailDocumentDao.deleteShipmentOCI(withoutUpdateDeleteData);
					inboundMailDocumentDao.deleteShipmentMasterHA(withoutUpdateDeleteData);
					inboundMailDocumentDao.deleteShipmentRemark(withoutUpdateDeleteData);
					inboundMailDocumentDao.deleteShipmentMaster(withoutUpdateDeleteData);
				}

			}
		}

		// to save the record
		this.documentIn(requestModel);
	}

	@Override
	public List<BigInteger> getDispatchYears() throws CustomException {
		List<BigInteger> years = new ArrayList<>();
		LocalDate currentDate = LocalDate.now();
		LocalDate startDate = currentDate.minusYears(10L);
		LocalDate endDate = currentDate.plusYears(10L);
		long startYear = startDate.getYear();
		long endYear = endDate.getYear();
		for (long i = startYear; i < endYear; i++) {
			years.add(new BigInteger(String.valueOf(i)));
		}
		return years;
	}

	private void checkShipmentId(List<InboundMailDocumentShipmentModel> requestModel) throws CustomException {
		for (InboundMailDocumentShipmentModel data : requestModel) {
			Integer count = inboundMailDocumentDao.checkShipmentId(data);

			Integer shipmentVerificationCount = inboundMailDocumentDao.checkShipmentVerificationId(data);
			if (count >= 1 || shipmentVerificationCount >= 1) {
				// throw new CustomException("BREAKDOWN001", form, ErrorType.ERROR);
				data.setPiece(BigInteger.ZERO);
				data.setWeight(BigDecimal.ZERO);
				List<InboundMailDocumentShipmentModel> updateShipmentData = new ArrayList<>();
				updateShipmentData.add(data);
				inboundMailDocumentDao.updateShpMst(data);
				data.setUpdated(true);
			}
			/*
			 * if (shipmentVerificationCount >= 1) { throw new
			 * CustomException("BREAKDOWN001", form, ErrorType.ERROR); }
			 */
		}
	}

	@Override
	public void validate(InboundMailDocumentShipmentModel requestModel) throws CustomException {
		if (null == requestModel.getShipmentNumber() || null == requestModel.getPiece()
				|| null == requestModel.getWeight() || " " == requestModel.getOriginOfficeExchange()
				|| "0" == requestModel.getOriginOfficeExchange() || "" == requestModel.getDestinationOfficeExchange()
				|| "0" == requestModel.getDestinationOfficeExchange() || "" == requestModel.getMailCategory()
				|| null == requestModel.getMailCategory() || "" == requestModel.getMailSubCategory()
				|| null == requestModel.getMailSubCategory() || null == requestModel.getRegistered()) {
			throw new CustomException("CUST003", form, ErrorType.ERROR);
		}
		Integer data = inboundMailDocumentDao.checkShipmentNumber(requestModel);
		if (data >= 1) {
			throw new CustomException("CN46_05", form, ErrorType.ERROR);
		}

		Integer count = inboundMailDocumentDao.checkOriginOfficeExchange(requestModel);
		if (count < 1) {
			throw new CustomException("data.mailbag.invalid.originoe", form, ErrorType.ERROR);
		}

		Integer value = inboundMailDocumentDao.checkDestinationOfficeExchange(requestModel);
		if (value < 1) {
			throw new CustomException("data.mailbag.invalid.destinationoe", form, ErrorType.ERROR);
		}
	}
}