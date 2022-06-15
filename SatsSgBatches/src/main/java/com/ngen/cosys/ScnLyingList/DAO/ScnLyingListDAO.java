package com.ngen.cosys.ScnLyingList.DAO;

import java.util.List;

import com.ngen.cosys.ScnLyingListModel.ScnLyingListParentModel;
import com.ngen.cosys.framework.exception.CustomException;

public interface ScnLyingListDAO {

	List<ScnLyingListParentModel> getShipmentCarrierForScn() throws CustomException;

	List<String> getEmils(String carrierCode) throws CustomException;

	List<String> getEmailsForDamage(String dmg) throws CustomException;

}
