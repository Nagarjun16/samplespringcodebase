package com.ngen.cosys.transhipment.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.transhipment.model.TransferByCarrier;
import com.ngen.cosys.transhipment.model.TransferByCarrierSearch;

@Repository("ShipmentTransferByCarrierDAO")
public class ShipmentTransferByCarrierDAOImpl extends BaseDAO implements ShipmentTransferByCarrierDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSession;

   @Override
   public TransferByCarrierSearch search(TransferByCarrierSearch search) throws CustomException {
      List<TransferByCarrier> awbList = fetchList("searchShipmentListByCarrier", search, sqlSession);
      search.setTransferByCarrierList(awbList);
      return search;
   }

}