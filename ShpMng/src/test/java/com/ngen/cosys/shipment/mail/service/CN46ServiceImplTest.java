/*package com.ngen.cosys.shipment.mail.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.mail.controller.CN46Controller;
import com.ngen.cosys.shipment.mail.model.CN46Details;
import com.ngen.cosys.shipment.mail.model.CreateCN46;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = { "/tddscripts/mail-cn46-schema-tdd-h2.sql", "/tddscripts/mail-cn46-data-tdd-h2.sql" })
public class CN46ServiceImplTest {

	@Autowired
	CN46Controller cn46Controller;

	*//**
	 * @throws CustomException
	 *//*
	@Test
	public void insertCN46RequestTest() throws CustomException {
	   	CreateCN46 cn = new CreateCN46();
		cn.setObservations("TEST DATA");
		cn.setDnNumberCount(0);
		cn.setAdminOfOriginOfMails("SIN");
		cn.setAirportOfLoading("SIN");
		cn.setAirportOfOffLoading("DEL");
		cn.setDestinationOffice("DEL");
		cn.setFlightId(123456);
		cn.setSegmentId(654320);
		cn.setAirmailManifestId(1);
		cn.setTrolleyNumber("AKE12345SQ");
		cn.setFlightKey("SQ001");
		cn.setFlightDate(LocalDate.now());
		cn.setBulkFlag(false);
		cn.setFlagCRUD("C");
		CN46Details req = new CN46Details();
		req.setManifestId(1);
		req.setMailNumber("SGSINAINDELBAEM92021");
		req.setUldNumber(null);
		req.setWeight(BigDecimal.valueOf(150.5));
		req.setOriginOfficeExchange("SGSINA");
		req.setDestinationOfficeExchange("INDELB");
		req.setAirportOfTranshipment("SIN");
		req.setAirportOfOffloading("DEL");
		req.setDateOfDispactch(LocalDate.now());
		req.setLetterPost(5);
		req.setCp(10);
		req.setOtherItems(15);
		req.setRemarks("FLIGHT DEPARTED");
		req.setAirmailManifestShipmentId(2);
		List<CN46Details> list = new ArrayList<CN46Details>();
		list.add(req);
		cn.setCn46Details(list);
		cn46Controller.insertCN46Request(cn);
		CreateCN46 cn46 = new CreateCN46();
		cn46.setObservations("TEST DATA");
		cn46.setDnNumberCount(0);
		cn46.setAdminOfOriginOfMails("SIN");
		cn46.setAirportOfLoading("SIN");
		cn46.setAirportOfOffLoading("DEL");
		cn46.setDestinationOffice("DEL");
		cn46.setFlightId(123456);
		cn46.setSegmentId(654320);
		cn46.setAirmailManifestId(1);
		cn46.setTrolleyNumber("AKE12345SQ");
		cn46.setFlightKey("SQ001");
		cn46.setFlightDate(LocalDate.now());
		cn46.setBulkFlag(false);
		cn46.setFlagCRUD("U");
		CN46Details request = new CN46Details();
		request.setManifestId(1);
		request.setMailNumber("SGSINAINDELBAEM92021");
		request.setUldNumber(null);
		request.setWeight(BigDecimal.valueOf(150.5));
		request.setOriginOfficeExchange("SGSINA");
		request.setDestinationOfficeExchange("INDELB");
		request.setAirportOfTranshipment("SIN");
		request.setAirportOfOffloading("DEL");
		request.setDateOfDispactch(LocalDate.now());
		request.setLetterPost(5);
		request.setCp(10);
		request.setOtherItems(15);
		request.setRemarks("FLIGHT DEPARTED");
		request.setAirmailManifestShipmentId(2);
		request.setFlagCRUD("U");
		List<CN46Details> list1 = new ArrayList<CN46Details>();
		list.add(request);
		cn46.setCn46Details(list1);
		//cn46Controller.insertCN46Request(cn46);
		//int j = 1;
	//	assertEquals(1, j);
	 
	}
}
*/