insert into Mst_Airport(AirportCode, AirportCityCode, AirportName) values('SIN', 'SIN', 'Singapore');
insert into Mst_Airport(AirportCode, AirportCityCode, AirportName) values('HKG', 'HKG', 'Honkong');
insert into Mst_Airport(AirportCode, AirportCityCode, AirportName) values('ICN', 'ICN', 'ICN');
INSERT INTO Mst_Carrier
		(CarrierCode,CarrierFullName)
		VALUES
		('SQ', 'Singapore Airline');

INSERT INTO Agt_SIDHeader( RequestNumber, ShipmentNumber, Pieces,
HandlingInformation,NatureOfGoodsDescription,Status,CreatedUser_Code,Created_DateTime) 
VALUES( '20180111152908','61820906933',1,'d','d','Created','SYSADMIN', CURRENT_TIMESTAMP());

INSERT INTO Agt_SIDHeader( RequestNumber, ShipmentNumber, Pieces,
HandlingInformation,NatureOfGoodsDescription,Status,CreatedUser_Code,Created_DateTime) 
VALUES( '20180111152909','11620906934',1,'d','d','Created','SYSADMIN', CURRENT_TIMESTAMP());

INSERT INTO Agt_SIDCustomerDtls(SIDHeaderId, CustomerType, Name) 
VALUES(1, 'SHP','kaushlender');

INSERT INTO Agt_SIDCustomerDtls(SIDHeaderId, CustomerType, Name) 
VALUES(1, 'CNE','kaushal');

INSERT INTO Agt_SIDCustomerDtls(SIDHeaderId, CustomerType, Name) 
VALUES(2, 'SHP','Ram');

INSERT INTO Agt_SIDCustomerDtls(SIDHeaderId, CustomerType, Name) 
VALUES(2, 'CNE','Shyam');

INSERT INTO Agt_SIDCarrierRouting
           (SIDHeaderId,TransactionSequenceNo,FromPoint,ToPoint,CarrierCode
           ,FlightKey,FlightDate)
     VALUES
           (1,1,'SIN','HKG','SQ'
           ,'SQ102',CURRENT_TIMESTAMP())	;
           
INSERT INTO Agt_SIDCarrierRouting
           (SIDHeaderId,TransactionSequenceNo,FromPoint,ToPoint,CarrierCode
           ,FlightKey,FlightDate)
     VALUES
           (2,1,'SIN','HKG','SQ'
           ,'SQ102',CURRENT_TIMESTAMP())	;         

INSERT INTO Agt_SIDCustAddressInfo
           (SIDCustomerDtlsId,StreetAddress1,StreetAddress2,
           CustomerPlace,StateCode,CountryCode,PostalCode)
     VALUES(1,'StreetAddress1','StreetAddress2','CustomerPlace1'
           ,'StateCod1','IN','110059');

INSERT INTO Agt_SIDCustAddressInfo
           (SIDCustomerDtlsId,StreetAddress1,StreetAddress2,
           CustomerPlace,StateCode,CountryCode,PostalCode)
     VALUES(2,'StreetAddress3','StreetAddress4','CustomerPlace2'
           ,'StateCod2','IN','110022');
           
INSERT INTO Agt_SIDCustAddressInfo
           (SIDCustomerDtlsId,StreetAddress1,StreetAddress2,
           CustomerPlace,StateCode,CountryCode,PostalCode)
     VALUES(3,'StreetAddress1','StreetAddress2','CustomerPlace1'
           ,'StateCod1','IN','110059');

INSERT INTO Agt_SIDCustAddressInfo
           (SIDCustomerDtlsId,StreetAddress1,StreetAddress2,
           CustomerPlace,StateCode,CountryCode,PostalCode)
     VALUES(4,'StreetAddress3','StreetAddress4','CustomerPlace2'
           ,'StateCod2','IN','110022');           

INSERT INTO Mst_ContactType
           (ContactTypeCode,ContactTypeDescription)
     VALUES('TE','TELEPHONE');
INSERT INTO Mst_ContactType
           (ContactTypeCode,ContactTypeDescription)
     VALUES('FX','TELEFAX');

INSERT INTO Agt_SIDCustContactInfo
           (SIDCustomerAddressInfoId,ContactIdentifier,ContactDetail)
     VALUES (1,'TE','9560286992');

INSERT INTO Agt_SIDCustContactInfo
           (SIDCustomerAddressInfoId,ContactIdentifier,ContactDetail)
     VALUES (1,'FX','4455443434343');

INSERT INTO Agt_SIDCustContactInfo
           (SIDCustomerAddressInfoId,ContactIdentifier,ContactDetail)
     VALUES (2,'TE','7503251528');

INSERT INTO Agt_SIDCustContactInfo
           (SIDCustomerAddressInfoId,ContactIdentifier,ContactDetail)
     VALUES (2,'FX','5544343434344');
     
     
INSERT INTO Agt_SIDCustContactInfo
           (SIDCustomerAddressInfoId,ContactIdentifier,ContactDetail)
     VALUES (3,'TE','9560286992');

INSERT INTO Agt_SIDCustContactInfo
           (SIDCustomerAddressInfoId,ContactIdentifier,ContactDetail)
     VALUES (3,'FX','4455443434343');

INSERT INTO Agt_SIDCustContactInfo
           (SIDCustomerAddressInfoId,ContactIdentifier,ContactDetail)
     VALUES (4,'TE','7503251528');

INSERT INTO Agt_SIDCustContactInfo
           (SIDCustomerAddressInfoId,ContactIdentifier,ContactDetail)
     VALUES (4,'FX','5544343434344');     
     
INSERT INTO Agt_SIDPaymentInfo
           (SIDHeaderId,WeightValuationCharges,OtherChanges,CurrencyCode,CarriageValue
           ,CustomsValue)
     VALUES  (1,'COL','COL','USD',1.0,2.0);
     
INSERT INTO Agt_SIDPaymentInfo
           (SIDHeaderId,WeightValuationCharges,OtherChanges,CurrencyCode,CarriageValue
           ,CustomsValue)
     VALUES  (2,'COL','COL','USD',2.0,3.0);     
     
INSERT INTO Com_AWBStock(CarrierCode,StockId,StockCategoryCode,FirstAWBNumber,NumberOfAWB,LowStockLimit)  
	VALUES('SQ','123456','OSC','20906933',8,5);
INSERT INTO Com_AWBStock(CarrierCode,StockId,StockCategoryCode,FirstAWBNumber,NumberOfAWB,LowStockLimit)  
	VALUES('SQ','123457','MAIL','20906933',9,6);
INSERT INTO Com_AWBStockDetails
           (AWBStockId,AWBPrefix,AWBSuffix,AWBNumber,Reserved
           ,ReservedBy,ReservedOn,Issued,IssuedOn,Deleted,DeletedOn)
     VALUES (1,'618','20906933','61820906933',0,null,null,0,null
           ,0,null);
INSERT INTO Com_AWBStockDetails
           (AWBStockId,AWBPrefix,AWBSuffix,AWBNumber,Reserved
           ,ReservedBy,ReservedOn,Issued,IssuedOn,Deleted,DeletedOn)
     VALUES (2,'618','20906944','61820906944',0,null,null,0,null
           ,0,null);	          

