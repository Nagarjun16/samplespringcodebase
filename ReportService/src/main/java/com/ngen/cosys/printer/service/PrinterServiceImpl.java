/**
 * Printer Service Implementation
 * 
 * @copyright SATS Singapore 2018-19
 */
package com.ngen.cosys.printer.service;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.common.CommodityChargesList;
import com.ngen.cosys.common.ESCPrinter;
import com.ngen.cosys.esb.connector.payload.PrinterPayload;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.printer.enums.PrinterFile;
import com.ngen.cosys.printer.model.NAWBRequest.NAWBDetails;
import com.ngen.cosys.printer.model.TransferManifestRequest.TransferManifestDetails;
import com.ngen.cosys.printer.processor.PrinterProcessor;
import com.ngen.cosys.report.common.ReportFormat;
import com.ngen.cosys.report.model.ReportRequest;

/**
 * Printer Service Implementation
 * 
 * Change History:
 * ===============
 * 
 * Date			BugReference		ChangedBy	ModificationDetails
 * ----			------------		---------	-------------------
 * 26/08/2020	NGC-COSYS:13737		Noel		Print DO: Tay advised not printing the UEN/IU No.field, due to its confidential info, so removed it. Refer Bugzilla.
 *  
 */
@Service
public class PrinterServiceImpl implements PrinterService {
	
	@Autowired
	PrinterProcessor printerProcessor;
   
   private static Logger LOGGER = LoggerFactory.getLogger(PrinterServiceImpl.class);
   
   /**
    * Generate Report by Printer type
    * 
    * @see com.ngen.cosys.printer.service.PrinterService#generatePrinterReport(com.ngen.cosys.report.model.ReportRequest)
    * 
    */
   @Override
   public PrinterPayload generatePrinterReport(ReportRequest reportRequest) throws Exception {
      // Generate report by Printer type
      PrinterPayload payload = null;
      String base64Text = null;
      switch (reportRequest.getPrinterType()) {
      case AWB_BARCODE:
         reportRequest.setReportName(PrinterFile.PRINT_AWB_BARCODE.getName());
         base64Text = generateAWBLabelReport(reportRequest.getParameters());
         break;
      case FLIGHT_POUCH_QR_CODE:
         reportRequest.setReportName(PrinterFile.PRINT_FLIGHT_POUCH_QR_CODE.getName());
         base64Text = generatePouchLabelReport(reportRequest.getParameters());
         break;
      case ULD_TAG:
         reportRequest.setReportName(PrinterFile.PRINT_ULDTAG.getName());
         base64Text = generateULDTAGReport(reportRequest.getParameters());
         break;
      case XPS_AWB_BARCODE:
         reportRequest.setReportName(PrinterFile.PRINT_XPS_AWB_BARCODE.getName());
         base64Text = generateRFIDAWBReport(reportRequest.getParameters());
         break;
      case XPS_ULD_TAG:
         reportRequest.setReportName(PrinterFile.PRINT_XPS_ULD_TAG.getName());
         base64Text = generateRFIDULDTagReport(reportRequest.getParameters());
         break;
      case HANDOVER_FORM:
         reportRequest.setReportName(PrinterFile.PRINT_HANDOVER_FORM.getName());
         base64Text = generateHandOverFormReport(reportRequest.getParameters());
         break;
      case HANDOVER_REPRINT_FORM:
         reportRequest.setReportName(PrinterFile.PRINT_HANDOVER_REPRINT_FORM.getName());
         base64Text = generateRePrintHandOverFormReport(reportRequest.getParameters());
         break;
      case OFFLOAD_FORM:
         reportRequest.setReportName(PrinterFile.PRINT_OFFLOAD_FORM.getName());
         base64Text = generateOffloadFormReport(reportRequest.getParameters());
         break;
      case PICK_ORDER:
         reportRequest.setReportName(PrinterFile.PRINT_PICK_ORDER.getName());
         base64Text = generatePickOrderReport(reportRequest.getParameters());
         break;
      case DELIVERY_ORDER:
         reportRequest.setReportName(PrinterFile.PRINT_DELIVERY_ORDER.getName());
         base64Text = generateNoteDeliveryReport(reportRequest.getParameters());
         break;
      case PRINT_BUP_DELIVERY_ORDER:
    	  reportRequest.setReportName(PrinterFile.PRINT_BUP_DELIVERY_ORDER.getName());
    	  base64Text = generateBupNoteDeliveryReport(reportRequest.getParameters());
    	  break;
      case TRANSFER_MANIFEST:
         reportRequest.setReportName(PrinterFile.PRINT_TRANSFER_MANIFEST.getName());
         base64Text = generateTransferManifestReport(reportRequest.getParameters());
         break;
      case CARGO_MANIFEST:
         reportRequest.setReportName(PrinterFile.PRINT_CARGO_MANIFEST.getName());
         // TODO:
         base64Text = null;
         break;
      case NAWB:
         reportRequest.setReportName(PrinterFile.PRINT_NAWB.getName());
         base64Text = generateNAWBReport(reportRequest.getParameters());
         break;
      case LASER:
          reportRequest.setReportName(PrinterFile.PRINT_LASER.getName());
          base64Text = generateLaserReport(reportRequest.getParameters());
          break;
      default:
         break;
      }
      reportRequest.setFormat(ReportFormat.enumOf(TEXT_FORMAT));
      payload = copyValue(reportRequest);
      payload.setContentData(base64Text);
      //
      return payload;
   }

   /**
    * Initiate Printer Payload
    * 
    * @param reportRequest
    * @return
    */
   private PrinterPayload copyValue(ReportRequest reportRequest) {
       PrinterPayload payload = new PrinterPayload();
       //
       payload.setContentName(reportRequest.getReportName());
       payload.setContentFormat(reportRequest.getFormat().format());
       payload.setQueueName(reportRequest.getQueueName());
       payload.setPrinterType(reportRequest.getPrinterType().getType());
       payload.setContentParams(reportRequest.getParameters());
       payload.setTenantID(TenantContext.getTenantId());
       //
       return payload;
   }
   
   /**
    * @param inputMapData
    * @return
    */
   private String generateAWBLabelReport(Map<String, Object> inputMapData) {
      

	   String ZPLcommand = "^XA\n" + "^LH0,0\n" + "^FO250,20\n" + "^BY 2,2.0,20\n" + "^BCN,85,N\n" + "^FD"
                       + ((String) inputMapData.get("awbNumBarCode")) + "^FS\n" + "^FO300,120\n" + "^CFE,30\n" + "^FD"
                       + ((String) inputMapData.get("awbNumTextCode")) + "^FS\n" + "^XZ\n";
               return convertStringToBase64Text(ZPLcommand);
   }

   /**
    * @param inputMapData
    * @return
    */
   private String generatePouchLabelReport(Map<String, Object> inputMapData) {
       //
       String ZPLcommand = "^XA\n" + "^LH20,10\n" + "^JMA\n" + "^FX Flight No.\n" + "^FO70,50\n" + "^CF0,70\n" + "^FD"
               + ((String) inputMapData.get("flightNo")) + "^FS\n" + "^FX Flight Date\n" + "^FO70,120\n" + "^CF0,45\n"
               + "^FD" + ((String) inputMapData.get("flightDate")) + "^FS\n" + "^FX SIN\n" + "^FO70,170\n" + "^CF0,45\n"
               + "^FD" + ((String) inputMapData.get("pouchDepartLoc")) + "^FS\n" + "^FX OFF Point\n" + "^FO170,170\n"
               + "^CF0,45\n" + "^FD" + ((String) inputMapData.get("offPoint")) + "^FS\n" + "^FX Pouch ID\n" + "^FO70,230\n"
               + "^CF0,30\n" + "^FD" + ((String) inputMapData.get("pouchId")) + "^FS\n" + "^FX QR Code\n" + "^FO370,90\n"
               + "^BQN,2,7,H^FDMM,A" + ((String) inputMapData.get("pouchId")) + "^FS\n" + "^XZ";
       //
       return convertStringToBase64Text(ZPLcommand);
   }

