package com.usertask.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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


@Component
@Repository
public class ScheduledTasks {
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

   
    
    @Scheduled(fixedRate = 20000, initialDelay = 2000)
    public void scheduleTaskWithInitialDelay() {
    	logger.info("######################## Scheduled task output #############################");
    		//Task task = entityManager.find(Task.class, new Long(1));
    	 List<Task> list = getDelayed();
    	 
    	 
   	 	
    }
    
    @Transactional
    public List<Task> getDelayed(){
        	
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
    /*	
   	entityManager.getTransaction().begin();
	 for(Task task: myList) {
		 task.setStatus("Done");
      	 entityManager.persist(task);
	 }
	 
	entityManager.getTransaction().commit();
	entityManager.close();    	 	 
    	*/
    	return myList;
    	
    	
    }
    
    
}
