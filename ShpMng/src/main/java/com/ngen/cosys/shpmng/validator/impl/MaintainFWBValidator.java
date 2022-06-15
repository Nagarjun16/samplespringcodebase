package com.ngen.cosys.shpmng.validator.impl;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.shipment.dao.MaintainFWBDAO;
import com.ngen.cosys.shipment.enums.OCIDataValues;
import com.ngen.cosys.shipment.model.AccountingInfo;
import com.ngen.cosys.shipment.model.CustomerInfo;
import com.ngen.cosys.shipment.model.FWB;
import com.ngen.cosys.shipment.model.FlightBooking;
import com.ngen.cosys.shipment.model.OtherCharges;
import com.ngen.cosys.shipment.model.OtherCustomsInfo;
import com.ngen.cosys.shipment.model.RateDescription;
import com.ngen.cosys.shipment.model.Routing;
import com.ngen.cosys.shipment.validator.ShpMngBusinessValidator;
import com.ngen.cosys.shipment.validators.MaintainFreightWayBillValidator;

@Component
public class MaintainFWBValidator implements ShpMngBusinessValidator {

	private static final String PATTERN = "^[a-zA-Z0-9]+$";
	private static final Pattern pattern = Pattern.compile(PATTERN);

	@Autowired
	private MaintainFWBDAO dao;

	@Autowired
	private Validator validator;

	public BaseBO validate(BaseBO baseModel) throws CustomException {
		FWB requestModel = (FWB) baseModel;

		// Validate house model
		Set<ConstraintViolation<FWB>> violations = this.validator.validate(requestModel,
				MaintainFreightWayBillValidator.class);
		for (final ConstraintViolation<?> violation : violations) {
			StringBuilder sbPath = new StringBuilder();
			sbPath.append(violation.getRootBeanClass().getName()).append(".").append(violation.getPropertyPath());
			requestModel.addError(violation.getMessage(), sbPath.toString(), ErrorType.ERROR);
			requestModel.setErrorFalg(true);
		}

		// Check whether the shipment is delivered or not
		if (MultiTenantUtility.isTenantCityOrAirport(requestModel.getOrigin())) {
			Boolean isDeliveryInitiated = this.dao.isShipmentDeliveryInitiated(requestModel);
			if (isDeliveryInitiated) {
				requestModel.addError("awb.delivery.initiated", "awbNumber", ErrorType.ERROR);
				requestModel.setErrorFalg(true);
			}
		}

		// Check for origin/destination/piece/weight matches for export acceptance
		// shipments
		if (!MultiTenantUtility.isTenantCityOrAirport(requestModel.getDestination())) {
			FWB acceptanceInfo = this.dao.getAcceptanceInfo(requestModel);

			Optional<FWB> oAcceptanceInfo = Optional.ofNullable(acceptanceInfo);
			if (oAcceptanceInfo.isPresent() && (!requestModel.getOrigin().equalsIgnoreCase(acceptanceInfo.getOrigin())
					|| !requestModel.getDestination().equalsIgnoreCase(acceptanceInfo.getDestination())
					|| requestModel.getPieces().compareTo(acceptanceInfo.getPieces()) != 0
					|| requestModel.getWeight().compareTo(acceptanceInfo.getWeight()) != 0)) {
				requestModel.addError("data.shipment.info.not.matching.with.acceptance", null, ErrorType.ERROR,
						new String[] { acceptanceInfo.getOrigin(), acceptanceInfo.getDestination(),
								acceptanceInfo.getPieces().toString(), acceptanceInfo.getWeight().toString() });
				requestModel.setErrorFalg(true);
			}
		}

		// At-least one SHC is mandatory
		this.validateSHC(requestModel);

		// Duplicate check for OSI
		this.checkForDuplicateOSI(requestModel);

		// Duplicate check for SSR
		this.checkForDuplicateSSR(requestModel);

		// Duplicate Route checking
		this.checkForRoutingInfoWithOnwardsDestination(requestModel);

		// Duplicate Booking
		this.checkFlightBookigDuplicate(requestModel);
		
		// Check Rate Description Info
		this.selectTypeOnRateDescOtherInfo(requestModel);

		// Check for NG/NC
		this.checkNatureOfGoods(requestModel);

		// DUPLICATE OTHER CHARGES
		this.checkDuplicateOtherCharges(requestModel);

		// Check valid country cocde or not
		this.checkValidCountry(requestModel);

		// check data for accounting info
		this.checkDataForAccountingInfo(requestModel);

		// Check valid cdc
		this.checkValidCDC(requestModel);

		// Validate agent info
		this.checkAgentInfo(requestModel);

		// check origin destination duplicate
		this.checkOriginDestinationAirport(requestModel);

		// Validate charge declaration
		this.checkValidChargeDeclaration(requestModel);

		// For CNE
		this.checkConsigneeDuplicateContatInfo(requestModel);

		// For Notify
		this.checkAlsoNotifyDuplicateContatInfo(requestModel);

		// Check for Duplicate Shipper Contact
		this.checkShipperDuplicateContatInfo(requestModel);
		requestModel.setShipperInfo(this.checkForShipperInputs(requestModel.getShipperInfo(), "SHP"));
		requestModel.setConsigneeInfo(this.checkForShipperInputs(requestModel.getConsigneeInfo(), "CNE"));
		requestModel.setAlsoNotify(this.checkForShipperInputs(requestModel.getAlsoNotify(), "NFY"));

		// Validate OCI line item for china customs
		Boolean isAirportBelongsToChina = this.dao.isAirportBelongsToChina(requestModel);
		if (isAirportBelongsToChina) {
			this.validateOCIForChinaCustoms(requestModel);
		}
		return requestModel;
	}

