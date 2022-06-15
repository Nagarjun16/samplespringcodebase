/**
 * 
 * DisplayffmDaoImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 30 April, 2018 NIIT -
 */
package com.ngen.cosys.impbd.displayffm.dao;

import java.math.BigInteger;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.constants.SqlIDs;
import com.ngen.cosys.impbd.displayffm.model.DisplayffmByFlightModel;
import com.ngen.cosys.impbd.displayffm.model.SearchDisplayffmModel;
import com.ngen.cosys.impbd.model.FFMCountDetails;

/**
 * This class takes care of the responsibilities related to the Displayffm DAO
 * operation that comes from the service.
 * 
 * @author NIIT Technologies Ltd.
 *
 */
@Repository
public class DisplayffmDaoImpl extends BaseDAO implements DisplayffmDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * Find Shipment Details using Flight Number,Date.
	 * 
	 * @param flightKey,flightDate
	 * @return DisplayffmByFlightModel
	 * @throws CustomException
	 */
	@Override
	public List<DisplayffmByFlightModel> search(SearchDisplayffmModel searchDisplayffmModel) throws CustomException {
		return fetchList(SqlIDs.SQL_GET_DISPLAY_FFM_INFO.toString(), searchDisplayffmModel, sqlSessionTemplate);
	}
	@Override
	public void updateFFMstatus(SearchDisplayffmModel searchDisplayffmModel) throws CustomException {
		List<BigInteger> impFreightFlightManifestBySegmentIds = this.fetchList("getmmpFreightFlightManifestBySegmentId",
				searchDisplayffmModel, sqlSessionTemplate);

		if (!CollectionUtils.isEmpty(impFreightFlightManifestBySegmentIds)) {
			for (BigInteger id : impFreightFlightManifestBySegmentIds) {
				this.updateData("updateProcessedFFMStatus", id, sqlSessionTemplate);
			}

		}
		this.updateData("updateUnProcessedFFMStatus", searchDisplayffmModel, sqlSessionTemplate);

	}
	@Override
	public String getStatus(FFMCountDetails ffmCountDetails) throws CustomException {
		
		return fetchObject("getStatusOfMessage",ffmCountDetails,sqlSessionTemplate);
	}
}