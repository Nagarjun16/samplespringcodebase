package com.ngen.cosys.TonnageReport.Service;


import java.util.List;


import com.ngen.cosys.TonnageReport.model.InsertRequest;
import com.ngen.cosys.TonnageReport.model.TonnageReportRequest;
import com.ngen.cosys.framework.exception.CustomException;

public interface TonnageReportService {

	void save(InsertRequest insertdata) throws CustomException;

	List<String> searchcarrier(TonnageReportRequest tonnageReportRequest) throws CustomException;

	String searchentitycombination(InsertRequest insertdata) throws CustomException;

	Integer searchdatacount(InsertRequest insertdata) throws CustomException;

	void update(InsertRequest insertdata) throws CustomException;

	//List<String> searchautopublish(InsertRequest insertdata) throws CustomException;
	
}