   /**
    * @param inputMapData
    * @return
    */
   private String generateULDTAGReport(Map<String, Object> inputMapData) {

	   List<String> dgDetailsList= (List<String>)inputMapData.get("dgDetailsList");
	   String para1 = null, para2 = null, para3 = null, para4 = null, para5 = null, para6 = null, para7 = null, para8 = null, para9 = null, para10 = null, para11 = null, para12 = null;
	   if(null!=dgDetailsList && dgDetailsList.size()>0) {
				para1=dgDetailsList.size()>0?padLeft(para1=dgDetailsList.get(0),8):"";
				para2=dgDetailsList.size()>1?padLeft(para2=dgDetailsList.get(1),8):"";
				para3=dgDetailsList.size()>2?padLeft(para3=dgDetailsList.get(2),8):"";
				para4=dgDetailsList.size()>3?padLeft(para4=dgDetailsList.get(3),8):"";;
				para5=dgDetailsList.size()>4?padLeft(para5=dgDetailsList.get(4),8):"";
				para6=dgDetailsList.size()>5?padLeft(para6=dgDetailsList.get(5),8):"";
				para7=dgDetailsList.size()>6?padLeft(para7=dgDetailsList.get(6),8):"";
				para8=dgDetailsList.size()>7?padLeft(para8=dgDetailsList.get(7),8):"";
				para9=dgDetailsList.size()>8?padLeft(para9=dgDetailsList.get(8),8):"";
				para10=dgDetailsList.size()>9?padLeft(para10=dgDetailsList.get(9),8):"";
				para11=dgDetailsList.size()>10?padLeft(para11=dgDetailsList.get(10),8):"";
				para12=dgDetailsList.size()>11?padLeft(para12=dgDetailsList.get(11),8):"";
	   }
	   //System.out.println("Print 1");
	   
	   String ZPLcommand="";
       String totweight="";
       String strXPS="";
	   
	   String strQrCode = "";  
       
      
	     String XPSchanged1 = "^GB830,5,5^FS\n";
		 String XPSchanged2 = "^GB830,5,5^FS\n";
		 String XPSchanged3 = "^GB830,5,5^FS\n";
	   
	   
		 String  qrCodeChange0 = "^BY3,3.5,50\n";
		 String  qrCodeChange1 = "^FO400,340\n";
		 String  qrCodeChange3 = "^FO550,450,1\n";
		 String  qrCodeChange5 = "^CF0,20\n";
		 String qrCodeChange6 = "^FO550,550,1\n";
		 String qrCodeChange8 = "^FO550,650,1\n";
		  
		  
       
	   String strIsQRCodePrint=inputMapData.get("IsQRCodePrint")==null?"":(String)inputMapData.get("IsQRCodePrint");
	   String strIsXPS=inputMapData.get("IsXPS")==null?"":(String)inputMapData.get("IsXPS");	   
	   
	   if(strIsXPS!="" && strIsXPS.equalsIgnoreCase("Yes"))
       {		   
		   strXPS="^LRY^FO680,305\n" +"^GB295,420,200^FS\n" +"^FO710,330^CF0,130,150^FPV^FDXPS^FS\n";
		   
		   
		 XPSchanged1 = "^GB680,5,5^FS\\n"; // ^GB830,5,5^FS\n
	     XPSchanged2 = "^GB680,5,5^FS\\n"; // ^GB830,5,5^FS\n
		 XPSchanged3 = "^GB680,5,5^FS\n"; // ^GB830,5,5^FS\n
       }
	   
	   String strRFID="";
	   String strIsRFID=inputMapData.get("IsRFID")==null?"":(String)inputMapData.get("IsRFID");
	   if(strIsRFID.equalsIgnoreCase("Yes")) {
		   strRFID = "^FX EPC WRITING  ^RFW,H^FD" + inputMapData.get("epcCode") 
   		   + "^FS ^RFW,H,0," 
           + ((String) inputMapData.get("hexaCollectiveUserMemory")).length() 
           + ",3^FD" 
           + inputMapData.get("hexaCollectiveUserMemory") 
           + "^FS ^RFR,H,0,16,1^FN1^FS^HV1,,^FS";
	   }
	   
	   if (strIsQRCodePrint!="" && strIsQRCodePrint.equalsIgnoreCase("Yes"))
	   {		   
        
		   totweight=inputMapData.get("totalweight")==null?"":(String)inputMapData.get("totalweight");
		   if(totweight!="" )
		   {
			   if(totweight!="null")
			   {
		         int firstIndex = totweight.indexOf('.');
		         if(firstIndex>0)
		          {
		           totweight=totweight.substring(0, firstIndex);
		          }
		           totweight=totweight+"KG,";
			   }else {
				   totweight=",";
			   } 
		   }else
		   {
			   totweight=",";
		   }
		   String flightdatec=(String)inputMapData.get("flightDate")==null?"":(String)inputMapData.get("flightDate");
		   String dMMMfor="";
		   String flightDatefinal="";
		   
		   if(flightdatec!="")
		   {
		   DateFormat sdf = new SimpleDateFormat("ddMMMyy");
	       Date date = null;
			try {
				date = sdf.parse(flightdatec);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	        
			flightDatefinal=new SimpleDateFormat("d-MMM").format(date);
		   }
		   
		   strQrCode = "^FO0,420\n" + "^GB470,305,5^FS\n"             
	               				+ "^FO490,480^BQN,2,6,H^FDMA," + (((String)inputMapData.get("uldtagId"))==null?",": ((String)inputMapData.get("uldtagId")))+"," + (((String)inputMapData.get("destination"))==null?",": ((String)inputMapData.get("destination")))+ ","+totweight + (((String)inputMapData.get("loaded"))==null?",": ((String)inputMapData.get("loaded")))+","+ (((String)inputMapData.get("flightNo"))==null?",": ((String)inputMapData.get("flightNo")))+"," + flightDatefinal + "^FS\n" ;//WORKING ALL FIELDS
		  
		   
	    qrCodeChange0 = "^BY3,3,50\\n"; // ^BY3,3.5,50\n
	    qrCodeChange1 = "^FO360,340"; // ^FO400,340\n
	    qrCodeChange3 = "^FO440,450,1\\n"; // ^FO550,450,1\n 
	    qrCodeChange5 = "^CF0,25\\n"; // ^CF0,20\n
	    qrCodeChange6 = "^FO440,550,1\\n"; // ^FO550,550,1\n
	    qrCodeChange8 = "^FO440,650,1\\n"; // ^FO550,650,1\n
		   
	   }	 
	   
	   ZPLcommand = "^XA\n" 
			   + strRFID
			   + "^GB830,1300,5,B,0^FS\n"
               + "^FO50,50^GFA,5016,5016,38,,::::::::U01FFE,T07KF8,S07MF8,R03OF,Q01PFE,Q07QF8,P01RFE,P07SF8,"
               + "O01TFE,O03UF,O0VFC,N01VFE,N03WF,N0XF8,M01XFE,M03YF,M07YF8,M0gFC,L01gFE,L03gGF,L07gGF8,:L0gHFC,K01gHFE,"
               + "K03gIF,:K07gIF8,K0gJFC,:J01gJFE,:J03gKF,:J07gKF8,:J0gLFC,::I01gLFE,::I03gMF,:I03WF3OF,I07VFC3OF8,"
               + "I07VF83OF8,I07KFC00IFC007F83IFE003FF8I0IFI0701JFC3FFE0E007003803807I07,I07KFI01FFI01F83IF8I0FF8003IFI0F81JFC3IF0E007003807C03800F,"
               + "I07JFEJ07CJ0F8001FJ03F800F803I0F8003C3038I0E00F803007C03C00E,I07JFCJ038J078001EJ01F801EK01D8001C003J0700F807006E01C01C,"
               + "I0KF801007CJ078001EJ03FC03CK01DC001C0038I0700F80700EE00E038,I0KF83FF8FE3FF038001C1FFC7FC038K019C001C0038I0700D80700E600F038,"
               + "I0KF83FFDFF7FF838001C1KFC07L038E001C0038I0301DC0601C700707,I0KF81NF0383FFC0KFC07L038E001C0038I0381DC0E01C70038F,"
               + "I0KF8003JF800383FFCI0IFC0EL0706001C0038I03818C0E01830038E,I0KFCI03FFJ0383FFEJ0FFC0EL0707001C0038I03818C0C0383801DC,"
               + "I0KFEJ0FCJ0383IFJ07FC0EL0607001C003FFE01838E0C0383800FC,I0LFJ07CJ0383IF8I01FC0EI0300E03801C003FFE01C38E1C0301C00F8,"
               + "I0LFCI0380018383JFI01FC0EI0380E03801C0038I01C3061C0701C007,I0OF0303FF8383LF01FC0EI0380C03801C0038J0C707180701C007,"
               + "I0OF8307FF8383LFC0FC0EI0381IFC01C0038J0E707180IFE007,I0OFC107FF8383LFE0FC0FI0381IFC01C0038J0E603380IFE007,"
               + "I0LF7FF8307FF8381FBFBFFE0FC07I0381801C01C0038J0E603300E00F007,I0KFE1FF0381FE03C1F1F0FF81FC07800383800E01C0038J06603B01C007007,"
               + "I0KFCJ038J07CI0EJ01F803C00383800E01C0038J06E03B01C007007,I07JF8J07CJ07CI0EJ03F801E00387I0701C003K07C01F018003807,"
               + "I07JFCJ0FCJ0FEI0EJ03F800FC0F87I0701C003FFE003C01E038003807,I07KFI01FFI03FF003F8I0FF8007IF86I0701C003IF003C01E038003C07,"
               + "I07LF00IFE01IFC0IF007FF8I0FFC0EI0381C003FFE003800E03I01C07,I07gMF8,I03gMF,:::I01gLFE,::J0gLFC,:J0gLF8,J07gKF8,:J03gKF,:J01gJFE,"
               + ":K0gJFC,K0gJF8,K07gIF8,K03gIF,K01gHFE,:L0gHFC,L07gGF8,L07gGF,L03gGF,L01gFE,M0gFC,M07YF8,M03YF,M01XFC,N07WF8,N03WF,N01VFE,O07UF8,O03UF,"
               + "P0TFC,P03SF,P01RFE,Q07QF8,R0PFC,R03OF,S03MF,T03KF,V03F,,:::::::^FS\n" 
               + "^CF0,25\n" 
               + "^FO350,20\n" + "^FD" + (inputMapData.get("user")==null?"": (String)inputMapData.get("user")) + " " 
               + (((String)inputMapData.get("printDateTime"))==null?"": ((String)inputMapData.get("printDateTime")).toUpperCase())  + "^FS\n"; 
               
  	
	   ZPLcommand += 
		   "^FO350,80\n" +qrCodeChange0 + "^BCN,85,N\n" + "^FD"+  (((String)inputMapData.get("uldtagId"))==null?"": ((String)inputMapData.get("uldtagId"))) + "^FS\n" + "^CF0,90\n" 
	       + "^FO180,200\n" + "^FD" + (((String)inputMapData.get("uldtagId"))==null?"": ((String)inputMapData.get("uldtagId"))) + "^FS\n" 
	       + "^FO0,300\n" + "^GB830,5,5^FS\n"
	       + "^FO30,360\n"+ "^CF0,30\n" + "^FDDestination^FS\n" 
		   + qrCodeChange1 + "^CF0,80\n" + "^FD" + (((String)inputMapData.get("destination"))==null?"": ((String)inputMapData.get("destination")))+ "^FS\n"
           +strXPS 
           + "^LRN^FO0,420\n" + XPSchanged1
           + strQrCode
           + "^FO30,440\n" + "^CF0,30\n" + "^FDNet^FS\n"
           + "^FO30,480\n" + "^CF0,25\n" + "^FDWeight(Kg)^FS\n" 
           + qrCodeChange3 + "^CF0,65\n" + "^FD" + ((inputMapData.get("netweight"))==null?"": padLeft(inputMapData.get("netweight").toString(),8))+ "^FS\n"
           + "^FO0,520\n" + XPSchanged2
           + "^FO30,540\n" + "^CF0,30\n" + "^FDTare^FS\n"
           + "^FO30,580\n" + qrCodeChange5 + "^FDWeight(Kg)^FS\n"
           + qrCodeChange6+ "^CF0,65\n" + "^FD" + ((inputMapData.get("tareweight"))==null?"": padLeft((inputMapData.get("tareweight").toString()),8)) + "^FS\n"
           + "^FO0,620\n" + XPSchanged3
           + "^FO30,640\n" + "^CF0,30\n" + "^FDTotal^FS\n"
           + "^FO30,680\n" + "^CF0,25\n" + "^FDWeight(Kg)^FS\n"
           + qrCodeChange8 + "^CF0,65\n" + "^FD" +  ((inputMapData.get("totalweight"))==null?"": padLeft((inputMapData.get("totalweight").toString()),8))+ "^FS\n"
           + "^FO0,720\n" + "^GB830,0,5^FS\n" 
           + "^FO30,740\n" + "^CF0,30\n" + "^FDLoaded At^FS\n" 
           + "^FO30,780\n" + "^CF0,70\n" + "^FD"+ ((inputMapData.get("loaded"))==null?"": (inputMapData.get("loaded").toString())) + "^FS\n" 
           + "^FO300,740\n" + "^CF0,30\n" + "^FDFlight^FS\n"
           + "^FO210,780\n" + "^CF0,70\n" + "^FD" + ((inputMapData.get("flightNo"))==null?"": (inputMapData.get("flightNo").toString())) + "^FS\n" 
           + "^FO580,740\n"+ "^CF0,30\n" + "^FDDate^FS\n" 
           + "^FO500,780\n" + "^CF0,70\n" + "^FD"+ ((inputMapData.get("flightDate"))==null?"": (inputMapData.get("flightDate").toString()).toUpperCase()) + "^FS\n" 
           + "^FO0,870\n" + "^GB830,0,5^FS\n" 
           + "^FO30,910\n"+ "^CF0,30\n" + "^FDContent^FS\n" 
           + "^FO180,900\n" + "^CF0,70\n" + "^FD"+ ((inputMapData.get("content"))==null?"": (inputMapData.get("content").toString()))+ "^FS\n" 
           + "^FO0,970\n" + "^GB830,0,5^FS\n" 
           + "^FO30,1000\n"+ "^CF0,30\n" + "^FDRemarks^FS\n" 
           + "^FO180,980\n" + "^CF0,40\n" + "^FD"+ ((inputMapData.get("remark"))==null?"": (inputMapData.get("remark").toString())) + "^FS\n" + "^CF0,45\n" 
           + "^FO30,1060\n" + "^FD" + (para1==null?"":para1) + "^FS\n" 
           + "^FO230,1060\n" + "^FD" + (para2==null?"":para2) + "^FS\n" 
           + "^FO430,1060\n" + "^FD" + (para3==null?"":para3) + "^FS\n" 
           + "^FO630,1060\n" + "^FD" + (para4==null?"":para4) + "^FS\n" 
           + "^FO30,1140\n" + "^FD" + (para5==null?"":para5) + "^FS\n" 
           + "^FO230,1140\n" + "^FD" +(para6==null?"":para6) + "^FS\n" 
           + "^FO430,1140\n" + "^FD" + (para7==null?"":para7) + "^FS\n" 
           + "^FO630,1140\n" + "^FD" + (para8==null?"":para8) + "^FS\n" 
           + "^FO30,1220\n" + "^FD" + (para9==null?"":para9) + "^FS\n" 
           + "^FO230,1220\n" + "^FD" + (para10==null?"":para10) + "^FS\n" 
           + "^FO430,1220\n" + "^FD" + (para11==null?"":para11) + "^FS\n" 
           + "^FO630,1220\n" + "^FD" +(para12==null?"":para12) + "^FS\n" 
           + "^XZ\n";
	   
       return convertStringToBase64Text(ZPLcommand);
   }

	   /**
	    * RFID AWB Report 
	    * @param inputMapData
	    * Structure of data
	    * -----------------
	    * Map<String, Object>
			* epcCode
			* hexaCollectiveUserMemory
			* tagNo
			* awbNo
			* displayAwbNo
			* qty			
	    * @return
	    */
   private String generateRFIDAWBReport(Map<String, Object> inputMapData) {
/*       String writeToRFID = "^XA ^FX EPC WRITING  ^RFW,H^FD" + inputMapData.get("epcCode") 
               + "^FS ^RFW,H,0," + ((String) inputMapData.get("hexaCollectiveUserMemory")).length() 
               + ",3^FD"+ inputMapData.get("hexaCollectiveUserMemory") 
               + "^FS ^RFR,H,0,16,1^FN1^FS^HV1,,^FS";*/
       
	  // String writeToRFID = "^XA ^RFW,H ^FD"+inputMapData.get("epcCode")+"^FS";
       
       String bodyCode = "^XA ^FX Tag No\n" 
    		   + "^FO30,20\n" + "^CF0,30\n" + "^FDTag No.^FS\n"
    		   + "^FO40,90\n" + "^CF0,200\n" + "^FD" + inputMapData.get("tagNo") + "^FS\n" + "^FX Barcode\n" + "^LH0,0\n" 
    		   + "^FO170,0\n" + "^GB0,420,5^FS\n" 
    		   + "^FO220,150\n" + "^BY 2,4.0,20\n" + "^BCN,85,N\n" + "^FD" + inputMapData.get("awbNo") + "^FS\n" 
    		   + "^FO190,70\n" + "^CF0,70\n" + "^FD" + inputMapData.get("displayAwbNo") + "^FS\n"
               + "^FX Qty\n"+"^FO640,0\n" + "^GB0,420,5^FS\n" 
    		   + "^FO700,20\n" + "^CF0,30\n" + "^FDQty^FS\n" 
               + "^FO650,90\n" 
               + "^CF0,200\n" + "^FD"+ inputMapData.get("qty") + "^FS\n"
       		   + "^RFW,H ^FD" + inputMapData.get("epcCode") + "^FS\n"               
               + "^XZ\n";
       //String rfidCommand = writeToRFID + bodyCode;
		   //+ "^RFW,H ^FD" + inputMapData.get("epcCode") + "^FS\n"
 
       String rfidCommand = bodyCode;
       //
       return convertStringToBase64Text(rfidCommand);
   }
   
   /**
    * RFID ULD Tag Report 
    * 
    * @param inputMapData
    	* Structure of data
		* -----------------
		* Map<String, Object>
			* user
			* printDateTime
			* uldtagId
			* destination
			* netweight
			* tareweight
			* totalweight
			* loaded
			* flightNo
			* flightDate
			* content
			* remark
			* List<String> dgDetailsList
    * @return
    */
   private String generateRFIDULDTagReport(Map<String, Object> inputMapData) {
	   inputMapData.put("IsRFID","Yes");
       
       return this.generateULDTAGReport(inputMapData);
   }
   
   /**
    * @param payload
    * @return
    */
   @SuppressWarnings({ "unused", "unchecked" })
   private String generateHandOverFormReport(Map<String, Object> inputMapdata) {
      StringBuilder sb = new StringBuilder();
	      sb.append(ESCPrinter.setCharacterSet(ESCPrinter.USA));
	      sb.append(ESCPrinter.underLineOn());
	      sb.append(ESCPrinter.print(" CARGO/MAIL HANDOVER FORM"));
	      sb.append(ESCPrinter.underLineOff());
	      sb.append(ESCPrinter.advanceVertical(1.5f));
	      sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1)); 
	      sb.append(ESCPrinter.print("  DRIVER ID : " + (inputMapdata.get("driverID")==null?"":inputMapdata.get("driverID"))));
	      sb.append(ESCPrinter.advanceVertical(0.7f));
	      sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
	      sb.append(ESCPrinter.print("FLIGHT/DATE : " +  (inputMapdata.get("flightNo")==null?"":inputMapdata.get("flightNo")) +"/" + (inputMapdata.get("flightDate")==null?"":inputMapdata.get("flightDate").toString().toUpperCase())));
	      sb.append(ESCPrinter.advanceVertical(0.7f));
	      sb.append(ESCPrinter.horizontalTab(1));
	      sb.append(ESCPrinter.print("  STD : " + (inputMapdata.get("std")==null?"":inputMapdata.get("std"))));
	      sb.append(ESCPrinter.advanceVertical(0.7f));
	      sb.append(ESCPrinter.horizontalTab(1));
	      sb.append(ESCPrinter.print("  ETD : " + (inputMapdata.get("etd")==null?"":inputMapdata.get("etd"))));	      
	      sb.append(ESCPrinter.advanceVertical(0.7f));
	      sb.append(ESCPrinter.horizontalTab(1));
	      sb.append(ESCPrinter.print("  BAY : " + (inputMapdata.get("bay")==null?"":inputMapdata.get("bay"))));
	      sb.append(ESCPrinter.advanceVertical(0.3f));
	      sb.append(ESCPrinter.print("-------------------------------------"));
	      sb.append(ESCPrinter.advanceVertical(0.5f));
	      sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
	      sb.append(ESCPrinter.print("HANDOVER"));
	      sb.append(ESCPrinter.advanceVertical(0.8f));
	      List<String> uldTrolleyNoList= (List<String>)inputMapdata.get("uldTrolleyNoList");
	      if(null!=uldTrolleyNoList && uldTrolleyNoList.size()>0) {
	    	  for(String uldTrolleyNo: uldTrolleyNoList) {
	    		  sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2));
	    		  sb.append(ESCPrinter.print(uldTrolleyNo));
	    		  sb.append(ESCPrinter.advanceVertical(0.7f));
	    	  }
	      }
	      sb.append(ESCPrinter.advanceVertical(0.5f));
	      sb.append(ESCPrinter.print("SATS : " + (inputMapdata.get("satsUserID")==null?"":inputMapdata.get("satsUserID")) + " " + (inputMapdata.get("releaseDateTime")==null?"":inputMapdata.get("releaseDateTime").toString().toUpperCase())));
	      sb.append(ESCPrinter.advanceVertical(7));
	      //sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
	      //sb.append(ESCPrinter.print(" "));
	      //sb.append(ESCPrinter.formFeed()); //eject paper
      return convertStringToBase64Text(sb.toString());
   }   
   
   @SuppressWarnings({ "unused", "unchecked" })
   private String generateLaserReport(Map<String, Object> inputMapdata)  {
      StringBuilder sb = new StringBuilder();
      sb.append(ESCPrinter.setCharacterSet2(ESCPrinter.USA));//sb.append(ESCPrinter.print(""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"));
      sb.append(ESCPrinter.print("\n \n \n \t \t \t Equipment Issue "));
      sb.append(ESCPrinter.HYPHEN);
      sb.append(ESCPrinter.print(" Self"));
      sb.append(ESCPrinter.HYPHEN);
      sb.append(ESCPrinter.print("Collection \n" ));      
      sb.append(ESCPrinter.print("\n \t \t \t \t \t \t \t \t \t \t \t Print By   : " + (inputMapdata.get("satsUserID")==null?"":inputMapdata.get("satsUserID"))));
      sb.append(ESCPrinter.print("\n \t \t \t \t \t \t \t \t \t \t \t Print Date : " + getCurrentDate()));
      sb.append(ESCPrinter.print("\n \t \t \t \t \t \t \t \t \t \t \t Print Time : " + getCurrentTime()));
      sb.append(ESCPrinter.print("\n    Terminal No: "+ (inputMapdata.get("terminalNo")==null?"":inputMapdata.get("terminalNo"))));
      sb.append(ESCPrinter.print("\n \n    Agent Code : "+ (inputMapdata.get("agentCode")==null?"":inputMapdata.get("agentCode"))));
      sb.append(ESCPrinter.print("\t\t COSYS"));
      sb.append(ESCPrinter.SINGLEQUOTES);	
      sb.append(ESCPrinter.print(" ID: "+ (inputMapdata.get("staffName")==null?"":inputMapdata.get("staffName"))+"   "+ (inputMapdata.get("staffID")==null?"":inputMapdata.get("staffID"))));
      sb.append(ESCPrinter.print("\n \n \n    Serial No : " + (inputMapdata.get("serialNo")==null?"":inputMapdata.get("serialNo"))));
      sb.append(ESCPrinter.print("\n \n \n \tPD Number\t\t ULD Number\t\t Handover Date/Time"));
      List<Map<String, String>> pouldList = (List<Map<String, String>>) inputMapdata.get("poUldDateList");
  	if(pouldList!=null && pouldList.size()>0 ) {
  		sb.append(ESCPrinter.print("\n \n     "));  		
  		for(Map<String, String> tmd:pouldList ) {
  			   sb.append("\t");
  			   sb.append(ESCPrinter.print(tmd.get("poNo")));  			   
			   sb.append(ESCPrinter.print(" \t\t\t " + tmd.get("uldNo")));
  			   sb.append(ESCPrinter.print(" \t\t " + tmd.get("hanoverdatetime")));
  			   sb.append("\n");
  		}
  	}      
     sb.append(ESCPrinter.print("\n \n \n \n \n \t \t \t \t  END OF REPORT"));
 
     return convertStringToBase64Text(sb.toString());    
      
   }
   
   
   
   /**
    * @param payload
    * @return
    */
   @SuppressWarnings({ "unused", "unchecked" })
   private String generateRePrintHandOverFormReport(Map<String, Object> payload) {
      String FirstSection = "ESC @\n" + "ESC 2 18\n" + "ESC a 0\n" + "GS L 30 0\n" + "GS ! 0x00\n"
            + "\"CARGO/MAIL HANDOVER FORM\"\n" + "ESC d 2\n" + "\"REPRINT\" LF\n" + "\"DUPLICATE\" LF\n"
            + "\"Driver ID:\" HT \"" + payload.get("driverID") + "\" LF\n" + "\"" + payload.get("flightNo") + "/"
            + payload.get("date") + "\" LF\n" + "\"STD:" + payload.get("std") + " ETD:" + payload.get("std") + "\" LF\n"
            + "\"BAY:" + payload.get("bay") + "\" LF\n" + "GS ! 0x00\n" + "ESC J 4\n" + "\"HANDOVER\"\n" + "ESC d 2\n";
      //
      String loopedSection = "";
      List<String> uldNoDate = (List<String>) payload.get("uldNoDate");
      if(null!=uldNoDate && uldNoDate.size()>0) {
	      for (int i = 0; i < uldNoDate.size(); i++) {
	         loopedSection += "\"" + payload.get("uldNoDate" + i) + "\" LF\n";
	      }
      }
      String LastSection = "ESC d 2\n" + "\"SATS:" + payload.get("satdID") + "\" LF LF\n" + "ESC d 2\n" + "GS V 66 0\n";
      String compliedCommand = FirstSection + loopedSection + LastSection;
      return convertStringToBase64Text(compliedCommand);
   }
  
   /**
    * @param payload
    * @return
    */
   @SuppressWarnings({ "unused", "unchecked" })
   private String generateOffloadFormReport(Map<String, Object> inputMapdata) {
	   String driverID = inputMapdata.get("driverID")==null?"":(String)inputMapdata.get("driverID");
	   String flightNo = inputMapdata.get("flightNo")==null?"":(String)inputMapdata.get("flightNo");
	   String flightDate = inputMapdata.get("flightDate")==null?"":((String)inputMapdata.get("flightDate")).toUpperCase();
	   String std = inputMapdata.get("std")==null?"":(String)inputMapdata.get("std");
	   String etd = inputMapdata.get("etd")==null?"":(String)inputMapdata.get("etd");
	   String atd = inputMapdata.get("atd")==null?"":(String)inputMapdata.get("atd");
	   String bay = inputMapdata.get("bay")==null?"":(String)inputMapdata.get("bay");
	   String offloadRemarks = inputMapdata.get("offloadRemarks")==null?"":(String)inputMapdata.get("offloadRemarks");
	   String satsUserID = inputMapdata.get("satsUserID")==null?"":(String)inputMapdata.get("satsUserID");
	   String releaseDateTime = inputMapdata.get("releaseDateTime")==null?"":((String)inputMapdata.get("releaseDateTime")).toUpperCase();
	   String formHeaderType ="OFFLOAD"; 
		if(inputMapdata.containsKey("returnInd") && (Boolean)inputMapdata.get("returnInd")) {
			formHeaderType = "RETURN";
		}	   
		StringBuilder sb = new StringBuilder();
			sb.append(ESCPrinter.setCharacterSet(ESCPrinter.USA));
			sb.append(ESCPrinter.underLineOn());
			sb.append(ESCPrinter.print(" CARGO/MAIL HANDOVER FORM: "+formHeaderType));
			sb.append(ESCPrinter.underLineOff());
			sb.append(ESCPrinter.advanceVertical(0.7f));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
			sb.append(ESCPrinter.print("  DRIVER ID : " +  driverID));
			sb.append(ESCPrinter.advanceVertical(0.7f));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
			sb.append(ESCPrinter.print("FLIGHT/DATE : " +  flightNo+"/" +flightDate));
			sb.append(ESCPrinter.advanceVertical(0.7f));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
			sb.append(ESCPrinter.print("	  STD : " + std));
			sb.append(ESCPrinter.advanceVertical(0.7f));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
			sb.append(ESCPrinter.print("	  ETD : " + etd));
			sb.append(ESCPrinter.advanceVertical(0.7f));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
			sb.append(ESCPrinter.print("	  ATD : " + atd));			
			sb.append(ESCPrinter.advanceVertical(0.7f));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
			sb.append(ESCPrinter.print("	  BAY : " + bay));
		    sb.append(ESCPrinter.advanceVertical(0.3f));
		    sb.append(ESCPrinter.print("-------------------------------------"));			
			sb.append(ESCPrinter.advanceVertical(0.9f));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
			//sb.append(ESCPrinter.print("Offload Reasons: " + offloadRemarks));
			if(inputMapdata.containsKey("returnInd") && (Boolean)inputMapdata.get("returnInd")) {
				sb.append(ESCPrinter.print("RETURN"));
			}else {
				sb.append(ESCPrinter.print("OFFLOAD"));
			}
			sb.append(ESCPrinter.advanceVertical(1.0f));
			List<String> uldNoFlightDateList= (List<String>)inputMapdata.get("uldNoFlightDateList");
			if(null!=uldNoFlightDateList && uldNoFlightDateList.size()>0) {
				for(String uldNoFlightDate: uldNoFlightDateList) {
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
			    sb.append(ESCPrinter.print(uldNoFlightDate));
			    sb.append(ESCPrinter.advanceVertical(0.7f));
				}
			}
			sb.append(ESCPrinter.advanceVertical(0.8f));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
			sb.append(ESCPrinter.print("SATS: " + satsUserID+ " " +releaseDateTime));
			sb.append(ESCPrinter.advanceVertical(1.0f));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1)); 
			sb.append(ESCPrinter.print("Please check that the PD locks are")); 
			sb.append(ESCPrinter.advanceVertical(0.7f));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
			sb.append(ESCPrinter.print("in the up position before towing."));			
			sb.append(ESCPrinter.advanceVertical(8));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
			sb.append(ESCPrinter.print(" "));
			sb.append(ESCPrinter.formFeed()); //eject paper
	
		return convertStringToBase64Text(sb.toString());
   }

   /**
    * @param payload
    * @return
    */
   @SuppressWarnings({ "unused", "unchecked", "unchecked" })
   private String generateBupNoteDeliveryReport(Map<String, Object> m) {	  
	   
	   StringBuilder sb = new StringBuilder();      
	   sb.append(ESCPrinter.setCharacterSet(ESCPrinter.USA));
	   sb.append(ESCPrinter.selectLQPrinting()); 
	   sb.append(ESCPrinter.advanceVertical(1));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f));
	   sb.append(ESCPrinter.setBarcode(m.get("doNo").toString()));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(8)); //go to the right in cm
	   sb.append(ESCPrinter.print("\t NOTE OF DELIVERY"));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(14)); 
	   sb.append(ESCPrinter.print("REGN NO: " + m.get("regnNo")));
	   sb.append(ESCPrinter.advanceVertical(1));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2));
	   sb.append(ESCPrinter.print("*" + m.get("doNo").toString()+ "*"));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(8)); 
	   sb.append(ESCPrinter.print("\t\t" + m.get("doCode").toString()));    
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(15.5f));
	   sb.append(ESCPrinter.print(m.get("from") + " - " + m.get("to") + " *"));
	   sb.append(ESCPrinter.advanceVertical(1));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f));
	   sb.append(ESCPrinter.print("QNTY* "));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2));
	   sb.append(ESCPrinter.print(m.get("qnty").toString()));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(4));
	   sb.append(ESCPrinter.print(m.get("weight").toString()));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
	   sb.append(ESCPrinter.print("\t\t CMOD*"));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(10));
	   sb.append(ESCPrinter.print(m.get("cmod").toString()));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(14));
	   sb.append(ESCPrinter.print(m.get("eli") +" " + m.get("spx")));	            
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(16));
	   sb.append(ESCPrinter.print("ACCS* " + (m.get("accs") == null ? "" : m.get("accs"))));
	   sb.append(ESCPrinter.advanceVertical(1));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f));
	   sb.append(ESCPrinter.print("CNEE* "));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2));
	   sb.append(ESCPrinter.print("#" + m.get("cnee")));
	   sb.append(ESCPrinter.advanceVertical(0.5f));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2));
	   sb.append(ESCPrinter.print("#" + m.get("hex")));
	   sb.append(ESCPrinter.advanceVertical(0.5f));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f));
	   sb.append(ESCPrinter.print("AGT* "));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2));
	   sb.append(ESCPrinter.print(m.get("agt").toString()));
	   sb.append(ESCPrinter.advanceVertical(1.5f));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(4));
	   sb.append(ESCPrinter.print("PD Number: " + m.get("pdNumber").toString()));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(10));
	   sb.append(ESCPrinter.print("\t ULD Number: " + m.get("uldNumber").toString()));
	   sb.append(ESCPrinter.advanceVertical(1.5f));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
	   sb.append(ESCPrinter.print("DLVY* "));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(3));
	   sb.append(ESCPrinter.print(m.get("agt").toString()));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
	   sb.append(ESCPrinter.print(m.get("qnty").toString() + "\t\t" + m.get("weight").toString()));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(11));
	   sb.append(ESCPrinter.print("STORAGE CHARGE DUE* "));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(16));
	   sb.append(ESCPrinter.print(m.get("chargeDue").toString()== null ? "NIL" :m.get("chargeDue").toString()));
	   sb.append(ESCPrinter.advanceVertical(1));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
	   sb.append(ESCPrinter.print("FRT* "));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(3));
	   sb.append(ESCPrinter.print(m.get("flight").toString()));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
	   sb.append(ESCPrinter.print(m.get("date").toString()));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(9));
	   sb.append(ESCPrinter.print(m.get("qnty").toString()));
	   sb.append(ESCPrinter.advanceVertical(2));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
	   sb.append(ESCPrinter.print("*DLVY TO: "));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(3.5f));
	   sb.append(ESCPrinter.print(m.get("dlvyTo").toString()));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(12));
	   sb.append(ESCPrinter.print("*SIGNATURE: ___________________"));
	   sb.append(ESCPrinter.advanceVertical(1));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1));
	   sb.append(ESCPrinter.print("*DLVY BY: "));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(3.5f));
	   sb.append(ESCPrinter.print(m.get("dlvyBy").toString()));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
	   sb.append(ESCPrinter.print("\t\t*TIME DELIVERY COMPLETED: "));
	   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(15));
	   sb.append(ESCPrinter.print(m.get("timeComp").toString())); 
	  // sb.append(ESCPrinter.formFeed()); //eject paper
     
	   return convertStringToBase64Text(sb.toString());
   }
  
   /**
    * @param payload
    * @return
    */
   @SuppressWarnings({ "unused", "unchecked" })
   private String generateTransferManifestReport(Map<String, Object> m) {
	StringBuilder sb = new StringBuilder();
	sb.append(ESCPrinter.resetPrinter());
	sb.append(ESCPrinter.setCharacterSet(ESCPrinter.USA).toString());
	sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
	sb.append(ESCPrinter.print("\t\t" + m.get("transferringCarrier").toString()));
	sb.append(ESCPrinter.setAbsoluteHorizontalPosition(15.5f)); 
	sb.append(ESCPrinter.print("\t" + m.get("tmNo").toString()));
	sb.append(ESCPrinter.advanceVertical(1)); 
	sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2.5f)); 
	sb.append(ESCPrinter.print(m.get("airport").toString()));    
	sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
	sb.append(ESCPrinter.print("\t\t" + m.get("date").toString())); 
	sb.append(ESCPrinter.setAbsoluteHorizontalPosition(13.5f));
	
	if (m.get("receivingCarrier").toString().length() < 25) {
	sb.append(ESCPrinter.print(m.get("receivingCarrier").toString()));
	sb.append(ESCPrinter.advanceVertical(3f));
	} else {
		String a = m.get("receivingCarrier").toString().substring(0, 25);
		String b = m.get("receivingCarrier").toString().substring(25);
		sb.append(ESCPrinter.print(a));
		sb.append(ESCPrinter.advanceVertical(0.5f));
		sb.append(ESCPrinter.setAbsoluteHorizontalPosition(13.5f));
		sb.append(ESCPrinter.print(b));
		sb.append(ESCPrinter.advanceVertical(2.5f));
	}
	
	//print the location details list...
	List<Map<String, String>> transferManifestDetails = (List<Map<String, String>>) m.get("trmList");
	if(transferManifestDetails!=null && transferManifestDetails.size()>0 ) {
		for(Map<String, String> tmd:transferManifestDetails ) {
			String remarks = tmd.get("remarks")==null?"":tmd.get("remarks");
			 sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f));
			   sb.append(ESCPrinter.print(tmd.get("awbNo")));
			   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
			   sb.append(ESCPrinter.print(tmd.get("awbDest")));
			   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
			   sb.append(ESCPrinter.print("\t\t\t" + tmd.get("noPackage")));
			   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(12));
			   sb.append(ESCPrinter.print(padLeft(tmd.get("weight")+tmd.get("weightUnitCode"),8)));
			   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(14.4f));
			   sb.append(ESCPrinter.print(remarks));
			   sb.append(ESCPrinter.advanceVertical(0.8f));
		}
	}
	
	sb.append(ESCPrinter.advanceVertical((7 - (transferManifestDetails.size()+ 1)) + 2f));
	sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
	sb.append(ESCPrinter.print(m.get("transferringCarrier").toString()));
	sb.append(ESCPrinter.advanceVertical(2));
	sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2));
	sb.append(ESCPrinter.print(m.get("userId").toString()));
	sb.append(ESCPrinter.setAbsoluteHorizontalPosition(13));
	
	if (m.get("receivingCarrier").toString().length() < 25) {
	sb.append(ESCPrinter.print(m.get("receivingCarrier").toString()));
	sb.append(ESCPrinter.advanceVertical(7f));
	} else {
		String a = m.get("receivingCarrier").toString().substring(0, 25);
		String b = m.get("receivingCarrier").toString().substring(25);
		sb.append(ESCPrinter.print(a));
		sb.append(ESCPrinter.advanceVertical(0.5f));
		sb.append(ESCPrinter.setAbsoluteHorizontalPosition(13));
		sb.append(ESCPrinter.print(b));
		sb.append(ESCPrinter.advanceVertical(6.5f));
	}
	sb.append(ESCPrinter.setAbsoluteHorizontalPosition(13));
	sb.append(ESCPrinter.print(""));
