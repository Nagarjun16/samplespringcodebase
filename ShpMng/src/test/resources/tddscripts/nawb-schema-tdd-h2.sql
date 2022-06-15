drop table Mst_Airport if exists;
drop table Mst_Carrier if exists;
drop table Mst_ContactType if exists;
drop table Agt_SIDHeader if exists;
drop table Agt_SIDCustomerDtls if exists;
drop table Agt_SIDCarrierRouting if exists;
drop table Agt_SIDCustAddressInfo if exists;
drop table Agt_SIDCustContactInfo if exists;
drop table Agt_SIDPaymentInfo if exists;
drop table Com_AWBStockDetails if exists;
drop table Com_AWBStock if exists;
drop table Exp_NeutralAWB_Master if exists;

create table Mst_Airport(	
	AirportCode varchar(3) not null,
	AirportCityCode varchar(3),
	AirportName varchar(35));

alter table Mst_Airport add primary key(AirportCode);

CREATE TABLE Mst_Carrier(CarrierCode varchar(3) NOT NULL,CarrierFullName varchar(35) NOT NULL);
alter table Mst_Carrier add primary key(CarrierCode);

CREATE TABLE Agt_SIDHeader(
	SIDHeaderId integer NOT NULL auto_increment,
	RequestNumber varchar(15) NOT NULL,
	ShipmentNumber varchar(15) NULL,
	Pieces integer NULL,
	HandlingInformation varchar(64) NULL,
	NatureOfGoodsDescription varchar(20) NOT NULL,
	Status varchar(15) NULL,
	CreatedUser_Code varchar(64) NOT NULL,
	Created_DateTime datetime NOT NULL);
alter table Agt_SIDHeader add primary key(SIDHeaderId);

CREATE TABLE Agt_SIDCustomerDtls(
	SIDCustomerDtlsId integer NOT NULL auto_increment,
	SIDHeaderId integer NOT NULL,
	CustomerType varchar(3) NOT NULL,
	Name varchar(35) NOT NULL);
	
alter table Agt_SIDCustomerDtls add primary key(SIDHeaderId,CustomerType);
alter table Agt_SIDCustomerDtls add foreign key(SIDHeaderId) references Agt_SIDHeader(SIDHeaderId);

CREATE TABLE Agt_SIDCarrierRouting(
	SIDHeaderId integer NOT NULL,
	TransactionSequenceNo integer NOT NULL,
	FromPoint varchar(3) NOT NULL,
	ToPoint varchar(3) NOT NULL,
	CarrierCode varchar(3) NULL,
	FlightKey varchar(8) NULL,
	FlightDate datetime NULL);

alter table Agt_SIDCarrierRouting add primary key(SIDHeaderId,TransactionSequenceNo);
alter table Agt_SIDCarrierRouting add foreign key(SIDHeaderId) references Agt_SIDHeader(SIDHeaderId);
alter table Agt_SIDCarrierRouting add foreign key(FromPoint) references Mst_Airport(AirportCode);
alter table Agt_SIDCarrierRouting add foreign key(ToPoint) references Mst_Airport(AirportCode);
alter table Agt_SIDCarrierRouting add foreign key(CarrierCode) references Mst_Carrier(CarrierCode);


CREATE TABLE Agt_SIDCustAddressInfo(
	SIDCustomerAddressInfoId integer NOT NULL auto_increment,
	SIDCustomerDtlsId integer NOT NULL,
	StreetAddress1 varchar(35) NOT NULL,
	StreetAddress2 varchar(35) NULL,
	CustomerPlace varchar(17) NOT NULL,
	StateCode varchar(9) NULL,
	CountryCode varchar(2) NOT NULL,
	PostalCode varchar(9) NULL);
alter table AGT_SIDCUSTADDRESSINFO add primary key(SIDCUSTOMERADDRESSINFOID ,SIDCUSTOMERDTLSID );
alter table AGT_SIDCUSTADDRESSINFO add foreign key(SIDCUSTOMERDTLSID ) references AGT_SIDCUSTOMERDTLS (SIDCUSTOMERDTLSID );

CREATE TABLE Mst_ContactType(
	ContactTypeCode varchar(3) NOT NULL,
	ContactTypeDescription varchar(35) NOT NULL);
alter table MST_CONTACTTYPE add primary key(CONTACTTYPECODE );

CREATE TABLE Agt_SIDCustContactInfo(
	SIDCustomerContactInfoId integer NOT NULL auto_increment,
	SIDCustomerAddressInfoId integer NOT NULL,
	ContactIdentifier varchar(3) NOT NULL,
	ContactDetail varchar(25) NOT NULL);

