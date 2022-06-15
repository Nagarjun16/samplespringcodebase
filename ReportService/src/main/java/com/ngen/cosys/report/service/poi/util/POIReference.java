/**
 * 
 * POIReference.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          10 MAR, 2019   NIIT      -
 */
package com.ngen.cosys.report.service.poi.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.service.util.enums.ReportFormat;

/**
 * POI Reference for implementation
 *
 * @author NIIT Technologies Ltd
 * 
 */
public class POIReference extends POIBase {

   private static final Logger LOGGER = LoggerFactory.getLogger(POIReference.class);
   
   /**
    * @see com.ngen.cosys.report.service.poi.util.POIBase#initialize(com.ngen.cosys.service.util.enums.ReportFormat)
    */
   @Override
   protected void initialize(ReportFormat reportFormat) throws CustomException {
      this.workbook = POIUtils.createWorkbook(reportFormat);
   }

   /**
    * @see com.ngen.cosys.report.service.poi.util.POIBase#createHeader(com.ngen.cosys.report.service.poi.util.POIElements)
    */
   @Override
   protected void createHeader(POIElements elements) throws CustomException {
      this.row = POIUtils.createRow(spreadSheet, elements, rowIndex++);
      if (Objects.nonNull(elements.getHeader()) && !CollectionUtils.isEmpty(elements.getHeader().getHeaders())) {
         for (String column : elements.getHeader().getHeaders()) {
            POIUtils.createCell(row, cellStyle, false, null, true, columnIndex++, false, null, column);
         }
      }
   }
   
   /**
    * @see com.ngen.cosys.report.service.poi.util.POIBase#build(com.ngen.cosys.report.service.poi.util.POIElements)
    */
   @Override
   public Object build(POIElements elements) throws CustomException {
      // Create workbook
      initialize(elements.getReportFormat());
      // Sheet name
      this.spreadSheet = POIUtils.createSpreadSheet(workbook, elements, sheetIndex);
      // Header
      createHeader(elements);
      // Content Implementation
      if (Objects.nonNull(elements.getDatasets())) {
         // TODO:
      }
      // Close
      return close(elements);
   }

   /**
    * @see com.ngen.cosys.report.service.poi.util.POIBase#close(com.ngen.cosys.report.service.poi.util.POIElements)
    */
   @Override
   protected Object close(POIElements elements) throws CustomException {
      String reportData = null;
      try {
         OutputStream outputStream = POIUtils.createFile(elements, workbook);
         reportData = POIUtils.convertOutputStreamToBase64Text(outputStream);
      } catch (IllegalStateException | IOException ex) {
         LOGGER.debug("Exception occurred while creating file");
      }
      return reportData;
   }

}