//	sb.append(ESCPrinter.formFeed());
      return convertStringToBase64Text(sb.toString());
   }
  
   /**
    * @param payload
    * @return
    */
   private static String convertStringToBase64Text(Object payload) {
      return Base64.getEncoder().encodeToString(((String) payload).getBytes());
   }
  
	  // pad with " " to the right to the given length (n)
	  public static String padRight(String s, int n) {
	    return String.format("%1$-" + n + "s", s);
	  }

	  // pad with " " to the left to the given length (n)
	  public static String padLeft(String s, int n) {
	    return String.format("%1$" + n + "s", s);
	  }
	  
	   public static String maskNRIC(String nric) {
		   	  if(nric==null)
		   		  return "";
		      nric=nric.toUpperCase();
		      if(nric.length()>=9 && nric.charAt(8)>='A' && nric.charAt(8)<='Z') {
		    	  nric=nric.charAt(0)+"***"+nric.substring(4);
		      }
		      return nric;
		   }

	   public static String getCurrentDate() {
		      return new SimpleDateFormat("ddMMMyyyy").format(new Date()).toUpperCase();
		   }
	   
	   public static String getCurrentTime() {
		      return new SimpleDateFormat("k:mm").format(new Date());
		   }
	   /**
	    * Structure of data
	    * -----------------
	    * Map<String, Object>
	    * awbNo
	    * awbWeight
	    * awbPcs
	    * dateTime DDMONYY HHMM
	    * poNo
	    * consigneeDetails
	    * personCollect
	    * icNo
	    * apptAgent
	    * nog
	    * shc List <String>
	    * locationInfoList List<Map<String,Object>>
	    * 	flightNo
	    * 	flightDate DDMONYY HHMM
	    * 	pcs
	    * 	loc
	    * 	truckDock
	    * 	uldBinLoc
	    * 	WhLoc
	    */
	   @SuppressWarnings({ "unused", "unchecked" })
	   private String generatePickOrderReport(Map<String, Object> inputMapData) {
			StringBuilder sb = new StringBuilder();
			String poHeader = "PICK ORDER";
			sb.append(ESCPrinter.resetPrinter());
			sb.append(ESCPrinter.setCharacterSet(ESCPrinter.USA).toString());
			sb.append(ESCPrinter.selectLQPrinting().toString());
			sb.append(ESCPrinter.select15CPI().toString());
			sb.append(ESCPrinter.setPageSizeLines(36));
			sb.append(ESCPrinter.fontSansserif());
			
			sb.append(ESCPrinter.advanceVertical(1));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
			if(inputMapData.containsKey("isPrintFromSSK") && (Boolean)inputMapData.get("isPrintFromSSK")) {
				poHeader = "E-PICK ORDER";
			}

			sb.append(ESCPrinter.underLineOn());
			sb.append(ESCPrinter.print(poHeader));
			sb.append(ESCPrinter.underLineOff());
		   	sb.append(ESCPrinter.horizontalTab(2));
			sb.append(ESCPrinter.setBarcode((inputMapData.get("awbNo").toString()).toUpperCase())); // BARCODE
			sb.append(ESCPrinter.bold(false));
			sb.append(ESCPrinter.advanceVertical(1.3f));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f));
			sb.append(ESCPrinter.print("Pick Order Print     " ));
			sb.append(ESCPrinter.bold(true));
			sb.append(ESCPrinter.print(((String)(inputMapData.get("dateTime")==null?"":inputMapData.get("dateTime"))).toUpperCase()));
			sb.append(ESCPrinter.bold(false));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(12));
			sb.append(ESCPrinter.print("E-DR No       " ));
			sb.append(ESCPrinter.bold(true));
			sb.append(inputMapData.get("poNo")==null?"":(String)inputMapData.get("poNo"));
			sb.append(ESCPrinter.bold(false));
			sb.append(ESCPrinter.advanceVertical(0.5f));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f)); 
			sb.append(ESCPrinter.print("Consignee Details    "));
			sb.append(ESCPrinter.bold(true));
			
			String conagt = "";
			conagt=(String)(inputMapData.get("consigneeDetails")==null?"":(String)inputMapData.get("consigneeDetails"));
			String firstcon = "";	
			String secondcon = "";
			
			String appagt = "";
			appagt=(String)((inputMapData.get("apptAgent")==null?"":(String)inputMapData.get("apptAgent")));
			
			if (conagt.length() < 42 && conagt!="") 
			{
				sb.append(conagt);	
			}
			if (conagt.length() > 42 && conagt!="") 
			{
				firstcon = conagt.substring(0 , 42);							
			    sb.append((firstcon));
			}
			
			sb.append(ESCPrinter.bold(false));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(12)); 
			sb.append(ESCPrinter.print("Appt Agent    "));
			sb.append(ESCPrinter.bold(true));
			
			String firststring = "";	
			String secondstring = "";
			String thirdstring = "";
							
		    if (appagt.length()<25 && appagt!="") 
			{
				 sb.append(appagt);				
			}
		    if (appagt.length()>25 && appagt.length() < 50 && appagt!="") 
			{
				firststring = appagt.substring(0 , 25);						
				sb.append((firststring));				
				
			 }
		    if (appagt.length() > 50 && appagt!="") 
			 {	
					firststring = appagt.substring(0 , 25);
					sb.append((firststring));
			 }
		    
		    if (conagt.length() > 42 && conagt!="") 
			{
				firstcon = conagt.substring(0 , 42);				
				secondcon = conagt.substring(42);
			    sb.append(ESCPrinter.setAbsoluteHorizontalPosition(4.0f)); 
			    sb.append(ESCPrinter.advanceVertical(0.5f)); 
			    sb.append((secondcon));
			}
		    if (appagt.length()>25 && appagt.length() < 50 && appagt!="" && conagt.length() < 42 && conagt!="") 
			{					
				secondstring = appagt.substring(25);
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(14.4f));
				sb.append(ESCPrinter.advanceVertical(0.5f));				
				sb.append((secondstring));
				
			 }
		    if (appagt.length()>25 && appagt.length() < 50 && appagt!="" && conagt.length() > 42 && conagt!="") 
			{					
				secondstring = appagt.substring(25);
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(14.4f));								
				sb.append((secondstring));				
			 }
		    
		    if (appagt.length() > 50 && appagt!="" && conagt.length() < 42 && conagt!="")
		    {
		    	secondstring = appagt.substring(25,50);	
				thirdstring = appagt.substring(50);				
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(14.4f));
				sb.append(ESCPrinter.advanceVertical(0.5f)); 
				sb.append((secondstring));				
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(14.4f));
				sb.append(ESCPrinter.advanceVertical(0.5f)); 
				sb.append((thirdstring));
		    }
		    	
		    if (appagt.length() > 50 && appagt!="" && conagt.length() > 42 && conagt!="") 
			 {	
					secondstring = appagt.substring(25,50);	
					thirdstring = appagt.substring(50);					
					sb.append(ESCPrinter.setAbsoluteHorizontalPosition(14.4f));					 
					sb.append((secondstring));					
					sb.append(ESCPrinter.setAbsoluteHorizontalPosition(14.4f));
					sb.append(ESCPrinter.advanceVertical(0.5f)); 
					sb.append((thirdstring));	
			 }
			sb.append(ESCPrinter.bold(false));
			sb.append(ESCPrinter.advanceVertical(0.5f));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f)); 
			sb.append(ESCPrinter.print("Person Collecting    "));
			sb.append(ESCPrinter.bold(true));
			sb.append((inputMapData.get("personCollect")==null?"":(String)inputMapData.get("personCollect"))+ " " );
			
			String icnum = (String)(inputMapData.get("icNo")==null?"":(String)inputMapData.get("icNo"));
			
			String lastFiveDigits = "";		//substring containing last 5 characters

			if (icnum.length() > 5 && icnum!="") 
			{
				lastFiveDigits = icnum.substring(icnum.length() - 5);				
				sb.append(lastFiveDigits);
			} 
			else 
			{
				sb.append(icnum);		
			}			
			sb.append(ESCPrinter.bold(false));
			
			sb.append(ESCPrinter.advanceVertical(1)); 
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f)); 
			sb.append(ESCPrinter.underLineOn());
			sb.append(ESCPrinter.print("AWB No"));    
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(4));
			sb.append(ESCPrinter.print("Tot PCS/Weight"));
			sb.append(ESCPrinter.horizontalTab(1));
			sb.append(ESCPrinter.print("NOG"));
			sb.append(ESCPrinter.horizontalTab(4));
			sb.append(ESCPrinter.print("SHC"));
			sb.append(ESCPrinter.underLineOff());

			
			sb.append(ESCPrinter.fontRoman());
			sb.append(ESCPrinter.advanceVertical(0.5f)); 
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f)); 
			sb.append(ESCPrinter.bold(true));
			sb.append(ESCPrinter.print(inputMapData.get("awbNo")==null?"":inputMapData.get("awbNo").toString().substring(0, 3)+" "+inputMapData.get("awbNo").toString().substring(3, inputMapData.get("awbNo").toString().length())));    
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(4));
			sb.append(ESCPrinter.print(inputMapData.get("awbPcs")==null?"":String.format("%-15s",(inputMapData.get("awbPcs").toString()+"/"+(inputMapData.get("awbWeight")==null?"":inputMapData.get("awbWeight").toString())))));			
			sb.append(ESCPrinter.horizontalTab(1));
			sb.append(ESCPrinter.print(inputMapData.get("nog")==null?"":String.format("%-20s", inputMapData.get("nog").toString()))); //pad 20 char

			//SHC
			//Print first 4 SHC
			sb.append(ESCPrinter.horizontalTab(2));
			List<String> shcList= (List<String>)inputMapData.get("shc");
			if(shcList!=null && shcList.size()>0 ) {
				for(int i=0;i<shcList.size(); i++) {
	/*				if(i==4) {//5th element should be in next line
						sb.append(ESCPrinter.advanceVertical(0.5f)); 
						sb.append(ESCPrinter.horizontalTab(5));
					}*/
					String shc=shcList.get(i);
					if(null!=shc) {
						sb.append(ESCPrinter.print(shc));
						sb.append(ESCPrinter.space());
					}
				}
			}
			sb.append(ESCPrinter.bold(false));
			sb.append(ESCPrinter.advanceVertical(1)); 
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f)); 
			sb.append(ESCPrinter.underLineOn());
			sb.append(ESCPrinter.print("Flight/Date"));    
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(4.2f));
			sb.append(ESCPrinter.print("Dly Pieces"));
			sb.append(ESCPrinter.underLineOff());
			sb.append(ESCPrinter.print("   "));
			sb.append(ESCPrinter.underLineOn());
			sb.append(ESCPrinter.horizontalTab(1));
			sb.append(ESCPrinter.print("Location"));
			sb.append(ESCPrinter.horizontalTab(1));			 
			sb.append(ESCPrinter.print("Warehouse"));			
			sb.append(ESCPrinter.horizontalTab(1));			
			sb.append(ESCPrinter.print("Truck Dock"));			
			sb.append(ESCPrinter.horizontalTab(1));
			sb.append(ESCPrinter.print("ULD/BIN Exit"));
			sb.append(ESCPrinter.horizontalTab(1));
			sb.append(ESCPrinter.print("ICS Loc"));
			sb.append(ESCPrinter.underLineOff());			



			
			sb.append(ESCPrinter.advanceVertical(0.2f)); 
			sb.append(ESCPrinter.bold(true));
			//print the location details list...
			List<Map<String, String>> locationInfoList = (List<Map<String, String>>) inputMapData.get("locationInfoList");
			if(locationInfoList!=null && locationInfoList.size()>0 ) {
				for(Map<String, String> locationInfo:locationInfoList ) {
					sb.append(ESCPrinter.advanceVertical(0.5f)); 
					sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f)); 
					sb.append(ESCPrinter.print(padRight(locationInfo.get("flightNo").toString(),8) + " / " + locationInfo.get("flightDate").toString().toUpperCase()));    
					sb.append(ESCPrinter.setAbsoluteHorizontalPosition(4.2f));
					sb.append(ESCPrinter.print((locationInfo.get("pcs")==null?String.format("%1$-8s", ""):String.format("%1$8s", locationInfo.get("pcs")))));
					sb.append(ESCPrinter.horizontalTab(1));
					sb.append(ESCPrinter.print((locationInfo.get("loc")==null?String.format("%1$10s", ""):String.format("%1$-10s", locationInfo.get("loc")))));
					sb.append(ESCPrinter.horizontalTab(1));									
					sb.append(ESCPrinter.print((locationInfo.get("warehouseLoc")==null?String.format("%1$-10s", ""):String.format("%1$-10s", locationInfo.get("warehouseLoc")))));
					sb.append(ESCPrinter.horizontalTab(1));									
					sb.append(ESCPrinter.print((locationInfo.get("truckDock")==null?String.format("%1$-10s", ""):String.format("%1$-10s", locationInfo.get("truckDock")))));
					sb.append(ESCPrinter.horizontalTab(1));
					sb.append(ESCPrinter.print((locationInfo.get("uldBinLoc")==null?String.format("%1$-15s", ""):String.format("%1$-15s", locationInfo.get("uldBinLoc")))));
					sb.append(ESCPrinter.horizontalTab(1));
					sb.append(ESCPrinter.print((locationInfo.get("exitICSLocation")==null?String.format("%1$-10s", ""):String.format("%1$-10s", locationInfo.get("exitICSLocation")))));
				}
			}
			sb.append(ESCPrinter.bold(false));

			if(inputMapData.containsKey("isPrintFromSSK") && (Boolean)inputMapData.get("isPrintFromSSK") && inputMapData.containsKey("agentUENNo")) {
				sb.append(ESCPrinter.advanceVertical(1));
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f));
				sb.append(ESCPrinter.underLineOn());
				sb.append(ESCPrinter.print("APPT Agent IA/Permit/Exemption"));
				sb.append(ESCPrinter.underLineOff());
				sb.append(ESCPrinter.advanceVertical(0.5f)); 
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f)); 
				sb.append(ESCPrinter.print("LAR : "+inputMapData.get("agentUENNo")));
				
				sb.append(ESCPrinter.advanceVertical(0.9f)); 
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f)); 
				sb.append(ESCPrinter.underLineOn());
				sb.append(ESCPrinter.print("Remarks:")); 
				sb.append(ESCPrinter.underLineOff());

				sb.append(ESCPrinter.advanceVertical(0.5f)); 
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f)); 
				sb.append(ESCPrinter.print("WARNING - Staff to ensure person collecting ID is correct."));
				sb.append(ESCPrinter.advanceVertical(0.4f));
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f)); 
				sb.append(ESCPrinter.print("Agent IA, Permit or Exemption requirement is in order before release of consignment.")); 

			}
			sb.append(ESCPrinter.advanceVertical(0.9f));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(4));
			sb.append(ESCPrinter.print("******** End Of Report ********"));
			//manual formfeed
			sb.append(ESCPrinter.advanceVertical(3));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(4));
			sb.append(ESCPrinter.print("   "));
			//sb.append(ESCPrinter.formFeed()); //eject paper
	   		//sb.append(ESCPrinter.resetPrinter());
		    return convertStringToBase64Text(sb.toString());
	   }

	   /**
	    * Structure of data
	    * -----------------
	    * Map<String, Object>
			* doNo (DO Number)
			* regnNo (SATS UEN No. Eg: 198500561R)
			* awbNo (AWB No.)
			* awbOrigin (Origin)
			* awbDestination (Destination)
			* awbPcs (Number of Pieces)
			* awbWt (Weight with Units K eg: 702.0K)
			* nog (Nature of goods)
			* shcList List<String> (Special Handling Code, list of string)
			* awbChargeCode (Charge Code - CC or PP)
			* cneeAddress1 (Consignee Name, Street)
			* cneeAddress2 (Consignee Country, Postal code)
			* apptAgent (Appointed Agent CODE)
			* clearingAgent (clearing Agent CODE)

			* deliveryPcs (pieces delivered in this DO)
			* deliveryWt (weight delivered in this DO)
			* chargesDue (Charges amount, if no charges pending 'NIL')
			* deliveryTo (person, name of person taking delivery)
			* deliveryToIC (person, Id / IC / etc of person taking delivery)
			* deliveryBy (staff delivering the cargo, userid)
			* deliveryDateTime (Delivery completed date - time ddMONYY HHMM)
			* locationInfo List<Map<String,Object>>
		    * 	flightNo
		    * 	flightDate
		    * 	pcs
	    * 
	    */
	   
	   @SuppressWarnings({ "unused", "unchecked" })
	   private String generateNoteDeliveryReport(Map<String, Object> inputMapData) {	  
		   StringBuilder sb = new StringBuilder();     
		   //setup/...
		   sb.append(ESCPrinter.setCharacterSet(ESCPrinter.USA));
		   sb.append(ESCPrinter.selectLQPrinting());
		   sb.append(ESCPrinter.select15CPI().toString());
		   sb.append(ESCPrinter.setPageSizeLines(36)); // set page size for tear paper.18cm/page.
		   sb.append(ESCPrinter.resetPrinter());
		   sb.append(ESCPrinter.advanceVertical(0.1f));		   
		   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(4));
			   sb.append(ESCPrinter.bold(true));
			   sb.append(ESCPrinter.print(inputMapData.get("doNo").toString()));
			   sb.append(ESCPrinter.horizontalTab(2));
			   sb.append(ESCPrinter.print("NOTE OF DELIVERY"));
			   sb.append(ESCPrinter.bold(false));
			   sb.append(ESCPrinter.horizontalTab(3)); 
			   sb.append(ESCPrinter.printBold("Regn No : "));
			   sb.append(inputMapData.get("regnNo")==null?"":inputMapData.get("regnNo"));
			   
			sb.append(ESCPrinter.advanceVertical(0.5f));
			   sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f));
