package com.ngen.cosys.damage.dao;

import java.util.List;
import com.ngen.cosys.damage.model.CaptureDamageDetails;
import com.ngen.cosys.damage.model.CaptureDamageModel;
import com.ngen.cosys.damage.model.FileUpload;
import com.ngen.cosys.damage.model.FileUploadModel;
import com.ngen.cosys.damage.model.MailingDetails;
import com.ngen.cosys.damage.model.SearchDamageDetails;
import com.ngen.cosys.framework.exception.CustomException;
//import com.ngen.cosys.shipment.model.ShipmentMaster;

public interface CaptureDamageDAO {

   String SQL_FILE_TOKEN_EXIST = "sqlcheckFileTokenExist";
   String SQL_DELETE_FILE = "sqldeleteFileDetail";
   String SQL_INSERT_FILE = "sqlinsertFileDetail";
   String SQL_SELECT_FILE = "sqlselectFileDetail";
   String SQL_UPDATE_FILE = "sqlupdateFileDetail";
   String SQL_DELETE_FILE_ONE = "sqldeleteFileDetailOne";
   String SQL_SELECT_FILE_FOR_EMAIL = "sqlselectFileDetailForEmail";

   /**
    * save damage
    * 
    * @param captureDamageModel
    * @throws CustomException
    */
   CaptureDamageModel save(CaptureDamageModel captureDamageModel) throws CustomException;

   /**
    * update damage
    * 
    * @param captureDamageModel
    * @throws CustomException
    */
   void delupdCaptureDamageConditions(CaptureDamageModel captureDamageModel) throws CustomException;

   /**
    * delete damage details
    * 
    * @param captureDamageModel
    * @throws CustomException
    */
   void delete(CaptureDamageDetails captureDamageDetails) throws CustomException;

   /**
    * fetch damage
    * 
    * @param captureDamageModel
    * @throws CustomException
    */
   CaptureDamageModel fetch(SearchDamageDetails captureDamageModel) throws CustomException;

   /**
    * Check File generated taken is already exist
    * 
    * @param tokenId
    * @return
    * @throws CustomException
    */
   boolean isTokenExist(String fileToken) throws CustomException;

   /**
    * 
    * @throws CustomException
    */
   void delete(FileUpload uploadFile) throws CustomException;

   /**
    * Upload a single file
    * 
    * @param uploadFile
    * @throws CustomException
    */
   void store(FileUpload uploadFile) throws CustomException;

   /**
    * Upload list of files
    * 
    * @param uploadFiles
    * @throws CustomException
    */
   void storeFiles(List<FileUpload> uploadFiles) throws CustomException;

   /**
    * @param uploadModel
    * @throws CustomException
    */
   void storeListOfFiles(FileUploadModel uploadModel) throws CustomException;

   /**
    * Load file for display
    * 
    * @param uploadFile
    * @throws CustomException
    */
   FileUpload load(FileUpload uploadFile) throws CustomException;

   /**
    * Load list of files
    * 
    * @param uploadFile
    * @throws CustomException
    */
   List<FileUpload> loadFiles(FileUpload file) throws CustomException;

   /**
    * @param deleteFile
    * @return
    * @throws CustomException
    */
   FileUpload deleteFile(FileUpload deleteFile) throws CustomException;

   /**
    * @param deleteFile
    * @return
    * @throws CustomException
    */
   FileUpload deleteAFile(FileUpload deleteFile) throws CustomException;

   /**
    * @param fileUpload
    * @return
    * @throws CustomException
    */
   List<FileUpload> loadListOfFiles(FileUpload fileUpload) throws CustomException;

   List<CaptureDamageModel> fetchManifestFlightDetails(SearchDamageDetails captureDamageModel) throws CustomException;

   String getCarrierCodeForAMailBag(String mailBag) throws CustomException;

   String getRegistrationNumber() throws CustomException;

List<CaptureDamageDetails> getDocumentRemarks(MailingDetails fileUploadModel) throws CustomException;

	void insertCargoDamageInfo(CaptureDamageModel captureDamageModel) throws CustomException;
	
	void updateCargoDamageInfo(CaptureDamageModel captureDamageModel) throws CustomException;
    
	String getDamageCodeForMail(String natureOfDamage) throws CustomException;

	List<CaptureDamageModel> fetchManifestFlightDetailsMobile(SearchDamageDetails captureDamageModel)
			throws CustomException;

	
	//SearchDamageDetails isHandledByHouse(SearchDamageDetails searchDamageDetails) throws CustomException;


}
