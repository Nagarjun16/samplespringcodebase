/**
 * {@link AlteaFMSOAPConstants}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.altea.fm.common;

/**
 * AlteaFM Soap constant holder for WSAP
 * 
 * @author NIIT Technologies Ltd
 */
public class AlteaFMSOAPConstants {

   private AlteaFMSOAPConstants() {
      throw new RuntimeException("create.instances.are.not.allowed");
   }
   
   // Envelope
   public static final String HEADER_ELEMENT_ENVELOPE_NAMESPACE_APP_PREFIX = "app";
   public static final String HEADER_ELEMENT_ENVELOPE_NAMESPACE_APP_VALUE = "http://xml.amadeus.com/2010/06/AppMdw_CommonTypes_v3";
   public static final String HEADER_ELEMENT_ENVELOPE_NAMESPACE_LINK_PREFIX = "link";
   public static final String HEADER_ELEMENT_ENVELOPE_NAMESPACE_LINK_VALUE = "http://wsdl.amadeus.com/2010/06/ws/Link_v1";
   public static final String HEADER_ELEMENT_ENVELOPE_NAMESPACE_SES_PREFIX = "ses";
   public static final String HEADER_ELEMENT_ENVELOPE_NAMESPACE_SES_VALUE = "http://xml.amadeus.com/2010/06/Session_v3";
   public static final String HEADER_ELEMENT_ENVELOPE_NAMESPACE_FEC_PREFIX = "fec";
   public static final String HEADER_ELEMENT_ENVELOPE_NAMESPACE_FEC_VALUE = "http://xml.amadeus.com/FECFUQ_17_1_1A";
   public static final String HEADER_ELEMENT_ENVELOPE_NAMESPACE_WSA_PREFIX = "wsa";
   public static final String HEADER_ELEMENT_ENVELOPE_NAMESPACE_WSA_VALUE = "http://www.w3.org/2005/08/addressing";
   //
   public static final String HEADER_ELEMENT_SECURITY_HOSTED_USER_NAMESPACE_PREFIX = "sec";
   public static final String HEADER_ELEMENT_SECURITY_HOSTED_USER_NAMESPACE = "AMA_SecurityHostedUser";
   public static final String HEADER_ELEMENT_SECURITY_HOSTED_USER_NAMESPACE_VALUE = "http://xml.amadeus.com/2010/06/Security_v1";
   public static final String HEADER_ELEMENT_SECURITY_USER_ID_NAMESPACE = "UserID";
   public static final String HEADER_ELEMENT_SECURITY_USER_ID_POS_TYP_KEY = "POS_Type";
   public static final String HEADER_ELEMENT_SECURITY_USER_ID_POS_TYP_VALUE = "1";
   public static final String HEADER_ELEMENT_SECURITY_USER_ID_REQUESTOR_TYPE = "RequestorType";
   public static final String HEADER_ELEMENT_SECURITY_USER_ID_REQUESTOR_VALUE = "U";
   public static final String HEADER_ELEMENT_SECURITY_CITY_CODE_KEY = "PseudoCityCode";
   public static final String HEADER_ELEMENT_SECURITY_CITY_CODE_VALUE = "SINSQ0872"; // OID
   public static final String HEADER_ELEMENT_SECURITY_DUTY_CODE_KEY = "AgentDutyCode";
   public static final String HEADER_ELEMENT_SECURITY_DUTY_CODE_VALUE = "SU"; // DutyCode
   public static final String HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE_PREFIX = "typ";
   public static final String HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE = "RequestorID";
   public static final String HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE_TYP_PREFIX = "typ";
   public static final String HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE_TYP_VALUE = "http://xml.amadeus.com/2010/06/Types_v1";
   public static final String HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE_IAT_PREFIX = "iat";
   public static final String HEADER_ELEMENT_SECURITY_REQUESTOR_ID_NAMESPACE_IAT_VALUE = "http://www.iata.org/IATA/2007/00/IATA2010.1";
   public static final String HEADER_ELEMENT_SECURITY_COMPANY_NAME_NAMESPACE_PREFIX = "iat";
   public static final String HEADER_ELEMENT_SECURITY_COMPANY_NAME_NAMESPACE = "CompanyName";
   public static final String HEADER_ELEMENT_SECURITY_COMPANY_NAME_NODE_VALUE = "SQ"; // LSSOrg
   public static final String HEADER_ELEMENT_WSSECURITY_NAMESPACE_PREFIX = "wsse";
   public static final String HEADER_ELEMENT_WSSECURITY_NAMESPACE = "Security";
   public static final String HEADER_ELEMENT_WSSECURITY_NAMESPACE_VALUE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
   public static final String HEADER_ELEMENT_WSSECURITY_NAMESPACE_WSU_PREFIX = "wsu";
   public static final String HEADER_ELEMENT_WSSECURITY_NAMESPACE_WSU_VALUE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
   public static final String HEADER_ELEMENT_WSSECURITY_USERNAME_TOKEN_NAMESPACE = "UsernameToken";
   public static final String HEADER_ELEMENT_WSSECURITY_USERNAME_NAMESPACE = "Username";
   public static final String HEADER_ELEMENT_WSSECURITY_USERNAME_VALUE = "WSSQNGC"; // "${#LSSUser}"; // LSSUser
   public static final String HEADER_ELEMENT_WSSECURITY_PASSWORD_NAMESAPCE = "Password";
   public static final String HEADER_ELEMENT_WSSECURITY_PASSWORD_NAMESPACE_TYPE_PREFIX = "Type";
   public static final String HEADER_ELEMENT_WSSECURITY_PASSWORD_NAMESPACE_TYPE_VALUE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest";
   public static final String HEADER_ELEMENT_WSSECURITY_PASSWORD_VALUE = "gyoPhkc8"; // Password
   public static final String HEADER_ELEMENT_WSSECURITY_NONCE_NAMESPACE = "Nonce";
   public static final String HEADER_ELEMENT_WSSECURITY_NONCE = "${#Nonce}";
   public static final String HEADER_ELEMENT_WSSECURITY_NONCE_NAMESPACE_ENCODING_TYPE_PREFIX = "EncodingType";
   public static final String HEADER_ELEMENT_WSSECURITY_NONCE_NAMESPACE_ENCODING_TYPE_VALUE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";
   public static final String HEADER_ELEMENT_WSSECURITY_CREATED_NAMESPACE_PREFIX = "wsu";
   public static final String HEADER_ELEMENT_WSSECURITY_CREATED_NAMESPACE = "Created";
   public static final String HEADER_ELEMENT_WSSECURITY_CREATED_NAMESPACE_VALUE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
   public static final String HEADER_ELEMENT_WSSECURITY_CREATED_VALUE = "${#Created}";
   public static final String HEADER_ELEMENT_WSADDRESS_NAMESPACE_PREFIX = "wsa";
   public static final String HEADER_ELEMENT_WSADDRESS_NAMESPACE_VALUE = "http://www.w3.org/2005/08/addressing";
   public static final String HEADER_ELEMENT_WSADDRESS_ACTION = "Action";
   public static final String HEADER_ELEMENT_WSADDRESS_ACTION_KEY = "http://webservices.amadeus.com/FECFUQ_17_1_1A";
   public static final String HEADER_ELEMENT_WSADDRESS_MESSAGE_ID = "MessageID";
   public static final String HEADER_ELEMENT_WSADDRESS_MESSAGE_KEY = "${#MessageKey}";
   public static final String HEADER_ELEMENT_WSADDRESS_MESSAGE_UUID = "uuid:";
   public static final String HEADER_ELEMENT_WSADDRESS_TO = "To";
   public static final String HEADER_ELEMENT_WSADDRESS_TO_KEY = "https://nodeA1.test.webservices.amadeus.com/1ASIWNGCSQ";
   
}
