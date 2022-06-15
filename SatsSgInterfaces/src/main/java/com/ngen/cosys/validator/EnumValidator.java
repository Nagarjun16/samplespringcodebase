package com.ngen.cosys.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = { EnumValidatorImpl.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValidator {
   public abstract String message() default "Invalid value. Provide A Valid Value";

   public abstract Class<?>[] groups() default {};

   public abstract Class<? extends Payload>[] payload() default {};

   public abstract Class<? extends java.lang.Enum<?>> enumClass();

   public abstract boolean ignoreCase() default false;
}