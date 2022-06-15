package com.ngen.cosys;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

import com.ngen.cosys.events.payload.CAMSModel;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.service.ResendCAMSMessagesService;


@Configuration
//@ImportResource("classpath:/config/camsInterfaceConfig.xml")
public class WebServiceTemplateConfiguration {
	
	private static Logger logger = LoggerFactory.getLogger(WebServiceTemplateConfiguration.class);
	
	@Autowired
	ResendCAMSMessagesService service;
	
	public static Map<String, String> headerMap = new HashMap<>();
	@Bean
    public Jaxb2Marshaller marshallers() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.ibsplc.icargo.business.mailtracking.defaults.types.standard");
        return marshaller;
    }
	
	
    @Bean(name = "wsTemplate")
    public WebServiceTemplate wsTemplate(Jaxb2Marshaller marshaller) {
    	
    	WebServiceTemplate template = new WebServiceTemplate();
    	try {
			CAMSModel model =service.getCAMSInterfaceConfigurations();
			Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
			wss4jSecurityInterceptor.setSecurementActions("UsernameToken");
			wss4jSecurityInterceptor.setSecurementUsername(model.getUserName());
			wss4jSecurityInterceptor.setSecurementPassword(model.getPassword());
			wss4jSecurityInterceptor.setSecurementPasswordType("PasswordText");
			ClientInterceptor[] interceptors = { wss4jSecurityInterceptor };
			template.setInterceptors(interceptors);
			template.setMarshaller(marshaller);
			template.setUnmarshaller(marshaller);
			template.setDefaultUri(model.getEndPointUrl());
			headerMap.put("icargo-identitytoken", model.getToken());
		} catch (CustomException e) {
			logger.debug(e.getMessage());
		}
    	
    	
		
        return template;
    }

}
