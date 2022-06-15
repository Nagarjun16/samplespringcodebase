package com.ngen.cosys.impbd.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.EccInboundResult;
import com.ngen.cosys.impbd.model.SearchInbound;
import com.ngen.cosys.impbd.model.ShipmentList;
import com.ngen.cosys.impbd.model.ShipmentListDetails;

/**
 * @author NIIT Technologies
 */
public interface EccInboundWorksheetDAO {

   /**
    * @param searchInbound
    * @return EccInboundResult
    * @throws CustomException
    */

   /**
    * @param searchInbound
    * @return
    * @throws CustomException
    */
   List<String> getTeamName(SearchInbound searchInbound) throws CustomException;

   /**
    * @param searchInbound
    * @return
    * @throws CustomException
    */
   List<String> getEOSummary(SearchInbound searchInbound) throws CustomException;

   /**
    * @param searchInbound
    * @return
    * @throws CustomException
    */
   List<ShipmentList> getShipmentList(SearchInbound searchInbound) throws CustomException;

   /**
    * @param searchInbound
    * @return
    * @throws CustomException
    */
   List<ShipmentListDetails> getShipmentDetails(ShipmentList searchInbound) throws CustomException;

   /**
    * @param searchInbound
    * @return
    * @throws CustomException
    */
   int checkShipment(ShipmentListDetails shipmentListDetails) throws CustomException;

   /**
    * @param searchInbound
    * @return
    * @throws CustomException
    */
   EccInboundResult getDetailsICIDTC(EccInboundResult searchInbound) throws CustomException;

   /**
    * @param searchInbound
    * @return
    * @throws CustomException
    */
   /**
    * @param searchInbound
    * @return
    * @throws CustomException
    */
   int getWorksheetID(SearchInbound searchInbound) throws CustomException;

   /**
    * @param eccInboundResult
    * @return EccInboundResult
    * @throws CustomException
    */
   EccInboundResult save(EccInboundResult eccInboundResult) throws CustomException;

   /**
    * @param shipmentListDetails
    * @return ShipmentListDetails
    * @throws CustomException
    */
   ShipmentListDetails deleteByFlight(ShipmentListDetails shipmentListDetails) throws CustomException;

   /**
    * @param eccInboundResult
    * @throws CustomException
    */
   void validateUsers(EccInboundResult eccInboundResult) throws CustomException;

   /**
    * @param eccInboundResult
    * @throws CustomException
    */
   void validateAgent(EccInboundResult eccInboundResult) throws CustomException;
   
   int checkShipmentDetails(ShipmentListDetails shipmentListDetails) throws CustomException;

   List<ShipmentListDetails> getCustomerIdByAgentCodeOrAgentId(ShipmentListDetails detail) throws CustomException;
   
}
