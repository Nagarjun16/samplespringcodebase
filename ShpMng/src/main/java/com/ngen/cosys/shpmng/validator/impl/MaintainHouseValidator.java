package com.ngen.cosys.shpmng.validator.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.enums.OCIDataValues;
import com.ngen.cosys.shipment.house.dao.MaintainHouseDAO;
import com.ngen.cosys.shipment.house.model.HouseCustomerContactsModel;
import com.ngen.cosys.shipment.house.model.HouseCustomerModel;
import com.ngen.cosys.shipment.house.model.HouseDescriptionOfGoodsModel;
import com.ngen.cosys.shipment.house.model.HouseHarmonisedTariffScheduleModel;
import com.ngen.cosys.shipment.house.model.HouseModel;
import com.ngen.cosys.shipment.house.model.HouseOtherChargeDeclarationModel;
import com.ngen.cosys.shipment.house.model.HouseOtherCustomsInformationModel;
import com.ngen.cosys.shipment.house.model.HouseSpecialHandlingCodeModel;
import com.ngen.cosys.shipment.house.validator.HouseWayBillConsigneeValidationGroup;
import com.ngen.cosys.shipment.house.validator.HouseWayBillShipperValidationGroup;
import com.ngen.cosys.shipment.house.validator.HouseWayBillValidationGroup;
import com.ngen.cosys.shipment.validator.ShpMngBusinessValidator;

@Component
public class MaintainHouseValidator implements ShpMngBusinessValidator {

	@Autowired
	private MaintainHouseDAO maintainHouseDAO;

	@Autowired
	private Validator validator;

	@Override
	public HouseModel validate(BaseBO baseModel) throws CustomException {
		// Validation for House Specific continues
		HouseModel houseRequestModel = (HouseModel) baseModel;

		// Validate house model
		Set<ConstraintViolation<HouseModel>> violations = this.validator.validate(houseRequestModel,
				HouseWayBillValidationGroup.class);
		for (final ConstraintViolation<?> violation : violations) {
			StringBuilder sbPath = new StringBuilder();
			sbPath.append(violation.getRootBeanClass().getName()).append(".").append(violation.getPropertyPath());
			houseRequestModel.addError(violation.getMessage(), sbPath.toString(), ErrorType.ERROR);
		}

		// Validate for duplicate
		if (maintainHouseDAO.checkDuplicateHAWB(houseRequestModel)) {
			houseRequestModel.addError("duplicate.house.found", "hawbNumber", ErrorType.APP);
		}

		// Validate OCI
		this.validateOCI(houseRequestModel);

		// Validate OCI line item for china customs
		Boolean isAirportBelongsToChina = this.maintainHouseDAO.isAirportBelongsToChina(houseRequestModel);
		if (isAirportBelongsToChina) {
			this.validateOCIForChinaCustoms(houseRequestModel);
		}

		// Validation for SHC
		this.validateSHC(houseRequestModel);

		//

		if (Optional.ofNullable(houseRequestModel.getShipper().getName()).isPresent()
				|| Optional.ofNullable(houseRequestModel.getShipper().getAddress().getCountry()).isPresent()
				|| Optional.ofNullable(houseRequestModel.getShipper().getAddress().getStreetAddress()).isPresent()
				||Optional.ofNullable(houseRequestModel.getShipper().getAddress().getPlace()).isPresent()
				) {
			this.validateShipperDetails(houseRequestModel.getShipper());
			// Validation for Shipper
			this.validateShipperContactDetails(houseRequestModel);
			if (!CollectionUtils.isEmpty(houseRequestModel.getShipper().getMessageList())) {
				houseRequestModel.setMessageList(houseRequestModel.getShipper().getMessageList());
			}
		}

		//

		if (Optional.ofNullable(houseRequestModel.getConsignee().getName()).isPresent()
				|| Optional.ofNullable(houseRequestModel.getConsignee().getAddress().getCountry()).isPresent()
				|| Optional.ofNullable(houseRequestModel.getConsignee().getAddress().getStreetAddress()).isPresent()
				||Optional.ofNullable(houseRequestModel.getConsignee().getAddress().getPlace()).isPresent()) {
			this.validateConsigneeDetails(houseRequestModel.getConsignee());

			// Validation for Consignee
			this.validateConsigneeContactDetails(houseRequestModel);
			if (!CollectionUtils.isEmpty(houseRequestModel.getConsignee().getMessageList())) {
				houseRequestModel.setMessageList(houseRequestModel.getConsignee().getMessageList());
			}
		}

		// Validation for Charge Declaration
		this.validateChargeDeclaration(houseRequestModel);

		// Validation for Description Of Goods
		this.validateDescriptionOfGoods(houseRequestModel);

		// Validation for Harmonised Tariff Schedule Info
		this.validateHarmonisedTariffScheduleInfo(houseRequestModel);

		// Throw exception if message list is not empty
		if (!CollectionUtils.isEmpty(houseRequestModel.getMessageList())) {
			throw new CustomException(houseRequestModel.getMessageList());
		}
		return houseRequestModel;
	}