	private void checkAlsoNotifyDuplicateContatInfo(FWB request) {
		Set<String> distinct = new HashSet<>();
		for (int i = 0; i < request.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo().size(); i++) {
			if (!StringUtils.isEmpty(request.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo().get(i)
					.getContactIdentifier())) {

				// Validate the contact number as alphanumeric only
				if (!StringUtils.isEmpty(request.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo()
						.get(i).getContactDetail())) {
					Matcher matcher = pattern.matcher(request.getAlsoNotify().getCustomerAddressInfo()
							.getCustomerContactInfo().get(i).getContactDetail());
					if (!ObjectUtils.isEmpty(matcher) && !matcher.matches()) {
						request.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo().get(i)
								.addError("INVALIDCONTACT", "contactDetail", ErrorType.APP);
						request.setErrorFalg(true);
					}
				}

				if (distinct.contains(request.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo().get(i)
						.getContactIdentifier())) {
					request.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo().get(i)
							.addError("awb.duplicate.contact.found", "contactIdentifier", ErrorType.APP);
					request.setErrorFalg(true);

				} else {
					distinct.add(request.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo().get(i)
							.getContactIdentifier());
				}
			}
		}

	}

	private void checkShipperDuplicateContatInfo(FWB request) {
		Set<String> distinct = new HashSet<>();
		for (int i = 0; i < request.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo().size(); i++) {
			if (!StringUtils.isEmpty(request.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo().get(i)
					.getContactIdentifier())) {

				// Validate the contact number as alphanumeric only
				if (!StringUtils.isEmpty(request.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo()
						.get(i).getContactDetail())) {
					Matcher matcher = pattern.matcher(request.getShipperInfo().getCustomerAddressInfo()
							.getCustomerContactInfo().get(i).getContactDetail());
					if (!ObjectUtils.isEmpty(matcher) && !matcher.matches()) {
						request.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo().get(i)
								.addError("INVALIDCONTACT", "contactDetail", ErrorType.APP);
						request.setErrorFalg(true);
					}
				}

				if (distinct.contains(request.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo().get(i)
						.getContactIdentifier())) {
					request.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo().get(i)
							.addError("awb.duplicate.contact.found", "contactIdentifier", ErrorType.APP);
					request.setErrorFalg(true);

				} else {
					distinct.add(request.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo().get(i)
							.getContactIdentifier());
				}
			}
		}
	}

	private void checkConsigneeDuplicateContatInfo(FWB request) {
		Set<String> distinct = new HashSet<>();
		for (int i = 0; i < request.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo().size(); i++) {
			if (!StringUtils.isEmpty(request.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo().get(i)
					.getContactIdentifier())) {

				// Validate the contact number as alphanumeric only
				if (!StringUtils.isEmpty(request.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo()
						.get(i).getContactDetail())) {
					Matcher matcher = pattern.matcher(request.getConsigneeInfo().getCustomerAddressInfo()
							.getCustomerContactInfo().get(i).getContactDetail());
					if (!ObjectUtils.isEmpty(matcher) && !matcher.matches()) {
						request.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo().get(i)
								.addError("INVALIDCONTACT", "contactDetail", ErrorType.APP);
						request.setErrorFalg(true);
					}
				}

				if (distinct.contains(request.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo()
						.get(i).getContactIdentifier())) {
					request.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo().get(i)
							.addError("awb.duplicate.contact.found", "contactIdentifier", ErrorType.APP);
					request.setErrorFalg(true);

				} else {
					distinct.add(request.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo().get(i)
							.getContactIdentifier());
				}
			}
		}

	}

	private void checkAgentInfo(FWB fwbModel) {
		if (Optional.ofNullable(fwbModel.getAgentInfo()).isPresent()) {
			if (Optional.ofNullable(fwbModel.getAgentInfo().getAgentName()).isPresent()
					&& !StringUtils.isEmpty(fwbModel.getAgentInfo().getAgentName())) {
				if (!Optional.ofNullable(fwbModel.getAgentInfo().getAgentPlace()).isPresent()
						&& StringUtils.isEmpty(fwbModel.getAgentInfo().getAgentPlace())) {

					fwbModel.addError("agent.place.required", "agentInfo.agentPlace", ErrorType.ERROR);
					fwbModel.setErrorFalg(true);
					return;
				}

				if (!Optional.ofNullable(fwbModel.getAgentInfo().getIATACargoAgentNumericCode()).isPresent()
						&& StringUtils.isEmpty(fwbModel.getAgentInfo().getIATACargoAgentNumericCode())) {

					fwbModel.addError("agent.iata.numeric.code.required", "agentInfo.iatacargoAgentNumericCode",
							ErrorType.ERROR);
					fwbModel.setErrorFalg(true);
					return;
				}
			}

			if (Optional.ofNullable(fwbModel.getAgentInfo().getAgentPlace()).isPresent()
					&& !StringUtils.isEmpty(fwbModel.getAgentInfo().getAgentPlace())) {
				if (!Optional.ofNullable(fwbModel.getAgentInfo().getAgentName()).isPresent()
						&& StringUtils.isEmpty(fwbModel.getAgentInfo().getAgentName())) {

					fwbModel.addError("agent.name.required", "agentInfo.agentName", ErrorType.ERROR);
					fwbModel.setErrorFalg(true);
					return;
				}

				if (!Optional.ofNullable(fwbModel.getAgentInfo().getIATACargoAgentNumericCode()).isPresent()
						&& StringUtils.isEmpty(fwbModel.getAgentInfo().getIATACargoAgentNumericCode())) {

					fwbModel.addError("agent.iata.numeric.code.required", "agentInfo.iatacargoAgentNumericCode",
							ErrorType.ERROR);
					fwbModel.setErrorFalg(true);
					return;
				}
			}

			if (Optional.ofNullable(fwbModel.getAgentInfo().getIATACargoAgentNumericCode()).isPresent()
					&& !StringUtils.isEmpty(fwbModel.getAgentInfo().getIATACargoAgentNumericCode())) {
				if (!Optional.ofNullable(fwbModel.getAgentInfo().getAgentName()).isPresent()
						&& StringUtils.isEmpty(fwbModel.getAgentInfo().getAgentName())) {

					fwbModel.addError("agent.name.required", "agentInfo.agentName", ErrorType.ERROR);
					fwbModel.setErrorFalg(true);
					return;
				}

				if (!Optional.ofNullable(fwbModel.getAgentInfo().getAgentPlace()).isPresent()
						&& StringUtils.isEmpty(fwbModel.getAgentInfo().getAgentPlace())) {

					fwbModel.addError("agent.place.required", "agentInfo.agentPlace", ErrorType.ERROR);
					fwbModel.setErrorFalg(true);
					return;
				}
			}
		}

	}

