package com.ngen.cosys.damage.service;

import java.util.List;

import com.ngen.cosys.damage.model.CaptureDamageDetails;
import com.ngen.cosys.damage.model.CaptureDamageModel;
import com.ngen.cosys.damage.model.FileUpload;
import com.ngen.cosys.damage.model.FileUploadModel;
import com.ngen.cosys.damage.model.MailingDetails;
import com.ngen.cosys.damage.model.SearchDamageDetails;
import com.ngen.cosys.framework.exception.CustomException;

public interface DamageService {
	/**
	 * save damage
	 * 
	 * @param captureDamageModel
	 * @throws CustomException
	 */
	CaptureDamageModel save(CaptureDamageModel captureDamageModel) throws CustomException;

	/**
	 * Delete damage
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
	 * Upload list of files
	 * 
	 * @param uploadFiles
	 * @throws CustomException
	 */
	List<FileUpload> storeFiles(List<FileUpload> uploadFiles) throws CustomException;

	/**
	 * @param deleteFile
	 * @return
	 * @throws CustomException
	 */
	FileUpload deleteFile(FileUpload deleteFile) throws CustomException;

	/**
	 * @param fileUploadModel
	 * @return
	 * @throws CustomException
	 */
	List<FileUpload> loadListOfFiles(FileUploadModel fileUploadModel) throws CustomException;

	void sendEmail(MailingDetails fileUploadModel) throws CustomException;

	List<CaptureDamageModel> fetchManifestFlightDetails(SearchDamageDetails captureDamageModel) throws CustomException;

	String getCarrierCodeForAMailBag(String mailBag) throws CustomException;

	void sendEmailForDamage(MailingDetails uploadedFile);
	
	String getDamageCodeForMail(String natureOfDamage)  throws CustomException;
	
	List<CaptureDamageModel> fetchManifestFlightDetailsMobile(SearchDamageDetails captureDamageModel)
			throws CustomException;
	
	boolean isHandledByHouse(SearchDamageDetails searchDamageDetails) throws CustomException;

}