	private void validateConsigneeDetails(HouseCustomerModel houseRequestModel) {
		Set<ConstraintViolation<HouseCustomerModel>> violations = this.validator.validate(houseRequestModel,
				HouseWayBillConsigneeValidationGroup.class);
		for (final ConstraintViolation<?> violation : violations) {
			StringBuilder sbPath = new StringBuilder();
			sbPath.append("consignee.");
			sbPath.append(violation.getPropertyPath());
			houseRequestModel.addError(violation.getMessage(), sbPath.toString(), ErrorType.ERROR);
		}
	}

	private void validateShipperDetails(HouseCustomerModel houseRequestModel) {

		Set<ConstraintViolation<HouseCustomerModel>> violations = this.validator.validate(houseRequestModel,
				HouseWayBillShipperValidationGroup.class);
		for (final ConstraintViolation<?> violation : violations) {
			StringBuilder sbPath = new StringBuilder();
			sbPath.append("shipper.");
			sbPath.append(violation.getPropertyPath());
			houseRequestModel.addError(violation.getMessage(), sbPath.toString(), ErrorType.ERROR);
		}

	}

	/*
	 * Validate the Shc
	 */
	private void validateSHC(HouseModel houseRequestModel) {
		if (!CollectionUtils.isEmpty(houseRequestModel.getShc())) {
			Set<ConstraintViolation<List<HouseSpecialHandlingCodeModel>>> violations = this.validator
					.validate(houseRequestModel.getShc(), HouseWayBillValidationGroup.class);
			for (final ConstraintViolation<?> violation : violations) {
				StringBuilder sbPath = new StringBuilder();
				sbPath.append(violation.getRootBeanClass().getName()).append(".").append(violation.getPropertyPath());
				houseRequestModel.addError(violation.getMessage(), sbPath.toString(), ErrorType.ERROR);
			}

			// Check for Duplicate SHC
			Set<String> distinct = new HashSet<>();
			for (HouseSpecialHandlingCodeModel shc : houseRequestModel.getShc()) {
				if (!StringUtils.isEmpty(shc.getCode())) {
					if (distinct.contains(shc.getCode())) {
						houseRequestModel.addError("duplicate.shc.found", "code", ErrorType.APP);
					} else {
						distinct.add(shc.getCode());
					}
				}
			}
		}
	}

	/*
	 * Validate the shipper contact details
	 */
	private void validateShipperContactDetails(HouseModel houseRequestModel) {
		if (Optional.of(houseRequestModel.getShipper()).isPresent()
				&& Optional.of(houseRequestModel.getShipper().getAddress()).isPresent()
				&& !CollectionUtils.isEmpty(houseRequestModel.getShipper().getAddress().getContacts())) {

			Set<ConstraintViolation<List<HouseCustomerContactsModel>>> violations = this.validator.validate(
					houseRequestModel.getShipper().getAddress().getContacts(),
					HouseWayBillShipperValidationGroup.class);
			for (final ConstraintViolation<?> violation : violations) {
				StringBuilder sbPath = new StringBuilder();
				sbPath.append("shipper.address.contacts.");
				sbPath.append(violation.getPropertyPath());
				houseRequestModel.addError(violation.getMessage(), sbPath.toString(), ErrorType.ERROR);
			}

			// Flag for duplicate check
			boolean duplicateContactInfo = false;

			Set<String> distinct = new HashSet<>();
			for (HouseCustomerContactsModel shipper : houseRequestModel.getShipper().getAddress().getContacts()) {
				if (!StringUtils.isEmpty(shipper.getType()) && !StringUtils.isEmpty(shipper.getDetail())) {
					if (distinct.contains(shipper.getType() + "_" + shipper.getDetail())) {
						duplicateContactInfo = true;
					} else {
						distinct.add(shipper.getType() + "_" + shipper.getDetail());
					}
				}
			}
			if (duplicateContactInfo) {
				houseRequestModel.addError("duplicate.shipper.contact.found", "shipper.contact.label.name",
						ErrorType.APP);
			}
		}
	}

