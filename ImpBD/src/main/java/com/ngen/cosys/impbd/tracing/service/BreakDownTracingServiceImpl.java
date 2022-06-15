package com.ngen.cosys.impbd.tracing.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.constants.HardcodedParam;
import com.ngen.cosys.impbd.dao.ArrivalManifestDao;
import com.ngen.cosys.impbd.model.ArrivalManifestByFlightModel;
import com.ngen.cosys.impbd.tracing.dao.BreakDownTracingDAO;
import com.ngen.cosys.impbd.tracing.model.BreakDownTracingFlightModel;
import com.ngen.cosys.impbd.tracing.model.BreakDownTracingFlightSegmentModel;
import com.ngen.cosys.impbd.tracing.model.BreakDownTracingShipmentModel;
import com.ngen.cosys.impbd.tracing.model.BreakDownTracingUldModel;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Service
public class BreakDownTracingServiceImpl implements BreakDownTracingService {

   @Autowired
   private BreakDownTracingDAO dao;
   
   @Autowired
   private ArrivalManifestDao arrivalDao;

   @Override
   public List<BreakDownTracingFlightModel> fetchTracingList(BreakDownTracingFlightModel flightBreakdownData)
         throws CustomException {
      List<BreakDownTracingFlightModel> tracingList = dao.fetchTracingList(flightBreakdownData);
      if (CollectionUtils.isEmpty(tracingList)) {
         throw new CustomException("NORECORD", null, ErrorType.ERROR);
      }
      LinkedHashMap<String, List<BreakDownTracingShipmentModel>> shipmentMap = new LinkedHashMap<String, List<BreakDownTracingShipmentModel>>();
      for (BreakDownTracingShipmentModel shipmentDetails : tracingList.get(0).getShipments())  
      {   
    	  if(!MultiTenantUtility.isTenantAirport(shipmentDetails.getDestination()))
    	  {
    		  shipmentDetails.setLocalShipmentFlag(1);
    	  }
    	  
    	  if(shipmentMap.get(shipmentDetails.getOrigin())==null) {
    		  List<BreakDownTracingShipmentModel> shipmentList = new ArrayList<BreakDownTracingShipmentModel>();
    		  shipmentList.add(shipmentDetails);
    		  shipmentMap.put(shipmentDetails.getOrigin(), shipmentList);
    	  }
    	  else {
    		  List<BreakDownTracingShipmentModel> shipmentList;
    		  shipmentList = shipmentMap.get(shipmentDetails.getOrigin());
    		  
               shipmentList.add(shipmentDetails);
               shipmentMap.put(shipmentDetails.getOrigin(), shipmentList);
               
    	  }
      }
      List<BreakDownTracingShipmentModel> shipmentList1 = new ArrayList<BreakDownTracingShipmentModel>();
      for (String key : shipmentMap.keySet()) {
    	  List<BreakDownTracingShipmentModel> shipmentList = new ArrayList<BreakDownTracingShipmentModel>();
    	  shipmentList = shipmentMap.get(key);
    	  Collections.sort(shipmentList, (s1, s2) -> s1.getLocalShipmentFlag() - s2.getLocalShipmentFlag());
    	  shipmentList1.addAll(shipmentList); 
    	}
      tracingList.get(0).setShipments(shipmentList1);
      
      for (BreakDownTracingFlightModel flightCargoDetails : tracingList) {
    	  ArrivalManifestByFlightModel arrivalManifestData = new ArrivalManifestByFlightModel();
    	  arrivalManifestData.setFlightId(flightCargoDetails.getFlightId());
    	  flightCargoDetails.setFlightStatus(arrivalDao.fetchFlightStatus(arrivalManifestData));
         /*for (BreakDownTracingFlightSegmentModel tracingSegmantData : flightCargoDetails.getSegmentData()) {
            BigInteger intactCount = BigInteger.ZERO;
            BigInteger breakCount = BigInteger.ZERO;
            BigInteger offloadCount = BigInteger.ZERO;
            BigInteger surplusCount = BigInteger.ZERO;
            BigInteger damageCount = BigInteger.ZERO;
            tracingSegmantData.setSegmentULDCount(dao.getUldCount(tracingSegmantData));
            for (BreakDownTracingUldModel tracingUldData : tracingSegmantData.getUldData()) {

               if (tracingUldData.getUldDamage()) {
                  damageCount = damageCount.add(BigInteger.ONE);
               }
               if (tracingUldData.getHandlingMode().equalsIgnoreCase(HardcodedParam.BREAK.toString())) {
                  breakCount = breakCount.add(BigInteger.ONE);
               } else if (tracingUldData.getHandlingMode().equalsIgnoreCase(HardcodedParam.NOBREAK.toString())) {
                  intactCount = intactCount.add(BigInteger.ONE);
               }
               for (BreakDownTracingShipmentModel tracingShipmentData : tracingUldData.getShipmentInULD()) {
                  if (tracingShipmentData.getSurplusFlag()) {
                     surplusCount = surplusCount.add(BigInteger.ONE);
                  } else if (tracingShipmentData.getOffLoadedFlag()) {
                     offloadCount = offloadCount.add(BigInteger.ONE);
                  }
               }
            }
            tracingSegmantData.setIntact(intactCount);
            tracingSegmantData.setBreakULD(breakCount);
            tracingSegmantData.setOffload(offloadCount);
            tracingSegmantData.setSurplus(surplusCount);
            tracingSegmantData.setDamage(damageCount);
         }*/
      }
      return tracingList;
   }

}