	private void checkValidChargeDeclaration(FWB fwbModel) {

		if (Optional.ofNullable(fwbModel.getChargeDeclaration()).isPresent()) {
			if (!StringUtils.isEmpty(fwbModel.getChargeDeclaration().getCarriageValueDeclaration())
					&& ((!fwbModel.getChargeDeclaration().getCarriageValueDeclaration().equals("NVD")) && (!fwbModel
							.getChargeDeclaration().getCarriageValueDeclaration().matches("-?\\d+(\\.\\d+)?")))) {
				fwbModel.addError("billing.only.numeric.value", "chargeDeclaration.carriageValueDeclaration", ErrorType.ERROR);
				fwbModel.setErrorFalg(true);
				return;
			}

			if (!StringUtils.isEmpty(fwbModel.getChargeDeclaration().getCarriageValueDeclaration())
					&& fwbModel.getChargeDeclaration().getCarriageValueDeclaration().contains(".")) {
				String array[] = fwbModel.getChargeDeclaration().getCarriageValueDeclaration().replace(".", "/").split("/");
				if (array.length > 0 && array[1].trim().length() > 3) {
					fwbModel.addError("billing.decimal.value", "chargeDeclaration.carriageValueDeclaration",
							ErrorType.ERROR);
					fwbModel.setErrorFalg(true);
				}

			}

			if (!StringUtils.isEmpty(fwbModel.getChargeDeclaration().getCustomsValueDeclaration())
					&& ((!fwbModel.getChargeDeclaration().getCustomsValueDeclaration().equals("NCV")) && (!fwbModel
							.getChargeDeclaration().getCustomsValueDeclaration().matches("-?\\d+(\\.\\d+)?")))) {
				fwbModel.addError("billing.only.numeric.value", "chargeDeclaration.customsValueDeclaration", ErrorType.ERROR);
				fwbModel.setErrorFalg(true);
				return;
			}

			if (!StringUtils.isEmpty(fwbModel.getChargeDeclaration().getCustomsValueDeclaration())
					&& fwbModel.getChargeDeclaration().getCustomsValueDeclaration().contains(".")) {
				String array[] = fwbModel.getChargeDeclaration().getCustomsValueDeclaration().replace(".", "/").split("/");
				if (array.length > 0 && array[1].trim().length() > 3) {
					fwbModel.addError("billing.decimal.value", "chargeDeclaration.customsValueDeclaration",
							ErrorType.ERROR);
					fwbModel.setErrorFalg(true);
				}

			}

			if (!StringUtils.isEmpty(fwbModel.getChargeDeclaration().getInsuranceValueDeclaration())
					&& ((!fwbModel.getChargeDeclaration().getInsuranceValueDeclaration().equals("XXX")) && (!fwbModel
							.getChargeDeclaration().getInsuranceValueDeclaration().matches("-?\\d+(\\.\\d+)?")))) {
				fwbModel.addError("billing.only.numeric.value", "chargeDeclaration.insuranceValueDeclaration", ErrorType.ERROR);
				fwbModel.setErrorFalg(true);
				return;
			}

			if (!StringUtils.isEmpty(fwbModel.getChargeDeclaration().getInsuranceValueDeclaration())
					&& fwbModel.getChargeDeclaration().getInsuranceValueDeclaration().contains(".")) {
				String array[] = fwbModel.getChargeDeclaration().getInsuranceValueDeclaration().replace(".", "/").split("/");
				if (array.length > 0 && array[1].trim().length() > 3) {
					fwbModel.addError("billing.decimal.value", "chargeDeclaration.insuranceValueDeclaration",
							ErrorType.ERROR);
					fwbModel.setErrorFalg(true);
				}

			}

			if (!StringUtils.isEmpty(fwbModel.getChargeDeclaration().getPrepaIdCollectChargeDeclaration())
					&& (!(fwbModel.getChargeDeclaration().getPrepaIdCollectChargeDeclaration().equals("PP")))
					&& (!fwbModel.getChargeDeclaration().getPrepaIdCollectChargeDeclaration().equals("CC"))) {
				fwbModel.addError("billing.inavlid.charge.dec", "chargeDeclaration.prepaIdCollectChargeDeclaration",
						ErrorType.ERROR);
				fwbModel.setErrorFalg(true);
				return;
			}

		}
	}

