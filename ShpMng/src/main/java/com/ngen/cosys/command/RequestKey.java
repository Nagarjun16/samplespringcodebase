/**
 * @author NIIT Technologies Ltd.
 *
 */

package com.ngen.cosys.command;

import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.common.ServiceExecutor;
import com.ngen.cosys.shipment.terminaltoterminalhandover.service.TerminalToTerminalHandoverService;
import com.ngen.cosys.shipment.terminaltoterminalhandover.service.TerminalToTerminalHandoverServiceImpl;

public enum RequestKey {

   Test("test", TerminalToTerminalHandoverServiceImpl.class), 
   GETDETAILSOFSHIPMENTFROMTERMINAL("getDetailsOfShipmentFromTerminal",TerminalToTerminalHandoverServiceImpl.class), 
   TRANSFERSHIPMENTTOTERMINAL("transferShipmentToTerminal",TerminalToTerminalHandoverServiceImpl.class), 
   GETDETAILSOFSHIPMENTTOTERMINAL("getDetailsOfShipmentToTerminal", TerminalToTerminalHandoverServiceImpl.class);

   private String taskSchedular;
   private Class<?> clazz;

   @Autowired
   private TerminalToTerminalHandoverService terminalToTerminalHandoverServiceImpl;

   RequestKey(String taskSchedular, Class<?> clazz) {
      this.clazz = clazz;
      this.taskSchedular = taskSchedular;
   }

   public ServiceExecutor commandInitializer(ServiceExecutor service) {

      return service;
   }

   public String getTaskSchedular() {
      return this.taskSchedular;
   }
}