//			   sb.append(ESCPrinter.setBarcode39(inputMapData.get("doNo").toString())); // Barcode
			   sb.append(ESCPrinter.setBarcode(inputMapData.get("doNo").toString())); // Barcode			   
		   	   sb.append(ESCPrinter.horizontalTab(1));
			   sb.append(ESCPrinter.print(inputMapData.get("awbNo")==null?"":inputMapData.get("awbNo").toString().substring(0, 3)+" "+inputMapData.get("awbNo").toString().substring(3, inputMapData.get("awbNo").toString().length())));  
			   sb.append(ESCPrinter.horizontalTab(2));
			   sb.append(ESCPrinter.print((inputMapData.get("awbOrigin")==null?"":inputMapData.get("awbOrigin")) + " - " + (inputMapData.get("awbDestination")==null?"":inputMapData.get("awbDestination"))));
		   
		   sb.append(ESCPrinter.advanceVertical(0.8f));
		   		sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f));
		   		sb.append(ESCPrinter.printBold("QNTY : "));
		   		sb.append(ESCPrinter.print(String.format("%-10s",inputMapData.get("awbPcs").toString()+"/"+inputMapData.get("awbWt").toString())));
		   		sb.append(ESCPrinter.printBold("    CMOD : "));
		   		sb.append(ESCPrinter.print(String.format("%-20s",inputMapData.get("nog").toString())));
		   		sb.append(ESCPrinter.printBold("    SHC : "));
				//SHC
				List<String> shcList= (List<String>)inputMapData.get("shcList");
				if(shcList!=null && shcList.size()>0 ) {
					for(int i=0;i<shcList.size(); i++) {
						String shc=shcList.get(i);
						if(null!=shc) {
							sb.append(ESCPrinter.print(shc));
							sb.append(ESCPrinter.space());
						}
					}
				} else {
					sb.append(ESCPrinter.space(30));
				}
		   		sb.append(ESCPrinter.printBold("    ACCS : "));
		   		sb.append(inputMapData.get("awbChargeCode") == null ? "" : inputMapData.get("awbChargeCode"));
		   		
		   	sb.append(ESCPrinter.advanceVertical(0.8f));
		   		sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f));
		   		sb.append(ESCPrinter.printBold("Consignee : "));
		   		sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2.5f));
		   		sb.append(ESCPrinter.print((String) inputMapData.get("cneeAddress1")));
		   		
		   	sb.append(ESCPrinter.advanceVertical(0.5f));
		   		sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2.5f));
		   		sb.append(ESCPrinter.print((String)inputMapData.get("cneeAddress2")));
		   		
		   	sb.append(ESCPrinter.advanceVertical(0.5f));
		   		sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f));
		   		sb.append(ESCPrinter.printBold("Agent : "));
		   		sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2));
		   		sb.append(ESCPrinter.print(inputMapData.get("apptAgent").toString()));

		   	sb.append(ESCPrinter.advanceVertical(2.7f));
				//print the location details list...
				List<Map<String, String>> locationInfoList = (List<Map<String, String>>) inputMapData.get("locationInfoList");
				List<String> pdULDNosList;
				if(!inputMapData.containsKey("pdULDNosList")) {
					pdULDNosList = new ArrayList<String>();
				} else {
					pdULDNosList = (List<String>) inputMapData.get("pdULDNosList");	
				}
				int a = 0;
				int b;
				if(locationInfoList!=null && locationInfoList.size()>0 ) {
					for(int i=0;i<=5; i++) {
						b = a+1;
						// Display max 7 lines of data
						//First column of data
						sb.append(ESCPrinter.advanceVertical(0.5f)); 					
						if(i<locationInfoList.size()) {
							Map<String, String> locationInfo=locationInfoList.get(i);
							sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f)); 
							sb.append(ESCPrinter.print(padLeft(locationInfo.get("flightNo").toString(),8) + " / " + locationInfo.get("flightDate").toString().toUpperCase()));    
							sb.append(ESCPrinter.print((locationInfo.get("pcs")==null)?"":padLeft(locationInfo.get("pcs"),8)));
						}else {
							sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5.0f)); 
						}
						// PRINT PD / ULD Details
						if(null!=pdULDNosList && a<pdULDNosList.size()) {
							sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5.4f));
							sb.append(ESCPrinter.horizontalTab(1));
							sb.append(ESCPrinter.print(padRight(pdULDNosList.get(a),20)));
								if (b<pdULDNosList.size()) {
									sb.append(ESCPrinter.horizontalTab(1));
									sb.append(ESCPrinter.print(padRight(pdULDNosList.get(b),20)));
								}else{
									sb.append(ESCPrinter.horizontalTab(3));
								}
							a = b+1;
						}else {
							sb.append(ESCPrinter.horizontalTab(6));
						}
						
						//Second column of data
						sb.append(ESCPrinter.horizontalTab(1));
						switch(i) {
						case 0: 			
							sb.append(ESCPrinter.printBold(padLeft("Delivery : ",10)));
                            String clagent = (String)(inputMapData.get("clearingAgent")==null?"":(String)inputMapData.get("clearingAgent"));
	 						String firststring = "";	
	 						String secondstring = "";
							if (clagent.length() > 21 && clagent!="") 
	 						{
	 							firststring = clagent.substring(0 , 21);				
								secondstring = clagent.substring(21);																
								sb.append(ESCPrinter.print(String.format("%s",firststring)));
								sb.append(ESCPrinter.horizontalTab(12));
								sb.append(ESCPrinter.print("   "+String.format("%s",secondstring.trim())));
	 						} 
	 						else 
	 						{
	 							sb.append(ESCPrinter.print(String.format("%-5s",clagent)));	 											
	 						}
							break;
						case 1:
					   		sb.append(ESCPrinter.printBold(padLeft("Pcs / Wt : ",10)));
					   		sb.append(ESCPrinter.print(inputMapData.get("deliveryPcs").toString()+" / "+inputMapData.get("deliveryWt").toString()));
					   		break;
						case 2:
			   				sb.append(ESCPrinter.printBold(padLeft(" Charges : ",10)));
			   				sb.append(ESCPrinter.print(inputMapData.get("chargesDue")== null ? "NIL" :inputMapData.get("chargesDue").toString()));
			   				break;
						case 3:
	 						sb.append(ESCPrinter.printBold(padLeft("IC/ID No : ",10)));
	 						//Nagesh commented and added on 26/08/2019 to display last 5 char in NRIC Start
	 						
							//sb.append(ESCPrinter.print(maskNRIC(inputMapData.get("deliveryToIC").toString())));
	 							
	 						String icnum = (String)(inputMapData.get("deliveryToIC")==null?"":(String)inputMapData.get("deliveryToIC"));
	 						
	 						String lastFiveDigits = "";		//substring containing last 5 characters

	 						if (icnum.length() > 5 && icnum!="") 
	 						{
	 							lastFiveDigits = icnum.substring(icnum.length() - 5);				
	 							sb.append(lastFiveDigits);
	 							//System.out.println("inside length and empty check="+lastFiveDigits);
	 						} 
	 						else 
	 						{
	 							sb.append(icnum);		
	 							//System.out.println("inside else icnum="+icnum);				
	 						}			
	 						//System.out.println("lastFiveDigits="+lastFiveDigits);
	 						
	 						
	 						//Nagesh commented and added on 26/08/2019 to display last 5 char in NRIC End
	 						break;
						case 4: 
/*
							sb.append(ESCPrinter.printBold(padLeft("   IU No : ", 10))); 
							if(inputMapData.containsKey("IUNo")) {
								sb.append(ESCPrinter.print(inputMapData.get("IUNo").toString()));
							}
							break;
*/
						}
					}
				}

		sb.append(ESCPrinter.advanceVertical(0.7f));
			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f));
				sb.append(ESCPrinter.printBold("To  : "));
				sb.append(ESCPrinter.print((inputMapData.get("deliveryTo").toString()==null)?"":padRight(inputMapData.get("deliveryTo").toString(), 25)));	
	   			sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5.4f));
	   			sb.append(ESCPrinter.horizontalTab(6));
	   			sb.append(ESCPrinter.printBold("Signature :_____________________________"));
	   			
		sb.append(ESCPrinter.advanceVertical(1f));
		   		sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f));
		   		sb.append(ESCPrinter.horizontalTab(5));
		   		sb.append(ESCPrinter.printBold("Delivery by   : "));
		   		sb.append(ESCPrinter.print(inputMapData.get("deliveryBy").toString()));

	    sb.append(ESCPrinter.advanceVertical(0.5f));
	       		sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f));
	       		sb.append(ESCPrinter.horizontalTab(5));
		   		sb.append(ESCPrinter.printBold("Completed on  : "));
		   		sb.append(ESCPrinter.print(inputMapData.get("deliveryDateTime").toString().toUpperCase())); 
		   		//manual formfeed
				sb.append(ESCPrinter.advanceVertical(4));
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(4));
				sb.append(ESCPrinter.print("   "));
		   		//sb.append(ESCPrinter.formFeed()); //eject paper
		   		//sb.append(ESCPrinter.resetPrinter());
				
				// Nagesh added to print if more than 6 location in next page start
		   		if(locationInfoList!=null && locationInfoList.size()>6 ) {
		   		sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f));	
		   		sb.append(ESCPrinter.print("AWB: "));	
				sb.append(ESCPrinter.print(inputMapData.get("awbNo")==null?"":inputMapData.get("awbNo").toString().substring(0, 3)+" "+inputMapData.get("awbNo").toString().substring(3, inputMapData.get("awbNo").toString().length())));
				sb.append(ESCPrinter.advanceVertical(1));
		   		for(int i=6;i<locationInfoList.size(); i++) {
		   		sb.append(ESCPrinter.advanceVertical(0.5f)); 
		   				if(i<locationInfoList.size()) {		   		
		   						Map<String, String> locationInfo=locationInfoList.get(i);
		   						sb.append(ESCPrinter.setAbsoluteHorizontalPosition(0.5f)); 
		   						sb.append(ESCPrinter.print(padLeft(locationInfo.get("flightNo").toString(),8) + " / " + locationInfo.get("flightDate").toString().toUpperCase()));    
		   						sb.append(ESCPrinter.print((locationInfo.get("pcs")==null)?"":padLeft(locationInfo.get("pcs"),8)));
		   				 }else {
		   						sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5.0f)); 
		   				 }
		   		} //for
		   		
		   	//manual formfeed
				   	  switch(locationInfoList.size()) {
				       case 7:
					   		sb.append(ESCPrinter.advanceVertical(14));			   								
							sb.append(ESCPrinter.print("   "));		   						   		
			   		   break;
				   	   case 8:
					   		sb.append(ESCPrinter.advanceVertical(13));			   								
							sb.append(ESCPrinter.print("   "));		   						   		
				   		   break;
					   case 9:
							sb.append(ESCPrinter.advanceVertical(12));			   								
							sb.append(ESCPrinter.print("   "));
							break;
					   case 10:
							sb.append(ESCPrinter.advanceVertical(11));			   								
							sb.append(ESCPrinter.print("   "));
							break;	
					   case 11:
							sb.append(ESCPrinter.advanceVertical(10));			   								
							sb.append(ESCPrinter.print("   "));
							break;				   		
				   		case 12:
							sb.append(ESCPrinter.advanceVertical(9));			   								
							sb.append(ESCPrinter.print("   "));
							break;
				   		case 13:
							sb.append(ESCPrinter.advanceVertical(8));			   								
							sb.append(ESCPrinter.print("   "));
							break;
				   		case 14:
							sb.append(ESCPrinter.advanceVertical(7));			   								
							sb.append(ESCPrinter.print("   "));
							break;
				   		case 15:
							sb.append(ESCPrinter.advanceVertical(6));			   								
							sb.append(ESCPrinter.print("   "));
							break;
				   		case 16:
							sb.append(ESCPrinter.advanceVertical(5));			   								
							sb.append(ESCPrinter.print("   "));
							break;
				   		case 17:
							sb.append(ESCPrinter.advanceVertical(4));			   								
							sb.append(ESCPrinter.print("   "));
							break;
				   		case 18:
							sb.append(ESCPrinter.advanceVertical(3));			   								
							sb.append(ESCPrinter.print("   "));
							break;
				   		case 19:
							sb.append(ESCPrinter.advanceVertical(2));			   								
							sb.append(ESCPrinter.print("   "));
							break;
				   		case 20:
							sb.append(ESCPrinter.advanceVertical(1));			   								
							sb.append(ESCPrinter.print("   "));
							break;
				   	  }
				
		   		}//if
				
				// Nagesh added to print if more than 6 location in next page end
				
   return convertStringToBase64Text(sb.toString());
	   }
	   
		@SuppressWarnings({ "unused", "unchecked" })
		private String generateNAWBReport(Map<String, Object> inputMapData) {
/*
			System.out.println("generateNAWBReport map values:");
			inputMapData.forEach((key, value) -> {
			    System.out.println("Key : " + key + " Value : " + value);
			});
*/
		      StringBuilder sb = new StringBuilder();
		     int selectPaper= 0;
		  	 for ( selectPaper = 0; selectPaper < 2; selectPaper++) { //0 = Original Copy(Consignee). 1 = Original Copy (Shipper) 
				sb.append(ESCPrinter.setCharacterSet(ESCPrinter.USA));
				sb.append(ESCPrinter.selectLQPrinting());
			   sb.append(ESCPrinter.select15CPI().toString());
			   sb.append(ESCPrinter.setPageSizeLines(36)); // set page size for tear paper.30.8cm/page.
			   if (selectPaper == 1 ) {
//					// Add spacing for the blue sheet
					sb.append(ESCPrinter.advanceVertical(2.5f));
				}
				// --------------------------  LINE 1
				  //Barcode
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1)); 
				sb.append(ESCPrinter.horizontalTab(4));
			    String printBarCode=inputMapData.get("awbPrefix").toString()+inputMapData.get("awbSuffix").toString();
				//sb.append(ESCPrinter.setBarcode(printBarCode));
				sb.append(ESCPrinter.setBarcode39(printBarCode));
				// --------------------------  LINE 1.5
		        //awbPrefix / originCode / awbSuffix
				sb.append(ESCPrinter.advanceVertical(0.3f));
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.5f)); 
				sb.append(ESCPrinter.print(inputMapData.get("awbPrefix")+ "  " + inputMapData.get("originCode") + "  " +  inputMapData.get("awbSuffix")));
		        //awbCode / awbNo
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(15));
				sb.append(ESCPrinter.print(inputMapData.get("awbPrefix")+ " " + inputMapData.get("awbSuffix")));
				
		        // -------------------------- NEXT LINE 2				
				sb.append(ESCPrinter.advanceVertical(0.9f));
		        //shipperAccNo
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
				sb.append(ESCPrinter.horizontalTab(2));
				sb.append(ESCPrinter.print((String)inputMapData.get("shipperAccNo")));
				//shipperName				 
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.5f));				
				sb.append(ESCPrinter.advanceVertical(0.5f));
				sb.append(ESCPrinter.print((String)inputMapData.get("shipperName")));
				//issuedBy
				sb.append(ESCPrinter.advanceVertical(0.4f));
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(11.8f));
				sb.append(ESCPrinter.print((String)inputMapData.get("issuedBy")));
		        // -------------------------- NEXT LINE 3				
				//shipperAddress
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.5f));
				sb.append(ESCPrinter.print((String)inputMapData.get("shipperAdd")));
				// -------------------------- NEXT LINE 4
				sb.append(ESCPrinter.advanceVertical(0.4f));
				//shipperCountry
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.5f));
				sb.append(ESCPrinter.print((String)inputMapData.get("shipperCountry")));	  
				//shipperPostalCode
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(3));
				sb.append(ESCPrinter.print("  "+(String)inputMapData.get("shipperPostalCode")));
				//shipperContact				
				sb.append(ESCPrinter.advanceVertical(0.4f));
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.5f));
				
				List<String> shipperContact= (List<String>)inputMapData.get("shipperContact");
				if(shipperContact!=null && shipperContact.size()>0 ) {
					for(int i=0;i<shipperContact.size(); i++) {
					    if(i<2 && null!=shipperContact.get(i) && StringUtils.isNotBlank(shipperContact.get(i).trim())) {
						sb.append(ESCPrinter.print(shipperContact.get(i)));
						sb.append(ESCPrinter.print("     "));												
					    }
					}
				}				
				// -------------------------- NEXT LINE 5							
				sb.append(ESCPrinter.advanceVertical(1f));
				//consigneeAccNo
				//sb.append(ESCPrinter.print(padLeft(" ",3)));
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(4.8f));
				sb.append(ESCPrinter.horizontalTab(2));
				sb.append(ESCPrinter.print((String)inputMapData.get("consigneeAccNo")));				
				sb.append(ESCPrinter.advanceVertical(0.4f));
			    //consigneeName
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.5f));
				sb.append(ESCPrinter.print(padRight((String)inputMapData.get("consigneeName"),20)));				
				// -------------------------- NEXT LINE 6
				sb.append(ESCPrinter.advanceVertical(0.4f));
				//consigneeAdd
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.5f));
				sb.append(ESCPrinter.print(padRight((String)inputMapData.get("consigneeAdd"),20)));
				// -------------------------- NEXT LINE 7
				sb.append(ESCPrinter.advanceVertical(0.4f));
				//consigneeCountry
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.5f));
				sb.append(ESCPrinter.print(padRight((String)inputMapData.get("consigneeCountry"),20)));	
				//consigneePostalCode
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(3));
				sb.append(ESCPrinter.print("  "+(String)inputMapData.get("consigneePostalCode")));
				//consigneeContact								
				sb.append(ESCPrinter.advanceVertical(0.4f));
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.5f));				
				
				List<String> consigneeContact= (List<String>)inputMapData.get("consigneeContact");
				if(consigneeContact!=null && consigneeContact.size()>0 ) {
					for(int i=0;i<consigneeContact.size(); i++) {
						if(i<2 && null!=consigneeContact.get(i) && StringUtils.isNotBlank(consigneeContact.get(i).trim())){
							sb.append(ESCPrinter.print(consigneeContact.get(i)));
							sb.append(ESCPrinter.print("     "));							
					    }
					}
				}				
				
