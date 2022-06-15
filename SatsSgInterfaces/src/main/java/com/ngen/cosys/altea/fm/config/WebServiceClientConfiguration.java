/**
 * {@link WebServiceClientConfiguration}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.altea.fm.config;

import javax.naming.NamingException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import com.ngen.cosys.altea.fm.common.WebServiceClientConstants;

/**
 * Web Service Client Configuration
 * 
 * @author NIIT Technologies Ltd
 */
@Configuration
public class WebServiceClientConfiguration {

   /**
    * Jaxb2Marshaller Bean Configuration
    * 
    * @return
    * @throws IllegalArgumentException
    * @throws NamingException
    */
   @Bean(name = WebServiceClientConstants.JAXB_2_MARSHALLER_BEAN)
   public Jaxb2Marshaller jaxb2Marshaller() throws IllegalArgumentException, NamingException {
      Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
      marshaller.setPackagesToScan(WebServiceClientConstants.BASE_PACKAGE_SCAN);
      return marshaller;
   }
   
   /**
    * Web Service Message Factory Bean Configuration
    * 
    * @return
    * @throws IllegalArgumentException
    * @throws NamingException
    * @throws SOAPException
    */
   @Bean(name = WebServiceClientConstants.WEB_SERVICE_SOAP_1_2_MESSAGE_FACTORY_BEAN)
   public WebServiceMessageFactory webServiceMessageFactory()
         throws IllegalArgumentException, NamingException, SOAPException {
      return new SaajSoapMessageFactory(MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL));
   }
   
   /**
    * Web Service Message Sender Bean Configuration
    * 
    * @return
    * @throws IllegalArgumentException
    * @throws NamingException
    */
   @Bean(name = WebServiceClientConstants.WEB_SERVICE_MESSAGE_SENDER_BEAN)
   public WebServiceMessageSender webServiceMessageSender() throws IllegalArgumentException, NamingException {
      HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
      // Timeout for creating a connection
      httpComponentsMessageSender.setConnectionTimeout(WebServiceClientConstants.TIME_OUT);
      // When you have a connection, timeout the read blocks for
      httpComponentsMessageSender.setReadTimeout(WebServiceClientConstants.TIME_OUT);
      return httpComponentsMessageSender;
   }
   
   /**
    * Web Service Security Interceptor Bean
    * 
    * @return
    * @throws IllegalArgumentException
    * @throws NamingException
    */
   @Bean(name = WebServiceClientConstants.WEB_SERVICE_SECURITY_INTERCEPTOR_BEAN)
   public Wss4jSecurityInterceptor wss4jSecurityInterceptor() throws IllegalArgumentException, NamingException {
      Wss4jSecurityInterceptor webServiceSecurityInterceptor = new Wss4jSecurityInterceptor();
      webServiceSecurityInterceptor.setSecurementUsername(WebServiceClientConstants.SECURITY_INTERCEPTOR_USERNAME);
      webServiceSecurityInterceptor.setSecurementPassword(WebServiceClientConstants.SECURITY_INTERCEPTOR_PASSWORD);
      webServiceSecurityInterceptor.setSecurementUsernameTokenNonce(true);
      webServiceSecurityInterceptor.setSecurementActions(WebServiceClientConstants.SECURITY_INTERCEPTOR_ACTIONS);
      return webServiceSecurityInterceptor;
   }
   
   /**
    * Client Interceptor
    * 
    * @return
    * @throws IllegalArgumentException
    * @throws NamingException
    */
   @Bean(name = WebServiceClientConstants.WEB_SERVICE_CLIENT_INTERCEPTOR_BEAN)
   public ClientInterceptor clientInterceptor() throws IllegalArgumentException, NamingException {
      return new WebServiceSecurityInterceptor();
   }
   
}
