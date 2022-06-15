package com.ngen.cosys.impbd.instruction.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.instruction.dao.BreakDownHandlingInstructionDAO;
import com.ngen.cosys.impbd.instruction.model.BreakDownHandlingInformationByHouse;
import com.ngen.cosys.impbd.instruction.model.BreakDownHandlingInformationQuery;
import com.ngen.cosys.impbd.instruction.model.BreakDownHandlingInstructionFlightSegmentModel;
import com.ngen.cosys.impbd.instruction.model.BreakDownHandlingInstructionShipmentModel;
import com.ngen.cosys.impbd.instruction.model.BreakdownHandlingListResModel;
import com.ngen.cosys.model.SHCModel;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class BreakDownHandlingInformationServiceImpl implements BreakDownHandlingInformationService {

	@Autowired
	private BreakDownHandlingInstructionDAO dao;

	@Autowired
	private ShipmentProcessorService shipmentProcessorService;

	@Override
	public List<BreakdownHandlingListResModel> selectBreakDownHandlingInformations(
			BreakDownHandlingInformationQuery breakDownHandlingInformationQuery) throws CustomException {
		return dao.selectBreakDownHandlingInformation(breakDownHandlingInformationQuery);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteBreakDownHandlingInformations(
			BreakDownHandlingInstructionFlightSegmentModel breakDownHandlingInformation) throws CustomException {

		List<BreakDownHandlingInstructionShipmentModel> bdhiModelList = breakDownHandlingInformation.getShipments();

		for (BreakDownHandlingInstructionShipmentModel breakDownHandlingInstructionShipmentModel : bdhiModelList) {
			breakDownHandlingInstructionShipmentModel.setCreatedOn(LocalDateTime.now());
			breakDownHandlingInstructionShipmentModel.setCreatedBy(breakDownHandlingInformation.getLoggedInUser());

			dao.deleteBreakDownHandlingInformationHouse(breakDownHandlingInstructionShipmentModel);
			dao.deleteBreakDownHandlingInformation(breakDownHandlingInstructionShipmentModel);

		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void createBreakDownHandlingInformation(
			BreakDownHandlingInstructionFlightSegmentModel breakDownHandlingInformationQuery) throws CustomException {

		List<BreakDownHandlingInstructionShipmentModel> bdhiModelList = breakDownHandlingInformationQuery
				.getShipments();
		
		ArrayList<String> hawbList = new ArrayList<String>();
		for(BreakDownHandlingInstructionShipmentModel houseList : bdhiModelList)
		{
			for(BreakDownHandlingInformationByHouse housenumberList: houseList.getHouse())
				{
					hawbList.add(housenumberList.getHouseNumber());
				}
		}
		Set<String> hawbSet = new HashSet<>();
		hawbSet.addAll(hawbList);
		if (hawbSet.size() < hawbList.size()) {
			throw new CustomException("duplicate.house.found", "", ErrorType.ERROR);
		}

		bdhiModelList.forEach(model -> {
			List<SHCModel> shcs = model.getShcs();
			shcs.forEach(m -> {
				if (m != null && null != m.getSpecialHandlingCode()) {
					m.setSpecialHandlingCode(m.getSpecialHandlingCode().trim());
				}
			});
		});

		List<String> awbList = bdhiModelList.stream()
				.map(breakDownHandlingInstructionShipmentModel -> breakDownHandlingInstructionShipmentModel
						.getShipmentNumber())
				.collect(Collectors.toList());
		

		Set<String> awbSet = new HashSet<>();
		awbSet.addAll(awbList);	
				if (awbSet.size() < awbList.size()) {
			throw new CustomException("duplicateAWB", "", ErrorType.ERROR);
		} else {
			for (BreakDownHandlingInstructionShipmentModel breakDownHandlingInstructionShipmentModel : bdhiModelList) {
				breakDownHandlingInstructionShipmentModel.setCreatedOn(LocalDateTime.now());
				breakDownHandlingInstructionShipmentModel
						.setCreatedBy(breakDownHandlingInformationQuery.getLoggedInUser());
				breakDownHandlingInstructionShipmentModel.setShipmentDate(shipmentProcessorService
						.getShipmentDate(breakDownHandlingInstructionShipmentModel.getShipmentNumber()));

				// Check for the duplicate break down handling information
				if (!CollectionUtils.isEmpty(breakDownHandlingInstructionShipmentModel.getHouse())) {
					// Iterate and check each house for a given shipment exists OR not
					for (BreakDownHandlingInformationByHouse breakDownHandlingInformationByHouse : breakDownHandlingInstructionShipmentModel
							.getHouse()) {
						if (ObjectUtils.isEmpty(breakDownHandlingInstructionShipmentModel.getBreakdownId())) {
							boolean isHouseExists = this.dao.isBreakDownInstructionExists(
									breakDownHandlingInformationQuery.getFlightId(),
									breakDownHandlingInstructionShipmentModel.getShipmentNumber(),
									breakDownHandlingInstructionShipmentModel.getShipmentDate(),
									breakDownHandlingInformationByHouse.getHouseNumber());
							if (isHouseExists) {
								throw new CustomException("DUPLICATE_PIGEONHOLEMASTER", "houseNumber", ErrorType.ERROR);
							}
						}

					}
				} else {
					// Check shipment exists OR not
					if (ObjectUtils.isEmpty(breakDownHandlingInstructionShipmentModel.getBreakdownId())) {
						boolean isShipmentExists = this.dao.isBreakDownInstructionExists(
								breakDownHandlingInformationQuery.getFlightId(),
								breakDownHandlingInstructionShipmentModel.getShipmentNumber(),
								breakDownHandlingInstructionShipmentModel.getShipmentDate(), null);
						if (isShipmentExists) {
							throw new CustomException("DUPLICATE_PIGEONHOLEMASTER", "shipmentNumber", ErrorType.ERROR);
						}
					}
				}

				if (breakDownHandlingInstructionShipmentModel.getBreakdownId() == null) {
					dao.createBreakDownHandlingInformation(breakDownHandlingInstructionShipmentModel);
					dao.createShipmentRemark(breakDownHandlingInstructionShipmentModel);

					List<BreakDownHandlingInformationByHouse> breakDownHandlingInformationByHouseList = breakDownHandlingInstructionShipmentModel
							.getHouse();

					if (!CollectionUtils.isEmpty(breakDownHandlingInformationByHouseList)) {

						for (BreakDownHandlingInformationByHouse breakDownHandlingInformationByHouse : breakDownHandlingInformationByHouseList) {

							// Check for house number
							if (!StringUtils.isEmpty(breakDownHandlingInformationByHouse.getHouseNumber())) {

								breakDownHandlingInformationByHouse.setCreatedOn(LocalDateTime.now());
								breakDownHandlingInformationByHouse
										.setCreatedBy(breakDownHandlingInformationQuery.getLoggedInUser());
								breakDownHandlingInformationByHouse
										.setImpBreakDownHandlingInformationId(breakDownHandlingInstructionShipmentModel
												.getImpBreakDownHandlingInformationId());

								dao.createBreakDownHandlingInformationHouse(breakDownHandlingInformationByHouse);
							}
						}
					}
				} else {
					dao.updateBreakDownHandlingInformation(breakDownHandlingInstructionShipmentModel);
					if (!StringUtils.isEmpty(breakDownHandlingInstructionShipmentModel.getInstruction())) {
						dao.updateShipmentRemark(breakDownHandlingInstructionShipmentModel);
					}
					List<BreakDownHandlingInformationByHouse> breakDownHandlingInformationByHouseList = breakDownHandlingInstructionShipmentModel
							.getHouse();

					dao.deleteBreakDownHandlingInformationHouse(breakDownHandlingInstructionShipmentModel);

					if (!CollectionUtils.isEmpty(breakDownHandlingInformationByHouseList)) {

						for (BreakDownHandlingInformationByHouse breakDownHandlingInformationByHouse : breakDownHandlingInformationByHouseList) {

							// Check for house number
							if (!StringUtils.isEmpty(breakDownHandlingInformationByHouse.getHouseNumber())) {
								breakDownHandlingInformationByHouse.setCreatedOn(LocalDateTime.now());
								breakDownHandlingInformationByHouse
										.setCreatedBy(breakDownHandlingInformationQuery.getLoggedInUser());
								breakDownHandlingInformationByHouse.setImpBreakDownHandlingInformationId(
										breakDownHandlingInstructionShipmentModel.getBreakdownId());

								dao.createBreakDownHandlingInformationHouse(breakDownHandlingInformationByHouse);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public List<BreakdownHandlingListResModel> selectArrivalData(
			BreakDownHandlingInformationQuery breakDownHandlingInformationQuery) throws CustomException {
		breakDownHandlingInformationQuery.setParameter1(breakDownHandlingInformationQuery.getFlightKey());
		breakDownHandlingInformationQuery.setParameter2(breakDownHandlingInformationQuery.getFlightOriginDate());
		return dao.selectArrivalData(breakDownHandlingInformationQuery);
	}

	@Override
	public List<String> getHawbInfo(String shipmentNumber) throws CustomException {
		if (!this.dao.checkForManifestData(shipmentNumber)) {
			throw new CustomException("impbd.shipment.not.exists.flight.manifest", "", ErrorType.ERROR);
		} else {
			return dao.getHAWBInfo(shipmentNumber);
		}
	}

}