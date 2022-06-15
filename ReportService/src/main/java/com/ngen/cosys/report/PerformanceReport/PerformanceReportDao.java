package com.ngen.cosys.report.PerformanceReport;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.report.service.poi.model.PerformanceReportModel;

public interface PerformanceReportDao {

	List<PerformanceReportModel> getPerofrmanceReport(PerformanceReportModel requestModel) throws CustomException;

}
