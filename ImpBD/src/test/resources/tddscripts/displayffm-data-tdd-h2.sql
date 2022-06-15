INSERT INTO Flt_OperativeFlight 
(Flight_ID, FlightKey, FlightOriginDate, CreatedUser_Code,Created_DateTime)
VALUES
(100, 'SQ001',CURRENT_TIMESTAMP,'SYSADMIN',CURRENT_TIMESTAMP);

INSERT INTO Flt_OperativeFlight_Legs 
(Flight_ID, FlightBoardPoint, FlightOffPoint, DateSTA, DateETA, DateATA, AircraftRegCode, CreatedUser_Code,Created_DateTime)
VALUES
(100,'HKK','SIN',CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP,'11', 'SYSADMIN',CURRENT_TIMESTAMP);

INSERT INTO Flt_OperativeFlight_Segments
           (Flight_ID
           ,FlightBoardPoint
           ,FlightOffPoint
           ,FlightSegmentOrder
           ,DateSTD
           ,DateSTA
           ,CreatedUser_Code
           ,Created_DateTime
           ,FlightSegmentId
           ,FFM_RejectCount )
     VALUES
(100,'HKK','SIN',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'SYSADMIN',CURRENT_TIMESTAMP,200,NULL);

INSERT INTO Imp_FreightFlightManifestByFlight
           (ImpFreightFlightManifestByFlightId
		   ,AircraftRegCode
           ,CountryCode
           ,ArrivalFlightKey
           ,ArrivaAirportCityCode
           ,MessageSequence
           ,MessageVersion
           ,MessageProcessedDate
           ,MessageStatus
           ,CreatedUserCode
           ,CreatedDateTime
           ,FlightId)
     VALUES
('101','11','65','9W0018','SIN',11,111,CURRENT_TIMESTAMP,'OK','SYSADMIN',CURRENT_TIMESTAMP,100);

INSERT INTO Imp_FreightFlightManifestBySegment
           (ImpFreightFlightManifestBySegmentId
		   ,ImpFreightFlightManifestByFlightId
           ,ArrivalDate
           ,DepartureDate
           ,CreatedUserCode
           ,CreatedDateTime
           ,FlightSegmentId)
     VALUES
(201,101,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'SYSADMIN',CURRENT_TIMESTAMP,200);

 INSERT INTO Imp_FreightFlightManifestULD
           (ImpFreightFlightManifestULDId
		 ,ImpFreightFlightManifestBySegmentId
           ,UldType
           ,UldSerialNumber
           ,UldOwnerCode
           ,CreatedUserCode
           ,CreatedDateTime
           ,ULDNumber
            ,UldLoadingIndicator 
           ,UldRemarks 
           ,VolumeAvailableCode)
     VALUES
 (303,201,'AAA','00951','SQ','SYSADMIN',CURRENT_TIMESTAMP,'AAA00951SQ','K','SLK',2);
 
INSERT INTO Imp_FreightFlightManifestByShipment
           (ImpFreightFlightManifestByShipmentId
		   ,ImpFreightFlightManifestULDId
           ,AwbPrefix
           ,AwbSuffix
           ,AwbNumber
           ,AwbDate
           ,Origin
           ,Destination
           ,ShipmentDescriptionCode
           ,Pieces
           ,WeightUnitCode
           ,Weight 
           ,VolumeUnitCode
           ,VolumeAmount 
	      ,DensityIndicator 
	      ,DensityGroupCode 
	      ,TotalPieces 
	      ,NatureOfGoodsDescription 
	      ,MovementPriorityCode 
	      ,OtherServiceInformation1 
	      ,OtherServiceInformation2 
	      ,CustomsOriginCode 
           ,CreatedUserCode
           ,CreatedDateTime
            )
     VALUES
(501,303,'AA','SS','11SS32',CURRENT_TIMESTAMP,'HKK','SIN','j',4,'K',100,'1',300,'d',4,8,'COURRIER','s','ss','dd','r','SYSADMIN',CURRENT_TIMESTAMP);
   
		   INSERT INTO Imp_FreightFlightManifestByShipmentSHC
           (ImpFreightFlightManifestByShipmentSHCId
		   ,ImpFreightFlightManifestByShipmentId
           ,SpecialHandlingCode
           ,CreatedUserCode
           ,CreatedDateTime
        )
     VALUES
(107,501,'AAT','SYSADMIN',CURRENT_TIMESTAMP);

INSERT INTO Imp_FreightFlightManifestOtherSvcInfo
           (ImpFreightFlightManifestOtherSvcInfoId
           ,ServiceInformation 
           ,CreatedUserCode
           ,CreatedDateTime
           ,TransactionSequenceNo
           ,ImpFreightFlightManifestByShipmentId)
     VALUES
 (108,'dddd','SYSADMIN',CURRENT_TIMESTAMP,1,501);
 
INSERT INTO Imp_FreightFlightManifestShipmentDimension
           (ImpFreightFlightManifestShipmentDimensionId
		   ,ImpFreightFlightManifestByShipmentId
           ,WeightUnitCode
           ,Weight
           ,MeasurementUnitCode
           ,DimensionLength
           ,DimensionWIdth
           ,DimensionHeight
           ,NumberOfPieces
           ,CreatedUserCode
           ,CreatedDateTime
          )
     VALUES
 (1,501,'K',10,'L',2,2,2,4,'SYSADMIN',CURRENT_TIMESTAMP);
 
INSERT INTO Imp_FreightFlightManifestShipmentOCI
           (ImpFreightFlightManifestShipmentOCIId
		 ,CountryCode
            ,InformationIdentifier 
	      ,CSRCIIdentifier 
	      ,SCSRCInformation 
           ,CreatedUserCode
           ,CreatedDateTime
           ,TransactionSequenceNo
           ,ImpFreightFlightManifestByShipmentId)
     VALUES
(2,65,'dd','ff','ddd','SYSADMIN',CURRENT_TIMESTAMP,1,501);

INSERT INTO Imp_FreightFlightManifestShipmentOnwardMovement
           (ImpFreightFlightManifestShipmentOnwardMovementId
		   ,ImpFreightFlightManifestByShipmentId
           ,AirportCityCode
           ,CarrierCode
           ,FlightNumber
           ,DepartureDate 
           ,CreatedUserCode
           ,CreatedDateTime
           ,TransactionSequenceNo)
     VALUES
 (3,501,'SIN','002','0018',CURRENT_TIMESTAMP,'SYSADMIN',CURRENT_TIMESTAMP,1);


