package com.ngen.cosys.ScnLyingList.Service;

import java.util.List;

import com.ngen.cosys.ScnLyingListModel.ScnLyingListParentModel;
import com.ngen.cosys.framework.exception.CustomException;

public interface ScnLyingListService {

	List<ScnLyingListParentModel> getShipemntInformation() throws CustomException;


	List<String> getEmailAddresses(String carrierCode)  throws CustomException;

	List<String> getEmailAddressesDamageSQImport() throws CustomException;

	List<String> getEmailAddressesDamageOALImport() throws CustomException;

	List<String> getEmailAddressesDamageSQExport() throws CustomException;

	List<String> getEmailAddressesDamageOALExport() throws CustomException;


	List<String> getEmailAddressesSVCSQImport() throws CustomException;


	List<String> getEmailAddressesSVCOALImport() throws CustomException;


	List<String> getEmailAddressesSVCSQExport() throws CustomException;


	List<String> getEmailAddressesSVCOALExport() throws CustomException;
	
	List<String> getEmailAddressesDelayOALImport() throws CustomException;
	List<String> getEmailAddressesDelaySQExport() throws CustomException;
	List<String> getEmailAddressesDelayOALExport() throws CustomException;
	List<String> getEmailAddressesDelaySQImport() throws CustomException;
	



   

}
