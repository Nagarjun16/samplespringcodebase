/*package com.ngen.cosys.impbd.controller;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.shipment.verification.controller.DocumentVerificationController;
import com.ngen.cosys.impbd.shipment.verification.model.DocumentVerificationFlightModel;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
//@Sql(scripts = { "/tddscripts/impbd-DocumentVerification-schema-tdd-h2.sql",
     // "/tddscripts/impbd-DocumentVerification-data-tdd-h2.sql" })
public class DocumentVerificationControllerTest {

   @Autowired
   DocumentVerificationController documentVerificationController;

   @Test
   public void getDocumentVerificationFlightDetails() throws CustomException {
      DocumentVerificationFlightModel documentVerificationFlightModel = new DocumentVerificationFlightModel();
      documentVerificationFlightModel.setFlightNumber("12H12345");
      documentVerificationFlightModel.setFlightDate(LocalDate.of(2018, 02, 15));
      // BaseResponse<DocumentVerificationFlightModel> outputFlight =
      // documentVerificationController.searchDocuments(documentVerificationFlightModel);
      // assertNotNull(outputFlight.getData());
   }
}
*/