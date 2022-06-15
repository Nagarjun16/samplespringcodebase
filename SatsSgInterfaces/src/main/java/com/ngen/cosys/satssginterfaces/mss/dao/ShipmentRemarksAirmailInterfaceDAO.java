package com.ngen.cosys.satssginterfaces.mss.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentRemarksAirmailInterfaceModel;

public interface ShipmentRemarksAirmailInterfaceDAO {

	ShipmentRemarksAirmailInterfaceModel get(ShipmentRemarksAirmailInterfaceModel shipmentRemarksModel) throws CustomException;

	void create(ShipmentRemarksAirmailInterfaceModel shipmentRemarksModel) throws CustomException;

}