/**
 * MarkShipmentForReuseDAOImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 6 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.AddShipmentNumberForReuse;
import com.ngen.cosys.shipment.model.MarkShipmentForReuse;
import com.ngen.cosys.shipment.model.SearchShipmentNumberForReuse;

/**
 * This class takes care of the responsibilities related to the Reuse of
 * shipment number Mark Shipment Number For Reuse DAO operation that comes from
 * the service.
 * 
 * @author NIIT Technologies Ltd.
 *
 */
@Repository("markShipmentForReuseDAO")
public class MarkShipmentForReuseDAOImpl extends BaseDAO implements MarkShipmentForReuseDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSessionShipment;

   /**
    * Search Awb Number which is available for reuse it will Return Awb Number
    * which is available for reuse it they exist else It will Return Null.
    * 
    * @param searchShipmentNumber
    * @return List<MarkShipmentForReuse>
    * @throws CustomException
    */
   @Override
   public List<SearchShipmentNumberForReuse> search(MarkShipmentForReuse shipmentNumber) throws CustomException {
      return super.fetchList("fetchShipmentNumber", shipmentNumber, sqlSessionShipment);
   }

   @Override

   public boolean validateShipmentNumber(MarkShipmentForReuse paramAWB) throws CustomException {
      Integer count = super.fetchObject("searchValidShipment", paramAWB, sqlSessionShipment);
      if (count > 0) {
         return true;
      } else {
         return false;
      }
   }

   /**
    * Search All Awb Number which is available for reuse it will Return Awb Number
    * List which are available for reuse if they exist else It will Return Null.
    * 
    * @param searchShipmentNumber
    * @return List<MarkShipmentForReuse>
    * @throws CustomException
    */
   @Override
   public List<SearchShipmentNumberForReuse> searchAll() throws CustomException {
      return super.fetchList("fetchAllShipmentNumber", null, sqlSessionShipment);
   }

   /**
    * Add Awb Number which are available for reuse it will Add Awb Number which is
    * available for reuse if they exist else It will Return Null.
    * 
    * @param searchShipmentNumber
    * @return List<MarkShipmentForReuse>
    * @throws CustomException
    */
   @Override
   public List<SearchShipmentNumberForReuse> add(AddShipmentNumberForReuse shipmentNumber) throws CustomException {
      super.insertData("addShipmentNumber", shipmentNumber, sqlSessionShipment);
      return new ArrayList<>();
   }

   /**
    * Delete Awb Number which is available for reuse it will Delete Awb Number List
    * which are available for reuse if they exist else It will Return Null.
    * 
    * @param searchShipmentNumber
    * @return List<MarkShipmentForReuse>
    * @throws CustomException
    */
   @Override
   public boolean delete(SearchShipmentNumberForReuse shipmentId) throws CustomException {
      int count = super.deleteData("deleteShipmentNumber", shipmentId, sqlSessionShipment);
      return count > 0;
   }

   /**
    * Delete List of AWB Number which is available for reuse it will Delete Awb
    * Number List which are available for reuse if they exist else It will Return
    * Null.
    * 
    * @param searchShipmentNumber
    * @return List<MarkShipmentForReuse>
    * @throws CustomException
    */
   @Override
   public boolean delete(List<SearchShipmentNumberForReuse> shipments) throws CustomException {
      List<Integer> counts = super.deleteData("deleteShipmentNumber", shipments, sqlSessionShipment);
      return !CollectionUtils.isEmpty(counts);
   }

   /**
    * @param shipmentNumber
    * @return
    * @throws CustomException
    */
   @Override
   public String validateOrigin(AddShipmentNumberForReuse shipmentNumber) throws CustomException {
      return super.fetchObject("fetchOriginAirport", shipmentNumber, sqlSessionShipment);
   }

   @Override

   public boolean validateShipmentNumberToAdd(AddShipmentNumberForReuse paramAWB) throws CustomException {
      Integer count = super.fetchObject("searchValidShipmentToAdd", paramAWB, sqlSessionShipment);
      return count > 0;
   }
}