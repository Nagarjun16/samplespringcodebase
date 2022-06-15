INSERT INTO Flt_OperativeFlight (
	Flight_ID,
	CarrierCode,
	FlightNumber,
	FlightKey,
	FlightOriginDate,
	GroundHandlerCode,
	CreatedUser_Code,
	Created_DateTime)
	VALUES 
	(123456,
	'SQ',
	'SQ007',
	'SQ001',
	getDate(),
	'TEST',
	'SYSADMIN',
	getDate());
	
INSERT INTO Airmail_Manifest
	(AirmailManifestId
	,FlightId
	,SegmentId
	,AirportOfLoading
	,AirportOfOffloading
	,DestinationOffice
	,AdministrationOfOriginOfMails
	,Observations
	,CreatedUserCode
	,CreatedDateTime)
	VALUES
	(1
	,123456
	,654321
	,'SIN'
	,'DEL'
	,'DEL'
	,'SIN'
	,'TEST DATA'
	,'SYSADMIN'
	,getDate());
	
INSERT INTO
	Airmail_ManifestShipment
	(AirmailManifestShipmentId
	,AirmailManifestId
	,DN
	,ULDTrolleyNumber
	,GrossWeight
	,OriginOfficeExchange
	,DestinationOfficeExchange
	,AirportOfTranshipment
	,AirportOfOffloading
	,DateOfDispactch
	,LetterPost
	,CP
	,OtherItems
	,Remarks
	,CreatedUserCode
	,CreatedDateTime)
	VALUES
	(2
	,1
	,'SGSINAINDELBAEM92022'
	,'AKE12345SQ'
	,150.5
	,'SGSINA'
	,'INDELB'
	,'SIN'
	,'DEL'
	,getDate()
	,5
	,10
	,15
	,'FLIGHT DEPARTED'
	,'SYSADMIN'
	,getDate());