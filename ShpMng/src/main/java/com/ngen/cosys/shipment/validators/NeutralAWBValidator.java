package com.ngen.cosys.shipment.validators;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.shipment.model.AccountingInfo;
import com.ngen.cosys.shipment.model.CustomerContactInfo;
import com.ngen.cosys.shipment.model.FlightBooking;
import com.ngen.cosys.shipment.model.OtherCharges;
import com.ngen.cosys.shipment.model.RateDescOtherInfo;
import com.ngen.cosys.shipment.model.RateDescription;
import com.ngen.cosys.shipment.model.Routing;
import com.ngen.cosys.shipment.model.SSROSIInfo;
import com.ngen.cosys.shipment.nawb.model.NeutralAWBMaster;
import com.ngen.cosys.shipment.nawb.validator.NeutralAWBValidatorGroup;

@Component
public class NeutralAWBValidator {

	@Autowired
	private Validator validator;

	public void checkShipperContactDetails(NeutralAWBMaster nawbmaster) {
		for (CustomerContactInfo c : nawbmaster.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo()) {
			if (c.getContactDetail() == null && c.getContactIdentifier() != null) {
				int index = nawbmaster.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo().indexOf(c);
				nawbmaster.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo().get(index)
						.addError("g.required", "contactDetail", ErrorType.ERROR);
				nawbmaster.setFlagError(true);
				break;
			}
			if (c.getContactDetail() != null && c.getContactIdentifier() == null) {
				int index = nawbmaster.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo().indexOf(c);
				nawbmaster.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo().get(index)
						.addError("g.required", "contactIdentifier", ErrorType.ERROR);
				nawbmaster.setFlagError(true);
				break;
			}
		}

	}

	public void checkConsigneeContactDetails(NeutralAWBMaster nawbmaster) {
		for (CustomerContactInfo c : nawbmaster.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo()) {
			if (c.getContactDetail() == null && c.getContactIdentifier() != null) {
				int index = nawbmaster.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo().indexOf(c);
				nawbmaster.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo().get(index)
						.addError("g.required", "contactDetail", ErrorType.ERROR);
				nawbmaster.setFlagError(true);
				break;
			}
			if (c.getContactDetail() != null && c.getContactIdentifier() == null) {
				int index = nawbmaster.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo().indexOf(c);
				nawbmaster.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo().get(index)
						.addError("g.required", "contactIdentifier", ErrorType.ERROR);
				nawbmaster.setFlagError(true);
				break;
			}
		}

	}

	public void checkAlsoNotifyContactDetails(NeutralAWBMaster nawbmaster) {
		for (CustomerContactInfo c : nawbmaster.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo()) {
			if (c.getContactDetail() == null && c.getContactIdentifier() != null) {
				int index = nawbmaster.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo().indexOf(c);
				nawbmaster.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo().get(index)
						.addError("g.required", "contactDetail", ErrorType.ERROR);
				nawbmaster.setFlagError(true);
				break;
			}
			if (c.getContactDetail() != null && c.getContactIdentifier() == null) {
				int index = nawbmaster.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo().indexOf(c);
				nawbmaster.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo().get(index)
						.addError("g.required", "contactIdentifier", ErrorType.ERROR);
				nawbmaster.setFlagError(true);
				break;
			}
		}

	}

	public void checkDuplicateDestination(NeutralAWBMaster nawbmaster) {
		int index = 0;
		Set<String> allItems = new HashSet<>();
		for (Routing r : nawbmaster.getRoutingList()) {
			if (allItems.add(r.getTo()) == false) {
				index = nawbmaster.getRoutingList().indexOf(r);
				break;
			}
		}
		if (index != 0) {
			nawbmaster.getRoutingList().get(index).addError("Duplicate Destination", "to", ErrorType.ERROR);
			nawbmaster.setFlagError(true);
		}

	}