//				// -------------------------- NEXT LINE 8			
				sb.append(ESCPrinter.advanceVertical(0.8f));
				// Issuing Carrier's agentName
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.4f));
				sb.append(ESCPrinter.print((String)inputMapData.get("issueCarrierAgentName")));				
//				// -------------------------- NEXT LINE 9
				sb.append(ESCPrinter.advanceVertical(0.4f));
				// Issuing Carrier's agentCity
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.4f));
				sb.append(ESCPrinter.print(padRight((String)inputMapData.get("issueCarrierAgentCity"),56)));
				
				//ingInfo
				List<String> InfoList= (List<String>)inputMapData.get("accountInfoList");
				if(null!=InfoList && InfoList.size()>0) {
					for(int i=0; i<=3; i++) { // Print 4  info only
						switch(i) {
							case 0:
								if(null!=InfoList.get(i) && !InfoList.get(i).isEmpty()) {
									sb.append(ESCPrinter.print(InfoList.get(i)));
								}
							break;
							case 1:
								sb.append(ESCPrinter.advanceVertical(0.4f));
								sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5.4f)); //5.4f end of page widht. Increase this value printed in next line, page will be messy.
								sb.append(ESCPrinter.horizontalTab(5));
								//if(null!=InfoList.get(i) && !InfoList.get(i).isEmpty()) {
								if(InfoList.size()>1 && null!=InfoList.get(i) && !InfoList.get(i).isEmpty()) {
									sb.append(ESCPrinter.print(InfoList.get(i)));
								}
							break;
							case 2:
								sb.append(ESCPrinter.advanceVertical(0.4f));
								sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5.4f));
								sb.append(ESCPrinter.horizontalTab(5));
								if(InfoList.size()>2 &&null!=InfoList.get(i) && !InfoList.get(i).isEmpty()) {
									sb.append(ESCPrinter.print(InfoList.get(i)));
								}
							break;
							case 3:
								sb.append(ESCPrinter.advanceVertical(0.4f));
								sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5.4f));
								sb.append(ESCPrinter.horizontalTab(5));
								if(InfoList.size()>3 &&null!=InfoList.get(i) && !InfoList.get(i).isEmpty()) {
									sb.append(ESCPrinter.print(InfoList.get(i)));
								}
							break;
						}
					}
				}else {
					sb.append(ESCPrinter.advanceVertical(1.2f));
					
				}
					

