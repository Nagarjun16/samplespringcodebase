package com.ngen.cosys.scheduler.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;


import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.job.CosysJob;
import com.ngen.cosys.multitenancy.timezone.support.TenantTimeZoneUtility;
import com.ngen.cosys.scheduler.config.PersistableCronTriggerFactoryBean;
import com.ngen.cosys.timezone.enums.TenantTimeZone;

public class JobUtil {

   private static final Logger LOG = LoggerFactory.getLogger(JobUtil.class);

   // Date Format
   static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
   // Formatter to convert date to local date time
   final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

   private JobUtil() {
      // Do not allow object creation
   }

   /**
    * Create Quartz Job.
    * 
    * @param jobClass
    *           Class whose executeInternal() method needs to be called.
    * @param isDurable
    *           Job needs to be persisted even after completion. if true, job will
    *           be persisted, not otherwise.
    * @param context
    *           Spring application context.
    * @param jobName
    *           Job name.
    * @param jobGroup
    *           Job group.
    * 
    * @return JobDetail object.
    */
   protected static JobDetail createJob(Class<? extends CosysJob> jobClass, boolean isDurable,
         ApplicationContext context, String jobName, String jobGroup, JobDataMap jobDataMap) {
      JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
      factoryBean.setJobClass(jobClass);
      factoryBean.setDurability(isDurable);
      factoryBean.setApplicationContext(context);
      factoryBean.setName(jobName);
      factoryBean.setGroup(jobGroup);

      // set job data map
      factoryBean.setJobDataMap(jobDataMap);

      // invoke properties set
      factoryBean.afterPropertiesSet();

      return factoryBean.getObject();
   }

   /**
    * Create cron trigger.
    * 
    * @param triggerName
    *           Trigger name.
    * @param triggerGroup
    *           Trigger Group.
    * @param startTime
    *           Trigger start time.
    * @param cronExpression
    *           Cron expression.
    * @param misFireInstruction
    *           Misfire instruction (what to do in case of misfire happens).
    * @param jobDataMap.
    * 
    * @return Trigger.
    */
   protected static Trigger createCronTrigger(String triggerName, String triggerGroup, LocalDateTime startTime,
         String cronExpression, int misFireInstruction) {
      PersistableCronTriggerFactoryBean factoryBean = new PersistableCronTriggerFactoryBean();
      factoryBean.setName(triggerName);
      ZonedDateTime zdt = startTime.atZone(ZoneId.systemDefault());
      factoryBean.setStartTime(Date.from(zdt.toInstant()));
      factoryBean.setCronExpression(cronExpression);
      factoryBean.setMisfireInstruction(misFireInstruction);
      factoryBean.setGroup(triggerGroup);
      try {
         factoryBean.afterPropertiesSet();
      } catch (ParseException e) {
         LOG.error("CreateCronTrigger Parser Exception", e);
      }
      return factoryBean.getObject();
   }

   /**
    * Create a Single trigger.
    * 
    * @param triggerName
    *           Trigger name.
    * @param startTime
    *           Trigger start time.
    * @param misFireInstruction
    *           Misfire instruction (what to do in case of misfire happens).
    * @param jobDataMap.
    * 
    * @return Trigger.
    */
   protected static Trigger createSingleTrigger(String triggerName, String triggerGroup, LocalDateTime startTime,
         int misFireInstruction) {
      SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
      factoryBean.setName(triggerName);
      ZonedDateTime zdt = startTime.atZone(ZoneId.systemDefault());
      factoryBean.setStartTime(Date.from(zdt.toInstant()));
      factoryBean.setMisfireInstruction(misFireInstruction);
      factoryBean.setRepeatCount(0);
      factoryBean.afterPropertiesSet();
      factoryBean.setGroup(triggerGroup);
      return factoryBean.getObject();
   }

   /**
    * Method to convert date to local date time.
    * 
    * @param date.
    * @return LocalDateTime.
    */
   public static LocalDateTime convertDateToLocalDateTime(Date date) {
      if (date != null) {
         ZoneId defaultZoneId = ZoneId.systemDefault();
         Instant instant = date.toInstant();
         return instant.atZone(defaultZoneId).toLocalDateTime();
      }
      return null;
   }
   
   /**
    * Get Zone date time by Tenant ID
    *   
    *   - Passed LocalDateTime argument considered as UTC date time
    *       LocalDateTime.now(ZoneOffset.UTC)
    * 
    *   - Zone info date time converted by Tenant
    * 
    * @param dateTime
    * @param tenantID
    * @return
    */
   public static LocalDateTime getZoneDateTime(LocalDateTime dateTime) {
      // 
      if (Objects.isNull(dateTime)) {
         return null;
      }
      String tenantID = null; 
      if (Objects.nonNull(TenantContext.get()) && Objects.nonNull(TenantContext.get().getTenantId())) {
          tenantID = TenantContext.get().getTenantId();
      } else {
          tenantID = TenantZone.SIN.getAirportCode();
      }
      // Retrieved LocalDateTime doesn't even associate with any Timezone so treated as UTC
      ZonedDateTime dateTimeUTC = dateTime.atZone(ZoneOffset.UTC);
      ZoneId zoneID = getTenantZone(tenantID);
      if (Objects.nonNull(zoneID)) {
         return dateTimeUTC.withZoneSameInstant(zoneID).toLocalDateTime();
      }
      //
      return dateTime;
   }
   
   /**
    * @param tenantID
    * @return
    */
   private static ZoneId getTenantZone(String tenantID) {
      
      return TenantTimeZoneUtility.getTenantZoneId();
   }
}