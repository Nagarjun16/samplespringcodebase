package com.ngen.cosys.validator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.ics.model.ULD;
import com.ngen.cosys.satssginterfaces.mss.dao.LoadShipmentDao;
import com.ngen.cosys.satssginterfaces.mss.model.BuildupLoadShipment;
import com.ngen.cosys.satssginterfaces.mss.model.LoadedShipment;
import com.ngen.cosys.satssginterfaces.mss.model.UldShipment;

@Component
public class LoadShipmentValidator extends BaseBO {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private LoadShipmentDao dao;
/*
	@Autowired
	private AssignULDService assignULDService;*/

	/**
	 * @param data
	 * @throws CustomException
	 */
	public void validateData(String data) throws CustomException {
		if (StringUtils.isEmpty(data)) {
			throw new CustomException();
		}
	}

	/**
	 * @param data
	 * @throws CustomException
	 */
	public void validateData(BigInteger data) throws CustomException {
		if (data.intValue() == 0) {
			throw new CustomException();
		}
	}

	/**
	 * @param load
	 * @return
	 */
	public boolean validatePieceWeight(LoadedShipment load) {
		boolean flag = true;
		if (load.getMovePiecs().longValue() > load.getLocationPiecs().longValue()
				|| load.getMoveWeight().doubleValue() > load.getLocationWeight().doubleValue()) {
			flag = false;
		}
		return flag;
	}

	/**
	 * @param load
	 * @return
	 */
	public boolean validateDryIceWeight(LoadedShipment load) {
		boolean flag = true;
		if (load.getMoveDryIce().setScale(2).doubleValue() > load.getDryIceWeight().setScale(2).doubleValue()) {
			flag = false;
		}
		return flag;
	}

	/**
	 * @param uld
	 * @return
	 * @throws CustomException
	 */
	public boolean validateULDCarrieAndULDTypeMatch(UldShipment uld) throws CustomException {
		boolean flag = true;
		if (!StringUtils.isEmpty(uld.getFlightKey()) && !StringUtils.isEmpty((uld.getAssUldTrolleyNo()))) {
			String carriercode = uld.getAssUldTrolleyNo().substring(uld.getAssUldTrolleyNo().length() - 3);
			String carrierCode2 = uld.getAssUldTrolleyNo().substring(uld.getAssUldTrolleyNo().length() - 2);
			String uldtype = uld.getAssUldTrolleyNo().substring(0, 3);
			uld.setUldCarrierCode(carriercode);
			uld.setUldType(uldtype);
			uld.setUldCarrierCode2(carrierCode2);
			// check whether uld type and carrier code is matched
			boolean isNotMatched = dao.checkULDCarrierAndTypeMatch(uld);
			boolean isBT = dao.checkIsULD(uld.getAssUldTrolleyId().intValue());

			// if carrier code and uldtype is not mathced then check for bypass is allowed
			// for this check or not
			if (!isNotMatched && !isBT) {
				boolean isBypassAllowed = dao.checkBypassForULDAndTypeMatch(uld);
				if (!isBypassAllowed) {
					flag = false;
				}
			}
		}
		return flag;
	}