	public void checkDuplicateFlightBooking(NeutralAWBMaster nawbmaster) {
		int index = 0;
		Set<String> allItems = new HashSet<>();
		for (FlightBooking f : nawbmaster.getFlightBookingList()) {

			if (f.getCarrierCode() == null && f.getFlightDate() == null && f.getFlightNumber() != null) {
				nawbmaster.addError("flight.incomplete.flight.details", "", ErrorType.ERROR);
			} else if (f.getCarrierCode() != null && f.getFlightDate() == null && f.getFlightNumber() == null) {
				nawbmaster.addError("flight.incomplete.flight.details", "", ErrorType.ERROR);
			} else if (f.getCarrierCode() == null && f.getFlightDate() != null && f.getFlightNumber() == null) {
				nawbmaster.addError("flight.incomplete.flight.details", "", ErrorType.ERROR);
			} else if (f.getCarrierCode() != null && f.getFlightDate() == null && f.getFlightNumber() != null) {
				nawbmaster.addError("flight.incomplete.flight.details", "", ErrorType.ERROR);
			} else if (f.getCarrierCode() == null && f.getFlightDate() != null && f.getFlightNumber() != null) {
				nawbmaster.addError("flight.incomplete.flight.details", "", ErrorType.ERROR);
			}
		}
		for (FlightBooking f : nawbmaster.getFlightBookingList()) {
			if (allItems.add(f.getFlightKey()) == false) {
				index = nawbmaster.getFlightBookingList().indexOf(f);
				break;
			}
		}
		if (index != 0) {
			nawbmaster.getFlightBookingList().get(index).addError("flight.duplicate.flight", "flightNumber", ErrorType.ERROR);
			nawbmaster.setFlagError(true);
		}

	}

	public void checkDuplicateDueAgent(NeutralAWBMaster nawbmaster) {
		int index = 0;
		Set<String> allItemsOtherCharges = new HashSet<>();
		Set<String> allItemsOtherChargeCode = new HashSet<>();
		for (OtherCharges o : nawbmaster.getOtherChargesDueAgent()) {
		   if(!o.getFlagCRUD().equalsIgnoreCase(Action.DELETE.value())) {
		      boolean otherChargeCodeFlag = allItemsOtherChargeCode.add(o.getOtherChargeCode());
	            boolean otherChargesFlag = allItemsOtherCharges.add(o.getOtherChargeIndicator());
	            if (otherChargesFlag == false && otherChargeCodeFlag == false) {
	                index = nawbmaster.getOtherChargesDueAgent().indexOf(o);
	                break;
	            }  
		   }
		}
		if (index != 0) {
			nawbmaster.getOtherChargesDueAgent().get(index).addError("billing.duplicate.chg.code.chg.identifier",
					"otherChargeIndicator", ErrorType.ERROR);
			nawbmaster.setFlagError(true);
		}

	}

	public void checkDuplicateDueCarrier(NeutralAWBMaster nawbmaster) {
		int index = 0;
		Set<String> allItemsOtherCharges = new HashSet<>();
		Set<String> allItemsOtherChargeCode = new HashSet<>();
		for (OtherCharges o : nawbmaster.getOtherChargesDueCarrier()) {
		   if(!o.getFlagCRUD().equalsIgnoreCase(Action.DELETE.value())) {
		      boolean otherChargeCodeFlag = allItemsOtherChargeCode.add(o.getOtherChargeCode());
	            boolean otherChargesFlag = allItemsOtherCharges.add(o.getOtherChargeIndicator());
	            if (otherChargesFlag == false && otherChargeCodeFlag == false) {
	                index = nawbmaster.getOtherChargesDueCarrier().indexOf(o);
	                break;
	            }
		   }
		}
		if (index != 0) {
			nawbmaster.getOtherChargesDueCarrier().get(index).addError("billing.duplicate.chg.code.chg.identifier",
					"otherChargeIndicator", ErrorType.ERROR);
			nawbmaster.setFlagError(true);
		}
	}

	public void checkDuplicateAccountingInfo(NeutralAWBMaster nawbmaster) {
		Set<String> allItems = new HashSet<>();
		for (AccountingInfo a : nawbmaster.getAccountingInfo()) {
			if (allItems.add(a.getInformationIdentifier()) == false) {
				int index = nawbmaster.getAccountingInfo().indexOf(a);
				nawbmaster.getAccountingInfo().get(index).addError("billing.duplicate.account.type", "informationIdentifier",
						ErrorType.ERROR);
				nawbmaster.setFlagError(true);
				break;
			}
		}
	}

