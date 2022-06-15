package com.ngen.cosys.util;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.billing.api.Charge;
import com.ngen.cosys.billing.api.enums.ChargeEvents;
import com.ngen.cosys.billing.api.model.BillingShipment;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownModel;
import com.ngen.cosys.impbd.shipment.verification.model.DocumentVerificationFlightModel;
import com.ngen.cosys.impbd.shipment.verification.model.DocumentVerificationShipmentModel;
import com.ngen.cosys.impbd.workinglist.model.BreakDownWorkingListModel;
import com.ngen.cosys.impbd.workinglist.model.BreakDownWorkingListShipmentResult;

@Component
public class BillingUtilsExpbd {

	/**
	 * @param documentVerification
	 */

	public void calculateBillForAWB(DocumentVerificationFlightModel documentVerification) {

		for (DocumentVerificationShipmentModel shipemtModel : documentVerification
				.getDocumentVerificationShipmentModelList()) {
			BillingShipment billingShipment = new BillingShipment();
			billingShipment.setShipmentNumber(shipemtModel.getShipmentNumber());
			billingShipment.setShipmentDate(shipemtModel.getShipmentdate());
			billingShipment.setHandlingTerminal(documentVerification.getTerminal());
			billingShipment.setUserCode(documentVerification.getLoggedInUser());
			billingShipment.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
			billingShipment.setEventType(ChargeEvents.IMP_SHIPMENT_UPDATE);
			try {
				Charge.calculateCharge(billingShipment);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void calculateBillForBreakOut(BreakDownWorkingListModel breakDownWorkingListModel) {
		for (BreakDownWorkingListShipmentResult breakDownWorkingListShipmentResult : breakDownWorkingListModel
				.getBreakDownWorkingListShipmentResult()) {
			BillingShipment billingShipment = new BillingShipment();
			billingShipment.setShipmentNumber(breakDownWorkingListShipmentResult.getShipmentNumber());
			billingShipment.setShipmentDate(breakDownWorkingListShipmentResult.getShipmentdate());
			billingShipment.setHandlingTerminal(breakDownWorkingListModel.getTerminal());
			billingShipment.setUserCode(breakDownWorkingListModel.getLoggedInUser());
			billingShipment.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
			billingShipment.setEventType(ChargeEvents.IMP_BREAKDOWN);
			try {
				Charge.calculateCharge(billingShipment);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void calculateBillForBreakOutOnSave(InboundBreakdownModel inboundBreakdownModel) {

		BillingShipment billingShipment = new BillingShipment();
		billingShipment.setShipmentNumber(inboundBreakdownModel.getShipment().getShipmentNumber());
		billingShipment.setShipmentDate(inboundBreakdownModel.getShipment().getShipmentdate());
		billingShipment.setHandlingTerminal(inboundBreakdownModel.getTerminal());
		billingShipment.setUserCode(inboundBreakdownModel.getLoggedInUser());
		billingShipment.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
		billingShipment.setEventType(ChargeEvents.IMP_BREAKDOWN);

		if (inboundBreakdownModel.getHawbInfo() != null
				&& !ObjectUtils.isEmpty(inboundBreakdownModel.getHawbInfo().getHawbNumber())
				&& !ObjectUtils.isEmpty(inboundBreakdownModel.getHawbInfo().getShipmentHouseId())) {
			billingShipment.setHandelByHouse(true);
			billingShipment.setShipmentHouseId(inboundBreakdownModel.getHawbInfo().getShipmentHouseId());
			billingShipment.setHouseNumber(inboundBreakdownModel.getHawbInfo().getHawbNumber());
		}

		Charge.calculateCharge(billingShipment);

	}

	/*
	 * private BigInteger
	 * calculatingInventaryPieces(List<InboundBreakdownShipmentInventoryModel> req)
	 * { BigInteger sum = BigInteger.ZERO; for
	 * (InboundBreakdownShipmentInventoryModel inventary : req) { sum =
	 * sum.add(inventary.getPieces()); } return sum; }
	 */

}
