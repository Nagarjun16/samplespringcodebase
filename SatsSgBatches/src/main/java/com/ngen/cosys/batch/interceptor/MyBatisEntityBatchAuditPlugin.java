/**
 * 
 * MyBatisEntityBatchAuditPlugin.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 24 February, 2018 NIIT -
 */
package com.ngen.cosys.batch.interceptor;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.multitenancy.context.TenantContext;


/**
 * 
 * This is iBatis plug-in interceptor for populating audit trail information
 * like created and modified.
 * 
 * Every call to database would be intercepted and if command matches to
 * INSERT/UPDATE then data would be populated.
 * 
 * Refer iBatis API Interceptor interface for more information
 * 
 * Usage:
 * 
 * <!-- mybatis-config.xml -->
 * 
 * <plugins>
 * 
 * <plugin interceptor=
 * "com.ngen.cosys.framework.mybatis.interceptors.MyBatisEntityAuditPlugin" />
 * </plugins>
 *
 * @author NIIT Technologies Pvt Ltd
 * 
 * @version 1.0
 * 
 * @since 24/02/2018
 * 
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
@Component
public class MyBatisEntityBatchAuditPlugin implements Interceptor {

   /**
    * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
    * 
    */
   @Override
   public Object intercept(Invocation invocation) throws Throwable {
      MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
      Object entity = invocation.getArgs()[1];

      if (entity instanceof BaseBO && (mappedStatement.getSqlCommandType() == SqlCommandType.INSERT
            || mappedStatement.getSqlCommandType() == SqlCommandType.UPDATE)) {
         // Get the logged in user from Thread Local
         String loggedInUser = null;
         if (!ObjectUtils.isEmpty(TenantContext.get())) {
            loggedInUser = TenantContext.get().getRequestUser();
         }

         // If it is empty check whether is populated in BaseBO itself
         if (StringUtils.isEmpty(loggedInUser) && !StringUtils.isEmpty(((BaseBO) entity).getLoggedInUser())) {
            loggedInUser = ((BaseBO) entity).getLoggedInUser();
         }

         // Set the default user if no user found
         if (StringUtils.isEmpty(loggedInUser)) {
            loggedInUser = "BATCHUSER";
         }

         // Base BO properties Injected by annotation
         // If empty then set the default user
         if (StringUtils.isEmpty(((BaseBO) entity).getCreatedBy())) {
            ((BaseBO) entity).setCreatedBy(loggedInUser);
         }

         if (Objects.isNull(((BaseBO) entity).getCreatedOn())) {
            ((BaseBO) entity).setCreatedOn(LocalDateTime.now());
         }

         if (StringUtils.isEmpty(((BaseBO) entity).getModifiedBy())) {
            ((BaseBO) entity).setModifiedBy(loggedInUser);
         }

         if (Objects.isNull(((BaseBO) entity).getModifiedOn())) {
            ((BaseBO) entity).setModifiedOn(LocalDateTime.now());
         }
      }
      return invocation.proceed();
   }

   /**
    * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
    * 
    */
   @Override
   public Object plugin(Object target) {
      return Plugin.wrap(target, this);
   }

   /**
    * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
    * 
    */
   @Override
   public void setProperties(Properties properties) {
      // There are no properties changes required for this implementation
   }

   /**
    * Convert Query Object
    * 
    * @param fields
    * @param obj
    * @throws Exception
    */
   private void convertQueryObject(List<Field> fields, Object obj) throws Exception {
      //
      if (!CollectionUtils.isEmpty(fields)) {
         for (Field field : fields) {
            field.setAccessible(true);
            if (Objects.equals(LocalDateTime.class, field.getType())
                  && Optional.ofNullable(field.get(obj)).isPresent()) {
               LocalDateTime dateTime = (LocalDateTime) field.get(obj);
               field.set(obj, getDateTimeUTC(dateTime));
            } else if (Collection.class.isAssignableFrom(field.getType())) {
               //
               List<Object> collectionObjects = (List<Object>) field.get(obj);
               if (!CollectionUtils.isEmpty(collectionObjects)) {
                  for (Object collectionObject : collectionObjects) {
                     //
                     List<Field> collectionObjectFields = Arrays
                           .asList(collectionObject.getClass().getDeclaredFields());
                     convertQueryObject(collectionObjectFields, collectionObject);
                     superClazzFields(collectionObject);
                  }
               }
            } else if (Objects.equals(BaseBO.class, field.getType())) {
               //
               Object nestedObject = field.get(obj);
               if (Optional.ofNullable(nestedObject).isPresent()) {
                  List<Field> nestedFields = Arrays.asList(nestedObject.getClass().getDeclaredFields());
                  convertQueryObject(nestedFields, nestedObject);
                  superClazzFields(nestedObject);
               }
            }
         }
      }
   }

   /**
    * Get fields by reading super class
    * 
    * @param superObject
    * @throws Exception
    */
   private void superClazzFields(Object superObject) throws Exception {
      //
      if (Objects.equals(superObject.getClass().getSuperclass(), BaseBO.class)) {
         List<Field> fields = Arrays.asList(superObject.getClass().getSuperclass().getDeclaredFields());
         convertQueryObject(fields, superObject);
      }
   }

   /**
    * Date Time UTC
    * 
    * @param dateTime
    * @return
    */
   private LocalDateTime getDateTimeUTC(LocalDateTime dateTime) {
      ZonedDateTime dateTimeUTC = dateTime.atZone(ZoneId.systemDefault());
      return dateTimeUTC.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
   }
}