	public void validateCommodity(NeutralAWBMaster neutralAWBMaster) {
		for (RateDescription rateDescription : neutralAWBMaster.getRateDescriptionForNawb()) {
			for (RateDescOtherInfo r : rateDescription.getRateDescriptionOtherInfoForNawb()) {
				if (r.getType() == null) {
					r.addError("g.required", "type", ErrorType.ERROR);
					neutralAWBMaster.setFlagError(true);
					break;
				}
				if (r.getType().equals("NO")) {
					if (r.getCountryCode() == null || r.getCountryCode().trim().length() == 0) {
						r.addError("g.required", "countryCode", ErrorType.ERROR);
						neutralAWBMaster.setFlagError(true);

					}
				}

				else if (r.getType().equals("NH")) {
					if (r.getHarmonisedCommodityCode() == null) {
						r.addError("g.required", "harmonisedCommodityCode", ErrorType.ERROR);
						neutralAWBMaster.setFlagError(true);

					}
				} else if (r.getType().equals("NU")) {
					if (r.getUldNumber() == null || r.getUldNumber().trim().length() == 0) {
						r.addError("g.required", "uldNumber", ErrorType.ERROR);
						neutralAWBMaster.setFlagError(true);

					}
				} else if (r.getType().equals("NC")) {
					if (r.getNatureOfGoodsDescription() == null || r.getNatureOfGoodsDescription().trim().length() == 0) {
						r.addError("g.required", "natureOfGoodsDescription", ErrorType.ERROR);
						neutralAWBMaster.setFlagError(true);

					}
				} else if (r.getType().equals("NV")) {
					if (r.getVolumeUnitCode() == null || r.getVolumeUnitCode().trim().length() == 0) {
						r.addError("g.required", "volumeUnitCode", ErrorType.ERROR);
						neutralAWBMaster.setFlagError(true);

					} else if (r.getVolumeAmount() == null) {
						r.addError("g.required", "volumeAmount", ErrorType.ERROR);
						neutralAWBMaster.setFlagError(true);

					}
				} else if (r.getType().equals("NG")) {
					if (r.getNatureOfGoodsDescription() == null || r.getNatureOfGoodsDescription().trim().length() == 0) {
						r.addError("g.required", "natureOfGoodsDescription", ErrorType.ERROR);
						neutralAWBMaster.setFlagError(true);

					}
				} else if (r.getType().equals("NS")) {
					if (r.getSlacCount() == null) {
						r.addError("g.required", "slacCount", ErrorType.ERROR);
						neutralAWBMaster.setFlagError(true);

					}
				} else if (r.getType().equals("ND")) {
					if (r.getWeight() == null) {
						r.addError("g.required", "weight", ErrorType.ERROR);
						neutralAWBMaster.setFlagError(true);

					}
					if (!r.isNoDimensionAvailable()) {
						if (r.getMeasurementUnitCode() == null || r.getMeasurementUnitCode().trim().length() == 0) {
							r.addError("g.required", "measurementUnitCode", ErrorType.ERROR);
							neutralAWBMaster.setFlagError(true);

						}

						if (r.getDimensionLength() == null) {
							r.addError("g.required", "dimensionLength", ErrorType.ERROR);
							neutralAWBMaster.setFlagError(true);

						}
						if (r.getDimensionHeight() == null) {
							r.addError("g.required", "dimensionHeight", ErrorType.ERROR);
							neutralAWBMaster.setFlagError(true);

						}
						if (r.getDimensionWIdth() == null) {
							r.addError("g.required", "dimensionWIdth", ErrorType.ERROR);
							neutralAWBMaster.setFlagError(true);

						}
						if (r.getNumberOfPieces() == null) {
							r.addError("g.required", "numberOfPieces", ErrorType.ERROR);
							neutralAWBMaster.setFlagError(true);

						}

					}
				}

			}
		}
	}

