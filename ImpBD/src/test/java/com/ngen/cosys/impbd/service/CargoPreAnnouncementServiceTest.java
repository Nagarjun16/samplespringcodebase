/*package com.ngen.cosys.impbd.service;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.ngen.cosys.ImpBdApplication;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.service.FrameworkValidationServiceImpl;
import com.ngen.cosys.impbd.model.CargoPreAnnouncement;
import com.ngen.cosys.impbd.model.CargoPreAnnouncementBO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImpBdApplication.class)
@ActiveProfiles("test")
//@Sql(scripts = { "impbd-incomingflight-schema-tdd-h2.sql", "impbd-incomingflight-data-tdd-h2.sql" })
public class CargoPreAnnouncementServiceTest {
   @Autowired
   private CargoPreAnnouncementService cargoPreAnnouncementService;
   @Mock
   private FrameworkValidationServiceImpl frameworkValidationService;
   CargoPreAnnouncementBO cargoPreAnnouncementBO1;

   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
   }

   private CargoPreAnnouncementBO cereateModel() {
      CargoPreAnnouncementBO cargoPreAnnouncementBO = new CargoPreAnnouncementBO();
      cargoPreAnnouncementBO.setFlight("12H0012");
      cargoPreAnnouncementBO.setDate(LocalDate.of(2017, 12, 16));
      return cargoPreAnnouncementBO;
   }

   @Test
   public void cargoPreAnnouncement() throws CustomException, ParseException {
      CargoPreAnnouncementBO cargoPreAnnouncementBO = cereateModel();
      // cargoPreAnnouncementBO1 =
      // cargoPreAnnouncementService.cargoPreAnnouncement(cargoPreAnnouncementBO);
      // assertEquals(2,
      // cargoPreAnnouncementBO1.getCargoPreAnnouncementList().size());

   }

   @Test
   public void insertUpdateCargoPreAnnouncement() throws CustomException, ParseException {
      CargoPreAnnouncement cargoPreAnnouncement = new CargoPreAnnouncement();
      cargoPreAnnouncement.setAnnouncementSourceType("FFM");
      // cargoPreAnnouncementBO1.getCargoPreAnnouncementList().add(cargoPreAnnouncement);
      // cargoPreAnnouncementService.insertUpdateCargoPreAnnouncement(cargoPreAnnouncementBO1);

   }

   @Test
   public void deleteCargoPreAnnouncement() throws CustomException, ParseException {
      CargoPreAnnouncementBO cargoPreAnnouncementBO = cereateModel();
      // cargoPreAnnouncementService.deleteCargoPreAnnouncement(cargoPreAnnouncementBO);

   }

}
*/