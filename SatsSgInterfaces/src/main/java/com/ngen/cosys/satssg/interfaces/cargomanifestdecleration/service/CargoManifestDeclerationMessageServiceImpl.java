package com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.service;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.esb.route.ConnectorPublisher;
import com.ngen.cosys.esb.route.ConnectorUtils;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.ESBRouterTypeUtils;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.enums.TextMessageConstants;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.dao.CargoManifestDeclerationMessageDAO;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.model.CmdLocalAuthorityInfoModel;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.model.CustomCargoManifestDeclarationMessageModel;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.parsers.AttachOrDetachCargoManifestDecleration;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.parsers.FNAOutboundConstructor;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.parsers.MasterAirWebBillConsignmentDetailParser;
import com.ngen.cosys.satssg.interfaces.manifestreconcillationstatement.service.ManifestReconcillationStatementMessageService;
import com.ngen.cosys.satssg.interfaces.util.MessageTypeIdentifier;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingException;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingExceptionFactory;
import com.ngen.cosys.timezone.util.TenantZoneTime;

@Service
public class CargoManifestDeclerationMessageServiceImpl implements CargoManifestDeclerationMessageService {

   private static final String INVALID_WEIGHT_UNIT_CODE = "INVALID WEIGHT UNIT CODE";

   private static final String INVALID_LATE_INDICATOR = "INVALID LATE INDICATOR";

   private static final String INVALID_ACTION_CODE = "INVALID ACTION CODE";

   private static final String PROCESSED = "PROCESSED";

   private static final String REJECTED = "REJECTED";

   private static final String HTTP = "HTTP";

   private static final String CCN = "CCN";

   private static final String COSYS = "COSYS";

   private static final String INVALID_WEIGHT = "INVALID WEIGHT";

   private static final String INVALID_PIECES = "INVALID PIECES";

   private static final String INVALID_LOAD_LIST_INDICATOR = "INVALID LOAD LIST INDICATOR";

   private static final Logger LOGGER = LoggerFactory.getLogger(CargoManifestDeclerationMessageServiceImpl.class);

   private static final byte[] LINEFEED_SITA = { 0x0D, 0x0A };

   @Autowired
   private BeanFactory beanFactory;

   @Autowired
   private AttachOrDetachCargoManifestDecleration attachOrDetachCargoManifestDecleration;

   @Autowired
   private CargoManifestDeclerationMessageDAO cargoManifestDeclerationMessageDAO;

   @Autowired
   private ConnectorPublisher publisher;

   @Autowired
   private ManifestReconcillationStatementMessageService manifestReconcillationStatementMessageService;

   @Qualifier("fnaOutboundConstructor")
   @Autowired
   private FNAOutboundConstructor fnaOutboundConstructor;

   @Autowired
   private MessageTypeIdentifier messageTypeIdentifier;

   @Autowired
   private ConnectorLoggerService connectorLoggerService;

   @Autowired
   private ApplicationLoggerService loggerService;

   @Value("${esb.connector.hostname}")
   private String esbHost;

   @Value("${esb.connector.portnumber}")
   private String esbPort;

   @Value("${esb.connector.path-jms}")
   private String esbPathJMS;

   // System parameter values
   private BigInteger firstWindowForImport;
   private BigInteger secondWindowForImport;
   private BigInteger firstWindowForExport;
   private BigInteger secondWindowForExport;

