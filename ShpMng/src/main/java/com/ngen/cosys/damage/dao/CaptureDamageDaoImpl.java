package com.ngen.cosys.damage.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.damage.enums.FlagCRUD;
import com.ngen.cosys.damage.model.CaptureDamageDetails;
import com.ngen.cosys.damage.model.CaptureDamageModel;
import com.ngen.cosys.damage.model.CaptureDamageNatureModel;
import com.ngen.cosys.damage.model.FileUpload;
import com.ngen.cosys.damage.model.FileUploadModel;
import com.ngen.cosys.damage.model.MailingDetails;
import com.ngen.cosys.damage.model.SearchDamageDetails;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;


@Repository("CaptureDamageDAO")
public class CaptureDamageDaoImpl extends BaseDAO implements CaptureDamageDAO {

   @Qualifier("sqlSessionTemplate")
   @Autowired
   private SqlSession sqlSessionConfiguration;

   @Override
   //@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
   public CaptureDamageModel save(CaptureDamageModel captureDamageModel) throws CustomException {
	   
	   
	   if (captureDamageModel.getEntityType() == "AWB") {
		   
			// check for awb number is existing in shipment_master
		         Integer count = super.fetchObject("sqlAwbchechshp", captureDamageModel, sqlSessionConfiguration);
		         if (count == 0) {
		            throw new CustomException("awb.shipmentMaster", "entityKey", ErrorType.ERROR);
		         }
	   }
	   
	   // To check if handled by house
	   if(!ObjectUtils.isEmpty(captureDamageModel.getIsHandleByHouse()) && captureDamageModel.getIsHandleByHouse()) {
		   // to check if subEntityKey is null or not
		   if(captureDamageModel.getSubEntityKey()==null) {
			   throw new CustomException("hawb.mandatory", "subEntityKey", ErrorType.ERROR);
		   }
		   
		// fetching pieces for particular hawb number
		   BigInteger totalPiecesWaight = super.fetchObject("sqlFetchHawbPiecesshp", captureDamageModel,
		            sqlSessionConfiguration);
		   if(!ObjectUtils.isEmpty(totalPiecesWaight)) {
		   
		      // total damage pieces can not be more than hawb's pieces
		         if (captureDamageModel.getDamagePieces().longValue() > totalPiecesWaight.longValue()) {
		            throw new CustomException("AWB.PIEECES", "captureDamageForm", ErrorType.ERROR);
		         }
		   }
			   
	   }

	   else {
      // fetching pieces for particular awb number
      BigInteger totalPiecesWaight = super.fetchObject("sqlFetchPiecesshp", captureDamageModel,
            sqlSessionConfiguration);
      if(!ObjectUtils.isEmpty(totalPiecesWaight)) {
         // total damage pieces can not be more than awb's pieces
         if (captureDamageModel.getDamagePieces().longValue() > totalPiecesWaight.longValue()) {
            throw new CustomException("AWB.PIEECES", "captureDamageForm", ErrorType.ERROR);
         }
      }
		   
	   }
	
      if (captureDamageModel.getFlight() != null && captureDamageModel.getFlightDate() != null) {
         BigInteger flightId = super.fetchObject("sqlFetchflightIdshp", captureDamageModel, sqlSessionConfiguration);
         captureDamageModel.setFlightId(flightId);
      }

      Integer damageid = super.fetchObject("selectidofdamageinfoshp", captureDamageModel, sqlSessionConfiguration);

      if (damageid == null) {
    	  insertCargoDamageInfo(captureDamageModel);
        // super.insertData("sqlInsertDamageshp", captureDamageModel, sqlSessionConfiguration);
      } else {
         captureDamageModel.setDamageInfoId(BigInteger.valueOf(damageid));
         updateCargoDamageInfo(captureDamageModel);
       //  super.updateData("sqlUpdateDamageshp", captureDamageModel, sqlSessionConfiguration);
      }

      // inserting data into Com_DamageLineItems
      for (CaptureDamageDetails i : captureDamageModel.getCaptureDetails()) {
         i.setDamageInfoId(captureDamageModel.getDamageInfoId());
         String str = i.getListNatureOfDamage().toString();
         i.setNatureOfDamage(str);
         if (i.getFlagCRUD().equalsIgnoreCase("C")) {
            super.insertData("sqlInsertDamageDetailsshp", i, sqlSessionConfiguration);
         } else if (i.getFlagCRUD().equalsIgnoreCase("U")) {
            super.updateData("sqlUpdateDamageDetailsshp", i, sqlSessionConfiguration);
         }
      }

      // inserting data in Com_DamageLineItemsConditions
      for (CaptureDamageDetails t1 : captureDamageModel.getCaptureDetails()) {
         for (int nm = 0; nm < t1.getListNatureOfDamage().size(); nm++) {
            CaptureDamageNatureModel obj = new CaptureDamageNatureModel();
            obj.setDamageLineItemsId(t1.getDamageLineItemsId());
            obj.setNatureOfDamage(t1.getListNatureOfDamage().get(nm));
            if (t1.getFlagCRUD().equalsIgnoreCase("C") && obj.getDamageLineItemsConditionsId() == null) {
               super.insertData("sqlInsertnatureofdamageshp", obj, sqlSessionConfiguration);
            } else {
               super.updateData("sqlUpdatenatureshp", obj, sqlSessionConfiguration);
            }
         }
      }
	   
      return captureDamageModel;

   }

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
	public void updateCargoDamageInfo(CaptureDamageModel captureDamageModel) throws CustomException {
		if(!ObjectUtils.isEmpty(captureDamageModel.getIsHandleByHouse()) && captureDamageModel.getIsHandleByHouse()) {
			super.updateData("sqlUpdateHawbDamageshp", captureDamageModel, sqlSessionConfiguration);	
		}
		else {
			super.updateData("sqlUpdateDamageshp", captureDamageModel, sqlSessionConfiguration);
		}
}
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
	public void insertCargoDamageInfo(CaptureDamageModel captureDamageModel) throws CustomException {
		if(!ObjectUtils.isEmpty(captureDamageModel.getIsHandleByHouse()) && captureDamageModel.getIsHandleByHouse()) {						super.insertData("sqlInsertHawbDamageshp", captureDamageModel, sqlSessionConfiguration);
		}
		else
		{
			super.insertData("sqlInsertDamageshp", captureDamageModel, sqlSessionConfiguration);
		}
		
		
		
	}

// update Damage info
   @Override
  public void delupdCaptureDamageConditions(CaptureDamageModel captureDamageModel) throws CustomException {
      for (CaptureDamageDetails b : captureDamageModel.getCaptureDetails()) {
    	  super.deleteData("sqldeletenatureofdamageshp", b, sqlSessionConfiguration);
      }
      updateCaptureDmgCondition(captureDamageModel);
   }
   
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
   public void updateCaptureDmgCondition(CaptureDamageModel captureDamageModel) throws CustomException {
	      for (CaptureDamageDetails b : captureDamageModel.getCaptureDetails()) {
	    	
         for (String c : b.getListNatureOfDamage()) {
            CaptureDamageNatureModel model = new CaptureDamageNatureModel();
            model.setDamageLineItemsId(b.getDamageLineItemsId());
            model.setNatureOfDamage(c);
            super.insertData("sqlInsertnatureofdamageshp", model, sqlSessionConfiguration);
         }
      }
   }

