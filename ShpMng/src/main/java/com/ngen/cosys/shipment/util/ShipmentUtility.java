package com.ngen.cosys.shipment.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.billing.api.Charge;
import com.ngen.cosys.billing.api.enums.ChargeEvents;
import com.ngen.cosys.billing.api.enums.ReferenceType;
import com.ngen.cosys.billing.api.model.BillingShipment;
import com.ngen.cosys.billing.api.model.ChargeableEntity;
import com.ngen.cosys.customs.api.SubmitDataToCustoms;
import com.ngen.cosys.customs.api.enums.CustomsEventTypes;
import com.ngen.cosys.customs.api.enums.CustomsShipmentType;
import com.ngen.cosys.customs.api.model.CustomsShipmentInfo;
import com.ngen.cosys.customs.api.model.CustomsShipmentLocalAuthorityDetails;
import com.ngen.cosys.customs.api.model.CustomsShipmentLocalAuthorityInfo;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.framework.model.ErrorBO;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.shipment.awb.dao.ShipmentAWBDocumentDao;
import com.ngen.cosys.shipment.awb.model.AWB;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterLocalAuthorityDetails;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterLocalAuthorityInfo;
import com.ngen.cosys.shipment.model.FWB;
import com.ngen.cosys.shipment.model.ShipmentMaster;
import com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO;
import com.ngen.cosys.shipment.nawb.model.NawbChargeValues;
import com.ngen.cosys.shipment.nawb.model.NeutralAWBMaster;

@Component
public class ShipmentUtility {

	@Autowired
	private ShipmentAWBDocumentDao shipmentAWBDocumentDAO;

	@Autowired
	private SubmitDataToCustoms submitDataToCustoms;

	@Autowired
	private NeutralAWBDAO neutralAWBDAO;

	private ShipmentUtility() {
		// Blank Constructor.
	}

