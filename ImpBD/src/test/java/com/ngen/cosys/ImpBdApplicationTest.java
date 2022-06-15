/*package com.ngen.cosys;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImpBdApplication.class)
@ActiveProfiles("test")
public class ImpBdApplicationTest {

   @Test
   @Sql(scripts = { "/tddscripts/breakdownworklist-schema-tdd-h2.sql",
         "/tddscripts/breakdownworklist-data-tdd-h2.sql" })
   public void contextLoads() {
      assertNotNull(this);
   }
}*/