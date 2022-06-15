package com.ngen.cosys.ulms.exception;

import com.ngen.cosys.ulms.model.ObjectFactory;
import com.ngen.cosys.ulms.model.ServiceStatus;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.AbstractEndpointExceptionResolver;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import javax.xml.namespace.QName;

public class DetailSoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {

    private static final QName CODE = new QName("statusCode");
    private static final QName MESSAGE = new QName("message");

    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
        logger.warn("Exception processed ", ex);
        System.out.println("---------------------------");
        System.out.println(ex.toString());
        System.out.println("---------------------------");

        if (ex instanceof ServiceFaultException) {

            ServiceStatus status = ((ServiceFaultException) ex).getServiceStatus();
            SoapFaultDetail detail = fault.addFaultDetail();
            ((SoapFaultDetail) detail).addFaultDetailElement(CODE).addText(status.getStatusCode());
            ((SoapFaultDetail) detail).addFaultDetailElement(MESSAGE).addText(status.getMessage());
        }
    }

}