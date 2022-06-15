package com.ngen.cosys.billing.sap.airline.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ngen.cosys.framework.exception.CustomException;

public interface SapAirlineService {
	String SENT = "SENT";
	String ERROR = "ERROR";
	public String sendCosysMessageToSAP(String fileName, String message,String MessageType) throws CustomException;
	public BigInteger logOutgoingMessage(String interfaceName, String messageType, String carrierCode,
			String flightNumber, LocalDateTime flightDate, String shipmentNumber, LocalDateTime shipmentDate,
			String payload, String status,String interfaceFileName,String hawbNumber);
	public void logOugoingErrorMessage(BigInteger messageLogId, String errorMessage);
	public BigInteger logInterfaceIncomingMessage(String interfaceName, String messageType, String carrierCode,
			String flightNumber, LocalDateTime flightDate, String shipmentNumber, LocalDateTime shipmentDate,
			String payload, String status,String interfaceFileName,String hawbNumber);
	public void logInterfaceIncomingErrorMessage(BigInteger messageLogId, String errorMessage, String lineItem);
	public void updateOutgoingMessageLog(BigInteger messageLogId, String carrierCode, String flightNumber,
			LocalDateTime flightDate, String status);
	public String getStringFromLocalDate(LocalDateTime date,String Dataformat);
}
