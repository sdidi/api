package com.usertask.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.usertask.model.*;
import com.usertask.service.TaskService;
import com.usertask.util.CustomErrorType;


@Component
@Repository
public class ScheduledTasks {
	@PersistenceContext
	private EntityManager entityManager;
	
	 @Autowired
	 TaskService taskService;
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

   
    
    @Scheduled(fixedRate = 20000, initialDelay = 2000)
    public void scheduleTaskWithInitialDelay() {
    	logger.info("######################## Scheduled task output #############################");
    		//Task task = entityManager.find(Task.class, new Long(1));
    	 getDelayed();
    	 
    	 
   	 	
    }
    
    public void updateTask(List<Task> tasks) {
    	
        for(Task task: tasks) {
        	task.setStatus("Done");
        	taskService.updateTask(task);
        }
        
 
    }
    
    @Transactional
    public void getDelayed(){
        	
    	//entityManager.getTransaction().begin();
    	List<Task> result = entityManager.createQuery( "from Task", Task.class ).getResultList();
    	List<Task> myList = new ArrayList<Task>();
    	logger.info("The total list of tasks in the database: "+result.size());
    	long millis=System.currentTimeMillis();  
    	Date today =new Date(millis);  
   	 for (Task task : result) {
        if(task.getStatus().equals("Pending") && task.getDue_date().before(today)) {
   		    logger.info("Record "+task);
           	 myList.add(task);
           	}
       }
   
   	 
    	updateTask(myList);
    	
    	
    }
    
    
}
