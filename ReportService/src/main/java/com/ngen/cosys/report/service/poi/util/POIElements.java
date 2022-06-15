/**
 * 
 * POIElements.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          10 MAR, 2019   NIIT      -
 */
package com.ngen.cosys.report.service.poi.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.ngen.cosys.service.util.enums.ReportFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * POI Elements
 *
 * @author NIIT Technologies Ltd
 * 
 */
@Getter
@Setter
@NoArgsConstructor
public class POIElements {

   // Report Format
   private ReportFormat reportFormat;
   // File Name
   private String fileName;
   // Sheet Name
   private String sheetName;
   // Specify Zoom size - Default Set to 100/80
   private int zoomScale;
   // Freeze pane details
   private int freezeColumnSplit;
   private int freezeRowSplit;
// POI Header holds of Header Information
   private POIHeader topHeader;
   // POI Header holds of Header Information
   private POIHeader header;
   // Data set details
   private Object datasets;
   // Formula to apply on Column index - Not applicable for Header
   private Map<String, String> formulas = Collections.emptyMap();

   @Getter
   @Setter
   @NoArgsConstructor
   public class POIHeader {
      // Header names of the Sheet
      private List<String> headers = Collections.emptyList();
      // Font Size
      private short fontHeight;
      // Font Height In Points
      private short fontHeightInPoints;
      private boolean bold;
      private boolean Italic;
      private boolean wrapText;
      // XSSF Color - XLSX Type
      private IndexedColors xlsxColor;
      // HSSF Color - XLS Type
      private HSSFColorPredefined xlsColor;
   }
   
}
