package com.ngen.cosys.scheduler.config;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.ngen.cosys.multitenancy.job.constants.SchedulerConstants;
import com.ngen.cosys.multitenancy.job.listener.CosysSchedulerListener;
import com.ngen.cosys.multitenancy.job.listener.CosysTriggerListener;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.scheduler.model.BatchJobModel;
import com.ngen.cosys.scheduler.service.JobService;

@Configuration
public class QuartzSchedulerConfig {
	//
	private static final String TENANTS_SCHEDULER_BEAN = "tenantsSchedulerBean";
	private static final String SCHEDULER_FACTORY_BEAN_S = "schedulerFactoryBean%s";

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private JobService jobService;

	/**
	 * create scheduler
	 */
	@Bean(name = TENANTS_SCHEDULER_BEAN)
	public String schedulerFactoryBean() throws IOException {
		List<String> availableTenants = CosysApplicationContext.getAvailableTenants();
		//
		for (String tenantId : availableTenants) {
			SchedulerFactoryBean factory = new SchedulerFactoryBean();
			AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
			Properties quartzProperties = quartzProperties();
			String schedulerName = String.format("%s.%s", tenantId, SchedulerConstants.SCHEDULER);
			String instanceName = String.format("%s.%s", tenantId, CosysApplicationContext.getHostName());
			//
			quartzProperties.put(SchedulerConstants.ORG_QUARTZ_SCHEDULER_INSTANCE_NAME, instanceName);
			//
			factory.setOverwriteExistingJobs(true);
			factory.setQuartzProperties(quartzProperties);
			factory.setStartupDelay(10);
			factory.setSchedulerName(schedulerName);
			// No Job Store
			factory.setDataSource(CosysApplicationContext.getTenantDataSource(tenantId));

			// Register listeners to get notification on Trigger miss fire etc
			factory.setGlobalTriggerListeners(new CosysTriggerListener(tenantId));
			jobFactory.setApplicationContext(applicationContext);
			factory.setJobFactory(jobFactory);
			factory.setWaitForJobsToCompleteOnShutdown(true);
			factory.setSchedulerListeners(new CosysSchedulerListener(tenantId) {
				/**
				 * Scheduler Started
				 */
				@Override
				public void schedulerStarted() {
					try {
						@SuppressWarnings("unchecked")
						List<BatchJobModel> jobList = (List<BatchJobModel>) CosysApplicationContext
								.switchDbAndExecute(tenantId, () -> {
									return jobService.getTenantJobs();
								});
						// Setup Jobs
						jobService.setupJobs(tenantId, factory, jobList);
					} catch (Throwable e) {
						// Do Nothing
					}
					// Log It
					super.schedulerStarted();
				}
			});
			try {
				// Update
				factory.afterPropertiesSet();
				// Update Scheduler Context with Tenant Id
				factory.getScheduler().getContext().put(SchedulerConstants.SCHEDULED_TENANT, tenantId);
				// Register Bean
				CosysApplicationContext.registerSingletonBean(String.format(SCHEDULER_FACTORY_BEAN_S, tenantId),
						factory);
			} catch (Exception e) {
				// Do Nothing
			}
		}
		return TENANTS_SCHEDULER_BEAN;
	}

	/**
	 * Configure quartz using properties file
	 */
	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/batches/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

}