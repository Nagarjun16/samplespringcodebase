/**
 * This is a repository implementation
 */
package com.ngen.cosys.impbd.tracing.activity.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.summary.model.TracingUserDetailsModel;
import com.ngen.cosys.impbd.tracing.activity.constants.TracingActivityQueryIds;
import com.ngen.cosys.impbd.tracing.activity.model.TracingActivityShipmentInventoryModel;
import com.ngen.cosys.impbd.tracing.activity.model.TracingActivityShipmentModel;
import com.ngen.cosys.impbd.tracing.activity.model.TracingActivityShipmentShcModel;

@Repository
public class TracingActivityDaoImpl extends BaseDAO implements TracingActivityDao {

   @Autowired
   SqlSessionTemplate sqlSessionTemplate;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.tracing.activity.dao.TracingActivityDao#getFlightInfo(
    * com.ngen.cosys.impbd.tracing.activity.model.TracingActivityShipmentModel)
    */
   @Override
   public TracingActivityShipmentModel getFlightInfo(TracingActivityShipmentModel requestModel) throws CustomException {
      return this.fetchObject(TracingActivityQueryIds.SQL_GET_FLIGHT_INFO_FOR_TRACING_ACTIVITY.getQueryId(),
            requestModel, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.tracing.activity.dao.TracingActivityDao#getShc(com.ngen.
    * cosys.impbd.tracing.activity.model.TracingActivityShipmentModel)
    */
   @Override
   public List<TracingActivityShipmentShcModel> getShc(TracingActivityShipmentModel requestModel)
         throws CustomException {
      return this.fetchList(TracingActivityQueryIds.SQL_GET_SHIPMENT_SHC_FOR_TRACING_ACTIVITY.getQueryId(),
            requestModel, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.tracing.activity.dao.TracingActivityDao#getMaxCaseNumber
    * ()
    */
   @Override
   public BigInteger getMaxCaseNumber() throws CustomException {
	   BigInteger bi = this.fetchObject(TracingActivityQueryIds.SQL_GET_MAX_TRACING_CASE_NUMBER.getQueryId(), null,
	              sqlSessionTemplate);
      return bi;
     
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.tracing.activity.dao.TracingActivityDao#
    * checkTracingActivityExistsForShipment(com.ngen.cosys.impbd.tracing.activity.
    * model.TracingActivityShipmentModel)
    */
   @Override
   public Boolean checkTracingActivityExistsForShipment(TracingActivityShipmentModel requestModel)
         throws CustomException {
      return this.fetchObject(TracingActivityQueryIds.SQL_CHECK_TRACING_ACTIVITY_FOR_SHIPMENT_EXISTS.getQueryId(),
            requestModel, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.tracing.activity.dao.TracingActivityDao#
    * createTracingActivityForShipment(com.ngen.cosys.impbd.tracing.activity.model.
    * TracingActivityShipmentModel)
    */
   @Override
   public void createTracingActivityForShipment(TracingActivityShipmentModel requestModel) throws CustomException {
      TracingUserDetailsModel userDetails = new TracingUserDetailsModel();
      userDetails.setLoggedInUser(requestModel.getLoggedInUser());
      TracingUserDetailsModel userDetail = fetchObject("sqlGetUserDetails", userDetails, sqlSessionTemplate);
      if (Objects.nonNull(userDetail)) {
         requestModel.setImportStaffName(userDetail.getUserShortName());
         requestModel.setImportStaffNumber(userDetail.getStaffIdNumber());
         requestModel.setImportUserCode(userDetail.getUserLoginCode());
      }

      String flightKey = this.fetchObject("getFlightKey", String.valueOf(requestModel.getFlightId()),
            sqlSessionTemplate);
      requestModel.setFlightKey(flightKey);

      this.insertData(TracingActivityQueryIds.SQL_INSERT_TRACING_ACTIVITY_FOR_SHIPMENT.getQueryId(), requestModel,
            sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.tracing.activity.dao.TracingActivityDao#
    * addShcToTracingActivity(java.util.List)
    */
   @Override
   public void addShcToTracingActivity(List<TracingActivityShipmentShcModel> requestModel) throws CustomException {
      this.insertData(TracingActivityQueryIds.SQL_INSERT_TRACING_ACTIVITY_FOR_SHIPMENT_SHC.getQueryId(), requestModel,
            sqlSessionTemplate);
   }

   @Override
   public List<TracingActivityShipmentInventoryModel> getInventoryInfoForTracing(TracingActivityShipmentModel t)
         throws CustomException {
      return this.fetchList(TracingActivityQueryIds.SQL_GET_INVENTORYINFO_FOR_TRACING_CASE_NUMBER.getQueryId(), t,
            sqlSessionTemplate);
   }

   @Override
   public void addInventoryToTracingActivity(List<TracingActivityShipmentInventoryModel> tracingShipmentInventory)
         throws CustomException {
      this.insertData(TracingActivityQueryIds.SQL_INSERT_INVENTORYINFO_FOR_TRACING_ACTIVITY.getQueryId(),
            tracingShipmentInventory, sqlSessionTemplate);

   }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.tracing.activity.dao.TracingActivityDao#
	 * deleteTracingActivityForShipment(com.ngen.cosys.impbd.tracing.activity.model.
	 * TracingActivityShipmentModel)
	 */
	@Override
	public void deleteTracingActivityForShipment(TracingActivityShipmentModel requestModel) throws CustomException {
		//Get the id
		List<BigInteger> ids = this.fetchList(
				TracingActivityQueryIds.SQL_GET_TRACING_ACTIVITY_FOR_SHIPMENT_ID.getQueryId(), requestModel,
				sqlSessionTemplate);

		// Check for empty
		if (!CollectionUtils.isEmpty(ids)) {
			for (BigInteger id : ids) {
				// Delete the dimension info
				this.deleteData(
						TracingActivityQueryIds.SQL_DELETE_TRACING_ACTIVITY_FOR_SHIPMENT_DIMENSION_INFO.getQueryId(),
						id, sqlSessionTemplate);
				// Delete the follow up action
				this.deleteData(
						TracingActivityQueryIds.SQL_DELETE_TRACING_ACTIVITY_FOR_SHIPMENT_FOLLOWUP_ACTION.getQueryId(),
						id, sqlSessionTemplate);
				// Delete the location
				this.deleteData(TracingActivityQueryIds.SQL_DELETE_TRACING_ACTIVITY_FOR_SHIPMENT_LOCATION.getQueryId(),
						id, sqlSessionTemplate);
				// Delete the SHC
				this.deleteData(TracingActivityQueryIds.SQL_DELETE_TRACING_ACTIVITY_FOR_SHIPMENT_SHC.getQueryId(), id,
						sqlSessionTemplate);
				// Delete the shipment
				this.deleteData(TracingActivityQueryIds.SQL_DELETE_TRACING_ACTIVITY_FOR_SHIPMENT.getQueryId(), id,
						sqlSessionTemplate);
			}
		}
		
	}

}