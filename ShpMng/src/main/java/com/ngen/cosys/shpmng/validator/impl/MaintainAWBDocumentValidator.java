package com.ngen.cosys.shpmng.validator.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.shipment.awb.model.AWB;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterRoutingInfo;
import com.ngen.cosys.shipment.validator.ShpMngBusinessValidator;
import com.ngen.cosys.shipment.validators.SaveAWBDocument;

@Component
public class MaintainAWBDocumentValidator implements ShpMngBusinessValidator {

	@Autowired
	private Validator validator;

	@Override
	public BaseBO validate(BaseBO baseModel) throws CustomException {

		AWB awbModel = null;
		//
		if (baseModel == null) {
			return baseModel;
		}
		if (baseModel instanceof AWB) {
			awbModel = (AWB) baseModel;
		}
		//
		this.validateRoutingInfo(awbModel);

		if (awbModel != null && !CollectionUtils.isEmpty(awbModel.getMessageList())) {
			throw new CustomException(awbModel.getMessageList());
		}
		return awbModel;
	}

	//
	private void validateRoutingInfo(AWB awbModel) {
		// By default First routing is mandatory
		if (CollectionUtils.isEmpty(awbModel.getRouting())) {
			awbModel.addError("Routing.info.not.found", "routing.field", ErrorType.APP);
			return;
		}
		// Origin & Destination is not SIN then 2 routing info is required
		int count = 0;
		for (ShipmentMasterRoutingInfo routing : awbModel.getRouting()) {
			//
			if (count < 2 && MultiTenantUtility.isTranshipment(awbModel.getOrigin(), awbModel.getDestination())) {
				if (!StringUtils.isEmpty(routing.getCarrier()) && !StringUtils.isEmpty(routing.getFromPoint())) {
					count++;
					continue;
				} else {
					awbModel.addError("routing.info.is.mandatory", "routing.info", ErrorType.APP);
					break;
				}
			} else {
				if (!StringUtils.isEmpty(routing.getCarrier()) && !StringUtils.isEmpty(routing.getFromPoint())) {
					break;
				}
			}
		}

		Set<ConstraintViolation<List<ShipmentMasterRoutingInfo>>> violations = this.validator
				.validate(awbModel.getRouting(), SaveAWBDocument.class);
		//
		for (final ConstraintViolation<?> violation : violations) {
			StringBuilder sbPath = new StringBuilder();
			sbPath.append(violation.getRootBeanClass().getName()).append(".").append(violation.getPropertyPath());
			awbModel.addError(violation.getMessage(), sbPath.toString(), ErrorType.ERROR);
		}

		Set<String> distinctCarrier = new HashSet<>();
		for (ShipmentMasterRoutingInfo routing : awbModel.getRouting()) {
			if (!StringUtils.isEmpty(routing.getCarrier())) {
				if (distinctCarrier.contains(routing.getCarrier())) {
					awbModel.addError("duplicate.carrier.found", "code", ErrorType.APP);
				} else {
					distinctCarrier.add(routing.getCarrier());
				}
			}
		}

		Set<String> distinctFromPoint = new HashSet<>();
		for (ShipmentMasterRoutingInfo routing : awbModel.getRouting()) {
			if (!StringUtils.isEmpty(routing.getFromPoint())) {
				if (distinctFromPoint.contains(routing.getCarrier())) {
					awbModel.addError("duplicate.from.point.found", "code", ErrorType.APP);
				} else {
					distinctFromPoint.add(routing.getFromPoint());
				}
			}
		}
	}
}