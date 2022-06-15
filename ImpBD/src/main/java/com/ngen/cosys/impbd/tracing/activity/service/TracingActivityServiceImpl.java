/**
 * This is implementation class which helps in creating tracing record for
 * shipments
 */
package com.ngen.cosys.impbd.tracing.activity.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.tracing.activity.dao.TracingActivityDao;
import com.ngen.cosys.impbd.tracing.activity.model.TracingActivityShipmentInventoryModel;
import com.ngen.cosys.impbd.tracing.activity.model.TracingActivityShipmentModel;
import com.ngen.cosys.impbd.tracing.activity.model.TracingActivityShipmentShcModel;
import com.ngen.cosys.validator.enums.ShipmentType;

@Service
public class TracingActivityServiceImpl implements TracingActivityService {

   @Autowired
   TracingActivityDao dao;

   private static final String FDCA = "FDCA";
   private static final String FDMB = "FDMB";
   private static final String DAMG = "DAMG";

   @Override
   public void createTracing(List<TracingActivityShipmentModel> requestModel) throws CustomException {
      // Check for empty irregularity
      if (!CollectionUtils.isEmpty(requestModel)) {
         // Fetch the Flight Info
         TracingActivityShipmentModel flightInfo = dao.getFlightInfo(requestModel.get(0));

         // iterate the list
         for (TracingActivityShipmentModel t : requestModel) {
            // 1. Check whether tracing exists
            Boolean isTracingExistsForShipment = dao.checkTracingActivityExistsForShipment(t);
            if (!isTracingExistsForShipment) {
               // Get SHC of an shipment
               List<TracingActivityShipmentShcModel> shcInfo = dao.getShc(t);
               t.setShcs(shcInfo);
               // Set the flight info
               t.setFlightKey(flightInfo.getFlightKey());
               t.setFlightDate(flightInfo.getFlightDate());
               t.setBoardingPoint(flightInfo.getBoardingPoint());
               t.setOffPoint(flightInfo.getOffPoint());
               t.setCarrier(flightInfo.getCarrier());
               t.setCaseStatus("INPROGRESS");
               t.setTotalPieces(flightInfo.getTotalPieces());

               // Get the case number
               BigInteger lastCaseNumber = dao.getMaxCaseNumber();
               lastCaseNumber = lastCaseNumber.add(BigInteger.ONE);
               String caseNumber = "CT" + StringUtils.leftPad(String.valueOf(lastCaseNumber.intValue()), 5, '0');
               t.setCaseNumber(caseNumber);

               // Set the total pieces/weight and Irregularity Piece/weight if empty
               List<TracingActivityShipmentInventoryModel> tracingShipmentInventory = new ArrayList<>();
               BigInteger totalPieces = BigInteger.ZERO;
               BigInteger irregularityPieces = BigInteger.ZERO;
               BigDecimal totalWeight = BigDecimal.ZERO;
               BigDecimal irregularityWeight = BigDecimal.ZERO;

               if (FDCA.equalsIgnoreCase(t.getIrregularityTypeCode())
                     || FDMB.equalsIgnoreCase(t.getIrregularityTypeCode())
                     || (DAMG.equalsIgnoreCase(t.getIrregularityTypeCode())
                           && ShipmentType.Type.MAIL.equalsIgnoreCase(t.getShipmentType()))) {
                  tracingShipmentInventory = dao.getInventoryInfoForTracing(t);
                  if (!CollectionUtils.isEmpty(tracingShipmentInventory)) {
                     for (TracingActivityShipmentInventoryModel obj : tracingShipmentInventory) {
                        obj.setNatureOfGoodsDescription(t.getNatureOfGoodsDescription());
                        obj.setWeightUnitCode(t.getWeightUnitCode());

                        // Calculate pieces/weight
                        totalPieces = totalPieces.add(obj.getInventoryPieces());
                        irregularityPieces = irregularityPieces.add(obj.getInventoryPieces());

                        totalWeight = totalWeight.add(obj.getInventoryWeight());
                        irregularityWeight = irregularityWeight.add(obj.getInventoryWeight());

                     }
                  }
               }

               if ((DAMG.equalsIgnoreCase(t.getIrregularityTypeCode())
                     && ShipmentType.Type.MAIL.equalsIgnoreCase(t.getShipmentType()))) {
                  // If total piece/weight if it is empty then set with inventory piece/weight
                  if (ObjectUtils.isEmpty(t.getPiece())
                        || (!ObjectUtils.isEmpty(t.getPiece()) && t.getPiece().compareTo(BigInteger.ZERO) == 0)) {
                     t.setPiece(totalPieces);
                  }

                  if (ObjectUtils.isEmpty(t.getWeight())
                        || (!ObjectUtils.isEmpty(t.getWeight()) && t.getWeight().compareTo(BigDecimal.ZERO) == 0)) {
                     t.setWeight(totalWeight);
                  }

               }
               // If irregularity piece/weight if it is empty then set with inventory
               // piece/weight
               if (ObjectUtils.isEmpty(t.getIrregularitypieces()) || (!ObjectUtils.isEmpty(t.getIrregularitypieces()))
                     && t.getIrregularitypieces().compareTo(BigInteger.ZERO) == 0) {
                  t.setIrregularitypieces(irregularityPieces);
                  t.setIrregularityWeight(irregularityWeight);
               }

               if (ObjectUtils.isEmpty(t.getIrregularityWeight()) || (!ObjectUtils.isEmpty(t.getIrregularityWeight()))
                     && t.getIrregularityWeight().compareTo(BigDecimal.ZERO) == 0) {
                  t.setIrregularityWeight(irregularityWeight);
               }

               // Create the shipment
               dao.createTracingActivityForShipment(t);

               // Add SHC
               if (!CollectionUtils.isEmpty(shcInfo)) {
                  for (TracingActivityShipmentShcModel s : shcInfo) {
                     s.setTracingActivityShipmentId(t.getId());
                  }
                  dao.addShcToTracingActivity(shcInfo);
               }

               // Add inventory
               if (!CollectionUtils.isEmpty(tracingShipmentInventory)) {
                  for (TracingActivityShipmentInventoryModel obj : tracingShipmentInventory) {
                     obj.setTracingShipmentInfoId(t.getId());
                  }
                  dao.addInventoryToTracingActivity(tracingShipmentInventory);
               }
            }
         }
      }

   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.tracing.activity.service.TracingActivityService#
    * deleteTracingActivityForShipment(java.util.List)
    */
   @Override
   public void deleteTracingActivityForShipment(List<TracingActivityShipmentModel> requestModel)
         throws CustomException {
      // Check for empty irregularity
      if (!CollectionUtils.isEmpty(requestModel)) {
         // iterate the list
         for (TracingActivityShipmentModel t : requestModel) {
            this.dao.deleteTracingActivityForShipment(t);
         }
      }
   }
}