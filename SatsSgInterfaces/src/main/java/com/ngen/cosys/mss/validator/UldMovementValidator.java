package com.ngen.cosys.mss.validator;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.satssginterfaces.mss.model.BaseErrorResponseMessagesMss;
import com.ngen.cosys.satssginterfaces.mss.model.ErrorResponseMessagesMss;
import com.ngen.cosys.satssginterfaces.mss.model.MSSMailBagMovement;
import com.ngen.cosys.satssginterfaces.mss.model.MailBagUldMovementBaseModel;

@Service
public class UldMovementValidator {

	public BaseErrorResponseMessagesMss validateUldMovement(MailBagUldMovementBaseModel mailBagUldData) {
		ErrorResponseMessagesMss errorData = new ErrorResponseMessagesMss();
		BaseErrorResponseMessagesMss baseErrorResponseMessagesMss = new BaseErrorResponseMessagesMss();
		//
		for (MSSMailBagMovement data : mailBagUldData.getListMailBagUldMovement()) {
			// checkMandatory
			errorData = this.validateMessage(data);
		}
		if (errorData != null) {
			errorData.setOriginalMessageType(mailBagUldData.getMsgType());
			errorData.setOriginalMessageReferenceNumber(mailBagUldData.getMsgReferenceNum());
			baseErrorResponseMessagesMss.setMsgType("TGFMRESP");
			errorData.setErrorReason("FLD_MISS");
			errorData.setErrorType("FORMAT");
			baseErrorResponseMessagesMss.setMsgSendingndSystem(mailBagUldData.getMsgSendingndSystem());
			baseErrorResponseMessagesMss.setMsgRecipientSystem(mailBagUldData.getMsgRecipientSystem());
			baseErrorResponseMessagesMss.setDataMsgSend(mailBagUldData.getDataMsgSend());
			baseErrorResponseMessagesMss.setMsgReferenceNum(mailBagUldData.getMsgReferenceNum());
			baseErrorResponseMessagesMss.setNumDataElmnt(mailBagUldData.getNumDataElmnt());
			baseErrorResponseMessagesMss.setErrorResponseMessagesMss(errorData);
		}
		//
		return baseErrorResponseMessagesMss;
	}

