package com.ngen.cosys.shipment.terminaltoterminalhandover.controller;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ngen.cosys.command.RequestKey;
import com.ngen.cosys.common.ServiceExecutor;
import com.ngen.cosys.common.Temp;
import com.ngen.cosys.framework.model.BaseBO;

@Component(value = "responder")
public class ControllerResponder {

   private static final String TERMINAL_TO_TERMINAL_HANDOVER_SERVICE_IMPL = "terminalToTerminalHandoverServiceImpl";
   @Autowired
   @Qualifier(TERMINAL_TO_TERMINAL_HANDOVER_SERVICE_IMPL)
   private ServiceExecutor service;

   protected BaseBO getResponse(RequestKey keys, BaseBO obj) throws IllegalArgumentException, SecurityException,
         InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
      ((Temp<ServiceExecutor, BaseBO>) (service = keys.commandInitializer(service)))
            .request(obj, keys.getTaskSchedular()).run();
      return ((Temp<ServiceExecutor, BaseBO>) service).response();
   }
}