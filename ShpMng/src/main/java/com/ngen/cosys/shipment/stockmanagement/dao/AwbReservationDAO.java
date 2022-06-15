package com.ngen.cosys.shipment.stockmanagement.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.stockmanagement.model.AwbReservation;
import com.ngen.cosys.shipment.stockmanagement.model.AwbReservationSearch;

public interface AwbReservationDAO {

	AwbReservationSearch searchNextAwbNumber(AwbReservationSearch search) throws CustomException;

	AwbReservation save(AwbReservation request) throws CustomException;

	List<AwbReservation> fetchAwbReservationDetails(AwbReservationSearch search) throws CustomException;

}