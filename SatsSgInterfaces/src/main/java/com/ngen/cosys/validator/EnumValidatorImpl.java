package com.ngen.cosys.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {
   private EnumValidator annotation;

   @Override
   public void initialize(EnumValidator annotation) {
      this.annotation = annotation;
   }

   @Override
   public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {
      boolean result = false;

      Object[] enumValues = this.annotation.enumClass().getEnumConstants();

      if (enumValues != null) {
         for (Object enumValue : enumValues) {
            if (valueForValidation.equals(enumValue.toString()) || (this.annotation.ignoreCase() && valueForValidation.equalsIgnoreCase(enumValue.toString()))) {
               result = true;
               break;
            }
         }
      }

      return result;
   }
   
   public String getValues() {
      return "test";
   }
}