//				// -------------------------- NEXT LINE 10
				sb.append(ESCPrinter.advanceVertical(0.2f));				
				//Agent's IATA Code
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.8f));
				sb.append(ESCPrinter.print((padRight((String)inputMapData.get("agentIATACode"),15))));
				// No 
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
				sb.append(ESCPrinter.horizontalTab(2));
				sb.append(ESCPrinter.print((String)inputMapData.get("No")));
				// -------------------------- NEXT LINE 11
				sb.append(ESCPrinter.advanceVertical(0.8f));
				//Depart Airport
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.4f));				
				//sb.append(ESCPrinter.print((String)inputMapData.get("departApt")));
				sb.append(ESCPrinter.print((String)inputMapData.get("executedOnPlace")));
				//Reference Number
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(10.7f));
				sb.append(ESCPrinter.print((String)inputMapData.get("refNo")));
				 // -------------------------- NEXT LINE 12
				sb.append(ESCPrinter.advanceVertical(0.9f));
				        //TC
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.6f));
				if(inputMapData.get("to")!=null && !inputMapData.get("to").toString().contains("null") && !inputMapData.get("to").toString().trim().isEmpty()){
				    sb.append(inputMapData.get("to"));					
					}else {						
						sb.append("   ");
					}				
								
		        //byFirstCarrier
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2.5f));
				
				if(inputMapData.get("byFirstCarrier")!=null && !inputMapData.get("byFirstCarrier").toString().contains("null") && !inputMapData.get("byFirstCarrier").toString().trim().isEmpty()){
				    sb.append(inputMapData.get("byFirstCarrier"));					
					}else {						
						sb.append("  ");
					}
								
				        //routeTo
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(7));
				sb.append(ESCPrinter.horizontalTab(3));				 
				sb.append(ESCPrinter.print(" "));
				if(inputMapData.get("routeAndDestTo1")!=null && !inputMapData.get("routeAndDestTo1").toString().contains("null") && !inputMapData.get("routeAndDestTo1").toString().trim().isEmpty()){
				    sb.append(inputMapData.get("routeAndDestTo1"));					
					}else {						
						sb.append("   ");
					}				
				        //routeBy
				sb.append(ESCPrinter.print("   "));
				
				if(inputMapData.get("routeAndDestBy1")!=null && !inputMapData.get("routeAndDestBy1").toString().contains("null") && !inputMapData.get("routeAndDestBy1").toString().trim().isEmpty()){
				    sb.append(inputMapData.get("routeAndDestBy1"));					
					}else {						
						sb.append("  ");
					}				
									
				        //destTo
				sb.append(ESCPrinter.print("   "));
				if(inputMapData.get("routeAndDestTo2")!=null && !inputMapData.get("routeAndDestTo2").toString().contains("null") && !inputMapData.get("routeAndDestTo2").toString().trim().isEmpty()){
				    sb.append(inputMapData.get("routeAndDestTo2"));					
					}else {						
						sb.append("   ");
					}							
				        //destBy
				sb.append(ESCPrinter.print("   "));
				
				if(inputMapData.get("routeAndDestBy2")!=null && !inputMapData.get("routeAndDestBy2").toString().contains("null") && !inputMapData.get("routeAndDestBy2").toString().trim().isEmpty()){
				    sb.append(inputMapData.get("routeAndDestBy2"));					
					}else {						
						sb.append("  ");
					}
				        //currency
				sb.append(ESCPrinter.print("   "));
				if(inputMapData.get("currencyCode")!=null && !inputMapData.get("currencyCode").toString().contains("null") && !inputMapData.get("currencyCode").toString().trim().isEmpty()){
				    sb.append(inputMapData.get("currencyCode"));					
					}else {						
						sb.append("   ");
					}
				
				       //cdgs
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(11.3f));
				sb.append(ESCPrinter.print(" "+(String)inputMapData.get("chgsCode")));      
				        //wtppd
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(12f));
				sb.append(ESCPrinter.print((String)inputMapData.get("wtValPPD")));
				        //wtcoll
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(12.5f));
				sb.append(ESCPrinter.print((String)inputMapData.get("wtValCOLL")));
				        //otherppd
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(13f));
				sb.append(ESCPrinter.print((String)inputMapData.get("othersValPPD")));
				        //othercoll
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(13.5f));
				sb.append(ESCPrinter.print((String)inputMapData.get("othersValCOLL")));    
				        //carriage
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(15f));
				sb.append(ESCPrinter.print((String)inputMapData.get("declareValueCarriage")));         
				        //customs
				//sb.append(ESCPrinter.setAbsoluteHorizontalPosition(17.4f));
				sb.append(ESCPrinter.horizontalTab(2));
				sb.append(ESCPrinter.print((String)inputMapData.get("declareValueCustoms")));  
				        // -------------------------- NEXT LINE 13
				sb.append(ESCPrinter.advanceVertical(0.8f));
				        //destinationAirport
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(3f));
				sb.append(ESCPrinter.print((String)inputMapData.get("destApt")));	
				        //requestFlight				
				//sb.append(ESCPrinter.setAbsoluteHorizontalPosition(9));
				//requestDate				
				if(null!=inputMapData.get("reqFlight") && null!=inputMapData.get("reqFlightDate")) {
					List<String> reqFlight= (List<String>)inputMapData.get("reqFlight");
					List<String> reqFlightDate= (List<String>)inputMapData.get("reqFlightDate");
					
					if(reqFlight.size()==1 && reqFlightDate.size()==1 ) {
						sb.append(ESCPrinter.setAbsoluteHorizontalPosition(7));				
					    sb.append(ESCPrinter.horizontalTab(3));						
					    sb.append(ESCPrinter.print(reqFlight.get(0)));
					    sb.append(ESCPrinter.setAbsoluteHorizontalPosition(10.5f));						
					    sb.append(ESCPrinter.print("   "+reqFlightDate.get(0).toString().toUpperCase()));						
					}
					if(reqFlight.size()==2 && reqFlightDate.size()==2) {
						sb.append(ESCPrinter.horizontalTab(2));
						sb.append(ESCPrinter.setAbsoluteHorizontalPosition(9f));
						sb.append(ESCPrinter.print("   "+reqFlight.get(0)+"/"+  reqFlightDate.get(0).toString().substring(0, 5) + " "+reqFlight.get(1)+"/"+ reqFlightDate.get(1).toString().substring(0, 5) ));
						
					}					
				}				
				
				//insuranceAmount
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(11f));
				sb.append(ESCPrinter.print((String)inputMapData.get("amountOfInsu")));	   
				
				        // -------------------------- NEXT LINE 14
				sb.append(ESCPrinter.advanceVertical(0.7f));
				        //handlingCode
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.5f));								
				sb.append(inputMapData.get("handlingCode")==null?"":inputMapData.get("handlingCode"));
				        //handlingInfo				
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.5f));				
				
				if(null!=inputMapData.get("handlingInfo")) {
					List<String> handlingInfo= (List<String>)inputMapData.get("handlingInfo");
					if(null!=handlingInfo && handlingInfo.size()>0) {
						for(int i=0;handlingInfo.size()>i;i++) {		
							if(i<3)
							{								
								sb.append(ESCPrinter.advanceVertical(0.3f));
								sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.5f));							
								sb.append(ESCPrinter.print(padRight(handlingInfo.get(i),65)));								
							}							
						}					
						
						if(handlingInfo.size()==1)
						{	
							sb.append(ESCPrinter.advanceVertical(0.6f));
						}
						if(handlingInfo.size()==2)
						{									
							sb.append(ESCPrinter.advanceVertical(0.3f));
						}
					}
				}
				        //handlingDetails
   			      sb.append(ESCPrinter.horizontalTab(4));				
				if(null!=inputMapData.get("handlingSci")) {
					sb.append(ESCPrinter.print(inputMapData.get("handlingSci").toString()));
				}
				// -------------------------- NEXT LINE 15
				//----------------FOR LOOP HERE based on NAWB List----------------
			      /**
			       * Print Commodity Nature of Goods info from the List
			       * **/
				float commodityTotalVerticalLinePosition=4.7f; //Set the vertical line position dynamically based on CommodityChargesList lines
				sb.append(ESCPrinter.advanceVertical(1.5f));
				//sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.5f));
				List<CommodityChargesList> ccList = (List<CommodityChargesList>) inputMapData.get("commodityChargesList");
				if(null!=ccList && 0<ccList.size()) {
					for (int i = 0; i < ccList.size(); i++) {
						sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.5f));
						Map<String, CommodityChargesList> mapObject = (Map<String, CommodityChargesList>) ccList.get(i);
						sb.append(ESCPrinter.print(
								padLeft(String.valueOf(mapObject.get("numberOfPieces")),2)+
								padLeft(String.valueOf(mapObject.get("grossWeight")),9)+" "+
								padLeft(String.valueOf(mapObject.get("weightUnitCode")),4)+
								padLeft(String.valueOf(mapObject.get("rateClassCode")),3)+" "+
								padLeft(String.valueOf(mapObject.get("commodityItemNo")),5)+
								padLeft(String.valueOf(mapObject.get("chargeableWeight")),13)+
								padLeft(String.valueOf(mapObject.get("rateChargeAmount")),13)+
								padLeft(String.valueOf(mapObject.get("totalChargeAmount")),17)));
						if(null!=mapObject.get("natureOfGoodsList")) {
							List<String> strList = (List<String>) mapObject.get("natureOfGoodsList");
							if(null!=strList && 0<strList.size()) {
								for(int line=0;strList.size()>line;line++) {
									sb.append(ESCPrinter.setAbsoluteHorizontalPosition(14.5f));						   
									sb.append(ESCPrinter.print("  "+(String)strList.get(line)));
									sb.append(ESCPrinter.advanceVertical(0.5f));
									commodityTotalVerticalLinePosition = commodityTotalVerticalLinePosition - 0.3f;
								} // End of Commodity Nature of Goods List
/*
								if(strList.size()==1) sb.append(ESCPrinter.advanceVertical(2.5f));
								if(strList.size()==2) sb.append(ESCPrinter.advanceVertical(2f));
								if(strList.size()==3) sb.append(ESCPrinter.advanceVertical(1.5f));
								if(strList.size()==4) sb.append(ESCPrinter.advanceVertical(1f));
								if(strList.size()==5) sb.append(ESCPrinter.advanceVertical(0.5f));
								if(strList.size()==6) sb.append(ESCPrinter.advanceVertical(2));
								if(strList.size()==7) sb.append(ESCPrinter.advanceVertical(1));
								if(strList.size()==8) sb.append(ESCPrinter.advanceVertical(0.5f));
*/
							}
						}
						sb.append(ESCPrinter.advanceVertical(0.2f)); // End of Commodity List
						commodityTotalVerticalLinePosition = commodityTotalVerticalLinePosition - 0.3f;
					}
					//System.out.println("Commodity List size: "+ccList.size());
					//System.out.println("Commodity commodityVerticalLine: "+commodityTotalVerticalLinePosition);
				}
				sb.append(ESCPrinter.advanceVertical(commodityTotalVerticalLinePosition)); // Commodity total row

				// -------------------------- NEXT LINE 16
				//sb.append(ESCPrinter.advanceVertical(0.01f));
				        //totalNoPieces
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.5f));
				sb.append(ESCPrinter.print(padLeft((String)inputMapData.get("numberOfPiecesTotal"),2)));				
				        //totalGrossWeight
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(1.7f));
				sb.append(ESCPrinter.print(padLeft((String)inputMapData.get("grossWeightTotal"),10)));
				        //total
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(10.7f));
				sb.append(ESCPrinter.horizontalTab(5));
				sb.append(ESCPrinter.print(padLeft((String)inputMapData.get("totalChargeAmountTotal"),20)));
				        // -------------------------- NEXT LINE 17				
				sb.append(ESCPrinter.advanceVertical(0.9f));
				        //prepaidWeightCharge
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2));
				sb.append(ESCPrinter.print(padLeft(inputMapData.get("weightChargePPD").toString(),15)));	
				        //collectWeightCharge
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
				sb.append(ESCPrinter.horizontalTab(1));
				sb.append(ESCPrinter.print(padLeft((String)inputMapData.get("weightChargeCOL"),15)));
				//otherChargesDetails
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(9.1f));
				List<String> otherCharges= (List<String>)inputMapData.get("otherChargesList");
				//System.out.println("otherChargesList toString"+otherCharges.toString());
				//if(otherCharges!=null && otherCharges.size()>3 ) {"
				if(null!=otherCharges &&  otherCharges.size()>0 && null!=otherCharges.get(0) && !otherCharges.get(0).isEmpty() && !otherCharges.get(0).contains("null")){
				    sb.append(ESCPrinter.print(padLeft(otherCharges.get(0),13)));
					}
				if(null!=otherCharges && otherCharges.size()>1 && null!=otherCharges.get(1) && !otherCharges.get(1).isEmpty() && !otherCharges.get(1).contains("null")){
				 		sb.append(ESCPrinter.space(3));
						sb.append(ESCPrinter.print(padLeft(otherCharges.get(1),13)));
					}
					if(null!=otherCharges  && otherCharges.size()>2 && null!=otherCharges.get(2) && !otherCharges.get(2).isEmpty() && !otherCharges.get(2).contains("null")){
						sb.append(ESCPrinter.space(3));
						sb.append(ESCPrinter.print(padLeft(otherCharges.get(2),13)));
					}
					if(null!=otherCharges && otherCharges.size()>3 && null!=otherCharges.get(3) && !otherCharges.get(3).isEmpty() && !otherCharges.get(3).contains("null")){
						sb.append(ESCPrinter.space(3));
						sb.append(ESCPrinter.print(padLeft(otherCharges.get(3),13)));
					}                    
					sb.append(ESCPrinter.advanceVertical(0.9f));
					        //prepaid valuation Charge
					sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2));
					sb.append(ESCPrinter.print(padLeft(inputMapData.get("vlauenChargePPD").toString(),15)));	
					        //collect valuation Charge
					sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
					sb.append(ESCPrinter.horizontalTab(1));
					sb.append(ESCPrinter.print(padLeft((String)inputMapData.get("vlauenChargeCOL"),15)));
					sb.append(ESCPrinter.setAbsoluteHorizontalPosition(9.1f));

					if(null!=otherCharges && otherCharges.size()>4 && null!=otherCharges.get(4) && !otherCharges.get(4).isEmpty() && !otherCharges.get(4).contains("null")){
						sb.append(ESCPrinter.space(3));
						sb.append(ESCPrinter.print(padLeft(otherCharges.get(4),13)));
					}
					if(null!=otherCharges && otherCharges.size()>5 && null!=otherCharges.get(5) && !otherCharges.get(5).isEmpty() && !otherCharges.get(5).contains("null")){
						sb.append(ESCPrinter.space(3));
						sb.append(ESCPrinter.print(padLeft(otherCharges.get(5),13)));
					}
					if(null!=otherCharges && otherCharges.size()>6 && null!=otherCharges.get(6) && !otherCharges.get(6).isEmpty() && !otherCharges.get(6).contains("null")){
						sb.append(ESCPrinter.space(3));
						sb.append(ESCPrinter.print(padLeft(otherCharges.get(6),13)));
					}
					if(null!=otherCharges && otherCharges.size()>7 && null!=otherCharges.get(7) && !otherCharges.get(7).isEmpty() && !otherCharges.get(7).contains("null")){
						sb.append(ESCPrinter.space(3));
						sb.append(ESCPrinter.print(padLeft(otherCharges.get(7),13)));
					}
					sb.append(ESCPrinter.advanceVertical(0.8f));
					        //prepaid valuation Charge
					sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2));
					sb.append(ESCPrinter.print(padLeft(inputMapData.get("taxPPD").toString(),15)));	
					        //collect valuation Charge
					sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
					sb.append(ESCPrinter.horizontalTab(1));
					sb.append(ESCPrinter.print(padLeft((String)inputMapData.get("taxCOL"),15)));
					
					if(null!=otherCharges && otherCharges.size()>8 && null!=otherCharges.get(8) && !otherCharges.get(8).isEmpty() && !otherCharges.get(8).contains("null")){
//						sb.append(ESCPrinter.setAbsoluteHorizontalPosition(9.1f));
						sb.append(ESCPrinter.space(3));
						sb.append(ESCPrinter.print(padLeft(otherCharges.get(8),13)));
					}
					if(null!=otherCharges && otherCharges.size()>9 && null!=otherCharges.get(9) && !otherCharges.get(9).isEmpty() && !otherCharges.get(9).contains("null")){
						sb.append(ESCPrinter.space(3));
						sb.append(ESCPrinter.print(padLeft(otherCharges.get(9),13)));
					}
					if(null!=otherCharges && otherCharges.size()>10 && null!=otherCharges.get(10) && !otherCharges.get(10).isEmpty() && !otherCharges.get(10).contains("null")){
						sb.append(ESCPrinter.space(3));
						sb.append(ESCPrinter.print(padLeft(otherCharges.get(10),13)));
					}
					if(null!=otherCharges && otherCharges.size()>11 && null!=otherCharges.get(11) && !otherCharges.get(11).isEmpty() && !otherCharges.get(11).contains("null")){
						sb.append(ESCPrinter.space(3));
						sb.append(ESCPrinter.print(padLeft(otherCharges.get(11),13)));
					}               	
				sb.append(ESCPrinter.advanceVertical(0.9f));			   
        // -------------------------- NEXT LINE 18
		        //prepaid total other charges due agent
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2));
				sb.append(ESCPrinter.print(padLeft((String)inputMapData.get("totalOtherChargesDueAgentPPD"),15)));	
		        //collect total other charges due agent
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
				sb.append(ESCPrinter.print(padLeft((String)inputMapData.get("totalOtherChargesDueAgentCOL"),15)));
				sb.append(ESCPrinter.advanceVertical(0.8f));
		        //prepaid total other charges due carrier
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2));
				sb.append(ESCPrinter.print(padLeft((String)inputMapData.get("totalOtherChargesDueCarrierPPD"),15)));	
		        //prepaid total other charges due carrier
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
				sb.append(ESCPrinter.print(padLeft((String)inputMapData.get("totalOtherChargesDueCarrierCOL"),15)));				
				        // -------------------------- NEXT LINE 21
				sb.append(ESCPrinter.advanceVertical(1.8f));
				        //prepaidOtherDueAgent
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2));
				sb.append(ESCPrinter.print(padLeft((String)inputMapData.get("totalPrepaid"),15)));	
				//collectOtherDueAgent
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
				sb.append(ESCPrinter.print(padLeft((String)inputMapData.get("totalCollect"),15)));	
				// -------------------------- NEXT LINE 25
				sb.append(ESCPrinter.advanceVertical(0.5f));
				//executedDate
				//sb.append(ESCPrinter.setAbsoluteHorizontalPosition(10f));
				sb.append(ESCPrinter.horizontalTab(2));
				sb.append(ESCPrinter.print(inputMapData.get("executedOnDate").toString().toUpperCase()));	
				//place
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(12f));
				sb.append(ESCPrinter.print(inputMapData.get("executedOnPlace").toString()));				
				//signofIssueCarrier				
				sb.append((inputMapData.get("signofIssueCarrier")==null?"":" "+padLeft((String)inputMapData.get("signofIssueCarrier"),35)));
				// -------------------------- NEXT LINE 22
				sb.append(ESCPrinter.advanceVertical(0.3f));
				//prepaidOtherDueCarrier
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(2));
				sb.append(ESCPrinter.print(padLeft(inputMapData.get("currencyConversionRate").toString(),15)));	
				//collectOtherDueAgent
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
				sb.append(ESCPrinter.print(padLeft(inputMapData.get("chargesInDestCurrency").toString(),15)));
				// -------------------------- NEXT LINE 27
				sb.append(ESCPrinter.advanceVertical(0.8f));
				//chargesDest
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(5));
				sb.append(ESCPrinter.print(padLeft(inputMapData.get("chargesAtDest").toString(),15)));
				//totalCollectCharges
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(9));
				sb.append(ESCPrinter.print(padLeft(inputMapData.get("totalCollectCharges").toString(),15)));
				sb.append(ESCPrinter.setAbsoluteHorizontalPosition(15));
				sb.append(ESCPrinter.print(inputMapData.get("awbPrefix")+ " " + inputMapData.get("awbSuffix")));
				sb.append(ESCPrinter.formFeed()); //eject paper
		} // end select paper for loop
		  	  
		      return convertStringToBase64Text(sb.toString());
		   }

		
	/**
	 * Connect the UAT room printers thru their IP and print directly for testing purposes.
	 * Test methods are listed below for respective print functions.
	 * Its just for DEV env only, comment the main method when commit to SVN, UAT or PROD.
	 **/