	private void checkOriginDestinationAirport(FWB fwbModel) {
		if (Optional.ofNullable(fwbModel).isPresent()) {
			String origin = fwbModel.getOrigin();
			String dest = fwbModel.getDestination();
			if (origin.equals(dest)) {
				fwbModel.addError("awb.org.des.same", "destination", ErrorType.NOTIFICATION);
				fwbModel.setErrorFalg(true);
				return;
			}
		}
	}

	private void checkDataForAccountingInfo(FWB fwbModel) {
		if (Optional.ofNullable(fwbModel.getAccountingInfo()).isPresent()) {
			for (int i = 0; i < fwbModel.getAccountingInfo().size(); i++) {
				if (!StringUtils.isEmpty(fwbModel.getAccountingInfo().get(i).getInformationIdentifier())
						&& StringUtils.isEmpty(fwbModel.getAccountingInfo().get(i).getAccountingInformation())) {
					fwbModel.getAccountingInfo().get(i).addError("AccountingInfo.AccountInfo.not.found",
							"accountingInformation", ErrorType.APP);
					fwbModel.setErrorFalg(true);
					return;
				}
				if (!StringUtils.isEmpty(fwbModel.getAccountingInfo().get(i).getAccountingInformation())
						&& StringUtils.isEmpty(fwbModel.getAccountingInfo().get(i).getInformationIdentifier())) {
					fwbModel.getAccountingInfo().get(i).addError("AccountingInfo.InfoIdentifier.not.found",
							"informationIdentifier", ErrorType.APP);
					fwbModel.setErrorFalg(true);
					return;
				}
			}
		}
	}

	private void checkValidCDC(FWB fwbModel) {
		if (Optional.ofNullable(fwbModel.getChargeDestCurrency()).isPresent()) {
			if (!StringUtils.isEmpty(fwbModel.getChargeDestCurrency().getDestinationCountryCode())
					|| !StringUtils.isEmpty(fwbModel.getChargeDestCurrency().getDestinationCurrencyCode())
					|| !ObjectUtils.isEmpty(fwbModel.getChargeDestCurrency().getCurrencyConversionExchangeRate())
					|| !ObjectUtils.isEmpty(fwbModel.getChargeDestCurrency().getChargesAtDestinationChargeAmount())
					|| !ObjectUtils.isEmpty(fwbModel.getChargeDestCurrency().getDestinationCurrencyChargeAmount())
					|| !ObjectUtils.isEmpty(fwbModel.getChargeDestCurrency().getTotalCollectChargesChargeAmount())) {
				if (StringUtils.isEmpty(fwbModel.getChargeDestCurrency().getDestinationCurrencyCode())) {

					fwbModel.getChargeDestCurrency().addError("CDC.currencyCode.not.found", "destinationCurrencyCode",
							ErrorType.APP);
					fwbModel.setErrorFalg(true);
					return;
				}
				if (ObjectUtils.isEmpty(fwbModel.getChargeDestCurrency().getCurrencyConversionExchangeRate())) {

					fwbModel.getChargeDestCurrency().addError("CDC.rate.charge.not.found",
							"currencyConversionExchangeRate", ErrorType.APP);
					fwbModel.setErrorFalg(true);
					return;
				}
				if (ObjectUtils.isEmpty(fwbModel.getChargeDestCurrency().getChargesAtDestinationChargeAmount())) {

					fwbModel.getChargeDestCurrency().addError("CDC.charge.amount.not.found",
							"chargesAtDestinationChargeAmount", ErrorType.APP);
					fwbModel.setErrorFalg(true);
					return;
				}
				if (ObjectUtils.isEmpty(fwbModel.getChargeDestCurrency().getDestinationCurrencyChargeAmount())) {

					fwbModel.getChargeDestCurrency().addError("CDC.destination.charge.amount.not.found",
							"destinationCurrencyChargeAmount", ErrorType.APP);
					fwbModel.setErrorFalg(true);
					return;
				}
				if (ObjectUtils.isEmpty(fwbModel.getChargeDestCurrency().getTotalCollectChargesChargeAmount())) {

					fwbModel.getChargeDestCurrency().addError("CDC.total.amount.not.found",
							"totalCollectChargesChargeAmount", ErrorType.APP);
					fwbModel.setErrorFalg(true);
					return;
				}
			}
		}
	}

	private void checkValidCountry(FWB fwbModel) throws CustomException {
		if (Optional.ofNullable(fwbModel.getOtherCustomsInfo()).isPresent()) {
			for (int i = 0; i < fwbModel.getOtherCustomsInfo().size(); i++) {
				if (!StringUtils.isEmpty(fwbModel.getOtherCustomsInfo().get(i).getCountryCode())) {
					Integer data = dao.isValidCountry(fwbModel.getOtherCustomsInfo().get(i).getCountryCode());
					if (data == 0) {
						fwbModel.getOtherCustomsInfo().get(i).addError("OCI.countryCode.not.valid", "countryCode",
								ErrorType.APP);
						fwbModel.setErrorFalg(true);
						return;
					}
				}
			}
		}
	}

