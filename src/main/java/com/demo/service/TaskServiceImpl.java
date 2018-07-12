package com.demo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.demo.model.Task;

@Service("taskService")
public class TaskServiceImpl implements TaskService{
private static final AtomicLong counter = new AtomicLong();
    
    private static List<Task> tasks;
     
    static{
        tasks= addTask();
    }
 
    public List<Task> findAllTasks() {
        return tasks;
    }
     
    public Task findById(long id) {
        for(Task task : tasks){
            if(task.getTask_id() == id){
                return task;
            }
        }
        return null;
    }
     
    public Task findByDescription(String description) {
        for(Task task : tasks){
            if(task.getDescription().equalsIgnoreCase(description)){
                return task;
            }
        }
        return null;
    }
     
    public void saveTask(Task task) {
        task.setTask_id(counter.incrementAndGet());
        tasks.add(task);
    }
 
    public void updateTask(Task task) {
        int index = tasks.indexOf(task);
        tasks.set(index, task);
    }
 
    public void deleteTaskById(long id) {
         
        for (Iterator<Task> iterator = tasks.iterator(); iterator.hasNext(); ) {
            Task task = iterator.next();
            if (task.getTask_id() == id) {
                iterator.remove();
            }
        }
    }
 
    public boolean isTaskExist(Task task) {
        return findByDescription(task.getDescription())!=null;
    }
     
    public void deleteAllTasks(){
       tasks.clear();
    }
 
    private static List<Task> addTask(){
    	List<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task(1,"Completed","Web Application","22/01/2018","12/06/2018"));
        tasks.add(new Task(2,"Pending","Mobile Application","13/03/2018","10/06/2018"));
        tasks.add(new Task(3,"Completed","Web service Application","02/04/2018","12/08/2018"));
        tasks.add(new Task(4,"Delayed","Web Application","07/05/2018","25/06/2018"));
      //  users.add(new User(counter.incrementAndGet(),"Niel",33, 70000));
        return tasks;
    }
}
