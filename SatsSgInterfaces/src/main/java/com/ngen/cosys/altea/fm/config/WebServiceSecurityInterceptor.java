/**
 * {@link WebServiceSecurityInterceptor}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.altea.fm.config;

import java.util.Objects;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

/**
 * Altea FM Web Service Security Interceptor  
 * 
 * @author NIIT Technologies Ltd
 */
public class WebServiceSecurityInterceptor implements ClientInterceptor {

   private static final Logger LOG = LoggerFactory.getLogger(WebServiceSecurityInterceptor.class);
   
   /**
    * @see org.springframework.ws.client.support.interceptor.ClientInterceptor#handleRequest(org.springframework.ws.context.MessageContext)
    */
   @Override
   public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
      LOG.warn("Altea FM Web Service Security Interceptor :: Handle Request {}");
      SoapMessage message = (SoapMessage) messageContext.getRequest();
      return true;
   }

   /**
    * @see org.springframework.ws.client.support.interceptor.ClientInterceptor#handleResponse(org.springframework.ws.context.MessageContext)
    */
   @Override
   public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
      LOG.warn("Altea FM Web Service Security Interceptor :: Handle Response {}");
      return true;
   }

   /**
    * @see org.springframework.ws.client.support.interceptor.ClientInterceptor#handleFault(org.springframework.ws.context.MessageContext)
    */
   @Override
   public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
      LOG.warn("Altea FM Web Service Security Interceptor :: Handle Fault {}");
      WebServiceMessage message = messageContext.getResponse();
      SaajSoapMessage saajSoapMessage = (SaajSoapMessage) message;
      SOAPMessage soapMessage = saajSoapMessage.getSaajMessage();
      SOAPPart soapPart = soapMessage.getSOAPPart();
      SOAPEnvelope soapEnvelope;
      try {
         soapEnvelope = soapPart.getEnvelope();
         SOAPBody soapBody = soapEnvelope.getBody();
         SOAPFault soapFault = soapBody.getFault();
         // Custom message parsing - Fault
         parseFaultMessage(soapFault);
      } catch (SOAPException ex) {
         LOG.error("Altea FM Web Service Fault Exception :: {}", String.valueOf(ex));
      }
      return true;
   }

   /**
    * @see org.springframework.ws.client.support.interceptor.ClientInterceptor#afterCompletion(org.springframework.ws.context.MessageContext,
    *      java.lang.Exception)
    */
   @Override
   public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {
      LOG.warn("Altea FM Web Service Security Interceptor :: After Completion - Exception {}",
            Objects.nonNull(ex) ? "Occurred" : "None");
   }
   
   /**
    * @param soapFault
    */
   private void parseFaultMessage(SOAPFault soapFault) {
      LOG.error("Altea FM Web Service Fault Message - {}", soapFault.getFaultString());
   }
   
}
