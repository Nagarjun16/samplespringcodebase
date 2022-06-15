/**
 * 
 * ServiceTemplateApplication.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 28 December, 2017 NIIT -
 */
package com.ngen.cosys;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.binder.jms.activemq.config.ActiveMQJmsConfiguration;
import org.springframework.context.annotation.Import;

import com.ngen.cosys.audit.NgenAuditConfig;
import com.ngen.cosys.bootstrap.CosysApplication;
import com.ngen.cosys.events.config.EventsStreamConfig;
import com.ngen.cosys.multitenancy.annotation.EnableMultiTenancy;

/**
 * This class is the heart of Masters module. It prepares the application
 * context and renders the application.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableMultiTenancy
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class })
@Import(value = { ActiveMQJmsConfiguration.class,
		EventsStreamConfig.class, NgenAuditConfig.class  })
public class SatsSgBatchesApplication extends CosysApplication {

	public static void main(String[] args) {
		run(SatsSgBatchesApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SatsSgBatchesApplication.class);
	}

}