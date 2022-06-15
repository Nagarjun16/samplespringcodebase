drop table Mst_Airport if exists;
drop table Flt_OperativeFlight if exists;
drop table Imp_FlightEvents if exists;
drop table Exp_FlightEvents if exists;
drop table Mst_Carrier if exists;
drop table Uld_UldMaster if exists;
drop table Uld_UldType if exists;

create table Mst_Airport(	
	AirportCode varchar(3) not null,
	AirportCityCode varchar(3),
	AirportName varchar(35)
);

alter table Mst_Airport add primary key(AirportCode);

create table Flt_OperativeFlight(
	Flight_ID integer not null,	
	FlightKey varchar(6),
	FlightOriginDate date not null,
    FlightStatus varchar(35), 
	boardingPoint varchar(3) not null,
	offPoint varchar(3) not null
);

alter table Flt_OperativeFlight add primary key(Flight_ID);

create table Imp_FlightEvents(
	id integer not null,	
	FlightId integer not null,
	FlightCompletedAt date null,
	FlightCompletedBy varchar(35) null,
	RampCheckInCompletedAt date NULL,
	DocumentVerificationCompletedAt date NULL,
	BreakDownCompletedAt date NULL
);



alter table Imp_FlightEvents add primary key(id);
alter table Imp_FlightEvents add foreign key(FlightId) references Flt_OperativeFlight(Flight_ID);

create table Exp_FlightEvents(
	id integer not null,	
	FlightId integer not null,
	FlightCompletedAt date null,
	FlightCompletedBy varchar(35) null
);

alter table Exp_FlightEvents add primary key(id);
alter table Exp_FlightEvents add foreign key(FlightId) references Flt_OperativeFlight(Flight_ID);

create table Mst_Carrier(
	CarrierCode varchar(5), CarrierFullName varchar(35)
);

create table Uld_UldMaster(
	uld_key varchar(11) 
);
create table Uld_UldType(
	UldType varchar(3)
);


DROP TABLE Mst_AircraftType if exists;
CREATE TABLE Mst_AircraftType
(
	AircraftType varchar(3) NOT NULL,
	AircraftName varchar(65) NOT NULL
);


DROP TABLE Flt_OperativeFlight_Legs IF EXISTS;

CREATE TABLE Flt_OperativeFlight_Legs
(
	Flight_ID integer  NOT NULL,
	FlightBoardPoint varchar(3) NOT NULL,
	FlightOffPoint varchar(3) NOT NULL,
	DateSTA datetime NOT NULL,
	DateATA datetime NULL,
	AircraftType varchar(3) NOT NULL
);

ALTER TABLE Flt_OperativeFlight_Legs  ADD FOREIGN KEY(Flight_ID)
REFERENCES Flt_OperativeFlight (Flight_ID);

ALTER TABLE Flt_OperativeFlight_Legs  ADD FOREIGN KEY(AircraftType)
REFERENCES Mst_AircraftType(AircraftType);

ALTER TABLE Flt_OperativeFlight_Legs  ADD FOREIGN KEY(FlightBoardPoint)
REFERENCES Mst_Airport(AirportCode);

ALTER TABLE Flt_OperativeFlight_Legs ADD FOREIGN KEY(FlightOffPoint)
REFERENCES Mst_Airport (AirportCode);


DROP TABLE Flt_OperativeFlight_Segments IF EXISTS;

CREATE TABLE Flt_OperativeFlight_Segments
(
	Flight_ID integer  NOT NULL,
	FlightBoardPoint varchar(3) NOT NULL,
	FlightOffPoint varchar(3) NOT NULL,
	FlightSegmentId integer NOT NULL
);
ALTER TABLE Flt_OperativeFlight_Segments ADD  FOREIGN KEY(Flight_ID)
REFERENCES Flt_OperativeFlight (Flight_ID);

ALTER TABLE Flt_OperativeFlight_Segments  ADD   FOREIGN KEY(FlightBoardPoint)
REFERENCES Mst_Airport(AirportCode);

ALTER TABLE Flt_OperativeFlight_Segments   ADD  FOREIGN KEY(FlightOffPoint)
REFERENCES Mst_Airport (AirportCode);


DROP TABLE Imp_ArrivalManifestByFlight IF EXISTS;

CREATE TABLE Imp_ArrivalManifestByFlight
(
	ImpArrivalManifestByFlightId integer   NOT NULL,
	FlightId integer  NOT NULL
);
ALTER TABLE Imp_ArrivalManifestByFlight ADD  FOREIGN KEY(FlightId) REFERENCES Flt_OperativeFlight (Flight_ID);


DROP TABLE Imp_ArrivalManifestBySegment IF EXISTS;
CREATE TABLE Imp_ArrivalManifestBySegment
(
	ImpArrivalManifestBySegmentId integer NOT NULL,
	ImpArrivalManifestByFlightId integer  NOT NULL,
	FlightSegmentId integer NOT NULL
);
 
ALTER TABLE Imp_ArrivalManifestBySegment ADD  FOREIGN KEY(FlightSegmentId)
REFERENCES Flt_OperativeFlight_Segments (FlightSegmentId);

ALTER TABLE Imp_ArrivalManifestBySegment ADD FOREIGN KEY(ImpArrivalManifestByFlightId)
REFERENCES Imp_ArrivalManifestByFlight(ImpArrivalManifestByFlightId);


DROP TABLE Imp_ArrivalManifestULD IF EXISTS;
CREATE TABLE Imp_ArrivalManifestULD
(
	ImpArrivalManifestULDId integer NOT NULL,
	ULDNumber varchar(11) NOT  NULL,
	ImpArrivalManifestBySegmentId  integer  NOT NULL
);


