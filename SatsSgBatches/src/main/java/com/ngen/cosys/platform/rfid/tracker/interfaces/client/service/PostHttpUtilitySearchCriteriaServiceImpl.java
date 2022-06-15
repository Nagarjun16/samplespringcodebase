package com.ngen.cosys.platform.rfid.tracker.interfaces.client.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.platform.rfid.tracker.feeder.interceptor.RequestResponseInterceptor;
import com.ngen.cosys.platform.rfid.tracker.interfaces.dao.CosysRFIDMapperDAO;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.SearchFilterModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.SearchFilterRequest;

/**
 * 
 * @author mithun.k
 *
 */
@Component
public class PostHttpUtilitySearchCriteriaServiceImpl implements PostHttpUtilitySearchCriteriaService {

   private static final Logger LOGGER = LoggerFactory.getLogger(PostHttpUtilitySearchCriteriaServiceImpl.class);

   private CloseableHttpClient httpClient = null;
   private SSLContext context = null;
   private RestTemplate restTemplate;

   @Autowired
   CosysRFIDMapperDAO cosysRFIDMapperDAO;

   @Value("${station}")
   private String station;

   // private AuthModel auth = null;
   List<ClientHttpRequestInterceptor> interceptors = null;

   // Post the Http data
   public List<SearchFilterModel> onPostExecute(String endPoint, AuthModel auth) throws CustomException {
      // String stationName = "{\"station\":\"SATS\"}";
      // JSONObject stationName = new JSONObject();
      // try {
      // stationName.put("station", station);
      // } catch (JSONException e) {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
      // }

      SearchFilterRequest searchBo = new SearchFilterRequest();
      searchBo.setStation(station);

      ArrayList<SearchFilterModel> result = null;
      try {
         result = searchConfig(endPoint, searchBo, auth);
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      // ArrayList<Object> result = this.doPostRequest(endPoint, stationName, auth);

//      List<SearchFilterModel> criteriaBO = new ArrayList<>();
//      if (!result.isEmpty()) {
//         for (Object object : result) {
//            if (object instanceof SearchFilterModel)
//               criteriaBO.add((SearchFilterModel)object);
//         }
//      }
      return result;
   }

   private ArrayList<SearchFilterModel> searchConfig(String url, SearchFilterRequest request, AuthModel auth) throws Exception {

//      System.setProperty("java.net.useSystemProxies", "true");
//      System.setProperty("http.proxyHost", "172.17.240.193");
//      System.setProperty("http.proxyPort", "80");
//      System.setProperty("https.proxyHost", "172.17.240.193");
//      System.setProperty("https.proxyPort", "80");
//      System.setProperty("http.proxyUser", "prithviraj.deb");
//      System.setProperty("http.proxyPassword", "Prithvi@3200");

      List<Header> defaultHeaders = new ArrayList<Header>();

      ArrayList<SearchFilterModel> t = new ArrayList<SearchFilterModel>();
     //defaultHeaders.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
      if (auth != null) {
         defaultHeaders.add(new BasicHeader(HttpHeaders.AUTHORIZATION,
               "Basic " + Base64Utils.encodeToString((auth.getUsername() + ":" + auth.getPassword()).getBytes())));
      }
      
//      HttpHost proxy = new HttpHost("proxy",80);
//      Credentials credentials = new UsernamePasswordCredentials("prithviraj.deb","Prithvi@3200");
//      AuthScope authScope = new AuthScope("proxy", 80);
//      CredentialsProvider credsProvider = new BasicCredentialsProvider();
//      credsProvider.setCredentials(authScope, credentials);
      
      if (url != null && url.startsWith("https://")) {
         // For HTTPS TLSv1.2 Enabling
         context = SSLContext.getInstance("TLSv1.2");
         context.init(null, null, null);

         httpClient = HttpClientBuilder.create()
//               .useSystemProperties()
//               .setProxy(proxy).setDefaultCredentialsProvider(credsProvider)
               .setDefaultHeaders(defaultHeaders).setSSLContext(context).build();
         
      } else {
         httpClient = HttpClientBuilder.create()
//               .useSystemProperties()
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
         LOGGER.debug(request.toString());
         ResponseEntity<SearchFilterModel[]> response = restTemplate.postForEntity(url, request, SearchFilterModel[].class);
         LOGGER.debug(response.toString());

          if (response != null && response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
           Collections.addAll(t, response.getBody());
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

//   private ArrayList<Object> postEventData(String url, JSONObject request, AuthModel auth) throws Exception {
//      List<Header> defaultHeaders = new ArrayList<Header>();
//
//      ArrayList<Object> t = new ArrayList<Object>();
//      if (auth != null) {
//         defaultHeaders.add(new BasicHeader(HttpHeaders.AUTHORIZATION,
//               "Basic " + Base64Utils.encodeToString((auth.getUsername() + ":" + auth.getPassword()).getBytes())));
//      }
//      if (url != null && url.startsWith("https://")) {
//         // For HTTPS TLSv1.2 Enabling
//         context = SSLContext.getInstance("TLSv1.2");
//         context.init(null, null, null);
//
//         httpClient = HttpClientBuilder.create().setDefaultHeaders(defaultHeaders).setSSLContext(context).build();
//      } else {
//         httpClient = HttpClientBuilder.create().setDefaultHeaders(defaultHeaders).build();
//      }
//      HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//
//      requestFactory.setConnectTimeout(10000);
//      requestFactory.setReadTimeout(15000);
//
//      restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory));
//      interceptors = new ArrayList<ClientHttpRequestInterceptor>();
//      interceptors.add(new RequestResponseInterceptor());
//      restTemplate.setInterceptors(interceptors);
//
//      try {
//         LOGGER.debug(request.toString());
//         ResponseEntity<TrackerFeedResponse> response = restTemplate.postForEntity(url, request,
//               TrackerFeedResponse.class);
//         LOGGER.debug(response.toString());
//
//         if (response != null && response.getBody() != null) {
//            if (response.getBody().getStatusCode() == 200) {
//               TrackerFeedResponse result = response.getBody();
//
//               t.add(result);
//            }
//
//         }
//         return t;
//
//         // if (response != null && response.getBody() != null &&
//         // response.getBody().getStatusCode() != 200) {
//         // throw new RuntimeException(response.getBody().getMessage());
//         // }
//      } catch (Exception e) {
//         throw e;
//      } finally {
//         this.context = null;
//         this.httpClient = null;
//         this.restTemplate = null;
//         this.interceptors = null;
//      }
//   }

   @Override
   public AuthModel getAuthUserPassword() throws CustomException {
      return cosysRFIDMapperDAO.getAuthUserPassword();
   }

}
