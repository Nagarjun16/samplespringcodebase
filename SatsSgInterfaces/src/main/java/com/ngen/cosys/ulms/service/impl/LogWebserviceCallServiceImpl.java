package com.ngen.cosys.ulms.service.impl;

import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ulms.dao.ULMSAssignedLoadedULDListDao;
import com.ngen.cosys.ulms.interceptor.InterfaceLogInterceptor;
import com.ngen.cosys.ulms.model.ULMSInterfaceDetail;
import com.ngen.cosys.ulms.service.LogWebserviceCallService;
import com.ngen.cosys.ulms.utility.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service("LogWebserviceCallService")
public class LogWebserviceCallServiceImpl implements LogWebserviceCallService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceLogInterceptor.class);

    @Autowired
    private ApplicationLoggerService loggerService;

    @Autowired
    ULMSAssignedLoadedULDListDao uldAssignedLoadedInFlightDao;

    private static String getHttpStatusMessage(HttpStatus httpStatus) {
        //
        StringBuilder message = new StringBuilder();
        message.append("{Statuscode=[").append(String.valueOf(httpStatus.value())).append("], Message=[");
        message.append(httpStatus.getReasonPhrase()).append("]}");
        if (message.length() > 100) {
            return String.valueOf(message.substring(0, 100));
        }
        return message.toString();
    }

    private static String getErrorMessage(String errorMessage) {
        if (Objects.nonNull(errorMessage)) {
            if (errorMessage.length() > 100) {
                return errorMessage.substring(0, 100);
            }
            return errorMessage;
        }
        return null;
    }

    private static String prettyPrintXml(String sourceXml) {
        if (StringUtils.isEmpty(sourceXml)) {
            return sourceXml;
        }
        try {
            Transformer serializer = SAXTransformerFactory.newInstance().newTransformer();

            serializer.setOutputProperty(OutputKeys.INDENT, "yes");

            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            Source xmlSource = new SAXSource(new org.xml.sax.InputSource(new ByteArrayInputStream(sourceXml.getBytes())));
            StreamResult res = new StreamResult(new ByteArrayOutputStream());

            serializer.transform(xmlSource, res);

            return new String(((ByteArrayOutputStream) res.getOutputStream()).toByteArray());
        } catch (Exception e) {
            return sourceXml;
        }
    }

    @Override
    public String getMessageContent(WebServiceMessage message) throws IOException {
        if (Objects.isNull(message)) {
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        message.writeTo(bos);
        return bos.toString("UTF-8");
    }

    private BigInteger logOutgoingMessage(String carrierCode,
                                          String flightNumber,
                                          LocalDateTime flightOriginDate, Object payload, String subMessageType) {
        OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
        outgoingMessage.setChannelSent("HTTP");
        outgoingMessage.setInterfaceSystem("ULMS");
        outgoingMessage.setSenderOriginAddress("COSYS");
        outgoingMessage.setMessageType("ULMS");
        outgoingMessage.setSubMessageType(subMessageType);
        outgoingMessage.setCarrierCode(carrierCode);
        outgoingMessage.setFlightNumber(flightNumber);
        outgoingMessage.setFlightOriginDate(flightOriginDate);
        outgoingMessage.setShipmentNumber(null);
        outgoingMessage.setShipmentDate(null);
        outgoingMessage.setRequestedOn(LocalDateTime.now());
        outgoingMessage.setSentOn(LocalDateTime.now());
        outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
        outgoingMessage.setVersionNo(1);
        outgoingMessage.setSequenceNo(1);
        outgoingMessage.setMessageContentEndIndicator(null);
        outgoingMessage.setCreatedBy("COSYS");
        outgoingMessage.setCreatedOn(LocalDateTime.now());
        outgoingMessage.setStatus("SENT");
        outgoingMessage.setMessage((String) payload);
        loggerService.logInterfaceOutgoingMessage(outgoingMessage);
        return outgoingMessage.getOutMessageId();
    }

    private BigInteger logIncomingMessage(String carrierCode,
                                          String flightNumber,
                                          LocalDateTime flightOriginDate, Object payload, String subMessageType) {
        IncomingMessageLog incomingMessage = new IncomingMessageLog();
        incomingMessage.setChannelReceived("HTTP");
        incomingMessage.setInterfaceSystem("ULMS");
        incomingMessage.setSenderOriginAddress("ULMS");
        incomingMessage.setMessageType("ULMS");
        incomingMessage.setSubMessageType(subMessageType);
        incomingMessage.setMessage((String) payload);
        incomingMessage.setCarrierCode(carrierCode);
        incomingMessage.setFlightNumber(flightNumber);
        incomingMessage.setFlightOriginDate(flightOriginDate);
        incomingMessage.setShipmentNumber(null);
        incomingMessage.setShipmentDate(null);
        incomingMessage.setReceivedOn(LocalDateTime.now());
        incomingMessage.setVersionNo(1);
        incomingMessage.setSequenceNo(1);
        incomingMessage.setMessageContentEndIndicator(null);
        incomingMessage.setStatus("RECEIVED");
        incomingMessage.setCreatedBy("ULMS");
        incomingMessage.setCreatedOn(LocalDateTime.now());
        incomingMessage.setCreatedBy("COSYS");

        loggerService.logInterfaceIncomingMessage(incomingMessage);
        return incomingMessage.getInMessageId();
    }

    public BigInteger insertOutgoingErrorMessage(BigInteger messageId, HttpStatus httpStatus, String errorMessage) {
        OutgoingMessageErrorLog outgoingErrorMessage = new OutgoingMessageErrorLog();

        outgoingErrorMessage.setOutMessageId(messageId);
        outgoingErrorMessage.setErrorCode("EXCEPTION");
        if (Objects.nonNull(httpStatus)) {
            outgoingErrorMessage.setMessage(getHttpStatusMessage(httpStatus));
        } else {
            if (Objects.nonNull(errorMessage)) {
                outgoingErrorMessage.setMessage(getErrorMessage(errorMessage));
            }
        }
        outgoingErrorMessage.setLineItem(null);
        loggerService.logInterfaceOutgoingErrorMessage(outgoingErrorMessage);

        return outgoingErrorMessage.getOutMessageId();
    }

    public BigInteger insertIncomingErrorMessage(BigInteger messageId, HttpStatus httpStatus, String errorMessage) {
        IncomingMessageErrorLog incomingMessageErrorLog = new IncomingMessageErrorLog();

        incomingMessageErrorLog.setInMessageId(messageId);
        incomingMessageErrorLog.setErrorCode("EXCEPTION");
        if (Objects.nonNull(httpStatus)) {
            incomingMessageErrorLog.setMessage(getHttpStatusMessage(httpStatus));
        } else {
            if (Objects.nonNull(errorMessage)) {
                incomingMessageErrorLog.setMessage(getErrorMessage(errorMessage));
            }
        }
        incomingMessageErrorLog.setLineItem(null);
        loggerService.logInterfaceIncomingErrorMessage(incomingMessageErrorLog);

        return incomingMessageErrorLog.getInMessageErrorId();
    }

    @Override
    public BigInteger insertMessageLog(String inOutFlag, String carrierCode, String flightNumber, LocalDateTime flightOriginDate, Object payload, String subMessageType) {
        if ("IN".equals(inOutFlag)) {
            return logIncomingMessage(carrierCode, flightNumber, flightOriginDate, payload, subMessageType);
        }
        return logOutgoingMessage(carrierCode, flightNumber, flightOriginDate, payload, subMessageType);
    }

    private ULMSInterfaceDetail getFlightInformation(MessageContext messageContext) {
        ULMSInterfaceDetail ulmsInterfaceDetail = null;
        String flightKey = null, flightDate = null, importExportFlag = null;

        try {
            DOMSource source = (DOMSource) messageContext.getRequest().getPayloadSource();
            NodeList nodeList = source.getNode().getChildNodes();

            if (Objects.nonNull(nodeList)) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) nodeList.item(i);
                        if (element.getLocalName().equalsIgnoreCase("flightNumber")) {
                            flightKey = element.getFirstChild().getTextContent();
                        } else if (element.getLocalName().equalsIgnoreCase("flightDate")) {
                            flightDate = element.getFirstChild().getTextContent();
                        } else if (element.getLocalName().equalsIgnoreCase("importExportFlag")) {
                            importExportFlag = element.getFirstChild().getTextContent();
                        }
                    }
                }

                ulmsInterfaceDetail = new ULMSInterfaceDetail();
                ulmsInterfaceDetail.setFlightNumber(flightKey);
                ulmsInterfaceDetail.setFlightDate(flightDate);
                ulmsInterfaceDetail.setImportExportFlag(importExportFlag);

                ulmsInterfaceDetail = uldAssignedLoadedInFlightDao.getFlightInfo(ulmsInterfaceDetail);
            }
        } catch (Exception ex) {
            ulmsInterfaceDetail = null;
            LOGGER.error(ex.getMessage());
        }

        return ulmsInterfaceDetail;
    }

    @Override
    public void insertMessageLog(MessageContext messageContext, Object endpoint, String subMessageType, Exception ex) {
        String carrierCode = null, flightNumber = null;
        LocalDateTime flightOriginDate = null;

        try {
            String requestContent = getMessageContent(messageContext.getRequest());
            String responseContent = getMessageContent(messageContext.getResponse());

            if ("FltULDList".equals(subMessageType) || "NMEULD".equals(subMessageType)) {
                ULMSInterfaceDetail ulmsInterfaceDetail = getFlightInformation(messageContext);
                if (ulmsInterfaceDetail != null) {
                    carrierCode = ulmsInterfaceDetail.getCarrierCode();
                    flightNumber = ulmsInterfaceDetail.getFlightNumberOnly();
                    flightOriginDate = ulmsInterfaceDetail.getFlightOriginDate();
                }
            }

            requestContent = prettyPrintXml(requestContent);
            responseContent = prettyPrintXml(responseContent);

            BigInteger incomingMessageLogId = insertMessageLog("IN", carrierCode,
                    flightNumber, flightOriginDate, requestContent, subMessageType);
            BigInteger outgoingMessageLogId = insertMessageLog("OUT", carrierCode,
                    flightNumber, flightOriginDate, responseContent, subMessageType);

            if (Objects.nonNull(ex)) {
                insertIncomingErrorMessage(incomingMessageLogId, null, requestContent);
                insertOutgoingErrorMessage(outgoingMessageLogId, null, requestContent);
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
