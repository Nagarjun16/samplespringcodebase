package com.ngen.cosys.satssg.breakdown.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.breakdown.dao.BreakDownSummaryDao;
import com.ngen.cosys.satssg.breakdown.model.BreakDownSummary;
import com.ngen.cosys.satssg.breakdown.model.BreakDownSummaryModel;
import com.ngen.cosys.satssg.breakdown.model.BreakDownSummaryProvider;
import com.ngen.cosys.satssg.breakdown.model.BreakDownSummaryTonnageHandledModel;
import com.ngen.cosys.satssg.breakdown.model.FlightDetails;

@Service
@Transactional
public class BreakDownSummaryServiceImpl implements BreakDownSummaryService {
	
	@Autowired
	private BreakDownSummaryDao breakDownSummaryDao;
	

	@Override
	public void getFlightDetails() throws CustomException {
		
		List<FlightDetails> flightData = breakDownSummaryDao.getFlightDetails();
		if(CollectionUtils.isEmpty(flightData)) {
			throw new CustomException("NORECORD",null,ErrorType.ERROR);
		}
		for(FlightDetails flightInfo : flightData) {
			BigInteger temp=flightInfo.getFctTime().subtract(flightInfo.getAtaTime());	
			flightInfo.setDelay(temp.subtract(flightInfo.getStpTime()));
			if(flightInfo.getDelay().compareTo(BigInteger.ONE) ==-1) {				
				getSummaryDetails(flightInfo,null);
			}else if(flightInfo.getDelay().compareTo(BigInteger.ONE) ==1) {
				BreakDownSummaryProvider serviceContractor=breakDownSummaryDao.getServiceContractorDetails(flightInfo);
				getSummaryDetails(flightInfo,serviceContractor);
			}
			
		}
	}


	@Override
	public void getSummaryDetails(FlightDetails flightData,BreakDownSummaryProvider serviceContractor) throws CustomException {
		//BreakDownSummaryModel summaryData= breakDownSummaryDao.getSummaryDetails(flightData);
		BreakDownSummary summaryDatas = new BreakDownSummary();
		summaryDatas.setBreakDownStaffGroup("AeroLog");
		summaryDatas.setApprovedLDWaiveApprovedBy("AUTO");		
		summaryDatas.setReasonForWaive("AUTO");	
		summaryDatas.setFlightId(flightData.getFlightId());
		if(Objects.nonNull(serviceContractor)) {
			summaryDatas.setFeedbackForStaff(new BigInteger("1"));
			if(Objects.nonNull(serviceContractor.getExemptTime())) {
				if(flightData.getDelay().compareTo(serviceContractor.getExemptTime()) ==-1) {
					autoClose(summaryDatas,flightData);
				}
			}else {
				autoClose(summaryDatas,flightData);
			}			
			
		}else if(flightData.getDelay().compareTo(BigInteger.ONE) ==-1) {		
		summaryDatas.setFeedbackForStaff(new BigInteger("5"));
		autoClose(summaryDatas,flightData);
		
		}
		
	}


	@Override
	public void createSummary(BreakDownSummary summaryData) throws CustomException {
		 if(breakDownSummaryDao.checkSummaryExists(summaryData)) {
			 breakDownSummaryDao.updateBreakDownSummary(summaryData);
		}else {
			breakDownSummaryDao.createSummary(summaryData);
		}
		 
	}
	
	public void createTonnageSummary(BreakDownSummaryTonnageHandledModel tonnageData)throws CustomException{
		breakDownSummaryDao.createTonnageSummary(tonnageData);
	}
	
	
	public void autoClose(BreakDownSummary summaryDatas,FlightDetails flightData)throws CustomException {
		createSummary(summaryDatas);
//		if(!ObjectUtils.isEmpty(summaryData)) {
//			if(!CollectionUtils.isEmpty(summaryData.getTonnageHandlingInfo())) {
//				for(BreakDownSummaryTonnageHandledModel tonnageInfo : summaryData.getTonnageHandlingInfo()) {
//					createTonnageSummary(tonnageInfo);
//				}
//			}			
//		}
		breakDownSummaryDao.updateFlightComplete(flightData);
	}

}
