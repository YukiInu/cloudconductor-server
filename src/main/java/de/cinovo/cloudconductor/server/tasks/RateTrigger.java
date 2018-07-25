/**
 * 
 */
package de.cinovo.cloudconductor.server.tasks;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.scheduling.TriggerContext;

/**
 * Copyright 2018 Cinovo AG<br>
 * <br>
 * 
 * @author mweise
 *
 */
public class RateTrigger extends AbstractTrigger {
	
	RateTrigger(IServerTasks task) {
		super(task);
	}
	
	@Override
	public String getNextExecutionAsString() {
		Date nextExecution = this.getNextExecution(DateTime.now());
		return this.sdf.format(nextExecution);
	}
	
	@Override
	protected Date getNextExectionTime(TriggerContext triggerContext) {
		Date lastCompletion = triggerContext.lastCompletionTime();
		
		if (lastCompletion == null) {
			return this.getNextExecution(null);
		}
		
		return this.getNextExecution(new DateTime(lastCompletion));
	}
	
	private Date getNextExecution(DateTime lastCompletion) {
		DateTime now = DateTime.now();
		IServerTasks task = this.getTask();
		
		DateTime nextExecution;
		if (lastCompletion == null) {
			nextExecution = now;
		} else {
			nextExecution = lastCompletion.plusMillis((int) task.getTimerUnit().toMillis(task.getTimer()));
		}
		
		while (nextExecution.isBefore(now)) {
			nextExecution = nextExecution.plusMillis((int) task.getTimerUnit().toMillis(task.getTimer()));
		}
		return nextExecution.toDate();
	}
	
}
