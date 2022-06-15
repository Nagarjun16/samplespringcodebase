package com.ngen.cosys.billing.chargepost.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.billing.chargepost.model.BillChargeConsolidationBO;
import com.ngen.cosys.billing.chargepost.model.BillEntryBO;
import com.ngen.cosys.billing.chargepost.model.BillGenBO;
import com.ngen.cosys.billing.chargepost.model.ChargeEntryBillPaidBO;
import com.ngen.cosys.billing.chargepost.model.CustomerBillingInfo;
import com.ngen.cosys.billing.chargepost.model.CustomerChargeWiseConsolidateData;
import com.ngen.cosys.billing.chargepost.model.CustomerDiscountBO;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;

/**
 * 
 * This ChargePostDAOImpl class will take care of all the DAO implementation
 * related to ChargePost
 * 
 * @author NIIT Technologies Ltd
 *
 */

@Repository
public class ChargePostDAOImpl extends BaseDAO implements ChargePostDAO {

	@Autowired
	@Qualifier(BeanFactoryConstants.SESSION_TEMPLATE)
	private SqlSession sqlSessionCode;

	@Autowired
	@Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
	private SqlSession sqlSessionROI;

	/*
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.billing.chargepost.dao.ChargePostDAO#
	 * searchCustomersByBillGenDate(com.ngen.cosys.billing.chargepost.model.
	 * CustomerChargeWiseConsolidateData)
	 * 
	 * 
	 * 
	 * 
	 */
	public List<CustomerChargeWiseConsolidateData> searchCustomersByBillGenDate(
			CustomerChargeWiseConsolidateData consolidateData) throws CustomException {
		return fetchList("fetchSDCustomerId", consolidateData, sqlSessionROI);
	}

	@Override
	public List<CustomerChargeWiseConsolidateData> searchAPCustomersByBillGenDate(
			CustomerChargeWiseConsolidateData consolidateData) throws CustomException {
		return fetchList("fetchAPCustomerId", consolidateData, sqlSessionROI);
	}

	/*
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.billing.chargepost.dao.ChargePostDAO#setBillGenData(java.util.
	 * List)
	 * 
	 * 
	 * 
	 */
	@Override
	public List<BillGenBO> setBillGenData(List<BillGenBO> billGenBOList) throws CustomException {
		List<BillGenBO> billGenBo = new ArrayList<>();
		for (BillGenBO billGenBO : billGenBOList) {
			Integer resultCount = fetchObject("fetchCustomersRecord", billGenBO, sqlSessionROI);
			if (resultCount == 0) {
				insertData("SaveBillGen", billGenBO, sqlSessionCode);
				billGenBo.add(billGenBO);
			}
		}
		return billGenBo;
	}

	/*
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.billing.chargepost.dao.ChargePostDAO#setBillCharge(java.util.
	 * List)
	 * 
	 * 
	 * 
	 */
	@Override
	public List<BillChargeConsolidationBO> setBillCharge(List<BillChargeConsolidationBO> billChargeConsolidationBOs)
			throws CustomException {
		ArrayList<BillChargeConsolidationBO> insertList = (ArrayList<BillChargeConsolidationBO>) insertList(
				"SaveBillChargeConsolidation", billChargeConsolidationBOs, sqlSessionCode);
		if (!CollectionUtils.isEmpty(insertList)) {
			updateData("updatePostingStatus", new Object(), sqlSessionCode);
		}
		return insertList;
	}

