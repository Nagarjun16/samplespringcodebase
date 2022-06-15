drop table Flt_OperativeFlight if exists;
drop table Airmail_Manifest if exists;
drop table Airmail_ManifestShipment if exists;

CREATE TABLE Flt_OperativeFlight (
	Flight_ID numeric(15, 0) NOT NULL,
	CarrierCode varchar(3) NOT NULL,
	FlightNumber varchar(5) NOT NULL,
	FlightKey varchar(8) NOT NULL,
	FlightOriginDate datetime NOT NULL,
	GroundHandlerCode varchar(32) NOT NULL,
	CreatedUser_Code varchar(64) NOT NULL,
	Created_DateTime datetime NOT NULL );

CREATE TABLE Airmail_Manifest(
	AirmailManifestId numeric(15, 0) IDENTITY(1,1) NOT NULL,
	FlightId numeric(15, 0) NOT NULL,
	SegmentId numeric(15, 0) NOT NULL,
	AirportOfLoading varchar(3) NULL,
	AirportOfOffloading varchar(3) NULL,
	DestinationOffice varchar(6) NULL,
	AdministrationOfOriginOfMails varchar(3) NULL,
	Observations varchar(65) NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL );
	
CREATE TABLE Airmail_ManifestShipment(
	AirmailManifestShipmentId numeric(15, 0) IDENTITY(1,1) NOT NULL,
	AirmailManifestId numeric(15, 0) NOT NULL,
	DN varchar(20) NOT NULL,
	ULDTrolleyNumber varchar(12) NOT NULL,
	GrossWeight decimal(8, 1) NOT NULL,
	OriginOfficeExchange varchar(6) NOT NULL,
	DestinationOfficeExchange varchar(6) NOT NULL,
	AirportOfTranshipment varchar(3) NULL,
	AirportOfOffloading varchar(3) NULL,
	DateOfDispactch date NULL,
	LetterPost numeric(4, 0) NULL,
	CP numeric(4, 0) NULL,
	OtherItems numeric(4, 0) NULL,
	Remarks varchar(65) NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL );
	
alter table Flt_OperativeFlight add primary key(Flight_ID);

alter table Airmail_Manifest add foreign key(FlightId) 
references Flt_OperativeFlight(Flight_ID);

alter table Airmail_ManifestShipment add foreign key(AirmailManifestId) 
references Airmail_Manifest(AirmailManifestId);

