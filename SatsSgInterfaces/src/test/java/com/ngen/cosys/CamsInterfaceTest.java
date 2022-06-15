/*package com.ngen.cosys;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/config/camsInterfaceConfig.xml")

public class CamsInterfaceTest {

      @Autowired
   @Qualifier("webServiceTemplate")
   private WebServiceTemplate webServiceTemplate;
   
   private static final Logger lOGGER = LoggerFactory.getLogger(CamsInterfaceTest.class);
   	
   
   // 1) Accept a mail bag from Postal Authority (to carrier)
   
   
   @Test
   public void acceptMailBagFromPA()  {
     List<MailDetailsRequestType> list= new ArrayList<>();
     MailDetailsRequestType mailOperationsDetailsRequestType = new ObjectFactory().createMailDetailsRequestType();
     SaveMailDetailsRequestType request = new ObjectFactory().createSaveMailDetailsRequestType();
     request.setCompanyCode("SQ");
     request.setHhtVersion("1.0");
     request.setMessagePartId(1);
     request.setScanningPort("SIN");
     mailOperationsDetailsRequestType.setProduct("mailtracking.defaults");
     mailOperationsDetailsRequestType.setScanType("ACP");
     mailOperationsDetailsRequestType.setCarrierCode("QF");
     mailOperationsDetailsRequestType.setFlightNumber(null);
     mailOperationsDetailsRequestType.setFlightDate(null);
     mailOperationsDetailsRequestType.setContainerPou(null);
     mailOperationsDetailsRequestType.setContainerNumber(null);
     mailOperationsDetailsRequestType.setContainerType(null);
     mailOperationsDetailsRequestType.setContainerDestination(null);
     mailOperationsDetailsRequestType.setContainerPol(null);
     mailOperationsDetailsRequestType.setRemarks(null);
     mailOperationsDetailsRequestType.setMailTag("DEFRAAINTRVAGCA41596002191000");
     mailOperationsDetailsRequestType.setDamageCode(null);
     mailOperationsDetailsRequestType.setDamageRemarks(null);
     mailOperationsDetailsRequestType.setOffloadReason(null);
     mailOperationsDetailsRequestType.setReturnCode(null);
     mailOperationsDetailsRequestType.setToContainerType(null);
     mailOperationsDetailsRequestType.setToContainer(null);
     mailOperationsDetailsRequestType.setToCarrierCode(null);
     mailOperationsDetailsRequestType.setToFlightNumber(null);
     mailOperationsDetailsRequestType.setToFlightDate(null);
     mailOperationsDetailsRequestType.setToContainerPOU(null);
     mailOperationsDetailsRequestType.setToContainerDestination(null);
     mailOperationsDetailsRequestType.setConsignmentDocumentNumber(null);
     mailOperationsDetailsRequestType.setSerialNumber("1");
     mailOperationsDetailsRequestType.setIsPABuilt(null);
     mailOperationsDetailsRequestType.setIsDelivered(null);
     mailOperationsDetailsRequestType.setUserName("SATSGHA");
     mailOperationsDetailsRequestType.setScanDateTime("07-SEP-2014 10:30:05");
     list.add(mailOperationsDetailsRequestType);
     request.setMailDetails(list);
   
     if (lOGGER.isInfoEnabled()) {
        lOGGER.info("Accept a mail bag from Postal Authority (to carrier) ===>\n", request);
     }
   //  http://www.ibsplc.com/icargo/services/types/MailOperationsService/standard/2012/12/12_01}mailSaveDetailsRequestType  
     Map<String, String> headers= new HashedMap<>();
     headers.put("icargo-identitytoken","aWNTZXNzaW9uSWQ6RkdwUkYlMkZ0NmZmOFdEYmdVWUtFTXRlJTJCbDdNdkp5Z1E4ME0wQnYzT0o5aFpwR1VnV0UlMkZOV3Q5akg3akFqdG1DU1JuWkdpeG95Rk45eiUwRCUwQWduR250VHBqVGclM0QlM0Q=");
     headers.put("Content-Type", "application/xml");
     headers.put("Accept", "application/xml");
     @SuppressWarnings("deprecation")
     Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
     wss4jSecurityInterceptor.setSecurementActions("UsernameToken");
     wss4jSecurityInterceptor.setSecurementUsername("ICGUSR");
     wss4jSecurityInterceptor.setSecurementPassword("iCargoSQ");
     wss4jSecurityInterceptor.setSecurementPasswordType("PasswordText");
     ClientInterceptor[] interceptors = {wss4jSecurityInterceptor};
     webServiceTemplate.setInterceptors(interceptors);
   
     SaveMailDetailsResponseType response = (SaveMailDetailsResponseType) webServiceTemplate.marshalSendAndReceive(request, getRequestCallback(headers));
     Object payload = JacksonUtility.convertObjectToXMLString(response);
     System.err.println(payload);
     System.err.println(response.getCompanyCode());
     assertNotNull(response);
     assertNotNull(response.getCompanyCode());
     assertNotNull(response.getErrorFlag());
     assertNotNull(response.getHhtVersion());
     assertNotNull(response.getScanningPort());
     if (lOGGER.isInfoEnabled()) {
        lOGGER.info("Accept a mail bag from Postal Authority (to carrier) ===>\n", response.toString());
     }
     }
   
   
   
   private WebServiceMessageCallback getRequestCallback(Map<String, String> headers) {
      return message -> {
         TransportContext context = TransportContextHolder.getTransportContext();
         HttpUrlConnection connection = (HttpUrlConnection)context.getConnection();
         addHeadersToConnection(connection, headers);
     };
   }
   
   
   private void addHeadersToConnection(HttpUrlConnection connection, Map<String, String> headers){
       headers.forEach((name, value) -> {
           try {
               connection.addRequestHeader(name, value);
           } catch (IOException e) {
               e.printStackTrace(); // or whatever you want
           }
       });
   }
   
   // 2) Receive (Accept) a mail bag from other carrier (to carrier)- with container
   
   @Test
   public void acceptMailBagFromOtherCarrier()  {
      List<MailOperationsDetailsRequestType> list= new ArrayList<>();
      MailOperationsDetailsRequestType mailOperationsDetailsRequestType = new ObjectFactory().createMailOperationsDetailsRequestType();
      MailSaveDetailsRequestType request = new ObjectFactory().createMailSaveDetailsRequestType();
     request.setCompanyCode("SQ");
     request.setHhtVersion("1.0");
     request.setMessagePartId(1);
     request.setScanningPort("SIN");
     mailOperationsDetailsRequestType.setProduct("mailtracking.defaults");
     mailOperationsDetailsRequestType.setScanType("ACP");
     mailOperationsDetailsRequestType.setCarrierCode("QF");
     mailOperationsDetailsRequestType.setFlightNumber(null);
     mailOperationsDetailsRequestType.setFlightDate(null);
     mailOperationsDetailsRequestType.setContainerPou(null);
     mailOperationsDetailsRequestType.setContainerNumber("AKE1987AV");
     mailOperationsDetailsRequestType.setContainerType(null);
     mailOperationsDetailsRequestType.setContainerDestination(null);
     mailOperationsDetailsRequestType.setContainerPol(null);
     mailOperationsDetailsRequestType.setRemarks(null);
     mailOperationsDetailsRequestType.setMailTag("DEFRAAINTRVAGCA41596002191000");
     mailOperationsDetailsRequestType.setDamageCode(null);
     mailOperationsDetailsRequestType.setDamageRemarks(null);
     mailOperationsDetailsRequestType.setOffloadReason(null);
     mailOperationsDetailsRequestType.setReturnCode(null);
     mailOperationsDetailsRequestType.setToContainerType(null);
     mailOperationsDetailsRequestType.setToContainer(null);
     mailOperationsDetailsRequestType.setToCarrierCode(null);
     mailOperationsDetailsRequestType.setToFlightNumber(null);
     mailOperationsDetailsRequestType.setToFlightDate(null);
     mailOperationsDetailsRequestType.setToContainerPOU(null);
     mailOperationsDetailsRequestType.setToContainerDestination(null);
     mailOperationsDetailsRequestType.setConsignmentDocumentNumber(null);
     mailOperationsDetailsRequestType.setSerialNumber("1");
     mailOperationsDetailsRequestType.setIsPABuilt(null);
     mailOperationsDetailsRequestType.setIsDelivered(null);
     mailOperationsDetailsRequestType.setUserName("SATSGHA");
     mailOperationsDetailsRequestType.setScanDateTime("07-SEP-2014 10:30:05");
     list.add(mailOperationsDetailsRequestType);
     request.setMailDetails(list);
     MailSaveDetailsResponseType response = (MailSaveDetailsResponseType) webServiceTemplate.marshalSendAndReceive(request);
     assertNotNull(response);
     assertNotNull(response.getCompanyCode());
     assertNotNull(response.getErrorFlag());
     assertNotNull(response.getHhtVersion());
     assertNotNull(response.getScanningPort());
     if (lOGGER.isInfoEnabled()) {
        lOGGER.info("Receive (Accept) a mail bag from other carrier (to carrier)- with container ===>\n", response.toString());
     }
   }
   
   // 3) Return a mail bag to Postal Authority
   
   @Test
   public void returnMailBagToPA()  {
      List<MailOperationsDetailsRequestType> list= new ArrayList<>();
      MailOperationsDetailsRequestType mailOperationsDetailsRequestType = new ObjectFactory().createMailOperationsDetailsRequestType();
      MailSaveDetailsRequestType request = new ObjectFactory().createMailSaveDetailsRequestType();
     request.setCompanyCode("SQ");
     request.setHhtVersion("1.0");
     request.setMessagePartId(1);
     request.setScanningPort("SIN");
     mailOperationsDetailsRequestType.setProduct("mailtracking.defaults");
     mailOperationsDetailsRequestType.setScanType("RTN");
     mailOperationsDetailsRequestType.setCarrierCode("SQ");
     mailOperationsDetailsRequestType.setFlightNumber(null);
     mailOperationsDetailsRequestType.setFlightDate(null);
     mailOperationsDetailsRequestType.setContainerPou(null);
     mailOperationsDetailsRequestType.setContainerNumber(null);
     mailOperationsDetailsRequestType.setContainerType(null);
     mailOperationsDetailsRequestType.setContainerDestination(null);
     mailOperationsDetailsRequestType.setContainerPol(null);
     mailOperationsDetailsRequestType.setRemarks("Damaged");
     mailOperationsDetailsRequestType.setMailTag("DEFRAAINTRVAGCA41596002191000");
     mailOperationsDetailsRequestType.setDamageCode(null);
     mailOperationsDetailsRequestType.setDamageRemarks(null);
     mailOperationsDetailsRequestType.setOffloadReason(null);
     mailOperationsDetailsRequestType.setReturnCode("100");
     mailOperationsDetailsRequestType.setToContainerType(null);
     mailOperationsDetailsRequestType.setToContainer(null);
     mailOperationsDetailsRequestType.setToCarrierCode(null);
     mailOperationsDetailsRequestType.setToFlightNumber(null);
     mailOperationsDetailsRequestType.setToFlightDate(null);
     mailOperationsDetailsRequestType.setToContainerPOU(null);
     mailOperationsDetailsRequestType.setToContainerDestination(null);
     mailOperationsDetailsRequestType.setConsignmentDocumentNumber(null);
     mailOperationsDetailsRequestType.setSerialNumber("1");
     mailOperationsDetailsRequestType.setIsPABuilt(null);
     mailOperationsDetailsRequestType.setIsDelivered(null);
     mailOperationsDetailsRequestType.setUserName("SATSGHA");
     mailOperationsDetailsRequestType.setScanDateTime("07-SEP-2014 10:30:05");
     list.add(mailOperationsDetailsRequestType);
     request.setMailDetails(list);
     MailSaveDetailsResponseType response = (MailSaveDetailsResponseType) webServiceTemplate.marshalSendAndReceive(request);
     assertNotNull(response);
     assertNotNull(response.getCompanyCode());
     assertNotNull(response.getErrorFlag());
     assertNotNull(response.getHhtVersion());
     assertNotNull(response.getScanningPort());
     if (lOGGER.isInfoEnabled()) {
        lOGGER.info("Return a mail bag to Postal Authority ===>\n", response.toString());
     }
   }
   
   // 4) Pass a mail bag to another carrier
   
   @Test
   public void passMailBagToAnotherCarrier()  {
      List<MailOperationsDetailsRequestType> list= new ArrayList<>();
      MailOperationsDetailsRequestType mailOperationsDetailsRequestType = new ObjectFactory().createMailOperationsDetailsRequestType();
      MailSaveDetailsRequestType request = new ObjectFactory().createMailSaveDetailsRequestType();
     request.setCompanyCode("SQ");
     request.setHhtVersion("1.0");
     request.setMessagePartId(1);
     request.setScanningPort("SIN");
     mailOperationsDetailsRequestType.setProduct("mailtracking.defaults");
     mailOperationsDetailsRequestType.setScanType("TRA");
     mailOperationsDetailsRequestType.setCarrierCode("SQ");
     mailOperationsDetailsRequestType.setFlightNumber(null);
     mailOperationsDetailsRequestType.setFlightDate(null);
     mailOperationsDetailsRequestType.setContainerPou(null);
     mailOperationsDetailsRequestType.setContainerNumber(null);
     mailOperationsDetailsRequestType.setContainerType(null);
     mailOperationsDetailsRequestType.setContainerDestination(null);
     mailOperationsDetailsRequestType.setContainerPol(null);
     mailOperationsDetailsRequestType.setRemarks(null);
     mailOperationsDetailsRequestType.setMailTag("DEFRAAINTRVAGCA41596002191000");
     mailOperationsDetailsRequestType.setDamageCode(null);
     mailOperationsDetailsRequestType.setDamageRemarks(null);
     mailOperationsDetailsRequestType.setOffloadReason(null);
     mailOperationsDetailsRequestType.setReturnCode(null);
     mailOperationsDetailsRequestType.setToContainerType(null);
     mailOperationsDetailsRequestType.setToContainer(null);
     mailOperationsDetailsRequestType.setToCarrierCode("QF");
     mailOperationsDetailsRequestType.setToFlightNumber(null);
     mailOperationsDetailsRequestType.setToFlightDate(null);
     mailOperationsDetailsRequestType.setToContainerPOU(null);
     mailOperationsDetailsRequestType.setToContainerDestination(null);
     mailOperationsDetailsRequestType.setConsignmentDocumentNumber(null);
     mailOperationsDetailsRequestType.setSerialNumber("1");
     mailOperationsDetailsRequestType.setIsPABuilt(null);
     mailOperationsDetailsRequestType.setIsDelivered(null);
     mailOperationsDetailsRequestType.setUserName("SATSGHA");
     mailOperationsDetailsRequestType.setScanDateTime("07-SEP-2014 10:30:05");
     list.add(mailOperationsDetailsRequestType);
     request.setMailDetails(list);
     MailSaveDetailsResponseType response = (MailSaveDetailsResponseType) webServiceTemplate.marshalSendAndReceive(request);
     assertNotNull(response);
     assertNotNull(response.getCompanyCode());
     assertNotNull(response.getErrorFlag());
     assertNotNull(response.getHhtVersion());
     assertNotNull(response.getScanningPort());
     if (lOGGER.isInfoEnabled()) {
        lOGGER.info("Pass a mail bag to another carrier ===>\n", response.toString());
     }
   }
   
   // 5) Load a mail bag into a container/MT (acceptance at carrier level)
   
   @Test
   public void loadMailBagIntoContainer()  {
      List<MailOperationsDetailsRequestType> list= new ArrayList<>();
      MailOperationsDetailsRequestType mailOperationsDetailsRequestType = new ObjectFactory().createMailOperationsDetailsRequestType();
      MailSaveDetailsRequestType request = new ObjectFactory().createMailSaveDetailsRequestType();
     request.setCompanyCode("SQ");
     request.setHhtVersion("1.0");
     request.setMessagePartId(1);
     request.setScanningPort("SIN");
     mailOperationsDetailsRequestType.setProduct("mailtracking.defaults");
     mailOperationsDetailsRequestType.setScanType("ACP");
     mailOperationsDetailsRequestType.setCarrierCode(null);
     mailOperationsDetailsRequestType.setFlightNumber(null);
     mailOperationsDetailsRequestType.setFlightDate(null);
     mailOperationsDetailsRequestType.setContainerPou(null);
     mailOperationsDetailsRequestType.setContainerNumber("AKE1908AV");
     mailOperationsDetailsRequestType.setContainerType(null);
     mailOperationsDetailsRequestType.setContainerDestination(null);
     mailOperationsDetailsRequestType.setContainerPol(null);
     mailOperationsDetailsRequestType.setRemarks(null);
     mailOperationsDetailsRequestType.setMailTag("DEFRAAINTRVAGCA41596002191000");
     mailOperationsDetailsRequestType.setDamageCode(null);
     mailOperationsDetailsRequestType.setDamageRemarks(null);
     mailOperationsDetailsRequestType.setOffloadReason(null);
     mailOperationsDetailsRequestType.setReturnCode(null);
     mailOperationsDetailsRequestType.setToContainerType(null);
     mailOperationsDetailsRequestType.setToContainer(null);
     mailOperationsDetailsRequestType.setToCarrierCode(null);
     mailOperationsDetailsRequestType.setToFlightNumber(null);
     mailOperationsDetailsRequestType.setToFlightDate(null);
     mailOperationsDetailsRequestType.setToContainerPOU(null);
     mailOperationsDetailsRequestType.setToContainerDestination(null);
     mailOperationsDetailsRequestType.setConsignmentDocumentNumber(null);
     mailOperationsDetailsRequestType.setSerialNumber("1");
     mailOperationsDetailsRequestType.setIsPABuilt(null);
     mailOperationsDetailsRequestType.setIsDelivered(null);
     mailOperationsDetailsRequestType.setUserName("SATSGHA");
     mailOperationsDetailsRequestType.setScanDateTime("07-SEP-2014 10:30:05");
     list.add(mailOperationsDetailsRequestType);
     request.setMailDetails(list);
     MailSaveDetailsResponseType response = (MailSaveDetailsResponseType) webServiceTemplate.marshalSendAndReceive(request);
     assertNotNull(response);
     assertNotNull(response.getCompanyCode());
     assertNotNull(response.getErrorFlag());
     assertNotNull(response.getHhtVersion());
     assertNotNull(response.getScanningPort());
     if (lOGGER.isInfoEnabled()) {
        lOGGER.info("Load a mail bag into a container/MT (acceptance at carrier level) ===>\n", response.toString());
     }
   }
   
   // 6) Remove a mail bag from a container/MT
   
   @Test
   public void removeMailBagFromContainer()  {
      List<MailOperationsDetailsRequestType> list= new ArrayList<>();
      MailOperationsDetailsRequestType mailOperationsDetailsRequestType = new ObjectFactory().createMailOperationsDetailsRequestType();
      MailSaveDetailsRequestType request = new ObjectFactory().createMailSaveDetailsRequestType();
     request.setCompanyCode("SQ");
     request.setHhtVersion("1.0");
     request.setMessagePartId(1);
     request.setScanningPort("SIN");
     mailOperationsDetailsRequestType.setProduct("mailtracking.defaults");
     mailOperationsDetailsRequestType.setScanType("RSN");
     mailOperationsDetailsRequestType.setCarrierCode("SQ");
     mailOperationsDetailsRequestType.setFlightNumber(null);
     mailOperationsDetailsRequestType.setFlightDate(null);
     mailOperationsDetailsRequestType.setContainerPou(null);
     mailOperationsDetailsRequestType.setContainerNumber(null);
     mailOperationsDetailsRequestType.setContainerType(null);
     mailOperationsDetailsRequestType.setContainerDestination(null);
     mailOperationsDetailsRequestType.setContainerPol(null);
     mailOperationsDetailsRequestType.setRemarks(null);
     mailOperationsDetailsRequestType.setMailTag("DEFRAAINTRVAGCA41596002191000");
     mailOperationsDetailsRequestType.setDamageCode(null);
     mailOperationsDetailsRequestType.setDamageRemarks(null);
     mailOperationsDetailsRequestType.setOffloadReason("100");
     mailOperationsDetailsRequestType.setReturnCode(null);
     mailOperationsDetailsRequestType.setToContainerType(null);
     mailOperationsDetailsRequestType.setToContainer(null);
     mailOperationsDetailsRequestType.setToCarrierCode(null);
     mailOperationsDetailsRequestType.setToFlightNumber(null);
     mailOperationsDetailsRequestType.setToFlightDate(null);
     mailOperationsDetailsRequestType.setToContainerPOU(null);
     mailOperationsDetailsRequestType.setToContainerDestination(null);
     mailOperationsDetailsRequestType.setConsignmentDocumentNumber(null);
     mailOperationsDetailsRequestType.setSerialNumber("1");
     mailOperationsDetailsRequestType.setIsPABuilt(null);
     mailOperationsDetailsRequestType.setIsDelivered(null);
     mailOperationsDetailsRequestType.setUserName("SATSGHA");
     mailOperationsDetailsRequestType.setScanDateTime("07-SEP-2014 10:30:05");
     list.add(mailOperationsDetailsRequestType);
     request.setMailDetails(list);
     MailSaveDetailsResponseType response = (MailSaveDetailsResponseType) webServiceTemplate.marshalSendAndReceive(request);
     assertNotNull(response);
     assertNotNull(response.getCompanyCode());
     assertNotNull(response.getErrorFlag());
     assertNotNull(response.getHhtVersion());
     assertNotNull(response.getScanningPort());
     if (lOGGER.isInfoEnabled()) {
        lOGGER.info("Remove a mail bag from a container/MT ===>\n", response.toString());
     }
   }
   
   // 7) Receive (arrive) a mail bag from a flight
   @Test
   public void recieveMailBagFromFlight()  {
      List<MailOperationsDetailsRequestType> list= new ArrayList<>();
      MailOperationsDetailsRequestType mailOperationsDetailsRequestType = new ObjectFactory().createMailOperationsDetailsRequestType();
      MailSaveDetailsRequestType request = new ObjectFactory().createMailSaveDetailsRequestType();
     request.setCompanyCode("SQ");
     request.setHhtVersion("1.0");
     request.setMessagePartId(1);
     request.setScanningPort("SIN");
     mailOperationsDetailsRequestType.setProduct("mailtracking.defaults");
     mailOperationsDetailsRequestType.setScanType("APR");
     mailOperationsDetailsRequestType.setCarrierCode("SQ");
     mailOperationsDetailsRequestType.setFlightNumber("1201");
     mailOperationsDetailsRequestType.setFlightDate("10-OCT-2014");
     mailOperationsDetailsRequestType.setContainerPou(null);
     mailOperationsDetailsRequestType.setContainerNumber(null);
     mailOperationsDetailsRequestType.setContainerType(null);
     mailOperationsDetailsRequestType.setContainerDestination(null);
     mailOperationsDetailsRequestType.setContainerPol(null);
     mailOperationsDetailsRequestType.setRemarks(null);
     mailOperationsDetailsRequestType.setMailTag("DEFRAAINTRVAGCA41596002191000");
     mailOperationsDetailsRequestType.setDamageCode(null);
     mailOperationsDetailsRequestType.setDamageRemarks(null);
     mailOperationsDetailsRequestType.setOffloadReason(null);
     mailOperationsDetailsRequestType.setReturnCode(null);
     mailOperationsDetailsRequestType.setToContainerType(null);
     mailOperationsDetailsRequestType.setToContainer(null);
     mailOperationsDetailsRequestType.setToCarrierCode(null);
     mailOperationsDetailsRequestType.setToFlightNumber(null);
     mailOperationsDetailsRequestType.setToFlightDate(null);
     mailOperationsDetailsRequestType.setToContainerPOU(null);
     mailOperationsDetailsRequestType.setToContainerDestination(null);
     mailOperationsDetailsRequestType.setConsignmentDocumentNumber(null);
     mailOperationsDetailsRequestType.setSerialNumber("1");
     mailOperationsDetailsRequestType.setIsPABuilt(null);
     mailOperationsDetailsRequestType.setIsDelivered(null);
     mailOperationsDetailsRequestType.setUserName("SATSGHA");
     mailOperationsDetailsRequestType.setScanDateTime("07-SEP-2014 10:30:05");
     list.add(mailOperationsDetailsRequestType);
     request.setMailDetails(list);
     MailSaveDetailsResponseType response = (MailSaveDetailsResponseType) webServiceTemplate.marshalSendAndReceive(request);
     assertNotNull(response);
     assertNotNull(response.getCompanyCode());
     assertNotNull(response.getErrorFlag());
     assertNotNull(response.getHhtVersion());
     assertNotNull(response.getScanningPort());
     if (lOGGER.isInfoEnabled()) {
        lOGGER.info("Receive (arrive) a mail bag from a flight ===>\n", response.toString());
     }
   }
   
   // 8) Deliver a mail bag to Postal Authority
   
   @Test
   public void deliverMailBagToPA()  {
      List<MailOperationsDetailsRequestType> list= new ArrayList<>();
      MailOperationsDetailsRequestType mailOperationsDetailsRequestType = new ObjectFactory().createMailOperationsDetailsRequestType();
      MailSaveDetailsRequestType request = new ObjectFactory().createMailSaveDetailsRequestType();
     request.setCompanyCode("SQ");
     request.setHhtVersion("1.0");
     request.setMessagePartId(1);
     request.setScanningPort("SIN");
     mailOperationsDetailsRequestType.setProduct("mailtracking.defaults");
     mailOperationsDetailsRequestType.setScanType("DLV");
     mailOperationsDetailsRequestType.setCarrierCode("SQ");
     mailOperationsDetailsRequestType.setFlightNumber(null);
     mailOperationsDetailsRequestType.setFlightDate(null);
     mailOperationsDetailsRequestType.setContainerPou(null);
     mailOperationsDetailsRequestType.setContainerNumber(null);
     mailOperationsDetailsRequestType.setContainerType(null);
     mailOperationsDetailsRequestType.setContainerDestination(null);
     mailOperationsDetailsRequestType.setContainerPol(null);
     mailOperationsDetailsRequestType.setRemarks(null);
     mailOperationsDetailsRequestType.setMailTag("DEFRAAINTRVAGCA41596002191000");
     mailOperationsDetailsRequestType.setDamageCode(null);
     mailOperationsDetailsRequestType.setDamageRemarks(null);
     mailOperationsDetailsRequestType.setOffloadReason(null);
     mailOperationsDetailsRequestType.setReturnCode(null);
     mailOperationsDetailsRequestType.setToContainerType(null);
     mailOperationsDetailsRequestType.setToContainer(null);
     mailOperationsDetailsRequestType.setToCarrierCode(null);
     mailOperationsDetailsRequestType.setToFlightNumber(null);
     mailOperationsDetailsRequestType.setToFlightDate(null);
     mailOperationsDetailsRequestType.setToContainerPOU(null);
     mailOperationsDetailsRequestType.setToContainerDestination(null);
     mailOperationsDetailsRequestType.setConsignmentDocumentNumber(null);
     mailOperationsDetailsRequestType.setSerialNumber("1");
     mailOperationsDetailsRequestType.setIsPABuilt(null);
     mailOperationsDetailsRequestType.setIsDelivered(null);
     mailOperationsDetailsRequestType.setUserName("SATSGHA");
     mailOperationsDetailsRequestType.setScanDateTime("07-SEP-2014 10:30:05");
     list.add(mailOperationsDetailsRequestType);
     request.setMailDetails(list);
     MailSaveDetailsResponseType response = (MailSaveDetailsResponseType) webServiceTemplate.marshalSendAndReceive(request);
     assertNotNull(response);
     assertNotNull(response.getCompanyCode());
     assertNotNull(response.getErrorFlag());
     assertNotNull(response.getHhtVersion());
     assertNotNull(response.getScanningPort());
     if (lOGGER.isInfoEnabled()) {
        lOGGER.info("Deliver a mail bag to Postal Authority ===>\n", response.toString());
     }
   }
   
   // 9) Delete a mail bag from system
   
   @Test
   public void deleteMailBagFromSystem()  {
      List<MailOperationsDetailsRequestType> list= new ArrayList<>();
      MailOperationsDetailsRequestType mailOperationsDetailsRequestType = new ObjectFactory().createMailOperationsDetailsRequestType();
      MailSaveDetailsRequestType request = new ObjectFactory().createMailSaveDetailsRequestType();
     request.setCompanyCode("SQ");
     request.setHhtVersion("1.0");
     request.setMessagePartId(1);
     request.setScanningPort("SIN");
     mailOperationsDetailsRequestType.setProduct("mailtracking.defaults");
     mailOperationsDetailsRequestType.setScanType("DEL");
     mailOperationsDetailsRequestType.setCarrierCode("SQ");
     mailOperationsDetailsRequestType.setFlightNumber(null);
     mailOperationsDetailsRequestType.setFlightDate(null);
     mailOperationsDetailsRequestType.setContainerPou(null);
     mailOperationsDetailsRequestType.setContainerNumber(null);
     mailOperationsDetailsRequestType.setContainerType(null);
     mailOperationsDetailsRequestType.setContainerDestination(null);
     mailOperationsDetailsRequestType.setContainerPol(null);
     mailOperationsDetailsRequestType.setRemarks(null);
     mailOperationsDetailsRequestType.setMailTag("DEFRAAINTRVAGCA41596002191000");
     mailOperationsDetailsRequestType.setDamageCode(null);
     mailOperationsDetailsRequestType.setDamageRemarks(null);
     mailOperationsDetailsRequestType.setOffloadReason(null);
     mailOperationsDetailsRequestType.setReturnCode(null);
     mailOperationsDetailsRequestType.setToContainerType(null);
     mailOperationsDetailsRequestType.setToContainer(null);
     mailOperationsDetailsRequestType.setToCarrierCode(null);
     mailOperationsDetailsRequestType.setToFlightNumber(null);
     mailOperationsDetailsRequestType.setToFlightDate(null);
     mailOperationsDetailsRequestType.setToContainerPOU(null);
     mailOperationsDetailsRequestType.setToContainerDestination(null);
     mailOperationsDetailsRequestType.setConsignmentDocumentNumber(null);
     mailOperationsDetailsRequestType.setSerialNumber("1");
     mailOperationsDetailsRequestType.setIsPABuilt(null);
     mailOperationsDetailsRequestType.setIsDelivered(null);
     mailOperationsDetailsRequestType.setUserName("SATSGHA");
     mailOperationsDetailsRequestType.setScanDateTime("07-SEP-2014 10:30:05");
     list.add(mailOperationsDetailsRequestType);
     request.setMailDetails(list);
     MailSaveDetailsResponseType response = (MailSaveDetailsResponseType) webServiceTemplate.marshalSendAndReceive(request);
     assertNotNull(response);
     assertNotNull(response.getCompanyCode());
     assertNotNull(response.getErrorFlag());
     assertNotNull(response.getHhtVersion());
     assertNotNull(response.getScanningPort());
     if (lOGGER.isInfoEnabled()) {
        lOGGER.info("Delete a mail bag from system ===>\n", response.toString());
     }
   }
   
   // 10) Report Damage
   @Test
   public void reportDamage()  {
      List<MailOperationsDetailsRequestType> list= new ArrayList<>();
      MailOperationsDetailsRequestType mailOperationsDetailsRequestType = new ObjectFactory().createMailOperationsDetailsRequestType();
      MailSaveDetailsRequestType request = new ObjectFactory().createMailSaveDetailsRequestType();
     request.setCompanyCode("SQ");
     request.setHhtVersion("1.0");
     request.setMessagePartId(1);
     request.setScanningPort("SIN");
     mailOperationsDetailsRequestType.setProduct("mailtracking.defaults");
     mailOperationsDetailsRequestType.setScanType("DMG");
     mailOperationsDetailsRequestType.setCarrierCode("SQ");
     mailOperationsDetailsRequestType.setFlightNumber(null);
     mailOperationsDetailsRequestType.setFlightDate(null);
     mailOperationsDetailsRequestType.setContainerPou(null);
     mailOperationsDetailsRequestType.setContainerNumber(null);
     mailOperationsDetailsRequestType.setContainerType(null);
     mailOperationsDetailsRequestType.setContainerDestination(null);
     mailOperationsDetailsRequestType.setContainerPol(null);
     mailOperationsDetailsRequestType.setRemarks(null);
     mailOperationsDetailsRequestType.setMailTag("DEFRAAINTRVAGCA41596002191000");
     mailOperationsDetailsRequestType.setDamageCode("100");
     mailOperationsDetailsRequestType.setDamageRemarks("Broken");
     mailOperationsDetailsRequestType.setOffloadReason(null);
     mailOperationsDetailsRequestType.setReturnCode(null);
     mailOperationsDetailsRequestType.setToContainerType(null);
     mailOperationsDetailsRequestType.setToContainer(null);
     mailOperationsDetailsRequestType.setToCarrierCode(null);
     mailOperationsDetailsRequestType.setToFlightNumber(null);
     mailOperationsDetailsRequestType.setToFlightDate(null);
     mailOperationsDetailsRequestType.setToContainerPOU(null);
     mailOperationsDetailsRequestType.setToContainerDestination(null);
     mailOperationsDetailsRequestType.setConsignmentDocumentNumber(null);
     mailOperationsDetailsRequestType.setSerialNumber("1");
     mailOperationsDetailsRequestType.setIsPABuilt(null);
     mailOperationsDetailsRequestType.setIsDelivered(null);
     mailOperationsDetailsRequestType.setUserName("SATSGHA");
     mailOperationsDetailsRequestType.setScanDateTime("07-SEP-2014 10:30:05");
     list.add(mailOperationsDetailsRequestType);
     request.setMailDetails(list);
     MailSaveDetailsResponseType response = (MailSaveDetailsResponseType) webServiceTemplate.marshalSendAndReceive(request);
     assertNotNull(response);
     assertNotNull(response.getCompanyCode());
     assertNotNull(response.getErrorFlag());
     assertNotNull(response.getHhtVersion());
     assertNotNull(response.getScanningPort());
     if (lOGGER.isInfoEnabled()) {
        lOGGER.info("Report Damage ===>\n", response.toString());
     }
   }
   
   // 11) Book a container/MT on a flight
   
   @Test
   public void bookContainerOnFlight()  {
      List<MailOperationsDetailsRequestType> list= new ArrayList<>();
      MailOperationsDetailsRequestType mailOperationsDetailsRequestType = new ObjectFactory().createMailOperationsDetailsRequestType();
      MailSaveDetailsRequestType request = new ObjectFactory().createMailSaveDetailsRequestType();
     request.setCompanyCode("SQ");
     request.setHhtVersion("1.0");
     request.setMessagePartId(1);
     request.setScanningPort("SIN");
     mailOperationsDetailsRequestType.setProduct("mailtracking.defaults");
     mailOperationsDetailsRequestType.setScanType("EXP");
     mailOperationsDetailsRequestType.setCarrierCode("SQ");
     mailOperationsDetailsRequestType.setFlightNumber("1201");
     mailOperationsDetailsRequestType.setFlightDate("10-OCT-2014");
     mailOperationsDetailsRequestType.setContainerPou("SYD");
     mailOperationsDetailsRequestType.setContainerNumber("AKE9876AV");
     mailOperationsDetailsRequestType.setContainerType("ULD");
     mailOperationsDetailsRequestType.setContainerDestination("LAX");
     mailOperationsDetailsRequestType.setContainerPol(null);
     mailOperationsDetailsRequestType.setRemarks(null);
     mailOperationsDetailsRequestType.setMailTag("DEFRAAINTRVAGCA41596002191000");
     mailOperationsDetailsRequestType.setDamageCode(null);
     mailOperationsDetailsRequestType.setDamageRemarks(null);
     mailOperationsDetailsRequestType.setOffloadReason(null);
     mailOperationsDetailsRequestType.setReturnCode(null);
     mailOperationsDetailsRequestType.setToContainerType(null);
     mailOperationsDetailsRequestType.setToContainer(null);
     mailOperationsDetailsRequestType.setToCarrierCode(null);
     mailOperationsDetailsRequestType.setToFlightNumber(null);
     mailOperationsDetailsRequestType.setToFlightDate(null);
     mailOperationsDetailsRequestType.setToContainerPOU(null);
     mailOperationsDetailsRequestType.setToContainerDestination(null);
     mailOperationsDetailsRequestType.setConsignmentDocumentNumber(null);
     mailOperationsDetailsRequestType.setSerialNumber("1");
     mailOperationsDetailsRequestType.setIsPABuilt(null);
     mailOperationsDetailsRequestType.setIsDelivered(null);
     mailOperationsDetailsRequestType.setUserName("SATSGHA");
     mailOperationsDetailsRequestType.setScanDateTime("07-SEP-2014 10:30:05");
     list.add(mailOperationsDetailsRequestType);
     request.setMailDetails(list);
     MailSaveDetailsResponseType response = (MailSaveDetailsResponseType) webServiceTemplate.marshalSendAndReceive(request);
     assertNotNull(response);
     assertNotNull(response.getCompanyCode());
     assertNotNull(response.getErrorFlag());
     assertNotNull(response.getHhtVersion());
     assertNotNull(response.getScanningPort());
     if (lOGGER.isInfoEnabled()) {
        lOGGER.info("Book a container/MT on a flight ===>\n", response.toString());
     }
   }
   
   // 12) Cancel the current booking for a container
   
   @Test
   public void cancelCurrentBookingContainer()  {
      List<MailOperationsDetailsRequestType> list= new ArrayList<>();
      MailOperationsDetailsRequestType mailOperationsDetailsRequestType = new ObjectFactory().createMailOperationsDetailsRequestType();
      MailSaveDetailsRequestType request = new ObjectFactory().createMailSaveDetailsRequestType();
     request.setCompanyCode("SQ");
     request.setHhtVersion("1.0");
     request.setMessagePartId(1);
     request.setScanningPort("SIN");
     mailOperationsDetailsRequestType.setProduct("mailtracking.defaults");
     mailOperationsDetailsRequestType.setScanType("CNL");
     mailOperationsDetailsRequestType.setCarrierCode("SQ");
     mailOperationsDetailsRequestType.setFlightNumber(null);
     mailOperationsDetailsRequestType.setFlightDate(null);
     mailOperationsDetailsRequestType.setContainerPou(null);
     mailOperationsDetailsRequestType.setContainerNumber("AKE9876AV");
     mailOperationsDetailsRequestType.setContainerType(null);
     mailOperationsDetailsRequestType.setContainerDestination(null);
     mailOperationsDetailsRequestType.setContainerPol(null);
     mailOperationsDetailsRequestType.setRemarks(null);
     mailOperationsDetailsRequestType.setMailTag("DEFRAAINTRVAGCA41596002191000");
     mailOperationsDetailsRequestType.setDamageCode(null);
     mailOperationsDetailsRequestType.setDamageRemarks(null);
     mailOperationsDetailsRequestType.setOffloadReason("100");
     mailOperationsDetailsRequestType.setReturnCode(null);
     mailOperationsDetailsRequestType.setToContainerType(null);
     mailOperationsDetailsRequestType.setToContainer(null);
     mailOperationsDetailsRequestType.setToCarrierCode(null);
     mailOperationsDetailsRequestType.setToFlightNumber(null);
     mailOperationsDetailsRequestType.setToFlightDate(null);
     mailOperationsDetailsRequestType.setToContainerPOU(null);
     mailOperationsDetailsRequestType.setToContainerDestination(null);
     mailOperationsDetailsRequestType.setConsignmentDocumentNumber(null);
     mailOperationsDetailsRequestType.setSerialNumber("1");
     mailOperationsDetailsRequestType.setIsPABuilt(null);
     mailOperationsDetailsRequestType.setIsDelivered(null);
     mailOperationsDetailsRequestType.setUserName("SATSGHA");
     mailOperationsDetailsRequestType.setScanDateTime("07-SEP-2014 10:30:05");
     list.add(mailOperationsDetailsRequestType);
     request.setMailDetails(list);
     MailSaveDetailsResponseType response = (MailSaveDetailsResponseType) webServiceTemplate.marshalSendAndReceive(request);
     assertNotNull(response);
     assertNotNull(response.getCompanyCode());
     assertNotNull(response.getErrorFlag());
     assertNotNull(response.getHhtVersion());
     assertNotNull(response.getScanningPort());
     if (lOGGER.isInfoEnabled()) {
        lOGGER.info("Cancel the current booking for a container ===>\n", response.toString());
     }
   }
   
   // 13) Receive a container from a flight
   
   @Test
   public void reciveContainerFromFlight()  {
      List<MailOperationsDetailsRequestType> list= new ArrayList<>();
      MailOperationsDetailsRequestType mailOperationsDetailsRequestType = new ObjectFactory().createMailOperationsDetailsRequestType();
      MailSaveDetailsRequestType request = new ObjectFactory().createMailSaveDetailsRequestType();
     request.setCompanyCode("SQ");
     request.setHhtVersion("1.0");
     request.setMessagePartId(1);
     request.setScanningPort("SIN");
     mailOperationsDetailsRequestType.setProduct("mailtracking.defaults");
     mailOperationsDetailsRequestType.setScanType("APR");
     mailOperationsDetailsRequestType.setCarrierCode("SQ");
     mailOperationsDetailsRequestType.setFlightNumber("1201");
     mailOperationsDetailsRequestType.setFlightDate("10-OCT-2014");
     mailOperationsDetailsRequestType.setContainerPou(null);
     mailOperationsDetailsRequestType.setContainerNumber("AKE9876AV");
     mailOperationsDetailsRequestType.setContainerType(null);
     mailOperationsDetailsRequestType.setContainerDestination(null);
     mailOperationsDetailsRequestType.setContainerPol(null);
     mailOperationsDetailsRequestType.setRemarks(null);
     mailOperationsDetailsRequestType.setMailTag("DEFRAAINTRVAGCA41596002191000");
     mailOperationsDetailsRequestType.setDamageCode(null);
     mailOperationsDetailsRequestType.setDamageRemarks(null);
     mailOperationsDetailsRequestType.setOffloadReason(null);
     mailOperationsDetailsRequestType.setReturnCode(null);
     mailOperationsDetailsRequestType.setToContainerType(null);
     mailOperationsDetailsRequestType.setToContainer(null);
     mailOperationsDetailsRequestType.setToCarrierCode(null);
     mailOperationsDetailsRequestType.setToFlightNumber(null);
     mailOperationsDetailsRequestType.setToFlightDate(null);
     mailOperationsDetailsRequestType.setToContainerPOU(null);
     mailOperationsDetailsRequestType.setToContainerDestination(null);
     mailOperationsDetailsRequestType.setConsignmentDocumentNumber(null);
     mailOperationsDetailsRequestType.setSerialNumber("1");
     mailOperationsDetailsRequestType.setIsPABuilt(null);
     mailOperationsDetailsRequestType.setIsDelivered(null);
     mailOperationsDetailsRequestType.setUserName("SATSGHA");
     mailOperationsDetailsRequestType.setScanDateTime("07-SEP-2014 10:30:05");
     list.add(mailOperationsDetailsRequestType);
     request.setMailDetails(list);
     MailSaveDetailsResponseType response = (MailSaveDetailsResponseType) webServiceTemplate.marshalSendAndReceive(request);
     assertNotNull(response);
     assertNotNull(response.getCompanyCode());
     assertNotNull(response.getErrorFlag());
     assertNotNull(response.getHhtVersion());
     assertNotNull(response.getScanningPort());
     if (lOGGER.isInfoEnabled()) {
        lOGGER.info("Receive a container from a flight ===>\n", response.toString());
     }
   }
   
   // 14) Deliver a container to Postal Authority
   @Test
   public void deliverContinerToPA()  {
      List<MailOperationsDetailsRequestType> list= new ArrayList<>();
      MailOperationsDetailsRequestType mailOperationsDetailsRequestType = new ObjectFactory().createMailOperationsDetailsRequestType();
      MailSaveDetailsRequestType request = new ObjectFactory().createMailSaveDetailsRequestType();
     request.setCompanyCode("SQ");
     request.setHhtVersion("1.0");
     request.setMessagePartId(1);
     request.setScanningPort("SIN");
     mailOperationsDetailsRequestType.setProduct("mailtracking.defaults");
     mailOperationsDetailsRequestType.setScanType("DLV");
     mailOperationsDetailsRequestType.setCarrierCode("SQ");
     mailOperationsDetailsRequestType.setFlightNumber(null);
     mailOperationsDetailsRequestType.setFlightDate(null);
     mailOperationsDetailsRequestType.setContainerPou(null);
     mailOperationsDetailsRequestType.setContainerNumber("AKE9876AV");
     mailOperationsDetailsRequestType.setContainerType(null);
     mailOperationsDetailsRequestType.setContainerDestination(null);
     mailOperationsDetailsRequestType.setContainerPol(null);
     mailOperationsDetailsRequestType.setRemarks(null);
     mailOperationsDetailsRequestType.setMailTag("DEFRAAINTRVAGCA41596002191000");
     mailOperationsDetailsRequestType.setDamageCode(null);
     mailOperationsDetailsRequestType.setDamageRemarks(null);
     mailOperationsDetailsRequestType.setOffloadReason(null);
     mailOperationsDetailsRequestType.setReturnCode(null);
     mailOperationsDetailsRequestType.setToContainerType(null);
     mailOperationsDetailsRequestType.setToContainer(null);
     mailOperationsDetailsRequestType.setToCarrierCode(null);
     mailOperationsDetailsRequestType.setToFlightNumber(null);
     mailOperationsDetailsRequestType.setToFlightDate(null);
     mailOperationsDetailsRequestType.setToContainerPOU(null);
     mailOperationsDetailsRequestType.setToContainerDestination(null);
     mailOperationsDetailsRequestType.setConsignmentDocumentNumber(null);
     mailOperationsDetailsRequestType.setSerialNumber("1");
     mailOperationsDetailsRequestType.setIsPABuilt(null);
     mailOperationsDetailsRequestType.setIsDelivered(null);
     mailOperationsDetailsRequestType.setUserName("SATSGHA");
     mailOperationsDetailsRequestType.setScanDateTime("07-SEP-2014 10:30:05");
     list.add(mailOperationsDetailsRequestType);
     request.setMailDetails(list);
     MailSaveDetailsResponseType response = (MailSaveDetailsResponseType) webServiceTemplate.marshalSendAndReceive(request);
     assertNotNull(response);
     assertNotNull(response.getCompanyCode());
     assertNotNull(response.getErrorFlag());
     assertNotNull(response.getHhtVersion());
     assertNotNull(response.getScanningPort());
     if (lOGGER.isInfoEnabled()) {
        lOGGER.info("Deliver a container to Postal Authority ===>\n", response.toString());
     }
   }
   
}
*/