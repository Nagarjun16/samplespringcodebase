package com.ngen.cosys.billing.chargepost.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.billing.chargepost.dao.ChargePostDAO;
import com.ngen.cosys.billing.chargepost.model.BillChargeConsolidationBO;
import com.ngen.cosys.billing.chargepost.model.BillEntryBO;
import com.ngen.cosys.billing.chargepost.model.BillGenBO;
import com.ngen.cosys.billing.chargepost.model.ChargeEntryBillPaidBO;
import com.ngen.cosys.billing.chargepost.model.CustomerBillingInfo;
import com.ngen.cosys.billing.chargepost.model.CustomerChargeWiseConsolidateData;
import com.ngen.cosys.billing.chargepost.model.CustomerDiscountBO;
import com.ngen.cosys.billing.chargepost.model.ThresholdRange;
import com.ngen.cosys.framework.constant.DiscountType;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * This service takes care of the ChargePost services.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
public class ChargePostServiceImpl implements ChargePostService {

	@Autowired
	private ChargePostDAO chargePostDAO;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void getGenerateCustomerSDBill() throws CustomException {
		CustomerChargeWiseConsolidateData consolidateData = new CustomerChargeWiseConsolidateData();
		LocalDate date = LocalDateTime.now().toLocalDate();
		consolidateData.setBillGenDate(date);
		List<CustomerChargeWiseConsolidateData> customerInfo = chargePostDAO
				.searchCustomersByBillGenDate(consolidateData);
		if (!customerInfo.isEmpty()) {
			// GET CUSTOMER BILLING CYCLE INFO
			List<CustomerBillingInfo> custBillCycleInfo = chargePostDAO.fetchCustomerCycleInfo(customerInfo);
			getNextBillingPostingDates(custBillCycleInfo);
			// SAVE BILL GEN INFO
			List<BillGenBO> billGenBOs = this.setBillGen(customerInfo, "SD");
			// SAVE BILL CHARGE CONSOLIDATION DETAILS
			this.saveBillChargeConsidation(customerInfo, billGenBOs);
			// GET CHARGE CODE DISCOUNT INFORMATION
			List<CustomerDiscountBO> customerDiscountBOs = this.getDiscountDetailsByChargeCodeId(billGenBOs);
			// APPLY CHARGE CODE DISCOUNT AND UPDATE
			this.saveDiscountAmountByChargeCodeId(customerDiscountBOs);
			// GET CHARGE CODE GROUP DISCOUNT INFORMATION
			List<CustomerDiscountBO> customerDiscountBOsByGroupId = this.getDiscountDetailsByGroupId();
			// APPLY CHARGE CODE GROUP DISCOUNT AND UPDATE
			this.saveDiscountAmountByGroupId(customerDiscountBOsByGroupId);
			List<BillEntryBO> billEntryBo = this.getApportionDetails(billGenBOs);
			if (billEntryBo != null)
				this.saveBillSDEntry(billEntryBo, billGenBOs, custBillCycleInfo);
		}
		// Update Next Billing/Posting date for customer without charges
		List<CustomerChargeWiseConsolidateData> customerUpdateInfo = chargePostDAO
				.searchCustomersWithoutCharges(customerInfo);
		if (!customerUpdateInfo.isEmpty()) {
			// GET CUSTOMER BILLING CYCLE INFO FOR CUSTOMERS WITHOUT CHARGES
			List<CustomerBillingInfo> custUpdateBillCycleInfo = chargePostDAO
					.fetchCustomerCycleInfo(customerUpdateInfo);
			getNextBillingPostingDates(custUpdateBillCycleInfo);
			updateNextBillingPostingDates(custUpdateBillCycleInfo);
		}
	}

