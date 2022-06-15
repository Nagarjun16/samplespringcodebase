package com.ngen.cosys.satssginterfaces.mss.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.enums.MapperId;
import com.ngen.cosys.satssginterfaces.mss.model.BuildUpMailSearch;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMailBagModel;

/**
 * Dao to fetch Mail Load Shipment related request
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Repository("mailLoadShipmentDao")
public class MailLoadShipmentDaoImpl extends BaseDAO implements MailLoadShipmentDao {
   
   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSession;
   
   @Override
   public BuildUpMailSearch fetchFlightDeatil(BuildUpMailSearch search) throws CustomException {
      return fetchObject(MapperId.SQL_GET_FLIGHT_DETAIL.getId(), search, sqlSession);
   }
   @Override
   public List<ShipmentMailBagModel> fetchPrebookedList(BuildUpMailSearch search) throws CustomException {
   	
     return fetchList(MapperId.SQL_PRE_BOOKED_LIST.getId(), search, sqlSession);
   }

   @Override
   public List<ShipmentMailBagModel> fetchContainerlist(BuildUpMailSearch search) throws CustomException {
   
	   return fetchList(MapperId.SQL_GET_CONTAINER_LIST.getId(), search, sqlSession);
   }

   @Override
   public List<BuildUpMailSearch> fetchMailbagDetail(BuildUpMailSearch buildUpMailSearch) throws CustomException {
      return fetchList(MapperId.SQL_MAIL_BAG_DETAIL.getId(), buildUpMailSearch, sqlSession);
   }

   @Override
   public BuildUpMailSearch checkMailBagNumber(BuildUpMailSearch search) throws CustomException {
      return fetchObject(MapperId.SQL_CHECK_MAIL_BAG.getId(), search, sqlSession);
   }
   
   @Override
   public boolean isDLSCompleted(BuildUpMailSearch flight) {
       Integer count = sqlSession.selectOne(MapperId.SQL_FOR_DLS_COMPLETE.getId(), flight);
       return count > 0;
   }

@Override
public BuildUpMailSearch uploadPhotos(BuildUpMailSearch common) throws CustomException {
	super.updateData(MapperId.SQL_FOR_UPLOAD_PHOTO.getId(), common, sqlSession);
	return new BuildUpMailSearch();
}



}
