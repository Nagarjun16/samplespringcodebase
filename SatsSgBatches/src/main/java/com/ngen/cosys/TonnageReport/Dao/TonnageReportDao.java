package com.ngen.cosys.TonnageReport.Dao;



import java.util.List;


import com.ngen.cosys.TonnageReport.model.InsertRequest;
import com.ngen.cosys.TonnageReport.model.TonnageReportRequest;
import com.ngen.cosys.framework.exception.CustomException;

public interface TonnageReportDao {

	void savereportdata(InsertRequest insertdata) throws CustomException;

	List<String> searchcarrriervalues(TonnageReportRequest tonnageReportRequest) throws CustomException;

	String searchentityvaluecombination(InsertRequest insertdata) throws CustomException;

	Integer searchdatacount(InsertRequest insertdata)  throws CustomException;

	void updatereportdata(InsertRequest insertdata) throws CustomException;

	//List<String> searchautopublishvalues(InsertRequest insertdata) throws CustomException;

	//List<String> getEmailAddresses() throws CustomException;

	//void savereportdata(Object data)throws CustomException;

}
