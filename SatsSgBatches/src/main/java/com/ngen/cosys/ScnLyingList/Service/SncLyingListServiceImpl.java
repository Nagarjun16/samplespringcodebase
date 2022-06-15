package com.ngen.cosys.ScnLyingList.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.ScnLyingList.DAO.ScnLyingListDAO;
import com.ngen.cosys.ScnLyingListModel.ScnLyingListParentModel;
import com.ngen.cosys.framework.exception.CustomException;
@Service
public class SncLyingListServiceImpl implements ScnLyingListService {
	private static final String DMG_SQ_I = "DMG_SQ_I";
	private static final String DMG_OAL_I = "DMG_OAL_I";
	private static final String DMG_SQ_E = "DMG_SQ_E";
	private static final String DMG_OAL_E = "DMG_OAL_E";
	
	private static final String SVC_SQ_I = "SVC_SQ_I";
	private static final String SVC_OAL_I = "SVC_OAL_I";
	private static final String SVC_SQ_E = "SVC_SQ_E";
	private static final String SVC_OAL_E = "SVC_OAL_E";
	private static final String Delay_SQ_I = "Delay_SQ_I";
	private static final String Delay_SQ_E = "Delay_SQ_E";
	private static final String Delay_OAL_I = "Delay_OAL_I";
	private static final String Delay_OAL_E = "Delay_OAL_E";
	@Autowired
	ScnLyingListDAO dao;
	@Override
	public List<ScnLyingListParentModel> getShipemntInformation() throws CustomException {

		return dao.getShipmentCarrierForScn();
	}

	@Override
	public List<String> getEmailAddresses(String carrierCode) throws CustomException {
		// TODO Auto-generated method stub
		return dao.getEmils(carrierCode);
	}

	@Override
	public List<String> getEmailAddressesDamageSQImport() throws CustomException{
		return dao.getEmailsForDamage(DMG_SQ_I);
		
	}

	@Override
	public List<String> getEmailAddressesDamageOALImport() throws CustomException {
		return dao.getEmailsForDamage(DMG_OAL_I);
	}

	@Override
	public List<String> getEmailAddressesDamageSQExport() throws CustomException {
		return dao.getEmailsForDamage(DMG_SQ_E);
	}

	@Override
	public List<String> getEmailAddressesDamageOALExport() throws CustomException {
		return dao.getEmailsForDamage(DMG_OAL_E);
	}

	@Override
	public List<String> getEmailAddressesSVCSQImport() throws CustomException {
		return dao.getEmailsForDamage(SVC_SQ_I);
	}

	@Override
	public List<String> getEmailAddressesSVCOALImport() throws CustomException {
		return dao.getEmailsForDamage(SVC_OAL_I);
	}

	@Override
	public List<String> getEmailAddressesSVCSQExport() throws CustomException {
		return dao.getEmailsForDamage(SVC_SQ_E);
	}

	@Override
	public List<String> getEmailAddressesSVCOALExport() throws CustomException {
		return dao.getEmailsForDamage(SVC_OAL_E);
	}

	@Override
	public List<String> getEmailAddressesDelayOALImport() throws CustomException {
		return dao.getEmailsForDamage(Delay_OAL_I);
	}

	@Override
	public List<String> getEmailAddressesDelaySQExport() throws CustomException {
		return dao.getEmailsForDamage(Delay_SQ_E);
	}

	@Override
	public List<String> getEmailAddressesDelayOALExport() throws CustomException {
		return dao.getEmailsForDamage(Delay_OAL_E);
	}

	@Override
	public List<String> getEmailAddressesDelaySQImport() throws CustomException {
		return dao.getEmailsForDamage(Delay_SQ_I);
	}
}
