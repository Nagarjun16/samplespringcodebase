package com.ngen.cosys.application.service;

import com.ngen.cosys.framework.exception.CustomException;

public interface ApplicationSystemMonitoringService {
  String fetchEmailsforSystemMonitoring() throws CustomException;

String fetchEmailsforDlsUldFlightReport() throws CustomException;

}
