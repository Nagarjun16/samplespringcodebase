package com.ngen.cosys.billing.chargepost.service;

import com.ngen.cosys.framework.exception.CustomException;

/**
 * This interface takes care of the ChargePost services.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface ChargePostService {

   /**
    * Generates Customer SD Bill.
    * 
    * @throws CustomException
    */
   void getGenerateCustomerSDBill() throws CustomException;

   /**
    * Generates Customer AP Bill.
    * 
    * @throws CustomException
    */
   void getGenerateCustomerAPBill() throws CustomException;
}