package com.ngen.cosys.TonnageReport.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.TonnageReport.Dao.TonnageReportDao;
import com.ngen.cosys.TonnageReport.model.InsertRequest;
import com.ngen.cosys.TonnageReport.model.TonnageDetails;
import com.ngen.cosys.TonnageReport.model.TonnageReportRequest;
import com.ngen.cosys.framework.exception.CustomException;

@Service
public class TonnageReportServiceImpl  implements TonnageReportService{
 
	 @Autowired
	   TonnageReportDao tonnageReportDao;

	@Override
	public void save(InsertRequest insertdata) throws CustomException {
		tonnageReportDao.savereportdata(insertdata);
		
	}

	@Override
	public List<String> searchcarrier(TonnageReportRequest tonnageReportRequest) throws CustomException {
		return tonnageReportDao.searchcarrriervalues(tonnageReportRequest);
	}

	@Override
	public String searchentitycombination(InsertRequest insertdata) throws CustomException {
		return tonnageReportDao.searchentityvaluecombination(insertdata);
		
	}

	@Override
	public Integer searchdatacount(InsertRequest insertdata) throws CustomException {
		return tonnageReportDao.searchdatacount(insertdata);
	}

	@Override
	public void update(InsertRequest insertdata) throws CustomException {
		tonnageReportDao.updatereportdata(insertdata);
		
	}

/*	@Override
	public List<String> searchcarrier(String carrierparam) throws CustomException {
		List<String> carriervalue =	tonnageReportDao.searchcarrriervalues(carrierparam)
		return carriervalue;
	}*/

/*	@Override
	public List<String> searchautopublish(InsertRequest insertdata)  throws CustomException {
		return tonnageReportDao.searchautopublishvalues(insertdata);
	}*/
	

}
