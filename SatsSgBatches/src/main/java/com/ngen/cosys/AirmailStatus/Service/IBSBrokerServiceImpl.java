package com.ngen.cosys.AirmailStatus.Service;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpUrlConnection;

import com.ibsplc.icargo.business.admin.accesscontrol.types.standard.LoginRequestType;
import com.ibsplc.icargo.business.admin.accesscontrol.types.standard.LoginResponseType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;

@Service("IBSBrokerService")
public class IBSBrokerServiceImpl implements IBSBrokerService {

   private static final Logger lOGGER = LoggerFactory.getLogger(IBSBrokerService.class);

   @Autowired
   private WebServiceTemplate loginWebServiceTemplate;

   @Autowired
   private WebServiceTemplate saveWebServiceTemplate;

   private Map<String, String> getHeader() {
      Map<String, String> header = new HashedMap<>();
      header.put("icargo-identitytoken",
            "aWNTZXNzaW9uSWQ6RkdwUkYlMkZ0NmZmOFdEYmdVWUtFTXRlJTJCbDdNdkp5Z1E4ME0wQnYzT0o5aFpwR1VnV0UlMkZOV3Q5akg3akFqdG1DU1JuWkdpeG95Rk45eiUwRCUwQWduR250VHBqVGclM0QlM0Q=");
      header.put("Content-Type", "application/xml");
      header.put("Accept", "application/xml");
      return header;
   }

   private void setInterceptors(String service) {
      Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
      wss4jSecurityInterceptor.setSecurementActions("UsernameToken");
      wss4jSecurityInterceptor.setSecurementUsername("ICGUSR");
      wss4jSecurityInterceptor.setSecurementPassword("iCargoSQ");
      wss4jSecurityInterceptor.setSecurementPasswordType("PasswordText");
      ClientInterceptor[] interceptors = { wss4jSecurityInterceptor };
      if ("Login".equalsIgnoreCase(service)) {
         this.loginWebServiceTemplate.setInterceptors(interceptors);
      } else if ("Save".equalsIgnoreCase(service)) {
         this.saveWebServiceTemplate.setInterceptors(interceptors);
      }
   }

   private void addHeadersToConnection(HttpUrlConnection connection, Map<String, String> headers) {
      headers.forEach((name, value) -> {
         try {
            connection.addRequestHeader(name, value);
         } catch (IOException e) {
            lOGGER.error(e.getCause().toString());
         }
      });
   }

   private WebServiceMessageCallback getRequestCallback(Map<String, String> headers) {
      return message -> {
         TransportContext context = TransportContextHolder.getTransportContext();
         HttpUrlConnection connection = (HttpUrlConnection) context.getConnection();
         addHeadersToConnection(connection, headers);
      };
   }

   @Override
   public String fetchLoginToken(LoginRequestType loginRequest) throws CustomException {
      if (null == loginRequest) {
         loginRequest = new com.ibsplc.icargo.business.admin.accesscontrol.types.standard.ObjectFactory()
               .createLoginRequestType();
         loginRequest.setUserName("ICGUSR");
         loginRequest.setPassword("iCargoSQ");
         loginRequest.setCompanyCode("SQ");
      }
      setInterceptors("Login");
      LoginResponseType response = (LoginResponseType) loginWebServiceTemplate.marshalSendAndReceive(loginRequest,
            getRequestCallback(getHeader()));
      Object payload = JacksonUtility.convertObjectToXMLString(response);
      System.err.println(payload);
      return response.getSessionId();
   }
}