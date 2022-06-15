--Start of Queries for Validator Utility Project--Kindly do not delete and add your queries--
drop table Mst_Airport if exists;
drop table Flt_OperativeFlight if exists;
drop table Imp_FlightEvents if exists;
drop table Exp_FlightEvents if exists;
drop table Uld_UldMaster if exists;
drop table Uld_UldType if exists;
drop table Shipment_Master if exists;
drop table Shipment_OtherChargeInfo if exists;
drop table Com_ShipmentTemperatureLogEntry  if exists;
drop table EXP_EACCEPTANCEDOCUMENTINFORMATION if exists;
drop table Mst_Carrier IF exists;

CREATE TABLE Com_ShipmentTemperatureLogEntry(
ShipmentTemperatureLogEntryId integer null,
	ShipmentId integer not null,
	Temperature varchar(30) not null,
	CapturedOn datetime not null,
	Activity varchar(30) not null,
	ShipmentDescription varchar(65) null,
	LocationCode varchar(12) null,
	CreatedUserCode varchar(64) not null,
	CreatedDateTime datetime not null,
	LastUpdatedUserCode varchar(64) null,
	LastUpdatedDateTime datetime null
); 

CREATE TABLE EXP_EACCEPTANCEDOCUMENTINFORMATION(
ShipmentNumber varchar(20) not null,
RequestedTemperatureRange varchar(10) null 
);



CREATE TABLE Shipment_OtherChargeInfo(
	ShipmentId numeric not null,
	ChargeCode varchar(2) null
);


create table Shipment_Master(
    ShipmentId integer not null,
	ShipmentNumber varchar(20) not null,
	SVC bit not null,
    Origin varchar(3) null,
	Destination varchar(3) null,
	Pieces integer null,
	Weight decimal(8, 1) null,
	WeightUnitCode varchar(12) null
	
);

alter table Shipment_Master add primary key(ShipmentId);

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
	boardingPoint varchar(3) not null,
	offPoint varchar(3) not null
);

alter table Flt_OperativeFlight add primary key(Flight_ID);

create table Imp_FlightEvents(
	id integer not null,	
	FlightId integer not null,
	FlightCompletedAt date null,
	FlightCompletedBy varchar(35) null
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

--End of Queries for Validator Utility Project--Kindly do not delete and add your queries--

