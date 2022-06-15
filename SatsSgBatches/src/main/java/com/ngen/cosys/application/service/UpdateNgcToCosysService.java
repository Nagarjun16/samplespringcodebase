/**
 * This is a service interface for scheduling delivery requests for an shipment
 * based on EDelivery Request
 */
package com.ngen.cosys.application.service;

import com.ngen.cosys.framework.exception.CustomException;

public interface UpdateNgcToCosysService {
  void getPreLodgeData() throws CustomException;

}