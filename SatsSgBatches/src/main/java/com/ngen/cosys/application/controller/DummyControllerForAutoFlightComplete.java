package com.ngen.cosys.application.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.export.flightautocomplete.model.FlightAutoCompleteDetails;
// import com.ngen.cosys.esb.connector.ConnectorService;
import com.ngen.cosys.export.flightautocomplete.service.FlightAutoCompleteService;
import com.ngen.cosys.framework.exception.CustomException;

@NgenCosysAppInfraAnnotation
public class DummyControllerForAutoFlightComplete {

   private static final Logger LOGGER = LoggerFactory.getLogger(DummyControllerForAutoFlightComplete.class);

   private static final String DEFAULT_USER = "AUTOCOMPLETE";

   private static final String DEFAULT_TENANT = "SIN";

   @Autowired
   private FlightAutoCompleteService service;

   @PostMapping(value = "/api/batches/flightautocomplete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public void performBatchOperation() throws CustomException {

      LOGGER.warn("Start of Flight Auto Complete", LocalDateTime.now());
      try {

         // 1. Get the list of flights for auto complete
         List<FlightAutoCompleteDetails> flights = this.service.getFlightList();

         // 2. Iterate each flight and perform auto complete
         if (!CollectionUtils.isEmpty(flights)) {

            for (FlightAutoCompleteDetails t : flights) {
               autoCompleteAFlight(t);
            }
         }

      } catch (Exception e) {
         LOGGER.error("Exception while performing Flight Auto Complete", e);
      }

      LOGGER.warn("End of Flight Auto Complete", LocalDateTime.now());
   }

   @Transactional
   private void autoCompleteAFlight(FlightAutoCompleteDetails t) throws CustomException {
      LOGGER.warn("Flight :", t.getFlightKey() + " - " + t.getFlightId() + " - " + t.getDateSTD());

      t.setCreatedBy(DEFAULT_USER);
      t.setModifiedBy(DEFAULT_USER);
      t.setTenantId(DEFAULT_TENANT);
      t.setCreatedOn(LocalDateTime.now());
      t.setModifiedOn(LocalDateTime.now());

      boolean fuseComplete = false;

      try {
         // 3. Calculate Volume
         // Calculate volume for shipments
          this.service.calculateVolume(t);
         // 3. Mark first flight complete to avoid other batch to pickup
         this.service.markFirstFlightComplete(t);

         // 4. Freight Out shipments
         this.service.freightOut(t);

         // 5. Submit data to customs
         this.service.submitShipmentsToCustoms(t);

         // Turn on flight complete
         fuseComplete = true;

      } catch (Exception ex) {
         LOGGER.error("Exception while performing Freight Out/Submit To Customs", ex);
         // Un-mark the first flight complete
         this.service.unmarkFirstFlightComplete(t);
      }

      try {
         // 6. Publish events for flight complete
         if (fuseComplete) {
            this.service.publishEvents(t);
         }

      } catch (Exception ex) {
         LOGGER.error("Exception while performing publishing events", ex);
      }

      // 7. Complete the flight
      if (fuseComplete) {
         this.service.markFlightComplete(t);
      }

      // 8. Trigger email based on carrier requirement
      if (fuseComplete && "LH".equalsIgnoreCase(t.getCarrierCode())
            || "LX".equalsIgnoreCase(t.getCarrierCode())) {

         List<String> emailIds = this.service.getCommunicationEmailIds(t);
         t.setCommunicationEmails(emailIds);

         // If email addresses have configured then trigger the email
         if (!CollectionUtils.isEmpty(t.getCommunicationEmails())) {

            // Email on FFM
            this.service.sendEmailOnFFMInfo(t);

            // Email on FWB
            this.service.sendEmailOnFWBInfo(t);

            // Email on RTD
            this.service.sendEmailOnRTDInfo(t);
         }
      }
   }

}