package com.ngen.cosys.ulms.interceptor;

import com.ngen.cosys.ulms.service.LogWebserviceCallService;
import com.ngen.cosys.ulms.utility.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.MethodEndpoint;

import java.util.HashMap;
import java.util.Map;

@Component
public class InterfaceLogInterceptor implements EndpointInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceLogInterceptor.class);

    @Autowired
    LogWebserviceCallService logService;

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {

        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {

        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {
        String methodName = ((MethodEndpoint)endpoint).getMethod().getName();
        String subMessageType= Utilities.getSubMessageType(methodName);
        logService.insertMessageLog(messageContext, endpoint, subMessageType, ex);
    }
}
