drop table Mst_Airport if exists;
drop table Mst_SpecialHandlingCode if exists;
drop table  Flt_OperativeFlight if exists;
drop table  Flt_OperativeFlight_Segments if exists;
drop table Flt_OperativeFlight_Legs if exists;
drop table Imp_ArrivalManifestByFlight if exists;
drop table Imp_ArrivalManifestBySegment if exists;
drop table Imp_ArrivalManifestULD if exists;
drop table Imp_ArrivalManifestShipmentInfo if exists;
drop table Imp_ArrivalManifestShipmentDimensionInfo if exists;
drop table Imp_ArrivalManifestShipmentOCI  if exists;
drop table Imp_ArrivalManifestShipmentOnwardMovement if exists;
drop table Imp_ArrivalManifestOtherSvcInfo if exists;
drop table Imp_ArrivalManifestByShipmentSHC if exists;
drop table Imp_FlightEvents if exists;
drop table Imp_CargoPreAnnouncement if exists;


CREATE TABLE Mst_Airport(
	AirportCode varchar(3) NOT NULL,
	AirportCityCode varchar(3) NOT NULL,
	AirportName varchar(35) NOT NULL);
 alter table Mst_Airport add primary key(AirportCode);
 
 create table Mst_SpecialHandlingCode(
	SpecialHandlingCode varchar(3) NOT NULL,
	SpecialHandlingCodeDescription varchar(30) NOT NULL,
	IataFlag bit NULL,
	SpecialHandlingPriority integer NOT NULL,
	StartDate date  NOT NULL,
	EndDate date NULL);
alter table Mst_SpecialHandlingCode add primary key(SpecialHandlingCode ,StartDate);
	

CREATE TABLE Flt_OperativeFlight(
Flight_ID NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
FlightKey varchar(8) NOT NULL,
FlightNumber varchar(5) NOT NULL,
FlightType varchar(10) NULL,
DateSTD datetime null,
FlightOriginDate datetime NOT NULL,
FlightStatus varchar(3) NULL,
OutboundAircraftRegNo varchar(10) NULL,
CarrierCode varchar(3) NOT NULL,
);

CREATE TABLE Flt_OperativeFlight_Segments(
Flight_ID integer NOT NULL primary key,
FlightBoardPoint varchar(3) NOT NULL,
	FlightOffPoint varchar(3) NOT NULL,
	DateSTD datetime NOT NULL,
	FlightSegmentId integer auto_increment NOT NULL
);

alter table Flt_OperativeFlight_Segments add foreign key(Flight_ID) references Flt_OperativeFlight(Flight_ID);

create table Flt_OperativeFlight_Legs(
	Flight_ID integer NOT NULL,
	FlightBoardPoint varchar(3) NOT NULL,
	FlightOffPoint varchar(3) NOT NULL,
	FlightSegmentOrder integer NOT NULL,
	DateSTD datetime NOT NULL,
	DateSTA datetime NULL,
	DateETD datetime NULL,
	DateETA datetime NULL,
	DateATD datetime NULL,
	DateATA datetime NULL,
	DomesticFlightFlag bit NULL,
	AircraftRegCode varchar(10) NULL,
	AircraftType varchar(3)  NULL);
alter table Flt_OperativeFlight_Legs add primary key(Flight_ID ,FlightBoardPoint ,FlightOffPoint);
alter table Flt_OperativeFlight_Legs add foreign key(Flight_ID) references Flt_OperativeFlight (Flight_ID);
alter table Flt_OperativeFlight_Legs add foreign key(FlightBoardPoint) references Mst_Airport (AirportCode);
alter table Flt_OperativeFlight_Legs add foreign key(FlightOffPoint) references Mst_Airport (AirportCode);


