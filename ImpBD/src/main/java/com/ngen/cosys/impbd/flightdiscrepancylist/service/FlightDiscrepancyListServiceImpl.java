package com.ngen.cosys.impbd.flightdiscrepancylist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.flightdiscrepancylist.dao.FlightDiscrepancyListDAO;
import com.ngen.cosys.impbd.flightdiscrepancylist.model.FlightDiscrepancyListModel;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class FlightDiscrepancyListServiceImpl implements FlightDiscrepancyListService {

   @Autowired
   private FlightDiscrepancyListDAO flightDiscrepancyListDAO;

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public FlightDiscrepancyListModel fetch(FlightDiscrepancyListModel searchDiscrepancyList) throws CustomException {

      FlightDiscrepancyListModel returnObj = flightDiscrepancyListDAO.fetch(searchDiscrepancyList);
      if (!StringUtils.isEmpty(searchDiscrepancyList.getSegment())) {
         returnObj.setSegment(searchDiscrepancyList.getSegment());
      }

      if (StringUtils.isEmpty(returnObj)) {
         throw new CustomException("NOFLIGHT", "No flights arriving for specific input", ErrorType.ERROR);
      } else {

         Integer count1 = flightDiscrepancyListDAO.checkForFdlStatus(returnObj.getFlightId().longValue());
         returnObj.setStatus(count1);

         int count = 1;
         for (int j = 0; j < returnObj.getFlightDiscrepancyList().size(); j++) {
            returnObj.getFlightDiscrepancyList().get(j).setSno((count++));
         }
      }
      return returnObj;
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
   public void updateFDLVersion(FlightDiscrepancyListModel requestModel) throws CustomException {
      this.flightDiscrepancyListDAO.updateFDLVersion(requestModel);
   }
   
   @Override
   public FlightDiscrepancyListModel getFDLVersion(FlightDiscrepancyListModel requestModel) throws CustomException {
		
	  return this.flightDiscrepancyListDAO.getFDLVersion(requestModel);
   }

}