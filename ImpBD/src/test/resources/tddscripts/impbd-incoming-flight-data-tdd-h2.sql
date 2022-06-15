-- Display Incoming Flight End --

INSERT INTO Flt_OperativeFlight (Flight_ID, CarrierCode,FlightNumber,FlightKey,FlightOriginDate,GroundHandlerCode,CreatedUser_Code,Created_DateTime)
     VALUES ('2535663','SQ','1234','SQ1234','2018-02-16','SATS','SYSADMIN','2018-02-16');

INSERT INTO Flt_OperativeFlight_Legs (Flight_ID,FlightBoardPoint,FlightOffPoint,FlightSegmentOrder,DateSTD,DateSTA,AircraftType,CreatedUser_Code,Created_DateTime)
     VALUES ('2535663','SIN','SIN',2,'2018-02-16','2018-02-16','320','SYSADMIN','2018-02-16');

INSERT INTO Flt_OperativeFlight_Segments (Flight_ID,FlightBoardPoint,FlightOffPoint,FlightSegmentOrder,DateSTD,DateSTA,CreatedUser_Code,Created_DateTime)
     VALUES('2535663','SIN','SIN',2,'2018-02-16','2018-02-16','SYSADMIN','2018-02-16');

INSERT INTO Imp_FlightEvents(FlightId,FirstULDTowedBy,FirstULDTowedAt,LastULDTowedBy,LastULDTowedAt,CreatedUserCode,CreatedDateTime,ShortTransitBulkShipmentExists)
     VALUES('2535663','MUNR','2018-02-16','MUNR','2018-02-16','SYSADMIN','2018-02-16',0);

-- Display Incoming Flight End --
