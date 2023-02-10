package com.externalbank.otherbank;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import com.externalbank.otherbank.OtherbankApplication;
import com.externalbank.otherbank.domain.dto.ScheduledOperationDTO;
import com.externalbank.otherbank.domain.service.ScheduledOperationService;
import com.externalbank.otherbank.exception.FinderException;
import com.externalbank.otherbank.task.RunnableTask;


/**
 * This class configures the auto bank transfers execution. 
 * 
 * @author Isabelle Deligniere
 *
 */
@SpringBootApplication
@EnableScheduling
public class OtherbankApplication implements SchedulingConfigurer {
    private static final Logger log = LoggerFactory.getLogger(OtherbankApplication.class);

    @Autowired
	private ApplicationContext applicationContext;

    @Autowired
    private ScheduledOperationService scheduledOperationService;

	public static void main(String[] args) {
		// Set Port on 8181
		System.getProperties().put( "server.port", 8181 ); 
		SpringApplication.run(OtherbankApplication.class, args);
	}
	
	
	 /**
     * This method configures the scheduled operations to be executed.
     * 
     * @param taskRegistrar	the task to be registered.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    	Iterable<ScheduledOperationDTO> scheduledOperations = null;
		try {
			scheduledOperations = scheduledOperationService.findScheduledOperations();
		} catch (FinderException e) {
			e.getMessage();
		}
				
		for(ScheduledOperationDTO scheduledOperationDTO : scheduledOperations){
			RunnableTask runnableTask = new RunnableTask(scheduledOperationDTO);
			applicationContext.getAutowireCapableBeanFactory().autowireBean(runnableTask);
			applicationContext.getAutowireCapableBeanFactory().initializeBean(runnableTask, "runnableTask");
			taskRegistrar.addTriggerTask(runnableTask , new Trigger() {
	            @Override
	            public Date nextExecutionTime(TriggerContext triggerContext) {
	            	String cron = scheduledOperationDTO.getCronExpression();
	            	log.info(cron);
	            	CronTrigger cronTrigger = new CronTrigger(cron);
	            	Date nextExec = cronTrigger.nextExecutionTime(triggerContext);
	                return nextExec;
	            }
			});
		}		
    }
	    
}

