--Start of Queries for Validator Utility Project--Kindly do not delete and add your queries--

INSERT INTO Flt_OperativeFlight 
(Flight_ID, FlightNumber, FlightKey,FlightOriginDate,GroundHandlerCode,CreatedUser_Code,Created_DateTime)
VALUES
(10, '0012','12H0012',SYSDATE,'SATS','SYSADMIN',SYSDATE);

INSERT INTO Imp_HandOver 
(FlightId,HandedOverAt,TractorNumber,HandedOverBy,StartedAt,CompletedAt,CreatedUserCode,CreatedDateTime,LastUpdatedUserCode,LastUpdatedDateTime,TripId)
VALUES(10,'EEE','E','EE',SYSDATE,SYSDATE,'SYSADMIN',SYSDATE,'SYSADMIN',SYSDATE,1);
		

INSERT INTO Imp_HandOverContainerTrolleyInformation 
(ImpHandOverId,ContainerTrolleyNumber,
UsedAsTrolley,CapturedManual,SourceOfInformation,CreatedUserCode,CreatedDateTime,LastUpdatedUserCode,LastUpdatedDateTime)
VALUES(3,'AKE88889SQ','0','0','ABC','SYSADMIN',SYSDATE,'SYSADMIN',SYSDATE);

--End of Queries for Validator Utility Project--Kindly do not delete and add your queries--

 