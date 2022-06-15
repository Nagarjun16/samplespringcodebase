/**
 * CargoPreAnnouncementServiceImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 01 FEB, 2018 NIIT -
 */
package com.ngen.cosys.impbd.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.payload.InboundULDFinalizedStoreEvent;
import com.ngen.cosys.events.producer.InboundULDFinalizedStoreEventProducer;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.dao.CargoPreAnnouncementDAO;
import com.ngen.cosys.impbd.dao.RampCheckInDAO;
import com.ngen.cosys.impbd.model.CargPreAnnouncementShcModel;
import com.ngen.cosys.impbd.model.CargoPreAnnouncement;
import com.ngen.cosys.impbd.model.CargoPreAnnouncementBO;
import com.ngen.cosys.impbd.model.CargoPreAnnouncementBulkShipment;
import com.ngen.cosys.impbd.model.FlightDetails;
import com.ngen.cosys.impbd.model.RampCheckInUld;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.service.util.model.FlightInfo;
import com.ngen.cosys.validator.dao.FlightValidationDao;
import com.ngen.cosys.validator.enums.FlightType;
import com.ngen.cosys.validator.model.FlightValidateModel;
import com.ngen.cosys.validator.utils.FlightHandlingSystemValidator;

/**
 * This class takes care of the responsibilities related to the
 * cargoPreAnnouncement Details that comes from the Controller.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class CargoPreAnnouncementServiceImpl implements CargoPreAnnouncementService {

	@Autowired
	private CargoPreAnnouncementDAO cargoPreAnnouncementDAO;

	@Autowired
	InboundULDFinalizedStoreEventProducer producer;

	@Autowired
	private RampCheckInDAO rampCheckInDAO;
	
	@Autowired
	private FlightHandlingSystemValidator handlingSystemValidator;
	
	@Autowired
	private FlightValidationDao flightValidationDao;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.service.CargoPreAnnouncementService#cargoPreAnnouncement
	 * (com.ngen.cosys.impbd.model.CargoPreAnnouncementBO)
	 */
	@Override
	public CargoPreAnnouncementBO cargoPreAnnouncement(CargoPreAnnouncementBO cargoPreAnnouncementBO)
			throws CustomException {
		String displaysegment = null;
		if(cargoPreAnnouncementBO.getSegment()!=null) {
			displaysegment=cargoPreAnnouncementBO.getSegment();
		}
		
		//Flight level checks
		FlightValidateModel flight = new FlightValidateModel();
		flight.setFlightType(FlightType.IMPORT.getType());
		flight.setFlightKey(cargoPreAnnouncementBO.getFlight());
		flight.setFlightDate(cargoPreAnnouncementBO.getDate());
		flight.setTenantAirport(cargoPreAnnouncementBO.getTenantAirport());

		boolean isFlightExist = flightValidationDao.isFlightExist(flight, FlightType.IMPORT.getType());

		if (isFlightExist) {
			boolean isFlightArrived = flightValidationDao.validateArivalFlightForImport(flight,
					cargoPreAnnouncementBO.getTenantAirport());
			if (!isFlightArrived) {
				throw new CustomException("incoming.flight.arrivalCheck", null, "E");
			}
		}
		if (!isFlightExist) {
			boolean isFlightCancelled = flightValidationDao.isIncomingFlightCancelled(flight,
					cargoPreAnnouncementBO.getTenantAirport());
			if (isFlightCancelled) {
				throw new CustomException("incoming.flight.cancelled", null, "E");
			} else {
				throw new CustomException("invalid.import.flight", null, "E");
			}
		}
		
		cargoPreAnnouncementBO = cargoPreAnnouncementDAO.flightDetails(cargoPreAnnouncementBO);
		if(!ObjectUtils.isEmpty(cargoPreAnnouncementBO) && !StringUtils.isEmpty(displaysegment)) {
			cargoPreAnnouncementBO.setSegment(displaysegment);
		}
		if (StringUtils.isEmpty(cargoPreAnnouncementBO)) {
			throw new CustomException("NORECORDS", "No records found ", "E");
		}

		cargoPreAnnouncementBO
				.setFinalzeAndunFinalize(cargoPreAnnouncementDAO.isFinalizeORunFinalize(cargoPreAnnouncementBO));
		CargoPreAnnouncementBO finalizedInfo=cargoPreAnnouncementDAO.preAnnoncementfinalizeInfo(cargoPreAnnouncementBO);
		if (finalizedInfo != null) {
			cargoPreAnnouncementBO.setFinalizedAt(finalizedInfo.getFinalizedAt());
			cargoPreAnnouncementBO.setFinalizedBy(finalizedInfo.getFinalizedBy());
		}
		
		List<CargoPreAnnouncementBulkShipment> cargoPreAnnouncementBulkShipmentList=cargoPreAnnouncementBO.getCargoPreAnnouncementBulkShipmentList();
		if(!CollectionUtils.isEmpty(cargoPreAnnouncementBulkShipmentList)) {
			for (CargoPreAnnouncementBulkShipment cargoPreAnnouncementBulkShipment : cargoPreAnnouncementBulkShipmentList) {
				if(cargoPreAnnouncementBulkShipment.getShipmentType().equalsIgnoreCase("BULK")) {
					cargoPreAnnouncementBO.setBulkShipments(cargoPreAnnouncementBulkShipment.getTotalShipments());
				}else if(cargoPreAnnouncementBulkShipment.getShipmentType().equalsIgnoreCase("ST BULK")) {
					cargoPreAnnouncementBO.setStBulkShipments(cargoPreAnnouncementBulkShipment.getTotalShipments());
				}
				
			}
		}

		
		List<String> shcs = new ArrayList<>();
		cargoPreAnnouncementBO.getShcListForPHC().forEach(shc -> {
			shcs.add(shc.getShc());
		});

		for (int i = 0; i < cargoPreAnnouncementBO.getCargoPreAnnouncementList().size(); i++) {
			
			if(cargoPreAnnouncementBO.getCargoPreAnnouncementList().get(i).getContentCode().equalsIgnoreCase("C")
					&& ("FFM".equalsIgnoreCase(cargoPreAnnouncementBO.getCargoPreAnnouncementList().get(i).getAnnouncementSourceType()) 
							|| "CPM".equalsIgnoreCase(cargoPreAnnouncementBO.getCargoPreAnnouncementList().get(i).getAnnouncementSourceType())
						    || "UCM".equalsIgnoreCase(cargoPreAnnouncementBO.getCargoPreAnnouncementList().get(i).getAnnouncementSourceType())
						    || "MANUAL".equalsIgnoreCase(cargoPreAnnouncementBO.getCargoPreAnnouncementList().get(i).getAnnouncementSourceType()))) {
				cargoPreAnnouncementBO.getCargoPreAnnouncementList().get(i).setHandlingWerehouseValidation(true);
				
			}

			List<CargPreAnnouncementShcModel> specialHandlingCodes = cargoPreAnnouncementBO
					.getCargoPreAnnouncementList().get(i).getSpecialHandlingCodes();
			String uldShcs = "";
			if (!CollectionUtils.isEmpty(specialHandlingCodes)) {
				uldShcs = specialHandlingCodes.stream().map(t -> t.getPreSpecialHandlingCode())
						.collect(Collectors.joining(","));
			}
			cargoPreAnnouncementBO.getCargoPreAnnouncementList().get(i).setShcCode(uldShcs);
			cargoPreAnnouncementBO.getCargoPreAnnouncementList().get(i).setSpecialHandlingCodes(specialHandlingCodes);
			if (cargoPreAnnouncementBO.getCargoPreAnnouncementList().get(i).isPhcFlag()) {
				cargoPreAnnouncementBO.getCargoPreAnnouncementList().get(i).setPhcFlag(true);
			} else if (specialHandlingCodes.size() == 0) {
				cargoPreAnnouncementBO.getCargoPreAnnouncementList().get(i).setPhcFlag(false);
			} else {
				int count = 0;
				for (int j = 0; j < specialHandlingCodes.size(); j++) {
					if (!shcs.contains(specialHandlingCodes.get(j).getPreSpecialHandlingCode())) {
						count++;
					}
				}

				if (count > 0) {
					cargoPreAnnouncementBO.getCargoPreAnnouncementList().get(i).setPhcFlag(false);
				} else {
					if (!cargoPreAnnouncementBO.getCargoPreAnnouncementList().get(i).isManualUpdateFlag()) {
						cargoPreAnnouncementBO.getCargoPreAnnouncementList().get(i).setPhcFlag(true);
					}
					
				}
			}

		}
		
		FlightInfo flightInfo = new FlightInfo();
		flightInfo.setFlightKey(cargoPreAnnouncementBO.getFlight());
		flightInfo.setFlightDate(cargoPreAnnouncementBO.getDate());
		flightInfo.setType("I");
		Boolean handlingSystem = handlingSystemValidator.isFlightHandlinginSystem(flightInfo);
		cargoPreAnnouncementBO.setHandlinginSystem(handlingSystem);

		return cargoPreAnnouncementBO;

	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.service.CargoPreAnnouncementService#
	 * insertCargoPreAnnouncement(com.ngen.cosys.impbd.model.CargoPreAnnouncementBO)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void insertUpdateCargoPreAnnouncement(CargoPreAnnouncementBO cargoPreAnnouncementBO) throws CustomException {
		// Mapping flightkey in List
		for (CargoPreAnnouncement cargoPreAnnouncement : cargoPreAnnouncementBO.getCargoPreAnnouncementList()) {
			cargoPreAnnouncement.setFlightId(cargoPreAnnouncementBO.getFlightId());
			cargoPreAnnouncement.setIncomingFlightId(cargoPreAnnouncementBO.getFlightId().toString());
			cargoPreAnnouncement.setIncomingFlight(cargoPreAnnouncementBO.getFlight());
			if (!ObjectUtils.isEmpty(cargoPreAnnouncementBO.getDate())) {
				cargoPreAnnouncement.setIncomingFlightDate(cargoPreAnnouncementBO.getDate().atStartOfDay());
			}
           List<String> prnShcCodes = cargoPreAnnouncementDAO.getPRNShcList(cargoPreAnnouncementBO);
			
			if(!CollectionUtils.isEmpty(prnShcCodes) && !CollectionUtils.isEmpty(cargoPreAnnouncement.getSpecialHandlingCodes())) {
				 List<String> shcList = cargoPreAnnouncement.getSpecialHandlingCodes().stream()
						                . map(e->e.getPreSpecialHandlingCode()).collect(Collectors.toList());
				List<String> nonMatch = shcList.stream()
					.filter(shcObj -> prnShcCodes.stream()
							.noneMatch(shcObj2 -> shcObj.equalsIgnoreCase(shcObj2)))
					.collect(Collectors.toList());
				String noneMatchShcs="";
				if(!CollectionUtils.isEmpty(nonMatch)) {
					noneMatchShcs = nonMatch.stream().map(t -> t).collect(Collectors.joining(","));
					throw new CustomException("Preannouement.groupShc","preSpecialHandlingCode", ErrorType.ERROR,
          					new String[] { cargoPreAnnouncement.getUldNumber()+"->"+noneMatchShcs });
				}
			}
		}
		List<CargoPreAnnouncement> cargoPreAnnouncementInsertList = cargoPreAnnouncementBO.getCargoPreAnnouncementList()
				.stream().filter(u -> u.getFlagInsert().equalsIgnoreCase("Y")).collect(Collectors.toList());
		for (CargoPreAnnouncement cargoPreAnnouncement : cargoPreAnnouncementInsertList) {
			if (MultiTenantUtility.isTenantAirport(cargoPreAnnouncement.getUldBoardPoint())) {
				throw new CustomException("cargoPre002", "uldBoardPoint", "E");
			}
			if (cargoPreAnnouncement.getUldBoardPoint().equalsIgnoreCase(cargoPreAnnouncement.getUldOffPoint())) {
				throw new CustomException("cargoPre003", "uldBoardPoint", "E");
			}

		}
		for (CargoPreAnnouncement cargoPreAnnouncement : cargoPreAnnouncementInsertList) {
			
			CargoPreAnnouncement tempcargoPreAnnouncement = cargoPreAnnouncementDAO
					.isCargoPreAnnouncementRecordExist(cargoPreAnnouncement);
			if (Optional.ofNullable(tempcargoPreAnnouncement).isPresent()) {
				cargoPreAnnouncement.setCargoPreAnnouncementId(tempcargoPreAnnouncement.getCargoPreAnnouncementId());
			}	
			
			if (Optional.ofNullable(cargoPreAnnouncement).isPresent()
					&& StringUtils.isEmpty(cargoPreAnnouncement.getCargoPreAnnouncementId())) {
				FlightDetails flightDetails =new FlightDetails();
				flightDetails.setFlightKey(cargoPreAnnouncement.getFlight());
				flightDetails.setFlightOriginDate(cargoPreAnnouncement.getDate());
				BigInteger outBoundFlightId=isFlightExist(flightDetails);
				cargoPreAnnouncement.setBookingFlightId(outBoundFlightId);
				cargoPreAnnouncementDAO.insertCargoPreAnnouncement(cargoPreAnnouncement);
				preAnnounceShc(cargoPreAnnouncement);
				BigInteger isRecordramcheckin = cargoPreAnnouncementDAO.isRamcheckinRecordExist(cargoPreAnnouncement);
				if (isRecordramcheckin != null) {
					List<CargoPreAnnouncement> ramcheckinList = new ArrayList<CargoPreAnnouncement>();
					ramcheckinList.add(cargoPreAnnouncement);
					cargoPreAnnouncementDAO.updateCargoPreannouncementRamCheckIn(ramcheckinList);
				} else {
					cargoPreAnnouncementDAO.insertCargoPreannouncementRamCheckIn(cargoPreAnnouncement);
					preAnnounceRampCheckinShc(cargoPreAnnouncement);
				}
			}
			
		}
		cargoPreAnnouncementDAO.updateCargoPreAnnouncement(cargoPreAnnouncementBO.getCargoPreAnnouncementList().stream()
				.filter(u -> u.getFlagCRUD().equalsIgnoreCase("U")).collect(Collectors.toList()));
		for (CargoPreAnnouncement shc : cargoPreAnnouncementBO.getCargoPreAnnouncementList().stream()
				.filter(u -> u.getFlagCRUD().equalsIgnoreCase("U")).collect(Collectors.toList())) {
			// delete shc
			cargoPreAnnouncementDAO.deleteCargoPreAnnouncementShc(shc);
			// insert shc preAnnounceShc();
			preAnnounceShc(shc);
		}
		cargoPreAnnouncementDAO
				.updateCargoPreannouncementRamCheckIn(cargoPreAnnouncementBO.getCargoPreAnnouncementList().stream()
						.filter(u -> u.getFlagCRUD().equalsIgnoreCase("U")).collect(Collectors.toList()));
		for (CargoPreAnnouncement shc : cargoPreAnnouncementBO.getCargoPreAnnouncementList().stream()
				.filter(u -> u.getFlagCRUD().equalsIgnoreCase("U")).collect(Collectors.toList())) {
			if (shc.getCargoPreAnnouncementId() != null) {
				shc.setCargoPreAnnouncementId(cargoPreAnnouncementDAO.isRamcheckinRecordExist(shc));
				shc.setRampCheckinId(cargoPreAnnouncementDAO.isRamcheckinRecordExist(shc));
			}
			// delete shc
			cargoPreAnnouncementDAO.deleteCargoPreAnnouncementRampcheckinShc(shc);
			// insert shc preAnnounceShc();
			preAnnounceRampCheckinShc(shc);
		}
	}

	private void preAnnounceShc(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException {
		if (!StringUtils.isEmpty(cargoPreAnnouncement.getSpecialHandlingCodes())) {
			for (CargPreAnnouncementShcModel preShcModel : cargoPreAnnouncement.getSpecialHandlingCodes()) {
				preShcModel.setSpecialHandlingCode(preShcModel.getPreSpecialHandlingCode());
				preShcModel.setId(cargoPreAnnouncement.getCargoPreAnnouncementId());
				if (!StringUtils.isEmpty(preShcModel.getId())
						&& !StringUtils.isEmpty(preShcModel.getPreSpecialHandlingCode())) {
					cargoPreAnnouncementDAO.insertCargoPreAnnouncementSHC(preShcModel);
				}
			}
		}
	}

	private void preAnnounceRampCheckinShc(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException {
		if (!StringUtils.isEmpty(cargoPreAnnouncement.getSpecialHandlingCodes())) {
			for (CargPreAnnouncementShcModel preShcModel : cargoPreAnnouncement.getSpecialHandlingCodes()) {
				preShcModel.setSpecialHandlingCode(preShcModel.getPreSpecialHandlingCode());
				preShcModel.setId(cargoPreAnnouncement.getRampCheckinId());
				if (!StringUtils.isEmpty(preShcModel.getId())
						&& !StringUtils.isEmpty(preShcModel.getPreSpecialHandlingCode())) {
					cargoPreAnnouncementDAO.insertCargoPreAnnouncementRamCheckinSHC(preShcModel);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.service.CargoPreAnnouncementService#
	 * deleteCargoPreAnnouncement(com.ngen.cosys.impbd.model.CargoPreAnnouncementBO)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public void deleteCargoPreAnnouncement(CargoPreAnnouncementBO cargoPreAnnouncementBO) throws CustomException {
		// On Deleting From CargoPreAnnouncement It Should Delete From Inbound Ramp
		// CheckIn Also
		List<RampCheckInUld> query = new ArrayList<>();
		cargoPreAnnouncementBO.getCargoPreAnnouncementList().forEach(record -> {
			record.setFlightId(cargoPreAnnouncementBO.getFlightId());
			// Check for Ramp check in i.e. driver associated OR check-in is done
			try {
				boolean isULDCheckedIn = this.cargoPreAnnouncementDAO.isULDCheckedIn(record);

				if (!isULDCheckedIn) {
					RampCheckInUld rampCheckInUld = new RampCheckInUld();
					rampCheckInUld.setUldNumber(record.getUldNumber());
					rampCheckInUld.setFlightId(cargoPreAnnouncementBO.getFlightId());
					query.add(rampCheckInUld);
				}
			} catch (CustomException ex) {
				// Do nothing
			}
		});

		rampCheckInDAO.delete(query);
		cargoPreAnnouncementDAO.deleteCargoPreAnnouncement(cargoPreAnnouncementBO.getCargoPreAnnouncementList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.service.CargoPreAnnouncementService#
	 * finalizeAndunFinalize(com.ngen.cosys.impbd.model.CargoPreAnnouncementBO)
	 */
	@Override
	public void finalizeAndunFinalize(CargoPreAnnouncementBO cargoPreAnnouncementBO) throws CustomException {
		if (cargoPreAnnouncementBO.getFinalzeAndunFinalize() == true) {
			cargoPreAnnouncementBO.setCreatedBy(cargoPreAnnouncementBO.getCreatedBy());
			if ("MAIL".equalsIgnoreCase(cargoPreAnnouncementBO.getScreenFunction())) {
				cargoPreAnnouncementDAO.mailPrefinalize(cargoPreAnnouncementBO);
				InboundULDFinalizedStoreEvent event = new InboundULDFinalizedStoreEvent();
				// event.setEventInboundULDFinalizedStoreId(new BigInteger("3"));
				event.setFlightId(cargoPreAnnouncementBO.getFlightId());
				event.setShipmentType("MAIL");
				event.setStatus(EventStatus.NEW.getStatus());
				event.setFinalizedAt(LocalDateTime.now());
				event.setFinalizedBy(cargoPreAnnouncementBO.getCreatedBy());
				event.setCreatedBy(cargoPreAnnouncementBO.getCreatedBy());
				event.setCreatedOn(LocalDateTime.now());
				event.setLastModifiedBy(cargoPreAnnouncementBO.getCreatedBy());
				event.setLastModifiedOn(LocalDateTime.now());
				producer.publish(event);
			} else {
				cargoPreAnnouncementDAO.cargoPrefinalize(cargoPreAnnouncementBO);
			}
		} else if (cargoPreAnnouncementBO.getFinalzeAndunFinalize() == false) {
			if ("MAIL".equalsIgnoreCase(cargoPreAnnouncementBO.getScreenFunction())) {
				cargoPreAnnouncementDAO.mailPreunfinalize(cargoPreAnnouncementBO);
			} else {
				cargoPreAnnouncementDAO.cargoPreunfinalize(cargoPreAnnouncementBO);
			}
		}

	}

	@Override
	public BigInteger isFlightExist(FlightDetails flightDetails) throws CustomException {
		return cargoPreAnnouncementDAO.isFlightExist(flightDetails);
	}

	@Override
	public void updateBreaKBulkIndicator(CargoPreAnnouncement preannoucementInfo) throws CustomException {
		cargoPreAnnouncementDAO.updateBrakBulkStatus(preannoucementInfo);
		
	}
	
}
