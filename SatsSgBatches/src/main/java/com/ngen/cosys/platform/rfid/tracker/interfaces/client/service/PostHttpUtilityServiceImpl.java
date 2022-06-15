package com.ngen.cosys.platform.rfid.tracker.interfaces.client.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.platform.rfid.tracker.feeder.interceptor.RequestResponseInterceptor;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.DataPushResponse;
import com.ngen.cosys.platform.rfid.tracker.interfaces.dao.CosysRFIDMapperDAO;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.ApiRequestModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.SearchFilterRequest;

@Service
@Transactional
public class PostHttpUtilityServiceImpl implements PostHttpUtilityService {

   private static final Logger LOG = LoggerFactory.getLogger(PostHttpUtilityServiceImpl.class);

   @Autowired
   CosysRFIDMapperDAO cosysRFIDMapperDAO;

   private CloseableHttpClient httpClient = null;
   private SSLContext context = null;
   private RestTemplate restTemplate;

   List<ClientHttpRequestInterceptor> interceptors = null;

   // Post the Http data
   public void onPostExecute(ApiRequestModel apiRequest, String endPoint, AuthModel auth) throws CustomException {
      DataPushResponse result = null;
      try {
         // result = this.doPostRequest(endPoint,
         // JacksonUtility.handleObjectToJSONString(apiRequest), auth);

         result = this.doPostRequest(endPoint, apiRequest, auth);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      // if (null != result && !result.isEmpty()) {
      // String message = (String) result.get(0);
      // if (message.equalsIgnoreCase("Ok")) {
      // LOG.info("Success");
      // } else {
      // LOG.error("Failed " + message);
      // }
      // }
      catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   private DataPushResponse doPostRequest(String url, ApiRequestModel request, AuthModel auth) throws Exception {

      // System.setProperty("java.net.useSystemProxies", "true");
//      System.setProperty("http.proxyHost", "172.17.240.193");
//      System.setProperty("http.proxyPort", "80");
//      System.setProperty("https.proxyHost", "172.17.240.193");
//      System.setProperty("https.proxyPort", "80");
//      System.setProperty("http.proxyUser", "prithviraj.deb");
//      System.setProperty("http.proxyPassword", "Prithvi@3200");

      List<Header> defaultHeaders = new ArrayList<Header>();

      ArrayList<Object> t = new ArrayList<Object>();
     defaultHeaders.add(new BasicHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE));
     defaultHeaders.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
      if (auth != null) {
         defaultHeaders.add(new BasicHeader(HttpHeaders.AUTHORIZATION,
               "Basic " + Base64Utils.encodeToString((auth.getUsername() + ":" + auth.getPassword()).getBytes())));
      }

//      HttpHost proxy = new HttpHost("proxy", 80);
//      Credentials credentials = new UsernamePasswordCredentials("prithviraj.deb", "Prithvi@3200");
//      AuthScope authScope = new AuthScope("proxy", 80);
//      CredentialsProvider credsProvider = new BasicCredentialsProvider();
//      credsProvider.setCredentials(authScope, credentials);

      if (url != null && url.startsWith("https://")) {
         // For HTTPS TLSv1.2 Enabling
         context = SSLContext.getInstance("TLSv1.2");
         context.init(null, null, null);

         httpClient = HttpClientBuilder.create()
               // .useSystemProperties()
//               .setProxy(proxy).setDefaultCredentialsProvider(credsProvider)
               .setDefaultHeaders(defaultHeaders)
               .setSSLContext(context).build();

      } else {
         httpClient = HttpClientBuilder.create()
               // .useSystemProperties()
//               .setProxy(proxy).setDefaultCredentialsProvider(credsProvider)
               .setDefaultHeaders(defaultHeaders).build();
      }
      HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

      requestFactory.setConnectTimeout(90000);
      requestFactory.setReadTimeout(90000);

      restTemplate = CosysApplicationContext.getRestTemplate(new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory)));	
      restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
      interceptors = new ArrayList<ClientHttpRequestInterceptor>();
      interceptors.add(new RequestResponseInterceptor());
      restTemplate.setInterceptors(interceptors);