	public void getNextBillingPostingDates(List<CustomerBillingInfo> custBillCycleInfo) {
		custBillCycleInfo.forEach(value -> {
			// Last Day of Billing Month
			int lastDayOfBillingMonth = getLastDayOfMonth(value.getNextBillingDate().getYear(),
					value.getNextBillingDate().getMonth());
			// Current Day of Next Billing Date
			int nextBillingDay = value.getNextBillingDate().getDayOfMonth();
			// Current Day of Next Posting Date
			int nextPostingDay = value.getNextSDPostingDate().getDayOfMonth();
			// First Bill Gen Day across cycles
			int firstBillGenDay = value.getSdCycleList().get(0).getBillGenerationDay().intValue();
			// First Posting Day across cycles
			int firstPostingDay = value.getSdCycleList().get(0).getPostingDay().intValue();
			// Last Bill Gen Day across cycles
			int lastBillGenDay = value.getSdCycleList().get(value.getSdCycleList().size() - 1).getBillGenerationDay()
					.intValue();
			lastBillGenDay = lastDayOfBillingMonth > lastBillGenDay ? lastBillGenDay : lastDayOfBillingMonth;
			// Last Posting Day across cycles
			int lastPostingDay = value.getSdCycleList().get(value.getSdCycleList().size() - 1).getPostingDay()
					.intValue();
			lastPostingDay = lastDayOfBillingMonth > lastPostingDay ? lastPostingDay : lastDayOfBillingMonth;

			LocalDateTime nextBillingDate = null;
			LocalDateTime nextPostingDate = null;

			int billingYear, postingYear;
			if (value.getNextBillingDate().getMonth().equals(Month.DECEMBER)) {
				billingYear = value.getNextBillingDate().getYear() + 1;
			} else {
				billingYear = value.getNextBillingDate().getYear();
			}

			if (value.getNextSDPostingDate().getMonth().equals(Month.DECEMBER)) {
				postingYear = value.getNextSDPostingDate().getYear() + 1;
			} else {
				postingYear = value.getNextSDPostingDate().getYear();
			}

			if (nextBillingDay >= lastBillGenDay) {
				try {
					nextBillingDate = LocalDateTime.of(billingYear, value.getNextBillingDate().getMonth().plus(1),
							firstBillGenDay, 0, 0);
				} catch (DateTimeException e) {
					nextBillingDate = LocalDateTime.of(billingYear, value.getNextBillingDate().getMonth().plus(1),
							getLastDayOfMonth(value.getNextBillingDate().getYear(),
									value.getNextBillingDate().getMonth().plus(1)),
							0, 0);
				}
				if (nextPostingDay < firstPostingDay) {
					try {
						nextPostingDate = LocalDateTime.of(value.getNextSDPostingDate().getYear(),
								value.getNextSDPostingDate().getMonth(), firstPostingDay, 0, 0);
					} catch (DateTimeException e) {
						nextPostingDate = LocalDateTime.of(value.getNextSDPostingDate().getYear(),
								value.getNextSDPostingDate().getMonth(),
								getLastDayOfMonth(value.getNextSDPostingDate().getYear(),
										value.getNextSDPostingDate().getMonth()),
								0, 0);
					}
				} else {
					try {
						nextPostingDate = LocalDateTime.of(postingYear, value.getNextSDPostingDate().getMonth().plus(1),
								firstPostingDay, 0, 0);
					} catch (DateTimeException e) {
						nextPostingDate = LocalDateTime.of(postingYear, value.getNextSDPostingDate().getMonth().plus(1),
								getLastDayOfMonth(value.getNextSDPostingDate().getYear(),
										value.getNextSDPostingDate().getMonth().plus(1)),
								0, 0);
					}
				}
				while (nextPostingDate.isBefore(nextBillingDate)) {
					try {
						nextPostingDate = LocalDateTime.of(postingYear, nextPostingDate.getMonth().plus(1),
								firstPostingDay, 0, 0);
					} catch (DateTimeException e) {
						nextPostingDate = LocalDateTime.of(postingYear, nextPostingDate.getMonth().plus(1),
								getLastDayOfMonth(value.getNextSDPostingDate().getYear(),
										nextPostingDate.getMonth().plus(1)),
								0, 0);
					}
				}
			} else {
				for (int i = 0; i < value.getSdCycleList().size(); i++) {
					int billGenDay = value.getSdCycleList().get(i).getBillGenerationDay().intValue();
					int postingDay = value.getSdCycleList().get(i).getPostingDay().intValue();
					int billGenDayComparator = billGenDay;
					if (nextBillingDay == lastDayOfBillingMonth) {
						billGenDayComparator = nextBillingDay;
					}
					if (nextBillingDay < billGenDayComparator) {
						try {
							nextBillingDate = LocalDateTime.of(value.getNextBillingDate().getYear(),
									value.getNextBillingDate().getMonth(), billGenDay, 0, 0);
						} catch (DateTimeException e) {
							nextBillingDate = LocalDateTime.of(value.getNextBillingDate().getYear(),
									value.getNextBillingDate().getMonth(),
									getLastDayOfMonth(value.getNextBillingDate().getYear(),
											value.getNextBillingDate().getMonth()),
									0, 0);
						}
						try {
							nextPostingDate = LocalDateTime.of(value.getNextSDPostingDate().getYear(),
									value.getNextSDPostingDate().getMonth(), postingDay, 0, 0);
						} catch (DateTimeException e) {
							nextPostingDate = LocalDateTime.of(value.getNextSDPostingDate().getYear(),
									value.getNextSDPostingDate().getMonth(),
									getLastDayOfMonth(value.getNextSDPostingDate().getYear(),
											value.getNextSDPostingDate().getMonth()),
									0, 0);
						}
						while (nextPostingDate.isBefore(nextBillingDate)) {
							try {
								nextPostingDate = LocalDateTime.of(postingYear, nextPostingDate.getMonth().plus(1),
										postingDay, 0, 0);
							} catch (DateTimeException e) {
								nextPostingDate = LocalDateTime.of(postingYear, nextPostingDate.getMonth().plus(1),
										getLastDayOfMonth(value.getNextSDPostingDate().getYear(),
												nextPostingDate.getMonth().plus(1)),
										0, 0);
							}
						}
						break;
					}
				}
			}
			value.setNextBillingDate(nextBillingDate);
			value.setNextSDPostingDate(nextPostingDate);
		});
	}

