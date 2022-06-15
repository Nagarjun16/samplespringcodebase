/**
 * 
 * POIUtils.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          10 MAR, 2019   NIIT      -
 */
package com.ngen.cosys.report.service.poi.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.ngen.cosys.report.service.poi.util.POIElements.POIHeader;
import com.ngen.cosys.service.util.enums.ReportFormat;

/**
 * Implementation of Apache POI library to generate XLS, XLSX files 
 *
 * @author NIIT Technologies Ltd
 * 
 */
public class POIUtils {
   
   private static final Logger LOGGER = LoggerFactory.getLogger(POIUtils.class);
   
   private static final String FILE_PATH = "D:\\Project\\Docs\\CiQ\\Temp\\ciqImportExportWorkingListReport.xlsx";
         
   /**
    * No Args Constructor
    */
   private POIUtils() {
      super();
   }
   
   /**
    * @param reportFormat
    * @return
    * @throws IllegalStateException
    * @throws IllegalArgumentException
    */
   private static Workbook getWorkbook(ReportFormat reportFormat) throws IllegalStateException, IllegalArgumentException {
      LOGGER.debug("Create Workbook instance by Report Format");
      //
      if (Objects.isNull(reportFormat)) {
         throw new IllegalStateException("Report Format cannot be null");
      }
      if (!Objects.equals(POIConstants.XLS, reportFormat.format())
            && !Objects.equals(POIConstants.XLSX, reportFormat.format())) {
         throw new IllegalArgumentException("Unknown report format type : " + reportFormat.format());
      }
      //
      Workbook workbook = null;
      if (Objects.equals(POIConstants.XLS, reportFormat.format())) {
         workbook = new HSSFWorkbook();
      } else {
         workbook = new XSSFWorkbook();
      }
      return workbook;
   }
   
   /**
    * GET XLSX SpreadSheet
    * 
    * @param workbook
    * @param elements
    * @param sheetIndex
    * @return XSSFSheet
    */
   private static Sheet getXLSXTypeSheet(XSSFWorkbook workbook, POIElements elements, int sheetIndex) {
      String sheetName = elements.getSheetName();
      LOGGER.debug("Create XSSF Sheet Instance :: SheetName - {}, Index - {}", sheetName, sheetIndex);
      // Validate sheet name
      WorkbookUtil.validateSheetName(sheetName);
      XSSFSheet spreadSheet = workbook.createSheet();
      if (!StringUtils.isEmpty(sheetName)) {
         workbook.setSheetName(sheetIndex, sheetName);
      }
      // Freeze Panes
      int colSplit = elements.getFreezeColumnSplit();
      int rowSplit = elements.getFreezeRowSplit();
      spreadSheet.createFreezePane(colSplit, rowSplit);
      // Zoom scale
      spreadSheet.setZoom(elements.getZoomScale());
      return spreadSheet;
   }
   
   /**
    * GET XLS Spread Sheet
    * 
    * @param workbook
    * @param sheetName
    * @param sheetIndex
    * @return HSSFSheet
    */
   private static Sheet getXLSTypeSheet(HSSFWorkbook workbook, POIElements elements, int sheetIndex) {
      String sheetName = elements.getSheetName();
      LOGGER.debug("Create HSSF Sheet Instance :: SheetName - {}, Index - {}", sheetName, sheetIndex);
      // Validate sheet name
      WorkbookUtil.validateSheetName(sheetName);
      HSSFSheet spreadSheet = workbook.createSheet();
      if (!StringUtils.isEmpty(sheetName)) {
         workbook.setSheetName(sheetIndex, sheetName);
      }
      // Freeze Panes
      int colSplit = elements.getFreezeColumnSplit();
      int rowSplit = elements.getFreezeRowSplit();
      spreadSheet.createFreezePane(colSplit, rowSplit);
      // Zoom scale
      spreadSheet.setZoom(elements.getZoomScale());
      return spreadSheet;
   }
   