	private void checkDuplicateOtherCharges(FWB fwbModel) {
		Set<String> distinctOtherCharges = new HashSet<>();
		for (OtherCharges othCharges : fwbModel.getOtherCharges()) {
			if (!ObjectUtils.isEmpty(othCharges)) {
				String s = new StringBuilder().append(othCharges.getOtherChargeIndicator())
						.append(othCharges.getOtherChargeCode()).append(othCharges.getEntitlementCode()).toString();
				if (distinctOtherCharges.contains(s)) {
					othCharges.addError("duplicate.record.found", "otherChargeIndicator", ErrorType.ERROR);
					fwbModel.setErrorFalg(true);
				} else {
					distinctOtherCharges.add(s);
				}
			}
		}

		distinctOtherCharges = new HashSet<>();
		for (OtherCharges othCharges : fwbModel.getOtherChargesCarrier()) {
			if (!ObjectUtils.isEmpty(othCharges)) {
				String s = new StringBuilder().append(othCharges.getOtherChargeIndicator())
						.append(othCharges.getOtherChargeCode()).append(othCharges.getEntitlementCode()).toString();
				if (distinctOtherCharges.contains(s)) {
					othCharges.addError("duplicate.record.found", "otherChargeIndicator", ErrorType.ERROR);
					fwbModel.setErrorFalg(true);
				} else {
					distinctOtherCharges.add(s);
				}
			}
		}
	}

	private void selectTypeOnRateDescOtherInfo(FWB fwbModel) {
		if (!CollectionUtils.isEmpty(fwbModel.getRateDescription())) {
			for (RateDescription rate : fwbModel.getRateDescription()) {
				for (int i = 0; i < rate.getRateDescriptionOtherInfo().size(); i++) {
					if (!StringUtils.isEmpty(rate.getRateDescriptionOtherInfo().get(i).getRateLine())) {
						if (rate.getRateDescriptionOtherInfo().get(i).getRateLine().equals("ND")) {
							if (rate.getRateDescriptionOtherInfo().get(i).isNoDimensionAvailable() == Boolean.FALSE) {
								if (StringUtils
										.isEmpty(rate.getRateDescriptionOtherInfo().get(i).getMeasurementUnitCode())) {
									rate.getRateDescriptionOtherInfo().get(i).addError(
											"rtdoi.measurementUCode.not.found", "measurementUnitCode", ErrorType.APP);
									fwbModel.setErrorFalg(true);
									return;
								}
								if (StringUtils
										.isEmpty(rate.getRateDescriptionOtherInfo().get(i).getDimensionLength())) {
									rate.getRateDescriptionOtherInfo().get(i).addError("rtdoi.dimension.not.found",
											"dimensionLength", ErrorType.APP);
									fwbModel.setErrorFalg(true);
									return;
								}
								if (StringUtils
										.isEmpty(rate.getRateDescriptionOtherInfo().get(i).getDimensionHeight())) {
									rate.getRateDescriptionOtherInfo().get(i).addError("rtdoi.Height.not.found",
											"dimensionHeight", ErrorType.APP);
									fwbModel.setErrorFalg(true);
									return;
								}
								if (StringUtils
										.isEmpty(rate.getRateDescriptionOtherInfo().get(i).getDimensionWIdth())) {
									rate.getRateDescriptionOtherInfo().get(i).addError("rtdoi.Width.not.found",
											"dimensionWIdth", ErrorType.APP);
									fwbModel.setErrorFalg(true);
									return;
								}
								if (StringUtils
										.isEmpty(rate.getRateDescriptionOtherInfo().get(i).getNumberOfPieces())) {
									rate.getRateDescriptionOtherInfo().get(i).addError("rtdoi.Pieces.not.found",
											"numberOfPieces", ErrorType.APP);
									fwbModel.setErrorFalg(true);
									return;
								}
							}
						}
						if (rate.getRateDescriptionOtherInfo().get(i).getRateLine().equals("NG")) {
							if (StringUtils
									.isEmpty(rate.getRateDescriptionOtherInfo().get(i).getNatureOfGoodsDescription())) {
								rate.getRateDescriptionOtherInfo().get(i).addError("NGGoods.not.found",
										"natureOfGoodsDescription", ErrorType.APP);
								fwbModel.setErrorFalg(true);
								return;
							}
						}
						if (rate.getRateDescriptionOtherInfo().get(i).getRateLine().equals("NH")) {
							if (StringUtils
									.isEmpty(rate.getRateDescriptionOtherInfo().get(i).getHarmonisedCommodityCode())) {
								rate.getRateDescriptionOtherInfo().get(i).addError("harmonisedCommodityCode.not.found",
										"harmonisedCommodityCode", ErrorType.APP);
								fwbModel.setErrorFalg(true);
								return;
							}
						}
						if (rate.getRateDescriptionOtherInfo().get(i).getRateLine().equals("NO")) {
							if (StringUtils.isEmpty(rate.getRateDescriptionOtherInfo().get(i).getCountryCode())) {
								rate.getRateDescriptionOtherInfo().get(i).addError("countryCode.not.found",
										"countryCode", ErrorType.NOTIFICATION);
								fwbModel.setErrorFalg(true);
								return;
							}
						}
						if (rate.getRateDescriptionOtherInfo().get(i).getRateLine().equals("NS")) {
							if (StringUtils.isEmpty(rate.getRateDescriptionOtherInfo().get(i).getSlacCount())) {
								rate.getRateDescriptionOtherInfo().get(i).addError("slacCount.not.found", "slacCount",
										ErrorType.APP);
								fwbModel.setErrorFalg(true);
								return;
							}
						}
						if (rate.getRateDescriptionOtherInfo().get(i).getRateLine().equals("NU")) {
							if (StringUtils.isEmpty(rate.getRateDescriptionOtherInfo().get(i).getUldNumber())) {
								rate.getRateDescriptionOtherInfo().get(i).addError("uldNumber.not.found", "uldNumber",
										ErrorType.APP);
								fwbModel.setErrorFalg(true);
								return;
							}
						}
						if (rate.getRateDescriptionOtherInfo().get(i).getRateLine().equals("NV")) {
							if (StringUtils.isEmpty(rate.getRateDescriptionOtherInfo().get(i).getVolumeUnitCode())) {
								rate.getRateDescriptionOtherInfo().get(i).addError("volumeUnitCode.not.found",
										"volumeUnitCode", ErrorType.APP);
								fwbModel.setErrorFalg(true);
								return;
							}
							if (StringUtils.isEmpty(rate.getRateDescriptionOtherInfo().get(i).getVolumeAmount())) {
								rate.getRateDescriptionOtherInfo().get(i).addError("volumeAmount.not.found",
										"volumeAmount", ErrorType.APP);
								fwbModel.setErrorFalg(true);
								return;
							}
						}
					}
				}
			}
		}
	}

