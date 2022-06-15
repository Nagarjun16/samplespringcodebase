package com.ngen.cosys.satssginterfaces.mss.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.enums.ManifestQuery;
import com.ngen.cosys.satssginterfaces.mss.model.Flight;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestFlight;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestHouse;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestSHC;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestSegment;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestShipment;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestULD;

@Repository("ManifestDAO")
public class ManifestDAOImpl extends BaseDAO implements ManifestDAO{

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	@Override
	public ManifestFlight fetchLoadDetails(Flight flight) throws CustomException {
		return super.fetchObject(ManifestQuery.FETCH_LOAD_DETAILS.getQueryId(), flight, sqlSession);
	}

	@Override
	public ManifestSegment insertManifest(ManifestSegment segment) throws CustomException {
		Integer manifestId = super.fetchObject(ManifestQuery.GET_MANIFEST_ID.getQueryId(), segment, sqlSession);
		if(manifestId == null)
			super.insertData(ManifestQuery.INSERT_MANIFEST.getQueryId(), segment, sqlSession);
		else
			segment.setManifestId(manifestId);
		return segment;
	}


	@Override
	public ManifestULD insertManifestULD(ManifestULD uld) throws CustomException {
		Integer manifestUldId = super.fetchObject(ManifestQuery.GET_MANIFEST_ULD_ID.getQueryId(), uld, sqlSession);
		if(manifestUldId == null)
			super.insertData(ManifestQuery.INSERT_MANIFEST_ULD.getQueryId(), uld, sqlSession);
		else
			uld.setManifestUldId(manifestUldId);
		return uld;
	}

	@Override
	public ManifestShipment insertManifestShipment(ManifestShipment shipment) throws CustomException {
		Integer manifestShipmentId = super.fetchObject(ManifestQuery.GET_MANIFEST_SHIPMENT_ID.getQueryId(), shipment, sqlSession);
		if(manifestShipmentId == null) {
			super.insertData(ManifestQuery.INSERT_MANIFEST_SHIPMENT.getQueryId(), shipment, sqlSession);
		}else {
			shipment.setManifestShipmentInfoId(manifestShipmentId.longValue());
			super.updateData(ManifestQuery.UPDATE_MANIFEST_SHIPMENT.getQueryId(), shipment, sqlSession);
		}
		return shipment;
	}

	@Override
	public ManifestShipment deleteAllManifestShipmentSHCHouse(ManifestShipment shipment) throws CustomException {
		super.deleteData(ManifestQuery.DELETE_ALL_MANIFEST_HOUSE.getQueryId(), shipment, sqlSession);
		super.deleteData(ManifestQuery.DELETE_ALL_MANIFEST_SHC.getQueryId(), shipment, sqlSession);
		return shipment;
	}

	@Override
	public ManifestSHC insertManifestShipmentSHC(ManifestSHC shc) throws CustomException {
		super.insertData(ManifestQuery.INSERT_MANIFEST_SHC.getQueryId(), shc, sqlSession);
		return shc;
	}

	@Override
	public ManifestHouse insertManifestShipmentHouse(ManifestHouse house) throws CustomException {
		super.insertData(ManifestQuery.INSERT_MANIFEST_HOUSE.getQueryId(), house, sqlSession);
		return house;
	}



}