   // delete damage details
   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
   public void delete(CaptureDamageDetails captureDamageDetails) throws CustomException {
      super.deleteData("sqldeletenatureofdamageshp", captureDamageDetails, sqlSessionConfiguration);
      super.deleteData("sqldeletedamagedetailsshp", captureDamageDetails, sqlSessionConfiguration);
      super.deleteData("sqldeletenatureofdamageshp", captureDamageDetails, sqlSessionConfiguration);
   }

   // fetch damage
   @Override
   public CaptureDamageModel fetch(SearchDamageDetails captureDamageModel) throws CustomException {
      CaptureDamageModel responseModel = null;
      if (captureDamageModel.getEntityType().equalsIgnoreCase("AWB")  ) {
         responseModel = super.fetchObject("sqlAwbchechshp", captureDamageModel, sqlSessionConfiguration);
         if (ObjectUtils.isEmpty(responseModel)) {
            throw new CustomException("awb.shipmentMaster", "entityKey", ErrorType.ERROR);
         }

         // Set entity key and type
         responseModel.setEntityKey(captureDamageModel.getEntityKey());
         responseModel.setEntityType(captureDamageModel.getEntityType());

         // If the shipment an import then fetch the first flight only in case of total
         // shipment
         if (!ObjectUtils.isEmpty(responseModel)
               && !MultiTenantUtility.isTenantCityOrAirport(responseModel.getOrigin())
               &&( (StringUtils.isEmpty(captureDamageModel.getFlight())
               && StringUtils.isEmpty(captureDamageModel.getFlightDate()))
               || (!StringUtils.isEmpty(captureDamageModel.getFlight()) && !StringUtils.isEmpty(captureDamageModel.getFlightDate()) && StringUtils.isEmpty(captureDamageModel.getFlightSegmentId())))) {
            List<CaptureDamageModel> incomingFlightInfo = this.fetchList("sqlGetFirstIncomingFlightInfo", responseModel,
                  sqlSessionConfiguration);

            if (!CollectionUtils.isEmpty(incomingFlightInfo) && incomingFlightInfo.size() == 1) {
               // Get the first flight info
               captureDamageModel.setFlight(incomingFlightInfo.get(0).getFlight());
               captureDamageModel.setFlightDate(incomingFlightInfo.get(0).getFlightDate());

               // Set the flight info
               responseModel.setFlight(incomingFlightInfo.get(0).getFlight());
               responseModel.setFlightDate(incomingFlightInfo.get(0).getFlightDate());
               responseModel.setFlightId(incomingFlightInfo.get(0).getFlightId());
               responseModel.setFlightSegmentId(incomingFlightInfo.get(0).getFlightSegmentId());
            }
         } else {
            responseModel.setFlight(captureDamageModel.getFlight());
            responseModel.setFlightDate(captureDamageModel.getFlightDate());
            responseModel.setFlightSegmentId(captureDamageModel.getFlightSegmentId());
         }
      }
      
      //to check if handled by house
      if(!ObjectUtils.isEmpty(captureDamageModel.getIsHandleByHouse()) && captureDamageModel.getIsHandleByHouse()) {
    	// to check if subEntityKey is null or not
    	  if(captureDamageModel.getSubEntityKey()==null) {
			   throw new CustomException("hawb.mandatory", "subEntityKey", ErrorType.ERROR);
		   }
    	  // Fetch the first damage info for respective AWB and HAWB number if staff does not provides flight
          List<CaptureDamageModel> damageList = this.fetchList("sqlfetchAllHawbRecordshp", captureDamageModel,
                sqlSessionConfiguration);
          if (!CollectionUtils.isEmpty(damageList)) {
        	  if(!ObjectUtils.isEmpty(responseModel) && 
        			  !StringUtils.isEmpty(responseModel.getOrigin())) {
        	    damageList.get(0).setOrigin(responseModel.getOrigin());
        	  }
             return damageList.get(0);
          } else if (!ObjectUtils.isEmpty(responseModel)) {
             return responseModel;
          }
    	  
      }      
      else {
    	  // Fetch the first damage info for respective AWB number if staff does not provides flight
          List<CaptureDamageModel> damageList = this.fetchList("sqlfetchAllRecordshp", captureDamageModel,
                sqlSessionConfiguration);
          if (!CollectionUtils.isEmpty(damageList)) {
        	  if(!ObjectUtils.isEmpty(responseModel) && 
        			  !StringUtils.isEmpty(responseModel.getOrigin())) {
        	    damageList.get(0).setOrigin(responseModel.getOrigin());
        	  }
             return damageList.get(0);
          } else if (!ObjectUtils.isEmpty(responseModel)) {
             return responseModel;
          }
    	  
      }    
      return null;
   
   }