	public void validateDueAgent(NeutralAWBMaster neutralAWBMaster) {
		for (OtherCharges o : neutralAWBMaster.getOtherChargesDueAgent()) {
			if (o.getOtherChargeCode() == null
					&& (o.getChargeAmount() != null || o.getOtherChargeIndicator() != null)) {
				int index = neutralAWBMaster.getOtherChargesDueAgent().indexOf(o);
				neutralAWBMaster.getOtherChargesDueAgent().get(index).addError("g.required", "otherChargeCode",
						ErrorType.ERROR);
				neutralAWBMaster.setFlagError(true);
			}

			else if (o.getOtherChargeIndicator() == null
					&& (o.getOtherChargeCode() != null || o.getChargeAmount() != null)) {
				int index = neutralAWBMaster.getOtherChargesDueAgent().indexOf(o);
				neutralAWBMaster.getOtherChargesDueAgent().get(index).addError("g.required", "chargeIndicator",
						ErrorType.ERROR);
				neutralAWBMaster.setFlagError(true);
			} else if (o.getOtherChargeIndicator() != null && o.getOtherChargeCode() != null
					&& o.getChargeAmount() == null) {
				int index = neutralAWBMaster.getOtherChargesDueAgent().indexOf(o);
				neutralAWBMaster.getOtherChargesDueAgent().get(index).addError("g.required", "chargeAmount",
						ErrorType.ERROR);
				neutralAWBMaster.setFlagError(true);
			}
		}

	}

	public void validateDueCarrier(NeutralAWBMaster neutralAWBMaster) {
		for (OtherCharges o : neutralAWBMaster.getOtherChargesDueCarrier()) {
			if (o.getOtherChargeIndicator() != null && o.getOtherChargeCode() == null && o.getChargeAmount() != null) {
				int index = neutralAWBMaster.getOtherChargesDueCarrier().indexOf(o);
				neutralAWBMaster.getOtherChargesDueCarrier().get(index).addError("g.required", "otherChargeCode",
						ErrorType.ERROR);
				neutralAWBMaster.setFlagError(true);
			} else if (o.getOtherChargeIndicator() == null && o.getOtherChargeCode() != null
					&& o.getChargeAmount() != null) {
				int index = neutralAWBMaster.getOtherChargesDueCarrier().indexOf(o);
				neutralAWBMaster.getOtherChargesDueCarrier().get(index).addError("g.required", "chargeIndicator",
						ErrorType.ERROR);
				neutralAWBMaster.setFlagError(true);
			} else if (o.getOtherChargeIndicator() != null && o.getOtherChargeCode() != null
					&& o.getChargeAmount() == null) {
				int index = neutralAWBMaster.getOtherChargesDueCarrier().indexOf(o);
				neutralAWBMaster.getOtherChargesDueCarrier().get(index).addError("g.required", "chargeAmount",
						ErrorType.ERROR);
				neutralAWBMaster.setFlagError(true);
			}
		}

	}

	public void checkValidChargeDeclaration(NeutralAWBMaster neutralAWBMaster) {

		if (Optional.ofNullable(neutralAWBMaster.getChargeDeclaration()).isPresent()) {
			if (!StringUtils.isEmpty(neutralAWBMaster.getChargeDeclaration().getCarriageValueDeclaration())
					&& ((!neutralAWBMaster.getChargeDeclaration().getCarriageValueDeclaration().equals("NVD"))
							&& (!neutralAWBMaster.getChargeDeclaration().getCarriageValueDeclaration()
									.matches("-?\\d+(\\.\\d+)?")))) {
				neutralAWBMaster.addError("billing.only.numeric.value", "chargeDeclaration.carriageValueDeclaration",
						ErrorType.ERROR);
				neutralAWBMaster.setFlagError(true);
				return;
			}
			if (!StringUtils.isEmpty(neutralAWBMaster.getChargeDeclaration().getCustomsValueDeclaration())
					&& ((!neutralAWBMaster.getChargeDeclaration().getCustomsValueDeclaration().equals("NCV"))
							&& (!neutralAWBMaster.getChargeDeclaration().getCustomsValueDeclaration()
									.matches("-?\\d+(\\.\\d+)?")))) {
				neutralAWBMaster.addError("billing.only.numeric.value", "chargeDeclaration.customsValueDeclaration",
						ErrorType.ERROR);
				neutralAWBMaster.setFlagError(true);
				return;
			}

			if (!StringUtils.isEmpty(neutralAWBMaster.getChargeDeclaration().getInsuranceValueDeclaration())
					&& ((!neutralAWBMaster.getChargeDeclaration().getInsuranceValueDeclaration().equals("XXX"))
							&& (!neutralAWBMaster.getChargeDeclaration().getInsuranceValueDeclaration()
									.matches("-?\\d+(\\.\\d+)?")))) {
				neutralAWBMaster.addError("billing.only.numeric.value", "chargeDeclaration.insuranceValueDeclaration",
						ErrorType.ERROR);
				neutralAWBMaster.setFlagError(true);
				return;
			}

		}
	}

