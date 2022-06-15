package com.ngen.cosys.report.service;

import java.io.IOException;
import java.io.OutputStream;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.report.service.poi.model.PerformanceReportModel;

public interface PerformanceReportService {
	OutputStream getPerformancereport(PerformanceReportModel requestModel) throws CustomException, IOException;

}
