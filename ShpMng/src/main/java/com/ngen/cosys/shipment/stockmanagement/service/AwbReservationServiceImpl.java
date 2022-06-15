package com.ngen.cosys.shipment.stockmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.stockmanagement.dao.AwbReservationDAO;
import com.ngen.cosys.shipment.stockmanagement.model.AwbReservation;
import com.ngen.cosys.shipment.stockmanagement.model.AwbReservationSearch;

@Service
@Transactional
public class AwbReservationServiceImpl implements AwbReservationService {

	@Autowired
	private AwbReservationDAO awbreservationDaoImpl;

	@Override
	public AwbReservationSearch searchNextAwbNumber(AwbReservationSearch search) throws CustomException {
		return awbreservationDaoImpl.searchNextAwbNumber(search);
	}

	@Override
	public AwbReservation save(AwbReservation search) throws CustomException {
		return awbreservationDaoImpl.save(search);
	}

	@Override
	public List<AwbReservation> fecthAwbReservationDetails(AwbReservationSearch search) throws CustomException {
		return awbreservationDaoImpl.fetchAwbReservationDetails(search);
	}

}