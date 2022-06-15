package com.ngen.cosys.ulms.service.impl;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ulms.constant.ErrorCode;
import com.ngen.cosys.ulms.dao.ReleaseULDToAgentDao;
import com.ngen.cosys.ulms.dao.ULMSAssignedLoadedULDListDao;
import com.ngen.cosys.ulms.exception.ServiceFaultException;
import com.ngen.cosys.ulms.model.*;
import com.ngen.cosys.ulms.service.ULMSErrorMessageService;
import com.ngen.cosys.ulms.service.UldReleaseToAgentService;
import com.ngen.cosys.ulms.utility.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UldReleaseToAgentServiceImpl  implements UldReleaseToAgentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UldReleaseToAgentServiceImpl.class);

    private static ObjectFactory objectFactory = new ObjectFactory();

    @Autowired
    ReleaseULDToAgentDao  releaseULDToAgentDao;

    @Autowired
    ULMSAssignedLoadedULDListDao ulmsAssignedLoadedULDListDao;

    @Autowired
    ULMSErrorMessageService errorMessageService;
    @Override
    public ULDReleaseToAgentResponse uldReleaseToAgent(ULDReleaseToAgentRequest request) throws  CustomException {
        ReleaseULDDetail releaseULDDetail = new ReleaseULDDetail();
        if (request.getAgentCode() != null && !"".equals(request.getAgentCode()) && request.getAgentCode().length() > 0) {
            releaseULDDetail.setCustomerCode(request.getAgentCode());
            releaseULDDetail = releaseULDToAgentDao.getCustomerDetail(releaseULDDetail);

            if (releaseULDDetail == null) {
                throw new ServiceFaultException("CUSTOMER-NOT-FOUND",
                        errorMessageService.buildServiceStatus(ErrorCode.CUSTOMER_NOT_FOUND));
            }
        }
        if ((request.getReleaseEndDate() == null && request.getReleaseStartDate() != null & !"".equals(request.getReleaseStartDate())) ||
                (request.getReleaseStartDate() == null && request.getReleaseEndDate() != null & !"".equals(request.getReleaseEndDate()))) {
            throw new ServiceFaultException("DATE-RANGE-MISSED",
                    errorMessageService.buildServiceStatus(ErrorCode.DATE_RANGE_MISSED));
        }

        LocalDateTime startDate, endDate;
        Long minusHours = 5L;
        try{
            minusHours = ulmsAssignedLoadedULDListDao.getAdministrationSubGroupDetailCode("ULDReleaseDuration");
        }catch (Exception e){
            minusHours = 5L;
        }

        if(request.getReleaseStartDate() != null && !"".equals(request.getReleaseStartDate())) {
            if (request.getReleaseStartDate().length() != 13) {
                throw new ServiceFaultException("DATE-FORMAT-INVALID",
                        errorMessageService.buildServiceStatus(ErrorCode.DATE_FORMAT_INVALID));
            }
            request.setReleaseStartDate(request.getReleaseStartDate()+"00");
        }

        if(request.getReleaseEndDate() != null && !"".equals(request.getReleaseEndDate())) {
            if (request.getReleaseEndDate().length() != 13) {
                throw new ServiceFaultException("DATE-FORMAT-INVALID",
                        errorMessageService.buildServiceStatus(ErrorCode.DATE_FORMAT_INVALID));
            }
            request.setReleaseEndDate(request.getReleaseEndDate()+"59");
        }

        startDate = Utilities.getDBDateTime(request.getReleaseStartDate(), LocalDateTime.now().minusHours(minusHours));
        if (startDate == null) {
            throw new ServiceFaultException("DATE-FORMAT-INVALID",
                    errorMessageService.buildServiceStatus(ErrorCode.DATE_FORMAT_INVALID));
        }

        endDate = Utilities.getDBDateTime(request.getReleaseEndDate(), LocalDateTime.now());
        if (endDate == null) {
            throw new ServiceFaultException("DATE-FORMAT-INVALID",
                    errorMessageService.buildServiceStatus(ErrorCode.DATE_FORMAT_INVALID));
        }

        releaseULDDetail.setStartDate(Utilities.formatToDBDateTime(startDate));
        releaseULDDetail.setEndDate(Utilities.formatToDBDateTime(endDate));


     

        List<ReleaseULDDetail> releasedULDs = releaseULDToAgentDao.getReleaseUldList(releaseULDDetail);
        

        if (releasedULDs == null || releasedULDs.size() == 0) {
            throw new ServiceFaultException("NO-DATA-FOUND",
                    errorMessageService.buildServiceStatus(ErrorCode.NO_DATA_FOUND));
        }

        ULDReleaseToAgentResponse response = objectFactory.createULDReleaseToAgentResponse();

        for (ReleaseULDDetail detail : releasedULDs) {
            if (detail.getUldNumbers() != null && detail.getUldNumbers().length() > 0) {
                AgentDetail agentDetail = objectFactory.createAgentDetail();
                agentDetail.setAgentCode(detail.getCustomerCode());
                agentDetail.setAgentName(detail.getCustomerName());
                agentDetail.setReleaseTime(Utilities.formatToXmlDateTime(detail.getReleaseDate()));
                String[] uldNumbers = detail.getUldNumbers().split(",");
                if (uldNumbers != null && uldNumbers.length > 0) {
                    for (int i = 0; i < uldNumbers.length; i++) {
                        ULD uld = objectFactory.createULD();
                        uld.setULDNumber(uldNumbers[i]);
                        agentDetail.getULDReleased().add(uld);
                    }
                } else {
                    ULD uld = objectFactory.createULD();
                    uld.setULDNumber(detail.getUldNumbers());
                    agentDetail.getULDReleased().add(uld);
                }
                response.getAgentDetail().add(agentDetail);
            }
        }

        return response;
    }
}