   /**
    * GET XLSX Sheet Row
    * 
    * @param spreadSheet
    * @param rowIndex
    * @return
    */
   private static Row getXLSXTypeRow(XSSFSheet spreadSheet, int rowIndex) {
      LOGGER.debug("Create XSSF Row instance :: Index - {}", rowIndex);
      return spreadSheet.createRow(rowIndex);
   }
   
   /**
    * GET XLS Sheet Row
    * 
    * @param spreadSheet
    * @param rowIndex
    * @return
    */
   private static Row getXLSTypeRow(HSSFSheet spreadSheet, int rowIndex) {
      LOGGER.debug("Create HSSF Row instance :: Index - {}", rowIndex);
      return spreadSheet.createRow(rowIndex);
   }
   
   /**
    * @param row
    * @param cellStyle
    * @param cellType
    * @param formula
    * @param header
    * @param columnIndex
    * @param numericValue
    * @param value
    * @return
    */
   private static Cell getXLSXTypeCell(XSSFRow row, XSSFCellStyle cellStyle, CellType cellType, String formula,
         boolean header, int columnIndex, Integer numericValue, String value) {
      LOGGER.debug("Create XSSF Cell instance :: Index - {}, CellStyle available - {}, CellType - {}, Formula - {}",
            columnIndex, Objects.isNull(cellStyle) ? String.valueOf(false) : String.valueOf(true),
            String.valueOf(cellType), formula);
      //
      XSSFCell cell = row.createCell(columnIndex);
      // Apply Cell Type
      if (Objects.nonNull(cellType)) {
         cell.setCellType(cellType);
         // Apply Formula If any
         if (Objects.equals(CellType.FORMULA, cellType) && !StringUtils.isEmpty(formula)) {
            cell.setCellFormula(formula);
         } else if (Objects.equals(CellType.STRING, cellType) && !StringUtils.isEmpty(value)) { // Apply Cell Value
            cell.setCellValue(value);
         } else if (Objects.equals(CellType.NUMERIC, cellType) && Objects.nonNull(numericValue)) { // Apply Cell Value
            cell.setCellValue(numericValue);
         }
      }
      // Apply Cell Style
      if (Objects.nonNull(cellStyle)) {
         cell.setCellStyle(cellStyle);
      }
      return cell;
   }
   
   /**
    * @param row
    * @param cellStyle
    * @param cellType
    * @param formula
    * @param header
    * @param columnIndex
    * @param numericValue
    * @param value
    * @return
    */
   private static Cell getXLSTypeCell(HSSFRow row, HSSFCellStyle cellStyle, CellType cellType, String formula,
         boolean header, int columnIndex, Integer numericValue, String value) {
      LOGGER.debug("Create XSSF Cell instance :: Index - {}, CellStyle available - {}, CellType - {}, Formula - {}",
            columnIndex, Objects.isNull(cellStyle) ? String.valueOf(false) : String.valueOf(true),
            String.valueOf(cellType), formula);
      //
      HSSFCell cell = row.createCell(columnIndex);
      // Apply Cell Type
      if (Objects.nonNull(cellType)) {
         cell.setCellType(cellType);
         // Apply Formula If any
         if (Objects.equals(CellType.FORMULA, cellType) && !StringUtils.isEmpty(formula)) {
            cell.setCellFormula(formula);
         } else if (Objects.equals(CellType.STRING, cellType) && !StringUtils.isEmpty(value)) { // Apply Cell Value
            cell.setCellValue(value);
         } else if (Objects.equals(CellType.NUMERIC, cellType) && Objects.nonNull(numericValue)) { // Apply Cell Value
            cell.setCellValue(numericValue);
         }
      }
      // Apply Cell Style
      if (Objects.nonNull(cellStyle)) {
         cell.setCellStyle(cellStyle);
      }
      return cell;
   }
   
