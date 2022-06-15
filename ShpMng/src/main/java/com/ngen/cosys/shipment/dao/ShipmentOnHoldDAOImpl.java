package com.ngen.cosys.shipment.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.timezone.support.TenantTimeZoneUtility;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.shipment.model.SearchAWB;
import com.ngen.cosys.shipment.model.ShipmentInventory;
import com.ngen.cosys.shipment.model.ShipmentMaster;
import com.ngen.cosys.timezone.enums.TenantTimeZone;

@Repository("shipmentOnHoldDAO")
public class ShipmentOnHoldDAOImpl extends BaseDAO implements ShipmentOnHoldDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSessionShipment;

   @Override
   public List<ShipmentMaster> getLockDetails(SearchAWB paramAWB) throws CustomException {
      List<ShipmentMaster> shipmentMaster = new ArrayList<>();
      ShipmentMaster shipment = super.fetchObject("searchAWBdetails", paramAWB, sqlSessionShipment);
      shipment.setSpecialHandlingCode(
            shipment.getShcList().stream().map(Object::toString).collect(Collectors.joining(" ")));
      boolean handledByHouse;
      handledByHouse=paramAWB.isHandledbyHouse();
      if(handledByHouse) { 
      List<String> houseSHC=super.fetchList("fetchHouseSHCList", shipment, sqlSessionShipment);
      if(!CollectionUtils.isEmpty(houseSHC)) {
    	  String hawbSHC="";
    	  for(String shc:houseSHC ) {
    		  hawbSHC+=' '+shc;
    	  }
    	  shipment.setSpecialHandlingCodeHAWB(hawbSHC);
      }
      }
      
      shipment.setFlightId(paramAWB.getFlightId());
      List<ShipmentInventory> detail = super.fetchList("searchShipmentOnHoldDetails", shipment, sqlSessionShipment);
      shipment.setShipmentInventories(detail);
      shipmentMaster.add(shipment);
      return shipmentMaster;
   }

	@Override
	//@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENT_ON_HOLD)
	public void updateLockDetails(ShipmentMaster shipmentMaster) throws CustomException {
		LocalDateTime tenantDateTime = TenantTimeZoneUtility.now();
		if(!StringUtils.isEmpty(tenantDateTime))
		shipmentMaster.setHold_unholdTime(tenantDateTime);
		
		this.updateShipmentOnHold(shipmentMaster);

		List<ShipmentInventory> updateData = shipmentMaster.getShipmentInventories().stream()
				.filter(obj -> (!"R".equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());
		super.updateData("updateShipmentOnHoldDetail", updateData, sqlSessionShipment);
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENT_ON_HOLD)
	public void updateShipmentOnHold(ShipmentMaster shipmentMaster) throws CustomException {
		super.updateData("updateShipmentOnHold", shipmentMaster, sqlSessionShipment);

	}

   @Override
   public boolean validateShipmentNumber(SearchAWB paramAWB) throws CustomException {
      return paramAWB.getShipmentNumber()
            .equalsIgnoreCase((String) super.fetchObject("searchshipmentNumber", paramAWB, sqlSessionShipment));
   }

   @Override
   public BigInteger getMaxCaseNumber() throws CustomException {
      return this.fetchObject("sqlGetTracingCaseNumber", null, sqlSessionShipment);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENT_ON_HOLD)
   public void insertTracingRecords(ShipmentMaster shipmentMaster) throws CustomException {
      int resultpieces = 0;
      BigDecimal test = new BigDecimal(0);
      if (MultiTenantUtility.isTenantCityOrAirport(shipmentMaster.getOrigin())) {
         shipmentMaster.setShipmentTypeflag("E");
      } else if (MultiTenantUtility.isTenantCityOrAirport(shipmentMaster.getDestination())) {
         shipmentMaster.setShipmentTypeflag("I");
      } else {
         shipmentMaster.setShipmentTypeflag("T");
      }

      for (ShipmentInventory shp : shipmentMaster.getShipmentInventories()) {
         resultpieces += shp.getPiecesInv();
         test = test.add(shp.getWeightInv());
      }
      shipmentMaster.setIrrPieces(resultpieces);
      shipmentMaster.setIrrWeight(test);
      insertData("insertTracingRecords", shipmentMaster, sqlSessionShipment);

   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENT_ON_HOLD)
   public void insertTracingShipmentInfo(ShipmentInventory shipmentMaster) throws CustomException {
      insertData("insertTracingShipmentInfoRecords", shipmentMaster, sqlSessionShipment);
   }

   @Override
   public boolean isHoldExists(ShipmentMaster paramAWB) throws CustomException {
      return this.fetchObject("sqlCheckShipmentInventoryHoldcount", paramAWB, sqlSessionShipment);
   }

@Override
public void updateTracingRecords(ShipmentInventory shipmentMaster) throws CustomException {
	updateData("updateTracingRecord",shipmentMaster, sqlSessionShipment);
	
}

@Override
public void insertTracingRecordActivity(ShipmentInventory shipmentMaster) throws CustomException {
	insertData("insertTracingShipmentActivity",shipmentMaster, sqlSessionShipment);
	
}

@Override
public BigInteger getTracingShipmentInfoId(ShipmentInventory shipmentMaster) throws CustomException {
	
	return fetchObject("getTracingShipmentInfoId",shipmentMaster,sqlSessionShipment);
}

}