	private ErrorResponseMessagesMss validateMessage(MSSMailBagMovement data) {
		ErrorResponseMessagesMss errorData = new ErrorResponseMessagesMss();
		//
		if (StringUtils.isEmpty(data.getTransactionSequenceNumber())) {
			errorData.setErrorDescription("Transaction Sequence Number can't be null");
			errorData.setErrorField("TRA_REF_NUM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getAction())) {
			errorData.setErrorDescription("Action can't be null");
			errorData.setErrorField("ACTION");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getUldType())) {
			if (StringUtils.isEmpty(data.getUldNumber())) {
				errorData.setErrorDescription("Uld Number can't be null");
				errorData.setErrorField("ULD_NUM");
				errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
				return errorData;
			} else if (StringUtils.isEmpty(data.getUldCarrier())) {
				errorData.setErrorDescription("Uld Carrier can't be null");
				errorData.setErrorField("ULD_CAR");
				errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
				return errorData;
			} else if (StringUtils.isEmpty(data.getUldDestination())) {
				errorData.setErrorDescription("Uld Destination can't be null");
				errorData.setErrorField("ULD_DES");
				errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
				return errorData;
			}
		} else if (!StringUtils.isEmpty(data.getIncomimngFlightCarrier())) {
			if (StringUtils.isEmpty(data.getIncomimngFlightNumber())) {
				errorData.setErrorDescription("Incoming Flight Number can't be null");
				errorData.setErrorField("IN_FLT_NUM");
				errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
				return errorData;
			} else if (StringUtils.isEmpty(data.getIncomimngFlightDate())) {
				errorData.setErrorDescription("Incoming Flight Date can't be null");
				errorData.setErrorField("IN_FLT_DATE");
				errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
				return errorData;
			} else if (StringUtils.isEmpty(data.getIncomimngFlightAirport())) {
				errorData.setErrorDescription("Incoming Flight Airport can't be null");
				errorData.setErrorField("IN_FLT_ARPO");
				errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
				return errorData;
			} else {

			}
		} else if (!StringUtils.isEmpty(data.getOrigin()) &&  MultiTenantUtility.isTenantCityOrAirport(data.getOrigin())) {
			if (StringUtils.isEmpty(data.getOutgoingFlightCarrier())) {
				errorData.setErrorDescription("Outgoing Flight Carrier can't be null");
				errorData.setErrorField("OUT_FLT_CAR");
				errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
				return errorData;
			}
		} else if (!StringUtils.isEmpty(data.getOutgoingFlightNumber())) {
			if (StringUtils.isEmpty(data.getOutgoingFlightDate())) {
				errorData.setErrorDescription("Outgoing Flight Date can't be null");
				errorData.setErrorField("OUT_FLT_DATE");
				errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
				return errorData;
			} else if (StringUtils.isEmpty(data.getOutgoingFlightTime())) {
				errorData.setErrorDescription("Outgoing Flight Time can't be null");
				errorData.setErrorField("OUT_FLT_TIME");
				errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
				return errorData;
			} else {

			}
		}
		if (StringUtils.isEmpty(data.getGrossWeight())) {
			errorData.setErrorDescription("Gross Weight can't be null");
			errorData.setErrorField("GROSS_WEIGHT");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getOccurrenceDateTime())) {
			errorData.setErrorDescription("Occurrence DateTime can't be null");
			errorData.setErrorField("OCCU_DTTM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getUser())) {
			errorData.setErrorDescription("User can't be null");
			errorData.setErrorField("USER");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getTransactionSequenceNumber())
				&& data.getTransactionSequenceNumber().length() > 8) {
			errorData.setErrorDescription("The length of Transaction Sequence Number can't be more than 8");
			errorData.setErrorField("TRA_REF_NUM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getAction()) && data.getAction().length() > 3) {
			errorData.setErrorDescription("The length of  Action can't be more than 3");
			errorData.setErrorField("ACTION");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getUldType()) && data.getUldType().length() > 3) {
			errorData.setErrorDescription("The length of  UldType can't be more than 3");
			errorData.setErrorField("ULD_TYP");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getUldNumber()) && data.getUldNumber().length() > 5) {
			errorData.setErrorDescription("The length of  Uld Number can't be more than 5");
			errorData.setErrorField("ULD_NUM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getUldCarrier()) && data.getUldCarrier().length() > 3) {
			errorData.setErrorDescription("The length of Uld Carrier can't be more than 3");
			errorData.setErrorField("ULD_CAR");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getUldDestination()) && data.getUldDestination().length() > 3) {
			errorData.setErrorDescription("The length of Uld Destination can't be more than 3");
			errorData.setErrorField("ULD_DES");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getUldNewDestination()) && data.getUldNewDestination().length() > 3) {
			errorData.setErrorDescription("The length of Uld New Destination can't be more than 3");
			errorData.setErrorField("ULD_NEW_DES");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getIncomimngFlightAirport())
				&& data.getIncomimngFlightAirport().length() > 3) {
			errorData.setErrorDescription("The length of Incoming Flight Airport can't be more than 3");
			errorData.setErrorField("IN_FLT_ARPO");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getIncomimngFlightCarrier())
				&& data.getIncomimngFlightCarrier().length() > 3) {
			errorData.setErrorDescription("The length of Incoming Flight Carrier can't be more than 3");
			errorData.setErrorField("IN_FLT_CAR");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getIncomimngFlightNumber())
				&& data.getIncomimngFlightNumber().length() > 5) {
			errorData.setErrorDescription("The length of Incoming Fligh tNumber can't be more than 5");
			errorData.setErrorField("IN_FLT_NUM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getIncomimngFlightDate()) && data.getIncomimngFlightDate().length() > 8) {
			errorData.setErrorDescription("The length of Incoming Flight Date can't be more than 8");
			errorData.setErrorField("IN_FLT_DATE");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getOutgoingFlightCarrier())
				&& data.getOutgoingFlightCarrier().length() > 3) {
			errorData.setErrorDescription("The length of Outgoing Flight Carrier can't be more than 3");
			errorData.setErrorField("OUT_FLT_CAR");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getOutgoingFlightNumber())
				&& data.getOutgoingFlightNumber().length() > 5) {
			errorData.setErrorDescription("The length of Outgoing Flight Number can't be more than 5");
			errorData.setErrorField("OUT_FLT_NUM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getOutgoingFlightDate()) && data.getOutgoingFlightDate().length() > 8) {
			errorData.setErrorDescription("The length of Outgoing Flight Date can't be more than 8");
			errorData.setErrorField("OUT_FLT_DATE");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getOutgoingFlightTime()) && data.getOutgoingFlightTime().length() > 6) {
			errorData.setErrorDescription("The length of Outgoing Flight Time can't be more than 6");
			errorData.setErrorField("OUT_FLT_TIME");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getBupIndicatorForDelivery())
				&& data.getBupIndicatorForDelivery().toString().length() > 1) {
			errorData.setErrorDescription("The length of Bup Indicator For Delivery can't be more than 1");
			errorData.setErrorField("DLV_BUP");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getTransitIndicatorBup())
				&& data.getTransitIndicatorBup().toString().length() > 1) {
			errorData.setErrorDescription("The length of Transit Indicator Bup can't be more than 1");
			errorData.setErrorField("TRA_BUP");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getCltrStat()) && data.getCltrStat().length() > 1) {
			errorData.setErrorDescription("The length of Cltr Status can't be more than 1");
			errorData.setErrorField("CLTR_STAT");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getUldRemarks()) && data.getUldRemarks().length() > 30) {
			errorData.setErrorDescription("The length of Uld Remarks can't be more than 30");
			errorData.setErrorField("ULD_RMKS");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getContainerlocation()) && data.getContainerlocation().length() > 20) {
			errorData.setErrorDescription("The length of Container location can't be more than 20");
			errorData.setErrorField("LOCN");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getGrossWeight()) && data.getGrossWeight().toString().length() > 8) {
			errorData.setErrorDescription("The length of Gross Weight can't be more than 8");
			errorData.setErrorField("GROSS_WGHT");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getOccurrenceDateTime()) && data.getOccurrenceDateTime().length() > 14) {
			errorData.setErrorDescription("The length of Occurrence DateTime can't be more than 14");
			errorData.setErrorField("OCCU_DTTM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getUser()) && data.getUser().length() > 20) {
			errorData.setErrorDescription("The length of User can't be more than 20");
			errorData.setErrorField("USER");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!data.getValidMailBags()) {
			errorData.setErrorDescription(data.getMessageList().get(0).getCode());
			errorData.setErrorField("MAIL_BAG_ID");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!data.getMessageList().isEmpty()) {
			errorData.setErrorDescription(data.getMessageList().get(0).getCode());
			errorData.setErrorField("ULD_NUM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else {
			return null;
		}
	}

	public BaseErrorResponseMessagesMss validateMailBagUldBasedMovement(MailBagUldMovementBaseModel mailBagUldData) {
		ErrorResponseMessagesMss errorData = new ErrorResponseMessagesMss();
		BaseErrorResponseMessagesMss baseErrorResponseMessagesMss = new BaseErrorResponseMessagesMss();
		//
		for (MSSMailBagMovement data : mailBagUldData.getListMailBagUldMovement()) {
			// checkMandatory
			errorData = this.checkMandatoryForMailBagUldBased(data);
			//
			if (StringUtils.isEmpty(errorData.getErrorDescription())) {
				// checkLength
				errorData = this.checkLengthForMailBagUldBased(data);
			}
		}
		if (errorData != null) {
			errorData.setOriginalMessageType(mailBagUldData.getMsgType());
			errorData.setOriginalMessageReferenceNumber(mailBagUldData.getMsgReferenceNum());
			baseErrorResponseMessagesMss.setMsgType("TGFMRESP");
			errorData.setErrorReason("FLD_MISS");
			errorData.setErrorType("FORMAT");
			baseErrorResponseMessagesMss.setMsgSendingndSystem(mailBagUldData.getMsgSendingndSystem());
			baseErrorResponseMessagesMss.setMsgRecipientSystem(mailBagUldData.getMsgRecipientSystem());
			baseErrorResponseMessagesMss.setDataMsgSend(mailBagUldData.getDataMsgSend());
			baseErrorResponseMessagesMss.setMsgReferenceNum(mailBagUldData.getMsgReferenceNum());
			baseErrorResponseMessagesMss.setNumDataElmnt(mailBagUldData.getNumDataElmnt());
			baseErrorResponseMessagesMss.setErrorResponseMessagesMss(errorData);
		}
		//
		return baseErrorResponseMessagesMss;
	}

	private ErrorResponseMessagesMss checkLengthForMailBagUldBased(MSSMailBagMovement data) {
		ErrorResponseMessagesMss errorData = new ErrorResponseMessagesMss();
		//
		if (!StringUtils.isEmpty(data.getTransactionSequenceNumber())
				&& data.getTransactionSequenceNumber().length() > 8) {
			errorData.setErrorDescription("The length of Transaction Sequence Number can't be more than 8");
			errorData.setErrorField("TRA_REF_NUM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getAction()) && data.getAction().length() > 3) {
			errorData.setErrorDescription("The length of  Action can't be more than 3");
			errorData.setErrorField("ACTION");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getMailBagId()) && data.getMailBagId().length() > 29) {
			errorData.setErrorDescription("The length of  MailBag Id can't be more than 29");
			errorData.setErrorField("MAIL_BAG_ID");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getUldType()) && data.getUldType().length() > 3) {
			errorData.setErrorDescription("The length of  Uld Type can't be more than 3");
			errorData.setErrorField("ULD_TYP");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getUldNumber()) && data.getUldNumber().length() > 5) {
			errorData.setErrorDescription("The length of  Uld Number can't be more than 5");
			errorData.setErrorField("ULD_NUM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getUldCarrier()) && data.getUldCarrier().length() > 3) {
			errorData.setErrorDescription("The length of Uld Carrier can't be more than 3");
			errorData.setErrorField("ULD_CAR");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getContainerlocation()) && data.getContainerlocation().length() > 20) {
			errorData.setErrorDescription("The length of Container location can't be more than 20");
			errorData.setErrorField("LOCN");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getOccurrenceDateTime()) && data.getOccurrenceDateTime().length() > 14) {
			errorData.setErrorDescription("The length of Occurrence DateTime can't be more than 14");
			errorData.setErrorField("OCCU_DTTM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else {
			return errorData;
		}
	}

	private ErrorResponseMessagesMss checkMandatoryForMailBagUldBased(MSSMailBagMovement data) {
		ErrorResponseMessagesMss errorData = new ErrorResponseMessagesMss();
		//
		if (StringUtils.isEmpty(data.getTransactionSequenceNumber())) {
			errorData.setErrorDescription("Transaction Sequence Number can't be null");
			errorData.setErrorField("TRA_REF_NUM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getAction())) {
			errorData.setErrorDescription("Action can't be null");
			errorData.setErrorField("ACTION");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getMailBagId())) {
			errorData.setErrorDescription("Mail BagId can't be null");
			errorData.setErrorField("MAIL_BAG_ID");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getUldType())) {
			errorData.setErrorDescription("Uld Type can't be null");
			errorData.setErrorField("ULD_TYP");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getUldNumber())) {
			errorData.setErrorDescription("Uld Number can't be null");
			errorData.setErrorField("ULD_NUM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getUldCarrier())) {
			errorData.setErrorDescription("Uld Carrier can't be null");
			errorData.setErrorField("ULD_CAR");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getContainerlocation())) {
			errorData.setErrorDescription("Container location can't be null");
			errorData.setErrorField("LOCN");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getOccurrenceDateTime())) {
			errorData.setErrorDescription("Occurrence DateTime can't be null");
			errorData.setErrorField("OCCU_DTTM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else {
			return errorData;
		}
	}

	public BaseErrorResponseMessagesMss validateMailbagMovement(MailBagUldMovementBaseModel mailBagUldData) {
		ErrorResponseMessagesMss errorData = new ErrorResponseMessagesMss();
		BaseErrorResponseMessagesMss baseErrorResponseMessagesMss = new BaseErrorResponseMessagesMss();
		//
		for (MSSMailBagMovement data : mailBagUldData.getListMailBagUldMovement()) {
			// checkMandatory
			errorData = this.checkMandatoryForMailbag(data);
			//
			if (StringUtils.isEmpty(errorData.getErrorDescription())) {
				// checkLength
				// errorData = this.checkLengthForMailbag(data);
			}
		}
		if (errorData != null) {
			errorData.setOriginalMessageType(mailBagUldData.getMsgType());
			errorData.setOriginalMessageReferenceNumber(mailBagUldData.getMsgReferenceNum());
			baseErrorResponseMessagesMss.setMsgType("TGFMRESP");
			errorData.setErrorReason("FLD_MISS");
			errorData.setErrorType("FORMAT");
			baseErrorResponseMessagesMss.setMsgSendingndSystem(mailBagUldData.getMsgSendingndSystem());
			baseErrorResponseMessagesMss.setMsgRecipientSystem(mailBagUldData.getMsgRecipientSystem());
			baseErrorResponseMessagesMss.setDataMsgSend(mailBagUldData.getDataMsgSend());
			baseErrorResponseMessagesMss.setMsgReferenceNum(mailBagUldData.getMsgReferenceNum());
			baseErrorResponseMessagesMss.setNumDataElmnt(mailBagUldData.getNumDataElmnt());
			baseErrorResponseMessagesMss.setErrorResponseMessagesMss(errorData);
		}
		//
		return baseErrorResponseMessagesMss;
	}

	private ErrorResponseMessagesMss checkLengthForMailbag(MSSMailBagMovement data) {
		ErrorResponseMessagesMss errorData = new ErrorResponseMessagesMss();
		//
		if (!StringUtils.isEmpty(data.getTransactionSequenceNumber())
				&& data.getTransactionSequenceNumber().length() > 8) {
			errorData.setErrorDescription("The length of Transaction Sequence Number can't be more than 8");
			errorData.setErrorField("TRA_REF_NUM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getAction()) && data.getAction().length() > 3) {
			errorData.setErrorDescription("The length of  Action can't be more than 3");
			errorData.setErrorField("ACTION");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getMailBagId()) && data.getMailBagId().length() > 29) {
			errorData.setErrorDescription("The length of  MailBag Id can't be more than 29");
			errorData.setErrorField("MAIL_BAG_ID");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getUldType()) && data.getUldType().length() > 3) {
			errorData.setErrorDescription("The length of  Uld Type can't be more than 3");
			errorData.setErrorField("ULD_TYP");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getUldNumber()) && data.getUldNumber().length() > 5) {
			errorData.setErrorDescription("The length of  Uld Number can't be more than 5");
			errorData.setErrorField("ULD_NUM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getUldCarrier()) && data.getUldCarrier().length() > 3) {
			errorData.setErrorDescription("The length of Uld Carrier can't be more than 3");
			errorData.setErrorField("ULD_CAR");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getContainerlocation()) && data.getContainerlocation().length() > 20) {
			errorData.setErrorDescription("The length of Container location can't be more than 20");
			errorData.setErrorField("LOCN");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (!StringUtils.isEmpty(data.getOccurrenceDateTime()) && data.getOccurrenceDateTime().length() > 14) {
			errorData.setErrorDescription("The length of Occurrence DateTime can't be more than 14");
			errorData.setErrorField("OCCU_DTTM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else {
			return errorData;
		}
	}

	private ErrorResponseMessagesMss checkMandatoryForMailbag(MSSMailBagMovement data) {
		ErrorResponseMessagesMss errorData = new ErrorResponseMessagesMss();
		//
		if (StringUtils.isEmpty(data.getTransactionSequenceNumber())) {
			errorData.setErrorDescription("Transaction Sequence Number can't be null");
			errorData.setErrorField("TRA_REF_NUM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getAction())) {
			errorData.setErrorDescription("Action can't be null");
			errorData.setErrorField("ACTION");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getAcceptanceDateTime())) {
			errorData.setErrorDescription("Acceptance DateTime can't be null");
			errorData.setErrorField("ACC_DTTM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getXRayStatus())) {
			errorData.setErrorDescription("xRay Status can't be null");
			errorData.setErrorField("XRAYRSLT");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getDamageStatus())) {
			errorData.setErrorDescription("Damage Status can't be null");
			errorData.setErrorField("DAMSTAT");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getOverSizeBagStatus())) {
			errorData.setErrorDescription("Over Size BagStatus can't be null");
			errorData.setErrorField("OOSSTAT");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getTornStatus())) {
			errorData.setErrorDescription("Torn Status can't be null");
			errorData.setErrorField("tornStatus");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getWetStatus())) {
			errorData.setErrorDescription("Wet Status can't be null");
			errorData.setErrorField("WETSTAT");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getSealStatus())) {
			errorData.setErrorDescription("Seal Status can't be null");
			errorData.setErrorField("SEALSTAT");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getPlunStatuts())) {
			errorData.setErrorDescription("Plun Statuts can't be null");
			errorData.setErrorField("plunStatuts");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getMailBagWeight())) {
			errorData.setErrorDescription("Mail BagWeight can't be null");
			errorData.setErrorField("MBAG_WGHT");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getAgent())) {
			errorData.setErrorDescription("Agent can't be null");
			errorData.setErrorField("AGNT");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		}
		/*
		 * else if (StringUtils.isEmpty(data.getOutgoingFlightCarrier())) {
		 * errorData.setErrorDescription("Outgoing Flight Carrier can't be null");
		 * errorData.setErrorField("OUT_FLT_CAR");
		 * errorData.setOriginalTransactionSequenceNumber(data.
		 * getTransactionSequenceNumber()); return errorData; }
		 */
		/*else if (!StringUtils.isEmpty(data.getOutgoingCarrier())
				&& StringUtils.isEmpty(data.getOutgoingFlightNumber())) {
			errorData.setErrorDescription("Outgoing Flight Number can't be null");
			errorData.setErrorField("OUT_FLT_NUM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} */
		else if (!StringUtils.isEmpty(data.getOutgoingCarrier())
				&& !StringUtils.isEmpty(data.getOutgoingFlightNumber())
				&& StringUtils.isEmpty(data.getOutgoingFlightDate())) {
			errorData.setErrorDescription("Outgoing Flight Date can't be null");
			errorData.setErrorField("OUT_FLT_DATE");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getMailBagNewDestination())) {
			errorData.setErrorDescription("MailBag New Destination can't be null");
			errorData.setErrorField("MBAG_NEW_DES");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} 
		/*else if (StringUtils.isEmpty(data.getWeighingDateTime())) {
			errorData.setErrorDescription("Weighing DateTime can't be null");
			errorData.setErrorField("WGHT_DTTM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} */
		/*else if (StringUtils.isEmpty(data.getSortDateTime())) {
			errorData.setErrorDescription("Sort DateTime can't be null");
			errorData.setErrorField("SORT_DTTM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		}*/ 
		else if (StringUtils.isEmpty(data.getDispatchNo())) {
			errorData.setErrorDescription("Dispatch No Carrier can't be null");
			errorData.setErrorField("NO_DISPATCH");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getPiecesNo())) {
			errorData.setErrorDescription("Pieces No can't be null");
			errorData.setErrorField("NO_PIECES");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getOccurrenceDateTime())) {
			errorData.setErrorDescription("Occurrence DateTime can't be null");
			errorData.setErrorField("OCCU_DTTM");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getUser())) {
			errorData.setErrorDescription("User can't be null");
			errorData.setErrorField("USER");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getRejectIndicator())) {
			errorData.setErrorDescription("RejectIndicator can't be null");
			errorData.setErrorField("REJECTIND");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getOffloadIndicator())) {
			errorData.setErrorDescription("Offload Indicator can't be null");
			errorData.setErrorField("OFFLIND");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else if (StringUtils.isEmpty(data.getOffloadCount())) {
			errorData.setErrorDescription("Offload Count can't be null");
			errorData.setErrorField("OFFLCNT");
			errorData.setOriginalTransactionSequenceNumber(data.getTransactionSequenceNumber());
			return errorData;
		} else {
			return errorData;
		}
	}
}