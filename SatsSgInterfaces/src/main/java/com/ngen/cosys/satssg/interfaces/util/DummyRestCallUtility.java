package com.ngen.cosys.satssg.interfaces.util;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.multitenancy.support.CosysApplicationContext;

// ICS_TODO: Remove This Dummy Service After Integrating The Proper Service
@Component
public class DummyRestCallUtility {

   public ResponseEntity<Object> getDummyResultForFetchUldList(String endPointUrl, Object payload) {
      RestTemplate restTemplate = CosysApplicationContext.getRestTemplate();
      return restTemplate.exchange(endPointUrl, HttpMethod.POST, getMessagePayload(payload), Object.class);
   }

   private HttpEntity<Object> getMessagePayload(Object payload) {
      final HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
      headers.setContentType(MediaType.APPLICATION_XML);
      return new HttpEntity<>(payload, headers);
   }

   public boolean checkResponseStatus(Map<String, String> result) {
      String status = null;
      for (Entry<String, String> entry : result.entrySet()) {
         if (entry.getKey().equalsIgnoreCase("status")) {
            status = String.valueOf(entry.getValue());
         }
      }
      if ("success".equalsIgnoreCase(status)) {
         return true;
      } else {
         return false;
      }
   }

}
