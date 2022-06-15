/**
 * 
 * DisplayffmServiceImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 30 April, 2018 NIIT -
 */
package com.ngen.cosys.impbd.displayffm.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.displayffm.dao.DisplayffmDao;
import com.ngen.cosys.impbd.displayffm.model.DisplayffmByFlightModel;
import com.ngen.cosys.impbd.displayffm.model.FreightFlightManifestBySegmentListModel;
import com.ngen.cosys.impbd.displayffm.model.FreightFlightManifestBySegmentModel;
import com.ngen.cosys.impbd.displayffm.model.FreightFlightManifestByShipmentModel;
import com.ngen.cosys.impbd.displayffm.model.FreightFlightManifestUldModel;
import com.ngen.cosys.impbd.displayffm.model.SearchDisplayffmModel;
import com.ngen.cosys.impbd.model.FFMCountDetails;
import com.ngen.cosys.impbd.model.FFMVersionDetails;

@Service
@Transactional
public class DisplayffmServiceImpl implements DisplayffmService {

   @Autowired
   private DisplayffmDao displayffmDao;

   private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("ddMMMyyyy HH:mm");

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.displayffm.service.DisplayffmService#search(com.ngen.
    * cosys.impbd.displayffm.model.SearchDisplayffmModel)
    */
   @Override
   public List<DisplayffmByFlightModel> search(SearchDisplayffmModel searchDisplayffmModel) throws CustomException {
      List<DisplayffmByFlightModel> displayffmByFlightList = displayffmDao.search(searchDisplayffmModel);

      Optional<List<DisplayffmByFlightModel>> oFFMResponse = Optional.ofNullable(displayffmByFlightList);
      if (!oFFMResponse.isPresent()) {
         throw new CustomException("data.no.ffm.received.for.flight", "flightNumber", ErrorType.APP);
      }

      for (DisplayffmByFlightModel flightCargoDetails : displayffmByFlightList) {

         BigInteger tempFlightULD = BigInteger.ZERO;
         BigInteger tempFlightPiece = BigInteger.ZERO;
         BigDecimal tempFlightWeight = BigDecimal.ZERO;
         BigInteger tempFlightCargoInULD = BigInteger.ZERO;
         BigInteger tempFlightLooseCargo = BigInteger.ZERO;
         Set<FreightFlightManifestByShipmentModel> shipmentRecords = new HashSet<>();
         if (!StringUtils.isEmpty(searchDisplayffmModel.getSegment())) {
            flightCargoDetails.setSegment(searchDisplayffmModel.getSegment());
         }         
         for (FreightFlightManifestBySegmentListModel segmentListData : flightCargoDetails.getSegmentsList()) {
			for (FreightFlightManifestBySegmentModel segmentData : segmentListData.getSegments()) {

               BigInteger tempSegmentULDCount = BigInteger.ZERO;
               segmentData.setLooseCargoCount(BigInteger.valueOf(segmentData.getFreightawbDetails().size()));
               BigInteger tempSegmentPiece = BigInteger.ZERO;
               BigDecimal tempSegmentWeight = BigDecimal.ZERO;
               BigInteger tempCargoInULD = BigInteger.ZERO;
               String allotmentUldCount=null;
               
               if(!CollectionUtils.isEmpty(segmentData.getSegmentUldGruopDetailsCountList())) {
            	   for (String count :segmentData.getSegmentUldGruopDetailsCountList()) {
					if(StringUtils.isEmpty(allotmentUldCount)) {
						allotmentUldCount = count;
					}else {
						allotmentUldCount=allotmentUldCount+" ,"+count;
					}
            	   }
               }
               
               for (FreightFlightManifestUldModel uldModelData : segmentData.getFreightmanifestedUlds()) {

                  BigInteger tempPieceCount = BigInteger.ZERO;
                  BigDecimal tempWeightValue = BigDecimal.ZERO;
                  BigInteger uldAWBCount = BigInteger.ZERO;
                  if (!uldModelData.getShipments().isEmpty()) {
                     tempSegmentULDCount = tempSegmentULDCount.add(BigInteger.ONE);
                  }
                 
                  for (FreightFlightManifestByShipmentModel shpModel : uldModelData.getShipments()) {
                	 Set<FreightFlightManifestByShipmentModel> duplicateShipments = shipmentRecords.stream()
                			  .filter(shipment -> shipment.getShipmentNumber().equalsIgnoreCase(shpModel.getShipmentNumber()))
                			  .collect(Collectors.toSet());
                     tempPieceCount = tempPieceCount.add(shpModel.getPiece());
                     tempWeightValue = tempWeightValue.add(shpModel.getWeight());
                     if (CollectionUtils.isEmpty(duplicateShipments)) {
                         uldAWBCount = uldAWBCount.add(BigInteger.ONE);
                         tempCargoInULD = tempCargoInULD.add(BigInteger.ONE);
					 }
                   
                     // Concatenate SHC
                     if (!CollectionUtils.isEmpty(shpModel.getShc())) {
                        StringBuilder shcBuilder = new StringBuilder();
                       
                        shpModel.getShc().forEach(t -> {
                           shcBuilder.append(t.getSpecialHandlingCode());
                           shcBuilder.append(" ");
                        });
                        shpModel.setShcCode(shcBuilder.toString());
                     }

                     // Concatenate Onward Movement Information
                     if (!CollectionUtils.isEmpty(shpModel.getMovementInfo())) {
                        StringBuilder movementInfo = new StringBuilder();
                        shpModel.getMovementInfo().forEach(e -> {

                           if (!StringUtils.isEmpty(e.getAirportCityCode())) {
                              movementInfo.append(e.getAirportCityCode());
                              movementInfo.append("/");
                           }

                           if (!StringUtils.isEmpty(e.getCarrierCode())) {
                              movementInfo.append(e.getCarrierCode());
                           }

                           if (!StringUtils.isEmpty(e.getFlightNumber())) {
                              movementInfo.append(e.getFlightNumber());
                           }

                           if (!ObjectUtils.isEmpty(e.getDepartureDate())) {
                              movementInfo.append("/");
                              movementInfo.append(FORMATTER.format(e.getDepartureDate()).toUpperCase());
                              movementInfo.append(" ");
                              if(Objects.isNull(flightCargoDetails.getEta())){
                           	   movementInfo
                                  .append(this.getDifferenceTime(flightCargoDetails.getSta(), e.getDepartureDate()));   
                              }else {
                           	   movementInfo
                                  .append(this.getDifferenceTime(flightCargoDetails.getEta(), e.getDepartureDate()));
                              }
                           }

                           if (!StringUtils.isEmpty(e.getTransferType())) {
                              movementInfo.append("/");
                              movementInfo.append(e.getTransferType());
                           }
                        });

                        shpModel.setOnwardMovement(movementInfo.toString());
                     }
               }
                  shipmentRecords.addAll(uldModelData.getShipments());
              uldModelData.setPieceCount(tempPieceCount);
              uldModelData.setWeight(tempWeightValue);
              uldModelData.setShipmentCount(uldAWBCount);
              tempSegmentPiece = tempSegmentPiece.add(tempPieceCount);
              tempSegmentWeight = tempSegmentWeight.add(tempWeightValue);
           }
           for (FreightFlightManifestByShipmentModel looseCargo : segmentData.getFreightawbDetails()) {

              tempSegmentPiece = tempSegmentPiece.add(looseCargo.getPiece());
              tempSegmentWeight = tempSegmentWeight.add(looseCargo.getWeight());

                  // Concatenate SHC
                  if (!CollectionUtils.isEmpty(looseCargo.getShc())) {
                     StringBuilder shcBuilder = new StringBuilder();
                     looseCargo.getShc().forEach(t -> {
                        shcBuilder.append(t.getSpecialHandlingCode());
                        shcBuilder.append(" ");
                     });
                     looseCargo.setShcCode(shcBuilder.toString());
                  }

                  // Concatenate Onward Movement Information
                  if (!CollectionUtils.isEmpty(looseCargo.getMovementInfo())) {
                     StringBuilder movementInfo = new StringBuilder();
                     looseCargo.getMovementInfo().forEach(e -> {

                        if (!StringUtils.isEmpty(e.getAirportCityCode())) {
                           movementInfo.append(e.getAirportCityCode());
                           movementInfo.append("/");
                        }

                        if (!StringUtils.isEmpty(e.getCarrierCode())) {
                           movementInfo.append(e.getCarrierCode());
                        }

                        if (!StringUtils.isEmpty(e.getFlightNumber())) {
                           movementInfo.append(e.getFlightNumber());
                        }

                        if (!ObjectUtils.isEmpty(e.getDepartureDate())) {
                           movementInfo.append("/");
                           movementInfo.append(FORMATTER.format(e.getDepartureDate()).toUpperCase());
                           movementInfo.append(" ");
                           if(Objects.isNull(flightCargoDetails.getEta())){
                        	   movementInfo
                               .append(this.getDifferenceTime(flightCargoDetails.getSta(), e.getDepartureDate()));   
                           }else {
                        	   movementInfo
                               .append(this.getDifferenceTime(flightCargoDetails.getEta(), e.getDepartureDate()));
                           }
                          
                        }

                        if (!StringUtils.isEmpty(e.getTransferType())) {
                           movementInfo.append("/");
                           movementInfo.append(e.getTransferType());
                        }
                     });

                     looseCargo.setOnwardMovement(movementInfo.toString());
                  }
            }
					segmentData.setUldCount(tempSegmentULDCount);
					segmentData.setSegmentPieceCount(tempSegmentPiece);
					segmentData.setSegmentWeight(tempSegmentWeight);
					segmentData.setCargoInULD(tempCargoInULD);
					segmentData.setSegmentUldGruopDetailsCount(allotmentUldCount);
					tempFlightPiece = tempFlightPiece.add(segmentData.getSegmentPieceCount());
					tempFlightWeight = tempFlightWeight.add(segmentData.getSegmentWeight());
					tempFlightCargoInULD = tempFlightCargoInULD.add(segmentData.getCargoInULD());
					tempFlightLooseCargo = tempFlightLooseCargo.add(segmentData.getLooseCargoCount());
					tempFlightULD = tempFlightULD.add(segmentData.getUldCount());
            }

            flightCargoDetails.setPieceCount(tempFlightPiece);
            flightCargoDetails.setWeight(tempFlightWeight);
            flightCargoDetails.setCargoInULD(tempFlightCargoInULD);
            flightCargoDetails.setLooseCargo(tempFlightLooseCargo);
            flightCargoDetails.setUldCount(tempFlightULD);
         }

         // For checking the status of FFM
         for (FFMCountDetails ffmCountDetails : flightCargoDetails.getFfmReceivedDetails()) {

            if (ObjectUtils.isEmpty(ffmCountDetails.getFfmVersionDetails())) {
               List<FFMVersionDetails> versionDetails = new ArrayList<>();
               FFMVersionDetails version = new FFMVersionDetails();
               ffmCountDetails.setMessageType("FFM");
               ffmCountDetails.setCarrierCode(flightCargoDetails.getCarrierCode());
               ffmCountDetails.setFlightDigits(flightCargoDetails.getFlightDigits());
               ffmCountDetails.setMessageStatus("ERROR");
               String status = displayffmDao.getStatus(ffmCountDetails);
               if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("ERROR")) {
                  version.setMessageVersionWithCopy("Message has been rejected");
                  versionDetails.add(version);
                  ffmCountDetails.setFfmVersionDetails(versionDetails);
               }

            }

         }
      }
      return displayffmByFlightList;
   }

    @Override
   public List<DisplayffmByFlightModel> updateFFMstatus(SearchDisplayffmModel searchDisplayffmModel)
         throws CustomException {
      List<DisplayffmByFlightModel> list = new ArrayList<DisplayffmByFlightModel>();
      displayffmDao.updateFFMstatus(searchDisplayffmModel);
      return list;
   }

/**
    * Method to derive difference in time
    * 
    * @param incoming
    * @param departure
    * @return Difference between incoming and outgoing
    */
   private String getDifferenceTime(LocalDateTime incoming, LocalDateTime departure) {
      Duration duration = Duration.between(departure, incoming);
      long seconds = Math.abs(duration.getSeconds());
      long hours = seconds / 3600;
      seconds -= (hours * 3600);
      long minutes = seconds / 60;
      return hours + "Hr " + minutes + "min";
   }
}