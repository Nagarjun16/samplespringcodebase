/**
 * {@link WebServiceMessageUtils}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.altea.fm.util;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Web Service Message Utility
 * 
 * @author NIIT Technologies Ltd
 */
public class WebServiceMessageUtils {

   private static final Logger LOG = LoggerFactory.getLogger(WebServiceMessageUtils.class);

   /**
    * @param envelope
    * @param localName
    * @param prefix
    * @param namespaceURI
    * @return
    * @throws SOAPException
    */
   public static Name nameElement(SOAPEnvelope envelope, String localName, String prefix, String namespaceURI)
         throws SOAPException {
      LOG.debug("Web Service Message Utils :: LocalName - {}, Prefix - {}, Namespace URI - {}", localName, prefix,
            namespaceURI);
      return envelope.createName(localName, prefix, namespaceURI);
   }
   
   /**
    * @param header
    * @param nameElement
    * @return
    * @throws SOAPException
    */
   public static SOAPHeaderElement headerElement(SOAPHeader header, Name nameElement) throws SOAPException {
      return header.addHeaderElement(nameElement);
   }
   
   /**
    * @param header
    * @param localName
    * @param prefix
    * @return
    * @throws SOAPException
    */
   public static SOAPHeaderElement headerElement(SOAPHeader header, String localName, String prefix, String namespaceURI)
         throws SOAPException {
      return header.addHeaderElement(new QName(namespaceURI, localName, prefix));
   }
   
   /**
    * @param headerElement
    * @param localName
    * @param prefix
    * @return
    * @throws SOAPException
    */
   public static SOAPElement headerSOAPElement(SOAPHeaderElement headerElement, String localName, String prefix)
         throws SOAPException {
      return headerElement.addChildElement(localName, prefix);
   }
   
   /**
    * @param soapElement
    * @param localName
    * @param prefix
    * @return
    * @throws SOAPException
    */
   public static SOAPElement soapElement(SOAPElement soapElement, String localName, String prefix)
         throws SOAPException {
      return soapElement.addChildElement(localName, prefix);
   }
   
   /**
    * @param soapElement
    * @param localName
    * @param prefix
    * @param namespaceURI
    * @return
    * @throws SOAPException
    */
   public static SOAPElement soapElement(SOAPElement soapElement, String localName, String prefix, String namespaceURI)
         throws SOAPException {
      return soapElement.addChildElement(localName, prefix, namespaceURI);
   }
   
   /**
    * @param headerElement
    * @param attribute
    * @param value
    * @throws SOAPException
    */
   public static void addAttribute(SOAPHeaderElement headerElement, String attribute, String value) throws SOAPException {
      headerElement.addAttribute(new QName(attribute), value);
   }
   
   /**
    * @param soapElement
    * @param attribute
    * @param value
    * @throws SOAPException
    */
   public static void addAttribute(SOAPElement soapElement, String attribute, String value) throws SOAPException {
      soapElement.addAttribute(new QName(attribute), value);
   }
   
   /**
    * Generate Nonce
    * 
    * @param instance
    * @return
    */
   public static String generateNonce(String instance) {
      String nonce = null;
      try {
         SecureRandom random = SecureRandom.getInstance(instance);
         random.setSeed(System.currentTimeMillis());
         byte[] bytes = new byte[16];
         random.nextBytes(bytes);
         nonce = Base64.encodeBase64String(bytes);
      } catch (NoSuchAlgorithmException ex) {
         LOG.error("WebService Message Util :: Generate Nonce exception occurred - {}", ex);
      }
      return nonce;
   }
   
   /**
    * Generate Created
    * 
    * @param timeFormat
    * @param timeZone
    * @return
    */
   public static String generateCreated(String timeFormat, String timeZone) {
      DateFormat dateFormat = new SimpleDateFormat(timeFormat);
      dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));
      Calendar calendar = Calendar.getInstance();
      return dateFormat.format(calendar.getTime());
   }
   
   /**
    * Password encrypt for Password Digest
    * 
    * @param password
    * @param instance
    * @return
    */
   private static byte[] encryptPassword(String password, String instance) {
      byte[] _encryptPassword = null;
      byte[] _password = password.getBytes(StandardCharsets.UTF_8);
      try {
         MessageDigest salt = MessageDigest.getInstance(instance);
         if (Objects.isNull(salt)) {
            throw new IllegalStateException("MessageDigest instance cannot be null");
         }
         salt.reset();
         salt.update(_password);
         _encryptPassword = salt.digest();
      } catch (NoSuchAlgorithmException ex) {
         LOG.error("MessageDigest Encrypt Password exception : %s%n" + String.valueOf(ex));
      }
      return _encryptPassword;
   }
   
   /**
    * Generate Password Digest
    * 
    * @param password
    * @param nonce
    * @param created
    * @param instance
    * @return
    */
   public static String generatePasswordDigest(String password, String nonce, String created, String instance) {
      String passwordDigest = null;
      //
      try {
         byte[] _nonce = !StringUtils.isEmpty(nonce) ? Base64.decodeBase64(nonce) : new byte[0];
         byte[] _created = !StringUtils.isEmpty(created) ? created.getBytes(StandardCharsets.UTF_8) : new byte[0];
         byte[] _password = encryptPassword(password, instance);
         byte[] _cipher = new byte[_nonce.length + _created.length + _password.length];
         int offset = 0;
         //
         System.arraycopy(_nonce, 0, _cipher, offset, _nonce.length);
         offset += _nonce.length;
         System.arraycopy(_created, 0, _cipher, offset, _created.length);
         offset += _created.length;
         System.arraycopy(_password, 0, _cipher, offset, _password.length);
         //
         MessageDigest salt = MessageDigest.getInstance(instance);
         if (Objects.isNull(salt)) {
            throw new IllegalStateException("MessageDigest instance cannot be null");
         }
         salt.reset();
         salt.update(_cipher);
         //
         passwordDigest = Base64.encodeBase64String(salt.digest());
      } catch (NoSuchAlgorithmException ex) {
         LOG.error("MessageDigest exception : %s%n" + String.valueOf(ex));
      }
      return passwordDigest;
   }
   
   /**
    * Generate Password Digest Token
    * 
    * @param password
    * @param nonce
    * @param created
    * @param instance
    * @return
    */
   public static String generatePasswordDigestToken(String password, String nonce, String created, String instance) {
      String passwordDigest = null;
      //
      try {
         byte[] _nonce = !StringUtils.isEmpty(nonce) ? Base64.decodeBase64(nonce) : new byte[0];
         byte[] _created = !StringUtils.isEmpty(created) ? created.getBytes(StandardCharsets.UTF_8) : new byte[0];
         byte[] _password = password.getBytes(StandardCharsets.UTF_8);
         //
         MessageDigest salt = MessageDigest.getInstance(instance);
         if (Objects.isNull(salt)) {
            throw new IllegalStateException("MessageDigest instance cannot be null");
         }
         // HASH function
         ByteArrayOutputStream stream = new ByteArrayOutputStream();
         stream.write(_nonce);
         stream.write(_created);
         stream.write(_password);
         //
         byte[] _digestPassword = stream.toByteArray(); //salt.digest(stream.toByteArray());
         // passwordDigest = Base64.encodeBase64String(_digestPassword);
         salt.update(_digestPassword, 0, _digestPassword.length);
         passwordDigest = Base64.encodeBase64String(salt.digest());
      } catch (Exception ex) {
         LOG.error("Message Digest Token exception - {}", ex);
      }
      //
      return passwordDigest;
   }
   
}
