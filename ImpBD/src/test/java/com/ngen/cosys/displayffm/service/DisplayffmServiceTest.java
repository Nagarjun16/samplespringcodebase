/*package com.ngen.cosys.displayffm.service;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.displayffm.dao.DisplayffmDaoImpl;
import com.ngen.cosys.impbd.displayffm.model.DisplayffmByFlightModel;
import com.ngen.cosys.impbd.displayffm.model.SearchDisplayffmModel;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")

public class DisplayffmServiceTest {

   @Autowired
   DisplayffmDaoImpl displayffmDaoImpl;
   @Test
   @Sql(scripts = {"/tddscripts/displayffm-schema-tdd-h2.sql","/tddscripts/displayffm-data-tdd-h2.sql"})
   public void testGetAllDisplayffm() throws CustomException{
      SearchDisplayffmModel searchDisplayffmModel=new SearchDisplayffmModel();
      searchDisplayffmModel.setFlightNumber("SQ001");
      searchDisplayffmModel.setFlightDate(LocalDate.now());
      //List<DisplayffmByFlightModel> ffmDetails = displayffmDaoImpl.getAllDisplayffm(searchDisplayffmModel);
      //assertNotNull(ffmDetails);
   }
}
*/