alter table AGT_SIDCUSTCONTACTINFO add primary key(SIDCUSTOMERCONTACTINFOID ,SIDCUSTOMERADDRESSINFOID ,CONTACTIDENTIFIER ,CONTACTDETAIL );
alter table AGT_SIDCUSTCONTACTINFO  add foreign key(ContactIdentifier) references MST_CONTACTTYPE (CONTACTTYPECODE );
alter table AGT_SIDCUSTCONTACTINFO  add foreign key(SIDCUSTOMERADDRESSINFOID ) references AGT_SIDCUSTADDRESSINFO (SIDCUSTOMERADDRESSINFOID );

CREATE TABLE Agt_SIDPaymentInfo(
	SIDHeaderId integer NOT NULL,
	WeightValuationCharges varchar(3) NULL,
	OtherChanges varchar(3) NULL,
	CurrencyCode varchar(3) NULL,
	CarriageValue decimal(14, 2) NULL,
	CustomsValue decimal(13, 2) NULL);
alter table Agt_SIDPaymentInfo add primary key(SIDHeaderId);
alter table Agt_SIDPaymentInfo add foreign key(SIDHeaderId) references Agt_SIDHeader(SIDHeaderId);

CREATE TABLE Com_AWBStock(
	AWBStockId integer NOT NULL auto_increment,
	CarrierCode varchar(3) NOT NULL,
	StockId varchar(6) NOT NULL,
	StockCategoryCode varchar(10) NOT NULL,
	FirstAWBNumber varchar(8) NOT NULL,
	NumberOfAWB integer  NULL,
	LowStockLimit integer  NULL);
alter table Com_AWBStock add primary key(AWBStockId,CarrierCode);
alter table Com_AWBStock add foreign key(CarrierCode ) references Mst_Carrier(CarrierCode);

CREATE TABLE Com_AWBStockDetails(
	AWBStockDetailsId integer NOT NULL auto_increment,
	AWBStockId integer NOT NULL,
	AWBPrefix varchar(3) NOT NULL,AWBSuffix varchar(8) NOT NULL,
	AWBNumber varchar(11) NOT NULL,Reserved bit NULL,
	ReservedBy varchar(64) NULL,ReservedOn datetime NULL,
	Issued bit NULL,IssuedOn datetime NULL,Deleted bit NULL,
	DeletedOn datetime NULL,Processing datetime NULL,Cancelled datetime NULL,
	Booked datetime NULL,Printed datetime NULL,RePrinted datetime NULL,Duplicated bit NULL);
alter table Com_AWBStockDetails add primary key(AWBStockDetailsId,AWBStockId);
alter table Com_AWBStockDetails add foreign key(AWBStockId) references Com_AWBStock(AWBStockId);

CREATE TABLE Exp_NeutralAWB_Master(
	NeutralAWBId integer NOT NULL auto_increment,
	SIDHeaderId integer NULL,
	AwbPrefix varchar(3) NOT NULL,
	AwbSuffix varchar(8) NOT NULL,
	AwbNumber varchar(11) NOT NULL,
	AwbDate datetime NOT NULL,
	Origin varchar(3) NOT NULL,
	Destination varchar(3) NOT NULL,
	Pieces numeric(4, 0) NOT NULL,
	WeightUnitCode varchar(1) NOT NULL,
	Weight decimal(8, 1) NOT NULL,
	NatureOfGoodsDescription varchar(20) NOT NULL,
	VolumeUnitCode varchar(2) NULL,
	VolumeAmount decimal(11, 2) NULL,
	DensityIndicator varchar(2) NULL,
	DensityGroupCode numeric(2, 0) NULL,
	ShippersCertificateSignature varchar(35) NULL,
	CarriersExecutionDate date NULL,
	CarriersExecutionPlace varchar(17) NULL,
	CarriersExecutionAuthorisationSignature varchar(35) NULL,
	CustomOrigin varchar(2) NULL,
	CommissionInformationCASSIndicator varchar(2) NULL,
	CommissionInformationCommissionAmount decimal(14, 2) NULL,
	SalesIncentiveDetailCommissionPercentage decimal(14, 2) NULL,
	SalesIncentiveDetailCASSIndicator varchar(2) NULL,
	ARDAgentReference varchar(3) NULL,
	EndorsedBy varchar(64) NULL,
	EndorsedFor varchar(35) NULL,
	EndorsedOn datetime NULL,
	CreatedUserCode varchar(64) NOT NULL,
	CreatedDateTime datetime NOT NULL,
	LastUpdatedUserCode varchar(64) NULL,
	LastUpdatedDateTime datetime NULL,
	HandlingArea varchar(25) NULL);
alter table Exp_NeutralAWB_Master add primary key(NeutralAWBId,AwbPrefix,AwbSuffix,AwbNumber,AwbDate);
alter table Exp_NeutralAWB_Master add foreign key(SIDHeaderId) references Agt_SIDHeader(SIDHeaderId);
