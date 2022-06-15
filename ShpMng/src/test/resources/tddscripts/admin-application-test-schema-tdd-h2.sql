drop table Customer_Master if exists;
drop table Customer_Types if exists;
drop table Customer_Location if exists;
drop table Com_ShipmentTemperatureLogEntry if exists;

CREATE TABLE Customer_Master(
	Customer_ID numeric(15, 0) auto_increment NOT NULL PRIMARY KEY,
	CustomerCode varchar(8) NOT NULL,
	CustomerShortName varchar(35) NOT NULL,
        BlacklistEndDate datetime null);
	
	
CREATE TABLE Customer_Types(
	Customer_ID numeric(15, 0) NOT NULL,
	CustomerTypeCode varchar(10) NOT NULL);

	
CREATE TABLE Customer_Location(
	CustomerLocationId numeric(15, 0) NOT NULL,
	CustomerID numeric(15, 0) NOT NULL,
	DeliveryLocation varchar(10) NOT NULL);

CREATE TABLE Com_ShipmentTemperatureLogEntry(
	ShipmentTemperatureLogEntryId numeric(15, 0) IDENTITY(1,1) NOT NULL,
	ShipmentId numeric(15, 0) NOT NULL,
	Temperature varchar(30) NOT NULL,
	CapturedOn datetime NOT NULL,
	Activity varchar(30) NOT NULL,
	ShipmentDescription varchar(65) NULL,
	LocationCode varchar(12) NOT NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	LastUpdatedUserCode varchar(64) NULL,
	LastUpdatedDateTime datetime NULL,