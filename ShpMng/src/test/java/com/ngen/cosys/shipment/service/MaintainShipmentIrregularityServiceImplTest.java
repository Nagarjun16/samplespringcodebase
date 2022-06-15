/*package com.ngen.cosys.shipment.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.dao.MaintainShipmentIrregularityDAO;
import com.ngen.cosys.shipment.model.IrregularityDetail;
import com.ngen.cosys.shipment.model.IrregularitySummary;
import com.ngen.cosys.shipment.model.SearchShipmentIrregularity;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class MaintainShipmentIrregularityServiceImplTest {

	@Mock
	private MaintainShipmentIrregularityDAO maintainShipmentIrregularityDAO;
	
	@InjectMocks
	private MaintainShipmentIrregularityServiceImpl maintainShipmentIrregularityServiceImpl;
	
	@InjectMocks
	private IrregularitySummary maintainShipmentIrregularity;
    
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
    public void testSearch() throws CustomException {

		 List<String> shc = new ArrayList<>();
	     shc.add("EAT");
	     shc.add("PER EAT");

		List<IrregularitySummary> searchList = new ArrayList<>();
		IrregularitySummary irregularity = new IrregularitySummary("123", "AWB", "123-456", "BLR", "DEL", 10, 100.0, "EATABLES", shc, null, 1);
		searchList.add(irregularity);
		
		SearchShipmentIrregularity search = new SearchShipmentIrregularity();
		
		when(maintainShipmentIrregularityDAO.search(search)).thenReturn(irregularity);
		
		IrregularitySummary irr = new IrregularitySummary();
		irr  =  maintainShipmentIrregularityDAO.search(search);
		verify(maintainShipmentIrregularityDAO, atLeastOnce()).search(search);
		assertEquals("AWB", irr.getShipmentType());
		assertEquals("123", irr.getShipmentId());
    }
	
	@Test
    public void testAdd() throws CustomException {

		 List<String> shc = new ArrayList<>();
	      shc.add("EAT");
	      shc.add("PER EAT");

	      List<IrregularityDetail> searchList = new ArrayList<>();
	      IrregularityDetail irrDetail = new IrregularityDetail(1, "123", "FDCA", new BigInteger("2"), new BigDecimal("10.0"), "SQA101", LocalDateTime.of(2018-01-01,1,1,1,1), "OK", true, "SIN", "BLR", LocalDate.now());
	      searchList.add(irrDetail);
	      
	      when(maintainShipmentIrregularityDAO.add(irrDetail)).thenReturn(irrDetail);
	      
	      IrregularityDetail irrD = maintainShipmentIrregularityDAO.add(irrDetail);
	      assertEquals(1, irrD.getSequenceNumber());
	      assertEquals("123", irrD.getShipmentNumber());
    }
	
	@Test
    public void testSave() throws CustomException {

		 List<String> shc = new ArrayList<>();
	      shc.add("EAT");
	      shc.add("PER EAT");

	      List<IrregularityDetail> searchList = new ArrayList<>();
	      IrregularityDetail irrDetail = new IrregularityDetail(1, "123", "FDCA", new BigInteger("2"), new BigDecimal("10.0"), "SQA101", LocalDateTime.of(2018-01-01,1,1,1,1), "OK", true, "SIN", "BLR", LocalDate.now());
	      searchList.add(irrDetail);
		
		when(maintainShipmentIrregularityDAO.save(irrDetail)).thenReturn(irrDetail);

		 IrregularityDetail irrD = maintainShipmentIrregularityDAO.save(irrDetail);
         assertEquals(1, irrD.getSequenceNumber());
         assertEquals("123", irrD.getShipmentNumber());
	}
	
	@Test
    public void testDelete() throws CustomException {

		 List<String> shc = new ArrayList<>();
	      shc.add("EAT");
	      shc.add("PER EAT");

	      List<IrregularityDetail> searchList = new ArrayList<>();
          IrregularityDetail irrDetail = new IrregularityDetail(1, "123", "FDCA", new BigInteger("2"), new BigDecimal("10.0"), "SQA101", LocalDateTime.of(2018-01-01,1,1,1,1), "OK", true, "SIN", "BLR", LocalDate.now());
          searchList.add(irrDetail);
		
		when(maintainShipmentIrregularityDAO.delete(irrDetail)).thenReturn(irrDetail);

		IrregularityDetail irrD = maintainShipmentIrregularityDAO.delete(irrDetail);
        assertEquals(1, irrD.getSequenceNumber());
        assertEquals("123", irrD.getShipmentNumber());
    }

}
*/