      try {
//         LOG.debug(request.toString());
         
         ResponseEntity<DataPushResponse> response = restTemplate.postForEntity(url, request, DataPushResponse.class);
//         LOG.debug(response.toString());
         
//         ObjectMapper mapper = new ObjectMapper();
//        LOG.error("===========================request begin============================================");
//        LOG.error("URI         : {}" + url);
//        LOG.error("Request body: {}" + mapper.writeValueAsString(request));
//        LOG.error("==========================request end===============================================");
//        LOG.error("============================response begin==========================================");
//        LOG.error("Status code  : {}" + response.getStatusCode());
//		LOG.error("Headers      : {}" + response.getHeaders());
//		LOG.error("Response body: {}" + mapper.writeValueAsString(response.getBody()));
//		LOG.error("=======================response end=================================================");

         if (response != null && response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
         }

         // if (response != null && response.getBody() != null &&
         // response.getBody().getStatusCode() != 200) {
         // throw new RuntimeException(response.getBody().getMessage());
         // }
      } catch (Exception e) {
         throw e;
      } finally {
         this.context = null;
         this.httpClient = null;
         this.restTemplate = null;
         this.interceptors = null;
      }
      return null;
   }

   // public ArrayList<Object> doPostRequest(String endPoint, String inputJson,
   // AuthModel auth) throws CustomException {
   //
   //
   //// System.setProperty("java.net.useSystemProxies", "true");
   ////
   ////
   //// System.setProperty("http.proxyHost", "proxy");
   ////
   //// System.setProperty("http.proxyPort", "80");
   ////
   //// System.setProperty("https.proxyHost", "proxy");
   ////
   //// System.setProperty("https.proxyPort", "80");
   ////
   //// System.setProperty("http.proxyUser", "prithviraj.deb");
   ////
   //// System.setProperty("http.proxyPassword", "Prithvi@3200");
   //
   // ArrayList<Object> result = new ArrayList<Object>();
   //
   // boolean isHttps = false;
   //
   // HttpURLConnection httpConn = null;
   // HttpsURLConnection httpsConn = null ;
   // if(!FormatUtils.isEmpty(endPoint) && endPoint.startsWith("https") ) {
   // isHttps = true;
   // }
   //
   // if(isHttps) {
   // // SATS Production Connection for HTTPS
   // httpsConn=this.getHttpsConnection(endPoint, auth);
   // } else {
   // // SATS UAT Connection for HTTP
   // httpConn = this.getHttpConnection(endPoint, auth);
   // }
   //
   // try {
   //
   // OutputStream os = (isHttps)? httpsConn.getOutputStream() :
   // httpConn.getOutputStream();
   // os.write(inputJson.getBytes());
   // os.flush();
   //
   // int statusCode = (isHttps)? httpsConn.getResponseCode() :
   // httpConn.getResponseCode();
   // switch (statusCode) {
   // case HttpURLConnection.HTTP_OK:
   // result.add("Ok");
   // LOG.info("PostHttpUtility Ok");
   // break;
   // case HttpURLConnection.HTTP_BAD_REQUEST:
   // result.add("Error 400 - Bad request.");
   // LOG.info("PostHttpUtility Error 400 - Bad request.");
   // break;
   // case HttpURLConnection.HTTP_UNAUTHORIZED:
   // result.add("Error 401 - Unauthorized request.");
   // break;
   // case HttpURLConnection.HTTP_CREATED:
   // result.add("Error 201 - HTTP Status-Code 201: Created .");
   // break;
   // default:
   // BufferedReader br = new BufferedReader(new InputStreamReader((isHttps)?
   // httpsConn.getErrorStream() : httpConn.getErrorStream()));
   // String line;
   // try {
   // while ((line = br.readLine()) != null) {
   // result.add(line.toString());
   // LOG.error("error", " " + line.toString() + result);
   // }
   // } catch (Exception ex) {
   // LOG.error("Exception", " " + ex);
   // }
   // break;
   // }
   //
   // if (isHttps) { httpsConn.disconnect(); } else { httpConn.disconnect(); }
   // } catch (MalformedURLException e) {
   // LOG.error("MalformedURLException Exception", e.toString());
   // } catch (IOException e) {
   // LOG.error("IOException Exception", e.toString());
   // }
   // return result;
   // }
   //
   // public HttpsURLConnection getHttpsConnection(String endPoint, AuthModel auth)
   // throws CustomException {
   // HttpsURLConnection conn = null;
   // try {
   // // Init the SSLContext with a TrustManager[] and SecureRandom()
   // try {
   //
   // SSLContext sc = SSLContext.getInstance("TLSv1.2");
   // sc.init(null, null, null);
   //
   // URL url = new URL(null, endPoint, new sun.net.www.protocol.https.Handler());
   // conn = (HttpsURLConnection) url.openConnection();
   // conn.setDoOutput(true);
   // conn.setRequestMethod("POST");
   // conn.setRequestProperty("Content-Type", "application/json");
   // conn.setConnectTimeout(45000);
   // conn.setSSLSocketFactory(sc.getSocketFactory());
   // // Sets the read timeout
   // conn.setReadTimeout(220000);
   //
   // if (auth != null && !FormatUtils.isEmpty(auth.getPassword())) {
   // String userCredentials = auth.getUsername() + ":" + auth.getPassword();
   // String basicAuth = "Basic " + new String(new
   // Base64().encode(userCredentials.getBytes()));
   // conn.setRequestProperty("Authorization", basicAuth);
   // }
   //
   // } catch (KeyManagementException e) {
   // // TODO Auto-generated catch block
   // LOG.error(e.toString());
   // } catch (NoSuchAlgorithmException e) {
   // // TODO Auto-generated catch block
   // LOG.error(e.toString());
   // }
   // } catch (IOException e) {
   // LOG.error("log--->" + e.toString());
   // } finally {
   // }
   // return conn;
   // }
   //
   // public HttpURLConnection getHttpConnection(String endPoint, AuthModel auth)
   // throws CustomException {
   // HttpURLConnection conn = null;
   // try {
   //
   // URL url = new URL(endPoint);
   // // Proxy proxy = new Proxy(Proxy.Type.HTTP, new
   // // InetSocketAddress("www.satstracer.com", 443));
   // conn = (HttpURLConnection) url.openConnection();
   // conn.setDoOutput(true);
   // conn.setRequestMethod("POST");
   // conn.setRequestProperty("Content-Type", "application/json");
   // conn.setConnectTimeout(45000);
   // // Sets the read timeout
   // conn.setReadTimeout(220000);
   //
   // if (auth != null && !FormatUtils.isEmpty(auth.getPassword())) {
   // String userCredentials = auth.getUsername() + ":" + auth.getPassword();
   // // String userCredentials = "XPS_USR:AATRFID$Pass";
   // String basicAuth = "Basic " + new String(new
   // Base64().encode(userCredentials.getBytes()));
   // conn.setRequestProperty("Authorization", basicAuth);
   // }
   //
   // } catch (IOException e) {
   // LOG.error("IOException exception", e.toString());
   // } finally {
   // }
   // return conn;
   // }

   private ArrayList<Object> searchConfig(String url, SearchFilterRequest request, AuthModel auth) throws Exception {

      // System.setProperty("java.net.useSystemProxies", "true");
      System.setProperty("http.proxyHost", "172.17.240.193");
      System.setProperty("http.proxyPort", "80");
      System.setProperty("https.proxyHost", "172.17.240.193");
      System.setProperty("https.proxyPort", "80");
      System.setProperty("http.proxyUser", "prithviraj.deb");
      System.setProperty("http.proxyPassword", "Prithvi@3200");

      List<Header> defaultHeaders = new ArrayList<Header>();

      ArrayList<Object> t = new ArrayList<Object>();
      defaultHeaders.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
      if (auth != null) {
         defaultHeaders.add(new BasicHeader(HttpHeaders.AUTHORIZATION,
               "Basic " + Base64Utils.encodeToString((auth.getUsername() + ":" + auth.getPassword()).getBytes())));
      }

      HttpHost proxy = new HttpHost("proxy", 80);
      Credentials credentials = new UsernamePasswordCredentials("prithviraj.deb", "Prithvi@3200");
      AuthScope authScope = new AuthScope("proxy", 80);
      CredentialsProvider credsProvider = new BasicCredentialsProvider();
      credsProvider.setCredentials(authScope, credentials);

      if (url != null && url.startsWith("https://")) {
         // For HTTPS TLSv1.2 Enabling
         context = SSLContext.getInstance("TLSv1.2");
         context.init(null, null, null);

         httpClient = HttpClientBuilder.create()
               // .useSystemProperties()
               .setProxy(proxy).setDefaultCredentialsProvider(credsProvider).setDefaultHeaders(defaultHeaders)
               .setSSLContext(context).build();

      } else {
         httpClient = HttpClientBuilder.create()
               // .useSystemProperties()
               .setProxy(proxy).setDefaultCredentialsProvider(credsProvider).setDefaultHeaders(defaultHeaders).build();
      }
      HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

      requestFactory.setConnectTimeout(90000);
      requestFactory.setReadTimeout(90000);

      restTemplate =  CosysApplicationContext.getRestTemplate(new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory)));	
      restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
      interceptors = new ArrayList<ClientHttpRequestInterceptor>();
      interceptors.add(new RequestResponseInterceptor());
      restTemplate.setInterceptors(interceptors);

      try {
         LOG.debug(request.toString());
         ResponseEntity<Object> response = restTemplate.postForEntity(url, request, Object.class);
         LOG.debug(response.toString());

         if (response != null && response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Object[] result = (Object[]) response.getBody();
            t = new ArrayList<Object>();
            Collections.addAll(t, result);
         }
         return t;

         // if (response != null && response.getBody() != null &&
         // response.getBody().getStatusCode() != 200) {
         // throw new RuntimeException(response.getBody().getMessage());
         // }
      } catch (Exception e) {
         throw e;
      } finally {
         this.context = null;
         this.httpClient = null;
         this.restTemplate = null;
         this.interceptors = null;
      }
   }

   @Override
   public AuthModel getAuthUserPassword() throws CustomException {
      return cosysRFIDMapperDAO.getAuthUserPassword();
   }

}