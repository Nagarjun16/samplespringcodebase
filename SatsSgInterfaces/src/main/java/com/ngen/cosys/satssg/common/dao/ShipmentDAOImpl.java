package com.ngen.cosys.satssg.common.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class ShipmentDAOImpl implements ShipmentDAO {

   @Qualifier("sqlSessionTemplate")
   @Autowired
   private SqlSession sqlSession;
   
   @Override
   public LocalDate getShipmentDate(String shipmentNumber) {
      Timestamp value = sqlSession.selectOne("sqlSelectShipmentDateCommon", shipmentNumber);
      // Check for NULL
      Optional<Timestamp> oValue = Optional.ofNullable(value);
      LocalDateTime shipmentDate = null;
      if (oValue.isPresent()) {
         shipmentDate = value.toLocalDateTime();
      } else {
         shipmentDate = LocalDateTime.now();
      }

      return shipmentDate.toLocalDate();
   }

}
