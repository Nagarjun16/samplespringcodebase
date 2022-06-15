/**
 * 
 * POIBase.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          10 MAR, 2019   NIIT      -
 */
package com.ngen.cosys.report.service.poi.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.service.util.enums.ReportFormat;

/**
 * POI Base for Implementation
 *
 * @author NIIT Technologies Ltd
 * 
 */
public abstract class POIBase {

   protected Workbook workbook;
   protected Sheet spreadSheet;
   protected Row row;
   protected Cell cell;
   protected Font font;
   protected CellStyle cellStyle;
   protected CellType cellType;
   protected CellAlignment cellAlignment;
   //
   protected int sheetIndex = 0;
   protected int rowIndex = 0;
   protected int columnIndex = 0;
   
   /**
    * Workbook Properties Initialization 
    */
   protected void initializeProperties() {
      this.workbook = null;
      this.spreadSheet = null;
      this.row = null;
      this.cell = null;
      this.font = null;
      this.cellStyle = null;
      this.cellType = null;
      this.cellAlignment = null;
      this.sheetIndex = 0;
      this.rowIndex = 0;
      this.columnIndex = 0;
   }
   
   /**
    * @param reportFormat
    * @throws CustomException
    */
   protected abstract void initialize(ReportFormat reportFormat) throws CustomException;

   /**
    * @param elements
    * @throws CustomException
    */
   protected abstract void createHeader(POIElements elements) throws CustomException;
   
   /**
    * @param elements
    * @throws CustomException
    */
   public abstract Object build(POIElements elements) throws CustomException;

   /**
    * @param elements
    * @throws CustomException
    */
   protected abstract Object close(POIElements elements) throws CustomException;

}
