----Start of Mst_Airport------------------
insert into Mst_Airport(AirportCode, AirportCityCode, AirportName) values('SIN', 'SIN', 'Singapore');
insert into Mst_Airport(AirportCode, AirportCityCode, AirportName) values('BLR', 'BLR', 'Bengaluru');
insert into Mst_Airport(AirportCode, AirportCityCode, AirportName) values('HKG', 'HKG', 'Hongkong');
--End of Mst_Airport----------------------

--Start of Queries for Mst_SpecialHandlingCode--
insert into Mst_SpecialHandlingCode (SpecialHandlingCode, StartDate, SpecialHandlingCodeDescription, 
      IataFlag, SpecialHandlingPriority, EndDate)
    values ('AAB', '2017-12-11', 'AAS WQD WDW444', 0 ,0, '2017-12-11');
insert into Mst_SpecialHandlingCode (SpecialHandlingCode, StartDate, SpecialHandlingCodeDescription, 
      IataFlag, SpecialHandlingPriority, EndDate)
    values ('BAO', '2017-12-11', 'HANDLING CODE (BA)', 0 ,0, '2017-12-11');

insert into Mst_SpecialHandlingCode (SpecialHandlingCode, StartDate, SpecialHandlingCodeDescription, 
      IataFlag, SpecialHandlingPriority, EndDate)
    values ('CRX', '2017-12-11', 'SQ COOLRIDER', 0 ,0, '2017-12-11');

insert into Mst_SpecialHandlingCode (SpecialHandlingCode, StartDate, SpecialHandlingCodeDescription, 
      IataFlag, SpecialHandlingPriority, EndDate)
    values ('DGR', '2017-12-11', 'TEST1 DGR FOR IMPORT STORAGE', 0 ,0, '2017-12-11');

insert into Mst_SpecialHandlingCode (SpecialHandlingCode, StartDate, SpecialHandlingCodeDescription, 
      IataFlag, SpecialHandlingPriority, EndDate)
    values ('EAT', '2017-12-11', 'FOODSTUFF', 0 ,0, '2017-12-11');

insert into Mst_SpecialHandlingCode (SpecialHandlingCode, StartDate, SpecialHandlingCodeDescription, 
      IataFlag, SpecialHandlingPriority, EndDate)
    values ('FRA', '2017-12-11', 'FRAGILE GOODS', 0 ,0, '2017-12-11');
insert into Mst_SpecialHandlingCode (SpecialHandlingCode, StartDate, SpecialHandlingCodeDescription, 
      IataFlag, SpecialHandlingPriority, EndDate)
    values ('GEN', '2017-12-11', 'GENERAL GOODS', 0 ,0, '2017-12-11');
insert into Mst_SpecialHandlingCode (SpecialHandlingCode, StartDate, SpecialHandlingCodeDescription, 
      IataFlag, SpecialHandlingPriority, EndDate)
    values ('HEG', '2017-12-11', 'HATCHING EGGS', 0 ,0, '2017-12-11');
--End of Queries for Mst_SpecialHandlingCode--

insert into Flt_OperativeFlight(Flight_ID,FlightKey,FlightNumber,FlightOriginDate,FlightType,CarrierCode) values(1,'SQ1234','8985','2018-01-30 00:00:00.000','Freighter','1R');

insert into Flt_OperativeFlight(Flight_ID,FlightKey,FlightNumber,FlightOriginDate,FlightType,CarrierCode) values(3,'SM1234','8985','2018-01-30 00:00:00.000','Freighter','SQ');

insert into Flt_OperativeFlight_Legs (Flight_ID,FlightBoardPoint, FlightOffPoint, FlightSegmentOrder, DateSTD, DateSTA, DateETD, DateETA)
values ( 1,'SIN', 'SIN', 1, '2018-01-30 00:00:00.000', NULL, '2018-01-30 10:00:00.000' , NULL);

insert into Flt_OperativeFlight_Segments(Flight_ID,FlightBoardPoint,FlightOffPoint,DateSTD)
values(1,'SIN','BLR','2018-02-13 23:59:59');

INSERT INTO Imp_ArrivalManifestByFlight (AircraftRegCode ,CreatedUserCode ,CreatedDateTime ,LastUpdatedUserCode ,LastUpdatedDateTime ,FlightId)
     VALUES ('12345' ,'SYSADMIN' ,CURRENT_TIMESTAMP ,'SYSADMIN' ,CURRENT_TIMESTAMP ,1);
		   
