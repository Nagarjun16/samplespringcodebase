package com.ngen.cosys.shipment.model;

/**
 * This test class is for Unit testing various model classes
 * 
 * 
 * @author NIIT Technologies Ltd
 *
 */
import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

public class SearchRemarkTest {

   @Test
   public void testSearchRemark() {
      SearchRemarks sr = new SearchRemarks();

      sr.setShipmentNumber("121");
      sr.setFlightKey("as1221");
      sr.setShipmentDate(LocalDateTime.of(2017, 8, 01, 0, 0));

      assertNotNull(sr);
      assertNotNull(sr.getShipmentNumber());
      assertNotNull(sr.getFlightKey());
      assertNotNull(sr.getShipmentDate());

      SearchRemarks sr1 = new SearchRemarks("121", "kl111", null, null);
      assertNotSame(sr, sr1);
      assertNotEquals(sr.toString(), sr1.toString());
   }

}
