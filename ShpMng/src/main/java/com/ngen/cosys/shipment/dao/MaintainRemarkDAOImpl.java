/**
 * MaintainRemarkDAOImpl.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          02 February, 2018   NIIT      -
 */
package com.ngen.cosys.shipment.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.helper.HAWBHandlingHelper;
import com.ngen.cosys.helper.model.HAWBHandlingHelperRequest;
import com.ngen.cosys.shipment.model.DeleteRemarkBO;
import com.ngen.cosys.shipment.model.FlightAssociatedRemarksBO;
import com.ngen.cosys.shipment.model.MaintainRemark;
import com.ngen.cosys.shipment.model.RequestSearchRemarksBO;
import com.ngen.cosys.shipment.model.ResponseSearchRemarksBO;
import com.ngen.cosys.shipment.model.ShipmentRemarksResponse;

@Repository("maintainRemarkDAO")
public class MaintainRemarkDAOImpl extends BaseDAO implements MaintainRemarkDAO {
	private static final String DELETE_ERROR = "CODE_ADMIN_DELETE";

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionShipment;
	
	@Autowired
	HAWBHandlingHelper helper;

	@Override
	public ResponseSearchRemarksBO fetch(RequestSearchRemarksBO searchParam) throws CustomException {
		ResponseSearchRemarksBO returnObj = new ResponseSearchRemarksBO();
		//checking if handled by house or master-----------------------
		HAWBHandlingHelperRequest req=new HAWBHandlingHelperRequest();
		req.setShipmentNumber(searchParam.getShipmentNumber());
		req.setShipmentDate(searchParam.getShipmentDate());
		 boolean handledByHouse=helper.isHandledByHouse(req);
		//-----------------------------------------------------------
		ResponseSearchRemarksBO awbDetails = super.fetchObject("sqlQueryFetchAwbDetails", searchParam,sqlSessionShipment);
		List<String> houseSHCList=super.fetchList("fetchHouseSHCLIST", awbDetails, sqlSessionShipment);
			// getting all the list which is having flight and no flight
		List<MaintainRemark> object =null;
		List<MaintainRemark> hwbRemarksobject =null;
		//If AWB is handled by house then both AWB remark fetch and HAWB remark fetch will happen
		if(handledByHouse) {	
		 object = super.fetchList("fetchShipmentRemark", searchParam,
					sqlSessionShipment);
		 hwbRemarksobject = super.fetchList("fetchHawbRemark", searchParam,
					sqlSessionShipment);
			} else {
				 object = super.fetchList("fetchShipmentRemarkMasters", searchParam,
						sqlSessionShipment);
			}
			
			
			List<MaintainRemark> withoutFlight = object.stream().filter(obj -> !Optional.ofNullable(obj.getFlightId()).isPresent()).collect(Collectors.toList());
			//Setting the withut flight renmarks which is in list into the object of maintainremark
			for(MaintainRemark value: withoutFlight) {
				value.setShipmentType(value.getRemarks().get(0).getShipmentType());
				value.setRemarkType(value.getRemarks().get(0).getRemarkType());
				value.setShipmentRemarks(value.getRemarks().get(0).getShipmentRemarks());
				value.setCreatedBy(value.getRemarks().get(0).getCreatedBy());
				value.setCreatedOn(value.getRemarks().get(0).getCreatedOn());
				value.setShipmentRemarkId(value.getRemarks().get(0).getShipmentRemarkId());
			}
			if(!Objects.isNull(awbDetails)) {
				returnObj.setShipmentNumber(awbDetails.getShipmentNumber());
				returnObj.setShipmentId(awbDetails.getShipmentId());
				returnObj.setOrigin(awbDetails.getOrigin());
				returnObj.setDestination(awbDetails.getDestination());
				returnObj.setNatureOfGoods(awbDetails.getNatureOfGoods());
				returnObj.setSpecialHandlingCode(awbDetails.getSpecialHandlingCode());
				returnObj.setPieces(awbDetails.getPieces());
				returnObj.setWeight(awbDetails.getWeight());
				returnObj.setShipmentHouseInfo(awbDetails.getShipmentHouseInfo());
			}else {
				returnObj.setShipmentNumber(searchParam.getShipmentNumber());
			}
			
			if(!Objects.isNull(houseSHCList)) {
				returnObj.setSpecialHandlingCodeHAWB(houseSHCList);
			}
			// Setting HAWB remarks
			if(handledByHouse) {
			List<MaintainRemark> hwbRemark = hwbRemarksobject;
			List<MaintainRemark> withoutFlightHAWBRemarks= hwbRemark.stream().filter(obj -> !Optional.ofNullable(obj.getFlightId()).isPresent()).collect(Collectors.toList());
			if(!CollectionUtils.isEmpty(withoutFlightHAWBRemarks)) {
				hwbRemark=withoutFlightHAWBRemarks;
			}
			if (!CollectionUtils.isEmpty(hwbRemark)) {
				List<MaintainRemark> withFlight = hwbRemark.stream().filter(obj -> Optional.ofNullable(obj.getFlightId()).isPresent()).collect(Collectors.toList());
			setSegmentForFlight(withFlight);}

			for(MaintainRemark hwbvalue: hwbRemark) {
				hwbvalue.setShipmentType(hwbvalue.getRemarks().get(0).getShipmentType());
				hwbvalue.setRemarkType(hwbvalue.getRemarks().get(0).getRemarkType());
				hwbvalue.setShipmentRemarks(hwbvalue.getRemarks().get(0).getShipmentRemarks());
				hwbvalue.setCreatedBy(hwbvalue.getRemarks().get(0).getCreatedBy());
				hwbvalue.setCreatedOn(hwbvalue.getRemarks().get(0).getCreatedOn());
				hwbvalue.setShipmentRemarkId(hwbvalue.getRemarks().get(0).getShipmentRemarkId());
			}
			returnObj.setHwbRemarks(hwbRemark);
			//returnObj.setHwbFlightBasedRemarks(setFlightRemarks(hwbRemarksobject));
			}
			returnObj.setNoFlightRemarks(withoutFlight);
			returnObj.setFlightBasedRemarks(setFlightRemarks(object));
		return returnObj;
	}

