package com.anotherbank.mochabank;

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

import com.anotherbank.mochabank.domain.dto.ScheduledOperationDTO;
import com.anotherbank.mochabank.domain.service.ScheduledOperationService;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.task.RunnableTask;


@SpringBootApplication
@EnableScheduling
public class MochabankApplication implements SchedulingConfigurer {
    private static final Logger log = LoggerFactory.getLogger(MochabankApplication.class);

    @Autowired
	private ApplicationContext applicationContext;

    @Autowired
    private ScheduledOperationService scheduledOperationService;

    public static void main(String[] args) {
    	SpringApplication.run(MochabankApplication.class, args);
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


