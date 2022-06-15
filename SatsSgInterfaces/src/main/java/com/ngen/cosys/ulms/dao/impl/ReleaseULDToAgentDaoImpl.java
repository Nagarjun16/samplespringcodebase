package com.ngen.cosys.ulms.dao.impl;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ulms.dao.ReleaseULDToAgentDao;
import com.ngen.cosys.ulms.model.ReleaseULDDetail;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReleaseULDToAgentDaoImpl extends BaseDAO implements ReleaseULDToAgentDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseULDToAgentDaoImpl.class);

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSession sqlSession;

    @Override
    public ReleaseULDDetail getCustomerDetail(ReleaseULDDetail releaseULDDetail) throws CustomException {
        return super.fetchObject("getCustomerDetail", releaseULDDetail, sqlSession);
    }

    @Override
    public List<ReleaseULDDetail> getReleaseUldList(ReleaseULDDetail releaseULDDetail) throws CustomException {
        return super.fetchList("getReleaseUldList", releaseULDDetail, sqlSession);
    }
}
