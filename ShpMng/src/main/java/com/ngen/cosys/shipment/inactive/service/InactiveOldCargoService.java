package com.ngen.cosys.shipment.inactive.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.inactive.model.InactiveSearch;
import com.ngen.cosys.shipment.inactive.model.InactiveSearchList;

@Service
public interface InactiveOldCargoService {

	List<InactiveSearchList> serchInactiveList(InactiveSearch searchParams) throws CustomException;

	String moveToFreightOut(InactiveSearch searchParams) throws CustomException;
	
	InactiveSearch defalutingDays(InactiveSearch searchParams) throws CustomException;


}
