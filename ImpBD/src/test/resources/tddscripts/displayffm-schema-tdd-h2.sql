
drop table Imp_FreightFlightManifestByFlight if exists;
drop table Imp_FreightFlightManifestBySegment if exists;
drop table Imp_FreightFlightManifestULD if exists;
drop table Imp_FreightFlightManifestByShipment if exists;
drop table Imp_FreightFlightManifestByShipmentSHC if exists;
drop table Imp_FreightFlightManifestOtherSvcInfo if exists;
drop table Imp_FreightFlightManifestShipmentDimension if exists;
drop table Imp_FreightFlightManifestShipmentOCI if exists;
drop table Imp_FreightFlightManifestShipmentOnwardMovement if exists;
drop table Flt_OperativeFlight if exists;
drop table Flt_OperativeFlight_Legs if exists;
drop table Flt_OperativeFlight_Segments if exists;

create table Flt_OperativeFlight(
	Flight_ID INTEGER NOT NULL,
	FlightKey varchar(8) NOT NULL,
	FlightOriginDate date NOT null,
	CreatedUser_Code varchar(64) NOT NULL,
	Created_DateTime date NOT null,
     LastUpdated_DateTime date null
);
alter table  Flt_OperativeFlight add primary key(Flight_ID);

create table Flt_OperativeFlight_Legs(
	Flight_ID INTEGER NOT NULL,
     FlightBoardPoint varchar(3) NOT NULL,
	FlightOffPoint varchar(3) NOT NULL,
	DateSTA date NOT null,
     DateETA date null,
     DateATA date null,
     AircraftRegCode varchar(10) NULL,
     CreatedUser_Code varchar(64) NOT NULL,
     Created_DateTime date NOT null
);
alter table  Flt_OperativeFlight_Legs add foreign key(Flight_ID) references Flt_OperativeFlight(Flight_ID);

CREATE TABLE Flt_OperativeFlight_Segments(
	Flight_ID INTEGER NOT NULL,
	FlightBoardPoint varchar(3) NOT NULL,
	FlightOffPoint varchar(3) NOT NULL,
	FlightSegmentOrder INTEGER NOT NULL,
	DateSTD datetime NOT NULL,
	DateSTA datetime NOT NULL,
	CreatedUser_Code varchar(64) NOT NULL,
	Created_DateTime datetime NOT NULL,
	FlightSegmentId INTEGER NOT NULL,
     FFM_RejectCount INTEGER NULL
);

alter table  Flt_OperativeFlight_Segments add foreign key(Flight_ID) references Flt_OperativeFlight(Flight_ID);

CREATE TABLE Imp_FreightFlightManifestByFlight(
	ImpFreightFlightManifestByFlightId INTEGER NOT NULL,
	AircraftRegCode varchar(10) NOT NULL,
	CountryCode varchar(2) NOT NULL,
	ArrivalFlightKey varchar(8) NOT NULL,
	ArrivaAirportCityCode varchar(3) NOT NULL,
	MessageSequence INTEGER NOT NULL,
	MessageVersion INTEGER NOT NULL,
	MessageProcessedDate datetime NOT NULL,
	MessageStatus varchar(10) NOT NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	FlightId INTEGER NOT NULL
);
alter table  Imp_FreightFlightManifestByFlight add foreign key(FlightId) references Flt_OperativeFlight(Flight_ID);

CREATE TABLE Imp_FreightFlightManifestBySegment(
	ImpFreightFlightManifestBySegmentId INTEGER NOT NULL,
	ImpFreightFlightManifestByFlightId INTEGER NOT NULL,
	ArrivalDate datetime NOT NULL,
	DepartureDate datetime NOT NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	FlightSegmentId INTEGER NOT NULL
 );

 alter table  Imp_FreightFlightManifestBySegment add foreign key(ImpFreightFlightManifestByFlightId) references Imp_FreightFlightManifestByFlight(ImpFreightFlightManifestByFlightId);
 alter table  Imp_FreightFlightManifestBySegment add foreign key(FlightSegmentId) references Flt_OperativeFlight_Segments(FlightSegmentId);

 CREATE TABLE Imp_FreightFlightManifestULD(
	ImpFreightFlightManifestULDId INTEGER NOT NULL,
	ImpFreightFlightManifestBySegmentId INTEGER NOT NULL,
	UldType varchar(3) NOT NULL,
	UldSerialNumber varchar(6) NOT NULL,
	UldOwnerCode varchar(3) NOT NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
       ULDNumber varchar(11) NULL,
      UldLoadingIndicator varchar(1) NULL,
	UldRemarks varchar(53) NULL,
	VolumeAvailableCode INTEGER NULL
);

alter table  Imp_FreightFlightManifestULD add foreign key(ImpFreightFlightManifestBySegmentId) references Imp_FreightFlightManifestBySegment(ImpFreightFlightManifestBySegmentId);