   /**
    * GET XLSX Type Font
    * 
    * @param workbook
    * @param header
    * @param headerDetails
    * @param fontName
    * @param bold
    * @param italic
    * @param fontHeightInPoints
    * @return
    */
   private static Font getXLSXTypeFont(XSSFWorkbook workbook, boolean header, POIHeader headerDetails,
         String fontName, boolean bold, boolean italic, short fontHeightInPoints) {
      LOGGER.debug("XSSF Font Features :: Name - {}, Bold - {}, Italic - {}, FontHeightInPoints - {}", fontName,
            String.valueOf(bold), String.valueOf(italic), fontHeightInPoints);
      // Create XSSF Font
      XSSFFont font = workbook.createFont();
      // Apply Font Name
      if (!StringUtils.isEmpty(fontName)) {
         font.setFontName(fontName);
      }
      font.setBold(bold);
      font.setItalic(italic);
      font.setFontHeightInPoints(fontHeightInPoints);
      // Apply Color to Font
      font.setColor(Font.COLOR_NORMAL);
      //
      if (header) {
         LOGGER.debug("XSSF Font Color :: Header is SET to be TRUE");
         if (Objects.nonNull(headerDetails)) {
            IndexedColors xlsxColor = headerDetails.getXlsxColor();
            if (Objects.nonNull(xlsxColor)) {
               LOGGER.debug("XSSF Indexed Color :: Name - {}, Index - {}", xlsxColor.name(), xlsxColor.getIndex());
               font.setColor(xlsxColor.getIndex());
            }
         }
      }
      //
      return font;
   }
   
   /**
    * GET XLS Type Font
    * 
    * @param workbook
    * @param header
    * @param headerDetails
    * @param fontName
    * @param bold
    * @param italic
    * @param color
    * @param fontHeightInPoints
    * @return
    */
   private static Font getXLSTypeFont(HSSFWorkbook workbook, boolean header, POIHeader headerDetails,
         String fontName, boolean bold, boolean italic, short fontHeightInPoints) {
      LOGGER.debug("HSSF Font Features :: Name - {}, Bold - {}, Italic - {}, FontHeightInPoints - {}", fontName,
            String.valueOf(bold), String.valueOf(italic), fontHeightInPoints);
      // Create HSSF Font
      HSSFFont font = workbook.createFont();
      // Apply Font Name
      if (!StringUtils.isEmpty(fontName)) {
         font.setFontName(fontName);
      }
      font.setBold(bold);
      font.setItalic(italic);
      font.setFontHeightInPoints(fontHeightInPoints);
      // Apply Color to Font
      font.setColor(Font.COLOR_NORMAL);
      //
      if (header) {
         LOGGER.debug("HSSF Font Color :: Header is SET to be TRUE");
         if (Objects.nonNull(headerDetails)) {
            HSSFColorPredefined xlsColor = headerDetails.getXlsColor();
            if (Objects.nonNull(xlsColor)) {
               LOGGER.debug("HSSF Predefined Color :: Name - {}, Index - {}", xlsColor.name(), xlsColor.getIndex());
               font.setColor(xlsColor.getIndex());
            }
         }
      }
      //
      return font;
   }
   
   /**
    * @param workbook
    * @param font
    * @param cellAlignment
    * @param blankHeader
    * @return
    */
   private static CellStyle getXLSXCellStyle(XSSFWorkbook workbook, XSSFFont font, CellAlignment cellAlignment,
         boolean blankHeader) {
      LOGGER.debug("XSSF Cell Style :: {}");
      XSSFCellStyle cellStyle = workbook.createCellStyle();
      // Font feature
      if (Objects.nonNull(font)) {
         LOGGER.debug("XSSF Cell Style Font : {}", font);
         cellStyle.setFont(font);
      }
      // Cell Alignment
      if (Objects.nonNull(cellAlignment)) {
         LOGGER.debug("XSSF Cell Style Alignment is NOT EMPTY : {}");
         // Horizontal
         if (Objects.nonNull(cellAlignment.getHorizontal())) {
            cellStyle.setAlignment(cellAlignment.getHorizontal());
         }
         // Vertical
         if (Objects.nonNull(cellAlignment.getVertical())) {
            cellStyle.setVerticalAlignment(cellAlignment.getVertical());
         }
         // Wrap Text
         cellStyle.setWrapText(cellAlignment.isWrapText());
      }
      // Background Color
      if (blankHeader) {
         cellStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
         cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
      }
      return cellStyle;
   }
   