CREATE TABLE Imp_ArrivalManifestByFlight(
	ImpArrivalManifestByFlightId numeric(15, 0) auto_increment NOT NULL,
	AircraftRegCode varchar(10) NOT NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	LastUpdatedUserCode varchar(64) NULL,
	LastUpdatedDateTime datetime NULL,
	FlightId numeric(15, 0) NOT NULL,
	);
	alter table Imp_ArrivalManifestByFlight add foreign key(FlightId) references Flt_OperativeFlight (Flight_ID);
	
	CREATE TABLE Imp_ArrivalManifestBySegment(
	ImpArrivalManifestBySegmentId numeric(15, 0) auto_increment NOT NULL,
	ImpArrivalManifestByFlightId numeric(15, 0) NOT NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	LastUpdatedUserCode varchar(64) NULL,
	LastUpdatedDateTime datetime NULL,
	FlightSegmentId numeric(15, 0) NOT NULL);
	alter table Imp_ArrivalManifestBySegment add primary key(ImpArrivalManifestByFlightId,FlightSegmentId);
alter table Imp_ArrivalManifestBySegment add foreign key(ImpArrivalManifestByFlightId) references Imp_ArrivalManifestByFlight (ImpArrivalManifestByFlightId);
alter table Imp_ArrivalManifestBySegment add foreign key(FlightSegmentId) references Flt_OperativeFlight_Segments (FlightSegmentId);
	
	CREATE TABLE Imp_ArrivalManifestULD(
	ImpArrivalManifestULDId numeric(15, 0) auto_increment NOT NULL,
	UldType varchar(3) NULL,
	UldSerialNumber varchar(6) NULL,
	UldOwnerCode varchar(3) NULL,
	ULDNumber varchar(11) NULL,
	UldLoadingIndicator varchar(1) NULL,
	UldRemarks varchar(64) NULL,
	VolumeAvailableCode numeric(1, 0) NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	LastUpdatedUserCode varchar(64) NULL,
	LastUpdatedDateTime datetime NULL,
	ImpArrivalManifestBySegmentId numeric(15, 0) NOT NULL,);
	
	CREATE TABLE Imp_ArrivalManifestShipmentInfo(
	ImpArrivalManifestShipmentInfoId numeric(15, 0) auto_increment NOT NULL,
	ImpArrivalManifestULDId numeric(15, 0) NOT NULL,
	Origin varchar(3) NOT NULL,
	Destination varchar(3) NOT NULL,
	ShipmentDescriptionCode varchar(1) NOT NULL,
	Piece numeric(4, 0) NOT NULL,
	WeightUnitCode varchar(1) NOT NULL,
	Weight decimal(8, 1) NOT NULL,
	VolumeUnitCode varchar(2) NULL,
	VolumeAmount decimal(11, 2) NULL,
	DensityIndicator varchar(2) NULL,
	DensityGroupCode numeric(2, 0) NULL,
	TotalPieces numeric(4, 0) NULL,
	NatureOfGoodsDescription varchar(20) NOT NULL,
	MovementPriorityCode varchar(1) NULL,
	CustomsOriginCode varchar(2) NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	LastUpdatedUserCode varchar(64) NULL,
	LastUpdatedDateTime datetime NULL,
	CustomsReference varchar(65) NULL,
	ShipmentNumber varchar(11) NOT NULL,
	ShipmentDate datetime NOT NULL,);
	alter table Imp_ArrivalManifestShipmentInfo add primary key(ImpArrivalManifestULDId,ShipmentNumber ,ShipmentDate);
	alter table Imp_ArrivalManifestShipmentInfo add foreign key(ImpArrivalManifestULDId) references Imp_ArrivalManifestULD (ImpArrivalManifestULDId);
	
	CREATE TABLE Imp_ArrivalManifestShipmentDimensionInfo(
	ImpArrivalManifestShipmentDimensionInfoId numeric(15, 0) auto_increment NOT NULL,
	ImpArrivalManifestShipmentInfoId numeric(15, 0) NOT NULL,
	WeightUnitCode varchar(1) NOT NULL,
	Weight decimal(8, 1) NOT NULL,
	MeasurementUnitCode varchar(3) NOT NULL,
	DimensionLength numeric(5, 0) NOT NULL,
	DimensionWIdth numeric(5, 0) NOT NULL,
	DimensionHeight numeric(5, 0) NOT NULL,
	NumberOfPieces numeric(4, 0) NOT NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	LastUpdatedUserCode varchar(64) NULL,
	LastUpdatedDateTime datetime NULL,
	TransactionSequenceNo numeric(15, 0) NOT NULL,);
	alter table Imp_ArrivalManifestShipmentDimensionInfo add primary key(ImpArrivalManifestShipmentInfoId,TransactionSequenceNo);
	alter table Imp_ArrivalManifestShipmentDimensionInfo add foreign key(ImpArrivalManifestShipmentInfoId) references Imp_ArrivalManifestShipmentInfo (ImpArrivalManifestShipmentInfoId);
	
	CREATE TABLE Imp_ArrivalManifestShipmentOCI(
	ImpArrivalManifestShipmentOCIId numeric(15, 0) auto_increment NOT NULL,
	ImpArrivalManifestShipmentInfoId numeric(15, 0) NOT NULL,
	CountryCode varchar(2) NOT NULL,
	InformationIdentifier varchar(3) NULL,
	CSRCIIdentifier varchar(2) NULL,
	SCSRCInformation varchar(35) NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	LastUpdatedUserCode varchar(64) NULL,
	LastUpdatedDateTime datetime NULL,
	TransactionSequenceNo numeric(15, 0) NOT NULL,);
	alter table Imp_ArrivalManifestShipmentOCI add primary key(ImpArrivalManifestShipmentInfoId,TransactionSequenceNo);
	alter table Imp_ArrivalManifestShipmentOCI add foreign key(ImpArrivalManifestShipmentInfoId) references Imp_ArrivalManifestShipmentInfo (ImpArrivalManifestShipmentInfoId);
	
	CREATE TABLE Imp_ArrivalManifestShipmentOnwardMovement(
	ImpArrivalManifestShipmentOnwardMovementId numeric(15, 0) auto_increment NOT NULL,
	ImpArrivalManifestShipmentInfoId numeric(15, 0) NOT NULL,
	AirportCityCode varchar(3) NOT NULL,
	CarrierCode varchar(3) NOT NULL,
	FlightNumber varchar(5) NOT NULL,
	DepartureDate datetime NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	LastUpdatedUserCode varchar(64) NULL,
	LastUpdatedDateTime datetime NULL,
	TransactionSequenceNo numeric(15, 0) NOT NULL,);
	alter table Imp_ArrivalManifestShipmentOnwardMovement add primary key(ImpArrivalManifestShipmentInfoId,TransactionSequenceNo);
	alter table Imp_ArrivalManifestShipmentOnwardMovement add foreign key(ImpArrivalManifestShipmentInfoId) references Imp_ArrivalManifestShipmentInfo (ImpArrivalManifestShipmentInfoId);
	
	CREATE TABLE Imp_ArrivalManifestOtherSvcInfo(
	Imp_ArrivalManifestOtherSvcInfoId numeric(15, 0) auto_increment NOT NULL,
	ImpArrivalManifestShipmentInfoId numeric(15, 0) NOT NULL,
	ServiceInformation varchar(65) NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	LastUpdatedUserCode varchar(64) NULL,
	LastUpdatedDateTime datetime NULL,
	TransactionSequenceNo numeric(15, 0) NOT NULL,);
	alter table Imp_ArrivalManifestOtherSvcInfo add primary key(ImpArrivalManifestShipmentInfoId,TransactionSequenceNo);
	alter table Imp_ArrivalManifestOtherSvcInfo add foreign key(ImpArrivalManifestShipmentInfoId) references Imp_ArrivalManifestShipmentInfo (ImpArrivalManifestShipmentInfoId);
	
	CREATE TABLE Imp_ArrivalManifestByShipmentSHC(
	ImpArrivalManifestByShipmentSHCId numeric(15, 0) auto_increment NOT NULL,
	ImpArrivalManifestShipmentInfoId numeric(15, 0) NOT NULL,
	SpecialHandlingCode varchar(3) NOT NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	LastUpdatedUserCode varchar(64) NULL,
	LastUpdatedDateTime datetime NULL,
);
alter table Imp_ArrivalManifestByShipmentSHC add primary key(ImpArrivalManifestShipmentInfoId,SpecialHandlingCode);
alter table Imp_ArrivalManifestByShipmentSHC add foreign key(ImpArrivalManifestShipmentInfoId) references Imp_ArrivalManifestShipmentInfo (ImpArrivalManifestShipmentInfoId);
alter table Imp_ArrivalManifestByShipmentSHC add foreign key(SpecialHandlingCode) references Mst_SpecialHandlingCode (SpecialHandlingCode);