	/*
	 * Validate the consignee contact details
	 */
	private void validateConsigneeContactDetails(HouseModel houseRequestModel) {
		if (Optional.of(houseRequestModel.getConsignee()).isPresent()
				&& Optional.of(houseRequestModel.getConsignee().getAddress()).isPresent()
				&& !CollectionUtils.isEmpty(houseRequestModel.getConsignee().getAddress().getContacts())) {

			Set<ConstraintViolation<List<HouseCustomerContactsModel>>> violations = this.validator.validate(
					houseRequestModel.getConsignee().getAddress().getContacts(),
					HouseWayBillConsigneeValidationGroup.class);
			for (final ConstraintViolation<?> violation : violations) {
				StringBuilder sbPath = new StringBuilder();
				sbPath.append("consignee.address.contacts.");
				sbPath.append(violation.getPropertyPath());
				houseRequestModel.addError(violation.getMessage(), sbPath.toString(), ErrorType.ERROR);
			}

			// Flag for duplicate check
			boolean duplicateContactInfo = false;

			Set<String> distinct = new HashSet<>();
			for (HouseCustomerContactsModel consignee : houseRequestModel.getConsignee().getAddress().getContacts()) {
				if (!StringUtils.isEmpty(consignee.getType()) && !StringUtils.isEmpty(consignee.getDetail())) {
					if (distinct.contains(consignee.getType() + "_" + consignee.getDetail())) {
						duplicateContactInfo = true;
					} else {
						distinct.add(consignee.getType() + "_" + consignee.getDetail());
					}
				}
			}
			if (duplicateContactInfo) {
				houseRequestModel.addError("duplicate.consignee.contact.found", "consignee.contact.label.name",
						ErrorType.APP);
			}
		}
	}

	/*
	 * Validate the line items of Harmonised Tariffs
	 */
	private void validateHarmonisedTariffScheduleInfo(HouseModel houseRequestModel) {
		if (!CollectionUtils.isEmpty(houseRequestModel.getTariffs())) {
			Set<ConstraintViolation<List<HouseHarmonisedTariffScheduleModel>>> violations = this.validator
					.validate(houseRequestModel.getTariffs(), HouseWayBillValidationGroup.class);
			for (final ConstraintViolation<?> violation : violations) {
				StringBuilder sbPath = new StringBuilder();
				sbPath.append(violation.getRootBeanClass().getName()).append(".").append(violation.getPropertyPath());
				houseRequestModel.addError(violation.getMessage(), sbPath.toString(), ErrorType.ERROR);
			}

			// Check for Duplicate Description Goods
			Set<String> distinct = new HashSet<>();
			for (HouseHarmonisedTariffScheduleModel tariff : houseRequestModel.getTariffs()) {
				if (!StringUtils.isEmpty(tariff.getCode())) {
					if (distinct.contains(tariff.getCode())) {
						houseRequestModel.addError("duplicate.tariff.found", "code", ErrorType.APP);
					} else {
						distinct.add(tariff.getCode());
					}
				}
			}
		}
	}

