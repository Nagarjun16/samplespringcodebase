/*package com.ngen.cosys.shipment.nawb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.ngen.cosys.shipment.model.ChargeDeclaration;
import com.ngen.cosys.shipment.model.CustomerAddressInfo;
import com.ngen.cosys.shipment.model.CustomerContactInfo;
import com.ngen.cosys.shipment.model.CustomerInfo;
import com.ngen.cosys.shipment.model.Routing;
import com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO;
import com.ngen.cosys.shipment.nawb.model.NeutralAWBMaster;
import com.ngen.cosys.shipment.nawb.model.SIDHeaderDetail;
import com.ngen.cosys.shipment.nawb.model.SearchSIDRQ;
import com.ngen.cosys.shipment.nawb.model.SearchStockRQ;
import com.ngen.cosys.shipment.nawb.model.Stock;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class NeutralAWBServiceImplTDDTest {
	private SIDHeaderDetail sidHeaderDetail;
	private SIDHeaderDetail sidHeaderDetail1;
	private List<SIDHeaderDetail> sidHeaderDetails;
	private List<Stock> stocks;
	private Stock stock;
	private Stock stock1;
	private final BigInteger sidHeaderId = new BigInteger("1");
	private final BigInteger sidCustomerDtlsId = new BigInteger("1");
	private final String sidNumber = "20180111152908";
	private final String shipmentNumber = "61820906933";
	private final String shipperName = "kaushlender";
	private final String consigneeName = "kaushal";
	private final String status = "Created";
	private final String createdBy = "SYSADMIN";
	private final LocalDateTime createdOn = LocalDateTime.now().minusDays(2);
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
	private final String stockCategoryCode = "OSC";
	private final String stockId = "123456";
	private final String carrierCode = "SQ";
	private final BigInteger awbStockDetailsId = new BigInteger("1");
	private final BigInteger awbStockId = new BigInteger("1");
	private final String awbNumber = "61820906933";
	@Autowired
	private NeutralAWBDAO neutralAWBDAO;

	@Before
	public void setUp() throws CustomException {
		sidHeaderDetail = new SIDHeaderDetail();
		sidHeaderDetail.setSidHeaderId(sidHeaderId);
		sidHeaderDetail.setSidNumber(sidNumber);
		sidHeaderDetail.setShipmentNumber(shipmentNumber);
		sidHeaderDetail.setShipperName(shipperName);
		sidHeaderDetail.setConsigneeName(consigneeName);
		sidHeaderDetail.setCreatedOn(createdOn);
		sidHeaderDetail.setStatus(status);
		sidHeaderDetail.setCreatedBy(createdBy);
		sidHeaderDetail.setSelectFlag(false);

		sidHeaderDetail1 = new SIDHeaderDetail();
		sidHeaderDetail1 = new SIDHeaderDetail();
		sidHeaderDetail1.setSidHeaderId(new BigInteger("2"));
		sidHeaderDetail1.setSidNumber("20180111152909");
		sidHeaderDetail1.setShipmentNumber("11620906934");
		sidHeaderDetail1.setShipperName("Ram");
		sidHeaderDetail1.setConsigneeName("Shyam");
		sidHeaderDetail1.setCreatedOn(LocalDateTime.now().minusDays(3));
		sidHeaderDetail1.setStatus(status);
		sidHeaderDetail1.setCreatedBy(createdBy);

		sidHeaderDetails = new ArrayList<>();
		sidHeaderDetails.add(sidHeaderDetail);

		stocks = new ArrayList<>();
		stock = new Stock();
		stock.setAwbStockDetailsId(awbStockDetailsId);
		stock.setAwbStockId(awbStockId);
		stock.setStockId(stockId);
		stock.setCarrierCode(carrierCode);
		stock.setStockCategoryCode(stockCategoryCode);
		stock.setAwbNumber(awbNumber);

		stock1 = new Stock();
		stock1.setAwbStockDetailsId(new BigInteger("2"));
		stock1.setAwbStockId(new BigInteger("2"));
		stock1.setStockId("123457");
		stock1.setCarrierCode(carrierCode);
		stock1.setStockCategoryCode("MAIL");
		stock1.setAwbNumber("61820906944");
		stocks.add(stock);
	}

	// Case1 if empty list returned by Dao searchSIDList method
	@Test
	@Sql(scripts = { "/tddscripts/nawb-schema-tdd-h2.sql", "/tddscripts/nawb-data-tdd-h2.sql" })
	public void testSearchSIDListFailScenarioWithNull() throws CustomException {
	   SearchSIDRQ searchSIDRQ = new SearchSIDRQ();
		sidHeaderDetails = new ArrayList<>();
		searchSIDRQ.setSidNumber("20180111152901");
		assertEquals(sidHeaderDetails.isEmpty(), neutralAWBDAO.searchSIDList(searchSIDRQ).isEmpty());
		
	}

	// Case2 Successful searchSIDList By SidNumber
	@Test
	@Sql(scripts = { "/tddscripts/nawb-schema-tdd-h2.sql", "/tddscripts/nawb-data-tdd-h2.sql" })
	public void testsearchSIDListBySIDNumberSuccessScenario() throws CustomException {
		SearchSIDRQ searchSIDRQ = new SearchSIDRQ();
		searchSIDRQ.setSidNumber("20180111152908");
		assertEquals(1, neutralAWBDAO.searchSIDList(searchSIDRQ).size());
		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ).get(0));

		assertEquals(sidHeaderDetails.get(0).getSidNumber(),
				neutralAWBDAO.searchSIDList(searchSIDRQ).get(0).getSidNumber());
		assertEquals(sidHeaderDetails.get(0).getShipmentNumber(),
				neutralAWBDAO.searchSIDList(searchSIDRQ).get(0).getShipmentNumber());
		assertEquals(sidHeaderDetails.get(0).getSidHeaderId(),
				neutralAWBDAO.searchSIDList(searchSIDRQ).get(0).getSidHeaderId());
		assertEquals(sidHeaderDetails.get(0).getStatus(), neutralAWBDAO.searchSIDList(searchSIDRQ).get(0).getStatus());
		assertEquals(sidHeaderDetails.get(0).getCreatedBy(),
				neutralAWBDAO.searchSIDList(searchSIDRQ).get(0).getCreatedBy());

	}

	// Case3 Successful searchSIDList without any criteria
	@Test
	@Sql(scripts = { "/tddscripts/nawb-schema-tdd-h2.sql", "/tddscripts/nawb-data-tdd-h2.sql" })
	public void testsearchSIDListSuccessScenario() throws CustomException {
		sidHeaderDetails.add(sidHeaderDetail1);
		SearchSIDRQ searchSIDRQ = new SearchSIDRQ();
		assertEquals(2, neutralAWBDAO.searchSIDList(searchSIDRQ).size());
		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ).get(0));

		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ).get(0).getSidHeaderId());
		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ).get(0).getStatus());
		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ).get(0).getCreatedBy());
		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ).get(1));
		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ).get(1).getConsigneeName());
		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ).get(1).getShipperName());
		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ).get(1).getSidNumber());
		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ).get(1).getShipmentNumber());
		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ).get(1).getSidHeaderId());
		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ).get(1).getStatus());
		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ).get(1).getCreatedBy());

	}

	// Case4 Successful searchSIDList By SidNumber
	@Test
	@Sql(scripts = { "/tddscripts/nawb-schema-tdd-h2.sql", "/tddscripts/nawb-data-tdd-h2.sql" })
	public void testsearchSIDDetailsBySIDHeaderIdSuccessScenario() throws CustomException {

		List<CustomerContactInfo> consigneeContactInfoList = new ArrayList<>();

		CustomerContactInfo consigneeContactInfoTe = new CustomerContactInfo();
		consigneeContactInfoTe.setSidCustomerAddressInfoId(new BigInteger("2"));
		consigneeContactInfoTe.setContactIdentifier("TE");
		consigneeContactInfoTe.setContactDetail("7503251528");

		CustomerContactInfo consigneeContactInfoFx = new CustomerContactInfo();
		consigneeContactInfoFx.setSidCustomerAddressInfoId(new BigInteger("2"));
		consigneeContactInfoFx.setContactIdentifier("FX");
		consigneeContactInfoFx.setContactDetail("5544343434344");

		consigneeContactInfoList.add(consigneeContactInfoTe);
		consigneeContactInfoList.add(consigneeContactInfoFx);

		List<CustomerContactInfo> shipperContactInfoList = new ArrayList<>();

		CustomerContactInfo shipperContactInfoTe = new CustomerContactInfo();
		shipperContactInfoTe.setSidCustomerAddressInfoId(new BigInteger("1"));
		shipperContactInfoTe.setContactIdentifier("TE");
		shipperContactInfoTe.setContactDetail("9560286992");
		CustomerContactInfo shipperContactInfoFx = new CustomerContactInfo();
		shipperContactInfoFx.setSidCustomerAddressInfoId(new BigInteger("1"));
		shipperContactInfoFx.setContactIdentifier("FX");
		shipperContactInfoFx.setContactDetail("4455443434343");

		shipperContactInfoList.add(shipperContactInfoTe);
		shipperContactInfoList.add(shipperContactInfoFx);

		CustomerAddressInfo shipperAddressInfo = new CustomerAddressInfo();
		shipperAddressInfo.setSidCustomerDtlsId(sidCustomerDtlsId);
		shipperAddressInfo.setSidCustomerAddressInfoId(new BigInteger("1"));
		shipperAddressInfo.setStreetAddress1("StreetAddress1");
		shipperAddressInfo.setCustomerPlace("CustomerPlace1");
		shipperAddressInfo.setStateCode("StateCod1");
		shipperAddressInfo.setCountryCode("IN");
		shipperAddressInfo.setPostalCode("110059");
		shipperAddressInfo.setCustomerContactInfo(shipperContactInfoList);

		CustomerAddressInfo consigneeAddressInfo = new CustomerAddressInfo();
		consigneeAddressInfo.setSidCustomerDtlsId(new BigInteger("2"));
		consigneeAddressInfo.setSidCustomerAddressInfoId(new BigInteger("2"));
		consigneeAddressInfo.setStreetAddress1("StreetAddress3");
		consigneeAddressInfo.setCustomerPlace("CustomerPlace2");
		consigneeAddressInfo.setStateCode("StateCod2");
		consigneeAddressInfo.setCountryCode("IN");
		consigneeAddressInfo.setPostalCode("110022");
		consigneeAddressInfo.setCustomerContactInfo(consigneeContactInfoList);

		CustomerInfo consigneeInfo = new CustomerInfo();
		consigneeInfo.setSidCustomerDtlsId(new BigInteger("2"));
		consigneeInfo.setSidHeaderId(sidHeaderId);
		consigneeInfo.setCustomerName(consigneeName);
		consigneeInfo.setCustomerType("CNE");
		consigneeInfo.setCustomerAddressInfo(consigneeAddressInfo);

		CustomerInfo shipperInfo = new CustomerInfo();
		shipperInfo.setSidCustomerDtlsId(sidCustomerDtlsId);
		shipperInfo.setSidHeaderId(sidHeaderId);
		shipperInfo.setCustomerName(shipperName);
		shipperInfo.setCustomerType("SHP");
		shipperInfo.setCustomerAddressInfo(shipperAddressInfo);

		List<Routing> routingList = new ArrayList<>();
		Routing routing = new Routing();
		routing.setSidHeaderId(sidHeaderId);
		routing.setCarrierCode("SQ");
		routing.setFrom("SIN");
		routing.setTo("HKG");
		routing.setFlightKey("SQ102");
		// routing.setFlightDate(LocalDateTime.now());
		routingList.add(routing);

		ChargeDeclaration paymentInfo = new ChargeDeclaration();
		paymentInfo.setSidHeaderId(sidHeaderId);
		paymentInfo.setPrepaIdCollectChargeDeclaration("C");
		paymentInfo.setCurrencyCode("USD");
		//paymentInfo.setCarriageValueDeclaration("1.00");
		//paymentInfo.setCustomsValueDeclaration("2.00");

		NeutralAWBMaster nawbMaster = new NeutralAWBMaster();
		nawbMaster.setSidHeaderId(sidHeaderId);
		nawbMaster.setAwbNumber(shipmentNumber);
		nawbMaster.setSidNumber(sidNumber);
		nawbMaster.setShipperInfo(shipperInfo);
		nawbMaster.setConsigneeInfo(consigneeInfo);
		// nawbMaster.setRouting(routingList);
		nawbMaster.setChargeDeclaration(paymentInfo);

		SearchSIDRQ searchSIDRQ = new SearchSIDRQ();
		searchSIDRQ.setSidHeaderId(sidHeaderId);
		if (neutralAWBDAO.searchSIDList(searchSIDRQ).isEmpty()) {
			System.out.println("empty " + neutralAWBDAO.searchSIDList(searchSIDRQ).size());
		}
		System.out.println("info " + neutralAWBDAO.searchSIDList(searchSIDRQ).size());
		// assertEquals(1,neutralAWBDAO.searchSIDList(searchSIDRQ).size());
		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ).get(0));
		// assertEquals(nawbMaster.getAwbNumber(),neutralAWBDAO.searchSIDList(searchSIDRQ).get(0).getShipmentNumber());
		// assertEquals(nawbMaster.getSidNumber(),neutralAWBDAO.searchSIDList(searchSIDRQ).get(0).getSidNumber());
		// assertEquals(nawbMaster.getConsigneeInfo().getCustomerName(),neutralAWBDAO.searchNAWBDetails(searchSIDRQ).get(0).getConsigneeInfo().getCustomerName());
		// assertEquals(nawbMaster.getConsigneeInfo().getCustomerType(),neutralAWBDAO.searchSIDList(searchSIDRQ).get(0).getConsigneeInfo().getCustomerType());
		// assertEquals(nawbMaster.getShipperInfo().getCustomerName(),neutralAWBDAO.searchSIDDetails(searchSIDRQ).get(0).getShipperInfo().getCustomerName());
		// assertEquals(nawbMaster.getShipperInfo().getCustomerType(),neutralAWBDAO.searchSIDDetails(searchSIDRQ).get(0).getShipperInfo().getCustomerType());
		//
		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ).get(0).getShipperName());
		// assertEquals(nawbMaster.getShipperInfo().getCustomerAddressInfo().getSidCustomerDtlsId(),neutralAWBDAO.searchSIDList(searchSIDRQ).get(0).getShipperName());
		// assertEquals(nawbMaster.getShipperInfo().getCustomerAddressInfo().getSidCustomerAddressInfoId(),neutralAWBDAO.searchSIDList(searchSIDRQ));
		// assertEquals(nawbMaster.getShipperInfo().getCustomerAddressInfo().getStreetAddress1(),neutralAWBDAO.searchSIDList(searchSIDRQ));

		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ).get(0));

		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ));

		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ));

		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ));
		// assertEquals(1,neutralAWBDAO.searchSIDDetails(searchSIDRQ).get(0).getRouting().size());
		// assertEquals(nawbMaster.getRouting().get(0).getCarrierCode(),neutralAWBDAO.searchSIDDetails(searchSIDRQ).get(0).getRouting().get(0).getCarrierCode());
		// assertEquals(nawbMaster.getRouting().get(0).getFrom(),neutralAWBDAO.searchSIDDetails(searchSIDRQ).get(0).getRouting().get(0).getFrom());
		// assertEquals(nawbMaster.getRouting().get(0).getFlightKey(),neutralAWBDAO.searchSIDDetails(searchSIDRQ).get(0).getRouting().get(0).getFlightKey());
		// assertEquals(nawbMaster.getRouting().get(0).getFlightDate().format(formatter),neutralAWBDAO.searchSIDDetails(searchSIDRQ).get(0).getRouting().get(0).getFlightDate().format(formatter));

		// assertNotNull(neutralAWBDAO.searchSIDDetails(searchSIDRQ).get(0).getRouting());
		// assertEquals(1,neutralAWBDAO.searchSIDDetails(searchSIDRQ).get(0).getRouting().size());
		// assertEquals(nawbMaster.getRouting().get(0).getCarrierCode(),neutralAWBDAO.searchSIDDetails(searchSIDRQ).get(0).getRouting().get(0).getCarrierCode());
		// assertEquals(nawbMaster.getRouting().get(0).getFrom(),neutralAWBDAO.searchSIDDetails(searchSIDRQ).get(0).getRouting().get(0).getFrom());
		// assertEquals(nawbMaster.getRouting().get(0).getFlightKey(),neutralAWBDAO.searchSIDDetails(searchSIDRQ).get(0).getRouting().get(0).getFlightKey());
		// assertEquals(nawbMaster.getRouting().get(0).getFlightDate().format(formatter),neutralAWBDAO.searchSIDDetails(searchSIDRQ).get(0).getRouting().get(0).getFlightDate().format(formatter));

		assertNotNull(neutralAWBDAO.searchSIDList(searchSIDRQ));
	}

	// Case5 Successful searchSIDList By StockId,CarrierCode,CategoryCode
	@Test
	@Sql(scripts = { "/tddscripts/nawb-schema-tdd-h2.sql", "/tddscripts/nawb-data-tdd-h2.sql" })
	public void testsearchStockByStockIdCarrierCodeCategoryCodeSuccessScenario() throws CustomException {
		SearchStockRQ searchStockRQ = new SearchStockRQ();
		searchStockRQ.setCarrierCode(carrierCode);
		searchStockRQ.setStockCategoryCode(stockCategoryCode);
		searchStockRQ.setStockId(stockId);
		assertEquals(1, neutralAWBDAO.searchAWBFromStockList(searchStockRQ).size());
		assertNotNull(neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(0));
		assertEquals(stocks.get(0).getAwbStockDetailsId(),
				neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(0).getAwbStockDetailsId());
		assertEquals(stocks.get(0).getAwbStockId(),
				neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(0).getAwbStockId());
		assertEquals(stocks.get(0).getAwbNumber(),
				neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(0).getAwbNumber());
		assertEquals(stocks.get(0).getStockId(),
				neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(0).getStockId());
		assertEquals(stocks.get(0).getStockCategoryCode(),
				neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(0).getStockCategoryCode());

	}

	// Case6 Successful searchSIDList By CarrierCode
	@Test
	@Sql(scripts = { "/tddscripts/nawb-schema-tdd-h2.sql", "/tddscripts/nawb-data-tdd-h2.sql" })
	public void testsearchStockByCarrierCodeSuccessScenario() throws CustomException {
		stocks.add(stock1);
		SearchStockRQ searchStockRQ = new SearchStockRQ();
		searchStockRQ.setCarrierCode(carrierCode);
		assertEquals(2, neutralAWBDAO.searchAWBFromStockList(searchStockRQ).size());
		assertNotNull(neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(0));
		assertEquals(stocks.get(0).getAwbStockDetailsId(),
				neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(0).getAwbStockDetailsId());
		assertEquals(stocks.get(0).getAwbStockId(),
				neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(0).getAwbStockId());
		assertEquals(stocks.get(0).getAwbNumber(),
				neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(0).getAwbNumber());
		assertEquals(stocks.get(0).getStockId(),
				neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(0).getStockId());
		assertEquals(stocks.get(0).getStockCategoryCode(),
				neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(0).getStockCategoryCode());

		assertEquals(stocks.get(1).getAwbStockDetailsId(),
				neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(1).getAwbStockDetailsId());
		assertEquals(stocks.get(1).getAwbStockId(),
				neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(1).getAwbStockId());
		assertEquals(stocks.get(1).getAwbNumber(),
				neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(1).getAwbNumber());
		assertEquals(stocks.get(1).getStockId(),
				neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(1).getStockId());
		assertEquals(stocks.get(1).getStockCategoryCode(),
				neutralAWBDAO.searchAWBFromStockList(searchStockRQ).get(1).getStockCategoryCode());

	}

}
*/