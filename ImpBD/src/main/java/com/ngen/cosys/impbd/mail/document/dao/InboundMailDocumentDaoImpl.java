package com.ngen.cosys.impbd.mail.document.dao;

import java.time.LocalDate;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.mail.document.model.InboundMailDocumentModel;
import com.ngen.cosys.impbd.mail.document.model.InboundMailDocumentShipmentModel;

@Repository
public class InboundMailDocumentDaoImpl extends BaseDAO implements InboundMailDocumentDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sessionTemplate;

	@Autowired
    private ShipmentProcessorService shipmentProcessorService;

	@Override
	public InboundMailDocumentModel flightdata(InboundMailDocumentModel requestModel) throws CustomException {
		return super.fetchObject("flightdetail", requestModel, sessionTemplate);
	}

	@Override
	public List<InboundMailDocumentShipmentModel> get(InboundMailDocumentModel requestModel) throws CustomException {
		return this.fetchList("sqlInboundMailDocumentData", requestModel, sessionTemplate);
	}
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.IMPORT_MAIL_CAPTURE_DOC)
	public void update(InboundMailDocumentShipmentModel requestModel) throws CustomException {
		//super.updateData("updatemail", requestModel, sessionTemplate);
            if(StringUtils.isEmpty(requestModel.getRemarks())) {
            	deleteData("deleteShipmentRemark", requestModel, sessionTemplate);
            } else {
            	if (super.updateData("updateremarks", requestModel, sessionTemplate) == 0) {
    				LocalDate shipmentDate = shipmentProcessorService.getShipmentDate(requestModel.getShipmentNumber());
    				requestModel.setShipmentdate(shipmentDate);
    				insertData("insertRemarksForDocument", requestModel, sessionTemplate);
    			}
            }
			
	}

	@Override
	public void updateRemarks(List<InboundMailDocumentShipmentModel> requestModel) throws CustomException {
		super.updateData("updateremarks", requestModel, sessionTemplate);
	}
	//@NgenAuditAction(entityType = NgenAuditEntityType.AIRMAIL_BREAKDOWN, eventName = NgenAuditEventType.AIRMAIL_IMPORT)
	@Override
	public void deleteShipmentVerificetion(List<InboundMailDocumentShipmentModel> requestModel) throws CustomException {
		super.deleteData("deleteShipmentverification", requestModel, sessionTemplate);
	}
	//@NgenAuditAction(entityType = NgenAuditEntityType.AIRMAIL_BREAKDOWN, eventName = NgenAuditEventType.AIRMAIL_IMPORT)
	@Override
	public void deleteShipmentOCI(List<InboundMailDocumentShipmentModel> requestModel) throws CustomException {
		super.deleteData("deleteShipmentOtherChargeInfo", requestModel, sessionTemplate);
	}
	//@NgenAuditAction(entityType = NgenAuditEntityType.AIRMAIL_BREAKDOWN, eventName = NgenAuditEventType.AIRMAIL_IMPORT)
	@Override
	public void deleteShipmentMasterHA(List<InboundMailDocumentShipmentModel> requestModel) throws CustomException {
		super.deleteData("deleteShipmentMasterHandlingArea", requestModel, sessionTemplate);

	}
	@NgenAuditAction(entityType = NgenAuditEntityType.AIRMAIL_BREAKDOWN, eventName = NgenAuditEventType.AIRMAIL_IMPORT)
	@Override
	public void deleteShipmentRemark(List<InboundMailDocumentShipmentModel> requestModel) throws CustomException {
		super.deleteData("deleteShipmentRemark", requestModel, sessionTemplate);
	}

	@Override
	public void deleteShipmentMaster(List<InboundMailDocumentShipmentModel> requestModel) throws CustomException {
		super.deleteData("deleteShipmentMaster", requestModel, sessionTemplate);
	}

	@Override
	public Integer checkShipmentId(InboundMailDocumentShipmentModel requestModel) throws CustomException {
		return super.fetchObject("checkshipmentId", requestModel, sessionTemplate);
	}

	@Override
	public Integer checkShipmentVerificationId(InboundMailDocumentShipmentModel requestModel) throws CustomException {
		return super.fetchObject("checkShipmentVerificationId", requestModel, sessionTemplate);
	}

	@Override
	public Integer checkOriginOfficeExchange(InboundMailDocumentShipmentModel requestModel) throws CustomException {
		return super.fetchObject("checkoriginofficeexchange", requestModel, sessionTemplate);
	}

	@Override
	public Integer checkDestinationOfficeExchange(InboundMailDocumentShipmentModel requestModel)
			throws CustomException {
		return super.fetchObject("checkdestinationofficeexchange", requestModel, sessionTemplate);
	}

	@Override
	public Integer checkShipmentNumber(InboundMailDocumentShipmentModel requestModel) throws CustomException {
		return super.fetchObject("checkshipmentnumber", requestModel, sessionTemplate);
	}
	
	@Override
	public void updateShpMst(InboundMailDocumentShipmentModel updateShipmentData) throws CustomException {
	   InboundMailDocumentShipmentModel totalPiecesAndWeightInfo = this.getTotalPiecesAndWeightFromVerification(updateShipmentData);
	   if(!ObjectUtils.isEmpty(totalPiecesAndWeightInfo)) {
	      updateShipmentData.setTotalPieces(totalPiecesAndWeightInfo.getTotalPieces());
	      updateShipmentData.setTotalWeight(totalPiecesAndWeightInfo.getTotalWeight());
	   }
	   updateData("updatemail", updateShipmentData, sessionTemplate);

	}
	
	@Override
    public InboundMailDocumentShipmentModel getTotalPiecesAndWeightFromVerification(InboundMailDocumentShipmentModel updateShipmentData) throws CustomException {
       return fetchObject("sqlGetTotalPiecesAndWeightFromVerification", updateShipmentData, sessionTemplate);
    }

   @Override
   public void updateShpVer(InboundMailDocumentShipmentModel requestModel) throws CustomException {
        updateData("sqlUpdateDocumentInfoForMail", requestModel, sessionTemplate);
   }
}