   /**
    * @param workbook
    * @param font
    * @param cellAlignment (Common for XSSF&HSSF)
    * @param blankHeader
    * @return
    */
   private static CellStyle getXLSCellStyle(HSSFWorkbook workbook, HSSFFont font, CellAlignment cellAlignment,
         boolean blankHeader) {
      LOGGER.debug("XSSF Cell Style :: {}");
      HSSFCellStyle cellStyle = workbook.createCellStyle();
      // Font feature
      if (Objects.nonNull(font)) {
         LOGGER.debug("XSSF Cell Style Font : {}", font);
         cellStyle.setFont(font);
      }
      // Cell Alignment
      if (Objects.nonNull(cellAlignment)) {
         LOGGER.debug("XSSF Cell Style Alignment is NOT EMPTY : {}");
         // Horizontal
         if (Objects.nonNull(cellAlignment.getHorizontal())) {
            cellStyle.setAlignment(cellAlignment.getHorizontal());
         }
         // Vertical
         if (Objects.nonNull(cellAlignment.getVertical())) {
            cellStyle.setVerticalAlignment(cellAlignment.getVertical());
         }
         // Wrap Text
         cellStyle.setWrapText(cellAlignment.isWrapText());
      }
      // Background Color
      if (blankHeader) {
         cellStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
         cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
      }
      return cellStyle;
   }
   
   /* Utility Methods */
   /**
    * Create Workbook
    * 
    * @param reportFormat
    * @return
    */
   public static Workbook createWorkbook(ReportFormat reportFormat) {
      return getWorkbook(reportFormat);
   }
   
   /**
    * Create SpreadSheet
    * 
    * @param workbook
    * @param elements
    * @param sheetIndex
    * @return
    */
   public static Sheet createSpreadSheet(Workbook workbook, POIElements elements, int sheetIndex) {
      if (workbook instanceof XSSFWorkbook) {
         return getXLSXTypeSheet((XSSFWorkbook) workbook, elements, sheetIndex);
      } else if (workbook instanceof HSSFWorkbook) {
         return getXLSTypeSheet((HSSFWorkbook) workbook, elements, sheetIndex);
      }
      LOGGER.debug("Spreadsheet creation :: Workbook instance is NULL");
      return null;
   }
   
   /**
    * Create Row in SpreadSheet
    * 
    * @param spreadSheet
    * @param elements
    * @param rowIndex
    * @return
    */
   public static Row createRow(Sheet spreadSheet, POIElements elements, int rowIndex) {
      if (spreadSheet instanceof XSSFSheet) {
         return getXLSXTypeRow((XSSFSheet) spreadSheet, rowIndex);
      } else if (spreadSheet instanceof HSSFSheet) {
         return getXLSTypeRow((HSSFSheet) spreadSheet, rowIndex);
      }
      LOGGER.debug("Row creation :: Spreadsheet instance is NULL");
      return null;
   }
   