	private void checkFlightBookigDuplicate(FWB fwbModel) {
		Set<String> distinctFlight = new HashSet<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMYYYY");
		if (!CollectionUtils.isEmpty(fwbModel.getFlightBooking())) {
			for (FlightBooking fb : fwbModel.getFlightBooking()) {
				if (!ObjectUtils.isEmpty(fb) && !StringUtils.isEmpty(fb.getCarrierCode())
						&& !StringUtils.isEmpty(fb.getFlightNumber()) && !StringUtils.isEmpty(fb.getFlightDate())) {
					String s = new StringBuilder().append(fb.getCarrierCode()).append(fb.getFlightNumber())
							.append(fb.getFlightNumber()).append(fb.getFlightDate().format(formatter)).toString();

					if (distinctFlight.contains(s)) {
						fb.addError("duplicate.flightBooking.found", "carrierCode", ErrorType.NOTIFICATION);
						fwbModel.setErrorFalg(true);
					} else {
						distinctFlight.add(s);
					}
				} else if (!StringUtils.isEmpty(fb.getCarrierCode()) && StringUtils.isEmpty(fb.getFlightDate())
						&& StringUtils.isEmpty(fb.getFlightNumber())) {
					fb.addError("g.enter.mandatory.feilds", "flightNumber", ErrorType.NOTIFICATION);
				}

			}
		}
	}