	/**
	 * @param load
	 * @return
	 * @throws CustomException Validating ULD's max weight capacity
	 */
	public boolean validateULDMaxWeight(BuildupLoadShipment load) throws CustomException {
		boolean flag = false;
		int count = 0;
		BigInteger assUldId = BigInteger.valueOf(0);
		Double existingLoadedWeight = 0.0d;
		Double currentWeight = 0.0d;
		Double uldMaxWeight = 0.0d;
		boolean isBypassAllowed = true;
		boolean isUldWeightCheckNotRequired = true;
		String uldtype = load.getAssUldTrolleyNo().substring(0, 3);
		String carriercode = load.getAssUldTrolleyNo().substring(load.getAssUldTrolleyNo().length() - 3);
		String carrierCode2 = load.getAssUldTrolleyNo().substring(load.getAssUldTrolleyNo().length() - 2);
		ULD uldObj = new ULD();
		//uldObj.setHandlingArea(load.getTerminal());
		//AssignULD assUld = new AssignULD();

		for (UldShipment uld : load.getLoadedShipmentList()) {
			// getting ULD's max capacity
			if (count == 0) {
/*				// getting AssULdTrollryId by calling Service of
				uldObj.setUldTrolleyNo(uld.getAssUldTrolleyNo());
				if (!StringUtils.isEmpty(uld.getContentCode())) {
					uldObj.setContentCode(uld.getContentCode());
				}
				uldObj.setHeightCode(uld.getHeightCode());
				uldObj.setSegmentId(uld.getSegmentId());
				Segment seg = new Segment();
				seg.setSegmentId(uld.getSegmentId());
				assUld.setSegment(seg);
				uldObj.setPhcFlag((uld.getPhcIndicator() == 0) ? false : true);
				//assUld.setUld(uldObj);
				//assUld.setFlightKey(load.getFlightKey());
				//assUld.setFlightOriginDate(load.getFlightOriginDate());
				
				//AssignULD assUldObj = assignULDService.assignUld(assUld);
				//assUldId = assUldObj.getAssUldTrolleyId();
*/				uld.setUldType(uldtype);
				//uld.setAssUldTrolleyId(assUldId);
				uld.setUldCarrierCode(carriercode);
				uld.setUldCarrierCode2(carrierCode2);
				uldMaxWeight = dao.getULDMaxWeight(uld);
				isBypassAllowed = dao.checkBypassForWeightCheck(uld);
				isUldWeightCheckNotRequired = dao.isUldWeightCheckNotRequired(uld);
				existingLoadedWeight = dao.getLoadedWeight(uld.getAssUldTrolleyId().intValue());

			}
			// Itrating each shipment to get the total loaded weight on ULD
			for (LoadedShipment loadShip : uld.getLoadShipmentList()) {
				loadShip.setLocationWeight(loadShip.getLocationWeight().setScale(2));
				loadShip.setMoveWeight(loadShip.getMoveWeight().setScale(2));
				loadShip.setUldType(uldtype);
				if (loadShip.getMoveWeight() != null && loadShip.getMoveWeight().setScale(2).doubleValue() != 0.0) {
					currentWeight += loadShip.getMoveWeight().setScale(2).doubleValue();
				}
			}
			count++;
		}
		// calculation for the ULD max weight capacity
		if (uldMaxWeight != null && existingLoadedWeight + currentWeight > uldMaxWeight && !isBypassAllowed
				&& !isUldWeightCheckNotRequired) {
			flag = true;
		}
		return flag;
	}