   /**
    * Create Cell in a Row
    * 
    * @param row
    * @param cellStyle
    * @param applyFormula
    * @param formula
    * @param header
    * @param columnIndex
    * @param numeric
    * @param numericValue
    * @param value
    * @return
    */
   public static Cell createCell(Row row, CellStyle cellStyle, boolean applyFormula, String formula, boolean header,
         int columnIndex, boolean numeric, Integer numericValue, String value) {
      //
      CellType cellType = applyFormula ? CellType.FORMULA : (numeric ? CellType.NUMERIC : CellType.STRING);
      //
      if (row instanceof XSSFRow) {
         return getXLSXTypeCell((XSSFRow) row, (XSSFCellStyle) cellStyle, cellType, formula, header, columnIndex,
               numericValue, value);
      } else if (row instanceof HSSFRow) {
         return getXLSTypeCell((HSSFRow) row, (HSSFCellStyle) cellStyle, cellType, formula, header, columnIndex,
               numericValue, value);
      }
      LOGGER.debug("Cell creation :: Row instance is NULL");
      return null;
   }
   
   /**
    * Create Font in Workbook
    * 
    * @param workbook
    * @param header
    * @param headerDetails
    * @param fontName
    * @param bold
    * @param italic
    * @param fontHeightInPoints
    * @return
    */
   public static Font createFont(Workbook workbook, boolean header, POIHeader headerDetails, String fontName,
         boolean bold, boolean italic, short fontHeightInPoints) {
      //
      if (workbook instanceof XSSFWorkbook) {
         return getXLSXTypeFont((XSSFWorkbook) workbook, header, headerDetails, fontName, bold, italic,
               fontHeightInPoints);
      } else if (workbook instanceof HSSFWorkbook) {
         return getXLSTypeFont((HSSFWorkbook) workbook, header, headerDetails, fontName, bold, italic,
               fontHeightInPoints);
      }
      LOGGER.debug("Font creation :: Workbook instance is NULL");
      return null;
   }
   
   /**
    * @param workbook
    * @param font
    * @param cellAlignment
    * @param blankHeader
    * @return
    */
   public static CellStyle createCellStyle(Workbook workbook, Font font, CellAlignment cellAlignment,
         boolean blankHeader) {
      //
      if (workbook instanceof XSSFWorkbook) {
         return getXLSXCellStyle((XSSFWorkbook) workbook, (XSSFFont) font, cellAlignment, blankHeader);
      } else if (workbook instanceof HSSFWorkbook) {
         return getXLSCellStyle((HSSFWorkbook) workbook, (HSSFFont) font, cellAlignment, blankHeader);
      }
      LOGGER.debug("Cell Style creation :: Workbook instance is NULL");
      return null;
   }
   
   /**
    * @param horizontalAlignment
    * @param verticalAlignment
    * @param wrapText
    * @return
    */
   public static CellAlignment createCellAlignment(HorizontalAlignment horizontalAlignment,
         VerticalAlignment verticalAlignment, boolean wrapText) {
      LOGGER.debug("Cell Alignment - Horizontal & Vertical");
      //
      CellAlignment cellAlignment = new CellAlignment();
      cellAlignment.setHorizontal(horizontalAlignment);
      cellAlignment.setVertical(verticalAlignment);
      cellAlignment.setWrapText(wrapText);
      //
      return cellAlignment;
   }
   
   /**
    * Create File
    * 
    * @param elements
    * @param workbook
    * @return
    * @throws IllegalStateException, IOException 
    */
   public static OutputStream createFile(POIElements elements, Workbook workbook)
         throws IllegalStateException, IOException {
      if (Objects.isNull(workbook)) {
         throw new IllegalStateException("Workbook instance cannot be null");
      }
      /*try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
            BufferedOutputStream writer = new BufferedOutputStream(fos)) {
         workbook.write(writer);
      }*/
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      workbook.write(outputStream);
      outputStream.close();
      workbook.close();
      //
      return outputStream;
   }
   
   /**
    * @param outputStream
    * @return
    */
   public static String convertOutputStreamToBase64Text(OutputStream outputStream) {
      if (outputStream instanceof ByteArrayOutputStream) {
         return Base64.getEncoder().encodeToString(((ByteArrayOutputStream) outputStream).toByteArray());
      }
      return Base64.getEncoder().encodeToString(outputStream.toString().getBytes());
   }
   
}