CREATE TABLE Imp_FreightFlightManifestByShipment(
	ImpFreightFlightManifestByShipmentId INTEGER NOT NULL,
	ImpFreightFlightManifestULDId INTEGER NOT NULL,
	AwbPrefix varchar(3) NOT NULL,
	AwbSuffix varchar(8) NOT NULL,
	AwbNumber varchar(8) NOT NULL,
	AwbDate datetime NOT NULL,
	Origin varchar(3) NOT NULL,
	Destination varchar(3) NOT NULL,
	ShipmentDescriptionCode varchar(1) NOT NULL,
	Pieces INTEGER NOT NULL,
	WeightUnitCode varchar(1) NOT NULL,
	Weight decimal NOT NULL,
       VolumeUnitCode varchar(2) NULL,
	VolumeAmount decimal NULL,
	DensityIndicator varchar(2) NULL,
	DensityGroupCode INTEGER NULL,
	TotalPieces INTEGER  NULL,
	NatureOfGoodsDescription varchar(20) NOT NULL,
	MovementPriorityCode varchar(1) NULL,
	OtherServiceInformation1 varchar(65) NULL,
	OtherServiceInformation2 varchar(65) NULL,
	CustomsOriginCode varchar(2) NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL
	);
alter table  Imp_FreightFlightManifestByShipment add foreign key(ImpFreightFlightManifestULDId) references Imp_FreightFlightManifestULD(ImpFreightFlightManifestULDId);

CREATE TABLE Imp_FreightFlightManifestByShipmentSHC(
	ImpFreightFlightManifestByShipmentSHCId INTEGER NOT NULL,
	ImpFreightFlightManifestByShipmentId INTEGER NOT NULL,
	SpecialHandlingCode varchar(3) NOT NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL
	);

alter table  Imp_FreightFlightManifestByShipmentSHC add foreign key(ImpFreightFlightManifestByShipmentId) references Imp_FreightFlightManifestByShipment(ImpFreightFlightManifestByShipmentId);

	CREATE TABLE Imp_FreightFlightManifestOtherSvcInfo(
	ImpFreightFlightManifestOtherSvcInfoId INTEGER NOT NULL,
      ServiceInformation varchar(65) NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	TransactionSequenceNo INTEGER NOT NULL,
	ImpFreightFlightManifestByShipmentId INTEGER NOT NULL
	);
alter table  Imp_FreightFlightManifestOtherSvcInfo add foreign key(ImpFreightFlightManifestByShipmentId) references Imp_FreightFlightManifestByShipment(ImpFreightFlightManifestByShipmentId);

CREATE TABLE Imp_FreightFlightManifestShipmentDimension(
	ImpFreightFlightManifestShipmentDimensionId INTEGER NOT NULL,
	ImpFreightFlightManifestByShipmentId INTEGER NOT NULL,
	WeightUnitCode varchar(1) NOT NULL,
	Weight decimal NOT NULL,
	MeasurementUnitCode varchar(3) NOT NULL,
	DimensionLength INTEGER NOT NULL,
	DimensionWIdth INTEGER NOT NULL,
	DimensionHeight INTEGER NOT NULL,
	NumberOfPieces INTEGER NOT NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL
 );
 alter table  Imp_FreightFlightManifestShipmentDimension add foreign key(ImpFreightFlightManifestByShipmentId) references Imp_FreightFlightManifestByShipment(ImpFreightFlightManifestByShipmentId);
 CREATE TABLE Imp_FreightFlightManifestShipmentOCI(
	ImpFreightFlightManifestShipmentOCIId INTEGER NOT NULL,
	CountryCode varchar(2) NOT NULL,
     InformationIdentifier varchar(3) NULL,
	CSRCIIdentifier varchar(2) NULL,
	SCSRCInformation varchar(35) NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	TransactionSequenceNo INTEGER NOT NULL,
	ImpFreightFlightManifestByShipmentId INTEGER NOT NULL,
	);
alter table  Imp_FreightFlightManifestShipmentOCI add foreign key(ImpFreightFlightManifestByShipmentId) references Imp_FreightFlightManifestByShipment(ImpFreightFlightManifestByShipmentId);

CREATE TABLE Imp_FreightFlightManifestShipmentOnwardMovement(
	ImpFreightFlightManifestShipmentOnwardMovementId INTEGER NOT NULL,
	ImpFreightFlightManifestByShipmentId INTEGER NOT NULL,
	AirportCityCode varchar(3) NOT NULL,
	CarrierCode varchar(3) NOT NULL,
	FlightNumber varchar(5) NOT NULL,
     DepartureDate datetime NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	TransactionSequenceNo INTEGER NOT NULL,
	);
alter table  Imp_FreightFlightManifestShipmentOnwardMovement add foreign key(ImpFreightFlightManifestByShipmentId) references Imp_FreightFlightManifestByShipment(ImpFreightFlightManifestByShipmentId);

