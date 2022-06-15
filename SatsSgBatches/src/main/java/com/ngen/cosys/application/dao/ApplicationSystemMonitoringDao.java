package com.ngen.cosys.application.dao;

import com.ngen.cosys.framework.exception.CustomException;

public interface ApplicationSystemMonitoringDao {
String fetchEmailsforSystemMonitoring() throws CustomException;

String fetchEmailsforDlsUldFlightReport()throws CustomException;
}
