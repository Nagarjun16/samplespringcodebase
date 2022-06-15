package com.ngen.cosys.billing.chargepost.dao;

import java.util.List;

import com.ngen.cosys.billing.chargepost.model.BillChargeConsolidationBO;
import com.ngen.cosys.billing.chargepost.model.BillEntryBO;
import com.ngen.cosys.billing.chargepost.model.BillGenBO;
import com.ngen.cosys.billing.chargepost.model.ChargeEntryBillPaidBO;
import com.ngen.cosys.billing.chargepost.model.CustomerBillingInfo;
import com.ngen.cosys.billing.chargepost.model.CustomerChargeWiseConsolidateData;
import com.ngen.cosys.billing.chargepost.model.CustomerDiscountBO;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * This ChargePostDAO interface will take care of all the DAO requests related
 * to ChargePost
 * 
 * @author NIIT Technologies Ltd
 *
 */

public interface ChargePostDAO {

   /**
    * @param consolidateData
    * @return
    * @throws CustomException
    */
   List<CustomerChargeWiseConsolidateData> searchCustomersByBillGenDate(
         CustomerChargeWiseConsolidateData consolidateData) throws CustomException;
   
   /**
    * @param consolidateData
    * @return
    * @throws CustomException
    */
   List<CustomerChargeWiseConsolidateData> searchCustomersWithoutCharges(
		   List<CustomerChargeWiseConsolidateData> consolidateData) throws CustomException;

   /**
    * @param consolidateData
    * @return
    * @throws CustomException
    */
   List<CustomerChargeWiseConsolidateData> searchAPCustomersByBillGenDate(
         CustomerChargeWiseConsolidateData consolidateData) throws CustomException;

   /**
    * @param billGenBOList
    * @return
    * @throws CustomException
    */
   List<BillGenBO> setBillGenData(List<BillGenBO> billGenBOList) throws CustomException;

   /**
    * @param billChargeConsolidationBOs
    * @return
    * @throws CustomException
    */
   List<BillChargeConsolidationBO> setBillCharge(List<BillChargeConsolidationBO> billChargeConsolidationBOs)
         throws CustomException;

   /**
    * @param
    * @return
    * @throws CustomException
    */
   List<CustomerDiscountBO> getDiscountDetails(List<BillGenBO> billGenBOs) throws CustomException;

   /**
    * @param billChargeConsolidationBOs
    * @return
    * @throws CustomException
    */
   List<Integer> setBillChargeConsolidation(List<BillChargeConsolidationBO> billChargeConsolidationBOs)
         throws CustomException;

   /**
    * @param
    * @return
    * @throws CustomException
    */
   List<CustomerDiscountBO> getDiscountDetailsByGroupId() throws CustomException;

   /**
    * @param billChargeConsolidationBOList
    * @return
    * @throws CustomException
    */
   List<Integer> setBillChargeConsolidationByGroupId(List<BillChargeConsolidationBO> billChargeConsolidationBOList)
         throws CustomException;

   /**
    * @param
    * @return
    * @throws CustomException
    */
   List<BillEntryBO> getApportionDetails(List<Long> list) throws CustomException;

   /**
    * @param billEntryBOs
    * @return
    * @throws CustomException
    */
   List<BillEntryBO> saveBillEntry(List<BillEntryBO> billEntryBOs) throws CustomException;

   List<BillEntryBO> saveAPBillEntry(List<BillEntryBO> billEntryBOs) throws CustomException;

   BillChargeConsolidationBO getBillingChargeConsolidationDetails(BillChargeConsolidationBO billChargeConsolidationBO)
         throws CustomException;

   List<ChargeEntryBillPaidBO> getChargeEntryBillEntryId() throws CustomException;

   List<ChargeEntryBillPaidBO> saveChargeEntryBillEntry(List<ChargeEntryBillPaidBO> listChargeEntryBillPaidBO)
         throws CustomException;

   List<ChargeEntryBillPaidBO> saveAPBillPaidChargeEntry(List<ChargeEntryBillPaidBO> listChargeEntryBillPaidBO)
         throws CustomException;

   List<CustomerBillingInfo> fetchCustomerCycleInfo(List<CustomerChargeWiseConsolidateData> customerInfo)
         throws CustomException;

   void updateNextBillingPostingDate(List<CustomerBillingInfo> billCycles) throws CustomException;
}