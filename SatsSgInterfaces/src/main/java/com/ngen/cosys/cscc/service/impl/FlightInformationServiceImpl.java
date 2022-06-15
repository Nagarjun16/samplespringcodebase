package com.ngen.cosys.cscc.service.impl;

import com.ngen.cosys.cscc.constant.ImportExport;
import com.ngen.cosys.cscc.dao.FlightInformationDao;
import com.ngen.cosys.cscc.modal.ErrorMessage;
import com.ngen.cosys.cscc.modal.FlightInform;
import com.ngen.cosys.cscc.modal.flightInformation.FlightInformation;
import com.ngen.cosys.cscc.modal.flightInformation.FlightsItem;
import com.ngen.cosys.cscc.modal.flightInformation.ObjectFactory;
import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import com.ngen.cosys.cscc.modal.request.RequestBody;
import com.ngen.cosys.cscc.service.FlightInformationService;
import com.ngen.cosys.cscc.utils.Utils;
import com.ngen.cosys.framework.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightInformationServiceImpl implements FlightInformationService {

    private FlightInformationDao flightInformationDao;

    public FlightInformationServiceImpl(FlightInformationDao flightInformationDao) {
        this.flightInformationDao = flightInformationDao;
    }

    @Override
    public List<FlightInform> getFlightInformation(CSCCRequest request) throws CustomException {
        if (request.getMessage().getBody().getImportExport().equals(
                ImportExport.IMPORT.getImportExportFlag())) {
            return flightInformationDao.getImportFlightInformation(request);
        }
        return flightInformationDao.getExportFlightInformation(request);
    }

    @Override
    public Object buildMessageObject(CSCCRequest request) throws CustomException {
        FlightInformation flightInformation = ObjectFactory.createFlightInformation();

        if (!validateRequest(request)) {
            flightInformation.gerErrorList().add(new ErrorMessage("400", "INVALID",
                    "At least FlightNo/FlightDate or FromTime/ToTime should be provided"));
            return flightInformation;
        }

        List<FlightInform> flights = getFlightInformation(request);
        if (flights == null || flights.size() == 0) {
            flightInformation.gerErrorList().
                    add(new ErrorMessage("401", "no.record", ""));
            return flightInformation;
        }

        for (FlightInform flight : flights) {
            FlightsItem item = ObjectFactory.createFlightItem();
            item.setFlightNo(flight.getFlightNo());
            item.setImportExport(flight.getImportExport());
            item.setSTA(flight.getSTA());
            item.setETA(flight.getETA());
            item.setATA(flight.getATA());
            item.setFlightType(flight.getFlightType());
            item.setAircraftType(flight.getAircraftType());
            item.setBay(flight.getBay());
            item.setSegment(flight.getSegment());
            item.setREG(flight.getREG());
            item.setSTD(flight.getSTD());
            item.setETD(flight.getETD());
            item.setATD(flight.getATD());
            if (request.getMessage().getBody().getImportExport().equals(
                    ImportExport.EXPORT.getImportExportFlag())
            ) {
                item.setPalletTotal(flight.getPalletTotal());
                item.setPalletCargoOffer(flight.getPalletCargoOffer());
                item.setPalletCargoUsed(flight.getPalletCargoUsed());
                item.setContainerTotal(flight.getContainerTotal());
                item.setContainerCargoOffer(flight.getContainerCargoOffer());
                item.setContainerCargoUsed(flight.getContainerCargoUsed());
            }

            item.setLastULDTowIn(flight.getLastULDTowIn());
            item.setBDComplete(flight.getBDComplete());
            item.setDocComplete(flight.getDocComplete());
            item.setNOTOC(flight.getNotoc());
            item.setManifest(flight.getManifest());
            item.setPouchComplete(flight.getPouchComplete());
            item.setDLS(flight.getDls());
            item.setFirstDLS(flight.getFirstDLS());
            item.setReleaseTimingULD(flight.getReleaseTimingULD());
            item.setNOTOCEntries(flight.getNOTOCEntries());
            item.setFlightComplete(flight.getFlightComplete());
            item.setFFMReceived(flight.getFFMReceived());
            item.setFDL(flight.isFDL());
            item.setXAF1(flight.getXAF1());
            item.setXAF2(flight.getXAF2());
            item.setXAF3(flight.getXAF3());
            flightInformation.getMessage().getBody().getFlights().add(item);
        }
        return flightInformation;
    }

    private boolean validateRequest(CSCCRequest request) {
        RequestBody body = request.getMessage().getBody();

        if ((Utils.isNullOrBlank(body.getFlightNo()) && Utils.isNullOrBlank(body.getFlightDate())) &&
                (Utils.isNullOrBlank(body.getFromTime()) &&
                        Utils.isNullOrBlank(body.getToTime()))) {
            return false;
        }

        return true;
    }
}
