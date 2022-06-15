package com.ngen.cosys.satssginterfaces.mss.service;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.MSSMailBagMovement;
import com.ngen.cosys.satssginterfaces.mss.model.MssMessageParentModel;
import com.ngen.cosys.satssginterfaces.mss.model.PreannouncementUldMessagesModel;
import com.ngen.cosys.satssginterfaces.mss.model.RequestPreannouncementUldMessagesModel;
import com.ngen.cosys.satssginterfaces.mss.model.ResponseErrorMessagesHeaderMss;
import com.ngen.cosys.satssginterfaces.mss.model.ResponseMssMailBagMovement;

public interface MssService {

	/**
	 * This method helps to fetch necessary data from Database and create object
	 * structure for MSS TGFMCPRE message.
	 * 
	 * Below is one sample message. { "MSG_TYPE": "TGFMCPRE", "MSG_REF_NUM":
	 * 1339290, "MSG_SND_SYS": "RFDT", "MSG_RCP_SYS": "MSS", "NUM_DATA_ELMNT": 2,
	 * "DAT_MSG_SND": "20180424092100", "DATA": [ { "TRA_REF_NUM": "00000001",
	 * "ACTION": "UPD", "ULD_TYP": "AKE", "ULD_NUM": "29072", "ULD_CAR": "KE",
	 * "ULD_DES": "SEL", "IN_FLT_CAR": "KE", "IN_FLT_NUM": "0647A", "IN_FLT_DATE":
	 * "20171130", "OUT_FLT_CAR": "", "OUT_FLT_NUM": "", "OUT_FLT_DATE": "",
	 * "DLV_BUP": "", "TRA_BUP": "Y", "ST_IND": "", "ULD_RMKS": "" }, {
	 * "TRA_REF_NUM": "00000002", "ACTION": "ADD", "ULD_TYP": "AKE", "ULD_NUM":
	 * "45621", "ULD_CAR": "SQ", "ULD_DES": "FRA", "IN_FLT_CAR": "SQ", "IN_FLT_NUM":
	 * "0807", "IN_FLT_DATE": "20180318", "OUT_FLT_CAR": "", "OUT_FLT_NUM": "",
	 * "OUT_FLT_DATE": "", "DLV_BUP": "", "TRA_BUP": "Y", "ST_IND": "", "ULD_RMKS":
	 * "" } ] }
	 * 
	 * @param model
	 *            - Search Criteria, not null.
	 * @return instance of MssMessageParentModel.
	 * @throws instance
	 *             of CustomException.
	 */
	MssMessageParentModel<List<PreannouncementUldMessagesModel>> preannouncementUldMessage(
			RequestPreannouncementUldMessagesModel model) throws CustomException;

	List<ResponseErrorMessagesHeaderMss> responseHeader() throws CustomException;

	Integer updateErrorResponseMessageinOutgoingMessage(BigInteger id) throws CustomException;

	RequestPreannouncementUldMessagesModel selectFlightKey(BigInteger flightId) throws CustomException;

	MSSMailBagMovement messageTypeTGFMMBSI(MSSMailBagMovement requestModel) throws CustomException;

	void messageTypeTGFMMBUL(MSSMailBagMovement requestModel) throws CustomException;

	void messageTypeTGFMCULD(MSSMailBagMovement requestModel) throws CustomException;

	void insertXRAYData(MSSMailBagMovement requestModel, ResponseMssMailBagMovement result) throws CustomException;

	void updateStoreLocationOfShipment(MSSMailBagMovement requestModel, ResponseMssMailBagMovement result)
			throws CustomException;

	void acceptmailbag(MSSMailBagMovement requestModel) throws CustomException;

	void breakDown(MSSMailBagMovement requestModel) throws CustomException;

	void updateLyingList(MSSMailBagMovement requestModel) throws CustomException;

	void unload(MSSMailBagMovement requestModel, BigInteger segmentId) throws CustomException;

	void produceAirmailStatusEvents(ResponseMssMailBagMovement result, MSSMailBagMovement requestModel);

	void produceAirmailStatusEventAfterAcceptance(MSSMailBagMovement requestModel) throws CustomException;

	void bookShipmentInfo(MSSMailBagMovement requestModel) throws CustomException;

	void load(MSSMailBagMovement requestModel, BigInteger segmentId) throws CustomException;

	void produceAirmailStatusEventAfterLoad(MSSMailBagMovement requestModel);

	MSSMailBagMovement messageTypeForTGFMMBUL(MSSMailBagMovement requestModel) throws CustomException;

    boolean checkMssIncomingIsActiveOrNot() throws CustomException;;
}