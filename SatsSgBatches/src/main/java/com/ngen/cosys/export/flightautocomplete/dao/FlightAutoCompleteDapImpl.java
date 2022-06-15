/**
 * This is an implementation class for repository which holds all methods for
 * performing CRUD operations on Auto Flight Complete
 */
package com.ngen.cosys.export.flightautocomplete.dao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.export.flightautocomplete.model.FlightAutoCompleteDetails;
import com.ngen.cosys.export.flightautocomplete.model.FlightAutoCompleteShipmentDetails;
import com.ngen.cosys.export.flightautocomplete.model.FlightAutoCompleteShipmentHouseDetails;
import com.ngen.cosys.export.flightautocomplete.model.Uld;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Repository
public class FlightAutoCompleteDapImpl extends BaseDAO implements FlightAutoCompleteDao {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.export.flightautocomplete.dao.FlightAutoCompleteDao#
    * getFlightList()
    */
   @Override
   public List<FlightAutoCompleteDetails> getFlightList() throws CustomException {
      return this.fetchList("sqlGetFlightListForAutoComplete",MultiTenantUtility.getAirportCityMap("") , sqlSession);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.export.flightautocomplete.dao.FlightAutoCompleteDao#
    * markFirstFlightComplete(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteDetails)
    */
   @Override
   public void markFirstFlightComplete(FlightAutoCompleteDetails request) throws CustomException {
      this.updateData("sqlUpdateMarkFirstAutoFlightComplete", request, sqlSession);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.export.flightautocomplete.dao.FlightAutoCompleteDao#
    * unmarkFirstFlightComplete(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteDetails)
    */
   @Override
   public void unmarkFirstFlightComplete(FlightAutoCompleteDetails request) throws CustomException {
      this.updateData("sqlUpdateUnMarkFirstAutoFlightComplete", request, sqlSession);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.export.flightautocomplete.dao.FlightAutoCompleteDao#
    * markFlightComplete(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteDetails)
    */
   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.AUTO_FLIGHT_COMPLETE)
   public void markFlightComplete(FlightAutoCompleteDetails request) throws CustomException {
      this.updateData("sqlUpdateFlightEventsOnAutoFlightComplete", request, sqlSession);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.export.flightautocomplete.dao.FlightAutoCompleteDao#
    * markFlightStats(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteDetails)
    */
   @Override
   public void markFlightStats(FlightAutoCompleteDetails request) throws CustomException {
      this.updateData("sqlUpdateFlightStatusForAutoFlightComplete", request, sqlSession);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.export.flightautocomplete.dao.FlightAutoCompleteDao#
    * deAssociateULDFromFlight(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteDetails)
    */
   @Override
   public void deAssociateULDFromFlight(FlightAutoCompleteDetails request) throws CustomException {
      this.updateData("sqlDeAssociateULDFromOutboundFlightOnAutoFlightComplete", request, sqlSession);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.export.flightautocomplete.dao.FlightAutoCompleteDao#
    * moveInventoryToFreightOut(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteShipmentDetails)
    */
   @Override
   public void moveInventoryToFreightOut(FlightAutoCompleteShipmentDetails request) throws CustomException {
      // Iterate each inventory line items
      for (BigInteger t : request.getInventoryIds()) {
         // Create the Map
         FlightAutoCompleteShipmentDetails invT = new FlightAutoCompleteShipmentDetails();
         invT.setShipmentInventoryId(t);
         invT.setCreatedBy(request.getCreatedBy());
         invT.setModifiedBy(request.getModifiedBy());

         // Move the inventory to freight out
         this.insertData("sqlMoveShipmentInventoryToFreightOutOnFlightAutoComplete", invT, sqlSession);
         this.insertData("sqlMoveShipmentInventorySHCToFreightOutSHCOnFlightAutoComplete", invT, sqlSession);
         this.insertData("sqlMoveShipmentInventoryHouseToFreightOutHouseOnFlightAutoComplete", invT, sqlSession);

         // Delete the inventory
         this.deleteData("sqlDeleteShipmentInventorySHCOnAutoFlightComplete", invT, sqlSession);
         this.deleteData("sqlDeleteShipmentInventoryHouseOnAutoFlightComplete", invT, sqlSession);
         this.deleteData("sqlDeleteShipmentInventoryOnAutoFlightComplete", invT, sqlSession);
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.export.flightautocomplete.dao.FlightAutoCompleteDao#
    * checkShipmentFullDepartedStatus(java.math.BigInteger)
    */
   @Override
   public boolean checkShipmentFullDepartedStatus(BigInteger shipmentId) throws CustomException {
      return sqlSession.selectOne("sqlCheckShipmentFullDepartedStatus", shipmentId);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.export.flightautocomplete.dao.FlightAutoCompleteDao#
    * updateShipmentStatus(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteShipmentDetails)
    */
   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AUTO_FLIGHT_COMPLETE)
   public void updateShipmentStatus(FlightAutoCompleteShipmentDetails request) throws CustomException {
      this.updateData("sqlUpdateShipmentStatusOnFlightAutoComplete", request, sqlSession);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.export.flightautocomplete.dao.FlightAutoCompleteDao#
    * updateShipmentHouseStatus(java.util.List)
    */
   @Override
   public void updateShipmentHouseStatus(List<FlightAutoCompleteShipmentHouseDetails> request) throws CustomException {
      this.updateData("sqlUpdateShipmentHouseStatusOnFlightAutoComplete", request, sqlSession);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.export.flightautocomplete.dao.FlightAutoCompleteDao#
    * getCommunicationEmailIds(com.ngen.cosys.export.flightautocomplete.model.
    * FlightAutoCompleteDetails)
    */
   @Override
   public List<String> getCommunicationEmailIds(FlightAutoCompleteDetails request) throws CustomException {
      return this.fetchList("sqlGetCommunicationAddressOnAutoFlightComplete", request, sqlSession);
   }

   /*
    * (non-Javadoc) check for Japan customs for checking configured sector for
    * message triggering
    * 
    * @see com.ngen.cosys.export.flightautocomplete.dao.FlightAutoCompleteDao#
    * checkJapanCustomsRequirement(java.math.BigInteger, java.lang.String,
    * java.lang.String)
    */
   @Override
   public BigInteger checkJapanCustomsRequirement(BigInteger flightId, String tenantAirport, String carrierCode)
         throws CustomException {
      HashMap<String, Object> queryMap = new HashMap<>();
      queryMap.put("flightId", flightId);
      queryMap.put("tenantAirport", tenantAirport);
      queryMap.put("carrierCode", carrierCode);
      return this.fetchObject("checkJapanCustoms", queryMap, sqlSession);
   }

   @Override
   public Boolean checkVolumeNeedsTobeDerived(FlightAutoCompleteDetails request) throws CustomException {
      return this.fetchObject("sqlCheckVolumeToBeIncludedInFFMOnAutoFlightComplete", request, sqlSession);
   }

   @Override
   public List<FlightAutoCompleteShipmentDetails> getManifestShipmentInfo(FlightAutoCompleteDetails request)
         throws CustomException {

      return super.fetchList("sqlGetManifestedShipmentInfoOnAutoFlightComplete", request, sqlSession);
   }

   @Override
   public void updateManifestShipmentVolume(FlightAutoCompleteShipmentDetails request) throws CustomException {
      this.updateData("sqlUpdateManifestShipmentVolumeOnAutoFlightComplete", request, sqlSession);
   }

	/* (non-Javadoc)
	 * @see com.ngen.cosys.export.flightautocomplete.dao.FlightAutoCompleteDao#createUldOutMovement(com.ngen.cosys.export.flightautocomplete.model.FlightAutoCompleteDetails)
	 */
	@Override
	public void createUldOutMovement(FlightAutoCompleteDetails request) throws CustomException {
		List<Uld> UldList = getUldList(request);
		if (!CollectionUtils.isEmpty(UldList)) {
			for (Uld uld : UldList) {
				uld.setModifiedBy(request.getModifiedBy());
				uld.setCreatedBy(request.getCreatedBy());
				uld.setCreatedOn(request.getCreatedOn());
				uld.setFlightCompletedOn(request.getFlightCompletedOn());
				uld.setHandlingCarrierCode(request.getCarrierCode());
				// to insert for ULD OFL movement
				createUldOutMovement(uld);
			}
		}
	}
	
	private void createUldOutMovement(Uld uld) throws CustomException {
		int count = fetchObject("checkDuplicateUldInUldOutMovement", uld, sqlSession);
		if (count == 0)
		insertData("insertForUldOutMovement", uld, sqlSession);
	}

	private List<Uld> getUldList(FlightAutoCompleteDetails request) throws CustomException {
		return fetchList("getUldlistToUpdateUldStatus", request, sqlSession);
	}
}