	/**
	 * @param load
	 * @return
	 * @throws CustomException
	 */
	public boolean validateAWBMaxWeight(BuildupLoadShipment load) throws CustomException {
		boolean flag = false;
		Double holdWeight = 0.0d;
		List<Integer> assIdList = new ArrayList<>();
		Map<Integer, Double> assIdMap = new HashMap<>();

		for (UldShipment uld : load.getLoadedShipmentList()) {
			// getting AssULdTrollryId by calling Service of
			ULD uldObj = new ULD();
			//uldObj.setHandlingArea(load.getTerminal());
			String carriercode = uld.getAssUldTrolleyNo().substring(uld.getAssUldTrolleyNo().length() - 3);
			String carrierCode2 = uld.getAssUldTrolleyNo().substring(uld.getAssUldTrolleyNo().length() - 2);
			/*AssignULD assUld = new AssignULD();
			BigInteger assUldId = BigInteger.valueOf(0);
			uldObj.setUldTrolleyNo(uld.getAssUldTrolleyNo());
			if (!StringUtils.isEmpty(uld.getContentCode())) {
				uldObj.setContentCode(uld.getContentCode());
			}
			uldObj.setHeightCode(uld.getHeightCode());

			uldObj.setSegmentId(uld.getSegmentId());
			Segment seg = new Segment();
			seg.setSegmentId(uld.getSegmentId());
			assUld.setSegment(seg);
			uldObj.setPhcFlag((uld.getPhcIndicator() == 0) ? false : true);
			assUld.setUld(uldObj);
			assUld.setFlightKey(load.getFlightKey());
			assUld.setFlightOriginDate(load.getFlightOriginDate());
			AssignULD assUldObj = assignULDService.assignUld(assUld);
			assUldId = assUldObj.getAssUldTrolleyId();
			uld.setAssUldTrolleyId(assUldId);*/

			String uldtype = load.getAssUldTrolleyNo().substring(0, 3);
			Double uldMaxWeight = 0.0d;
			boolean isBypassAllowed = true;
			Double existingLoadedWeight = 0.0d;
			Double currentWeight = 0.0d;
			uld.setUldType(uldtype);
			uld.setUldCarrierCode(carriercode);
			uld.setUldCarrierCode2(carrierCode2);
			uldMaxWeight = dao.getULDMaxWeight(uld);
			isBypassAllowed = dao.checkBypassForWeightCheck(uld);
			existingLoadedWeight = dao.getLoadedWeight(uld.getAssUldTrolleyId().intValue());

			for (LoadedShipment loadShip : uld.getLoadShipmentList()) {
				if (loadShip.getMoveWeight() != null && loadShip.getMoveWeight().setScale(2).doubleValue() != 0.0) {
					currentWeight += loadShip.getMoveWeight().setScale(2).doubleValue();
				}
			}

			if (assIdList.contains(uld.getAssUldTrolleyId().intValue())) {
				holdWeight = assIdMap.get(uld.getAssUldTrolleyId().intValue());
				assIdMap.put(uld.getAssUldTrolleyId().intValue(), currentWeight + holdWeight);
				currentWeight += holdWeight;
			} else {
				assIdList.add(uld.getAssUldTrolleyId().intValue());
				assIdMap.put(uld.getAssUldTrolleyId().intValue(), currentWeight);
			}

			// calculation for the ULD max weight capacity
			if (uldMaxWeight != null && existingLoadedWeight + currentWeight > uldMaxWeight && !isBypassAllowed) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * @param load
	 * @return
	 * @throws CustomException
	 */
	public boolean validateCaoShcCheck(LoadedShipment load) throws CustomException {
		boolean flag = true;
		Integer id = load.getFlightId().intValue();
		if (id != null && id != 0) {
			return dao.checkCaoShcFlight(id);
		}
		return flag;
	}

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	public boolean validateShipmentLock(Integer id) throws CustomException {
		boolean flag = false;
		if (id != null && id != 0) {
			flag = dao.checkShipmentLock(id);
		}
		return flag;
	}

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	public boolean validateAcceptance(Integer id) throws CustomException {
		boolean flag = true;
		UldShipment shipment = dao.checkAcceptance(id);

		if (shipment != null && (shipment.getPartConfirm() != 1 || shipment.getFinalizeWeight() != 1)
				&& shipment.getReturned() != 0 && shipment.getRejected() != 0) {
			flag = false;
		}
		return flag;
	}

	/**
	 * @param shcs
	 * @return
	 * @throws CustomException
	 */
	public boolean validateColadableSHCCheck(List<String> shcs) throws CustomException {
		boolean flag = true;
		/*BuildUpSHC newSHCList = setSHC(shcs);

		List<BuildUpSHC> buildSHC = dao.checkNonCoLoadableSHC(newSHCList);

		for (BuildUpSHC build : buildSHC) {
			if (shcs.contains(build.getNonColoadableSHC())) {
				flag = false;
				return flag;
			}
		}*/
		return flag;
	}

	/**
	 * @param shcs
	 * @param count
	 * @return
	 */
	/*private BuildUpSHC setSHC(List<String> shcs) {
		int count = 0;
		BuildUpSHC newSHCList = new BuildUpSHC();

		for (String shcVal : shcs) {
			if (count == 0 && !StringUtils.isEmpty(shcVal)) {
				newSHCList.setShc1(shcVal);
			} else if (count == 1 && !StringUtils.isEmpty(shcVal)) {
				newSHCList.setShc2(shcVal);
			} else if (count == 2 && !StringUtils.isEmpty(shcVal)) {
				newSHCList.setShc3(shcVal);
			} else if (count == 3 && !StringUtils.isEmpty(shcVal)) {
				newSHCList.setShc4(shcVal);
			} else if (count == 4 && !StringUtils.isEmpty(shcVal)) {
				newSHCList.setShc5(shcVal);
			} else if (count == 5 && !StringUtils.isEmpty(shcVal)) {
				newSHCList.setShc6(shcVal);
			} else if (count == 6 && !StringUtils.isEmpty(shcVal)) {
				newSHCList.setShc7(shcVal);
			} else if (count == 7 && !StringUtils.isEmpty(shcVal)) {
				newSHCList.setShc8(shcVal);
			} else if (count == 8 && !StringUtils.isEmpty(shcVal)) {
				newSHCList.setShc9(shcVal);
			}
			count++;
		}
		return newSHCList;
	}*/

	/**
	 * @param shcs
	 * @return
	 * @throws CustomException
	 */
	public boolean validatePerishableSHCCheck(List<String> shcs) throws CustomException {
		/*BuildUpSHC newSHCList = setSHC(shcs);
		return dao.checkPerishableCompatibleGroup(newSHCList);*/
	   return false;
	}

	/**
	 * @param uld
	 * @return
	 * @throws CustomException
	 */
	public boolean validateULDAssignToFlight(UldShipment uld) throws CustomException {
		return dao.checkULDAssignToFlight(uld);
	}

	/**
	 * @param uld
	 * @return
	 * @throws CustomException
	 */
	public boolean validateUldSegment(UldShipment uld) throws CustomException {
		boolean flag = true;
		List<Integer> segment = dao.checkValidateSegment(uld.getAssUldTrolleyNo());

		if (!segment.contains(uld.getSegmentId().intValue())) {
			flag = false;
		}
		return flag;
	}

	/**
	 * @param uld
	 * @return
	 * @throws CustomException
	 */
	public boolean validatePERSHC(UldShipment uld) throws CustomException {
		boolean flag = true;
		Set<String> shcSet = new HashSet<>();
		for (LoadedShipment load : uld.getLoadShipmentList()) {
			if (!CollectionUtils.isEmpty(load.getShcList())) {
				load.getShcList().forEach(s -> {
					shcSet.add(s);
				});
			}
		}
		if (!CollectionUtils.isEmpty(shcSet)) {
			for (String s : shcSet) {
				if (!"PER".equalsIgnoreCase(s)) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * @param load
	 * @return
	 */
	public boolean validateNullPiecesWeight(LoadedShipment load) {
		boolean flag = true;

		if (load.getMoveWeight().doubleValue() == 0.0 && load.getMovePiecs().longValue() != 0
				|| load.getMovePiecs().longValue() == 0 && load.getMoveWeight().doubleValue() != 0.0) {
			flag = false;
		}
		return flag;
	}

	/**
	 * @param uld
	 * @return
	 */
	public boolean validateEmptyLoading(UldShipment uld) {
		boolean flag = false;

		for (LoadedShipment load : uld.getLoadShipmentList()) {
			if (load.getMovePiecs().longValue() != 0) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * @param load
	 * @return
	 */
	public boolean validateEqualityOfPieceWeight(UldShipment uld, LoadedShipment load) {
		boolean flag = true;

		if (uld.getLoadShipmentList().size() == 1) {
			long pieces = load.getLocationPiecs().longValue() - load.getMovePiecs().longValue();
			double weight = load.getLocationWeight().setScale(2).doubleValue()
					- load.getMoveWeight().setScale(2).doubleValue();
			
			if (pieces == 0 && weight != 0.0 || weight == 0 && pieces != 0) {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * @param event
	 * @throws CustomException
	 */
	/*public void validateBuildUpComplete(BuildUpCompleteEvent event) throws CustomException {
		boolean isLoadingDone = dao.isLoadingDone(event.getFlightId().intValue());

		if (!isLoadingDone) {
			throw new CustomException(LoadShipmentError.BUILDUP_COMPLETE_ERROR.getErrorId(), "", ErrorType.ERROR);
		}
	}*/
}
