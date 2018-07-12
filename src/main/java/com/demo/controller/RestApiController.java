package com.demo.controller;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.demo.data.TaskRepository;
import com.demo.data.UserRepository;
import com.demo.model.Task;
import com.demo.model.User;
import com.demo.service.TaskService;
import com.demo.service.UserService;
import com.demo.util.CustomErrorType;


@RestController
@RequestMapping("/api")
public class RestApiController {
	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
	private static final AtomicLong counter = new AtomicLong();
    @Autowired
    UserService userService; 
    @Autowired
    TaskService taskService;
    
    @Autowired
    UserRepository userData;
        
    @Autowired
    TaskRepository taskData;
 
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
 
    //Display a single User
    @RequestMapping(value = "/user/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("user_id") long id) {
        logger.info("Fetching a User with id {}", id);
       // User user = userService.findById(id);
        Optional<User> userOpt = userData.findById(id); //returns a crudrepository
        User user = userService.findById(id);
        if (userOpt.isPresent())
        { user = userOpt.get(); }
        
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
    
    //used for MySQL data persistence
    public void persistData(User user) {
    	try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpapersistenceUnit");
    		EntityManager em = emf.createEntityManager();
    		em.getTransaction().begin();
    		//em.merge(user); // merge instead of persist
    		em.persist(user);
    		em.getTransaction().commit();		
    		em.close();
    		
            }catch(Exception e) {
            	System.out.println("Error on jpa persist "+ e);
            }
    	
    }
    
    
    //Create a user
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", user);
 
        if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getUsername());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with username " + user.getUsername() + " already exist."),HttpStatus.CONFLICT);
        }
        
       // user.setUser_id(1);
        /* to save to MySQL database */
        //persistData(user);
        
        /* to save to H2 file-based database */
        user.setUser_id(counter.incrementAndGet());
        userData.save(user);
        
        //userService.saveUser(user);     //to be disabled after testing   
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{user_id}").buildAndExpand(user.getUser_id()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    // Create a task
    @RequestMapping(value = "/task/", method = RequestMethod.POST)
    public ResponseEntity<?> createTask(@RequestBody Task task, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Task : {}", task);
 
        if (taskService.isTaskExist(task)) {
            logger.error("Unable to create. A task with description {} already exist", task.getDescription());
            return new ResponseEntity(new CustomErrorType("Unable to create. A Task with a description " + task.getDescription() + " already exist."),HttpStatus.CONFLICT);
        }
        
       	
		taskService.saveTask(task);
		
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/task/{id}").buildAndExpand(task.getTask_id()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 
    
    
    //Update a user
    @RequestMapping(value = "/user/{user_id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("user_id") long id, @RequestBody User user) {
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
        persistData(currentUser); //add data to the database
        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
    
    //update a task
    @RequestMapping(value = "/task/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTask(@PathVariable("id") long id, @RequestBody Task task) {
        logger.info("Updating a task with id {}", id);
 
        Task currentTask = taskService.findById(id);
 
        if (currentTask == null) {
            logger.error("Unable to update a task with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update a task with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
 
        currentTask.setDescription(task.getDescription());
        currentTask.setStatus(task.getStatus());
        currentTask.setAssign_date(task.getAssign_date());
        currentTask.setDue_date(task.getDue_date());
 
        taskService.updateTask(currentTask);
        return new ResponseEntity<Task>(currentTask, HttpStatus.OK);
    }
    
    //delete a user
    @RequestMapping(value = "/user/{user_id}", method = RequestMethod.DELETE)
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
 
  //delete a task
    @RequestMapping(value = "/task/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTask(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting a task with id {}", id);
 
        Task task = taskService.findById(id);
        if (task == null) {
            logger.error("Unable to delete. Task with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Task with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        taskService.deleteTaskById(id);
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