	/*
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.billing.chargepost.dao.ChargePostDAO#getDiscountDetails()
	 * 
	 * 
	 * 
	 */
	@Override
	public List<CustomerDiscountBO> getDiscountDetails(List<BillGenBO> billGenBOs) throws CustomException {
		return fetchList("fetchDiscountCustomerDetailsByChargecodeId", billGenBOs, sqlSessionCode);
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.billing.chargepost.dao.ChargePostDAO#
	 * setBillChargeConsolidation(java.util.List)
	 * 
	 * 
	 */
	@Override
	public List<Integer> setBillChargeConsolidation(List<BillChargeConsolidationBO> billChargeConsolidationBOs)
			throws CustomException {
		List<Integer> returnList = new ArrayList<>();
		for (BillChargeConsolidationBO billChargeConsolidationBO : billChargeConsolidationBOs) {
			returnList.add(
					updateData("setDiscountAmountBillChargeConsolidation", billChargeConsolidationBO, sqlSessionCode));
		}
		return returnList;
	}

	/*
	 * 
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.billing.chargepost.dao.ChargePostDAO#getApportionDetails()
	 * 
	 * 
	 * 
	 * 
	 */
	@Override
	public List<BillEntryBO> getApportionDetails(List<Long> customerIds) throws CustomException {
		return fetchList("fetchApportionRecords", customerIds, sqlSessionCode);
	}

	/*
	 * 
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.billing.chargepost.dao.ChargePostDAO#saveBillEntry(java.util.
	 * List)
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	@Override
	public List<BillEntryBO> saveBillEntry(List<BillEntryBO> billEntryBOs) throws CustomException {
		List<BillEntryBO> billEntryList = (List<BillEntryBO>) insertList("SaveBillEntry", billEntryBOs, sqlSessionCode);
		updateData("updateBillEntryId", new Object(), sqlSessionCode);
		return billEntryList;
	}

	@Override
	public List<BillEntryBO> saveAPBillEntry(List<BillEntryBO> billEntryBOs) throws CustomException {
		List<BillEntryBO> billEntryList = (List<BillEntryBO>) insertList("SaveAPBillEntry", billEntryBOs,
				sqlSessionCode);
		return billEntryList;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.billing.chargepost.dao.ChargePostDAO#
	 * getDiscountDetailsByGroupId()
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	@Override
	public List<CustomerDiscountBO> getDiscountDetailsByGroupId() throws CustomException {
		return fetchList("fetchDiscountDetailsBygroupId", null, sqlSessionCode);
	}

	/*
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.billing.chargepost.dao.ChargePostDAO#
	 * setBillChargeConsolidationByGroupId(java.util.List)
	 * 
	 * 
	 * 
	 * 
	 */
	@Override
	public List<Integer> setBillChargeConsolidationByGroupId(
			List<BillChargeConsolidationBO> billChargeConsolidationBOList) throws CustomException {
		List<Integer> returnList = new ArrayList<>();
		for (BillChargeConsolidationBO billChargeConsolidationBO : billChargeConsolidationBOList) {
			returnList.add(updateData("setDiscountAmountBillChargeConsolidationByGroupId", billChargeConsolidationBO,
					sqlSessionCode));
		}
		return returnList;
	}

	@Override
	public BillChargeConsolidationBO getBillingChargeConsolidationDetails(
			BillChargeConsolidationBO billChargeConsolidationBO) throws CustomException {
		return super.fetchObject("fetchBillChargeConsolidationDetails", billChargeConsolidationBO, sqlSessionCode);
	}

	/*
	 * 
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.billing.chargepost.dao.ChargePostDAO#getChargeEntryBillEntryId
	 * ()
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	@Override
	public List<ChargeEntryBillPaidBO> getChargeEntryBillEntryId() throws CustomException {
		return fetchList("fetchChargeEntryIdBillEntryId", null, sqlSessionCode);
	}

	/*
	 * 
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.billing.chargepost.dao.ChargePostDAO#saveChargeEntryBillEntry(
	 * java.util.List)
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	@Override
	public List<ChargeEntryBillPaidBO> saveChargeEntryBillEntry(List<ChargeEntryBillPaidBO> listChargeEntryBillPaidBO)
			throws CustomException {
		List<ChargeEntryBillPaidBO> billEntryBillPaidList = new ArrayList<>();
		insertData("SaveChargeEntryBillPaid", listChargeEntryBillPaidBO, sqlSessionCode);

		for (ChargeEntryBillPaidBO chargeEntryBillPaidUpdate : listChargeEntryBillPaidBO) {

			updateData("updateBillPaidAmount", chargeEntryBillPaidUpdate, sqlSessionCode);

		}

		return billEntryBillPaidList;
	}

	@Override
	public List<ChargeEntryBillPaidBO> saveAPBillPaidChargeEntry(List<ChargeEntryBillPaidBO> listChargeEntryBillPaidBO)
			throws CustomException {
		List<ChargeEntryBillPaidBO> billEntryBillPaidList = new ArrayList<>();
		insertData("SaveAPChargeEntryBillPaid", listChargeEntryBillPaidBO, sqlSessionCode);
		for (ChargeEntryBillPaidBO chargeEntryBillPaidUpdate : listChargeEntryBillPaidBO) {
			updateData("updateBillPaidAmount", chargeEntryBillPaidUpdate, sqlSessionCode);
		}
		return billEntryBillPaidList;
	}

	@Override
	public List<CustomerBillingInfo> fetchCustomerCycleInfo(List<CustomerChargeWiseConsolidateData> customerInfo)
			throws CustomException {
		List<Long> customerList = new ArrayList<>();
		customerInfo.forEach(value -> customerList.add(value.getCustomerId()));
		return fetchList("fetchCycleInfo", customerList, sqlSessionCode);
	}

	@Override
	public void updateNextBillingPostingDate(List<CustomerBillingInfo> billCycles) throws CustomException {
		updateData("updateCycleDates", billCycles, sqlSessionCode);

	}

	@Override
	public List<CustomerChargeWiseConsolidateData> searchCustomersWithoutCharges(
			List<CustomerChargeWiseConsolidateData> consolidateData) throws CustomException {
		List<Long> customerList = new ArrayList<>();
		consolidateData.forEach(value -> customerList.add(value.getCustomerId()));
		return fetchList("fetchSDCustomerWithoutCharges", customerList, sqlSessionROI);
	}
}