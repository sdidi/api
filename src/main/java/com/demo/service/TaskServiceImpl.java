package com.demo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.data.TaskRepository;
import com.demo.data.UserRepository;
import com.demo.model.Task;
import com.demo.model.User;

@Service("taskService")
public class TaskServiceImpl implements TaskService{
	private static final AtomicLong counter = new AtomicLong();
	@Autowired
	private TaskRepository taskRepository;
    
    private static List<Task> tasks;
     
        
    /***
     * method returns a list from a recordset 
     * Uses a CRUDRepository method findAll()
     */
    public List<Task> findAllTasks() {
    	tasks = new ArrayList<Task>(); 
    	//uses CrudRepository method to find all instances of user in the database and 
    	//then iterates over each record to assign it to the list users. 
        taskRepository.findAll().forEach(tasks::add); 
       return tasks;
    }
     
    /***
     * returns a task is present or else null
     * It uses CRUDRepository method findById and orElse(null)
     */
   	public Task findById(long id) {
   		 return taskRepository.findById(id).orElse(null);
    }
   	
    /***
     * Calls a findAllTasks() method to get a list of tasks.
     * Returns a task searched by name.
     */
    public Task findByName(String name) {
    	tasks = findAllTasks(); 
        for(Task task : tasks){
            if(task.getName().equalsIgnoreCase(name)){
                return task;
            }
        }
        return null;
    }
    
    /***
     * Calls a findAllTasks() method to get a list of tasks.
     * Returns a task searched by description.
     */
    public Task findByDescription(String description) {
    	tasks = findAllTasks(); 
        for(Task task : tasks){
            if(task.getDescription().equalsIgnoreCase(description)){
                return task;
            }
        }
        return null;
    }
     
    /***
     *  takes a task instance as an argument
     *  persist it to a database using a CRUDRepository method save()
     */
    public void saveTask(Task task) {
        task.setTask_id(counter.incrementAndGet());
        taskRepository.save(task);
    }
    
    /***
     * takes a task instance as an argument
     * as in saveTask method update uses a CRUDRepository methods save()
     * If the id exists it updates otherwise it creates a new record
     */
    public void updateTask(Task task) {
    	taskRepository.save(task);
    }
 
    /***
     * tasks an id and uses CRUDRepository method deleteById()
     */
    public void deleteTaskById(long id) {
         taskRepository.deleteById(id);
    }
 
    /***
     * checks existence of a task instance by using CRUDRepository method existsById();
     */
    public boolean isTaskExist(Task task) {
        return taskRepository.existsById(task.getTask_id());
    }
    
    /***
     * tasks an id and uses CRUDRepository method deleteAll()
     */
    public void deleteAllTasks(){
       taskRepository.deleteAll();
    }
}
