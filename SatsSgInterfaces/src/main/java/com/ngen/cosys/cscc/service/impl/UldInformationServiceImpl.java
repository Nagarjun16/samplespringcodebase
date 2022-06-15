package com.ngen.cosys.cscc.service.impl;

import com.ngen.cosys.cscc.constant.ImportExport;
import com.ngen.cosys.cscc.dao.UldInformationDao;
import com.ngen.cosys.cscc.modal.ErrorMessage;
import com.ngen.cosys.cscc.modal.UldInform;
import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import com.ngen.cosys.cscc.modal.request.RequestBody;
import com.ngen.cosys.cscc.modal.uldInformation.ObjectFactory;
import com.ngen.cosys.cscc.modal.uldInformation.ULDInformation;
import com.ngen.cosys.cscc.modal.uldInformation.ULDsItem;
import com.ngen.cosys.cscc.service.UldInformationService;
import com.ngen.cosys.cscc.utils.Utils;
import com.ngen.cosys.framework.exception.CustomException;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.awt.datatransfer.SystemFlavorMap;
import java.util.List;

@Service
public class UldInformationServiceImpl implements UldInformationService {

    private UldInformationDao uldInformationDao;

    public UldInformationServiceImpl(UldInformationDao uldInformationDao) {
        this.uldInformationDao = uldInformationDao;
    }

    @Override
    public List<UldInform> getUldInformation(CSCCRequest request) throws CustomException {
        if (request.getMessage().getBody().getImportExport().equals(
                ImportExport.IMPORT.getImportExportFlag()
        )) {
            return uldInformationDao.getUldImportInformation(request.getMessage().getBody());
        }
        return uldInformationDao.getUldExportInformation(request.getMessage().getBody());
    }

    @Override
    public ULDInformation buildMessageObject(CSCCRequest request) throws CustomException {
        ULDInformation uldInformation = ObjectFactory.createULDInformation();

        if (!validateRequest(request)) {
            uldInformation.gerErrorList().add(new ErrorMessage("400", "INVALID",
                    "At least FlightNo/FlightDate, FromTime/ToTime or ULD should be provided"));
            return uldInformation;
        }

        List<UldInform> ulds = getUldInformation(request);

        if (ulds == null || ulds.size() == 0) {
            uldInformation.gerErrorList().
                    add(new ErrorMessage("401", "no.record", "No Data Found"));
            return uldInformation;
        }

        for (UldInform uldInform : ulds) {
            ULDsItem ulDsItem = ObjectFactory.createULDsItem();
            ulDsItem.setULDNo(uldInform.getULDNo());

            ulDsItem.setSHC("");
            if (Utils.isNotBlank(uldInform.getSHC())) {
                ulDsItem.setSHC(uldInform.getSHC().replace(",", ""));
            }

            ulDsItem.setWeight(uldInform.getWeight());
            ulDsItem.setImportExport(uldInform.getImportExport());
            ulDsItem.setFlightNo(uldInform.getFlightNo());
            ulDsItem.setFlightDate(uldInform.getFlightDate());
            ulDsItem.setTTHConnectedFlightNo(uldInform.getTTHConnectedFlightNo());
            ulDsItem.setTTHConnectedFlightDate(uldInform.getTTHConnectedFlightDate());
            ulDsItem.setRampReleaseTime(uldInform.getRampReleaseTime());
            ulDsItem.setTowInTimeByAFT(uldInform.getTowInTimeByAFT());
            ulDsItem.setWarehouseLocation(uldInform.getWarehouseLocation());
            ulDsItem.setBreakdownTime(uldInform.getBreakdownTime());

            uldInformation.getMessage().getBody().getULDs().add(ulDsItem);
        }

        return uldInformation;
    }

    private boolean validateRequest(CSCCRequest request) {
        RequestBody body = request.getMessage().getBody();

        if (Utils.isNullOrBlank(body.getFlightNo()) &&
                Utils.isNullOrBlank(body.getFromTime()) &&
                Utils.isNullOrBlank(body.getUldNo())) {
            return false;
        }

        return true;
    }
}