   @PostConstruct
   public void getOneTimeParameterValues() throws CustomException {
      this.firstWindowForImport = cargoManifestDeclerationMessageDAO.getMrsFirstWindowForImport();
      this.secondWindowForImport = cargoManifestDeclerationMessageDAO.getMrsSecondWindowForImport();
      this.firstWindowForExport = cargoManifestDeclerationMessageDAO.getMrsFirstWindowForExport();
      this.secondWindowForExport = cargoManifestDeclerationMessageDAO.getMrsSecondWindowForExport();
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.service.
    * CargoManifestDeclerationMessageService#processMessages(java.lang.String,
    * javax.servlet.http.HttpServletRequest)
    */
   @Override
   public ResponseEntity<String> processMessages(String customIncomingCargoManifestDeclarationContent,
         HttpServletRequest request) throws Exception {
      String cmdContent = customIncomingCargoManifestDeclarationContent;
      // Identify Message Type
      String messageType = MessageTypeIdentifier.getMessageType(cmdContent);
      boolean contains = StringUtils.isEmpty(messageType) ? true : false;
      BigInteger esbMessageId = ConnectorUtils.getESBMessageID(request);
      //
      LocalDateTime msgRecievedTime = java.time.LocalDateTime.now();
      String incomingMessage = customIncomingCargoManifestDeclarationContent;
      CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel = new CustomCargoManifestDeclarationMessageModel();
      LocalDate date = java.time.LocalDate.now();
      if (!ObjectUtils.isEmpty(firstWindowForImport)) {
         customCargoManifestDeclarationMessageModel
               .setFirstWindowForImport(date.plusDays(firstWindowForImport.intValue()));
      }
      if (!ObjectUtils.isEmpty(secondWindowForImport)) {
         customCargoManifestDeclarationMessageModel
               .setSecondWindowForImport(date.plusDays(secondWindowForImport.intValue()));
      }
      if (!ObjectUtils.isEmpty(firstWindowForExport)) {
         customCargoManifestDeclarationMessageModel
               .setFirstWindowForExport(date.plusDays(firstWindowForExport.intValue()));
      }
      if (!ObjectUtils.isEmpty(secondWindowForExport)) {
         customCargoManifestDeclarationMessageModel
               .setSecondWindowForExport(date.plusDays(secondWindowForExport.intValue()));
      }
      // From & To date for query params
      customCargoManifestDeclarationMessageModel.setFromDate(LocalDate.now().minusDays(365));
      customCargoManifestDeclarationMessageModel.setToDate(LocalDate.now().plusDays(10));
      String finalMsg = "";

      if (!contains && !messageType.equalsIgnoreCase(TextMessageConstants.FMA_IDENTIFIER.getValue())
            || contains && !cmdContent.contains(TextMessageConstants.FMA_IDENTIFIER.getValue()) && !contains
                  && !messageType.equalsIgnoreCase(TextMessageConstants.FNA_IDENTIFIER.getValue())
            || contains && !cmdContent.contains(TextMessageConstants.FNA_IDENTIFIER.getValue()) && !contains
                  && !messageType.equalsIgnoreCase("PSN")
            || contains && !cmdContent.contains("PSN")) {
         finalMsg = constructHeader(customIncomingCargoManifestDeclarationContent);
         LOGGER.warn("Process NOT PSN Message constructor :: {}", finalMsg);
      } else {
         LOGGER.warn("Process ACK Message with payload 'NULL' in ResponseEntity :: {}");
         
         String systemName = request.getHeader("SYSTEM_NAME");
         
         return manifestReconcillationStatementMessageService
               .processAcKMessages(customIncomingCargoManifestDeclarationContent, systemName);
      }

      String acknowledgement = null;

      try {
         parseMessages(customIncomingCargoManifestDeclarationContent, customCargoManifestDeclarationMessageModel);
         validateCmdMessages(customCargoManifestDeclarationMessageModel);
         attachOrDetachCargoManifestDecleration.validateCmdWithActionCode(customCargoManifestDeclarationMessageModel);
         customIncomingCargoManifestDeclarationContent = finalMsg.replace("CMD", "CMA");
         acknowledgement = "CMA";
         if (!StringUtils.isEmpty(customCargoManifestDeclarationMessageModel.getActionCode())
               && (!customCargoManifestDeclarationMessageModel.getActionCode().equalsIgnoreCase("D"))) {
            if (customCargoManifestDeclarationMessageModel.getFinalDestination() != null
                  && customCargoManifestDeclarationMessageModel.getFinalDestination().equalsIgnoreCase("SIN")) {
               customCargoManifestDeclarationMessageModel.setImportExportIndicator("I");
               if (customCargoManifestDeclarationMessageModel.getScheduleArrivalDate() != null) {
                  customCargoManifestDeclarationMessageModel
                        .setScheduleDate(customCargoManifestDeclarationMessageModel.getScheduleArrivalDate());
               }
            } else {
               customCargoManifestDeclarationMessageModel.setImportExportIndicator("E");
               if (customCargoManifestDeclarationMessageModel.getScheduleDepartureDate() != null) {
                  customCargoManifestDeclarationMessageModel
                        .setScheduleDate(customCargoManifestDeclarationMessageModel.getScheduleDepartureDate());
               }
            }
            attachOrDetachCargoManifestDecleration
                  .attachCmdToEarliestFlight(customCargoManifestDeclarationMessageModel);
         }
      } catch (MessageParsingException e) {
         acknowledgement = "FNA";
         customIncomingCargoManifestDeclarationContent = fnaOutboundConstructor.constructFNA(finalMsg, e,
               messageTypeIdentifier);
         LOGGER.warn("MessageParsingException :: FNA Message Constructor : {}",
               customIncomingCargoManifestDeclarationContent);
      } catch (Exception e) {
         LOGGER.warn("Exception occurred in CMD Service Implementation : NULL Payload & BAD Request Exception :: {}",
               String.valueOf(e));
         LOGGER.warn("CMD Detailed Exception :: {}", getStackTrace(e));
         logincomingMessagesOnException(customCargoManifestDeclarationMessageModel, incomingMessage, msgRecievedTime,
               esbMessageId);
         return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
      }
      // Contains Modified
      if (!contains && !messageType.equalsIgnoreCase("MRS")
            || contains && !customIncomingCargoManifestDeclarationContent.contains("MRS")) {
         LOGGER.warn("CMD not Contains MRS Block :: {}");
         customIncomingCargoManifestDeclarationContent = customIncomingCargoManifestDeclarationContent
               .replaceAll("(?m)^[ \t]*\r?\n", "");
         sendAckForCmd(customIncomingCargoManifestDeclarationContent, customCargoManifestDeclarationMessageModel,
               acknowledgement);
         logincomingMessage(customCargoManifestDeclarationMessageModel, incomingMessage, acknowledgement,
               msgRecievedTime, esbMessageId);
      }
      return new ResponseEntity<>(customIncomingCargoManifestDeclarationContent, HttpStatus.OK);
   }

