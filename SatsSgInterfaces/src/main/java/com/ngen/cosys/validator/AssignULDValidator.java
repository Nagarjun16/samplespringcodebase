package com.ngen.cosys.validator;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.satssginterfaces.mss.dao.AssignULDDAO;
import com.ngen.cosys.satssginterfaces.mss.dao.LoadShipmentDao;
import com.ngen.cosys.satssginterfaces.mss.model.AssignULD;
import com.ngen.cosys.satssginterfaces.mss.model.Segment;
import com.ngen.cosys.validator.dao.ContainerValidationDAO;

@Component
public class AssignULDValidator {

	@Autowired
	private AssignULDDAO assignULDDAO;
	@Autowired
	private ContainerValidationDAO containerValidationDao;
	@Autowired
	private LoadShipmentDao loadShipmentdao;

	public boolean checkIfUldCarrierCompatible(AssignULD uld) throws CustomException {
		if (uld != null && uld.getUld().getUldTrolleyNo() != null && !uld.getUld().isTrolleyInd()
				&& uld.getFlightKey() != null && uld.getFlightOriginDate() != null) {
			String uldNo = uld.getUld().getUldTrolleyNo();
			uld.getUld().setUldCarrier(uldNo.substring(uldNo.length() - 2));
			boolean carrierCompatible = false;
			Integer countOfRows = assignULDDAO.isCarrierCompatible(uld);
			if (countOfRows > 0) {
				carrierCompatible = true;
			}
			if (!carrierCompatible && uld.isDlsInd()) {
				uld.addError("exp.assignUldTrolley.carrierCompatible", "uldTrolleyNo",
						ErrorType.ERROR);
				uld.setErrorFlag(true);
			}
			return carrierCompatible;
		}
		return true;
	}

	public AssignULD checkIfAssignULDExists(AssignULD uld) throws CustomException {
		AssignULD assignUld;
		if (uld.getUld().getUldTrolleyNo() != null
				&& uld.getUld().getUldTrolleyNo().equalsIgnoreCase("BULK")) {
			assignUld = assignULDDAO.checkIfAssignUldBulkExists(uld);
		} else {
			assignUld = assignULDDAO.checkIfAssignUldExists(uld);
		}
		if (assignUld != null) {
			uld.setAssUldTrolleyId(assignUld.getAssUldTrolleyId());
			Object[] message = { assignUld.getFlightKey(),
					assignUld.getFlightOriginDate().format(MultiTenantUtility.getTenantDateFormat()) };
			uld.addError("exp.assignUldTrolley.alreadyAssigned", "uldTrolleyNo", ErrorType.ERROR,
					message);
			uld.setErrorFlag(true);
		}
		boolean piggyUldExist = assignULDDAO.checkIfPiggyBackUldExistsInInventory(uld);
		if (piggyUldExist) {
			uld.addError("exp.assignPiggybackUldTrolley.alreadyAssigned", "uldTrolleyNo",
					ErrorType.ERROR);
			uld.setErrorFlag(true);
		}
		return uld;
	}

	public AssignULD checkULDSpecificValidations(AssignULD uld) throws CustomException {
		// if (uld.getUld().getTareWeight() == null ||
		// uld.getUld().getTareWeight().compareTo(BigDecimal.ZERO) == 0) {
		// uld.addError(AssignULDError.ULD_TAREWEIGHT_BLANK.getErrorId(), "tareWeight",
		// ErrorType.ERROR);
		// uld.setErrorFlag(true);
		// }
		if (uld.getUld().getHeightCode() == null) {
			uld.addError("exp.assignUldTrolley.blankHeightCode", "heightCode", ErrorType.ERROR);
			uld.setErrorFlag(true);
		}
		Integer isPerishableCargo = assignULDDAO.isPerishableCargo(uld);
		Integer isUldLoaded = assignULDDAO.isUldLoaded(uld);
		if ((isUldLoaded > 0) && (!uld.getUld().isPhcFlag()) && (isPerishableCargo > 0)) {
			uld.addError("exp.assignUldTrolley.perishableCargo", "uldTrolleyNo", ErrorType.ERROR);
			uld.setErrorFlag(true);
		}
		if ((isUldLoaded > 0) && (uld.getUld().isPhcFlag()) && (isPerishableCargo == 0)) {
			uld.addError("exp.assignUldTrolley.generalCargo", "uldTrolleyNo", ErrorType.ERROR);
			uld.setErrorFlag(true);
		}
		checkPiggyBackULDSpecificValidations(uld);
		return uld;
	}

