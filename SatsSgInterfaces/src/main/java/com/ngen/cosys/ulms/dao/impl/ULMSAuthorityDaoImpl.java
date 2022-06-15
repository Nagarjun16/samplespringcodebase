package com.ngen.cosys.ulms.dao.impl;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ulms.dao.ULMSAuthorityDao;
import com.ngen.cosys.ulms.model.HeaderInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class ULMSAuthorityDaoImpl extends BaseDAO implements ULMSAuthorityDao {

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSession sqlSession;

    @Override
    public Integer checkClientValid(HeaderInfo headerInfo) throws CustomException {
        return super.fetchObject("checkClientValid",headerInfo,sqlSession);
    }
}