   /**
    * @param customIncomingCargoManifestDeclarationContent
    * @throws MessageParsingException
    * @throws CustomException
    */
   public void validateCmdMessages(
         CustomCargoManifestDeclarationMessageModel customIncomingCargoManifestDeclarationContent)
         throws MessageParsingException, CustomException {
      if ((StringUtils.isEmpty(customIncomingCargoManifestDeclarationContent.getActionCode()))) {
         throw MessageParsingExceptionFactory.createMessageParsingException(INVALID_ACTION_CODE);
      }
      if ((!StringUtils.isEmpty(customIncomingCargoManifestDeclarationContent.getActionCode()))
            && (!("A".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getActionCode())
                  || "M".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getActionCode())
                  || "D".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getActionCode())))) {
         throw MessageParsingExceptionFactory.createMessageParsingException(INVALID_ACTION_CODE);
      }
      if (!("N".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getLoadListIndicator())
            || "Y".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getLoadListIndicator()))) {
         throw MessageParsingExceptionFactory.createMessageParsingException(INVALID_LOAD_LIST_INDICATOR);
      }
      if ((!("Y".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getLateIndicator())
            || "N".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getLateIndicator())))) {
         throw MessageParsingExceptionFactory.createMessageParsingException(INVALID_LATE_INDICATOR);
      }
      if (BigInteger.ZERO.compareTo(customIncomingCargoManifestDeclarationContent.getPieces()) == 0) {
         throw MessageParsingExceptionFactory.createMessageParsingException(INVALID_PIECES);
      }
      if (BigDecimal.ZERO.compareTo(customIncomingCargoManifestDeclarationContent.getWeight()) == 0) {
         throw MessageParsingExceptionFactory.createMessageParsingException(INVALID_WEIGHT);
      }
      if (ObjectUtils.isEmpty(customIncomingCargoManifestDeclarationContent.getWeightUnitCode())
            || (customIncomingCargoManifestDeclarationContent.getWeightUnitCode() != 'K')) {
         throw MessageParsingExceptionFactory.createMessageParsingException(INVALID_WEIGHT_UNIT_CODE);
      }
      if (!ObjectUtils.isEmpty(
            cargoManifestDeclerationMessageDAO.getMrsSentDetail(customIncomingCargoManifestDeclarationContent))) {
         customIncomingCargoManifestDeclarationContent.setMrsSent(true);
      } else {
         customIncomingCargoManifestDeclarationContent.setMrsSent(false);
      }

      if (customIncomingCargoManifestDeclarationContent.getActionCode().equalsIgnoreCase("A")
            && (ObjectUtils.isEmpty(
                  cargoManifestDeclerationMessageDAO.getMrsSentDate(customIncomingCargoManifestDeclarationContent)))
            && "Y".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getLoadListIndicator())
            && "Y".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getLateIndicator())) {
         throw MessageParsingExceptionFactory.createMessageParsingException(INVALID_LOAD_LIST_INDICATOR);
      }
      if (customIncomingCargoManifestDeclarationContent.getActionCode().equalsIgnoreCase("A")
            && (!ObjectUtils.isEmpty(
                  cargoManifestDeclerationMessageDAO.getMrsSentDate(customIncomingCargoManifestDeclarationContent)))
            && "Y".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getLoadListIndicator())
            && "Y".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getLateIndicator())) {
         throw MessageParsingExceptionFactory.createMessageParsingException(INVALID_LOAD_LIST_INDICATOR);
      }

      if (("M".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getActionCode())
            || "D".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getActionCode()))
            && "Y".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getLoadListIndicator())
            && "Y".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getLateIndicator())) {
         throw MessageParsingExceptionFactory.createMessageParsingException(INVALID_LOAD_LIST_INDICATOR);
      }

      // sin needs to be replaced by tenant id
      if (MultiTenantUtility.isTenantAirport(customIncomingCargoManifestDeclarationContent.getFinalDestination())
            && (!StringUtils.isEmpty(customIncomingCargoManifestDeclarationContent.getLoadListIndicator()))
            && customIncomingCargoManifestDeclarationContent.getLoadListIndicator().equalsIgnoreCase("Y")) {
         throw MessageParsingExceptionFactory.createMessageParsingException(INVALID_LOAD_LIST_INDICATOR);
      }

      if ("Y".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getLoadListIndicator())
            && "Y".equalsIgnoreCase(customIncomingCargoManifestDeclarationContent.getLateIndicator())) {
         throw MessageParsingExceptionFactory.createMessageParsingException(INVALID_LOAD_LIST_INDICATOR);
      }
   }

   public static String parseCRLFPayloadData(Object payload) {
      // Replace the control characters to XML CDATA specific characters
      // \r character
      CharSequence crlfCharSequence = new String(new char[] { 0x0d });
      CharSequence xmlCDATACrlfCharSequence = String.valueOf("&#x0D;");
      return ((String) payload).replace(crlfCharSequence, xmlCDATACrlfCharSequence);
   }

   /**
    * Parse incoming CMD Message
    * 
    * @param customIncomingCargoManifestDeclarationContentModel
    * @return
    * @throws Exception
    * @throws CustomException
    */
   public String parseMessages(String customIncomingCargoManifestDeclarationContentModel,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws CustomException, Exception {
      String[] lines = customIncomingCargoManifestDeclarationContentModel.split("\\r?\\n");
      List<String> msgLis = new LinkedList<>();
      for (String line : lines) {
         msgLis.add(line);
      }

      List<String> finalMsgList = removeCR(msgLis);
      lookForMsgsToProcess(finalMsgList, 0, customCargoManifestDeclarationMessageModel);
      return customIncomingCargoManifestDeclarationContentModel;
   }

   public void lookForMsgsToProcess(List<String> finalMsgList, int msgloop,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws CustomException, Exception {
      String[] msgdata = new String[30];
      int strNo = 0;
      msgdata[0] = null;
      // resuming from the sent Array list index (After reading the header)
      List<CmdLocalAuthorityInfoModel> cmdLarinfo = new ArrayList<>();
      for (; msgloop < finalMsgList.size(); msgloop++) {
         String msg = finalMsgList.get(msgloop);

         if (msg.charAt(0) == TextMessageConstants.SLANTCHAR) {

            String lineIdentifier = "";
            int loop = msgloop;
            for (; loop > 0; loop--) {
               String previousline = finalMsgList.get(loop - 1);
               if (previousline.charAt(0) != TextMessageConstants.SLANTCHAR) {
                  lineIdentifier = previousline.substring(0, 3);
                  StringBuilder sb = new StringBuilder();
                  sb.append(lineIdentifier);
                  sb.append(msg);
                  msgdata[0] = !StringUtils.isEmpty(sb.toString()) ? sb.toString().trim() : sb.toString();
                  strNo = 1;
                  dispatchMsg(msgdata, strNo, customCargoManifestDeclarationMessageModel, cmdLarinfo);
                  break;
               }
            }

            // child record
            msgdata[strNo++] = msg;
         } else {
            // if its not a child record
            msgdata[0] = msg;
            strNo = 0;
            dispatchMsg(msgdata, strNo, customCargoManifestDeclarationMessageModel, cmdLarinfo);

         }
      }
   }

   public void dispatchMsg(String[] msgdata, int strNo,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel,
         List<CmdLocalAuthorityInfoModel> cmdLarinfo) throws CustomException, Exception {
      if (msgdata[0].indexOf(TextMessageConstants.CMD_IDENTIFIER.getValue()) == 0) {
         this.extractMsgVerNo(msgdata[0], customCargoManifestDeclarationMessageModel);

      } else if (msgdata[0].indexOf(TextMessageConstants.ADD_ACTION_CODE_IDENTIFIER.getValue()) == 0
            || msgdata[0].indexOf(TextMessageConstants.MODIFY_ACTION_CODE_IDENTIFIER.getValue()) == 0
            || msgdata[0].indexOf(TextMessageConstants.DELETE_ACTION_CODE_IDENTIFIER.getValue()) == 0) {
         extractLoadListIndicator(msgdata[0], customCargoManifestDeclarationMessageModel);
      } else if (msgdata[0].indexOf(TextMessageConstants.MASTER_AIR_WEB_BILL_IDENTIFIER.getValue()) == 0) {
         MasterAirWebBillConsignmentDetailParser masterAirWebBillConsignmentDetailParser = beanFactory
               .getBean(MasterAirWebBillConsignmentDetailParser.class);

         masterAirWebBillConsignmentDetailParser.parseMasterAWBConsignmentDetail(msgdata, strNo,
               customCargoManifestDeclarationMessageModel);
      } else if (msgdata[0].indexOf(TextMessageConstants.FLIGHT_IDENTIFIER.getValue()) == 0) {
         MasterAirWebBillConsignmentDetailParser masterAirWebBillConsignmentDetailParser = beanFactory
               .getBean(MasterAirWebBillConsignmentDetailParser.class);

         masterAirWebBillConsignmentDetailParser.parseFlightDetails(msgdata,
               customCargoManifestDeclarationMessageModel);

      } else if (msgdata[0].indexOf(TextMessageConstants.HOUSE_WAY_BILL_IDENTIFIER.getValue()) == 0) {
         MasterAirWebBillConsignmentDetailParser masterAirWebBillConsignmentDetailParser = beanFactory
               .getBean(MasterAirWebBillConsignmentDetailParser.class);

         masterAirWebBillConsignmentDetailParser.parseHouseWayBillDetails(msgdata, strNo,
               customCargoManifestDeclarationMessageModel);

      } else if (msgdata[0].indexOf(TextMessageConstants.MASTER_AWB_SHIPPER_NAME_AND_ADDRESS_IDENTIFIER.getValue()) == 0
            || msgdata[0]
                  .indexOf(TextMessageConstants.MASTER_AWB_CONSIGNEE_NAME_AND_ADDRESS_IDENTIFIER.getValue()) == 0) {
         MasterAirWebBillConsignmentDetailParser masterAirWebBillConsignmentDetailParser = beanFactory
               .getBean(MasterAirWebBillConsignmentDetailParser.class);
         masterAirWebBillConsignmentDetailParser.parseMasterAwbShipperOrConsigneeDetails(msgdata,
               customCargoManifestDeclarationMessageModel);

      } else if (msgdata[0].indexOf(TextMessageConstants.PERMIT_DETAIL.getValue()) == 0) {
         MasterAirWebBillConsignmentDetailParser masterAirWebBillConsignmentDetailParser = beanFactory
               .getBean(MasterAirWebBillConsignmentDetailParser.class);
         masterAirWebBillConsignmentDetailParser.parsePermitDetails(msgdata, strNo,
               customCargoManifestDeclarationMessageModel, cmdLarinfo);
         customCargoManifestDeclarationMessageModel.setLarInfo(cmdLarinfo);
      }

      else if (msgdata[0].indexOf(TextMessageConstants.IMPORT_MASTER_AWB_DETAIL_IDENTIFIER.getValue()) == 0) {
         MasterAirWebBillConsignmentDetailParser masterAirWebBillConsignmentDetailParser = beanFactory
               .getBean(MasterAirWebBillConsignmentDetailParser.class);
         masterAirWebBillConsignmentDetailParser.parseImportMasterAwbDetails(msgdata,
               customCargoManifestDeclarationMessageModel);
      } else if (msgdata[0].indexOf(TextMessageConstants.EXEMPTION_DETAILS_IDENTIFIER.getValue()) == 0) {
         MasterAirWebBillConsignmentDetailParser masterAirWebBillConsignmentDetailParser = beanFactory
               .getBean(MasterAirWebBillConsignmentDetailParser.class);
         masterAirWebBillConsignmentDetailParser.parseExemptionDetails(msgdata,
               customCargoManifestDeclarationMessageModel, cmdLarinfo);
         customCargoManifestDeclarationMessageModel.setLarInfo(cmdLarinfo);
      } else if (msgdata[0].indexOf(TextMessageConstants.DELIVERED_OR_UNDELIVERED_IDENTIFIER.getValue()) == 0) {
         MasterAirWebBillConsignmentDetailParser masterAirWebBillConsignmentDetailParser = beanFactory
               .getBean(MasterAirWebBillConsignmentDetailParser.class);
         masterAirWebBillConsignmentDetailParser.parseDeliverUndeliverInfoDetails(msgdata,
               customCargoManifestDeclarationMessageModel);
      } else if (msgdata[0].indexOf(TextMessageConstants.SENDER_INFORMATION_IDENTIFIER.getValue()) == 0) {
         MasterAirWebBillConsignmentDetailParser masterAirWebBillConsignmentDetailParser = beanFactory
               .getBean(MasterAirWebBillConsignmentDetailParser.class);
         masterAirWebBillConsignmentDetailParser.parseSenderInfo(msgdata, strNo,
               customCargoManifestDeclarationMessageModel);
      }
   }

   /**
    * This method divides each line into different elements and puts it in the
    * array list
    * 
    * @param msgList
    *           - ArrayList old array list
    * @return List containing each line
    */
   public List<String> removeCR(List<String> msgList) {
      List<String> finalMsgList = new LinkedList<String>();
      for (int i = 2; i < msgList.size(); i++) {
         String msg = msgList.get(i);
         if (msg != null)
            if (msg.indexOf('\n') >= 0) {
               for (StringTokenizer st = new StringTokenizer(msg, "\n"); st.hasMoreElements();) {
                  String subMsg = st.nextToken();
                  if (subMsg.length() > 0)
                     finalMsgList.add(subMsg.toUpperCase());
               }

            } else if (msg.length() > 0)
               finalMsgList.add(msg);
      }
      return finalMsgList;
   }

   /**
    * Retrieves message version no
    * 
    * @param msg
    * @param airwayBillData
    * @throws MessageParsingException
    */
   private void extractMsgVerNo(String msg,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException {
      try {
         customCargoManifestDeclarationMessageModel.setCmdVersionNumber(new BigInteger(msg.substring(4)));
      } catch (NumberFormatException e) {
         throw MessageParsingExceptionFactory.createMessageParsingException("MESSAGE VERSION NO NOT CORRECT. ",
               e.getMessage());
      }
   }

   /**
    * @param msg
    * @param customCargoManifestDeclarationMessageModel
    * @throws MessageParsingException
    */
   private void extractLoadListIndicator(String msg,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException {

      try {
         customCargoManifestDeclarationMessageModel.setActionCode(msg.substring(0, 1));

      } catch (Exception e) {
         throw MessageParsingExceptionFactory.createMessageParsingException("ACTION CODE IS NOT CORRECT. ",
               e.getMessage());
      }
      try {
         if (msg.substring(1, 2).equals(TextMessageConstants.SLANT_IDENTIFIER.getValue())) {
            customCargoManifestDeclarationMessageModel.setLoadListIndicator(msg.substring(2, 3));
         }
      } catch (Exception e) {
         throw MessageParsingExceptionFactory.createMessageParsingException("lOAD LIST INDICATOR IS  NOT CORRECT. ",
               e.getMessage());
      }
      try {
         if (msg.substring(3, 4).equals(TextMessageConstants.SLANT_IDENTIFIER.getValue())) {
            customCargoManifestDeclarationMessageModel.setLateIndicator(msg.substring(4));
         }
      } catch (Exception e) {
         throw MessageParsingExceptionFactory.createMessageParsingException("LATE INDICATOR IS  NOT CORRECT. ",
               e.getMessage());
      }
   }

   public void sendAckForCmd(String customIncomingCargoManifestDeclarationContent,
         CustomCargoManifestDeclarationMessageModel data, String acknowledgement) {
      boolean loggerEnabled = false;
      BigInteger messageId = null;
      HttpStatus httpStatus = null;
      try {
         if (!loggerEnabled) {
            messageId = logOutgoingMessage(customIncomingCargoManifestDeclarationContent, data, acknowledgement);
         }
      }
      catch (Exception e) {
          String errorMessage = String.valueOf(e);
          // Log Outgoing message
          if (messageId != null) {
             logOutgoingMessage(messageId, "ERROR", data, acknowledgement);
             // Log Error Outgoing Message
             logOutgoingErrorMessage(messageId, httpStatus, errorMessage);
          }
       }
      try
      {
    	  Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled, "CMD", TenantContext.getTenantId());
         ResponseEntity<Object> response = publisher.sendJobDataToConnector(
               customIncomingCargoManifestDeclarationContent, CCN, MediaType.APPLICATION_JSON, payloadHeaders);
         if (Objects.nonNull(response)) {
            if (Objects.equals(HttpStatus.BAD_REQUEST, response.getStatusCode())) {
               httpStatus = response.getStatusCode();
               CustomException ex = null;
               if (Objects.nonNull(response.getBody()) && response.getBody() instanceof CustomException) {
                  ex = (CustomException) response.getBody();
                  messageId = (BigInteger) ex.getPlaceHolders()[1];
               } else {
                  // Partial Success Case
                  if (loggerEnabled) {
                     messageId = new BigInteger(
                           response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
                  }
               }
            } else {
                // Success Case
                if (loggerEnabled) {
                   messageId = new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
                   if (response.getHeaders().containsKey(ESBRouterTypeUtils.MESSAGE_ID.getName())
                         && Objects.nonNull(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()))) {
 
                   }
                }
 
             }
      }
      LOGGER.warn("response :: ", response);
      // Log Outgoing message
      logOutgoingMessage(messageId, "SENT", data, acknowledgement);



   } catch (Exception e) {
      String errorMessage = String.valueOf(e);
      // Log Outgoing message
      if (messageId != null) {
         logOutgoingMessage(messageId, "ESBERROR", data, acknowledgement);
         // Log Error Outgoing Message
         logOutgoingErrorMessage(messageId, httpStatus, errorMessage);
      }
   }
}

   
   
 




   private String constructHeader(String msg) throws CustomException, IOException {
      // Addresses
      String telexAddress = messageTypeIdentifier.getCosysTelexAddress();
      String cmaAddress = messageTypeIdentifier.getCMARecipientAddress();
      //
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      DataOutputStream dout = new DataOutputStream(bout);
      String num = msg.substring(27, msg.indexOf("CMD") - 1);
      msg = msg.substring(msg.indexOf(TextMessageConstants.CMD_IDENTIFIER.getValue()));
      dout.writeBytes("QK");
      dout.writeBytes(" ");
      dout.writeBytes(cmaAddress);
      dout.write(LINEFEED_SITA);
      dout.writeBytes(".");
      dout.writeBytes(telexAddress);
      dout.writeBytes(" ");
      dout.writeBytes(getStringFromDate(new Date(), "ddHHmm"));
      dout.writeBytes(" ");
      dout.writeBytes(StringUtils.isEmpty(num) ? num : num.trim());
      dout.write(LINEFEED_SITA);
      // Generate list of string for a given message
      String[] lines = msg.split("\\r?\\n");
      for (String line : lines) {
         dout.writeBytes(StringUtils.isEmpty(line) ? line : line.trim());
         dout.write(LINEFEED_SITA);
      }
      return new String(bout.toByteArray());
   }

   public static String getStringFromDate(Date date, String dateFormat) {
      String sDate = null;
      SimpleDateFormat sdf = null;
      try {
         sdf = new SimpleDateFormat(dateFormat);
         // sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
         sDate = sdf.format(date);
      } catch (Exception e) {
         LOGGER.error("Exception while formatting Date to String", e);
      }
      return sDate;
   }

   private void logincomingMessage(CustomCargoManifestDeclarationMessageModel data, String msg, String acknowledgement,
         LocalDateTime msgRecievedTime, BigInteger esbMessageId) throws CustomException {
      IncomingMessageLog incomingMessage = new IncomingMessageLog();
      incomingMessage.setChannelReceived(HTTP);
      incomingMessage.setInterfaceSystem(CCN);
      incomingMessage.setSenderOriginAddress(COSYS);
      incomingMessage.setMessageType("CMD");
      incomingMessage.setSubMessageType("TXT");
      incomingMessage.setCarrierCode(data.getCarrierCode());
      incomingMessage.setFlightNumber(data.getFlightNumber());
      if (!ObjectUtils.isEmpty(data.getFlightDate())) {
         incomingMessage.setFlightOriginDate(data.getFlightDate().atStartOfDay());
      }
      incomingMessage.setShipmentNumber(data.getAwbPrefix() + data.getAwbSerialNumber());
      incomingMessage.setShipmentDate(null);
      incomingMessage.setReceivedOn(msgRecievedTime);
      incomingMessage.setVersionNo(1);
      incomingMessage.setSequenceNo(1);
      incomingMessage.setMessageContentEndIndicator(null);
      incomingMessage.setMessage(msg);
      if (acknowledgement.equalsIgnoreCase("FNA")) {
         incomingMessage.setStatus(REJECTED);
      } else {
         incomingMessage.setStatus(PROCESSED);
      }
      incomingMessage.setEsbMessageLogId(esbMessageId);
      loggerService.logInterfaceIncomingMessage(incomingMessage);
      if (!acknowledgement.equalsIgnoreCase("FNA")) {
         data.setShipmentNumber(data.getAwbPrefix() + data.getAwbSerialNumber());
         if (!data.getActionCode().equalsIgnoreCase("D")) {
        	 LocalDateTime cmddatetime= TenantZoneTime.getZoneDateTime(LocalDateTime.now(), data.getTenantId());
             LocalDate cmddate = cmddatetime.toLocalDate();
        	 data.setCmdRecieved(cmddate);
        	 
            cargoManifestDeclerationMessageDAO.insertCmdInfoIntoCustShipInfo(data);
         }
      }
   }

   private void logOutgoingMessage(BigInteger referenceId, String status,
         CustomCargoManifestDeclarationMessageModel data, String acknowledgement) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      outgoingMessage.setOutMessageId(referenceId);
      outgoingMessage.setChannelSent(HTTP);
      outgoingMessage.setInterfaceSystem(CCN);
      outgoingMessage.setSenderOriginAddress(COSYS);
      outgoingMessage.setMessageType(acknowledgement);
      outgoingMessage.setCarrierCode(data.getCarrierCode());
      outgoingMessage.setFlightNumber(data.getFlightNumber());
      if (!ObjectUtils.isEmpty(data.getFlightDate())) {
         outgoingMessage.setFlightOriginDate(data.getFlightDate().atStartOfDay());
      }
      outgoingMessage.setShipmentNumber(data.getAwbPrefix() + data.getAwbSerialNumber());
      outgoingMessage.setShipmentDate(null);
      outgoingMessage.setRequestedOn(LocalDateTime.now());
      outgoingMessage.setSentOn(LocalDateTime.now());
      outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
      outgoingMessage.setVersionNo(1);
      outgoingMessage.setSequenceNo(1);
      outgoingMessage.setMessageContentEndIndicator(null);
      outgoingMessage.setStatus(status);
      connectorLoggerService.logOutgoingMessage(outgoingMessage);
   }

   public void logOutgoingErrorMessage(BigInteger messageId, HttpStatus httpStatus, String errorMessage) {
      OutgoingMessageErrorLog outgoingErrorMessage = new OutgoingMessageErrorLog();
      //
      outgoingErrorMessage.setOutMessageId(messageId);
      outgoingErrorMessage.setErrorCode("EXCEPTION");
      if (Objects.nonNull(httpStatus)) {
         outgoingErrorMessage.setMessage(ConnectorUtils.getHttpStatusMessage(httpStatus));
      } else {
         if (Objects.nonNull(errorMessage)) {
            outgoingErrorMessage.setMessage(ConnectorUtils.getErrorMessage(errorMessage));
         }
      }
      outgoingErrorMessage.setLineItem(null);
      loggerService.logInterfaceOutgoingErrorMessage(outgoingErrorMessage);
   }

   private BigInteger logOutgoingMessage(String payload, CustomCargoManifestDeclarationMessageModel data,
         String acknowledgement) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      outgoingMessage.setChannelSent(HTTP);
      outgoingMessage.setInterfaceSystem(CCN);
      outgoingMessage.setSenderOriginAddress(COSYS);
      outgoingMessage.setMessageType(acknowledgement);
      outgoingMessage.setCarrierCode(data.getCarrierCode());
      outgoingMessage.setFlightNumber(data.getFlightNumber());
      if (!ObjectUtils.isEmpty(data.getFlightDate())) {
         outgoingMessage.setFlightOriginDate(data.getFlightDate().atStartOfDay());
      }
      outgoingMessage.setShipmentNumber(data.getAwbPrefix() + data.getAwbSerialNumber());
      outgoingMessage.setShipmentDate(null);
      outgoingMessage.setRequestedOn(LocalDateTime.now());
      outgoingMessage.setSentOn(LocalDateTime.now());
      outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
      outgoingMessage.setVersionNo(1);
      outgoingMessage.setSequenceNo(1);
      outgoingMessage.setMessageContentEndIndicator(null);
      if (acknowledgement.equalsIgnoreCase("FNA")) {
         outgoingMessage.setStatus(REJECTED);
      } else {
         outgoingMessage.setStatus(PROCESSED);
      }
      outgoingMessage.setMessage(payload);
      loggerService.logInterfaceOutgoingMessage(outgoingMessage);
      return outgoingMessage.getOutMessageId();
   }

   private void logincomingMessagesOnException(CustomCargoManifestDeclarationMessageModel data,
         String customIncomingCargoManifestDeclarationContentModel, LocalDateTime msgRecievedTime,
         BigInteger esbMessageId) throws CustomException {
      IncomingMessageLog incomingMessage = new IncomingMessageLog();
      incomingMessage.setChannelReceived(HTTP);
      incomingMessage.setInterfaceSystem(CCN);
      incomingMessage.setSenderOriginAddress(COSYS);
      incomingMessage.setMessageType("CMD");
      incomingMessage.setCarrierCode(data.getCarrierCode());
      incomingMessage.setFlightNumber(data.getFlightNumber());
      incomingMessage.setFlightOriginDate(null);
      incomingMessage.setShipmentNumber(data.getAwbPrefix() + data.getAwbSerialNumber());
      incomingMessage.setShipmentDate(null);
      incomingMessage.setReceivedOn(msgRecievedTime);
      incomingMessage.setVersionNo(1);
      incomingMessage.setSequenceNo(1);
      incomingMessage.setMessageContentEndIndicator(null);
      incomingMessage.setStatus(REJECTED);
      incomingMessage.setEsbMessageLogId(esbMessageId);
      incomingMessage.setMessage(customIncomingCargoManifestDeclarationContentModel);
      loggerService.logInterfaceIncomingMessage(incomingMessage);
   }

   private static String getStackTrace(Throwable throwable) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw, true);
      throwable.printStackTrace(pw);
      if (Objects.nonNull(sw.getBuffer())) {
         return sw.getBuffer().toString();
      }
      return null;
   }

}