	/*
	 * if (uld.getUld().getUldTrolleyNo().length() < 9) {
	 * uld.addError(AssignULDError.ULD_MIN_LENGTH.getErrorId(), "uldTrolleyNo",
	 * ErrorType.ERROR); uld.setErrorFlag(true); }
	 */
	public AssignULD checkTrolleySpecificValidations(AssignULD uld) throws CustomException {
		if (uld.getUld().getUldTrolleyNo().length() > 9) {
			uld.addError("exp.assignUldTrolley.uldMinLength", "uldTrolleyNo", ErrorType.ERROR);
			uld.setErrorFlag(true);
		}
		if (assignULDDAO.searchInULDInventory(uld)) {
			uld.addError("exp.assignUldTrolley.invalidTrolleyNumber", "uldTrolleyNo", ErrorType.ERROR);
			uld.setErrorFlag(true);
		}
		return uld;
	}

	public AssignULD checkCommonValidations(AssignULD uld) throws CustomException {
		if (assignULDDAO.isDamaged(uld) > 0) {
			uld.addError("exp.assignUldTrolley.damagedUld", "uldTrolleyNo", ErrorType.ERROR);
			uld.setErrorFlag(true);
		}
		return uld;
	}

	public void checkPiggyBackULDSpecificValidations(AssignULD uld) throws CustomException {
		// else {
		String uldTrolleyNumber = uld.getUld().getUldTrolleyNo();
		boolean uldFlag = containerValidationDao.isULDType(uldTrolleyNumber.substring(0, 3)) && (containerValidationDao
				.isULDCarrier(uldTrolleyNumber.substring(uldTrolleyNumber.length() - 3))
				|| containerValidationDao.isULDCarrier(uldTrolleyNumber.substring(uldTrolleyNumber.length() - 2)));
		if (!uldFlag) {
			uld.addError("exp.assignUldTrolley.invalidContainerNumber", "uldTrolleyNo", ErrorType.ERROR);
			uld.setErrorFlag(true);
		}
		// }
		// return uld;
	}

	public AssignULD checkIfUldExistsInInventory(AssignULD uld) throws CustomException {
		// TODO Auto-generated method stub
		boolean uldExists = assignULDDAO.checkIfUldExistsInInventory(uld);
		if (!uldExists) {
			uld.setWarningFlag(true);
		}
		return uld;
	}

