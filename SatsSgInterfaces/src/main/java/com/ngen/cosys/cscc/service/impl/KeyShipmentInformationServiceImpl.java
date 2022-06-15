package com.ngen.cosys.cscc.service.impl;

import com.ngen.cosys.cscc.constant.ImportExport;
import com.ngen.cosys.cscc.dao.CargoInformationDao;
import com.ngen.cosys.cscc.modal.ErrorMessage;
import com.ngen.cosys.cscc.modal.ShipmentInform;
import com.ngen.cosys.cscc.modal.keyShipmentInformation.BodyItem;
import com.ngen.cosys.cscc.modal.keyShipmentInformation.KeyShipmentInformation;
import com.ngen.cosys.cscc.modal.keyShipmentInformation.ObjectFactory;
import com.ngen.cosys.cscc.modal.keyShipmentInformation.ShipmentsItem;
import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import com.ngen.cosys.cscc.modal.request.RequestBody;
import com.ngen.cosys.cscc.service.KeyShipmentInformationService;
import com.ngen.cosys.cscc.utils.Utils;
import com.ngen.cosys.framework.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KeyShipmentInformationServiceImpl implements KeyShipmentInformationService {

    private CargoInformationDao cargoInformationDao;

    public KeyShipmentInformationServiceImpl(CargoInformationDao cargoInformationDao) {
        this.cargoInformationDao = cargoInformationDao;
    }

    @Override
    public List<ShipmentInform> getShipmentInformation(CSCCRequest request) throws CustomException {
        List<ShipmentInform> shipmentInforms;
        if (ImportExport.IMPORT.getImportExportFlag().equals(request.getMessage().getBody().getImportExport())) {
            shipmentInforms = cargoInformationDao.getImportCargoInformation(request);
        } else if (ImportExport.EXPORT.getImportExportFlag().equals(request.getMessage().getBody().getImportExport())) {
            shipmentInforms = cargoInformationDao.getExportCargoInformation(request);
        } else {
            List<ShipmentInform> importShipment = cargoInformationDao.getImportCargoInformation(request);
            List<ShipmentInform> exportShipment = cargoInformationDao.getExportCargoInformation(request);
            shipmentInforms = new ArrayList<>();
            if (importShipment != null && importShipment.size() > 0) {
                shipmentInforms.addAll(importShipment);
            }
            if (exportShipment != null && exportShipment.size() > 0) {
                shipmentInforms.addAll(exportShipment);
            }

        }
        return shipmentInforms;
    }

    @Override
    public KeyShipmentInformation buildMessageObject(CSCCRequest request) throws CustomException {
        KeyShipmentInformation keyShipmentInformation = ObjectFactory.createKeyShipmentInfo();

        if (!validateRequest(request)) {
            keyShipmentInformation.gerErrorList().add(new ErrorMessage("400", "INVALID",
                    "At least FlightNo/FlightDate or FromTime/ToTime should be provided"));
            return keyShipmentInformation;
        }

        List<ShipmentInform> shipments = getShipmentInformation(request);

        if (shipments == null || shipments.size() == 0) {
            keyShipmentInformation.gerErrorList().
                    add(new ErrorMessage("401", "no.record", "No Data Found"));
            return keyShipmentInformation;
        }

        Map<String, List<ShipmentInform>> flights = shipments.stream().collect(
                Collectors.groupingBy(w -> w.getFlightNo() + "-" +
                        w.getFlightDate() + "-" +
                        w.getImportExport())
        );
        for (String flight : flights.keySet()) {
            BodyItem bodyItem = ObjectFactory.createBodyItem();
            List<ShipmentInform> shipmentInforms = flights.get(flight);
            bodyItem.setFlightNo(shipmentInforms.get(0).getFlightNo());
            bodyItem.setFlightDate(shipmentInforms.get(0).getFlightDate());
            bodyItem.setImportExport(shipmentInforms.get(0).getImportExport());
            keyShipmentInformation.getMessage().getBody().add(bodyItem);
            for (ShipmentInform shipment : shipmentInforms) {
                ShipmentsItem shipmentsItem = ObjectFactory.createShipmentItem();
                shipmentsItem.setAWBNo(shipment.getAWBNo());

                if (ImportExport.EXPORT.getImportExportFlag().equals(shipment.getImportExport())) {
                    shipmentsItem.setImportULDS(null);
                } else {
                    shipmentsItem.setImportULDS(new ArrayList<>());
                    if (Utils.isNotBlank(shipment.getImportULDSString())) {
                        String[] ULDs = shipment.getImportULDSString().split(",");
                        shipmentsItem.setImportULDS(Arrays.asList(ULDs));
                    }
                }
                shipmentsItem.setSHC("");
                if (Utils.isNotBlank(shipment.getSHC())) {
                    shipmentsItem.setSHC(shipment.getSHC().replaceAll(",", ""));
                }
                shipmentsItem.setNOG(shipment.getNOG());
                shipmentsItem.setOrigin(shipment.getOrigin());
                shipmentsItem.setDestination(shipment.getDestination());
                shipmentsItem.setPcs(shipment.getPcs());
                shipmentsItem.setWeight(shipment.getWeight());
                shipmentsItem.setBookingChanges(shipment.isBookingChanges());
                shipmentsItem.setTransshipment(shipment.isTransshipment());

                shipmentsItem.setFlightNoIn(null);
                if (Utils.isNotBlank(shipment.getFlightNoIns())) {
                    String[] flightIns = shipment.getFlightNoIns().split(",");
                    shipmentsItem.setFlightNoIn(Arrays.asList(flightIns));
                }

                shipmentsItem.setFlightNoOut(null);
                if (Utils.isNotBlank(shipment.getFlightNoOuts())) {
                    String[] flightOuts = shipment.getFlightNoOuts().split(",");
                    shipmentsItem.setFlightNoOut(Arrays.asList(flightOuts));
                }

                shipmentsItem.setAcceptanceTime(shipment.getAcceptanceTime());
                shipmentsItem.setBuildUpTime(shipment.getBuildUpTime());
                shipmentsItem.setAWBReceivedTime(shipment.getAWBReceivedTime());
                shipmentsItem.setAWBScannedTime(shipment.getAWBScannedTime());

                shipmentsItem.setLoadedInfo(null);
                if (Utils.isNotBlank(shipment.getLoadedInfos())) {
                    String[] loadedInfos = shipment.getLoadedInfos().split(",");
                    shipmentsItem.setLoadedInfo(Arrays.asList(loadedInfos));
                }

                shipmentsItem.setLocationInfo(null);
                if (Utils.isNotBlank(shipment.getLocationInfos())) {
                    String[] locationInfos = shipment.getLocationInfos().split(",");
                    shipmentsItem.setLocationInfo(Arrays.asList(locationInfos));
                }

                shipmentsItem.setScreenFlag(shipment.isScreenFlag());
                shipmentsItem.setScreenedBy(shipment.getScreenedBy());
                shipmentsItem.setScreenedAt(shipment.getScreenedAt());
                shipmentsItem.setOffloadFinalizationTiming(shipment.getOffloadFinalizationTiming());
                shipmentsItem.setRampReceivedTiming(shipment.getRampReceivedTiming());
                shipmentsItem.setXAF1(shipment.getXAF1());
                shipmentsItem.setXAF2(shipment.getXAF2());
                shipmentsItem.setXAF3(shipment.getXAF3());

                bodyItem.getShipments().add(shipmentsItem);
            }
        }
        return keyShipmentInformation;
    }

    private boolean validateRequest(CSCCRequest request) {
        RequestBody body = request.getMessage().getBody();

        if (Utils.isNullOrBlank(body.getFlightNo()) &&
                Utils.isNullOrBlank(body.getFromTime())) {
            return false;
        }

        return true;
    }
}
