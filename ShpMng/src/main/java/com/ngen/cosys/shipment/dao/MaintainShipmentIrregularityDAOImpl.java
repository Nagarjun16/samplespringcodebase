/**
 * MaintainShipmentIrregularityDAOImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 4 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.shipment.awb.model.ShipmentRemarksModel;
import com.ngen.cosys.shipment.model.IrregularityDetail;
import com.ngen.cosys.shipment.model.IrregularitySummary;
import com.ngen.cosys.shipment.model.SearchShipmentIrregularity;

/**
 * This class takes care of the responsibilities related to the Maintaining
 * Shipment Irregularity operation that comes from the service.
 * 
 * @author NIIT Technologies Ltd.
 *
 */
@Repository("maintainShipmentIrregularityDAO")
public class MaintainShipmentIrregularityDAOImpl extends BaseDAO implements MaintainShipmentIrregularityDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionShipment;

	public List<IrregularityDetail> fetchFlightDetails(SearchShipmentIrregularity search) throws CustomException {
		return super.fetchList("fetchIrregularityList", search, sqlSessionShipment);
	}
	
	public List<IrregularityDetail> fetchHAWBIrregularity(SearchShipmentIrregularity search) throws CustomException {
		return super.fetchList("fetchIrregularityListHAWB", search, sqlSessionShipment);
	}

	/**
	 * Find irregularity for shipment using shipment type and shipment number. it
	 * will Return Irregularity List with all irregularities if they exist
	 * 
	 * @param search
	 * @return MaintainShipmentIrregularity
	 * @throws CustomException
	 */
	@Override
	public IrregularitySummary search(SearchShipmentIrregularity search) throws CustomException {
		IrregularitySummary maintain;
		List<String> houseSHCList=null;
		List<IrregularitySummary> maintainList = super.fetchList("fetchAwbDetails", search, sqlSessionShipment);
		if(!CollectionUtils.isEmpty(maintainList)) {
		houseSHCList=super.fetchList("fetchHouseSHCLISTIrr", maintainList.get(0), sqlSessionShipment);}
		if (!maintainList.isEmpty()) {
			maintain = maintainList.get(0);
			maintain.setShipmentType(search.getShipmentType());
			String hawbshcs="";
			for(String shc:houseSHCList) {
				hawbshcs+=' '+shc;
			}
			maintain.setSpecialHandlingCodeHAWB(hawbshcs);
			return maintain;
		} else {
			return null;
		}
	}

	/**
	 * Save an irregularity to a shipment it will Return the updated irregularity
	 * 
	 * @param maintain
	 * @return MaintainShipmentIrregularity
	 * @throws CustomException
	 */
	@Override
	public IrregularityDetail save(IrregularityDetail maintain) throws CustomException {
		super.updateData("updateShipment", maintain, sqlSessionShipment);
		if (maintain.getIrregularityType().equalsIgnoreCase("MSCA")) {
			ShipmentRemarksModel remarks = new ShipmentRemarksModel();
			remarks.setRemarkType("IRR");
			remarks.setShipmentNumber(maintain.getShipmentNumber());
			remarks.setShipmentdate(maintain.getShipmentDate());
			remarks.setShipmentType("AWB");
			if (!StringUtils.isEmpty(maintain.getFlightKey())) {
				remarks.setFlightId(new BigInteger(maintain.getFlightKey()));
			}
			remarks.setShipmentRemarks(getRemarksFormat(maintain));
			remarks.setHawbNumber(maintain.getHawbNumber());
			super.updateData("sqlUpdateIRRShipmentRemarks", remarks, sqlSessionShipment);
		}
		return maintain;
	}

	/**
	 * Add an irregularity to a shipment it will Return the added irregularity
	 * 
	 * @param maintain
	 * @return MaintainShipmentIrregularity
	 * @throws CustomException
	 */

	@Override
	public IrregularityDetail add(IrregularityDetail maintain) throws CustomException {
		super.insertData("addIrregularity", maintain, sqlSessionShipment);
		if (maintain.getIrregularityType().equalsIgnoreCase("MSCA")) {
			ShipmentRemarksModel remarks = new ShipmentRemarksModel();
			remarks.setRemarkType("IRR");
			remarks.setShipmentNumber(maintain.getShipmentNumber());
			remarks.setShipmentdate(maintain.getShipmentDate());
			remarks.setShipmentType("AWB");
			if (!StringUtils.isEmpty(maintain.getFlightKey())) {
				remarks.setFlightId(new BigInteger(maintain.getFlightKey()));
			}
			remarks.setShipmentRemarks(getRemarksFormat(maintain));
			remarks.setHawbNumber(maintain.getHawbNumber());
			super.insertData("sqlInsertIRRShipmentRemarks", remarks, sqlSessionShipment);
		}
		return maintain;
	}

	private String getRemarksFormat(IrregularityDetail requestModel) throws CustomException {

	
		String formatDate2 =  super.fetchObject("fetchFlightSTA", requestModel, sqlSessionShipment);

		StringBuilder builder = new StringBuilder();
		builder.append(requestModel.getIrregularityType()).append("-");
		builder.append("P");
		builder.append(requestModel.getPieces());
		builder.append("W");
		if (requestModel.getWeight() == null) {
			requestModel.setWeight(BigDecimal.ZERO);
		}
		builder.append(requestModel.getWeight());
		builder.append("/");
		builder.append(requestModel.getOldflightKey());
		builder.append("/");
		builder.append(formatDate2).append("/");
		builder.append(requestModel.getRemark());
		return builder.toString();
	}

	/**
	 * Delete an irregularity to a shipment it will Return the deleted irregularity
	 * 
	 * @param maintain
	 * @return MaintainShipmentIrregularity
	 * @throws CustomException
	 */
	@Override
	public IrregularityDetail delete(IrregularityDetail maintain) throws CustomException {
		super.deleteData("deleteIrregularity", maintain, sqlSessionShipment);

		// Check the shipment is not an export then delete information from inward
		// service report if inward service report is not finalized
		if (!MultiTenantUtility.isTenantAirport(maintain.getSource())) {
			// Delete
			this.deleteData("sqlDeleteIrregularityFromInwardServiceReport", maintain, sqlSessionShipment);
		}
		return maintain;
	}

	/**
	 * fetchflightId based on flightKey and flightDate
	 * 
	 * @param maintain
	 * @return BigInteger
	 * @throws CustomException
	 */
	@Override
	public BigInteger getFlightId(IrregularityDetail irregularityInfo) throws CustomException {
		return super.fetchObject("fetchFlightIdInIrregularity", irregularityInfo, sqlSessionShipment);
	}

	@Override
	public Integer checkforduplicate(IrregularityDetail add) throws CustomException {
		return super.fetchObject("duplicateIrr", add, sqlSessionShipment);
	}

	@Override
	public Integer checkforduplicate1(IrregularityDetail update) throws CustomException {
		return super.fetchObject("duplicateIrrUp", update, sqlSessionShipment);
	}

	@Override
	public Boolean checkForFlightDetails(IrregularityDetail requestModel) throws CustomException {
		int isFlightValid = super.fetchObject("checkForImpFlightDetails", requestModel, sqlSessionShipment);
		return (isFlightValid > 0 ? true : false);
	}

	@Override
	public Boolean checkDocumentFlag(IrregularityDetail requestModel) throws CustomException {
		return super.fetchObject("checkDocumentInfo", requestModel, sqlSessionShipment);
	}

	@Override
	public Boolean checkAcceptanceFlag(IrregularityDetail requestModel)  throws CustomException {
		return super.fetchObject("checkAcceptanceFlag", requestModel, sqlSessionShipment);
	}
	
	@Override
	public String getFlightSegment(IrregularityDetail requestModel)  throws CustomException {
		return super.fetchObject("getFlightSegment", requestModel, sqlSessionShipment);
	}

}