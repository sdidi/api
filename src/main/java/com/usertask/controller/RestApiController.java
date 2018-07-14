package com.usertask.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.usertask.data.TaskRepository;
import com.usertask.data.UserRepository;
import com.usertask.model.Task;
import com.usertask.model.User;
import com.usertask.service.TaskService;
import com.usertask.service.UserService;
import com.usertask.util.CustomErrorType;


@EntityScan( basePackages = {"com.usertask"} )
@RestController
@RequestMapping("/api")
public class RestApiController {
	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
	private static final AtomicLong counter = new AtomicLong();
    @Autowired
    UserService userService; 
    @Autowired
    TaskService taskService;
    
     
    //List all users 
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
    
    //List all tasks 
    @RequestMapping(value = "/task/", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> listAllTasks() {
        List<Task> tasks = taskService.findAllTasks();
        if (tasks.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }
    
  //List all tasks for a single user 
    @RequestMapping(value = "/user/{user_id}/task/", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> listAllTasks(@PathVariable("user_id") long user_id) {
        List<Task> tasks = taskService.findByUserID(user_id);
        if (tasks.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }
 
    //Display a single User
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        logger.info("Fetching a User with id {}", id);
       // User user = userService.findById(id);
         User user = userService.findById(id);
        
         if (user == null) {
         logger.error("User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
    //Display a single Task
    @RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTask(@PathVariable("id") long id) {
        logger.info("Fetching a Task with id {}", id);
        Task task = taskService.findById(id);
        if (task == null) {
            logger.error("Task with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Task with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }
    
  //Display a task by user id
    @RequestMapping(value = "/user/{user_id}/task/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTaskbyUser(@PathVariable("user_id") long user_id, @PathVariable("id") long id) {
    	Task task = taskService.findByUserID(user_id, id);
        if (task == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }
    
  //Display a tasks by status
    @RequestMapping(value = "/task/task/{statuscode}", method = RequestMethod.GET)
    public ResponseEntity<?> getTaskbyStatus(@PathVariable("statuscode") int statuscode) {
    	String status;
    	switch(statuscode) {
    	case 0: status = "Completed"; break;
    	case 1: status = "Pending"; break;
    	case 2: status = "Delayed"; break;
    	default: status = "Not known"; break;  	}
    	List<Task> tasks = taskService.findByStatus(status);
        if (tasks.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }
    
    //used for MySQL external data persistence
    public void persistData(User user) {
    	try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpapersistenceUnit");
    		EntityManager em = emf.createEntityManager();
    		em.getTransaction().begin();
    		logger.info("Populate user data: {}", user);
    		//em.merge(user); // merge instead of persist
    		em.persist(user);
    		em.getTransaction().commit();		
    		em.close();
    		
            }catch(Exception e) {
            	 logger.info("Error on jpa persist", e);
            	}
    	
    }
    
    
    //Create a user
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User hererrre : {}", user);
 
        if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getUsername());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with username " + user.getUsername() + " already exist."),HttpStatus.CONFLICT);
        }
         //persist the user data      
         userService.saveUser(user);       
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getUser_id()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    // Create a task
    @RequestMapping(value = "/task/{user_id}/task/", method = RequestMethod.POST)
    public ResponseEntity<?> createTask(@RequestBody Task task, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Task : {}", task);
 
        if (taskService.isTaskExist(task)) {
            logger.error("Unable to create. A task with description {} already exist", task.getDescription());
            return new ResponseEntity(new CustomErrorType("Unable to create. A Task with a description " + task.getDescription() + " already exist."),HttpStatus.CONFLICT);
        }
        
       	//persist task data
		taskService.saveTask(task);
		
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/task/{id}").buildAndExpand(task.getTask_id()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 
    
    
    //Update a user
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);
 
        User currentUser = userService.findById(id);
 
        if (currentUser == null) {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
 
        currentUser.setUsername(user.getUsername());
        currentUser.setFirstname(user.getFirstname());
        currentUser.setLastname(user.getLastname());
       // persistData(currentUser); // external database
        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
    
    //update a task
    @RequestMapping(value = "/user/{user_id}/task/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTask(@PathVariable("id") long id, @PathVariable("user_id") long user_id, @RequestBody Task task ) {
        logger.info("Updating a task with id {}", id);
 
        Task currentTask = taskService.findById(id);
 
        if (currentTask == null) {
            logger.error("Unable to update a task with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update a task with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        currentTask.setName(task.getName());
        currentTask.setUser_id(user_id);
        currentTask.setDescription(task.getDescription());
        currentTask.setStatus(task.getStatus());
        
        String dueDateString = null, assignDateString = null;
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
     	assignDateString = sdformat.format(task.getAssign_date());
     	dueDateString = sdformat.format(task.getDue_date());     	
        }catch (Exception ex ){
        	 logger.error("Incorrect format", ex);
        }
        
        currentTask.setAssign_date(assignDateString);
        currentTask.setDue_date(dueDateString);
 
        taskService.updateTask(currentTask);
        return new ResponseEntity<Task>(currentTask, HttpStatus.OK);
    }
    
    //delete a user
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting User with id {}", id);
 
        User user = userService.findById(id);
        if (user == null) {
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
 
  //delete a task by user id and task id
    @RequestMapping(value = "/user/{user_id}/task/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTask(@PathVariable("user_id") long user_id, @PathVariable("id") long id) {
        logger.info("Fetching & Deleting a task with id {}", id);
 
        Task task = taskService.findTaskByUserIDTaskID(user_id, id);
        if (task == null) {
            logger.error("Unable to delete. Task with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Task with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        taskService.deleteTaskByUserIDTaskID(task);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
 
    
    //Delete all users
    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        logger.info("Deleting All Users");
 
        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
        
  //Delete all tasks
    @RequestMapping(value = "/tasks/", method = RequestMethod.DELETE)
    public ResponseEntity<Task> deleteAllTasks() {
        logger.info("Deleting all tasks");
 
        taskService.deleteAllTasks();
        return new ResponseEntity<Task>(HttpStatus.NO_CONTENT);
    }

}
