package com.ngen.cosys.ulms.config;

import com.ngen.cosys.ulms.adaptor.WSCustomizedMessageReceiverHandlerAdapter;
import com.ngen.cosys.ulms.exception.DetailSoapFaultDefinitionExceptionResolver;
import com.ngen.cosys.ulms.exception.ServiceFaultException;
import com.ngen.cosys.ulms.interceptor.InterfaceLogInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Properties;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceConfig.class);

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setMessageReceiverHandlerAdapterBeanName("customsReceiverHandlerAdapter");
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ulms/*");
    }

    @Bean(name="messageFactory")
    public SaajSoapMessageFactory saajSoapMessageFactory(){
        return new SaajSoapMessageFactory();
    }

    @Bean(name = "customsReceiverHandlerAdapter")
    public WSCustomizedMessageReceiverHandlerAdapter customsReceiverHandlerAdapter(){
        WSCustomizedMessageReceiverHandlerAdapter wsReceiverHandler= new WSCustomizedMessageReceiverHandlerAdapter();

        wsReceiverHandler.setMessageFactory(saajSoapMessageFactory());
        return wsReceiverHandler;
    }

    @Bean(name = "UldPDAssociation")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("UldPDAssociationPort");
        wsdl11Definition.setLocationUri("/ulms");
        wsdl11Definition.setSoapActions(getSoapActions());

        wsdl11Definition.setTargetNamespace("http://www.sats.com.sg/uldAssociationWebservice");
        wsdl11Definition.setSchema(schema);
        return wsdl11Definition;
    }

    private Properties getSoapActions() {
        Properties properties = new Properties();
        properties.setProperty("FlightAssignedULD", "http://www.cosys.ngen.com/namespace/ulmsWebservice/FlightAssignedULD");
        properties.setProperty("FlightULDPDAssociation", "http://www.cosys.ngen.com/namespace/ulmsWebservice/FlightULDPDAssociation");
        properties.setProperty("FlightAssignedULDChanges", "http://www.cosys.ngen.com/namespace/ulmsWebservice/FlightAssignedULDChanges");
        properties.setProperty("ULDReleaseToAgent", "http://www.cosys.ngen.com/namespace/ulmsWebservice/ULDReleaseToAgent");
        return properties;
    }

    @Bean
    public XsdSchema flightULDSchema() {
        return new SimpleXsdSchema(new ClassPathResource("/config/flightULDPDAssociation.xsd"));
    }

    @Bean
    public SoapFaultMappingExceptionResolver exceptionResolver() {
        SoapFaultMappingExceptionResolver exceptionResolver = new DetailSoapFaultDefinitionExceptionResolver();

        SoapFaultDefinition faultDefinition = new SoapFaultDefinition();
        //faultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
        faultDefinition.setFaultCode(new QName("BUSINESS-ERROR"));
        exceptionResolver.setDefaultFault(faultDefinition);

        Properties errorMappings = new Properties();
        errorMappings.setProperty(Exception.class.getName(), SoapFaultDefinition.SERVER.toString());
        errorMappings.setProperty(ServiceFaultException.class.getName(), SoapFaultDefinition.SERVER.toString());
        exceptionResolver.setExceptionMappings(errorMappings);
        exceptionResolver.setOrder(1);
        return exceptionResolver;
    }

    @Bean
    public EndpointInterceptor getLogEndpointInterceptor(){
        return new InterfaceLogInterceptor();
    }
    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        super.addInterceptors(interceptors);
        interceptors.add(getLogEndpointInterceptor());
    }
}