CREATE TABLE Imp_FlightEvents(
	ImpFlightEventsId numeric(15, 0) auto_increment NOT NULL,
	FlightId numeric(15, 0) NOT NULL,
	FirstULDTowedBy varchar(64) NULL,
	FirstULDTowedAt datetime NULL,
	LastULDTowedBy varchar(64) NULL,
	LastULDTowedAt datetime NULL,
	InboundULDListFinalizedBy varchar(64) NULL,
	InboundULDListFinalizedAt datetime NULL,
	FirstULDCheckedInAt datetime NULL,
	FirstULDCheckedInBy varchar(64) NULL,
	FirstTimeRampCheckInCompletedAt datetime NULL,
	FirstTimeRampCheckInCompletedBy varchar(64) NULL,
	RampCheckInCompletedAt datetime NULL,
	RampCheckInCompletedBy varchar(64) NULL,
	FirstTimeDocumentVerificationCompletedAt datetime NULL,
	FirstTimeDocumentVerificationCompletedBy varchar(64) NULL,
	DocumentVerificationCompletedAt datetime NULL,
	DocumentVerificationCompletedBy varchar(64) NULL,
	FirstTimeBreakDownCompletedAt datetime NULL,
	FirstTimeBreakDownCompletedBy varchar(64) NULL,
	BreakDownCompletedAt datetime NULL,
	BreakDownCompletedBy varchar(64) NULL,
	InwardServiceReportFinalizedAt datetime NULL,
	InwardServiceReportFinalizedBy varchar(64) NULL,
	FirstTimeFlightCompletedAt datetime NULL,
	FirstTimeFlightCompletedBy varchar(64) NULL,
	FlightCompletedAt datetime NULL,
	FlightCompletedBy varchar(64) NULL,
	FirstTimeFlightClosedBy varchar(64) NULL,
	FirstTimeFightClosedAt datetime NULL,
	FlightClosedBy varchar(64) NULL,
	FightClosedAt datetime NULL,
	ThroughTransitWorkingListFinalizedAt datetime NULL,
	ThroughTransitWorkingListFinalizedBy varchar(64) NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	LastUpdatedUserCode varchar(64) NULL,
	LastUpdatedDateTime datetime NULL,
	FlightDiscrepncyListSentBy varchar(64) NULL,
	FlightDiscrepncyListSentAt datetime NULL,);
	
	alter table Imp_FlightEvents add primary key(FlightId);
	
	
	CREATE TABLE Imp_CargoPreAnnouncement(
	IncomingFlightId numeric(15, 0)  NOT NULL,
	ImpArrivalManifestShipmentInfoId numeric(15, 0) NOT NULL,
	UldBoardPoint varchar(3) NOT NULL,UldOffPoint varchar(3) NOT NULL,ULDNumber varchar(11) NULL,CargoPreAnnouncementId numeric(15, 0) auto_increment  NOT NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	LastUpdatedUserCode varchar(64) NULL,
	LastUpdatedDateTime datetime NULL,ManualFlag bit NOT NULL
	
);
	
	