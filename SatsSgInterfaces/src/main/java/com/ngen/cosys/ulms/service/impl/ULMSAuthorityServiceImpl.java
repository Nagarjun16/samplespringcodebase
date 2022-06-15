package com.ngen.cosys.ulms.service.impl;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ulms.constant.ErrorCode;
import com.ngen.cosys.ulms.dao.ULMSAuthorityDao;
import com.ngen.cosys.ulms.exception.ServiceFaultException;
import com.ngen.cosys.ulms.model.AuthenticationHeader;
import com.ngen.cosys.ulms.model.HeaderInfo;
import com.ngen.cosys.ulms.model.ObjectFactory;
import com.ngen.cosys.ulms.service.ULMSAuthorityService;
import com.ngen.cosys.ulms.service.ULMSErrorMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.soap.SoapHeaderElement;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.util.Base64;

@Service
public class ULMSAuthorityServiceImpl implements ULMSAuthorityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ULMSAuthorityServiceImpl.class);

    @Autowired
    ULMSAuthorityDao clientInfoDao;

    @Autowired
    ULMSErrorMessageService errorMessageService;

    private HeaderInfo getHeaderInfo(SoapHeaderElement soapHeaderElement) {
        HeaderInfo headerInfo = new HeaderInfo();
        try {
            // create an unmarshaller
            JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // unmarshal the header from the specified source
            JAXBElement<AuthenticationHeader> headers =
                    (JAXBElement<AuthenticationHeader>) unmarshaller
                            .unmarshal(soapHeaderElement.getSource());

            // get the header values
            AuthenticationHeader authenticationHeader = headers.getValue();
            headerInfo.setClientId(authenticationHeader.getClientId());
            headerInfo.setMessageId(authenticationHeader.getMessageId());
            headerInfo.setAccessToken(authenticationHeader.getAccessToken());

            if(headerInfo.getAccessToken() != null && headerInfo.getAccessToken().length()>0){
                String accessToken = new String(Base64.getDecoder().decode(headerInfo.getAccessToken().getBytes()));

                if(accessToken != null && accessToken.length()>0 ){
                    String[] userNamePasswordPair = accessToken.split(":");
                    if(userNamePasswordPair.length == 2) {
                        headerInfo.setUserName(userNamePasswordPair[0]);
                        headerInfo.setUserPassword(userNamePasswordPair[1]);
                    }
                }
            }
        } catch (JAXBException e){
            headerInfo.setErrorCodeDesc(ErrorCode.JAXB_ERROR);
        }
        catch (Exception e) {
            headerInfo.setErrorCodeDesc(ErrorCode.GENERIC_ERROR);
        }

        return headerInfo;
    }


    public HeaderInfo internalAuthorityCaller(SoapHeaderElement soapHeaderElement) throws CustomException {
        HeaderInfo headerInfo = getHeaderInfo(soapHeaderElement);

        if(headerInfo.getErrorCodeDesc() != null){
            return headerInfo;
        }

        if(headerInfo.getClientId() == null || headerInfo.getClientId().isEmpty()){
            headerInfo.setErrorCodeDesc(ErrorCode.CLIENT_IS_BLANK);
        }

        if(headerInfo.getUserName() == null || headerInfo.getUserName().isEmpty() ||
                headerInfo.getUserPassword() == null || headerInfo.getUserPassword().isEmpty()){
            headerInfo.setErrorCodeDesc(ErrorCode.USERNAME_PASSWORD_IS_BLANK);
        }
        Integer clientCount = clientInfoDao.checkClientValid(headerInfo);

        if(clientCount <=0){
            headerInfo.setErrorCodeDesc(ErrorCode.AUTHORITY_FAIL);
        }

        return headerInfo;
    }

    @Override
    public void authorityCaller(SoapHeaderElement soapHeaderElement) throws CustomException {
        HeaderInfo headerInfo =internalAuthorityCaller(soapHeaderElement);

        if(headerInfo.getErrorCodeDesc() != null){
            throw new ServiceFaultException("AUTHORITY_FAIL",
                    errorMessageService.buildServiceStatus(ErrorCode.AUTHORITY_FAIL));
        }
    }
}
