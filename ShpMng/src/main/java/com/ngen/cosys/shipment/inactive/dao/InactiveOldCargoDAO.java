package com.ngen.cosys.shipment.inactive.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.inactive.model.InactiveSearch;
import com.ngen.cosys.shipment.inactive.model.InactiveSearchList;

@Repository
public interface InactiveOldCargoDAO {
	List<InactiveSearchList> getInactiveList(InactiveSearch searchParams) throws CustomException;

	String moveToFreightOut(InactiveSearch searchParams) throws CustomException;
	
	InactiveSearch getDefaultCreationDays(InactiveSearch searchParams) throws CustomException;

}
