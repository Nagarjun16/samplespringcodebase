--Start of Queries for Validator Utility Project--Kindly do not delete and add your queries--
insert into Mst_Airport(AirportCode, AirportCityCode, AirportName) values('SIN', 'SIN', 'Singapore');

insert into Flt_OperativeFlight(Flight_ID, FlightKey, FlightOriginDate, boardingPoint, offPoint) values(1, 'SQ101', sysdate, 'SIN', 'HKG');
insert into Flt_OperativeFlight(Flight_ID, FlightKey, FlightOriginDate, boardingPoint, offPoint) values(2, 'SQ102', sysdate, 'HKG', 'SIN');

insert into Flt_OperativeFlight(Flight_ID, FlightKey, FlightOriginDate, boardingPoint, offPoint) values(3, 'SQ103', sysdate, 'SIN', 'ICN');
--
insert into Imp_FlightEvents(id, FlightId, FlightCompletedAt, FlightCompletedBy) values(1, 1, sysdate, 'Me');

insert into Exp_FlightEvents(id, FlightId, FlightCompletedAt, FlightCompletedBy) values(1, 2, sysdate, 'Me');

INSERT INTO Mst_Carrier
		(CarrierCode,CarrierFullName)
		VALUES
		('SQ', 'Singapore Airline');

insert into Uld_UldMaster(uld_key) values('AKE00951SQ');
insert into Uld_UldType(UldType ) values('AKE');
insert into Shipment_Master(WeightUnitCode,ShipmentId,ShipmentNumber,SVC,Origin,Destination,Pieces,Weight) values ('k',1138,'61255235224',0,'HKG','AAN',500,500.0);
insert into Shipment_OtherChargeInfo(ShipmentId,ChargeCode) values (1138,'PP');
insert into Com_ShipmentTemperatureLogEntry(ShipmentTemperatureLogEntryId,ShipmentId,Temperature,CapturedOn,Activity,ShipmentDescription,LocationCode,CreatedUserCode,CreatedDateTime,LastUpdatedUserCode,LastUpdatedDateTime) values ('41',1138,'2',sysdate,'0','GHI','UI','sysadmin',sysdate,'sysadmin',sysdate);
insert into EXP_EACCEPTANCEDOCUMENTINFORMATION(ShipmentNumber,RequestedTemperatureRange) values ('61255235224','2');

--End of Queries for Validator Utility Project--Kindly do not delete and add your queries--  