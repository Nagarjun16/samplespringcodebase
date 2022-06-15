package com.ngen.cosys.shipment.airmail.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.shipment.airmail.dao.LyingListDao;
import com.ngen.cosys.shipment.airmail.model.LyingListContainer;
import com.ngen.cosys.shipment.airmail.model.SearchForLyingList;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository("lyingListDao")
public class LyingListDaoImpl extends BaseDAO implements LyingListDao{


	  @Autowired
	   @Qualifier("sqlSessionTemplate")
	   private SqlSession sqlSession;
	  
	@Override
	public List<LyingListContainer> getSearchedLyingList(SearchForLyingList param) throws CustomException {
		List<LyingListContainer> resp = super.fetchList("searchlyingList", param, sqlSession);
		return resp;
	}
}
