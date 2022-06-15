--Start of Queries for Validator Utility Project--Kindly do not delete and add your queries--
insert into Mst_Airport(AirportCode, AirportCityCode, AirportName) values('SIN', 'SIN', 'Singapore');
insert into Mst_Airport(AirportCode, AirportCityCode, AirportName) values('HKG', 'HKG', 'HongKong');

insert into Flt_OperativeFlight(Flight_ID, FlightKey, FlightOriginDate, boardingPoint, offPoint) values(1, 'SQ101', sysdate, 'SIN', 'HKG');
insert into Flt_OperativeFlight(Flight_ID, FlightKey, FlightOriginDate, boardingPoint, offPoint) values(2, 'SQ102', sysdate, 'HKG', 'SIN');

insert into Flt_OperativeFlight(Flight_ID, FlightKey, FlightOriginDate, boardingPoint, offPoint) values(3, 'SQ103', sysdate, 'SIN', 'ICN');
insert into Imp_FlightEvents(id, FlightId, FlightCompletedAt, FlightCompletedBy) values(1, 1, sysdate, 'Me');

insert into Exp_FlightEvents(id, FlightId, FlightCompletedAt, FlightCompletedBy) values(1, 2, sysdate, 'Me');

INSERT INTO Mst_Carrier
		(CarrierCode,CarrierFullName)
		VALUES
		('SQ', 'Singapore Airline');

insert into Uld_UldMaster(uld_key) values('AKE00951SQ');
insert into Uld_UldType(UldType ) values('AKE');

--End of Queries for Validator Utility Project--Kindly do not delete and add your queries--

insert into Mst_AircraftType (AircraftType , AircraftName) values('goo' , 'kingfisher');
insert into Flt_OperativeFlight_Legs (Flight_ID , FlightBoardPoint ,FlightOffPoint ,DateSTA , DateATA , AircraftType ) values  (2 , 'HKG' , 'SIN' , sysdate , sysdate , 'goo');

insert into Flt_OperativeFlight_Segments (Flight_ID ,FlightBoardPoint ,FlightOffPoint ,FlightSegmentId )  values  (2 , 'HKG' , 'SIN' ,1);

insert into Imp_ArrivalManifestByFlight (ImpArrivalManifestByFlightId , FlightId ) values  (1 ,2);

insert into Imp_ArrivalManifestBySegment (ImpArrivalManifestBySegmentId , ImpArrivalManifestByFlightId  , FlightSegmentId ) values( 1 , 1 , 1);

insert into Imp_ArrivalManifestULD (ImpArrivalManifestULDId  , ULDNumber ,ImpArrivalManifestBySegmentId  ) values (1 , 'AKE15021SQ' , 1);

insert into Imp_ArrivalManifestShipmentInfo (ImpArrivalManifestShipmentInfoId ,ImpArrivalManifestULDId ,AwbPrefix , AwbSuffix ,AwbNumber ,Origin ,Destination ,Piece ,Weight,TotalPieces ,NatureOfGoodsDescription ,ShipmentNumber , ShipmentDate ) values (1,1,'613', '0306590' ,'6130306590' ,'KIX', 'SIN' , 100 , 100 ,100, 'HATCHING EGGS' , '12345' , sysdate);

insert into Imp_BreakDownHandlingInformation (ImpBreakDownHandlingInformationId  ,FlightId ,SegmentId ,Instruction,ShipmentNumber  ) values (1,2,1, 'Both Mail and cargo' , '12345');

insert into Imp_ShipmentVerification (ImpShipmentVerificationId  , FlightId ,SegmentId ,ShipmentId , NatureOfGoodsDescription,ManifestPieces ,ManifestWeight ,BreakDownPieces ,BreakDownWeight,DamagedFlag ,IrregularityFoundFlag,OffloadedFlag ,Destination    ) values (1,2,1,1,'Hatching Eggs' , 100 , 100 , 98 , 98 , 1 , 1 , 1 , 'SIN');
 
insert into Mst_SpecialHandlingCode (SpecialHandlingCode) values ('HEG');

insert into Imp_ArrivalManifestByShipmentSHC (ImpArrivalManifestByShipmentSHCId , ImpArrivalManifestShipmentInfoId , SpecialHandlingCode) values (1,1,'HEG');

insert into Shipment_Master (ShipmentId , ShipmentType , ShipmentNumber , ShipmentDate) values (1 , 'AWBNUMBER' , '12345' , sysdate);