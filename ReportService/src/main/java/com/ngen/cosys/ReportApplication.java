/**
 * Report Application
 */
package com.ngen.cosys;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import com.ngen.cosys.bootstrap.CosysApplication;
import com.ngen.cosys.multitenancy.annotation.EnableMultiTenancy;

/**
 * Report Application
 */
@SpringBootApplication(exclude = { JmsAutoConfiguration.class, ActiveMQAutoConfiguration.class })
@EnableDiscoveryClient
@EnableMultiTenancy
@Import({ WebSocketConfiguration.class })
public class ReportApplication extends CosysApplication {

	/**
	 * Start Point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		run(ReportApplication.class, args);
	}

	/**
	 * @see org.springframework.boot.web.support.SpringBootServletInitializer#configure(org.springframework.boot.builder.SpringApplicationBuilder)
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ReportApplication.class);
	}
}