DROP TABLE Imp_ArrivalManifestShipmentInfo IF EXISTS;
CREATE TABLE Imp_ArrivalManifestShipmentInfo
(
	ImpArrivalManifestShipmentInfoId integer  NOT NULL,
	ImpArrivalManifestULDId integer NOT NULL,
	ShipmentNumber varchar(10) NOT NULL,
	ShipmentDate date NOT NULL,
	AwbPrefix varchar(3) NOT NULL,
	AwbSuffix varchar(8) NOT NULL,
	AwbNumber varchar(11) NOT NULL,
	Origin varchar(11) NOT NULL,
	Destination varchar(3) NOT NULL,
	Piece integer  NOT NULL,
	Weight integer  NOT NULL,
	TotalPieces numeric(4, 0)  NOT NULL,
	NatureOfGoodsDescription varchar(20) NOT NULL
);

ALTER TABLE Imp_ArrivalManifestShipmentInfo ADD FOREIGN KEY(ImpArrivalManifestULDId)
REFERENCES Imp_ArrivalManifestULD (ImpArrivalManifestULDId);


DROP TABLE Imp_BreakDownHandlingInformation IF EXISTS;
CREATE TABLE  Imp_BreakDownHandlingInformation
(
	ImpBreakDownHandlingInformationId integer NOT NULL,
	FlightId integer  NOT NULL,
	SegmentId integer  NOT NULL,
	ShipmentNumber varchar(10) NOT NULL,
	Instruction varchar(30) NOT NULL
);

ALTER TABLE Imp_BreakDownHandlingInformation ADD FOREIGN KEY(FlightId)
REFERENCES Flt_OperativeFlight (Flight_ID);

ALTER TABLE Imp_BreakDownHandlingInformation ADD FOREIGN KEY(SegmentId)
REFERENCES Flt_OperativeFlight_Segments (FlightSegmentId);


DROP TABLE Imp_ShipmentVerification IF EXISTS;
CREATE TABLE Imp_ShipmentVerification
(
	ImpShipmentVerificationId integer NOT NULL,
	FlightId integer   NOT NULL,
	SegmentId integer  NOT NULL,
	ShipmentId integer NOT NULL,
	NatureOfGoodsDescription varchar(20) NOT NULL,
	ManifestPieces integer  NOT NULL,
	ManifestWeight integer  NOT NULL,
	BreakDownPieces integer  NULL,
	BreakDownWeight integer NULL,
	DamagedFlag bit NULL,
	IrregularityFoundFlag bit NULL,
	OffloadedFlag bit NULL,
	Destination varchar(3) NOT NULL
);

ALTER TABLE Imp_ShipmentVerification ADD  FOREIGN KEY(FlightId)
REFERENCES Flt_OperativeFlight (Flight_ID);

ALTER TABLE Imp_ShipmentVerification ADD FOREIGN KEY(SegmentId)
REFERENCES Flt_OperativeFlight_Segments (FlightSegmentId);

ALTER TABLE Imp_ShipmentVerification ADD FOREIGN KEY(Destination)  
REFERENCES Mst_Airport (AirportCode);

DROP TABLE Mst_SpecialHandlingCode IF EXISTS;
CREATE TABLE Mst_SpecialHandlingCode(
	SpecialHandlingCode varchar(3) NOT NULL
); 

DROP TABLE Imp_ArrivalManifestByAWBSHC IF EXISTS;
CREATE TABLE Imp_ArrivalManifestByAWBSHC
(
	ImpArrivalManifestByAWBSHCId INTEGER NOT NULL,
	ImpArrivalManifestShipmentInfoId INTEGER NOT NULL,
	SpecialHandlingCode varchar(3) NOT NULL
);

ALTER TABLE Imp_ArrivalManifestByAWBSHC ADD  FOREIGN KEY (ImpArrivalManifestShipmentInfoId)
REFERENCES Imp_ArrivalManifestShipmentInfo(ImpArrivalManifestShipmentInfoId);


ALTER TABLE Imp_ArrivalManifestByAWBSHC  ADD FOREIGN KEY(SpecialHandlingCode)
REFERENCES Mst_SpecialHandlingCode (SpecialHandlingCode);

DROP TABLE Shipment_Master IF EXISTS;
CREATE TABLE Shipment_Master(
	ShipmentId INTEGER NOT NULL,
	ShipmentType varchar(10) NOT NULL,
	ShipmentNumber varchar(10) NOT NULL,
	ShipmentDate date NOT NULL
);

DROP TABLE Shipment_Irregularity IF EXISTS;
CREATE TABLE Shipment_Irregularity(
	ShipmentNumber INTEGER NOT NULL,
	Flight_ID INTEGER  NULL,
	CargoIrregularityCode varchar(4) NOT NULL,
);

ALTER TABLE Shipment_Irregularity ADD  FOREIGN KEY(Flight_ID)
REFERENCES Flt_OperativeFlight (Flight_ID);

DROP TABLE Imp_ArrivalManifestByShipmentSHC IF EXISTS;
CREATE TABLE Imp_ArrivalManifestByShipmentSHC(
	ImpArrivalManifestByShipmentSHCId INTEGER NOT NULL,
	ImpArrivalManifestShipmentInfoId INTEGER NOT NULL,
	SpecialHandlingCode varchar(3) NOT NULL
);

ALTER TABLE Imp_ArrivalManifestByShipmentSHC ADD  FOREIGN KEY(ImpArrivalManifestShipmentInfoId)
REFERENCES Imp_ArrivalManifestShipmentInfo (ImpArrivalManifestShipmentInfoId);








