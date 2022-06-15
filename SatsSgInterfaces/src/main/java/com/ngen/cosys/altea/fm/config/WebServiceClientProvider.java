/**
 * {@link WebServiceClientProvider}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.altea.fm.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.transport.TransportConstants;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ngen.cosys.altea.fm.common.AlteaFMSOAPConstants;
import com.ngen.cosys.altea.fm.common.WebServiceClientConstants;
import com.ngen.cosys.altea.fm.logger.AlteaFMLogger;
import com.ngen.cosys.altea.fm.util.WebServiceMessageUtils;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;

/**
 * Base Web Service Client Provider for SOAP models
 * 
 * @author NIIT Technologies Ltd
 */
public abstract class WebServiceClientProvider extends WebServiceGatewaySupport
      implements BeanPostProcessor, WebServiceMessageCallback {

   protected final Logger LOG = LoggerFactory.getLogger(WebServiceClientProvider.class);
   
   @Autowired
   @Qualifier(WebServiceClientConstants.JAXB_2_MARSHALLER_BEAN)
   private Jaxb2Marshaller jaxb2Marshaller;
   
   @Autowired
   @Qualifier(WebServiceClientConstants.WEB_SERVICE_SOAP_1_2_MESSAGE_FACTORY_BEAN)
   private WebServiceMessageFactory webServiceMessageFactory;
   
   @Autowired
   @Qualifier(WebServiceClientConstants.WEB_SERVICE_MESSAGE_SENDER_BEAN)
   private WebServiceMessageSender messageSender;
   
   private ApplicationLoggerService loggerService;
   private OutgoingMessageLog outgoingMessage;
   private SoapVersion soapVersion;
   
   /**
    * Initialize
    * 
    * @param soapVersion
    */
   public WebServiceClientProvider() {
      this.soapVersion = SoapVersion.SOAP_11;
   }
   
   /**
    * Set Outgoing Message
    * 
    * @param eventSource
    */
   private void setOutgoingMessageLog(OutgoingMessageLog outgoingMessage) {
      this.outgoingMessage = outgoingMessage;
   }
   
   /**
    * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object,
    *      java.lang.String)
    */
   @Override
   public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
      return bean;
   }

   /**
    * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object,
    *      java.lang.String)
    */
   @Override
   public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
      return bean;
   }

   /**
    * Sets Web Service Security Interceptor
    * 
    * @param webServiceSecurityInterceptor
    */
   public void setWebServiceSecurityInterceptor(Wss4jSecurityInterceptor webServiceSecurityInterceptor) {
      // Enable Security Interceptor
      setInterceptors(getInterceptors());
   }
   
   /**
    * Set Application Logger Service
    * 
    * @param loggerService
    */
   private void setApplicationLoggerService(ApplicationLoggerService loggerService) {
      this.loggerService = loggerService;
   }
   
   /**
    * Send SOAP Web Service Request
    * 
    * @param uri
    *           Web Service URI
    * @param payload
    *           Request Payload
    * @param callback
    *           Web Service Message Callback
    * @return response
    */
   public Object marshalSendAndReceive(String uri, Object payload, WebServiceMessageCallback callback) {
      LOG.debug("Marshal Send & Receive");
      WebServiceTemplate webServiceTemplate = getWebServiceTemplate();
      // Set URI
      setDefaultUri(uri);
      // Set Marshaller & Unmarshaller
      if (Objects.nonNull(jaxb2Marshaller)) {
         setMarshaller(jaxb2Marshaller);
         setUnmarshaller(jaxb2Marshaller);
      }
      // Set SOAP Message Factory
      if (Objects.nonNull(webServiceMessageFactory)) {
         setMessageFactory(webServiceMessageFactory);
      }
      // Set Message Sender
      if (Objects.nonNull(messageSender)) {
         setMessageSender(messageSender);
      }
      //
      return webServiceTemplate.marshalSendAndReceive(payload, callback);
   }
   
   /**
    * Send SOAP Web Service Request
    * 
    * @param uri
    *           Web Service URI
    * @param eventSource
    *           Event Source
    * @param payload
    *           Request Payload
    * @return response
    */
   public Object marshalSendAndReceive(String uri, Object payload) {
      return marshalSendAndReceive(uri, payload, this);
   }
   
   /**
    * Send SOAP Web Service Request
    * 
    * @param uri
    *           Web Service URI
    * @param payload
    *           Request Payload
    * @param outgoingMessage
    *           OutgoingMessageLog
    * @param webServiceSecurityInterceptor
    *           Web Service Security Interceptor
    * @param loggerService
    *           Application Logger Service
    * @return response
    */
   public Object marshalSendAndReceive(String uri, Object payload, OutgoingMessageLog outgoingMessage,
         Wss4jSecurityInterceptor webServiceSecurityInterceptor, ApplicationLoggerService loggerService) {
      // Security Interceptor
      setWebServiceSecurityInterceptor(webServiceSecurityInterceptor);
      // Application Logger Service
      setApplicationLoggerService(loggerService);
      // Set Outgoing Message Log
      setOutgoingMessageLog(outgoingMessage);
      //
      return marshalSendAndReceive(uri, payload, this);
   }
   
   /**
    * @param soapHeader
    * @throws SOAPException
    */
   private void initializeSecurityHostedUserElement(SOAPHeader soapHeader) throws SOAPException {
      SOAPHeaderElement securityHostedUserElement = WebServiceMessageUtils.headerElement(soapHeader,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_HOSTED_USER_NAMESPACE,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_HOSTED_USER_NAMESPACE_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_HOSTED_USER_NAMESPACE_VALUE);
      // Security UserID
      SOAPElement securityUserIDElement = WebServiceMessageUtils.soapElement(securityHostedUserElement,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_USER_ID_NAMESPACE,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_HOSTED_USER_NAMESPACE_PREFIX);
      // Attributes
      WebServiceMessageUtils.addAttribute(securityUserIDElement,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_USER_ID_POS_TYP_KEY,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_USER_ID_POS_TYP_VALUE);
      WebServiceMessageUtils.addAttribute(securityUserIDElement,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_USER_ID_REQUESTOR_TYPE,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_USER_ID_REQUESTOR_VALUE);
      WebServiceMessageUtils.addAttribute(securityUserIDElement,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_CITY_CODE_KEY,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_CITY_CODE_VALUE);
      WebServiceMessageUtils.addAttribute(securityUserIDElement,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_DUTY_CODE_KEY,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_DUTY_CODE_VALUE);
      // Type RequestorID
      SOAPElement requestorIDElement = WebServiceMessageUtils.soapElement(securityUserIDElement,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE_PREFIX);
      requestorIDElement.addNamespaceDeclaration(
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE_TYP_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE_TYP_VALUE);
      requestorIDElement.addNamespaceDeclaration(
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE_IAT_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE_IAT_VALUE);
      // CompanyName
      SOAPElement companyNameElement = WebServiceMessageUtils.soapElement(requestorIDElement,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_COMPANY_NAME_NAMESPACE,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_COMPANY_NAME_NAMESPACE_PREFIX);
      companyNameElement.addTextNode(AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_COMPANY_NAME_NODE_VALUE);
   }
   
   /**
    * @param soapHeader
    * @throws SOAPException
    */
   private void initializeWSSecurityElement(SOAPEnvelope soapEnvelope, SOAPHeader soapHeader) throws SOAPException {
      //
      String nonce = WebServiceMessageUtils.generateNonce(WebServiceClientConstants.NONCE_ALGORITHM);
      String created = WebServiceMessageUtils.generateCreated(WebServiceClientConstants.CREATED_TIMEFORMAT,
            WebServiceClientConstants.TIME_ZONE_ZULU);
      String password = AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_PASSWORD_VALUE;
      String passwordDigest = WebServiceMessageUtils.generatePasswordDigest(password, nonce, created,
            WebServiceClientConstants.PASSWORD_DIGEST_CRYPT);
      // WSSecurity Header Element
      SOAPHeaderElement wssecurityElement = WebServiceMessageUtils.headerElement(soapHeader,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_NAMESPACE,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_NAMESPACE_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_NAMESPACE_VALUE);
      wssecurityElement.addNamespaceDeclaration(AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_NAMESPACE_WSU_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_NAMESPACE_WSU_VALUE);
      // UserName Token Element
      SOAPElement usernameTokenElement = WebServiceMessageUtils.soapElement(wssecurityElement,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_USERNAME_TOKEN_NAMESPACE,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_NAMESPACE_PREFIX);
      // UserName Element
      SOAPElement usernameElement = WebServiceMessageUtils.soapElement(usernameTokenElement,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_USERNAME_NAMESPACE,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_NAMESPACE_PREFIX);
      usernameElement.addTextNode(AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_USERNAME_VALUE);
      // Password Element
      SOAPElement passwordElement = WebServiceMessageUtils.soapElement(usernameTokenElement,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_PASSWORD_NAMESAPCE,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_NAMESPACE_PREFIX);
      WebServiceMessageUtils.addAttribute(passwordElement,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_PASSWORD_NAMESPACE_TYPE_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_PASSWORD_NAMESPACE_TYPE_VALUE);
      passwordElement.addTextNode(passwordDigest);
      // Nonce Element
      SOAPElement nonceElement = WebServiceMessageUtils.soapElement(usernameTokenElement,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_NONCE_NAMESPACE,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_NAMESPACE_PREFIX);
      WebServiceMessageUtils.addAttribute(nonceElement,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_NONCE_NAMESPACE_ENCODING_TYPE_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_NONCE_NAMESPACE_ENCODING_TYPE_VALUE);
      nonceElement.addTextNode(nonce);
      // Created Element
      SOAPElement createdElement = WebServiceMessageUtils.soapElement(usernameTokenElement,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_CREATED_NAMESPACE,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_CREATED_NAMESPACE_PREFIX);
      createdElement.addTextNode(created);
   }
   
   /**
    * @param soapHeader
    * @throws SOAPException
    */
   private void initializeMessageConfig(SOAPHeader soapHeader) throws SOAPException {
      // Action Element
      SOAPHeaderElement actionElement = WebServiceMessageUtils.headerElement(soapHeader,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSADDRESS_ACTION,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSADDRESS_NAMESPACE_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSADDRESS_NAMESPACE_VALUE);
      actionElement.addTextNode(AlteaFMSOAPConstants.HEADER_ELEMENT_WSADDRESS_ACTION_KEY);
      // Message ID Element
      SOAPHeaderElement messageIDElement = WebServiceMessageUtils.headerElement(soapHeader,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSADDRESS_MESSAGE_ID,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSADDRESS_NAMESPACE_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSADDRESS_NAMESPACE_VALUE);
      // messageIDElement.addTextNode(AlteaFMSOAPConstants.HEADER_ELEMENT_WSADDRESS_MESSAGE_KEY);
      String messageKey = AlteaFMSOAPConstants.HEADER_ELEMENT_WSADDRESS_MESSAGE_UUID + UUID.randomUUID().toString();
      messageIDElement.addTextNode(messageKey);
      // TO Element
      SOAPHeaderElement toElement = WebServiceMessageUtils.headerElement(soapHeader,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSADDRESS_TO,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSADDRESS_NAMESPACE_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_WSADDRESS_NAMESPACE_VALUE);
      toElement.addTextNode(AlteaFMSOAPConstants.HEADER_ELEMENT_WSADDRESS_TO_KEY);
   }
   
   /**
    * @param envelope
    * @throws SOAPException
    */
   private void envelopeNamespaceDeclaration(SOAPEnvelope soapEnvelope) throws SOAPException {
      soapEnvelope.addNamespaceDeclaration(AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_HOSTED_USER_NAMESPACE_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_HOSTED_USER_NAMESPACE_VALUE);
      soapEnvelope.addNamespaceDeclaration(
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE_TYP_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE_TYP_VALUE);
      soapEnvelope.addNamespaceDeclaration(
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE_IAT_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE_IAT_VALUE);
      soapEnvelope.addNamespaceDeclaration(
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE_IAT_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE_IAT_VALUE);
      soapEnvelope.addNamespaceDeclaration(AlteaFMSOAPConstants.HEADER_ELEMENT_ENVELOPE_NAMESPACE_APP_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_ENVELOPE_NAMESPACE_APP_VALUE);
      soapEnvelope.addNamespaceDeclaration(AlteaFMSOAPConstants.HEADER_ELEMENT_ENVELOPE_NAMESPACE_LINK_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_ENVELOPE_NAMESPACE_LINK_VALUE);
      soapEnvelope.addNamespaceDeclaration(AlteaFMSOAPConstants.HEADER_ELEMENT_ENVELOPE_NAMESPACE_SES_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_ENVELOPE_NAMESPACE_SES_VALUE);
      soapEnvelope.addNamespaceDeclaration(AlteaFMSOAPConstants.HEADER_ELEMENT_ENVELOPE_NAMESPACE_FEC_PREFIX,
            AlteaFMSOAPConstants.HEADER_ELEMENT_ENVELOPE_NAMESPACE_FEC_VALUE);
   }
   
   /**
    * @see org.springframework.ws.client.core.WebServiceMessageCallback#doWithMessage(org.springframework.ws.WebServiceMessage)
    */
   @Override
   public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
      String payload = null;
      BigInteger messageLogId = null;
      try {
         SOAPMessage soapMessage = ((SaajSoapMessage) message).getSaajMessage();
         // Headers
         MimeHeaders headers = soapMessage.getMimeHeaders();
         headers.addHeader(TransportConstants.HEADER_CONTENT_TYPE, SoapVersion.SOAP_11.getContentType());
         headers.addHeader(TransportConstants.HEADER_ACCEPT, SoapVersion.SOAP_11.getContentType());
         headers.removeHeader(TransportConstants.HEADER_SOAP_ACTION);
         headers.addHeader(TransportConstants.HEADER_SOAP_ACTION,
               AlteaFMSOAPConstants.HEADER_ELEMENT_WSADDRESS_ACTION_KEY);
         //
         SOAPPart soapPart = soapMessage.getSOAPPart();
         SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
         // Namespace declaration
         envelopeNamespaceDeclaration(soapEnvelope);
         SOAPHeader soapHeader = soapEnvelope.getHeader(); // soapMessage.getSOAPHeader();
         soapHeader.addNamespaceDeclaration(AlteaFMSOAPConstants.HEADER_ELEMENT_ENVELOPE_NAMESPACE_WSA_PREFIX,
               AlteaFMSOAPConstants.HEADER_ELEMENT_ENVELOPE_NAMESPACE_WSA_VALUE);
         // Security Hosted User
         initializeSecurityHostedUserElement(soapHeader);
         // WSSecurity
         initializeWSSecurityElement(soapEnvelope, soapHeader);
         // Action, Message ID, To
         initializeMessageConfig(soapHeader);
         // Body
         // SOAPBody body = soapMessage.getSOAPBody();
         soapMessage.saveChanges();
         // Payload
         payload = transformXMLPayloadToString(soapMessage);
         // LOG
         outgoingMessage.setMessage(payload);
         loggerService.logInterfaceOutgoingMessage(outgoingMessage);
         messageLogId = Objects.nonNull(outgoingMessage) ? outgoingMessage.getOutMessageId() : null;
         LOG.warn("Web Service Client Provider - Outgoing Message Log ID :: {}", String.valueOf(messageLogId));
      } catch (SOAPException ex) {
         LOG.error("Web Service Client Provider - DoWithMessage SOAP Exception - {}", String.valueOf(ex));
         // Outgoing Message Log status update
         loggerService.logOutgoingMessage(
               AlteaFMLogger.getOutgoingMessageLogForUpdate(outgoingMessage, LocalDateTime.now(), EventStatus.ERROR));
         // Outgoing Error Message Log
         loggerService.logInterfaceOutgoingErrorMessage(
               AlteaFMLogger.getOutgoingErrorMessageLog(outgoingMessage, EventStatus.ERROR, String.valueOf(ex)));
      }
   }
   
   /**
    * Transform Payload to String format
    * 
    * @param message
    * @return
    */
   @Deprecated
   private void transformPayloadToString(SOAPMessage message) {
      ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
      String payload = null;
      try {
         message.writeTo(byteStream);
         payload = new String(byteStream.toByteArray());
      } catch (SOAPException | IOException ex) {
         LOG.error("SOAP Message transform to String exception - {}", ex);
      }
      LOG.warn("SOAP Message Transform to String {}", payload);
   }
   
   /**
    * @param message
    */
   private String transformXMLPayloadToString(SOAPMessage message) {
      String payload = null;
      //
      if (Objects.isNull(message)) {
         return payload;
      }
      try {
         Transformer transformer = TransformerFactory.newInstance().newTransformer();
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         //
         ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
         message.writeTo(byteStream);
         payload = new String(byteStream.toByteArray());
         // Convert XML String into a document
         Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
               .parse(new InputSource(new ByteArrayInputStream(payload.getBytes(StandardCharsets.UTF_8))));
         // Remove whitespace outside tags
         document.normalize();
         StreamResult result = new StreamResult(new StringWriter());
         DOMSource source = new DOMSource(document);
         transformer.transform(source, result);
         //
         payload = result.getWriter().toString();
         LOG.warn("SOAP Message XML Transform to String {}", payload);
      } catch (TransformerFactoryConfigurationError | SOAPException | IOException | SAXException
            | ParserConfigurationException | TransformerException ex) {
         LOG.error("SOAP Message XML transform to String exception - {}", ex);
      }
      return payload;
   }
   
}
