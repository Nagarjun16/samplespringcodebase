package com.ngen.cosys.shipment.stockmanagement.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.stockmanagement.model.AwbReservation;
import com.ngen.cosys.shipment.stockmanagement.model.AwbReservationSearch;

public interface AwbReservationService {

	AwbReservationSearch searchNextAwbNumber(AwbReservationSearch search) throws CustomException;

	AwbReservation save(AwbReservation request) throws CustomException;

	List<AwbReservation> fecthAwbReservationDetails(AwbReservationSearch search) throws CustomException;

}