INSERT INTO Imp_ArrivalManifestBySegment (ImpArrivalManifestByFlightId ,CreatedUserCode ,CreatedDateTime ,LastUpdatedUserCode ,LastUpdatedDateTime ,FlightSegmentId)
     VALUES (1 ,'SYSADMIN' ,CURRENT_TIMESTAMP ,'SYSADMIN' ,CURRENT_TIMESTAMP ,1);
		   
INSERT INTO Imp_ArrivalManifestULD (UldType ,UldSerialNumber ,UldOwnerCode ,ULDNumber ,UldLoadingIndicator ,UldRemarks ,VolumeAvailableCode ,CreatedUserCode ,CreatedDateTime ,LastUpdatedUserCode ,LastUpdatedDateTime ,ImpArrivalManifestBySegmentId)
     VALUES ('AAF' ,null ,'SIN' ,'AAF12354SQ' ,null ,'Test' ,1 ,'SYSADMIN' ,CURRENT_TIMESTAMP ,'SYSADMIN' ,CURRENT_TIMESTAMP ,1);

INSERT INTO Imp_ArrivalManifestShipmentInfo (ImpArrivalManifestULDId ,Origin ,Destination ,ShipmentDescriptionCode ,Piece ,WeightUnitCode ,Weight ,VolumeUnitCode ,VolumeAmount ,DensityIndicator ,DensityGroupCode ,TotalPieces ,NatureOfGoodsDescription ,MovementPriorityCode ,CustomsOriginCode ,CreatedUserCode ,CreatedDateTime ,LastUpdatedUserCode ,LastUpdatedDateTime ,CustomsReference ,ShipmentNumber ,ShipmentDate)
     VALUES (1 ,'AAQ' ,'SIN' ,'S' ,12 ,'K' ,35.4 ,null ,null ,null ,null ,50 ,'PERISHABLE' ,null ,null ,'SYSADMIN' ,CURRENT_TIMESTAMP ,'SYSADMIN' ,CURRENT_TIMESTAMP ,null ,'96008597648' ,CURRENT_TIMESTAMP);

INSERT INTO Imp_ArrivalManifestShipmentDimensionInfo (ImpArrivalManifestShipmentInfoId ,WeightUnitCode ,Weight ,MeasurementUnitCode ,DimensionLength ,DimensionWIdth ,DimensionHeight ,NumberOfPieces ,CreatedUserCode ,CreatedDateTime ,LastUpdatedUserCode ,LastUpdatedDateTime ,TransactionSequenceNo)
     VALUES (1 ,'K' ,32.4 ,'CMT' ,12.5 ,12.4 ,12.3 ,10 ,'SYSADMIN' ,CURRENT_TIMESTAMP ,'SYSADMIN' ,CURRENT_TIMESTAMP ,1);
		   
INSERT INTO Imp_ArrivalManifestShipmentOCI (ImpArrivalManifestShipmentInfoId ,CountryCode ,InformationIdentifier ,CSRCIIdentifier ,SCSRCInformation ,CreatedUserCode ,CreatedDateTime ,LastUpdatedUserCode ,LastUpdatedDateTime ,TransactionSequenceNo)
     VALUES (1 ,'AC' ,null ,null ,null ,'SYSADMIN' ,CURRENT_TIMESTAMP ,'SYSADMIN' ,CURRENT_TIMESTAMP ,1);

INSERT INTO Imp_ArrivalManifestShipmentOnwardMovement (ImpArrivalManifestShipmentInfoId ,AirportCityCode ,CarrierCode ,FlightNumber ,DepartureDate ,CreatedUserCode ,CreatedDateTime ,LastUpdatedUserCode ,LastUpdatedDateTime ,TransactionSequenceNo)
     VALUES (1 ,'SIN' ,'2G' ,'21234' ,CURRENT_TIMESTAMP,'SYSADMIN' ,CURRENT_TIMESTAMP ,'SYSADMIN' ,CURRENT_TIMESTAMP ,1);

INSERT INTO Imp_ArrivalManifestOtherSvcInfo (ImpArrivalManifestShipmentInfoId ,ServiceInformation ,CreatedUserCode ,CreatedDateTime ,LastUpdatedUserCode ,LastUpdatedDateTime ,TransactionSequenceNo)
     VALUES (1 ,'Test' ,'SYSADMIN' ,CURRENT_TIMESTAMP ,'SYSADMIN' ,CURRENT_TIMESTAMP ,1);		   
		   
		   