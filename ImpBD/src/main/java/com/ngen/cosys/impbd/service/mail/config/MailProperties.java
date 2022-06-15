package com.ngen.cosys.impbd.service.mail.config;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ngen.cosys.impbd.service.mail.enums.MailTypeUtils;
import com.ngen.cosys.mail.config.MailProps;

@Component
public class MailProperties {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailProperties.class);
	private static final String TIMEOUT = "30000";
	private static final String TLS_1_2 = "TLSv1.2";
	public static Properties properties = new Properties();

	/**
	 * SMTP Mail configuration
	 */
	@PostConstruct
	private void initialize() {
		LOGGER.debug("Cloud Interfaces Mail Properties :: initialize {}");
		//
		properties.setProperty(MailTypeUtils.SMTP_AUTH.getValue(), String.valueOf(true));
		properties.setProperty(MailTypeUtils.SMTP_SSL.getValue(), String.valueOf(false));
		properties.setProperty(MailTypeUtils.SMTP_TSL.getValue(), String.valueOf(true));
		properties.setProperty(MailTypeUtils.SMTP_CONNECTION_TIMEOUT.getValue(), TIMEOUT);
		properties.setProperty(MailTypeUtils.SMTP_TIMEOUT.getValue(), TIMEOUT);
		properties.setProperty(MailTypeUtils.SMTP_TLS_ENABLE.getValue(), String.valueOf(true));
		properties.setProperty(MailTypeUtils.SMTP_MAIL_DEBUG_AUTH.getValue(), String.valueOf(true));
		properties.setProperty(MailTypeUtils.SMTP_SSL_TRANSPORT_PROTOCOL.getValue(), TLS_1_2);
		//
		LOGGER.debug("Cloud Interfaces Mail Properties :: initialization loaded {}");
	}

	/**
	 * Set mail configuration in mail properties
	 * 
	 * @param mailProps
	 */
	public static void setMailConfig(MailProps mailProps) {
		LOGGER.debug("Cloud Interfaces Mail Properties :: set mail configuration {}");
		properties.setProperty(MailTypeUtils.SMTP_HOST.getValue(), mailProps.getSmtpHost());
		properties.setProperty(MailTypeUtils.SMTP_PORT.getValue(), mailProps.getSmptPort());
		properties.setProperty(MailTypeUtils.SMTP_USERNAME.getValue(), mailProps.getUsername());
		properties.setProperty(MailTypeUtils.SMTP_PASSWORD.getValue(), mailProps.getPassword());
	}

}
