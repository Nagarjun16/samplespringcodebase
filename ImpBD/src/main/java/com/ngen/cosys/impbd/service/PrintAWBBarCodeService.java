/**
 * PrintAWBBarCodeService.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 23 January, 2018 NIIT -
 */
package com.ngen.cosys.impbd.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.AWBPrintRequest;

/**
 * This class takes care of the responsibilities related to the PrintAWBBarCode
 * Service operations that comes from the Controller.
 * 
 * @author NIIT Technologies Ltd
 *
 */

public interface PrintAWBBarCodeService {
   /**
    * Validates AWB number against Arival manifest shipment info
    * 
    * @param awbDetail
    * @return SearchAWBManifest
    * @throws CustomException
    */
   AWBPrintRequest validateAWBNumber(AWBPrintRequest awbDetail) throws CustomException;

   /**
    * Responsible to Prints the AWB barcode
    * 
    * @param awbDetail
    * @return SearchAWBManifest
    * @throws CustomException
    */
   AWBPrintRequest printBarcode(AWBPrintRequest shpInfo) throws CustomException;

   AWBPrintRequest printMultiBarcode(List<AWBPrintRequest> shpInfo) throws CustomException;

}