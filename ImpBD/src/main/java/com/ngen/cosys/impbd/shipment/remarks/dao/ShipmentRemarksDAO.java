package com.ngen.cosys.impbd.shipment.remarks.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.remarks.model.ShipmentRemarksModel;

public interface ShipmentRemarksDAO {

	ShipmentRemarksModel get(ShipmentRemarksModel shipmentRemarksModel) throws CustomException;

	void create(ShipmentRemarksModel shipmentRemarksModel) throws CustomException;

}