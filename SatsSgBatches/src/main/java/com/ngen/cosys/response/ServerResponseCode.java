/**
 * Response code for Quartz Job Controller Function
 */
package com.ngen.cosys.response;

public enum ServerResponseCode {

   JOB_WITH_SAME_NAME_EXIST("JOB_NAME_EXISTS"), 
   JOB_NAME_NOT_PRESENT("JOB_NAME_NOT_PRESENT"), 
   JOB_ALREADY_IN_RUNNING_STATE("JOB_IN_RUNNING_STATE"), 
   JOB_NOT_IN_PAUSED_STATE("JOB_NOT_IN_PAUSED_STATE"), 
   JOB_NOT_IN_RUNNING_STATE("JOB_NOT_IN_RUNNING_STATE"), 
   JOB_DOESNT_EXIST("JOB_DOESNOT_EXISTS");

   private String code;

   private ServerResponseCode(String code) {
      this.code = code;
   }

   public String getCode() {
      return this.code;
   }
}