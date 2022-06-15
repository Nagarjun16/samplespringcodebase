package com.ngen.cosys.ulms.adaptor;

import com.ngen.cosys.ulms.constant.ErrorCode;
import com.ngen.cosys.ulms.model.ServiceStatus;
import com.ngen.cosys.ulms.exception.ServiceFaultException;
import com.ngen.cosys.ulms.service.LogWebserviceCallService;
import com.ngen.cosys.ulms.service.ULMSErrorMessageService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ws.FaultAwareWebServiceMessage;
import org.springframework.ws.InvalidXmlException;
import org.springframework.ws.NoEndpointFoundException;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.DefaultMessageContext;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.transport.EndpointAwareWebServiceConnection;
import org.springframework.ws.transport.FaultAwareWebServiceConnection;
import org.springframework.ws.transport.WebServiceConnection;
import org.springframework.ws.transport.WebServiceMessageReceiver;
import org.springframework.ws.transport.context.DefaultTransportContext;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.WebServiceMessageReceiverHandlerAdapter;
import org.springframework.ws.transport.support.TransportUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WSCustomizedMessageReceiverHandlerAdapter extends WebServiceMessageReceiverHandlerAdapter {


    @Autowired
    LogWebserviceCallService logService;

    @Autowired
    ULMSErrorMessageService errorMessageService;

    private String buildSOAPResponse(String faultCode, String faultString, ServiceStatus serviceStatus) {
        return "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" + "\r\n" +
                "   <SOAP-ENV:Header/>" + "\r\n" +
                "   <SOAP-ENV:Body>" + "\r\n" +
                "       <SOAP-ENV:Fault>" + "\r\n" +
                "           <faultcode>" + faultCode + "</faultcode>" + "\r\n" +
                "           <faultstring xml:lang=\"en\">" + faultString + "</faultstring>" + "\r\n" +
                "           <detail>" + "\r\n" +
                "                <statusCode>" + serviceStatus.getStatusCode() + "</statusCode>" + "\r\n" +
                "                <message>" + "<![CDATA[" + serviceStatus.getMessage() + "]]></message>" + "\r\n" +
                "           </detail>" + "\r\n" +
                "       </SOAP-ENV:Fault>" + "\r\n" +
                "   </SOAP-ENV:Body>" + "\r\n" +
                "</SOAP-ENV:Envelope>";
    }

    protected void overriddenHandleConnection(WebServiceConnection connection, WebServiceMessageReceiver receiver)
            throws Exception {

        TransportContext previousTransportContext = TransportContextHolder.getTransportContext();
        TransportContextHolder.setTransportContext(new DefaultTransportContext(connection));

        try {
            WebServiceMessage request = connection.receive(getMessageFactory());
            MessageContext messageContext = new DefaultMessageContext(request, getMessageFactory());
            receiver.receive(messageContext);
            if (messageContext.hasResponse()) {
                WebServiceMessage response = messageContext.getResponse();
                if (response instanceof FaultAwareWebServiceMessage &&
                        connection instanceof FaultAwareWebServiceConnection) {
                    FaultAwareWebServiceMessage faultResponse = (FaultAwareWebServiceMessage) response;
                    FaultAwareWebServiceConnection faultConnection = (FaultAwareWebServiceConnection) connection;
                    faultConnection.setFault(faultResponse.hasFault());
                }
                connection.send(messageContext.getResponse());
            }
        }
        catch (NoEndpointFoundException ex) {
            if (connection instanceof EndpointAwareWebServiceConnection) {
                ((EndpointAwareWebServiceConnection) connection).endpointNotFound();
            }
            throw ex;
        }
        finally {
            TransportUtils.closeConnection(connection);
            TransportContextHolder.setTransportContext(previousTransportContext);
        }
    }

    private void logInterfaceError(HttpServletRequest  httpServletRequest,String response){
        String request = ((WSCustomHttpServletRequestWrapper)httpServletRequest).getBody();
        logService.insertMessageLog("IN",null,null,null,request,null);
        logService.insertMessageLog("OUT",null,null,null,response,null);
    }

    @Override
    public ModelAndView handle(HttpServletRequest httpServletRequest,
                               HttpServletResponse httpServletResponse,
                               Object handler) throws Exception {
        try {
            WSCustomHttpServletRequestWrapper requestWrapper = new WSCustomHttpServletRequestWrapper(httpServletRequest);
            return super.handle(requestWrapper, httpServletResponse, handler);
        } catch (ServiceFaultException ex) {
            handleEndpointNotFoundException(httpServletRequest, httpServletResponse, handler, ex);
        } catch (Exception ex){
            handleGenericErrorException(httpServletRequest,httpServletResponse,handler,ex);
        }
        return null;
    }

    @Override
    protected void handleNoEndpointFoundException(NoEndpointFoundException ex,
                                                  WebServiceConnection connection,
                                                  WebServiceMessageReceiver receiver) throws Exception {
        super.handleNoEndpointFoundException(ex, connection, receiver);
        ServiceStatus serviceStatus = errorMessageService.buildServiceStatus(ErrorCode.ENDPOINT_NOT_FOUND);



        throw new ServiceFaultException("ENDPOINT-NOT-FOUND", serviceStatus);
    }

    @Override
    protected void handleInvalidXmlException(HttpServletRequest httpServletRequest,
                                             HttpServletResponse httpServletResponse,
                                             Object handler,
                                             InvalidXmlException ex) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        ServiceStatus serviceStatus = errorMessageService.buildServiceStatus(ErrorCode.INVALID_XML_FORMAT);



        String errorXml = buildSOAPResponse("CLIENT", "INVALID-XML", serviceStatus);

        logInterfaceError(httpServletRequest,errorXml);

        httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        httpServletResponse.setContentType("text/xml");
        httpServletResponse.getWriter().write(errorXml);
        httpServletResponse.getWriter().flush();
    }

    private void handleEndpointNotFoundException(HttpServletRequest httpServletRequest,
                                                 HttpServletResponse httpServletResponse,
                                                 Object handler,
                                                 ServiceFaultException ex) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        ServiceStatus serviceStatus = errorMessageService.buildServiceStatus(ErrorCode.ENDPOINT_NOT_FOUND);


        String errorXml = buildSOAPResponse("SERVER", "ENDPOINT-NOT-FOUND", ex.getServiceStatus());
        String requestBody = ((WSCustomHttpServletRequestWrapper)httpServletRequest).getBody();

        logInterfaceError(httpServletRequest,errorXml);

        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        httpServletResponse.setContentType("text/xml");
        httpServletResponse.getWriter().write(errorXml);
        httpServletResponse.getWriter().flush();
    }

    private void handleGenericErrorException(HttpServletRequest httpServletRequest,
                                             HttpServletResponse httpServletResponse,
                                             Object handler,
                                             Exception ex) throws IOException {
        ServiceStatus serviceStatus = errorMessageService.buildServiceStatus(ErrorCode.GENERIC_ERROR);

        serviceStatus.setMessage(ex.getMessage());

        String errorXml = buildSOAPResponse("SERVER", "GENERIC-ERROR", serviceStatus);

        logInterfaceError(httpServletRequest,errorXml);

        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        httpServletResponse.setContentType("text/xml");
        httpServletResponse.getWriter().write(errorXml);
        httpServletResponse.getWriter().flush();
    }
}