	private CustomerInfo checkForShipperInputs(CustomerInfo customerInfo, String customerType) {
		if ("CNE".equals(customerType) || "SHP".equals(customerType)) {
			if (Optional.ofNullable(customerInfo.getCustomerName()).isPresent()
					&& customerInfo.getCustomerName().length() != 0) {
				if (!(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getStreetAddress1()).isPresent()
						&& customerInfo.getCustomerAddressInfo().getStreetAddress1().length() != 0)
						|| !(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getCustomerPlace()).isPresent()
								&& customerInfo.getCustomerAddressInfo().getCustomerPlace().length() != 0)
						|| !(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getCountryCode()).isPresent()
								&& customerInfo.getCustomerAddressInfo().getCountryCode().length() != 0)) {

					if ("CNE".equals(customerType)) {
						customerInfo.addError("data.ShpCons.valid", "Invalid  consignee", ErrorType.APP);
					} else {
						customerInfo.addError("data.ShpCons.valid", "Invalid Shipper ", ErrorType.APP);
					}
				}
			} else if (Optional.ofNullable(customerInfo.getCustomerAddressInfo().getStreetAddress1()).isPresent()
					&& customerInfo.getCustomerAddressInfo().getStreetAddress1().length() != 0) {
				if (!(Optional.ofNullable(customerInfo.getCustomerName()).isPresent()
						&& customerInfo.getCustomerName().length() != 0)
						|| !(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getCustomerPlace()).isPresent()
								&& customerInfo.getCustomerAddressInfo().getCustomerPlace().length() != 0)
						|| !(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getCountryCode()).isPresent()
								&& customerInfo.getCustomerAddressInfo().getCountryCode().length() != 0)) {
					if ("CNE".equals(customerType)) {
						customerInfo.addError("data.ShpCons.valid", "Invalid  consignee", ErrorType.APP);
					} else {
						customerInfo.addError("data.ShpCons.valid", "Invalid Shipper ", ErrorType.APP);
					}
				}
			}

			else if ((Optional.ofNullable(customerInfo.getCustomerAddressInfo().getCustomerPlace()).isPresent()
					&& customerInfo.getCustomerAddressInfo().getCustomerPlace().length() != 0)
					&& (!(Optional.ofNullable(customerInfo.getCustomerName()).isPresent()
							&& customerInfo.getCustomerName().length() != 0)
							|| !(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getStreetAddress1())
									.isPresent()
									&& customerInfo.getCustomerAddressInfo().getStreetAddress1().length() != 0)
							|| !(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getCountryCode()).isPresent()
									&& customerInfo.getCustomerAddressInfo().getCountryCode().length() != 0))) {
				if ("CNE".equals(customerType)) {
					customerInfo.addError("data.ShpCons.valid", "Invalid  consignee", ErrorType.APP);
				} else {
					customerInfo.addError("data.ShpCons.valid", "Invalid Shipper ", ErrorType.APP);
				}
			}

			else if (Optional.ofNullable(customerInfo.getCustomerAddressInfo().getCountryCode()).isPresent()
					&& customerInfo.getCustomerAddressInfo().getCountryCode().length() != 0) {
				if (!(Optional.ofNullable(customerInfo.getCustomerName()).isPresent()
						&& customerInfo.getCustomerName().length() != 0)
						|| !(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getStreetAddress1()).isPresent()
								&& customerInfo.getCustomerAddressInfo().getStreetAddress1().length() != 0)
						|| !(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getCustomerPlace()).isPresent()
								&& customerInfo.getCustomerAddressInfo().getCustomerPlace().length() != 0)) {
					if ("CNE".equals(customerType)) {
						customerInfo.addError("data.ShpCons.valid", "Invalid  consignee", ErrorType.APP);
					} else {
						customerInfo.addError("data.ShpCons.valid", "Invalid Shipper ", ErrorType.APP);
					}
				}
			}

		} else if ("NFY".equals(customerType)) {// Level check
			if (Optional.ofNullable(customerInfo.getCustomerName()).isPresent()
					&& customerInfo.getCustomerName().length() != 0) {
				if (!(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getStreetAddress1()).isPresent()
						&& customerInfo.getCustomerAddressInfo().getStreetAddress1().length() != 0)
						|| !(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getCustomerPlace()).isPresent()
								&& customerInfo.getCustomerAddressInfo().getCustomerPlace().length() != 0)
						|| !(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getCountryCode()).isPresent()
								&& customerInfo.getCustomerAddressInfo().getCountryCode().length() != 0)) {
					customerInfo.addError("data.alsoNfy.valid", "Invalid AlsoNotify", ErrorType.APP);
				}
			} else if (Optional.ofNullable(customerInfo.getCustomerAddressInfo().getStreetAddress1()).isPresent()
					&& customerInfo.getCustomerAddressInfo().getStreetAddress1().length() != 0) {
				if (!(Optional.ofNullable(customerInfo.getCustomerName()).isPresent()
						&& customerInfo.getCustomerName().length() != 0)
						|| !(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getCustomerPlace()).isPresent()
								&& customerInfo.getCustomerAddressInfo().getCustomerPlace().length() != 0)
						|| !(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getCountryCode()).isPresent()
								&& customerInfo.getCustomerAddressInfo().getCountryCode().length() != 0)) {
					customerInfo.addError("data.alsoNfy.valid", "Invalid AlsoNotify", ErrorType.APP);
				}
			} else if ((Optional.ofNullable(customerInfo.getCustomerAddressInfo().getCustomerPlace()).isPresent()
					&& customerInfo.getCustomerAddressInfo().getCustomerPlace().length() != 0)
					&& (!(Optional.ofNullable(customerInfo.getCustomerName()).isPresent()
							&& customerInfo.getCustomerName().length() != 0)
							|| !(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getStreetAddress1())
									.isPresent()
									&& customerInfo.getCustomerAddressInfo().getStreetAddress1().length() != 0)
							|| !(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getCountryCode()).isPresent()
									&& customerInfo.getCustomerAddressInfo().getCountryCode().length() != 0))) {
				customerInfo.addError("data.alsoNfy.valid", "Invalid AlsoNotify", ErrorType.APP);
			} else if (Optional.ofNullable(customerInfo.getCustomerAddressInfo().getCountryCode()).isPresent()
					&& customerInfo.getCustomerAddressInfo().getCountryCode().length() != 0) {
				if (!(Optional.ofNullable(customerInfo.getCustomerName()).isPresent()
						&& customerInfo.getCustomerName().length() != 0)
						|| !(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getStreetAddress1()).isPresent()
								&& customerInfo.getCustomerAddressInfo().getStreetAddress1().length() != 0)
						|| !(Optional.ofNullable(customerInfo.getCustomerAddressInfo().getCustomerPlace()).isPresent()
								&& customerInfo.getCustomerAddressInfo().getCustomerPlace().length() != 0)) {
					customerInfo.addError("data.alsoNfy.valid", "Invalid AlsoNotify", ErrorType.APP);

				}
			}
		}
		return customerInfo;
	}

	/**
	 * Method to check one SHC
	 * 
	 * @param shc
	 */
	private void validateSHC(FWB request) {

		// Check for Duplicate SHC
		Set<String> distinct = new HashSet<>();
		for (int i = 0; i < request.getShcode().size(); i++) {
			if (!StringUtils.isEmpty(request.getShcode().get(i).getSpecialHandlingCode())) {
				if (distinct.contains(request.getShcode().get(i).getSpecialHandlingCode())) {
					request.getShcode().get(i).addError("duplicate.shc.data", "specialHandlingCode", ErrorType.APP);
					request.setErrorFalg(true);

				} else {
					distinct.add(request.getShcode().get(i).getSpecialHandlingCode());
				}
			}
		}
	}

	/**
	 * Method to check duplicate OSI line
	 * 
	 * @param ssrOsiInfo
	 */
	private void checkForDuplicateOSI(FWB fwbModel) {
		Set<String> osiLineSet = new HashSet<>();
		for (int i = 0; i < fwbModel.getOsiInfo().size(); i++) {

			if (!StringUtils.isEmpty(fwbModel.getOsiInfo().get(i).getServiceRequestcontent())) {
				if (osiLineSet.contains(fwbModel.getOsiInfo().get(i).getServiceRequestcontent())) {
					fwbModel.getOsiInfo().get(i).addError("duplicate.osi.found", "serviceRequestcontent",
							ErrorType.APP);
					fwbModel.setErrorFalg(true);
				} else {
					osiLineSet.add(fwbModel.getOsiInfo().get(i).getServiceRequestcontent());
				}
			}
		}
	}

	/**
	 * Method to check duplicate SSR line
	 * 
	 * @param ssrOsiInfo
	 */
	private void checkForDuplicateSSR(FWB fwbModel) {
		Set<String> ssrLineSet = new HashSet<>();
		for (int i = 0; i < fwbModel.getSsrInfo().size(); i++) {

			if (!StringUtils.isEmpty(fwbModel.getSsrInfo().get(i).getServiceRequestcontent())) {
				if (ssrLineSet.contains(fwbModel.getSsrInfo().get(i).getServiceRequestcontent())) {
					fwbModel.getSsrInfo().get(i).addError("duplicate.ssr.found", "serviceRequestcontent",
							ErrorType.APP);
					fwbModel.setErrorFalg(true);
				} else {
					ssrLineSet.add(fwbModel.getSsrInfo().get(i).getServiceRequestcontent());
				}
			}
		}
	}

	private void checkForRoutingInfoWithOnwardsDestination(FWB fwbModel) throws CustomException {
		// checking routing first carrier code
		for (int i = 0; i == fwbModel.getRouting().size() - 3; i++) {
			// Check for 1st routing carrier code and destination
			if (i == 0 && !ObjectUtils.isEmpty(fwbModel.getRouting().get(i))) {
				if (StringUtils.isEmpty(fwbModel.getRouting().get(i).getAirportCode())) {
					fwbModel.getRouting().get(i).addError("Routing.first.destination.not.found", "airportCode",
							ErrorType.APP);
					fwbModel.setErrorFalg(true);
					return;
				}

				if (StringUtils.isEmpty(fwbModel.getRouting().get(i).getCarrierCode())) {
					fwbModel.getRouting().get(i).addError("Routing.first.carrier.not.found", "carrierCode",
							ErrorType.APP);
					fwbModel.setErrorFalg(true);
					return;
				}
			}
		}

		Set<String> distinctFromPoint = new HashSet<>();
		if (!CollectionUtils.isEmpty(fwbModel.getRouting())) {
			for (Routing routing : fwbModel.getRouting()) {
				if (!ObjectUtils.isEmpty(routing) && !StringUtils.isEmpty(routing.getAirportCode())) {
					if (distinctFromPoint.contains(routing.getAirportCode())) {
						routing.addError("duplicate.airport.found", "airportCode", ErrorType.APP);
						fwbModel.setErrorFalg(true);
					} else {
						distinctFromPoint.add(routing.getAirportCode());
					}
				}
			}
		}

	}

	/**
	 * @param awbNumber
	 * @return boolean
	 * @throws CustomException
	 */
	public boolean validateAwbNumber(String shipmentNumber) throws CustomException {
		String[] array = shipmentNumber.split("-");
		String awbNumber = "";
		if (array != null && array.length == 2) {
			awbNumber = array[array.length - 1];
		} else {
			if (shipmentNumber.length() < 11) {
				throw new CustomException("BOOKING20", "", ErrorType.ERROR);
			}
			awbNumber = shipmentNumber.substring(3);
		}
		boolean isNumeric = awbNumber.chars().allMatch(Character::isDigit);
		if (!isNumeric) {
			throw new CustomException("BOOKING21", "", ErrorType.ERROR);
		}
		String partialAwbNumber = awbNumber.substring(0, awbNumber.length() - 1);
		int partialAwbNumberInteger = Integer.parseInt(partialAwbNumber);
		return awbNumber
				.equals(Integer.toString(partialAwbNumberInteger) + String.valueOf(partialAwbNumberInteger % 7));
	}

	/**
	 * Method to validate OCI information for airports belonging to
	 * 
	 * @param fwbRequestModel
	 */
	private void validateOCIForChinaCustoms(FWB fwbRequestModel) {
		if (CollectionUtils.isEmpty(fwbRequestModel.getOtherCustomsInfo())) {
			fwbRequestModel.addError("data.oci.china.mandatory", null, ErrorType.ERROR);
		} else {
			boolean shpTFound = false;
			boolean cneTFound = false;
			boolean cneKCFound = false;
			boolean cneUFound = false;
			for (OtherCustomsInfo t : fwbRequestModel.getOtherCustomsInfo()) {
				// Check each line item
				String lineItem = t.getInformationIdentifier() + "/" + t.getCsrciIdentifier();
				switch (lineItem) {
				case OCIDataValues.Value.SHP_T:
					shpTFound = true;
					break;
				case OCIDataValues.Value.CNE_T:
					cneTFound = true;
					break;
				case OCIDataValues.Value.CNE_U:
					cneUFound = true;
					break;
				case OCIDataValues.Value.CNE_KC:
					cneKCFound = true;
					break;
				default:
					break;
				}
			}

			if (shpTFound && cneTFound && cneKCFound && cneUFound) {
				// Do nothing
			} else {
				fwbRequestModel.addError("data.oci.china.requirement", null, ErrorType.ERROR);
			}
		}
	}

	/*
	 * Method to check Nature of Goods present in RTD under NG/NC
	 */
	private void checkNatureOfGoods(FWB fwbRequestModel) {
		if (!CollectionUtils.isEmpty(fwbRequestModel.getRateDescription())) {
			for (RateDescription rate : fwbRequestModel.getRateDescription()) {
				for (int i = 0; i < rate.getRateDescriptionOtherInfo().size(); i++) {
					if ("NG".equalsIgnoreCase(rate.getRateDescriptionOtherInfo().get(i).getRateLine())
							|| "NC".equalsIgnoreCase(rate.getRateDescriptionOtherInfo().get(i).getRateLine())
									&& (fwbRequestModel.getNatureOfgoods() == null || rate.getRateDescriptionOtherInfo()
											.get(i).getNatureOfGoodsDescription() == null)) {
						if (rate.getRateDescriptionOtherInfo().get(i).getNatureOfGoodsDescription() == null) {
							rate.getRateDescriptionOtherInfo().get(i).addError("Required", "natureOfGoodsDescription",
									ErrorType.ERROR);
							fwbRequestModel.setErrorFalg(true);
							return;
						}
						if (fwbRequestModel.getNatureOfgoods() == null) {
							fwbRequestModel.addError("g.required", "natureOfgoods", ErrorType.ERROR);
							fwbRequestModel.setErrorFalg(true);
							return;
						}
					}
				}
			}
		}
	}

}