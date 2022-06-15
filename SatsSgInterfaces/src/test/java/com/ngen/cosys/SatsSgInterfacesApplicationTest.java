package com.ngen.cosys;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

//@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SatsSgInterfacesApplicationTest {

   @Test
   public void contextLoads() {
      assertNotNull(this);
   }

}