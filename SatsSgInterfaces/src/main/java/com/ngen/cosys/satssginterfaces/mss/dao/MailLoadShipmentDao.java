package com.ngen.cosys.satssginterfaces.mss.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.BuildUpMailSearch;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMailBagModel;

public interface MailLoadShipmentDao {
   

   /**
    * @param BuildUpMailSearch
    * @return BuildUpMailSearch
    * @throws CustomException
    */ 
   BuildUpMailSearch fetchFlightDeatil(BuildUpMailSearch search)  throws CustomException;
   List<ShipmentMailBagModel> fetchPrebookedList(BuildUpMailSearch search)  throws CustomException;
   List<ShipmentMailBagModel> fetchContainerlist(BuildUpMailSearch search)  throws CustomException;
   /**
    * @param BuildUpMailSearch
    * @return List<BuildUpMailSearch>
    * @throws CustomException
    */ 
   List<BuildUpMailSearch> fetchMailbagDetail(BuildUpMailSearch mailbagId)  throws CustomException;
   /**
    * @param BuildUpMailSearch
    * @return BuildUpMailSearch
    * @throws CustomException
    */ 
   BuildUpMailSearch checkMailBagNumber(BuildUpMailSearch search) throws CustomException;
   /**
    * @param BuildUpMailSearch
    * @return boolean
    * @throws CustomException
    */ 
   boolean isDLSCompleted(BuildUpMailSearch search) throws CustomException;
   
   BuildUpMailSearch uploadPhotos(BuildUpMailSearch common) throws CustomException;

}
