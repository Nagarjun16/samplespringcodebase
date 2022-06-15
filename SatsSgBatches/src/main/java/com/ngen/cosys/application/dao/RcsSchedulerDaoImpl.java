package com.ngen.cosys.application.dao;

import java.math.BigInteger;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.application.job.RcsSchedulerJob;
import com.ngen.cosys.application.model.RcsSchedulerDetail;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Repository
public class RcsSchedulerDaoImpl extends BaseDAO implements RcsSchedulerDao{

	   private static final Logger LOGGER = LoggerFactory.getLogger(RcsSchedulerDaoImpl.class);
	   
	   @Autowired
	   @Qualifier("sqlSessionTemplate")
	   private SqlSessionTemplate sqlSession;
	
	@Override
	public List<RcsSchedulerDetail> getList() throws CustomException {
		LOGGER.warn("inside getList() daoimpl");
		 return this.fetchList("getRcsSchedulerData", MultiTenantUtility.getAirportCityMap(""), sqlSession);
	}

	@Override
	public BigInteger getIsShipmentEawb(String shipmentNumber) throws CustomException {
	      LOGGER.warn("inside getIsShipmentEawb daoimpl");
		 return this.fetchObject("checkShipmentIsEAwbShipmentRcs", shipmentNumber, sqlSession);
	}

	
}
