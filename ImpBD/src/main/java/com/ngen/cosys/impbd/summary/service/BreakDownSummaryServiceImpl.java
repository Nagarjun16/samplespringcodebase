/**
 * This is a service implementation component which provides methods for
 * handling break down summary
 */
package com.ngen.cosys.impbd.summary.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.summary.dao.BreakDownSummaryDAO;
import com.ngen.cosys.impbd.summary.model.BreakDownSummary;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryModel;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryTonnageHandledModel;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryUldModel;
import com.ngen.cosys.impbd.summary.model.Email;

@Service
public class BreakDownSummaryServiceImpl implements BreakDownSummaryService {

	@Autowired
	private BreakDownSummaryDAO dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.summary.service.BreakDownSummaryService#get(com.ngen.
	 * cosys.impbd.summary.model.BreakDownSummaryModel)
	 */
	@Override
	public BreakDownSummaryModel get(BreakDownSummaryModel requestModel) throws CustomException {

		BreakDownSummaryModel summaryData = this.dao.get(requestModel);

		Optional<BreakDownSummaryModel> oWeight = Optional.ofNullable(summaryData);
		if (!oWeight.isPresent()) {
			throw new CustomException("no.record", null, ErrorType.ERROR);
		}
		BigDecimal tonnageBySP = BigDecimal.ZERO;
		BigDecimal tonnageBySATS = BigDecimal.ZERO;
		BigDecimal tonnageByULD = BigDecimal.ZERO;
		BigDecimal tonnageByBulk = BigDecimal.ZERO;
		BigDecimal totalTonnageByFlight = BigDecimal.ZERO;
		for (BreakDownSummaryUldModel summaryInfoData : summaryData.getUldInfo()) {
			// Calculate tonnage based on SATS or Service Contractor
			if ("YES".equalsIgnoreCase(summaryInfoData.getSats())) {
				tonnageBySATS = tonnageBySATS.add(summaryInfoData.getTonnageByGha());
				tonnageBySATS = tonnageBySATS.add(summaryInfoData.getTonnageByContractor());
			} else {
				tonnageBySP = tonnageBySP.add(summaryInfoData.getTonnageByContractor());
				tonnageBySP = tonnageBySP.add(summaryInfoData.getTonnageByGha());
			}

			// Calculate Tonnage Weight By Flight

			totalTonnageByFlight = totalTonnageByFlight.add(summaryInfoData.getTonnageByContractor());
			totalTonnageByFlight = totalTonnageByFlight.add(summaryInfoData.getTonnageByGha());

			// Calculate ULD and Bulk Weight
			if (!"Bulk".equalsIgnoreCase(summaryInfoData.getUldNumber())) {
				tonnageByULD = tonnageByULD.add(summaryInfoData.getTonnageByContractor());
				tonnageByULD = tonnageByULD.add(summaryInfoData.getTonnageByGha());
			} else {
				tonnageByBulk = tonnageByBulk.add(summaryInfoData.getTonnageByContractor());
				tonnageByBulk = tonnageByBulk.add(summaryInfoData.getTonnageByGha());
			}
		}

		// Add other tonnage
		if (!CollectionUtils.isEmpty(summaryData.getTonnageHandlingInfo())) {
			for (BreakDownSummaryTonnageHandledModel t : summaryData.getTonnageHandlingInfo()) {

				t.setOldAdditionalTonnage(t.getAdditionalTonnage());
				t.setOldSubtractTonnage(t.getSubtractTonnage());

				if (!StringUtils.isEmpty(t.getServiceContractor())) {
					tonnageBySP = tonnageBySP.add(t.getTonnage());
				} else {
					tonnageBySATS = tonnageBySATS.add(t.getTonnage());
				}
			}
		}

		summaryData.setTonnageBreakDownBySp(tonnageBySP);
		summaryData.setTonnageBreakDownBySats(tonnageBySATS);
		summaryData.setTotalTonnageByFlight(totalTonnageByFlight);
		summaryData.setTonnageBulkWeight(tonnageByBulk);
		summaryData.setTonnageULDWeight(tonnageByULD);

		summaryData.setDelayInMinutes(calculateFlightDelay(summaryData));

		if (summaryData.getDelayInMinutes().signum() == -1) {
			summaryData.setDelayInMinutes(BigInteger.ZERO);
		}

		return summaryData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.summary.service.BreakDownSummaryService#
	 * createBreakDownSummaryOnFlightComplete(com.ngen.cosys.impbd.summary.model.
	 * BreakDownSummary)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void createBreakDownSummaryOnFlightComplete(BreakDownSummary requestModel) throws CustomException {

		// 1. Derive the inbound break down tonnage information
		List<BreakDownSummaryUldModel> uldTrolleyTonnageInfo = this.dao.getInboundBreakDownTonnageInfo(requestModel);
		requestModel.setUldInfo(uldTrolleyTonnageInfo);

		// Retrieve distinct service contractor names
		if (!CollectionUtils.isEmpty(requestModel.getUldInfo())) {
			Set<String> serviceContractorNames = uldTrolleyTonnageInfo.stream()
					.collect(Collectors.groupingBy(BreakDownSummaryUldModel::getServiceContractorName)).keySet();
			StringBuilder serviceContractors = new StringBuilder();
			serviceContractorNames.forEach(t -> serviceContractors.append(t + " "));

			// Set it to model
			requestModel.setBreakDownStaffGroup(serviceContractors.toString());
		}

		// 2. Derive the inbound break down other tonnage information
		List<BreakDownSummaryTonnageHandledModel> otherTonnageInfo = this.dao
				.getInboundBreakDownOtherTonnageInfo(requestModel);
		requestModel.setTonnageHandlingInfo(otherTonnageInfo);

		// 3. Create Break Down Tonnage Summary
		this.dao.createBreakDownTonnageSummary(requestModel);

		// 4. Clear the ULD Trolley Info/SHC Info and other tonnage info
		this.dao.clearExistingSummaryInfo(requestModel);

		// 5. Create Break Down Tonnage ULD Summary
		if (!CollectionUtils.isEmpty(requestModel.getUldInfo())) {

			requestModel.getUldInfo().forEach(t -> t.setSummaryId(requestModel.getId()));
			this.dao.createBreakDownTonnageULDTrolleySummaryInfo(requestModel.getUldInfo());
		}

		// 6. Create Break Down Other Tonnage Summary Info
		if (!CollectionUtils.isEmpty(requestModel.getTonnageHandlingInfo())) {
			requestModel.getTonnageHandlingInfo().forEach(t -> t.setSummaryId(requestModel.getId()));
			this.dao.createBreakDownOtherTonnageSummaryInfo(requestModel.getTonnageHandlingInfo());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.summary.service.BreakDownSummaryService#
	 * createBreakDownSummary(com.ngen.cosys.impbd.summary.model.BreakDownSummary)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public BreakDownSummary createBreakDownSummary(BreakDownSummary requestModel) throws CustomException {

		if (!CollectionUtils.isEmpty(requestModel.getUldInfo())) {
			for (BreakDownSummaryUldModel uldData : requestModel.getUldInfo()) {
				if (uldData.getSats().equalsIgnoreCase("NO")) {
					if (Objects.isNull(uldData.getServiceContrator())) {
						uldData.addError("bd.emptyServiceContractor", null, ErrorType.ERROR);
						throw new CustomException();
					} else {
						uldData.setTonnageByContractor(uldData.getTonnageByContractor().add(uldData.getTonnageByGha()));
						uldData.setTonnageByGha(BigDecimal.ZERO);
					}
				}
				if (uldData.getSats().equalsIgnoreCase("YES")) {
					if (Objects.nonNull(uldData.getServiceContrator())) {
						uldData.addError("bd.servicecontractorshouldbeempty", null, ErrorType.ERROR);
						throw new CustomException();
					} else {
						uldData.setTonnageByGha(uldData.getTonnageByGha().add(uldData.getTonnageByContractor()));
						uldData.setTonnageByContractor(BigDecimal.ZERO);
					}
				}
			}
		}

		if (!StringUtils.isEmpty(requestModel.getReasonForDelay())
				&& !StringUtils.isEmpty(requestModel.getReasonForWaive())) {
			requestModel.addError("error.reason.for.delay.select.both", "", ErrorType.ERROR);
			throw new CustomException();
		}

		if (requestModel.getLiquIdatedDamageApplicable() && StringUtils.isEmpty(requestModel.getReasonForDelay())) {
			requestModel.addError("error.reason.for.delay", "", ErrorType.ERROR);
			throw new CustomException();
		}
		if (!requestModel.getLiquIdatedDamageApplicable() && !StringUtils.isEmpty(requestModel.getReasonForDelay())) {
			requestModel.addError("error.select.ld.applicable.checkbox", "", ErrorType.ERROR);
			throw new CustomException();
		}
		if (requestModel.getLiquIdatedDamagesWaived() && StringUtils.isEmpty(requestModel.getReasonForWaive())) {
			requestModel.addError("error.reason.for.waive", "", ErrorType.ERROR);
			throw new CustomException();
		}
		if (!requestModel.getLiquIdatedDamagesWaived() && !StringUtils.isEmpty(requestModel.getReasonForWaive())) {
			requestModel.addError("error.select.ld.waived.checkbox", "", ErrorType.ERROR);
			throw new CustomException();
		}
		if (requestModel.getLiquIdatedDamageApplicable()) {
			requestModel.setApprovedLDApplicableApprovedOn(requestModel.getCreatedOn());
		}
		if (requestModel.getLiquIdatedDamagesWaived()) {
			requestModel.setApprovedLDWaiveApprovedOn(requestModel.getCreatedOn());
		}
		if (!CollectionUtils.isEmpty(requestModel.getUldInfo())) {
			for (BreakDownSummaryUldModel uldModel : requestModel.getUldInfo()) {
				if (!StringUtils.isEmpty(uldModel.getServiceContrator())) {
					uldModel.setServiceContractorName(
							this.dao.getServiceContractorName(uldModel.getServiceContrator()));
				} else {
					uldModel.setServiceContractorName("");
				}
			}
		}
		if (!CollectionUtils.isEmpty(requestModel.getTonnageHandlingInfo())) {
			for (BreakDownSummaryTonnageHandledModel breakDownSummaryTonnageHandledModel : requestModel
					.getTonnageHandlingInfo()) {
				if (!StringUtils.isEmpty(breakDownSummaryTonnageHandledModel.getServiceContractor())) {
					breakDownSummaryTonnageHandledModel.setServiceContractorName(this.dao
							.getServiceContractorName(breakDownSummaryTonnageHandledModel.getServiceContractor()));
				} else {
					breakDownSummaryTonnageHandledModel.setServiceContractorName("");
				}
			}
		}

		// 1. Create Break Down Tonnage Summary
		this.dao.createBreakDownTonnageSummary(requestModel);

		if (!CollectionUtils.isEmpty(requestModel.getUldInfo())) {
			requestModel.getUldInfo().get(0).setRequestedFrom("Breakdown Summary");
		}

		// 2. Create Break Down Tonnage ULD Summary
		if (!CollectionUtils.isEmpty(requestModel.getUldInfo())) {
			requestModel.getUldInfo().forEach(t -> t.setSummaryId(requestModel.getId()));
			this.dao.createBreakDownTonnageULDTrolleySummaryInfo(requestModel.getUldInfo());
		}

		// 3. Create Break Down Other Tonnage Summary Info
		if (!CollectionUtils.isEmpty(requestModel.getTonnageHandlingInfo())) {
			for (BreakDownSummaryTonnageHandledModel breakDownSummaryTonnageHandledModel : requestModel
					.getTonnageHandlingInfo()) {
				if (StringUtils.isEmpty(breakDownSummaryTonnageHandledModel.getServiceContractor())) {
					breakDownSummaryTonnageHandledModel.addError("bd.emptyServiceContractor", "serviceContractor",
							ErrorType.ERROR);
				}
				if (StringUtils.isEmpty(breakDownSummaryTonnageHandledModel.getCargoType())) {
					breakDownSummaryTonnageHandledModel.addError("error.cargo.type.cannot.be.empty", "cargoType",
							ErrorType.ERROR);
				}
				if (ObjectUtils.isEmpty(breakDownSummaryTonnageHandledModel.getTonnage())) {
					breakDownSummaryTonnageHandledModel.setTonnage(BigDecimal.ZERO);

				}
				// 4.added validation in case of duplicate cargo types
				if ("C".equalsIgnoreCase(breakDownSummaryTonnageHandledModel.getFlagCRUD())) {
					breakDownSummaryTonnageHandledModel.setSummaryId(requestModel.getId());
					boolean isCargoTypeExists = this.dao
							.checkForCargoTypeExistsOrNot(breakDownSummaryTonnageHandledModel);
					if (isCargoTypeExists) {
						breakDownSummaryTonnageHandledModel.addError("error.cargo.type.cannot.be.duplicate",
								"cargoType", ErrorType.ERROR);
					}
				}

				if (!CollectionUtils.isEmpty(breakDownSummaryTonnageHandledModel.getMessageList())) {
					throw new CustomException();
				}
				if (!ObjectUtils.isEmpty(breakDownSummaryTonnageHandledModel.getAdditionalTonnage())) {
					BigDecimal additionalTonnage = breakDownSummaryTonnageHandledModel.getAdditionalTonnage()
							.subtract(breakDownSummaryTonnageHandledModel.getOldAdditionalTonnage());
					breakDownSummaryTonnageHandledModel
							.setTonnage(breakDownSummaryTonnageHandledModel.getTonnage().add(additionalTonnage));
				}
				if (!ObjectUtils.isEmpty(breakDownSummaryTonnageHandledModel.getSubtractTonnage())) {
					BigDecimal subtractTonnage = breakDownSummaryTonnageHandledModel.getSubtractTonnage()
							.subtract(breakDownSummaryTonnageHandledModel.getOldSubtractTonnage());
					breakDownSummaryTonnageHandledModel
							.setTonnage(breakDownSummaryTonnageHandledModel.getTonnage().subtract(subtractTonnage));
				}
				if (breakDownSummaryTonnageHandledModel.getTonnage().signum() == -1) {
					breakDownSummaryTonnageHandledModel.addError("error.tonnage.value.cannot.greater.than",
							"subtractTonnage", ErrorType.ERROR);
					throw new CustomException();
				}
			}
			requestModel.getTonnageHandlingInfo().forEach(t -> t.setSummaryId(requestModel.getId()));
			this.dao.createBreakDownOtherTonnageSummaryInfo(requestModel.getTonnageHandlingInfo());
		}

		return requestModel;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.summary.service.BreakDownSummaryService#updateFeedBack(
	 * com.ngen.cosys.impbd.summary.model.BreakDownSummary)
	 */
	@Override
	public void updateFeedBack(BreakDownSummary breakDownSummary) throws CustomException {
		this.dao.updateFeedBack(breakDownSummary);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.summary.service.BreakDownSummaryService#
	 * calculateFlightDelay(com.ngen.cosys.impbd.summary.model.
	 * BreakDownSummaryModel)
	 */
	@Override
	public BigInteger calculateFlightDelay(BreakDownSummaryModel flightInfo) throws CustomException {
		if (Objects.nonNull(flightInfo.getStp()) && Objects.nonNull(flightInfo.getAta())
				&& Objects.nonNull(flightInfo.getFlightCompletionDataTime())) {
			// Logic For Flight Delay is that you will add Ata and STP and See the
			// Difference between flightComplete and ATA+STP.
			LocalDateTime ataWithStipulatedTime = flightInfo.getAta()
					.plusHours(flightInfo.getStp().divide(BigInteger.valueOf(60)).longValue());
			flightInfo.setDelayInMinutes(BigInteger.valueOf(
					Duration.between(ataWithStipulatedTime, flightInfo.getFlightCompletionDataTime()).toMinutes()));
			return flightInfo.getDelayInMinutes();
		}
		return BigInteger.ZERO;
	}

	@Override
	public List<Email> fetchEmails(String s) throws CustomException {
		return this.dao.fetchEmails(s);
	}

}