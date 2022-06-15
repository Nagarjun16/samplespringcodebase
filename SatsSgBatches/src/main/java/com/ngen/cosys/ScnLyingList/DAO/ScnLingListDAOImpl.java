package com.ngen.cosys.ScnLyingList.DAO;

import java.util.List;
import java.util.stream.Collectors;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.ScnLyingListModel.ScnLyingListParentModel;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository
public class ScnLingListDAOImpl extends BaseDAO implements ScnLyingListDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;

@Override
public List<ScnLyingListParentModel> getShipmentCarrierForScn() throws CustomException {
	
	return this.fetchList("fetchCarrier", null, sqlSession);
}

@Override
public List<String> getEmils(String carrierCode) throws CustomException {
	List<String> allEmails = this.fetchList("getEmailAddressesForSCNLying", carrierCode, sqlSession);
    return allEmails.stream().distinct().collect(Collectors.toList());
}

@Override
public List<String> getEmailsForDamage(String dmg) throws CustomException {
	List<String> allEmailsForDamage = this.fetchList("getEmailAddressesForDamage", dmg, sqlSession);
    return allEmailsForDamage.stream().distinct().collect(Collectors.toList());
	
}




}
