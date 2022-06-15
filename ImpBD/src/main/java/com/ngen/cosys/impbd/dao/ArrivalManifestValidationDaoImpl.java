package com.ngen.cosys.impbd.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.constants.SqlIDs;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.model.ArrivalManifestUldModel;

@Repository
public class ArrivalManifestValidationDaoImpl extends BaseDAO implements ArrivalManifestValidationDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionImportAM;

	@Override
	public List<ArrivalManifestShipmentInfoModel> checkShipmentExists(ArrivalManifestShipmentInfoModel shipmentModel)
			throws CustomException {
		return fetchList(SqlIDs.CHECKARRIVALMANIFESTSHIPMENT.toString(), shipmentModel, sqlSessionImportAM);
	}

	@Override
	public List<ArrivalManifestUldModel> fetchUldInfo(ArrivalManifestUldModel shipmentModel) throws CustomException {
		return fetchList(SqlIDs.FETCHULDNUMBER.toString(), shipmentModel, sqlSessionImportAM);
	}

	@Override
	public List<ArrivalManifestShipmentInfoModel> fetchShipmentExists(ArrivalManifestShipmentInfoModel shipmentModel)
			throws CustomException {

		return fetchList(SqlIDs.FETCHSHIPMENTS.toString(), shipmentModel, sqlSessionImportAM);
	}

	@Override
	public List<ArrivalManifestShipmentInfoModel> fetchDuplicateSplitShipments(
			ArrivalManifestShipmentInfoModel shipmentModel) throws CustomException {
		return fetchList("fetchDuplicateSplitShipments", shipmentModel, sqlSessionImportAM);
	}

	@Override
	public Boolean checkSegmentLevelDuplicateShipments(ArrivalManifestShipmentInfoModel shipmentModel)
			throws CustomException {
		return fetchObject("sqlCheckSegmentLevelDuplicateShipments", shipmentModel, sqlSessionImportAM);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.dao.ArrivalManifestValidationDao#
	 * isShipmentExistsInArrivalManifest(com.ngen.cosys.impbd.model.
	 * ArrivalManifestShipmentInfoModel)
	 */
	@Override
	public Boolean isShipmentExistsInArrivalManifest(ArrivalManifestShipmentInfoModel shipmentModel)
			throws CustomException {
		return this.fetchObject("sqlGetShipmentExistsInArrivalManifestForAnULDLoose", shipmentModel,
				sqlSessionImportAM);
	}

}