	public int getLastDayOfMonth(int year, Month month) {
		return YearMonth.of(year, month).atEndOfMonth().getDayOfMonth();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void getGenerateCustomerAPBill() throws CustomException {
		CustomerChargeWiseConsolidateData consolidateData = new CustomerChargeWiseConsolidateData();
		consolidateData.setBehalfOfAirline(1);
		LocalDate date = LocalDateTime.now().toLocalDate();
		consolidateData.setBillGenDate(date);
		List<CustomerChargeWiseConsolidateData> customerInfo = chargePostDAO
				.searchAPCustomersByBillGenDate(consolidateData);
		// SAVE BILL GEN INFO
		List<BillGenBO> billGenBOs = this.setBillGen(customerInfo, "AP");
		this.saveBillAPEntry(customerInfo, billGenBOs);
	}

	/*
	 * 
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.billing.chargepost.service.ChargePostService#
	 * getCustomersForRunDate()
	 * 
	 * 
	 * 
	 */
	public List<CustomerChargeWiseConsolidateData> getCustomersForRunDate() throws CustomException {
		CustomerChargeWiseConsolidateData consolidateData = new CustomerChargeWiseConsolidateData();
		LocalDate date = LocalDateTime.now().toLocalDate();
		consolidateData.setBillGenDate(date);
		return chargePostDAO.searchCustomersByBillGenDate(consolidateData);
	}

	/*
	 * 
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.billing.chargepost.service.ChargePostService#setBillGen(java.
	 * util.List)
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public List<BillGenBO> setBillGen(List<CustomerChargeWiseConsolidateData> data, String serviceType)
			throws CustomException {
		List<BillGenBO> billGenBOList = new ArrayList<>();
		Set<Long> customerIds = new HashSet<>();
		for (CustomerChargeWiseConsolidateData chargeWiseConsolidateData : data) {
			customerIds.add(chargeWiseConsolidateData.getCustomerId());
		}
		for (Long customerId : customerIds) {
			BillGenBO billGenBO = new BillGenBO();
			LocalDate date = LocalDateTime.now().toLocalDate();
			billGenBO.setBillGenDate(LocalDateTime.now());
			billGenBO.setConsolidationStatus(false);
			billGenBO.setCustomerId(customerId);
			billGenBO.setPostingStatus(false);
			// TODO Remove hardcoded user
			billGenBO.setCreatedUserCode("COSYS");
			billGenBO.setCreatedDateTime(LocalDateTime.now());
			billGenBO.setLastUpdatedDateTime(LocalDateTime.now());
			billGenBO.setLastUpdatedUserCode("COSYS");
			billGenBO.setBillGenDayNo(date.getDayOfMonth());
			billGenBO.setBillGenMonthNo(date.getMonthValue());
			billGenBO.setServiceType(serviceType);
			billGenBOList.add(billGenBO);
		}
		return chargePostDAO.setBillGenData(billGenBOList);
	}

	/*
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.billing.chargepost.service.ChargePostService#
	 * saveBillChargeConsidation(java.util.List, java.util.List)
	 * 
	 * 
	 * 
	 */
	public List<BillChargeConsolidationBO> saveBillChargeConsidation(List<CustomerChargeWiseConsolidateData> data,
			List<BillGenBO> billGenBOs) throws CustomException {
		List<BillChargeConsolidationBO> billChargeConsolidationBOs = new ArrayList<>();
		Map<Long, Long> billGenMap = new HashMap<>();
		if (!CollectionUtils.isEmpty(billGenBOs)) {
			for (BillGenBO billGenBo : billGenBOs) {
				billGenMap.put(billGenBo.getCustomerId(), billGenBo.getBillGenId());
			}
			for (CustomerChargeWiseConsolidateData consolidateData : data) {
				BillChargeConsolidationBO billChargeConsolidationBO = new BillChargeConsolidationBO();
				billChargeConsolidationBO.setBillGenId(billGenMap.get(consolidateData.getCustomerId()));
				billChargeConsolidationBO.setCustomerId(consolidateData.getCustomerId());
				billChargeConsolidationBO.setChargeCodeId(consolidateData.getChargeCodeId());
				billChargeConsolidationBO.setChargeTotalAmount(consolidateData.getTotalAmount());
				billChargeConsolidationBO.setDiscountAmount(new BigDecimal(0));
				billChargeConsolidationBO.setAmount(new BigDecimal(0));
				// TODO Remove hardcoded user
				billChargeConsolidationBO.setCreatedUserCode("COSYS");
				billChargeConsolidationBO.setCreatedDateTime(LocalDateTime.now());
				billChargeConsolidationBO.setLastUpdatedUserCode("COSYS");
				billChargeConsolidationBO.setLastUpdatedDateTime(LocalDateTime.now());
				billChargeConsolidationBO.setChargeGroupId(consolidateData.getChargeGroupId());
				billChargeConsolidationBOs.add(billChargeConsolidationBO);
			}
		}
		return chargePostDAO.setBillCharge(billChargeConsolidationBOs);
	}

	/*
	 * 
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.billing.chargepost.service.ChargePostService#
	 * getDiscountDetails()
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public List<CustomerDiscountBO> getDiscountDetailsByChargeCodeId(List<BillGenBO> billGenBOs)
			throws CustomException {
		if (!billGenBOs.isEmpty()) {
			return chargePostDAO.getDiscountDetails(billGenBOs);
		}
		return null;
	}

	/*
	 * 
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.billing.chargepost.service.ChargePostService#
	 * getApportionDetails()
	 * 
	 * 
	 * 
	 * 
	 */
	public List<BillEntryBO> getApportionDetails(List<BillGenBO> billGenBOs) throws CustomException {
		List<Long> list = new ArrayList<>();
		for (BillGenBO bo : billGenBOs) {
			list.add(bo.getCustomerId());
		}
		if (list.size() > 0) {
			List<BillEntryBO> billEntryBo = chargePostDAO.getApportionDetails(list);
			billEntryBo.forEach(a -> a.setChargeEntryIds(Arrays.asList(a.getChargeEntryId().split(","))));
			return billEntryBo;
		}
		return null;
	}

	public List<ChargeEntryBillPaidBO> saveBillAPEntry(List<CustomerChargeWiseConsolidateData> consolidateData,
			List<BillGenBO> billGenBOs) throws CustomException {
		List<BillEntryBO> billEntryBOs = new ArrayList<>();
		Map<Long, Long> billGenMap = new HashMap<>();
		if (!CollectionUtils.isEmpty(billGenBOs)) {
			for (BillGenBO billGenBo : billGenBOs) {
				billGenMap.put(billGenBo.getCustomerId(), billGenBo.getBillGenId());
			}
			for (CustomerChargeWiseConsolidateData entryBO : consolidateData) {
				BillEntryBO billEntryBos = new BillEntryBO();
				billEntryBos.setBillGenId(billGenMap.get(entryBO.getCustomerId()));
				/* billEntryBos.setChargeEntryId(entryBO.getChargeEntryId()); */
				billEntryBos.setChargeEntryIds(Arrays.asList(entryBO.getChargeEntryId().split(",")));
				billEntryBos.setBillPaidAmount(Arrays.asList(entryBO.getBillPaidAmount().split(",")));
				billEntryBos.setCustomerId(entryBO.getCustomerId());
				billEntryBos.setChargeCodeId(entryBO.getChargeCodeId());
				billEntryBos.setApportionedChargeTotalAmount(entryBO.getTotalAmount());
				// TODO Remove hardcoded user
				billEntryBos.setCreatedUserCode("COSYS");
				billEntryBos.setCreatedDateTime(LocalDateTime.now());
				billEntryBos.setBillingNo(0l);
				billEntryBos.setPostingStatus(false);
				billEntryBOs.add(billEntryBos);
			}
		}
		chargePostDAO.saveAPBillEntry(billEntryBOs);
		List<ChargeEntryBillPaidBO> billPaidList = new ArrayList<>();
		for (BillEntryBO entryObj : billEntryBOs) {
			int index = 0;
			for (String chargeEntryId : entryObj.getChargeEntryIds()) {
				ChargeEntryBillPaidBO billPaidBO = new ChargeEntryBillPaidBO();
				billPaidBO.setBillEntryId(entryObj.getBillEntryId());
				billPaidBO.setChargeEntryId(chargeEntryId);
				BigDecimal billAmount = new BigDecimal(entryObj.getBillPaidAmount().get(index));
				billPaidBO.setBillPaidAmount(billAmount);
				// TODO Remove hardcoded user
				billPaidBO.setCreatedUserCode("COSYS");
				billPaidList.add(billPaidBO);
				index++;
			}
		}
		return chargePostDAO.saveAPBillPaidChargeEntry(billPaidList);
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.billing.chargepost.service.ChargePostService#saveBillEntry(
	 * java.util.List, java.util.List)
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public List<ChargeEntryBillPaidBO> saveBillSDEntry(List<BillEntryBO> billEntryBo, List<BillGenBO> billGenBOs,
			List<CustomerBillingInfo> billCycles) throws CustomException {
		List<BillEntryBO> billEntryBOs = new ArrayList<>();
		Map<Long, Long> billGenMap = new HashMap<>();
		if (!CollectionUtils.isEmpty(billGenBOs)) {
			for (BillGenBO billGenBo : billGenBOs) {
				billGenMap.put(billGenBo.getCustomerId(), billGenBo.getBillGenId());
			}
			for (BillEntryBO entryBO : billEntryBo) {
				BillEntryBO billEntryBos = new BillEntryBO();
				billEntryBos.setBillGenId(billGenMap.get(entryBO.getCustomerId()));
				billEntryBos.setChargeEntryId(entryBO.getChargeEntryId());
				billEntryBos.setChargeEntryIds(entryBO.getChargeEntryIds());
				billEntryBos.setCustomerId(entryBO.getCustomerId());
				billEntryBos.setChargeCodeId(entryBO.getChargeCodeId());
				billEntryBos.setApportionedChargeTotalAmount(
						entryBO.getApportionedChargeTotalAmount().setScale(2, RoundingMode.HALF_UP));
				billEntryBos.setPricingType(entryBO.getPricingType());
				billEntryBos.setChargeSubGroupId(entryBO.getChargeSubGroupId());
				/*
				 * billEntryBos.setApportionedDiscountAmount(new BigDecimal(0));
				 * billEntryBos.setApportionedAmount(new BigDecimal(0));
				 */
				BillChargeConsolidationBO billChargeConsolidationBO = new BillChargeConsolidationBO();
				billChargeConsolidationBO.setCustomerId(entryBO.getCustomerId());
				billChargeConsolidationBO.setBillGenId(billGenMap.get(entryBO.getCustomerId()));
				billChargeConsolidationBO.setChargeCodeId(entryBO.getChargeCodeId());
				billChargeConsolidationBO.setChargeGroupId(entryBO.getGroupChargeCodeId());
				// billChargeConsolidationBO.setChargeGroupId(entryBO.getChargeSubGroupId().longValue());
				billChargeConsolidationBO = chargePostDAO
						.getBillingChargeConsolidationDetails(billChargeConsolidationBO);
				BigDecimal apportionedDiscount = BigDecimal.ZERO;
				if (billChargeConsolidationBO != null) {
					apportionedDiscount = billChargeConsolidationBO.getDiscountAmount()
							.multiply(entryBO.getApportionedChargeTotalAmount(), MathContext.DECIMAL32)
							.divide(billChargeConsolidationBO.getChargeTotalAmount(), MathContext.DECIMAL32)
							.setScale(2, RoundingMode.HALF_UP);
				}

				billEntryBos.setApportionedDiscountAmount(apportionedDiscount);
				billEntryBos.setApportionedAmount(entryBO.getApportionedChargeTotalAmount()
						.subtract(apportionedDiscount).setScale(2, RoundingMode.HALF_UP));
				billEntryBos.setApportioningPercentage(entryBO.getApportioningPercentage());
				billEntryBos.setFinSysChargeCode(entryBO.getFinSysChargeCode());
				// TODO Remove hardcoded user
				billEntryBos.setCreatedUserCode("COSYS");
				billEntryBos.setCreatedDateTime(LocalDateTime.now());
				billEntryBos.setApportionedQuantity(entryBO.getApportionedQuantity());
				billEntryBos.setApportionedQuantityWeight(entryBO.getApportionedQuantityWeight());
				billEntryBos.setApportionedQuantityNetWeight(entryBO.getApportionedQuantityNetWeight());
				billEntryBos.setBillingNo(0l);
				billEntryBos.setPostingStatus(false);
				billEntryBOs.add(billEntryBos);
			}
		}
		chargePostDAO.saveBillEntry(billEntryBOs);
		List<ChargeEntryBillPaidBO> billPaidList = new ArrayList<>();
		List<ChargeEntryBillPaidBO> billPaidBo = getChargeEntryBillEntryId();
		for (ChargeEntryBillPaidBO chargeEntry : billPaidBo) {
			for (BillEntryBO billChargeEntry : billEntryBOs) {
				for (String chargeId : billChargeEntry.getChargeEntryIds()) {
					if (chargeEntry.getChargeEntryId().equalsIgnoreCase(chargeId) && chargeEntry.getCount() == 0) {
						chargeEntry.setBillEntryId(billChargeEntry.getBillEntryId());
						chargeEntry.setCount(chargeEntry.getCount() + 1);
						billPaidList.add(chargeEntry);
					}
				}
			}
		}
		updateNextBillingPostingDates(billCycles);
		/*
		 * for (ChargeEntryBillPaidBO a : billPaidList) { billPaidBo.add(a); }
		 * billPaidBo = billPaidBo.stream().filter(a -> a.getBillEntryId() !=
		 * null).collect(Collectors.toList());
		 */
		return chargePostDAO.saveChargeEntryBillEntry(billPaidList);
	}

	/**
	 * @param billCycles
	 * @throws CustomException
	 */
	private void updateNextBillingPostingDates(List<CustomerBillingInfo> billCycles) throws CustomException {
		chargePostDAO.updateNextBillingPostingDate(billCycles);
	}

	/*
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.billing.chargepost.service.ChargePostService#
	 * getDiscountDetailsByGroupId()
	 * 
	 * 
	 * 
	 */

	public List<CustomerDiscountBO> getDiscountDetailsByGroupId() throws CustomException {
		return chargePostDAO.getDiscountDetailsByGroupId();
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.billing.chargepost.service.ChargePostService#
	 * saveDiscountAmount(java.util.List)
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	public List<Integer> saveDiscountAmountByChargeCodeId(List<CustomerDiscountBO> customerDiscountBOs)
			throws CustomException {
		List<BillChargeConsolidationBO> billChargeConsolidationBOList = new ArrayList<>();
		Map<String, BigDecimal> lastThresholdMap = new HashMap<>();
		Map<String, Map<String, ThresholdRange>> thresholdMap = new HashMap<>();
		//
		for (CustomerDiscountBO discountBO : customerDiscountBOs) {
			String discountAppliedKey = discountBO.getCustomerId() + "-" + discountBO.getBillingChargeCodeId();
			Map<String, ThresholdRange> customerThresholdMap = thresholdMap.get(discountAppliedKey);
			BigDecimal lastThreshold = lastThresholdMap.get(discountAppliedKey);
			//
			if (customerThresholdMap == null) {
				customerThresholdMap = new HashMap<>();
				thresholdMap.put(discountAppliedKey, customerThresholdMap);
			}
			if (discountBO.getThreshhold() != null) {
				if (lastThreshold != null && lastThreshold.compareTo(BigDecimal.ZERO) > 0) {
					ThresholdRange firstRange = new ThresholdRange();
					firstRange.setFromThreshold(lastThreshold);
					firstRange.setTillThreshold(discountBO.getThreshhold());
					firstRange.setDiscountType(DiscountType.MULTIPLE.toString());
					customerThresholdMap.put(String.valueOf((lastThreshold)), firstRange);
					ThresholdRange secondRange = new ThresholdRange();
					secondRange.setFromThreshold(discountBO.getThreshhold());
					secondRange.setTillThreshold(BigDecimal.valueOf(0.0));
					secondRange.setDiscountType(DiscountType.MULTIPLE.toString());

					customerThresholdMap.put(String.valueOf(discountBO.getThreshhold()), secondRange);
				} else {
					ThresholdRange thirdRange = new ThresholdRange();
					thirdRange.setFromThreshold(discountBO.getThreshhold());
					thirdRange.setTillThreshold(BigDecimal.valueOf(0.0));
					thirdRange.setDiscountType(DiscountType.SINGLE.toString());
					customerThresholdMap.put(String.valueOf(discountBO.getThreshhold()), thirdRange);
				}
				// Update at Last
				lastThresholdMap.put(discountAppliedKey, discountBO.getThreshhold());
			}
		}
		//
		Map<String, BillChargeConsolidationBO> consolidatedDiscountMap = new LinkedHashMap<>();
		//
		for (CustomerDiscountBO discountBO : customerDiscountBOs) {
			BigDecimal totalAmount = discountBO.getChargeTotalAmount() != null ? discountBO.getChargeTotalAmount()
					: BigDecimal.valueOf(0);
			BigDecimal flatAmount = discountBO.getFlatAmount() != null ? discountBO.getFlatAmount()
					: BigDecimal.valueOf(0);
			BigDecimal percentage = discountBO.getPercentage() != null ? discountBO.getPercentage()
					: BigDecimal.valueOf(0);
			BigDecimal threshold = discountBO.getThreshhold() != null ? discountBO.getThreshhold()
					: BigDecimal.valueOf(0);
			BigDecimal discount = BigDecimal.valueOf(0.0);
			BigDecimal minAmount = discountBO.getMinAmount() != null ? discountBO.getMinAmount()
					: BigDecimal.valueOf(0);
			String discountAppliedKey = discountBO.getCustomerId() + "-" + discountBO.getBillingChargeCodeId();
			performDiscountAndPercentage(thresholdMap, consolidatedDiscountMap, discountBO, totalAmount, flatAmount,
					percentage, threshold, discount, minAmount, discountAppliedKey);
		}

		Set<Entry<String, BillChargeConsolidationBO>> discountSet = consolidatedDiscountMap.entrySet();
		Iterator<Entry<String, BillChargeConsolidationBO>> discountIter = discountSet.iterator();
		//
		while (discountIter.hasNext()) {
			BillChargeConsolidationBO billChargeConsolidationBO = new BillChargeConsolidationBO();
			Entry<String, BillChargeConsolidationBO> discountEntry = discountIter.next();
			BillChargeConsolidationBO discount = discountEntry.getValue();
			//
			billChargeConsolidationBO.setPercentage(discount.getPercentage());
			billChargeConsolidationBO.setCustomerId(discount.getCustomerId());
			billChargeConsolidationBO.setChargeCodeId(discount.getChargeCodeId());
			billChargeConsolidationBO.setDiscountAmount(discount.getDiscountAmount());
			billChargeConsolidationBO.setAmount(discount.getChargeTotalAmount().subtract(discount.getDiscountAmount()));
			billChargeConsolidationBOList.add(billChargeConsolidationBO);
		}
		return chargePostDAO.setBillChargeConsolidation(billChargeConsolidationBOList);
	}

	public void performDiscountAndPercentage(final Map<String, Map<String, ThresholdRange>> thresholdMap,
			final Map<String, BillChargeConsolidationBO> consolidatedDiscountMap, final CustomerDiscountBO discountBO,
			BigDecimal totalAmount, BigDecimal flatAmount, BigDecimal percentage, BigDecimal threshold,
			BigDecimal discount, BigDecimal minAmount, String discountAppliedKey) {
		Map<String, ThresholdRange> customerThresholdMap = thresholdMap.get(discountAppliedKey);

		// If Threshold is > 0, Apply Discount
		if (totalAmount.compareTo(minAmount) > 0 && threshold.compareTo(BigDecimal.ZERO) > 0
				&& totalAmount.compareTo(threshold) > 0) {
			ThresholdRange thresholdRange = customerThresholdMap.get(String.valueOf(threshold));
			// If FlatAmount is > 0
			if (flatAmount.compareTo(BigDecimal.ZERO) > 0
					&& DiscountType.SINGLE.toString().equalsIgnoreCase(thresholdRange.getDiscountType())) {
				discount = flatAmount;
			} else if (flatAmount.compareTo(BigDecimal.ZERO) > 0
					&& DiscountType.MULTIPLE.toString().equalsIgnoreCase(thresholdRange.getDiscountType())) {
				discount = totalAmount.compareTo(threshold) > 0 ? flatAmount : BigDecimal.ZERO;

			} /*
				 * slice tier discount logic else if (percentage.compareTo(BigDecimal.ZERO) > 0)
				 * { if (thresholdRange.getTillThreshold().compareTo(BigDecimal.ZERO) > 0) {
				 * slice tier discount logic discount =
				 * ((thresholdRange.getTillThreshold().subtract(thresholdRange.getFromThreshold(
				 * )).divide(BigDecimal.valueOf(100))).multiply(percentage));
				 * 
				 * 
				 * 
				 * } else { discount =
				 * ((totalAmount.subtract(threshold).divide(BigDecimal.valueOf(100))).multiply(
				 * percentage)); } }
				 */
			else if (percentage.compareTo(BigDecimal.ZERO) > 0
					&& thresholdRange.getDiscountType().equalsIgnoreCase("Multiple")) {
				discount = totalAmount.compareTo(threshold) > 0
						? totalAmount.multiply(percentage).divide(BigDecimal.valueOf(100))
						: BigDecimal.ZERO;
			} else if (percentage.compareTo(BigDecimal.ZERO) > 0
					&& thresholdRange.getDiscountType().equalsIgnoreCase("Single")) {
				discount = ((totalAmount.subtract(threshold).divide(BigDecimal.valueOf(100))).multiply(percentage));
			}
		} else if (minAmount.compareTo(BigDecimal.ZERO) > 0) {
			discountBO.setChargeTotalAmount(totalAmount);
			percentage = minAmount.subtract(totalAmount).multiply(BigDecimal.valueOf(100)).divide(totalAmount, 2,
					RoundingMode.HALF_UP);
			discount = (totalAmount.multiply(percentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
			if (discount.compareTo(BigDecimal.ZERO) > 0) {
				discount = BigDecimal.valueOf(0.0).subtract(discount);
			} else {
				discount = BigDecimal.valueOf(0.0);
			}

		}
		if (totalAmount.subtract(discount).compareTo(minAmount) < 0) {
			percentage = totalAmount.subtract(minAmount).multiply(BigDecimal.valueOf(100)).divide(totalAmount, 2,
					RoundingMode.HALF_UP);
			discount = percentage.multiply(totalAmount).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
		}
		if (consolidatedDiscountMap.containsKey(discountAppliedKey) && totalAmount.compareTo(threshold) > 0) {
			consolidatedDiscountMap.get(discountAppliedKey).setDiscountAmount(discount);
		} else {
			BillChargeConsolidationBO consolidatedDiscount = new BillChargeConsolidationBO();
			consolidatedDiscount.setCustomerId(discountBO.getCustomerId());
			consolidatedDiscount.setChargeCodeId(discountBO.getBillingChargeCodeId());
			consolidatedDiscount
					.setChargeTotalAmount(discountBO.getChargeTotalAmount().setScale(2, RoundingMode.HALF_UP));
			consolidatedDiscount.setDiscountAmount(discount.setScale(2, RoundingMode.HALF_UP));
			// Update Consolidate Map
			consolidatedDiscountMap.put(discountAppliedKey, consolidatedDiscount);
		}

		/*
		 * // If chargeConsolidation == null if (consolidatedDiscount == null) {
		 * consolidatedDiscount = new BillChargeConsolidationBO(); // Set Default
		 * consolidatedDiscount.setCustomerId(discountBO.getCustomerId());
		 * consolidatedDiscount.setChargeCodeId(discountBO.getBillingChargeCodeId());
		 * consolidatedDiscount.setChargeTotalAmount(discountBO.getChargeTotalAmount());
		 * // consolidatedDiscount.setDiscountAmount(BigDecimal.valueOf(0.0));
		 * System.out.println("1==="+discountAppliedKey+"===distount===="+discount);
		 * consolidatedDiscount.setDiscountAmount(discount); // Update Consolidate Map
		 * consolidatedDiscountMap.put(discountAppliedKey, consolidatedDiscount); } else
		 * { // slice tier discount // old code //
		 * consolidatedDiscount.setDiscountAmount(consolidatedDiscount.getDiscountAmount
		 * ().add(discount));
		 * System.out.println("2==="+discountAppliedKey+"===distount===="+discount);
		 * consolidatedDiscount.setDiscountAmount(discount); }
		 */
	}

	/**
	 * Saves discounted amount by group ID.
	 * 
	 * @param customerDiscountBOsByGroupId,
	 *            not null.
	 * @return
	 * @throws CustomException
	 */
	public List<Integer> saveDiscountAmountByGroupId(List<CustomerDiscountBO> customerDiscountBOsByGroupId)
			throws CustomException {
		List<BillChargeConsolidationBO> billChargeConsolidationBOList = new ArrayList<>();

		Map<String, BigDecimal> lastThresholdMap = new HashMap<>();
		Map<String, Map<String, ThresholdRange>> thresholdMap = new HashMap<>();
		//
		for (CustomerDiscountBO discountBO : customerDiscountBOsByGroupId) {
			String discountAppliedKey = discountBO.getCustomerId() + "-" + discountBO.getChargeGroupId();
			Map<String, ThresholdRange> customerThresholdMap = thresholdMap.get(discountAppliedKey);
			BigDecimal lastThreshold = lastThresholdMap.get(discountAppliedKey);
			//
			if (customerThresholdMap == null) {
				customerThresholdMap = new HashMap<>();
				thresholdMap.put(discountAppliedKey, customerThresholdMap);
			}
			if (discountBO.getThreshhold() != null) {
				if (lastThreshold != null && lastThreshold.compareTo(BigDecimal.ZERO) > 0) {
					ThresholdRange firstRange = new ThresholdRange();
					firstRange.setDiscountType(DiscountType.MULTIPLE.toString());
					firstRange.setFromThreshold(lastThreshold);
					firstRange.setTillThreshold(discountBO.getThreshhold());
					customerThresholdMap.put(String.valueOf((lastThreshold)), firstRange);
					ThresholdRange secondRange = new ThresholdRange();
					secondRange.setDiscountType(DiscountType.MULTIPLE.toString());
					secondRange.setFromThreshold(discountBO.getThreshhold());
					secondRange.setTillThreshold(BigDecimal.valueOf(0.0));

					customerThresholdMap.put(String.valueOf(discountBO.getThreshhold()), secondRange);
				} else {
					ThresholdRange thirdRange = new ThresholdRange();
					thirdRange.setFromThreshold(discountBO.getThreshhold());
					thirdRange.setTillThreshold(BigDecimal.valueOf(0.0));
					thirdRange.setDiscountType(DiscountType.SINGLE.toString());
					customerThresholdMap.put(String.valueOf(discountBO.getThreshhold()), thirdRange);
				}
				// Update at Last
				lastThresholdMap.put(discountAppliedKey, discountBO.getThreshhold());
			}
		}
		//
		Map<String, BillChargeConsolidationBO> consolidatedDiscountMap = new LinkedHashMap<>();
		//
		for (CustomerDiscountBO discountBO : customerDiscountBOsByGroupId) {
			BigDecimal totalAmount = discountBO.getChargeTotalAmount() != null ? discountBO.getChargeTotalAmount()
					: BigDecimal.valueOf(0);
			BigDecimal flatAmount = discountBO.getFlatAmount() != null ? discountBO.getFlatAmount()
					: BigDecimal.valueOf(0);
			BigDecimal percentage = discountBO.getPercentage() != null ? discountBO.getPercentage()
					: BigDecimal.valueOf(0);
			BigDecimal threshold = discountBO.getThreshhold() != null ? discountBO.getThreshhold()
					: BigDecimal.valueOf(0);
			BigDecimal discount = BigDecimal.valueOf(0.0);
			BigDecimal minAmount = discountBO.getMinAmount() != null ? discountBO.getMinAmount()
					: BigDecimal.valueOf(0);
			String discountAppliedKey = discountBO.getCustomerId() + "-" + discountBO.getChargeGroupId();
			performDiscountAndPercentage(thresholdMap, consolidatedDiscountMap, discountBO, totalAmount, flatAmount,
					percentage, threshold, discount, minAmount, discountAppliedKey);
		}
		Set<Entry<String, BillChargeConsolidationBO>> discountSet = consolidatedDiscountMap.entrySet();
		Iterator<Entry<String, BillChargeConsolidationBO>> discountIter = discountSet.iterator();
		//
		while (discountIter.hasNext()) {
			BillChargeConsolidationBO billChargeConsolidationBO = new BillChargeConsolidationBO();
			Entry<String, BillChargeConsolidationBO> discountEntry = discountIter.next();
			BillChargeConsolidationBO discount = discountEntry.getValue();
			//
			billChargeConsolidationBO.setCustomerId(discount.getCustomerId());
			billChargeConsolidationBO.setChargeGroupId(discount.getChargeGroupId());
			billChargeConsolidationBO.setDiscountAmount(discount.getDiscountAmount());
			billChargeConsolidationBO.setAmount(discount.getChargeTotalAmount().subtract(discount.getDiscountAmount()));
			billChargeConsolidationBOList.add(billChargeConsolidationBO);
		}
		return chargePostDAO.setBillChargeConsolidationByGroupId(billChargeConsolidationBOList);
	}

	public List<ChargeEntryBillPaidBO> getChargeEntryBillEntryId() throws CustomException {
		return chargePostDAO.getChargeEntryBillEntryId();
	}
}