//		   public static void main(String[] argc) {
//			   int i=0;
//			   while(i<=2) {
//				   testPOPrint(i);
//				   i++;
//			   }
//			testPOPrint();
//			testEPOPrint();
//			testDOPrint();
//		 	testNAWBPrint();
//		 	testRampHandOverPrint();
//		 	testULDTagPrint();
//		    testOffloadFormPrint();
//			testTRMPrint();
//		    testBarCodePrint(); /// barcode
//          testLaserPrint(); //Laser
//  }

	 public static void printDirect(byte[] data) {
		String hostName = "";
		//String hostName = "10.74.84.207"; // PO/DO/EPO Print
		//String hostName = "10.10.138.209"; // PROD - L3_11136V DO print
		//String hostName = "10.10.138.210"; //PROD
		//String hostName = "10.10.150.212"; //PROD
		//String hostName = "10.74.84.203"; // NAWB Print
		//String hostName = "10.74.84.204"; // Ramp release Handover Print
		//String hostName = "10.74.84.201"; // ULD TAG Print
		//String hostName = "10.74.84.202"; // Barcode Print
        //String hostName = "10.74.84.209"; // Laser Print 
		int portNumber = Integer.parseInt("9100");
		try {
		    Socket printsocket = new Socket(hostName, portNumber);
				OutputStream outstream=printsocket.getOutputStream();
				outstream.write(data);
				outstream.flush();
				printsocket.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	 private static void testBarCodePrint() {
			Map<String, Object > data= new HashMap<String, Object>();
			data.put("awbNumBarCode", "58016137026");
			data.put("awbNumTextCode", "58016137026");		
			PrinterServiceImpl p = new PrinterServiceImpl();		
			String outputCamelCase=p.generateAWBLabelReport(data);
			System.out.println(new String(Base64.getDecoder().decode(outputCamelCase)));	
			//p.printDirect(Base64.getDecoder().decode(outputCamelCase));					
		}


		public static void testNAWBPrint() {
			   Map<String, Object > data= new HashMap<String, Object>();
				data.put("awbPrefix", "618");
				data.put("awbSuffix", "21422133");
				data.put("originCode","SIN");
				data.put("shipperAccNo","1111111111");
				data.put("shipperName","DHL GLOBAL FWDG SIN");
				data.put("shipperAdd","20 FLOOR FINANCE SQUARE 333 JIU");
				data.put("shipperCountry","SHANGAI");
				data.put("shipperPostalCode","2000001");
				
				List<String> shipperContact= new ArrayList<String>();
				shipperContact.add("TEL 123456789012");
				shipperContact.add("FAX 123456789013");
				//shipperContact.add("TX 123456789014");
			    data.put("shipperContact",shipperContact);
							
				data.put("issuedBy", "SINGAPORE AIRLINES CARGO PTE LTD");
				data.put("consigneeAccNo", "2222222222");
				data.put("consigneeName","DHL GLOBAL");
				data.put("consigneeAdd","CASTELLANZA VA IT VAT");
				data.put("consigneeCountry","MANILA");
				data.put("consigneePostalCode","21053");
				
				List<String> consigneeContact= new ArrayList<String>();
				consigneeContact.add("TEL 123456789012");
				consigneeContact.add("FAX 123456789013");
				//consigneeContact.add("TX 123456789014");
			    data.put("consigneeContact",consigneeContact);
			
				data.put("issueCarrierAgentName","CTS");
				data.put("issueCarrierAgentCity","SHANGHAI");
				
				List<String> accountInfoList= new ArrayList<String>();
					accountInfoList.add("GENERAL INFORMATION");
					accountInfoList.add("GOVERNMENT BILL OF LADINGQ");
					//accountInfoList.add("MODE OF SETTLEMENT");
				data.put("accountInfoList",accountInfoList);
				
				data.put("agentIATACode", "3238906");
				data.put("No","5555555555");
				data.put("departApt","SINGAPORE");
				data.put("refNo","");
				data.put("to","HKG");
				data.put("byFirstCarrier","SQ");
				data.put("routeAndDestTo1","BLR");
				data.put("routeAndDestBy1","SQ");
				data.put("routeAndDestTo2","DLR");
				data.put("routeAndDestBy2","SQ");								
				data.put("currencyCode","CNY");		
				data.put("chgsCode", "PP");
				data.put("wtValPPD", "X");
				data.put("wtValCOLL","X");
				data.put("othersValPPD", "");
				data.put("othersValCOLL","");
				data.put("declareValueCarriage","NVD");
				data.put("declareValueCustoms","NCV");
				data.put("destApt","HKG");		
				List<String> reqFlight= new ArrayList<String>();
				reqFlight.add("SQ0002");				
				//reqFlight.add("SQ0003");				
			    data.put("reqFlight",reqFlight);	
				
			    List<String> reqFlightDate= new ArrayList<String>();
			    reqFlightDate.add("07MAY2019");				
			    //reqFlightDate.add("08MAY2019");				
			    data.put("reqFlightDate",reqFlightDate);
			    data.put("amountOfInsu","XXX");
				data.put("handlingCode","EAP");
				
				List<String> handlingInfoList= new ArrayList<String>();
				handlingInfoList.add("PLEASE NOTIFY CONSIGNEE IMMEDIATELLY UPON ARRIVAL");				
				handlingInfoList.add("DOCUMENT ATTACHED 1");
				handlingInfoList.add("DOCUMENT ATTACHED 2");
			    data.put("handlingInfo",handlingInfoList);	
				data.put("handlingSci", "SCI");			

				List<CommodityChargesList> commodityChargesList = new ArrayList<CommodityChargesList>();
		      	CommodityChargesList ccl = new CommodityChargesList();
		      	List<String> natureOfGoodsList = new ArrayList<String>();
	    		  ccl.setRateLineNumber("1");
	    		  ccl.setNumberOfPieces("12");
	    		  ccl.setWeightUnitCode("K");
	    		  ccl.setGrossWeight("23");
	    		  ccl.setRateClassCode("U");
	    		  ccl.setCommodityItemNo("1");
	    		  ccl.setChargeableWeight("34");
	    		  ccl.setRateChargeAmount("23.10");
	    		  ccl.setTotalChargeAmount("45.25");
	    		  
	    		  natureOfGoodsList.add("CONSOLE");
	    		  natureOfGoodsList.add("Nature of goods desc");
	    		  
	    		  ccl.setNatureOfGoods(natureOfGoodsList);
	    		  commodityChargesList.add(ccl);

	    		  data.put("commodityChargesList", commodityChargesList);
	    		  data.put("numberOfPiecesTotal", "12");
	    		  data.put("grossWeightTotal", "24.23");
	    		  data.put("totalChargeAmountTotal", "345.64");

				data.put("prepaidCollect", "");						

				List<String> otherChargesList= new ArrayList<String>();
				otherChargesList.add("AC 50.00");
				otherChargesList.add("BC 1,080.00");
				otherChargesList.add("CC 252.00");
				otherChargesList.add("DC 150.00");
				otherChargesList.add("CC 21,080.00");
				
				data.put("otherChargesList",otherChargesList);
				data.put("weightChargePPD","1111.11");
				data.put("weightChargeCOL","2222.22");
				data.put("vlauenChargePPD","3333.33");
				data.put("vlauenChargeCOL","4444.44");
				data.put("taxPPD","5555.55");
				data.put("taxCOL","6666.66");
				data.put("totalOtherChargesDueAgentPPD","7777.77");
				data.put("totalOtherChargesDueAgentCOL","8888.88");
				data.put("totalOtherChargesDueCarrierPPD","9999.99");
				data.put("totalOtherChargesDueCarrierCOL","1111.11");
				data.put("totalPrepaid", "2222.22");	
				data.put("totalCollect", "3333.33");	
				data.put("currencyConversionRate", "10.5");	
				data.put("chargesInDestCurrency", "4444.44");	
				data.put("executedOnDate", "29SEP2018");	
				data.put("executedOnPlace", "SINGAPORE");	
				data.put("signofIssueCarrier", "TEKURI NAGESH TEKURI NAGESH");
				data.put("chargesAtDest", "5555.55");	
				data.put("totalCollectCharges", "6666.66");

				PrinterServiceImpl p = new PrinterServiceImpl();		
				String outputCamelCase=p.generateNAWBReport(data);
				System.out.println(new String(Base64.getDecoder().decode(outputCamelCase)));	
				//p.printDirect(Base64.getDecoder().decode(outputCamelCase));	
		   }
	   
	
	
			private static void testRampHandOverPrint() {
	 			  /**
				    * Structure of data
				    * -----------------
				    * Map<String, Object>
				    * driverID
				    * flightNo
				    * flightDate
				    * std
				    * etd
				    * bay
				    * uldTrolleyNoList List<String>
					* satsUserID
					* releaseDateTime
				    */
				Map<String, Object > data= new HashMap<String, Object>();
					data.put("driverID","MACK");
					data.put("flightNo","CK0288");
					data.put("flightDate","05Dec18");
					data.put("std","14:55");
					data.put("etd","15:55");
					data.put("bay","502");
		
				List<String> uldTrolleyNoList = new ArrayList<String>();
					uldTrolleyNoList.add("PGA22007MU");
					uldTrolleyNoList.add("PGA22008MU");
					uldTrolleyNoList.add("PGA22009MU");
					uldTrolleyNoList.add("PGA22010MU");
				data.put("uldTrolleyNoList", uldTrolleyNoList);
				data.put("satsUserID","COSYS");
				data.put("releaseDateTime","05Dec18 10:20");
				PrinterServiceImpl p = new PrinterServiceImpl();
				String outputCamelCase=p.generateHandOverFormReport(data);
				System.out.println(new String(Base64.getDecoder().decode(outputCamelCase)));	
				//p.printDirect(Base64.getDecoder().decode(outputCamelCase));				
			}	
			
private static void testLaserPrint() {
	 			  /**
				    * Structure of data
				    * -----------------
				    * Map<String, Object>
				    * driverID
				    * flightNo
				    * flightDate
				    * std
				    * etd
				    * bay
				    * uldTrolleyNoList List<String>
					* satsUserID
					* releaseDateTime
				    */
				Map<String, Object > data= new HashMap<String, Object>();
					data.put("satsUserID","COSYS");
					data.put("terminalNo","T6");					
					data.put("agentCode","SPC");
					data.put("staffName","NAGESH");
					data.put("staffID","99999");
					data.put("serialNo","12345679");					
		
					List<Map<String,Object>> pouldList = new ArrayList<Map<String,Object>>();
					 Map<String,Object> trm = new HashMap<String,Object>();
					 trm.put("poNo", "PD0448");
					 trm.put("uldNo", "PCM1324351");
					 trm.put("hanoverdatetime", "03DEC19 10:08");					 
					 pouldList.add(trm);
					 
					 trm = new HashMap<String,Object>();
					 trm.put("poNo", "PD0558");
					 trm.put("uldNo", "PCM1324352");
					 trm.put("hanoverdatetime", "03DEC19 10:08");
					 pouldList.add(trm);
					 
					 trm = new HashMap<String,Object>();
					 trm.put("poNo", "PD0668");
					 trm.put("uldNo", "PCM1324353");
					 trm.put("hanoverdatetime", "22OCT19 12:08");
					 pouldList.add(trm);

                     data.put("poUldDateList", pouldList);
				
				
				PrinterServiceImpl p = new PrinterServiceImpl();
				String outputCamelCase=p.generateLaserReport(data);
				//String outputCamelCase=p.generateRePrintHandOverFormReport(data);
				
				//System.out.println(new String(Base64.getDecoder().decode(outputCamelCase)));	
				//p.printDirect(Base64.getDecoder().decode(outputCamelCase));				
			}
			//private static void testPOPrint(int noOfPrint) {
			private static void testPOPrint() {
				  /**
				    * Structure of data
				    * -----------------
				    *generateTransferManifestReport Map<String, Object>
				    * 
				    * awbNo
				    * dateTime
				    * poNo
				    * uldDetails
				    * truckDock
				    * consigneeDetails
				    * personCollect
				    * icNo
				    * apptAgent
				    * nog
				    * shc List <String>
				    * locationInfo List<Map<String,Object>>
				    * 	flightNo
				    * 	flightDate
				    * 	pcs
				    * 	loc
				    * isPrintFromSSK boolean (from SSK)
				    * agentUENNo (from SSK)
				    */
				
				Map<String, Object > data= new HashMap<String, Object>();

				data.put("dateTime", "08 NOV 2018 1451");
				//data.put("isPrintFromSSK", true);
				//data.put("agentUENNo","197700524Z");
				data.put("poNo","P2018102815136");
				//data.put("consigneeDetails","SCHENKER SINGAPORE PTE LTD");
				//data.put("consigneeDetails","SCHENKER SINGAPORE PTE LTD SCHENKER SINGAPORE PTE LTD SCHENKER SINGAPO");				
				//data.put("consigneeDetails","SCHENKER SINGAPORE PTE LTD"); //ok
				//data.put("consigneeDetails","CONSINEE SINGAPORE PTE LTD CONSINEE SINGAPORE PTE LTD CONSINEE SINGAPO"); //ok				
				data.put("personCollect","POKKEY");
				data.put("icNo","G6080037K"); // test if full nric
				//data.put("icNo","0037K"); // test if only 5 char
				//data.put("icNo","null"); // test if null
				//data.put("icNo",""); // test if empty
				//data.put("apptAgent","SCH");							
				//data.put("apptAgent","SCHENKER SINGAPORE PTE LTD"); //ok				
				/* CASE 1: WITH Existing values -DONE */
				//data.put("consigneeDetails","SCHENKER SINGAPORE PTE LTD");
				//data.put("apptAgent","SCH");
				/* Case 2: apptAgent < 46 and consigneeDetails >25 -DONE */
				//data.put("apptAgent","SCHENKER SINGAPORE PTE LTD");	
				//data.put("consigneeDetails","SCHENKER SINGAPORE PTELTD"); 
				
				/* Case 3: apptAgent <46 and consigneeDetails>25 AND <50 */
				//data.put("apptAgent","SCHENKER SINGAPORE PTE LTD");	
				//data.put("consigneeDetails","SCHENKER SINGAPORE PTELTDCHENKER SINGAPORE PTELTD"); 
				
				/* Case 4: apptAgent <46 and consigneeDetails>25 AND <50 */
				//data.put("apptAgent","SCHENKER SINGAPORE PTE LTD");	
				//data.put("consigneeDetails","SCHENKER SINGAPORE PTELTDCHENKER SINGAPORE PTELTD");
				
				/* Case 5: consigneeDetails <49 and apptAgent>25 AND <50 */				
				data.put("consigneeDetails","CONSIGNE SINGAPORE PTE LTD");
				//data.put("apptAgent","APTAGEN SINGAPORE PTE LTD APPTAGEN SINGAPORE LTD");
				data.put("apptAgent","APTAGEN SINGAPORE PTE LTD");
				
				/* Case 5: consigneeDetails <49 and apptAgent >50 */				
				//data.put("consigneeDetails","SCHENKER SINGAPORE PTE LTD");
				//data.put("apptAgent","SCHENKER SINGAPORE PTELTDCHENKER SINGAPORE PTELTD SINGAPORE PTELTD");
				
				/* Case 6: consigneeDetails >50 and apptAgent >50 */				
				//data.put("consigneeDetails","SCHENKER SINGAPORE PTE LTD SCHENKER SINGAPORE");
				//data.put("apptAgent","SCHENKER SINGAPORE PTELTDCHENKER SINGAPORE PTELTD SINGAPORE PTELTD");
				
				/* Case 7: Live case */
				//data.put("consigneeDetails","UNIMARINE SHIPPING SERVICES PTE LTD");
				//data.put("apptAgent","MARINE AND OFFSHORE INTEGRATED LOG");
				
				/* Case 8: if both empty */
				//data.put("consigneeDetails","");
				//data.put("apptAgent","");		
				
				
				data.put("awbNo","61855566066");
				data.put("awbPcs","10");
				data.put("awbWeight","100");
				data.put("nog","NEWSPAPERS ");
				List<String> shcList = new ArrayList<String>();
					shcList.add("SCH");
					shcList.add("DAN");
					shcList.add("PSH");
					shcList.add("ABC");
					shcList.add("DEF");
					shcList.add("GHI");
					shcList.add("JKL");
					shcList.add("MNO");
					shcList.add("PQR");
				data.put("shc",shcList);

				 List<Map<String,Object>> loctionInfoList = new ArrayList<Map<String,Object>>();
				 Map<String,Object> locationInfo = new HashMap<String,Object>();
					 locationInfo.put("flightNo", "SQ1002");
					 locationInfo.put("flightDate", "10NOV17");
					 locationInfo.put("pcs", "230");
					 locationInfo.put("loc", "AKE84096SQ");
					 locationInfo.put("truckDock", "");
					 locationInfo.put("uldBinLoc", "T6/L1_SQMI_GEN");
					 locationInfo.put("warehouseLoc", "FGN1");
					 locationInfo.put("exitICSLocation", "ICS1");
				 loctionInfoList.add(locationInfo);
				 
				 locationInfo = new HashMap<String,Object>();
					 locationInfo.put("flightNo", "SQ113");
					 locationInfo.put("flightDate", "10NOV18");
					 locationInfo.put("pcs", "3300");
					 locationInfo.put("loc", "BT0001");
					 locationInfo.put("truckDock", "615A1");
					 locationInfo.put("uldBinLoc", "T6");
					 locationInfo.put("warehouseLoc", "FGN1");
					 locationInfo.put("exitICSLocation", "ICS1");
				 loctionInfoList.add(locationInfo);
				 
				 locationInfo = new HashMap<String,Object>();
					 locationInfo.put("flightNo", "SQ113");
					 locationInfo.put("flightDate", "10NOV18");
					 locationInfo.put("pcs", "3300");
					 locationInfo.put("loc", "PMC54083SQ");
					 locationInfo.put("truckDock", "");
					 locationInfo.put("uldBinLoc", "");
					 locationInfo.put("warehouseLoc", "FGN2");
					 locationInfo.put("exitICSLocation", "ICS1");
				 loctionInfoList.add(locationInfo);
				 
				 locationInfo = new HashMap<String,Object>();
					 locationInfo.put("flightNo", "SQ113");
					 locationInfo.put("flightDate", "10NOV18");
					 locationInfo.put("pcs", "3300");
					 locationInfo.put("loc", "");
					 locationInfo.put("truckDock", "615A1");
					 locationInfo.put("uldBinLoc", "T6/L1_SQMI_GEN");
					 locationInfo.put("warehouseLoc", "PIL6");
					 locationInfo.put("exitICSLocation", "ICS1");
			      loctionInfoList.add(locationInfo);
					 locationInfo = new HashMap<String,Object>();
					 locationInfo.put("flightNo", "SQ113");
					 locationInfo.put("flightDate", "10NOV18");
					 locationInfo.put("pcs", "3");
					 locationInfo.put("loc", "BT0009");
					 locationInfo.put("truckDock", "");
					 locationInfo.put("uldBinLoc", "");
					 locationInfo.put("warehouseLoc", "CC01");
					 locationInfo.put("exitICSLocation", "ICS1");
			      loctionInfoList.add(locationInfo);
					 locationInfo = new HashMap<String,Object>();
					 locationInfo.put("flightNo", "SQ5656");
					 locationInfo.put("flightDate", "10JAN18");
					 locationInfo.put("pcs", "10");
					 locationInfo.put("loc", "PMC23393MU");
					 locationInfo.put("truckDock", "618A");
					 locationInfo.put("uldBinLoc", "");
					 locationInfo.put("warehouseLoc", "NE_TDOCK_A");
					 locationInfo.put("exitICSLocation", "ICS1");
			      loctionInfoList.add(locationInfo);
				 data.put("locationInfoList", loctionInfoList);
				
				PrinterServiceImpl p = new PrinterServiceImpl();
				
				String outputCamelCase=p.generatePickOrderReport(data);
				System.out.println(new String(Base64.getDecoder().decode(outputCamelCase)));	
				//p.printDirect(Base64.getDecoder().decode(outputCamelCase));		
			}
			
			private static void testDOPrint() {
				   /**
				    * Structure of data
				    * -----------------
				    * Map<String, Object>
						* doNo (DO Number)
						* regnNo (SATS UEN No. Eg: 198500561R)
						* awbNo (AWB No.)
						* awbOrigin (Origin)
						* awbDestination (Destination)
						* awbPcs (Number of Pieces)
						* awbWt (Weight with Units K eg: 702.0K)
						* nog (Nature of goods)
						* shcList List<String> (Special Handling Code, list of string)
						* awbChargeCode (Charge Code - CC or PP)
						* cneeAddress1 (Consignee Name, Street)
						* cneeAddress2 (Consignee Country, Postal code)
						* apptAgent (Appointed Agent CODE)
						* clearingAgent (clearing Agent CODE)
						* deliveryPcs (pieces delivered in this DO)
						* deliveryWt (weight delivered in this DO)
						* chargesDue (Charges amount, if no charges pending 'NIL')
						* deliveryTo (person, name of person taking delivery)
						* deliveryToIC (person, Id / IC / etc of person taking delivery)
						* deliveryBy (staff delivering the cargo, userid)
						* deliveryDateTime (Delivery completed date - time ddMONYY HHMM)
						* locationInfoList List<Map<String,Object>>
					    * 	flightNo
					    * 	flightDate
					    * 	pcs
				    * 
				    */
					
					Map<String, Object > data= new HashMap<String, Object>();
					  	data.put("doNo", "D1900000158T6");
						data.put("regnNo", "198500561R");
						data.put("awbNo","61861033534");
						data.put("awbOrigin","BLR");
						data.put("awbDestination","SIN");
						data.put("awbPcs","71");
						data.put("awbWt","5700");
						data.put("nog","CONSOL");
						List<String> shcList = new ArrayList<String>();
							shcList.add("SCH");
							shcList.add("DAN");
							shcList.add("PSH");
							shcList.add("ABC");
							shcList.add("DEF");
							shcList.add("GHI");
							shcList.add("JKL");
							shcList.add("MNO");
							shcList.add("PQR");
						data.put("shcList",shcList);
					
						data.put("awbChargeCode","PP");
						data.put("cneeAddress1","EQUITY LOGISTICS S PTE LTD#NO 1 BBUKIT BATOK CRESCENT 04-55");
						data.put("cneeAddress2","SINGAPORE#658064");
						data.put("apptAgent","EQL");
						data.put("chargesDue", "No Charges For Collection");
						
						 List<Map<String,Object>> loctionInfoList = new ArrayList<Map<String,Object>>();
						 Map<String,Object> locationInfo = new HashMap<String,Object>();
							 locationInfo.put("flightNo", "SQ0951");
							 locationInfo.put("flightDate", "21Jul20");
							 locationInfo.put("pcs", "1");
							 locationInfo.put("loc", "6000");
							 locationInfo.put("warehouseLoc", "FGN1");
						 loctionInfoList.add(locationInfo);

						 locationInfo = new HashMap<String,Object>();
							 locationInfo.put("flightNo", "SQ0951");
							 locationInfo.put("flightDate", "21Jul20");
							 locationInfo.put("pcs", "1");
							 locationInfo.put("loc", "6005");
							 locationInfo.put("warehouseLoc", "ZZZ9");
						 loctionInfoList.add(locationInfo);
						 
						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0951");
						 locationInfo.put("flightDate", "21Jul20");
						 locationInfo.put("pcs", "1");
						 locationInfo.put("loc", "6006");
						 locationInfo.put("warehouseLoc", "GGG8");
						 loctionInfoList.add(locationInfo);
						 
						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0951");
						 locationInfo.put("flightDate", "21Jul20");
						 locationInfo.put("pcs", "1");
						 locationInfo.put("loc", "6007");
						 locationInfo.put("warehouseLoc", "FGN1");
						 loctionInfoList.add(locationInfo);
						 
						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0951");
						 locationInfo.put("flightDate", "21Jul20");
						 locationInfo.put("pcs", "1");
						 locationInfo.put("loc", "6008");
						 locationInfo.put("warehouseLoc", "EEE6");
						 loctionInfoList.add(locationInfo);
					 
						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0951");
						 locationInfo.put("flightDate", "21Jul20");
						 locationInfo.put("pcs", "1");
						 locationInfo.put("loc", null);
						 locationInfo.put("warehouseLoc", "FGN1");
						 loctionInfoList.add(locationInfo);
						 
						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0951");
						 locationInfo.put("flightDate", "21Jul20");
						 locationInfo.put("pcs", "100");
						 locationInfo.put("loc", "6010");
						 locationInfo.put("warehouseLoc", "FGN1");
						 loctionInfoList.add(locationInfo);

						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0951");
						 locationInfo.put("flightDate", "21Jul20");
						 locationInfo.put("pcs", "45");
						 locationInfo.put("loc", "6011");
						 locationInfo.put("warehouseLoc", "FGN4");
						 loctionInfoList.add(locationInfo);
						 
						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0951");
						 locationInfo.put("flightDate", "21Jul20");
						 locationInfo.put("pcs", "1");
						 locationInfo.put("loc", "6012");
						 locationInfo.put("warehouseLoc", "FGN2");
						 loctionInfoList.add(locationInfo);
						 
						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0951");
						 locationInfo.put("flightDate", "21Jul20");
						 locationInfo.put("pcs", "5000");
						 locationInfo.put("loc", "6013");
						 locationInfo.put("warehouseLoc", "FGN3");
						 loctionInfoList.add(locationInfo);
						 
						 locationInfo = new HashMap<String,Object>();
							 locationInfo.put("flightNo", "SQ0951");
							 locationInfo.put("flightDate", "21Jul20");
							 locationInfo.put("pcs", "1");
							 locationInfo.put("loc", "6014");
							 locationInfo.put("warehouseLoc", "FGN1");
						 loctionInfoList.add(locationInfo);
						 
						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0951");
						 locationInfo.put("flightDate", "21Jul20");
						 locationInfo.put("pcs", "123");
						 locationInfo.put("loc", null);
						 locationInfo.put("warehouseLoc", null);
						 loctionInfoList.add(locationInfo);
						 
						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0951");
						 locationInfo.put("flightDate", "21Jul20");
						 locationInfo.put("pcs", "4444");
						 locationInfo.put("loc", "6016");
						 locationInfo.put("warehouseLoc", "FGN5");
						 loctionInfoList.add(locationInfo);
						 
						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0953");
						 locationInfo.put("flightDate", "23Jul20");
						 locationInfo.put("pcs", "1");
						 locationInfo.put("loc", null);
						 locationInfo.put("warehouseLoc", null);
						 loctionInfoList.add(locationInfo);
					 
						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0951");
						 locationInfo.put("flightDate", "21Jul20");
						 locationInfo.put("pcs", "1");
						 locationInfo.put("loc", "6018");
						 locationInfo.put("warehouseLoc", null);
						 loctionInfoList.add(locationInfo);
						 
						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0951");
						 locationInfo.put("flightDate", "21Jul20");
						 locationInfo.put("pcs", "13");
						 locationInfo.put("loc", "6019");
						 locationInfo.put("warehouseLoc", "AFT1");
						 loctionInfoList.add(locationInfo);

						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0952");
						 locationInfo.put("flightDate", "22Jul20");
						 locationInfo.put("pcs", "1");
						 locationInfo.put("loc", null);
						 locationInfo.put("warehouseLoc", null);
						 loctionInfoList.add(locationInfo);
						 
						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0951");
						 locationInfo.put("flightDate", "21Jul20");
						 locationInfo.put("pcs", "10");
						 locationInfo.put("loc", "6021");
						 locationInfo.put("warehouseLoc", "ABC1");
						 loctionInfoList.add(locationInfo);
						 
						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0951");
						 locationInfo.put("flightDate", "21Jul20");
						 locationInfo.put("pcs", "1");
						 locationInfo.put("loc", "6022");
						 locationInfo.put("warehouseLoc", null);
						 loctionInfoList.add(locationInfo);

						 locationInfo = new HashMap<String,Object>();
						 locationInfo.put("flightNo", "SQ0951");
						 locationInfo.put("flightDate", "21Jul20");
						 locationInfo.put("pcs", "11");
						 locationInfo.put("loc", "6023");
						 locationInfo.put("warehouseLoc", "FGN2");
						 loctionInfoList.add(locationInfo);

				 data.put("locationInfoList", loctionInfoList);	

				 List<String> pdULDNosList = new ArrayList<String>();
				 
							 pdULDNosList.add("PD1017");
 							 pdULDNosList.add("PD2027");
							 pdULDNosList.add("PD5567");

							 pdULDNosList.add("RKN60782PC");
 							 pdULDNosList.add("RKN61029PC");
							 pdULDNosList.add("RKN61943PC");

							 pdULDNosList.add("PD8876");
							 pdULDNosList.add("PD9080");
							 pdULDNosList.add("PD4123");
 							 pdULDNosList.add("PD1001");

							 pdULDNosList.add("RKN62301PC");
							 pdULDNosList.add("RKN62719PC");
							 pdULDNosList.add("RKN61819PC");
 							 pdULDNosList.add("RKN62613PC");

 							 pdULDNosList.add("PD3009");
							 pdULDNosList.add("PD9982");
							 pdULDNosList.add("PD4422");

 							 pdULDNosList.add("RKN60798PC");
							 pdULDNosList.add("RKN60934PC");
							 pdULDNosList.add("RKN60305PC");

							 pdULDNosList.add("RKN62783PC");
							 pdULDNosList.add("RKN62438PC");
							 pdULDNosList.add("RKN62344PC");
 							 pdULDNosList.add("RKN61932PC");

 							 pdULDNosList.add("PD6712");
							 pdULDNosList.add("PD2211");
							 pdULDNosList.add("PD4465");

 							 pdULDNosList.add("RKN60810PC");
							 pdULDNosList.add("RKN60594PC");
							 pdULDNosList.add("RKN60305PC");

					data.put("pdULDNosList", pdULDNosList);
							 
						data.put("deliveryTo","KARTHICK");
						//Test for NRIC 5 Char
						//data.put("deliveryToIC","S1234567A"); //if nric
						//data.put("deliveryToIC","4567A"); //if nric has 5 char
						data.put("deliveryToIC","G7579118L"); //if nric has less than 5 char
						//data.put("deliveryToIC","null"); //if nric has null
						//data.put("deliveryToIC",""); //if nric has empty
						data.put("deliveryBy","ADKR");		
						data.put("deliveryDateTime","08NOV18 1537");						
						data.put("clearingAgent","IMPORT DIRECT CREDIT FACILITY");						
						data.put("deliveryPcs","10");	
						data.put("deliveryWt","200");
						data.put("uenNo", "198301309G");
						PrinterServiceImpl p = new PrinterServiceImpl();		
					String outputCamelCase=p.generateNoteDeliveryReport(data);
					System.out.println(new String(Base64.getDecoder().decode(outputCamelCase)));	
					//p.printDirect(Base64.getDecoder().decode(outputCamelCase));	
				}

			  /**
			    * Structure of data
			    * -----------------
			    * Map<String, Object>
					* user
					* printDateTime
					* uldtagId
					* destination
					* netweight
					* tareweight
					* totalweight
					* loaded
					* flightNo
					* flightDate
					* content
					* remark
					* List<String> dgDetailsList
			    */
			private static void testULDTagPrint() {
				Map<String, Object > data= new HashMap<String, Object>();
					data.put("user", "COSYS");
					data.put("printDateTime","18SEP");
					data.put("uldtagId","PAG41165SQQ");
					data.put("destination","BKKK");
					data.put("netweight","-2000.0");
					data.put("tareweight","110.0");
					data.put("totalweight","30000.0");
					data.put("loaded","SINN");					
					data.put("flightNo","FQ0970");					
					data.put("flightDate","28JAN20");					
					data.put("content","DANGEROUS GOODS");
					data.put("remark","REMARKS 123");
					data.put("IsXPS","Yes");					
					data.put("IsQRCodePrint","Yes");


				List<String> dgDetailsList = new ArrayList<String>();
					dgDetailsList.add("6.1 SCH");
					dgDetailsList.add("9 DAN");
					dgDetailsList.add("3 PSH");
					dgDetailsList.add("2.1 ABC");
					dgDetailsList.add("5 DEF");
					dgDetailsList.add("6 GHI");
					dgDetailsList.add("7 JKL");
					dgDetailsList.add("8 MNO");
					dgDetailsList.add("9 PQR");
				data.put("dgDetailsList",dgDetailsList);

				PrinterServiceImpl p = new PrinterServiceImpl();		
				String outputCamelCase=p.generateULDTAGReport(data);
				System.out.println(new String(Base64.getDecoder().decode(outputCamelCase)));	
				//p.printDirect(Base64.getDecoder().decode(outputCamelCase));					
			}

			private static void testOffloadFormPrint() {
				  /**
				    * Structure of data
				    * -----------------
				    * Map<String, Object>
					    * driverID
					    * flightNo
					    * flightDate
					    * std
					    * etd
					    * bay
					    * offloadRemarks
					    * uldNoFlightDateList List<String>
					    * 	(format eg: PGA22007MU  07JUL2018  1743)
						* satsUserID
						* releaseDateTime
				    */
				Map<String, Object > data= new HashMap<String, Object>();
					data.put("driverID","MACK");
					data.put("flightNo","CK0288");
					data.put("flightDate","05Dec18");
					data.put("std","14:55");
					data.put("etd",null);
					data.put("bay","502");
					data.put("offloadRemarks","Offload Reasson 123");
					data.put("returnInd",false);
		
				List<String> uldNoFlightDateList = new ArrayList<String>();
					uldNoFlightDateList.add("PGA22007MU 07JUL2018 1743");
					uldNoFlightDateList.add("PGA22008MU 07JUL2018 1743");
					uldNoFlightDateList.add("PGA22009MU 07JUL2018 1743");
					uldNoFlightDateList.add("PGA22010MU 07JUL2018 1743");
					uldNoFlightDateList.add("PGA22011MU 07JUL2018 1743");
				data.put("uldNoFlightDateList", uldNoFlightDateList);
			
				data.put("satsUserID","COSYS");
				data.put("releaseDateTime","05Dec18 10:20");
				
				PrinterServiceImpl p = new PrinterServiceImpl();
				String outputCamelCase=p.generateOffloadFormReport(data);
				System.out.println(new String(Base64.getDecoder().decode(outputCamelCase)));	
				//p.printDirect(Base64.getDecoder().decode(outputCamelCase));				
			}
			
			private static void testTRMPrint() {
				  /**
				    * Structure of data
				    * -----------------
				    * Map<String, Object>
						* airport
						* date
						* printQueue
						* receiveDate
						* receivetime
						* receivingCarrier
						* tmNo
						* transferringCarrier
						* userId
						* transferManifestDetails List<transferManifestDetails>
						* 	awbDest
						* 	awbNo
						* 	noPackage
						* 	remarks
						* 	weight				
				    */
				Map<String, Object > data= new HashMap<String, Object>();
					data.put("airport","SINGAPORE");
					data.put("date","28AUG17");
					data.put("printQueue","NGCUATPRT08");
					data.put("receiveDate","06AUG2018");
					data.put("receivetime","23:59");
					data.put("receivingCarrier","SINGAPORE AIRLINE LIMTED");
					data.put("tmNo","AI170100");
					data.put("transferringCarrier","AIR-INDIA LTD.");
					data.put("userId","AZAI");					
					
					List<Map<String,Object>> transferManifestDetails = new ArrayList<Map<String,Object>>();
					 Map<String,Object> trm = new HashMap<String,Object>();
					 trm.put("awbDest", "CGK");
					 trm.put("awbNo", "09860270313");
					 trm.put("noPackage", "1");
					 trm.put("remarks", "QI0380/27AUG2017");
					 trm.put("weight", "5");
					 trm.put("weightUnitCode", "K");
					 transferManifestDetails.add(trm);
					 
					 trm = new HashMap<String,Object>();
					 trm.put("awbDest", "CGK");
					 trm.put("awbNo", "023423273455");
					 trm.put("noPackage", "5");
					 trm.put("remarks", "QI0320/28AUG2017");
					 trm.put("weight", "6.2");
					 trm.put("weightUnitCode", "K");
					 transferManifestDetails.add(trm);
					 
					 trm = new HashMap<String,Object>();
					 trm.put("awbDest", "CGK");
					 trm.put("awbNo", "023423273423");
					 trm.put("noPackage", "5");
					 trm.put("remarks", "QI0320/28AUG2017");
					 trm.put("weight", "600");
					 trm.put("weightUnitCode", "K");
					 transferManifestDetails.add(trm);

					 trm = new HashMap<String,Object>();
					 trm.put("awbDest", "CGK");
					 trm.put("awbNo", "023423273423");
					 trm.put("noPackage", "5");
					 trm.put("remarks", "QI0320/28AUG2017");
					 trm.put("weight", "620.23");
					 trm.put("weightUnitCode", "K");
					 transferManifestDetails.add(trm);
					 
					 data.put("trmList", transferManifestDetails);
				PrinterServiceImpl p = new PrinterServiceImpl();
				String outputCamelCase=p.generateTransferManifestReport(data);
				System.out.println(new String(Base64.getDecoder().decode(outputCamelCase)));	
				//p.printDirect(Base64.getDecoder().decode(outputCamelCase));				
			}
}