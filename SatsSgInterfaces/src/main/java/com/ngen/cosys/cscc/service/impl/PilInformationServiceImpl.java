package com.ngen.cosys.cscc.service.impl;

import com.ngen.cosys.cscc.constant.ImportExport;
import com.ngen.cosys.cscc.dao.PilInformationDao;
import com.ngen.cosys.cscc.modal.ErrorMessage;
import com.ngen.cosys.cscc.modal.PilInform;
import com.ngen.cosys.cscc.modal.pilInformation.ObjectFactory;
import com.ngen.cosys.cscc.modal.pilInformation.PILsItem;
import com.ngen.cosys.cscc.modal.pilInformation.PilInformation;
import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import com.ngen.cosys.cscc.modal.request.RequestBody;
import com.ngen.cosys.cscc.service.PilInformationService;
import com.ngen.cosys.cscc.utils.Utils;
import com.ngen.cosys.framework.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PilInformationServiceImpl implements PilInformationService {

    private PilInformationDao pilInformationDao;

    public PilInformationServiceImpl(PilInformationDao pilInformationDao) {
        this.pilInformationDao = pilInformationDao;
    }

    @Override
    public List<PilInform> getPilInformation(CSCCRequest request) throws CustomException {
        if (request.getMessage().getBody().getImportExport().equals(
                ImportExport.IMPORT.getImportExportFlag()
        )) {
            if (Utils.isNullOrBlank(request.getMessage().getBody().getAwbNo())){
                return pilInformationDao.getImportPilInformationByDateRange(request.getMessage().getBody());
        }
            return pilInformationDao.getImportPilInformation(request.getMessage().getBody());
        }
        return pilInformationDao.getExportPilInformation(request.getMessage().getBody());
    }

    @Override
    public Object buildMessageObject(CSCCRequest request) throws CustomException {
        PilInformation pilInformation = ObjectFactory.createPilInformation();

        if (!validateRequest(request)) {
            pilInformation.gerErrorList().add(new ErrorMessage("400", "INVALID",
                    "At least FromTime/ToTime or AWBNo should be provided"));
            return pilInformation;
        }

        List<PilInform> pils = getPilInformation(request);
        if (pils == null || pils.size() == 0) {
            pilInformation.gerErrorList().
                    add(new ErrorMessage("401", "no.record", "No Data Found"));
            return pilInformation;
        }

        for (PilInform item : pils) {
            PILsItem pilItem = ObjectFactory.createPilItem();
            pilItem.setAWBNo(item.getAwbNo());

            pilItem.setFlightNumberIN(null);
            if (Utils.isNotBlank(item.getFlightNumberINs())) {
                String[] flightNumberIns = item.getFlightNumberINs().split(",");
                pilItem.setFlightNumberIN(Arrays.asList(flightNumberIns));
            }
            pilItem.setTowInTime(item.getTowInTime());
            pilItem.setIntoColdRoomTime(item.getIntoColdRoomTime());

            pilItem.setULDNumIN(null);
            if (Utils.isNotBlank(item.getUldNumIns())) {
                String[] uldIns = item.getUldNumIns().split(",");
                pilItem.setULDNumIN(Arrays.asList(uldIns));
            }

            pilItem.setPHC(item.isPHC());
            pilItem.setTransshipment(item.isTransshipment());

            pilItem.setFlightNumOut(null);
            if (Utils.isNotBlank(item.getFlightNumOuts())) {
                String[] flightOuts = item.getFlightNumOuts().split(",");
                pilItem.setFlightNumOut(Arrays.asList(flightOuts));
            }

            pilItem.setULDNumOut(null);
            if (Utils.isNotBlank(item.getUldNumOuts())) {
                String[] ultOuts = item.getUldNumOuts().split(",");
                pilItem.setULDNumOut(Arrays.asList(ultOuts));
            }

            pilItem.setOutOfColdRoomTime(item.getOutOfColdRoomTime());
            pilItem.setRampReleaseTime(item.getRampReleaseTime());
            pilItem.setXAF1(item.getXAF1());
            pilItem.setXAF2(item.getXAF2());
            pilItem.setXAF3(item.getXAF3());
            pilInformation.getMessage().getBody().getPILs().add(pilItem);
        }
        return pilInformation;
    }

    private boolean validateRequest(CSCCRequest request) {
        RequestBody body = request.getMessage().getBody();

        if (Utils.isNullOrBlank(body.getFromTime()) &&
                Utils.isNullOrBlank(body.getAwbNo())) {
            return false;
        }

        return true;
    }
}
