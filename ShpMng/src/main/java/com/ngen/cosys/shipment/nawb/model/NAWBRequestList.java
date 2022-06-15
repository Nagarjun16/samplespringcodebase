package com.ngen.cosys.shipment.nawb.model;

import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@Getter
@Setter
@Component
@ToString
@ApiModel
@NoArgsConstructor
public class NAWBRequestList extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String departCode;
	private String awbNumber;
	private String shipperAccNo;
	private String shipperName;
	private String shipperAdd;
	private String shipperCountry;
	private String shipperPostalCode;
	private String consigneeAccNo;
	private String consigneeName;
	private String consigneeAdd;
	private String consigneeCountry;
	private String consigneePostalCode;
	private String carrierAgentName;
	private String carrierAgentCity;
	private String accountInfo;
	private String agentCode;
	private String accountNo;
	private String departApt;
	private String refNo;
	private String tc;
	private String fCarrier;
	private String routeTo;
	private String routeBy;
	private String destTo;
	private String destBy;
	private String currencies;
	private String chgsCode;
	private String wtPPD;
	private String wtColl;
	private String othPPD;
	private String othColl;
	private String carriage;
	private String customValue;
	private String destApt;
	private String insurance;
	private String handlingDetails;
	private String handlingCode;
	private String sci;
	private String natureOfGoods;
	private String totalNoPieces;
	private String totalGrossWeight;
	private String totalCost;
	private String prepaidWc;
	private String collectWc;
	private String prepaidVc;
	private String collectVc;
	private String prepaidTax;
	private String collectTax;
	private String prepaidOcA;
	private String collectOcA;
	private String prepaidOcC;
	private String collectOcC;
	private String totalPrepaid;
	private String totalCollect;
	private String ccCharges;
	private String ccRate;
	private String totalChargers;
	private String destCharges;
	private String shipperSign;
	private String printQueue;
	private List<NeutralAirWayBillItemDetails> otherCharges;
	private List<NeutralAirWayBillItemDetails> nawbDetails;
	private String printerName;
	private String aentIATACode;
	private List<String> dueChargeAmount;
	private List<String> accountingInfo;
	private List<String> commodityList;
	private List<String> handlingInfo;
	private String noPieces;
	private String kg;
	private String rateCharges;
	private String chargableWeight;
	private String itemNo;
	private String grossWeight;
	private List<String> requestedFlight;
	private List<String> requestedFlightDate;
	private String firstFlightCarrier;
	private String insuranceValueDeclaration;
	private String executionDate;
	private String executionPlace;
	private List<String> consigneeContactDetail;
	private List<String> shipperContactDetail;
	private String issuingAgentName;
	private String issuingAgentPlace;
	private String rateClass;
	private String ttotal;
	private String carriersExecutionAuthorisationSignature;
	private List<CommodityChargesList> commodityChargesList;
	private List<FlightBookingToList> flightBookingToList;
	private List<RoutingToByList> routingToByList;
}