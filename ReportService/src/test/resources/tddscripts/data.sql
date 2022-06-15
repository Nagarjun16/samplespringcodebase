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

--End of Queries for Validator Utility Project--Kindly do not delete and add your queries--