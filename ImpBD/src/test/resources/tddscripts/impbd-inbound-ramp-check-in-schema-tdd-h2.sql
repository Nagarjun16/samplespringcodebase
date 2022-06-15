drop table Flt_OperativeFlight if exists;
CREATE TABLE Flt_OperativeFlight(
	Flight_ID integer,
	FlightNumber varchar(5),
	FlightKey varchar(8),
	FlightOriginDate datetime,
	MaxULDCount integer,
	  CreatedUserCode varchar(64),
  CreatedDateTime datetime,
	LastUpdatedUserCode varchar(64),
	LastUpdatedDateTime datetime
	);

drop table Flt_OperativeFlight_Legs if exists;
CREATE TABLE Flt_OperativeFlight_Legs(
	Flight_ID integer(15),
	FlightOffPoint varchar(3),
	FlightSegmentOrder integer(4),
	DateSTD datetime,
	DateSTA datetime,
	DateETD datetime,
	DateETA datetime,
	DateATD datetime,
	DateATA datetime,
	  CreatedUserCode varchar(64),
  CreatedDateTime datetime,
	LastUpdatedUserCode varchar(64),
	LastUpdatedDateTime datetime);
	
drop table Imp_RampCheckIn if exists;
CREATE TABLE Imp_RampCheckIn(
  ImpRampCheckInId integer  auto_increment,
  FlightId integer,
  ULDNumber varchar(11),
  TransferType varchar(5),
  ContentCode varchar(1),
  UsedAsTrolley bit,
  DamagedFlag bit,
  EmptyFlag bit,
  PiggybackFlag bit,
  Remarks varchar(65),
  CheckedInAt datetime,
  CheckedInBy varchar(64),
  CheckedInArea varchar(10),
  OffloadedFlag bit,
  OffloadedReasonCode varchar(10),
  CreatedUserCode varchar(64),
  CreatedDateTime datetime,
  LastUpdatedUserCode varchar(64),
  LastUpdatedDateTime datetime,
  PHCFlag bit ,
  ValFlag bit ,
  ManualFlag bit
);

drop table Imp_RampCheckInPiggyBackULDInfo if exists;
CREATE TABLE Imp_RampCheckInPiggyBackULDInfo(
  ImpRampCheckInPiggyBackULDInfoId integer auto_increment,
  ImpRampCheckInId integer,
  ULDNumber varchar(11),
    CreatedUserCode varchar(64),
  CreatedDateTime datetime,
	LastUpdatedUserCode varchar(64),
	LastUpdatedDateTime datetime
);

drop table Imp_RampCheckInULDSHC if exists;
CREATE TABLE Imp_RampCheckInULDSHC(
  ImpRampCheckInULDSHCId integer auto_increment,
  ImpRampCheckInId integer(15),
  SpecialHandlingCode varchar (3),
  CreatedUserCode varchar(64),
  CreatedDateTime datetime,
	LastUpdatedUserCode varchar(64),
	LastUpdatedDateTime datetime
);

drop table Imp_HandOver if exists;
create table Imp_HandOver(
   ImpHandOverId integer auto_increment,
   FlightId integer,
   HandedOverAt varchar(64),
   TractorNumber varchar(15),
   HandedOverBy varchar(64),
   StartedAt   date,
   CompletedAt date,
     CreatedUserCode varchar(64),
  CreatedDateTime datetime,
	LastUpdatedUserCode varchar(64),
	LastUpdatedDateTime datetime
   );
   
 drop table Imp_HandOverContainerTrolleyInformation if exists;
 create table Imp_HandOverContainerTrolleyInformation (
  ImpHandOverContainerTrolleyInformationId  integer auto_increment,
  ImpHandOverId  integer,
 ContainerTrolleyNumber varchar(11),
 UsedAsTrolley varchar,
 CapturedManual varchar,
 SourceOfInformation varchar(25),
     CreatedUserCode varchar(64),
    CreatedDateTime date,
    LastUpdatedUserCode varchar(64),
    LastUpdatedDateTime DATE
 );
 
 drop table Imp_FlightEvents if exists;
 CREATE TABLE Imp_FlightEvents(
	ImpFlightEventsId integer,
	FlightId integer,
	FirstULDCheckedInAt datetime,
	FirstULDCheckedInBy varchar(64), 
	FirstTimeRampCheckInCompletedAt datetime,
	FirstTimeRampCheckInCompletedBy varchar(64),
	RampCheckInCompletedAt datetime,
	RampCheckInCompletedBy varchar(64),
	CreatedUserCode varchar(64),
	CreatedDateTime datetime,
	LastUpdatedUserCode varchar(64),
	LastUpdatedDateTime datetime,
	FirstULDTowedBy varchar(64),
	FirstULDTowedAt datetime,
	LastULDTowedBy varchar(64),
	BulkShipmentsExists varchar(64),
	LastULDTowedAt datetime);

 drop table Mst_SpecialHandlingCode if exists;
CREATE TABLE Mst_SpecialHandlingCode(
	SpecialHandlingCode varchar(3),
	SpecialHandlingCodeDescription varchar(65));
	
	drop table Uld_ContentCodes if exists;
	CREATE TABLE Uld_ContentCodes(
	ContentCode varchar(1) NOT NULL,
	ContentCodeDescription varchar(65) NOT NULL,
	ApronCargoLocation varchar(1) NOT NULL,
	CreatedUser_Code varchar(64) NOT NULL,
	Created_DateTime datetime NOT NULL,
	LastUpdatedUser_Code varchar(64) NULL,
	LastUpdated_DateTime datetime NULL,
	PriorityCode numeric(2, 0) NULL,
	SortingOrder numeric(2, 0) NULL
  )