	public static boolean isBaseModelHasError(BaseBO baseModel) {
		FWB fwb = null;
		if (baseModel instanceof FWB) {
			fwb = (FWB) baseModel;
			if (Optional.ofNullable(fwb.getMessageList()).isPresent() && !fwb.getMessageList().isEmpty()) {
				return true;
			}
			/* Temp */
			if (!CollectionUtils.isEmpty(fwb.getSsrInfo()) && fwb.getSsrInfo().stream()
					.filter(e -> e.getMessageList() != null && !e.getMessageList().isEmpty()).count() > 0) {
				return true;
			}
			if (!CollectionUtils.isEmpty(fwb.getOsiInfo()) && fwb.getOsiInfo().stream()
					.filter(e -> e.getMessageList() != null && !e.getMessageList().isEmpty()).count() > 0) {
				return true;
			}
			/* Temp over */
			if (!CollectionUtils.isEmpty(fwb.getOtherCustomsInfo()) && fwb.getOtherCustomsInfo().stream()
					.filter(e -> e.getMessageList() != null && !e.getMessageList().isEmpty()).count() > 0) {
				return true;
			}

			if (!CollectionUtils.isEmpty(fwb.getRouting()) && fwb.getRouting().stream()
					.filter(e -> e.getMessageList() != null && !e.getMessageList().isEmpty()).count() > 0) {
				return true;
			}

			/* For Shipper */
			if (!ObjectUtils.isEmpty(fwb.getShipperInfo()) && fwb.getShipperInfo().getMessageList() != null
					&& !fwb.getShipperInfo().getMessageList().isEmpty()) {
				return true;
			}

			/* For Consignee */
			if (!ObjectUtils.isEmpty(fwb.getConsigneeInfo()) && fwb.getConsigneeInfo().getMessageList() != null
					&& !fwb.getConsigneeInfo().getMessageList().isEmpty()) {
				return true;
			}

			/* For Also Notify */
			if (!ObjectUtils.isEmpty(fwb.getAlsoNotify()) && fwb.getAlsoNotify().getMessageList() != null
					&& !fwb.getAlsoNotify().getMessageList().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public static FWB getBaseModelErrorList(BaseBO baseModel) {
		FWB fwb = null;
		if (baseModel instanceof FWB) {
			fwb = (FWB) baseModel;
			fwb.getMessageList()
					.add((ErrorBO) fwb.getSsrInfo().stream()
							.filter(e -> e.getMessageList() != null && !e.getMessageList().isEmpty())
							.collect(Collectors.toList()));

			fwb.getMessageList()
					.add((ErrorBO) fwb.getOsiInfo().stream()
							.filter(e -> e.getMessageList() != null && !e.getMessageList().isEmpty())
							.collect(Collectors.toList()));

			fwb.getMessageList()
					.add((ErrorBO) fwb.getOtherCustomsInfo().stream()
							.filter(e -> e.getMessageList() != null && !e.getMessageList().isEmpty())
							.collect(Collectors.toList()));

			fwb.getMessageList()
					.add((ErrorBO) fwb.getRouting().stream()
							.filter(e -> e.getMessageList() != null && !e.getMessageList().isEmpty())
							.collect(Collectors.toList()));
		}
		return fwb;
	}

	public AWB calculateBilling(AWB awbDetails) throws CustomException {
		/* for export flow */
		if (MultiTenantUtility.isTenantCityOrAirport(awbDetails.getOrigin())) {
			BillingShipment billingShipment = new BillingShipment();
			billingShipment.setShipmentNumber(awbDetails.getShipmentNumber());
			billingShipment.setShipmentDate(awbDetails.getShipmentdate());
			billingShipment.setProcessType(ChargeEvents.PROCESS_EXP.getEnum());
			billingShipment.setEventType(ChargeEvents.EXP_SHIPMENT_UPDATE);
			billingShipment.setUserCode(awbDetails.getLoggedInUser());
			billingShipment.setHandlingTerminal(awbDetails.getTerminal());

			if (awbDetails.getOtherChargeInfo().isCollectBankEndorsementClearanceLetter()) {
				billingShipment.setBankEndorsementFlag("Y");
			} else {
				billingShipment.setBankEndorsementFlag("N");
			}

			//
			if (StringUtils.isEmpty(awbDetails.getShipper().getAppointedAgentCode())
					|| !StringUtils.isEmpty(awbDetails.getShipper().getAppointedAgentCode())
							&& "EXX".equalsIgnoreCase(awbDetails.getShipper().getAppointedAgentCode())) {
				billingShipment.setCustomerId(awbDetails.getShipper().getCustomerId());
			} else {
				billingShipment.setCustomerId(awbDetails.getShipper().getAppointedAgent());
			}
			billingShipment.setHandlingTerminal(awbDetails.getTerminal());
			Charge.calculateCharge(billingShipment);
			/* for import flow */
		} else if (MultiTenantUtility.isTenantCityOrAirport(awbDetails.getDestination())) {
			// update the charges--
			BillingShipment billingShipment = new BillingShipment();
			billingShipment.setShipmentNumber(awbDetails.getShipmentNumber());
			billingShipment.setShipmentDate(awbDetails.getShipmentdate());
			billingShipment.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
			billingShipment.setEventType(ChargeEvents.IMP_SHIPMENT_UPDATE);
			billingShipment.setUserCode(awbDetails.getLoggedInUser());
			billingShipment.setHandlingTerminal(awbDetails.getTerminal());
			if (awbDetails.getOtherChargeInfo().isCollectBankEndorsementClearanceLetter()) {
				billingShipment.setBankEndorsementFlag("Y");
			} else {
				billingShipment.setBankEndorsementFlag("N");
			}

			if (StringUtils.isEmpty(awbDetails.getConsignee().getAppointedAgentCode())
					|| (!StringUtils.isEmpty(awbDetails.getConsignee().getAppointedAgentCode())
							&& "IXX".equalsIgnoreCase(awbDetails.getConsignee().getAppointedAgentCode()))) {
				// take the customer details customerId
				billingShipment.setCustomerId(awbDetails.getConsignee().getCustomerId());
			} else {
				// take the Appointed agent
				billingShipment.setCustomerId(awbDetails.getConsignee().getAppointedAgent());
			}

			Charge.calculateCharge(billingShipment);
			// for new charge creation --
			BigDecimal exchangeRate = shipmentAWBDocumentDAO.fetchExchangeRate(awbDetails);

			if (StringUtils.isEmpty(exchangeRate)
					&& (!StringUtils.isEmpty(awbDetails.getOtherChargeInfo().getCurrency()))) {
				awbDetails.addError("billing.no.exchg.rate.available", "ExchangeRate", ErrorType.ERROR);
				return awbDetails;
			}
			if (StringUtils.isEmpty(exchangeRate)) {
				exchangeRate = BigDecimal.ZERO;
			}
			ChargeableEntity chargeableEntity = new ChargeableEntity();
			chargeableEntity.setReferenceId(awbDetails.getShipmentId());
			chargeableEntity.setEventType(ChargeEvents.IMP_CC_CHARGE);
			chargeableEntity.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
			chargeableEntity.setReferenceType(ReferenceType.SHIPMENT.getReferenceType());
			chargeableEntity.setHandlingTerminal(awbDetails.getTerminal());
			chargeableEntity.setUserCode(awbDetails.getLoggedInUser());
			chargeableEntity.setCarrierCode(awbDetails.getCarrierCode());
			// always take the Appointed agent as customer
			chargeableEntity.setCustomerId(awbDetails.getConsignee().getAppointedAgent());

			// calculating the amount
			// get total charge
			BigDecimal ccCharges = BigDecimal.ZERO;
			BigDecimal freightCharges = BigDecimal.ZERO;

			// add ccFee
			if (!StringUtils.isEmpty(awbDetails.getOtherChargeInfo().getTotalCollectChargesChargeAmount())
					&& !StringUtils.isEmpty(awbDetails.getOtherChargeInfo().getExchangeRate())) {

				ccCharges = (awbDetails.getOtherChargeInfo().getFreightCharges()
						.add(awbDetails.getOtherChargeInfo().getValuationCharges()))
								.multiply(awbDetails.getOtherChargeInfo().getExchangeRate()
										.multiply(awbDetails.getCcFeeprecentage()))
								.divide(BigDecimal.valueOf(100));

				ccCharges = ccCharges.compareTo(awbDetails.getMinccFee()) > 0 ? ccCharges : awbDetails.getMinccFee();

				freightCharges = (awbDetails.getOtherChargeInfo().getFreightCharges()
						.add(awbDetails.getOtherChargeInfo().getValuationCharges()
								.add(awbDetails.getOtherChargeInfo().getTax()
										.add(awbDetails.getOtherChargeInfo().getDueFromAgent()
												.add(awbDetails.getOtherChargeInfo().getDueFromAirline())))))
														.multiply(awbDetails.getOtherChargeInfo().getExchangeRate());

				// ccCharges = awbDetails.getOtherChargeInfo().getCcFee();
				// freightCharges =
				// awbDetails.getOtherChargeInfo().getDestinationCurrencyChargeAmount();
			}

			chargeableEntity.setQuantity(ccCharges);
			if ("CC".equalsIgnoreCase(awbDetails.getChargeCode()) && chargeableEntity.getQuantity() != null
					&& (chargeableEntity.getQuantity().compareTo(BigDecimal.ZERO) > 0)) {
				Charge.calculateCharge(chargeableEntity);
				chargeableEntity.setEventType(ChargeEvents.IMP_FREIGHT_CHARGE);
				chargeableEntity.setQuantity(freightCharges);
				Charge.calculateCharge(chargeableEntity);

			}

		}
		return awbDetails;
	}

	public void calculateLocationCharges(ShipmentMaster shipment) throws CustomException {

		BillingShipment ship = new BillingShipment();
		ship.setShipmentNumber(shipment.getShipmentNumber());
		ship.setShipmentDate(shipment.getShipmentDate());
		ship.setUserCode(shipment.getLoggedInUser());
		ship.setHandlingTerminal(shipment.getTerminal());

		if (shipment.getHouseInformation() != null
				&& !ObjectUtils.isEmpty(shipment.getHouseInformation().getHwbNumber())
				&& !ObjectUtils.isEmpty(shipment.getHouseInformation().getHouseId())) {
			ship.setHouseNumber(shipment.getHouseInformation().getHwbNumber());
			ship.setShipmentHouseId(new BigInteger(shipment.getHouseInformation().getHouseId()));
		}

		if (MultiTenantUtility.isTenantCityOrAirport(shipment.getDestination())) {
			ship.setEventType(ChargeEvents.IMP_SHIPMENT_UPDATE);
			ship.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
		} else {
			ship.setEventType(ChargeEvents.EXP_SHIPMENT_UPDATE);
			ship.setProcessType(ChargeEvents.PROCESS_EXP.getEnum());
		}
		Charge.calculateCharge(ship);

	}

	/**
	 * Method to cancel the charge for PP shipment which would have been charged
	 * earlier
	 * 
	 * @param awbDetails
	 */
	public void cancelShipmentCharge(AWB awbDetails) {
		ChargeableEntity chargeableEntity = new ChargeableEntity();
		chargeableEntity.setReferenceId(awbDetails.getShipmentId());
		chargeableEntity.setEventType(ChargeEvents.IMP_CC_CHARGE);
		chargeableEntity.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
		chargeableEntity.setReferenceType(ReferenceType.SHIPMENT.getReferenceType());
		chargeableEntity.setHandlingTerminal(awbDetails.getTerminal());
		chargeableEntity.setUserCode(awbDetails.getLoggedInUser());
		chargeableEntity.setCarrierCode(awbDetails.getCarrierCode());
		Charge.cancelCharge(chargeableEntity);
		chargeableEntity.setEventType(ChargeEvents.IMP_FREIGHT_CHARGE);
		Charge.cancelCharge(chargeableEntity);
	}

	/**
	 * Method to submit data to customs
	 * 
	 * @param awbDetails
	 * @throws CustomException
	 */
	public void submitToCustoms(AWB awbDetails) throws CustomException {
		// Submit to customs
		CustomsShipmentInfo customsShipmentInfo = new CustomsShipmentInfo();
		customsShipmentInfo.setShipmentNumber(awbDetails.getShipmentNumber());
		customsShipmentInfo.setShipmentDate(awbDetails.getShipmentdate());
		customsShipmentInfo.setOrigin(awbDetails.getOrigin());
		customsShipmentInfo.setDestination(awbDetails.getDestination());
		customsShipmentInfo.setPartShipment(awbDetails.getPartShipment());
		customsShipmentInfo.setTenantId(awbDetails.getTenantAirport());
		customsShipmentInfo.setEventType(CustomsEventTypes.Type.DOCUMENT_CREATION);
		customsShipmentInfo.setCreatedBy(awbDetails.getLoggedInUser());
		customsShipmentInfo.setCreatedOn(LocalDateTime.now());
		customsShipmentInfo.setModifiedBy(awbDetails.getLoggedInUser());
		customsShipmentInfo.setModifiedOn(LocalDateTime.now());
		customsShipmentInfo.setFlightId(awbDetails.getFlightId());
		customsShipmentInfo.setFlightKey(awbDetails.getFlightKey());
		if (!ObjectUtils.isEmpty(awbDetails.getFlightDate())) {
			customsShipmentInfo.setFlightDate(LocalDate.of(awbDetails.getFlightDate().getYear(),
					awbDetails.getFlightDate().getMonth(), awbDetails.getFlightDate().getDayOfMonth()));
		}

		if (MultiTenantUtility.isTenantAirport(awbDetails.getOrigin())
				&& !CollectionUtils.isEmpty(awbDetails.getLocalAuthority())) {
			for (ShipmentMasterLocalAuthorityInfo localAuthority : awbDetails.getLocalAuthority()) {
				if (!CollectionUtils.isEmpty(localAuthority.getDetails())) {
					List<CustomsShipmentLocalAuthorityDetails> localAuthorityDetails = new ArrayList<>();
					for (ShipmentMasterLocalAuthorityDetails t : localAuthority.getDetails()) {
						if (StringUtils.isEmpty(t.getDeliveryOrderNo())) {
							CustomsShipmentLocalAuthorityDetails customsShipmentLocalAuthorityDetails = new CustomsShipmentLocalAuthorityDetails();
							customsShipmentLocalAuthorityDetails.setLicense(t.getLicense());
							customsShipmentLocalAuthorityDetails.setRemarks(t.getRemarks());
							// Convert string to date
							if (!StringUtils.isEmpty(t.getTsRedocFlightDate())) {
								DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00");
								// convert String to LocalDate
								LocalDate tsRedocFlightDate = LocalDate.parse(t.getTsRedocFlightDate(), dateFormatter);
								customsShipmentLocalAuthorityDetails.setTsRedocFlightDate(tsRedocFlightDate);
							}
							customsShipmentLocalAuthorityDetails.setTsRedocFlightKey(t.getTsRedocFlightKey());
							customsShipmentLocalAuthorityDetails.setReferenceNumber(t.getReferenceNumber());
							// Set the Appointed Agent if it is empty
							if (ObjectUtils.isEmpty(t.getCustomerAppAgentId())
									&& !ObjectUtils.isEmpty(awbDetails.getConsignee())) {
								customsShipmentLocalAuthorityDetails
										.setCustomerAppAgentId(awbDetails.getConsignee().getAppointedAgent());
							} else if (ObjectUtils.isEmpty(t.getCustomerAppAgentId())
									&& !ObjectUtils.isEmpty(awbDetails.getShipper())) {
								customsShipmentLocalAuthorityDetails
										.setCustomerAppAgentId(awbDetails.getShipper().getAppointedAgent());
							} else {
								customsShipmentLocalAuthorityDetails.setCustomerAppAgentId(t.getCustomerAppAgentId());
							}

							// Add it to the list
							localAuthorityDetails.add(customsShipmentLocalAuthorityDetails);
						}
					}

					if (!CollectionUtils.isEmpty(localAuthorityDetails)) {
						// Customs Local Authority Info
						CustomsShipmentLocalAuthorityInfo customsShipmentLocalAuthorityInfo = new CustomsShipmentLocalAuthorityInfo();
						customsShipmentLocalAuthorityInfo.setType(localAuthority.getType());
						customsShipmentLocalAuthorityInfo.setDetails(localAuthorityDetails);
						// Set the local authority
						customsShipmentInfo.setLocalAuthorityInfo(customsShipmentLocalAuthorityInfo);
						break;
					}
				}
			}
		}
		if (MultiTenantUtility.isTenantAirport(awbDetails.getOrigin())) {
			customsShipmentInfo.setShipmentType(CustomsShipmentType.Type.EXPORT);

		} else {
			customsShipmentInfo.setShipmentType(CustomsShipmentType.Type.IMPORT);

		}

		// Submit to customs
		this.submitDataToCustoms.submitShipment(customsShipmentInfo);
	}

	public void shipmentBillingUpdateForNAWB(NeutralAWBMaster nawb) throws CustomException {
		Boolean isMailOcs = false;
		isMailOcs = neutralAWBDAO.checkMalOcsForNawb(nawb);

		if (!ObjectUtils.isEmpty(isMailOcs) && isMailOcs) {
			NawbChargeValues chargevalues = new NawbChargeValues();
			chargevalues = neutralAWBDAO.getNawbChargeDetails(nawb);

			if (!ObjectUtils.isEmpty(chargevalues)) {
				if (!Objects.isNull(chargevalues.getShipmentId())) {
					BillingShipment billingShipment = new BillingShipment();
					billingShipment.setShipmentNumber(nawb.getAwbNumber());
					billingShipment.setShipmentDate(nawb.getAwbDate());
					billingShipment.setProcessType(ChargeEvents.PROCESS_EXP.getEnum());
					billingShipment.setHandlingTerminal(nawb.getTerminal());
					billingShipment.setUserCode(nawb.getLoggedInUser());
					billingShipment.setEventType(ChargeEvents.EXP_ISSUE_NAWB);
					Charge.calculateCharge(billingShipment);
				}

				ChargeableEntity chargeableEntity = new ChargeableEntity();
				if (Objects.isNull(chargevalues.getShipmentId()) && !Objects.isNull(nawb.getNeutralAWBId())) {
					chargeableEntity.setReferenceId(nawb.getNeutralAWBId());
					chargeableEntity.setReferenceType(ReferenceType.NAWB_ID.getReferenceType());
//					chargeableEntity.setAdditionalReferenceId(new BigInteger(nawb.getShipmentNumber()));
					chargeableEntity.setAdditionalInfo(nawb.getShipmentNumber());
					chargeableEntity.setAdditionalReferenceType(ReferenceType.SHIPMENT.getReferenceType());
				} else {
					chargeableEntity.setReferenceId(chargevalues.getShipmentId());
					chargeableEntity.setReferenceType(ReferenceType.SHIPMENT.getReferenceType());
				}
				chargeableEntity.setProcessType(ChargeEvents.PROCESS_EXP.getEnum());
				chargeableEntity.setHandlingTerminal(nawb.getTerminal());
				chargeableEntity.setUserCode(nawb.getLoggedInUser());

				if (!Objects.isNull(chargevalues.getCarrierCode())) {
					chargeableEntity.setCarrierCode(chargevalues.getCarrierCode());
				} else if (!Objects.isNull(nawb.getFlightBookingList().get(0).getCarrierCode())) {
					chargeableEntity.setCarrierCode(nawb.getFlightBookingList().get(0).getCarrierCode());
				}
				chargeableEntity.setCustomerId(chargevalues.getCustomerId());

				chargeableEntity.setEventType(ChargeEvents.EXP_NAWB_FREIGHT);
				chargeableEntity.setQuantity(chargevalues.getFreightcharges());
				Charge.calculateCharge(chargeableEntity);

				chargeableEntity.setEventType(ChargeEvents.EXP_NAWB_VALUATION);
				chargeableEntity.setQuantity(chargevalues.getValuationCharges().add(chargevalues.getTax()));
				Charge.calculateCharge(chargeableEntity);

				chargeableEntity.setEventType(ChargeEvents.EXP_NAWB_DUEAGENT);
				chargeableEntity.setQuantity(chargevalues.getDueagentcharge());
				Charge.calculateCharge(chargeableEntity);

				chargeableEntity.setEventType(ChargeEvents.EXP_NAWB_DUECARRIER);
				chargeableEntity.setQuantity(chargevalues.getDueagentcarrier());
				Charge.calculateCharge(chargeableEntity);
			}
		}
	}

	public boolean isAnIRRCarrier(AWB awbDetails) throws CustomException {

		CustomsShipmentInfo customsShipmentInfo = new CustomsShipmentInfo();
		customsShipmentInfo.setFlightKey(awbDetails.getFlightKey());
		customsShipmentInfo
				.setFlightDate(awbDetails.getFlightDate() != null ? awbDetails.getFlightDate().toLocalDate() : null);
		return this.submitDataToCustoms.isAnIRRCarrier(customsShipmentInfo);
	}

}