	private List<FlightAssociatedRemarksBO> setFlightRemarks(List<MaintainRemark> result)
			throws CustomException {
		List<FlightAssociatedRemarksBO> returnFlightList = new ArrayList<>();
		
		
		List<MaintainRemark> withFlight = result.stream().filter(obj -> Optional.ofNullable(obj.getFlightId()).isPresent()).collect(Collectors.toList());

		FlightAssociatedRemarksBO remarksBo = null;
		long localflightId = -1;
        // Concating the segment
		setSegmentForFlight(withFlight);
		/*List<MaintainRemark> unique = flightRemakrsList.stream()
	              .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingInt(MaintainRemark::getShipmentRemarkId))),
	                                         ArrayList::new));*/
		// getting those values which are having the segment as not null
		List<MaintainRemark> resultaData= withFlight.stream() 
				.filter(line -> null != line.getSegments()) 
				.collect(Collectors.toList());
        for(int i = 0; i< resultaData.size(); i++) {
        	MaintainRemark flightRemark = resultaData.get(i);
        	if (localflightId != flightRemark.getFlightId()) {
				// push old remarks BO into return Arraylist
				if (-1 != localflightId) {
					returnFlightList.add(remarksBo);
				}
				// Flight id to be assigned into running comparator
				localflightId = flightRemark.getFlightId();
				// constitute new remarks BO
				remarksBo = new FlightAssociatedRemarksBO();
				remarksBo.setFlightId(localflightId);
				remarksBo.setFlightKey(flightRemark.getFlightKey());
				remarksBo.setFlightDate(flightRemark.getFlightDate());
				remarksBo.setFlightSource(flightRemark.getFlightSource());
				remarksBo.setFlightDestination(flightRemark.getFlightDestination());
				remarksBo.setSegments(flightRemark.getSegments());
				List<MaintainRemark> remarksList = new ArrayList<>();
				for(ShipmentRemarksResponse value1: flightRemark.getRemarks()) {
					MaintainRemark remarks = new MaintainRemark();
					remarks.setShipmentRemarkId(value1.getShipmentRemarkId());
					remarks.setShipmentType(value1.getShipmentType());
					remarks.setRemarkType(value1.getRemarkType());
					remarks.setShipmentRemarks(value1.getShipmentRemarks());
					remarks.setCreatedBy(value1.getCreatedBy());
					remarks.setCreatedOn(value1.getCreatedOn());
					remarksList.add(remarks);
				}
				remarksBo.setRemarks(remarksList);
				//remarksBo.getRemarks().addAll(remarksList);
			} else {
				if (null == remarksBo) {
					remarksBo = new FlightAssociatedRemarksBO();
				}
				if (CollectionUtils.isEmpty(remarksBo.getRemarks())) {
					remarksBo.setRemarks(Collections.emptyList());
				}
				remarksBo.getRemarks().add(flightRemark);
			}
        }
		/*for (MaintainRemark flightRemark : resultaData) {
			
		}*/
		// push last remarksBO into List
		returnFlightList.add(remarksBo);
		return returnFlightList;
	}

	private List<MaintainRemark> setSegmentForFlight(List<MaintainRemark> flightRemakrsList) {
		String segmentts = "";
		for (int i = 0; i< flightRemakrsList.size(); i++) {
			if(i > 0 && !flightRemakrsList.get(i).getFlightId().equals(flightRemakrsList.get(i-1).getFlightId()) ) {
				segmentts = "";
			}
               
			MaintainRemark obj = flightRemakrsList.get(i);
			if(obj.getSegmentOrder() == 1) {
				if(!StringUtils.isEmpty(segmentts)) {
					segmentts = obj.getFlightSource()+'-'+obj.getFlightDestination()+'-'+segmentts;
				}
				else {
					segmentts = obj.getFlightSource()+'-'+obj.getFlightDestination();
				}
				flightRemakrsList.get(i).setSegments(segmentts);
			} else {
				if(StringUtils.isEmpty(segmentts))
				segmentts = obj.getFlightDestination()+segmentts;
				else 
					segmentts = obj.getFlightDestination()+'-'+segmentts; 	
			}
		}
		
		return flightRemakrsList;
	}

	@Override
	public void insert(List<MaintainRemark> paramRemarksList) throws CustomException {
		super.insertData("insertMaintainRemarkDetail", paramRemarksList, sqlSessionShipment);
	}
	//To insert HAWB Remarks
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_MAINTAIN_REMARK) 
	public void insertHWB(List<MaintainRemark> paramRemarksList) throws CustomException {
		super.insertData("insertMaintainHWBRemarkDetail", paramRemarksList, sqlSessionShipment);
	}

	
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_MAINTAIN_REMARK) 
	public void delete(MaintainRemark rmk) throws CustomException {
		super.deleteData("deleteMaintainRemark", rmk, sqlSessionShipment);
	}
	
	@Override
	public MaintainRemark getRemarksDetails(int remarkId) throws CustomException {
		return super.fetchObject("getRemarksDetails", remarkId, sqlSessionShipment);
	}

}