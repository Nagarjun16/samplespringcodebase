/**
 * Listener class for intercepting calls during trigger execution
 */
package com.ngen.cosys.scheduler.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;
import org.springframework.stereotype.Component;

@Component
public class TriggerListner implements TriggerListener {

	@Override
	public String getName() {
		return "globalTrigger";
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		// No implementation if required need to add functionality in this method
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		// No implementation if required need to add functionality in this method

	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		// No implementation if required need to add functionality in this method
	}

}