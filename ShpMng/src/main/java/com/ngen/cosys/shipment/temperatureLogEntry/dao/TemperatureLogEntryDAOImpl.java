package com.ngen.cosys.shipment.temperatureLogEntry.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureLogEntry;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureLogResponse;
import com.ngen.cosys.shipment.temperatureLogEntry.model.TemperatureSearch;

@Repository("temperatureLogEntryDAOImpl")
public class TemperatureLogEntryDAOImpl extends BaseDAO implements TemperatureLogEntryDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionShipment;

	@Override
	public TemperatureLogResponse getShipmentInformation(TemperatureSearch shipmentInformationRequestModel)
			throws CustomException {
		return super.fetchObject("fetchShipmentTemperature", shipmentInformationRequestModel, sqlSessionShipment);
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.TEMPARATURE_LOG_ENTRY)
	@Override
	public List<TemperatureLogEntry> addTemperatureLogEntry(List<TemperatureLogEntry> entrydata) 
			throws CustomException {	
		 super.insertData("saveTemperatureLogEntry", entrydata, sqlSessionShipment);
		 return entrydata;
	}
	
	
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.TEMPARATURE_LOG_ENTRY)
	@Override
	public List<TemperatureLogEntry> deleteTemperatureLogEntry(List<TemperatureLogEntry> entrydata) 
			throws CustomException {	
		 super.deleteData("deleteTemperatureLogEntry", entrydata, sqlSessionShipment);
		 return entrydata;
	}
}
