--Start of Queries for Validator Utility Project--Kindly do not delete and add your queries--

drop table Flt_OperativeFlight if exists;
create table Flt_OperativeFlight(
	Flight_ID INTEGER NOT NULL,
	FlightNumber varchar(5) NOT NULL,
	FlightKey varchar(8) NOT NULL,
	FlightOriginDate date NOT null,
	GroundHandlerCode varchar(32) NOT null,
	CreatedUser_Code varchar(64) NOT  null,
	Created_DateTime date NOT null
);
alter table Flt_OperativeFlight add primary key(Flight_ID);


drop table Imp_HandOver if exists;
create table Imp_HandOver(
   ImpHandOverId integer auto_increment,
   FlightId integer NOT NULL,
   HandedOverAt varchar(64) null,
   TractorNumber varchar(15) null,
   HandedOverBy varchar(64) null,
   StartedAt   date null,
   CompletedAt date null,
   TripId integer null,
    CreatedUserCode varchar(64) NOT null,
    CreatedDateTime date NOT null,
    LastUpdatedUserCode varchar(64) null,
    LastUpdatedDateTime date null
    
   );
 alter table  Imp_HandOver add primary key(ImpHandOverId);
 --alter table  Imp_HandOver add foreign key(FlightId) references Flt_OperativeFlight(Flight_ID);
   
 drop table Imp_HandOverContainerTrolleyInformation if exists;
 create table Imp_HandOverContainerTrolleyInformation (
  ImpHandOverContainerTrolleyInformationId  integer auto_increment,
  ImpHandOverId  integer NOT  null,
 ContainerTrolleyNumber varchar(11) NOT  null,
 UsedAsTrolley VARCHAR null,
 CapturedManual VARCHAR null,
 SourceOfInformation varchar(25) null,
     CreatedUserCode varchar(64) NOT null,
    CreatedDateTime date NOT null,
    LastUpdatedUserCode varchar(64) null,
    LastUpdatedDateTime DATE NULL
 );
alter table Imp_HandOverContainerTrolleyInformation add primary key(ImpHandOverContainerTrolleyInformationId);
--alter table Imp_HandOverContainerTrolleyInformation add foreign key(ImpHandOverId) references Imp_HandOver(ImpHandOverId);


--End of Queries for Validator Utility Project--Kindly do not delete and add your queries--

 