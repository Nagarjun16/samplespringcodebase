/**
 * {@link WebServiceClientConstants}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.altea.fm.common;

/**
 * Web Service Client Constants
 * 
 * @author NIIT Technologies Ltd
 */
public class WebServiceClientConstants {

   private WebServiceClientConstants() {
      throw new RuntimeException("create.instances.are.not.allowed");
   }
   
   public static final String BASE_PACKAGE_SCAN = "com.ngen.cosys.altea.fm";
   public static final int TIME_OUT = 20000;
   //
   public static final String JAXB_2_MARSHALLER_BEAN = "jaxb2MarshallerBeanNGC";
   public static final String WEB_SERVICE_SOAP_1_1_MESSAGE_FACTORY_BEAN = "webServiceSoapV1MessageFactoryBean";
   public static final String WEB_SERVICE_SOAP_1_2_MESSAGE_FACTORY_BEAN = "webServiceSoapV2MessageFactoryBean";
   public static final String WEB_SERVICE_MESSAGE_SENDER_BEAN = "webServiceMessageSenderBean";
   public static final String WEB_SERVICE_SECURITY_INTERCEPTOR_BEAN = "webServiceSecurityInterceptorBean";
   public static final String WEB_SERVICE_CLIENT_INTERCEPTOR_BEAN = "webServiceClientInterceptorBean";
   public static final String WEB_SERVICE_TEMPLATE = "webServiceTemplateBean";
   //
   public static final String SECURITY_INTERCEPTOR_ACTIONS = "UsernameToken Timestamp"; //Timestamp UsernameToken
   public static final String SECURITY_INTERCEPTOR_USERNAME = AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_USERNAME_VALUE;
   public static final String SECURITY_INTERCEPTOR_PASSWORD = AlteaFMSOAPConstants.HEADER_ELEMENT_WSSECURITY_PASSWORD_VALUE;
   //
   public static final String NONCE_ALGORITHM = "SHA1PRNG";
   public static final String CREATED_TIMEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
   public static final String TIME_ZONE_ZULU = "Zulu";
   public static final String PASSWORD_DIGEST_CRYPT = "SHA-1";
   
}
