
INSERT INTO Flt_OperativeFlight(Flight_ID, FlightKey, FlightOriginDate)
VALUES ('1','SQ202',SYSDATE);

	INSERT INTO Flt_OperativeFlight_Legs (Flight_ID,FlightOffPoint,FlightSegmentOrder ,DateSTD ,DateSTA ,DateETD ,DateETA ,DateATD ,DateATA)
     VALUES ('1','SIN','1',SYSDATE,SYSDATE,SYSDATE,SYSDATE,SYSDATE,SYSDATE);

	 INSERT INTO Imp_RampCheckIn
           (FlightId
           ,ULDNumber
           ,TransferType
           ,ContentCode)
     VALUES
           (1
           ,'AKE0007SQ'
           ,'TT'
           ,'C');

	INSERT INTO Imp_HandOver (ImpHandOverId, FlightId,HandedOverAt,TractorNumber,HandedOverBy)
	VALUES(1, 1,'EEE','E','EE');
			
	INSERT INTO Imp_HandOverContainerTrolleyInformation 
	(ImpHandOverId,ContainerTrolleyNumber,
	UsedAsTrolley,CapturedManual,SourceOfInformation,CreatedUserCode,CreatedDateTime,LastUpdatedUserCode,LastUpdatedDateTime)
	VALUES(1,'AKE0007SQ','0','0','ABC','SYSADMIN',SYSDATE,'SYSADMIN',SYSDATE);
	
	INSERT INTO Mst_SpecialHandlingCode
(SpecialHandlingCode, SpecialHandlingCodeDescription)
VALUES ('VAL', 'Valuable');

INSERT INTO Imp_FlightEvents
           (FlightId
           ,CreatedUserCode
           ,CreatedDateTime
           ,LastUpdatedUserCode
           ,LastUpdatedDateTime
           ,BulkShipmentsExists)
     VALUES
           (1
           ,'ADMIN'
           ,SYSDATE
           ,'ADMIN'
           ,SYSDATE
           ,'1');
           
INSERT INTO Uld_ContentCodes
           (ContentCode
           ,ContentCodeDescription
           ,ApronCargoLocation
           ,CreatedUser_Code
           ,Created_DateTime
           ,LastUpdatedUser_Code
           ,LastUpdated_DateTime
           ,PriorityCode
           ,SortingOrder)
     VALUES
           ('C'
           ,'CARGO'
           ,'C'
           ,'ADMIN'
           ,SYSDATE
           ,'ADMIN'
           ,SYSDATE
           ,1
           ,1
          )