	public AssignULD checkPiggyBackUldIsExist(AssignULD uld) throws CustomException {
		int count = 0;
		if (uld.getPiggyBackULDList() != null) {
			for (AssignULD piggyBackUld : uld.getPiggyBackULDList()) {
				if (Action.CREATE.toString().equalsIgnoreCase(piggyBackUld.getFlagCRUD())
						|| Action.UPDATE.toString().equalsIgnoreCase(piggyBackUld.getFlagCRUD())) {
					AssignULD assignUld = assignULDDAO.checkIfAssignUldExists(piggyBackUld);
					// BigInteger assignUldTrolleyId =
					// assignULDDAO.checkIfAssignUldExists(piggyBackUld);
					if (assignUld != null) {
						/*
						 * Object[] message = { assignUld.getFlightKey(),
						 * assignUld.getFlightOriginDate().format(DateTimeFormatter.ofPattern(
						 * "ddMMMMyyyy"))};
						 * uld.addError(AssignULDError.ULDTROLLEY_ASSIGNED_TO_FLIGHT.getErrorId(),
						 * "assignedULDPiggyBackList." + count + ".uldTrolleyNo", ErrorType.ERROR,
						 * message); uld.setErrorFlag(true);
						 */
						// uld.setErrorFlag(true);
					}
					boolean piggyUldExist = assignULDDAO.checkIfPiggyBackUldExistsInInventory(piggyBackUld);
					if (piggyUldExist) {
						/*
						 * uld.addError(AssignULDError.ULDTROLLEY_ASSIGNED_TO_FLIGHT.getErrorId(),
						 * "assignedULDPiggyBackList." + count + ".uldTrolleyNo", ErrorType.ERROR);
						 */
						// uld.setErrorFlag(true);
						// uld.setErrorFlag(true);
					}
					String uldTrolleyNumber = piggyBackUld.getUld().getUldTrolleyNo();
					boolean uldFlag = containerValidationDao.isULDType(uldTrolleyNumber.substring(0, 3))
							&& (containerValidationDao
									.isULDCarrier(uldTrolleyNumber.substring(uldTrolleyNumber.length() - 3))
									|| containerValidationDao
									.isULDCarrier(uldTrolleyNumber.substring(uldTrolleyNumber.length() - 2)));
					if (!uldFlag) {
						uld.addError("exp.assignUldTrolley.invalidContainerNumber",
								"assignedULDPiggyBackList." + count + ".uldTrolleyNo", ErrorType.ERROR);
						uld.setErrorFlag(true);
					}
				}
				++count;
			}
		}
		return uld;
	}

	// public boolean validateULDCarrieAndULDTypeMatch(UldShipment uld) throws
	// CustomException {
	// boolean flag = true;
	// if (!StringUtils.isEmpty(uld.getFlightKey()) &&
	// !StringUtils.isEmpty((uld.getAssUldTrolleyNo()))) {
	// String carriercode =
	// uld.getAssUldTrolleyNo().substring(uld.getAssUldTrolleyNo().length() - 3);
	// String carrierCode2 =
	// uld.getAssUldTrolleyNo().substring(uld.getAssUldTrolleyNo().length() - 2);
	// String uldtype = uld.getAssUldTrolleyNo().substring(0, 3);
	// uld.setUldCarrierCode(carriercode);
	// uld.setUldType(uldtype);
	// uld.setUldCarrierCode2(carrierCode2);
	// // check whether uld type and carrier code is matched
	// boolean isNotMatched = loadShipmentdao.checkULDCarrierAndTypeMatch(uld);
	// // if carrier code and uldtype is not mathced then check for bypass is
	// allowed
	// // for this check or not
	// if (!isNotMatched) {
	// boolean isBypassAllowed = loadShipmentdao.checkBypassForULDAndTypeMatch(uld);
	// if (!isBypassAllowed) {
	// flag = false;
	// }
	// }
	// }
	// return flag;
	// }

	/*public void validateAssignUldTrolleyToFlight(AssignULDFlight flight) throws CustomException {
		for (AssignULD assUld : flight.getAssignedULDList()) {
			Segment seg = new Segment();
			seg.setSegmentId(assUld.getUld().getSegmentId());
			assUld.setSegment(seg);
			if (!assUld.getFlagCRUD().equalsIgnoreCase(Action.DELETE.value())) {
				validateUldType(assUld);
			}
			if (assUld.isErrorFlag()) {
				flight.setErrorFlag(true);
			}
		}
	}*/

	public void validateUldType(AssignULD assUld) throws CustomException {
		String uldNumber = assUld.getUld().getUldTrolleyNo();
		if (uldNumber.length() < 9 && !uldNumber.startsWith("BT")
				&& !uldNumber.startsWith("MT")) {
			assUld.addError("exp.assignUldTrolley.invalidContainerNumber", "uldTrolleyNo", ErrorType.ERROR);
			assUld.setErrorFlag(true);

		} else if (uldNumber.length() > 9) {
			Boolean isvlaidULdType = assignULDDAO.isUldExistInUldMaster(uldNumber.substring(0, 3));
			if (!isvlaidULdType) {
				assUld.addError("exp.assignUldTrolley.invalid.uld.type", "", ErrorType.ERROR);
				assUld.setErrorFlag(true);
			}

		}
	}
}