/*package com.ngen.cosys.impbd.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.controller.IncomingFlightsController;
import com.ngen.cosys.impbd.dao.IncomingFlightDAO;
import com.ngen.cosys.impbd.model.IncomingFlightModel;
import com.ngen.cosys.impbd.model.IncomingFlightQuery;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = {"/tddscripts/impbd-incoming-flight-schema-tdd-h2.sql", "/tddscripts/impbd-incoming-flight-data-tdd-h2.sql"})
public class IncomingFlightServiceImplTest {

	@Autowired
	private IncomingFlightDAO incomingFlightDAO;
	
	@Autowired
	private IncomingFlightsController ifc;

	@Before
	public void setUp() {

	}

   @Test
   public void fetchIncomingFlights() throws CustomException {
      IncomingFlightQuery incomingFlightQuery = new IncomingFlightQuery();
      incomingFlightQuery.setTerminal("T6");
      incomingFlightQuery.setFromDate(LocalDateTime.now().minusDays(1000));
      incomingFlightQuery.setToDate(LocalDateTime.now());
      incomingFlightQuery.setCarrierCode("SQ");
      BaseResponse<List<IncomingFlightModel>> outputFlight = ifc.search(incomingFlightQuery);
      assertNotNull(outputFlight);
   }
	*//**
	 * Test case for no result
	 * 
	 * @throws CustomException
	 *//*
	@Test
	public void noResultFound() throws CustomException {
		IncomingFlightQuery incomingFlightQuery = new IncomingFlightQuery();
		incomingFlightQuery.setTerminal("T6");
		incomingFlightQuery.setFromDate(LocalDateTime.now().minusDays(1000));
		incomingFlightQuery.setToDate(LocalDateTime.now().minusDays(1000));
		incomingFlightQuery.setCarrierCode("SQ");
		List<IncomingFlightModel> testData = incomingFlightDAO.fetch(incomingFlightQuery);
		assertEquals(0, testData.size());
	}
	
	*//**
	 * Test case for Result Found
	 * 
	 * @throws CustomException
	 *//*
	@Test
	public void resultFound() throws CustomException {
		IncomingFlightQuery incomingFlightQuery = new IncomingFlightQuery();
		incomingFlightQuery.setTerminal("T6");
		incomingFlightQuery.setFromDate(LocalDateTime.now().minusDays(1000));
		incomingFlightQuery.setToDate(LocalDateTime.now());
		incomingFlightQuery.setCarrierCode("SQ");
		List<IncomingFlightModel> testData = incomingFlightDAO.fetch(incomingFlightQuery);
		assertEquals(1, testData.size());
	}
}
*/