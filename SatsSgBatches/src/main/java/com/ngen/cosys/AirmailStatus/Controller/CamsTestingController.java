package com.ngen.cosys.AirmailStatus.Controller;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpUrlConnection;

import com.ibsplc.icargo.business.mailtracking.defaults.types.standard.SaveMailDetailsRequestType;
import com.ibsplc.icargo.business.mailtracking.defaults.types.standard.SaveMailDetailsResponseType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;

@RestController
public class CamsTestingController {

   @Autowired
   private WebServiceTemplate saveWebServiceTemplate;
   /*
    * @Autowired private WebServiceTemplate loginWebServiceTemplate;
    */

   @RequestMapping(value = "/api/cams/dummy", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public SaveMailDetailsResponseType camsController(@RequestBody SaveMailDetailsRequestType req)
         throws CustomException {
      /*
       * List<MailDetailsRequestType> list = new ArrayList<>(); MailDetailsRequestType
       * mailDetailsRequestType = new ObjectFactory().createMailDetailsRequestType();
       * SaveMailDetailsRequestType request = new
       * ObjectFactory().createSaveMailDetailsRequestType();
       * request.setCompanyCode("SQ"); request.setHhtVersion("1.0");
       * request.setMessagePartId(1); request.setScanningPort("SIN");
       * mailDetailsRequestType.setProduct("mailtracking.defaults");
       * mailDetailsRequestType.setScanType("ACP");
       * mailDetailsRequestType.setCarrierCode("QF");
       * mailDetailsRequestType.setFlightNumber(null);
       * mailDetailsRequestType.setFlightDate(null);
       * mailDetailsRequestType.setContainerPou(null);
       * mailDetailsRequestType.setContainerNumber(null);
       * mailDetailsRequestType.setContainerType(null);
       * mailDetailsRequestType.setContainerDestination(null);
       * mailDetailsRequestType.setContainerPol(null);
       * mailDetailsRequestType.setRemarks(null);
       * mailDetailsRequestType.setMailTag("DEFRAAINTRVAGCA41596002191000");
       * mailDetailsRequestType.setDamageCode(null);
       * mailDetailsRequestType.setDamageRemarks(null);
       * mailDetailsRequestType.setOffloadReason(null);
       * mailDetailsRequestType.setReturnCode(null);
       * mailDetailsRequestType.setToContainerType(null);
       * mailDetailsRequestType.setToContainer(null);
       * mailDetailsRequestType.setToCarrierCode(null);
       * mailDetailsRequestType.setToFlightNumber(null);
       * mailDetailsRequestType.setToFlightDate(null);
       * mailDetailsRequestType.setToContainerPOU(null);
       * mailDetailsRequestType.setToContainerDestination(null);
       * mailDetailsRequestType.setConsignmentDocumentNumber(null);
       * mailDetailsRequestType.setSerialNumber("1");
       * mailDetailsRequestType.setIsPABuilt(null);
       * mailDetailsRequestType.setIsDelivered(null);
       * mailDetailsRequestType.setUserName("SATSGHA");
       * mailDetailsRequestType.setScanDateTime("07-SEP-2014 10:30:05");
       * list.add(mailDetailsRequestType); request.setMailDetails(list);
       */

      /*
       * LoginRequestType loginRequestType = new
       * com.ibsplc.icargo.business.admin.accesscontrol.types.standard.ObjectFactory()
       * .createLoginRequestType(); loginRequestType.setUserName("ICGUSR");
       * loginRequestType.setPassword("iCargoSQ");
       * loginRequestType.setCompanyCode("SQ");
       * 
       * LoginResponseType loginResponseType = (LoginResponseType)
       * loginWebServiceTemplate .marshalSendAndReceive(loginRequestType);
       * System.err.println(
       * "LLLLLLLLLLOOOOOOOOOOOOOOOOOOOOGGGGGGGGGGGIIIIIIIIIIIIIIIINNNNNNNNNNNNN");
       * System.err.println(loginResponseType);
       */
      // SaveMailDetailsRequestType payloadJson = (SaveMailDetailsRequestType)
      // JacksonUtility.convertXMLStringToObject(req,
      // SaveMailDetailsRequestType.class);
      System.err.println("Request----");
      System.err.println(JacksonUtility.convertObjectToXMLString(req));
      SaveMailDetailsResponseType response = getResponse(req);
      Object payload = JacksonUtility.convertObjectToXMLString(response);
      System.err.println(payload);
      return response;

   }

   private Map<String, String> getHeader() {
      Map<String, String> headers = new HashedMap<>();
      headers.put("icargo-identitytoken",
            "aWNTZXNzaW9uSWQ6RkdwUkYlMkZ0NmZmOFdEYmdVWUtFTXRlJTJCbDdNdkp5Z1E4ME0wQnYzT0o5aFpwR1VnV0UlMkZOV3Q5akg3akFqdG1DU1JuWkdpeG95Rk45eiUwRCUwQWduR250VHBqVGclM0QlM0Q=");
      return headers;
   }

   private SaveMailDetailsResponseType getResponse(SaveMailDetailsRequestType request) {
      Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
      wss4jSecurityInterceptor.setSecurementActions("UsernameToken");
      wss4jSecurityInterceptor.setSecurementUsername("ICGUSR");
      wss4jSecurityInterceptor.setSecurementPassword("iCargoSQ");
      wss4jSecurityInterceptor.setSecurementPasswordType("PasswordText");
      ClientInterceptor[] interceptors = { wss4jSecurityInterceptor };
      saveWebServiceTemplate.setInterceptors(interceptors);
      SaveMailDetailsResponseType response = (SaveMailDetailsResponseType) saveWebServiceTemplate
            .marshalSendAndReceive(request, getRequestCallback(getHeader()));
      return response;
   }

   private WebServiceMessageCallback getRequestCallback(Map<String, String> headers) {
      return message -> {
         TransportContext context = TransportContextHolder.getTransportContext();
         HttpUrlConnection connection = (HttpUrlConnection) context.getConnection();
         addHeadersToConnection(connection, headers);
      };
   }

   private void addHeadersToConnection(HttpUrlConnection connection, Map<String, String> headers) {
      headers.forEach((name, value) -> {
         try {
            connection.addRequestHeader(name, value);
         } catch (IOException e) {
            e.printStackTrace(); // or whatever you want
         }
      });
   }
}