   @Override
   public boolean isTokenExist(String fileToken) throws CustomException {
      // Check Exist
      Integer count = sqlSessionConfiguration.selectOne(SQL_FILE_TOKEN_EXIST, fileToken);
      return count > 0;
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
   public void delete(FileUpload uploadFile) throws CustomException {
      // Delete the file if exist with the same fileToken
      sqlSessionConfiguration.delete(SQL_DELETE_FILE, uploadFile);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
   public void store(FileUpload uploadFile) throws CustomException {	  
      // Insert the file detail
      sqlSessionConfiguration.insert(SQL_INSERT_FILE, uploadFile);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
   public void storeFiles(List<FileUpload> uploadFiles) throws CustomException {
      // Insert multiple files detail
      super.insertData(SQL_INSERT_FILE, uploadFiles, sqlSessionConfiguration);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
   public void storeListOfFiles(FileUploadModel uploadModel) throws CustomException {
      // Insert multiple file on FLAGCRUD basis
      for (FileUpload fileUpload : uploadModel.getFilesList()) {
         switch (fileUpload.getFlagCRUD()) {
         case FlagCRUD.Type.CREATE:
            super.insertData(SQL_INSERT_FILE, fileUpload, sqlSessionConfiguration);
            break;
         case FlagCRUD.Type.UPDATE:
            super.updateData(SQL_UPDATE_FILE, fileUpload, sqlSessionConfiguration);
            break;
         case FlagCRUD.Type.DELETE:
            super.deleteData(SQL_DELETE_FILE_ONE, fileUpload, sqlSessionConfiguration);
            break;
         }
      }
   }

   @Override
   public FileUpload load(FileUpload uploadFile) throws CustomException {
      // load file deatil
      return fetchObject("selectFileDetailForDownload", uploadFile, sqlSessionConfiguration);
   }

   @Override
   public List<FileUpload> loadFiles(FileUpload uploadFile) throws CustomException {
      // load file deatil
      return fetchList(SQL_SELECT_FILE, uploadFile, sqlSessionConfiguration);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
   public FileUpload deleteFile(FileUpload deleteFile) throws CustomException {
      // Delete the file
      sqlSessionConfiguration.delete(SQL_DELETE_FILE, deleteFile.getReferenceId());
      deleteFile.setDeleted(true);
      return deleteFile;
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
   public FileUpload deleteAFile(FileUpload deleteFile) throws CustomException {
	   // Delete the file
      sqlSessionConfiguration.delete(SQL_DELETE_FILE_ONE, deleteFile);
      deleteFile.setDeleted(true);
      return deleteFile;
   }

   @Override
   public List<FileUpload> loadListOfFiles(FileUpload fileUpload) throws CustomException {
      return super.fetchList(SQL_SELECT_FILE_FOR_EMAIL, fileUpload, sqlSessionConfiguration);
   }

   @Override
   public List<CaptureDamageModel> fetchManifestFlightDetails(SearchDamageDetails captureDamageModel) throws CustomException {
      return fetchList("getManifestFlightDetails", captureDamageModel, sqlSessionConfiguration);
   }
   
   @Override
   public List<CaptureDamageModel> fetchManifestFlightDetailsMobile(SearchDamageDetails captureDamageModel) throws CustomException {
      return fetchList("fetchManifestFlightDetailsMobile", captureDamageModel, sqlSessionConfiguration);
   }

   @Override
   public String getCarrierCodeForAMailBag(String mailBag) throws CustomException {
      return fetchObject("getcarrierCodeForAMailBag", mailBag, sqlSessionConfiguration);
   }

@Override
public String getRegistrationNumber() throws CustomException {
	// TODO Auto-generated method stub
	 return fetchObject("getRegistraion", null, sqlSessionConfiguration);
}


	@Override
	public List<CaptureDamageDetails> getDocumentRemarks(MailingDetails fileUploadModel) throws CustomException{
		return fetchList("getDocumentRemarks",fileUploadModel,sqlSessionConfiguration);
	}

   @Override
   public String getDamageCodeForMail(String natureOfDamage) throws CustomException {
      // TODO Auto-generated method stub
      return fetchObject("getDamageCodeForMail", natureOfDamage, sqlSessionConfiguration);
   }
   
   /*//to check handle by house
   @Override
	public SearchDamageDetails isHandledByHouse(SearchDamageDetails searchDamageDetails) throws CustomException {
		return fetchObject("checkHandledByHouse", searchDamageDetails, sqlSessionConfiguration);
	}*/
}