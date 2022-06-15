/*package com.ngen.cosys.shipment.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

*//**
 * This test class is for Unit testing various model classes
 * 
 * 
 * @author NIIT Technologies Ltd
 *
 *//*

@EnableAutoConfiguration
public class MaintainRemarkTest {

	@Test
	public void testMaintainRemark() {

		MaintainRemark mr = new MaintainRemark();
		mr.setShipmentId(68198989);
		mr.setShipmentNumber("121212");
		mr.setFlightKey("SQ111");
		mr.setRemarkType("OSI");
		mr.setShipmentRemarks("THE SHIPMENT REMAINING PROCESS IS BOOKED FOR REMAINING FLIGHT");
		mr.setShipmentRemarkId(1253);

		assertNotNull(mr);
		assertNotNull(mr.getShipmentId());
		assertNotNull(mr.getShipmentNumber());
		assertNotNull(mr.getFlightKey());
		assertNotNull(mr.getRemarkType());
		assertNotNull(mr.getShipmentRemarks());
		assertNotNull(mr.getShipmentRemarkId());

		MaintainRemark mr1 = new MaintainRemark(11211, null, 0, (long) 0, null, null, null, null, null, null, null, null, null, null);
		MaintainRemark mr2 = new MaintainRemark(1145, null, 0, (long) 0, null, null, null, null, null, null, null, null, null, null);
		assertEquals(61, mr.getShipmentRemarks().length());
		assertNotSame(mr1, mr2);
		assertNotEquals(mr.toString(), mr1.toString());
		assertNotEquals(mr.toString(), mr2.toString());
		assertNotEquals(mr1.toString(), mr2.toString());
	}

}
*/