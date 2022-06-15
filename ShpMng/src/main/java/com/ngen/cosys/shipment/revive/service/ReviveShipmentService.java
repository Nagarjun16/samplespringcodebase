package com.ngen.cosys.shipment.revive.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.revive.model.ReviveShipmentModel;
import com.ngen.cosys.shipment.revive.model.ReviveShipmentSummary;

public interface ReviveShipmentService {

   public List<ReviveShipmentModel> getReviveShipmentInfo(ReviveShipmentSummary requestModel) throws CustomException;

   public void onRevive(ReviveShipmentModel requestModel) throws CustomException;

}