	public void checkAgentInfo(NeutralAWBMaster neutralAWBMaster) {
		if (Optional.ofNullable(neutralAWBMaster.getAgentInfo()).isPresent()) {
			if (!Optional.ofNullable(neutralAWBMaster.getAgentInfo().getCargAgentNumericCode()).isPresent()) {
				neutralAWBMaster.addError("g.required", "agentInfo.cargAgentNumericCode", ErrorType.ERROR);
				neutralAWBMaster.setFlagError(true);
				return;
			}
		}
	}

	public void validateNAWBModel(NeutralAWBMaster neutralAWBMaster) {
		// Validate house model
		Set<ConstraintViolation<NeutralAWBMaster>> violations = this.validator.validate(neutralAWBMaster,
				NeutralAWBValidatorGroup.class);
		for (final ConstraintViolation<?> violation : violations) {
			StringBuilder sbPath = new StringBuilder();
			sbPath.append(violation.getRootBeanClass().getName()).append(".").append(violation.getPropertyPath());
			neutralAWBMaster.addError(violation.getMessage(), sbPath.toString(), ErrorType.ERROR);
			neutralAWBMaster.setFlagError(true);
		}
	}

	public void checkAlsoNotify(NeutralAWBMaster nawbmaster) {
		if (Optional.ofNullable(nawbmaster.getAlsoNotify()).isPresent()) {
			if (!StringUtils.isEmpty(nawbmaster.getAlsoNotify().getCustomerName())
					&& !nawbmaster.getAlsoNotify().getCustomerName().matches("^[A-Za-z0-9-. ]*$")) {
				nawbmaster.addError("data.invalid.telex.characters", "alsoNotify.customerName", ErrorType.ERROR);
				nawbmaster.setFlagError(true);
				return;
			}

			if (!StringUtils.isEmpty(nawbmaster.getAlsoNotify().getCustomerAddressInfo().getStreetAddress1())
					&& !nawbmaster.getAlsoNotify().getCustomerAddressInfo().getStreetAddress1()
							.matches("^[A-Za-z0-9-. ]*$")) {
				nawbmaster.addError("data.invalid.telex.characters", "alsoNotify.customerAddressInfo.streetAddress1",
						ErrorType.ERROR);
				nawbmaster.setFlagError(true);
				return;
			}

			if (!StringUtils.isEmpty(nawbmaster.getAlsoNotify().getCustomerAddressInfo().getCustomerPlace())
					&& !nawbmaster.getAlsoNotify().getCustomerAddressInfo().getCustomerPlace()
							.matches("^[A-Za-z0-9-. ]*$")) {
				nawbmaster.addError("data.invalid.telex.characters", "alsoNotify.customerAddressInfo.customerPlace",
						ErrorType.ERROR);
				nawbmaster.setFlagError(true);
				return;
			}

			if (!StringUtils.isEmpty(nawbmaster.getAlsoNotify().getCustomerAddressInfo().getStateCode()) && !nawbmaster
					.getAlsoNotify().getCustomerAddressInfo().getStateCode().matches("^[A-Za-z0-9-. ]*$")) {
				nawbmaster.addError("data.invalid.telex.characters", "alsoNotify.customerAddressInfo.stateCode",
						ErrorType.ERROR);
				nawbmaster.setFlagError(true);
				return;
			}
		}

	}

	public void validateSsrOci(NeutralAWBMaster nawbmaster) {

		for (SSROSIInfo s : nawbmaster.getSsrOsiInfo()) {
			if (s.getServiceRequestType() == null && s.getServiceRequestcontent() != null) {
				int index = nawbmaster.getSsrOsiInfo().indexOf(s);
				nawbmaster.getSsrOsiInfo().get(index)
						.addError("g.required", "serviceRequestType", ErrorType.ERROR);
				nawbmaster.setFlagError(true);
				break;
			}
			if (s.getServiceRequestType() != null && s.getServiceRequestcontent() == null) {
				int index = nawbmaster.getSsrOsiInfo().indexOf(s);
				nawbmaster.getSsrOsiInfo().get(index)
						.addError("g.required", "serviceRequestcontent", ErrorType.ERROR);
				nawbmaster.setFlagError(true);
				break;
			}
		}

	
	}

}