	/*
	 * Validate the other charge info
	 */
	private void validateChargeDeclaration(HouseModel houseRequestModel) {
		Optional<HouseOtherChargeDeclarationModel> oOtherCharges = Optional
				.ofNullable(houseRequestModel.getOtherChargeDeclarations());
		if (oOtherCharges.isPresent()) {
			if (!StringUtils.isEmpty(oOtherCharges.get().getCurrencyCode())
					&& StringUtils.isEmpty(oOtherCharges.get().getOtherCharge())) {
				houseRequestModel.addError("data.house.other.charge.required", "otherCharge", ErrorType.ERROR);
			}

			if (!StringUtils.isEmpty(oOtherCharges.get().getCurrencyCode())
					&& StringUtils.isEmpty(oOtherCharges.get().getPcIndicator())) {
				houseRequestModel.addError("data.house.pcindicator.required", "pcIndicator", ErrorType.ERROR);
			}

			if (StringUtils.isEmpty(oOtherCharges.get().getCurrencyCode())
					&& (!StringUtils.isEmpty(oOtherCharges.get().getOtherCharge())
							|| !StringUtils.isEmpty(oOtherCharges.get().getPcIndicator()))) {
				houseRequestModel.addError("data.house.currency.required", "currencyCode", ErrorType.ERROR);
			}

			if (!StringUtils.isEmpty(oOtherCharges.get().getCurrencyCode())
					&& !StringUtils.isEmpty(oOtherCharges.get().getPcIndicator())
					&& !StringUtils.isEmpty(oOtherCharges.get().getOtherCharge())) {
				// Check for insurance value
				if (!StringUtils.isEmpty(oOtherCharges.get().getInsuranceValue())) {
					if ("XXX".equalsIgnoreCase(oOtherCharges.get().getInsuranceValue())) {
						houseRequestModel.getOtherChargeDeclarations().setInsuranceValue(null);
					} else {
						boolean showError = false;

						try {
							BigDecimal insuranceValue = new BigDecimal(oOtherCharges.get().getInsuranceValue());
							if (new BigDecimal("99999999999").compareTo(insuranceValue) < 0) {
								showError = true;
							}
						} catch (Exception e) {
							showError = true;
						}
						if (showError) {
							houseRequestModel.addError("data.house.oth.insurancevalue.invalid", "insuranceValue",
									ErrorType.ERROR);
						}
					}
				} else if (StringUtils.isEmpty(oOtherCharges.get().getInsuranceValue())) {
					houseRequestModel.getOtherChargeDeclarations().setInsuranceValue(null);
				}

				// Check for custom value
				if (!StringUtils.isEmpty(oOtherCharges.get().getCarriageValue())) {
					if ("NVD".equalsIgnoreCase(oOtherCharges.get().getCarriageValue())) {
						houseRequestModel.getOtherChargeDeclarations().setCarriageValue(null);
					} else {
						boolean showError = false;
						try {
							BigDecimal carriageValue = new BigDecimal(oOtherCharges.get().getCarriageValue());
							if (new BigDecimal("999999999999").compareTo(carriageValue) < 0) {
								showError = true;
							}
						} catch (Exception e) {
							showError = true;
						}

						if (showError) {
							houseRequestModel.addError("data.house.oth.carriagevalue.invalid", "carriageValue",
									ErrorType.ERROR);
						}
					}
				} else if (StringUtils.isEmpty(oOtherCharges.get().getCarriageValue())) {
					houseRequestModel.getOtherChargeDeclarations().setCarriageValue(null);
				}

				// Check for carriage value
				if (!StringUtils.isEmpty(oOtherCharges.get().getCustomValue())) {
					if ("NCV".equalsIgnoreCase(oOtherCharges.get().getCustomValue())) {
						houseRequestModel.getOtherChargeDeclarations().setCustomValue(null);
					} else {
						boolean showError = false;

						try {
							BigDecimal customValue = new BigDecimal(oOtherCharges.get().getCustomValue());
							if (new BigDecimal("999999999999").compareTo(customValue) < 0) {
								showError = true;
							}
						} catch (Exception e) {
							showError = true;
						}
						if (showError) {
							houseRequestModel.addError("data.house.oth.customvalue.invalid", "customValue",
									ErrorType.ERROR);
						}
					}
				} else if (StringUtils.isEmpty(oOtherCharges.get().getCustomValue())) {
					houseRequestModel.getOtherChargeDeclarations().setCustomValue(null);
				}

			}
		}
	}

	/**
	 * Method to validate OCI information for airports belonging to
	 * 
	 * @param houseRequestModel
	 */
	private void validateOCIForChinaCustoms(HouseModel houseRequestModel) {
		if (CollectionUtils.isEmpty(houseRequestModel.getOci())) {
			houseRequestModel.addError("data.oci.china.mandatory", null, ErrorType.ERROR);
		} else {
			boolean shpTFound = false;
			boolean cneTFound = false;
			boolean cneKCFound = false;
			boolean cneUFound = false;
			for (HouseOtherCustomsInformationModel t : houseRequestModel.getOci()) {
				// Check each line item
				String lineItem = t.getIdentifier() + "/" + t.getCsrcIdentifier();
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
				houseRequestModel.addError("data.oci.china.requirement", null, ErrorType.ERROR);
			}
		}
	}

	/**
	 * Method to validate OCI information for airports belonging to
	 * 
	 * @param houseRequestModel
	 */
	private void validateOCI(HouseModel houseRequestModel) {
		if (!CollectionUtils.isEmpty(houseRequestModel.getOci())) {
			Set<ConstraintViolation<List<HouseOtherCustomsInformationModel>>> violations = this.validator
					.validate(houseRequestModel.getOci(), HouseWayBillValidationGroup.class);
			for (final ConstraintViolation<?> violation : violations) {
				StringBuilder sbPath = new StringBuilder();
				sbPath.append(violation.getRootBeanClass().getName()).append(".").append(violation.getPropertyPath());
				houseRequestModel.addError(violation.getMessage(), sbPath.toString(), ErrorType.ERROR);
			}
		}
	}

	/*
	 * Validate the line items of Free Text
	 */
	private void validateDescriptionOfGoods(HouseModel houseRequestModel) {
		if (!CollectionUtils.isEmpty(houseRequestModel.getDescriptionOfGoods())) {
			Set<ConstraintViolation<List<HouseDescriptionOfGoodsModel>>> violations = this.validator
					.validate(houseRequestModel.getDescriptionOfGoods(), HouseWayBillValidationGroup.class);
			for (final ConstraintViolation<?> violation : violations) {
				StringBuilder sbPath = new StringBuilder();
				sbPath.append(violation.getRootBeanClass().getName()).append(".").append(violation.getPropertyPath());
				houseRequestModel.addError(violation.getMessage(), sbPath.toString(), ErrorType.ERROR);
			}
		}
	}
}